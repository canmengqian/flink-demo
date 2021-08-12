package org.example.service.stream;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.flink.api.common.io.FileInputFormat;
import org.apache.flink.api.common.io.FilePathFilter;
import org.apache.flink.api.common.state.ValueState;
import org.apache.flink.api.common.state.ValueStateDescriptor;
import org.apache.flink.api.java.io.TextInputFormat;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.core.fs.Path;
import org.apache.flink.shaded.jackson2.org.yaml.snakeyaml.events.Event;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.RichCoFlatMapFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.functions.sink.WriteFormat;
import org.apache.flink.streaming.api.functions.sink.WriteFormatAsText;
import org.apache.flink.streaming.api.functions.sink.WriteSinkFunctionByMillis;
import org.apache.flink.streaming.api.functions.source.FileProcessingMode;
import org.apache.flink.util.Collector;

import java.io.File;

/**
 * @author zhengzz
 * @version 1.0.0
 * @className SimpleStreamService
 * @description TODO
 * @date 2021/8/12
 */
@Data
@Slf4j
public class SimpleStreamService {
    public static void main(String[] args) throws Exception {
       /* final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        FileInputFormat fileInputFormat = new TextInputFormat(Path.fromLocalFile(new File("")));
        DataStreamSource<String> dataStreamSource= env.readTextFile("D:\\test\\table.txt");
        DataStream<String> evets = dataStreamSource.setParallelism(10).map(line-> line);

        WriteFormat writeFormat = new WriteFormatAsText();
        SinkFunction sink = new WriteSinkFunctionByMillis("d://sink.txt",writeFormat,10000);
        evets.addSink(sink);
        evets.executeAndCollect().forEachRemaining(line->{
            log.info(line);
        });
        env.execute();*/
        test();

    }

    public static void test() throws Exception {
        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> dataStreamSource = streamEnv.readTextFile("D:\\test\\table.txt");
        dataStreamSource.setParallelism(2).map(str -> "Hello:" + str)
                .filter(str->str.contains("H"))
                .executeAndCollect()
                .forEachRemaining(s -> System.out.println(s));

        // streamEnv.execute("packaged");
    }

    /**
     * 控制流
     */
    public void controlStream() throws Exception {
        StreamExecutionEnvironment streamEnv = StreamExecutionEnvironment.getExecutionEnvironment();
        // 设置控制流
       DataStream<String> control= streamEnv.fromElements("DROP", "IGNORE").keyBy(x->x);
       // 设置输入流
        DataStream<String> streamOfWords = streamEnv.setParallelism(2).fromElements("Apache", "DROP", "Flink", "IGNORE").keyBy(x -> x);
        // TODO
        control.connect(streamOfWords)
                .flatMap(new ControlFunction())
                .print();
        streamEnv.execute();
    }

}
@Slf4j
class ControlFunction extends RichCoFlatMapFunction<String, String, String> {
    private ValueState<Boolean> blocked;

    @Override
    public void open(Configuration config) {
        blocked = getRuntimeContext()
                .getState(new ValueStateDescriptor<>("blocked", Boolean.class));
    }

    @Override
    public void flatMap1(String control_value, Collector<String> out) throws Exception {
        log.info("update");
        blocked.update(Boolean.TRUE);
    }

    @Override
    public void flatMap2(String data_value, Collector<String> out) throws Exception {
        log.info("set status");
        if (blocked.value() == null) {
            out.collect(data_value);
        }
    }
}

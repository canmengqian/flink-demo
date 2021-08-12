package org.example.service.stream;

import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.operators.Keys;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.util.keys.KeySelectorUtil;
import org.example.bean.Person;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author zhengzz
 * @version 1.0.0
 * @className PersonStreamService
 * @description TODO
 * @date 2021/8/12
 */
public class PersonStreamService {
    /**
     * stream执行
     * @throws Exception
     */
    public void process() throws Exception {
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        /**
         * 设置元素
         */
        DataStream<Person> flintstones = env.fromElements(
                new Person("Fred", 35),
                new Person("Wilma", 35),
                new Person("Pebbles", 2));

        DataStream<Person> adults = flintstones.filter(new FilterFunction<Person>() {
            @Override
            public boolean filter(Person person) throws Exception {
                return person.age >= 18;
            }
        });
        adults.print();
        env.execute();
    }

    /**
     * 使用水位线标记
     */
    public void processWatermarks() throws Exception {
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Person> data = env.fromElements(
                new Person("Fred", 35),
                new Person("Wilma", 35),
                new Person("Pebbles", 2));
        WatermarkStrategy<Person> watermarkStrategy =WatermarkStrategy.forBoundedOutOfOrderness(Duration.ofSeconds(20L));
        data.assignTimestampsAndWatermarks(watermarkStrategy).map(Person::getName).print();
        env.execute("person watermarker");
    }

    /**
     * 窗口
     * @throws Exception
     */
    public void processWindows() throws Exception {
        final StreamExecutionEnvironment env =
                StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<Person> data = env.fromElements(
                new Person("Fred", 35),
                new Person("Wilma", 35),
                new Person("Pebbles", 2));
        data.keyBy(Person::getAge).window(TumblingEventTimeWindows.of(Time.of(20, TimeUnit.SECONDS))).max(0).print();
        env.execute("person windows");
    }
}

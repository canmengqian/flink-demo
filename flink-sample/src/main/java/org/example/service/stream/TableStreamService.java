package org.example.service.stream;

import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.LocalEnvironment;
import org.apache.flink.table.api.Table;
import org.apache.flink.table.api.TableEnvironment;
import org.apache.flink.table.api.bridge.java.BatchTableEnvironment;
import org.apache.flink.table.sources.CsvTableSource;
import org.example.bean.Person;

/**
 * @author zhengzz
 * @version 1.0.0
 * @className TableStreamService
 * @description TODO
 * @date 2021/8/12
 */
public class TableStreamService {
    String path ="D:\\github\\fink-demo\\flink-sample\\src\\main\\resources\\doc\\person.csv";
    public void procTable(){
        final ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        BatchTableEnvironment tableEnv = BatchTableEnvironment.create(env);
        DataSet<Person> personInput = env
                .readCsvFile(path)
                .ignoreFirstLine().pojoType(Person.class,"name","age");
        Table table =  tableEnv.fromDataSet(personInput);
       tableEnv.registerTable("person",table);

         tableEnv.sqlQuery("SELECT COUNT(*) FROM person").execute().print();
    }
}

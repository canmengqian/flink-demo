package org.example.simple;

import org.example.service.stream.SimpleStreamService;
import org.junit.Test;

public class SimpleDemoTest {
    @Test
    public  void test() {

    }
    @Test
    public void testControlStream() throws Exception {
        SimpleStreamService service = new SimpleStreamService();
        service.controlStream();
    }

}
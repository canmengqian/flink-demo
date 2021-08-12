package org.example.service.stream;

import junit.framework.TestCase;
import org.junit.Test;

public class TableStreamServiceTest extends TestCase {
    @Test
    public void test() {
        TableStreamService service = new TableStreamService();
        service.procTable();
    }

}
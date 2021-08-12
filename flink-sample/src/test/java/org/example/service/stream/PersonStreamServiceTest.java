package org.example.service.stream;

import junit.framework.TestCase;
import org.junit.Test;

public class PersonStreamServiceTest extends TestCase {

    @Test
    public void testProcess() throws Exception {
        PersonStreamService service = new PersonStreamService();
        service.process();
    }

    @Test
    public void testProcessWatermarks() throws Exception {
        PersonStreamService service = new PersonStreamService();
        service.processWatermarks();
    }

    @Test
    public void testProcessWindows() throws Exception {
        PersonStreamService service = new PersonStreamService();
        service.processWindows();
    }
}
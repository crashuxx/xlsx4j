package org.xlsx;

import org.junit.Assert;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;

public class XLSXReaderTest {

    @Test
    public void testIterator() throws IOException, XMLStreamException, InterruptedException {

        XLSXReader reader = new XLSXReader( new FileInputStream("res/test/test.xlsx"));

        Assert.assertTrue(reader.getSheet(0).iterator().hasNext());

        for(XLSXSheetRow row: reader.getSheet(0) ) {
            Assert.assertNotNull(row.get(0));
        }
    }
}

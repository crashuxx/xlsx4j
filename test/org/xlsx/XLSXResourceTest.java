package org.xlsx;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;

public class XLSXResourceTest {

    @Test
    public void testExtract() throws IOException {

        StreamXLSXResource xlsxResource = new StreamXLSXResource(new FileInputStream("res/test/test.xlsx"));
        Assert.assertTrue(xlsxResource.has("xl/workbook.xml"));
        Assert.assertTrue(xlsxResource.has("xl/sharedStrings.xml"));
        Assert.assertTrue(xlsxResource.has("xl/worksheets/sheet1.xml"));
    }
}

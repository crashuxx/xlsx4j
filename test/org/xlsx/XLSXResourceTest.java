package org.xlsx;

import org.junit.Assert;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class XLSXResourceTest {

    @Test
    public void testExtract() throws IOException {

        XLSXResource xlsxResource = new XLSXResource(new FileInputStream("res/test/test.xlsx"));
        Assert.assertTrue(xlsxResource.has("xl/workbook.xml"));
        Assert.assertTrue(xlsxResource.has("xl/sharedStrings.xml"));
        Assert.assertTrue(xlsxResource.has("xl/worksheets/sheet1.xml"));
    }
}

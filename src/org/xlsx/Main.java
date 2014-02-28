package org.xlsx;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, XMLStreamException, InterruptedException {

        long start = System.nanoTime();

        XLSXReader reader = new XLSXReader( new FileInputStream("test.xlsx"));

//        reader.readRelationships("xl/_rels/workbook.xml.rels");
        reader.readSharedStrings("xl/sharedStrings.xml");
        reader.readSheet("xl/worksheets/sheet1.xml");

        System.out.println("All " + ((System.nanoTime() - start) / 1000000) + " ms");
    }
}

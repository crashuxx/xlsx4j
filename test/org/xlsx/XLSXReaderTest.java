package org.xlsx;

import org.apache.commons.collections4.Factory;
import org.apache.commons.collections4.list.LazyList;
import org.junit.Assert;
import org.junit.Test;

import javax.xml.stream.XMLStreamException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class XLSXReaderTest {

    static String filename = "res/test/test.xlsx";

    @Test
    public void testStreamIteration() throws IOException, XMLStreamException, InterruptedException {

        long startAll = System.nanoTime();

        XLSXReader reader = new XLSXReader(new FileInputStream(filename));

        long start = System.nanoTime();
        Assert.assertTrue(reader.getSheet(0).iterator().hasNext());
        System.out.println("testStreamIteration first hasNext " + ((System.nanoTime() - start) / 1000000) + " ms");

        start = System.nanoTime();
        for (XLSXSheetRow row : reader.getSheet(0)) {
            Assert.assertNotNull(row.get(0));
            //    System.out.println(row.get(0));
        }
        System.out.println("testStreamIteration foreach " + ((System.nanoTime() - start) / 1000000) + " ms");
        System.out.println("testStreamIteration All " + ((System.nanoTime() - startAll) / 1000000) + " ms");
    }

    @Test
    public void testFileIteration() throws IOException, XMLStreamException, InterruptedException {

        long startAll = System.nanoTime();

        XLSXReader reader = new XLSXReader(new FileXLSXResource(filename));

        long start = System.nanoTime();
        Assert.assertTrue(reader.getSheet(0).iterator().hasNext());
        System.out.println("testFileIteration first hasNext " + ((System.nanoTime() - start) / 1000000) + " ms");

        start = System.nanoTime();
        for (XLSXSheetRow row : reader.getSheet(0)) {
            Assert.assertNotNull(row.get(0));
            //    System.out.println(row.get(0));
        }
        System.out.println("testFileIteration foreach " + ((System.nanoTime() - start) / 1000000) + " ms");
        System.out.println("testFileIteration All " + ((System.nanoTime() - startAll) / 1000000) + " ms");
    }

    @Test
    public void testStreamLazyIteration() throws IOException, XMLStreamException, InterruptedException {

        long startAll = System.nanoTime();

        XLSXReader reader = new XLSXReader(new FileInputStream(filename));

        long start = System.nanoTime();
        Factory<String> factory = new XMLSharedStringFactory(reader.getSharedStringsReader());
        List<String> sharedStringsList = LazyList.lazyList(new ArrayList<String>(), factory);
        reader.setSharedStrings(sharedStringsList);

        Assert.assertTrue(reader.getSheet(0).iterator().hasNext());
        System.out.println("testStreamLazyIteration first hasNext " + ((System.nanoTime() - start) / 1000000) + " ms");

        start = System.nanoTime();
        for (XLSXSheetRow row : reader.getSheet(0)) {
            Assert.assertNotNull(row.get(0));
//            System.out.println(row.get(0));
        }
        System.out.println("testStreamLazyIteration foreach " + ((System.nanoTime() - start) / 1000000) + " ms");
        System.out.println("testStreamLazyIteration All " + ((System.nanoTime() - startAll) / 1000000) + " ms");
    }

    @Test
    public void testFileLazyIteration() throws IOException, XMLStreamException, InterruptedException {

        long startAll = System.nanoTime();

        XLSXReader reader = new XLSXReader(new FileXLSXResource(filename));

        long start = System.nanoTime();
        Factory<String> factory = new XMLSharedStringFactory(reader.getSharedStringsReader());
        List<String> sharedStringsList = LazyList.lazyList(new ArrayList<String>(), factory);
        reader.setSharedStrings(sharedStringsList);

        Assert.assertTrue(reader.getSheet(0).iterator().hasNext());
        System.out.println("testFileLazyIteration first hasNext " + ((System.nanoTime() - start) / 1000000) + " ms");

        start = System.nanoTime();
        for (XLSXSheetRow row : reader.getSheet(0)) {
            Assert.assertNotNull(row.get(0));
            //    System.out.println(row.get(0));
        }
        System.out.println("testFileLazyIteration foreach " + ((System.nanoTime() - start) / 1000000) + " ms");
        System.out.println("testFileLazyIteration All " + ((System.nanoTime() - startAll) / 1000000) + " ms");
    }

    class XMLSharedStringFactory implements Factory<String> {

        XMLSharedStringReader reader;

        public XMLSharedStringFactory(XMLSharedStringReader xmlSharedStringReader) {
            reader = xmlSharedStringReader;
        }

        public String create() {
            try {
                return reader.next();
            } catch (XMLStreamException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

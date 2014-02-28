package org.xlsx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class XLSXReader implements Closeable {

    private static Log logger = LogFactory.getLog(XLSXReader.class);

    InputStream inputStream;
    Map<String, File> files;

    Map<String, String> relationship;
    List<String> sharedStrings;

    public XLSXReader(InputStream input) {
        inputStream = input;
        files = new HashMap<String, File>();
        sharedStrings = new ArrayList<String>();
        relationship = new HashMap<String, String>();
    }

    public void readRelationships(String name) throws IOException, XMLStreamException {

        InputStream stream = getInputStreamForFileName(name);
        XMLRelationReader reader = new XMLRelationReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        relationship = reader.all();
    }

    public void readSharedStrings(String name) throws IOException, XMLStreamException {

        InputStream stream = getInputStreamForFileName(name);
        XMLSharedStringReader reader = new XMLSharedStringReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        sharedStrings = reader.all();
    }

    public void readSheet(String name) throws IOException, XMLStreamException {

        InputStream stream = getInputStreamForFileName(name);
        XMLSheetReader reader = new XMLSheetReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        reader.setSharedStrings(sharedStrings);
        int i = 0;
        while( reader.next() != null ) {
            i++;
        }
        System.out.println(i);
    }

    private InputStream getInputStreamForFileName(String name) throws IOException {

        if( files.isEmpty() ) {
            extract();
        }

        if (files.containsKey(name)) {
            return new FileInputStream(files.get(name));
        }

        return null;
    }

    private void extract() throws IOException {

        long start = System.nanoTime();
        byte[] buffer = new byte[2048];
        ZipInputStream zipStream = new ZipInputStream(inputStream);

        try {
            ZipEntry entry;
            while ((entry = zipStream.getNextEntry()) != null) {

                long startTime = System.nanoTime();
                File file = File.createTempFile(getClass().getName(), null);
                file.deleteOnExit();

                OutputStream output = null;

                try {
                    output = new FileOutputStream(file);

                    int len = 0;
                    while ((len = zipStream.read(buffer)) > 0) {
                        output.write(buffer, 0, len);
                    }

                    files.put(entry.getName(), file);

                } finally {
                    if (output != null) {
                        output.close();
                    }
                }
                logger.info(entry.getName()+" "+((System.nanoTime() - startTime) / 1000000) + " ms");
            }
        } finally {
            zipStream.close();
        }

        logger.info("All "+((System.nanoTime() - start) / 1000000) + " ms");
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public void close() {
        for(Map.Entry<String,File> entry: files.entrySet()) {
            entry.getValue().delete();
        }
    }
}

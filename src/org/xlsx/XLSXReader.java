package org.xlsx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.*;

public class XLSXReader implements Closeable {

    private static Log logger = LogFactory.getLog(XLSXReader.class);

    XLSXResource xlsxResource;

    Map<String, String> relationship;
    List<String> sharedStrings;

    public XLSXReader(InputStream input) {
        sharedStrings = new ArrayList<String>();
        relationship = new HashMap<String, String>();

        xlsxResource = new XLSXResource(input);
    }

    public void readRelationships(String name) throws IOException, XMLStreamException {

        InputStream stream = xlsxResource.get(name);
        XMLRelationReader reader = new XMLRelationReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        relationship = reader.all();
    }

    public void readSharedStrings(String name) throws IOException, XMLStreamException {

        InputStream stream = xlsxResource.get(name);
        XMLSharedStringReader reader = new XMLSharedStringReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        sharedStrings = reader.all();
    }

    public void readSheet(String name) throws IOException, XMLStreamException {

        InputStream stream = xlsxResource.get(name);
        XMLSheetReader reader = new XMLSheetReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        reader.setSharedStrings(sharedStrings);
        int i = 0;
        while (reader.next() != null) {
            i++;
        }
    }

    protected void finalize() throws Throwable {
        close();
        super.finalize();
    }

    @Override
    public void close() {
        xlsxResource.close();
    }
}

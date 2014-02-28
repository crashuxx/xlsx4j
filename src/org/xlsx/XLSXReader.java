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

    /**
     *
     * fixme
     *
     * @param i
     * @return
     * @throws IOException
     * @throws XMLStreamException
     */
    public XMLSheetReader getSheet(int i) throws IOException, XMLStreamException {

        readSharedStrings("xl/sharedStrings.xml");
        return readSheet("xl/worksheets/sheet" + (i + 1) + ".xml");
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

    public XMLSheetReader readSheet(String name) throws IOException, XMLStreamException {

        InputStream stream = xlsxResource.get(name);
        XMLSheetReader reader = new XMLSheetReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        reader.setSharedStrings(sharedStrings);

        return reader;
    }


    @Override
    public void close() {
        xlsxResource.close();
    }
}

package org.xlsx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.*;
import java.util.*;

public class XLSXReader implements Closeable {

    private static Log logger = LogFactory.getLog(XLSXReader.class);

    private XLSXResource xlsxResource;
    private List<String> sharedStrings;

    public XLSXReader(InputStream input) {
        xlsxResource = new StreamXLSXResource(input);
    }

    public XLSXReader(XLSXResource resource) {
        xlsxResource = resource;
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

        String name = "xl/worksheets/sheet" + (i + 1) + ".xml";

        if( !xlsxResource.has(name) ) {
            throw new FileNotFoundException(name);
        }

        XMLSheetReader reader = getSheetReader(name);
        reader.setSharedStrings(getSharedStrings());

        return reader;
    }

    public XMLSharedStringReader getSharedStringsReader() throws IOException, XMLStreamException {

        InputStream stream = xlsxResource.get("xl/sharedStrings.xml");
        XMLSharedStringReader reader = new XMLSharedStringReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));

        return reader;
    }

    private XMLSheetReader getSheetReader(String name) throws IOException, XMLStreamException {

        InputStream stream = xlsxResource.get(name);
        XMLSheetReader reader = new XMLSheetReader(XMLInputFactory.newInstance().createXMLStreamReader(stream));
        return reader;
    }

    public List<String> getSharedStrings() {
        if( sharedStrings == null ) {
            try {
                sharedStrings = getSharedStringsReader().fetchAll();
            } catch (XMLStreamException e) {
                logger.fatal(e,e);
                e.printStackTrace();
            } catch (IOException e) {
                logger.fatal(e,e);
            }
        }

        return sharedStrings;
    }

    public void setSharedStrings(List<String> sharedStrings) {
        this.sharedStrings = sharedStrings;
    }

    @Override
    public void close() throws IOException {
        xlsxResource.close();
    }
}

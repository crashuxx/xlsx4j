package org.xlsx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.xml.stream.XMLStreamException;
import java.util.Iterator;

public class XLSXSheetRowIterator implements Iterator<XLSXSheetRow> {

    private static Log logger = LogFactory.getLog(XLSXResource.class);

    XMLSheetReader reader;

    private XLSXSheetRow next;

    public XLSXSheetRowIterator(XMLSheetReader sheetReader) {
        reader = sheetReader;
    }

    @Override
    public boolean hasNext() {

        if( next == null ) {
            next = fetch();
        }

        return next != null;
    }

    @Override
    public XLSXSheetRow next() {

        XLSXSheetRow row = null;

        if( hasNext() ) {
            row = next;
            next = null;
        }

        return row;
    }

    private XLSXSheetRow fetch() {
        XLSXSheetRow row = null;
        try {
            row = reader.next();
        } catch (XMLStreamException e) {
            logger.fatal(e.getMessage(), e);
        }
        return row;
    }

    @Override
    public void remove() {  }
}

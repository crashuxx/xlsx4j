package org.xlsx;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.*;

public class XMLSheetReader implements Iterable<XLSXSheetRow> {

    private XMLStreamReader inputStream;
    private XLSXRowIterator rowsIterator = null;
    private List<String> sharedStrings;

    private Stack<XMLTag> stack = new Stack<XMLTag>();

    public XMLSheetReader(XMLStreamReader input) {
        inputStream = input;
        sharedStrings = new ArrayList<String>();
    }

    public void setSharedStrings(List<String> collection) {
        sharedStrings = collection;
    }

    private boolean inSheetData = false;

    /**
     *
     * fixme
     *
     * @throws XMLStreamException
     */
    private void moveToSheetData() throws XMLStreamException {

        while (inputStream.hasNext()) {

            int event = inputStream.next();

            if (event == XMLStreamConstants.START_ELEMENT) {

                stack.push(new XMLTag(inputStream.getLocalName()));
                if ("sheetData".equals(inputStream.getName())) {
                    break;
                }
            }

            if (event == XMLStreamConstants.END_ELEMENT) {
                stack.pop();
            }
        }
    }

    public XLSXSheetRow next() throws XMLStreamException {

        if (inSheetData) {
            moveToSheetData();
            inSheetData = true;
        }
        String tagContent = null;
        XLSXSheetRow sheetRow = new XLSXSheetRow();

        int parsedRows = 0;

        while (inputStream.hasNext() && parsedRows < 1) {

            int event = inputStream.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    stack.push(new XMLTag(inputStream.getLocalName()));
                    if (stack.peek().equals("c")) {
                        stack.peek().getProperties().setProperty("t", inputStream.getAttributeValue(null, "t"));
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:

                    XMLTag tag = stack.pop();

                    if (tag.equals("v")) {
                        String text = tagContent;

                        if (stack.peek().getProperties().getProperty("t").equals("s")) {
                            text = sharedStrings.get(new Integer(tagContent));
                        }
                        sheetRow.add(text);
                    }

                    if (tag.equals("row")) {
                        parsedRows++;
                    }

                    tagContent = null;

                    break;

                case XMLStreamConstants.CHARACTERS:
                    tagContent = inputStream.getText().trim();
                    break;
            }
        }

        return parsedRows > 0 ? sheetRow : null;
    }

    @Override
    public XLSXRowIterator iterator() {
        if( rowsIterator == null ) {
            rowsIterator = new XLSXRowIterator(this);
        }

        return rowsIterator;
    }
}

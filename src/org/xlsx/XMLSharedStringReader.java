package org.xlsx;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.*;

public class XMLSharedStringReader {

    XMLStreamReader inputStream;

    public XMLSharedStringReader(XMLStreamReader input) {
        inputStream = input;
    }

    public String next() throws XMLStreamException {

        StringBuilder tagContent = new StringBuilder();

        int si = 0;
        boolean inT = false;

        while (inputStream.hasNext() && si >= 0) {

            int event = inputStream.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("si".equals(inputStream.getLocalName())) {
                        si++;
                    }

                    if ("t".equals(inputStream.getLocalName()) && si > 0) {
                        inT = true;
                    }
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    inT = false;
                    if ("si".equals(inputStream.getLocalName())) {
                        si -= (si > 1) ? 1 : 2;
                    }
                    break;

                case XMLStreamConstants.CHARACTERS:
                    if (inT) {
                        tagContent.append(inputStream.getText().trim());
                    }
                    break;
            }
        }

        return si < 0 ? tagContent.toString() : null;
    }

    public List<String> fetchAll() throws XMLStreamException {

        List<String> sharedStrings = new ArrayList<String>();

        String entry;
        while ((entry = next()) != null) {
            sharedStrings.add(entry);
        }

        return sharedStrings;
    }
}

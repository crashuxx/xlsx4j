package org.xlsx;

import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class XMLRelationReader {

    private XMLStreamReader inputStream;

    public XMLRelationReader(XMLStreamReader input) {
        inputStream = input;
    }

    public Properties next() throws XMLStreamException {

        while( inputStream.hasNext() ) {

            int event = inputStream.next();

            if( event == XMLStreamConstants.START_ELEMENT ) {

                if( "Relationship".equals(inputStream.getLocalName()) ) {

                    Properties properties = new Properties();

                    for(int i = 0; i < inputStream.getAttributeCount(); i++ ) {
                        String name = inputStream.getAttributeLocalName(i).toLowerCase();
                        String value = inputStream.getAttributeValue(i);
                        properties.setProperty(name,value);
                    }
                    return properties;
                }
            }
        }

        return null;
    }

    public Map<String,String> all() throws XMLStreamException {

        Map<String,String> relationship = new HashMap<String, String>();

        Properties properties;
        while ((properties = next()) != null) {
            relationship.put(properties.getProperty("id"), properties.getProperty("target"));
        }

        return relationship;
    }
}

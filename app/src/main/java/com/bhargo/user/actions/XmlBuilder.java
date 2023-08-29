package com.bhargo.user.actions;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class XmlBuilder {
    public static String convertHashMapToXML_(HashMap<String, Object> hashMap) throws XMLStreamException {
        StringWriter writer = new StringWriter();
        XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
        XMLStreamWriter xmlStreamWriter = xmlOutputFactory.createXMLStreamWriter(writer);

        // Start the XML document
        xmlStreamWriter.writeStartDocument();
        xmlStreamWriter.writeStartElement("root"); // You can replace "root" with any desired root element name.

        // Iterate through the HashMap and write key-value pairs as XML elements or attributes
        for (Map.Entry<String, Object> entry : hashMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof HashMap) {
                // If the value is a nested HashMap, call the helper method to convert it to XML
                writeNestedHashMap(xmlStreamWriter, key, (HashMap<String, Object>) value);
            } else if (value instanceof String) {
                // If the value is a String, write it as an XML element
                xmlStreamWriter.writeStartElement(key);
                xmlStreamWriter.writeCharacters((String) value);
                xmlStreamWriter.writeEndElement();
            } else if (value instanceof HashMapEntryWithAttributes) {
                // If the value is a special HashMapEntryWithAttributes object, treat it as an element with attributes
                HashMapEntryWithAttributes entryWithAttributes = (HashMapEntryWithAttributes) value;
                xmlStreamWriter.writeStartElement(key);
                for (Map.Entry<String, String> attrEntry : entryWithAttributes.getAttributes().entrySet()) {
                    String attrKey = attrEntry.getKey();
                    String attrValue = attrEntry.getValue();
                    xmlStreamWriter.writeAttribute(attrKey, attrValue);
                }
                xmlStreamWriter.writeCharacters(entryWithAttributes.getValue());
                xmlStreamWriter.writeEndElement();
            }
        }

        // End the XML document
        xmlStreamWriter.writeEndElement();
        xmlStreamWriter.writeEndDocument();

        xmlStreamWriter.flush();
        xmlStreamWriter.close();

        return writer.toString();
    }

    // Helper method to handle nested HashMaps
    private static void writeNestedHashMap(XMLStreamWriter xmlStreamWriter, String key, HashMap<String, Object> nestedHashMap)
            throws XMLStreamException {
        xmlStreamWriter.writeStartElement(key);
        for (Map.Entry<String, Object> entry : nestedHashMap.entrySet()) {
            String nestedKey = entry.getKey();
            Object nestedValue = entry.getValue();

            if (nestedValue instanceof HashMap) {
                // If the nested value is a HashMap, call the helper method recursively
                writeNestedHashMap(xmlStreamWriter, nestedKey, (HashMap<String, Object>) nestedValue);
            } else {
                // Otherwise, write the value as an XML element
                xmlStreamWriter.writeStartElement(nestedKey);
                xmlStreamWriter.writeCharacters(nestedValue.toString());
                xmlStreamWriter.writeEndElement();
            }
        }
        xmlStreamWriter.writeEndElement();
    }

    public static String generateXmlSchema(String xmlData) throws TransformerException, ParserConfigurationException, IOException, SAXException {
        DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        // Parse the XML data
        Document document = documentBuilder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlData)));


        StringBuilder xsdSchema = new StringBuilder();
        xsdSchema.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        xsdSchema.append("<xs:schema xmlns:xs=\"http://www.w3.org/2001/XMLSchema\">\n");

        Element rootElement = document.getDocumentElement();
        xsdSchema.append(generateElementSchema(rootElement));

        xsdSchema.append("</xs:schema>");
        return xsdSchema.toString();
    }

    private static String generateElementSchema(Element element) {
        StringBuilder elementSchema = new StringBuilder();
        elementSchema.append("  <xs:element name=\"").append(element.getNodeName()).append("\"");

        // Add attributes
        if (element.hasAttributes()) {
            // Iterate through attributes and include them in the schema
            // elementSchema.append(" attribute=\"value\"");
        }

        elementSchema.append(">\n");

        // Analyze child elements
        NodeList childNodes = element.getChildNodes();
        for (int i = 0; i < childNodes.getLength(); i++) {
            if (childNodes.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
                Element childElement = (Element) childNodes.item(i);
                elementSchema.append(generateElementSchema(childElement));
            }
        }

        elementSchema.append("  </xs:element>\n");
        return elementSchema.toString();
    }

    public static String buildXmlFromHashMap(HashMap<String, Object> dataHashMap, String rootElementName) {
        StringBuilder xmlBuilder = new StringBuilder();

        xmlBuilder.append("<").append(rootElementName).append(">");
        generateXmlFromMap(dataHashMap, xmlBuilder);
        xmlBuilder.append("</").append(rootElementName).append(">");

        return xmlBuilder.toString();
    }

    private static void generateXmlFromMap(Map<String, Object> dataMap, StringBuilder xmlBuilder) {
        for (Map.Entry<String, Object> entry : dataMap.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (value instanceof Map) {
                xmlBuilder.append("<").append(key).append(">");
                generateXmlFromMap((Map<String, Object>) value, xmlBuilder);
                xmlBuilder.append("</").append(key).append(">");
            } else if (value instanceof List) {
                xmlBuilder.append("<").append(key).append(">");
                for (Object listItem : (List<?>) value) {
                    if (listItem instanceof Map) {
                        xmlBuilder.append("<").append(key).append(">");
                        generateXmlFromMap((Map<String, Object>) listItem, xmlBuilder);
                        xmlBuilder.append("</").append(key).append(">");
                    } else {
                        xmlBuilder.append("<").append(key).append(">");
                        xmlBuilder.append(escapeXmlCharacters(listItem.toString()));
                        xmlBuilder.append("</").append(key).append(">");
                    }
                }
                xmlBuilder.append("</").append(key).append(">");
            } else {
                xmlBuilder.append("<").append(key).append(">");
                xmlBuilder.append(escapeXmlCharacters(value.toString()));
                xmlBuilder.append("</").append(key).append(">");
            }
        }
    }

    private static String escapeXmlCharacters(String value) {
        return value.replaceAll("&", "&amp;")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("\"", "&quot;")
                .replaceAll("'", "&apos;");
    }

    public static String convertXmlToXmlSchema(String xmlData, String rootElementName) throws ParserConfigurationException, TransformerException, IOException, SAXException {
        // Element rootElement = document.getDocumentElement();
        //            System.out.println("Root element: " + rootElement.getNodeName());
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();

        // Parse the XML data
        Document document = documentBuilder.parse(new org.xml.sax.InputSource(new java.io.StringReader(xmlData)));

        // Create a new XML document for the XML Schema
        Document schemaDocument = documentBuilder.newDocument();

        // Create the root element for the XML Schema
        Element rootElement = schemaDocument.createElement("xs:schema");
        rootElement.setAttribute("xmlns:xs", "http://www.w3.org/2001/XMLSchema");
        schemaDocument.appendChild(rootElement);

        // Create the complex type for the root element
        Element complexType = schemaDocument.createElement("xs:complexType");
        Element sequence = schemaDocument.createElement("xs:sequence");
        complexType.appendChild(sequence);
        rootElement.appendChild(complexType);

        // Process the root element
        Element rootElementXml = document.getDocumentElement();
        processElement(schemaDocument, sequence, rootElementXml);

        // Serialize the XML Schema document to a string
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        StringWriter writer = new StringWriter();
        transformer.transform(new DOMSource(schemaDocument), new StreamResult(writer));

        return writer.toString();
    }

    private static void processElement(Document schemaDocument, Element parentElement, Element xmlElement) {
        String elementName = xmlElement.getTagName();

        // Create an element in the XML Schema for the current element
        Element xsElement = schemaDocument.createElement("xs:element");
        xsElement.setAttribute("name", elementName);

        NamedNodeMap attributes = xmlElement.getAttributes();
        int numAttributes = attributes.getLength();
        if (numAttributes > 0) {
            // Create a complex type with attributes if the element has attributes
            Element complexType = schemaDocument.createElement("xs:complexType");
            Element attributeGroup = schemaDocument.createElement("xs:attributeGroup");

            for (int i = 0; i < numAttributes; i++) {
                Node attribute = attributes.item(i);
                String attributeName = attribute.getNodeName();

                Element xsAttribute = schemaDocument.createElement("xs:attribute");
                xsAttribute.setAttribute("name", attributeName);
                xsAttribute.setAttribute("type", "xs:string");
                attributeGroup.appendChild(xsAttribute);
            }

            complexType.appendChild(attributeGroup);
            xsElement.appendChild(complexType);
        } else {
            // Set the type as a simple type if the element has no attributes
            xsElement.setAttribute("type", "xs:string");
        }

        // Recursively process the child elements
        NodeList childNodes = xmlElement.getChildNodes();
        int numChildNodes = childNodes.getLength();
        for (int i = 0; i < numChildNodes; i++) {
            Node childNode = childNodes.item(i);
            if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                processElement(schemaDocument, xsElement, (Element) childNode);
            }
        }

        // Add the element to the parent in the XML Schema
        parentElement.appendChild(xsElement);
    }

    // Helper method to handle HashMapEntryWithAttributes objects
    private static class HashMapEntryWithAttributes {
        private final String value;
        private final HashMap<String, String> attributes;

        public HashMapEntryWithAttributes(String value, HashMap<String, String> attributes) {
            this.value = value;
            this.attributes = attributes;
        }

        public String getValue() {
            return value;
        }

        public HashMap<String, String> getAttributes() {
            return attributes;
        }
    }
}

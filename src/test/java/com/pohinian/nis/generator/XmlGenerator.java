package com.pohinian.nis.generator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * The type Xml generator.
 */
public class XmlGenerator {

    private Document doc;
    private Transformer transformer = null;


    /**
     * Instantiates a new Xml generator.
     */
    public XmlGenerator() {

        final DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant  
        docFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_SCHEMA, ""); // compliant  
        DocumentBuilder docBuilder;
        try {
            docBuilder = docFactory.newDocumentBuilder();
            doc = docBuilder.newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, ""); // Compliant  
        transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, ""); // Compliant
        try {
            transformer = transformerFactory.newTransformer();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        }

        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

    }

    /**
     * Set property omit.
     *
     * @param type the type
     */
    public void setPropertyOmit(String type) {
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, type);
    }

    /**
     * Set property indent.
     *
     * @param type the type
     */
    public void setPropertyIndent(String type) {
        transformer.setOutputProperty(OutputKeys.INDENT, type);
    }

    /**
     * Set property preview.
     */
    public void setPropertyPreview() {
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
    }

    /**
     * Add root element.
     *
     * @param elementName the element name
     */
    public void addRootElement(String elementName) {
        Element rootElement = doc.createElement(elementName);
        doc.appendChild(rootElement);
    }

    /**
     * Add element boolean.
     *
     * @param parentElementName the parent element name
     * @param index             the index
     * @param elementName       the element name
     * @param elementValue      the element value
     * @return the boolean
     */
    public boolean addElement(String parentElementName, int index, String elementName, String elementValue) {

        NodeList nodeList = doc.getElementsByTagName(parentElementName);
        if (nodeList == null || nodeList.getLength() == 0) return false;

        Element element = doc.createElement(elementName);
        element.appendChild(doc.createTextNode(elementValue));
        nodeList.item(index).appendChild(element);


        return true;

    }

    /**
     * Add element cdata section boolean.
     *
     * @param parentElementName the parent element name
     * @param index             the index
     * @param elementName       the element name
     * @param elementValue      the element value
     * @return the boolean
     */
    public boolean addElementCDATASection(String parentElementName, int index, String elementName, String elementValue) {

        NodeList nodeList = doc.getElementsByTagName(parentElementName);
        if (nodeList == null || nodeList.getLength() == 0) return false;

        Element element = doc.createElement(elementName);
        element.appendChild(doc.createCDATASection(elementValue));
        nodeList.item(index).appendChild(element);


        return true;

    }

    /**
     * Add element cdata section boolean.
     *
     * @param parentElementName the parent element name
     * @param elementName       the element name
     * @param elementValue      the element value
     * @return the boolean
     */
    public boolean addElementCDATASection(String parentElementName, String elementName, String elementValue) {
        NodeList nodeList = doc.getElementsByTagName(parentElementName);
        if (nodeList == null || nodeList.getLength() == 0) return false;
        return addElementCDATASection(parentElementName, nodeList.getLength() - 1, elementName, elementValue);
    }

    /**
     * Add element boolean.
     *
     * @param parentElementName the parent element name
     * @param elementName       the element name
     * @param elementValue      the element value
     * @return the boolean
     */
    public boolean addElement(String parentElementName, String elementName, String elementValue) {
        NodeList nodeList = doc.getElementsByTagName(parentElementName);
        if (nodeList == null || nodeList.getLength() == 0) return false;
        return addElement(parentElementName, nodeList.getLength() - 1, elementName, elementValue);
    }

    /**
     * Add element due cdata section boolean.
     *
     * @param parentElementName the parent element name
     * @param index             the index
     * @param elementName       the element name
     * @param elementValue      the element value
     * @return the boolean
     */
    public boolean addElementDueCDATASection(String parentElementName, int index, String elementName, String elementValue) {

        NodeList nodeList = doc.getElementsByTagName(parentElementName);
        if (nodeList == null || nodeList.getLength() == 0) return false;


        Element element = doc.createElement(elementName);
        element.appendChild(doc.createCDATASection(elementValue));

        for (int i = 0; i < nodeList.getLength(); i++) {
            nodeList.item(i).appendChild(element);
        }

        return true;

    }

    /**
     * Add attribute boolean.
     *
     * @param elementName    the element name
     * @param index          the index
     * @param attributeValue the attribute value
     * @return the boolean
     */
    public boolean addAttribute(String elementName, int index, String attributeValue) {

        NodeList nodeList = doc.getElementsByTagName(elementName);
        if (nodeList == null) return false;

        Element element = (Element) nodeList.item(index);
        element.appendChild(doc.createTextNode(attributeValue));

        return true;
    }

    /**
     * Add attribute cdata section boolean.
     *
     * @param elementName    the element name
     * @param index          the index
     * @param attributeValue the attribute value
     * @return the boolean
     */
    public boolean addAttributeCDATASection(String elementName, int index, String attributeValue) {

        NodeList nodeList = doc.getElementsByTagName(elementName);
        if (nodeList == null) return false;

        Element element = (Element) nodeList.item(index);
        element.appendChild(doc.createCDATASection(attributeValue));

        return true;
    }

    /**
     * Set value boolean.
     *
     * @param elementName    the element name
     * @param index          the index
     * @param attributeName  the attribute name
     * @param attributeValue the attribute value
     * @return the boolean
     */
    public boolean setValue(String elementName, int index, String attributeName, String attributeValue) {

        NodeList nodeList = doc.getElementsByTagName(elementName);
        if (nodeList == null) return false;

        Element element = (Element) nodeList.item(index);
        element.setAttribute(attributeName, attributeValue);

        return true;
    }

    /**
     * Gets xml data.
     *
     * @return the xml data
     * @throws TransformerException the transformer exception
     */
    public String getXmlData() throws TransformerException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        StreamResult result = new StreamResult(out);

        DOMSource source = new DOMSource(doc);
        transformer.transform(source, result);

        return new String(out.toByteArray(), StandardCharsets.UTF_8);
    }


}

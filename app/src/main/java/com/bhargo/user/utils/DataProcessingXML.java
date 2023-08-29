package com.bhargo.user.utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

public class DataProcessingXML {

    public static JSONObject processData(String xmlData, List<String> inParam) {
        JSONObject jsonObjResponse = new JSONObject();

        try {

            xmlData = xmlData.replaceAll("[^\\x20-\\x7e]", "");

            if (isXMLValid(xmlData)) {

                //File inputFile = new File("input.txt");

                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder;

                dBuilder = dbFactory.newDocumentBuilder();

                Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
                doc.getDocumentElement().normalize();

                XPath xPath = XPathFactory.newInstance().newXPath();

                for (String inputParam : inParam) {

                    JSONArray jsonReturnAry = new JSONArray();

                    String jsonKey = inputParam.replace("/", "_");

                    String expression = inputParam;

                    NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

                    //  xPath.setNamespaceContext(new SoapNamespaceContext());

                    // Node resultNode = (Node) xPath.evaluate("//m:GetWhoISResult", message.getSOAPBody(), XPathConstants.NODE);


                    // System.out.println("\nNode Length :" + nodeList.getLength());

                    for (int i = 0; i < nodeList.getLength(); i++) {
                        Node nNode = nodeList.item(i);

                        // System.out.println("\nCurrent Elementttt :" + nNode.getNodeName());

                        if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                            Element eElement = (Element) nNode;

                            //System.out.println( nNode.getNodeName()+ ": " + getCharacterDataFromElement(eElement));

                            String value = getCharacterDataFromElement(eElement);

                            jsonReturnAry.put(value);

                        } else if (nNode.getNodeType() == Node.ATTRIBUTE_NODE) {

                            // System.out.println("Attribute-->"+nodeList.item(i).getNodeValue());

                            String value = nodeList.item(i).getNodeValue();

                            jsonReturnAry.put(value);
                        }
                    }

                    jsonObjResponse.put(jsonKey, jsonReturnAry);

                }


            } else {
                System.out.println("Invalid XML String...");
            }


        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XPathExpressionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        System.out.println("response-->" + jsonObjResponse);

        return jsonObjResponse;
    }

	public static JSONObject xmlToJson(String xmlData, List<String> inParam) {
		JSONObject jsonObjResponse = new JSONObject();
		try {
			xmlData = xmlData.replaceAll("[^\\x20-\\x7e]", "");
			if (isXMLValid(xmlData)) {
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder;

				dBuilder = dbFactory.newDocumentBuilder();

				Document doc = dBuilder.parse(new InputSource(new StringReader(xmlData)));
				doc.getDocumentElement().normalize();
                NodeList childNodeList=doc.getChildNodes();
                for (int i = 0; i <childNodeList.getLength() ; i++) {
                    Node nNode = childNodeList.item(i);
                    if(nNode.hasAttributes()){

                    }
                    if(nNode.hasChildNodes()){

                    }
                }

				XPath xPath = XPathFactory.newInstance().newXPath();

				for (String inputParam : inParam) {

					JSONArray jsonReturnAry = new JSONArray();

					String jsonKey = inputParam.replace("/", "_");

					String expression = inputParam;

					NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(doc, XPathConstants.NODESET);

					for (int i = 0; i < nodeList.getLength(); i++) {
						Node nNode = nodeList.item(i);

						// System.out.println("\nCurrent Elementttt :" + nNode.getNodeName());

						if (nNode.getNodeType() == Node.ELEMENT_NODE) {
							Element eElement = (Element) nNode;

							//System.out.println( nNode.getNodeName()+ ": " + getCharacterDataFromElement(eElement));

							String value = getCharacterDataFromElement(eElement);

							jsonReturnAry.put(value);

						} else if (nNode.getNodeType() == Node.ATTRIBUTE_NODE) {

							// System.out.println("Attribute-->"+nodeList.item(i).getNodeValue());

							String value = nodeList.item(i).getNodeValue();

							jsonReturnAry.put(value);
						}
					}

					jsonObjResponse.put(jsonKey, jsonReturnAry);

				}


			} else {
				System.out.println("Invalid XML String...");
			}


		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}


		System.out.println("response-->" + jsonObjResponse);

		return jsonObjResponse;
	}

    public static String getCharacterDataFromElement(Element e) {
        Node child = e.getFirstChild();

        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }

    public static boolean isXMLValid(String xmlString) {

        try {
            DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
            // System.out.println("Message is valid XML.");
            return true;
        } catch (Exception e) {
            // System.out.println("Message is not valid XML.");
            return false;
        }

    }


    public static String nodeToString(Node node) throws Exception {

        StringWriter buf = new StringWriter();

        Transformer xform = TransformerFactory.newInstance().newTransformer();

        xform.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

        xform.transform(new DOMSource(node), new StreamResult(buf));

        return (buf.toString());
    }

    public static String getXmlOrJsonFromSoap(String xmlData) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document;
        Node result = null;
        String resultStr = "";

        try {

            document = dbf.newDocumentBuilder().parse(new InputSource(new StringReader(xmlData)));
            XPath xPath = XPathFactory.newInstance().newXPath();
            String xpathStr = "//Envelope//Body/*/*";
            result = (Node) xPath.evaluate(xpathStr, document, XPathConstants.NODE);

            resultStr = nodeToString(result);

            System.out.println(resultStr);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultStr;

    }

    public static void main(String[] args) {

        List<String> inParam = new ArrayList<String>();

        inParam.add("result/records/item/state");

        inParam.add("result/records/item/district");

        inParam.add("result/field/item/type");

        inParam.add("result/index_name");

        String xmlData = "<result><index_name>9ef84268-d588-465a-a308-a864a43d0070</index_name><title>Current Daily Price of Various Commodities from Various Markets (Mandi)</title><desc>Current Daily Price of Various Commodities from Various Markets (Mandi)</desc><org_type>Central</org_type><org><item>Ministry of Agriculture and Farmers Welfare</item><item>Department of Agriculture, Cooperation and Farmers Welfare</item><item>Directorate of Marketing and Inspection (DMI)</item></org><sector><item>Agriculture</item><item>Agricultural Marketing</item></sector><source>data.gov.in</source><catalog_uuid>6141ea17-a69d-4713-b600-0a43c8fd9a6c</catalog_uuid><visualizable>1</visualizable><active>1</active><created>1543321994</created><updated>1583170522</updated><created_date>2018-11-27T18:03:14Z</created_date><updated_date>2020-03-02T23:05:22Z</updated_date><target_bucket><index>daily_mandi</index><type>6141ea17-a69d-4713-b600-0a43c8fd9a6c</type><field>9ef84268-d588-465a-a308-a864a43d0070</field></target_bucket><field><item><id>timestamp</id><name>timestamp</name><type>double</type></item><item><id>state</id><name>state</name><type>keyword</type></item><item><id>district</id><name>district</name><type>keyword</type></item><item><id>market</id><name>market</name><type>keyword</type></item><item><id>commodity</id><name>commodity</name><type>keyword</type></item><item><id>variety</id><name>variety</name><type>keyword</type></item><item><id>arrival_date</id><name>arrival_date</name><type>date</type></item><item><id>min_price</id><name>min_price</name><type>double</type></item><item><id>max_price</id><name>max_price</name><type>double</type></item><item><id>modal_price</id><name>modal_price</name><type>double</type></item></field><external_ws_url></external_ws_url><external_ws>0</external_ws><message>Resource lists</message><version>2.2.0</version><status>ok</status><total>4672</total><count>10</count><limit>10</limit><offset>0</offset><records><item><timestamp>1583170507</timestamp><state>Andhra Pradesh</state><district>Chittor</district><market>Mulakalacheruvu</market><commodity>Tomato</commodity><variety>Local</variety><arrival_date>02/03/2020</arrival_date><min_price>450</min_price><max_price>650</max_price><modal_price>550</modal_price></item><item><timestamp>1583170507</timestamp><state>Andhra Pradesh</state><district>Kurnool</district><market>Pattikonda</market><commodity>Tomato</commodity><variety>Local</variety><arrival_date>02/03/2020</arrival_date><min_price>700</min_price><max_price>1000</max_price><modal_price>800</modal_price></item><item><timestamp>1583170507</timestamp><state>Chattisgarh</state><district>Kanker</district><market>Charama</market><commodity>Paddy(Dhan)(Common)</commodity><variety>Other</variety><arrival_date>02/03/2020</arrival_date><min_price>1450</min_price><max_price>1450</max_price><modal_price>1450</modal_price></item><item><timestamp>1583170507</timestamp><state>Chattisgarh</state><district>Kanker</district><market>Lakhanpuri</market><commodity>Paddy(Dhan)(Common)</commodity><variety>Other</variety><arrival_date>02/03/2020</arrival_date><min_price>1450</min_price><max_price>1450</max_price><modal_price>1450</modal_price></item><item><timestamp>1583170507</timestamp><state>Chattisgarh</state><district>Kanker</district><market>Narharpur</market><commodity>Paddy(Dhan)(Common)</commodity><variety>Other</variety><arrival_date>02/03/2020</arrival_date><min_price>1450</min_price><max_price>1450</max_price><modal_price>1450</modal_price></item><item><timestamp>1583170507</timestamp><state>Gujarat</state><district>Amreli</district><market>Bagasara</market><commodity>Wheat</commodity><variety>Lokwan Gujrat</variety><arrival_date>02/03/2020</arrival_date><min_price>1555</min_price><max_price>1730</max_price><modal_price>1642</modal_price></item><item><timestamp>1583170507</timestamp><state>Gujarat</state><district>Amreli</district><market>Damnagar</market><commodity>Bhindi(Ladies Finger)</commodity><variety>Bhindi</variety><arrival_date>02/03/2020</arrival_date><min_price>1050</min_price><max_price>1250</max_price><modal_price>1150</modal_price></item><item><timestamp>1583170507</timestamp><state>Gujarat</state><district>Amreli</district><market>Damnagar</market><commodity>Cabbage</commodity><variety>Cabbage</variety><arrival_date>02/03/2020</arrival_date><min_price>1200</min_price><max_price>1400</max_price><modal_price>1300</modal_price></item><item><timestamp>1583170507</timestamp><state>Gujarat</state><district>Amreli</district><market>Damnagar</market><commodity>Cauliflower</commodity><variety>Cauliflower</variety><arrival_date>02/03/2020</arrival_date><min_price>1300</min_price><max_price>1500</max_price><modal_price>1400</modal_price></item><item><timestamp>1583170507</timestamp><state>Gujarat</state><district>Amreli</district><market>Damnagar</market><commodity>Coriander(Leaves)</commodity><variety>Coriander</variety><arrival_date>02/03/2020</arrival_date><min_price>1150</min_price><max_price>1350</max_price><modal_price>1250</modal_price></item></records></result>";

        processData(xmlData, inParam);
    }


}

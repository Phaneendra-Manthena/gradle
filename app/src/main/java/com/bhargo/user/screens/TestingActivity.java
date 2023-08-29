package com.bhargo.user.screens;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bhargo.user.R;
import com.bhargo.user.actions.JsonBuilder;
import com.bhargo.user.actions.XmlBuilder;
import com.bhargo.user.utils.BaseActivity;
import com.fasterxml.jackson.databind.ObjectMapper;



import org.json.JSONException;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

public class TestingActivity extends BaseActivity {

    Button bt_buildjson,bt_buildxml;
    TextView tv_result,tv_schema,tv_xmltojson;
    String msg = "";

    private static HashMap<String, Object> createSchemaHashMap() {
        HashMap<String, Object> schemaMap = new HashMap<>();
        // Add properties for dealer
        schemaMap.put("dealerId", "ABC123");
        schemaMap.put("dealerName", "John’s AgriMart");
        schemaMap.put("address", "123 Main Street");
        schemaMap.put("GSTNo", "GST123");
        schemaMap.put("ratings", 4.5);
        schemaMap.put("registrationDate", "2023-07-20");
        schemaMap.put("image", "dealer_image.jpg");
        schemaMap.put("website", "https://www.johnsagrimart.com");
        schemaMap.put("phone", "+1-123-456-7890");
        // Add location object
        Location location = new Location();
        location.setLatitude(37.7749);
        location.setLongitude(-122.4194);
        schemaMap.put("location", location);
        // Add catalog (list of products)
        List<Product> catalog = new ArrayList<>();
        Product product1 = new Product();
        product1.setCropId("CROP001");
        product1.setCropType("Vegetable");
        product1.setSku("SKU123");
        product1.setDescription("Fresh Tomatoes");
        product1.setUnits("kg");
        product1.setQuatity("500");
        product1.setVariety("Cherry");
        product1.setPrice(2.99);
        product1.setStock(100);
        product1.setImages(Arrays.asList("tomato_img1.jpg", "tomato_img2.jpg"));
        product1.setBestSeller(true);
        product1.setProductRatings(4.8);
        catalog.add(product1);
        Product product2 = new Product();
        product2.setCropId("CROP002");
        product2.setCropType("Fruit");
        product2.setSku("SKU456");
        product2.setDescription("Fresh Apples");
        product2.setUnits("kg");
        product2.setQuatity("300");
        product2.setVariety("Fuji");
        product2.setPrice(3.49);
        product2.setStock(50);
        product2.setImages(Arrays.asList("apple_img1.jpg", "apple_img2.jpg"));
        product2.setBestSeller(false);
        product2.setProductRatings(4.2);
        catalog.add(product2);
        schemaMap.put("catalog", catalog);
        return schemaMap;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        findViews();
    }

    private void findViews() {
        bt_buildjson = findViewById(R.id.bt_buildjson);
        bt_buildxml = findViewById(R.id.bt_buildxml);
        tv_schema= findViewById(R.id.tv_schema);
        tv_result = findViewById(R.id.tv_result);
        tv_xmltojson = findViewById(R.id.tv_xmltojson);
        bt_buildjson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //execute();
                //execute1();
                convertJSONToJSONschemaAndHashMapToJSON();
            }
        });

        bt_buildxml.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                convertXmlToXmlschemaAndHashMapToXml();
            }
        });
    }

    public void convertXmlToXmlschemaAndHashMapToXml() {
        String xmlData = "<root><name>John</name><age>30</age><isEmployed>true</isEmployed></root>";
        xmlData="<?xml version=\"1.0\"?><result><index_name>3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69</index_name><title>Real time Air Quality Index from various locations</title><desc>Real time Air Quality Index from various locations</desc><org_type>Central</org_type><org><item>Ministry of Environment, Forest and Climate Change</item><item>Central Pollution Control Board</item></org><sector><item>Environment and Forest</item><item>Vehicular Air Pollution</item><item>Residential Air Pollution</item><item>Industrial Air Pollution</item></sector><source>data.gov.in</source><catalog_uuid>a3e7afc6-b799-4ede-b143-8e074b27e062</catalog_uuid><visualizable>1</visualizable><active>1</active><created>1543320551</created><updated>1686895242</updated><created_date>2018-11-27T17:39:11Z</created_date><updated_date>2023-06-16T11:30:42Z</updated_date><external_ws>0</external_ws><external_ws_url/><target_bucket><index>aqi</index><type>a3e7afc6-b799-4ede-b143-8e074b27e062</type><field>3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69</field></target_bucket><field><item><id>id</id><name>id</name><type>double</type></item><item><id>country</id><name>country</name><type>keyword</type></item><item><id>state</id><name>state</name><type>keyword</type></item><item><id>city</id><name>city</name><type>keyword</type></item><item><id>station</id><name>station</name><type>keyword</type></item><item><id>last_update</id><name>last_update</name><type>date</type></item><item><id>pollutant_id</id><name>pollutant_id</name><type>keyword</type></item><item><id>pollutant_min</id><name>pollutant_min</name><type>double</type></item><item><id>pollutant_max</id><name>pollutant_max</name><type>double</type></item><item><id>pollutant_avg</id><name>pollutant_avg</name><type>double</type></item><item><id>pollutant_unit</id><name>pollutant_unit</name><type>keyword</type></item></field><field_exposed><item><name>country</name><id>country</id><type>keyword</type></item><item><name>state</name><id>state</id><type>keyword</type></item><item><name>city</name><id>city</id><type>keyword</type></item><item><name>station</name><id>station</id><type>keyword</type></item></field_exposed><message>Resource lists</message><version>2.2.0</version><status>ok</status><total>2930</total><count>10</count><limit>10</limit><offset>0</offset><records><item><id>1</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM2.5</pollutant_id><pollutant_min>12</pollutant_min><pollutant_max>92</pollutant_max><pollutant_avg>33</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>2</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM10</pollutant_id><pollutant_min>62</pollutant_min><pollutant_max>152</pollutant_max><pollutant_avg>87</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>3</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>NO2</pollutant_id><pollutant_min>7</pollutant_min><pollutant_max>26</pollutant_max><pollutant_avg>14</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>4</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>NH3</pollutant_id><pollutant_min>3</pollutant_min><pollutant_max>4</pollutant_max><pollutant_avg>4</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>5</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>SO2</pollutant_id><pollutant_min>17</pollutant_min><pollutant_max>26</pollutant_max><pollutant_avg>21</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>6</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>CO</pollutant_id><pollutant_min>8</pollutant_min><pollutant_max>102</pollutant_max><pollutant_avg>21</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>7</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>OZONE</pollutant_id><pollutant_min>4</pollutant_min><pollutant_max>62</pollutant_max><pollutant_avg>14</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>8</id><country>India</country><state>Andhra_Pradesh</state><city>Anantapur</city><station>Gulzarpet, Anantapur - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM2.5</pollutant_id><pollutant_min>27</pollutant_min><pollutant_max>39</pollutant_max><pollutant_avg>32</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>9</id><country>India</country><state>Andhra_Pradesh</state><city>Anantapur</city><station>Gulzarpet, Anantapur - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM10</pollutant_id><pollutant_min>36</pollutant_min><pollutant_max>111</pollutant_max><pollutant_avg>70</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>10</id><country>India</country><state>Andhra_Pradesh</state><city>Anantapur</city><station>Gulzarpet, Anantapur - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>NO2</pollutant_id><pollutant_min>14</pollutant_min><pollutant_max>54</pollutant_max><pollutant_avg>22</pollutant_avg><pollutant_unit>NA</pollutant_unit></item></records></result>";
        xmlData="<ArrayOfDBTFarmerRegistration xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns=\"http://dbtagriculture.bihar.gov.in/\"><DBTFarmerRegistration><Exist>1</Exist><Registration_ID>2371516487434</Registration_ID><FarmerName>RAJIV KUMAR </FarmerName><Father_Husband_Name>ONKAR PRASAD</Father_Husband_Name><CastCateogary>पिछड़ा वर्ग</CastCateogary><AadhaarNo>2097XXXX3979</AadhaarNo><DistrictCode>237</DistrictCode><DistrictName>NAWADA</DistrictName><BlockCode>1516</BlockCode><BlockName>KAWAKOLE</BlockName><PanchayatCode>98336</PanchayatCode><PanchayatName>NAWADIH</PanchayatName><VillageCode>257769</VillageCode><VillageName>Nawadih</VillageName><MobileNumber>6201338773</MobileNumber><DistrictCode_LG>210</DistrictCode_LG><BlockCode_LG>1918</BlockCode_LG><VillageCode_LG>257769</VillageCode_LG><DOB>10-Aug-1995</DOB><AccountNo>39012805674</AccountNo><IFSCCODE>SBIN0000141</IFSCCODE><BankName>SBI</BankName><Gender>पुरुष</Gender><Farmertype>लघु किसान</Farmertype><PanchayatCode_LG>98336</PanchayatCode_LG><FarmerCat>रैयत</FarmerCat></DBTFarmerRegistration></ArrayOfDBTFarmerRegistration>";
        xmlData="<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<breakfast_menu>\n" +
                "<food>\n" +
                "    <name>Belgian Waffles</name>\n" +
                "    <price>$5.95</price>\n" +
                "    <description>\n" +
                "   Two of our famous Belgian Waffles with plenty of real maple syrup\n" +
                "   </description>\n" +
                "    <calories>650</calories>\n" +
                "</food>\n" +
                "<food>\n" +
                "    <name>Strawberry Belgian Waffles</name>\n" +
                "    <price>$7.95</price>\n" +
                "    <description>\n" +
                "    Light Belgian waffles covered with strawberries and whipped cream\n" +
                "    </description>\n" +
                "    <calories>900</calories>\n" +
                "</food>\n" +
                "<food>\n" +
                "    <name>Berry-Berry Belgian Waffles</name>\n" +
                "    <price>$8.95</price>\n" +
                "    <description>\n" +
                "    Belgian waffles covered with assorted fresh berries and whipped cream\n" +
                "    </description>\n" +
                "    <calories>900</calories>\n" +
                "</food>\n" +
                "<food>\n" +
                "    <name>French Toast</name>\n" +
                "    <price>$4.50</price>\n" +
                "    <description>\n" +
                "    Thick slices made from our homemade sourdough bread\n" +
                "    </description>\n" +
                "    <calories>600</calories>\n" +
                "</food>\n" +
                "<food>\n" +
                "    <name>Homestyle Breakfast</name>\n" +
                "    <price>$6.95</price>\n" +
                "    <description>\n" +
                "    Two eggs, bacon or sausage, toast, and our ever-popular hash browns\n" +
                "    </description>\n" +
                "    <calories>950</calories>\n" +
                "</food>\n" +
                "</breakfast_menu>";
        try {

            // Convert XML to XML Schema
            //String xmlSchema = XmlBuilder.convertXmlToXmlSchema(xmlData,"result");
            //String xmlSchema2 = XmlBuilder.generateXmlSchema(xmlData);
            //tv_xmltojson.setText(xmlSchema2);
            //tv_schema.setText(xmlSchema);
            //Convert XML Json Obj to HashMap
            JSONObject xmlJsonObj = JsonBuilder.convertXmlToJsonObj(xmlData);
            String xmlJsonObjString = xmlJsonObj.toString(4);
            HashMap<String, Object> xmlJsonHashMapObj = JsonBuilder.ConvertJSONToHashMap(xmlJsonObjString);
            // Build XML data from the HashMap and XML schema
            String xmlObj=XmlBuilder.buildXmlFromHashMap(xmlJsonHashMapObj,"breakfast_menu");
            //String xmlObj2=XmlBuilder.convertHashMapToXML(xmlJsonHashMapObj);
            //tv_schema.setText(xmlObj2);
            tv_result.setText(xmlObj);
        } catch (Exception e) {//22vvS0371 43
            e.printStackTrace();
        }
    }

    public void convertXmlToJsonschemaAndHashMapToJson() {
        String xmlData = "<root><name>John</name><age>30</age><isEmployed>true</isEmployed></root>";
       xmlData="<?xml version=\"1.0\"?>\n" +
               "<result><index_name>3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69</index_name><title>Real time Air Quality Index from various locations</title><desc>Real time Air Quality Index from various locations</desc><org_type>Central</org_type><org><item>Ministry of Environment, Forest and Climate Change</item><item>Central Pollution Control Board</item></org><sector><item>Environment and Forest</item><item>Vehicular Air Pollution</item><item>Residential Air Pollution</item><item>Industrial Air Pollution</item></sector><source>data.gov.in</source><catalog_uuid>a3e7afc6-b799-4ede-b143-8e074b27e062</catalog_uuid><visualizable>1</visualizable><active>1</active><created>1543320551</created><updated>1686895242</updated><created_date>2018-11-27T17:39:11Z</created_date><updated_date>2023-06-16T11:30:42Z</updated_date><external_ws>0</external_ws><external_ws_url/><target_bucket><index>aqi</index><type>a3e7afc6-b799-4ede-b143-8e074b27e062</type><field>3b01bcb8-0b14-4abf-b6f2-c1bfd384ba69</field></target_bucket><field><item><id>id</id><name>id</name><type>double</type></item><item><id>country</id><name>country</name><type>keyword</type></item><item><id>state</id><name>state</name><type>keyword</type></item><item><id>city</id><name>city</name><type>keyword</type></item><item><id>station</id><name>station</name><type>keyword</type></item><item><id>last_update</id><name>last_update</name><type>date</type></item><item><id>pollutant_id</id><name>pollutant_id</name><type>keyword</type></item><item><id>pollutant_min</id><name>pollutant_min</name><type>double</type></item><item><id>pollutant_max</id><name>pollutant_max</name><type>double</type></item><item><id>pollutant_avg</id><name>pollutant_avg</name><type>double</type></item><item><id>pollutant_unit</id><name>pollutant_unit</name><type>keyword</type></item></field><field_exposed><item><name>country</name><id>country</id><type>keyword</type></item><item><name>state</name><id>state</id><type>keyword</type></item><item><name>city</name><id>city</id><type>keyword</type></item><item><name>station</name><id>station</id><type>keyword</type></item></field_exposed><message>Resource lists</message><version>2.2.0</version><status>ok</status><total>2930</total><count>10</count><limit>10</limit><offset>0</offset><records><item><id>1</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM2.5</pollutant_id><pollutant_min>12</pollutant_min><pollutant_max>92</pollutant_max><pollutant_avg>33</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>2</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM10</pollutant_id><pollutant_min>62</pollutant_min><pollutant_max>152</pollutant_max><pollutant_avg>87</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>3</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>NO2</pollutant_id><pollutant_min>7</pollutant_min><pollutant_max>26</pollutant_max><pollutant_avg>14</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>4</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>NH3</pollutant_id><pollutant_min>3</pollutant_min><pollutant_max>4</pollutant_max><pollutant_avg>4</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>5</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>SO2</pollutant_id><pollutant_min>17</pollutant_min><pollutant_max>26</pollutant_max><pollutant_avg>21</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>6</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>CO</pollutant_id><pollutant_min>8</pollutant_min><pollutant_max>102</pollutant_max><pollutant_avg>21</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>7</id><country>India</country><state>Andhra_Pradesh</state><city>Amaravati</city><station>Secretariat, Amaravati - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>OZONE</pollutant_id><pollutant_min>4</pollutant_min><pollutant_max>62</pollutant_max><pollutant_avg>14</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>8</id><country>India</country><state>Andhra_Pradesh</state><city>Anantapur</city><station>Gulzarpet, Anantapur - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM2.5</pollutant_id><pollutant_min>27</pollutant_min><pollutant_max>39</pollutant_max><pollutant_avg>32</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>9</id><country>India</country><state>Andhra_Pradesh</state><city>Anantapur</city><station>Gulzarpet, Anantapur - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>PM10</pollutant_id><pollutant_min>36</pollutant_min><pollutant_max>111</pollutant_max><pollutant_avg>70</pollutant_avg><pollutant_unit>NA</pollutant_unit></item><item><id>10</id><country>India</country><state>Andhra_Pradesh</state><city>Anantapur</city><station>Gulzarpet, Anantapur - APPCB</station><last_update>16-06-2023 11:00:00</last_update><pollutant_id>NO2</pollutant_id><pollutant_min>14</pollutant_min><pollutant_max>54</pollutant_max><pollutant_avg>22</pollutant_avg><pollutant_unit>NA</pollutant_unit></item></records></result>";
        try {
            // Convert XML to JSON Obj
            JSONObject xmlJsonObj = JsonBuilder.convertXmlToJsonObj(xmlData);
            String xmlJsonObjString = xmlJsonObj.toString(4);
            tv_xmltojson.setText(xmlJsonObjString);
            // Convert XML JSON object to JSON schema
            JSONObject xmlJsonSchema = JsonBuilder.generateJsonSchema(xmlJsonObj);
            String xmlJsonSchemaString = xmlJsonSchema.toString(4);
            tv_schema.setText(xmlJsonSchemaString);
            //Convert XML Json Obj to HashMap
            HashMap<String, Object> xmlJsonHashMapObj = JsonBuilder.ConvertJSONToHashMap(xmlJsonObjString);
            // Build JSON data from the HashMap and JSON schema
            String jsonData = JsonBuilder.buildJsonFromHashMap(xmlJsonHashMapObj, xmlJsonSchemaString);
            tv_result.setText(jsonData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void convertJSONToJSONschemaAndHashMapToJSON() {
        //sample json obj
        String jsonString = "{ \"name\": \"John\", \"age\": 30, \"isEmployed\": true }";
        jsonString = "{\"State\":\"AP\",\"District\":\"Vizag\",\"Schools\":[{\"SchoolID\":\"123\",\"SchoolName\":\"School 123\",\"Phone\":\"9000000123\",\"classes\":[{\"classID\":\"1\",\"className\":\"1st\",\"Sections\":[\"1A\",\"1B\"]},{\"classID\":\"2\",\"className\":\"2nd\",\"Sections\":[\"2A\",\"2B\"]}],\"Facilities\":[\"PlayGround\",\"Swimming\"],\"StaffTypes\":{\"Teachers\":[{\"ID\":\"t1\",\"Name\":\"Teacher 1\",\"Details\":{\"Phone\":\"999999\",\"Address\":\"Vizag\",\"Education\":\"BTech\"}},{\"ID\":\"t2\",\"Name\":\"Teacher 2\",\"Details\":{\"Phone\":\"999999222\",\"Address\":\"Vizag\",\"Education\":\"Degree\"}}]},\"NonStaff\":[{\"ID\":\"s1\",\"Name\":\"Nostaff 1\"},{\"ID\":\"s2\",\"Name\":\"Nostaff 2\"}]},{\"SchoolID\":\"333\",\"SchoolName\":\"School 333\",\"Phone\":\"9000000333\",\"classes\":[{\"classID\":\"LKG\",\"className\":\"LKG\",\"Sections\":[\"A\",\"B\"]},{\"classID\":\"UKG\",\"className\":\"UKG\",\"Sections\":[\"A\",\"B\"]}],\"Facilities\":[\"PlayGround\",\"Swimming\",\"Chess\",\"Skating\"],\"StaffTypes\":{\"Teachers\":[{\"ID\":\"t1\",\"Name\":\"Teacher 1\",\"Details\":{\"Phone\":\"999999\",\"Address\":\"Vizag\",\"Education\":\"BTech\"}},{\"ID\":\"t2\",\"Name\":\"Teacher 2\",\"Details\":{\"Phone\":\"999999222\",\"Address\":\"Vizag\",\"Education\":\"Degree\"}}]},\"NonStaff\":[{\"ID\":\"s1\",\"Name\":\"Nostaff 1\"},{\"ID\":\"s2\",\"Name\":\"Nostaff 2\"}]}]}";
        try {
            tv_xmltojson.setText("");
            JSONObject jsonObject = new JSONObject(jsonString);
            // Convert JSON object to JSON schema representation
            JSONObject jsonSchema = JsonBuilder.generateJsonSchema(jsonObject);
            String jsonSchemaString = jsonSchema.toString(4);
            tv_schema.setText(jsonSchemaString);

            //Convert json Obj to HashMap
            HashMap<String, Object> hashMapObj = JsonBuilder.ConvertJSONToHashMap(jsonString);
            // Build JSON data from the HashMap and JSON schema
            String jsonData = JsonBuilder.buildJsonFromHashMap(hashMapObj, jsonSchemaString);
            tv_result.setText(jsonData);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void execute() {
        String schemaJson = "{\"type\": \"object\",\"properties\": {\"name\": {\"type\": \"string\"},\"age\": {\"type\": \"integer\"},\"email\": {\"type\": \"string\",\"format\": \"email\"},\"address\": {\"type\": \"object\",\"properties\": {\"street\": {\"type\": \"string\"},\"city\": {\"type\": \"string\"},\"zipCode\": {\"type\": \"string\"}}}},\"required\": [\"name\", \"age\", \"email\",\"address\"]}";

        HashMap<String, Object> addressData = new HashMap<>();
        addressData.put("street", "123 Main Street");
        addressData.put("city", "Sample City");
        addressData.put("zipCode", "12345");
        HashMap<String, Object> data = new HashMap<>();
        data.put("name", "John Doe");
        data.put("age", 30);
        data.put("email", "john.doe@example.com");
        data.put("address", addressData);
        //2nd level

        schemaJson = "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"dealerId\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"dealerName\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"address\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"GSTNo\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"ratings\": {\n" +
                "      \"type\": \"number\",\n" +
                "      \"minimum\": 0,\n" +
                "      \"maximum\": 5\n" +
                "    },\n" +
                "    \"registrationDate\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"format\": \"date\"\n" +
                "    },\n" +
                "    \"image\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"website\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"phone\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"location\": {\n" +
                "      \"type\": \"object\",\n" +
                "      \"properties\": {\n" +
                "        \"latitude\": {\n" +
                "          \"type\": \"number\"\n" +
                "        },\n" +
                "        \"longitude\": {\n" +
                "          \"type\": \"number\"\n" +
                "        }\n" +
                "      },\n" +
                "      \"required\": [\"latitude\", \"longitude\"]\n" +
                "    },\n" +
                "    \"catalog\": {\n" +
                "      \"type\": \"array\",\n" +
                "      \"items\": {\n" +
                "        \"type\": \"object\",\n" +
                "        \"properties\": {\n" +
                "          \"cropId\": {\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          \"cropType\": {\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          \"sku\": {\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          \"description\": {\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          \"units\": {\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          \"quatity\": {\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          \"variety\": {\n" +
                "            \"type\": \"string\"\n" +
                "          },\n" +
                "          \"price\": {\n" +
                "            \"type\": \"number\"\n" +
                "          },\n" +
                "          \"stock\": {\n" +
                "            \"type\": \"integer\"\n" +
                "          },\n" +
                "          \"images\": {\n" +
                "            \"type\": \"array\",\n" +
                "            \"items\": {\n" +
                "              \"type\": \"string\"\n" +
                "            }\n" +
                "          },\n" +
                "          \"bestSeller\": {\n" +
                "            \"type\": \"boolean\"\n" +
                "          },\n" +
                "          \"productRatings\": {\n" +
                "            \"type\": \"number\",\n" +
                "            \"minimum\": 0,\n" +
                "            \"maximum\": 5\n" +
                "          }\n" +
                "        },\n" +
                "        \"required\": [\"cropId\", \"cropType\", \"variety\", \"price\", \"stock\"]\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"required\": [\"dealerId\", \"dealerName\", \"address\", \"GSTNo\", \"ratings\", \"location\", \"catalog\"]\n" +
                "}";

        data = JsonBuilder.ConvertJSONToHashMap("{\"image\":\"dealer_image.jpg\",\"website\":\"https://www.johnsagrimart.com\",\"dealerName\":\"John’s AgriMart\",\"address\":\"123 Main Street\",\"phone\":\"+1-123-456-7890\",\"dealerId\":\"ABC123\",\"GSTNo\":\"GST123\",\"ratings\":4.5,\"catalog\":[{\"bestSeller\":true,\"cropId\":\"CROP001\",\"cropType\":\"Vegetable\",\"description\":\"Fresh Tomatoes\",\"images\":[\"tomato_img1.jpg\",\"tomato_img2.jpg\"],\"price\":2.99,\"productRatings\":4.8,\"quatity\":\"500\",\"sku\":\"SKU123\",\"stock\":100,\"units\":\"kg\",\"variety\":\"Cherry\"},{\"bestSeller\":false,\"cropId\":\"CROP002\",\"cropType\":\"Fruit\",\"description\":\"Fresh Apples\",\"images\":[\"apple_img1.jpg\",\"apple_img2.jpg\"],\"price\":3.49,\"productRatings\":4.2,\"quatity\":\"300\",\"sku\":\"SKU456\",\"stock\":50,\"units\":\"kg\",\"variety\":\"Fuji\"}],\"registrationDate\":\"2023-07-20\"}");

        // Build JSON data from the HashMap and JSON schema
        String jsonData = JsonBuilder.buildJsonFromHashMap(data, schemaJson);
        if (jsonData != null) {
            tv_result.setText(jsonData);
            System.out.println(jsonData);
        } else {
            tv_result.setText("Failed to build JSON data.");
            System.out.println("Failed to build JSON data.");
        }

        /*// Convert HashMap to JSON string using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonDataTemp = objectMapper.writeValueAsString(data);
            String validateStatus = validateJsonDataAgainst_JSONschema(jsonDataTemp, schemaJson);
            tv_result.setText(validateStatus + ":=" + jsonDataTemp);
            msg=msg+validateStatus + "E:No Lib" + jsonDataTemp+"\n";
            boolean flag = JsonBuilder.validateJsonData(new JSONObject(jsonDataTemp), new JSONObject(schemaJson));
            tv_result.setText(flag + ":=" + jsonDataTemp);
            msg=msg+flag + "E:No Lib" + jsonDataTemp+"\n";
            tv_result.setText(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    private void execute1() {
        // JSON schema as a String
        String jsonSchema = "{\n" +
                "  \"type\": \"object\",\n" +
                "  \"properties\": {\n" +
                "    \"name\": {\n" +
                "      \"type\": \"string\"\n" +
                "    },\n" +
                "    \"age\": {\n" +
                "      \"type\": \"integer\"\n" +
                "    },\n" +
                "    \"email\": {\n" +
                "      \"type\": \"string\",\n" +
                "      \"format\": \"email\"\n" +
                "    },\n" +
                "    \"isMarried\": {\n" +
                "      \"type\": \"boolean\"\n" +
                "    }\n" +
                "  },\n" +
                "  \"required\": [\"name\", \"age\", \"email\", \"isMarried\"]\n" +
                "}";

        HashMap<String, Object> dataMap = new HashMap<>();
        dataMap.put("name", "John Doe");
        dataMap.put("age", 30);
        dataMap.put("email", "johndoe@example.com");
        dataMap.put("isMarried", false);

        // Convert HashMap to JSON string using Jackson
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String jsonData = objectMapper.writeValueAsString(dataMap);
            boolean flag = JsonBuilder.validateJsonData(new JSONObject(jsonData), new JSONObject(jsonSchema));
            tv_result.setText(flag + ":=" + jsonData);
            msg = msg + flag + "E1:No Lib" + jsonData + "\n";
            execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class Location {
        private double latitude;
        private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
        // Constructor, getters, and setters
    }

    static class Product {
        private String cropId;
        private String cropType;
        private String sku;
        private String description;
        private String units;
        private String quatity;
        private String variety;
        private double price;
        private int stock;
        private List<String> images;
        private boolean bestSeller;
        private double productRatings;

        public String getCropId() {
            return cropId;
        }

        public void setCropId(String cropId) {
            this.cropId = cropId;
        }

        public String getCropType() {
            return cropType;
        }

        public void setCropType(String cropType) {
            this.cropType = cropType;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUnits() {
            return units;
        }

        public void setUnits(String units) {
            this.units = units;
        }

        public String getQuatity() {
            return quatity;
        }

        public void setQuatity(String quatity) {
            this.quatity = quatity;
        }

        public String getVariety() {
            return variety;
        }

        public void setVariety(String variety) {
            this.variety = variety;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public boolean isBestSeller() {
            return bestSeller;
        }

        public void setBestSeller(boolean bestSeller) {
            this.bestSeller = bestSeller;
        }

        public double getProductRatings() {
            return productRatings;
        }

        public void setProductRatings(double productRatings) {
            this.productRatings = productRatings;
        }
        // Constructor, getters, and setters
    }

    class Dealer {
        private String dealerId;
        private String dealerName;
        private String address;
        private String GSTNo;
        private double ratings;
        private String registrationDate;
        private String image;
        private String website;
        private String phone;
        private Location location;
        private List<Product> catalog;

        public String getDealerId() {
            return dealerId;
        }

        public void setDealerId(String dealerId) {
            this.dealerId = dealerId;
        }

        public String getDealerName() {
            return dealerName;
        }

        public void setDealerName(String dealerName) {
            this.dealerName = dealerName;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getGSTNo() {
            return GSTNo;
        }

        public void setGSTNo(String GSTNo) {
            this.GSTNo = GSTNo;
        }

        public double getRatings() {
            return ratings;
        }

        public void setRatings(double ratings) {
            this.ratings = ratings;
        }

        public String getRegistrationDate() {
            return registrationDate;
        }

        public void setRegistrationDate(String registrationDate) {
            this.registrationDate = registrationDate;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getWebsite() {
            return website;
        }

        public void setWebsite(String website) {
            this.website = website;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public Location getLocation() {
            return location;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        public List<Product> getCatalog() {
            return catalog;
        }

        public void setCatalog(List<Product> catalog) {
            this.catalog = catalog;
        }
        // Constructor, getters, and setters
    }

}
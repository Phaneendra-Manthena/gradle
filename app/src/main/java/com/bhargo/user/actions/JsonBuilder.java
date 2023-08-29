package com.bhargo.user.actions;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import javax.xml.parsers.ParserConfigurationException;

public class JsonBuilder {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static String buildJsonFromHashMap(HashMap<String, Object> data, String schemaJson) {
        try {
            // Parse the JSON schema into a JsonNode
            JsonNode schemaNode = mapper.readTree(schemaJson);
            // Convert the HashMap to a JsonNode
            JsonNode dataNode = buildJsonNode(data, schemaNode);
            // Convert the JsonNode to a JSON string
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(dataNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static JsonNode buildJsonNode(HashMap<String, Object> data, JsonNode schemaNode) {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        for (String key : data.keySet()) {
            if (schemaNode.has("properties") && schemaNode.get("properties").has(key)) {
                JsonNode propertySchema = schemaNode.get("properties").get(key);
                if (propertySchema.has("type") && propertySchema.get("type").asText().equals("object")) {
                    if (data.get(key) instanceof HashMap) {
                        JsonNode nestedNode = buildJsonNode((HashMap<String, Object>) data.get(key), propertySchema);
                        objectNode.set(key, nestedNode);
                    }
                } else {
                    JsonNode valueNode = objectMapper.valueToTree(data.get(key));
                    objectNode.set(key, valueNode);
                }
            }
        }
        return objectNode;
    }

    public static String ConvertHashMapToJSON(HashMap<String, Object> hashMapObj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonObjStr = mapper.writeValueAsString(hashMapObj);
            return jsonObjStr;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
            return null;
        } catch (JsonMappingException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static HashMap<String, Object> ConvertJSONToHashMap(String jsonData) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            HashMap<String, Object> hashMapObj = mapper.readValue(jsonData,
                    HashMap.class);
            return hashMapObj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean validateJsonData(JSONObject dataJson, JSONObject schemaJson) {
        //As to check?
        try {
            // Check if the data is an object
            if (!dataJson.keys().equals(schemaJson.getJSONObject("properties").keys())) {
                return false;
            }

            JSONObject properties = schemaJson.getJSONObject("properties");
            for (Iterator<String> it = properties.keys(); it.hasNext(); ) {
                String key = it.next();
                String type = properties.getJSONObject(key).getString("type");
                Object value = dataJson.get(key);

                if ("string".equals(type) && !(value instanceof String)) {
                    return false;
                } else if ("integer".equals(type) && !(value instanceof Integer)) {
                    return false;
                } else if ("boolean".equals(type) && !(value instanceof Boolean)) {
                    return false;
                } else if ("object".equals(type) && !(value instanceof JSONObject)) {
                    return false;
                } else if ("array".equals(type) && !(value instanceof JSONArray)) {
                    return false;
                } else if ("array".equals(type)) {
                    JSONArray items = properties.getJSONObject(key).getJSONArray("items");
                    for (int i = 0; i < ((JSONArray) value).length(); i++) {
                        if (!validateJsonData(((JSONArray) value).getJSONObject(i), items.getJSONObject(0))) {
                            return false;
                        }
                    }
                }
                // Add more checks for other data types as needed.
            }

            JSONArray requiredProperties = schemaJson.getJSONArray("required");
            for (int i = 0; i < requiredProperties.length(); i++) {
                if (!dataJson.has(requiredProperties.getString(i))) {
                    return false;
                }
            }

            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static JSONObject generateJsonSchema(JSONObject jsonObject) throws JSONException {
        JSONObject jsonSchema = new JSONObject();
        jsonSchema.put("type", "object");
        jsonSchema.put("properties", new JSONObject());

        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
            String key = it.next();
            Object value = jsonObject.get(key);
            JSONObject propertySchema = new JSONObject();

            if (value instanceof String) {
                propertySchema.put("type", "string");
            } else if (value instanceof Boolean) {
                propertySchema.put("type", "boolean");
            } else if (value instanceof Integer) {
                propertySchema.put("type", "integer");
            } else if (value instanceof Double) {
                propertySchema.put("type", "number");
            } else if (value instanceof JSONObject) {
                propertySchema = generateJsonSchema((JSONObject) value);
            } else if (value instanceof JSONArray) {
                propertySchema = generateArraySchema((JSONArray) value);
            } else {
                // Handle unsupported data types or other special cases
                propertySchema.put("type", "unknown");
            }

            jsonSchema.getJSONObject("properties").put(key, propertySchema);
        }

        return jsonSchema;
    }

    private static JSONObject generateArraySchema(JSONArray jsonArray) throws JSONException {
        JSONObject arraySchema = new JSONObject();
        arraySchema.put("type", "array");

        if (jsonArray.length() > 0) {
            Object firstElement = jsonArray.get(0);
            JSONObject itemsSchema = new JSONObject();

            if (firstElement instanceof String) {
                itemsSchema.put("type", "string");
            } else if (firstElement instanceof Boolean) {
                itemsSchema.put("type", "boolean");
            } else if (firstElement instanceof Integer) {
                itemsSchema.put("type", "integer");
            } else if (firstElement instanceof Double) {
                itemsSchema.put("type", "number");
            } else if (firstElement instanceof JSONObject) {
                itemsSchema = generateJsonSchema((JSONObject) firstElement);
            } else {
                // Handle unsupported data types or other special cases
                itemsSchema.put("type", "unknown");
            }

            arraySchema.put("items", itemsSchema);
        }

        return arraySchema;
    }

    public static JSONObject convertXmlToJsonObj(String xmlData) throws JSONException{
        // Convert XML data to JSON object
        return XML.toJSONObject(xmlData);
    }


}

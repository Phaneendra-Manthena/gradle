package com.bhargo.user.utils;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DataProcessingJSON 
{
	
	public static JSONObject processData(String jsonStructure,String jsonData,List<String> inParam) throws Exception
	{	
		
		JSONObject jsonObj=new JSONObject();
		JSONArray jsonAry=new JSONArray();
		
		JSONObject jsonObjResponse=new JSONObject();
		JSONArray jsonAryResponse=new JSONArray();
		
		JSONObject jsonObjStruct=new JSONObject(jsonStructure);
		JSONObject jsonObjData=new JSONObject(jsonData);
		
		// JSONObject jsonObjDataSingle=null;
		
		System.out.println("isJSONValid(jsonData)-->"+isJSONValid(jsonData));
		System.out.println("isJSONValid(jsonStructure)-->"+isJSONValid(jsonStructure));
		
	  if(isJSONValid(jsonData) && isJSONValid(jsonStructure))
	  {
			
		JSONArray jsonArrayData=null;
		
		for(String inputParam:inParam)
		{			
			
			if(inputParam.contains("/"))
			{
				String pathVal="";
				
				String []inputParamAry=inputParam.split("\\/");
				
				String jsonKey=inputParam.replace("/", "_");
				
				//for(int i=0;i<inputParamAry.length;i++)
				//{	
				//	pathVal+="/"+inputParamAry[i];
					
				     String pathValTemp=pathVal.substring(pathVal.indexOf("/")+1); 
				     String type=getStructureType(jsonObjStruct,pathValTemp);
				     
					//System.out.println("pathValTemp-->"+pathValTemp);
					
					String jsonString="";
					JSONArray jsonReturnAry=new JSONArray();
					
					/*
					if(i==0)
					{
						jsonString=getJsonStringForObject(jsonObjData,inputParamAry[0]);
					}else
					{
						
						jsonString=getJsonStringForArray(jsonAry,inputParam);
					}
						*/
						
					//System.out.println("welcome-->");
					
					jsonString=getJsonStringForObject(jsonObjData,inputParamAry[0]);	
						
					boolean jsonObjValid=isJSONObjectValid(jsonString);
					
					boolean jsonArrayValid=isJSONArrayValid(jsonString);	
						
					if(jsonObjValid==true && jsonArrayValid==false)
					{
						jsonAry=new JSONArray("["+jsonString+"]");					
					}
					
					if(jsonObjValid==false && jsonArrayValid==true)
					{
						jsonAry=new JSONArray(jsonString);
					}
					
					if(jsonObjValid==false && jsonArrayValid==false)
					{
						
					}
					
					jsonReturnAry=getJsonStringForArray(jsonAry,inputParam,jsonReturnAry);
					
					jsonObjResponse.put(jsonKey, jsonReturnAry);
					
					
					// System.out.println("jsonString-->"+jsonAry.toString());
					
					//
					//System.out.println("jsonObjValid-->"+jsonObjValid);
					//System.out.println("jsonArrayValid-->"+jsonArrayValid);
										   
						/*
						for(int j=0;j<jsonAry.length();j++)
						{
							
						JSONObject jsonFinalObj=jsonAry.getJSONObject(j);
							
						if(jsonFinalObj.has(inputParamAry[i]))
						{
							String value=jsonFinalObj.getString(inputParamAry[i]);
							jsonAryResponse.put(value);
							
							System.out.println("value-->"+value);
						
						}
						
						}
						
						
						jsonObjResponse.put(inputParamAry[i], jsonAryResponse);
						*/
						
					//}
								
				
				} else {
					
					JSONArray jsonReturnAry=new JSONArray();
					
					String jsonString="";
					
					jsonString=getJsonStringForObject(jsonObjData,inputParam);	
					
					jsonReturnAry.put(jsonString);
					
					jsonObjResponse.put(inputParam, jsonReturnAry);
					
					
				}
			
		}
		
	}else{
		
		System.out.println("Invalid JSON Format");
	}
		
		
		System.out.println("jsonObjResponse.toString()-->"+jsonObjResponse.toString());
		return jsonObjResponse;
	}
	
	
	public static String  getJsonStringForObject(JSONObject jsonObjData,String key)
	{
		String jsonString=null;
		try {
			jsonString=jsonObjData.getString(key);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return jsonString;
	}
	
	public static JSONArray  getJsonStringForArray(JSONArray jsonAry,String inputParam,JSONArray jsonReturnAry)
	{	
		JSONArray jsonAryTmp=null;
		
		String []lastKeyAry=inputParam.split("\\/");
		
		if(inputParam.contains("/") && jsonAry != null)
		{
		//System.out.println("inputParam-->"+inputParam);
		
		inputParam=inputParam.substring(inputParam.indexOf("/")+1);
		
		//System.out.println("inputParam2-->"+inputParam);
		
		String []pathValTempAry=inputParam.split("\\/");
		
		String key=pathValTempAry[0];
		
		try{
			
		for(int j=0;j<jsonAry.length();j++)
		{
			
		
			
		JSONObject jsonFinalObj=jsonAry.getJSONObject(j);
		
		
			
		if(jsonFinalObj.has(key))
		{
			String jsonStr=jsonFinalObj.getString(key);
			
			System.out.println("j jsonStr-->"+jsonStr);
			System.out.println("j jsonStr-->"+lastKeyAry[lastKeyAry.length-1]);
			System.out.println("j jsonStr-->"+key);
			
			if(lastKeyAry[lastKeyAry.length-1].equals(key))
			{	
				jsonReturnAry.put(jsonStr);
				
			}
			
			
			boolean jsonObjValid=isJSONObjectValid(jsonStr);
			
			boolean jsonArrayValid=isJSONArrayValid(jsonStr);	
				
			if(jsonObjValid==true && jsonArrayValid==false)
			{
				jsonAryTmp=new JSONArray("["+jsonStr+"]");					
			}
			
			if(jsonObjValid==false && jsonArrayValid==true)
			{
				jsonAryTmp=new JSONArray(jsonStr);
			}
			
			if(jsonObjValid==false && jsonArrayValid==false)
			{
				
			}
			
			System.out.println("jsonAryTmp value-->"+jsonAryTmp);
			System.out.println("inputParam value-->"+inputParam);
			System.out.println("jsonReturnAry value-->"+jsonReturnAry);
			
			getJsonStringForArray(jsonAryTmp,inputParam,jsonReturnAry);
			//System.out.println("jsonAryyyy-->"+jsonAryTmp.toString());
		
		}
		
		}
		
		}catch(JSONException e)
		{
			e.printStackTrace();
			
		}
		//System.out.println("jsonAryyyy-->"+jsonAryTmp.toString());
		// getJsonStringForArray(jsonAryTmp,inputParam);
		
		}
		
		// System.out.println("jsonReturnAry-->"+jsonReturnAry.toString());
		
		return jsonReturnAry;
	}
		
	public static String getStructureType(JSONObject jsonObjStruct,String key)
	{
	   String structType="";
		
	try {
		
	JSONArray jsonArrayStruct=jsonObjStruct.getJSONArray("structure");
	
	 for(int i=0;i<jsonArrayStruct.length();i++)
	 { 
		 JSONObject jsonInObj=jsonArrayStruct.getJSONObject(i);
		 
		 String path=jsonInObj.getString("path");
		 
		 if(path.equals(key)) 
		 {
			 structType=jsonInObj.getString("type"); 
			 
		 }
		 
	 }
	 
		}catch(Exception e)
		{
			System.out.println("exception -->"+e);
		}
		
		return structType;
		
	}
	
	
	public static boolean isJSONObjectValid(String string) 
	{
	    try {
	 
	    	new JSONObject(string);
	    	
	    }catch (JSONException ex) 
	    {
	    	return false;
	    }
	    return true;
	}
	
	public static boolean isJSONArrayValid(String string) 
	{
    // edited, to include @Arthur's comment
    // e.g. in case JSONArray is valid as well...
    
	try {
    	
        new JSONArray(string);
        
    } catch (JSONException ex1) {
        
    	return false;
    }
    return true;
	
	}
	
	public static boolean isJSONValid(String string) 
	{

	    try {

	        new JSONObject(string);

	    } catch (JSONException ex) {

	    	try {

	            new JSONArray(string);

	        } catch (JSONException ex1) {

	            return false;

	        }

	    }

	    return true;

	}
	
	
	public static void main(String args[])
	{
		
		String jsonStructure="{\"structure\":[{\"path\":\"Status\",\"type\":\"Single\"},{\"path\":\"Data\",\"type\":\"Multiple\"},{\"path\":\"Data/District\",\"type\":\"Single\"},{\"path\":\"Data/Mandal\",\"type\":\"Single\"},{\"path\":\"Data/Panchayat\",\"type\":\"Single\"},{\"path\":\"Data/GPS\",\"type\":\"Multiple\"},{\"path\":\"Data/GPS/east\",\"type\":\"Multiple\"},{\"path\":\"Data/GPS/west\",\"type\":\"Single\"},{\"path\":\"Data/GPS/north\",\"type\":\"Single\"},{\"path\":\"Data/GPS/soutch\",\"type\":\"Single\"},{\"path\":\"InParameters\",\"type\":\"Single\"},{\"path\":\"InParameters/one\",\"type\":\"Single\"},{\"path\":\"InParameters/two\",\"type\":\"Single\"},{\"path\":\"InParameters/three\",\"type\":\"Multiple\"},{\"path\":\"InParameters/three/four\",\"type\":\"Single\"},{\"path\":\"InParameters/three/five\",\"type\":\"Single\"},{\"path\":\"OptParameters\",\"type\":\"Single\"},{\"path\":\"OutParameters\",\"type\":\"Single\"}]}";
		
		//String jsonData="{\"Status\":\"200\",\"Data\":[{\"District\":\"Vizianagaram\",\"Mandal\":\"Kothavalasa\",\"Panchayat\":\"Nimalapalem\",\"GPS\":[{\"east\":[{\"key1\":\"value1\"}]},{\"west\":{\"west1\":\"west1_1\",\"west2\":\"west2_2\",\"west3\":[{\"westinwest1\":\"westinwest1_1\"},{\"westinwest2\":\"westinwest1_2\"}]}},{\"north\":\"three\"},{\"soutch\":\"four\"}]},{\"District\":\"Visakhapatnam\",\"Mandal\":\"Pendurthi\",\"Panchayat\":\"Mudapaka\",\"GPS\":[{\"east\":[{\"key1\":\"value1\"}]},{\"west\":{\"west1\":\"west1_12\",\"west22\":\"west2_22\",\"west3\":[{\"westinwest1\":\"westinwest1_12\"},{\"westinwest2\":\"westinwest1_22\"}]}},{\"north\":\"three\"},{\"soutch\":\"four\"}]},{\"District\":\"Krishna\",\"Mandal\":\"Gudivada\",\"Panchayat\":\"Nuzella\",\"GPS\":[{\"east\":[{\"key1\":\"value1\"}]},{\"west\":{\"west1\":\"west1_13\",\"west2\":\"west2_23\",\"west3\":[{\"westinwest1\":\"westinwest1_13\"},{\"westinwest2\":\"westinwest1_23\"}]}},{\"north\":\"three\"},{\"soutch\":\"four\"}]},{\"District\":\"Chittoor\",\"Mandal\":\"Chowdepalle\",\"Panchayat\":\"Laddigam\",\"GPS\":[{\"east\":[{\"key1\":\"value1\"}]},{\"west\":{\"west1\":\"west1_14\",\"west2\":\"west2_24\",\"west3\":[{\"westinwest1\":\"westinwest1_14\"},{\"westinwest2\":\"westinwest1_24\"}]}},{\"north\":\"three\"},{\"soutch\":\"four\"}]}],\"InParameters\":{\"one\":{\"one1\":\"one1_1\",\"one2\":\"one2_2\",\"one3\":[{\"oneinone1\":\"oneinone1_1\"},{\"oneinone2\":\"oneinone1_2\"}]},\"two\":\"2\",\"three\":[{\"four\":\"4\",\"five\":\"5\"},{\"four\":\"6\",\"five\":\"7\"},{\"four\":\"8\",\"five\":\"9\"}]},\"OptParameters\":\"\",\"OutParameters\":\"District|Mandal|Panchayat|GPS\"}";
	
		// String jsonData="{\"status\":\"success\",\"data\":[{\"id\":\"1\",\"employee_name\":\"Tiger Nixon\",\"employee_salary\":\"320800\",\"employee_age\":\"61\",\"profile_image\":\"\"},{\"id\":\"2\",\"employee_name\":\"Garrett Winters\",\"employee_salary\":\"170750\",\"employee_age\":\"63\",\"profile_image\":\"\"},{\"id\":\"3\",\"employee_name\":\"Ashton Cox\",\"employee_salary\":\"86000\",\"employee_age\":\"66\",\"profile_image\":\"\"},{\"id\":\"4\",\"employee_name\":\"Cedric Kelly\",\"employee_salary\":\"433060\",\"employee_age\":\"22\",\"profile_image\":\"\"},{\"id\":\"5\",\"employee_name\":\"Airi Satou\",\"employee_salary\":\"162700\",\"employee_age\":\"33\",\"profile_image\":\"\"},{\"id\":\"6\",\"employee_name\":\"Brielle Williamson\",\"employee_salary\":\"372000\",\"employee_age\":\"61\",\"profile_image\":\"\"},{\"id\":\"7\",\"employee_name\":\"Herrod Chandler\",\"employee_salary\":\"137500\",\"employee_age\":\"59\",\"profile_image\":\"\"},{\"id\":\"8\",\"employee_name\":\"Rhona Davidson\",\"employee_salary\":\"327900\",\"employee_age\":\"55\",\"profile_image\":\"\"},{\"id\":\"9\",\"employee_name\":\"Colleen Hurst\",\"employee_salary\":\"205500\",\"employee_age\":\"39\",\"profile_image\":\"\"},{\"id\":\"10\",\"employee_name\":\"Sonya Frost\",\"employee_salary\":\"103600\",\"employee_age\":\"23\",\"profile_image\":\"\"},{\"id\":\"11\",\"employee_name\":\"Jena Gaines\",\"employee_salary\":\"90560\",\"employee_age\":\"30\",\"profile_image\":\"\"},{\"id\":\"12\",\"employee_name\":\"Quinn Flynn\",\"employee_salary\":\"342000\",\"employee_age\":\"22\",\"profile_image\":\"\"},{\"id\":\"13\",\"employee_name\":\"Charde Marshall\",\"employee_salary\":\"470600\",\"employee_age\":\"36\",\"profile_image\":\"\"},{\"id\":\"14\",\"employee_name\":\"Haley Kennedy\",\"employee_salary\":\"313500\",\"employee_age\":\"43\",\"profile_image\":\"\"},{\"id\":\"15\",\"employee_name\":\"Tatyana Fitzpatrick\",\"employee_salary\":\"385750\",\"employee_age\":\"19\",\"profile_image\":\"\"},{\"id\":\"16\",\"employee_name\":\"Michael Silva\",\"employee_salary\":\"198500\",\"employee_age\":\"66\",\"profile_image\":\"\"},{\"id\":\"17\",\"employee_name\":\"Paul Byrd\",\"employee_salary\":\"725000\",\"employee_age\":\"64\",\"profile_image\":\"\"},{\"id\":\"18\",\"employee_name\":\"Gloria Little\",\"employee_salary\":\"237500\",\"employee_age\":\"59\",\"profile_image\":\"\"},{\"id\":\"19\",\"employee_name\":\"Bradley Greer\",\"employee_salary\":\"132000\",\"employee_age\":\"41\",\"profile_image\":\"\"},{\"id\":\"20\",\"employee_name\":\"Dai Rios\",\"employee_salary\":\"217500\",\"employee_age\":\"35\",\"profile_image\":\"\"},{\"id\":\"21\",\"employee_name\":\"Jenette Caldwell\",\"employee_salary\":\"345000\",\"employee_age\":\"30\",\"profile_image\":\"\"},{\"id\":\"22\",\"employee_name\":\"Yuri Berry\",\"employee_salary\":\"675000\",\"employee_age\":\"40\",\"profile_image\":\"\"},{\"id\":\"23\",\"employee_name\":\"Caesar Vance\",\"employee_salary\":\"106450\",\"employee_age\":\"21\",\"profile_image\":\"\"},{\"id\":\"24\",\"employee_name\":\"Doris Wilder\",\"employee_salary\":\"85600\",\"employee_age\":\"23\",\"profile_image\":\"\"}]}";
		
		String jsonData="{\"Status\":\"200\",\"Message\":\"Data submitted successfully.\",\"MobileData\":{\"StudentID\":\"101\",\"StudentName\":\"santhosh\",\"StudentAddress\":null,\"StudentMarks\":null}}";
		
		String contentType="json";
		
		List<String> inParam=new ArrayList<String>();
		/*
	    inParam.add("Data/GPS/west/west1");
		inParam.add("Data/GPS/east");
		inParam.add("Data/GPS/east/key1");
		inParam.add("Data/GPS");
	    inParam.add("Data/District"); */
	    /*
	    inParam.add("InParameters/three");
		inParam.add("InParameters/one/one3/oneinone1");
		inParam.add("Status");*/
		
		
		inParam.add("Status");
		inParam.add("MobileData/StudentID");
		inParam.add("MobileData/StudentAddress");
		inParam.add("MobileData/StudentAddress/DoorNo");
		inParam.add("MobileData/StudentAddress/PinCode");
		inParam.add("MobileData/StudentMarks/SubjectName");
		inParam.add("MobileData/StudentMarks/SubjectMarks");
		//inParam.add("ArrayObject/Name");
		
		//String jsonData="{\"ArrayObject\":[{\"StudentId\":1,\"Name\":\"NitinTyagi\",\"Marks\":400},{\"StudentId\":2,\"Name\":\"AshishTripathi\",\"Marks\":500}]}";
		
		try {
			
		processData(jsonStructure,jsonData,inParam);
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
			
			

}

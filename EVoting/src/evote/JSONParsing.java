package evote;
/*package evote;

import java.io.IOException;
import java.io.StringWriter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONParsing {

	@SuppressWarnings("unchecked")
	public static void JSONEncode(String x1,String x2,String x3,String x4, String x5,String x6){
		JSONObject obj = new JSONObject();

		obj.put("x1", x1);
		obj.put("x2", x2);
		obj.put("x3", x3);
		obj.put("x4", x4);
		obj.put("x5", x5);
		obj.put("x6", x6);

		StringWriter out = new StringWriter();
		try {
			obj.writeJSONString(out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String jsonText = out.toString();
		System.out.print(jsonText + "\n");
	}
	
	
	public static void JSONDecode(){
		
	      JSONParser parser = new JSONParser();
	      String s = "[0,{\"1\":{\"2\":{\"3\":{\"4\":[5,{\"6\":7}]}}}}]";
			
	      try{
	         Object obj = parser.parse(s);
	         JSONArray array = (JSONArray)obj;
				
	         System.out.println("The 2nd element of array");
	         System.out.println(array.get(1));
	         System.out.println();

	         JSONObject obj2 = (JSONObject)array.get(1);
	         System.out.println("Field \"1\"");
	         System.out.println(obj2.get("1"));    

	         s = "{}";
	         obj = parser.parse(s);
	         System.out.println(obj);

	         s = "[5,]";
	         obj = parser.parse(s);
	         System.out.println(obj);

	         s = "[5,,2]";
	         obj = parser.parse(s);
	         System.out.println(obj);
	      }catch(ParseException pe){
			
	         System.out.println("position: " + pe.getPosition());
	         System.out.println(pe);
	      }
	   }
}
*/
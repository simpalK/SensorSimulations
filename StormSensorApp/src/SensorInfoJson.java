import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

import static java.lang.System.out;

public class SensorInfoJson extends Thread {
	JsonArray jsonArrayNode;
	JsonArrayBuilder jsonArrayBuild= Json.createArrayBuilder();
	JsonArray jsonArrayLinks;
	JsonArrayBuilder jsonArrayBuildLinks = Json.createArrayBuilder();

	FileReader fileReader;
	String[] tokens =new String[10000];
	String[] filteredTokens =new String[5];
	int[][] topo = new int[][]{
      		{1, 0, 0, 1, 0},
      		{1, 1, 1, 0, 0},
      		{0, 1, 1, 0, 0},
      		{1, 0, 0, 1, 1},
      		{1, 0, 0, 1, 1}
      		};
	int countLines=0;
	int countFilterLines =0;
	LinkedList sensorDataMap; 
	private final long sensorStart = System.currentTimeMillis();
	public String lastSensorData = null;
	Date myClock = new Date();
    boolean sensorFlag = true;
    private int sensId;
    
   
  public void run (){
	  FileInputStream in=null;
      String str = "";
      String sentence="";
   // A simple array
     // JsonProvider parser = new JsonProvider();
      try{
      	//define links based on topo array or it can be done by parsing groupid coming from bolt 
      	
          	for(int i=0; i<5; i++){
      			for(int j=0; j<5; j++){
      				if(topo[i][j]==1){
      					JsonObject jsonObjectLink = Json.createObjectBuilder()
      		        			.add("source", i)
      		        			.add("target", j)
      		        			.add("value",i)
      		        			.build();
      					jsonArrayBuildLinks.add(jsonObjectLink);
      				}
      			}        				
      			} 
      	
			fileReader = new FileReader("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/jsonSensorFile.txt");
			
			BufferedReader reader = new BufferedReader(fileReader);
  		while((str = reader.readLine()) != null){
  			tokens[countLines++] =str;
  		}
      	if(tokens[0]!=null){
          	String[] senseVal = tokens[countLines-1].split(",");
      		String lastTimestamp = senseVal[3];
      		for(int i=countLines-1;i>=0;i--){
              	String word = tokens[i];
              	String[] sensorVal = word.split(",");
                  word = word.trim();
                  if(!word.isEmpty() && sensorVal[3].contentEquals(lastTimestamp)){
                  	Boolean foundItem = false;
                  	for(int j=0;j<countFilterLines;j++){
                      	String[] sensorCurrentVal = filteredTokens[j].split(",");
                  		if(sensorCurrentVal[1].contentEquals(sensorVal[1])){
                  			foundItem = true;
                  		break;
                  		}
                  	}
                  	if(foundItem==false)
                  	filteredTokens[countFilterLines++]= tokens[i];
                  }
              }
      	}
      	Arrays.sort(filteredTokens, new Comparator<String>() {
      	    public int compare(String str1, String str2) {
      	    	//change the substring to sort array based on sensor when groupid is longer
      	        String substr1 = str1.substring(6);
      	        String substr2 = str2.substring(6);

      	        return substr1.compareTo(substr2);
      	    }
      	});

          
          for(int i=0;i<countFilterLines;i++){
          	String word = filteredTokens[i];
          	String[] senseVal = word.split(",");
              word = word.trim();
              if(!word.isEmpty()){
              	JsonObject jsonObject1 = Json.createObjectBuilder()
              			.add("sensorId", senseVal[1])
              			.add("group", senseVal[4])
              			.build();
              	jsonArrayBuild.add(jsonObject1);
              }
          }
          jsonArrayNode = jsonArrayBuild.build();
          jsonArrayLinks = jsonArrayBuildLinks.build();
          
          //Computing group Ids for tuples to forward for LISA compute
          /*for(String word : tokens){
          	String[] senseVal = word.split(",");
              word = word.trim();
              if(!word.isEmpty()){
              	for(int i=0; i<5; i++){
                  	if(Integer.parseInt(senseVal[0]) == i){
          			for(int j=0; j<5; j++){
          				groupIds += topo[i][j];
          			} 
                      
                  	
                  	}
              	}
              }
      	//InputStream inputStream = new FileInputStream("jsonSensorFile.json");
      	/*JsonObject jsonObject1 = Json.createObjectBuilder()
      			.add("sensorId", "1")
      			.add("group", "11001")
      			.build();
      	JsonObject jsonObject2 = Json.createObjectBuilder()
      			.add("sensorId", "2")
      			.add("group", "11001")
      			.build();
      	JsonArray jsonArrayNodes = Json.createArrayBuilder()
      			.add(jsonObject1).add(jsonObject2).build();
      	JsonObject jsonObject3 = Json.createObjectBuilder()
      			.add("source", 1)
      			.add("target", 2)
      			.add("value",1)
      			.build();
      	JsonObject jsonObject4 = Json.createObjectBuilder()
      			.add("source", 2)
      			.add("target", 1)
      			.add("value",1)
      			.build();*/
      	//JsonArray jsonArrayLinks = Json.createArrayBuilder().build();
      			//.add(jsonObject3).add(jsonObject4).build();
      	
      	JsonObject jsonObject = Json.createObjectBuilder()
      			.add("nodes", jsonArrayNode)
      			.add("links", jsonArrayLinks)
      			.build();
      	// config Map is created for pretty printing.
      	Map<String, Boolean> config = new HashMap<>();

      	// Pretty printing feature is added.
      	config.put(JsonGenerator.PRETTY_PRINTING, true);

      	try (PrintWriter pw = new PrintWriter("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/d3.v3/jsonDataD3.json")
          ;JsonWriter jsonWriter = Json.createWriterFactory(config).createWriter(pw)) {

      		// Json object is being sent into file system
      		
      		jsonWriter.writeObject(jsonObject);   		
		   System.out.print("writing info in json file****************************");
      	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      	} } catch (Exception e) {
  			// TODO Auto-generated catch block
  			e.printStackTrace();
          	}finally {
			        
			    }
          	         		                 
    }

}


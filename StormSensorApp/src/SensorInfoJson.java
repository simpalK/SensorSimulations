import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
import java.util.concurrent.TimeUnit;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

import org.apache.commons.io.FileUtils;

import static java.lang.System.out;

public class SensorInfoJson extends Thread {
	JsonArray jsonArrayNode;
	JsonArray jsonArrayLinks;
	JsonArrayBuilder jsonArrayBuildLinks = Json.createArrayBuilder();

	FileReader fileReader;
	FileReader topologyReader;
	String[] tokens =new String[100000];
	String[] filteredTokens =new String[Global.numberOfNodes];
	String[] sensorsIds = new String[Global.numberOfNodes];
	String[] finalAddedTokens =new String[Global.numberOfNodes];
	String[] topologyRows =new String[Global.numberOfNodes];

	
	//int[][] coOrdinates = new int[1600][2];
	int[][] coOrdinates = 
	{
			{100, 400},
			{200, 400},
			{300, 400},
			{400, 400},
			{100, 300},
			{200, 300},
			{300, 300},
			{400, 300},
			{100, 200},
			{200, 200},
			{300, 200},
			{400, 200},
			{100, 100},
			{200, 100},
			{300, 100},
			{400, 100}
	};
	int countLines=0;
	int countFilterLines =0;
	int topologyLines = 0;
	LinkedList sensorDataMap; 
	private final long sensorStart = System.currentTimeMillis();
	public String lastSensorData = null;
	Date myClock = new Date();
    boolean sensorFlag = true;
    private int sensId;
   // int[][] topoFromFile= new int[1600][1600];
    int[][] topoFromFile= new int[Global.numberOfNodes][Global.numberOfNodes];

    SensorInfoJson(String[] sensorsIds) {
    	this.sensorsIds = sensorsIds;
    	int y=20;
    	int x = 20;
    	//set node co ordinates
    	/*for(int i=0; i<16; i+=40){
    		x = 20;
    		for(int j =0; j<40;j++){
    		  		coOrdinates[i + j][0]= x;
    		  		coOrdinates[i + j][1]= y;
    		  		x = x+ 20;
    		}
    		y = y + 20;
    	}*/
    	
    	
    	
    }
   
  public void run (){

	  try {
		sense ();

	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }
	  
	  synchronized void sense () throws InterruptedException {

	   /* File fileSensor = new File("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/d3.v3/jsonDataD3.json");

	  //FileUtils.deleteQuietly(new File("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/d3.v3/jsonDataD3.json"));
	  if (fileSensor.exists()) {
		  PrintWriter writer;
		try {
			writer = new PrintWriter(fileSensor);
			writer.print("");
	      	writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      	
		}*/
	 
	 FileInputStream in=null;
      String str = "";
      String sentence="";
  	  finalAddedTokens =new String[Global.numberOfNodes];
  	  tokens =new String[100000];
	  filteredTokens =new String[Global.numberOfNodes];
	  countLines=0;
	  countFilterLines =0;
	  topologyLines = 0;
   // A simple array
     // JsonProvider parser = new JsonProvider();
      try{
      	//define links based on topo array or it can be done by parsing groupid coming from bolt 
			//topologyReader = new FileReader("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/topologyInformation.txt");
    	  Scanner inputStream = null;
			try
			{
			  inputStream = new Scanner(new File(Global.filesPath + "/topologyInformation.txt"));//The txt file is being read correctly.
			}
			catch(FileNotFoundException e)
			{
			  System.exit(0);
			}
            
			for (int row = 0; row < Global.numberOfNodes; row++) {
			    String line = inputStream.nextLine();
			    String[] lineValues = line.split(",");
			  for (int column = 0; column < Global.numberOfNodes; column++) {
			    topoFromFile[row][column] = Integer.parseInt(lineValues[column]);
			  }
			}
			inputStream.close();
          	for(int i=0; i<Global.numberOfNodes; i++){
      			for(int j=0; j<Global.numberOfNodes; j++){
      				if(topoFromFile[i][j]==1){
      					JsonObject jsonObjectLink = Json.createObjectBuilder()
      		        			.add("source", i)
      		        			.add("target", j)
      		        			.add("value",1)
      		        			.build();
      					jsonArrayBuildLinks.add(jsonObjectLink);
      				}
      			}        				
      			} 
      	
			fileReader = new FileReader(Global.filesPath + "/jsonSensorFile.txt");

			BufferedReader reader = new BufferedReader(fileReader);
  		while((str = reader.readLine()) != null){
  			tokens[countLines++] =str;
  		}
      	if(tokens[0]!=null){
          	String[] senseVal = tokens[countLines-1].split(",");
      		String lastTimestamp = senseVal[2];
      		for(int i=countLines-1;i>=0;i--){
              	String word = tokens[i];
              	String[] sensorVal = word.split(",");
                  word = word.trim();
                  if(!word.isEmpty() && sensorVal[2].contentEquals(lastTimestamp)){
                  	Boolean foundItem = false;
                  	for(int j=0;j<countFilterLines;j++){
                      	String[] sensorCurrentVal = filteredTokens[j].split(",");
                  		if(sensorCurrentVal[0].contains(sensorVal[0])){
                  			foundItem = true;
                  		break;
                  		}
                  	}
                  	if(foundItem==false)
                  	filteredTokens[countFilterLines++]= tokens[i];
                  }
              }
      	}
        for(int k=0; k<Global.numberOfNodes; k++){
        	Boolean foundSensor= false;
        	String findSensor = sensorsIds[k];
        	for(int l=0; l<countFilterLines; l++)
        		{
              	String[] sensorCurrentVal = filteredTokens[l].split(",");
        		if(findSensor.contentEquals(sensorCurrentVal[0])){
        			finalAddedTokens[k]= filteredTokens[l];
        			foundSensor= true;
        			break;
        		}
        		}
        	if(foundSensor== false)
        		finalAddedTokens[k] = sensorsIds[k] + ",no data,notimestamp,noValue";
        }      	
      	/*if(filteredTokens[0] !=null){
      	Arrays.sort(filteredTokens, new Comparator<String>() {
      	    public int compare(String str1, String str2) {
      	    	//change the substring to sort array based on sensor when groupid is longer
      	        String substr1 = str1.substring(16);
      	        String substr2 = str2.substring(16);

      	        return substr1.compareTo(substr2);
      	    }
      	
      	});
      	}*/

    	JsonArrayBuilder jsonArrayBuild= Json.createArrayBuilder();

        for(int i=0;i<Global.numberOfNodes;i++){
          	String word = finalAddedTokens[i];
          	String[] senseVal = word.split(",");
              word = word.trim();
              if(!word.isEmpty()){
            	  int sensorLisaValue;
            	  if(senseVal[3].contains("noValue")) {
            		  sensorLisaValue = 179;
            		  } else {            	  
            			  if(Double.parseDouble(senseVal[3])<0.0){ 
            				  sensorLisaValue = (int) (225 * (Math.pow(Double.parseDouble(senseVal[3]),20))); 
            				  } else { 
            					  sensorLisaValue = 123; 
            					  }}
              	JsonObject jsonObject1 = Json.createObjectBuilder()
              			.add("sensorId", senseVal[0])
              			.add("group", sensorLisaValue)
              			.add("x", coOrdinates[i][0] )
              			.add("y", coOrdinates[i][1])
              			.add("fixed", true)
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
        //jsonArrayNode.clear();

      	// config Map is created for pretty printing.
      	Map<String, Boolean> config = new HashMap<>();

      	// Pretty printing feature is added.
      	config.put(JsonGenerator.PRETTY_PRINTING, true);
	   // File file = new File("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/d3.v3/jsonDataD3.json");
       //if(file.exists())file.createNewFile();
      	try {
      	FileWriter pw = new FileWriter(Global.filesPath + "/d3.v3/jsonDataD3.json",false);
      			JsonWriter jsonWriter = Json.createWriterFactory(config).createWriter(pw);

      		// Json object is being sent into file system
      		
      		jsonWriter.writeObject(jsonObject);  
      		//jsonObject.clear();
      		jsonWriter.close();
      		pw.close();
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


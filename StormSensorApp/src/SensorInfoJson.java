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
	String[] tokens =new String[10000];
	String[] filteredTokens =new String[16];
	String[] sensorsIds = {"N-H563T",	"N-QWNZH",	"N-LETTK",	"N-SCK04",	"N-8HOVD",	"N-2GWON",	"N-UFCUA",	"N-6PFYW",	"N-TZD20",	"N-WRYAZ",	"N-3IK0Y",	"N-JQ338",	"N-Y47X6",	"N-2Z2WK",	"N-GRDHN",	"N-L04BJ"};
	String[] finalAddedTokens =new String[16];
	String[] topologyRows =new String[16];

	
	int[][] coOrdinates = new int[][]{
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
    int[][] topoFromFile= new int[16][16];
    
   
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
  	  finalAddedTokens =new String[16];
  	  tokens =new String[10000];
	  filteredTokens =new String[16];
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
			  inputStream = new Scanner(new File("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/topologyInformation.txt"));//The txt file is being read correctly.
			}
			catch(FileNotFoundException e)
			{
			  System.exit(0);
			}
            
			for (int row = 0; row < 16; row++) {
			    String line = inputStream.nextLine();
			    String[] lineValues = line.split(",");
			  for (int column = 0; column < 16; column++) {
			    topoFromFile[row][column] = Integer.parseInt(lineValues[column]);
			  }
			}
			inputStream.close();
          	for(int i=0; i<16; i++){
      			for(int j=0; j<16; j++){
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
                  		if(sensorCurrentVal[1].contains(sensorVal[1])){
                  			foundItem = true;
                  		break;
                  		}
                  	}
                  	if(foundItem==false)
                  	filteredTokens[countFilterLines++]= tokens[i];
                  }
              }
      	}
        for(int k=0; k<16; k++){
        	Boolean foundSensor= false;
        	String findSensor = sensorsIds[k];
        	for(int l=0; l<countFilterLines; l++)
        		{
              	String[] sensorCurrentVal = filteredTokens[l].split(",");
        		if(findSensor.contentEquals(sensorCurrentVal[1])){
        			finalAddedTokens[k]= filteredTokens[l];
        			foundSensor= true;
        			break;
        		}
        		}
        	if(foundSensor== false)
        		finalAddedTokens[k] = "noGroup," + sensorsIds[k] + ",no data,notimestamp,noValue";
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

        for(int i=0;i<16;i++){
          	String word = finalAddedTokens[i];
          	String[] senseVal = word.split(",");
              word = word.trim();
              if(!word.isEmpty()){
              	JsonObject jsonObject1 = Json.createObjectBuilder()
              			.add("sensorId", senseVal[1])
              			.add("group", senseVal[4])
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
      	FileWriter pw = new FileWriter("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/d3.v3/jsonDataD3.json",false);
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


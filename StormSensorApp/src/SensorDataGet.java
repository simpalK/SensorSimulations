import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.json.stream.JsonGenerator;

import static java.lang.System.out;

public class SensorDataGet extends Thread{
	JsonArray jsonArrayNode;
	JsonArray jsonArrayLinks;	
SensorDataSet sensor = null;
int maxInData;
int dataCount;
static int i =0;
static int j=0;
File fileSensor = new File("logData.txt");


public void run () {
    
	File fileSense = new File("logDataSensor.txt");
	// if file doesnt exists, then create it
	if (fileSense.exists()) {
		try {
		InputStream is = new BufferedInputStream(new FileInputStream(Global.filesPath + "logDataSensor.txt"));
		
	        byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        //deleting data from file if it exceeds 25 lines for 5 sensors
	        if(count>25){
	        	PrintWriter writer = new PrintWriter(fileSense);
	        	writer.print("");
	        	writer.close();
	        }
	        is.close();
	    } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
     
	 /*File fileSensor = new File("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/d3.v3/jsonDataD3.json");

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
	JsonArrayBuilder jsonArrayBuildLinks = Json.createArrayBuilder();
	JsonArrayBuilder jsonArrayBuild= Json.createArrayBuilder();

	  JsonObject jsonObjectLink = Json.createObjectBuilder()
    			.add("source", i++)
    			.add("target", j++)
    			.add("value",i)
    			.build();
		jsonArrayBuildLinks.add(jsonObjectLink);
		
		JsonObject jsonObject1 = Json.createObjectBuilder()
      			.add("sensorId", i+10)
      			.add("group", j+10)
      			.add("x", 10 )
      			.add("y", 10)
      			.add("fixed", true)
      			.build();
      	jsonArrayBuild.add(jsonObject1);
      	jsonArrayNode = jsonArrayBuild.build();
        jsonArrayLinks = jsonArrayBuildLinks.build();
        JsonObject jsonObject = Json.createObjectBuilder()
      			.add("nodes", jsonArrayNode)
      			.add("links", jsonArrayLinks)
      			.build();
        
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
      		jsonWriter.close();
      		pw.close();
		   System.out.print("writing info in json file****************************");
      	} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
      	} 
		  	         		                 
    
	File file = new File("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/jsonSensorFile.txt");
	if (file.exists()) {
		InputStream is;
		try {
			is = new BufferedInputStream(new FileInputStream("/home/simpal/stormSensorReco/SensorSimulation/SensorSimulations/StormSensorApp/jsonSensorFile.txt"));
			byte[] c = new byte[1024];
	        int count = 0;
	        int readChars = 0;
	        boolean empty = true;
	        while ((readChars = is.read(c)) != -1) {
	            empty = false;
	            for (int i = 0; i < readChars; ++i) {
	                if (c[i] == '\n') {
	                    ++count;
	                }
	            }
	        }
	        if(count>100){
	        	PrintWriter writer = new PrintWriter(file);
	        	writer.print("");
	        	writer.close();
	        }
	        is.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	try {
		sleep(1000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  } // run

}

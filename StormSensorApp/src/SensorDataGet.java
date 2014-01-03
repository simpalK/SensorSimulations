import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.*;

import static java.lang.System.out;

public class SensorDataGet extends Thread{
	
SensorDataSet sensor = null;
int maxInData;
int dataCount;
File fileSensor = new File("logData.txt");


public void run () {
    
	File fileSense = new File("logDataSensor.txt");
	// if file doesnt exists, then create it
	if (fileSense.exists()) {
		try {
		InputStream is = new BufferedInputStream(new FileInputStream("logDataSensor.txt"));
		
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
		sleep(1000000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  } // run

}

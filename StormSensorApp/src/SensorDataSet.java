import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.Random;

import static java.lang.System.out;



public class SensorDataSet extends Thread {
	LinkedList sensorDataMap; 
	private final long sensorStart = System.currentTimeMillis();
	public String lastSensorData = null;
	Date myClock = new Date();
    boolean sensorFlag = true;
    private String sensId;
    SensorDataSet(int sensorId) {
    	sensId = "Sensor" + sensorId;
    	sensorDataMap = new LinkedList();
    }
    public void stopReadingData (){
	    sensorFlag = false;
	  }
  public void run (){
	    while (sensorFlag)
			try {
    			  Thread.sleep(100);
				sense ();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
  
  
  
  synchronized void sense () throws InterruptedException {
	  try {
		  
			String content = "This is the content to write into file";

			File file = new File("logData.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			}
			String value = "sensorId:" + sensId + "value" + new Random(System.currentTimeMillis());
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(value);
			bw.newLine();
			bw.close();
			sensorDataMap.add(sensId + "  value:  " +value);
		} catch (IOException e) {
			e.printStackTrace();
		}
  }
  
  synchronized String get () {
	  String sensdata ="null";  
	  if(sensorDataMap.isEmpty() == false)
	     sensdata= sensorDataMap.getLast().toString();
    
        return sensdata;
     } 
  

}

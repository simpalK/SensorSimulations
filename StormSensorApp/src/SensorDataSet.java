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

import static java.lang.System.out;



public class SensorDataSet extends Thread {
	LinkedList sensorDataMap; 
	private final long sensorStart = System.currentTimeMillis();
	public String lastSensorData = null;
	Date myClock = new Date();
    boolean sensorFlag = true;
    private int sensId;
    SensorDataSet(int sensorId) {
    	sensId = sensorId;
    	sensorDataMap = new LinkedList();
    }
    public void stopReadingData (){
	    sensorFlag = true;
	  }
  public void run (){
	    while (sensorFlag)
			try {
    			  Thread.sleep(30000);
				sense ();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	  }
    
  synchronized void sense () throws InterruptedException {
	  try {
			File file = new File("logDataSensor.txt");

			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} /*else {
				InputStream is = new BufferedInputStream(new FileInputStream("logDataSensor.txt"));
				try {
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
			        	PrintWriter writer = new PrintWriter(file);
			        	writer.print("");
			        	writer.close();
			        }
			    } finally {
			        is.close();
			    }
			}*/
			DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
			   //get current date time with Date()
			   Date date = new Date();
			   //System.out.println(dateFormat.format(date));
		 
			   //get current date time with Calendar()
			   Calendar cal = Calendar.getInstance();
			  // System.out.println(dateFormat.format(cal.getTime()));
			String value = sensId + "," + Math.abs(new Random(System.currentTimeMillis()).nextInt())+ "," + dateFormat.format(cal.getTime());
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(value);
			bw.newLine();
			bw.close();
			sensorDataMap.add(sensId + ",  value:  " +value + ",");
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

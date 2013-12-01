import static java.lang.System.out;

import java.lang.Number;
import java.math.BigDecimal;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
public class StartSensors {
    static HashMap<String,HashMap<Integer,String>> sens = new HashMap<String,HashMap<Integer,String>>();
    public static HashMap<Integer,String> dat1 = new HashMap<Integer,String>();
    public static HashMap<Integer,String> dat2 = new HashMap<Integer,String>();
    public static HashMap<Integer,String> dat3 = new HashMap<Integer,String>();
    public static HashMap<Integer,String> dat4 = new HashMap<Integer,String>();
    public static HashMap<Integer,String> dat5 = new HashMap<Integer,String>();
    
    //topology definition: connection between sensors
    static int[][] topo = new int[][]{
    		{0, 0, 0, 1, 0},
    		{1, 0, 1, 0, 0},
    		{0, 1, 0, 0, 0},
    		{1, 0, 0, 0, 1},
    		{1, 0, 0, 1, 0}
    		};
    
    static File fileSensor = new File("logData.txt");
    public static void defTopology(HashMap<String, HashMap<Integer, String>> sens2)
    {
    	mapDataFile(sens2);
    	
    	//genGapEvent(topo[i][i]);
    	computeLisa();
    		
    	
    	
    
    
    }
    private static BigDecimal computeSD(BigDecimal mean,List<BigDecimal> values){
    	BigDecimal sdVal = new BigDecimal(0);
    	BigDecimal finalSD = new BigDecimal(0);
    	for(int j=0; j<values.size();j++)
			sdVal =sdVal.add(mean.subtract(values.get(j)).multiply(mean.subtract(values.get(j))));
    	finalSD = new BigDecimal(Math.sqrt(sdVal.divide(new BigDecimal(values.size())).doubleValue()));
    	return finalSD;
    	
    }
    private static BigDecimal computeMean(){
    	BigDecimal sum = new BigDecimal(0);
    	BigDecimal mean = new BigDecimal(0);
    	BigDecimal standardDev = new BigDecimal(0);
      List<BigDecimal> temp;
    	
    	for(int i =0; i<5; i++){

		temp= getValuesForLisaCompuation(i);
		for(int j=0; j<temp.size();j++)
			sum =sum.add(temp.get(j));
		mean = sum.divide(new BigDecimal(temp.size()),2);
		standardDev =computeSD(mean,temp);
    	
    	}
    		
    	return new BigDecimal(1);
    }
    private static List<BigDecimal> getValuesForLisaCompuation(int i){
    	
        List<BigDecimal> lValues= new ArrayList<BigDecimal>();
    	
    	String sensorName;
    		
    		for(int j =0; j<5; j++)
    		{
    			
    			if(i!=j && topo[i][j]==1){
    			switch (j) {
    			case 0:    
    				sensorName = "Sensor" + (1);        			
    				lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat1.size()-1).toString())));
        		
    			case 1: 
        			sensorName = "Sensor" + (2);
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat2.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" +(sens.get("Sensor1").get(dat2.size()-1 +"\n")));
        			
    			case 2: 
        			sensorName = "Sensor" + (3);        			
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat3.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat3.size()-1)+"\n"));
        			
    			case 3: 
        			sensorName = "Sensor" + (4);                   
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat4.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat4.size()-1)+"\n"));
    			case 4: 
        			sensorName = "Sensor" + (5);       			
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat5.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor5").get(dat5.size()-1)+"\n"));
        					
    			}
    			        
    		}
    		switch (i) {
    			case 0:    
    				sensorName = "Sensor" + (1);        			
    				lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat1.size()-1).toString())));
        			        			    	
    			case 1: 
        			sensorName = "Sensor" + (2);        			
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat2.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" +(sens.get("Sensor1").get(dat2.size()-1 +"\n")));
        			
    			case 2: 
        			sensorName = "Sensor" + (3);
        			
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat3.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat3.size()-1)+"\n"));
        			
    			case 3: 
        			sensorName = "Sensor" + (4);                    
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat4.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat4.size()-1)+"\n"));
        			
    			case 4: 
        			sensorName = "Sensor" + (5);        			
        			lValues.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat5.size()-1).toString())));
        			//sum= sum.divide(new BigDecimal(dat5.size()),2);
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor5").get(dat5.size()-1)+"\n"));        		       			    		
    		}
    			
    	}
    		
    		out.print("sum of sensor "  + lValues + "\n");
    			return lValues;
    }
    private static void computeLisa() {
    	BigDecimal mean=new BigDecimal(0);
    	BigDecimal sum =new BigDecimal(0);
    	String sensorName;
    	BigDecimal result=new BigDecimal(0);
    	for(int i =0; i<5; i++){
    		
    		for(int j =0; j<5; j++)
    		{
    			
    			if(i!=j && topo[i][j]==1){
    			switch (j) {
    			case 0:    
    				sensorName = "Sensor" + (1);        			
    				sum = sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat1.size()-1).toString())));
        		
    			case 1: 
        			sensorName = "Sensor" + (2);
        			sum =	sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat2.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" +(sens.get("Sensor1").get(dat2.size()-1 +"\n")));
        			
    			case 2: 
        			sensorName = "Sensor" + (3);        			
        			sum =sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat3.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat3.size()-1)+"\n"));
        			
    			case 3: 
        			sensorName = "Sensor" + (4);                   
        			sum =sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat4.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat4.size()-1)+"\n"));
    			case 4: 
        			sensorName = "Sensor" + (5);       			
        			sum =sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat5.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor5").get(dat5.size()-1)+"\n"));
        					
    			}
    			        
    		}
    		switch (i) {
    			case 0:    
    				sensorName = "Sensor" + (1);        			
    				sum = sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat1.size()-1).toString())));
        			        			    	
    			case 1: 
        			sensorName = "Sensor" + (2);        			
        			sum =	sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat2.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" +(sens.get("Sensor1").get(dat2.size()-1 +"\n")));
        			
    			case 2: 
        			sensorName = "Sensor" + (3);
        			
        			sum =sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat3.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat3.size()-1)+"\n"));
        			
    			case 3: 
        			sensorName = "Sensor" + (4);                    
        			sum =sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat4.size()-1).toString())));
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor1").get(dat4.size()-1)+"\n"));
        			
    			case 4: 
        			sensorName = "Sensor" + (5);        			
        			sum =sum.add(new BigDecimal(Integer.parseInt(sens.get(sensorName).get(dat5.size()-1).toString())));
        			//sum= sum.divide(new BigDecimal(dat5.size()),2);
        			//out.print("sum is " + sensorName + "::" + (sens.get("Sensor5").get(dat5.size()-1)+"\n"));        		       			    		
    		}
    			
    	}
    		out.print("sum of sensor " + (i+1) + " is ::  " + sum + "\n");
    		mean = sum.divide(new BigDecimal(5),2);
    		
			sum = new BigDecimal(0);
			
			
    	}    			
	}
    
	private void genGapEvent(int is) {
		
	}
	private static void mapDataFile(HashMap<String, HashMap<Integer, String>> sens2) {
		
		String fromfileData = lastNlines(fileSensor,10);
		String[] tokens=fromfileData.split("[,\r \n]");  
        int len = tokens.length;
       
        for(int i=0;i <len; i++ )
        {
        	
        	if(tokens[i].contains("sensorId:Sensor1"))
        	{
        		dat1.put(dat1.size(),tokens[i+6]);
        	
        	}else if(tokens[i].contains("sensorId:Sensor2"))
        	{
        		dat2.put( dat2.size(),tokens[i+6]);;

        	}
        	else if(tokens[i].contains("sensorId:Sensor3"))
        	{
        		dat3.put( dat3.size(),tokens[i+6]);;

        	}
        	else if(tokens[i].contains("sensorId:Sensor4"))
        	{
        		dat4.put(dat4.size(),tokens[i+6]);
        	
        	}else if(tokens[i].contains("sensorId:Sensor5"))
        	{
        		dat5.put( dat5.size(),tokens[i+6]);;

        	}
        	
        }
        sens.put("Sensor1",dat1);
        sens.put("Sensor2",dat2);
        sens.put("Sensor3",dat3);
        sens.put("Sensor4",dat4);
        sens.put("Sensor5",dat5);
        
        
		
	}

	public static void main(String[] args) {
		SensorDataSet sensorStart = new SensorDataSet (1);
	     sensorStart.start ();

	     // Create DataGetter and tell it to obtain
	     // 100 sensor readings for first sensor.
	     SensorDataGet sensorDataget = new SensorDataGet (10, sensorStart);
	     sensorDataget.start ();
	     
	     SensorDataSet sensorStart2 = new SensorDataSet (2);
	     sensorStart2.start ();

	     // Create DataGetter and tell it to obtain
	     // 100 sensor readings for second sensor.
	     SensorDataGet sensorDataget2 = new SensorDataGet (10, sensorStart2);
	     sensorDataget2.start ();
	     SensorDataSet sensorStart3 = new SensorDataSet (3);
	     sensorStart3.start ();
	     
	     

	     // Create DataGetter and tell it to obtain
	     // 100 sensor readings for 3rd sensor.
	     SensorDataGet sensorDataget3 = new SensorDataGet (10, sensorStart3);
	     sensorDataget3.start ();
	     
	     SensorDataSet sensorStart4 = new SensorDataSet (4);
	     sensorStart4.start ();
	     
	     SensorDataGet sensorDataget4 = new SensorDataGet (10, sensorStart4);
	     sensorDataget4.start ();
	     
	     SensorDataSet sensorStart5 = new SensorDataSet (5);
	     sensorStart5.start ();
	     
	     SensorDataGet sensorDataget5 = new SensorDataGet (10, sensorStart5);
	     sensorDataget5.start ();
	     
	     defTopology(sens);
	        out.println("\n");
	     	out.println ( "Data for Sensor1" + sens.get("Sensor1").toString());
	        out.println ( "Data from Sensor2" + sens.get("Sensor2").toString());
	        out.println ( "Data from Sensor3" + sens.get("Sensor3").toString());
	        out.println ( "Data from Sensor4" + sens.get("Sensor4").toString());
	        out.println ( "Data from Sensor5" + sens.get("Sensor5").toString());
	}
	public static String lastNlines( File file, int lines) {
	    java.io.RandomAccessFile fileHandler = null;
	    try {
	        fileHandler = 
	        new java.io.RandomAccessFile( file, "r" );
	        long fileLength = file.length() - 1;
	        StringBuilder sb = new StringBuilder();
	        int line = 0;

	        for(long filePointer = fileLength; filePointer != -1; filePointer--){
	            fileHandler.seek( filePointer );
	            int readByte = fileHandler.readByte();

	            if( readByte == 0xA ) {
	                if (line == lines) {
	                    if (filePointer == fileLength) {
	                        continue;
	                    } else {
	                        break;
	                    }
	                }
	            } else if( readByte == 0xD ) {
	                line = line + 1;
	                if (line == lines) {
	                    if (filePointer == fileLength - 1) {
	                        continue;
	                    } else {
	                        break;
	                    }
	                }
	            }
	           sb.append( ( char ) readByte );
	        }

	        sb.deleteCharAt(sb.length()-1);
	        String lastLine = sb.reverse().toString();
	        String delims = "[ ]+";
	        String[] tokens=lastLine.split("[,]");  
	        int len = tokens.length;
	        // Stop both threads if data taking finished.
	        //out.println ( "from file" + tokens.length + tokens[len-1] + tokens[len-2] + tokens[len-3] + tokens[len-4]  );
	        //out.println ( "from file" +  tokens[len-1]   );
	        return lastLine;
	    } catch( java.io.FileNotFoundException e ) {
	        e.printStackTrace();
	        return null;
	    } catch( java.io.IOException e ) {
	        e.printStackTrace();
	        return null;
	    }
	     finally {
	        if (fileHandler != null )
	            try {
	                fileHandler.close();
	            } catch (IOException e) {
	                /* ignore */
	            }
	    }
	}

}

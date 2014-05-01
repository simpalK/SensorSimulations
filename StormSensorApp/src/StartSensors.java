import static java.lang.System.out;

import java.lang.Number;
import java.math.BigDecimal;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
public class StartSensors {
    
    
    static File fileSensor;
	/* for testing 1600 nodes by generating sensors values in 4 different files.
	 * static File file1 = new File("logDataSensor1.txt");
	static File file2 = new File("logDataSensor2.txt");
	static File file3 = new File("logDataSensor3.txt");
	static File file4 = new File("logDataSensor4.txt");*/


	public static void main(String[] args) {
		try {
			Global.numberOfNodes = (Integer.parseInt(args[1]));
			Global.filesPath = args[0];
            FileReader fr = new FileReader((args.length > 0) ? Global.filesPath + "/nodes_test16.csv" : "nodes_test.csv");
            String[] sensorsIds = parseCsv(fr, ",", true);
            fileSensor = new File(Global.filesPath + "/logSensorsData.txt");
		//String[] sensorsIds = {"N-H563T",	"N-QWNZH",	"N-LETTK",	"N-SCK04",	"N-8HOVD",	"N-2GWON",	"N-UFCUA",	"N-6PFYW",	"N-TZD20",	"N-WRYAZ",	"N-3IK0Y",	"N-JQ338",	"N-Y47X6",	"N-2Z2WK",	"N-GRDHN",	"N-L04BJ"};
        
		// Start sensors thread to write random values
		for(int i=0; i<Global.numberOfNodes;i++){
		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], fileSensor);
	     sensorStart.start ();
	     }
		//for testing 1600 nodes by writing sensor values in 4 separate files
        /*for(int i=400; i<800;i++){
    		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], file2);
    	     sensorStart.start ();
    	     }
        for(int i=800; i<1200;i++){
    		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], file3);
    	     sensorStart.start ();
    	     }
        for(int i=1200; i<1600; i++){
    		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], file4);
    	     sensorStart.start ();
        }*/
	    // SensorInfoJson sensorJsonInfo = new SensorInfoJson();
	     //sensorJsonInfo.start ();
         while(true){
        	 SensorInfoJson sensorJsonInfo = new SensorInfoJson(sensorsIds);
    	     sensorJsonInfo.start ();
         try {
			TimeUnit.SECONDS.sleep(1);
			sensorJsonInfo.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  }  
		} catch (IOException e) {
           e.printStackTrace();
       }
	     
	}
		
		
		public static String[] parseCsv(Reader reader, String separator, boolean hasHeader) throws IOException {
	        //Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();
	        List<String> columnNames = new LinkedList<String>();
	        String[] nodesInfo = new String[Global.numberOfNodes];
	        int nodeCount=0;
	        BufferedReader br = null;
	        br = new BufferedReader(reader);
	        String line;
	        int numLines = 0;
	        while ((line = br.readLine()) != null) {
	                if (!line.startsWith("#")) {
	                    String[] tokens = line.split(separator);
	                    if (tokens != null) {
	                            if (numLines == 0) {
	                                for (int i = 0; i < tokens.length; ++i) {

	                                columnNames.add(hasHeader ? tokens[i] : ("row_"+i));
	                                }
	                            } else {
	                               nodesInfo[nodeCount++]=  tokens[0];                            
	                            }
	                        
	                    }
	                    ++numLines;                
	            }
	        }
	        return nodesInfo;
	    }	
	/*public static String lastNlines( File file, int lines) {
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

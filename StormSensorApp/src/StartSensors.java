import static java.lang.System.out;

import java.lang.Number;
import java.math.BigDecimal;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.TimeUnit;
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
	static File file1 = new File("logDataSensor1.txt");
	static File file2 = new File("logDataSensor2.txt");
	static File file3 = new File("logDataSensor3.txt");
	static File file4 = new File("logDataSensor4.txt");


	public static void main(String[] args) {
		String[] sensorsIds = {"N-H563T",	"N-QWNZH",	"N-LETTK",	"N-SCK04",	"N-8HOVD",	"N-2GWON",	"N-UFCUA",	"N-6PFYW",	"N-TZD20",	"N-WRYAZ",	"N-3IK0Y",	"N-JQ338",	"N-Y47X6",	"N-2Z2WK",	"N-GRDHN",	"N-L04BJ"};
        for(int i=0; i<4;i++){
		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], file1);
	     sensorStart.start ();
	     }
        for(int i=4; i<8;i++){
    		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], file2);
    	     sensorStart.start ();
    	     }
        for(int i=8; i<12;i++){
    		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], file3);
    	     sensorStart.start ();
    	     }
        for(int i=12; i<16;i++){
    		SensorDataSet sensorStart = new SensorDataSet (sensorsIds[i], file4);
    	     sensorStart.start ();
    	     }
	     	     
	    // SensorInfoJson sensorJsonInfo = new SensorInfoJson();
	     //sensorJsonInfo.start ();
         while(true){
        	 SensorInfoJson sensorJsonInfo = new SensorInfoJson();
    	     sensorJsonInfo.start ();
         try {
			TimeUnit.SECONDS.sleep(100);
			sensorJsonInfo.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         }
	     
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

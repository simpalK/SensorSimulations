import static java.lang.System.out;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.*;
public class StartSensors {
    static List<List<String>> sens = new ArrayList<>();
    
    //topology definition: connection between sensors
    int[][] topo = new int[][]{
    		{1 ,0, 0, 1, 0},
    		{0, 1, 1, 0, 0},
    		{0, 1, 1, 0, 0},
    		{1, 0, 0, 1, 1},
    		{0, 0, 0, 1, 1}
    		};
    
    static File fileSensor = new File("logData.txt");
    public static void defTopology(List<List<String>> sen)
    {
    for(int i=0; i<5; i++)
    {
    	mapDataFile(sen);
    	//genGapEvent(topo[i][i]);
    	//computeLisa(topo[i]);
    		
    	
    	
    }
    
    }
    private void computeLisa(int[] is) {
		// TODO Auto-generated method stub
		
	}
	private void genGapEvent(int is) {
		
	}
	private static void mapDataFile(List<List<String>> sen) {
		
		String fromfileData = lastNlines(fileSensor,20);
		String[] tokens=fromfileData.split("[,]");  
        int len = tokens.length;
        // Stop both threads if data taking finished.
        out.println ( "from file" + tokens.length + tokens[len-1] + tokens[len-2] + tokens[len-3] + tokens[len-4]  );
        
		
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
	     defTopology(sens);
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
	        out.println ( "from file" + tokens.length + tokens[len-1] + tokens[len-2] + tokens[len-3] + tokens[len-4]  );
	        
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

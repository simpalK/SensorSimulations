import java.io.File;
import java.io.IOException;
import java.util.*;

import static java.lang.System.out;

public class SensorDataGet extends Thread{
	
SensorDataSet sensor = null;
int maxInData;
int dataCount;
File fileSensor = new File("logData.txt");
SensorDataGet(int maxLimit,SensorDataSet sensor) {
	maxInData = maxLimit;
	this.sensor = sensor;
	dataCount = 0;
}

public void run () {
    Random r = new Random ();
    if (dataCount <= maxInData) {
    	String fromfileData = lastNlines(fileSensor,10);
        String data_value = sensor.get ();
       //out.println ( "from file" + fromfileData);
        String delims = "[ ]+";
        String[] tokens=fromfileData.split(",");  
        // Stop both threads if data taking finished.
        //out.println ( "from file" + tokens[0].toString());
        sensor.stopReadingData ();
        dataCount++;
      
      try{
        sleep (r.nextInt () % 300);
      }
      catch (Exception e){}
    }
  } // run

public String lastNlines( File file, int lines) {
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


import java.io.*;
class ReadFileUsingThread
{
    public void readFromFile(final String f, Thread thread) {
    Runnable readRun = new Runnable() {
      public void run() {
        FileInputStream in=null;
        String text = null;
        try{
          Thread.sleep(5000);
          File inputFile = new File(f);
          in = new FileInputStream(inputFile);
          byte bt[] =  new byte[(int)inputFile.length()];
          in.read(bt);
          text = new String(bt);
          System.out.println(text);
       } catch(Exception ex) {
       }  
      }
    };
    thread = new Thread(readRun);
    thread.start();
  }

    public static void main(String[] args) 
    {
        ReadFileUsingThread files=new ReadFileUsingThread();
        Thread thread1=null,thread2=null,thread3=null;
        String f1="logData.txt",f2="logData.txt",f3="logData.txt";
        files.readFromFile(f1,thread1);
        files.readFromFile(f2,thread2);
        files.readFromFile(f3,thread3);
    }
}
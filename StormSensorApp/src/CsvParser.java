

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


public class CsvParser {
	static int edgesRows =0;
	static int[][] tempTopo = new int[1600][1600];

    public static void main(String[] args) {
        try {
            FileReader fr = new FileReader(Global.filesPath + "nodes_test.csv");
            String[] nodesInfo = parseCsv(fr, ",", true);
            FileReader fr1 = new FileReader(Global.filesPath + "edges_test.csv");
            String[] edgesInfo = parseCsvEdges(fr1, "\\t", true);
            File file = new File(Global.filesPath + "tempTopoFile.txt");
			Global.numberOfNodes = (Integer.parseInt(args[1]));

            for(int i=0;i<Global.numberOfNodes;i++){
            	for(int j=0; j<Global.numberOfNodes; j++)
            		if(i==j)
            			tempTopo[i][j] = 1;
            		else
            			tempTopo[i][j] = 0;
            }
            findMapTopo(nodesInfo,edgesInfo);
            
            FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			
            for(int i=0;i<Global.numberOfNodes;i++){
            	String value ="";
            	for(int j=0; j<Global.numberOfNodes; j++){
            		value += tempTopo[i][j] + ",";
                    System.out.print(tempTopo[i][j] + ",");
                    }
            	bw.write(value);
    			bw.newLine();
            	System.out.println("\n");
            }
			bw.close();

            //Map<String, List<String>> values = parseCsv(fr, ",", true);
            for(int i =0;i<Global.numberOfNodes;i++)
            System.out.println(nodesInfo[i]);
            
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static String[] parseCsvEdges(Reader reader, String separator, boolean hasHeader) throws IOException {
        //Map<String, List<String>> values = new LinkedHashMap<String, List<String>>();
        List<String> columnNames = new LinkedList<String>();
        String[] nodesInfo = new String[Global.numberOfNodes * 10];
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
                               nodesInfo[nodeCount++]=  tokens[0].trim() + ":" + tokens[1].trim() + ":" + tokens[2].trim();                            
                            }
                        
                    }
                    ++numLines;                
            }
        }
        edgesRows = nodeCount-1;
        return nodesInfo;
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
    
    public static void findMapTopo(String[] nodesInfo, String[] edgesInfo) {
    	for(int i=0;i<Global.numberOfNodes;i++)
    	{
    		String insertNode = nodesInfo[i];
    		for(int j =0; j<edgesRows; j++)
    		{
    			String[] tokensEdge = edgesInfo[j].split(":");    			
    			if(insertNode.contentEquals(tokensEdge[1].trim())){
    				insertPositionNodeNeighbor(i,tokensEdge[2].trim(),nodesInfo);
    			}
    		}    		
    	}
    	
		
    }
	private static void insertPositionNodeNeighbor(int node, String neighbor, String[] nodes) {
		for(int i=0; i<Global.numberOfNodes; i++)
			if(nodes[i].contentEquals(neighbor)){
				tempTopo[node][i] = 1;
				tempTopo[i][node] = 1;
				break;
			}
		// TODO Auto-generated method stub
		
	}
	
}
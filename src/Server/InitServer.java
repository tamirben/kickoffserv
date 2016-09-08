package Server;

import java.net.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.*;

public class InitServer extends Thread{
	
	private ServerSocket my_server;
	Map<Integer,String> textArray=new HashMap();
	public InitServer(int port) throws IOException{
			
		my_server = new ServerSocket(port);
		//my_server.setSoTimeout(10000);
	}
		
	public void run(){
			
		while(true){
				
				try{
					System.out.println("Waiting for client on port " +
					my_server.getLocalPort() + "...");
					
					Socket server = my_server.accept();
					
					System.out.println("Just connected to "
			             + server.getRemoteSocketAddress());
					
					
			    	
					
					DataInputStream in = new DataInputStream(server.getInputStream());
					
					
			    	int i=textArray.size();
			    	textArray.put(i, in.readUTF());	
			    	chack(i);
			    	i++;
			
			    	
					DataOutputStream out =
			         new DataOutputStream(server.getOutputStream());
					
					out.writeUTF("Thank you for connecting to "
				              + server.getLocalSocketAddress() + "\nGoodbye!");
					server.close();
					
					
					
					
					
				}catch(SocketTimeoutException s)
		         {
		            System.out.println("Socket timed out!");
		            break;
		         }catch(IOException e)
		         {
		            e.printStackTrace();
		            break;
		         }
			}
		}
	
	public void chack(int pos) throws IOException{
		
		ArrayList<String> badWords = new ArrayList<>();
   
		for (String line : Files.readAllLines(Paths.get(".","BadWords.txt"))) {
			
			badWords.add(line);
			
		}

		for(String word : badWords){
			
			List<Integer> matches = Algo.match(word, textArray.get(pos)); 
			 
			if(!matches.isEmpty()){
		        	FileWriter fw = new FileWriter("TextCategorization.txt", true);
		        	fw.write("\r\n"+textArray.get(pos));
		        	
		        	
		        	fw.close();
		       }
			
		}
		 
        
       
		
	
		
		
	}
		
	public static void main(String [] args) throws IOException
	   {
		
    	
		int port = Integer.parseInt("8005");
	      try
	      {
	         Thread t = new InitServer(port);
	         t.start();
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	      }
	      
       
	      
	   }
		 
	}



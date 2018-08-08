package javaNetworkingPattern;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
public class GreetingServer extends Thread {
   private ServerSocket serverSocket;
   private String returnMessage;
   public GreetingServer(int port,String message) throws IOException {
      serverSocket = new ServerSocket(port);
      serverSocket.setSoTimeout(100000);
      this.returnMessage = message;
   }

   public void run() {
      while(true) {
         try {
            System.out.println("Waiting for client on port " + 
               serverSocket.getLocalPort() + "...");
            Socket server = serverSocket.accept();
            
            System.out.println("Just connected to " + server.getRemoteSocketAddress());
            DataInputStream in = new DataInputStream(server.getInputStream());
            
            System.out.println(in.readUTF());
            DataOutputStream out = new DataOutputStream(server.getOutputStream());
            out.writeUTF(returnMessage + " " + server.getLocalSocketAddress()
               + "\nGoodbye!");
            server.close();
            
         }catch(SocketTimeoutException s) {
            System.out.println("Socket timed out!");
            break;
         }catch(IOException e) {
            e.printStackTrace();
            break;
         }
      }
   }
   
   public static void main(String [] args) {
      int port = Integer.parseInt(args[0]);
      String returnMessage = args[1];
      try {
         Thread t = new GreetingServer(port,returnMessage);
         t.start();
      }catch(IOException e) {
         e.printStackTrace();
      }
   }
}


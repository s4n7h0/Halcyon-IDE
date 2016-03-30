/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package halcyon.ide;

/**
 *
 * @author s4n7h0
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.List;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

/**
 *
 * @author SANOOP
 */
public class CommandExecutionHandler {
    public static ProcessBuilder pb;
    public static Process process;
    
    public StringBuilder outputBuffer = null;
    
    public List<String> commandInformation;
    public String adminPassword;
    public ThreadHandler inputStreamHandler;
    public ThreadHandler errorStreamHandler;
    
    public CommandExecutionHandler(){
        
    }
    public CommandExecutionHandler(final List<String> commandInformation){
        if (commandInformation==null) throw new NullPointerException("The commandInformation is required.");
        this.commandInformation = commandInformation;
        this.adminPassword = null;
    }
    
    public void killProcess(){
        process.destroy();
    }

    public synchronized StringBuilder executeCommand()throws IOException, InterruptedException{
         
         Thread t = new Thread(){
          @Override
          public void run(){
              try{
                  pb = new ProcessBuilder(commandInformation);
                  process = pb.start();
                  
                  OutputStream stdOutput = process.getOutputStream();
                  
                  InputStream inputStream = process.getInputStream();
                  InputStream errorStream = process.getErrorStream();
                  outputBuffer = new StringBuilder();
                  
                  //extraction of process 
                  BufferedReader br = null;
                  br = new BufferedReader(new InputStreamReader(inputStream));
                  String line = null;
                  
                  while((line = br.readLine()) != null){ 
                      outputBuffer.append(line + "\n");
                  }
                  
                  br = new BufferedReader(new InputStreamReader(errorStream));
                  
                  while((line = br.readLine()) != null){ 
                      outputBuffer.append(line + "\n");
                  }
                  
              }catch(Exception e){
                  
              }
          }
      };
      t.start();
      t.join();
      return outputBuffer;
  }
  
}
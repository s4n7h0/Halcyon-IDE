/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package halcyon.ide;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader; 
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer; 
import java.util.Properties; 
import java.util.logging.Level;
import java.util.logging.Logger; 

/**
 *
 * @author s4n7h0
 */
public class ConfigurationManager {
    
    String os, home, appfolder, filename, configfile; 
    String scan_type, script_arg, script_argfile;  
    String pkt_trace, debug, verbose;
    String nmap_path, script_path, lib_path;  
    Properties prop; 
        
    public ConfigurationManager() {
        prop = new Properties(); 
        os = System.getProperty("os.name"); 
        home = System.getProperty("user.home");
        appfolder = "HalcyonIDE";
        filename = "HalcyonIDE.ini";  
        
    }
    
    public void init(){
        scan_type = "tcp";
        script_arg = "";
        script_argfile = "";
        pkt_trace = "False";
        debug = "True";
        verbose = "False"; 
        nmap_path = "";
        script_path = "";
        lib_path = ""; 
        configfile = getConfigFilePath();
        SetNmapEnv();
        if(configfile.equals(null) && configfile.isEmpty()){ 
            CreateConfiguration();
        }else{
            LoadConfiguration();
        } 
    }
    
    
    
    public String validateConfigPath(String base, String appdir, String file){
        String absolutepath = null;
        File f = new File(base+File.separator+appdir+File.separator+file);
        File af = new File(base+File.separator+appdir); 
        try {
            if(af.exists()){ 
                if (f.exists()) { 
                    absolutepath = f.getAbsolutePath(); 
                    
                }else{ 
                    f.createNewFile(); 
                    absolutepath = f.getAbsolutePath();
                } 
            }else{ 
                af.mkdir(); 
                f.createNewFile(); 
                CreateConfiguration();
                absolutepath = f.getAbsolutePath();
            }
        } catch (Exception e) {
            System.out.println("Exception in validateConfigpath");
            e.printStackTrace();
        }
        
        return absolutepath;
    }
    
    public String getConfigFilePath(){
        String configfilepath = "";
        if(os.contains("Mac OS X")){ 
            configfilepath = validateConfigPath(System.getProperty("user.home"), appfolder, filename);
        }else if(os.contains("Windows")){
            configfilepath = validateConfigPath(System.getenv("APPDATA"), appfolder, filename);  
        }else if(os.contains("Linux")){ 
            configfilepath = validateConfigPath(System.getProperty("user.home"), appfolder, filename);
        }  
        return configfilepath;
        
    } 
    
    public void LoadConfiguration(){  
        try{
            if(configfile != null){
                InputStream in = new FileInputStream(configfile);
                prop.load(in);
                if(prop.getProperty("version") == null){
                    CreateConfiguration();
                    LoadConfiguration();
                }else{
                    scan_type = prop.getProperty("scan_type"); 
                    script_arg = prop.getProperty("script_arg");
                    script_argfile = prop.getProperty("script_argfile");
                    pkt_trace = prop.getProperty("pkt_trace");
                    debug = prop.getProperty("debug");
                    verbose = prop.getProperty("verbose"); 
                    nmap_path = prop.getProperty("nmap_path");
                    script_path = prop.getProperty("script_path");
                    lib_path = prop.getProperty("lib_path");
                } 
            }else{ 
                CreateConfiguration(); 
                LoadConfiguration(); 
            } 
        }catch(Exception e){ 
            e.printStackTrace();
        }
        
    }
    
    public void CreateConfiguration(){ 
        try{
            if(configfile != null){
                OutputStream out = new FileOutputStream(configfile);
                prop.setProperty("version", "2.0.2");
                prop.setProperty("scan_type", scan_type);
                prop.setProperty("script_arg", script_arg);
                prop.setProperty("script_argfile", script_argfile);
                prop.setProperty("pkt_trace", pkt_trace);
                prop.setProperty("debug", debug);
                prop.setProperty("verbose", verbose); 
                prop.setProperty("nmap_path", nmap_path);
                prop.setProperty("script_path", script_path); 
                prop.setProperty("lib_path", lib_path); 
                prop.store(out, "Halcyon IDE - https://halcyon-ide.org");
                
            }
             
        }catch(Exception e){ 
            e.printStackTrace();
        }
                    
    }
 
    
    public void UpdateConfiguration(String key, Object value){ 
         
    }
    
    public void SaveConfiguration(String type, String arg, String argfile, String pt, String d, String v, String nmap, String nscript, String nlib){ 
        try{
            configfile = getConfigFilePath();
            if(configfile != null){
                OutputStream out = new FileOutputStream(configfile);
                prop.setProperty("version", "2.0.2");
                prop.setProperty("scan_type", type); 
                prop.setProperty("script_arg", arg);
                prop.setProperty("script_argfile", argfile);
                prop.setProperty("pkt_trace", pt);
                prop.setProperty("debug", d);
                prop.setProperty("verbose", v); 
                prop.setProperty("nmap_path", nmap);
                prop.setProperty("script_path", nscript); 
                prop.setProperty("lib_path", nlib); 
                prop.store(out, "Halcyon IDE - https://halcyon-ide.org");
                
            }else{ 
                System.out.println("Error in saving configuration");
            }
             
        }catch(Exception e){ 
            e.printStackTrace();
        }
        
    }
    
    public void SetNmapEnv(){
        String npath ="", nse="", lib = "";
        boolean flag = false;
        try{  
            //detecting possible class paths depends on OS. 
            String os = System.getProperty("os.name");
            if(os.contains("Mac OS X")){
                // identifying common linux paths
                npath = "/usr/local/bin/nmap";
                nse = "/usr/local/share/nmap/scripts/";
                lib = "/usr/local/share/nmap/nselib/";
                flag = true;
            }else if(os.contains("Windows")){
                String p=null;
                // searching environment variables
                String path = System.getenv("PATH");
                String[] paths = path.split(";"); 
                for (String path1 : paths) {
                    if (path1.contains("Nmap") || path1.contains("nmap")) { 
                        //found class paths from environment variables.  
                        npath = path1+"\\nmap";
                        nse = path1+"\\scripts\\";
                        lib = path1+"\\nselib\\";
                        flag = true;
                    }
                }
                File f = new File(System.getenv("ProgramFiles")+"\\Nmap");
                if((new File(System.getenv("ProgramFiles")+"\\Nmap")).exists()){
                    p = System.getenv("ProgramFiles");
                }else if((new File(System.getenv("ProgramFiles(X86)")+"\\Nmap")).exists()){
                    p = System.getenv("ProgramFiles(X86)");
                }
                if(!p.isEmpty()){
                    npath = p+"\\Nmap\\nmap.exe";
                    nse = p+"\\Nmap\\scripts\\";
                    lib = p+"\\Nmap\\nselib\\";
                    flag = true;
                }  
            }else if(os.contains("Linux")){
                npath = "/usr/bin/nmap";
                nse = "/usr/share/nmap/scripts/";
                lib = "/usr/share/nmap/nselib/";
                flag = true;
            } 
            
            if(flag){
                File isNPATH = new File(npath);
                File isNSE = new File(nse);
                File isLIB = new File(lib);
                if(isNPATH.exists() && isNSE.exists() && isLIB.exists()){
                    nmap_path = npath;
                    script_path = nse;
                    lib_path = lib;  
                }else{
                   //JOptionPane.showConfirmDialog(jFrame_Configure,"Autoconfiguration failed because one or more files are not found or invalid. Please configure manually by clicking corresponding edit buttons.", "Warning", JOptionPane.PLAIN_MESSAGE);  
                } 
            }else{
                //JOptionPane.showConfirmDialog(jFrame_Configure,"Autoconfiguration failed. Please configure manually by clicking corresponding edit buttons.", "Warning", JOptionPane.PLAIN_MESSAGE);  
            }
         
        }catch(NullPointerException e){
            String error = "<html>Unable to find the nmap location.<br>Please configure it manually.</html>";
            //int done = JOptionPane.showConfirmDialog(jFrame_Configure,error, "Error !!",JOptionPane.OK_OPTION);
        }
    }
     
    
     
    
}



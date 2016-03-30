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
public class ScriptBuilder {
    String scriptName, scriptDesc, scriptAuthor, scriptService, scriptCateg;
    String port, protocol, service = null;
    
    public void setTemplate(String name, String desc, String author, String service, Object[] categ){
        scriptName = name;
        scriptDesc = desc;
        scriptAuthor = author;
        
        String t = "{";
        
       int i = 0;
            for(Object c: categ){
                t = t + "\""+c+"\"";
                 if(i<(categ.length-1)) t = t+", ";
                 i++;
            } 
        
            
        
        t = t+"}";
        scriptCateg = t;

        scriptService = service;
        
        setServiceVars(scriptService);
         
        
    }
    
    public void setServiceVars(String s){
        // 25/tcp SMTP
        if(s.contains("/")){
        String[] split1 = s.split(" ");
        String[] split2 = split1[0].split("/");
        
        port = split2[0];
        protocol = split2[1];
        service =split1[1]; 
        }
        
    }
    
    public StringBuilder createTemplate(){
       StringBuilder template = new StringBuilder("local shortport = require \"shortport\"");
       template.append("\r\n\r\n");
       template.append("description = [[");
       template.append(scriptDesc);
       template.append("]]");
       template.append("\r\n\r\n");
       template.append("author = \""+scriptAuthor+"\"\r\n");
       template.append("license = \"Same as Nmap--See http://nmap.org/book/man-legal.html\"");
       
       template.append("\r\n");
       template.append("categories = "+scriptCateg+"\r\n");
       template.append("\r\n\r\n");
       template.append(getPortrule());
       template.append("\r\n\r\n");
       template.append("action = function(host, port)");
       template.append("\r\n\r\n");
       template.append("\r\n\r\n");
       template.append("end");
       template.append("\r\n\r\n");
       
       return template;
    }
    
    public String getPortrule(){
        String rule = null;
        if(scriptService.equals("default")){
            rule = "portrule = function(host, port)" ;
        }else{
            if(port.equals("80") || service.equals("HTTP")){
                rule = "portrule = shortport.http";
            }else if(port.equals("21") || service.equals("FTP")){
                rule = "portrule = shortport.service(\"ftp\")";
            }else{
                rule = "portrule = shortport.port_or_service("+port+", '"+service.toLowerCase()+"', '"+protocol+"')";
            }
        }
        
        return rule;
    }
}

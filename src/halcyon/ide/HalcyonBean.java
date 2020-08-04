/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package halcyon.ide;

import java.beans.*;
import java.io.File;
import java.io.Serializable;

/**
 *
 * @author s4n7h0
 */
public class HalcyonBean implements Serializable{
    
    public static final String PROP_SAMPLE_PROPERTY = "ScanProperty";
    
    private String scriptPath, scriptName, scriptArgs, scriptArgsFile, scanType = "TCP Scan";
    File f;
    boolean isArgs = false;
    boolean isArgsFile = false;
    boolean isVerbose = false;
    boolean isDebug = true;
    boolean isPtrace = false;
    boolean isOption = false;
    String nmap, nscript, nlib = "";
    
    private PropertyChangeSupport propertySupport;
    
    public HalcyonBean() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public void setNmap(String arg){
        nmap = arg;
    }
    
    public String getNmap(){
        return nmap;
    }
    
    public void setNscript(String arg){
        nscript = arg;
    }
    
    public String getNscript(){
        return nscript;
    }
    
    public void setNlib(String arg){
        nlib = arg;
    }
    
    public String getNlib(){
        return nlib;
    }
    
    public void setScriptArgs(String arg, boolean val){
        isArgs = val;
        scriptArgs = arg;
    }
    
    public void setScriptArgsFile(String arg, boolean val){
        isArgsFile = val;
        scriptArgsFile = arg;
    } 
    
    public boolean isScriptArg(){
        return isArgs;
    }
    
    public String getScriptArgs(){
        return scriptArgs;
    }
    
    public String getScriptArgsFile(){
        return scriptArgsFile;
    }
    
    public boolean isScriptArgsFile(){
        return isArgsFile;
    }
    
    public void setVerbose(boolean val){
        isVerbose = val;
    }
    
    public boolean getVerbose(){
        return isVerbose;
    }
    
    public void setDebug(boolean val){
        isDebug = val;
    }
    
    public boolean getDebug(){
        return isDebug;
    }
    
    public void setPtrace(boolean val){
        isPtrace = val;
    }
    
    public boolean getPtrace(){
        return isPtrace;
    } 
    
    public void setScanType(String arg){ 
        scanType = arg; 
    }
    
    public String getScanType(){
        return scanType;
    }
    
    public void setIsOptions(boolean flag){
        isOption = flag;
    }
    
    public boolean getIsOptions(){
        return isOption;
    }
    
    public void setFile(File file){
        f = file;
    }
    
    public File getFile(){
        return f;
    }
    
    public String getPath() {
        return scriptPath;
    }
    
    public String getName(){
        return scriptName;
    }
    
    public void setName(String value) {
        String oldValue = scriptName;
        scriptName = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, scriptName);
    }
    
    public void setPath(String value) {
        String oldValue = scriptPath;
        scriptPath = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, scriptPath);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
    public String getafpHelper(){
        return "helper = afp.Helper:new()\n" +
            "status, response = helper:OpenSession( host, port )\n" +
            "status, response = helper:Login()\n" +
            "-- do some fancy AFP stuff --\n" +
            "status, response = helper:Logout()\n" +
            "status, response = helper:CloseSession()";
    }
    
    public String getBittorrent(){
        return "local filename = \"/home/user/name.torrent\"\n" +
            " local torrent = bittorrent.Torrent:new()\n" +
            " torrent:load_from_file(filename)\n" +
            " torrent:trackers_peers() -- to load peers from the trackers\n" +
            " torrent:dht_peers() -- to further load peers using the DHT protocol from existing peers";
        
    }
    
    public String getBruteDriver(){
        return "Driver = {\n" +
            "  new = function(self, host, port, options)\n" +
            "    local o = {}\n" +
            "    setmetatable(o, self)\n" +
            "    self.__index = self\n" +
            "    o.host = host\n" +
            "    o.port = port\n" +
            "    o.options = options\n" +
            "    return o\n" +
            "  end,\n" +
            "  connect = function( self )\n" +
            "    self.socket = nmap.new_socket()\n" +
            "    return self.socket:connect( self.host, self.port )\n" +
            "  end,\n" +
            "  disconnect = function( self )\n" +
            "    return self.socket:close()\n" +
            "  end,\n" +
            "  check = function( self )\n" +
            "    return true\n" +
            "  end,\n" +
            "  login = function( self, username, password )\n" +
            "    local status, err, data\n" +
            "    status, err = self.socket:send( username .. \":\" .. password)\n" +
            "    status, data = self.socket:receive_bytes(1)\n" +
            "\n" +
            "    if ( data:match(\"SUCCESS\") ) then\n" +
            "      return true, brute.Account:new(username, password, \"OPEN\")\n" +
            "    end\n" +
            "    return false, brute.Error:new( \"login failed\" )\n" +
            "  end,\n" +
            "}  ";
    }
    
    public String getBruteEngine(){
        
        return "local options = { key1 = val1, key2 = val2 }\n" +
            "  local status, accounts = brute.Engine:new(Driver, host, port, options):start()\n" +
            "  if( not(status) ) then\n" +
            "    return accounts\n" +
            "  end";
        
    }
    
    public String getCredtoDB(){
        
        return "local c = creds.Credentials:new( SCRIPT_NAME, host, port )\n" +
            "c:add(\"patrik\", \"secret\", creds.State.VALID )";
    } 
    
    public String getCredtoCSV(){
        
        return "local c = creds.Credentials:new( SCRIPT_NAME, host, port )\n" +
            "c:add(\"patrik\", \"secret\", creds.State.VALID )\n" +
            "status, err = c:saveToFile(\"outputname\",\"csv\")";
        
    }
     
    public String getDHCP6(){
        
        return "local helper = DHCP6.Helper:new(\"eth0\")\n" +
            "  local status, response = helper:solicit()\n" +
            "  if ( status ) then\n" +
            "     return stdnse.format_output(true, response)\n" +
            "  end";
        
    }
    
    public String getDNSQuery(){
        return "local status, result = dns.query('www.google.ca')";
    }
    
    public String getDNSbl(){
        return "local helper = dnsbl.Helper:new(\"SPAM\", \"short\")\n" +
            "helper:setFilter('dnsbl.inps.de')\n" +
            "local status, result = helper:checkBL(host.ip)";
    }
    
    public String getDNSsd(){
        return "local helper = dnssd.Helper:new( host, port )\n" +
            "  return stdnse.format_output(helper:queryServices())";
    }
    
    public String getDRDA(){
        return "db2helper = drda.Helper:new()\n" +
            "status, err = db2helper:connect(host, port)\n" +
            "status, res = db2helper:getServerInfo()\n" +
            "status, err = db2helper:close()";
    }
    
    public String getEAP(){
        
        return "pcap:pcap_open(iface.device, 512, true, \"ether proto 0x888e\")\n" +
            "\n" +
            "local _, _, l2_data, l3_data, _ = pcap:pcap_receive()\n" +
            "local packet = eap.parse(l2_data .. l3_data3)\n" +
            "if packet then\n" +
            "  if packet.eap.type == eap.eap_t.IDENTITY and  packet.eap.code == eap.code_t.REQUEST then\n" +
            "    eap.send_identity_response(iface, packet.eap.id, \"anonymous\")\n" +
            "  end\n" +
            "end";
    }
    
    public String getGOIP(){
        
        return "helper   = giop.Helper:new(host, port)\n" +
            " status, err = helper:Connect()\n" +
            " status, ctx = helper:GetNamingContext()\n" +
            " status, objs = helper:ListObjects(ctx)";
    }
    
    public String getHTTPSpider(){
        
        return "\tlocal url_list = {}\n"+
	"\tlocal crawler = httpspider.Crawler:new(host, port, '/', { scriptname = SCRIPT_NAME } )\n"+
	"\tcrawler:set_timeout(10000)\n"+
        "\twhile(true) do\n"+
            "\t\tstatus, r = crawler:crawl()\n"+
            "\t\t-- the crawler wont fails normally, if it does, it can be a number of reasons, \n"+
            "\t\tit's better to do an error handle.\n"+
            "\t\tif ( not(status) ) then\n"+
                "\t\t\tif ( r.err ) then\n"+
                    "\t\t\treturn stdnse.format_output(true, \"ERROR: %s\", r.reason)\n"+
                    "\t\t\telse\n"+
                    "\t\t\tbreak\n"+
                    "\t\t\tend\n"+

            "\t\tend\n"+
            "\t\t--collecting all urls crawled\n"+
            "\t\ttable.insert(url_list, tostring(r.url))\n"+
	"\tend\n";
        
        
    }
    
    
    
    public String getIMAP(){
        
        return "local helper = imap.Helper:new(host, port)\n" +
            "helper:connect()\n" +
            "helper:login(\"user\",\"password\",\"PLAIN\")\n" +
            "helper:close()";
    }
    
    public String getInformix(){
        
        return "helper   = informix.Helper:new( host, port, \"on_demo\" )\n" +
            " status, err = helper:Connect()\n" +
            " status, res = helper:Login(\"informix\", \"informix\")\n" +
            " status, err = helper:Close()";
    }
    
    public String getJDWP(){
        
        return "local status,socket = jdwp.connect(host,port)\n" +
            "if not status then\n" +
            "  stdnse.print_debug(\"error, %s\",socket)\n" +
            "end\n" +
            "local version_info\n" +
            "status, version_info = jdwp.getVersion(socket,0)";
    }
    
    public String getMSSQLquery(){
        
        return "local helper = mssql.Helper:new()\n" +
            "status, result = helper:Connect( host, port )\n" +
            "status, result = helper:Login( username, password, \"temdpb\", host.ip )\n" +
            "status, result = helper:Query( \"SELECT name FROM master..syslogins\" )\n" +
            "helper:Disconnect()";
    }
    
    public String getNCP(){
        return "local helper = ncp.Helper:new(host,port)\n" +
            " local status, resp = helper:connect()\n" +
            " status, resp = helper:search(\"[Root]\", \"User\", \"*\")\n" +
            " status = helper:close()";
    }
    
    public String getNRPC(){
        return "helper = nrpc.Helper:new(host, port)\n" +
            " status, err = nrpc:Connect()\n" +
            " status, res = nrpc:isValidUser(\"Patrik Karlsson\")\n" +
            " status, err = nrpc:Close()";
    }
    
    public String getOpenssl(){
        return "if not pcall(require, \"openssl\") then\n" +
            "  action = function(host, port)\n" +
            "    stdnse.print_debug(2, \"Skipping \\\"%s\\\" because OpenSSL is missing.\", id)\n" +
            "  end\n" +
            "end\n" +
            "action = action or function(host, port)\n" +
            "  ...\n" +
            "end";
    }
    
    public String getRPC(){
        
        return "-- retrieve a list of NFS export\n" +
            "status, mounts = rpc.Helper.ShowMounts( host, port )\n" +
            "\n" +
            "-- iterate over every share\n" +
            "for _, mount in ipairs( mounts ) do\n" +
            "\n" +
            "   -- get the NFS attributes for the share\n" +
            "   status, attribs = rpc.Helper.GetAttributes( host, port, mount.name )\n" +
            "   .... process NFS attributes here ....\n" +
            "end";

    }
    
    public String getTftp(){
        return "tftp.start()\n" +
            "  local status, f = tftp.waitFile(\"incoming.txt\", 10)\n" +
            "  if ( status ) then return f:getContent() end";
    }
    
    public String getXamp(){
        
        return "local helper = xmpp.Helper:new(host, port, options)\n" +
            "local status, err = helper:connect()\n" +
            "status, err = helper:login(user, pass, \"DIGEST-MD5\")";
    }
    
    public String getSockConnect(){
        return "socket:connect(host,port)\n" +
            "  socket:send(\"\\r\\n\\r\\n\")\n" +
            "  response, data =socket:receive(1000)";
    }
    
     
    
}

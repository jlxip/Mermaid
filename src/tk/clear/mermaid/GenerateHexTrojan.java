package tk.clear.mermaid;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class GenerateHexTrojan {
    public String GenerateHexTrojan(File uncompiledTrojan){
        String trojanPath = uncompiledTrojan.getAbsolutePath();
        Pattern raw_dots_pattern = Pattern.compile(Pattern.quote("."));
        String[] dots = raw_dots_pattern.split(trojanPath);
        
        String trojanPathNOExtension = "";
        for(int i=0;i<dots.length-1;i++){
            trojanPathNOExtension = trojanPathNOExtension + dots[i] + ".";
        }
        
        String compiledTrojanPath = trojanPathNOExtension + "exe";
        
        byte[] array = null;
        try{
            FileInputStream fis = new FileInputStream(compiledTrojanPath);
            BufferedInputStream bis = new BufferedInputStream(fis);
            array = new byte[bis.available()];
            bis.read(array);
            bis.close();
            fis.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String hexed = bytesToHex(array);
        return hexed;
    }
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}

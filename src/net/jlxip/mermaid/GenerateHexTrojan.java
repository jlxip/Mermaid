package net.jlxip.mermaid;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.regex.Pattern;

public class GenerateHexTrojan {
    public String GenerateHexTrojan(File uncompiledTrojan){ // FUNCIÓN PARA CONVERTIR EL TROYANO A HEXADECIMAL
        String trojanPath = uncompiledTrojan.getAbsolutePath(); // Creamos una variable con la ruta del archivo que recibimos por argumentos
        Pattern raw_dots_pattern = Pattern.compile(Pattern.quote(".")); // Creamos un patrón para separar Strings mediante el punto
        String[] dots = raw_dots_pattern.split(trojanPath); // Separamos la variable con la ruta del archivo por puntos
        
        String trojanPathNOExtension = "";  // Creamos un String vacío para almacenar la ruta del troyano SIN extensión
        for(int i=0;i<dots.length-1;i++){   // Recorremos el array de puntos hasta llegar a uno menos del final
            trojanPathNOExtension = trojanPathNOExtension + dots[i] + ".";  // Añadimos a la variable trojanPathNOExtension el String de la posición actual del array más un punto
        }
        
        String compiledTrojanPath = trojanPathNOExtension + "exe";  // Creamos un String para almacenar la ruta del troyano sin extensión más "exe", por lo que le añadimos esta extensión
        
        byte[] array = null;    // Creamos un nuevo array de bytes
        try{
            FileInputStream fis = new FileInputStream(compiledTrojanPath);  // Abrimos el archivo
            BufferedInputStream bis = new BufferedInputStream(fis);         // para lectura binaria
            array = new byte[bis.available()];  // Creamos el tamaño del array con el tamaño de los bytes disponibles para lectura
            bis.read(array);    // Leemos el archivo y lo almacenamos en la array "array" (muy currado el nombre)
            bis.close();    // Cerramos...
            fis.close();    // "
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        String hexed = bytesToHex(array);   // Creamos un nuevo String con la salida del método bytesToHex pasándole el array de bytes como argumento
        return hexed;   // Returnamos la variable hexed
    }
    
    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray(); //Creamos un array de carácteres con todos los carácteres posibles en el lenguaje hexadecimal
    public static String bytesToHex(byte[] bytes) { // FUNCIÓN PARA CONVERTIR UN ARRAY DE BYTES EN UNA CADENA HEXADECIMAL
        char[] hexChars = new char[bytes.length * 2];       // La verdad es que no sé muy bien cómo funciona esto, lo admito
        for ( int j = 0; j < bytes.length; j++ ) {          // Lo encontré en este StackOverflow:
            int v = bytes[j] & 0xFF;                        // http://goo.gl/ETe6us
            hexChars[j * 2] = hexArray[v >>> 4];            // 
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];       // Siento la desinformación :S
        }
        return new String(hexChars);    // Returnamos un nuevo String con el array de carácteres hexChars
    }
}

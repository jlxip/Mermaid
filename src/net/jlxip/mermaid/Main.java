package net.jlxip.mermaid;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

public class Main extends JFrame {
	private static final long serialVersionUID = 1L;
	final Boolean debug = false;
	ArrayList<Profile> profiles = new ArrayList<Profile>();  // Este ArrayList será utilizado para almacenar los perfiles del archivo "profiles.dat"

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Main frame = new Main();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	JList<String> list;
	public Main() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 510, 541);
		getContentPane().setLayout(null);
		setLocationRelativeTo(null);
		
		JLabel logo = new JLabel();
		logo.setIcon(new ImageIcon(Main.class.getResource("/net/jlxip/mermaid/Logo50.png")));
		final Dimension Dlogo = new Dimension(310, 155);
		final int Plogo_x = 92;
		final int Plogo_y = 8;
		final Point Plogo = new Point(Plogo_x, Plogo_y);
		final Rectangle Rlogo = new Rectangle(Plogo, Dlogo);
		logo.setBounds(Rlogo);
		getContentPane().add(logo);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(8, 178, 390, 303);
		getContentPane().add(scrollPane);
		
		list = new JList<String>();
		scrollPane.setViewportView(list);
		
		JButton button = new JButton("+");
		Main me = this;
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				EditProfile editProfile = new EditProfile(me, true, me);        // Abrimos EditProfile CREANDO un perfil
		        editProfile.setVisible(true);   // Lo hacemos visible
			}
		});
		button.setBounds(410, 188, 51, 25);
		getContentPane().add(button);
		
		JButton button_1 = new JButton("-");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list.isSelectionEmpty()){       // Si se ha seleccionado algún elemento...
		            // ELIMINAR DEL ARCHIVO
		            String str = profiles.get(list.getSelectedIndex()).getString(); // Guardamos el String del perfil seleccionado
		            File file = new File("profiles.dat");   // Abrimos el archivo "profiles.dat"
		            
		            String allfile = "";
		            
		            // Primero leemos el archivo y lo almacenamos en la variable: allfile
		            try{
		                FileReader profilesReader = new FileReader(file);                           // Preparamos para leer
		                BufferedReader profilesBufferedReader = new BufferedReader(profilesReader); // "
		                String lastLine = "";       // Variable para la última línea
		                while((lastLine=profilesBufferedReader.readLine())!=null){      // Mientras no se acabe el archivo...
		                    if(!lastLine.equals(str)){      // Si la línea que hemos leído NO es igual a la línea del perfil que hemos seleccionado
		                        allfile = allfile + lastLine + "\n";        // La almacenamos el allfile
		                    }
		                }
		                profilesBufferedReader.close();     // Cerramos el archivo
		                profilesReader.close();             // "
		            } catch (FileNotFoundException ex) {
		                ex.printStackTrace();
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		            
		            // ESCRIBIMOS EL ARCHIVO
		            try{
		            	FileWriter profilesFW = new FileWriter(file);
		                PrintWriter profilesPW = new PrintWriter(profilesFW);   // Abrimos el archivo para escribir en él
		                profilesPW.print(allfile);      // Escribimos la variable allfile
		                profilesPW.close();             // Cerramos
		                profilesFW.close();
		            } catch (IOException ex) {
		                ex.printStackTrace();
		            }
		            
		            UpdateList();       // Y por último, actualizamos la lista
		        }
			}
		});
		button_1.setBounds(410, 225, 51, 25);
		getContentPane().add(button_1);
		
		JButton btnEdit = new JButton("EDIT");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list.isSelectionEmpty()){       // Si se ha seleccionado algún elemento de la lista...
		            Profile selected = profiles.get(list.getSelectedIndex());   // Cargamos el perfil seleccionado
		            EditProfile editProfile = new EditProfile(me, true, me, selected);  // Abrimos EditProfile EDITANDO un perfil
		            editProfile.setVisible(true);   // Lo hacemos visible
		        }
			}
		});
		btnEdit.setBounds(410, 263, 62, 25);
		getContentPane().add(btnEdit);
		
		JButton btnBuild = new JButton("BUILD");
		btnBuild.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(!list.isSelectionEmpty()){   // Si se ha seleccionado algún elemento de la lista...
		            Profile selected = profiles.get(list.getSelectedIndex());       // Cargamos el perfil seleccionado
		            Build build = new Build(me, true, selected, debug);      // Lanzamos Build con el perfil
		            build.setVisible(true);         // Hacemos visible la nueva ventana
		        }
			}
		});
		btnBuild.setBounds(410, 300, 73, 25);
		getContentPane().add(btnBuild);
		
		UpdateList();
	}
	
	public void UpdateList() {
        File profilesFile = new File("profiles.dat");   // Cargamos el archivo "profiles.dat"
        
        if(!profilesFile.exists() || !profilesFile.isFile()){   // Si no existe o no es un archivo...
            try {
				profilesFile.createNewFile();	 // CREAR ARCHIVO
			} catch (IOException e) {
				e.printStackTrace();	// Esto no debería de ser llamado nunca.
			}
        }
        
        try{
            // LEER ARCHIVO
            FileReader profilesReader = new FileReader(profilesFile);
            BufferedReader profilesBufferedReader = new BufferedReader(profilesReader);
            
            profiles.clear();   // Limpiamos el JList
            String lastLine = "";    // Esta variable almacenará la última línea leída 
            while((lastLine=profilesBufferedReader.readLine())!=null){  // Mientras no se acabe el archivo...
                // CARGAR PERFIL
                Pattern raw_pattern = Pattern.compile(Pattern.quote("|"));  // Creamos un patrón que divida Strings mediante el símbolo |
                String[] data = raw_pattern.split(lastLine);    // Separamos la última línea leída
                
                if(data[5].equals("NULL")){
                    data[5] = "";           // Si ADFOLDER es NULL, establecemos en el perfil un String vacío
                }
                
                if(data[6].equals("NULL")){
                    data[6] = "";           // Si HKCU es NULL, establecemos en el perfil un String vacío
                }
                
                if(data[7].equals("NULL")){
                    data[7] = "";           // Lo mismo de arriba pero con HKLM
                }
                
                Profile profile = new Profile(data[0], data[1], data[2], data[3], data[4],
                data[5], data[6], data[7], data[8], data[9], data[10], data[11]);      // Creamos el perfil
                
                profiles.add(profile);              // Añadimos el perfil a la lista
            }
            
            profilesBufferedReader.close();     // Cerramos el archivo
            profilesReader.close();             // "
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        // ACTUALIZAR LISTA
        DefaultListModel<String> model = new DefaultListModel<String>();    // Creamos el modelo de la lista
        
        for(int i=0;i<profiles.size();i++){             // Por cada perfil que haya en el ArrayList
            model.addElement(profiles.get(i).NAME);     // Añadimos el nombre de ese perfil al modelo
        }
        
        list.setModel(model);           // Establecemos el modelo de la lista como el que hemos creado
    }
}

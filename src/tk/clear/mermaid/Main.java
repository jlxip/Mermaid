package tk.clear.mermaid;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.ListModel;

public class Main extends javax.swing.JFrame {
    ArrayList<Profile> profiles = new ArrayList();
    
    public Main() {
        initComponents();
        this.setLocationRelativeTo(null);       // CENTRAR
        this.setLayout(null);
        
        // SET MERMAID LABEL POSITION TO CENTER
        int mermaidLabelX = (this.getContentPane().getWidth() - mermaid.getWidth())/2;
        int mermaidLabelY = 6;  // SE SUPONE QUE ES CORRECTO
        Point mermaidLabelPoint = new Point(mermaidLabelX, mermaidLabelY);
        Dimension mermaidPreferredSize = mermaid.getPreferredSize();
        Rectangle mermaidRectangle = new Rectangle(mermaidLabelPoint, mermaidPreferredSize);
        mermaid.setBounds(mermaidRectangle);
        
        UpdateList();
    }
    
    public void UpdateList() {
        // UPDATE ARRAY LIST
        File profilesFile = new File("profiles.dat");
        
        if(!profilesFile.exists() || !profilesFile.isFile()){
            // CREAR ARCHIVO
            try{
                BufferedWriter profilesBW = new BufferedWriter(new FileWriter(profilesFile));
                profilesBW.write("");
                profilesBW.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        try{
            FileReader profilesReader = new FileReader(profilesFile);
            BufferedReader profilesBufferedReader = new BufferedReader(profilesReader);
            
            profiles.clear();
            String lastLine;
            while((lastLine=profilesBufferedReader.readLine())!=null){
                // LOAD PROFILE
                Pattern raw_pattern = Pattern.compile(Pattern.quote("|"));
                String[] data = raw_pattern.split(lastLine);
                
                if(data[5].equals("NULL")){
                    data[5] = "";
                }
                
                if(data[6].equals("NULL")){
                    data[6] = "";
                }
                
                Profile profile = new Profile(data[0], data[1], data[2], data[3], data[4],
                data[5], data[6]);
                
                String profileName = data[0];
                
                profiles.add(profile);
            }
            
            profilesBufferedReader.close();
            profilesReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //UPDATE LIST
        DefaultListModel model = new DefaultListModel();
        
        for(int i=0;i<profiles.size();i++){
            model.addElement(profiles.get(i).NAME);
        }
        
        list.setModel(model);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mermaid = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        list = new javax.swing.JList<>();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mermaid by CL34R");

        mermaid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tk/clear/mermaid/Logo50.png"))); // NOI18N

        jScrollPane1.setViewportView(list);

        jButton2.setText("+");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("-");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton6.setText("BUILD");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(mermaid)
                        .addGap(13, 13, 13)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(mermaid)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EditProfile editProfile = new EditProfile(this, true, this);
        editProfile.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        if(!list.isSelectionEmpty()){
            // REMOVE FROM FILE
            String str = profiles.get(list.getSelectedIndex()).getString();
            File file = new File("profiles.dat");
            
            String allfile = "";
            
            // READ OLD FILE
            try{
                FileReader profilesReader = new FileReader(file);
                BufferedReader profilesBufferedReader = new BufferedReader(profilesReader);
                String lastLine;
                while((lastLine=profilesBufferedReader.readLine())!=null && !lastLine.equals(str)){
                    allfile = allfile + lastLine + "\n";
                }
                profilesBufferedReader.close();
                profilesReader.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // WRITE
            try{
                BufferedWriter profilesBW = new BufferedWriter(new FileWriter(file));
                profilesBW.write(allfile);
                profilesBW.close();
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            UpdateList();
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(!list.isSelectionEmpty()){
            Profile selected = profiles.get(list.getSelectedIndex());
            Build build = new Build(this, true, selected);
            build.setVisible(true);
        }
    }//GEN-LAST:event_jButton6ActionPerformed
    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> list;
    private javax.swing.JLabel mermaid;
    // End of variables declaration//GEN-END:variables
}

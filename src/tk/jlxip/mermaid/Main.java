/*

M E R M A I D
Un generador de payloads creado por JlXip
VERSIÓN 1.1.1
Página oficial: http://jlxip.esy.es/Mermaid/

*/

// Nota mental: A la hora de hacer una actualización cambiar el String "VERSION".

package tk.jlxip.mermaid;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

public class Main extends javax.swing.JFrame {
    ArrayList<Profile> profiles = new ArrayList();  // Este ArrayList será utilizado para almacenar los perfiles del archivo "profiles.dat"
    Boolean debug;  // Variable booleana para determinar si estamos en modo debug
    
    public Main(Boolean recvDebug) {
        debug = recvDebug;
        String VERSION="1.1.1";       // Versión de Mermaid
        initComponents();
        this.setLocationRelativeTo(null);       // Centramos la ventana
        this.setLayout(null);                   // Con esta línea permitimos mover los componentes por la pantalla libremente
        
        setTitle("Mermaid by JlXip v"+VERSION);     // Establecer título del JFrame
        
        // CENTRAR EL LABEL: mermaid
        int mermaidLabelX = (this.getContentPane().getWidth() - mermaid.getWidth())/2;  // Calculamos la posición en X para que el label esté centrado
        int mermaidLabelY = 6;  // La posición Y es constante
        Point mermaidLabelPoint = new Point(mermaidLabelX, mermaidLabelY);  // Creamos un punto con las dos anteriores coordenadas
        Dimension mermaidPreferredSize = mermaid.getPreferredSize();    // Creamos una dimensión con el tamaño recomendado
        Rectangle mermaidRectangle = new Rectangle(mermaidLabelPoint, mermaidPreferredSize);    // Creamos un rectángulo con el punto y la dimensión previamente creados
        mermaid.setBounds(mermaidRectangle);    // Establecemos los puntos del label al rectángulo mermaidRectangle
        
        UpdateList();       // Actualizamos la lista de perfiles
        
        // COMPROBAR ACTUALIZACIONES
        try{
            StringBuilder result = new StringBuilder();     // Creamos un nuevo StringBuilder
            URL url = new URL("http://jlxip.esy.es/Mermaid/lastv.php"); // Creamos una URL con el enlace para ver la última versión
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  // Creamos un HttpURLConnection para la conexión
            conn.setRequestMethod("GET");   // Establecemos el método de petición a "GET"
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));   // Creamos el BufferedReader para leer la página
            String line = "ERROR";  // Creamos una variable con el contenido de "ERROR" por si falla algo
            line = rd.readLine();   // Leemos la primera línea
            result.append(line);    // La convertimos a una String correcta
            rd.close(); // Cerramos el BufferedReader
            if(!line.equals(VERSION) && !line.equals("ERROR")){   // Si la última versión no es la actual y tampoco es "ERROR"...
                JOptionPane.showMessageDialog(this, "Version "+line+" is released. Please go to http://jlxip.esy.es/Mermaid to download it.");  // Avisamos al usuario
            }
        } catch (MalformedURLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ProtocolException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void UpdateList() {
        File profilesFile = new File("profiles.dat");   // Cargamos el archivo "profiles.dat"
        
        if(!profilesFile.exists() || !profilesFile.isFile()){   // Si no existe o no es un archivo...
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
                
                String profileName = data[0];       // Sacamos el nombre del perfil
                
                profiles.add(profile);              // Añadimos el perfil a la lista
            }
            
            profilesBufferedReader.close();     // Cerramos el archivo
            profilesReader.close();             // "
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex); // Esto no ocurre nunca, porque creamos el archivo antes
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // ACTUALIZAR LISTA
        DefaultListModel model = new DefaultListModel();    // Creamos el modelo de la lista
        
        for(int i=0;i<profiles.size();i++){             // Por cada perfil que haya en el ArrayList
            model.addElement(profiles.get(i).NAME);     // Añadimos el nombre de ese perfil al modelo
        }
        
        list.setModel(model);           // Establecemos el modelo de la lista como el que hemos creado
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
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mermaid by JlXip");
        setResizable(false);

        mermaid.setIcon(new javax.swing.ImageIcon(getClass().getResource("/tk/jlxip/mermaid/Logo50.png"))); // NOI18N

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

        jButton1.setText("EDIT");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(mermaid))
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
                        .addComponent(jButton1)
                        .addGap(9, 9, 9)
                        .addComponent(jButton6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        EditProfile editProfile = new EditProfile(this, true, this);        // Abrimos EditProfile CREANDO un perfil
        editProfile.setVisible(true);   // Lo hacemos visible
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
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
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);     // Poco probable que ocurra
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            // ESCRIBIMOS EL ARCHIVO
            try{
                BufferedWriter profilesBW = new BufferedWriter(new FileWriter(file));   // Abrimos el archivo para escribir en él
                profilesBW.write(allfile);      // Escribimos la variable allfile
                profilesBW.close();             // Cerramos
            } catch (IOException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            UpdateList();       // Y por último, actualizamos la lista
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        if(!list.isSelectionEmpty()){   // Si se ha seleccionado algún elemento de la lista...
            Profile selected = profiles.get(list.getSelectedIndex());       // Cargamos el perfil seleccionado
            Build build = new Build(this, true, selected, debug);      // Lanzamos Build con el perfil
            build.setVisible(true);         // Hacemos visible la nueva ventana
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(!list.isSelectionEmpty()){       // Si se ha seleccionado algún elemento de la lista...
            Profile selected = profiles.get(list.getSelectedIndex());   // Cargamos el perfil seleccionado
            EditProfile editProfile = new EditProfile(this, true, this, selected);  // Abrimos EditProfile EDITANDO un perfil
            editProfile.setVisible(true);   // Lo hacemos visible
        }
    }//GEN-LAST:event_jButton1ActionPerformed
    
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
                Boolean tmpDebug = false;
                
                if(args.length>0){
                    for(int i=0;i<args.length;i++){
                        if(args[i].equals("debug")){
                            System.out.println("Mode debug enabled.");
                            tmpDebug = true;
                        }
                    }
                    
                    new Main(tmpDebug).setVisible(true);    // ¡ALLÁ VAMOS!
                } else {
                    new Main(false).setVisible(true);    // ¡ALLÁ VAMOS!
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JList<String> list;
    private javax.swing.JLabel mermaid;
    // End of variables declaration//GEN-END:variables
}

/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package GUI;

import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 *
 * @author Juan Luis
 */
public class SongInfoView extends javax.swing.JDialog {
    /**
     * Song view referenced.
     */
    private SongView view;
    
    /**
     * Sets the setting view.
     * @param mv MSNView to apply settings.
     */
    private void setView(SongView sv){
        this.view = sv;
        this.labPath.setText(view.getSong().getPath());
        this.txtTitle.setText(view.getSong().getTitle());
        this.txtAuthor.setText(view.getSong().getAuthor());
        this.txtDescription.setText(view.getSong().getDescription());
        this.txtAlbum.setText(view.getSong().getAlbum());
        this.txtDisc.setText(view.getSong().getDisc());
        this.txtNumber.setText(view.getSong().getNumber());
        this.setTitle(view.getSong().getTitle());
    }
    /**
     * Creates new form SongInfoView
     */
    public SongInfoView(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                view.enableBtInfo(true);
                dispose();
            }
        });
    }
    
    /**
     * Shows song info view.
     * @param sv song view referring.
     */
    public void showView(SongView sv){
        this.setView(sv);
        this.setVisible(true);
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labPath = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTitle = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txtAuthor = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtDescription = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtAlbum = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtDisc = new javax.swing.JTextField();
        btUpdate = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtNumber = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Song");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/player_icon.png")));
        setResizable(false);

        labPath.setText("C:\\");

            jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
            jLabel1.setText("Título:");

            txtTitle.setText("mySong");

            jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
            jLabel2.setText("Autor:");

            txtAuthor.setText("Juan Luis Suárez Díaz");

            jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
            jLabel3.setText("Descripción:");

            txtDescription.setText("Es una canción.");

            jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
            jLabel4.setText("Album:");

            txtAlbum.setText("myAlbum");

            jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
            jLabel5.setText("Disco:");

            txtDisc.setText("28");

            btUpdate.setText("Actualizar");
            btUpdate.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    btUpdateActionPerformed(evt);
                }
            });

            jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
            jLabel6.setText("Número");

            txtNumber.setText("2");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(98, 98, 98)
                                    .addComponent(btUpdate))
                                .addGroup(layout.createSequentialGroup()
                                    .addContainerGap()
                                    .addComponent(jLabel6)))
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(labPath, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1)
                                        .addComponent(jLabel2)
                                        .addComponent(jLabel3)
                                        .addComponent(jLabel4)
                                        .addComponent(jLabel5))
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(txtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(labPath)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel1)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtTitle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel3)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtDescription, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtAlbum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtDisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(txtNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(btUpdate)
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            pack();
            setLocationRelativeTo(null);
        }// </editor-fold>//GEN-END:initComponents

    private void btUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btUpdateActionPerformed
        view.getSong().setTitle(txtTitle.getText());
        view.getSong().setAuthor(txtAuthor.getText());
        view.getSong().setDescription(txtDescription.getText());
        view.getSong().setAlbum(txtAlbum.getText());
        view.getSong().setDisc(txtDisc.getText());
        view.getSong().setNumber(txtNumber.getText());
        view.setSong(view.getSong());
        this.setTitle(view.getSong().getTitle());
    }//GEN-LAST:event_btUpdateActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btUpdate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel labPath;
    private javax.swing.JTextField txtAlbum;
    private javax.swing.JTextField txtAuthor;
    private javax.swing.JTextField txtDescription;
    private javax.swing.JTextField txtDisc;
    private javax.swing.JTextField txtNumber;
    private javax.swing.JTextField txtTitle;
    // End of variables declaration//GEN-END:variables
}

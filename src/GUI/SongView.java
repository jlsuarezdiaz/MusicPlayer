/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package GUI;

import Model.Song;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * Class SongView.
 * A GUI for a song.
 * @author Juan Luis
 */
public class SongView extends javax.swing.JPanel {
    /**
     * Model song.
     */
    private Song songModel;
    
    /**
     * Playing mode.
     */
    private PlayingViewState playMode;
    
    /**
     * Soft selction.
     */
    boolean softSelected;
    
    /**
     * Hard selection.
     */
    boolean hardSelected;

    /**
     * Date format for songs time.
     */
    private static final DateFormat df = new SimpleDateFormat("HH:mm:ss");
    static {df.setTimeZone(TimeZone.getTimeZone("GMT"));}
    /**
     * Sets background according to playing mode.
     */
    private void setBackground(){
        if(hardSelected){
            this.setBackground(Color.CYAN);
        }
        else{
            if(playMode == PlayingViewState.PLAYING){
                this.setBackground(new Color(0xF7D358));
            }
            else{
                if(softSelected){
                    this.setBackground(new Color(0xFF0080));
                }
                else{
                    this.setBackground(new Color(0xF0F0F0));
                }
            }
        }
        
    }
    
    /**
     * Sets the view when song is stopped.
     */
    private void setStoppedView(){
        this.BtBackward.setVisible(false);
        this.BtForward.setVisible(false);
        this.BtPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/play_button.png")));
        this.BtPlayPause.setToolTipText("Reproducir");
        this.BtStop.setVisible(false);
        playMode = PlayingViewState.STOPPED;
        this.setBackground();
    }
    
    /**
     * Sets the view when song is playing.
     */
    private void setPlayingView(){
        this.BtBackward.setVisible(true);
        this.BtForward.setVisible(true);
        this.BtPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/pause_button.png")));
        this.BtPlayPause.setToolTipText("Pausa");
        this.BtStop.setVisible(true);
        playMode = PlayingViewState.PLAYING;
        this.setBackground();
    } 
    
    /**
     * Sets the view when song is paused.
     */
    private void setPausedView(){
        this.BtBackward.setVisible(true);
        this.BtForward.setVisible(true);
        this.BtPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/play_button.png")));
        this.BtPlayPause.setToolTipText("Continuar");
        this.BtStop.setVisible(true);    
        playMode = PlayingViewState.PAUSED;
        this.setBackground();
    }
    
    /**
     * Creates new form SongView
     */
    public SongView() {
        initComponents();    
    }

    /**
     * Set the view for a song.
     * @param s Song to set.
     */
    public void setSong(Song s){
        this.songModel = s;
        titleLabel.setText(songModel.getTitle());
        playMode = PlayingViewState.STOPPED;
        ratingPanel.setRating(songModel.getRate());
        songModel.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                setStoppedView();
                songModel.endSong();
            }
        });
        songModel.setOnPlaying(new Runnable() {
            @Override
            public void run() {
                setPlayingView();
            }
        });
        songModel.setOnPaused(new Runnable() {
            @Override
            public void run() {
                setPausedView();
            }
        });
        songModel.setOnStopped(new Runnable() {
            @Override
            public void run() {
                setStoppedView();
            }
        });
        setStoppedView();
        repaint();
        ratingPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                   songModel.rate(ratingPanel.getRating());
            }
        });
    }
    
    /**
     * Gets model song.
     * @return model song.
     */
    public Song getSong(){
        return songModel;
    }
    
    /**
     * Gets playing mode.
     * @return play mode.
     */
    public PlayingViewState getPlayMode(){
        return playMode;
    }
    
    /**
     * Soft selects. A soft select doesn't have background priority when song is playing.
     * @param select Selection boolean.
     */
    public void softSelect(boolean select){
        this.softSelected = select;
        setBackground();
    }
    
    /**
     * Gets soft selection.
     * @return true if and only if song is soft selected.
     */
    public boolean isSoftSelected(){
        return this.softSelected;
    }
    
    /**
     * Hard selects. A hard select has background priority when song is playing.
     * @param select Selection boolean.
     */
    public void hardSelect(boolean select){
        this.hardSelected = select;
        setBackground();
    }
    
    /**
     * Gets hard selection.
     * @return true if and only if song is hard selected.
     */
    public boolean isHardSelected(){
        return this.hardSelected;
    }
    public void addCompleteMouseListener(MouseAdapter evt){
        this.addMouseListener(evt);
        BtPlayPause.addMouseListener(evt);
        BtStop.addMouseListener(evt);
        BtForward.addMouseListener(evt);
        BtBackward.addMouseListener(evt);
        ratingPanel.addMouseListener(evt);
        titleLabel.addMouseListener(evt);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        BtBackward = new javax.swing.JButton();
        BtPlayPause = new javax.swing.JButton();
        BtForward = new javax.swing.JButton();
        BtStop = new javax.swing.JButton();
        ratingPanel = new GUI.RatingPanel();
        btInfo = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        setMaximumSize(new java.awt.Dimension(715, 44));
        setMinimumSize(new java.awt.Dimension(715, 44));
        setPreferredSize(new java.awt.Dimension(715, 44));

        titleLabel.setText("Por qué no compila Superemix");
        titleLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                titleLabelMouseEntered(evt);
            }
        });

        BtBackward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/backward_button.png"))); // NOI18N
        BtBackward.setToolTipText("Rebobinar");
        BtBackward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBackwardActionPerformed(evt);
            }
        });

        BtPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/play_button.png"))); // NOI18N
        BtPlayPause.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtPlayPauseActionPerformed(evt);
            }
        });

        BtForward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/forward_button.png"))); // NOI18N
        BtForward.setToolTipText("Avanzar");
        BtForward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtForwardActionPerformed(evt);
            }
        });

        BtStop.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/stop_button.png"))); // NOI18N
        BtStop.setToolTipText("Parar");
        BtStop.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtStopActionPerformed(evt);
            }
        });

        ratingPanel.setToolTipText("Valoración");

        btInfo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/info_icon.png"))); // NOI18N
        btInfo.setToolTipText("Información detallada");
        btInfo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btInfoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(BtBackward, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(BtPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(BtForward, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(BtStop, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addComponent(ratingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(titleLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BtBackward, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BtPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BtForward, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(BtStop, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ratingPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void BtForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtForwardActionPerformed
        songModel.fastForward();
    }//GEN-LAST:event_BtForwardActionPerformed

    private void BtBackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBackwardActionPerformed
        songModel.backward();
    }//GEN-LAST:event_BtBackwardActionPerformed

    private void BtPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtPlayPauseActionPerformed
        if(playMode == PlayingViewState.PLAYING){
            setPausedView();
            songModel.pause();
        }
        else{
            setPlayingView();
            songModel.play(); 
        }
    }//GEN-LAST:event_BtPlayPauseActionPerformed

    private void BtStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtStopActionPerformed
        setStoppedView();
        songModel.stop();
    }//GEN-LAST:event_BtStopActionPerformed

    private void titleLabelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleLabelMouseEntered
        titleLabel.setToolTipText("<html>" +
            songModel.getPath() + "<br> <br>" + songModel.getTitle() + "<br>" +
            (("UNKNOWN".equals(songModel.getAuthor()))?"":("<br> Autor: "+songModel.getAuthor())) +
            ((songModel.getDescription() == "-")?"":("<br>"+songModel.getDescription())) +
            ((songModel.getAlbum() == "UNKNOWN")?"":("<br> Album: "+songModel.getAlbum())) +
            ((songModel.getDisc() == "NONE")?"":("<br> Disco: "+songModel.getDisc())) +
            ((songModel.getNumber() == "-")?"":("<br> Nº: "+songModel.getNumber())) +    
            "<br> <br>" + df.format(new Time((long)(songModel.getElapsedTime()*1000))) + " / "
            + df.format(new Time((long) (songModel.getLength()*1000))) + "</html>"
        );
    }//GEN-LAST:event_titleLabelMouseEntered

    public void enableBtInfo(boolean b){
        btInfo.setEnabled(b);
    }
    
    private void btInfoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btInfoActionPerformed
        SongInfoView info = new SongInfoView(null, false);
        enableBtInfo(false);
        info.showView(this);
    }//GEN-LAST:event_btInfoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtBackward;
    private javax.swing.JButton BtForward;
    private javax.swing.JButton BtPlayPause;
    private javax.swing.JButton BtStop;
    private javax.swing.JButton btInfo;
    private GUI.RatingPanel ratingPanel;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}

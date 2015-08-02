/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package GUI;

import Model.MusicPlayer;
import Model.PlayingMode;
import Model.Song;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Juan Luis
 */
public class MusicPlayerView extends javax.swing.JFrame {
    /**
     * Checks if player is running.
     */
    private boolean playing;
    
    /**
     * Model Music Player.
     */
    private MusicPlayer mpModel;
    
    /**
     * Song controller handler.
     */
    private final ActionListener taskSongController;
    
    /**
     * Song controller timer.
     */
    private final Timer timerSongController;
    
    /**
     * Date format for songs time.
     */
    private static final DateFormat df = new SimpleDateFormat("HH:mm:ss");
    static {df.setTimeZone(TimeZone.getTimeZone("GMT"));}
    
    /**
     * Current song index in the panel.
     */
    //private int currentSongIndex;
    
    /**
     * Sets the view according to if music is playing.
     * @param playing Determines if player is running.
     */
    private void setPlayingView(boolean playing){
        this.playing = playing;
        if(playing){
            this.BtPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/pause_button.png")));
            this.BtPlayPause.setToolTipText("Pausa");
        }
        else{
            this.BtPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/play_button.png")));
            this.BtPlayPause.setToolTipText("Reproducir");
        }
    }
    
    /**
     * Manages possible changes when a song is clicked in songs panel.
     * @param evt 
     */
    private void manageSongClick(java.awt.event.MouseEvent evt){
        Object obj = evt.getSource();
        SongView sv;
        if(obj instanceof SongView)
            sv = (SongView)obj;
        else if(obj instanceof javax.swing.JButton)
            sv = (SongView)((javax.swing.JButton)obj).getParent();
        else if(obj instanceof javax.swing.JPanel)
            sv = (SongView)((javax.swing.JPanel)obj).getParent();
        else if(obj instanceof javax.swing.JLabel)
            sv = (SongView)((javax.swing.JLabel)obj).getParent();
        else
            sv = null;
        
        //Song button pressed management.
        if(obj instanceof javax.swing.JButton){
            if(sv.getSong() != mpModel.getCurrentSong()){
                if(sv.getPlayMode() == PlayingViewState.PLAYING){
                    findCurrentSongView().softSelect(false);
                    //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(false);
                    try{
                        mpModel.chooseSong(sv.getSong());
                    }
                    catch(MediaException ex){
                        JOptionPane.showMessageDialog(this,"Error al reproducir el archivo: "+ex.getMessage(),
                            "Error de reproducción", JOptionPane.ERROR_MESSAGE);
                        mpModel.stop();
                    }
                    findCurrentSongView().softSelect(true);
                    scrollToSelection();
                    //currentSongIndex = mpModel.getCurrentSongIndex();
                    //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(true);
                    
                    this.setPlayingView(true);
                }
            }
            else{
                if(sv.getPlayMode() == PlayingViewState.PLAYING){
                    try{
                        mpModel.play();
                    }
                    catch(MediaException ex){
                        JOptionPane.showMessageDialog(this,"Error al reproducir el archivo: "+ex.getMessage(),
                            "Error de reproducción", JOptionPane.ERROR_MESSAGE);
                        mpModel.stop();
                    }
                    this.setPlayingView(true);
                }
                else if(sv.getPlayMode() == PlayingViewState.PAUSED){
                    mpModel.pause();
                    this.setPlayingView(false);
                }
                else{
                    mpModel.stop();
                    this.setPlayingView(false);
                }
            }
        }
    }
    
    /**
     * Finds current song view.
     * @return current song view.
     */
    private SongView findCurrentSongView(){
        SongView sv;
        for(Component c : SongPanel.getComponents()){
            sv = (SongView)c;
            if(sv.getSong() == mpModel.getCurrentSong()){
                return sv;
            }
        }
        return null;
    }
    
    /**
     * Enables or disables playing buttons according to whether player is empty.
     */
    private void enableButtons(){
        BtPlayPause.setEnabled(!mpModel.isEmpty());
        BtStop.setEnabled(!mpModel.isEmpty());
        BtNext.setEnabled(!mpModel.isEmpty());
        BtBack.setEnabled(!mpModel.isEmpty());
        BtForward.setEnabled(!mpModel.isEmpty());
        BtBackward.setEnabled(!mpModel.isEmpty());
    }
    
    /**
     * Scrolls song panel to selected song.
     */
    private void scrollToSelection() {
        if(!mpModel.isEmpty()){
            //SongView selected = findCurrentSongView();
            jScrollPane1.getVerticalScrollBar().setValue(SongPanel.getComponent(0).getHeight() * mpModel.getCurrentSongIndex());
            jScrollPane1.repaint();
            jScrollPane1.revalidate();
            this.repaint();
            this.revalidate();
        }
    }
    
    /**
     * Creates new form PlayerView
     */
    public MusicPlayerView() {
        initComponents();
        this.taskSongController = (ActionEvent evt) -> {
            if(!mpModel.isEmpty()){ 
                if(mpModel.getCurrentSong().hasEnded()){
                    findCurrentSongView().softSelect(false);  /////
                    //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(false);
                    try{
                        mpModel.next();
                    }
                    catch(MediaException ex){
                            JOptionPane.showMessageDialog(this,"Error al reproducir el archivo: "+ex.getMessage(),
                                "Error de reproducción", JOptionPane.ERROR_MESSAGE);
                            mpModel.stop();
                    }
                    findCurrentSongView().softSelect(true);   /////
                    scrollToSelection();
                    //currentSongIndex = mpModel.getCurrentSongIndex();
                    //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(true);
                    setPlayingView(mpModel.isPlaying());
                }
                double cur_ms = mpModel.getCurrentSong().getElapsedTime() * 1000;
                double tot_ms = mpModel.getCurrentSong().getLength()*1000;
                songTimeBar.setMaximum((int) (tot_ms));
                songTimeBar.setValue((int) (cur_ms));
                Time cur = new Time((long) cur_ms);
                Time tot = new Time((long) tot_ms);

                SongTimeLabel.setText(df.format(cur) + " / " + df.format(tot) + " ");
                //SongTimeLabel.setText(cur.toString() + " / " + tot.toString() + " ");
                //System.out.println(mpModel.getCurrentSong().getElapsedTime()*1000);
                //System.out.println(mpModel.getCurrentSong().getRate());
            }
            else{
                songTimeBar.setMaximum(1);
                songTimeBar.setValue(0);
                SongTimeLabel.setText("");
            }
        };
        this.timerSongController = new Timer(1,taskSongController);
        
        this.addWindowListener (new WindowAdapter() {
            @Override
            public void windowClosing (WindowEvent e) {
                int opt = JOptionPane.showConfirmDialog(e.getComponent(), "¿Desea guardar la lista de reproducción antes de salir?"
                        , "Salir", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
                if(opt == JOptionPane.YES_OPTION){
                    if(performSaveSongList() == JFileChooser.APPROVE_OPTION) System.exit(0);
                }
                else if(opt == JOptionPane.NO_OPTION){
                    System.exit(0);
                }
            }
        });
        
        //this.currentSongIndex = -1;
        jScrollPane1.getVerticalScrollBar().setUnitIncrement(45);

    }

    /**
     * Sets the view.
     */
    public void setMusicPlayer(MusicPlayer mp){
        mpModel = mp;
        fillSongsPanel(mpModel.getSongList());
        playing = mpModel.isPlaying();
        setPlayingView(playing);
        timerSongController.start();
        setMode(mpModel.getMode());
        
        //this.currentSongIndex = mpModel.getCurrentSongIndex();
        if(!mpModel.isEmpty()) findCurrentSongView().softSelect(true); /////
        //if(!mpModel.isEmpty()) ((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(true);
        scrollToSelection();
        
        AllSongsInfoLab.setText(Integer.toString(mpModel.getPlaylistSize())+" canciones. Tiempo de música: "+
                df.format(new Time((long) (mpModel.getPlaylistLength()*1000)))+ " ");
        
        enableButtons();
        repaint();
        revalidate();
    }
    
    public MusicPlayer getMusicPlayer(){
        return mpModel;
    }
    
    public void showView(){
        this.setVisible(true);
        scrollToSelection();
    }
    
    public void fillSongsPanel(ArrayList<Song> list) {
        // Deletes old information
        SongPanel.removeAll();

        // For each treasure in the list add its view to the panel.
        for (Song s : list) {
            SongView sv = new SongView();
            sv.setSong(s);
            sv.setVisible (true);
            SongPanel.add(sv);
            sv.addCompleteMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    manageSongClick(evt);
                }
            });
        }

        //scrollToSelection();
        
        // Update the panel
        SongPanel.repaint();
        SongPanel.revalidate();
    }
    
    public void setMode(PlayingMode mode){
        BtRandomStar.setSelected(false);
        BtRandom.setSelected(false);
        BtSequential.setSelected(false);
        BtRepeat.setSelected(false);
        BtOnce.setSelected(false);
        mpModel.setMode(mode);
        switch(mode){
            case RANDOMBYRATE:
                BtRandomStar.setSelected(true);
                break;
            case RANDOM:
                BtRandom.setSelected(true);
                break;
            case SEQUENTIAL:
                BtSequential.setSelected(true);
                break;
            case REPEAT:
                BtRepeat.setSelected(true);
                break;
            case ONCE:
                BtOnce.setSelected(true);
                break;
        }
        this.repaint();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BtBackward = new javax.swing.JButton();
        BtPlayPause = new javax.swing.JButton();
        BtForward = new javax.swing.JButton();
        BtStop = new javax.swing.JButton();
        BtNext = new javax.swing.JButton();
        BtBack = new javax.swing.JButton();
        songTimeBar = new javax.swing.JProgressBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        SongPanel = new javax.swing.JPanel();
        SongTimeLabel = new javax.swing.JLabel();
        BtRandomStar = new javax.swing.JToggleButton();
        BtRandom = new javax.swing.JToggleButton();
        BtSequential = new javax.swing.JToggleButton();
        BtRepeat = new javax.swing.JToggleButton();
        BtOnce = new javax.swing.JToggleButton();
        BtSettings = new javax.swing.JButton();
        BtAddFolder = new javax.swing.JButton();
        BtAddSong = new javax.swing.JButton();
        BtSaveSongList = new javax.swing.JButton();
        BtLoadSongList = new javax.swing.JButton();
        AllSongsInfoLab = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Music Player");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Media/player_icon.png")));
        setResizable(false);

        BtBackward.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/backward_button.png"))); // NOI18N
        BtBackward.setToolTipText("Rebobinar");
        BtBackward.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBackwardActionPerformed(evt);
            }
        });

        BtPlayPause.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/play_button.png"))); // NOI18N
        BtPlayPause.setToolTipText("Reproducir");
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

        BtNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/next_button.png"))); // NOI18N
        BtNext.setToolTipText("Siguiente");
        BtNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtNextActionPerformed(evt);
            }
        });

        BtBack.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/back_button.png"))); // NOI18N
        BtBack.setToolTipText("Anterior");
        BtBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtBackActionPerformed(evt);
            }
        });

        SongPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        SongPanel.setLayout(new javax.swing.BoxLayout(SongPanel, javax.swing.BoxLayout.PAGE_AXIS));
        jScrollPane1.setViewportView(SongPanel);

        SongTimeLabel.setText("0:00 / 3:28  ");

        BtRandomStar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/randomstar_mode.png"))); // NOI18N
        BtRandomStar.setToolTipText("Reproducción ponderada");
        BtRandomStar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRandomStarActionPerformed(evt);
            }
        });

        BtRandom.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/random_mode.png"))); // NOI18N
        BtRandom.setToolTipText("Reproducción aleatoria");
        BtRandom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRandomActionPerformed(evt);
            }
        });

        BtSequential.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/sequential_mode.png"))); // NOI18N
        BtSequential.setToolTipText("Reproducción secuencial");
        BtSequential.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSequentialActionPerformed(evt);
            }
        });

        BtRepeat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/repeat_mode.png"))); // NOI18N
        BtRepeat.setToolTipText("Repetir");
        BtRepeat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtRepeatActionPerformed(evt);
            }
        });

        BtOnce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/once_mode.png"))); // NOI18N
        BtOnce.setToolTipText("Reproducción única");
        BtOnce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtOnceActionPerformed(evt);
            }
        });

        BtSettings.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/settings_icon_xs.png"))); // NOI18N
        BtSettings.setToolTipText("Configuración");
        BtSettings.setPreferredSize(new java.awt.Dimension(28, 28));
        BtSettings.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSettingsActionPerformed(evt);
            }
        });

        BtAddFolder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/add_folder_icon.png"))); // NOI18N
        BtAddFolder.setToolTipText("Añadir canciones de directorio");
        BtAddFolder.setPreferredSize(new java.awt.Dimension(32, 32));
        BtAddFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAddFolderActionPerformed(evt);
            }
        });

        BtAddSong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/add_icon.png"))); // NOI18N
        BtAddSong.setToolTipText("Añadir canción");
        BtAddSong.setPreferredSize(new java.awt.Dimension(32, 32));
        BtAddSong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtAddSongActionPerformed(evt);
            }
        });

        BtSaveSongList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/save_icon.png"))); // NOI18N
        BtSaveSongList.setToolTipText("Guardar lista de reproducción");
        BtSaveSongList.setPreferredSize(new java.awt.Dimension(32, 32));
        BtSaveSongList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtSaveSongListActionPerformed(evt);
            }
        });

        BtLoadSongList.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Media/load_icon.png"))); // NOI18N
        BtLoadSongList.setToolTipText("Añadir lista de reproducción");
        BtLoadSongList.setPreferredSize(new java.awt.Dimension(32, 32));
        BtLoadSongList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtLoadSongListActionPerformed(evt);
            }
        });

        AllSongsInfoLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        AllSongsInfoLab.setText("228 canciones. Tiempo de música: 10:28:57 ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(songTimeBar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(BtSequential, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtRandomStar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BtSettings, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtBackward, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(BtForward, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(93, 93, 93)
                                .addComponent(BtBack, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtNext, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(70, 70, 70)
                                .addComponent(BtStop, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtLoadSongList, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BtSaveSongList, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(BtAddSong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(BtAddFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(BtOnce, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(SongTimeLabel))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(251, 251, 251)
                                        .addComponent(AllSongsInfoLab, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 375, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(songTimeBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(SongTimeLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(AllSongsInfoLab))
                            .addComponent(BtRandomStar, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtRandom, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtRepeat, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtOnce, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtSequential, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtAddSong, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtAddFolder, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BtLoadSongList, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(BtSaveSongList, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(BtBackward, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtPlayPause, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtForward, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtStop, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtNext, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(BtBack, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(BtSettings, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void BtBackwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBackwardActionPerformed
        mpModel.backward();
    }//GEN-LAST:event_BtBackwardActionPerformed

    private void BtPlayPauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtPlayPauseActionPerformed
        if(playing){
            mpModel.pause();
            setPlayingView(false);
        }
        else{
            try{
                mpModel.play();
            }
            catch(MediaException ex){
                        JOptionPane.showMessageDialog(this,"Error al reproducir el archivo: "+ex.getMessage(),
                            "Error de reproducción", JOptionPane.ERROR_MESSAGE);
                        mpModel.stop();
            }
            setPlayingView(true);
        }
    }//GEN-LAST:event_BtPlayPauseActionPerformed

    private void BtForwardActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtForwardActionPerformed
        mpModel.fastForward();
    }//GEN-LAST:event_BtForwardActionPerformed

    private void BtStopActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtStopActionPerformed
        mpModel.stop();
        setPlayingView(false);
    }//GEN-LAST:event_BtStopActionPerformed

    private void BtNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtNextActionPerformed
        findCurrentSongView().softSelect(false);
        //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(false);
        try{
            mpModel.next();
        }
        catch(MediaException ex){
            JOptionPane.showMessageDialog(this,"Error al reproducir el archivo: "+ex.getMessage(),
                "Error de reproducción", JOptionPane.ERROR_MESSAGE);
            mpModel.stop();
        }
        findCurrentSongView().softSelect(true);
        scrollToSelection();
        
        //currentSongIndex = mpModel.getCurrentSongIndex();
        //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(true);
        
        setPlayingView(mpModel.isPlaying());
    }//GEN-LAST:event_BtNextActionPerformed

    private void BtBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtBackActionPerformed
        findCurrentSongView().softSelect(false);
        //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(false);
        try{
            mpModel.back();
        }
        catch(MediaException ex){
                        JOptionPane.showMessageDialog(this,"Error al reproducir el archivo: "+ex.getMessage(),
                            "Error de reproducción", JOptionPane.ERROR_MESSAGE);
                        mpModel.stop();
        }
        findCurrentSongView().softSelect(true);
        scrollToSelection();
        //currentSongIndex = mpModel.getCurrentSongIndex();
        //((SongView)SongPanel.getComponent(currentSongIndex)).softSelect(true);
        setPlayingView(mpModel.isPlaying());
    }//GEN-LAST:event_BtBackActionPerformed

    private void BtRandomStarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRandomStarActionPerformed
        setMode(PlayingMode.RANDOMBYRATE);
    }//GEN-LAST:event_BtRandomStarActionPerformed

    private void BtRandomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRandomActionPerformed
        setMode(PlayingMode.RANDOM);
    }//GEN-LAST:event_BtRandomActionPerformed

    private void BtSequentialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSequentialActionPerformed
        setMode(PlayingMode.SEQUENTIAL);
    }//GEN-LAST:event_BtSequentialActionPerformed

    private void BtRepeatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtRepeatActionPerformed
        setMode(PlayingMode.REPEAT);
    }//GEN-LAST:event_BtRepeatActionPerformed

    private void BtOnceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtOnceActionPerformed
        setMode(PlayingMode.ONCE);
    }//GEN-LAST:event_BtOnceActionPerformed

    public void enableSettingsButton(boolean b){
        BtSettings.setEnabled(b);
    }
    
    private void BtSettingsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSettingsActionPerformed
        SettingsView sv = new SettingsView(this,false);
        enableSettingsButton(false);
        sv.showView(this);
    }//GEN-LAST:event_BtSettingsActionPerformed

    public static final JDialog showLoadingDialog(){
        final JOptionPane optionPane = new JOptionPane("Cargando...\nLa operación puede requerir varios minutos.",
                JOptionPane.INFORMATION_MESSAGE, JOptionPane.DEFAULT_OPTION, null, new Object[]{}, null);

        final JDialog dialog = new JDialog();
        
        dialog.setTitle("Loading...");
        dialog.setModal(false);
        dialog.setContentPane(optionPane);        

        dialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        dialog.setAlwaysOnTop(true);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
        
        dialog.repaint();
        dialog.revalidate();
        
        return dialog;
    }
    
    public static final void closeLoadingDialog(JDialog dialog){
        dialog.dispose();
    }
    
    private void BtAddFolderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAddFolderActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = fc.showDialog(this,"Añadir directorio");
        if(returnVal == JFileChooser.APPROVE_OPTION){
            int rec = JOptionPane.showConfirmDialog(this, "¿Desea añadir las canciones que se hallen en subdirectorios?",
                    "Add folder", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            
            JDialog j = MusicPlayerView.showLoadingDialog();
            
            try{         
                if(rec == JOptionPane.YES_OPTION)
                    mpModel.addDirectoryRecursive(fc.getSelectedFile().getAbsolutePath());
                else
                    mpModel.addDirectory(fc.getSelectedFile().getAbsolutePath());
                //////////////////////////////////////////// MEJORAR ESTO
                this.setMusicPlayer(mpModel);
                ////////////////////////////////////////////
                
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, "Se esperaba un directorio." , "Error : " + ex.getMessage() , JOptionPane.ERROR_MESSAGE);
            }
            
            finally{
                MusicPlayerView.closeLoadingDialog(j);
                scrollToSelection();//????
            }
        }
    }//GEN-LAST:event_BtAddFolderActionPerformed

    private void BtAddSongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtAddSongActionPerformed
        JFileChooser fc = new JFileChooser();
        int returnVal = fc.showDialog(this,"Añadir canción");
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try{
                mpModel.add(new Song(fc.getSelectedFile().getAbsolutePath()));
                //////////////////////////////////////////// MEJORAR ESTO
                this.setMusicPlayer(mpModel);
                ////////////////////////////////////////////
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(this, "El archivo indicado no es reproducible." , "Error : " + ex.getMessage() , JOptionPane.ERROR_MESSAGE);
            }
            //scrollToSelection();//????
        }
    }//GEN-LAST:event_BtAddSongActionPerformed

    private int performSaveSongList(){
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos de lista de reproducción (.mplj)", "mplj"));
        int returnVal = fc.showSaveDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION){
            try {
                mpModel.write(fc.getSelectedFile().getAbsolutePath());
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al guardar la lista de reproducción.\nError: "+
                    ex.getMessage(), "Saving error.", JOptionPane.ERROR_MESSAGE);
            }
        }
        return returnVal;
    }
    private void BtSaveSongListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtSaveSongListActionPerformed
       performSaveSongList();
    }//GEN-LAST:event_BtSaveSongListActionPerformed

    private void BtLoadSongListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtLoadSongListActionPerformed
        JFileChooser fc = new JFileChooser();
        fc.setFileFilter(new FileNameExtensionFilter("Archivos de lista de reproducción (.mplj)", "mplj"));
        int returnVal = fc.showDialog(this,"Añadir lista de reproducción");
        if(returnVal == JFileChooser.APPROVE_OPTION){
            
            JDialog j = MusicPlayerView.showLoadingDialog();
            
            try {
                mpModel.read(fc.getSelectedFile().getAbsolutePath());
                this.setMusicPlayer(mpModel);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al cargar la lista de reproducción.\nError: "+
                        ex.getMessage(), "Loading error.", JOptionPane.ERROR_MESSAGE);
            }
            finally{
                MusicPlayerView.closeLoadingDialog(j);
                scrollToSelection();
            }
        }
    }//GEN-LAST:event_BtLoadSongListActionPerformed



    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel AllSongsInfoLab;
    private javax.swing.JButton BtAddFolder;
    private javax.swing.JButton BtAddSong;
    private javax.swing.JButton BtBack;
    private javax.swing.JButton BtBackward;
    private javax.swing.JButton BtForward;
    private javax.swing.JButton BtLoadSongList;
    private javax.swing.JButton BtNext;
    private javax.swing.JToggleButton BtOnce;
    private javax.swing.JButton BtPlayPause;
    private javax.swing.JToggleButton BtRandom;
    private javax.swing.JToggleButton BtRandomStar;
    private javax.swing.JToggleButton BtRepeat;
    private javax.swing.JButton BtSaveSongList;
    private javax.swing.JToggleButton BtSequential;
    private javax.swing.JButton BtSettings;
    private javax.swing.JButton BtStop;
    private javax.swing.JPanel SongPanel;
    private javax.swing.JLabel SongTimeLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JProgressBar songTimeBar;
    // End of variables declaration//GEN-END:variables


}
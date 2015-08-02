/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package musicplayer;

import GUI.MusicPlayerIntro;
import GUI.MusicPlayerView;
import Model.MusicPlayer;
import javax.swing.JDialog;

/**
 *
 * @author Juan Luis
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        MusicPlayer mp = new MusicPlayer();
                
        MusicPlayerIntro intro = new MusicPlayerIntro(null,false);
        intro.showView();
        
        JDialog d = MusicPlayerView.showLoadingDialog();
        mp.readDefaultSettings();
        MusicPlayerView.closeLoadingDialog(d);
        
        MusicPlayerView mpv = new MusicPlayerView();
        mpv.setMusicPlayer(mp);
        mpv.showView();
    }
    
}

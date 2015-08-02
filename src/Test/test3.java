/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package Test;

import GUI.MusicPlayerView;
import Model.MusicPlayer;
import Model.Song;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Juan Luis
 */
public class test3 {
    /**
     * 
     * @param args 
     */
   public static void main(String[] args){
        MusicPlayer mp = new MusicPlayer();
        Song s1 = new Song("leader.mp3");
        Song s2 = new Song("pruebam4a.m4a");
        Song s3 = new Song("MSNSound.wav");
        Song s4 = new Song("flute.mp3");
        s1.rate(5);
        s2.rate(6);
        s3.rate(1);
        s4.rate(3);
        ArrayList<Song> ss = new ArrayList(Arrays.asList(s1,s2,s3,s4));
        
        mp.add(ss);
        
        MusicPlayerView mpv = new MusicPlayerView();
        mpv.setMusicPlayer(mp);
        mpv.showView();
   } 
}

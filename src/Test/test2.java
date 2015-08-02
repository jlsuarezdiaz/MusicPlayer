/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package Test;

import Model.MusicPlayer;
import Model.Song;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Test 2. A simple console music player.
 * @author Juan Luis
 */
public class test2 {
    public static void main(String[] args) throws IOException{
        MusicPlayer mp = new MusicPlayer();
        Song s1 = new Song("leader.mp3");
        Song s2 = new Song("pruebam4a.m4a");
        Song s3 = new Song("MSNSound.wav");
        s2.rate(6);
        ArrayList<Song> ss = new ArrayList(Arrays.asList(s1,s2,s3));
        //new ArrayList(Arrays.asList(s1, s2, s3));
        
        mp.add(ss);
        while(true){
            BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
 
            String read = br.readLine();
 
            
            switch(read){
                case "play":
                case "p":
                    mp.play();
                    break;
                case "pause":
                case "ps":
                    mp.pause();
                    break;
                case "stop":
                case "s":
                    mp.stop();
                    break;
                case "next":
                case "n":
                    mp.next();
                    break;
                case "back":
                case "b":
                    mp.back();
                    break;
                case "exit":
                case "e":
                    System.exit(0);
            }
        }
    }
}

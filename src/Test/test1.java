/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package Test;

import Model.Song;
import java.io.File;
import static java.lang.Thread.sleep;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.stage.Stage;

/**
 *
 * @author Juan Luis
 */
public class test1{
    public static void main(String[] args) throws InterruptedException{
        Song s; 
        //s = new Song("C:\\Users\\Juan Luis\\Documents\\PokeMusic\\ORAS\\[HikarinoAkari] Pokémon Omega Ruby & Alpha Sapphire Super Music Complete\\Disc3\\(08) [GAME FREAK] Aqua Magma Leader.mp3");
        s = new Song("leader.mp3");
        //s = new Song("pruebam4a.m4a");
        //s = new Song("Alanwakeup.3ga"); // NOT SUPPORTED
        //s = new Song("MSNsound.wav");
        
        //File f; 
        //f = new File("C:\\Users\\Juan Luis\\Documents\\PokeMusic\\ORAS\\[HikarinoAkari] Pokémon Omega Ruby & Alpha Sapphire Super Music Complete\\Disc3\\(08) [GAME FREAK] Aqua Magma Leader.mp3");
        //f = new File("./leader.mp3");
        //System.out.println(f.exists());
        
        //sleep(10000);
        System.out.println(s.getStatus());
        System.out.println(s.getLength());
        s.play();
        System.out.println(s.getStatus());
        System.out.println("Running...");
        sleep(3000);
        s.pause();
        System.out.println(s.getStatus());
        System.out.println("Pause...");
        sleep(3000);
        s.play();
        System.out.println(s.getStatus());
        System.out.println("Running...");
        sleep(3000);
        s.stop();
        System.out.println(s.getStatus());
        System.out.println("Stopped.");
        s.play();
        System.out.println(s.getStatus());
        System.out.println("Running...");
    /*    while(true){
            System.out.println(s.getStatus());
            //if(s.getStatus() == Status.STOPPED) break;
        }*/
        while(!s.hasEnded()){
            sleep(10);
        }
        System.out.println("Stopped");
        System.exit(0);
    }        

   
}

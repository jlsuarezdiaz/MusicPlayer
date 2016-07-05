/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package Model;

import java.beans.EventHandler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import static java.lang.Thread.sleep;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javafx.scene.media.*;
import javafx.scene.media.MediaPlayer.Status;
import javafx.util.Duration;

/**
 * Class song.
 * It represents a song with every music functionalities.
 * @author Juan Luis
 */
public class Song {
    
    // ---------- PRIVATE ATTRIBUTES ---------- //
    
    /**
     * Song's path.
     */
    private String path;
    
    /**
     * Song's title.
     */
    private String title;
    
    /**
     * Song's author.
     */
    private String author;
    
    /**
     * Song's description.
     */
    private String description;
    
    /**
     * Song's album.
     */
    private String album;
    
    /**
     * Song's disc.
     */
    private String disc;
    
    /**
     * Song's number.
    */
    private String number;
    
    /**
     * Song's rate.
     */
    private int rate;
    
    /**
     * Maximum rate number.
     */
    private static final int MAX_RATE = 6;
    
    /**
     * Song's clip.
     */
    //private Clip clip;
    
    /**
     * Internal media player.
     */
    private MediaPlayer player;
    
    /**
     * The song structure.
     */
    private Media song;
    
    /**
     * End of song flag.
     */
    private boolean end;
    
    /**
     * JFX Panel
     */
    private static JFXPanel jfx = new JFXPanel();
    
    /**
     * Input/Output delimiter.
     */
    private static final String IO_LIM = "\0";
    
    /**
     * Song's duration in seconds.
     */
    private double length;
    
    /**
     * Handler attributes.
     */
    private Runnable onStoppedTask;
    private Runnable onStalledTask;
    private Runnable onRepeatTask;
    private Runnable onReadyTask;
    private Runnable onPlayingTask;
    private Runnable onPausedTask;
    private Runnable onHaltedTask;
    private Runnable onErrorTask;
    private Runnable onEndOfMediaTask;
    
    /**
     * Error flag.
     */
    private boolean error;
    
    // ---------- PRIVATE METHODS ---------- //
    /**
     * Initializes media.
     */
    private void initMedia() throws MediaException{
        try{
            song = new Media(Paths.get(path).toUri().toString());
            player = new MediaPlayer(song);
        }
        catch(MediaException ex){
           error = true;
           throw ex;
        }
        
        while(player.getStatus() != Status.READY){
            try {
                sleep(10);
            } catch (InterruptedException ex) {}
        }
        end = false;
        
        player.setOnEndOfMedia(onEndOfMediaTask);
        player.setOnError(onErrorTask);
        player.setOnHalted(onHaltedTask);
        player.setOnPaused(onPausedTask);
        player.setOnPlaying(onPlayingTask);
        player.setOnReady(onReadyTask);
        player.setOnRepeat(onRepeatTask);
        player.setOnStalled(onStalledTask);
        player.setOnStopped(onStoppedTask);
    }
    
    /**
     * Discards media.
     */
    private void discardMedia(){
        try {
            while(player != null && (player.getStatus() != Status.STOPPED && player.getStatus() != Status.READY ))
                sleep(10);  //Updating time
        } catch (InterruptedException ex) {}
        song = null;
        player = null;
        System.gc();
    }
    
    /**
     * Private setter.
     * @param path Song's path.
     * @param title Song's title.
     */
    private void set(String path, String title) throws MediaException{
        this.path = path;
        this.title = title;
        if(title.trim().isEmpty()) this.title = path;
        
        this.rate = 0;
    /*    try{
            clip = AudioSystem.getClip();
            AudioInputStream inputStream = AudioSystem.getAudioInputStream(
                    (getClass().getResource(path)));
            clip.open(inputStream);
        }
        catch(Exception ex){
            System.err.println(ex.getMessage());
        }*/
        
        //--// initMedia();
        //song = new Media(Paths.get(path).toUri().toString()); //--//
        //length = song.durationProperty().get().toSeconds(); //song.getDuration().toSeconds();              //--//
        //song = null;                                          //--//
        //System.gc();                                          //--//
        initMedia();
        length = player.getTotalDuration().toSeconds();
        discardMedia();
        
        this.author = "UNKNOWN";
        this.description = "-";
        this.album = "UNKNOWN";
        this.disc = "NONE";
        this.number = "-";
        
        this.end = false;
        
        setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                endSong();
            }
        });
        
        setOnError(null);
        setOnHalted(null);
        setOnPaused(null);
        setOnPaused(null);
        setOnPlaying(null);
        setOnReady(null);
        setOnRepeat(null);
        setOnStalled(null);
        setOnStopped(null);
        
        this.error = false;
    }
    
    // ---------- CONSTRUCTOR ---------- //
 
    /**
     * Default constructor.
     */
    public Song(){
        path = null;
        title = null;
        rate = 0;
        end = false;
        player = null;
        song = null;  
        this.author = "UNKNOWN";
        this.description = "-";
        this.album = "UNKNOWN";
        this.disc = "NONE";
        this.number = "-";
        this.error = false;
    }
    
    /**
     * Constructor.
     * @param path Song's path.
     */
    public Song(String path) throws MediaException{
        File f = new File(path);
        set(path,f.getName());
    }
    
    /**
     * Constructor.
     * @param path Song's path.
     * @param title Song's title.
     */
    public Song(String path, String title) throws MediaException{
        set(path,title);
    }
    
    // ---------- SETTER & GETTERS ---------- //
    
    /**
     * Rates a song.
     * @param rate New rate.
     */
    public void rate(int rate){
        if(rate <= MAX_RATE){
            this.rate = rate;
        }
    }
    
    /**
     * Gets the song's path.
     * @return path.
     */
    public String getPath(){
        return this.path;
    }
    
    /**
     * Gets the song's title.
     * @return title.
     */
    public String getTitle(){
        return this.title;
    }
    
    /**
     * Gets the song's rate.
     * @return rate.
     */
    public int getRate(){
        return rate;
    }
    
    /**
     * Gets song's length.
     * @return song's length (in seconds). 
     */
    public double getLength(){
        //--// return player.getTotalDuration().toSeconds();
        return length; //--//
    }
    
    /**
     * Gets song's elapsed time.
     * @return Elapsed time (in seconds). 
     */
    public double getElapsedTime(){
        return (player==null)?0:player.currentTimeProperty().get().toSeconds();
    }
    
    /**
     * Gets maximum rating for a song.
     * @return maximum rating.
     */
    public static int getMaxRating(){
        return MAX_RATE;
    }
    
    /**
     * Gets song's playing status.
     * @return status
     */
    public Status getStatus(){
        return (player==null)?null:player.getStatus();
    }
    
    /**
     * Gets whether the song has ended.
     * @return end of song.
     */
    public boolean hasEnded(){
        return end;
    }
    
    public void setTitle(String title){
        if(title.trim().isEmpty()) this.title = path;
        else this.title = title;
    }
    
    public String getAuthor(){
        return author;
    }
    
    public void setAuthor(String author){
        if(author.trim().isEmpty()) this.author = "UNKNOWN";
        else this.author = author;
    }
    
    public String getDescription(){
        return description;
    }
    
    public void setDescription(String description){
        if(description.trim().isEmpty()) this.description = "-";
        else this.description = description;
    }
    
    public String getAlbum(){
        return album;
    }
    
    public void setAlbum(String album){
        if(album.trim().isEmpty()) this.album = "UNKNOWN";
        else this.album = album;
    }
    
    public String getDisc(){
        return disc;
    }
    
    public void setDisc(String disc){
        if(disc.trim().isEmpty()) this.disc = "NONE";
        else this.disc = disc;
    }
    
    public String getNumber(){
        return number;
    }
    
    public void setNumber(String number){
        if(number.trim().isEmpty()) this.number = "-";
        else this.number = number;
    }
    
    public boolean getError(){
        return error;
    }

    // ---------- HANDLERS ---------- //
    
    /**
     * Sets the end of media event handler
     * @param value the event handler or null.
     */
    public void setOnEndOfMedia(Runnable value){
        //--//if(player != null) player.setOnEndOfMedia(value);
        this.onEndOfMediaTask = value;
    }
    
    /**
     * Sets the event handler to be called when an error occurs
     * @param value the event handler or null.
     */
    public void setOnError(Runnable value){
        //--//if(player != null) player.setOnError(value);
        this.onErrorTask = value;
    }
    
    /**
     * Sets the MediaPlayer.Status.HALTED event handler
     * @param value the event handler or null.
     */
    public void setOnHalted(Runnable value){
        //--//if(player != null) player.setOnHalted(value);
        this.onHaltedTask = value;
    }
    
    /**
     * Sets the MediaPlayer.Status.PAUSED event handler.
     * @param value the event handler or null.
     */
    public void setOnPaused(Runnable value){
        //--//if(player != null) player.setOnPaused(value);
        this.onPausedTask = value;
    }
    
    /**
     * Sets the MediaPlayer.Status.PLAYING event handler.
     * @param value the event handler or null.
     */
    public void setOnPlaying(Runnable value){
        //--//if(player != null) player.setOnPlaying(value);
        this.onPlayingTask = value;
    }
    
    /**
     * Sets the MediaPlayer.Status.READY event handler.
     * @param value the event handler or null.
     */
    public void setOnReady(Runnable value){
        //--//if(player != null) player.setOnReady(value);
        this.onReadyTask = value;
    }
    
    /**
     * Sets the repeat event handler.
     * @param value the event handler or null.
     */
    public void setOnRepeat(Runnable value){
        //--//if(player != null) player.setOnRepeat(value);
        this.onRepeatTask = value;
    }
    
    /**
     * Sets the MediaPlayer.Status.STALLED event handler
     * @param value the event handler or null.
     */
    public void setOnStalled(Runnable value){
        //--//if(player != null) player.setOnStalled(value);
        this.onStalledTask = value;
    }
    
    /**
     * Sets the MediaPlayer.Status.STOPPED event handler.
     * @param value the event handler or null.
     */
    public void setOnStopped(Runnable value){
        //--//if(player != null) player.setOnStopped(value);
        this.onStoppedTask = value;
    }
    
    
    
    // ---------- PUBLIC METHODS ---------- //
    
    /**
     * Plays the song.
     */
    public void play() throws MediaException{
        end = false;
        if(player==null) initMedia();
        player.play();
        //System.out.println(song.getMetadata().toString());
       
    }
    
    /**
     * Pauses the song.
     */
    public void pause(){
        if(player != null) player.pause(); //--//
        //--// player.pause();
    }
    
    /**
     * Stops the song.
     */
    public void stop(){
        if(player != null) player.stop(); //--//
        //--// player.stop();
        discardMedia();
    }
    
    /**
     * Restarts the song.
     */
    public void restart(){
        player.stop();
        player.play();
    }
    /**
     * Fast forward.
     */
    public void fastForward(){
        
    }
    
    /**
     * Go backwards.
     */
    public void backward(){
        
    }
    
    public void endSong(){
        end = true;
        if(player != null) player.stop();
        try {
            sleep(100);
        } catch (InterruptedException ex) {}
        end = false;
        
    }
    
    // ---------- IO-METHODS ---------- //
    
    public void write(String path) throws IOException{
        //FileWriter fw = null;
        //fw = new FileWriter(path);
        OutputStreamWriter fw = new OutputStreamWriter(new FileOutputStream(path), "UTF-8");
        write(fw);
        if(fw != null) fw.close();
    }
    
    public void write(FileWriter fw) throws IOException{
        fw.write(this.path + IO_LIM + this.title + IO_LIM + this.author + IO_LIM +
                this.description + IO_LIM + this.album + IO_LIM + this.disc + IO_LIM +
                this.number + IO_LIM + Integer.toString(this.rate) + IO_LIM + Double.toString(length)+ IO_LIM);
    }
    
    public void write(OutputStreamWriter fw) throws IOException{
        fw.write(this.path + IO_LIM + this.title + IO_LIM + this.author + IO_LIM +
                this.description + IO_LIM + this.album + IO_LIM + this.disc + IO_LIM +
                this.number + IO_LIM + Integer.toString(this.rate) + IO_LIM + Double.toString(length)+ IO_LIM);
    }
    
    public static String getIO_LIM(){
        return IO_LIM;
    }
    
    public void read(String path) throws FileNotFoundException, NoSuchElementException, ParseException{
        Scanner scan = null;
        File f = new File(path);
        scan = new Scanner(f,"UTF-8");
        scan.useDelimiter(IO_LIM);
        read(scan);
        if(scan != null) scan.close();
    }
    
    public void read(Scanner scan) throws NoSuchElementException, ParseException{
        this.path = scan.next();
        this.title = scan.next();
        this.author = scan.next();
        this.description = scan.next();
        this.album = scan.next();
        this.disc = scan.next();
        this.number = scan.next();
        this.rate = Integer.parseInt(scan.next());
        //--//initMedia();
        //--//this.length = player.getTotalDuration().toSeconds();
        this.length = Double.parseDouble(scan.next());
        //--//discardMedia();
    }
}

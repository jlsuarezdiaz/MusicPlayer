/*
 * Author: Juan Luis Suárez Díaz
 * July, 2015
 * Music Player
 */
package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.media.MediaException;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan Luis
 */
public class MusicPlayer {
    /**
     * Songs list.
     */
    private ArrayList<Song> songList;
    
    /**
     * Current song.
     */
    private Song currentSong;
    
    /**
     * Current song index.
     */
    private int currentSongIndex;
    
    /**
     * Array with frequency for each rating.
     */
    private double[] ratingsFreq;
    
    /**
     * Playing mode.
     */
    private PlayingMode playMode;
    
    /**
     * Songs history.
     */
    private ArrayList<Song> history;
    
    /**
     * Songs history position.
     */
    private int historyPos;
    
    /**
     * Indicates whether player is working.
     */
    private boolean playing;
    
    /**
     * Playing time.
     */
    private double playTime;
    
    /**
     * Randomizer.
     */
    private static Random rand = new Random();
    
    
    // ---------- DEFAULT ---------- //
    
    private double[] defaultRatings;
    
    private PlayingMode defaultMode;
    
    private String defaultPlaylistPath;
    
    private static final String IO_LIM = "\0";
    
    // ---------- PRIVATE METHODS ---------- //
    private void setEndOfMedia(Song s){
        s.setOnEndOfMedia(new Runnable() {
            @Override public void run() {
                s.stop();
                next();
            }
        });
    }
    
    // ---------- CONSTRUCTORS ---------- //
    
    /**
     * Default constructor.
     */
    public MusicPlayer(){
        songList = new ArrayList();
        currentSong = null;
        currentSongIndex = -1;
        playMode = defaultMode = PlayingMode.RANDOMBYRATE;
        playing = false;
        ratingsFreq = new double[Song.getMaxRating()+1];
        defaultRatings = new double[Song.getMaxRating()+1];
        
        for(int i = 0; i <= Song.getMaxRating(); i++)
            defaultRatings[i] = ratingsFreq[i] = 100.0 / (Song.getMaxRating()+1);
        /*for(int i = 0; i <= Song.getMaxRating(); i++)
            ratingsFreq[i] = 4.0;
        ratingsFreq[6] = 76;*/
            
        history = new ArrayList();
        historyPos = 0;
        playTime = 0;
        
        defaultPlaylistPath = "";
    }
    
    // ---------- SETTERS & GETTERS ---------- //
    
    public boolean isPlaying(){
        return playing;
    }
    
    public ArrayList<Song> getSongList(){
        return songList;
    }
    
    public Song getCurrentSong(){
        return currentSong;
    }
    
    public int getCurrentSongIndex(){
        return currentSongIndex;
    }
    
    /**
     * Sets the playing mode.
     * @param mode Mode to set.
     */
    public void setMode(PlayingMode mode){
        this.playMode = mode;
    }
    
    /**
     * Gets the playing mode.
     * @return playing mode.
     */
    public PlayingMode getMode(){
        return this.playMode;
    }
    
    /**
     * Gets ratings frequency in Random by Ratings mode.
     * @return ratings frequency.
     */
    public double[] getRatingsFreq(){
       return this.ratingsFreq;
    }
    
    public void setRatingsFreq(double[] frequencies){
        double sum = 0;
        for(double f : frequencies){
            if(f <= 0.0) throw new ArithmeticException("Todos los valores deben ser positivos.");
            sum += f;
        }
        for(int i = 0; i <= Song.getMaxRating(); i++){
            ratingsFreq[i] = (100.0 / sum) * frequencies[i];
        }
    }
    
    /**
     * Gets size of the play list.
     * @return Number of songs.
     */
    public int getPlaylistSize(){
        return songList.size();
    }
    
    /**
     * Gets the whole time of the playlist.
     * @return playlist time.
     */
    public double getPlaylistLength(){
        return playTime;
    }
    // ---------- PUBLIC METHODS ---------- //
    
    /**
     * Checks if player is empty of songs.
     * @return true if and only if the are no songs.
     */
    public boolean isEmpty(){
        return songList.isEmpty();
    }
    
    /**
     * Starts playing.
     */
    public void play() throws MediaException{
        if(!isEmpty()){
            currentSong.play();
            playing = true;
        }
    }
    
    /**
     * Pauses playing.
     */
    public void pause(){
        if(!isEmpty()){
            currentSong.pause();
            playing = false;
        }
    }
    
    /**
     * Stops playing.
     */
    public void stop(){
        if(!isEmpty()){
            currentSong.stop();
            playing = false;
        }
    }
    
    /**
     * Fast forward.
     */
    public void fastForward(){
        if(!isEmpty()){
            currentSong.fastForward();
        }
    }
    
    /**
     * Go backwards.
     */
    public void backward(){
        if(!isEmpty()){
            currentSong.backward();
        }
    }
    
    /**
     * Next song.
     */
    public void next() throws MediaException{
        if(!isEmpty()){
            if(currentSong != null) currentSong.stop();
            if(historyPos < history.size() - 1){
                historyPos++;
                currentSong = history.get(historyPos);
                currentSongIndex = songList.indexOf(currentSong);
                if(playing) currentSong.play();
            }
            else{
                switch(playMode){
                    case ONCE:
                        if(currentSong != null)stop();
                        else{
                            currentSongIndex = rand.nextInt(songList.size());
                            currentSong = songList.get(currentSongIndex);
                        }
                        break;
                    case SEQUENTIAL:
                        currentSongIndex = (currentSongIndex + 1) % songList.size();
                        currentSong = songList.get(currentSongIndex);
                        
                        break;
                    case REPEAT:
                        if(currentSong == null){
                            currentSongIndex = rand.nextInt(songList.size());
                            currentSong = songList.get(currentSongIndex);
                        }
                        break;
                    case RANDOM:
                        currentSongIndex = rand.nextInt(songList.size());
                        currentSong = songList.get(currentSongIndex);
                        
                        break;
                    case RANDOMBYRATE:
                        int rnd = rand.nextInt(100);
                        int rate = 0;
                        double acum = 0;
                        for(double d : ratingsFreq){
                            acum += d;
                            if(rnd >= acum) rate++;
                        }
                        ArrayList<Song> ratesongs = getSongsOfRate(rate);
                        if(ratesongs.isEmpty()) next();
                        else{
                            rnd = rand.nextInt(ratesongs.size());
                            currentSong = ratesongs.get(rnd);
                            currentSongIndex = songList.indexOf(currentSong);
                        }
                        break;
                }
                
                if(playing) currentSong.play();
                
                history.add(currentSong);
                historyPos++;
            }
        }
    }
    
    /**
     * Goes back to previous song.
     */
    public void back() throws MediaException{
        if(!isEmpty()){
            currentSong.stop();
            if(historyPos <= 1 && playing){
                currentSong.restart();
            }
            else{
                historyPos--;
                currentSong = history.get(historyPos);
                currentSongIndex = songList.indexOf(currentSong);
                if(playing) currentSong.play();
            }
        }
    }
    
    /**
     * Chooses a song and plays it.
     * @param s Song to play.
     */
    public void chooseSong(Song s) throws MediaException{
        int i = songList.indexOf(s);
        if(i != -1){
            history.add(currentSong);
            historyPos = history.size();
            currentSong.stop();
            currentSongIndex = i;
            currentSong = s;
            play();
        }
    }
       
    /**
     * Adds a new song.
     * @param s song to add.
     */
    public void add(Song s){
        boolean wasEmpty = isEmpty();
        setEndOfMedia(s);
        songList.add(s);
        playTime += s.getLength();
        if(wasEmpty) next();
    }
    
    /**
     * Adds a new list of songs.
     * @param songs List of songs to add.
     */
    public void add(ArrayList<Song> songs){
        boolean wasEmpty = isEmpty();
        for(Song s : songs){
            setEndOfMedia(s);
            songList.add(s);
            playTime += s.getLength();
        }
        if(wasEmpty) next();
    }
    
    /**
     * Adds all the songs in a given directory.
     * @param path Directory path.
     */
    public void addDirectory(String path){
        File dir = new File(path);
        File[] list = dir.listFiles();
        ArrayList<Song> songs = new ArrayList();
        if(list != null){
            int i = 1;
            for(File f : list){
                try{
                    Song s = new Song(f.getAbsolutePath());
                    s.setAlbum(path);
                    s.setNumber(Integer.toString(i));
                    songs.add(s);
                    i++;
                }
                catch(Exception ex){}
            }
            add(songs);
        }
        else throw new IllegalArgumentException("Expected directory file!");
    }
    
    /**
     * Adds all songs from a given folder, including every sub-directories in it.
     * @param path Directory path.
     */
    public void addDirectoryRecursive(String path){
        File dir = new File(path);
        File[] list = dir.listFiles();
        ArrayList<Song> songs = new ArrayList();
        if(list != null){
            int i = 1;
            for(File f : list){
                if(f.isDirectory()){
                    addDirectoryRecursive(f.getAbsolutePath());
                }
                else{
                    try{
                        Song s = new Song(f.getAbsolutePath());
                        s.setAlbum(path);
                        s.setNumber(Integer.toString(i));
                        songs.add(s);
                        i++;
                    }
                    catch(Exception ex){}
                }
            }
            add(songs);
        }
        else throw new IllegalArgumentException("Expected directory file!");  
    }
    
    /**
     * Gets list of songs with a given rate.
     * @param rate Rate to choose.
     * @return List of songs.
     */
    public ArrayList<Song> getSongsOfRate(int rate){
        ArrayList<Song> ratesongs = new ArrayList();
        for(Song s: songList){
            if(s.getRate() == rate){
                ratesongs.add(s);
            }
        }
        return ratesongs;
    }

        // ---------- IO-METHODS ---------- //
    
    public void write(String path) throws IOException{
        FileWriter fw = null;
        fw = new FileWriter(path);
        write(fw);
        if(fw != null) fw.close();
    }
    
    public void write(FileWriter fw) throws IOException{
        for(Song s : songList){
            s.write(fw);
        }
    }
    
    public void read(String path) throws FileNotFoundException, NoSuchElementException, ParseException{
        Scanner scan = null;
        File f = new File(path);
        scan = new Scanner(f);
        scan.useDelimiter(Song.getIO_LIM());
        read(scan);
        if(scan != null) scan.close();
    }
    
    public void read(Scanner scan) throws NoSuchElementException, ParseException{
        ArrayList<Song> songs = new ArrayList();
        while(scan.hasNext()){
            Song s = new Song();
            s.read(scan);
            songs.add(s);          
        }
        add(songs);
    }
    
    // ---------- DEFAULT SETTINGS ---------- //
    
    public void setDefaultSettings(double[] freq, PlayingMode mode, String playlist){
        this.defaultRatings = freq;
        double sum = 0;
        for(double f : defaultRatings){
            if(f <= 0.0) throw new ArithmeticException("Todos los valores deben ser positivos.");
            sum += f;
        }
        for(int i = 0; i <= Song.getMaxRating(); i++){
            defaultRatings[i] = (100.0 / sum) * defaultRatings[i];
        }
        this.defaultMode = mode;
        this.defaultPlaylistPath = playlist;
    }
    
    public double[] getDefaultRatings(){
        return this.defaultRatings;
    }
    
    public PlayingMode getDefaultPlayMode(){
        return this.defaultMode;
    }
    
    public String getDefaultPlayList(){
        return this.defaultPlaylistPath;
    }
    
    public void writeSettings(String path){
        FileWriter fw = null;
        try{
            fw = new FileWriter(path);
            for(int i = 0; i <= Song.getMaxRating(); i++){
                fw.write(Double.toString(defaultRatings[i]) + IO_LIM);
            }
            
            fw.write(this.defaultMode.toString() + IO_LIM +
                    this.defaultPlaylistPath + IO_LIM);
        }
        catch(IOException ex){
        }
        finally{
            try{
                if(fw != null) fw.close();
            }
            catch(IOException ex){}
        }
    }
    
    public void writeDefaultSettings(){
        writeSettings(".default.mps");
    }
    
    public void readSettings(String path){
        Scanner scan = null;
        File f = new File(path);
        try {
            scan = new Scanner(f);
            scan.useDelimiter(IO_LIM);

            for(int i = 0; i <= Song.getMaxRating(); i++){
                ratingsFreq[i] = defaultRatings[i] = Double.parseDouble(scan.next());
            }
            setRatingsFreq(ratingsFreq);
            
            playMode = defaultMode = PlayingMode.valueOf(scan.next());

            defaultPlaylistPath = scan.next();
            try{
                this.read(defaultPlaylistPath);
            }
        catch(Exception ex){}
        } catch (FileNotFoundException | NoSuchElementException ex) {}
        finally{
            if(scan != null) scan.close();
        }
    }
    
    public void readDefaultSettings(){
        readSettings(".default.mps");
    }
    
    public void setInitialDefault(){
        for(int i = 0; i <= Song.getMaxRating(); i++)
            defaultRatings[i] = ratingsFreq[i] = 100.0 / (Song.getMaxRating()+1);
        
        this.defaultMode = PlayingMode.RANDOMBYRATE;
        this.defaultPlaylistPath = "";
    }
}

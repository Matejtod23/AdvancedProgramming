package labs1.zad1;

import java.util.ArrayList;
import java.util.List;

interface IMP3{


    public void pressPlay();
    public void printCurrentSong();
    public void pressFWD();
    public void pressREW();
    public void pressStop();
    public void setIsPlaying(boolean b);
}
class Song{
    String title;
    String author;

    public Song(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public Song() {

    }

    @Override
    public String toString() {
        return "Song{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                '}';
    }
}

class MP3Player implements IMP3{
    List<Song> listOfSongs;
    int index;
    boolean isPlaying;

    public MP3Player(List<Song> listOfSongs) {
        this.listOfSongs = listOfSongs;
        index = 0;
        isPlaying = false;
    }

    @Override
    public void pressPlay() {
        if (!isPlaying){
            System.out.printf("Song %d is playing\n", index);
            setIsPlaying(true);
        }else {
            System.out.println("Song is already playing");
        }
    }

    @Override
    public void printCurrentSong() {
        System.out.println(listOfSongs.get(index).toString());
    }

    @Override
    public void pressFWD() {
        System.out.println("Forward...");
        setIsPlaying(false);
        if (index == listOfSongs.size()-1){
            index = 0;
        }else
            index++;
    }

    @Override
    public void pressREW() {
        System.out.println("Reward...");
        setIsPlaying(false);
        if (index == 0) {
            index = listOfSongs.size()-1;
        }else
            index--;
    }

    @Override
    public void pressStop() {
        System.out.printf("Song %d is paused\n", index);
        setIsPlaying(false);
    }

    @Override
    public void setIsPlaying(boolean b) {
        this.isPlaying = b;
    }

    @Override
    public String toString() {
        return "MP3Player{" +
                "currentSong=" + index +
                ", SongList=" + listOfSongs +
                '}';
    }
}

public class PatternTest {
    public static void main(String args[]) {
        List<Song> listSongs = new ArrayList<Song>();
        listSongs.add(new Song("first-title", "first-artist"));
        listSongs.add(new Song("second-title", "second-artist"));
        listSongs.add(new Song("third-title", "third-artist"));
        listSongs.add(new Song("fourth-title", "fourth-artist"));
        listSongs.add(new Song("fifth-title", "fifth-artist"));
        MP3Player player = new MP3Player(listSongs);


        System.out.println(player.toString());
        System.out.println("First test");


        player.pressPlay();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressPlay();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Second test");


        player.pressStop();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressStop();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
        System.out.println("Third test");


        player.pressFWD();
        player.printCurrentSong();
        player.pressFWD();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressPlay();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressStop();
        player.printCurrentSong();

        player.pressFWD();
        player.printCurrentSong();
        player.pressREW();
        player.printCurrentSong();


        System.out.println(player.toString());
    }
}

//Vasiot kod ovde

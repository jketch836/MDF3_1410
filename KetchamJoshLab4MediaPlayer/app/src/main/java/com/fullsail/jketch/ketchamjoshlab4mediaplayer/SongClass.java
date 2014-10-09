package com.fullsail.jketch.ketchamjoshlab4mediaplayer;

/**
 * Created by jketch on 10/7/14.
 */
public class SongClass {

    String songName;
    int cover;
    String songNameText;
    String artist;

    public SongClass (String nSong, int songId, String nSongText, String theArtist) {

        songName = nSong;
        cover = songId;
        songNameText = nSongText;
        artist = theArtist;

    }

    public String getnSong () {

        return songName;

    }


    public int getCover () {

        return cover;

    }


    public String getSongName () {

        return songNameText;

    }

    public String getArtist () {

        return artist;

    }

}

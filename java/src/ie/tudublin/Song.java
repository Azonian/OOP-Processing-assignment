//the csv that stores the songs must be called song.csv

package ie.tudublin;

import java.util.ArrayList;
import java.lang.Math;

//import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import processing.core.*;
//-----------------------------------------------------------------------------
public class Song extends PApplet{
	public PImage cover;
	public SoundFile audio;
	public Table beatCsv;
	
	public Song(TableRow tr) {
		
		this(
			tr.getString("CoverImage")
			,tr.getString("AudioFile")
			,tr.getString("BeatMap")
		);
	}
	/*
	public Song(String cover,String audio,String beatCsv) {
		println("Song created attempting to load image");
		PImage img = loadImage(cover);
		println("image in img");
		this.cover = img;
		println("image in this.cover");
		this.audio = audio;
		this.beatCsv = beatCsv;
	}
	*/
	public Song(){
		
	}
}//end song

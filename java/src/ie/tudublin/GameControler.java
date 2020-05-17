//the csv that stores the songs must be called song.csv

package ie.tudublin;

import java.util.ArrayList;

import java.lang.Math;

import java.io.*;

//import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import processing.core.*;
import processing.sound.*;

public class GameControler extends PApplet{
	boolean  debug;
	String dispalyMode;
	/*stores the curent location of the player in the menu system
	"OpenScreen":draws block captials B E A T until any user input
	"PlayOrCreate":draws two buttons
	"ChosesSongPlay":shows the album covers navigate by either moving mouse or arrow keys click to play one
	"ChosesSongCreate":shows the album covers navigate by either moving mouse or arrow keys click to play one and create a beat csv for it
	"Play":playing a song drawing all the relvant clickables etc ends and retursn to ChosesSongPlay when song ends
	"Create":plays a song wirting over the songs csv
	*/
	ArrayList<Song> songs = new ArrayList<Song>();
	ArrayList<ClickableBeat> clickables = new ArrayList<ClickableBeat>();
	
	/*stores the info for each song
	has headers: CoverImage,AudioFile,BeatMap*/
	
	Song songToPlay;
	float songFrame;
	
	float DefaultClickableLifeSpan;
	
	float clickableRadius;
	float playClickableX;
	float playClickableY;
	float createClickableX;
	float createClickableY;
	
	float largeTextSize;
	float smallTextSize;
	
	float postionInMenu;
	
	float menuItemW;
	float menuItemH;
	
	float menuItem0X;
	float menuItem0Y;
	
	float menuItem1X;
	float menuItem1Y;
	
	float menuItem2X;
	float menuItem2Y;
	
	float menuItem3X;
	float menuItem3Y;
	
	Table clickableTable;
	
	String beatMapFileHeader;
	
	//PrintWriter output;
	
	public void loadSongs() {
		if(debug){
			println("loadSongs called");
		}
		
	    Table songTable = loadTable("SongsInfo.csv","header");
        
		for(TableRow tr:songTable.rows())
        {
            Song tempSongStore = new Song();
			tempSongStore.cover = loadImage(tr.getString("CoverImage"));
			println("audio file load" + tr.getString("AudioFile"));
			tempSongStore.audio = new SoundFile(this, tr.getString("AudioFile"));
			println("tempSongStore.audio is " + tempSongStore.audio); 
			//tempSongStore.audio.play();
			tempSongStore.beatCsv = tr.getString("BeatMap");
            songs.add(tempSongStore);
			if(debug){
				println("loaded song" + tempSongStore);
			}
        }
		if(debug){
			println("loadSongs termiating");
		}
	}
	
	public void printSongs(){
		println("songs loaded:");
		for(Song s:songs){
			println(s);
			//s.audio.play();
		}
	}
	
	public void settings(){
		//size(500,500);
		
		fullScreen();
	} 
	 
	public void setup(){
		
		debug = true;
		textAlign(CENTER);
		clickableRadius = width/20.f;
		
		playClickableX = width/3.f;
		playClickableY = height/2.f;
		
		createClickableX = 2.f*(width/3.f);
		createClickableY = height/2.f;
		
		largeTextSize = height/5.f;
		smallTextSize = height/25.f;
		
		postionInMenu = 0.f;
		
		menuItemW = 4.f*(width/11.f);
		menuItemH = 4.f*(height/11.f);
		
		menuItem0X = width/11.f;
		menuItem0Y = height/11.f;
		
		menuItem1X = 6.f*(width/11.f);
		menuItem1Y = height/11.f;
		
		menuItem2X = width/11.f;
		menuItem2Y = 6.f*(height/11.f);
		
		menuItem3X = 6.f*(width/11.f);
		menuItem3Y = 6.f*(height/11.f);
		
		DefaultClickableLifeSpan = 300.f;
		
		loadSongs();
		printSongs();
		
		dispalyMode = "OpenScreen";
		
		beatMapFileHeader = "rowData,Xcord,Ycord";
		
		noStroke();
	 }
	 
	 public void draw(){
		 background(0);
		 switch(dispalyMode) {//optimse case postions
			case "OpenScreen":
				drawOpenScreen();
				break;
			case "PlayOrCreate":
				drawPlayOrCreate();
				break;
			case "Play":
				drawSongClickables();
				break;
			case "Create":
				drawCreateTrack();
				break;
			case "CreateMenu":
				drawCreateTrackMenu();
				break;
			case "PlayMenu":
				drawSongMenu();
				break;
		}
	}
	
	public void mousePressed() {//optimse case postions
		switch(dispalyMode) {
			case "OpenScreen":
				openScreenMouseCheck();
				break;
			case "PlayOrCreate":
				playOrCreateMouseCheck();
				break;
			case "Play":
				songClickablesMouseCheck();
				break;
			case "Create":
				createTrackMouseCheck();
				break;
			case "CreateMenu":
				createTrackMenuMouseCheck();
				break;
			case "PlayMenu":
				songMenuMouseCheck();
				break;
		}
	}	

	//open screen handling
	public void drawOpenScreen(){
		textSize(largeTextSize);
		text("B E A T",width/2.f,height/2.f);
		textSize(smallTextSize);
		text("click to start",width/2.f,height/1.5f);
	}
	
	public void openScreenMouseCheck(){
		goToPlayOrCreate();
	}
	
	//play or create handling
	public void goToPlayOrCreate(){
		dispalyMode = "PlayOrCreate";
		ClickableBeat temp = new ClickableBeat(1.f,playClickableX,playClickableY);
		clickables.add(temp);
		temp = new ClickableBeat(1.f,createClickableX,createClickableY);
		clickables.add(temp);
	}
	
	public void drawPlayOrCreate(){
		fill(255);
		drawClickables();
		textSize(smallTextSize);
		fill(0);
		text("PLAY",playClickableX,playClickableY + width/100);
		text("CREATE",createClickableX,createClickableY + width/100);
	}
	
	public void playOrCreateMouseCheck(){
		//MouseCheckClickableBeats()
		if(pythagrosTherom(mouseX,mouseY,playClickableX,playClickableY) <= clickableRadius)
		{
			goToPlayMenu();
		}
		if(pythagrosTherom(mouseX,mouseY,createClickableX,createClickableY) <= clickableRadius)
		{
			goToCreateMenu();
		}
	}
	
	//gernal rediring tech
	public void drawClickables(){
		for(ClickableBeat b:clickables){
			circle(b.xCord,b.yCord,clickableRadius*2);
		}
	}
	
	//play handling
	public void goToPlay(){
		println("go to play called");
		dispalyMode ="Play";
		clickableTable = loadTable(songToPlay.beatCsv,"header");
		println( songToPlay.beatCsv + " opemed");
		songToPlay.audio.play();
		songFrame = 0.f;
		fill(255);
	}
	
	public void drawSongClickables(){
		if(songFrame < clickableTable.getRowCount()){
			for(ClickableBeat b:clickables){
				println("drawing circle a" +b.xCord+" "+b.yCord + " with a width of " + (b.lifeSpan/DefaultClickableLifeSpan)*2*clickableRadius);
				circle(b.xCord,b.yCord,(b.lifeSpan/DefaultClickableLifeSpan)*2*clickableRadius);
				b.lifeSpan--;
			}
			if(clickableTable.getFloat((int)songFrame,"rowData") == 1){
				ClickableBeat temp = new ClickableBeat();
				temp.xCord = clickableTable.getFloat((int)songFrame,"Xcord");
				temp.yCord = clickableTable.getFloat((int)songFrame,"Ycord");
				temp.lifeSpan = DefaultClickableLifeSpan;
				clickables.add(temp);
			}
			else{
				println("row had 0 in row data");
			}
			songFrame++;
			println("incremented song frame frame is now = " + songFrame);
		}
		if(!(songToPlay.audio.isPlaying())){
			goToPlayMenu();
		}
	}
	
	/*if(!(songToPlay.audio.isPlaying())){
			createTrackClickablesToFile();
			goToCreateMenu();
		}*/
	
	public void songClickablesMouseCheck(){
		ArrayList<ClickableBeat> clickablesToDelete = new ArrayList<ClickableBeat>();
		
		for(ClickableBeat b:clickables){
			if(pythagrosTherom(mouseX,mouseY,b.xCord,b.yCord) <= (b.lifeSpan / DefaultClickableLifeSpan)*clickableRadius){
				clickablesToDelete.add(b);
			}
		}
		for(ClickableBeat b:clickablesToDelete){
			clickables.remove(b);
		}
		//pythagrosTherom(mouseX,mouseY,createClickableX,createClickableY)
	}
	
	//PlayMenu handling
	public void goToPlayMenu(){
		dispalyMode = "PlayMenu";
		clickables.clear();
	}
	
	public void drawSongMenu(){
		image(songs.get((int)postionInMenu).cover,menuItem0X,menuItem0Y,menuItemW,menuItemH);
		if(postionInMenu+1 < songs.size()){
			image(songs.get((int)(postionInMenu*4+1)).cover,menuItem1X,menuItem1Y,menuItemW,menuItemH);
		}
		if(postionInMenu+2 < songs.size()){
			image(songs.get((int)(postionInMenu*4+2)).cover,menuItem2X,menuItem2Y,menuItemW,menuItemH);
		}
		if(postionInMenu+3 < songs.size()){
			image(songs.get((int)(postionInMenu*4+3)).cover,menuItem3X,menuItem3Y,menuItemW,menuItemH);
		}
	}
	
	public void songMenuMouseCheck (){
		if(mouseWithInBox(menuItem0X,menuItem0Y,menuItemW,menuItemH)){
			songToPlay = songs.get((int)(postionInMenu*4));
			goToPlay();
		}
		if(mouseWithInBox(menuItem1X,menuItem1Y,menuItemW,menuItemH) && (postionInMenu*4+1 < songs.size())){
			songToPlay = songs.get((int)(postionInMenu*4 + 1));
			goToPlay();
		}
		if(mouseWithInBox(menuItem2X,menuItem2Y,menuItemW,menuItemH) && (postionInMenu*4+2 < songs.size())){
			songToPlay = songs.get((int)(postionInMenu*4 + 2));
			goToPlay();
		}
		if(mouseWithInBox(menuItem3X,menuItem3Y,menuItemW,menuItemH) && (postionInMenu*4+3 < songs.size())){
			songToPlay = songs.get((int)(postionInMenu*4+3));
			goToPlay();
		}
		//navigate back and foward buttons
		//if(postionInMenu != 0){	
		//}
		//if(postionInMenu + 4 >= songs.size()){	
		//}
		if(debug){
			println("songToPlay = " + songToPlay);
		}
	}
	
	//create handling
	public void goToCreate(){
		dispalyMode = "Create";
		//println("attempting to run debug");
		//println("attempting to play audio" + songToPlay.audio);
		songToPlay.audio.play();
		songFrame = 0.f;
		fill(255);
	}
	
	public void drawCreateTrack(){
		println("attempting to draw clickables");
		drawClickables();
		songFrame++;
		if(!(songToPlay.audio.isPlaying())){
			createTrackClickablesToFile();
			goToCreateMenu();
		}
	}
	
	public void createTrackMouseCheck(){
		println("attempting to add a clickable");
		float newBeatX;
		float newBeatY;
		
		newBeatX = mouseX;
		newBeatY = mouseY;
		
		for(ClickableBeat b:clickables){
			if(pythagrosTherom(mouseX,mouseY,b.xCord,b.yCord) <= clickableRadius){
				newBeatX = b.xCord;
				newBeatY = b.yCord;
				break;//i know this is bad but it runs fast mabye in
			}
		}

		ClickableBeat temp = new ClickableBeat(songFrame,newBeatX,newBeatY);
		clickables.add(temp);
	}
	
	public void createTrackClickablesToFile(){
		println("amptting to open ./data/" + songToPlay.beatCsv);
		PrintWriter output = createWriter("./data/"+songToPlay.beatCsv);
		println("worked");
		output.println(beatMapFileHeader);
		float lastLifeSpan = 0;
		for(ClickableBeat b:clickables){
			for(int i = 0;i < b.lifeSpan - lastLifeSpan;i++){
				output.println("0,,");
			}
			lastLifeSpan = b.lifeSpan;
			output.println("1," + b.xCord + "," + b.yCord);
		}
		
		output.flush(); // Writes the remaining data to the file
		output.close();
	}
	
	//createMenu handling
	public void goToCreateMenu(){
		dispalyMode = "CreateMenu";
		clickables.clear();
	}
	
	public void drawCreateTrackMenu(){//turn this into methods
		image(songs.get((int)postionInMenu).cover,menuItem0X,menuItem0Y,menuItemW,menuItemH);
		if(postionInMenu+1 < songs.size()){
			image(songs.get((int)(postionInMenu*4+1)).cover,menuItem1X,menuItem1Y,menuItemW,menuItemH);
		}
		if(postionInMenu+2 < songs.size()){
			image(songs.get((int)(postionInMenu*4+2)).cover,menuItem2X,menuItem2Y,menuItemW,menuItemH);
		}
		if(postionInMenu+3 < songs.size()){
			image(songs.get((int)(postionInMenu*4+3)).cover,menuItem3X,menuItem3Y,menuItemW,menuItemH);
		}
		//navigate back and foward buttons
		//if(postionInMenu != 0){	
		//}
		//if(postionInMenu + 4 >= songs.size()){	
		//}
	}
	
	public void createTrackMenuMouseCheck(){//turn this into methods
		if(mouseWithInBox(menuItem0X,menuItem0Y,menuItemW,menuItemH)){
			songToPlay = songs.get((int)(postionInMenu*4));
			goToCreate();
			println("loaded song " + songToPlay + " to song to play");
		}
		if(mouseWithInBox(menuItem1X,menuItem1Y,menuItemW,menuItemH) && (postionInMenu*4+1 < songs.size())){
			songToPlay = songs.get((int)(postionInMenu*4 + 1));
			goToCreate();
		}
		if(mouseWithInBox(menuItem2X,menuItem2Y,menuItemW,menuItemH) && (postionInMenu*4+2 < songs.size())){
			songToPlay = songs.get((int)(postionInMenu*4 + 2));
			goToCreate();
		}
		if(mouseWithInBox(menuItem3X,menuItem3Y,menuItemW,menuItemH) && (postionInMenu*4+3 < songs.size())){
			songToPlay = songs.get((int)(postionInMenu*4+3));
			goToCreate();
		}
		//navigate back and foward buttons
		//if(postionInMenu != 0){	
		//}
		//if(postionInMenu + 4 >= songs.size()){	
		//}
		if(debug){
			println("songToPlay = " + songToPlay);
		}
	}
	
	//genaralMathfunctions
	public boolean  mouseWithInBox(float boxX ,float boxY,float boxW, float boxH){
		//mouseX mouseY
		if((mouseX >= boxX)&&(mouseX <= boxX+boxW)&&(mouseY >= boxY)&&(mouseY <= boxY+boxH)){
			return true;
		}
		else{
			return false;
		}
		
	}
	
	public float pythagrosTherom(float x0,float y0,float x1,float y1){
		float deltaX = x0 - x1;
		float deltaY = y0 - y1;
		return (float)Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
	}
}


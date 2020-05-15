package ie.tudublin;

import java.util.ArrayList;

import java.lang.Math;

//import processing.core.PApplet;
import processing.data.Table;
import processing.data.TableRow;

import processing.core.*;
//-----------------------------------------------------------------------------
public class ClickableBeat extends PApplet{
	public float lifeSpan;
	public float xCord;
	public float yCord;
	public boolean clicked = false;
	
	public ClickableBeat(TableRow tr) {
		this(
			tr.getInt("LifeSpan"),
			tr.getFloat("XCord"),
			tr.getFloat("YCord")
		);
	}
	
	public  ClickableBeat(float lifeSpan, float xCord,float yCord){
		this.lifeSpan = lifeSpan;
		this.xCord = xCord;
		this.yCord = yCord;
	}
}//end ClickableBeat;
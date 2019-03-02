package lia;
import lia.api.GameState;
import lia.api.UnitData;
import processing.core.PApplet;
import processing.core.PFont;

/**
 * The coordinate system is different that you may be used to. x coordinate is normal, and is measured from left to right, y coordinate however is measured from top to bottom which flips the units. That means that the point (0,0) is on the upper left corner. This program renders the game map on a 1920*1080 screen, with some space left over. The program is based on the Procesing library available for free on https://processing.org/. The library first calls settings() then setup() and then 60 times per second draw().
 * 
 * @author Gregor Kikelj
 *
 */
public class rendering extends PApplet{
		
	/*	Setup instructions:
	 * 	-add "compile group: 'org.processing', name: 'core', version: '3.3.7'" to build.gradle dependencies
	 * 	-set pixelSize to desired number
	 */
	
	int pixelSize = 10; //Change to resize the window
	
	//----------------------------------------------------//
	
	static Analyzer analyzer;
	int time = 0;
	int offset = 0;
	boolean playVid = false;
	
	public PFont f;//Not mandatory but nice to have to be able to render data on screen	
	int mapWidth = 176, mapHeight = 99;
	
	int shift=pixelSize*mapWidth;//x coordinate of the end of the map
	public static boolean gameDone=false;//if the game is not started we shouldn't render game data as it doesn't exist
	static GameState state;//we need a pointer so we know what to draw. 
	
	String txt ="";
		
	public void settings(){
		size(pixelSize*mapWidth,pixelSize*mapHeight);//we define the screen size. If your monitor doesn't support such a large resolution, try modfying the program.
	}
	
	public void setup(){
		f=createFont("Arial",20,true);//we create the font
	}
	
	public void draw(){
		if(!gameDone){//if the game is not started don't draw anything
			return;
		}
		formatOutput();
		background(255,255,255);//white background-defined by rgb
		fill(0,0,0);//the shapes we draw will have a black interior
		for(int i=0;i<mapWidth;i++){
			for(int j=0;j<mapHeight;j++){
				if(Constants.MAP[i][j]){
					rect(pixelSize*i,pixelSize*j,pixelSize,pixelSize);//we draw the map
				}
			}
		}
		fill(255,0,0);//switch to green
		for(UnitData u:state.units) {
			ellipse(pixelSize*u.x,pixelSize*u.y,pixelSize,pixelSize);//make an ellipse for our unit
		}
		text(txt,10,10);
	}
	
	public void formatOutput()
	{
		long lastTime = 0;
		
		if(playVid && System.currentTimeMillis() - lastTime >= 100 && offset < analyzer.vars.get("GameState").size()-1)
		{
			offset++;
			lastTime = System.currentTimeMillis();
		}
		if(offset == analyzer.vars.get("GameState").size()-1)	playVid = !playVid;
		
		
		state = (GameState)(analyzer.vars.get("GameState").get(0+offset));
		time = Math.round(state.time*10);
		txt = "Frame: " + time + "\n\r";
				
		for(String s : analyzer.vars.keySet())
		{
			if(s.endsWith((char)0 + Integer.toString(time)))
			{
				for(Object o : analyzer.vars.get(s))
				{
					txt += s.substring(0, s.indexOf((char)0)) + ": " + o + "\n\r";
				}
			}
		}
	}
	
	public void keyPressed()
	{
		if(key == CODED)
		{
			if(keyCode == LEFT)
			{
				int curr = analyzer.vars.get("GameState").indexOf(state);
				
				if(curr < 1)	offset = analyzer.vars.get("GameState").size()-1;
				else	offset--;
			}
			
			if(keyCode == RIGHT)
			{
				int curr = analyzer.vars.get("GameState").indexOf(state);
				
				if(curr > analyzer.vars.get("GameState").size()-2)	offset = 0;
				else	offset++;
			}
		}
		if(key == ' ')
		{
			playVid = !playVid;
		}
	}
}
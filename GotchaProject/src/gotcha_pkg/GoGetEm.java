/***************************************************

  Name: Andres Alvarez
  Date: 05/01/2020
  
  
  Program name: Gotcha    
  Program description: A program to create a disk game that has the user click on a moving circle and keeps score
  
****************************************************/
package gotcha_pkg;

import processing.core.PApplet;
import java.util.ArrayList;
import java.util.Random;


import processing.core.PImage;   // For background image

public class GoGetEm extends PApplet {
	// Timer 
	int timer;
	
	//length of game milliseconds
	int gameTime = 10 * 1000;
	

    // Keep track of current score
    int score = 0;

    // Canvas size
    final int canvasWidth  = 500;
    final int canvasHeight = 500;
    
    // declare disks
    float [] y = {55, 120, 160, 205};
    

    

   public static void main(String[] args){
        PApplet.main("gotcha_pkg.GoGetEm");
    }
   ArrayList<Disk> disks = new ArrayList<Disk>();
   
   
   
    public void settings() {
        size(canvasWidth, canvasHeight);
        smooth();
    }
    
    // setup() runs one time at the beginning of your program
    public void setup() {
    	background(255, 255, 255);
    	
    	//start
    	timer = millis() + gameTime;
    	
    	for (int i = 0; i < 4; i++) {
        	disks.add(new Disk(random(0, 255), random(0,255), random(0,255),
        			random(0,255), y[i], 1 + (2 *i)));
    		}
    }
    	
    
    	

    // draw() is called repeatedly in an infinite loop.
    // By default, it is called about 60 times per second.
    public void draw() {
        // Erase the background, if you don't, the previous shape(s) will 
        // still be displayed
        eraseBackground();

        for (int j = 0; j<disks.size(); j++) {
        	disks.get(j).calcCoords();
        	disks.get(j).drawShape();
        	disks.get(j).displayPointValue();
        }

        // Display player's score 
        text("Score: " + this.score, 250, 430);
        
        // timer for the end of game
        if (millis()>= timer) {
        		//clear canvas
        		textSize(20);
        		background (134,200, 255);
        		text("Great Job!", 250, 200);
        		text("Your final score is: ", + this.score, 60, 430);

        		
        }
    }

    public void eraseBackground() {      
        // White background:
        background(255);
        
        /* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
           | Use the following lines to display an image in the background. |
           | You will need to bring a .png file into your package. The path |
           | given below should be replaced by your path and png file name. |
           |                                                                |
           |   PImage bg = loadImage("hw_pkg/moon_walk.png");               |
           |   background(bg);                                              |
           ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/
    }

    // mousePressed() is a PApplet method that you will override.
    // This method is called from PApplet one time when the mouse is pressed.
    public void mousePressed() {
        // Draw a circle wherever the mouse is
        int mouseWidth  = 20;
        int mouseHeight = 20;
        fill(0, 132, 0);
        ellipse(mouseX, mouseY, mouseWidth, mouseHeight);
        
        for (int i = 0; i<disks.size(); i++) {
        
        // Check whether the click occurred within range of the shape
        if ((this.mouseX >= (disks.get(i).x - disks.get(i).targetRange)) && (this.mouseX <= (disks.get(i).x + disks.get(i).targetRange)) && 
        	    (this.mouseY >= (disks.get(i).y - disks.get(i).targetRange)) && (this.mouseY <= (disks.get(i).y + disks.get(i).targetRange))) {
            
            // Update score:
        		score = score + disks.get(i).pointValue;
            System.out.println("DBG:  HIT!");
        		}
        }
    }

    // Create a Disk class that you will use to create one or more disks with each
    // disk having a color, speed, position, etc.
    class Disk {
 
    	Random rand = new Random();
    		

        // Size of disk
    		final int shapeWidth = 50 ;
        final int shapeHeight = 25;

        // Point value of disk
        int pointValue;

        // Position of disk - keep track of x and y position of disk
        float x;
        float y;

        // Horizontal speed of disk
        int xSpeed = (int)(Math.random() * 8 + 1);

        // It's hard to click a precise pixel on a disk, to make it easier we can
        // allow the user to click somewhere on the disk.
        // You might want to make the scoring space be a rectangle fitted tightly
        // to the disk - it's easier than calculating a rounded boundary.
        int targetRange = 25;

        float red;
        float green;
        float blue;

        // The constructor could be extended to accept other disk characteristics
        Disk(float red, float green, float blue, float f, float y2, int i) {
            this.red   = red;
            this.green = green;
            this.blue  = blue;
            this.x = i;
            this.y = y2;
        }
        
        Disk() {
            //this(0, 100, 255);
        }

        public void calcCoords() {      
            // Compute the x position where the shape will be drawn
        		this.x += xSpeed;

            // If the x position is off right side of the canvas, reverse direction of 
            // movement:
        		if (this.x > 500) {
                // Log a debug message:
                System.out.println("DBG:  <---  Change direction, go left because x = " + this.x);

                // Recalculate:
                this.xSpeed = -1 * this.xSpeed;
            }

            // If the x position is off left side of the canvas, reverse direction of 
            // movement:
                if (this.x < 0) {
                // Log a debug message:
                System.out.println("DBG:      ---> Change direction, go right because x = " + this.x + "\n");
                
                // Recalculate:
                this.xSpeed = -1 * this.xSpeed;
                }
        
        }
                
                
        public void drawShape() {
            // Select color, then draw the shape at computed x, y location
            fill(red,green,blue);
            ellipse(x, y, shapeWidth, shapeHeight);
        }

        public void displayPointValue() {
            // Draw the text at computed x, y location
        		this.pointValue = xSpeed*10;
            textSize(15);
            fill(225,225,225);
            textAlign(CENTER);
            text(pointValue, x, y);
        }
    }
}
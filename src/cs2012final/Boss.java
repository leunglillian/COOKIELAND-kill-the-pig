package cs2012final;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* Lillian Leung
* CS2012
* 01 & 02
* Description: This is the Boss class which will create a Boss object.
* 			   Some purposes of this methods are:
* 					- generate a boss
* 					- set the size of the boss's image
* 					- set the position of the boss
* 					- check how many times the boss has been shot
* Other Comments: 
*/

public class Boss extends ImageView{
	private Image boss = new Image("images/boss.png");
	
	private int row, col; //stores the row and col amount of the game pane
	private int xPos, yPos; //stores the (x, y) coordinates of the current object

	private int shotCount = 0; //stores how many times the current object has been "shot"

	//constructor that creates a boss
	public Boss(Sprite sprite, int row, int col, double size) {
		Random rand = new Random();
		
		this.setImage(boss);
		
		this.row = row;
		this.col = col;
		
		this.setFitHeight(size);
		this.setFitWidth(size);
		
		//generate random (x, y) coordinates
		this.xPos = rand.nextInt(row);
		this.yPos = rand.nextInt(col);
		//make sure the coordinates are not the asme as the sprite's
		while(this.xPos == sprite.getXPos() && this.yPos == sprite.getYPos()) {
			this.xPos = rand.nextInt(row);	
		}
	}

	
	public int getXPos() {
		return this.xPos;
	}

	public int getYPos() {
		return this.yPos;
	}
	
	public int getShotCount() {
		return this.shotCount;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setShotCount(int shotCount, Sprite sprite) {
		Random rand = new Random();
		
		int prevX = this.xPos;
		int prevY = this.yPos;
		
		this.xPos = rand.nextInt(row);
		this.yPos = rand.nextInt(col);
		
		//make sure that the Boss will not generate where the sprite is
		while((this.xPos == sprite.getXPos() && this.yPos == sprite.getYPos()) || (this.xPos == prevX && this.yPos == prevY)) {
			this.xPos = rand.nextInt(row);	
		}
		
		this.shotCount = shotCount;
	}

}

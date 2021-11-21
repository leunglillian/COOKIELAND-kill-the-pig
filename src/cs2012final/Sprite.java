package cs2012final;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;


/* Lillian Leung
* CS2012
* 01 & 02
* Description: This is the Sprite class which will create a Sprite object.
* 			   Some purposes of this methods are:
* 					- generate a player sprite
* 					- set the size of the sprite's image
* 					- allow the sprite to move
* 					- check whether the player has died or won
* Other Comments: 
*/


public class Sprite extends ImageView{
	private Image spriteRight = new Image("images/sprite right.png");
	private Image spriteLeft = new Image("images/sprite left.png");
	private Image spriteUp = new Image("images/sprite up.png");
	private Image spriteDown = new Image("images/sprite down.png");
	
	private boolean hasCollectedAllAmmo = false; //checks to see if the player has collected all 5 ammunition from the grid
	private boolean hasLost = false; //checks to see if the player has lost/died
	private boolean hasWon = false; //checks to see if the player has won
	
	private int ammoCount = 0; //stores how much ammunition the player has
	private int stepCount = 0; //stores how many steps the player has taken
	
	private int row, col; //stores the row and col amount of the game pane
	private int xPos, yPos; //stores the (x, y) coordinates of the current object
	
	//constructor that creates a sprite
	public Sprite(int row, int col, double size) {
		Random rand = new Random();
		
		//set spriteDown as the default 
		this.setImage(spriteDown);
		
		this.setFitWidth(size);
		this.setFitHeight(size);
		
		this.row = row;
		this.col = col;		
		
		//generate random cooridinates
		this.xPos = rand.nextInt(row);
		this.yPos = rand.nextInt(col);
	}
	
	//method that moves a sprite to the right
	public void moveRight(GridPane gamePane) {		
		this.setImage(this.spriteRight);
		
		//checks if the sprite will be in bound
		if(this.xPos + 1 < row) {
			this.xPos++;
		}
		
		//updates the coordinates
		gamePane.getChildren().remove(this);
		gamePane.add(this, this.xPos, this.yPos);		
		
		this.stepCount++;
	}
	
	//method that moves a sprite to the left
	public void moveLeft(GridPane gamePane) {
		this.setImage(this.spriteLeft);
		
		//checks if the sprite will be in bound
		if(this.xPos - 1 > -1) {
			this.xPos--;
		}
		
		gamePane.getChildren().remove(this);
		gamePane.add(this, this.xPos, this.yPos);	
		
		this.stepCount++;
	}
	
	//method that moves a sprite up
	public void moveUp(GridPane gamePane) {
		this.setImage(this.spriteUp);
		
		//checks if the sprite will be in bound
		if(this.yPos - 1 > -1) {
			this.yPos--;
		}
		
		gamePane.getChildren().remove(this);
		gamePane.add(this, this.xPos, this.yPos);	
		
		this.stepCount++;
	}
	
	//method that moves a sprite down
	public void moveDown(GridPane gamePane) {
		this.setImage(this.spriteDown);
		
		//checks if the sprite will be in bound
		if(this.yPos + 1 < col) {
			this.yPos++;
		}
		
		gamePane.getChildren().remove(this);
		gamePane.add(this, this.xPos, this.yPos);	
		
		this.stepCount++;
	}
	
	public int getAmmoCount() {
		return this.ammoCount;
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public boolean getHasLost() {
		return this.hasLost;
	}
	
	public boolean getHasWon() {
		return this.hasWon;
	}
	
	public int getStepCount() {
		return this.stepCount;
	}
	
	public boolean getHasCollectedAllAmmo() {
		return this.hasCollectedAllAmmo;
	}
	
	public void setAmmoCount(int ammoCount) {
		//check that the new ammunition count is in bounds
		if(ammoCount < 6 && ammoCount > -1) {
			this.ammoCount = ammoCount;
		}
	}
	
	public void setHasCollectedAllAmmo(boolean hasCollectedAllAmmo) {
		this.hasCollectedAllAmmo = hasCollectedAllAmmo;
	}
	
	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
	}
	
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setHasLost(boolean hasLost) {
		this.hasLost = hasLost;
	}
	
	public void setHasWon(boolean hasWon) {
		this.hasWon = hasWon;
	}
}

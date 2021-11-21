package cs2012final;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/* Lillian Leung
* CS2012
* 01 & 02
* Description: This is the Ammunition class which will create an Ammunition object.
* 			   Some purposes of this methods are:
* 					- generate ammunition
* 					- set the x and y positions of the ammunition
* Other Comments: 
*/

public class Ammunition extends ImageView{
	private Image ammo = new Image("images/chocolate.png");
	
	private int xPos, yPos; //stores the (x, y) coordinates of the current object
	
	//constructor that creates an Ammunition
	public Ammunition(int row, int col, double size) {
		Random rand = new Random();
		
		this.setImage(ammo);
		this.setFitHeight(size);
		this.setFitWidth(size);
		
		//generate random (x, y) coordinates
		this.xPos = rand.nextInt(row);
		this.yPos = rand.nextInt(col);		
	}
	
	public int getXPos() {
		return this.xPos;
	}
	
	public int getYPos() {
		return this.yPos;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
}

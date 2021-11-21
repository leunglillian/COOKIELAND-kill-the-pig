package cs2012final;

import java.util.Random;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Obstacle extends ImageView{
	private Image obstacle = new Image("images/milk.png");
	private Image obstacleChoco = new Image("images/chocolate milk.png");
	private Image obstacleStrawberry = new Image("images/strawberry milk.png");
	
	private int xPos, yPos; //stores the (x, y) coordinates of the current object
	
	private boolean isChocoMilk = false; //checks if the obstacle has been touched bt a player before. If it has, it becomes "chocolate milk".
	private boolean isStrawberryMilk = false; //checks if the obstacle is strawberry milk (rare)
	
	public Obstacle(Sprite sprite, int row, int col, double size) {
		this.setImage(this.obstacle);
		
		this.setFitHeight(size);
		this.setFitWidth(size);		
		
		Random rand = new Random();
		//generates random coordinates
		
		//there is a 1/3 chance that the milk is strawberry milk
		if(rand.nextInt(3) == 0) {
			this.isStrawberryMilk = true;
			this.setImage(this.obstacleStrawberry);
		}
		
		this.xPos = rand.nextInt(row);
		this.yPos = rand.nextInt(col);
		//checks that the obstacle and sprite do not have the same coordinates
		while(this.xPos == sprite.getXPos() && this.yPos == sprite.getYPos()) {
			this.xPos = rand.nextInt(row);
			this.yPos = rand.nextInt(col);
		}
	}
	
	public int getXPos() {
		return this.xPos;
	}

	public int getYPos() {
		return this.yPos;
	}
	
	public boolean getIsChocoMilk() {
		return this.isChocoMilk;
	}
	
	public boolean getIsStrawberryMilk() {
		return this.isStrawberryMilk;
	}
	
	public void setXPos(int xPos) {
		this.xPos = xPos;
	}
	
	public void setYPos(int yPos) {
		this.yPos = yPos;
	}
	
	public void setIsChocoMilk(boolean isChocoMilk) {
		if(isChocoMilk) {
			this.setImage(obstacleChoco);
		}
		this.isChocoMilk = isChocoMilk;
	}
}

package cs2012final;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/* Lillian Leung
* CS2012
* 01 & 02
* Description: This is the Grid class which will create a Grid object.
* 			   The purpose of this method is to generate a game grid 
* Other Comments: 
*/


public class Grid extends GridPane{
	private Image backgroundImage = new Image("images/background.png");

	private double height, width; //stores the width and height of the game pane
	private int row, col; //stores the row and col amount of the game pane
	
	//constructor which creates a new grid
	public Grid(double width, double height, int row, int col) {
		this.height = height;
		this.width = width;
		this.row = row;
		this.col = col;
		
		this.setMinHeight(height);
		this.setMinWidth(width);
		this.setMaxHeight(height);
		this.setMaxWidth(width);
		this.setVgap(0);
		this.setHgap(0);
		this.setAlignment(Pos.CENTER);
				
		//add a background to the grids
		ImageView background;
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				background = new ImageView(this.backgroundImage);
				background.setFitHeight(width/row);
				background.setFitWidth(width/row);
				this.add(background, i, j);
			}
		}
	}

	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public double getGridHeight() {
		return this.height;
	}
	
	public double getGridWidth() {
		return this.width;
	}
}

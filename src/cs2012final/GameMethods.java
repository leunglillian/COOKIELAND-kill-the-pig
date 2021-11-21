package cs2012final;

import java.util.Random;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/* Lillian Leung
* CS2012
* 01 & 02
* Description: This class is where all of the methods for gameplay are stored.
* 			   Some functions of this methods are:
* 					- generating a game grid
* 			   		- player movement
* 					- spawning in obstacles
* 					- spawning in a boss 			   
* Other Comments: I had some trouble making it so that my ammunition would not spawn on top of my obstacles
*/


public class GameMethods{		
	private Stage window; //stores the game window
	private Grid grid; //stores the game pane
	private Sprite sprite; //stores a sprite
	private Boss boss; //stores a boss
	private Obstacle[] obstacleArray; //stores an array of obstacles 
	private Ammunition[] ammoArray; //stores an array of ammunition
	private Rectangle[][] rectangleArray; //stores an array of rectangle (the rectangles hide parts of the grid)
	private ImageView debugger;
	private StatusBar playerStats; //stores a status bar
	
	private double width, height; //stores the width and height of the game pane
	private int row, col; //stores the row and col amount of the game pane
	
	private boolean isDebuggerOn = false; //stores if debugger is on or not
	
	//constructor which sets the window, grid, width, height, row, and col variables
	public GameMethods(Stage window, Grid grid) {
		this.window = window;
		this.grid = grid;
		this.width = grid.getGridWidth();
		this.height = grid.getGridHeight();
		
		this.row = grid.getRow();
		this.col = grid.getCol();
	}

	//this method creates sprite, boss, obstacle, ammunition, rectangle, debugger and player statistics and runs all of their actions
	public void addElementsAndRun() {
		Random rand = new Random();
		
		//create sprite
		sprite = new Sprite(row, col, this.width/row);
		//add sprite to grid
		grid.add(sprite, sprite.getXPos(), sprite.getYPos()); 
		
		//create obstacles
		obstacleArray = this.generateObstacles();
		//add obstacles to grid
		for(int i = 0; i < obstacleArray.length; i++) {
			grid.add(obstacleArray[i], obstacleArray[i].getXPos(), obstacleArray[i].getYPos());
		}
		
		//create ammunition
		ammoArray = this.generateAmmo();
		//add ammunition to grid
		for(int i = 0; i < ammoArray.length; i++) {			
			//check that ammunition does not spawn on top of player
			while(ammoArray[i].getXPos() == sprite.getXPos() && ammoArray[i].getYPos() == sprite.getYPos()) {
				ammoArray[i].setXPos(rand.nextInt(row));
				ammoArray[i].setYPos(rand.nextInt(col));
			}
			
			grid.add(ammoArray[i], ammoArray[i].getXPos(), ammoArray[i].getYPos());			
		}
		
		//create boss
		boss = new Boss(sprite, row, col, width/row);
		//add boss to grid
		grid.add(boss, boss.getXPos(), boss.getYPos());
	
		//create status bar
		playerStats = new StatusBar(width/row);
		//add status bar to grid
		grid.add(playerStats, 0, col - 1);
		
		//create rectangle array (to cover grid)
		rectangleArray = this.generateRectangles();	
		//add the rectangles to the grid
		this.hideGrids();
		
		//create debugger
		debugger = new ImageView(new Image("images/debug.png"));	
		debugger.setFitHeight(width/row);
		debugger.setFitWidth(width/row);
		grid.add(debugger, row - 1, col - 1);
		
		//create scene
		Scene gameScene = new Scene(grid, width, height);
		
		//call on sprite action (key press)
		gameScene.setOnKeyPressed(event -> {
			this.spriteAction(event);
		});
		
		//call on boss action (mouse click)
		boss.setOnMouseClicked(event -> {
			if(sprite.getXPos() == boss.getXPos()) {
				//check if the sprite's (x + 1) or (x - 1) match (j)
				if(sprite.getYPos() + 1 == boss.getYPos() || sprite.getYPos() - 1 == boss.getYPos()) {
					this.bossAction();
				}
			}
			//check if (j) matches the sprite's (y) coordinate
			if(sprite.getYPos() == boss.getYPos()) {
				//check if the sprite's (y + 1) or (y - 1) match (i)
				if(sprite.getXPos() + 1 == boss.getXPos() || sprite.getXPos() - 1 == boss.getXPos()) {
					this.bossAction();
				}
			}
			
		});
		
		//call on debugger action
		debugger.setOnMouseClicked(e -> {
			if(isDebuggerOn) {
				isDebuggerOn = false;
			}
			else {
				isDebuggerOn = true;
			}
			
			this.updateRectangles();
		});
	
		//update the scene
		window.setScene(gameScene);
	}
	
	//this method deals with sprite actions
	private void spriteAction(KeyEvent event) {
		//store the key
		KeyCode key = event.getCode();
		
		switch(key) {
			case RIGHT:
			case D:
				sprite.moveRight(grid);				
				break;
			case LEFT:
			case A:
				sprite.moveLeft(grid);
				break;
			case UP:
			case W:
				sprite.moveUp(grid);
				break;
			case DOWN:
			case S:
				sprite.moveDown(grid);
				break;
			//if a random key was pressed, do nothing
			default:
				break;
		}	
		
		//update the rectangles covering the grid
		this.updateRectangles();
		
		//check if the player touches ammo
		this.checkSpriteAndAmmo();
		
		//check if the player touches an obstacle
		this.checkSpriteAndObstacle();
		
		//check if the player touches boss
		this.checkSpriteAndBoss();
		
		//check if user has lost
		if(sprite.getHasLost()) {
			window.setScene(this.losingScreen());
		}
	}

	//this method deals with boss actions
	private void bossAction() {
		//check that the sprite has ammo
		if(sprite.getAmmoCount() > 0) {
			//update the shot count of the boss
			boss.setShotCount(boss.getShotCount() + 1, sprite);
			
			//move boss
			grid.getChildren().remove(this.boss);
			grid.add(this.boss, this.boss.getXPos(), this.boss.getYPos());
			
			//update grid hider so that boss is covered
			this.updateRectangles();
		}
			
		//if the boss has been shot three times, the player has one
		if(boss.getShotCount() == 3) {
			sprite.setHasWon(true);
			window.setScene(this.winningScreen());
		}
		
		//update the ammunition counter
		sprite.setAmmoCount(sprite.getAmmoCount() - 1);
		//update the status bar
		playerStats.setStatusBar(sprite.getAmmoCount());	
	}
	
	//this method generates an array of obstacles
	private Obstacle[] generateObstacles() {
		Random rand = new Random();
		
		//create random amount of obstacles 
		int obstacleAmount = rand.nextInt(row + col);
		//make sure that there are more than (col - 1) amount of obstacles
		while(obstacleAmount < col) {
			obstacleAmount = rand.nextInt(row) + rand.nextInt(col);
		}
		
		//create an array and set its length to obstacleAmount
		Obstacle[] obstacleArray = new Obstacle[obstacleAmount];
		
		//populate array with obstacles
		for(int i = 0; i < obstacleAmount; i++) {
			obstacleArray[i] = new Obstacle(sprite, row, col, width/row);
		}
		
		return obstacleArray;
	}
	
	//this method generates an array of ammunition
	private Ammunition[] generateAmmo(){
		Random rand = new Random();
				
		//create an array that will hold 5 ammunition
		Ammunition[] ammoArray = new Ammunition[5];
		
		//populate the array with ammunition
		for(int i = 0; i < ammoArray.length; i++) {
			ammoArray[i] = new Ammunition(row, col, width/row);
			
			//go through obstacle array and check that the ammo and obstacles are not in the same grid
			for(int j = 0; j < obstacleArray.length; j++) {
				while(obstacleArray[j].getXPos() == ammoArray[i].getXPos() && obstacleArray[j].getYPos() == ammoArray[i].getYPos()) {
					ammoArray[i].setXPos(rand.nextInt(row));
					ammoArray[i].setYPos(rand.nextInt(col));
					//reset j to make sure that previously matching coordinates will not be generated
					j = 0;
				}
			}
		}
		
		return ammoArray;
	}


	//this method generates an array of rectangles that will hide the objects on the grid
	private Rectangle[][] generateRectangles() {
		//create a new 2D array of rectangles
		Rectangle[][] rectangleArray = new Rectangle[row][col];
		
		//populate rectangleArray using a nested for-loop
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				//check if (i, j) match the sprite's (x, y) coordinates
				if(sprite.getXPos() == i && sprite.getYPos() == j) {
					//add a transparent rectangle to the grid
					rectangleArray[i][j] = new Rectangle(width/row, width/row, Color.TRANSPARENT);
				}
				//check if (i) matches the sprite's (x) coordinate
				else if(sprite.getXPos() == i) {
					//check if the sprite's (x + 1) or (x - 1) match (j)
					if(sprite.getYPos() + 1 == j || sprite.getYPos() - 1 == j) {
						rectangleArray[i][j] = new Rectangle(width/row, width/row, Color.TRANSPARENT);
					}
					else {
						//add a black rectangle to the grid
						rectangleArray[i][j] = new Rectangle(width/row, width/row, Color.BLACK);
					}
				}
				//check if (j) matches the sprite's (y) coordinate
				else if(sprite.getYPos() == j) {
					//check if the sprite's (y + 1) or (y - 1) match (i)
					if(sprite.getXPos() + 1 == i || sprite.getXPos() - 1 == i) {
						rectangleArray[i][j] = new Rectangle(width/row, width/row, Color.TRANSPARENT);
					}
					else {
						rectangleArray[i][j] = new Rectangle(width/row, width/row, Color.BLACK);
					}
				}
				else {
					rectangleArray[i][j] = new Rectangle(width/row, width/row, Color.BLACK);
				}				
			}
		}
		
		return rectangleArray;
	}

	
	//this method adds the rectangles from rectangleArray to the grid
	private void hideGrids() {
		
		//use a nested for loop to go through rectangleArray[][]
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				//check if the current (i, j) match the boss's (x, y) coordinates.
				if(rectangleArray[i][j].getFill() == Color.TRANSPARENT && i == boss.getXPos() && j == boss.getYPos()) {
					//add the current rectangle
					grid.add(rectangleArray[i][j], i, j);
					
					//remove boss
					grid.getChildren().remove(this.boss);
					//re-add boss so boss is on top of current rectangle (this way, users can click on the boss)
					grid.add(this.boss, i, j);
				}
				else {
					grid.add(rectangleArray[i][j], i, j);
				}
			}
		}
		
		//remove playerStats
		grid.getChildren().remove(this.playerStats);
		//re-add playerStats so that they are on top of the current rectangle (this way, they are visible)
		grid.add(playerStats, 0, col - 1);
	}
	
	//this method removes rectangleArray's rectangles from the grid
	private void removeHiddenGrids() {		
		//use a nested for loop to go through rectangleArray
		for(int i = 0; i < row; i++) {
			for(int j = 0; j < col; j++) {
				//remove the current rectangle from the grid
				grid.getChildren().remove(rectangleArray[i][j]);
			}
		}
	}
	
	//this method calls on the calls on removeHiddenGrids, generateRectangles, and hideGrids all at once
	private void updateRectangles() {
		//check if debug mode is on
		if(isDebuggerOn) {
			this.removeHiddenGrids();
		}
		else {
			//remove the rectangles that are covering the grid
			this.removeHiddenGrids();
			//generate a new array of rectangles
			this.rectangleArray = this.generateRectangles();
			//add this new array of rectangles to the grid
			this.hideGrids();
		}
		
		//update debugger button so that it is on top
		grid.getChildren().remove(debugger);
		grid.add(debugger, row - 1, col - 1);
	}
	
	//this method checks if sprite and obstacle touch
	private void checkSpriteAndObstacle() {
		//go through obstacleArray
		for(int i = 0; i < obstacleArray.length; i++) {
			//check if the coordinates of the obstacle and sprite touch
			if(sprite.getXPos() == obstacleArray[i].getXPos() && sprite.getYPos() == obstacleArray[i].getYPos()) {
				//check if the sprite has zero ammunition
				if(sprite.getAmmoCount() == 0) {
					//the player has lost
					sprite.setHasLost(true);
				}
				//check if the obstacle is strawberry milk
				else if(obstacleArray[i].getIsStrawberryMilk()) {
					sprite.setHasLost(true);
				}
				else{
					//update the obstacle to turn into chocolate milk
					obstacleArray[i].setIsChocoMilk(true);
					
					//take away an ammunition from the player
					grid.getChildren().remove(playerStats);
					sprite.setAmmoCount(sprite.getAmmoCount() - 1);
					playerStats.setStatusBar(sprite.getAmmoCount());
					grid.add(playerStats, 0, col - 1);
				}
			}
		}
	}

	//this method checks if sprite and ammunition touch
	private void checkSpriteAndAmmo() {
		//go through ammoArray
		for(int i = 0; i < ammoArray.length; i++) {
			//check if the coordinates of the sprite and ammunition match
			if(sprite.getXPos() == ammoArray[i].getXPos() && sprite.getYPos() == ammoArray[i].getYPos()) {
				//remove the ammunition from the grid
				grid.getChildren().remove(ammoArray[i]);
				
				//set their coordinates to -1 (this is so that I can go through the ammoArray later on and check which ammo has been picked up)
				ammoArray[i].setXPos(-1);
				ammoArray[i].setYPos(-1);
				
				//give user an ammunition and update status bar
				sprite.setAmmoCount(sprite.getAmmoCount() + 1);
				grid.getChildren().remove(playerStats);
				playerStats.setStatusBar(sprite.getAmmoCount());
				grid.add(playerStats, 0, col - 1);
			}
		}
		
	
		
		int ammoCounter = 0;
		for(int i = 0; i < 5; i++) {
			if(ammoArray[i].getXPos() == -1 && ammoArray[i].getYPos() == - 1) {
				ammoCounter++;
			}
		}
		
		if(ammoCounter == 5) {
			sprite.setHasCollectedAllAmmo(true);
			playerStats.setHasCollectedAllAmmo(true);
		}
		
		if(sprite.getAmmoCount() == 0 && sprite.getHasCollectedAllAmmo()){
			sprite.setHasLost(true);
		}
	}

	//this method checks if the sprite and boss touch
	private void checkSpriteAndBoss() {
		//check if their coordinates are matching
		if(sprite.getXPos() == boss.getXPos() && sprite.getYPos() == boss.getYPos()) {
			//the player has lost
			sprite.setHasLost(true);
		}
	}
	
	//this method creates a scene that will play if the player has lost
	private Scene losingScreen() {
		//create new pane
		StackPane losingPane = new StackPane();
		
		//create a rectangle to color the background
		Rectangle backgroundRectangle = new Rectangle(width, height, Color.rgb(191, 159, 117));
		
		//create nodes
		ImageView losingImage = new ImageView(new Image("images/you lost.png"));
		losingImage.setFitHeight(height);
		losingImage.setFitWidth(height);
		
		ImageView exit = new ImageView(new Image("images/exit.png"));
		if(col == 7) {
			exit.setFitHeight(width/row * 2);
			exit.setFitWidth(width/row * 2);
		}
		else {
			exit.setFitHeight(width/row);
			exit.setFitWidth(width/row);
		}

		ImageView retry = new ImageView(new Image("images/retry.png"));
		if(col == 7) {
			retry.setFitHeight(width/row * 2);
			retry.setFitWidth(width/row * 2);
		}
		else {
			retry.setFitHeight(width/row);
			retry.setFitWidth(width/row);
		}
		
		Text stepCount = new Text("You took " + sprite.getStepCount() + " step(s).");
		stepCount.setFont(Font.font("fonts/consolas.ttf", 20));
		
		//set the coordinates of the nodes
		exit.setTranslateY(0);
		retry.setTranslateY(70);
		stepCount.setTranslateY(150);

		//add nodes to the pane
		losingPane.getChildren().addAll(
			backgroundRectangle,
			losingImage,
			exit,
			retry,
			stepCount
		);
		
		//create a scene
		Scene losingScene = new Scene(losingPane, width, height);
		
		exit.setOnMouseClicked(e -> {
			System.exit(0);
		});
		
		retry.setOnMouseClicked(e -> {
			Main.displayMenu(window);
		});
	

		return losingScene;	
	}

	//this method creates a scene that will play if the player has won
	private Scene winningScreen() {
		//create new pane
		StackPane winningPane = new StackPane();
		
		//create a rectangle to color the background
		Rectangle backgroundRectangle = new Rectangle(width, height, Color.rgb(191, 159, 117));
		
		//create nodes
		ImageView winningImage = new ImageView(new Image("images/you won.png"));
		winningImage.setFitHeight(height);
		winningImage.setFitWidth(height);
		
		ImageView exit = new ImageView(new Image("images/exit.png"));
		if(col == 7) {
			exit.setFitHeight(width/row * 2);
			exit.setFitWidth(width/row * 2);
		}
		else {
			exit.setFitHeight(width/row);
			exit.setFitWidth(width/row);
		}

		ImageView retry = new ImageView(new Image("images/retry.png"));
		if(col == 7) {
			retry.setFitHeight(width/row * 2);
			retry.setFitWidth(width/row * 2);
		}
		else {
			retry.setFitHeight(width/row);
			retry.setFitWidth(width/row);
		}
		
		Text stepCount = new Text("You took " + sprite.getStepCount() + " step(s).");
		stepCount.setFont(Font.font("fonts/consolas.ttf", 20));
		
		//set the coordinates of the nodes
		exit.setTranslateY(0);
		retry.setTranslateY(70);
		stepCount.setTranslateY(150);

		//add nodes to the pane
		winningPane.getChildren().addAll(
			backgroundRectangle,
			winningImage,
			exit,
			retry,
			stepCount
		);
		
		//create a scene
		Scene winningScene = new Scene(winningPane, width, height);
		
		exit.setOnMouseClicked(e -> {
			System.exit(0);
		});
		
		retry.setOnMouseClicked(e -> {
			Main.displayMenu(window);
		});
	

		return winningScene;		
	}
}

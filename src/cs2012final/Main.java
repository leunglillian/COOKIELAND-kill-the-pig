package cs2012final;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/* Lillian Leung
* CS2012
* 01 & 02
* Description: This is the Main class, 
* 			   it creates the main menu of the game which displays options for a 5x5, 7x7, and a 10x7 
* 			   grid format for the game.
* Other Comments: 
*/

public class Main extends Application{		
	public static void main(String[] args) {
		try {
			Application.launch(args);
		}
		catch(Error e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void start(Stage window) throws Exception {
		//display the menu (5x5, 7x7, 10x7)
		Main.displayMenu(window);
		window.show();
	}
	
	//creates buttons and adds them to the menu scene
	public static void displayMenu(Stage window) {	        
		//create a StackPane to hold buttons
		StackPane menuPane = new StackPane();
		
		//add background to pane
		ImageView menuBackground = new ImageView(new Image("images/starting menu.png"));
		menuBackground.setFitHeight(500);
		menuBackground.setFitWidth(500);
		menuPane.getChildren().add(menuBackground);
		
		//create "buttons"
		ImageView fiveXfive = new ImageView(new Image("images/5x5 button.png"));
		ImageView sevenXseven = new ImageView(new Image("images/7x7 button.png"));
		ImageView tenXseven = new ImageView(new Image("images/10x7 button.png"));
		
		//set button size
		fiveXfive.setFitHeight(95);
		fiveXfive.setFitWidth(95);
		sevenXseven.setFitHeight(95);
		sevenXseven.setFitWidth(95);
		tenXseven.setFitHeight(95);
		tenXseven.setFitWidth(95);
		
		//set button coordinates
		fiveXfive.setTranslateY(67);
		sevenXseven.setTranslateY(110);
		tenXseven.setTranslateY(153);
		
		//add buttons to StackPane
		menuPane.getChildren().addAll(
			fiveXfive,
			sevenXseven,
			tenXseven
		);
		
		//adds the menuPane to a scene
		Scene menuScene = new Scene(menuPane, 500, 500);
		
		//add scene to window
		window.setScene(menuScene);
	
		//call on button actions
		Main.menuButtonAction(window, fiveXfive, sevenXseven, tenXseven);		
	}
	
	//deals with button actions
	public static void menuButtonAction(Stage window, ImageView fiveXfive, ImageView sevenXseven, ImageView tenXseven) {		
		fiveXfive.setOnMouseClicked(e -> {
			//width = the width of the game grid
			//height = the height of the game grid
			double width = 500, height = 500;
			//row = how many rows the game grid has
			//col = how many cols the game grid has
			int row = 5, col = 5;
			
			//create a Grid
			Grid grid = new Grid(width, height, row, col);	
			//call on GameMethods and pass in parameters
			GameMethods methods = new GameMethods(window, grid);
			//call on addElementsAndRun method which will generate sprites, obstacles, etc. and run the rest of the game mechanics
			methods.addElementsAndRun();
		});
		
		sevenXseven.setOnMouseClicked(e -> {
			double width = 700 / 1.25, height = 700 / 1.25;
			int row = 7, col = 7;
			
			Grid grid = new Grid(width, height, row, col);	
			GameMethods methods = new GameMethods(window, grid);
			methods.addElementsAndRun();
		});

		tenXseven.setOnMouseClicked(e -> {
			double width = 1000 / 1.5, height = 700 / 1.5;
			int row = 10, col = 7;
			
			Grid grid = new Grid(width, height, row, col);		
			GameMethods methods = new GameMethods(window, grid);
			methods.addElementsAndRun();
		});
	}
	
	
}

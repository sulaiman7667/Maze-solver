import maze.*;
import maze.routing.*;
import maze.visualisation.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import java.io.*;
import java.util.*;
/**
 * The Class MazeApplication.
* @author Sulaiman Alhmoudi
* @version 1.0
*/
public class MazeApplication extends Application  {

	public static void main(String[] args) {
		launch(args);
	}
	/**
     * the start method that contains the stage.
     *
     * @param primaryStage the primary stage
     */
	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Maze solver");
		MenuItem loadMapBtn = new MenuItem("Load map");
		MenuItem loadRouteBtn = new MenuItem("Load route");
		MenuItem saveRouteBtn = new MenuItem("save route");
		Button stepBtn = new Button("step");
		Menu fileMenu = new Menu("File");
		fileMenu.getItems().add(loadMapBtn);
		fileMenu.getItems().add(loadRouteBtn);
		fileMenu.getItems().add(saveRouteBtn);
		MenuBar menuBar = new MenuBar();
		menuBar.getMenus().addAll(fileMenu);
		stepBtn.setStyle("-fx-background-color: red;");
		HBox hbox = new HBox();
		hbox.setStyle("-fx-background-color: white;");
		hbox.getChildren().addAll(menuBar,stepBtn);
		BorderPane borderpane = new BorderPane();
		borderpane.setTop(hbox);
		borderpane.setStyle("-fx-background-color: lightgreen;");
		Scene scene = new Scene(borderpane, 360, 360);
		primaryStage.setScene(scene);
		primaryStage.show();


		loadMapBtn.setOnAction(e -> {
			try {
				FileChooser f = new FileChooser();
				File file = f.showOpenDialog(primaryStage);
				GridPane grid = new GridPane();
				try {
					Maze maze = Maze.fromTxt(file.getPath());
					List<List<Tile>> tiles = maze.getTiles();
					int row = 0;
					int column = 0;
					for(int i = 0; i<tiles.size(); i ++){
						for(int j = 0; j<tiles.get(i).size(); j++){
							Tile tile = tiles.get(i).get(j);
							Block block = new Block(tile);
							block.setPrefSize(60,60);
							GridPane.setConstraints(block,column,row);
							grid.getChildren().addAll(block);
							column = column + 1;
						}
						row = row + 1;
						column = 0;
					}

					RouteFinder routeFinder = new RouteFinder(maze);

					stepBtn.setOnAction(n -> {
						try {
						routeFinder.step();
						Tile currentTile = routeFinder.getRoute().get(routeFinder.getRoute().size()-1);
						Tile poppedTile = routeFinder.getPoppedTile();
						Maze.Coordinate c = maze.getTileLocationNew(currentTile);
						Block block = new Block();
						block.setPrefSize(60,60);

						if(poppedTile == null){
							if(currentTile.getType() == Tile.Type.EXIT)
								block = new Block(currentTile);
						}
						else{
							c = maze.getTileLocationNew(poppedTile);
							block.setStyle("-fx-background-color: red;");
						}
						GridPane.setConstraints(block,c.getY(),c.getX());
						grid.getChildren().addAll(block);
					}
					catch(NoRouteFoundException g) {
							System.out.print(g.getMessage());
						}
					});
					saveRouteBtn.setOnAction(m ->{
					routeFinder.save("route.bin");
					
					});
			}
			catch(InvalidMazeException a) {
				System.out.print(a.getMessage());
			}
			borderpane.setTop(hbox);
			borderpane.setCenter(grid);
			primaryStage.setScene(scene);
			}
			catch(Exception b) {
				System.out.print("No file selected/invalid file, try again.\n");
			}
			
		});




		loadRouteBtn.setOnAction(e -> {
			try {
				FileChooser f = new FileChooser();
				File file = f.showOpenDialog(primaryStage);
				RouteFinder routeFinder = RouteFinder.load(file.getPath());
				GridPane grid = new GridPane();
				Maze maze = routeFinder.getMaze();
				List<List<Tile>> tiles = maze.getTiles();
				int row = 0;
				int column = 0;
				for(int i = 0; i<tiles.size(); i ++){
					for(int j = 0; j<tiles.get(i).size(); j++){
						Tile tile = tiles.get(i).get(j);
						Block block = new Block(tile);
						block.setPrefSize(60,60);
						GridPane.setConstraints(block,column,row);
						grid.getChildren().addAll(block);
						column = column + 1;
					}
					row = row + 1;
					column = 0;
				}
				for(int i =0;i<routeFinder.getRoute().size(); i++){
					Tile tile = routeFinder.getRoute().get(i);
					Maze.Coordinate c = maze.getTileLocationNew(tile);
					Block block = new Block();
					block.setPrefSize(60,60);
					if(tile.getType() == Tile.Type.EXIT)
							block.setStyle("-fx-background-color: orange;");
					GridPane.setConstraints(block,c.getY(),c.getX());
					grid.getChildren().addAll(block);

				}
				for(int i =0;i<routeFinder.getPoppedList().size(); i++){
					Tile t = routeFinder.getPoppedList().get(i);
					Maze.Coordinate c = maze.getTileLocationNew(t);
					Block block = new Block();
					block.setPrefSize(60,60);
					block.setStyle("-fx-background-color: red;");
					GridPane.setConstraints(block,c.getY(),c.getX());
					grid.getChildren().addAll(block);

				}


				stepBtn.setOnAction(n -> {
					try {
						routeFinder.step();
						Tile currentTile = routeFinder.getRoute().get(routeFinder.getRoute().size()-1);
						Tile poppedTile = routeFinder.getPoppedTile();
						Maze.Coordinate c = maze.getTileLocationNew(currentTile);
						Block block = new Block();
						block.setPrefSize(60,60);

						if(poppedTile == null){
							if(currentTile.getType() == Tile.Type.EXIT)
								block = new Block(currentTile);
						}
						else{
							c = maze.getTileLocationNew(poppedTile);
							block.setStyle("-fx-background-color: red;");
						}
						GridPane.setConstraints(block,c.getY(),c.getX());
						grid.getChildren().addAll(block);
					}
					catch(NoRouteFoundException g) {
							System.out.print(g.getMessage());
						}

				});
			borderpane.setTop(hbox);
			borderpane.setCenter(grid);
			primaryStage.setScene(scene);

			saveRouteBtn.setOnAction(m ->{
				routeFinder.save("route.bin");
			});

		}
		catch(Exception c){
			System.out.print("No route file selected/invalid file, try again.\n");
		}
		});


	}

}




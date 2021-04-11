import maze.*;
import maze.routing.*;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.*;
import java.util.*;




public class MazeApplication extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	@Override
	public void start(Stage primaryStage){
		primaryStage.setTitle("Maze solver");
		Button loadMapBtn = new Button("Load map");
		Button loadRouteBtn = new Button("Load route");
		Button saveRouteBtn = new Button("save route");
		Button stepBtn = new Button("step");

		HBox vbox = new HBox();
		vbox.getChildren().addAll(loadMapBtn, loadRouteBtn, saveRouteBtn, stepBtn);

		GridPane grid = new GridPane();
		Maze maze = Maze.fromTxt("Maze2.txt");
		List<List<Tile>> tiles = maze.getTiles();
		int row = 0;
		int column = 0;
		for(int i = 0; i<tiles.size(); i ++){
			for(int j = 0; j<tiles.get(i).size(); j++){
				Tile tile = tiles.get(i).get(j);
				Pane pane = new Pane();
				pane.setPrefSize(60,60);
				if(tile.getType() == Tile.Type.ENTRANCE)
					pane.setStyle("-fx-background-color: green;");
				else if(tile.getType() == Tile.Type.WALL)
					pane.setStyle("-fx-background-color: brown;");
				else if(tile.getType() == Tile.Type.CORRIDOR)
					pane.setStyle("-fx-background-color: white;");
				else if(tile.getType() == Tile.Type.EXIT)
					pane.setStyle("-fx-background-color: red;");

				
				
				GridPane.setConstraints(pane,column,row);
				grid.getChildren().addAll(pane);
				column = column + 1;
			}
			row = row + 1;
			column = 0;
	}
		RouteFinder routeFinder = new RouteFinder(maze);
		stepBtn.setOnAction(e -> {
			routeFinder.step();
			Tile currentTile = routeFinder.getRoute().get(routeFinder.getRoute().size()-1);
			Tile poppedTile = routeFinder.getPoppedTile();
			Maze.Coordinate c = maze.getTileLocationNew(currentTile);
			Pane pane = new Pane();
			pane.setPrefSize(60,60);
			if(poppedTile == null)
				pane.setStyle("-fx-background-color: green;");
			else{
				c = maze.getTileLocationNew(poppedTile);
				pane.setStyle("-fx-background-color: white;");
			}
			GridPane.setConstraints(pane,c.getY(),c.getX());
			grid.getChildren().addAll(pane);

		});
		BorderPane borderpane = new BorderPane();
		borderpane.setTop(vbox);
		borderpane.setCenter(grid);
		Scene scene = new Scene(borderpane, 360, 360);
		primaryStage.setScene(scene);
		primaryStage.show();
}
}




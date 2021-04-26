package maze.visualisation;
import maze.*;
import maze.routing.*;
import javafx.scene.layout.Pane;


public class Block extends Pane {
	public Block(){
			this.setStyle("-fx-background-color: green;");
		}
		public Block(Tile t){
			if(t.getType() == Tile.Type.ENTRANCE)
				this.setStyle("-fx-background-color: lightgreen;");
			else if(t.getType() == Tile.Type.WALL)
				this.setStyle("-fx-background-color: brown;");
			else if(t.getType() == Tile.Type.CORRIDOR)
				this.setStyle("-fx-background-color: white;");
			else if(t.getType() == Tile.Type.EXIT)
				this.setStyle("-fx-background-color: orange;");
		}
}
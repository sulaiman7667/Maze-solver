package maze.visualisation;
import maze.*;
import maze.routing.*;
import javafx.scene.layout.Pane;

/**
 * The Class Block.
* @author Sulaiman Alhmoudi
* @version 1.0
*/
public class Block extends Pane {
	/**
     * Instantiates a new Block with color green.
     *
     */
	public Block(){
			this.setStyle("-fx-background-color: green;");
		}
		/**
     * Instantiates a new Block with a specific color.
     *
     * @param t the tile
     */
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
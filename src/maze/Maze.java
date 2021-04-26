package maze;
import java.io.*;
import java.util.*;

/**
 * The Class Maze.
* @author Sulaiman Alhmoudi
* @version 1.0
*/
public class Maze implements java.io.Serializable{
	/**
     * The enum Direction.
     */
	public enum Direction {
		/** The north direction. */
		NORTH,
		/** The south direction. */
		SOUTH,
		/** The east direction. */
		EAST,
		/** The west direction. */
		WEST

	}
	/** The entrance tile. */
	private Tile entrance;
	/** The exit tile. */
	private Tile exit;
	/** The list of tiles. */
	private List<List<Tile>> tiles;
	
	/**
     * Instantiates a new Maze.
     *
     */
	private Maze(){

	}
	/**
     * Instantiates a new Maze.
     *
     * @param l the tiles 2d-list
     * @param en the entrance tile
     * @param ex the exit tile
     */
	private Maze(List<List<Tile>> l, Tile en, Tile ex){
		tiles = l;
		entrance = en;
		exit = ex;
	}
	/**
     * returns a maze from a textfile.
     *
     * @param filepath the filepath
     * @return the maze
     * @throws InvalidMazeException throws when some conditions are not met
     */
	public static Maze fromTxt(String filepath) throws InvalidMazeException{
		String line = "";
    	String mazeTxt = "";
    	int ent = 0;
    	int ex = 0;
    	List<List<Tile>> tileList = new ArrayList();
    	Tile enTile = null;
    	Tile exTile = null;
    	try{
    		BufferedReader mazeFile = new BufferedReader(new FileReader(filepath));
    		while((line = mazeFile.readLine()) != null){
    			List<Tile> templist = new ArrayList();
    			for(int i = 0; i<line.length(); i++){
    				templist.add(Tile.fromChar(line.charAt(i)));

    			}
    			tileList.add(templist);
    			mazeTxt += (line + "\n");

    		}
    		for(int i = 0; i<mazeTxt.length(); i++){
    			if(mazeTxt.charAt(i) == 'e')
    				ent = ent + 1;
    			if(mazeTxt.charAt(i) == 'x')
    				ex = ex + 1;
    		}
    		for(List<Tile> l: tileList){
    			for(Tile t: l){
    				if(t.getType() == Tile.Type.ENTRANCE)
    					enTile = t;
    				if(t.getType() == Tile.Type.EXIT)
    					exTile = t;
    		}
    		}


    	}
    	catch(Exception e) {
    		return null;
    	}

    	if(ent > 1)
			throw new MultipleEntranceException("Error: invalid maze, multiple entrances found.\n");
		if(ent == 0)
			throw new NoEntranceException("Error: invalid maze, no entrance found.\n");
		if(ex > 1)
			throw new MultipleExitException("Error: invalid maze, multiple exits found.\n");
		if(ex == 0)
			throw new NoExitException("Error: invalid maze, no exit found.\n");
		for(int i = 0; i<tileList.size(); i++){
			int size = tileList.get(0).size();
			if(tileList.get(i).size() != size)
				throw new RaggedMazeException("Error: invalid maze, maze is ragged; length of rows do not match each other.\n");
		}

    	return new Maze(tileList, enTile, exTile);
	}
	/**
     * return adjacent tile in a certain direction.
     *
     * @param t the tile
     * @param d the direction
     * @return the adjacent tile
     */
	public Tile getAdjacentTile(Tile t, Direction d){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j) == t){
					if(d == Direction.NORTH){
						if(i-1 < 0)
							return null;
						return tiles.get(i-1).get(j);
						
					}
					if(d == Direction.SOUTH){
						if(i+1 > tiles.size() - 1)
							return null;
						return tiles.get(i+1).get(j);
					}
					
					if(d == Direction.EAST){
						if(j+1 > tiles.get(i).size() - 1)
							return null;
						return tiles.get(i).get(j+1);
						
					}	
					if(d == Direction.WEST){
						if(j-1 < 0)
							return null;
						return tiles.get(i).get(j-1);
						
					}		
				}
			}	
		}
		return null;
	}
	/**
     * returns the entrance tile.
     *
     * @return the entrance tile
     */
	public Tile getEntrance(){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j).getType() == Tile.Type.ENTRANCE)
					return tiles.get(i).get(j);

			}	
		}
		return null;

	}
	/**
     * returns the exit tile.
     *
     * @return the exit tile
     */
	public Tile getExit(){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j).getType() == Tile.Type.EXIT)
					return tiles.get(i).get(j);

			}	
		}
		return null;
	}
	/**
     * returns the entrance tile.
     *
     * @param c the coordinate
     * @return the tile at location c
     */
	public Tile getTileAtLocation(Coordinate c){
		int column = c.getX();
		int row = c.getY();
		return tiles.get(5-row).get(column);
	}
	/**
     * returns the coordinate of a tile from bottom to top ordering.
     *
     * @param t the tile
     * @return the location of a tile
     */
	public Coordinate getTileLocation(Tile t){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j) == t)
					return new Coordinate(j,5-i);			
			}	
		}
		return null;
	}
	/**
     * returns the coordinate of a tile from top to bottom ordering.
     *
     * @param t the tile
     * @return the location of a tile
     */
	public Coordinate getTileLocationNew(Tile t){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j) == t)
					return new Coordinate(i,j);			
			}	
		}
		return null;
	}
	/**
     * returns the tiles list.
     *
     * @return the tiles list
     */
	public List<List<Tile>> getTiles() {
		return tiles;

	}
	/**
     * sets the tile to an entrance variable.
     *
     * @param t the tile
     */
	private void setEntrance(Tile t) throws MultipleEntranceException{
		try {
			if(getTileLocation(t) == null)
				throw new Exception("Tile not in maze.");
			if(entrance == null)
				entrance = t;
			else
				throw new MultipleEntranceException("Multiple entrances found.");
		}
		catch(MultipleEntranceException a) {
			throw a;
		}
		catch(Exception b){

		}
	}
	/**
     * sets the tile to an exit variable.
     *
     * @param t the tile
     */
	private void setExit(Tile t) throws MultipleExitException{
		try {
			if(getTileLocation(t) == null)
				throw new Exception("Tile not in maze.");
			if(exit == null)
				exit = t;
			else
				throw new MultipleExitException("Multiple exits found.");
		}
		catch(MultipleExitException a) {
			throw a;
		}
		catch(Exception b){

		}
	}	
	/**
     * converts the maze to a string.
     *
     * @return the string
     */
	public String toString(){
		String x = "";
		for(int i = 0; i<tiles.size(); i++){
			for(int j = 0; j<tiles.get(i).size(); j++){
				x+= tiles.get(i).get(j).toString();
			}
			x += "\n";
		}
		return x;

	}
	/**
 	* The Class Maze.
	*/
	public class Coordinate{
	/** the x coordinate. */
	private int x;
	/** the y coordinate. */
	private int y;	
	/**
     * instantiates a new coordinate
     *
     * @param a the x value
     * @param b the y value
     */
	public Coordinate(int a, int b){
		x = a;
		y = b;
	}
	/**
     * returns x.
     *
     * @return the x value
     */
	public int getX(){
		return x;

	}
	/**
     * returns y.
     *
     * @return the y value
     */
	public int getY(){
		return y;
		
	}
	/**
     * converts the coordinate to a string
     *
     * @return the string
     */
	public String toString(){
		return ("(" + getX() + ", " + getY() + ")");
	}
	
}


	
}
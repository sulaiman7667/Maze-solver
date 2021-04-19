package maze;
import java.io.*;
import java.util.*;

public class Maze implements java.io.Serializable{
	public enum Direction {
	NORTH,SOUTH,EAST,WEST

	}
	private Tile entrance;
	private Tile exit;
	private List<List<Tile>> tiles;
	

	private Maze(){

	}

	private Maze(List<List<Tile>> l, Tile en, Tile ex){
		tiles = l;
		entrance = en;
		exit = ex;
	}

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
			throw new MultipleEntranceException("Error: invalid maze, multiple entrances found\n");
		if(ent == 0)
			throw new NoEntranceException("Error: invalid maze, no entrance found\n");
		if(ex > 1)
			throw new MultipleExitException("Error: invalid maze, multiple exits found\n");
		if(ex == 0)
			throw new NoExitException("Error: invalid maze, no exit found\n");
		for(int i = 0; i<tileList.size(); i++){
			int size = tileList.get(0).size();
			if(tileList.get(i).size() != size)
				throw new RaggedMazeException("Error: invalid maze, maze is ragged.\n");
		}

    	return new Maze(tileList, enTile, exTile);
	}

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

	public Tile getEntrance(){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j).getType() == Tile.Type.ENTRANCE)
					return tiles.get(i).get(j);

			}	
		}
		return null;

	}

	public Tile getExit(){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j).getType() == Tile.Type.EXIT)
					return tiles.get(i).get(j);

			}	
		}
		return null;
	}

	public Tile getTileAtLocation(Coordinate c){
		int column = c.getX();
		int row = c.getY();
		return tiles.get(5-row).get(column);
	}

	public Coordinate getTileLocation(Tile t){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j) == t)
					return new Coordinate(j,5-i);			
			}	
		}
		return null;
	}

	public Coordinate getTileLocationNew(Tile t){
		for (int i = 0;i<tiles.size(); i++) {
			for (int j = 0;j < tiles.get(i).size();j++) {
				if(tiles.get(i).get(j) == t)
					return new Coordinate(i,j);			
			}	
		}
		return null;
	}

	public List<List<Tile>> getTiles() {
		return tiles;

	}

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

	public class Coordinate{
	private int x;
	private int y;

	public Coordinate(int a, int b){
		x = a;
		y = b;
	}

	public int getX(){
		return x;

	}

	public int getY(){
		return y;
		
	}

	public String toString(){
		return ("(" + getX() + ", " + getY() + ")");
	}
	
}


	
}
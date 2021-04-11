package maze;
import java.io.*;
import java.util.*;

public class Maze{
	public enum Direction {
	NORTH,SOUTH,EAST,WEST

	}
	private Tile entrance;
	private Tile exit;
	private List<List<Tile>> tiles;

	private Maze(){

	}

	private Maze(List<List<Tile>> l){
		tiles = l;
	}

	public static Maze fromTxt(String filepath){
		String tempstr = "";
    	String mazeTxt = "";
    	List<List<Tile>> tempTilesList = new ArrayList();
    	Tile enTile = null;
    	Tile exTile = null;
    	try{
    		BufferedReader mazeFile = new BufferedReader(new FileReader(filepath));
    		while((tempstr = mazeFile.readLine()) != null){
    			List<Tile> templist = new ArrayList();
    			for(int i = 0; i<tempstr.length(); i++){
    				templist.add(Tile.fromChar(tempstr.charAt(i)));

    			}
    			tempTilesList.add(templist);
    			mazeTxt += (tempstr + "\n");
    		}

    	}
    	catch(Exception ex){
    		return null;
    	}
    	return new Maze(tempTilesList);
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

	// private void setEntrance(Tile t){

	// }

// 	private void setExit(Tile t){

// 	}

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
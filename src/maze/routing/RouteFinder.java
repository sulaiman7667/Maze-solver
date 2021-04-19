package maze.routing;
import maze.*;
import java.util.*;
import java.io.*;
public class RouteFinder implements java.io.Serializable{
	private Maze maze;
	private Stack<Tile> route = new Stack();
	private boolean finished;

	private List<Tile> poppedList = new ArrayList();
	private boolean ent = true;
	private Tile poppedTile = null;

	public RouteFinder(Maze m){
		maze = m;
		finished = false;
	}

	public Maze getMaze(){
		return maze;
	}

	public List<Tile> getRoute(){
		return route;
	}
	public List<Tile> getPoppedList(){
		return poppedList;
	}

	public Tile getPoppedTile(){
		return poppedTile;
	}

	public boolean isFinished(){
		return finished;
	}

	public static RouteFinder load(String path){
		 RouteFinder rf = null;
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream obj = new ObjectInputStream(file);
            rf = (RouteFinder) obj.readObject();
        } catch(Exception ex){
        	System.out.println(ex.getMessage());
        } 

        return rf;
	}

	public void save(String path){
		try {
            FileOutputStream file = new FileOutputStream(path);
            ObjectOutputStream obj = new ObjectOutputStream(file);
            obj.writeObject(this);
            obj.close();
            file.close();
        } catch(Exception ex) {
        	System.out.println(ex.getMessage());
        }

	}

	public boolean step(){
		poppedTile = null;
		if(ent == true){
			route.add(maze.getEntrance());
			ent = false;
		}
		if(!isFinished()){
			Tile northTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.NORTH);
			Tile southTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.SOUTH);
			Tile eastTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.EAST);
			Tile westTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.WEST);
			if(northTile !=null && northTile.isNavigable() && !route.contains(northTile) && !poppedList.contains(northTile)){
				route.add(northTile);
			}
			else if(eastTile !=null && eastTile.isNavigable() && !route.contains(eastTile) && !poppedList.contains(eastTile)){
				route.add(eastTile);
			}
			else if(southTile !=null && southTile.isNavigable() && !route.contains(southTile) && !poppedList.contains(southTile)){
				route.add(southTile);
			}
			else if(westTile !=null && westTile.isNavigable() && !route.contains(westTile) && !poppedList.contains(westTile)){
				route.add(westTile);
			}
			
			else{
				poppedList.add(route.peek());
				poppedTile = route.pop();

			}
			if(route.get((route.size() - 1)).toString() == "x")
				finished = true;
			return false;
		}
		return true;
		

	}

	public String toString(){
		String x = "";
		List<Tile> route = getRoute();
		for (int i = 0;i<maze.getTiles().size();i++) {
			for (int j = 0;j<maze.getTiles().get(i).size();j++) {
				if(route.contains(maze.getTiles().get(i).get(j)))
					if(maze.getTiles().get(i).get(j).getType() == Tile.Type.ENTRANCE)
						x += "e";
					else if(maze.getTiles().get(i).get(j).getType() == Tile.Type.EXIT)
						x += "x";
					else
						x += "*";
				else if(poppedList.contains(maze.getTiles().get(i).get(j)))
					x += "-";
				else
					x += maze.getTiles().get(i).get(j).toString();	
			}
			x+="\n";	
		}
		return x;
	}

	
}
package maze.routing;
import maze.*;
import java.util.*;
public class RouteFinder{
	private Maze maze;
	private Stack<Tile> route = new Stack();
	private boolean finished;

	private List<Tile> pathList = new ArrayList();
	private boolean ent = true;
	private Tile poppedTile = null;

	public RouteFinder(Maze m){
		maze = m;
	}

	public Maze getMaze(){
		return maze;
	}

	public List<Tile> getRoute(){
		return route;
	}

	public Tile getPoppedTile(){
		return poppedTile;
	}

	public boolean isFinished(){
		if(route.get((route.size() - 1)).toString() == "x")
			return true;
		return false;
	}

	// public static RouteFinder load(String s){

	// }

	// public void save(String s){

	// }

	public boolean step(){
		poppedTile = null;
		if(ent == true){
			route.add(maze.getEntrance());
			pathList.add(maze.getEntrance());
			ent = false;
		}
		if(!isFinished()){
			Tile northTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.NORTH);
			Tile southTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.SOUTH);
			Tile eastTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.EAST);
			Tile westTile = maze.getAdjacentTile(route.get(route.size() - 1), Maze.Direction.WEST);
			if(northTile !=null && northTile.isNavigable() && !route.contains(northTile)){
				route.add(northTile);
				pathList.add(northTile);
				Tile prevTile = pathList.get(pathList.size() - 2);
				if(prevTile.getType() != Tile.Type.ENTRANCE)
					prevTile.setType(Tile.Type.NOTNAVIGABLE);
			}
			else if(southTile !=null && southTile.isNavigable() && !route.contains(southTile)){
				route.add(southTile);
				pathList.add(southTile);
				Tile prevTile = pathList.get(pathList.size() - 2);
				if(prevTile.getType() != Tile.Type.ENTRANCE)
					prevTile.setType(Tile.Type.NOTNAVIGABLE);
			}

			else if(eastTile !=null && eastTile.isNavigable() && !route.contains(eastTile)){
				route.add(eastTile);
				pathList.add(eastTile);
				Tile prevTile = pathList.get(pathList.size() - 2);
				if(prevTile.getType() != Tile.Type.ENTRANCE)
					prevTile.setType(Tile.Type.NOTNAVIGABLE);
			}
			else if(westTile !=null && westTile.isNavigable() && !route.contains(westTile)){
				route.add(westTile);
				pathList.add(westTile);
				Tile prevTile = pathList.get(pathList.size() - 2);
				if(prevTile.getType() != Tile.Type.ENTRANCE)
					prevTile.setType(Tile.Type.NOTNAVIGABLE);
			}
			
			else{
				poppedTile = route.pop();
				poppedTile.setType(Tile.Type.NOTNAVIGABLE);

			}
			return false;
	}
		return true;
		

	}

	public String toString(){
		String x = "e";
		List<Tile> route = getRoute();
		for (int i = 0;i<maze.getTiles().size();i++) {
			for (int j = 1;j<maze.getTiles().get(i).size();j++) {
				if(route.contains(maze.getTiles().get(i).get(j)))
					x += "*";
				else
					x += maze.getTiles().get(i).get(j).toString();
			
		}
		x+="\n";
			
		}
		return x;
	}

	
}
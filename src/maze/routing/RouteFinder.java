package maze.routing;
import maze.*;
import java.util.*;
import java.io.*;
/**
 * The Class RouteFinder.
* @author Sulaiman Alhmoudi
* @version 1.0
*/
public class RouteFinder implements java.io.Serializable{
	/** The maze. */
	private Maze maze;
	/** The route. */
	private Stack<Tile> route = new Stack();
	/** The finished boolean. */
	private boolean finished;
	/** The poppedlist. */
	private List<Tile> poppedList = new ArrayList();
	/** The ent boolean. */
	private boolean ent = true;
	/** The popped tile. */
	private Tile poppedTile = null;
	/**
     * Instantiates a new RouteFinder.
     *
     * @param m the maze
     */
	public RouteFinder(Maze m){
		maze = m;
		finished = false;
	}
	/**
     * returns the maze.
     *
     * @return the maze
     */
	public Maze getMaze(){
		return maze;
	}
	/**
     * returns the route.
     *
     * @return the route
     */
	public List<Tile> getRoute(){
		return route;
	}
	/**
     * returns the poppedlist.
     *
     * @return the poppedList
     */
	public List<Tile> getPoppedList(){
		return poppedList;
	}
	/**
     * returns the popped tile.
     *
     * @return the poppedTile
     */
	public Tile getPoppedTile(){
		return poppedTile;
	}
	/**
     * returns the finished boolean.
     *
     * @return the finished
     */
	public boolean isFinished(){
		return finished;
	}
	/**
     * loads the RouteFinder object.
     *
     * @param path the file path
     * @return the RouteFinder object
     */
	public static RouteFinder load(String path){
		 RouteFinder rf = null;
        try {
            FileInputStream file = new FileInputStream(path);
            ObjectInputStream obj = new ObjectInputStream(file);
            rf = (RouteFinder) obj.readObject();
        } catch(Exception ex){
        } 

        return rf;
	}
	/**
     * saves the RouteFinder object.
     *
     * @param filename the file name
     */
	public void save(String filename){
		try {
            FileOutputStream file = new FileOutputStream(filename);
            ObjectOutputStream obj = new ObjectOutputStream(file);
            obj.writeObject(this);
            obj.close();
            file.close();
        } catch(Exception ex) {
        }

	}
	/**
     * algorithm for solving the maze step by step.
     *
     * @throws NoRouteFoundException thrown when no route is found
     */
	public boolean step() throws NoRouteFoundException{
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
			else if(southTile !=null && southTile.isNavigable() && !route.contains(southTile) && !poppedList.contains(southTile)){
				route.add(southTile);
			}
			
			else if(eastTile !=null && eastTile.isNavigable() && !route.contains(eastTile) && !poppedList.contains(eastTile)){
				route.add(eastTile);
			}
			
			else if(westTile !=null && westTile.isNavigable() && !route.contains(westTile) && !poppedList.contains(westTile)){
				route.add(westTile);
			}
			
			else{
				if(route.size() == 1)
					throw new NoRouteFoundException("No route found.\n");
				poppedList.add(route.peek());
				poppedTile = route.pop();

			}
			if(route.get((route.size() - 1)).toString() == "x")
				finished = true;
	
			return false;
		}
		return true;
		

	}
	/**
     * converts the maze with the route to a string.
     *
     * @return the string
     */
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
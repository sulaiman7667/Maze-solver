package maze;
/**
 * The Class Tile.
* @author Sulaiman Alhmoudi
* @version 1.0
*/
public class Tile implements java.io.Serializable {

	/**
     * The enum Type.
     */
	public enum Type {
		/** The entrance Type. */
		ENTRANCE,
		/** The corridor Type. */
		CORRIDOR,
		/** The wall Type. */
		WALL,
		/** The exit Type. */
		EXIT

	}
	/** The type. */
	private Type type;
	/**
     * Instantiates a new tile.
     *
     * @param t the type
     */
	private Tile(Type t){
		type = t;

	}
	/**
     * converts char to tile.
     *
     * @param c the character
     * @return the tile
     */
	protected static Tile fromChar(char c){
		if(c == 'e')
			return new Tile(Type.ENTRANCE);
		else if(c == '.')
			return new Tile(Type.CORRIDOR);
		else if(c == '#')
			return new Tile(Type.WALL);
		else if(c == 'x')
			return new Tile(Type.EXIT);
		else
			return null;

	}
	/**
     * returns the type.
     *
     * @return the type
     */
	public Type getType() {
		return type;

	}
	/**
     * sets the type of tile.
     *
     * @param t the type
     */
	public void setType(Type t) {
		type = t;

	}
	/**
     * checks if navigable.
     *
     * @return the true if navigable else false
     */
	public boolean isNavigable(){
		if(getType() == Type.WALL)
			return false;
		else
			return true;

	}
	/**
     * converts the type of tile to a string.
     *
     * @return the string
     */
	public String toString(){
		String x = "";
		if(getType() == Type.ENTRANCE)
			x = "e";
		if(getType() == Type.CORRIDOR)
			x = ".";
		if(getType() == Type.WALL)
			x = "#";
		if(getType() == Type.EXIT)
			x = "x";
		return x;

	}

	
}
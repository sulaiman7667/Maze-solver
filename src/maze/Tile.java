package maze;

public class Tile implements java.io.Serializable {

	public enum Type{
	CORRIDOR,ENTRANCE,EXIT,WALL
	}

	private Type type;

	private Tile(Type t){
		type = t;

	}
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
	public Type getType() {
		return type;

	}

	public void setType(Type t) {
		type = t;

	}
	public boolean isNavigable(){
		if(getType() == Type.WALL)
			return false;
		else
			return true;

	}

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
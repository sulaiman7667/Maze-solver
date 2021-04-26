package maze;
/**
 * The Class NoExitException.
* @author Sulaiman Alhmoudi
* @version 1.0
*/
public class NoExitException extends InvalidMazeException {
	/**
     * Instantiates a new NoExitException.
     *
     * @param s the error message
     */
    public NoExitException(String s) {
        super(s);
    }
}

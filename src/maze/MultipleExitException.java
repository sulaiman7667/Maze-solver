package maze;
/**
 * The Class MultipleExitException.
* @author Sulaiman Alhmoudi
* @version 1.0
*/
public class MultipleExitException extends InvalidMazeException {
    
	/**
     * Instantiates a new MultipleExitException.
     *
     * @param s the error message
     */
    public MultipleExitException(String s) {
        super(s);
    }
}

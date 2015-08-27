/**
 * Golf ball, it is thrown on the field by the golfers
 * it is picked from the field and put to the stash by Bollie
 * @author Michelle
 * @version 1 by Othniel
 */
package golfGame;

public class golfBall {
	
	private static int noBalls;
	private int myID;
	
	/**
	 * Create a golfBall and give it an ID
	 */
	golfBall() {
		myID=noBalls;
		incID();
	}
	
	/**
	 * Return ball's ID
	 * @return the Id of the golfBall
	 */
	public int getID() {
		return myID;		
	}
	
	/**
	 * Increment ball's number (i.e. create new ID)
	 */
	private static void  incID() {
		noBalls++;
	}
	
}

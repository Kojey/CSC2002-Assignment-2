package golfGame;


public class golfBall {
	//add mechanisms for thread saftey
	private static int noBalls;
	private int myID;
	
	golfBall() {
		myID=noBalls;
		incID();
	}
	
	/*
	 * Return ball's ID
	 */
	public int getID() {
		return myID;		
	}
	
	/*
	 * Increment ball's number
	 */
	private static void  incID() {
		noBalls++;
	}
	
}

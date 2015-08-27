/**
 * Simulation of a Bollie
 * Bollie is the one who clean the field (take balls on the field)
 * He/She is responsible of the filling of the stash  
 * @author Michelle
 * @version 1 by Othniel
 */
package golfGame;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bollie extends Thread{

	private AtomicBoolean done;  // flag to indicate when threads should stop
	
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random waitTime;

	//link to shared field
	/**
	 * @param stash the stash shared with the golfers
	 * @param field field object representing the range
	 * @param doneFlag announce when the club closes
	 */
	Bollie(BallStash stash,Range field,AtomicBoolean doneFlag) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		waitTime = new Random();
		done = doneFlag;
	}
	
	
	public synchronized void run() {
		
		// Create a bucket for the collection of ball
		golfBall [] ballsCollected = new golfBall[sharedStash.getSizeStash()];
		// Collect balls if the the Club is not closing 
		do{
			try {
				sleep(waitTime.nextInt(1000));
				System.out.println("*********** Bollie collecting balls   ************");	
				
				// collect balls, no golfers allowed to swing while this is happening
				Golfer.setCart(new AtomicBoolean(true));
				// count the number of balls collected
				int k = sharedField.collectAllBallsFromField(ballsCollected);
				System.out.println("*********** Bollie collected "+k+" balls from range ************");
				sleep(1000);
				// bollie leave the range, golfer can swing
				Golfer.setCart(new AtomicBoolean(false));
				// add balls to stash
				sharedStash.addBallsToStash(ballsCollected,k);
				System.out.println("*********** Bollie adding "+k+" balls to stash ("+sharedStash.getBallsInStash()+" ball(s) in stash)************");	
				
				} catch (InterruptedException e) {e.printStackTrace();} 
		}while (done.get()!=true);
		
	}	
	
}

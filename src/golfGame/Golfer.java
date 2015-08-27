/**
 * Golfer plays the game
 * He/She fill his/her bucket and swing the balls to the range 
 * @author Michelle
 * @version 1 by Othniel
 */
package golfGame;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Golfer extends Thread {
	
	// boolean variable 
	private AtomicBoolean done; 
	private static AtomicBoolean cartOnField;
	
	// integers variable
	private static AtomicInteger busyTees = new AtomicInteger(0);
	private static int maxTees;
	private static int noGolfers; //shared amoungst threads
	private  static int ballsPerBucket=4; //shared amongst threads
	private  static int maxBucket=4; 
	private  int bucketTaken=0; 
	private int myID;
	private int sleepTime;
	
	// Object needed
	private golfBall  [] golferBucket;
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random swingTime;
	
	
	
	/**
	 * 
	 * @param stash the stash shared with Bollie and the other golfer
	 * @param field the field on with balls are thrown
	 * @param cartFlag an indication of Bollie's presence on the field
	 * @param doneFlag an indication of the Club closing its door
	 * @param sleep the time taken by the golfer to arrive at the club 
	 */
	Golfer(BallStash stash,Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag, int sleep) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		cartOnField = cartFlag; //shared
		done = doneFlag;
		golferBucket = new golfBall[ballsPerBucket];
		swingTime = new Random();
		myID=newGolfID();
		sleepTime = sleep;
	}
	/**
	 * Generate an ID for the golfer
	 * @return the golfer's ID
	 */
	public  static int newGolfID() {  
		noGolfers++;
		return noGolfers;
	}
	/**
	 * Set the number of balls per bucket
	 * @param noBalls the number of balls per bucket
	 */
	public static void setBallsPerBucket (int noBalls) {
		ballsPerBucket=noBalls;
	}
	/**
	 * Return the number of balls per bucket
	 * @return the number of balls per bucket
	 */
	public static int getBallsPerBucket () {
		return ballsPerBucket;
	}
	/**
	 * Set the value of the cart
	 * @param c the boolean value of cart
	 */
	public static void setCart(AtomicBoolean c){
		cartOnField = c;
	}
	/**
	 * Return the number of tees available
	 * @return the number of tees occupied
	 */
	public static AtomicInteger getBusyTees() {
		return busyTees;
	}
	/**
	 * Set the maximum number of tees in the club
	 * @param maxTees the maximum number of tees in the club
	 */
	public static void setMaxTees(int maxTees) {
		Golfer.maxTees = maxTees;
	}
	/**
	 * Set the maximum number of buckets per golfer
	 * @param maxBucket the maximum number of bucket per golfer
	 */
	public static void setMaxBucket(int maxBucket) {
		Golfer.maxBucket = maxBucket;
	}
	/**
	 * Get the maximum number of bucket per golfer
	 * @return maximum number of bucket per golfer
	 */
	public static int getMaxTees() {
		return maxTees;
	}
	/**
	 * Set the number of tee which are occupied
	 * @param busyTees the number of occupied tees
	 */
	public static void setBusyTees(int busyTees) {
		Golfer.busyTees = new AtomicInteger(busyTees);
	}
	/**
	 * Run method of the golfer thread
	 */
	public synchronized void run() {
		// Golfer arrives at random time
		System.out.println(">>> Golfer #"+ myID +" waiting for "+sleepTime+"ms to start.");
		try {sleep(sleepTime);} catch (InterruptedException e1) {e1.printStackTrace();}
		
		while (done.get()!=true) { // While the Club is not closing or the golfer
								// did not take up to the maximum of bucket allowed
		if( bucketTaken<maxBucket ){	
			System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");
			// Check if there is enough balls in the stash
			while(sharedStash.getBallsInStash()<ballsPerBucket && done.get()!=true){
				// if game if over
				if(done.get()==true){
					System.out.println(">>> Golfer #"+ myID +" Couldn't play.");
						System.out.println("*********** Bollie adding "+ballsPerBucket+" balls from golfer #"+myID+" to stash ************");
						sharedStash.addBallsToStash(golferBucket,ballsPerBucket);
						return;
					}
			}
			golferBucket = BallStash.getBucketBalls(ballsPerBucket);
			bucketTaken++;
			System.out.println("<<< Golfer #"+ myID + " filled bucket with "+getBallsPerBucket()+" balls (remaining stash="+sharedStash.getBallsInStash() +")");
			
			// check if tee is empty
			int p=0;
			//synchronized (this){
			while(busyTees.get()>=maxTees){
				// wait for tee to be free
				if(p==0){
					System.out.println(">>> Golfer #"+ myID +" waiting for empty tee.");
				}
				p++;
				System.out.print("");// Refresh the thread
			if(done.get()==true){
				System.out.println(">>> Golfer #"+ myID +" Couldn't play.");
					System.out.println("*********** Bollie adding "+ballsPerBucket+" balls from golfer #"+myID+" to stash ************");
					sharedStash.addBallsToStash(golferBucket,ballsPerBucket);
					return;
				}
			}
				// get hold of a tee
			synchronized (this){
				setBusyTees(getBusyTees().get()+1);
				System.out.println(">>> Golfer #"+ myID +" on tee #"+busyTees.get()+".");
			}
			for (int b=0;b<ballsPerBucket;b++)
			{ //for every ball in bucket
				
			    try {
					sleep(swingTime.nextInt(2000));
					// sharedField.hitBallOntoField
					sharedField.hitBallOntoField(golferBucket[b]);
					System.out.println("Golfer #"+ myID + " hit ball #"+golferBucket[b].getID()+" onto field");	
					
				} catch (InterruptedException e) {e.printStackTrace();} //      swing
			    //!!wait for cart if necessary if cart there
			    if(cartOnField!=null){
			    	while(cartOnField.get()==true){
			    		//try {this.wait();} catch (InterruptedException e) {e.printStackTrace();}	
			    	}
			    }
			}// swing all balls
			
			// leave the tee
		    synchronized (this){
		    	System.out.println(">>> Golfer #"+ myID + " leave tee "+busyTees.get()+".");
				setBusyTees(getBusyTees().get()-1);
			}
		}	
		} // still not closed
		
	}


}


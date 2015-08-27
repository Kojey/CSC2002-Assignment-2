/**
 * Simulation of the range
 * The place where the golfer swing the balls to
 * @author Michelle
 * @version 1 by Othniel
 */
package golfGame;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Range {
	private static int sizeStash=20;
	private AtomicBoolean cartOnField;

	static ArrayBlockingQueue<golfBall> ballsOnField = new ArrayBlockingQueue<golfBall>(sizeStash);
	
	/**
	 * @param stash the number of element in the stash
	 * @param cart a boolean indicating the presence of bollie on the field
	 */
	Range(int stash, AtomicBoolean cart){
		sizeStash = stash;
		cartOnField = cart;
	}
	
	/**
	 * Collect all balls on the field and put it in Bollie's bucket 
	 * @param ballsCollected Bollie's bucket for balls' collection
	 * @return the number of balls collected
	 */
	public static int collectAllBallsFromField(golfBall [] ballsCollected){
		int k = 0;
		for(int i=0; i<ballsCollected.length; i++) 
			{
				if(!ballsOnField.isEmpty()){
					try {ballsCollected[i]=ballsOnField.take();} 
					catch (InterruptedException e) {e.printStackTrace();} // Move balls from field to stash
					k++;
				}
			}
		return k;
	}
	
	/**
	 * Simulate the throwing of a ball on the range
	 * @param ball the ball to be swung
	 */
	public static void hitBallOntoField(golfBall ball) {
		try {ballsOnField.put(ball);} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
}

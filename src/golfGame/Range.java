package golfGame;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;

public class Range {
	private static int sizeStash=20;
	private AtomicBoolean cartOnField;

	//ADD variable: ballsOnField collection;
	static ArrayBlockingQueue<golfBall> ballsOnField = new ArrayBlockingQueue<golfBall>(sizeStash);
	
	//Add constructors
	Range(int stash, AtomicBoolean cart){
		sizeStash = stash;
		cartOnField = cart;
	}
	
	//ADD method: collectAllBallsFromField(golfBall [] ballsCollected) 
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
	
	//ADD method: hitBallOntoField(golfBall ball) 
	public static void hitBallOntoField(golfBall ball) {
		try {ballsOnField.put(ball);} 
		catch (InterruptedException e) {e.printStackTrace();}
	}
}

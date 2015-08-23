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
	public static void collectAllBallsFromField(golfBall [] ballsCollected){
		for(int i=0; i<ballsCollected.length; i++) 
			ballsCollected[i]=ballsOnField.poll(); // Move balls from field to stash
	}
	
	//ADD method: hitBallOntoField(golfBall ball) 
	public static void hitBallOntoField(golfBall ball) {
		ballsOnField.add(ball);
	}
}

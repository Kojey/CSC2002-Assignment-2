package golfGame;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;

public class Bollie extends Thread{

	private AtomicBoolean done;  // flag to indicate when threads should stop
	
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random waitTime;

	//link to shared field
	Bollie(BallStash stash,Range field,AtomicBoolean doneFlag) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		waitTime = new Random();
		done = doneFlag;
	}
	
	
	public synchronized void run() {
		
		//while True
		golfBall [] ballsCollected = new golfBall[sharedStash.getSizeStash()];
		 do{
			try {
				sleep(waitTime.nextInt(1000));
				System.out.println("*********** Bollie collecting balls   ************");	
				// sharedField.collectAllBallsFromField(ballsCollected);	
				// collect balls, no golfers allowed to swing while this is happening
				Golfer.setCart(new AtomicBoolean(true));
				int k = sharedField.collectAllBallsFromField(ballsCollected);
				System.out.println("*********** Bollie collected "+k+" balls from range ************");
				sleep(1000);
				Golfer.setCart(new AtomicBoolean(false));
				//this.notifyAll();
				System.out.println("*********** Bollie adding "+k+" balls to stash ************");	
				//sharedStash.addBallsToStash(ballsCollected,noCollected);
				Golfer.setStash(new AtomicBoolean(true));
				sharedStash.addBallsToStash(ballsCollected,k);
				Golfer.setStash(new AtomicBoolean(false));
				} catch (InterruptedException e) {e.printStackTrace();} 
		}while (done.get()!=true);
		
	}	
	
}

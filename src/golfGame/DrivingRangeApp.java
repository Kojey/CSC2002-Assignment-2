package golfGame;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;
public class DrivingRangeApp {


	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done  =new AtomicBoolean(false);
		AtomicBoolean cart  =new AtomicBoolean(false);
		//read these in as command line arguments instead of hard coding
		int noGolfers=3;
		int sizeStash=20;
		int sizeBucket=5;
		
		if(args.length<3){	// A least three arguments
			System.out.println("Using default argment");
		}
		else if(Integer.valueOf(args[2])>Integer.valueOf(args[1])){ // Check input's logic
			System.out.println("Bucket's size cannot be greater than Stash's size");
			return;
		}
		else{
			noGolfers = Integer.valueOf(args[0]);
			sizeStash = Integer.valueOf(args[1]);
			sizeBucket = Integer.valueOf(args[2]);
		}
		
		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);
		
		//initialize shared variables
		BallStash bStash = new BallStash();
		for(int i=0; i<sizeStash; i++){	// add balls to stash
			bStash.addBallsToStash(new golfBall());
		}
		Range field = new Range(sizeStash,cart);
		
		
		//create threads and set them running
		for(int i=0; i<noGolfers; i++){
			
		}
		Bollie bollie = new Bollie(bStash, field, done);
		bollie.start();
		
		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"  ======");

		//for testing, just run for a bit
		Random r = new Random();
		Thread.sleep(r.nextInt(1000));// this is an arbitrary value - you may want to make it random
		done.set(true);
		System.out.println("=======  River Club Driving Range Closing ========");

		
	}

}

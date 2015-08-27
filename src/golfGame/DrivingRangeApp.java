/**
 * Main class of the Simulation, simulate the arrival of the golfers, the arrival of bollie
 * and fill the stash at the beginning
 * @author Michelle
 * @version 1 by Othniel
 */
package golfGame;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;
public class DrivingRangeApp {


	public static void main(String[] args) throws InterruptedException {
		AtomicBoolean done  =new AtomicBoolean(false);
		AtomicBoolean cart  =new AtomicBoolean(false);
		//read these in as command line arguments instead of hard coding
		int noGolfers=2;
		int sizeStash=20;
		int sizeBucket=5;
		int maxTees = 3;
		int maxBucket = 2;
		
		// A least four arguments
		if(args.length<6){	
			System.out.println("Cannot run with less than five arguments....");
			System.out.println("Using default value for arguments.");
		}
		// Number of balls per bucket should be less than number of balls in stash
		else if(Integer.valueOf(args[2])>Integer.valueOf(args[1])){ 
			System.out.println("Bucket's size cannot be greater than Stash's size.");
			System.out.println("Using default value for sizeStash and sizeBucket.");
			noGolfers = Integer.valueOf(args[0]);
		}
		// size of bucket entered should be less than the maximum
		else if(Integer.valueOf(args[4])<0){
			System.out.println("Number of bucket allowed shoud not be less than 0");
			System.out.println("Using default value for arguments");
		}
		// Use default value for the program
		else{
			noGolfers = Integer.valueOf(args[0]);
			sizeStash = Integer.valueOf(args[1]);
			sizeBucket = Integer.valueOf(args[2]);
			maxTees = Integer.valueOf(args[3]);
			maxBucket = Integer.valueOf(args[4]);
		}
		
		// Set shared variables for classes
		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);
		Golfer.setMaxTees(maxTees);
		Golfer.setMaxBucket(maxBucket);
		
		// Creation of object needed
		Random r = new Random();
		BallStash bStash = new BallStash(sizeStash);
		Range field = new Range(sizeStash,cart);
		Golfer [] golfer = new Golfer[noGolfers];
		
		// Add balls to the stash
		for(int i=0; i<sizeStash; i++){	// add balls to stash
			bStash.addBallsToStash(new golfBall());
		}
		
		// Open Club
		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls:"+sizeStash+ " bucketSize:"+sizeBucket+" maxTees:"+maxTees+" maxBucket:"+maxBucket+"  ======");

		// Create golfer threads and start it
		for(int i=0; i<noGolfers; i++){
			golfer[i] = new Golfer(bStash,field,cart,done,r.nextInt(1000));
			golfer[i].start();
		}
		
		// Create Bollie thread and start it
		Bollie bollie = new Bollie(bStash, field, done);
		bollie.start();
	
		//for testing, just run for a bit
		Thread.sleep(r.nextInt(10000));// this is an arbitrary value - you may want to make it random
		done.set(true);
		// Announce closing of the club
		System.out.println("=======  River Club Driving Range Closing ========");
		
		// Wait for all the golfer to finish their game before Bollie goes to pick the last balls
		// on the filed
		for(int i=0; i<noGolfers; i++){
			golfer[i].join();
		}
		bollie.run();
		// Close of the club
		System.out.println("=======  River Club Driving Range Closed ========");
				
	}

}

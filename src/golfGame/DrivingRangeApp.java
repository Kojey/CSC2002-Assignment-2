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
		int maxBucketSize = 7;
		int maxTees = 3;
		
		if(args.length<4){	// A least three arguments
			System.out.println("Cannot run with less than three arguments....");
			System.out.println("Using default value for arguments.");
		}
		else if(Integer.valueOf(args[2])>Integer.valueOf(args[1])){ // Check input's logic
			System.out.println("Bucket's size cannot be greater than Stash's size.");
			System.out.println("Using default value for sizeStash and sizeBucket.");
			noGolfers = Integer.valueOf(args[0]);
		}
		else if(Integer.valueOf(args[2])>maxBucketSize){
			System.out.println("Bucket's size is greater than the maximum allowed");
			System.out.println("Using default value for sizeStash and sizeBucket.");
			noGolfers = Integer.valueOf(args[0]);
		}
		else{
			noGolfers = Integer.valueOf(args[0]);
			sizeStash = Integer.valueOf(args[1]);
			sizeBucket = Integer.valueOf(args[2]);
			maxTees = Integer.valueOf(args[3]);
		}
		
		Random r = new Random();
		BallStash.setSizeStash(sizeStash);
		BallStash.setSizeBucket(sizeBucket);
		Golfer.setBallsPerBucket(sizeBucket);
		Golfer.setMaxTees(maxTees);
		//initialize shared variables
		BallStash bStash = new BallStash(sizeStash);
		
		for(int i=0; i<sizeStash; i++){	// add balls to stash
			bStash.addBallsToStash(new golfBall());
		}
		Range field = new Range(sizeStash,cart);
		System.out.println("=======   River Club Driving Range Open  ========");
		System.out.println("======= Golfers:"+noGolfers+" balls: "+sizeStash+ " bucketSize:"+sizeBucket+"maxTees:"+maxTees+"  ======");

		
		//create threads and set them running
		Golfer [] golfer = new Golfer[noGolfers];
		for(int i=0; i<noGolfers; i++){
			golfer[i] = new Golfer(bStash,field,cart,done,r.nextInt(1000));
			golfer[i].start();
			//if(Golfer.getBusyTees().get()<Golfer.getMaxTees())
			//	Golfer.setBusyTees(Golfer.getBusyTees().get()+1);
		}
		Bollie bollie = new Bollie(bStash, field, done);
		bollie.start();
		
		
		//for testing, just run for a bit
		
		Thread.sleep(r.nextInt(10000));// this is an arbitrary value - you may want to make it random
		done.set(true);
		System.out.println("=======  River Club Driving Range Closing ========");
		//
		for(int i=0; i<noGolfers; i++){
			golfer[i].join();
		}
		bollie.run();
		
	}

}

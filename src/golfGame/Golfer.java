package golfGame;

import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class Golfer extends Thread {

	//remeber to ensure thread saftey
	
	private AtomicBoolean done; 
	private static AtomicBoolean cartOnField;
	private static AtomicBoolean stashFill=new AtomicBoolean(false);
	
	private static AtomicInteger busyTees = new AtomicInteger(0);
	private static int maxTees;
	private static int noGolfers; //shared amoungst threads
	private  static int ballsPerBucket=4; //shared amoungst threads
	
	private int myID;
	private int sleepTime;
	
	private golfBall  [] golferBucket;
	private BallStash sharedStash; //link to shared stash
	private Range sharedField; //link to shared field
	private Random swingTime;
	
	
	
	
	Golfer(BallStash stash,Range field, AtomicBoolean cartFlag, AtomicBoolean doneFlag, int s) {
		sharedStash = stash; //shared 
		sharedField = field; //shared
		cartOnField = cartFlag; //shared
		done = doneFlag;
		golferBucket = new golfBall[ballsPerBucket];
		swingTime = new Random();
		myID=newGolfID();
		sleepTime = s;
	}

	public  static int newGolfID() { 
		noGolfers++;
		return noGolfers;
	}
	
	public static void setBallsPerBucket (int noBalls) {
		ballsPerBucket=noBalls;
	}
	public static int getBallsPerBucket () {
		return ballsPerBucket;
	}
	public static AtomicBoolean getStash(){
		return stashFill;
	}
	public static void setStash(AtomicBoolean c){
		stashFill = c;
	}
	public static void setCart(AtomicBoolean c){
		cartOnField = c;
	}
	public static AtomicInteger getBusyTees() {
		return busyTees;
	}

	public static void setMaxTees(int maxTees) {
		Golfer.maxTees = maxTees;
	}
	public static int getMaxTees() {
		return maxTees;
	}

	public static void setBusyTees(int busyTees) {
		Golfer.busyTees = new AtomicInteger(busyTees);
	}

	public synchronized void run() {
		System.out.println(">>> Golfer #"+ myID +" waiting for "+sleepTime+"ms to start.");
		try {sleep(sleepTime);} catch (InterruptedException e1) {e1.printStackTrace();}
		
		while (done.get()!=true) {
			
			System.out.println(">>> Golfer #"+ myID + " trying to fill bucket with "+getBallsPerBucket()+" balls.");
			// Check if there is enough balls in the stash
			while(sharedStash.getBallsInStash()<ballsPerBucket){
				// if game if over
				if(done.get()==true){
					System.out.println(">>> Golfer #"+ myID +" Couldn't play.");
						System.out.println("*********** Bollie adding "+ballsPerBucket+" balls from golfer #"+myID+" to stash ************");
						sharedStash.addBallsToStash(golferBucket,ballsPerBucket);
						return;
					}
			}
			golferBucket = BallStash.getBucketBalls(ballsPerBucket);
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
			
		} // still not closed
		
	}


}


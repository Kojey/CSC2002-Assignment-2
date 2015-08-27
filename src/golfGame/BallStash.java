package golfGame;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BallStash {
	//static variables
	private static int sizeStash=15;
	private static int sizeBucket=2;
	
	 //ADD variables: a collection of golf balls, called stash
	static ArrayBlockingQueue<golfBall> stash;
	
	BallStash(int size){
		stash =  new ArrayBlockingQueue<golfBall>(size); 
	}
	//ADD methods:
	//getBucketBalls
	/*
	 * Given a bucket size, it returns a bucket filled of golf balls
	 */
	synchronized public static golfBall[] getBucketBalls(int size){
		golfBall[] bucket = new golfBall[size];
		for(int i=0; i<size; i++){
			try { bucket[i]=stash.take();} 
			catch (InterruptedException e){ e.printStackTrace();}
		}
		return bucket;
	}
	// addBallsToStash
	/*
	 * Add a ball to the stash
	 */
	public void addBallsToStash(golfBall b){
			try{stash.put(b);} 
			catch (InterruptedException e) {e.printStackTrace();}
	}
	/*
	 * Add a ball to the stash
	 */
	public static void addBallsToStash(golfBall [] b, int size){
		for(int i=0; i<size; i++)
			if(b[i]!=null){
				try{stash.put(b[i]);}
				catch(InterruptedException e){e.printStackTrace();}
			}
	}
	// getBallsInStash - return number of balls in the stash
	/*
	 * return number of balls in the stash
	 */
	synchronized public static int getBallsInStash(){
		return stash.size();
	}
	
	//getters and setters for static variables - you need to edit these
	public static  void setSizeBucket (int noBalls) {
		sizeBucket=noBalls;
	}
	public static int getSizeBucket () {
		return sizeBucket;
	}
	public static void setSizeStash (int noBalls) {
		sizeStash=noBalls;
	}
	public static int getSizeStash () {
		return sizeStash;
	}
	
	
}

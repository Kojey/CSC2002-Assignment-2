/**
 * BallStash represents the balls in the stash 
 * @author Michelle
 * @version 1 by Othniel
 */
package golfGame;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BallStash {
	//static variables
	private static int sizeStash=15;
	private static int sizeBucket=2;
	
	static ArrayBlockingQueue<golfBall> stash;
	
	BallStash(int size){
		stash =  new ArrayBlockingQueue<golfBall>(size); 
	}
	
	/**
	 * Take balls from the stash
	 * @param size the number of ball to take from stash
	 * @return a bucket containing the balls taken from stash
	 */
	synchronized public static golfBall[] getBucketBalls(int size){
		golfBall[] bucket = new golfBall[size];
		for(int i=0; i<size; i++){
			try { bucket[i]=stash.take();} 
			catch (InterruptedException e){ e.printStackTrace();}
		}
		return bucket;
	}
	/**
	 * Add a ball to stash
	 * @param b the ball to be added to stash
	 */
	public void addBallsToStash(golfBall b){
			try{stash.put(b);} 
			catch (InterruptedException e) {e.printStackTrace();}
	}
	/**
	 * Add a bucket of ball to stash
	 * @param b a bucket of ball
	 * @param size the number of ball in the bucket
	 */
	public static void addBallsToStash(golfBall [] b, int size){
		for(int i=0; i<size; i++)
			if(b[i]!=null){
				try{stash.put(b[i]);}
				catch(InterruptedException e){e.printStackTrace();}
			}
	}
	/**
	 * Get the number of ball in the stash
	 * @return the number of balls in the stash
	 */
	synchronized public static int getBallsInStash(){
		return stash.size();
	}
	
	/**
	 * Set the size of the bucket
	 * @param noBalls the size of the bucket
	 */
	public static  void setSizeBucket (int noBalls) {
		sizeBucket=noBalls;
	}
	/**
	 * Set the size of the bucket
	 * @return the size of the bucket
	 */
	public static int getSizeBucket () {
		return sizeBucket;
	}
	/**
	 * Set the size of the stash
	 * @param noBalls the size of stash
	 */
	public static void setSizeStash (int noBalls) {
		sizeStash=noBalls;
	}
	/**
	 * Get the size of the stash
	 * @return the size of the stash
	 */
	public static int getSizeStash () {
		return sizeStash;
	}
	
	
}

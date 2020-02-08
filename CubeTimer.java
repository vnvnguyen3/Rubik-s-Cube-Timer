/*
 * CubeTimer class
 * Starts and stops a hidden timer 
 */
public class CubeTimer {
	boolean running = false;
	double begin = 0;
	double end = 0;
	double time = 0;
	/*
	 * isRunning method
	 * Returns true if the timer is still running
	 */
	public boolean isRunning() {
		return running;
	}
	/*
	 * start method
	 * Begins the timer
	 */
	public void start() {
		begin = System.currentTimeMillis();
		running = true;
	}
	/*
	 * stop method
	 * Finishes the timer
	 */
	public void stop() {
		end = System.currentTimeMillis();
		time = end - begin;
		running = false;
	}
	/*
	 * getTime method
	 * Returns a solve time with two decimal digits
	 */
	public double getTime() {
		return (double)Math.round(time/10)/100;
	}
}

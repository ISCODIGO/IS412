/**
 * Utilities for causing a thread to sleep.
 * Note, we should be handling interrupted exceptions
 * but choose not to do so for code clarity.
 */

package is412.concurrencia;

public class SleepUtilities {
	/**
	 * Nap between zero and NAP_TIME seconds.
	 */
	public static void nap() {
		nap(NAP_TIME);
	}

	/**
	 * Nap between zero and duration seconds.
	 */
	public static void nap(int duration) {
		int sleepTime = (int) (duration * Math.random());

		try {
			Thread.sleep(sleepTime * 1000);
		} catch (InterruptedException e) {
		}
	}

	private static final int NAP_TIME = 5;
}

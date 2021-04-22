/**
 * GetAndSetFactory.java
 *
 * This program tests the get-and-set instructions.
 */

package is412.concurrencia.hardware;

public class GetAndSetFactory {
	public static void main(String args[]) {
		HardwareData lock = new HardwareData(false);

		for (int i = 0; i < 3; i++)
			new Thread(new Worker("worker " + i, lock)).start();
	}
}

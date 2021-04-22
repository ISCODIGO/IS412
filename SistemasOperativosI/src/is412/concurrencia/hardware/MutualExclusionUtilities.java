
/**
 * MutualExclusionUtilities.java
 *
 * Utilities for simulating critical and non-critical sections.
 */
package is412.concurrencia.hardware;

public class MutualExclusionUtilities {
   public static int DURATION = 5;
   /**
    * critical and non-critical sections are simulated by sleeping
    * for a random amount of time between 0 and 3 seconds.
    */
   public static void criticalSection(String name) {
      System.out.println(name + " *** entra a CS ***");
      SleepUtilities.nap(DURATION);
   }
   
   public static void remainderSection(String name) {
      System.out.println(name + " sale de CS");
      SleepUtilities.nap(DURATION);
   }
}

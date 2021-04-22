/*****************************************************************************

 El siguiente codigo establece un problema comun en la concurrencia. En este
 caso puede incurrir en la modificación errónea del atributo 'count' de la
 clase 'ProcessingThread'. Se supone que el bucle se itera 5 veces y al
 existir 2 hilos se asume que el conteo tendrá el valor 10.

 Fuente: https://www.journaldev.com/1061/thread-safety-in-java

*****************************************************************************/

package is412.concurrencia.problema;

public class ThreadSafety {
    public static void main(String[] args) throws InterruptedException {
        ProcessingThread pt = new ProcessingThread();
        Thread t1 = new Thread(pt, "t1");
        t1.start();
        Thread t2 = new Thread(pt, "t2");
        t2.start();
        //Esperar que ambos hilos terminen
        t1.join();
        t2.join();
        System.out.println("Processing count=" + pt.getCount());
    }

}

class ProcessingThread implements Runnable {
    private int count;

    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            procesarAlgo(i);
            this.count++;
        }
    }

    public int getCount() {
        return this.count;
    }

    private void procesarAlgo(int i) {
        try {
            Thread.sleep(i * 100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
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
        Thread t2 = new Thread(pt, "t2");
        t1.start();
        t2.start();
        // Espera para finalizacion
        t1.join();
        t2.join();
        System.out.printf("Conteo procesado= %d%n", pt.getCount());
        System.out.printf("Conteo esperado= %d%n", pt.ITERACIONES * 2);
    }

}

class ProcessingThread implements Runnable{
    public final int ITERACIONES = 5;
    private int count;

    @Override
    public void run() {
        for(int i=1; i <= ITERACIONES; i++){
            processSomething(i);
            count++;
        }
    }

    public int getCount() {
        return this.count;
    }

    private void processSomething(int i) {
        // processing some job
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
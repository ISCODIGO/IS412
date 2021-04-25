package is412.concurrencia.semaforos;

import is412.concurrencia.ButtonStatus;

import javax.swing.*;
import java.awt.*;

//Main class
public class SemaforoFactory {
    private final int PROCESOS = 4;
    private final int ITERACIONES = 5;
    private JFrame ventana;
    private ButtonStatus[] botones;

    public static void main(String[] args) throws InterruptedException {
        SemaforoFactory factory = new SemaforoFactory();
        factory.crearVentana();
        factory.ejecutar();
    }

    public void crearVentana() {
        ventana = new JFrame("Sincronizacion por Hardware");
        Container contentPane = ventana.getContentPane();
        contentPane.setLayout(new GridLayout(0, 2));
        botones = new ButtonStatus[PROCESOS];

        for (int i = 0; i < PROCESOS; i++) {
            botones[i] = new ButtonStatus(String.format("Worker %d", i));
            contentPane.add(botones[i]);
        }

        ventana.setSize(300, 300);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void ejecutar() throws InterruptedException {
        Semaforo semaphore = new Semaforo(1);
        Worker[] workers = new Worker[PROCESOS];

        for (int i = 0; i < PROCESOS; i++) {
            workers[i] = new Worker(i, semaphore, String.format("Worker %d", i));
            workers[i].start();
        }

        for (int i = 0; i < PROCESOS; i++) {
            workers[i].join();
        }

        System.out.println("count: " + RecursoCompartido.count);
    }

    class Worker extends Thread {
        Semaforo semaphore;
        String threadName;
        int id;

        public Worker(int id, Semaforo semaphore, String threadName) {
            super(threadName);
            this.id = id;
            this.semaphore = semaphore;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            System.out.println("Iniciando " + threadName);
            ButtonStatus btn = botones[id];
            try {
                // First, get a permit.
                System.out.println(threadName + " espera por un permiso");
                btn.esperar();

                //Acquiring the lock
                semaphore.acquire();
                System.out.println(threadName + " obtiene un permiso");
                btn.entrar();

                for (int i = 0; i < ITERACIONES; i++) {
                    RecursoCompartido.count++;
                    System.out.println(threadName + ": " + RecursoCompartido.count);

                    //Allowing a context switch if possible.for thread B to execute
                    Thread.sleep(1000);
                }
            } catch (InterruptedException exc) {
                System.out.println(exc);
            }

            // Release the permit.
            btn.salir();
            System.out.println(threadName + " libera el permiso");
            semaphore.release();
        }
    }


}

class Semaforo {
    private int value;

    public Semaforo(int value) {
        this.value = value;
    }

    // WAIT
    public synchronized void acquire() throws InterruptedException {
        while (value <= 0) {
            wait();
        }

        value--;
    }

    // SIGNAL
    public synchronized void release() {
        ++value;
        notify();
    }
}

//A shared resource/class.
class RecursoCompartido {
    static int count = 0;
}
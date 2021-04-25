/**
 * GetAndSetFactory.java
 * <p>
 * This program tests the get-and-set instructions.
 */

package is412.concurrencia.hardware;

import is412.concurrencia.MutualExclusionUtilities;
import is412.concurrencia.ButtonStatus;

import javax.swing.*;
import java.awt.*;

public class GetAndSetFactory {
    private final int PROCESOS = 3;
    private JFrame ventana;
    private ButtonStatus[] botones;

    class Worker implements Runnable {
        private String name;                // nombre del thread
        private HardwareData mutex;        // compartido
        private int id;

        public Worker(int id, String name, HardwareData mutex) {
            this.id = id;
            this.name = name;
            this.mutex = mutex;
        }

        public void run() {
            while (true) {
                //ButtonStatus btn = botones[this.id];
                botones[this.id].esperar();
                System.out.println(name + " quiere entrar a CS");

                // Espera
                while (mutex.getAndSet(true)) {
                    Thread.yield(); // espera
                }

                botones[this.id].entrar();
                MutualExclusionUtilities.criticalSection(name);
                mutex.setValue(false);
                botones[this.id].salir();
                MutualExclusionUtilities.nonCriticalSection(name);
            }
        }
    }

    public void crearVentana() {
        ventana = new JFrame("Sincronizacion por Hardware");
        Container contentPane = ventana.getContentPane();
        contentPane.setLayout(new GridLayout(3, 0));
        botones = new ButtonStatus[PROCESOS];

        for(int i = 0; i < PROCESOS; i++) {
            botones[i] = new ButtonStatus(String.format("Worker %d", i));
            contentPane.add(botones[i]);
        }

        ventana.setSize(300, 300);
        ventana.setVisible(true);
        ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public void ejecutar() {
        HardwareData lock = new HardwareData(false);

        for (int i = 0; i < PROCESOS; i++) {
            new Thread(new GetAndSetFactory.Worker(i, String.format("Worker %d", i), lock)).start();
        }
    }

    public static void main(String args[]) {
        GetAndSetFactory factory = new GetAndSetFactory();
        factory.crearVentana();
        factory.ejecutar();
    }
}


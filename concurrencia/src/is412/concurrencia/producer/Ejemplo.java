package is412.concurrencia.producer;

import is412.concurrencia.ButtonStatus;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class Ejemplo {
    public static void main(String[] args)
            throws InterruptedException {
        // Object of a class that has both produce()
        // and consume() methods
        final PC pc = new PC();
        pc.crearVentana();

        // Create producer thread
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Create consumer thread
        Thread t2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // Start both threads
        t1.start();
        t2.start();

        // t1 finishes before t2
        t1.join();
        t2.join();
    }

    // This class has a list, producer (adds items to list
    // and consumber (removes items).
    public static class PC {

        private JFrame frm;
        private ButtonStatus btnProducer;
        private ButtonStatus btnConsumer;
        private JButton btnLista;

        LinkedList<Integer> list = new LinkedList<>();
        int capacity = 5;

        public void crearVentana() {
            frm = new JFrame("Problema de Productor y Consumidor");
            Container contentPane = frm.getContentPane();
            contentPane.setLayout(new BorderLayout());

            JPanel panel1 = new JPanel();
            panel1.setLayout(new GridLayout(0, 2));
            contentPane.add(panel1, BorderLayout.CENTER);

            btnLista = new JButton();
            btnLista.setBackground(Color.white);
            btnLista.setFont(new Font("Courier New", Font.PLAIN, 24));
            contentPane.add(btnLista, BorderLayout.PAGE_END);


            btnProducer = new ButtonStatus("Productor");
            panel1.add(btnProducer);
            btnConsumer = new ButtonStatus("Consumidor");
            panel1.add(btnConsumer);
            frm.setVisible(true);
            frm.setSize(500, 300);
            frm.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        }

        // Function called by producer thread
        public void produce() throws InterruptedException {
            int value = 0;
            while (true) {
                synchronized (this) {
                    // producer thread waits while list
                    // is full
                    btnProducer.esperar();
                    while (list.size() == capacity) {
                        wait();
                    }

                    btnProducer.entrar();
                    btnProducer.setText(String.format("Productor: %d", value));
                    System.out.println("Productor: " + value);
                    System.out.printf("Size linkedlist: %d%n", list.size());

                    // to insert the jobs in the list
                    list.add(value++);

                    btnLista.setText(list.toString());
                    System.out.println(list.toString());

                    // notifies the consumer thread that
                    // now it can start consuming
                    notify();

                    // makes the working of program easier
                    // to understand
                    Thread.sleep(1000);
                    btnProducer.salir();
                }
            }
        }

        // Function called by consumer thread
        public void consume() throws InterruptedException {
            while (true) {
                synchronized (this) {
                    btnConsumer.esperar();
                    while (list.size() == 0) {
                        wait();
                    }

                    int val = list.removeFirst();

                    btnLista.setText(list.toString());
                    System.out.println(list.toString());
                    btnConsumer.entrar();
                    btnConsumer.setText(String.format("Consumidor: %d", val));
                    System.out.println("Consumidor: " + val);
                    System.out.printf("Size linkedlist: %d%n", list.size());

                    // Wake up producer thread
                    notify();

                    // and sleep
                    Thread.sleep(1500);
                    btnConsumer.salir();
                }
            }
        }
    }
}


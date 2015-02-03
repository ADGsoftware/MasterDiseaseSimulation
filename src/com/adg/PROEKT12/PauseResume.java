package com.adg.PROEKT12;

//Imports

import javax.swing.*;
import java.awt.*;

public class PauseResume {
    public PauseResume(Frame frame) {
        counter.start();
        button.addActionListener(pauseResume);

        frame.add(button, java.awt.BorderLayout.NORTH);
        frame.pack();
        frame.setVisible(true);
    }

    private JButton button = new JButton("Start");

    private Object lock = new Object();
    private volatile boolean paused = true;

    private Thread counter = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                work();
            }
        }
    });

    private void work() {
        for (int i = 0; i < 10; i++) {
            allowPause();
            sleep();
        }
        done();
    }

    void allowPause() {
        synchronized (lock) {
            while (paused) {
                try {
                    lock.wait();
                } catch (InterruptedException e) {
                    // nothing
                }
            }
        }
    }

    private java.awt.event.ActionListener pauseResume =
            new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    paused = !paused;
                    button.setText(paused ? "Resume" : "Pause");
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            };

    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            // nothing
        }
    }

    private void done() {
        button.setText("Pause");
        paused = false;
    }
}
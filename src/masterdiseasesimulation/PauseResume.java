package masterdiseasesimulation;

//Imports
import java.awt.Dimension;
import java.awt.GridBagConstraints;

import javax.swing.JButton;
import javax.swing.JPanel;

public class PauseResume {
	public PauseResume(JPanel panel, GridBagConstraints c) {
		counter.start();
		button.addActionListener(pauseResume);
		button.setPreferredSize(new Dimension(100, 20));

		panel.add(button, c);
		//panel.setVisible(true);
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
			if (button.isEnabled()) {
				button.setText(paused ? "Resume" : "Pause");
			}
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
		if (button.isEnabled()) {
			button.setText("Pause");
		}
		paused = false;
	}
	
	public void disable() {
		button.setText("Done");
		button.setEnabled(false);
	}
}
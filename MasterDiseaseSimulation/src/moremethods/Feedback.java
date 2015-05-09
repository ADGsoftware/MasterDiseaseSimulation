package moremethods;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class Feedback {

	public static void run () {
		String[] inputs = getInput();
		if (inputs != null) {
			sendRequest(inputs);
		}
	}
	
	public static String[] getInput () {
		GridBagLayout layout = new GridBagLayout();
		JPanel panel = new JPanel(layout);
		GridBagConstraints c = new GridBagConstraints();
		layout.setConstraints(panel, c);
		
		c.anchor = GridBagConstraints.WEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Name:"), c);
		c.gridx = 1;
		JTextField name = new JTextField(10);
		name.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(name, c);
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Email*:"), c);
		c.gridx = 1;
		JTextField email = new JTextField(10);
		email.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		/*
		email.getDocument().addDocumentListener(new DocumentListener() {
			public void action() {
				if (!email.getText().contains("@")) {
					email.setBorder(BorderFactory.createLineBorder(Color.RED));
				}
				else {
					email.setBorder(BorderFactory.createLineBorder(Color.GRAY));
				}
			}
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				action();
			}
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				action();
			}
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				action();
			}
		});
		*/
		panel.add(email, c);
		
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Subject:   "), c);
		c.gridx = 1;
		JTextField subject = new JTextField(10);
		subject.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(subject, c);
		
		c.ipady = 5;
		c.gridwidth = 2;
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Comment*:"), c);
		c.gridy++;
		c.gridx = 0;
		JTextArea comment = new JTextArea("");
		comment.setRows(8);
		comment.setColumns(50);
		comment.setSize(500, 500);
		comment.setLineWrap(true);
		comment.setWrapStyleWord(true);
		JScrollPane scrollPane = new JScrollPane(comment, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		panel.add(scrollPane, c);
		
		boolean again = true;
		while (again) {
			again = false;
			int result = JOptionPane.showConfirmDialog(null, panel, "Input", JOptionPane.OK_CANCEL_OPTION);
			if (result != JOptionPane.OK_OPTION) {
				return null;
			}
			if (email.getText().equals("") || !email.getText().contains("@")) {
				email.setBorder(BorderFactory.createLineBorder(Color.RED));
				again = true;
			}
			else {
				email.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
			if (comment.getText().trim().equals("")) {
				scrollPane.setBorder(BorderFactory.createLineBorder(Color.RED));
				again = true;
			}
			else {
				scrollPane.setBorder(BorderFactory.createLineBorder(Color.GRAY));
			}
		}
		
		String[] input = new String[4];
		input[0] = name.getText();
		input[1] = email.getText();
		input[2] = subject.getText();
		input[3] = comment.getText();
		
		return input;
	}
	
	public static void sendRequest (String[] inputs) {
		// Create progress bar
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(1,0));
		panel.add(new JLabel("Sending feedback..."));
		JFrame frame = new JFrame();
		JProgressBar progressBar = new JProgressBar(0, 1);
		progressBar.setIndeterminate(true);
		progressBar.setStringPainted(true);
		panel.add(progressBar);
		panel.setBorder(new EmptyBorder(5, 10, 5, 10));
		frame.add(panel);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final String username = "researchadg@gmail.com";
		final String password = "AlikGrishkaDyushka";
 
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(username));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
			message.setSubject(inputs[2]);
			message.setText(inputs[3] + "\n\n[REPLY: " + inputs[0] + " " + inputs[1] + "]");
			
			Transport.send(message);
			
			frame.dispose();
		}
		catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}
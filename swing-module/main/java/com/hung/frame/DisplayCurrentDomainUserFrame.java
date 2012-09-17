package com.hung.frame;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.hung.factory.FactoryMethods;

public class DisplayCurrentDomainUserFrame extends AbstractFrame implements ActionListener {

	private static Logger log = Logger.getLogger(DisplayCurrentDomainUserFrame.class);
	
	protected JTextField displayField = null;
	protected JButton checkIsLoggedinButton = null;
	protected JButton logoutButton = null;

	public DisplayCurrentDomainUserFrame() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
	}
	
	protected void setupUI() {
		JPanel contentPanel = new JPanel();
		
		displayField = new JTextField(10);
		
		checkIsLoggedinButton = new JButton("Check Is Logged In?");
		checkIsLoggedinButton.addActionListener(this);
		
		logoutButton = new JButton("logout");
		logoutButton.addActionListener(this);
		
		setLayout(new FlowLayout());
		add(displayField);
		add(checkIsLoggedinButton);
		add(logoutButton);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
	
	protected void displayField() {
		if (FactoryMethods.getLoginFactory().getServiceAdaptor().isLoggedIn()) {
			String loginId = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession().getLoginId();
			displayField.setText(loginId);
		} else {
			displayField.setText("Not loggedIn");
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				if (e.getSource().equals(logoutButton)) {
					FactoryMethods.getLoginFactory().getServiceAdaptor().logout();
				}
				displayField();
			}
		});
	}
}
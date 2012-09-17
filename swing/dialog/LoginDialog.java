package com.hung.dialog;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.apache.log4j.Logger;

import com.hung.adaptor.LoginServiceAdaptor;
import com.hung.factory.FactoryMethods;

public class LoginDialog extends JDialog implements ActionListener {
	private static Logger log = Logger.getLogger(LoginDialog.class);
	
	protected JTextField loginField = null;
	protected JButton loginButton = null;
	
	protected LoginServiceAdaptor loginService = null;
	
	public LoginDialog(JFrame frame) {
		super(frame);
		initializeModel();
		setupUI(frame);
	}
	
	protected void initializeModel() {
		loginService = (LoginServiceAdaptor) FactoryMethods.getLoginFactory().getServiceAdaptor();
	}
	
	protected void setupUI(JFrame frame) {
		loginField = new JTextField(10);
		
		loginButton = new JButton("login");
		loginButton.addActionListener(this);
		
		setLayout(new FlowLayout());
		add(new JLabel("domainName/loginName"));
		add(loginField);
		add(loginButton);
		
		pack();
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				String[] loginInfos = loginField.getText().split("/");	// apparent StringTokenizer is deprecated...
				if (loginInfos.length == 2) {
					String domainName = loginInfos[0];
					String loginId = loginInfos[1];
					log.info("domainName="+domainName+", loginId="+loginId);
					if (loginService.login(loginId, domainName)) {
						log.info("login with domainName="+domainName+", loginId="+loginId);
						setVisible(false);
					} else {
						log.info("failed to login with domainName="+domainName+", loginId="+loginId);
					}
				} else {
					log.info("failed to login with loginInfos="+loginInfos);
				}
			}
		});
	}
}
package com.hung.frame;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.hung.factory.GUIFactoryMethods;
import com.hung.picker.AbstractPicker;

public class DomainUserFrame extends AbstractFrame {

	private static Logger log = Logger.getLogger(DomainUserFrame.class);
	
	protected AbstractPicker domainUserPicker = null;

	public DomainUserFrame() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
	}
	
	protected void setupUI() {
		JPanel contentPanel = new JPanel();
		
		domainUserPicker = GUIFactoryMethods.getDomainUserGUIFactory().createPicker();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(domainUserPicker);
		
		setLayout(new FlowLayout());
		add(contentPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
}
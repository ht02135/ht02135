package com.hung.frame;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.hung.blotter.AbstractBlotter;
import com.hung.factory.GUIFactoryMethods;
import com.hung.picker.AbstractPicker;

public class DomainFrame extends AbstractFrame {

	private static Logger log = Logger.getLogger(DomainFrame.class);
	
	protected AbstractPicker domainPicker = null;
	protected AbstractBlotter domainBlotter = null;

	public DomainFrame() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
	}
	
	protected void setupUI() {
		JPanel contentPanel = new JPanel();
		
		domainPicker = GUIFactoryMethods.getDomainGUIFactory().createPicker();
		domainBlotter = GUIFactoryMethods.getDomainGUIFactory().createBlotter();
		
		contentPanel.setLayout(new FlowLayout());
		
		contentPanel.add(domainPicker);
		contentPanel.add(domainBlotter);
		
		setLayout(new FlowLayout());
		add(contentPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		pack();
		setVisible(true);
	}
}
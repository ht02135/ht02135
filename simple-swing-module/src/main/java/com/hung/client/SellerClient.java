package com.hung.client;

import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import com.hung.picker.SellerPicker;

public class SellerClient extends JFrame
{
	private static Logger log = Logger.getLogger(SellerClient.class);
	
	public SellerClient() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
	}
	
	protected void setupUI() {
		JPanel contentPanel = new JPanel();
		
		SellerPicker sellerPicker = new SellerPicker();
		contentPanel.setLayout(new FlowLayout());
		contentPanel.add(sellerPicker);
		
		setLayout(new FlowLayout());
		add(contentPanel);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
    public static void main( String[] args )
    {
    	new SellerClient();
    }
}

package com.hung.dialog;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;

import org.apache.log4j.Logger;

import com.hung.factory.FactoryMethods;

public class DomainSearchDialog extends AbstractSearchDialog {

	private static Logger log = Logger.getLogger(DomainSearchDialog.class);
	
	protected JList domainList = null;
	protected JButton okButton = null;
	protected JButton closeButton = null;
	
	protected List<Object> domains = null;
	
	public DomainSearchDialog(List<Object> domains) {
		initializeModel(domains);
		setupUI();
	}
	
	protected void initializeModel(List<Object> domains) {
		this.domains = domains;
	}
	
	protected void setupUI() {
		domainList = new JList(domains.toArray());
		
		okButton = new JButton("ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	Object domain = domainList.getSelectedValue();
            	if (domain != null) {
            		FactoryMethods.getDomainFactory().getCache().put(domain);
            		setVisible(false);
            	}
            }
        });
        
        closeButton = new JButton("close");
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	setVisible(false);
            }
        });
        
        setLayout(new FlowLayout());
        add(domainList);
        add(okButton);
        add(closeButton);
        
        pack();
        setVisible(true);
	}

}
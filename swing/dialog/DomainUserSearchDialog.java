package com.hung.dialog;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JList;

import org.apache.log4j.Logger;

import com.hung.factory.FactoryMethods;

public class DomainUserSearchDialog extends AbstractSearchDialog {
	private static Logger log = Logger.getLogger(DomainSearchDialog.class);
	
	protected JList domainUserList = null;
	protected JButton okButton = null;
	protected JButton closeButton = null;
	
	protected List<Object> domainUsers = null;
	
	public DomainUserSearchDialog(List<Object> domainUsers) {
		initializeModel(domainUsers);
		setupUI();
	}
	
	protected void initializeModel(List<Object> domainUsers) {
		this.domainUsers = domainUsers;
	}
	
	protected void setupUI() {
		domainUserList = new JList(domainUsers.toArray());
		
		okButton = new JButton("ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	Object domainUser = domainUserList.getSelectedValue();
            	if (domainUser != null) {
            		FactoryMethods.getDomainUserFactory().getCache().put(domainUser);
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
        add(domainUserList);
        add(okButton);
        add(closeButton);
        
        pack();
        setVisible(true);
	}

}
package com.hung.picker;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JList;

import org.apache.log4j.Logger;

import com.hung.auction.domain.Seller;
import com.hung.cache.SellerCache;

// in plain MVC, SellerPicker will burden with most responsibility of Controller
public class SellerSearchDialog extends JDialog {
	private static Logger log = Logger.getLogger(SellerSearchDialog.class);
	
	protected JList resultList = null;
	protected JButton okButton = null;
	protected JButton closeButton = null;
	
	protected List<Seller> sellers = null;
	protected JComboBox comboBox = null;
	
	public SellerSearchDialog(List<Seller> sellers) {
		initializeModel(sellers);
		setupUI();
	}
	
	protected void initializeModel(List<Seller> sellers) {
		this.sellers = sellers;
	}
	
	protected void setupUI() {
		resultList = new JList(sellers.toArray());
		
		okButton = new JButton("ok");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e)
            {
            	log.info("setupUI.okButton.actionPerformed: e="+e);
            	
            	Seller seller = (Seller) resultList.getSelectedValue();
            	if (seller != null) {
            		SellerCache.getInstance().putSeller(seller);
            		SellerCache.getInstance().fireCacheChanged();
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
        add(resultList);
        add(okButton);
        add(closeButton);
        
        setVisible(true);
	}

}
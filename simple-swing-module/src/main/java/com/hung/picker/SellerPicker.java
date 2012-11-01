package com.hung.picker;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hung.auction.domain.Seller;
import com.hung.auction.service.SellerService;
import com.hung.cache.SellerCache;
import com.hung.cache.SellerCacheEventListener;

// in plain MVC, SellerPicker will burden with most responsibility of Controller
public class SellerPicker extends JPanel implements ActionListener, SellerCacheEventListener {
	private static Logger log = Logger.getLogger(SellerPicker.class);
	
	protected JComboBox comboBox = null;
	protected JTextField idTextField = null;
	protected JButton findButton = null;
	
	protected ComboBoxModel comboBoxModel = null;
	private SellerService sellerService = null;
	
	public SellerPicker() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
		comboBoxModel = new DefaultComboBoxModel(SellerCache.getInstance().getSellers().toArray());
		
    	ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext-swing.xml");
    	sellerService = (SellerService) applicationContext.getBean("sellerService");
	}
	
	protected void setupUI() {
		comboBox = new JComboBox(comboBoxModel);
		idTextField = new JTextField(10);
		findButton = new JButton("find");
		
		comboBox.setRenderer(new SellerListCellRenderer());
		
		findButton.addActionListener(this);
		SellerCache.getInstance().addSellerCacheEventListener(this);
		
		setLayout(new FlowLayout());
		add(comboBox);
		add(idTextField);
		add(findButton);
		
		setVisible(true);
	}
	
	public List<Seller> getAllSellers() {
		List<Seller> sellers = getSellerWebService().findAll();
		return sellers;
	}
	
	public List<Seller> getSellersByName() {
		List<Seller> sellers = null;
		if (idTextField.getText().equals("")) {
			sellers = getAllSellers();
		} else {
			sellers = getSellerWebService().findByName(idTextField.getSelectedText());
		}
		return sellers;
	}
	
	public SellerService getSellerWebService() {
		return sellerService;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		log.info("actionPerformed e="+e);
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				log.info("actionPerformed.invokeLater: display searchDialog");
				// handle action-event from findButton to popup search dialog to
				// search against seller-cache to synch with seller-service
				SellerSearchDialog searchDialog = new SellerSearchDialog(getSellersByName());
				searchDialog.setVisible(true);
			}
		});
	}

	@Override
	public void cacheChanged() {
		log.info("cacheChanged: enter");
		comboBoxModel = new DefaultComboBoxModel(SellerCache.getInstance().getSellers().toArray());
		comboBox.setModel(comboBoxModel);
	}
	
}

class SellerListCellRenderer extends BasicComboBoxRenderer
{
	public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
        
        String textValue = "";
        if (value != null) {
        	Seller seller = (Seller) value;
        	textValue = seller.getId() + " : " + seller.getName();
        }
        return super.getListCellRendererComponent(list, textValue, index, isSelected, cellHasFocus);
	}
} 

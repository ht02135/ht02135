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
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import org.apache.log4j.Logger;

import com.hung.adaptor.DomainServiceAdaptor;
import com.hung.adaptor.DomainSettingServiceAdaptor;
import com.hung.auction.domain.Domain;
import com.hung.cache.ICache;
import com.hung.cache.ICacheObserver;
import com.hung.cache.SellerCache;
import com.hung.dialog.AbstractSearchDialog;
import com.hung.factory.FactoryMethods;
import com.hung.factory.GUIFactoryMethods;
import com.hung.setting.SettingNames;

public class DomainPicker extends AbstractPicker implements ActionListener, ICacheObserver {

	private static Logger log = Logger.getLogger(DomainPicker.class);
	
	protected JComboBox comboBox = null;
	protected JTextField textField = null;
	protected JButton findButton = null;
	
	protected ComboBoxModel comboBoxModel = null;
	protected ICache domainCache = null;
	protected DomainServiceAdaptor domainService = null;
	protected DomainSettingServiceAdaptor domainSettingService = null;
	
	public DomainPicker() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
		comboBoxModel = new DefaultComboBoxModel(SellerCache.getInstance().getSellers().toArray());
		domainCache = FactoryMethods.getDomainFactory().getCache();
    	domainService = (DomainServiceAdaptor) FactoryMethods.getDomainFactory().getServiceAdaptor();
    	domainSettingService = FactoryMethods.getDomainSettingFactory().getServiceAdaptor();
    	domainCache.addCacheObserver(this);
	}
	
	protected void setupUI() {
		comboBox = new JComboBox(comboBoxModel);
		comboBox.setRenderer(new DomainListCellRenderer());
		
		int textFieldWidth = domainSettingService.getIntegerDomainSetting(SettingNames.PICKER_EDITOR_WIDTH_SETTING).getIntegerValue().intValue();
		log.info("setupUI: textFieldWidth="+textFieldWidth);
		textField = new JTextField(textFieldWidth);
		
		String findButtonLabel = domainSettingService.getStringDomainSetting(SettingNames.PICKER_FIND_LABEL_SETTING).getStringValue();
		log.info("setupUI: findButtonLabel="+findButtonLabel);
		findButton = new JButton(findButtonLabel);
		findButton.addActionListener(this);
		
		setLayout(new FlowLayout());
		add(comboBox);
		add(textField);
		add(findButton);
		
		setVisible(true);
	}
	
	protected List<Object> findAll() {
		List<Object> objects =  domainService.findAll();
		log.info("findAll: objects="+objects);
		return objects;
	}

	@Override
	public void cacheChanged() {
		comboBoxModel = new DefaultComboBoxModel(domainCache.getAll().toArray());
		comboBox.setModel(comboBoxModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AbstractSearchDialog searchDialog = GUIFactoryMethods.getDomainGUIFactory().createSearchDialog(findAll());
				searchDialog.setVisible(true);
			}
		});
	}
} 

class DomainListCellRenderer extends BasicComboBoxRenderer
{
	public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
        
        String textValue = "";
        if (value != null) {
        	Domain domain = (Domain) value;
        	textValue = domain.getName();
        }
        return super.getListCellRendererComponent(list, textValue, index, isSelected, cellHasFocus);
	}
} 

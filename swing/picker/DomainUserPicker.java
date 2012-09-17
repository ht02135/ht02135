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

import com.hung.adaptor.DomainSettingServiceAdaptor;
import com.hung.adaptor.DomainUserServiceAdaptor;
import com.hung.auction.domain.DomainUser;
import com.hung.cache.ICache;
import com.hung.cache.ICacheObserver;
import com.hung.cache.SellerCache;
import com.hung.dialog.AbstractSearchDialog;
import com.hung.factory.FactoryMethods;
import com.hung.factory.GUIFactoryMethods;
import com.hung.setting.SettingNames;

public class DomainUserPicker extends AbstractPicker implements ActionListener, ICacheObserver {

	private static Logger log = Logger.getLogger(DomainUserPicker.class);
	
	protected JComboBox comboBox = null;
	protected JTextField textField = null;
	protected JButton findButton = null;
	
	protected ComboBoxModel comboBoxModel = null;
	protected ICache domainUserCache = null;
	protected DomainUserServiceAdaptor domainUserService = null;
	protected DomainSettingServiceAdaptor domainSettingService = null;
	
	public DomainUserPicker() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
		comboBoxModel = new DefaultComboBoxModel(SellerCache.getInstance().getSellers().toArray());
		domainUserCache = FactoryMethods.getDomainUserFactory().getCache();
		domainUserService = (DomainUserServiceAdaptor) FactoryMethods.getDomainUserFactory().getServiceAdaptor();
		domainSettingService = FactoryMethods.getDomainSettingFactory().getServiceAdaptor();
		domainUserCache.addCacheObserver(this);
	}
	
	protected void setupUI() {
		comboBox = new JComboBox(comboBoxModel);
		comboBox.setRenderer(new DomainUserListCellRenderer());
		
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
		List<Object> objects =  domainUserService.findAll();
		log.info("findAll: objects="+objects);
		return objects;
	}

	@Override
	public void cacheChanged() {
		comboBoxModel = new DefaultComboBoxModel(domainUserCache.getAll().toArray());
		comboBox.setModel(comboBoxModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				AbstractSearchDialog searchDialog = GUIFactoryMethods.getDomainUserGUIFactory().createSearchDialog(findAll());
				searchDialog.setVisible(true);
			}
		});
	}
} 

class DomainUserListCellRenderer extends BasicComboBoxRenderer
{
	public Component getListCellRendererComponent(JList list, Object value,
        int index, boolean isSelected, boolean cellHasFocus) {
        
        String textValue = "";
        if (value != null) {
        	DomainUser domainUser = (DomainUser) value;
        	textValue = domainUser.getLoginId() + " : " + domainUser.getName();
        }
        return super.getListCellRendererComponent(list, textValue, index, isSelected, cellHasFocus);
	}
} 

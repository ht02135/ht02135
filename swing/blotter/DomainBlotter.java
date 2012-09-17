package com.hung.blotter;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.apache.log4j.Logger;

import com.hung.auction.client.ClientReservation;
import com.hung.auction.client.ClientSession;
import com.hung.auction.domain.Domain;
import com.hung.command.ICommand;
import com.hung.factory.FactoryMethods;

public class DomainBlotter extends AbstractBlotter implements ActionListener {

	private static Logger log = Logger.getLogger(DomainBlotter.class);
	
	protected DomainTable domainTable = null;
	
	protected Map<AbstractButton, ICommand> commands = null;
	protected JButton reserveButton = null;
	protected JButton unReserveButton = null;
	
	protected DomainTableModel domainTableModel = null;
	protected TableColumnModel domainTableColumnModel = null;
	
	public DomainBlotter() {
		initializeModel();
		setupUI();
	}
	
	protected void initializeModel() {
		domainTableModel = new DomainTableModel();
		domainTableColumnModel = new DefaultTableColumnModel();
		
		// adding reservation col
		TableColumn reservationCol = new TableColumn(DomainTableModel.RESERVATION_COL_IDX);
		reservationCol.setHeaderValue(DomainTableModel.RESERVATION_HEADER);
		domainTableColumnModel.addColumn(reservationCol);

		// adding reservation col
		TableColumn domainCol = new TableColumn(DomainTableModel.DOMAIN_COL_IDX);
		domainCol.setCellRenderer(new DomainTableCellRenderer());
		reservationCol.setHeaderValue(DomainTableModel.DOMAIN_HEADER);
		domainTableColumnModel.addColumn(domainCol);
	}
	
	protected void setupUI() {
		domainTable = new DomainTable(domainTableModel, domainTableColumnModel);
		
		// Design Pattern: DomainBlotter is invoker that store concrete-commands
		// Design Pattern: Commands separate the sender from the receiver of a request
		commands = new HashMap<AbstractButton, ICommand>();
		
		reserveButton = new JButton("reserve");
		commands.put(reserveButton, new ICommand() {	// Design Pattern: DomainBlotter is invoker that create concrete-commands and specifies its receiver	
			public void execute() {
				// Design Pattern: receiver is encapsulated by reserve() method
				reserve();
			}
		});
		reserveButton.addActionListener(this);
		
		unReserveButton = new JButton("unReserve");
		commands.put(unReserveButton, new ICommand() {	// Design Pattern: DomainBlotter is invoker that create concrete-commands and specifies its receiver	
			public void execute() {	
				// Design Pattern: receiver is encapsulated by runeserve() method
				unReserve();
			}
		});
		unReserveButton.addActionListener(this);
		
		setLayout(new FlowLayout());
		add(domainTable);
		add(reserveButton);
		add(unReserveButton);
		
		setVisible(true);
	}
	
	protected void reserve() {
		List<ClientReservation> clientReservations = new ArrayList<ClientReservation>();

		int[] selectedRows = domainTable.getSelectedRows();
		for (int i=0; i<selectedRows.length; i++) {
			ClientSession clientSession = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession();
			Domain domain = (Domain) domainTableModel.getValueAt(selectedRows[i], DomainTableModel.DOMAIN_COL_IDX);
			clientReservations.add(new ClientReservation(clientSession.getLoginId(), ClientReservation.DOMAIN_ENTITY, domain.getName()));
		}
		
		log.info("*** reserve: selectedRows="+selectedRows+", clientReservations="+clientReservations+" ***");
		FactoryMethods.getClientReservationFactory().getCache().reserveUnReserve(clientReservations, Collections.EMPTY_LIST);
	}
	
	protected void unReserve() {
		List<ClientReservation> clientUnReservations = new ArrayList<ClientReservation>();
		
		int[] selectedRows = domainTable.getSelectedRows();
		for (int i=0; i<selectedRows.length; i++) {
			ClientSession clientSession = FactoryMethods.getLoginFactory().getServiceAdaptor().getClientSession();
			Domain domain = (Domain) domainTableModel.getValueAt(selectedRows[i], DomainTableModel.DOMAIN_COL_IDX);
			clientUnReservations.add(new ClientReservation(clientSession.getLoginId(), ClientReservation.DOMAIN_ENTITY, domain.getName()));
		}
		
		log.info("*** unReserve: selectedRows="+selectedRows+", clientUnReservations="+clientUnReservations+" ***");
		FactoryMethods.getClientReservationFactory().getCache().reserveUnReserve(Collections.EMPTY_LIST, clientUnReservations);
	}

	public void actionPerformed(ActionEvent e) {
		// Design Pattern: DomainBlotter is invoker that issues request by calling execute on command
		ICommand command = (ICommand) commands.get(e.getSource());           
		if (null != command) command.execute();
	}
} 

class DomainTableCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value,
        boolean isSelected, boolean hasFocus, int row, int column) {

        String textValue = "";
        if (value != null) {
        	Domain domain = (Domain) value;
        	textValue = domain.getName();
        }
        
        return super.getTableCellRendererComponent(table, textValue, isSelected, hasFocus, row, column);
    }
}


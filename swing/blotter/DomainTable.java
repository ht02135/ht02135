package com.hung.blotter;

import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import org.apache.log4j.Logger;

public class DomainTable extends JTable {

	private static Logger log = Logger.getLogger(DomainTable.class);
	
	public DomainTable(TableModel tableModel, TableColumnModel tableColumnModel) {
		super(tableModel, tableColumnModel);
	}
} 

package com.hung.factory;

import java.util.List;

import com.hung.blotter.AbstractBlotter;
import com.hung.dialog.AbstractSearchDialog;
import com.hung.frame.AbstractFrame;
import com.hung.picker.AbstractPicker;


public interface IGUIFactory {
	public AbstractPicker createPicker();
	public AbstractSearchDialog createSearchDialog(List<Object> objects);
	public AbstractBlotter createBlotter();
	public AbstractFrame createFrame();
}
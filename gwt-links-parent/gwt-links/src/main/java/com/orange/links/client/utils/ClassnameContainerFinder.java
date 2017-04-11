package com.orange.links.client.utils;

import  com.google.gwt.dom.client.Element;

public class ClassnameContainerFinder implements IContainerFinder {

	private static final String PARENT_CLASS_NAME = "dragdrop-boundary";
	
	public boolean isContainer(Element element) {
		String classNames = element.getAttribute("class");
			//	DOM.getElementAttribute(element, "class");
    	return classNames.contains(PARENT_CLASS_NAME);
	}
	
}

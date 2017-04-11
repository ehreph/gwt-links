package com.orange.links.client.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.orange.links.client.utils.LinksClientBundle;


public class ConnectionCanvas extends MultiBrowserDiagramCanvas{

	public ConnectionCanvas(int width, int height, Canvas canvas) {
		super(width, height, canvas);
		this.getElement().getStyle().setZIndex(1);
		this.asWidget().addStyleName(LinksClientBundle.INSTANCE.css().connectionCanvas());
	}

}

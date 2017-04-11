package com.orange.links.client.canvas;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.user.client.Element;
import com.google.gwt.dom.client.Style.Position;
import com.google.gwt.dom.client.Style.Unit;
import com.google.gwt.event.dom.client.DomEvent.Type;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Widget;

public class MultiBrowserDiagramCanvas implements DiagramCanvas{

	protected int width;
	protected int height;
	private Canvas canvas;
	
	public MultiBrowserDiagramCanvas(int width, int height, Canvas context){
		
		this.width = width;
		this.height = height;
		this.canvas = context;
		
		setBackground();
		getElement().getStyle().setPosition(Position.ABSOLUTE);
		getElement().getStyle().setWidth(width, Unit.PX);
		getElement().getStyle().setHeight(height, Unit.PX);
		
		canvas.setCoordinateSpaceHeight(height);
		canvas.setCoordinateSpaceWidth(width);
	}
	
	@Override
	public void setForeground() {
		this.getElement().getStyle().setZIndex(5);
	}

	@Override
	public void setBackground() {
		this.getElement().getStyle().setZIndex(2);
	}


	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void setStrokeStyle(CssColor color) {
		setStrokeStyle(color.toString());
	}

	@Override
	public void setFillStyle(CssColor color) {
		setFillStyle(color.toString());
	}
	
	@Override
	public Widget asWidget(){
		return canvas;
	}

	@Override
	public void setStrokeStyle(String color) {
		canvas.getContext2d().setStrokeStyle(color);
	}

	@Override
	public void setFillStyle(String color) {
		canvas.getContext2d().setFillStyle(color);
	}


	@Override
	public void clear() {
		canvas.getContext2d().clearRect(0,0, canvas.getOffsetWidth(), canvas.getOffsetWidth());	
	}

	@Override
	public <H extends EventHandler> HandlerRegistration addDomHandler(
			H handler, Type<H> type) {
		return canvas.addDomHandler(handler, type);
	}

	@Override
	public Element getElement() {
		return canvas.getElement();
	}

	@Override
	public void beginPath() {
		canvas.getContext2d().beginPath();
	}

	@Override
	public void closePath() {
		canvas.getContext2d().closePath();
	}

	@Override
	public void lineTo(double x, double y) {
		canvas.getContext2d().lineTo(x, y);
	}

	@Override
	public void moveTo(double x, double y) {
		canvas.getContext2d().moveTo(x, y);
	}

	@Override
	public void fillRect(double x, double y, double w, double h) {
		canvas.getContext2d().fillRect(x, y, w, h);
	}

	@Override
	public void stroke() {
		canvas.getContext2d().stroke();
	}

	@Override
	public void arc(double x, double y, double radius, double startAngle,
			double endAngle, boolean anticlockwise) {
		canvas.getContext2d().arc(x, y, radius, startAngle, endAngle, anticlockwise);
	}

	@Override
	public void fill() {
		canvas.getContext2d().fill();
	}

	@Override
	public void bezierCurveTo(double cpx, double cpy, double x, double y) {
		canvas.getContext2d().bezierCurveTo(cpx, cpy, cpx, cpy, x, y);
	}

	@Override
    public void setGlobalAlpha(double alpha) {
		canvas.getContext2d().setGlobalAlpha(alpha);
    }

}

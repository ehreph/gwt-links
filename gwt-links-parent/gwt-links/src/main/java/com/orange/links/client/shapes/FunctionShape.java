package com.orange.links.client.shapes;


import com.google.gwt.canvas.dom.client.CssColor;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.user.client.ui.Widget;
import com.orange.links.client.DiagramController;
import com.orange.links.client.canvas.DiagramCanvas;
import com.orange.links.client.utils.Couple;
import com.orange.links.client.utils.Direction;
import com.orange.links.client.utils.LinksClientBundle;

public class FunctionShape extends AbstractShape {

	private int selectableAreaRadius = 13;
	private int selectableTriangleSize =16;
	private CssColor highlightSelectableAreaColor = CssColor.make("#DDDDDD");

	Point centerW;
	Point centerN;
	Point centerS;
	Point centerE;

	public FunctionShape(final DiagramController controller, Widget widget) {
		super(controller, widget);
		
		widget.addHandler(new MouseDownHandler() {
			
			@Override
			public void onMouseDown(MouseDownEvent event) {
				controller.getDiagramCanvas().clear();
				
			}
		}, MouseDownEvent.getType());
		
		
	}

	@Override
	public void setSynchronized(boolean sync) {
		if (allowSync && !sync) {
			centerW = null;
			centerN = null;
			centerS = null;
			centerE = null;
		}
		super.setSynchronized(sync);
	}

	public boolean isMouseNearSelectableArea(Point mousePoint) {
		return getSelectableArea(mousePoint) != null;
	}

	public boolean isMouseOverShape(Point p) {
		if (centerW == null || !isSynchronized()) {
			centerW = new Point(getLeft(), getTop() + getHeight() / 2);
			centerN = new Point(getLeft() + getWidth() / 2, getTop());
			centerS = new Point(getLeft() + getWidth() / 2, getTop() + getHeight() - 1);
			centerE = new Point(getLeft() + getWidth() - 1, getTop() + getHeight() / 2);
			setSynchronized(true);
		}
		return isOverShape(p);
	}

	public boolean isOverShape(Point p) {
		Widget wid = this.widget;
		int range = 10;
		int x, y;
		int w, h;
		x = wid.getAbsoluteLeft() - range;
		y = wid.getAbsoluteTop() - range;
		w = wid.getOffsetWidth() + 2 * range;
		h = wid.getOffsetHeight() + 2 * range;
		int px = p.getLeft();
		int py = p.getTop();

		if ((x | y | w | h) < 0) {
			return false;
		}
		if (px < x | px > (x + w)) {
			return false;
		}
		if (py < y | py > (y + h)) {
			return false;
		}

		return true;

	
	}

	public void highlightSelectableArea(Point mousePoint) {
		// Mouse Point
		Couple<Direction, Point> couple = getSelectableArea(mousePoint);
		Direction direction = couple.getFirstArg();
		Point closestSelectablePoint = couple.getSecondArg();
		if (closestSelectablePoint != null) {
			DiagramCanvas canvas = controller.getDiagramCanvas();
			canvas.beginPath();
			canvas.arc(closestSelectablePoint.getLeft(), closestSelectablePoint.getTop(), selectableAreaRadius,
					direction.getAngle() - Math.PI / 2, direction.getAngle() + Math.PI / 2, true);
			canvas.setStrokeStyle(highlightSelectableAreaColor);
			canvas.stroke();
			canvas.setFillStyle(highlightSelectableAreaColor);
			canvas.fill();
			canvas.closePath();
		}
	}

	public void highlightSelectableAreaAsArrow(Point mousePoint) {
		Couple<Direction, Point> couple = getSelectableArea(mousePoint);
		Direction direction = couple.getFirstArg();
		int triSize = selectableTriangleSize;
		
		Point closestSelectablePoint = couple.getSecondArg();
		if (closestSelectablePoint != null) {
			DiagramCanvas canvas = controller.getDiagramCanvas();
			canvas.beginPath();
			int px = closestSelectablePoint.getLeft();
			int py = closestSelectablePoint.getTop();

			canvas.moveTo(mousePoint.getLeft(), mousePoint.getTop());
			if (direction.equals(Direction.N)) {
				canvas.lineTo(px - triSize, py);
				canvas.lineTo(px, py - triSize);
				canvas.lineTo(px + triSize, py);
				canvas.lineTo(px, py);
			} else if (direction.equals(Direction.S)) {
				canvas.lineTo(px - triSize, py);
				canvas.lineTo(px, py + triSize);
				canvas.lineTo(px + triSize, py);
				canvas.lineTo(px, py);
			} else if (direction.equals(Direction.W)) {
				canvas.lineTo(px, py + triSize);
				canvas.lineTo(px - triSize, py);
				canvas.lineTo(px, py - triSize);
				canvas.lineTo(px, py);
			} else if (direction.equals(Direction.E)) {
				canvas.lineTo(px, py + triSize);
				canvas.lineTo(px + triSize, py);
				canvas.lineTo(px, py - triSize);
				canvas.lineTo(px, py);
			}
			canvas.setStrokeStyle(highlightSelectableAreaColor);
			canvas.stroke();
			canvas.setFillStyle(highlightSelectableAreaColor);
			canvas.fill();
			canvas.closePath();
		}
	}

	public void highlightArea() {

		if (centerW == null || !isSynchronized()) {
			centerW = new Point(getLeft(), getTop() + getHeight() / 2);
			centerN = new Point(getLeft() + getWidth() / 2, getTop());
			centerS = new Point(getLeft() + getWidth() / 2, getTop() + getHeight() - 1);
			centerE = new Point(getLeft() + getWidth() - 1, getTop() + getHeight() / 2);
			setSynchronized(true);
		}
	
		highlightSelectableAreaAsArrow(centerW);
		highlightSelectableAreaAsArrow(centerN);
		highlightSelectableAreaAsArrow(centerS);
		highlightSelectableAreaAsArrow(centerE);
	}

	public Couple<Direction, Point> getSelectableArea(Point p) {
		// Center of the selectable areas
		if (centerW == null || !isSynchronized()) {
			centerW = new Point(getLeft(), getTop() + getHeight() / 2);
			centerN = new Point(getLeft() + getWidth() / 2, getTop());
			centerS = new Point(getLeft() + getWidth() / 2, getTop() + getHeight() - 1);
			centerE = new Point(getLeft() + getWidth() - 1, getTop() + getHeight() / 2);
			setSynchronized(true);
		}

		if (p.distance(centerW) <= selectableAreaRadius) {
			return new Couple<Direction, Point>(Direction.W, centerW);
		} else if (p.distance(centerN) <= selectableAreaRadius) {
			return new Couple<Direction, Point>(Direction.N, centerN);
		} else if (p.distance(centerS) <= selectableAreaRadius) {
			return new Couple<Direction, Point>(Direction.S, centerS);
		} else if (p.distance(centerE) <= selectableAreaRadius) {
			return new Couple<Direction, Point>(Direction.E, centerE);
		}

		return null;
	}

	public Couple<Direction, Point> getCoupleDirection(Point p) {
		Couple<Direction, Point> c = new Couple<Direction, Point>(Direction.W, centerW);
		double distance = p.distance(centerW);

		if (distance > p.distance(centerN)) {
			distance = p.distance(centerN);
			c = new Couple<Direction, Point>(Direction.N, centerN);

		} else if (distance > p.distance(centerS)) {
			distance = p.distance(centerS);
			c = new Couple<Direction, Point>(Direction.S, centerS);

		} else if (distance > p.distance(centerE)) {
			distance = p.distance(centerE);
			c = new Couple<Direction, Point>(Direction.E, centerE);
		}
		return c;
	}

	@Override
	public void drawHighlight() {
		widget.addStyleName(LinksClientBundle.INSTANCE.css().translucide());
		setSynchronized(true);
	}

	@Override
	public void draw() {
		widget.removeStyleName(LinksClientBundle.INSTANCE.css().translucide());
		setSynchronized(true);
	}

}

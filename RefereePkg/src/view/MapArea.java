package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Map;
import model.TaskTriplet;
import model.TripletEvent;
import controller.TripletListener;

public class MapArea extends JScrollPane implements TripletListener {

	private static final long serialVersionUID = 1L;

	private BufferedImage backgroundMap = Map.loadBackgroundMap();;
	private MapPane mapPane = new MapPane();
	private HashMap<String, Point> validPositions;
	private List<TaskTriplet> taskTripletList;

	public MapArea() {
		super();
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		this.getViewport().add(mapPane, BorderLayout.CENTER);
		this.setMaximumSize(new Dimension(backgroundMap.getWidth(),
				backgroundMap.getHeight()));
		mapPane.setMaximumSize(new Dimension(backgroundMap.getWidth(),
				backgroundMap.getHeight()));
		mapPane.setPreferredSize(new Dimension(backgroundMap.getWidth(),
				backgroundMap.getHeight()));
		taskTripletList = new ArrayList<TaskTriplet>();
	}

	/** The component inside the scroll pane. */
	public class MapPane extends JPanel {
		private static final long serialVersionUID = 1L;
		private static final int R_ARROW = 30;
		private static final double R140 = Math.PI / 180 * 140;
		private static final double R180 = Math.PI;
		private static final double R220 = Math.PI / 180 * 220;

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(backgroundMap, 0, 0, backgroundMap.getWidth(),
					backgroundMap.getHeight(), this);
			int i =  0;
			Point pPrev = new Point(0,0);
			for (TaskTriplet tT : taskTripletList) {
				Point p = validPositions.get(tT.getPlace());
				if (i==0) {
				pPrev = p;
				} else {
					g2.setStroke(new BasicStroke(6.0f));
					g2.setColor(Color.black);
				g2.drawLine(pPrev.x,pPrev.y, p.x, p.y);
				g2.setStroke(new BasicStroke(4.0f));
				g2.setColor(Color.gray);
				g2.drawLine(pPrev.x,pPrev.y, p.x, p.y);
				
				}
				i++;
				pPrev = p;
			}
			g2.setStroke(new BasicStroke(2.0f));
			for (TaskTriplet tT : taskTripletList) {
				int theta = 0;
				switch (tT.getOrientation()) {
				case "N":
					theta = 90;
					break;
				case "S":
					theta = -90;
					break;
				case "W":
					theta = 180;
					break;
				case "E":
					theta = 0;
					break;
				case "NE":
					theta = 45;
					break;
				case "SE":
					theta = -45;
					break;
				case "SW":
					theta = -135;
					break;
				case "NW":
					theta = 135;
					break;
				}
				g.setColor(Color.cyan);
				Point p = validPositions.get(tT.getPlace());
				drawArrow(g, p, theta * Math.PI / 180.0);
				g2.setColor(Color.black);
				g2.drawString(tT.getPlace(), p.x - 5, p.y + 4);
			}
		}

		private void drawArrow(Graphics g2, Point point, double theta) {
			theta *= -1;
			Polygon poly = new Polygon();
			Point2D p = getPolyPoint(theta);
			poly.addPoint((int) (point.getX() + p.getX() * R_ARROW),
					(int) (point.getY() + p.getY() * R_ARROW));
			p = getPolyPoint(theta < 0 ? theta - R140 : theta + R140);
			poly.addPoint((int) (point.getX() + p.getX() * R_ARROW),
					(int) (point.getY() + p.getY() * R_ARROW));
			p = getPolyPoint(theta < 0 ? theta - R180 : theta + R180);
			poly.addPoint((int) (point.getX() + p.getX() * R_ARROW / 2),
					(int) (point.getY() + p.getY() * R_ARROW / 2));
			p = getPolyPoint(theta < 0 ? theta - R220 : theta + R220);
			poly.addPoint((int) (point.getX() + p.getX() * R_ARROW),
					(int) (point.getY() + p.getY() * R_ARROW));
			g2.fillPolygon(poly);
			g2.setColor(Color.black);
			g2.drawPolygon(poly);
		}

		private Point2D getPolyPoint(double d) {
			Point2D p = new Point2D.Double(Math.cos(d), Math.sin(d));
			return p;
		}
	}

	@Override
	public void tripletAdded(TripletEvent evt) {
		this.taskTripletList = evt.getTaskTripletList();
		mapPane.repaint();
	}

	@Override
	public void tripletDeleted(TripletEvent evt) {
		this.taskTripletList = evt.getTaskTripletList();
		mapPane.repaint();
	}

	public void setValidPositions(HashMap<String, Point> positions) {
		this.validPositions = positions;
	}
	
	public MapPane getMapPane() {
		return mapPane;
	}
}

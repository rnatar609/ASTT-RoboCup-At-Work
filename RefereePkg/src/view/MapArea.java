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
import java.util.List;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.Map;
import model.TripletEvent;
import controller.TripletListener;

public class MapArea extends JScrollPane {

	private static final long serialVersionUID = 1L;

	BufferedImage backgroundMap = Map.loadBackgroundMap();;
	private MapPane mapPane = new MapPane();

	private List<String> places;
	private List<Point> points;

	// private JScrollPane areaScrollPane = new JScrollPane(mapPane);

	public MapPane getMapPane() {
		return mapPane;
	}

	public MapArea() {
		super();
		// this.add(mapPane);
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		mapPane.setPreferredSize(new Dimension(backgroundMap.getWidth(),
				backgroundMap.getHeight()));
		mapPane.setLayout(null);

		this.getViewport().add(mapPane, BorderLayout.CENTER);
		//this.setPreferredSize(new Dimension(100, 100));
		this.setMaximumSize(new Dimension(backgroundMap.getWidth(),
				backgroundMap.getHeight()));
		mapPane.setMaximumSize(new Dimension(backgroundMap.getWidth(),
				backgroundMap.getHeight()));
	}

	/** The component inside the scroll pane. */
	public class MapPane extends JPanel implements TripletListener {
		private static final long serialVersionUID = 1L;
		private Vector<String> names = new Vector<String>();
		// this is not a good idea, better to get real the whole list by the
		// listener
		private Vector<Integer[]> poses = new Vector<Integer[]>();
		private static final int R_ARROW = 30;
		private static final double R140 = Math.PI / 180 * 140;
		private static final double R180 = Math.PI;
		private static final double R220 = Math.PI / 180 * 220;

		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g;
			g2.setStroke(new BasicStroke(2.0f));
			g2.drawImage(backgroundMap, 0, 0, backgroundMap.getWidth(),
					backgroundMap.getHeight(), this);
			for (String pos : names) {
				g.setColor(Color.cyan);
				Point p = new Point(poses.get(names.indexOf(pos))[0],
						poses.get(names.indexOf(pos))[1]);
				drawArrow(g, p, (double) poses.get(names.indexOf(pos))[2]
						* Math.PI / 180.0);
				g2.setColor(Color.black);
				g2.drawString(pos, p.x - 5, p.y + 4);
			}
			// g2.setStroke(new BasicStroke(8.0f));
			// g2.drawArc(100, 100, 50, 50, 45, 45);

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

		@Override
		public void tripletAdded(TripletEvent evt) {
			// TripletEvent: this is not a good idea, better to get the real
			// whole list by the listener
			String s = evt.getTaskTriplet().getPlace();
			Integer[] pos = new Integer[3];
			pos[0] = points.get(places.indexOf(s)).x;
			pos[1] = points.get(places.indexOf(s)).y;
			switch (evt.getTaskTriplet().getOrientation()) {
			case "N":
				pos[2] = 90;
				break;
			case "S":
				pos[2] = -90;
				break;
			case "W":
				pos[2] = 180;
				break;
			case "E":
				pos[2] = 0;
				break;
			case "NE":
				pos[2] = 45;
				break;
			case "SE":
				pos[2] = -45;
				break;
			case "SW":
				pos[2] = -135;
				break;
			case "NW":
				pos[2] = 135;
				break;
			}
			names.add(s);
			poses.add(pos);
			repaint();
		}

		@Override
		public void tripletDeleted(TripletEvent evt) {
			// TODO Auto-generated method stub

		}
	}

	public void setValidPlaces(List<String> places) {
		this.places = places;
	}

	public void setValidPoints(List<Point> validPoints) {
		this.points = validPoints;
	}
}

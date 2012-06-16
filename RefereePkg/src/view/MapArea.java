package view;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import model.BmtTask;
import model.BntTask;
import model.BttTask;
import model.CompetitionIdentifier;
import model.CttTask;
import model.StateOfTask;
//import model.TaskTriplet;
import controller.TaskListener;

public class MapArea extends JScrollPane implements TaskListener {

	private static final long serialVersionUID = 1L;

	private BufferedImage backgroundMap;
	private MapPane mapPane;
	private HashMap<String, Point> validPositions;
	private List<BntTask> taskList;
	private CompetitionIdentifier compIdent;
	private BmtTask bmtTask;
	private ArrayList<BttTask> bttList;

	public MapArea() {
		super();
		compIdent = CompetitionIdentifier.BNT;
		mapPane = new MapPane();
		taskList = new ArrayList<BntTask>();
		this.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		this.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		// this.setBorder(new javax.swing.border.EmptyBorder(0,0,0,0));
	}

	/** The component inside the scroll pane. */
	public class MapPane extends JPanel {
		private static final long serialVersionUID = 1L;
		private static final int R_ARROW = 30;
		private static final int R_CIRCLE = 16;
		private static final double R140 = Math.PI / 180 * 140;
		private static final double R180 = Math.PI;
		private static final double R220 = Math.PI / 180 * 220;
		private final double phi = Math.toRadians(40);
		private double dis = 20;

		protected void paintComponent(Graphics g) {
			Dimension d = getSize();
			Color c = getBackground();
			g.setColor(c);
			g.fillRect(0, 0, d.width, d.height);
			Graphics2D g2 = (Graphics2D) g;
			g2.drawImage(backgroundMap, 0, 0, backgroundMap.getWidth(),
					backgroundMap.getHeight(), this);
			if (compIdent == CompetitionIdentifier.BNT) {
				g2.drawImage(backgroundMap, 0, 0, backgroundMap.getWidth(),
						backgroundMap.getHeight(), this);
				int i = 0;
				Point pPrev = new Point(0, 0);
				for (BntTask tT : taskList) {
					Point p = validPositions.get(tT.getPlace());
					if (i == 0) {
						pPrev = p;
					} else {
						g2.setStroke(new BasicStroke(4.0f));
						g2.setColor(Color.black);
						g2.drawLine(pPrev.x, pPrev.y, p.x, p.y);
						drawArrowHead(g2, p, pPrev);
					}
					i++;
					pPrev = p;
				}
				g2.setStroke(new BasicStroke(2.0f));
				for (BntTask tT : taskList) {
					int theta = 0;
					if (((BntTask) tT).getOrientation().equals("N")) {
						theta = 90;
					} else if (tT.getOrientation().equals("S")) {
						theta = -90;
					} else if (tT.getOrientation().equals("W")) {
						theta = 180;
					} else if (tT.getOrientation().equals("E")) {
						theta = 0;
					} else if (tT.getOrientation().equals("NE")) {
						theta = 45;
					} else if (tT.getOrientation().equals("SE")) {
						theta = -45;
					} else if (tT.getOrientation().equals("SW")) {
						theta = -135;
					} else if (tT.getOrientation().equals("NW")) {
						theta = 135;
					}
					if (tT.getState() == StateOfTask.INIT)
						g.setColor(Color.cyan);
					if (tT.getState() == StateOfTask.PASSED)
						g.setColor(Color.green);
					if (tT.getState() == StateOfTask.FAILED)
						g.setColor(Color.red);
					Point p = validPositions.get(tT.getPlace());
					drawArrow(g, p, theta * Math.PI / 180.0);
					g2.setColor(Color.black);
					g2.drawString(tT.getPlace(), p.x - 7, p.y + 4);
				}
			}
			if (compIdent == CompetitionIdentifier.BMT) {
				g2.setStroke(new BasicStroke(2.0f));
				if (validPositions.containsKey(BmtTask.getPlaceInitial())) {
					g.setColor(Color.cyan);
					Point p = validPositions.get(BmtTask.getPlaceInitial());
					g2.fillOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.setColor(Color.black);
					g2.drawOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.drawString(BmtTask.getPlaceInitial(), p.x - 7, p.y + 3);
					g2.drawString("initial", p.x + R_CIRCLE + 2, p.y + 4);
				}
				if (validPositions.containsKey(BmtTask.getPlaceSource())) {
					g.setColor(Color.cyan);
					Point p = validPositions.get(BmtTask.getPlaceSource());
					g2.fillOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.setColor(Color.black);
					g2.drawOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.drawString(BmtTask.getPlaceSource(), p.x - 7, p.y + 3);
					g2.drawString("source", p.x + R_CIRCLE + 2, p.y + 4);
				}
				if (validPositions.containsKey(BmtTask.getPlaceDestination())) {
					g.setColor(Color.cyan);
					Point p = validPositions.get(BmtTask.getPlaceDestination());
					g2.fillOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.setColor(Color.black);
					g2.drawOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.drawString(BmtTask.getPlaceDestination(), p.x - 7,
							p.y + 3);
					g2.drawString("destination", p.x + R_CIRCLE + 2, p.y + 4);
				}
				if (validPositions.containsKey(BmtTask.getPlaceFinal())) {
					g.setColor(Color.cyan);
					Point p = validPositions.get(BmtTask.getPlaceFinal());
					g2.fillOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.setColor(Color.black);
					g2.drawOval(p.x - R_CIRCLE, p.y - R_CIRCLE, 2 * R_CIRCLE,
							2 * R_CIRCLE);
					g2.drawString(BmtTask.getPlaceFinal(), p.x - 7, p.y + 3);
					g2.drawString(BmtTask.getConfiguration(), p.x + R_CIRCLE
							+ 2, p.y + 4);
				}
			}
			if (compIdent == CompetitionIdentifier.BTT) {
				g2.setStroke(new BasicStroke(2.0f));
				for (BttTask tT : bttList) {
					if (validPositions.containsKey(tT.getPlace())) {
						if (tT.getSituation().equals("initial"))
							g.setColor(Color.yellow);
						else
							g.setColor(Color.cyan);
						Point p = validPositions.get(tT.getPlace());
						g2.fillOval(p.x - R_CIRCLE, p.y - R_CIRCLE,
								2 * R_CIRCLE, 2 * R_CIRCLE);
						g2.setColor(Color.black);
						g2.drawOval(p.x - R_CIRCLE, p.y - R_CIRCLE,
								2 * R_CIRCLE, 2 * R_CIRCLE);
						String ident;
						g2.drawString(tT.getPlace(), p.x - 5, p.y + 4);
						if (tT.getConfiguration().length() == 0)
							g2.drawString(tT.getSituation(),
									p.x + R_CIRCLE + 2, p.y + 4);
						else
							g2.drawString(tT.getConfiguration(), p.x + R_CIRCLE
									+ 2, p.y + 4);
					}
				}
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

		private void drawArrowHead(Graphics2D g2, Point end, Point start) {
			g2.setPaint(Color.black);
			double dy = end.y - start.y;
			double dx = end.x - start.x;
			double theta = Math.atan2(dy, dx);
			double x, y, rho = theta + phi;
			double partX = end.x - dx / 4;
			double partY = end.y - dy / 4;
			for (int j = 0; j < 2; j++) {
				x = partX - dis * Math.cos(rho);
				y = partY - dis * Math.sin(rho);
				g2.setStroke(new BasicStroke(6.0f));
				g2.setColor(Color.cyan);
				g2.draw(new Line2D.Double(partX, partY, x, y));
				rho = theta - phi;
			}
		}
	}

	public void setValidPositions(HashMap<String, Point> positions) {
		this.validPositions = positions;
	}

	public MapPane getMapPane() {
		return mapPane;
	}

	public void setBackgroundMap(BufferedImage backgroundMap) {
		this.backgroundMap = backgroundMap;
		mapPane.setPreferredSize(new Dimension(backgroundMap.getWidth(),
				backgroundMap.getHeight()));
		this.getViewport().add(mapPane, BorderLayout.CENTER);
	}

	@Override
	public void bntTaskSpecChanged(BntTask bntTask, int pos,
			ArrayList<BntTask> bntTaskList) {
		this.taskList = bntTaskList;
		compIdent = CompetitionIdentifier.BNT;
		mapPane.repaint();
	}

	@Override
	public void bmtTaskSpecChanged(BmtTask bmtTask, int pos,
			ArrayList<BmtTask> bmtTaskList) {
		this.bmtTask = bmtTask;
		compIdent = CompetitionIdentifier.BMT;
		mapPane.repaint();
	}

	@Override
	public void bttTaskSpecChanged(BttTask bttTask, int pos,
			ArrayList<BttTask> bttTaskList) {
		this.bttList = bttTaskList;
		compIdent = CompetitionIdentifier.BTT;
		mapPane.repaint();
	}

	@Override
	public void cttTaskSpecChanged(CttTask cttTask, int pos,
			ArrayList<CttTask> cttTaskList) {
		// TODO Auto-generated method stub

	}
}

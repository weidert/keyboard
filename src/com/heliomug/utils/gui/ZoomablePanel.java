package com.heliomug.utils.gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JPanel;

public class ZoomablePanel extends JPanel  {
  private static final long serialVersionUID = 6395548544912733950L;

  private static final boolean DEFAULT_IS_ZOOMABLE = true;
  private static final boolean DEFAULT_IS_DRAGGABLE = true;
  private static final boolean DEFAULT_IS_FIX_ASPECT_RATIO = false;

  private static final double WHEEL_ZOOM_FACTOR = 1.25;
  
  private double left, right, bottom, top;
  
  private boolean isZoomable, isDraggable, isFixAspectRatio;
  
  public ZoomablePanel() {
    this(-10, 10, -10, 10);
  }
  
  public ZoomablePanel(int pixelWidth, int pixelHeight) {
    this(pixelWidth, pixelHeight, -10, 10, -10, 10);
  }

  public ZoomablePanel(Rectangle2D bounds) {
    this(bounds.getMinX(), bounds.getMaxX(), bounds.getMinY(), bounds.getMaxY());
  }
    
  public ZoomablePanel(int pixelWidth, int pixelHeight, Rectangle2D bounds) {
    this(pixelWidth, pixelHeight, bounds.getMinX(), bounds.getMaxX(), bounds.getMinY(), bounds.getMaxY());
  }
  
  public ZoomablePanel(int pixelWidth, int pixelHeight, double left, double right, double bottom, double top) {
    this(left, right, bottom, top);
    this.setPreferredSize(new Dimension(pixelWidth, pixelHeight));
  }
  
  public ZoomablePanel(double left, double right, double bottom, double top) {
    super();
    this.left = left;
    this.right = right;
    this.top = top;
    this.bottom = bottom;
    
    this.isZoomable = DEFAULT_IS_ZOOMABLE;
    this.isDraggable = DEFAULT_IS_DRAGGABLE;
    this.isFixAspectRatio = DEFAULT_IS_FIX_ASPECT_RATIO;
    
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        if (isFixAspectRatio) fixAspectRatio();
      }
    });
    
    ScreenMouser mouser = new ScreenMouser();
    this.addMouseWheelListener(mouser);
    this.addMouseListener(mouser);
    this.addMouseMotionListener(mouser);
    
    this.setDoubleBuffered(true);
  }
  
  public double getLeft() { return left; }
  public void setLeft(double left) { this.left = left; }
  public double getRight() { return right; }
  public void setRight(double right) { this.right = right; }
  public double getBottom() { return bottom; }
  public void setBottom(double bottom) { this.bottom = bottom; }
  public double getTop() { return top; }
  public void setTop(double top) { this.top = top; }

  public void setZoomable(boolean isZoomable) { this.isZoomable = isZoomable; }
  public void setDraggable(boolean isDraggable) { this.isDraggable = isDraggable; }
  
  @Override
  public void paintComponent(Graphics g) {
    update();
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setTransform(getTransform());
  }
  
  public void translateScreenByPixels(int dx, int dy) {
    AffineTransform t = getTransform();
    translateScreen(dx / t.getScaleX(), dy / t.getScaleY());
  }
  
  public void translateScreen(double dx, double dy) {
    left += dx;
    right += dx;
    top += dy;
    bottom += dy;
  }

  public void setScreenBounds(Rectangle2D bounds) {
    setScreenBounds(bounds.getMinX(), bounds.getMaxX(), bounds.getMinY(), bounds.getMaxY());
  }

  public void setScreenBounds(double left, double right, double bottom, double top) {
    this.left = left;
    this.right = right;
    this.bottom = bottom;
    this.top = top;
    if (isFixAspectRatio) fixAspectRatio();
  }

  public void setCenterOfScreen(double x, double y) {
    double xSpan = (right - left) / 2;
    double ySpan = (top - bottom) / 2;
    setScreenBounds(x - xSpan, x + xSpan, y - ySpan, y + ySpan);
  }
  
  public void setRadiusShown(double r) {
    double xCen = (left + right) / 2;
    double yCen = (top + bottom) / 2;
    if (getWidth() > getHeight()) {
      setScreenBounds(xCen - r * getWidth() / getHeight(), xCen + r * getWidth() / getHeight(), yCen - r, yCen + r);
    } else {
      setScreenBounds(xCen - r, xCen + r, yCen - r * getHeight() / getWidth(), yCen + r * getHeight() / getWidth());
    }
  }
  
  public void zoom(double x, double y, double s) {
    double l = x - (x - left) * s;
    double r = x + (right - x) * s;
    double t = y + (top - y) * s;
    double b = y - (y - bottom) * s;
    setScreenBounds(l, r, b, t);
  }
  
  public void handleMouseClick(double x, double y, MouseEvent e) {
    System.out.println(String.format("%f, %f", x, y));
    System.out.println("click");
  }

  public void update() {
  }
  
  private Point2D getLocation(int px, int py) { 
    try {
      return getTransform().inverseTransform(new Point2D.Double(px,  py), null);
    } catch (NoninvertibleTransformException e) {
      e.printStackTrace();
    } 
    return null;
  }
  
  public final AffineTransform getTransform() {
    double xCoeff = getWidth() / (right - left);
    double yCoeff = getHeight() / (bottom - top);
    double xConst = left * getWidth() / (left - right);
    double yConst = top * getHeight() / (top - bottom);
    return new AffineTransform(xCoeff, 0, 0, yCoeff, xConst, yConst);
  }
  
  public void fixAspectRatio() {
    AffineTransform t = getTransform();
    double xScale = Math.abs(t.getScaleX());
    double yScale = Math.abs(t.getScaleY());
    if (xScale > yScale) {
      double cen = (left + right) / 2;
      double halfWidth = getWidth() / yScale / 2; 
      left = cen - halfWidth;    
      right = cen + halfWidth;
    } else if (xScale < yScale) {
      double cen = (top + bottom) / 2;
      double halfHeight = getHeight() / xScale / 2; 
      bottom = cen - halfHeight;    
      top = cen + halfHeight;
    }
  }
  
  private class ScreenMouser extends MouseAdapter implements MouseWheelListener {
    private int dragStartX, dragStartY;

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
      if (isZoomable) {
        int notches = e.getWheelRotation();
        Point2D zoomPoint = getLocation(e.getX(), e.getY());
        zoom(zoomPoint.getX(), zoomPoint.getY(), Math.pow(WHEEL_ZOOM_FACTOR, notches));
        repaint();
      }
    }
  
    @Override
    public void mouseDragged(MouseEvent e) {
      if (isDraggable) {
        translateScreenByPixels(dragStartX - e.getX(), dragStartY - e.getY());
        updateDragStart(e);
        repaint();
      }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
      if (isDraggable) updateDragStart(e);
      Point2D p = getLocation(e.getX(), e.getY());
      ZoomablePanel.this.handleMouseClick(p.getX(), p.getY(), e);
    }
    
    private void updateDragStart(MouseEvent e) {
      dragStartX = e.getX();
      dragStartY = e.getY();
    }
  }
}
package buba.main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

public class MouseManager implements MouseListener, MouseMotionListener{

	private boolean leftPressed, rightPressed, dragged, lastDragged;
	private int MouseX, MouseY, draggedX, draggedY;
	
	public MouseManager() {
	}
	
	public boolean isLeftPressed() {
		return leftPressed;
	}
	
	public boolean isRightPressed() {
		return rightPressed;
	}

	public int getMouseX() {
		return MouseX;
	}

	public int getMouseY() {
		return MouseY;
	}
	
	

	public int getDraggedX() {
		return draggedX;
	}

	public int getDraggedY() {
		return draggedY;
	}
	
	public boolean isLastDragged() {
		return lastDragged;
	}

	public boolean isDragged() {
		return dragged;
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		draggedX = e.getX();
		draggedY = e.getY();
		dragged = true;
		lastDragged = true;
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		MouseX = e.getX();
		MouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = true;
		if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = true;
		
		lastDragged = false;
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1)
			leftPressed = false;
		if(e.getButton() == MouseEvent.BUTTON3)
			rightPressed = false;
		
		dragged = false;
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}

package src.main.view;

import java.awt.Graphics;
import java.awt.Font;
import javax.swing.*;

import src.main.controller.ViewController;

/**
Parent class of all Pages, inherits from JPanel, sets certain common parameters among pages
such as title and main fonts, frame, and widget. contains an int that keep track of page switching
and a ViewController for Database access
*/
public abstract class Page extends JPanel {
	protected JFrame f;
	protected Font titleFont;
	protected Font mainText;
	protected int switchEvent;
	protected Widget widget;
	protected static ViewController controller;
	
	
	/**
	Constructor for Page, creates and sets the fonts and opens a new JFrame
	@param c ViewController reference passed in so that Pages can communicate with the rest of the model
	*/
	public Page(ViewController c){
		titleFont= new Font("Helvetica", Font.BOLD, 35);
		mainText= new Font("Times New Roman", Font.PLAIN, 16);
		f = new JFrame("Property Rental System");
		f.setSize(800, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		resetSwitchEvent();
		controller = c;
	}
	/**
	function for resetting the switch event
	*/
	public void resetSwitchEvent(){
		switchEvent=0;
	}
	/**
	function for getting the switch event
	@return the current switchEvent value
	*/
	public int getSwitchEvent(){
		return switchEvent;
	}
	/**
	function for setting the switch event
	@param swe the desired switchEvent
	*/
	public void setSwitchEvent(int swe){
		switchEvent=swe;
	}
	/**
	function for getting the JFrame object
	@return a reference to the current JFrame object
	*/
	public JFrame getFrame(){
		return this.f;
	}
	/**
	abstract function for painting componenets that use the decorator pattern
	@param g a Graphics object reference used for drawing
	*/
	public abstract void paintComponent(Graphics g);
	/**
	abstract function for drawing components that use action listeners
	*/
	public abstract void draw ();
}
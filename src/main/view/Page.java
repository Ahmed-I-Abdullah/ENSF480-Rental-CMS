package src.main.view;

import java.awt.Graphics;
import java.awt.Font;
import javax.swing.*;

import src.main.controller.ViewController;

public abstract class Page extends JPanel {
	protected JFrame f;
	protected Font titleFont;
	protected Font mainText;
	protected int switchEvent;
	protected Widget widget;
	protected static ViewController controller;
	
	public Page(ViewController c){
		titleFont= new Font("Helvetica", Font.BOLD, 35);
		mainText= new Font("Times New Roman", Font.PLAIN, 16);
		f = new JFrame();
		f.setSize(800, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		resetSwitchEvent();
		controller = c;
	}
	
	public void resetSwitchEvent(){
		switchEvent=0;
	}
	public int getSwitchEvent(){
		return switchEvent;
	}
	
	public void setSwitchEvent(int swe){
		switchEvent=swe;
	}
	
	public abstract void paintComponent(Graphics g);
	
	public abstract void draw ();
}
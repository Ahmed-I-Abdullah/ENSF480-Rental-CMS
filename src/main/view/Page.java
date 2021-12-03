package src.main.view;
//package GUIPackage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import javax.swing.*;
public abstract class Page extends JPanel {
	protected JFrame f;
	protected Font titleFont;
	protected Font mainText;
	protected int switchEvent;
	protected Widget widget;
	
	public Page(){
		titleFont= new Font("Helvetica", Font.BOLD, 35);
		mainText= new Font("Times New Roman", Font.PLAIN, 16);
		f = new JFrame();
		f.setSize(800, 800);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setLocationRelativeTo(null);
		resetSwitchEvent();
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
	
	public abstract void draw (String [] t);
}
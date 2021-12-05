package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

public class MainPage extends Page{
	protected String [] text;
	
	
	public MainPage(Widget w){
		widget=w;
		resetSwitchEvent();
	}
	
	@Override
	public void draw(String [] t){
		this.text=t;
		
		JLabel prompt= new JLabel("Enter Username and Password");
		prompt.setBounds(600, 70, 200, 30);
		
		JTextField userN= new JTextField();
		JLabel un= new JLabel("Username");
		un.setBounds(600,100,80,30);
		userN.setBounds(670, 100, 100, 30);
		
		JPasswordField pass= new JPasswordField();
		JLabel pw=new JLabel("Password");
		pw.setBounds(600,150,80,30);
		pass.setBounds(670,150,100,30);
		
		JButton button= new JButton("Sign in");
		button.setBounds(670, 200,70,25);
		
		JButton browse = new JButton("Browse");
		browse.setBounds(100,120, 100, 50);
		
		browse.addActionListener(e->{
			f.setVisible(false);
			f.getContentPane().removeAll();
			switchEvent=1;			
		});

		button.addActionListener(e-> {	
			String username=userN.getText();
			String password=new String(pass.getPassword());
			if(MainPage.login(username, password)){
				text[0]+=userN.getText();
				f.setVisible(false);
				f.getContentPane().remove(this);
				f.getContentPane().remove(pass);
				f.getContentPane().remove(pw);
				f.getContentPane().remove(userN);
				f.getContentPane().remove(un);
				f.getContentPane().remove(button);
				f.getContentPane().remove(prompt);
				f.getContentPane().add(this);
				f.setVisible(true);
			}else{
				prompt.setText("Please try again");
				pass.setText("");
				userN.setText("");
			}
		});
		pass.addActionListener(e-> {	
			String username=userN.getText();
			String password=new String(pass.getPassword());
			if(MainPage.login(username, password)){
				text[0]+=userN.getText();
				f.setVisible(false);
				f.getContentPane().remove(this);
				f.getContentPane().remove(pass);
				f.getContentPane().remove(pw);
				f.getContentPane().remove(userN);
				f.getContentPane().remove(un);
				f.getContentPane().remove(button);
				f.getContentPane().remove(prompt);
				f.getContentPane().add(this);
				f.setVisible(true);
			}else{
				prompt.setText("Please try again");
				pass.setText("");
				userN.setText("");
			}
		});
		
		JButton createListing = new JButton("Add a Property!");
		createListing.setBounds(100, 220, 150, 50);
		
		createListing.addActionListener(e->{
			f.setVisible(false);
			f.getContentPane().removeAll();
			switchEvent=5;			
		});
		
		f.add(userN);
		f.add(un);
		f.add(pass);
		f.add(pw);
		f.add(button);
		f.add(prompt);
		f.add(browse);
		f.add(createListing);

		f.getContentPane().add(this);
		f.setVisible(true);
		
	}
	public void paintComponent(Graphics g){
		//System.out.println("paint");
		widget=new Text(300, 40, "RENT A PLACE");
		g.setFont(titleFont);
		widget.draw(g);
		widget=new Border(widget,0,0, 785,762, 10);
		widget.draw(g);
		g.setFont(mainText);
		widget=new Text(300, 100, text[0]);
		widget.draw(g);
	}
	
	public static boolean login(String username, String password){
		System.out.println("User name: " + username+"\nPassword is: "+password);
		//return ViewController.login(username, password);
		if(username.equals("Bob")&&password.equals("Password")){
			return true;
		}
		return false;
	}
	
// 	public static void main(String [] args){
// 	Widget w=null;
// 	Page p= new MainPage(w);
// 	String [] welcome ={"Hello "};
// 	String [] Listings ={"1234 SunHarbor Cresant", "47 Sommervale Drive", "14 North Drive SW Apt. 7", "88 Los Almos Blvd","191 Leninskya Street Apt. 41" };
// 	p.draw(welcome);

// 	while(p.getSwitchEvent()==0){ 
// 		System.out.println("");
// 	}
// 	if(p.getSwitchEvent()==1){
// 		p=new BrowseListingsPage(w);
// 		p.draw(Listings);
// 	}
// 	//System.out.println("click");
// 	//System.exit(1);
// }
	
}
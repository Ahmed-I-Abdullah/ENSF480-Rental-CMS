//package GUIPackage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import javax.swing.*;
import java.awt.*;

public class MainPage extends Page{
	protected String [] text;
	
	
	public MainPage(Widget w){
		widget=w;
		resetSwitchEvent();
	}
	
	@Override
	public void draw(String [] t){
		this.text=t;
		
		final JLabel prompt= new JLabel("Enter Username and Password");
		prompt.setBounds(600, 70, 200, 30);
		
		final JTextField userN= new JTextField();
		JLabel un= new JLabel("Username");
		un.setBounds(600,100,80,30);
		userN.setBounds(670, 100, 100, 30);
		
		final JPasswordField pass= new JPasswordField();
		JLabel pw=new JLabel("Password");
		pw.setBounds(600,150,80,30);
		pass.setBounds(670,150,100,30);
		
		final JButton button= new JButton("Sign in");
		button.setBounds(670, 200,100,25);
		
		final JButton Sinbutton= new JButton("Register");
		Sinbutton.setBounds(670, 225,100,25);
		
		final JButton browse = new JButton("Browse");
		browse.setBounds(100,120, 100, 50);
		
		final JButton manage = new JButton("Manage");
		manage.setBounds(100,220,100,50);
		
		final JButton post = new JButton("Post");
		post.setBounds(100,170,100,50);
		
		final JButton signout= new JButton("Sign out");
		signout.setBounds(670,200,100,25);
		
		browse.addActionListener(e->{
			f.setVisible(false);
			f.getContentPane().removeAll();
			switchEvent=1;			
		});
		
		post.addActionListener(e->{
			f.setVisible(false);
			f.getContentPane().removeAll();
			switchEvent=2;			
		});
		
		manage.addActionListener(e->{
			f.setVisible(false);
			f.getContentPane().removeAll();
			switchEvent=3;			
		});
		
		Sinbutton.addActionListener(e->{
			
			JFrame pop = new JFrame("Register");
			pop.setSize(250,250);
			pop.setLocationRelativeTo(null);
			pop.setLayout(new GridBagLayout());
			
			GridBagConstraints c= new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			
			JButton register = new JButton("Register");
			
			JTextField username= new JTextField("Username");
			
			JTextField email= new JTextField("Email");
			
			JTextField name = new JTextField("Name");
			
			JTextField password = new JTextField("Password");
			
			String [] select= {"Renter", "Landlord"};
			
			JLabel typeLabel = new JLabel("Are you a:");
			JComboBox <String> type = new JComboBox <String>(select);
			type.setSelectedIndex(0);
			
			
			register.addActionListener(p->{

				//Register(username.getText(), password.getText(), type.getSelectedItem());
				text[0]+=username.getText();
				user=username.getText();
				f.setVisible(false);
				f.getContentPane().remove(this);
				f.getContentPane().remove(pass);
				f.getContentPane().remove(pw);
				f.getContentPane().remove(userN);
				f.getContentPane().remove(un);
				f.getContentPane().remove(button);
				f.getContentPane().remove(prompt);
				f.getContentPane().remove(Sinbutton);
				f.getContentPane().add(signout);
				f.getContentPane().add(this);
				f.setVisible(true);
				pop.setVisible(false);
				
			});
			c.weighty=1.0;
			c.weightx=0.5;
			c.ipadx=1;
			c.gridwidth=1;
			c.gridx=0;
			c.gridy=0;
			pop.add(username, c);
			
			c.gridx=0;
			c.gridy=1;
			pop.add(password, c);
			
			c.gridx=1;
			c.gridy=1;
			pop.add(email, c);
			
			c.gridx=1;
			c.gridy=0;
			pop.add(name, c);
			
			c.gridx=0;
			c.gridy=2;
			pop.add(typeLabel, c);
			
			c.weighty=0.0;
			c.gridx=0;
			c.gridy=3;
			pop.add(type, c);
			
			c.weighty=1.0;
			c.gridx=0;
			c.gridy=4;
			pop.add(register, c);
		
			pop.setVisible(true);

		});
		
		
		
		button.addActionListener(e-> {	
			String username=userN.getText();
			user=username;
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
				f.getContentPane().remove(Sinbutton);
				f.getContentPane().add(signout);
				f.getContentPane().add(this);
				f.setVisible(true);
			}else{
				prompt.setText("Please try again");
				pass.setText("");
				userN.setText("");
			}
		});
		
		signout.addActionListener(e->{
				text[0]="Hello";
				f.setVisible(false);
				f.getContentPane().remove(this);
				f.getContentPane().add(pass);
				f.getContentPane().add(pw);
				f.getContentPane().add(userN);
				userN.setText("");
				pass.setText("");
				f.getContentPane().add(un);
				f.getContentPane().add(button);
				f.getContentPane().add(prompt);
				f.getContentPane().add(Sinbutton);
				f.getContentPane().remove(signout);
				f.getContentPane().add(this);
				f.setVisible(true);
			
			
		});
		
		
		if(user==null){
			f.add(userN);
			f.add(un);
			f.add(pass);
			f.add(pw);
			f.add(button);
			f.add(prompt);
			f.add(Sinbutton);
		}else{
			if(user.equals("Jim")||user.equals("Bob")){
				f.add(post);
				if(user.equals("Bob")){
					f.add(manage);
				}
			}
		}
		f.add(browse);
		f.getContentPane().add(this);
		f.setVisible(true);
		
	}
	public void paintComponent(Graphics g){
		widget=new Text(230, 40, "RENT A PLACE");
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
	
	public static void main(String [] args){
	Widget w=null;
	Page p= new MainPage(w);
	String [] welcome ={"Hello "};
	String [] Listings ={"1234 SunHarbor Cresant", "47 Sommervale Drive", "14 North Drive SW Apt. 7", "88 Los Almos Blvd","191 Leninskya Street Apt. 41" };
	p.draw(welcome);
	while(p.getSwitchEvent()==0){System.out.println();}
	
	while(true){
	if(p.getSwitchEvent()==1){
		p=new BrowseListingsPage(w);
		p.draw(Listings);
		
		while(p.getSwitchEvent()==1){System.out.println();}

	}else if(p.getSwitchEvent()==0){
		p=new MainPage(w);
		p.draw(welcome);
		
		while(p.getSwitchEvent()==0){System.out.println();}
	}else if(p.getSwitchEvent()==3){
		p=new ManagerControlPage(w);
		p.draw(null);
		while(p.getSwitchEvent()==3){System.out.println();}
	}
	}
}
	
}
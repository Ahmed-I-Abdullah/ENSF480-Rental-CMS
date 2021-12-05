//package GUIPackage;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Font;
import javax.swing.*;
import java.awt.*;

public class BrowseListingsPage extends Page {
	protected String [] text;
	
	public BrowseListingsPage(Widget w){
		widget=w;
		switchEvent=1;
	}
	@Override
	public void draw(String[] t){
		this.text =t;
		
		JButton back= new JButton("Back");
		back.setBounds(10,10, 75,50);
		
		
		JButton filters = new JButton("Filters");
		filters.setBounds(100,100,100,50);
		
		filters.addActionListener(e->{
			JFrame pop = new JFrame("Select Your Preferances");
			pop.setSize(350,350);
			pop.setLocationRelativeTo(null);
			pop.setLayout(new GridBagLayout());
			
			GridBagConstraints c= new GridBagConstraints();
			c.fill = GridBagConstraints.HORIZONTAL;
			
			JButton ok = new JButton("OK");
			
			JButton save= new JButton("Save");
			
			String [] select= {"Yes", "No"};
			
			String [] quad={"NW","SW","SE", "NE"};
			
			JLabel quadrantLabel = new JLabel("Quadrant");
			JComboBox <String> quadrant = new JComboBox <String>(quad);
			quadrant.setSelectedIndex(1);
			
			
			JLabel furnishedLabel= new JLabel("Furnished");
			JComboBox <String> furnished = new JComboBox <String>(select);
			furnished.setSelectedIndex(1);
			
			JTextField type = new JTextField("Type: apartment, townhouse etc.");
			
			
			JTextField minPrice = new JTextField("Min price");
			
			JTextField maxPrice = new JTextField("Max price");
			
			JLabel bedroomsLabel = new JLabel("Number of Bedrooms");
			JSlider bedrooms = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
			bedrooms.setMajorTickSpacing(1);
			bedrooms.setPaintTicks(true);
			bedrooms.setPaintLabels(true);
			bedrooms.setFont(mainText);
			
			JLabel bathroomsLabel= new JLabel("Number of Bathrooms");
			JSlider bathrooms = new JSlider(JSlider.HORIZONTAL, 0, 5, 0);
			bathrooms.setMajorTickSpacing(1);
			bathrooms.setPaintTicks(true);
			bathrooms.setPaintLabels(true);
			bathrooms.setFont(mainText);
			
			
			ok.addActionListener(p->{
				// System.out.println("Ok pressed");
				// System.out.println("Pets: " + (String) pets.getSelectedItem());
				// System.out.println("Furnished: " + (String) furnished.getSelectedItem());
				// System.out.println("Min Price: " + minPrice.getText());
				// System.out.println("Max Price: " + maxPrice.getText());
				// System.out.println("number of bathrooms: " + bathrooms.getValue());
				// System.out.println("number of bedrooms: " + bedrooms.getValue());
				
				pop.setVisible(false);
				//text = Update(text, above filters);
				//f.setVisible(false);
				//f.draw(text);
				//f.setVisble(true);
			});
			
			save.addActionListener(p->{
				// System.out.println("Save pressed");
				// System.out.println("Pets: " + (String) pets.getSelectedItem());
				// System.out.println("Furnished: " + (String) furnished.getSelectedItem());
				// System.out.println("Min Price: " + minPrice.getText());
				// System.out.println("Max Price: " + maxPrice.getText());
				// System.out.println("number of bathrooms: " + bathrooms.getValue());
				// System.out.println("number of bedrooms: " + bedrooms.getValue());
				//Controller.save();
			});
			
			
			
			c.weightx=0.0;
			c.gridwidth=1;
			c.gridx=1;
			c.gridy=0;
			pop.add(minPrice, c);
			
			c.gridx=1;
			c.gridy=1;
			pop.add(maxPrice, c);
			
			c.gridx=0;
			c.gridy=0;
			pop.add(type, c);
			
			c.gridx=0;
			c.gridy=2;
			pop.add(quadrantLabel, c);
			
			c.gridx=1;
			c.gridy=2;
			pop.add(furnishedLabel, c);
			
			c.gridx=0;
			c.gridy=3;
			pop.add(quadrant, c);
			
			c.gridx=1;
			c.gridy=3;
			pop.add(furnished, c);
			
			
			c.gridx=0;
			c.gridy=4;
			pop.add(bedroomsLabel, c);
			
			c.gridx=0;
			c.gridy=6;
			pop.add(bathroomsLabel, c);
			
			c.gridx=1;
			c.gridy=8;
			pop.add(ok, c);
			
			if(user!=null){
			c.gridx=0;
			c.gridy=8;
			pop.add(save, c);
			}
			c.gridwidth=2;
			c.weightx=0.0;
			c.gridx=0;
			c.gridy=5;
			pop.add(bedrooms, c);
			
			c.gridwidth=2;
			c.weightx=0.0;
			c.gridx=0;
			c.gridy=7;
			pop.add(bathrooms, c);
		
		pop.setVisible(true);
			
			
		});
		
		
		for(int i=0; i< text.length ; i++){
			JButton clickme = new JButton("View");
			clickme.setBounds(500, 50+(i*50), 75,50);
			final int p=i;
			clickme.addActionListener( e->{
				for(int z=0; z<text.length; z++){
					if(z==p)
						System.out.println(text[z]);
				}
			});
			f.add(clickme);
		}
		
		back.addActionListener(e->{
			f.setVisible(false);
			f.removeAll();
			resetSwitchEvent();
			
		});
		
		f.add(back);
		f.add(filters);
		f.getContentPane().add(this);
		f.setVisible(true);
	}
	
	public void paintComponent(Graphics g){
		widget=new Text(230, 40, "BROWSE LISTINGS");
		g.setFont(titleFont);
		widget.draw(g);
		widget=new Border(widget,0,0, 785,762, 10);
		widget.draw(g);
		g.setFont(mainText);
		for(int i=0; i< text.length ; i++){
			widget=new Border(widget,250, 50+(i*50), 250,50, 5);
			widget.draw(g);
			widget=new Text(275, 80+(i*50), text[i]);
			widget.draw(g);
		}
	}
	
	
	
	
}
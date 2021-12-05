package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

import src.main.model.property.ListingDetails;
import src.main.model.property.ListingState;
import src.main.model.property.Property;

import java.awt.*;
import java.awt.event.*;

import java.util.Date;
import src.main.model.property.*;

public class CreateListingsPage extends Page {
    protected String [] text;
	
	public CreateListingsPage(Widget w){
		widget=w;
		switchEvent=5;
	}

	@Override
	public void draw(String[] t){
		this.text = t;
		
		//JFrame f = new JFrame("Register your property:");
		//f.setSize(700,700);
		//f.setLocationRelativeTo(null);
		f.setLayout(new GridBagLayout());
		
		GridBagConstraints c= new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JButton ok = new JButton("OK");
		
		JButton save= new JButton("Save");
		
		String [] select= {"Yes", "No"};
		
		String [] quad={"NW","SW","SE", "NE"};
		
		JLabel quadrantLabel = new JLabel("Quadrant");
		JComboBox <String> quadrant = new JComboBox <String>(quad);
		quadrant.setSelectedIndex(1);
		
		JLabel descriptionLabel= new JLabel("Enter a description");
		JTextField description = new JTextField();

		JLabel addressLabel= new JLabel("Fill in the Address");
		JTextField city = new JTextField("City");
		JTextField country = new JTextField("Country");
		JTextField province = new JTextField("Province");
		JTextField street = new JTextField("Street");
		JLabel label = new JLabel("Street number");
		JTextField streetNo = new JTextField(25);
		setLayout(new FlowLayout());
		//label.setForeground(Color.red);
		streetNo.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent ke) {
				String value = streetNo.getText();
				//int l = value.length();
				
			    if ((ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') || ke.getKeyCode() == 8) { //8 is code for delete
					streetNo.setEditable(true);
				  	label.setText("Street number");
			   	} else {
					streetNo.setEditable(false);
				  	label.setText("* Enter only numeric digits(0-9)");
			   	}	
			}
		 });
		JTextField postalCode = new JTextField("Postal Code");
		
		JLabel furnishedLabel= new JLabel("Furnished");
		JComboBox <String> furnished = new JComboBox <String>(select);
		furnished.setSelectedIndex(1);
		
		JLabel typeLabel = new JLabel("Enter House Type:");
		JTextField type = new JTextField("Apartment, attached/detached house, townhouse, etc.");

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
			//make it go to payment page!
			f.setVisible(false);
		});
		
		save.addActionListener(p->{
			boolean furnish = false;
			if(furnished.getSelectedItem().toString() == "Yes"){
				furnish = true;
			}
			ListingDetails listingDetails = new ListingDetails(ListingState.REGISTERED, bedrooms.getValue(), type.getSelectedText(), furnish, quadrant.getSelectedItem().toString());
			Address address = new Address(city.getSelectedText(),province.getSelectedText(),country.getSelectedText(),street.getSelectedText(),Integer.valueOf(streetNo.getSelectedText()), postalCode.getSelectedText());
			Date date = java.util.Calendar.getInstance().getTime(); //get todays date
			//how to populate houseID and postedBy?
			Property property = new Property(1, address, listingDetails, null, date, description.getText());
		});
		
		c.weightx=0.0;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		f.add(addressLabel, c);
		
		c.gridx=0;
		c.gridy=1;
		f.add(label, c);

		c.gridx=1;
		c.gridy=1;
		f.add(streetNo, c);

		c.gridx=0;
		c.gridy=2;
		f.add(street, c);

		c.gridx=1;
		c.gridy=2;
		f.add(postalCode, c);

		c.gridx=0;
		c.gridy=3;
		f.add(city, c);

		c.gridx=1;
		c.gridy=3;
		f.add(province, c);

		c.gridx=2;
		c.gridy=3;
		f.add(country, c);
		
		c.gridx=0;
		c.gridy=4;
		f.add(quadrantLabel, c);
		
		c.gridx=1;
		c.gridy=4;
		f.add(furnishedLabel, c);
		
		c.gridx=0;
		c.gridy=5;
		f.add(quadrant, c);
		
		c.gridx=1;
		c.gridy=5;
		f.add(furnished, c);
		
		c.gridx=0;
		c.gridy=6;
		f.add(typeLabel, c);

		c.gridwidth=2;
		c.gridx=0;
		c.gridy=7;
		f.add(type, c);
		
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=8;
		f.add(bedroomsLabel, c);
		
		c.gridx=0;
		c.gridy=10;
		f.add(bathroomsLabel, c);

		c.gridx=0;
		c.gridy=12;
		f.add(descriptionLabel, c);
		
		c.gridx=1;
		c.gridy=14;
		f.add(ok, c);
		
		//if(user!=null){
		c.gridx=0;
		c.gridy=14;
		f.add(save, c);
		//}
		c.gridwidth=2;
		c.weightx=0.0;
		c.gridx=0;
		c.gridy=9;
		f.add(bedrooms, c);
		
		c.gridwidth=2;
		c.gridx=0;
		c.gridy=11;
		f.add(bathrooms, c);

		c.gridwidth=2;
		c.weightx=1.0;
		c.gridx=0;
		c.gridy=13;
		f.add(description, c);
		
		f.setVisible(true);
		f.getContentPane().add(this);
	}
	
	public void paintComponent(Graphics g){
		widget = new Text(230, 40, "Register your property:");
		g.setFont(titleFont);
		widget.draw(g);
	}
}

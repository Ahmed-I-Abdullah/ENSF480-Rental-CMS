package src.main.view;

import java.awt.Graphics;
import javax.swing.*;

import java.awt.*;

public class ManagerReportPage extends Page {
    protected String [] text;
	
	public ManagerReportPage(Widget w){
		widget=w;
		switchEvent=6;
	}

	@Override
	public void draw(String[] t){
		this.text = t;

		f.setLayout(new GridBagLayout());
		
		GridBagConstraints c= new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel label = new JLabel("Select Report Period:");
        JLabel from = new JLabel("from");
		JTextField period1 = new JTextField("MM/DD/YYYYY");
        JLabel to = new JLabel("to");
        JTextField period2 = new JTextField("MM/DD/YYYY");

		JButton submit = new JButton("Submit");

        JLabel listed = new JLabel("Total number of houses listed: ");
        JLabel listedAmount = new JLabel("");
        JLabel rented = new JLabel("Total number of houses rented: ");
        JLabel rentedAmount = new JLabel("");
        JLabel active = new JLabel("Total number of active listings: ");
        JLabel activeAmount = new JLabel("");
        

        JLabel list = new JLabel("List of houses rented:");
        Font font = new Font("Courier", Font.BOLD,12);
        
        JLabel landlord = new JLabel("Landlord Name");
        landlord.setFont(font);
        JLabel houseID = new JLabel("House Id");
        houseID.setFont(font);
        JLabel houseAddress = new JLabel("House Address");
        houseAddress.setFont(font);

		submit.addActionListener(p->{
		//add AdminController, use the period set by doing period1.getTextField()

            listedAmount.setText("318");
            rentedAmount.setText("87");
            activeAmount.setText("40");

            c.gridwidth=1;
            c.gridx=0;
            c.gridy=5;
            f.add(listed, c);

            c.gridx=1;
            c.gridy=5;
            f.add(listedAmount, c);
            
            c.gridx=0;
            c.gridy=6;
            f.add(rented, c);

            c.gridx=1;
            c.gridy=6;
            f.add(rentedAmount, c);
            
            c.gridx=0;
            c.gridy=7;
            f.add(active, c);

            c.gridx=1;
            c.gridy=7;
            f.add(activeAmount, c);
            
            c.gridx=0;
            c.gridy=9;
            f.add(list, c);
            
            c.gridx=0;
            c.gridy=10;
            f.add(landlord, c);

            c.gridx=1;
            c.gridy=10;
            f.add(houseID, c);
            
            c.gridx=2;
            c.gridy=10;
            f.add(houseAddress, c);

            for(int i=0; i< text.length ; i++){
                JLabel houseText = new JLabel(text[i]);
                c.gridx=i;
                c.gridy=11;
                f.add(houseText, c);
            }

            f.setVisible(true);
		    f.getContentPane().add(this);
		});
		
		c.weightx=0.0;
		c.gridwidth=1;
		c.gridx=0;
		c.gridy=0;
		f.add(label, c);
		
		c.gridx=0;
		c.gridy=1;
		f.add(from, c);

		c.gridx=1;
		c.gridy=1;
		f.add(period1, c);

		c.gridx=0;
		c.gridy=2;
		f.add(to, c);

		c.gridx=1;
		c.gridy=2;
		f.add(period2, c);

		c.gridx=0;
		c.gridy=3;
        c.gridwidth=2;
		f.add(submit, c);
		
		f.setVisible(true);
		f.getContentPane().add(this);
	}
	
	public void paintComponent(Graphics g){
		//widget.draw(g);
	}
}

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;


public class MyPanel extends JPanel implements ActionListener, KeyListener{
	
	BufferedImage parking_map;
	Car car;
	MyJFrame parent_frame;
	
	public MyPanel(MyJFrame frame){
		setFocusable(true);
		addKeyListener(this);
		parent_frame = frame;
		car = new Car(this);

		//FileReader fileReader = new FileReader("/home/tomek/WORKSPACES!!/ObliczeniaMiekkie/Parkowanie/Maps");
		//File file = fileReader.re
		//parking_map = ImageIO.read(file);
		

		File flPlik =  new File("/home/tomek/WORKSPACES!!/ObliczeniaMiekkie/Parkowanie/Maps/1.bmp");
		try {
			parking_map =   ImageIO.read(flPlik);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 	   	car.setMap(parking_map);
 	    int width = parking_map.getWidth(null);
 	    int height = parking_map.getHeight(null);
 	    parent_frame.setSize(width, height);
 	    repaint();
		
		setLayout(new BorderLayout());
		MyJMenuBar menu = new MyJMenuBar(this);
		//add, constraints, index)(img, BorderLayout.CENTER);
		add(menu, BorderLayout.NORTH);
	}

	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		//drawAll rysuje samochod I MAPE PARKINGU!!
		car.drawAll(g2d);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JPG Images", "jpg");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(this);
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	       System.out.println("You chose to open this file: " +
	            chooser.getSelectedFile().getName());
	       File selected_file = chooser.getSelectedFile();
	       try {
	    	   parking_map = ImageIO.read(selected_file);
	    	   car.setMap(parking_map);
	    	   int width = parking_map.getWidth(null);
	    	   int height = parking_map.getHeight(null);
	    	   parent_frame.setSize(width, height);
	    	   car.start();
	    	   //revalidate();
	    	   repaint();
	       } catch (IOException e1) {
	    	   // TODO Auto-generated catch block
	    	   e1.printStackTrace();
	       }
	    }	
	}

	@Override
	public void keyPressed(KeyEvent e) {
		char c = e.getKeyChar();
		car.move(c);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}

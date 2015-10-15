import javax.swing.JFrame;


public class MyJFrame extends JFrame {
	
	public MyJFrame(String s){
		super(s);
		setBounds(10, 10, 800, 500);
		MyPanel panel = new MyPanel(this);
		add(panel);
	}

}

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;


public class Car{
	BufferedImage mapa_parkingu;
	MyPanel panel;
	Point polozenie;
	Point wymiary; //zawsze pierwszy wymiar ma byc mniejszy!!!!
	int odleglosc_od_tylu_do_osi;
	double predkosc;
	double kierunek; //0 znaczy jade w gore ekranu, pi/2 w prawo pi w dol itd
	WidokAutaZGory widok_z_gory;
	
	public Car(MyPanel _panel){
		panel = _panel;
		polozenie = new Point(100, 100);
		//stosunek dlugosc szerokosc  i odleglosc od tylu auta do osi tylnych kol ( os obrotu samochodu) taka jak we fiacie punto
		wymiary = new Point(130, 300); //zawsze pierwszy wymiar ma byc mniejszy!!!!
		odleglosc_od_tylu_do_osi = 45;
		predkosc = 0;
		kierunek = 0;//Math.PI*0.5; //0 znaczy jade w gore ekranu, pi/2 w prawo pi w dol itd
		widok_z_gory = new WidokAutaZGory(polozenie, wymiary, kierunek, predkosc, odleglosc_od_tylu_do_osi);
	}
	
	public void setMap(BufferedImage map){
		mapa_parkingu = map;
	}
	
	public void drawAll(Graphics2D g2d){
		g2d.drawImage(mapa_parkingu, 0, 0, null);
		widok_z_gory.draw(g2d);
	}
	
	public void start(){
		panel.repaint();
	}
	
	public void move(char c){
		if(c=='d')
			turnRight(Math.PI/90);
		if(c=='a')
			turnLeft(Math.PI/90);
		if(c=='w')
			goForward(10);
		if(c=='s')
			goBack(10);
		if(collision()){
			System.out.println("collision!!!!");
		}
	}
	
	public boolean collision(){
		//na razie sprawdzam tylko czy nie zahaczylem ktoryms rogiem
		Point rog;
		Polygon widok = widok_z_gory.widok;
		for(int i=0; i<4; i++){
			rog = new Point(widok.xpoints[i], widok.ypoints[i]);
			int color_int = mapa_parkingu.getRGB(rog.x, rog.y);
			Color kolor = new Color(color_int);
			int r = kolor.getRed();
			int g = kolor.getGreen();
			int b = kolor.getBlue();
			//System.out.println(r+" "+g+" "+" "+b);
			//tylko kolizja z czerwonym 
			//trzeba bedzie sprawdzic jeszcze czy jestem we wlasciwym miejscu ale to chyba 
			//inna metoda lepiej
			if(r>230){
				return true;
			}
		}
		return false;
	}
	
	public void turnRight(double angle){
		kierunek+=angle;
		while(kierunek > Math.PI*2)
			kierunek -= Math.PI*2;
		widok_z_gory.zmianaPolozenia(polozenie, kierunek);
		panel.repaint();
	}
	
	public void turnLeft(double angle){
		kierunek-=angle;
		while(kierunek < 0)
			kierunek += Math.PI*2;
		widok_z_gory.zmianaPolozenia(polozenie, kierunek);
		panel.repaint();
	}

	public void goForward(double distance){
		double noweX = polozenie.getX()+Math.sin(kierunek)*distance;
		double noweY = polozenie.getY()-Math.cos(kierunek)*distance;//minus bo os oy jest skierowana w dol
		polozenie = new Point((int)Math.round(noweX), (int)Math.round(noweY));
		widok_z_gory.zmianaPolozenia(polozenie, kierunek);
		panel.repaint();
	}
	
	public void goBack(double distance){
		//tu zamiana tylko znakow +/-
		double noweX = polozenie.getX()-Math.sin(kierunek)*distance;
		double noweY = polozenie.getY()+Math.cos(kierunek)*distance;
		polozenie = new Point((int)Math.round(noweX), (int)Math.round(noweY));
		widok_z_gory.zmianaPolozenia(polozenie, kierunek);
		panel.repaint();
	}
	
}

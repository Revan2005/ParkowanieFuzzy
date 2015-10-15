import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.util.Vector;


public class WidokAutaZGory {
	Point polozenie, wymiary;
	int tyl_os; //odleglosc od tylu samochodu do osi tylnych kol (to jest os obrowu auta)
	int os_przod;//odleglosc od osi tylnych kol do przodu auta
	int promienSwiatel = 5; //promien swiatel swiatla sa wyswietlane w metodzie draw
	double kierunek, predkosc; //kierunek 0 -gora, pi - prawo itd
	int x[] = new int[4];
	int y[] = new int[4];
	public Polygon widok;
	//double dlugoscPolowyPrzekatnej, alfa, beta;
	double promienDlugi, promienKrotki, alfa, beta, gamma;
	//alfa kat z przodu auta, beta - katy boczne, gamma z tylu
	
	public WidokAutaZGory(Point pos, Point wym, double k, double v, int od_tylu_do_osi) {
		polozenie = pos;
		wymiary = wym;
		kierunek = k;
		predkosc = v;	
		tyl_os = od_tylu_do_osi;
		os_przod = wym.y-tyl_os; //y- dlugosc auta (wiekszy wymiar)
		

		promienDlugi = Math.sqrt(wymiary.getX()*wymiary.getX()*0.25+os_przod*os_przod); //*0.25 - 0.2^2 bo ma byc polowa szerokosci uwzgledniona tutaj
		promienKrotki = Math.sqrt(wymiary.getX()*wymiary.getX()*0.25+tyl_os*tyl_os);
		
		alfa = Math.atan((wymiary.getX()/2.0)/os_przod)*2;
		gamma = Math.atan((wymiary.getX()/2.0)/tyl_os)*2;
		beta = (2*Math.PI-alfa-gamma)*0.5;
		System.out.println(alfa+" "+beta+" "+gamma+" "+promienDlugi+" "+promienKrotki);

		ustalWidok();
	}
	
	public void ustalWidok(){
		double prawyPrzedniRogX = polozenie.getX()+Math.sin(kierunek+alfa*0.5)*promienDlugi*0.5;//tu wszedzie i ponizej przed promienDlugi nie pozinno byc *0.5??
		double prawyPrzedniRogY = polozenie.getY()-Math.cos(kierunek+alfa*0.5)*promienDlugi*0.5;
		x[0] = (int)prawyPrzedniRogX;
		y[0] = (int)prawyPrzedniRogY;
		//System.out.println(alfa);
		//System.out.println(Math.sin(alfa/2)*promienDlugi);
		
		double prawyTylnyRogX = polozenie.getX()+Math.sin(kierunek+(alfa*0.5)+beta)*promienKrotki*0.5;
		double prawyTylnyRogY = polozenie.getY()-Math.cos(kierunek+(alfa*0.5)+beta)*promienKrotki*0.5;
		x[1] = (int)prawyTylnyRogX;
		y[1] = (int)prawyTylnyRogY;
		
		double lewyTylnyRogX = polozenie.getX()+Math.sin(kierunek+(alfa*0.5)+beta+gamma)*promienKrotki*0.5;
		double lewyTylnyRogY = polozenie.getY()-Math.cos(kierunek+(alfa*0.5)+beta+gamma)*promienKrotki*0.5;
		x[2] = (int)lewyTylnyRogX;
		y[2] = (int)lewyTylnyRogY;
		
		double lewyPrzedniRogX = polozenie.getX()+Math.sin(kierunek-alfa*0.5)*promienDlugi*0.5;
		//System.out.println(Math.sin(kierunek+alfa*0.5+beta+gamma+beta)*promienDlugi);
		double lewyPrzedniRogY = polozenie.getY()-Math.cos(kierunek-alfa*0.5)*promienDlugi*0.5;
		//System.out.println(Math.cos(kierunek+alfa*0.5+beta+gamma+beta)*promienDlugi);
		
		x[3] = (int)lewyPrzedniRogX;
		y[3] = (int)lewyPrzedniRogY;
		//System.out.println(kierunek+" "+alfa+" "+beta+" "+dlugoscPolowyPrzekatnej);
		//System.out.println(x[0]+" "+y[0]+" "+x[1]+" "+y[1]+" "+x[2]+" "+y[2]+" "+x[3]+" "+y[3]);
		
		widok = new Polygon(x, y, 4);
	}
	
	public void zmianaPolozenia(Point _polozenie, double _kierunek) {
		polozenie = _polozenie;
		kierunek = _kierunek;
		ustalWidok();
	}
	
	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.BLUE);
		g2d.fillPolygon(widok);
		g2d.setColor(Color.YELLOW);
		g2d.fillOval(widok.xpoints[0]-promienSwiatel, widok.ypoints[0]-promienSwiatel, promienSwiatel*2, promienSwiatel*2);
		g2d.fillOval(widok.xpoints[3]-promienSwiatel, widok.ypoints[3]-promienSwiatel, promienSwiatel*2, promienSwiatel*2);
		g2d.setColor(Color.RED);
		g2d.fillOval(widok.xpoints[1]-promienSwiatel, widok.ypoints[1]-promienSwiatel, promienSwiatel*2, promienSwiatel*2);
		g2d.fillOval(widok.xpoints[2]-promienSwiatel, widok.ypoints[2]-promienSwiatel, promienSwiatel*2, promienSwiatel*2);
	}

}

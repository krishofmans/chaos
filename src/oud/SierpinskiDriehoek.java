package oud;

import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/*
 * Created on 5-dec-03
 *
 * To change the template for this generated file go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */

/**
 * @author Kris Hofmans
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SierpinskiDriehoek extends Applet {
	
	Image offScreenBuffer = null;
	Driehoek d = new Driehoek();
	Hoek hoeken[] = new Hoek[3];
	int hoeknr = 3;
	Punt vorigPunt;
	
	public void init(){
		
		hoeken[0] = new Hoek(Color.red, d.getOffset(), d.getOffset() + d.getZijde());
		hoeken[1] = new Hoek(Color.green, d.getZijde() / 2, d.getZijde() - d.getHeight());
		hoeken[2] = new Hoek(Color.blue, d.getZijde() + d.getOffset(), d.getOffset() + d.getZijde());
		
		vorigPunt = new Punt(hoeken[0], 225, 225);
		//this.addPunten();
	
	}
	
	public void paint(Graphics g) {
	
		d.draw(g);
		
		for (int i = 0; i < 1000000; i++){
			
			hoeknr = (int) (Math.random() * 3);

			vorigPunt = new Punt(hoeken[hoeknr], vorigPunt);
			vorigPunt.draw(g);
			
		}
	
	}
	
	/*
	public void addPunten(){
	
		int hoeknr;
	
		for (int i = 0; i < 1000000; i++){
			
			hoeknr = (int) (Math.random() * 3);

			vorigPunt = new old.Punt(hoeken[hoeknr], vorigPunt);
			
		}
	
	}
	*/
	
	public void update(Graphics g) {
		Graphics gr;

		if (offScreenBuffer == null
			|| (!(offScreenBuffer.getWidth(this) == this.size().width
				&& offScreenBuffer.getHeight(this) == this.size().height))) {

			offScreenBuffer = this.createImage(size().width, size().height);

		}

		gr = offScreenBuffer.getGraphics();

		paint(gr);
		g.drawImage(offScreenBuffer, 0, 0, this);

	}


}

class Driehoek {

	int zijde = 1500, offset = 10;

	public void draw(Graphics g){

		int hoogte = this.getHeight();

		/*
		g.drawLine(offset, (offset + zijde), (offset + zijde), (offset + zijde));
		g.drawLine(offset, (offset + zijde), (zijde / 2) + offset, (zijde - hoogte));
		g.drawLine((zijde / 2) + offset, (zijde - hoogte), (offset + zijde), (offset + zijde));
		*/

	}
	
	public int getZijde(){
	
		return zijde;
	
	}
	
	public int getOffset(){
	
		return offset;
	
	}
	
	public int getHeight(){
	
		// C� = A� + B�
		// C� - A� = B�
		return (int) Math.sqrt((zijde * zijde) - ((zijde / 2) * (zijde / 2)));
	
	}

}

class Punt {

	int x, y;
	Hoek h;
	
	public Punt(Hoek nh, int nx, int ny){
	
		h = nh;
		x = nx;
		y = ny;
	
	}
	
	public Punt(Hoek nh, Punt vorigPunt){
	
		h = nh;
		berekenHelft(vorigPunt);
	
	}
	
	public void berekenHelft(Punt vp){
	
		this.x = coordDiff(h.getX(), vp.getX()); 
		this.y = coordDiff(h.getY(), vp.getY());	
	
	}
	
	private int coordDiff(int c1, int c2){
	
		if (c1 > c2){
		
			return ((c1 - c2) / 2) + c2;
		
		} else {
			
			return ((c2 - c1) / 2) + c1;
			
		}
	
	}
	
	public int getX(){
	
		return x;
	
	}
	
	public int getY(){
	
		return y;
	
	}

	public void draw(Graphics g){

		g.setColor(h.getColor());
		g.drawOval(this.x - 1, this.y - 1, 2, 2);
	
	}

}

class Hoek {

	String naam;
	Color kleur;
	int x, y;
	
	public Hoek(Color k, int nx, int ny){

		kleur = k;
		x = nx;
		y = ny;
	
	}
	
	public Color getColor() {

		return kleur;

	}

	public int getX(){
	
		return x;
	
	}
	
	public int getY(){
	
		return y;
	
	}

}

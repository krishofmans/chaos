package nieuw;

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

public class ChaosTheory {

    // gegeven: een gelijkzijdige driehoek
    // 1. we nemen een willekeurig punt, p, binnen deze driehoek
    // 2. we kiezen een willekeurige hoek, h, van de driehoek
    // 3. we tekenen een denkbeeldige lijn van p naar h
    // 4. exact op het midden van de denkbeeldige lijn stippen we een punt aan
    // 5. dit nieuwe punt, p' gaan we hetzelfde principe herhalen vanaf stap 2 en herhaal dit 10.000x
    // * optioneel kan de stip, afhankelijk van de willekeurige hoek, in een andere kleur worden getekend

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Scherm().setVisible(true);
            }
        });
    }


}

class Scherm extends JFrame {

    public Scherm() {
        setPreferredSize(new Dimension(800, 800));
        this.add(new DriehoekGrafisch(new SierpinskiDriehoek(600)));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
    }

}

class DriehoekGrafisch extends JPanel {

    private SierpinskiDriehoek driehoek;

    public DriehoekGrafisch(SierpinskiDriehoek driehoek) {
        setPreferredSize(new Dimension(800, 800));
        setBackground(Color.darkGray);
        this.driehoek = driehoek;

        genereerPunten();
    }

    private void genereerPunten() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                driehoek.genereerPunten(10);

                try {
                    Thread.sleep(80);
                    repaint();
                    genereerPunten();
                } catch (InterruptedException e) {
                    /* negeer */
                }
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        lijn(g, driehoek.getHoek1(), driehoek.getHoek2());
        lijn(g, driehoek.getHoek2(), driehoek.getHoek3());
        lijn(g, driehoek.getHoek3(), driehoek.getHoek1());

        for (Stip stip : driehoek.getStippen()) {
            g.setColor(stip.getHoek().getKleur());
            g.drawRect(stip.getX(), stip.getY(), 1, 1);
        }
    }

    private void lijn(Graphics g, Hoek h1, Hoek h2) {
        g.drawLine(h1.getX(), h1.getY(), h2.getX(), h2.getY());
    }
}


class SierpinskiDriehoek {

    private Random random = new Random();

    private double zijde;
    private Hoek hoek1;
    private Hoek hoek2;
    private Hoek hoek3;

    private Stip laatstePunt;
    private java.util.List<Stip> stippen = new LinkedList<Stip>();

    public SierpinskiDriehoek(double zijde) {
        this.zijde = zijde;

        // hoogte van driehoek
        double halveZijde = zijde / 2;
        int hoogte = (int) Math.sqrt(zijde * zijde - (halveZijde * halveZijde));

        // hoek 1 is de bovenste hoek
        hoek1 = new Hoek(Color.ORANGE, (int) halveZijde, 0);
        // hoek 2 is de linkerhoek
        hoek2 = new Hoek(Color.RED, 0, hoogte);
        // hoek 3 is de rechterhoek
        hoek3 = new Hoek(Color.YELLOW, (int) zijde, hoogte);

        // laatstePunt kiezen we in het begin "random" moet gewoon ergens in de driehoek liggen
        laatstePunt = new Stip(null, (int) (zijde / 2), hoogte / 2);
    }

    public Hoek getHoek1() {
        return hoek1;
    }

    public Hoek getHoek2() {
        return hoek2;
    }

    public Hoek getHoek3() {
        return hoek3;
    }

    public java.util.List<Stip> getStippen() {
        return Collections.unmodifiableList(stippen);
    }

    public void genereerPunten(int aantal) {
        for (int i = 0; i < aantal; i++) {
            Hoek randomHoek = kiesRandomHoek();

            // fictieve lijn tussen x,y van de hoek, en x,y van de laatste stip
            int nieuweX = coordinatenVerschil(randomHoek.getX(), laatstePunt.getX());
            int nieuweY = coordinatenVerschil(randomHoek.getY(), laatstePunt.getY());

            laatstePunt = new Stip(randomHoek, nieuweX, nieuweY);
            stippen.add(laatstePunt);
        }
    }

    private int coordinatenVerschil(int c1, int c2) {

        if (c1 > c2) {
            return ((c1 - c2) / 2) + c2;
        } else {
            return ((c2 - c1) / 2) + c1;
        }

    }

    private Hoek kiesRandomHoek() {
        switch (random.nextInt(3)) {
            case 0:
                return hoek1;
            case 1:
                return hoek2;
            case 2:
            default:
                return hoek3;
        }
    }
}

class Hoek {

    private Color kleur;
    private int x;
    private int y;

    public Hoek(Color kleur, int x, int y) {
        this.kleur = kleur;
        this.x = x;
        this.y = y;
    }

    public Color getKleur() {
        return kleur;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

class Stip {
    private int x;
    private int y;
    private Hoek hoek;

    public Stip(Hoek hoek, int x, int y) {
        this.hoek = hoek;
        this.x = x;
        this.y = y;
    }

    public Hoek getHoek() {
        return hoek;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
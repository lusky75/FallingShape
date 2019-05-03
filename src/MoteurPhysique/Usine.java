package MoteurPhysique;

import java.awt.Color;

public class Usine {

    private final Color[] colors = {Color.BLUE, Color.GREEN, Color.MAGENTA, Color.CYAN};

    public Figure getFigure(double x, double y, double vX, double vY, double m) {
        int c = (int)(Math.random() * 4);
        int f = (int)(Math.random() * 3);
        int s = (int)(Math.random() * 2);
        double inclinaison = Math.random() * Math.PI * 2;
        double vitAngle = Math.random() * Math.random() * Math.random() * (s == 0 ? 1 : -1);
        Deplacement d = new Deplacement(x, y, vX, vY, inclinaison, vitAngle, m);
        switch(f) {
            case 0 : return new Cercle(20, colors[c], d);
            case 1 : return new PolyReg(20, 3, colors[c], d);
            default : return new PolyReg(20, 4, colors[c], d);
        }
    }

    public Figure getFigure(double x, double y) {
        int c = (int)(Math.random() * 4);
        int f = (int)(Math.random() * 3);
        Deplacement d = new Deplacement(x, y, 0, 0, 0, 0, 0);
        switch(f) {
            case 0 : return new Cercle(20, colors[c], d);
            case 1 : return new PolyReg(20, 3, colors[c], d);
            default : return new PolyReg(20, 4, colors[c], d);
        }
    }


}
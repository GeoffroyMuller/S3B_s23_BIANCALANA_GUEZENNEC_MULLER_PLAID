package vue.ComposantVueSalle;

import javax.swing.*;
import java.awt.*;

public class Indicateur extends JPanel {
    public static int HAUTEUR = 30;
    public static int LARGEUR = 30;
    public static int X = 20;
    public static int Y = 60;

    public Indicateur(){

    }

    @Override
    public void paintComponent(Graphics g){
        //Definition de la police d'ecriture
        Font fonte = new Font(" Serial",Font.PLAIN,14);
        g.setFont(fonte);

        //Metrics
        FontMetrics metrics = g.getFontMetrics();

        //Rectangle Allée
        g.setColor(new Color(252,223,78));
        g.fillRect(X,Y,LARGEUR,HAUTEUR);
        //Texte
        g.setColor(new Color(0, 0, 0));
        g.drawString("Allée",LARGEUR+30,((((HAUTEUR-metrics.getHeight())/2)+metrics.getAscent())+Y));

        //Rectangle Place
        g.setColor(new Color(23, 148, 153));
        g.fillRect(X,(((HAUTEUR)+10)+Y),LARGEUR,HAUTEUR);
        //Texte
        g.setColor(new Color(0, 0, 0));
        int y = (HAUTEUR*3)+20;
        g.drawString("Place",LARGEUR+30,((((y-metrics.getHeight())/2)+metrics.getAscent())+Y));

        //Rectangle Place Inutilisable
        g.setColor(new Color(130, 39, 32));
        g.fillRect(X,((((HAUTEUR)*2)+20)+Y),LARGEUR,HAUTEUR);
        //Texte
        g.setColor(new Color(0, 0, 0));
        y = (HAUTEUR*5)+40;
        g.drawString("Place Inutilisable",LARGEUR+30,((((y-metrics.getHeight())/2)+metrics.getAscent())+Y));


    }
}

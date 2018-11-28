package vue;

import javax.swing.plaf.basic.BasicTabbedPaneUI;
import java.awt.*;

public class TabbedPanDesign extends BasicTabbedPaneUI {
    private FontMetrics boldFontMetrics;
    private Font boldFont;

    /**
     * Cette méthode permet de changer la couleur de fond des onglets
     * @param g
     * @param tabPlacement
     * @param tabIndex
     * @param x
     * @param y
     * @param w
     * @param h
     * @param isSelected
     */
    protected void paintTabBackground(Graphics g, int tabPlacement,
                                      int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        Rectangle rect = new Rectangle();
        g.setColor(new Color(249, 250, 255));
        g.fillRect(x, y, w, h);
        if(isSelected) {
            g.setColor(new Color(196, 197, 198));
            g.fillRect(x, y, w, h+1);
        }
    }


    /**
     * Cette méthode permet de changer la bordure des onglets
     * @param g
     * @param tabPlacement
     * @param tabIndex
     * @param x
     * @param y
     * @param w
     * @param h
     * @param isSelected
     */
    protected void paintTabBorder(Graphics g, int tabPlacement,
                                  int tabIndex, int x, int y, int w, int h, boolean isSelected) {
        //Rectangle rect = getTabBounds(tabIndex, new Rectangle(x, y, w, h));
        if(isSelected){
            g.setColor(new Color(0, 0, 0));
            g.drawLine(x,y,x+w,y);
            g.drawLine(x,y,x,y+h);
            g.drawLine(x+w,y,x+w,y+h);

        }else{
            g.setColor(new Color(19,19,19));
            g.drawRect(x, y, w, h);
        }

    }

    /**
     *  Cette méthode permet de supprimer la bordure d'un onglet lorsque ce dernier est sélectionné
     * @param g
     * @param tabPlacement
     * @param rects
     * @param tabIndex
     * @param iconRect
     * @param textRect
     * @param isSelected
     */
    protected void paintFocusIndicator(Graphics g, int tabPlacement, Rectangle[]
            rects, int tabIndex, Rectangle iconRect, Rectangle textRect, boolean isSelected) {
    }

    /**
     * Cette méthode permet de calculer la hauteur d'un onglet
     * @param tabPlacement
     * @param tabIndex
     * @param fontHeight
     * @return
     */
    protected int calculateTabHeight(int tabPlacement, int tabIndex, int fontHeight) {
        int vHeight = fontHeight;
        if (vHeight % 2 > 0)
            vHeight += 2;
        return vHeight;
    }

    /**
     * Cette méthode permet de calculer la largeur d'un onglet
     * @param tabPlacement
     * @param tabIndex
     * @param metrics
     * @return
     */
    protected int calculateTabWidth(int tabPlacement, int tabIndex, FontMetrics
            metrics){
        return super.calculateTabWidth(tabPlacement, tabIndex, metrics) +
                metrics.getHeight()+15;
    }

    /**
     * Cette méthode permet de centrer le texte dans l'onglet
     * @param tabPlacement
     * @param tabIndex
     * @param isSelected
     * @return
     */
    protected int getTabLabelShiftY(int tabPlacement,int tabIndex,boolean isSelected) {
        return 0;
    }

    /**
     * Cette méthode permet l'insertion d'une marge entre l'onglet et le contenu
     * @param tabPlacement
     * @return
     */
    protected Insets getContentBorderInsets(int tabPlacement) {
        return new Insets(0,0,0,0);
    }

    /**
     * Permet de choisir la couleur du texte ainsi que sa police
     * @param g
     * @param tabPlacement
     * @param font
     * @param metrics
     * @param tabIndex
     * @param title
     * @param textRect
     * @param isSelected
     */
    protected void paintText(Graphics g, int tabPlacement, Font font, FontMetrics
            metrics, int tabIndex, String title, Rectangle textRect, boolean isSelected) {
        if (isSelected) {
            int vDifference = (int)(boldFontMetrics.getStringBounds(title,g).getWidth())
                    - textRect.width;
            textRect.x -= (vDifference / 2);
            super.paintText(g, tabPlacement, boldFont, boldFontMetrics, tabIndex,
                    title, textRect, isSelected);
        }
        else

            super.paintText(g, tabPlacement, font, metrics, tabIndex, title, textRect,
                    isSelected);
    }

    /**
     * Permet la définition de point précis
     */
    protected void installDefaults() {
        super.installDefaults();
        tabAreaInsets.left = -1;
        tabAreaInsets.top = 0;
        selectedTabPadInsets = new Insets(0, 0, 0, 0);
        tabInsets = selectedTabPadInsets;
        boldFont = tabPane.getFont().deriveFont(Font.BOLD);
        boldFontMetrics = tabPane.getFontMetrics(boldFont);
    }

    /**
     * Permettent de redéfinir la bordure du haut des onglets.
     */
    protected void paintContentBorderTopEdge(Graphics g, int tabPlacement, int
            selectedIndex, int x, int y, int w, int h) {
    }

    /**
     * Permettent de redéfinir la bordure de droite des onglets.
     */
    protected void paintContentBorderRightEdge(Graphics g, int tabPlacement,int
            selectedIndex, int x, int y, int w, int h) {
    }

    /**
     * Permettent de redéfinir la bordure de gauche des onglets.
     */
    protected void paintContentBorderLeftEdge(Graphics g, int tabPlacement,int
            selectedIndex, int x, int y, int w, int h) {
    }

    /**
     * Permettent de redéfinir la bordure du bas des onglets.
     */
    protected void paintContentBorderBottomEdge(Graphics g, int tabPlacement,int
            selectedIndex, int x, int y, int w, int h) {
    }












}

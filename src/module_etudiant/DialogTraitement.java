package module_etudiant;

import modele.BDD.Salle;

import javax.swing.*;
import java.awt.*;

public class DialogTraitement extends JDialog {

    public JProgressBar progress;
    public Object objet;

    public DialogTraitement(JFrame parent, String title, boolean modal){
        super(parent,title,modal);
        this.objet = objet;
        this.setSize(new Dimension(200,80));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();
    }

    private void initComponent(){
        JPanel container = new JPanel();
        this.progress = new JProgressBar();
        this.progress.setIndeterminate(true);
        this.getContentPane().setLayout(new BorderLayout());
        container.add(progress);
        this.getContentPane().add(container,BorderLayout.CENTER);
    }



    public void afficherDialog(){
        this.setVisible(true);
    }

    public void close(){
        this.setVisible(false);
    }
}

package controleur;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModificationNomPlaceDialog extends JDialog {
    private boolean sendData;
    private ModificationNomPlaceDialogInfo dialogInfo;
    private JLabel labelNomPlace,labelOldPlaceName;
    private JTextField nomPlace;
    private JButton valider,annuler;

    public ModificationNomPlaceDialog(JFrame parent, String title, boolean modal,String oldName){
        super(parent,title,modal);

        this.setSize(new Dimension(300,130));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent(oldName);
    }

    private void initComponent(String oldName){
        JPanel container = new JPanel();
        container.setLayout(new GridBagLayout());

        //Création des contraintes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.insets = new Insets(0,0,5,0);


        //JLabel
        this.labelOldPlaceName = new JLabel("Nom actuel :  "+oldName);
        container.add(this.labelOldPlaceName,gbc);

        this.labelNomPlace = new JLabel("Nouveau nom : ");
        gbc.gridx=0;
        gbc.gridy=1;
        container.add(this.labelNomPlace,gbc);

        this.nomPlace = new JTextField();
        this.nomPlace.setPreferredSize(new Dimension(100,20));
        gbc.gridx=1;
        gbc.gridy=1;
        gbc.gridwidth = 3;
        container.add(this.nomPlace,gbc);



        //JButton
        this.valider = new JButton("Valider");
        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogInfo = new ModificationNomPlaceDialogInfo(nomPlace.getText());
                setVisible(false);
            }
        });
        this.annuler = new JButton("Annuler");
        this.annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogInfo = new ModificationNomPlaceDialogInfo(oldName);
                setVisible(false);
            }
        });


        JPanel boutons = new JPanel();
        boutons.setLayout(new GridLayout(1,2));
        boutons.add(this.valider);
        boutons.add(this.annuler);
        this.getContentPane().add(container,BorderLayout.CENTER);
        this.getContentPane().add(boutons,BorderLayout.SOUTH);
    }

    public ModificationNomPlaceDialogInfo afficherDialog(){
        this.sendData =false;
        this.setVisible(true);
        return this.dialogInfo;
    }
}

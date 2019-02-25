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

        this.setSize(new Dimension(550,270));
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
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.insets = new Insets(0,0,5,0);
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;

        //JLabel
        this.labelOldPlaceName = new JLabel("Nom actuel : "+oldName);
        container.add(this.labelOldPlaceName,gbc);

        this.labelNomPlace = new JLabel("Nouveau nom : ");
        gbc.gridy=1;
        gbc.insets = new Insets(0,0,0,5);
        container.add(this.labelNomPlace);

        this.nomPlace = new JTextField();
        gbc.gridx=1;
        gbc.insets = new Insets(0,0,0,0);
        container.add(this.nomPlace,gbc);

        JPanel global= new JPanel();
        global.setLayout(new BorderLayout());
        global.add(container,BorderLayout.CENTER);

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
                setVisible(false);
            }
        });

        global.add(this.valider,BorderLayout.SOUTH);
        global.add(this.annuler,BorderLayout.SOUTH);
    }

    public ModificationNomPlaceDialogInfo afficherDialog(){
        this.sendData =false;
        this.setVisible(true);
        return this.dialogInfo;
    }
}

package module_etudiant;

import modele.BDD.Categorie;
import modele.BDD.Groupe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DialogCreerGroupe extends JDialog {

    private boolean sendData;
    private JLabel labNom,labCategorie;
    private JTextField textNomGroupe;
    private JComboBox<Categorie> comboCategorie;
    private JButton valider,annuler;
    private boolean modification;

    public DialogCreerGroupe(JFrame parent, String title, boolean modal,boolean modification){
        super(parent,title,modal);
        this.modification = modification;
        this.setSize(new Dimension(550,270));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();

    }

    private void initComponent(){
        JPanel containerInfo = new JPanel();
        containerInfo.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.insets = new Insets(5,0,5,5);


        this.labNom = new JLabel("Nom du groupe : ");
        containerInfo.add(this.labNom,gbc);

        gbc.gridx=1;
        this.textNomGroupe = new JTextField();
        this.textNomGroupe.setPreferredSize(new Dimension(VueModuleEtudiant.TEXTFIELD_WIDTH,VueModuleEtudiant.TEXTFIELD_HEIGHT));

        containerInfo.add(this.textNomGroupe,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        this.labCategorie = new JLabel("Mettre dans catégorie : ");
        containerInfo.add(this.labCategorie,gbc);

        gbc.gridx=1;
        ArrayList<Categorie> categories = Categorie.getlistCategorie();
        this.comboCategorie = new JComboBox<Categorie>((Categorie[])categories.toArray(new Categorie[categories.size()]));
        this.comboCategorie.setSelectedIndex(0);
        this.comboCategorie.setPreferredSize(new Dimension(VueModuleEtudiant.COMBOBOX_WIDTH,VueModuleEtudiant.COMBOBOX_HEIGHT));
        containerInfo.add(this.comboCategorie,gbc);




        JPanel boutons = new JPanel();
        boutons.setLayout(new GridBagLayout());

        gbc =new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy=0;
        gbc.insets = new Insets(10,5,10,0);

        this.valider = new JButton("Valider");
        boutons.add(this.valider,gbc);

        this.annuler = new JButton("Annuler");
        gbc.gridx=1;
        boutons.add(this.annuler,gbc);


        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Groupe groupe = new Groupe(textNomGroupe.getText());
                groupe.save();

                Categorie categorie = (Categorie)comboCategorie.getSelectedItem();
                categorie.ajouterGroupe(groupe);
            }
        });

        this.annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });


        this.getContentPane().add(containerInfo, BorderLayout.CENTER);
        this.getContentPane().add(boutons,BorderLayout.SOUTH);



    }

    public void afficherDialog(){
        this.sendData =false;
        this.setVisible(true);
    }
}

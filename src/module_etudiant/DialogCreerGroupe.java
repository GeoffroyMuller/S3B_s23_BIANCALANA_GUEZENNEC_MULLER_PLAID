package module_etudiant;

import modele.BDD.Categorie;
import modele.BDD.Groupe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DialogCreerGroupe extends JDialog {

    private JLabel labNom,labCategorie;
    private JTextField textNomGroupe;
    private JComboBox<Categorie> comboCategorie;
    private JButton valider,annuler,supprimer;
    private boolean modification;
    private Groupe groupe;

    /**
     * Permet de créer une boîte de dialogue pour permettre la création d'un nouveau groupe
     * @param parent
     * @param title
     * @param modal
     * @param modification
     */
    public DialogCreerGroupe(JFrame parent, String title, boolean modal,boolean modification){
        super(parent,title,modal);
        this.modification = modification;
        this.setSize(new Dimension(550,270));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();

    }

    /**
     * Permet de créer une boîte de dialogue pour permettre la modification d'un groupe existant
     * @param parent
     * @param title
     * @param modal
     * @param modification
     * @param groupe
     */
    public DialogCreerGroupe(JFrame parent, String title, boolean modal,boolean modification,Groupe groupe){
        super(parent,title,modal);
        this.modification = modification;
        this.groupe = groupe;
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
        if(modification){
            this.textNomGroupe.setText(this.groupe.getNom());
        }
        this.textNomGroupe.setPreferredSize(new Dimension(VueModuleEtudiant.TEXTFIELD_WIDTH,VueModuleEtudiant.TEXTFIELD_HEIGHT));

        containerInfo.add(this.textNomGroupe,gbc);

        gbc.gridx=0;
        gbc.gridy=1;
        this.labCategorie = new JLabel("Mettre dans catégorie : ");
        containerInfo.add(this.labCategorie,gbc);

        gbc.gridx=1;
        ArrayList<Categorie> categories = Categorie.getlistCategorie();
        this.comboCategorie = new JComboBox<Categorie>((Categorie[])categories.toArray(new Categorie[categories.size()]));
        if(modification){
            Categorie categorieDuGroupe = this.groupe.getCategorie();
            this.comboCategorie.setSelectedIndex(recupererIndexDansCombo(categorieDuGroupe));
        }else{
            this.comboCategorie.setSelectedIndex(0);
        }
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

        if(modification) {
            this.supprimer = new JButton("Supprimer");
            gbc.gridx = 2;
            boutons.add(this.supprimer, gbc);

            this.supprimer.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int dialogButton = JOptionPane.YES_NO_OPTION;
                    int dialogResult = JOptionPane.showConfirmDialog (null, "Êtes-vous sur de vouloir supprimer le groupe : "+groupe.getNom(),"Confirmation de suppression",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        groupe.delete();
                    }
                    setVisible(false);
                }
            });
        }


        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modification){
                    groupe.setNom(textNomGroupe.getText());
                    groupe.save();

                    Categorie categorie = (Categorie)comboCategorie.getSelectedItem();
                    Categorie ancienneCategorie = groupe.getCategorie();
                    if(!categorie.getNom().equals(ancienneCategorie.getNom())){
                        ancienneCategorie.enleverUnGroupe(groupe);
                        categorie.ajouterGroupe(groupe);
                    }
                }else {
                    Groupe groupe = new Groupe(textNomGroupe.getText());
                    groupe.save();

                    Categorie categorie = (Categorie) comboCategorie.getSelectedItem();
                    categorie.ajouterGroupe(groupe);
                }
                setVisible(false);
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

    /**
     * Permet l'affichage de la boîte de dialogue
     */
    public void afficherDialog(){
        this.setVisible(true);
    }


    private int recupererIndexDansCombo(Categorie categorie){
        int res=0;
        for(int i = 0; i < comboCategorie.getItemCount();i++){
            if(comboCategorie.getItemAt(i).getNom().equals(categorie.getNom())){
                res=i;
                break;
            }
        }
        return res;
    }
}

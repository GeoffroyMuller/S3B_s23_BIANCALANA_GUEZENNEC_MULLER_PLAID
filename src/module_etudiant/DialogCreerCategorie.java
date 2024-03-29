package module_etudiant;

import modele.BDD.Categorie;
import modele.BDD.Salle;
import vue.CreationSalleDialogInfos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogCreerCategorie extends JDialog {

    private JLabel labNom;
    private JTextField textNomCategorie;
    private JButton valider,annuler,supprimer;
    private boolean modification;
    private Categorie categorie;

    /**
     * Permet la cr�ation d'une bo�te de dialogue pour la cr�ation d'une nouvelle cat�gorie
     * @param parent
     * @param title
     * @param modal
     * @param modification
     */
    public DialogCreerCategorie(JFrame parent, String title, boolean modal,boolean modification){
        super(parent,title,modal);
        this.modification = modification;
        this.setSize(new Dimension(550,270));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();

    }

    /**
     * Permet la cr�ation d'une bo�te de dialogue pour la modification d'une cat�gorie existante
     * @param parent
     * @param title
     * @param modal
     * @param modification
     * @param categorie
     */
    public DialogCreerCategorie(JFrame parent, String title, boolean modal,boolean modification,Categorie categorie){
        super(parent,title,modal);
        this.categorie = categorie;
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

        this.labNom = new JLabel("Nom de la cat�gorie : ");
        containerInfo.add(this.labNom,gbc);

        gbc.gridx=1;
        this.textNomCategorie = new JTextField();
        if(modification){
            this.textNomCategorie.setText(this.categorie.getNom());
        }
        this.textNomCategorie.setPreferredSize(new Dimension(VueModuleEtudiant.TEXTFIELD_WIDTH,VueModuleEtudiant.TEXTFIELD_HEIGHT));

        containerInfo.add(this.textNomCategorie,gbc);

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
                    int dialogResult = JOptionPane.showConfirmDialog (null, "�tes-vous sur de vouloir supprimer la cat�gorie : "+categorie.getNom(),"Confirmation de suppression",dialogButton);
                    if(dialogResult == JOptionPane.YES_OPTION){
                        categorie.delete();
                    }
                    setVisible(false);
                }
            });
        }

        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(modification){

                    categorie.setNom(textNomCategorie.getText());
                    verifierLeNouveauNom(categorie);
                }else{
                    Categorie categorieNouvelle = new Categorie(textNomCategorie.getText());
                    verifierLeNouveauNom(categorieNouvelle);
                }

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
        this.setVisible(true);
    }

    public void verifierLeNouveauNom(Categorie categorie){
        if(categorie.checkUniciterNomCategorie()){
            categorie.save();
            setVisible(false);
        }else{
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null,"Une cat�gorie avec ce nom est d�ja pr�sente","Nom d�ja utilis�",JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

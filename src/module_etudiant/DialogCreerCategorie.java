package module_etudiant;

import modele.BDD.Categorie;
import modele.BDD.Salle;
import vue.CreationSalleDialogInfos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DialogCreerCategorie extends JDialog {

    private boolean sendData;
    private JLabel labNom;
    private JTextField textNomCategorie;
    private JButton valider,annuler;
    private boolean modification;

    public DialogCreerCategorie(JFrame parent, String title, boolean modal,boolean modification){
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

        this.labNom = new JLabel("Nom de la catégorie : ");
        containerInfo.add(this.labNom,gbc);

        gbc.gridx=1;
        this.textNomCategorie = new JTextField();
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


        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Categorie categorie = new Categorie(textNomCategorie.getText());
                if(categorie.checkUniciterNomCategorie()){
                    categorie.save();
                    setVisible(false);
                }else{
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null,"Une catégorie avec ce nom est déja présente","Nom déja utilisé",JOptionPane.INFORMATION_MESSAGE);
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
        this.sendData =false;
        this.setVisible(true);
    }
}

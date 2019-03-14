package module_etudiant;

import modele.BDD.Categorie;
import modele.GestionFichiersExcel.ImportEtudiant;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

public class DialogImportExcel extends JDialog {

    private boolean sendData;
    private JLabel labChoixFichier,labelCategorie;
    private JComboBox<Categorie> comboCategorie;
    private FileChooserImport fileChooser;
    private JButton valider,annuler;

    public DialogImportExcel(JFrame parent, String title, boolean modal){
        super(parent,title,modal);
        this.setSize(new Dimension(400,170));
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

        gbc.insets = new Insets(10,10,10,0);

        fileChooser = new FileChooserImport();
       containerInfo.add(fileChooser,gbc);

        gbc.gridy=1;
        gbc.gridx=0;
        this.labelCategorie = new JLabel("Choix d'une catégorie : ");
        containerInfo.add(this.labelCategorie,gbc);

        gbc.gridx=1;
        ArrayList<Categorie> categories = Categorie.getlistCategorie();
        this.comboCategorie = new JComboBox<Categorie>((Categorie[])categories.toArray(new Categorie[categories.size()]));
        this.comboCategorie.setPreferredSize(new Dimension(120,VueModuleEtudiant.TEXTFIELD_HEIGHT));
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
                ImportEtudiant ie = new ImportEtudiant(fileChooser.getFilePath(), "Feuil1", (Categorie)comboCategorie.getSelectedItem());
                ie.importerEtudiant();
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null,"Importation réussie !","Réussite !",JOptionPane.INFORMATION_MESSAGE);

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

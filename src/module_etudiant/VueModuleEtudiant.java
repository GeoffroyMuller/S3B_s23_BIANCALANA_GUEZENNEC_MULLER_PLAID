package module_etudiant;

import modele.BDD.Categorie;
import modele.BDD.Etudiant;
import modele.BDD.Groupe;
import modele.BDD.Particularite;
import vue_Examen.VueExamen;
import modele.CritereRechercheEtudiant;

import javax.swing.*;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

/**
 * Classe VueModuleEtudiant
 */
public class VueModuleEtudiant extends Observable {

    public static int TEXTFIELD_WIDTH = 100;
    public static int TEXTFIELD_HEIGHT = 20;
    public static int COMBOBOX_WIDTH = 120;
    public static int COMBOBOX_HEIGHT = 20;

    /**
     * Ensemble des combobox du module étudiant
     */
    private JComboBox combocategorie,combogroupe;
    /**
     * Ensemble des JtextField pour rentrer les renseignements de l'étudiant
     */
    private JTextField textnom,textprenom;
    /**
     * Ensembles des JLabel du module étudiant
     */
    private JLabel labCategorie, labGroupe, labNom, labPrenom, labelModifCateg, labelModifGroupe;
    /**
     * JTable permettant d'afficher les informations des étudiants
     */
    private JTable etudiants;
    /**
     * Ensembles des JButton du module étudiant
     */
    private JButton buttonCreeEtudiant, buttonCreeGroupe, buttonCreeCategorie,buttonRechercher, boutonImporter;
    /**
     * Permet de contenir les données de la combobox pour les groupes
     */
    private ArrayList<Groupe> donneeComboGroupe;
    /**
     * En tête de laJTable
     */
    private String[] columnJTable={"Nom","Prénom","Groupe","Situation de Handicap","Tier-Temps","Prise en compte","ID"};
    /**
     * JScrollPan pour contenir la JTable
     */
    private JScrollPane js;

    private JPanel mainModuleEtudiant;

    private CritereRechercheEtudiant rechercheCourante;

    /**
     * Permet d'initialiser le module étudiant
     */
    public VueModuleEtudiant(){
        this.rechercheCourante = new CritereRechercheEtudiant(null,null);
        this.mainModuleEtudiant= new JPanel();
        //TOPBAR
        JPanel topbar = new JPanel();
        topbar.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,20,10,0);
        this.labCategorie = new JLabel("Catégorie : ");
        topbar.add(this.labCategorie,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        ArrayList<Categorie> categories = Categorie.getlistCategorie();
        this.combocategorie = new JComboBox<Categorie>();
        this.combocategorie.addItem("Toutes les catégories");

        for(Categorie categ : categories){
            combocategorie.addItem(categ);
        }

        topbar.add(this.combocategorie,gbc);
        this.combocategorie.setSelectedIndex(0);
        this.combocategorie.setPreferredSize(new Dimension(TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT));

        gbc.gridy = 1;
        gbc.insets = new Insets(-10,10,5,0);
        this.labelModifCateg = new JLabel("modifier la catégorie");
        this.labelModifCateg.setFont(new Font("Arial",Font.PLAIN,10));
        this.labelModifCateg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Font font = this.labelModifCateg.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        this.labelModifCateg.setFont(font.deriveFont(attributes));
        topbar.add(this.labelModifCateg,gbc);
        this.labelModifCateg.setForeground(new Color(0x0544ED));
        this.labelModifCateg.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    DialogCreerCategorie dialog = new DialogCreerCategorie(null, "Modification d'une catégorie", true, true, (Categorie) combocategorie.getSelectedItem());
                    dialog.afficherDialog();
                    
                    updateCombobox();
                    setChanged();
                    notifyObservers(VueExamen.VUE_CATEG_SAVE);
                }catch(ClassCastException exception){
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"Veuillez selectionner la catégorie à modifier","Message Informatif",JOptionPane.INFORMATION_MESSAGE);
            }
            }
        });


        gbc.gridy=0;
        gbc.insets = new Insets(10,20,10,0);

        this.combocategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(combocategorie.getSelectedItem() instanceof Categorie){
                    ArrayList<Groupe> groupesCateg = ((Categorie)combocategorie.getSelectedItem()).getListGroupe();
                    combogroupe.removeAllItems();
                    combogroupe.addItem("Tout les groupes");
                    for(Groupe group : groupesCateg){
                        combogroupe.addItem(group);
                    }
                    // combogroupe = new JComboBox<Groupe>((Groupe[])groupesCateg.toArray(new Groupe[groupesCateg.size()]));
                    try {
                        combogroupe.setSelectedIndex(0);
                    }catch(IllegalArgumentException exception){
                        JOptionPane jop = new JOptionPane();
                        jop.showMessageDialog(null,"La catégorie n'a pas de groupe actuellement. Ceci est un message à caractére informatif uniquement, il ne s'agit pas d'une erreur.","Message Informatif",JOptionPane.INFORMATION_MESSAGE);
                    }
                }

            }
        });

        gbc.gridx = 3;
        gbc.gridy = 0;
        this.labGroupe = new JLabel("Groupe : ");
        topbar.add(this.labGroupe,gbc);

        gbc.gridx = 4;
        ArrayList<Groupe> listGroupes = new ArrayList<Groupe>();//((Categorie)combocategorie.getItemAt(0)).getListGroupe();
        this.combogroupe = new JComboBox<Groupe>();
        this.combogroupe.addItem("Tout les groupes");
        for(Groupe groupe : listGroupes){
            this.combogroupe.addItem(groupe);
        }


        this.combogroupe.setSelectedIndex(0);
        this.combogroupe.setPreferredSize(new Dimension(TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT));
        topbar.add(this.combogroupe,gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(-10,10,5,0);
        this.labelModifGroupe = new JLabel("modifier le groupe");
        this.labelModifGroupe.setFont(new Font("Arial",Font.PLAIN,10));
        this.labelModifGroupe.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        font = this.labelModifGroupe.getFont();
        attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        this.labelModifGroupe.setFont(font.deriveFont(attributes));
        topbar.add(this.labelModifGroupe,gbc);
        this.labelModifGroupe.setForeground(new Color(0x0544ED));
        this.labelModifGroupe.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                try {
                    DialogCreerGroupe dialog = new DialogCreerGroupe(null, "Modification d'un groupe", true, true, (Groupe) combogroupe.getSelectedItem());
                    dialog.afficherDialog();
                    updateCombobox();
                    setChanged();
                    notifyObservers(VueExamen.VUE_CATEG_SAVE);
                }catch(ClassCastException exception){
                    JOptionPane jop = new JOptionPane();
                    jop.showMessageDialog(null,"Veuillez selectionner le groupe à modifier","Message Informatif",JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });


        gbc.gridy=0;
        gbc.insets = new Insets(10,20,10,0);



        gbc.gridx=5;
        this.labNom = new JLabel("Nom : ");
        topbar.add(this.labNom,gbc);

        gbc.gridx=6;
        this.textnom = new JTextField();
        this.textnom.setPreferredSize(new Dimension(TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT));
        topbar.add(this.textnom,gbc);

        gbc.gridx=7;
        this.labPrenom = new JLabel("Prénom : ");
        topbar.add(this.labPrenom,gbc);

        gbc.gridx=8;
        this.textprenom = new JTextField();
        this.textprenom.setPreferredSize(new Dimension(TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT));
        topbar.add(this.textprenom,gbc);

        gbc.gridx=9;
        this.buttonRechercher = new JButton("Rechercher");
        topbar.add(this.buttonRechercher,gbc);

        this.buttonRechercher.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rechercheCourante.setNom(null);
                rechercheCourante.setPrenom(null);
                String prenom = "";
                String nom = "";

                if(null != textnom.getText()){
                    nom = textnom.getText();
                }

                if(null != textprenom.getText()){
                    prenom = textprenom.getText();
                }

                ArrayList<Etudiant> etu = new ArrayList<Etudiant>();
               //HERE
                effectuerLaRecherche(nom,prenom);


            }
        });

        //CENTRE - VISUALISATION DES ETUDIANT
        JPanel centre = new JPanel();
        ArrayList<Etudiant> etudiants = Etudiant.listEtudiant();
        this.etudiants = new JTable(infosEtudiants(etudiants),columnJTable){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        this.etudiants = setupJTable(this.etudiants);





        js = new JScrollPane(this.etudiants);
        js.setPreferredSize(new Dimension(1000,650));
        js.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        js.setBackground(new Color(40, 73, 92));

        //Récupération de tout les étudiants
        ArrayList<Etudiant>listEtudiants = new ArrayList<Etudiant>();
        listEtudiants = Etudiant.listEtudiant();

        centre.add(js);
        //BOTTOM BAR
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        this.buttonCreeCategorie = new JButton("Creer une catégorie");
        gbc.gridy=0;
        gbc.gridx=0;
        gbc.insets = new Insets(10,5,10,0);
        bottomBar.add(this.buttonCreeCategorie,gbc);
        this.buttonCreeCategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCreerCategorie dialog = new DialogCreerCategorie(null,"Création d'une catégorie",true,false);
                dialog.afficherDialog();
                updateCombobox();
                setChanged();
                notifyObservers(VueExamen.VUE_CATEG_SAVE);
            }
        });

        this.buttonCreeEtudiant = new JButton("Créer étudiant");
        this.buttonCreeEtudiant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCreerEtudiant dialog = new DialogCreerEtudiant(null,"Création d'un étudiant",true,false);
                dialog.afficherDialog();
                String nom = "";
                String prenom = "";
                if(null != rechercheCourante.getNom()){
                    nom = rechercheCourante.getNom();
                }

                if(null != rechercheCourante.getPrenom()){
                    prenom = rechercheCourante.getPrenom();
                }

                effectuerLaRecherche(nom,prenom);

                setChanged();
                notifyObservers(VueExamen.VUE_CATEG_SAVE);

            }
        });
        gbc.gridx=1;
        bottomBar.add(this.buttonCreeEtudiant,gbc);

        this.buttonCreeGroupe = new JButton("Créer groupe");
        gbc.gridx=2;
        bottomBar.add(this.buttonCreeGroupe,gbc);
        this.buttonCreeGroupe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCreerGroupe dialog = new DialogCreerGroupe(null,"Création d'un groupe",true,false);
                dialog.afficherDialog();
                updateCombobox();
                setChanged();
                notifyObservers(VueExamen.VUE_CATEG_SAVE);
            }
        });

        this.boutonImporter = new JButton("Importer une liste Excel");
        gbc.gridx=3;
        bottomBar.add(this.boutonImporter,gbc);

        this.boutonImporter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogImportExcel dialog = new DialogImportExcel(null,"Importer une liste",true);
                dialog.afficherDialog();
                updateCombobox();
                effectuerLaRecherche("","");

                setChanged();
                notifyObservers(VueExamen.VUE_CATEG_SAVE);
            }
        });
        topbar.setBackground(new Color(236, 241, 245));
        bottomBar.setBackground(new Color(236, 241, 245));
        centre.setBackground(new Color(40, 73, 92));
        this.mainModuleEtudiant.setLayout(new BorderLayout());
        this.mainModuleEtudiant.add(topbar,BorderLayout.NORTH);
        this.mainModuleEtudiant.add(centre,BorderLayout.CENTER);
        this.mainModuleEtudiant.add(bottomBar,BorderLayout.SOUTH);


    }

    /**
     * Permet de récuperer les informations des étudiants en parametres sous la forme d'un tableau à double entree pour les JTable
     * @param etudiants
     *  ArrayList
     * @return
     */
    private String[][] infosEtudiants(ArrayList<Etudiant> etudiants){
        String[][] res = new String[etudiants.size()][7];
        for(int i = 0; i < etudiants.size();i++){
            Etudiant etudiant = etudiants.get(i);
            res[i][0] = etudiant.getNom();
            res[i][1] = etudiant.getPrenom();
            res[i][2] = etudiant.recupererToutLesNomDeGroupe();
            res[i][3] = "NON";
            res[i][4] = "NON";
            res[i][5] = "OUI";
            res[i][6] = etudiant.getIdEtu()+"";

            ArrayList<Particularite> particularites = etudiant.getParticularites();

            for(Particularite particularite : particularites){
                if(particularite.getNom().equals("Situation de handicap (Prise en compte)")){
                    res[i][3] = "OUI";
                }

                if(particularite.getNom().equals("Tiers-Temps (Prendre en compte)")){
                    res[i][4] = "OUI";

                }

                if(particularite.getNom().equals("Tiers-Temps (Non pris en compte)")){
                    res[i][4] = "OUI";
                    res[i][5] = "NON";
                }

                if(particularite.getNom().equals("Situation de handicap (Non pris en compte)")){
                    res[i][3] = "OUI";
                    res[i][5] = "NON";

                }
            }

        }

        return res;
    }

    public JPanel getJPanel(){
        return this.mainModuleEtudiant;
    }


    /**
     * Permet de mettre à jour les ComboBox lors de l'ajout d'un groupe ou d'une categorie
     */
    private void updateCombobox(){
        //ComboGroupe
        this.combogroupe.removeAllItems();
        for(Groupe groupe : Groupe.listGroupe()){
            this.combogroupe.addItem(groupe);
        }



        //Combocategorie
        this.combocategorie.removeAllItems();
        for(Categorie categorie : Categorie.getlistCategorie()){
            this.combocategorie.addItem(categorie);
        }
    }

    /**
     * Permet de Setup les JTable avec les parametres voulus
     * @param jTable
     * @return
     */
    private JTable setupJTable(JTable jTable){
        ArrayList<Etudiant> etudiants = Etudiant.listEtudiant();
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    String nom = (String)table.getValueAt(table.getSelectedRow(),0);
                    String prenom = (String)table.getValueAt(table.getSelectedRow(),1);
                    String groupe = (String)table.getValueAt(table.getSelectedRow(),2);
                    String handicap = (String)table.getValueAt(table.getSelectedRow(),3);
                    String tier = (String)table.getValueAt(table.getSelectedRow(),4);
                    String priseEnCompte = (String)table.getValueAt(table.getSelectedRow(),5);
                    int id = Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),6));

                    CritereRechercheEtudiant cre = new CritereRechercheEtudiant(id,nom,prenom,groupe,handicap,tier,priseEnCompte);
                    DialogCreerEtudiant dialog = new DialogCreerEtudiant(null,"Modification d'un étudiant",true,true,cre);
                    dialog.afficherDialog();
                    String nomRecherche="";
                    String prenomRecherche = "";
                    if(!(null == rechercheCourante.getNom())){
                        nomRecherche = rechercheCourante.getNom();
                    }

                    if(!(null == rechercheCourante.getPrenom())){
                        prenomRecherche = rechercheCourante.getPrenom();
                    }
                    effectuerLaRecherche(nomRecherche,prenomRecherche);
                    setChanged();
                    notifyObservers();
                }
            }
        });
        jTable.setBounds(30,40,500,700);
        jTable.setFont(new Font("Arial",Font.PLAIN,15));
        jTable.setRowHeight(20);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = this.etudiants.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }
        jTable.setAutoCreateRowSorter(true);
        return jTable;
    }

    private void effectuerLaRecherche(String nom,String prenom){
        ArrayList<Etudiant> etu = new ArrayList<Etudiant>();
        if(!prenom.isEmpty() || prenom.length() != 0){
            System.out.println("Prneom OK");

            if(!nom.isEmpty() || nom.length() != 0){
                //On recherche un étudiant ayant le nom y et le prenom x dans le groupe z
                rechercheCourante.setNom(nom);
                rechercheCourante.setPrenom(prenom);
                if(combogroupe.getSelectedItem() instanceof Groupe){
                    Groupe groupe = (Groupe)combogroupe.getSelectedItem();
                    etu = groupe.rechercherEtudiant(nom,prenom);
                }else if(combocategorie.getSelectedItem() instanceof Categorie){
                    Categorie categorie = (Categorie)combocategorie.getSelectedItem();
                    for(Groupe groupeDeLaCateg : categorie.getListGroupe()){
                        etu.addAll(groupeDeLaCateg.rechercherEtudiant(nom,prenom));
                    }
                }else{
                    //Recherche par nom et prenom sans groupe ni categorie
                    etu = Etudiant.rechercherEtudiant(nom,prenom);
                }

            }else{
                //Recherche que par prenom
                rechercheCourante.setPrenom(prenom);
                if(combogroupe.getSelectedItem() instanceof Groupe) {
                    Groupe groupe = (Groupe)combogroupe.getSelectedItem();
                    etu = groupe.rechercherEtudiant(null, prenom);
                }else if(combocategorie.getSelectedItem() instanceof Categorie){
                    Categorie categorie = (Categorie)combocategorie.getSelectedItem();
                    for(Groupe groupeDeLaCateg : categorie.getListGroupe()){
                        etu.addAll(groupeDeLaCateg.rechercherEtudiant(null,prenom));
                    }
                }else{
                    etu = Etudiant.rechercherEtudiant(null,prenom);

                }
            }
        }else if(!nom.isEmpty() || nom.length() != 0){
            System.out.println("Nom OK");

            //Recherche que par nom
            rechercheCourante.setNom(nom);
            if(combogroupe.getSelectedItem() instanceof Groupe) {
                Groupe groupe = (Groupe)combogroupe.getSelectedItem();
                etu = groupe.rechercherEtudiant(nom, null);
            }else if(combocategorie.getSelectedItem() instanceof Categorie){
                Categorie categorie = (Categorie)combocategorie.getSelectedItem();
                for(Groupe groupeDeLaCateg : categorie.getListGroupe()){
                    etu.addAll(groupeDeLaCateg.rechercherEtudiant(nom,null));
                }
            }else{
                etu = Etudiant.rechercherEtudiant(nom,null);
            }
        }else{
            //Recherche tout le groupe
            if(combogroupe.getSelectedItem() instanceof Groupe) {
                Groupe groupe = (Groupe)combogroupe.getSelectedItem();
                etu = groupe.getListeEtudiants();
            }else if(combocategorie.getSelectedItem() instanceof Categorie){
                Categorie categorie = (Categorie)combocategorie.getSelectedItem();
                for(Groupe groupeDeLaCateg : categorie.getListGroupe()){
                    etu.addAll(groupeDeLaCateg.getListeEtudiants());
                }
            }else{
                System.out.println("TOUT LES ETUDIANTS");
                etu = Etudiant.rechercherToutLesEtudiants();
            }
        }
        etudiants = new JTable(infosEtudiants(etu),columnJTable){
            private static final long serialVersionUID = 1L;

            public boolean isCellEditable(int row, int column) {
                return false;
            };
        };
        etudiants = setupJTable(etudiants);
        js.setViewportView(etudiants);
    }
}

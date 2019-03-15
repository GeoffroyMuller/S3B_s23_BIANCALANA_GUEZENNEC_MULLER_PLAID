package module_etudiant;

import modele.BDD.Categorie;
import modele.BDD.Etudiant;
import modele.BDD.Groupe;
import modele.BDD.Particularite;
import modele.CritereRechercheEtudiant;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.Map;
import java.util.Observable;

public class VueModuleEtudiant extends Observable {

    public static int TEXTFIELD_WIDTH = 100;
    public static int TEXTFIELD_HEIGHT = 20;
    public static int COMBOBOX_WIDTH = 120;
    public static int COMBOBOX_HEIGHT = 20;

    private JComboBox combocategorie,combogroupe;
    private JTextField textnom,textprenom;
    private JLabel labCategorie, labGroupe, labNom, labPrenom;
    private JTable etudiants;
    private JButton buttonCreeEtudiant, buttonCreeGroupe, buttonCreeCategorie,buttonRechercher, boutonImporter;
    private ArrayList<Groupe> donneeComboGroupe;
    private String[] columnJTable={"Nom","Pr�nom","Groupe","Situation de Handicap","Tier-Temps","Prise en compte","ID"};
    private JLabel labelModifCateg, labelModifGroupe;

    private JPanel mainModuleEtudiant;

    public VueModuleEtudiant(){
        this.mainModuleEtudiant= new JPanel();
        //TOPBAR
        JPanel topbar = new JPanel();
        topbar.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,20,10,0);
        this.labCategorie = new JLabel("Cat�gorie : ");
        topbar.add(this.labCategorie,gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        ArrayList<Categorie> categories = Categorie.getlistCategorie();
        this.combocategorie = new JComboBox<Categorie>((Categorie[])categories.toArray(new Categorie[categories.size()]));
        topbar.add(this.combocategorie,gbc);
        this.combocategorie.setSelectedIndex(0);
        this.combocategorie.setPreferredSize(new Dimension(TEXTFIELD_WIDTH,TEXTFIELD_HEIGHT));

        gbc.gridy = 1;
        gbc.insets = new Insets(-10,10,5,0);
        this.labelModifCateg = new JLabel("modifier la cat�gorie");
        this.labelModifCateg.setFont(new Font("Arial",Font.PLAIN,10));
        this.labelModifCateg.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        Font font = this.labelModifCateg.getFont();
        Map attributes = font.getAttributes();
        attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        this.labelModifCateg.setFont(font.deriveFont(attributes));
        topbar.add(this.labelModifCateg,gbc);
        this.labelModifCateg.setForeground(new Color(0x0544ED));


        gbc.gridy=0;
        gbc.insets = new Insets(10,20,10,0);

        this.combocategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                donneeComboGroupe = ((Categorie)combocategorie.getSelectedItem()).getListGroupe();
                combogroupe.removeAllItems();
                combogroupe = new JComboBox<Groupe>((Groupe[])donneeComboGroupe.toArray());
                combogroupe.setSelectedIndex(0);
            }
        });

        gbc.gridx = 3;
        gbc.gridy = 0;
        this.labGroupe = new JLabel("Groupe : ");
        topbar.add(this.labGroupe,gbc);

        gbc.gridx = 4;
        ArrayList<Groupe> listGroupes = ((Categorie)combocategorie.getItemAt(0)).getListGroupe();
        this.combogroupe = new JComboBox<Groupe>((Groupe[])listGroupes.toArray(new Groupe[listGroupes.size()]));
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
        this.labPrenom = new JLabel("Pr�nom : ");
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
                Groupe groupe = (Groupe)combogroupe.getSelectedItem();
                String prenom = textprenom.getText();
                String nom = textnom.getText();

                if(prenom.length() != 0){
                    if(nom.length() != 0){
                        //On recherche un �tudiant ayant le nom y et le prenom x dans le groupe z
                        ArrayList<Etudiant> etu = groupe.rechercherEtudiant(nom,prenom);
                        etudiants = new JTable(infosEtudiants(etu),columnJTable);
                    }else{
                        //Recherche que par prenom
                        ArrayList<Etudiant> etu = groupe.rechercherEtudiant(null,prenom);
                        etudiants = new JTable(infosEtudiants(etu),columnJTable);
                    }
                }else if(nom.length() != 0){
                    //Recherche que par nom
                    ArrayList<Etudiant> etu = groupe.rechercherEtudiant(nom,null);
                    etudiants = new JTable(infosEtudiants(etu),columnJTable);
                }else{
                    //Recherche tout le groupe
                    ArrayList<Etudiant> etu = groupe.getListeEtudiants();
                    etudiants = new JTable(infosEtudiants(etu),columnJTable);
                }
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
        this.etudiants.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                JTable table =(JTable) e.getSource();
                Point point = e.getPoint();
                int row = table.rowAtPoint(point);
                if (e.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    System.out.println("YES"+table.getSelectedRow());
                    String nom = (String)table.getValueAt(table.getSelectedRow(),0);
                    String prenom = (String)table.getValueAt(table.getSelectedRow(),1);
                    String groupe = (String)table.getValueAt(table.getSelectedRow(),2);
                    String handicap = (String)table.getValueAt(table.getSelectedRow(),3);
                    String tier = (String)table.getValueAt(table.getSelectedRow(),4);
                    String priseEnCompte = (String)table.getValueAt(table.getSelectedRow(),5);
                    int id = Integer.parseInt((String)table.getValueAt(table.getSelectedRow(),6));

                    CritereRechercheEtudiant cre = new CritereRechercheEtudiant(id,nom,prenom,groupe,handicap,tier,priseEnCompte);
                    DialogCreerEtudiant dialog = new DialogCreerEtudiant(null,"Modification d'un �tudiant",true,true,cre);
                    dialog.afficherDialog();
                    setChanged();
                    notifyObservers();
                }
            }
        });
        this.etudiants.setBounds(30,40,500,700);
        this.etudiants.setFont(new Font("Arial",Font.PLAIN,15));
        this.etudiants.setRowHeight(20);
        TableColumn column = null;
        for (int i = 0; i < 5; i++) {
            column = this.etudiants.getColumnModel().getColumn(i);
            column.setPreferredWidth(150);
        }





        JScrollPane js = new JScrollPane(this.etudiants);
        js.setPreferredSize(new Dimension(1000,650));
        js.setBorder(BorderFactory.createEmptyBorder(20,0,0,0));
        js.setBackground(new Color(40, 73, 92));

        //R�cup�ration de tout les �tudiants
        ArrayList<Etudiant>listEtudiants = new ArrayList<Etudiant>();
        listEtudiants = Etudiant.listEtudiant();

        centre.add(js);
        //BOTTOM BAR
        JPanel bottomBar = new JPanel();
        bottomBar.setLayout(new GridBagLayout());

        gbc = new GridBagConstraints();

        this.buttonCreeCategorie = new JButton("Creer une cat�gorie");
        gbc.gridy=0;
        gbc.gridx=0;
        gbc.insets = new Insets(10,5,10,0);
        bottomBar.add(this.buttonCreeCategorie,gbc);
        this.buttonCreeCategorie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCreerCategorie dialog = new DialogCreerCategorie(null,"Cr�ation d'une cat�gorie",true,false);
                dialog.afficherDialog();
                setChanged();
                notifyObservers();
            }
        });

        this.buttonCreeEtudiant = new JButton("Cr�er �tudiant");
        this.buttonCreeEtudiant.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCreerEtudiant dialog = new DialogCreerEtudiant(null,"Cr�ation d'un �tudiant",true,false);
                dialog.afficherDialog();
                setChanged();
                notifyObservers();
            }
        });
        gbc.gridx=1;
        bottomBar.add(this.buttonCreeEtudiant,gbc);

        this.buttonCreeGroupe = new JButton("Cr�er groupe");
        gbc.gridx=2;
        bottomBar.add(this.buttonCreeGroupe,gbc);
        this.buttonCreeGroupe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DialogCreerGroupe dialog = new DialogCreerGroupe(null,"Cr�ation d'un groupe",true,false);
                dialog.afficherDialog();
                setChanged();
                notifyObservers();
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
                setChanged();
                notifyObservers();
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

    private String[][] infosEtudiants(ArrayList<Etudiant> etudiants){
        String[][] res = new String[etudiants.size()][7];
        for(int i = 0; i < etudiants.size();i++){
            Etudiant etudiant = etudiants.get(i);
            res[i][0] = etudiant.getNom();
            res[i][1] = etudiant.getPrenom();
            res[i][2] = etudiant.getGroupe();
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
}
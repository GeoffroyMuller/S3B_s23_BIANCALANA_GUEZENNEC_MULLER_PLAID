package vue;

import modele.BDD.Salle;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreationSalleDialog extends JDialog {
    private boolean sendData;
    private CreationSalleDialogInfos dialogInfo;
    private JLabel labelNomSalle,labelTailleHauteur,labelTailleLargeur,labelInfos;
    private JTextField nomSalle;
    private JSpinner hauteurSalle,largeurSalle;
    private JButton valider,annuler;
    private boolean modif;

    public CreationSalleDialog(JFrame parent, String title, boolean modal,boolean modification,Salle salle){
        super(parent,title,modal);
        modif = modification;
        this.setSize(new Dimension(550,270));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.initComponent();
        if(modification){
            this.nomSalle.setText(salle.getNom());
            this.hauteurSalle.setValue(salle.getNbCaseHauteur());
            this.largeurSalle.setValue(salle.getNbCaseLargeur());
        }

    }

    private void initComponent(){
        //Creation du panel contenent les informations à renseigner
        JPanel containerInfo = new JPanel();
        containerInfo.setLayout(new GridBagLayout());

        //Création des contraintes
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = gbc.gridheight = 1;
        gbc.insets = new Insets(0,10,5,10);
        gbc.anchor = GridBagConstraints.BASELINE_TRAILING;

        //Mise en place des composants
        this.labelNomSalle = new JLabel("Nom de la salle :");
        this.labelNomSalle.setFont(new Font("sans-serif",Font.PLAIN,14));
        containerInfo.add(this.labelNomSalle,gbc);


        this.labelTailleHauteur = new JLabel("Hauteur de la salle (Allées comprises) :");
        this.labelTailleHauteur.setFont(new Font("sans-serif",Font.PLAIN,14));

        gbc.gridy = 1;
        containerInfo.add(this.labelTailleHauteur,gbc);

        this.labelTailleLargeur = new JLabel(("Largeur de la salle (Allées comprises):"));
        this.labelTailleLargeur.setFont(new Font("sans-serif",Font.PLAIN,14));

        gbc.gridy = 2;
        containerInfo.add(this.labelTailleLargeur,gbc);

        gbc.gridy = 0;
        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.LINE_START;

        this.nomSalle = new JTextField();
        this.nomSalle.setPreferredSize(new Dimension(100,20));
        containerInfo.add(this.nomSalle,gbc);

        this.hauteurSalle = new JSpinner(new SpinnerNumberModel(0,0,9999999,1));
        gbc.gridy = 1;
        containerInfo.add(this.hauteurSalle,gbc);

        this.largeurSalle = new JSpinner(new SpinnerNumberModel(0,0,9999999,1));
        gbc.gridy = 2;
        containerInfo.add(this.largeurSalle,gbc);

        //Container boutons
        JPanel boutons = new JPanel();
        boutons.setLayout(new GridBagLayout());

        this.valider =new JButton("Valider");
        this.annuler = new JButton("Annuler");

        this.valider.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                        dialogInfo = new CreationSalleDialogInfos(nomSalle.getText(), (Integer) hauteurSalle.getValue(), (Integer) largeurSalle.getValue());
                        setVisible(false);

            }
        });

        this.annuler.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });

        gbc.gridx= gbc.gridy= 0;
        gbc.gridwidth = 1;

        boutons.add(this.valider,gbc);
        gbc.gridx = 1;
        boutons.add(this.annuler,gbc);

        this.getContentPane().add(containerInfo, BorderLayout.CENTER);
        this.getContentPane().add(boutons,BorderLayout.SOUTH);



    }

    public CreationSalleDialogInfos afficherDialog(){
        this.sendData =false;
        this.setVisible(true);
        return this.dialogInfo;
    }
}

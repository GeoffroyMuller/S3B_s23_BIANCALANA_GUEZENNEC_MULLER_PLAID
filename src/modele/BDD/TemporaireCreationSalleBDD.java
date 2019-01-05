package modele.BDD;

import modele.Examen;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class TemporaireCreationSalleBDD {
    public static void main(String[] args) {
        try {
            Connection bd = DBConnection.getConnection();

            Salle salle = new Salle("Salle_Test",20,20);
            salle.save();

            int idSalle = salle.getIdSalle();

            for(int i = 0; i < 20;i++){
                for(int j = 0; j < 20;j++){
                    Place place = new Place(i+""+j,i,j,salle.getIdSalle());
                    place.save();
                }
            }

            //On génére des étudiant du groupe A
            Groupe groupeA = new Groupe("A");
            for(int i = 0; i < 100;i++){
                Etudiant etudiant = new Etudiant("A","A");
            }

            //On génére des étudiant du groupe B
            Groupe groupeB = new Groupe("B");
            for(int i = 0; i < 100;i++){
                Etudiant etudiant = new Etudiant("B","B");
            }

            //On génére des étudiant du groupe C
            Groupe groupeC = new Groupe("C");
            for(int i = 0; i < 100;i++){
                Etudiant etudiant = new Etudiant("C","C");
            }

            groupeA.save();
            groupeB.save();
            groupeC.save();

            Examen examen = new Examen();

            examen.ajouterGroupe(groupeA);
            examen.ajouterGroupe(groupeB);
            examen.ajouterGroupe(groupeC);

            examen.genererUnPlacement();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void visualisation(HashMap<Place,Etudiant> placement){
        String[][] visu = new String[20][20];

        Set<Place> key = placement.keySet();
        Iterator it = key.iterator();

        while(it.hasNext()){
            Place cle = (Place)it.next();

            visu[cle.getI()][cle.getJ()] = placement.get(cle).getNom();
        }

        for(int i = 0; i < 20;i++){
            for(int j = 0; j<20;j++){
                if(null == visu[i][j]){
                    System.out.print(" " + 'V' + " ");
                }else {
                    System.out.print(" " + visu[i][j] + " ");
                }
            }
            System.out.print('\n');
        }
    }
}

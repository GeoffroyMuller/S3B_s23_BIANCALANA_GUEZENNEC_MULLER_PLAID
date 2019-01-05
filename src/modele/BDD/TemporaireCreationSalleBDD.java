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

            Salle salle = new Salle("Salle_Test2",10,10);
            salle.save();



            TypePlace typePlace = new TypePlace("Chaise",1);
            TypePlace typePlaceAllee = new TypePlace("Allee",0);

            typePlaceAllee.save();
            typePlace.save();

            int idSalle = salle.getIdSalle();

            for(int i = 0; i < 10;i++){
                for(int j = 0; j < 10;j++){
                    if(j == 7 || j == 4){
                        Place place = new Place(i+""+j,typePlaceAllee.getIdTypePlace(),i,j,0,salle.getIdSalle());
                        place.save();
                    }else{
                        Place place = new Place(i+""+j,typePlace.getIdTypePlace(),i,j,1,salle.getIdSalle());
                        place.save();
                    }

                }
            }

            //On génére des étudiant du groupe A
            Groupe groupeA = new Groupe("A");
            groupeA.save();

            for(int i = 0; i < 25;i++){
                Etudiant etudiant = new Etudiant("A","A");
                etudiant.save();
                EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiant.getIdEtu(), groupeA.getIdGroupe());
            }

            //On génére des étudiant du groupe B
            Groupe groupeB = new Groupe("B");
            groupeB.save();

            for(int i = 0; i < 25;i++){
                Etudiant etudiant = new Etudiant("B","B");
                etudiant.save();
                EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiant.getIdEtu(), groupeB.getIdGroupe());
            }

            //On génére des étudiant du groupe C
            Groupe groupeC = new Groupe("C");
            groupeC.save();
            for(int i = 0; i < 25;i++){
                Etudiant etudiant = new Etudiant("C","C");
                etudiant.save();
                EtudiantGroupe.ajouterEtudiantAUnGroupe(etudiant.getIdEtu(), groupeC.getIdGroupe());
            }


            Examen examen = new Examen();

            salle.getTableauPlaces(salle.getIdSalle());
            examen.ajouterSalle(salle);
            examen.ajouterGroupe(groupeA);
            examen.ajouterGroupe(groupeB);
            examen.ajouterGroupe(groupeC);

            System.out.println("Lancement du placement...");
            examen.genererUnPlacement();
            System.out.println("Placement généré !");

            visualisation(examen.getPlacement().get(salle));

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void visualisation(HashMap<Place,Etudiant> placement){
        String[][] visu = new String[10][10];

        Set<Place> key = placement.keySet();
        Iterator it = key.iterator();

        while(it.hasNext()){
            Place cle = (Place)it.next();

            visu[cle.getI()][cle.getJ()] = placement.get(cle).getNom();
        }

        for(int i = 0; i < 10;i++){
            for(int j = 0; j<10;j++){
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

package modele;

import modele.BDD.*;
import modele.BDD.Etudiant;
import modele.BDD.Groupe;
import modele.BDD.Particularite;
import modele.BDD.Place;
import modele.BDD.Salle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.sql.SQLException;
import java.util.*;

public class Examen {
	
	private String nom;
	private String matiere;
	private String date;

    /**
     * Attribut HashMap placement lie chaque salle à sa "grille" de placement
     */
    public HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>> placement;

    /**
     * Tout les étudiants qui ont été sélectionné
     */
    public HashMap<modele.BDD.Etudiant,String> etudiants;

    /**
     * Toutes les salles sélectionné trié par ordre de priorité
     */
    public ArrayList<modele.BDD.Salle> salles;

    /**
     * Distancce entre chaque etudiant
     */
    public int pas;


    /**
     * Constructeur Examen
     */
    public Examen(){
        this.placement = new HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>>();
        this.etudiants = new HashMap<modele.BDD.Etudiant, String>();
        this.salles = new ArrayList<>();
        this.pas = 1;
    }

    /**
     * Permet d'ajouter la salle donnée en paramétre
     * @param salle
     */
    public void ajouterSalle(modele.BDD.Salle salle){
        this.placement.put(salle, new HashMap<modele.BDD.Place, modele.BDD.Etudiant>());
        this.salles.add(salle);
    }

    /**
     * Permet d'ajouter un groupe, seuls les étudiants sont ajouté et pas le groupe en lui-même
     * @param groupe
     */
    public void ajouterGroupe(Groupe groupe){
        /**
         * TO DO RECUPERE LES ETUDIANTS AVEC LA METHODE
         */




        for(modele.BDD.Etudiant etu : groupe.etudiants){
            this.etudiants.put(etu,groupe.nom);
        }
    }

    /**
     * Méthode permettant de générer un placement le plus optimal possible
     */
    public void genererUnPlacement(){
        int nbTentative = 0;
        int resultat = 500;

        int margeErreur = 0;

        while(resultat !=margeErreur){
            placerEleve();
            resultat = this.verifierSolution();
            nbTentative++;
            if(nbTentative== 20000){
                margeErreur++;
                nbTentative = 0;
            }
        }
        System.out.println("Le placement générer contient une marge d'erreur de :"+margeErreur+" cela peut être du à la présence d'élève ayant un placement spéccifique.");
    }

    /**
     * Méthode permettant de placer tout les éléves dans les salles choisies
     */
    public void placerEleve(){
        //On récupére d'abord tout les éléve dans une seule et même liste
        ArrayList<modele.BDD.Etudiant> listeEtu = new ArrayList<modele.BDD.Etudiant>(this.etudiants.keySet());
        //On filtre les étudiant particulier qui ne sont pas à placer
        listeEtu= this.filtrerEleveParticulier(listeEtu);

        //Trier la liste en plaçent les etudiant particuclier au début de celle-ci
        //Pour qu'il soit placer en premier
        Collections.sort(listeEtu);

        //On suppose qu'il y a déja assez de place pour placer tout les éléves

        //On initialise le debut du placement (Ici on commence en bas à droite de la salle)
        int i = salles.get(0).getNbCaseHauteur()-1;
        int j = salles.get(0).getNbCaseLargeur()-1;
        int k = 0;
        int iterateurChangementSalle = 0;
        int nombreEssai = 0;
        modele.BDD.Etudiant etudiantTeste=null;
        modele.BDD.Salle salle = salles.get(iterateurChangementSalle);

        Iterateur iterateurSalle = salle.getIterateur(i,j);

        //Tant qu'il y a des Etudiant non place
        while(listeEtu.size()!=0){
            Random r = new Random();
            int nbAlea = r.nextInt((listeEtu.size()-1) + 1);
            //On prend le premier étudiant de la liste (celui-ci change dés qu'un étudiant est placé)

            ArrayList<Particularite> particularites = new ArrayList<Particularite>();
            try {
                particularites = ParticulariteEtudiant.listParticularitePourEtudiant(listeEtu.get(0).getIdEtu());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(!(particularites.size()>0)){
                etudiantTeste = listeEtu.get(nbAlea);
            }else{
                etudiantTeste = listeEtu.get(0);
            }

            //On vérifie si la place est disponible (chaise cassé,allée...) et qu'aucun n'autre étudiant n'a été placé dessus

            if(((modele.BDD.Place)iterateurSalle.actual()).estDisponible && !(this.placement.containsKey((modele.BDD.Place)iterateurSalle.actual()))){

                //On regarde si il y a un conflit de groupe avec les places adjacentes, de plus si on à déja testé tout les étudiants alors l'étudiant
                // sera placé même si un membre du même groupe est adjacent à lui
                if(verifierPlacement(salle,etudiantTeste,(modele.BDD.Place) iterateurSalle.actual()) || nombreEssai+1>=listeEtu.size()){

                    //Il n'y a pas de conflit donc l'étudiant peut être placer à cet endroit et l'enlever de la liste
                    this.placement.get(salle).put(salle.getPlaces()[iterateurSalle.getCoordI()][iterateurSalle.getCoordY()],etudiantTeste);

                    listeEtu.remove(etudiantTeste);
                    // k=0;
                    nombreEssai=0;

                    //On va ensuite à une autre place si elle existe
                    if(iterateurSalle.hasPrevious(this.pas)){
                        iterateurSalle.previous(this.pas);
                    }else if(listeEtu.size()>0){
                        //Sinon on change de salle
                        i = salles.get(0).getNbCaseHauteur()-1;
                        j = salles.get(0).getNbCaseLargeur()-1;

                        iterateurChangementSalle++;
                        salle =salles.get(iterateurChangementSalle);
                        iterateurSalle = salle.getIterateur(i,j);
                    }

                }else{
                    //On test un autre étudiant
                    nombreEssai++;
                }
            }else{
                //La place n'est pas disponible donc on en teste une autre
                if(iterateurSalle.hasPrevious(this.pas)){
                    iterateurSalle.previous(this.pas);
                }
            }
        }
    }


    /**
     * Méthode permettant de vérifier le nombre d'éléve cote-â-cote de la solution
     * @return int
     *      Le nombre d'éléve placé côte-â-côte
     */
    private int verifierSolution(){
        int resultat = 0;
        Set<modele.BDD.Salle> salles = this.placement.keySet();

        for(Salle salle : salles){
            Set<modele.BDD.Place> places = this.placement.get(salle).keySet();

            for(modele.BDD.Place p : places){
                Etudiant etudiant = this.placement.get(salle).get(p);
                int[] tabValeurI = {(p.getI())+1,(p.getI())-1};
                int[] tabValeurJ = {(p.getJ())+1,(p.getJ())-1};

                for(int i = 0; i < tabValeurI.length;i++){
                    modele.BDD.Place placeTestee = new modele.BDD.Place(tabValeurI[i],p.getJ());
                    if(!testerPlace(placeTestee,etudiant,salle)){
                        resultat++;
                    }
                }

                for(int i = 0; i < tabValeurJ.length;i++){
                    modele.BDD.Place placeTestee = new Place(p.getI(),tabValeurJ[i]);
                    if(!(testerPlace(placeTestee,etudiant,salle))){
                        resultat++;
                    }
                }
            }
        }

        return resultat;
    }

    /**
     * Méthode permettant de vérifier les places autour d'un étudiant
     * @param salle
     * @param etu
     * @param place
     * @return
     */
    private boolean verifierPlacement(modele.BDD.Salle salle, modele.BDD.Etudiant etu, modele.BDD.Place place){
        Iterateur iterateurSalle = salle.getIterateur(place.getI(),place.getJ());
        modele.BDD.Place placeTestee;

        //On test toutes les places autour de l'étudiant
        //Si il y a une place à gauche
        if(iterateurSalle.hasPrevious(this.pas)){
            placeTestee = (modele.BDD.Place) iterateurSalle.previous(this.pas);
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur à la position de l'étudiant
        iterateurSalle.reset();
        //Si il y a une place à droite
        if(iterateurSalle.hasNext(this.pas)){
            placeTestee = (modele.BDD.Place) iterateurSalle.next(this.pas);
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur à la position de l'étudiant
        iterateurSalle.reset();
        //Si il y a une place en haut
        if(iterateurSalle.hasUp()){
            placeTestee = (modele.BDD.Place) iterateurSalle.up();
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur à la position de l'étudiant
        iterateurSalle.reset();
        //Si il y a une place à droite
        if(iterateurSalle.hasDown()){
            placeTestee = (modele.BDD.Place) iterateurSalle.down();
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }
        return true;
    }

    /**
     * Vérifie si la place est occupé par un étudiant et si celui-ci à un groupe différent de l'étudiant passé en paramétre
     * @param place
     * @param etu
     * @param salle
     * @return
     */
    private boolean testerPlace(modele.BDD.Place place, modele.BDD.Etudiant etu, modele.BDD.Salle salle){
        if(place.isDisponnible()){
            //On vérifie si il y a un étudiant placer à la place donné en paramétre
            modele.BDD.Etudiant etudiantOccupantLaPlace = this.placement.get(salle).get(place);
            //Si il y a un étudiant placer
            if(!(null==etudiantOccupantLaPlace)){
                //On vérifie les groupes
                //Si ils ont le même groupe
                if(verifierGroupe(etu,  etudiantOccupantLaPlace)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Méthode permettant de vérifier les groupes de deux étudiants
     * @param etudiantAPlacer
     * @param etudiantPlacer
     * @return boolean
     *      True si les étudiants ont le même groupe, False sinon.
     */
    public boolean verifierGroupe(modele.BDD.Etudiant etudiantAPlacer, modele.BDD.Etudiant etudiantPlacer){
        boolean res = false;

        if(this.etudiants.get(etudiantAPlacer).equals(this.etudiants.get(etudiantPlacer))){
            return true;
        }

        return res;
    }

    /**
     * Méthode permettant d'enlever les étudiants qui ne doivent pas être pris en compte dans le placement
     * @param etu
     * @return
     */
    public ArrayList<modele.BDD.Etudiant> filtrerEleveParticulier(ArrayList<modele.BDD.Etudiant> etu){
        ArrayList<modele.BDD.Etudiant> res = etu;
        for(modele.BDD.Etudiant etudiant : res){

            ArrayList<Particularite> particularites = new ArrayList<Particularite>();
            try {
                particularites = ParticulariteEtudiant.listParticularitePourEtudiant(etudiant.getIdEtu());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(particularites.size()>0){
                if(!(etudiant.verifierPriseEnCompte())){
                    res.remove(etudiant);
                }
            }
        }
        return res;
    }



	
	
	
	public String getNom() {
		return nom;
	}
	public String getMatiere() {
		return matiere;
	}
	public String getDate() {
		return date;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}
	public void setMatiere(String matiere) {
		this.matiere = matiere;
	}
	public void setDate(String date) {
		this.date = date;
	}


	
	
	
	//Groupe Participant

	//Salle par Priorité

	//Contrainte








	
	
	
}

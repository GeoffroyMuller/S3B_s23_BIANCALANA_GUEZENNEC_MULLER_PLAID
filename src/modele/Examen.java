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

import modele.BDD.Categorie;

public class Examen extends Observable{
	
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
    public HashMap<Etudiant,String> etudiants;

    /**
     * Toutes les salles sélectionné trié par ordre de priorité
     */
    public ArrayList<Salle> salles;

    /**
     * Distancce entre chaque etudiant
     */
    public int pas;


    /**
     * Constructeur Examen
     */
    public Examen(){
        this.nom = "";
        this.date = "";
        this.matiere = "";
        this.placement = new HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>>();
        this.etudiants = new HashMap<modele.BDD.Etudiant, String>();
        this.salles = new ArrayList<Salle>();
        this.pas = 1;
    }

    /**
     * Permet d'ajouter la salle donnée en paramétre
     * @param salle
     */
    public void ajouterSalle(modele.BDD.Salle salle){
        this.placement.put(salle, new HashMap<modele.BDD.Place, modele.BDD.Etudiant>());
        this.salles.add(salle);
		setChanged();
		notifyObservers();
    }


    /**
     * Permet d'enlever un étudiant de la liste des étudiants pris en compte pour l'examen
     * @param etudiant
     */
    public void enleverUnEtudiantDeExamen(Etudiant etudiant){
        this.etudiants.remove(etudiant);
       /* HashMap<Etudiant, String> etudiants = new HashMap<Etudiant,String>();
        Set<Etudiant> listeEtudiant = this.etudiants.keySet();
        for(Etudiant etu : listeEtudiant){
            if(etudiant != etu){
                etudiants.put(etu,etu.getGroupe());
            }
        }
        this.etudiants = etudiants;*/
    }

    /**
     * Permet d'enlever des étudiants de la liste des étudiants pris en compte pour l'exmamen
     * @param etudiants
     */
    public void enleverDesEtudiantsDeExamen(ArrayList<Etudiant> etudiants){
        for (Etudiant etudiant: etudiants) {
            this.etudiants.remove(etudiant);
        }
		setChanged();
		notifyObservers();
    }

    /**
     * Permet d'enlever un groupe de la liste des étudiants pris en comptepour l'examen
     * @param groupe
     */
    public void enleverDesGroupesDeExamen(Groupe groupe){
        ArrayList<Etudiant> etudiants = groupe.getListeEtudiants();
        this.enleverDesEtudiantsDeExamen(etudiants);
    }

    /**
     * Permet d'ajouter un groupe, seuls les étudiants sont ajoutés et pas le groupe en lui-même
     * @param groupe
     */
    public void ajouterGroupe(Groupe groupe){

        ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
        try {
            etudiants = EtudiantGroupe.recupererEtudiantDansGroupe(groupe.getIdGroupe());
        } catch (SQLException e) {
            e.printStackTrace();
        }


        for(modele.BDD.Etudiant etu : etudiants){
            this.etudiants.put(etu,groupe.getNom());
        }
    }

    /**
     * Méthode permettant l'ajout d'un etudiant à un examen
     * @param etudiant
     */
    public void ajouterUnEtudiant(Etudiant etudiant){
        this.etudiants.put(etudiant,etudiant.getGroupe());
    }

    /**
     * Méthode permettant de générer un placement le plus optimal possible
     */
    public void genererUnPlacement(){
        int nbTentative = 0;
        int resultat = 500;

        int margeErreur = 0;

        if(!this.verifierLesParametresExamen()){
            /*
            To do ouverture fenetre exception
             */
        }

        while(resultat !=margeErreur){
            placerEleve();
            resultat = this.verifierSolution();
            nbTentative++;
            if(nbTentative== 20000){
                System.out.println("TENTNATIVE");
                margeErreur++;
                nbTentative = 0;
            }
        }
        System.out.println("Le placement générer contient une marge d'erreur de :"+margeErreur+" cela peut être du à la présence d'élève ayant un placement spéccifique.");
    }


    /**
     * Méthode permettant de vérifier si le nombre de places sélectionnées est suffisant pour le nombre d'éléve sélectionnés
     * @return boolean
     */
    public boolean vérifierLeNombreDePlace(){
        boolean res = false;
        int nombreDePlacesTotales = 0;

        //On compte le nombre d'étudiant
        Set<Etudiant> etudiants = this.etudiants.keySet();
        int nombreEtudiants = etudiants.size();

        if(nombreEtudiants==0){
            res=false;
        }else{
            //On Compte le nombre de place disponible dans toutes les salles
            for (Salle salle: this.salles) {
                nombreDePlacesTotales+=salle.compterLeNombreDePlaceDisponible();
            }



            //On compare
            if(nombreDePlacesTotales>= nombreEtudiants){
                res=true;
            }
        }

        return res;
    }

    /**
     * Méthode permettant de vérifier que tout les champs nécessaire à la création d'un examen on été renseigné
     * @return
     */
    public boolean verifierLesParametresExamen(){
        boolean res=true;

        if(this.nom.equals("") || this.date.equals("") || this.matiere.equals("")){
            res=false;
        }

        return res;
    }


    /**
     * Méthode permettant de placer tout les éléves dans les salles choisies
     */
    public void placerEleve(){
        //On récupére d'abord tout les éléve dans une seule et même liste
        ArrayList<modele.BDD.Etudiant> listeEtu = new ArrayList<Etudiant>(this.etudiants.keySet());
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

        Iterateur iterateurSalle = salle.getIterateur(i,j,salle);

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
            Place placeActuelle = (Place)iterateurSalle.actual();

            if(placeActuelle.getDisponnible() && !(this.placement.containsKey((modele.BDD.Place)iterateurSalle.actual()))){

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
                        iterateurSalle = salle.getIterateur(i,j,salle);
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
                    modele.BDD.Place placeTestee = new modele.BDD.Place(tabValeurI[i]+""+p.getJ(),tabValeurI[i],p.getJ(),salle.getIdSalle());
                    if(!testerPlace(placeTestee,etudiant,salle)){
                        resultat++;
                    }
                }

                for(int i = 0; i < tabValeurJ.length;i++){
                    modele.BDD.Place placeTestee = new Place(p.getI()+""+tabValeurJ[i],p.getI(),tabValeurJ[i],salle.getIdSalle());
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
        Iterateur iterateurSalle = salle.getIterateur(place.getI(),place.getJ(),salle);
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
        if(place.getDisponnible()){
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

    public HashMap<Salle, HashMap<Place, Etudiant>> getPlacement() {
        return placement;
    }

    public void setPlacement(HashMap<Salle, HashMap<Place, Etudiant>> placement) {
        this.placement = placement;
    }

    public HashMap<Etudiant, String> getEtudiants() {
        return etudiants;
    }

    public void setEtudiants(HashMap<Etudiant, String> etudiants) {
        this.etudiants = etudiants;
    }

    public ArrayList<Salle> getSalles() {
        return salles;
    }

    public void setSalles(ArrayList<Salle> salles) {
        this.salles = salles;
    }

    public int getPas() {
        return pas;
    }

    public void setPas(int pas) {
        this.pas = pas;
    }
    
    public void refresh() {
    	this.setChanged();
    	this.notifyObservers();
    }


    //Groupe Participant

	//Salle par Priorité

	//Contrainte








	
	
	
}

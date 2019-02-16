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
     * Attribut HashMap placement lie chaque salle � sa "grille" de placement
     */
    public HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>> placement;

    /**
     * Tout les �tudiants qui ont �t� s�lectionn�
     */
    public HashMap<Etudiant,String> etudiants;

    /**
     * Toutes les salles s�lectionn� tri� par ordre de priorit�
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
     * Permet d'ajouter la salle donn�e en param�tre
     * @param salle
     */
    public void ajouterSalle(modele.BDD.Salle salle){
        this.placement.put(salle, new HashMap<modele.BDD.Place, modele.BDD.Etudiant>());
        this.salles.add(salle);
		setChanged();
		notifyObservers();
    }


    /**
     * Permet d'enlever un �tudiant de la liste des �tudiants pris en compte pour l'examen
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
     * Permet d'enlever des �tudiants de la liste des �tudiants pris en compte pour l'exmamen
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
     * Permet d'enlever un groupe de la liste des �tudiants pris en comptepour l'examen
     * @param groupe
     */
    public void enleverDesGroupesDeExamen(Groupe groupe){
        ArrayList<Etudiant> etudiants = groupe.getListeEtudiants();
        this.enleverDesEtudiantsDeExamen(etudiants);
    }

    /**
     * Permet d'ajouter un groupe, seuls les �tudiants sont ajout�s et pas le groupe en lui-m�me
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
     * M�thode permettant l'ajout d'un etudiant � un examen
     * @param etudiant
     */
    public void ajouterUnEtudiant(Etudiant etudiant){
        this.etudiants.put(etudiant,etudiant.getGroupe());
    }

    /**
     * M�thode permettant de g�n�rer un placement le plus optimal possible
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
        System.out.println("Le placement g�n�rer contient une marge d'erreur de :"+margeErreur+" cela peut �tre du � la pr�sence d'�l�ve ayant un placement sp�ccifique.");
    }


    /**
     * M�thode permettant de v�rifier si le nombre de places s�lectionn�es est suffisant pour le nombre d'�l�ve s�lectionn�s
     * @return boolean
     */
    public boolean v�rifierLeNombreDePlace(){
        boolean res = false;
        int nombreDePlacesTotales = 0;

        //On compte le nombre d'�tudiant
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
     * M�thode permettant de v�rifier que tout les champs n�cessaire � la cr�ation d'un examen on �t� renseign�
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
     * M�thode permettant de placer tout les �l�ves dans les salles choisies
     */
    public void placerEleve(){
        //On r�cup�re d'abord tout les �l�ve dans une seule et m�me liste
        ArrayList<modele.BDD.Etudiant> listeEtu = new ArrayList<Etudiant>(this.etudiants.keySet());
        //On filtre les �tudiant particulier qui ne sont pas � placer
        listeEtu= this.filtrerEleveParticulier(listeEtu);

        //Trier la liste en pla�ent les etudiant particuclier au d�but de celle-ci
        //Pour qu'il soit placer en premier
        Collections.sort(listeEtu);

        //On suppose qu'il y a d�ja assez de place pour placer tout les �l�ves

        //On initialise le debut du placement (Ici on commence en bas � droite de la salle)
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
            //On prend le premier �tudiant de la liste (celui-ci change d�s qu'un �tudiant est plac�)

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

            //On v�rifie si la place est disponible (chaise cass�,all�e...) et qu'aucun n'autre �tudiant n'a �t� plac� dessus
            Place placeActuelle = (Place)iterateurSalle.actual();

            if(placeActuelle.getDisponnible() && !(this.placement.containsKey((modele.BDD.Place)iterateurSalle.actual()))){

                //On regarde si il y a un conflit de groupe avec les places adjacentes, de plus si on � d�ja test� tout les �tudiants alors l'�tudiant
                // sera plac� m�me si un membre du m�me groupe est adjacent � lui
                if(verifierPlacement(salle,etudiantTeste,(modele.BDD.Place) iterateurSalle.actual()) || nombreEssai+1>=listeEtu.size()){

                    //Il n'y a pas de conflit donc l'�tudiant peut �tre placer � cet endroit et l'enlever de la liste
                    this.placement.get(salle).put(salle.getPlaces()[iterateurSalle.getCoordI()][iterateurSalle.getCoordY()],etudiantTeste);

                    listeEtu.remove(etudiantTeste);
                    // k=0;
                    nombreEssai=0;

                    //On va ensuite � une autre place si elle existe
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
                    //On test un autre �tudiant
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
     * M�thode permettant de v�rifier le nombre d'�l�ve cote-�-cote de la solution
     * @return int
     *      Le nombre d'�l�ve plac� c�te-�-c�te
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
     * M�thode permettant de v�rifier les places autour d'un �tudiant
     * @param salle
     * @param etu
     * @param place
     * @return
     */
    private boolean verifierPlacement(modele.BDD.Salle salle, modele.BDD.Etudiant etu, modele.BDD.Place place){
        Iterateur iterateurSalle = salle.getIterateur(place.getI(),place.getJ(),salle);
        modele.BDD.Place placeTestee;

        //On test toutes les places autour de l'�tudiant
        //Si il y a une place � gauche
        if(iterateurSalle.hasPrevious(this.pas)){
            placeTestee = (modele.BDD.Place) iterateurSalle.previous(this.pas);
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur � la position de l'�tudiant
        iterateurSalle.reset();
        //Si il y a une place � droite
        if(iterateurSalle.hasNext(this.pas)){
            placeTestee = (modele.BDD.Place) iterateurSalle.next(this.pas);
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur � la position de l'�tudiant
        iterateurSalle.reset();
        //Si il y a une place en haut
        if(iterateurSalle.hasUp()){
            placeTestee = (modele.BDD.Place) iterateurSalle.up();
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur � la position de l'�tudiant
        iterateurSalle.reset();
        //Si il y a une place � droite
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
     * V�rifie si la place est occup� par un �tudiant et si celui-ci � un groupe diff�rent de l'�tudiant pass� en param�tre
     * @param place
     * @param etu
     * @param salle
     * @return
     */
    private boolean testerPlace(modele.BDD.Place place, modele.BDD.Etudiant etu, modele.BDD.Salle salle){
        if(place.getDisponnible()){
            //On v�rifie si il y a un �tudiant placer � la place donn� en param�tre
            modele.BDD.Etudiant etudiantOccupantLaPlace = this.placement.get(salle).get(place);
            //Si il y a un �tudiant placer
            if(!(null==etudiantOccupantLaPlace)){
                //On v�rifie les groupes
                //Si ils ont le m�me groupe
                if(verifierGroupe(etu,  etudiantOccupantLaPlace)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * M�thode permettant de v�rifier les groupes de deux �tudiants
     * @param etudiantAPlacer
     * @param etudiantPlacer
     * @return boolean
     *      True si les �tudiants ont le m�me groupe, False sinon.
     */
    public boolean verifierGroupe(modele.BDD.Etudiant etudiantAPlacer, modele.BDD.Etudiant etudiantPlacer){
        boolean res = false;

        if(this.etudiants.get(etudiantAPlacer).equals(this.etudiants.get(etudiantPlacer))){
            return true;
        }

        return res;
    }

    /**
     * M�thode permettant d'enlever les �tudiants qui ne doivent pas �tre pris en compte dans le placement
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

	//Salle par Priorit�

	//Contrainte








	
	
	
}

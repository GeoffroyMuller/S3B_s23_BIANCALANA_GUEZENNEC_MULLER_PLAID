package modele;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;

public class Examen {
	
	private String nom;
	private String matiere;
	private String date;

    /**
     * Attribut HashMap placement lie chaque salle � sa "grille" de placement
     */
    public HashMap<Salle, HashMap<Place,Etudiant>> placement;

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
        this.placement = new HashMap<Salle,HashMap<Place,Etudiant>>();
        this.etudiants = new HashMap<Etudiant,String>();
        this.salles = new ArrayList<>();
        this.pas = 1;
    }

    /**
     * Permet d'ajouter la salle donn�e en param�tre
     * @param salle
     */
    public void ajouterSalle(Salle salle){
        this.placement.put(salle, new HashMap<Place,Etudiant>());
        this.salles.add(salle);
    }

    /**
     * Permet d'ajouter un groupe, seuls les �tudiants sont ajout� et pas le groupe en lui-m�me
     * @param groupe
     */
    public void ajouterGroupe(Groupe groupe){
        /**
         * TO DO RECUPERE LES ETUDIANTS AVEC LA METHODE
         */
        for(Etudiant etu : groupe.etudiants){
            this.etudiants.put(etu,groupe.nom);
        }
    }

    /**
     * M�thode permettant de g�n�rer un placement le plus optimal possible
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
            System.out.println(nbTentative);
            // System.out.println("Resultat ="+resultat);
        }
        System.out.println("Le placement g�n�rer contient une marge d'erreur de :"+margeErreur+" cela peut �tre du � la pr�sence d'�l�ve ayant un placement sp�ccifique.");
    }

    /**
     * M�thode permettant de placer tout les �l�ves dans les salles choisies
     */
    public void placerEleve(){
        //On r�cup�re d'abord tout les �l�ve dans une seule et m�me liste
        ArrayList<Etudiant> listeEtu = new ArrayList<Etudiant>(this.etudiants.keySet());
        //On filtre les �tudiant particulier qui ne sont pas � placer
        listeEtu= this.filtrerEleveParticulier(listeEtu);

        //Trier la liste en pla�ent les etudiant particuclier au d�but de celle-ci
        //Pour qu'il soit placer en premier
        Collections.sort(listeEtu);

        //On suppose qu'il y a d�ja assez de place pour placer tout les �l�ves

        //On initialise le debut du placement (Ici on commence en bas � droite de la salle)
        int i = salles.get(0).nbCaseHauteur-1;
        int j = salles.get(0).nbCaseLargeur-1;
        int k = 0;
        int iterateurChangementSalle = 0;
        int nombreEssai = 0;
        Etudiant etudiantTeste=null;
        Salle salle = salles.get(iterateurChangementSalle);

        Iterateur iterateurSalle = salle.getIterateur(i,j);

        //Tant qu'il y a des Etudiant non place
        while(listeEtu.size()!=0){
            Random r = new Random();
            int nbAlea = r.nextInt((listeEtu.size()-1) + 1);
            //On prend le premier �tudiant de la liste (celui-ci change d�s qu'un �tudiant est plac�)
            if(!(listeEtu.get(0).particularite.size()>0)){
                etudiantTeste = listeEtu.get(nbAlea);
            }else{
                etudiantTeste = listeEtu.get(0);
            }

            //On v�rifie si la place est disponible (chaise cass�,all�e...) et qu'aucun n'autre �tudiant n'a �t� plac� dessus
            if(((Place)iterateurSalle.actual()).estDisponible && !(this.placement.containsKey((Place)iterateurSalle.actual()))){

                //On regarde si il y a un conflit de groupe avec les places adjacentes, de plus si on � d�ja test� tout les �tudiants alors l'�tudiant
                // sera plac� m�me si un membre du m�me groupe est adjacent � lui
                if(verifierPlacement(salle,etudiantTeste,(Place)iterateurSalle.actual()) || nombreEssai+1>=listeEtu.size()){

                    //Il n'y a pas de conflit donc l'�tudiant peut �tre placer � cet endroit et l'enlever de la liste
                    this.placement.get(salle).put(salle.places[iterateurSalle.getCoordI()][iterateurSalle.getCoordY()],etudiantTeste);

                    listeEtu.remove(etudiantTeste);
                    // k=0;
                    nombreEssai=0;

                    //On va ensuite � une autre place si elle existe
                    if(iterateurSalle.hasPrevious(this.pas)){
                        iterateurSalle.previous(this.pas);
                    }else if(listeEtu.size()>0){
                        //Sinon on change de salle
                        i = salles.get(0).nbCaseHauteur-1;
                        j = salles.get(0).nbCaseLargeur-1;

                        iterateurChangementSalle++;
                        salle =salles.get(iterateurChangementSalle);
                        iterateurSalle = salle.getIterateur(i,j);
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
        Set<Salle> salles = this.placement.keySet();

        for(Salle salle : salles){
            Set<Place> places = this.placement.get(salle).keySet();

            for(Place p : places){
                Etudiant etudiant = this.placement.get(salle).get(p);
                int[] tabValeurI = {(p.i)+1,(p.i)-1};
                int[] tabValeurJ = {(p.j)+1,(p.j)-1};

                for(int i = 0; i < tabValeurI.length;i++){
                    Place placeTestee = new Place(tabValeurI[i],p.j);
                    if(!testerPlace(placeTestee,etudiant,salle)){
                        resultat++;
                    }
                }

                for(int i = 0; i < tabValeurJ.length;i++){
                    Place placeTestee = new Place(p.i,tabValeurJ[i]);
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
    private boolean verifierPlacement(Salle salle, Etudiant etu, Place place){
        Iterateur iterateurSalle = salle.getIterateur(place.i,place.j);
        Place placeTestee;

        //On test toutes les places autour de l'�tudiant
        //Si il y a une place � gauche
        if(iterateurSalle.hasPrevious(this.pas)){
            placeTestee = (Place)iterateurSalle.previous(this.pas);
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur � la position de l'�tudiant
        iterateurSalle.reset();
        //Si il y a une place � droite
        if(iterateurSalle.hasNext(this.pas)){
            placeTestee = (Place)iterateurSalle.next(this.pas);
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur � la position de l'�tudiant
        iterateurSalle.reset();
        //Si il y a une place en haut
        if(iterateurSalle.hasUp()){
            placeTestee = (Place)iterateurSalle.up();
            //On test la place
            if(!testerPlace(placeTestee,etu,salle)){
                return false;
            }
        }

        //On remet l'iterateur � la position de l'�tudiant
        iterateurSalle.reset();
        //Si il y a une place � droite
        if(iterateurSalle.hasDown()){
            placeTestee = (Place)iterateurSalle.down();
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
    private boolean testerPlace(Place place, Etudiant etu, Salle salle){
        if(place.isDisponnible()){
            //On v�rifie si il y a un �tudiant placer � la place donn� en param�tre
            Etudiant etudiantOccupantLaPlace = this.placement.get(salle).get(place);
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
    public boolean verifierGroupe(Etudiant etudiantAPlacer, Etudiant etudiantPlacer){
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
    public ArrayList<Etudiant> filtrerEleveParticulier(ArrayList<Etudiant> etu){
        ArrayList<Etudiant> res = etu;
        for(Etudiant etudiant : res){
            if(etudiant.particularite.size()>0){
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

	//Salle par Priorit�

	//Contrainte








	
	
	
}

package modele;

import modele.BDD.*;
import modele.BDD.Etudiant;
import modele.BDD.Groupe;
import modele.BDD.Particularite;
import modele.BDD.Place;
import modele.BDD.Salle;
import vue_Examen.VueExamen;

import java.awt.*;
import java.sql.Array;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

/**
 * Classe du mod�le Examen, repr�sente un Examen
 */
public class Examen extends Observable{

	private String nom;
	private String matiere;
	private String date;

	private boolean groupeSepare;

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
     * Permet de savoir quand l'examen a finit de se g�n�rer
     */
	boolean fini;


	/**
	 * Constructeur Examen
	 */
	public Examen(){
		this.nom = "";
		this.date = "";
		this.matiere = "";
		this.groupeSepare = true;
		this.placement = new HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>>();
		this.etudiants = new HashMap<modele.BDD.Etudiant, String>();
		this.salles = new ArrayList<Salle>();
		this.pas = 2;
		this.fini = false;
	}

	/**
	 * Permet d'ajouter la salle donn�e en param�tre
	 * @param salle
	 */
	public void ajouterSalle(modele.BDD.Salle salle){
        System.out.println("Ajout SALLE");
		this.salles.add(salle);
		this.placement.put(salle,new HashMap<Place, Etudiant>());
		System.out.println("La premiere salle est maintenant : "+this.salles.get(0).getNom());

		for(Salle salleD :salles){
            System.out.println("SALLE tous : "+salleD.getNom());
        }

		setChanged();
		notifyObservers();
    }

    /**
     * Permet de retirer une salle d'un examen
     * @param salle
     */
    public void retirerSalle(Salle salle){
        //this.placement.remove(salle);
        System.out.println("Suppr Salle");
        //Supression dans le placement
        HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>> copiePlacement = new HashMap<>(this.placement);

        this.placement = new HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>>();
        for(Salle salleKey : copiePlacement.keySet()){
            if(salleKey != salle){
                this.placement.put(salleKey,copiePlacement.get(salleKey));
            }
        }
        System.out.println("La premiere salle est maintenant : "+this.salles.get(0).getNom());
        this.salles.remove(salle);
    }

    /**
     * Permet de retirer toutes les salles d'un examen
     */
    public void reinitiliserLesSalles(){
        ArrayList<Salle> copyListeSalle = new ArrayList<>();
        copyListeSalle.addAll(this.salles);
        for(Salle salle : copyListeSalle){
            retirerSalle(salle);
        }
    }

    /**
     * Permet de v�rifier si une salle � �t� selectionn� plusieurs fois
     */
    public boolean verificationDoublonSalle(){
        boolean res = false;
        int compteur=0;
        for(int i = 0; i < salles.size();i++){
            Salle salle = salles.get(i);
            for(int j = 0; j < salles.size();j++){
                if(salle.getNom().equals(salles.get(j))){
                    compteur++;
                }
            }
            if(compteur>1){
                res=true;
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"Une m�me salle � �t� ajout� plusieurs fois ! \n Veuillez changer les salles ou enlever des priorit�s.","Message Informatif",JOptionPane.INFORMATION_MESSAGE);
                break;
            }
        }
        return res;
    }


    /**
     * Permet d'enlever un �tudiant de la liste des �tudiants pris en compte pour l'examen
     * @param etudiant
     */
    public void enleverUnEtudiantDeExamen(Etudiant etudiant){
        this.etudiants.remove(etudiant);
    }

    /**
     * Permet d'enlever des �tudiants de la liste des �tudiants pris en compte pour l'exmamen
     * @param etudiants
     */
    public void enleverDesEtudiantsDeExamen(ArrayList<Etudiant> etudiants){
        for (Etudiant etudiant: etudiants) {
            this.etudiants.remove(etudiant);
        }

    }

    /**
     * Permet d'enlever un groupe de la liste des �tudiants pris en comptepour l'examen
     * @param groupe
     */
    public void enleverDesGroupesDeExamen(Groupe groupe){

        ArrayList<Etudiant> etudiants = groupe.getListeEtudiants();
        this.enleverDesEtudiantsDeExamen(etudiants);
        setChanged();
		notifyObservers(VueExamen.VUE_ETU);
	}

	/**
	 * Permet d'ajouter un groupe, seuls les �tudiants sont ajout�s et pas le groupe en lui-m�me
	 * @param groupe
	 */
	public void ajouterGroupe(Groupe groupe){
        System.out.println("AJOUT GROUPE");

		ArrayList<Etudiant> etudiants = new ArrayList<Etudiant>();
		try {
			etudiants = EtudiantGroupe.recupererEtudiantDansGroupe(groupe.getIdGroupe());
		} catch (SQLException e) {
			e.printStackTrace();
		}


		for(modele.BDD.Etudiant etu : etudiants){
			this.etudiants.put(etu,groupe.getNom());
		}

        setChanged();
		notifyObservers(VueExamen.VUE_ETU);
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

        if(!this.verificationDoublonSalle()){
            if(!this.verifierLesParametresExamen()){
                JOptionPane jop = new JOptionPane();
                jop.showMessageDialog(null,"Les param�tres de l'examen ont mal �t� renseign�s.","Erreur",JOptionPane.INFORMATION_MESSAGE);
            }else{
                while(resultat !=margeErreur){
                    placerEleve();
                    resultat = this.verifierSolution();
                    nbTentative++;
                    if(nbTentative== 20000){
                        margeErreur++;
                        nbTentative = 0;
                    }
                }
                this.fini=true;
            }
            System.out.println("Le placement g�n�rer contient une marge d'erreur de :"+margeErreur+" cela peut �tre du � la pr�sence d'�l�ve ayant un placement sp�ccifique.");
        }
    }


    /**
     * M�thode permettant de v�rifier si le nombre de places s�lectionn�es est suffisant pour le nombre d'�l�ve s�lectionn�s
     * @return boolean
     */
    public boolean verifierLeNombreDePlace(){
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
                System.out.println("Salle dans la verif : "+salle.getNom());
                nombreDePlacesTotales+=salle.compterLeNombreDePlaceDisponible();
            }

            int test = nombreDePlacesTotales;

            //On prend en compte le pas

            /*int iteNombreDePLaceLaisse=0;
            for(int i = 0; i < nombreDePlacesTotales; i++){
                if(iteNombreDePLaceLaisse!=pas){
                    nombreDePlacesTotales--;
                }else{
                    iteNombreDePLaceLaisse=0;
                }
            }*/

            int nbPlace=1;

            for(int i = 1; i < nombreDePlacesTotales;i++){
                if(i%this.pas == 0){
                    nbPlace++;
                }
            }

            System.out.println("Verification du nombre de place : \n Pas = "+pas+"\nNombre de place totales = "+test+"\n Resultat = "+nbPlace);
            //On compare
            if(nbPlace>= nombreEtudiants){
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

        if(this.etudiants.keySet().size() == 0){
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null,"Veuillez ajouter des �tudiants � l'examen","Erreur",JOptionPane.ERROR_MESSAGE);
            return false;
        }

       if(this.nom.isEmpty() || this.matiere.isEmpty() || this.date.isEmpty()){
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null,"Veuillez renseigner les champs : Nom, Mati�re et Date","Erreur",JOptionPane.ERROR_MESSAGE);
            return false;
        }

      if(this.salles.isEmpty()){
          JOptionPane jop = new JOptionPane();
          jop.showMessageDialog(null,"Veuillez ajouter des salles � l'examen","Erreur",JOptionPane.ERROR_MESSAGE);
          return false;
      }

        if(!verifierLeNombreDePlace()){
            res=false;
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null,"Il y trop d'�tudiants pour les salles selectionn�es.\n Veuillez ajouter des salles ou enlever des �tudiant.","Erreur",JOptionPane.ERROR_MESSAGE);
        }

        return res;
    }


    /**
     * M�thode permettant de placer tout les �l�ves dans les salles choisies
     */
    public void placerEleve(){
        //On r�cup�re d'abord tout les �l�ves dans une seule et m�me liste
        ArrayList<modele.BDD.Etudiant> listeEtu = new ArrayList<Etudiant>(this.etudiants.keySet());
        //On filtre les �tudiant particulier qui ne sont pas � placer
        listeEtu= this.filtrerEleveParticulier(listeEtu);

        //Trier la liste en pla�ent les etudiant particuclier au d�but de celle-ci
        //Pour qu'il soit placer en premier
        Collections.sort(listeEtu);


        //On initialise le debut du placement (Ici on commence en bas � droite de la salle)
        int i = salles.get(0).getNbCaseHauteur()-1;
        int j = salles.get(0).getNbCaseLargeur()-1;
        int k = 0;
        int iterateurChangementSalle = 0;
        int nombreEssai = 0;
        modele.BDD.Etudiant etudiantTeste=null;
        modele.BDD.Salle salle = salles.get(iterateurChangementSalle);
        salle.getTableauPlaces(salle.getIdSalle());
        Iterateur iterateurSalle = salle.getIterateur(i,j,salle);

        //Tant qu'il y a des Etudiant non place
        while(listeEtu.size()!=0){
            Random r = new Random();
            int nbAlea = r.nextInt((listeEtu.size()-1) + 1);


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
            Place place = placeActuelle;

            boolean disponible = placeActuelle.getDisponnible();
            boolean cleContenu = this.placement.containsKey((modele.BDD.Place)iterateurSalle.actual());

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
                        iterateurChangementSalle++;
                        System.out.println("Changement de salle");
                        System.out.println("Iterateur : "+iterateurChangementSalle);
                        i = salles.get(iterateurChangementSalle).getNbCaseHauteur()-1;
                        j = salles.get(iterateurChangementSalle).getNbCaseLargeur()-1;

                        salle =salles.get(iterateurChangementSalle);
                        salle.getTableauPlaces(salle.getIdSalle());
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
                }else{
                    System.out.println("Changement de salle_Previous");
                    iterateurChangementSalle++;
                    System.out.println("Iterateur : "+iterateurChangementSalle);
                    i = salles.get(iterateurChangementSalle).getNbCaseHauteur()-1;
                    j = salles.get(iterateurChangementSalle).getNbCaseLargeur()-1;

                    salle =salles.get(iterateurChangementSalle);
                    salle.getTableauPlaces(salle.getIdSalle());
                    iterateurSalle = salle.getIterateur(i,j,salle);
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
                    modele.BDD.Place placeTestee = new modele.BDD.Place(tabValeurI[i]+""+p.getJ(),tabValeurI[i],p.getJ(),salle.getIdSalle(),p.getJ()+"",tabValeurI[i]+"");
                    if(!testerPlace(placeTestee,etudiant,salle)){
                        resultat++;
                    }
                }

                for(int i = 0; i < tabValeurJ.length;i++){
                    modele.BDD.Place placeTestee = new Place(p.getI()+""+tabValeurJ[i],p.getI(),tabValeurJ[i],salle.getIdSalle(),p.getI()+"",tabValeurJ[i]+"");
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

        if(!this.groupeSepare){
            return false;
        }

        if(!this.groupeSepare){
            res=false;
        }else{
            if(this.etudiants.get(etudiantAPlacer).equals(this.etudiants.get(etudiantPlacer))){
                return true;
            }
        }



        return res;
    }

    /**
     * M�thode permettant d'enlever les �tudiants qui ne doivent pas �tre pris en compte dans le placement
     * @param etu
     * @return
     */
    public ArrayList<modele.BDD.Etudiant> filtrerEleveParticulier(ArrayList<modele.BDD.Etudiant> etu){
        ArrayList<Etudiant> res = etu;
        ArrayList<Etudiant> resCPY = new ArrayList<>(res);
        for(Etudiant etudiant : res){

            ArrayList<Particularite> particularites = new ArrayList<Particularite>();
            try {
                particularites = ParticulariteEtudiant.listParticularitePourEtudiant(etudiant.getIdEtu());
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if(particularites.size()>0){
                if(!(etudiant.verifierPriseEnCompte())){
                    resCPY.remove(etudiant);
                }
            }
        }
        return resCPY;
    }

    /**
     * Permet de r�cup�rer les informations de placement d'un �tudiant
     * @param cre
     * @return
     */
    public InformationsPlacementEtudiant trouverPlaceEtudiant(CritereRechercheEtudiant cre){
        InformationsPlacementEtudiant resultat= new InformationsPlacementEtudiant(null,new Place("Non trouve",-1,-1,-2,"Non trouve","Non trouve"),new Etudiant("Non trouve","Non trouve"));
        Set<Salle> clesSalle = this.placement.keySet();

        for(Salle salle : clesSalle){
            Set<Place> clesPlaces = this.placement.get(salle).keySet();
            System.out.println("Boucle salle : "+salle.getNom());
            for(Place place : clesPlaces){
                Etudiant etudiant = this.placement.get(salle).get(place);
                if(etudiant.getNom().toLowerCase().equals(cre.getNom().toLowerCase())
                && etudiant.getPrenom().toLowerCase().equals(cre.getPrenom().toLowerCase())
                && etudiant.getGroupe().toLowerCase().equals(cre.getGroupe().toLowerCase())){
                    resultat = new InformationsPlacementEtudiant(salle,place,etudiant);
                }
            }
        }


        return resultat;
    }

     /**
      * Permet de g�n�rer la pr�visualisation d'un placementsous forme d'un JTable
      * @return
      */
    public ArrayList<JTable> genererPrevisualisationFiche(){
        ArrayList<JTable> resultat = new ArrayList<JTable>();
        String column[]={"ID","GROUPE","NOM","PRENOM","SALLE","RANG","PLACE"};

        Set<Salle> clesSalle= this.placement.keySet();
        for(Salle salle : this.salles){
            Set<Place> clesPlaces = this.placement.get(salle).keySet();
            String data[][] = new String[clesPlaces.size()][7];
            int iterateur = 0;
            for(Place place : clesPlaces){
                Etudiant etudiant = this.placement.get(salle).get(place);
                data[iterateur][0] = iterateur+"";
                data[iterateur][1] = etudiant.getGroupe();
                data[iterateur][2] = etudiant.getNom();
                data[iterateur][3] = etudiant.getPrenom();
                data[iterateur][4] = salle.getNom();
                data[iterateur][5] = place.getNomRangee()+"";
                data[iterateur][6] = place.getNomColonne()+"";
                iterateur++;
            }
            DefaultTableModel model = new DefaultTableModel(data, column);
            JTable tableau = new JTable(model){
                private static final long serialVersionUID = 1L;

                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            for(int i=0;i<tableau.getColumnCount();i++)
            {
                TableColumn column1 = tableau.getTableHeader().getColumnModel().getColumn(i);

                column1.setHeaderValue(column[i]);
                column1.setPreferredWidth(150);
            }
            tableau.setBounds(30,40,500,700);
            tableau.setFont(new Font("Arial",Font.PLAIN,15));
            tableau.setRowHeight(20);
            resultat.add(tableau);
        }

        return resultat;
    }


     /**
      *  Retourne tout les groupes participants
      * @return
      */
    public String[] groupeParticipant(){
        ArrayList<String> groupes = new ArrayList<String>();

        Set<Salle> clesSalles = this.placement.keySet();
        for(Salle salle : clesSalles){
            Set<Place> clesPlace = this.placement.get(salle).keySet();
            for(Place place : clesPlace){
                Etudiant etudiant = this.placement.get(salle).get(place);
                if(!(groupes.contains(etudiant.getGroupe().toLowerCase()))){
                    groupes.add(etudiant.getGroupe().toLowerCase());
                }
            }
        }
        String[] resultat = groupes.toArray(new String[groupes.size()]);


        return resultat;
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
		this.pas = pas+1;
	}
	
	

	public boolean isGroupeSepare() {
		return groupeSepare;
	}

	public void setGroupeSepare(boolean groupeSepare) {
		this.groupeSepare = groupeSepare;
	}

	public void refresh() {
		this.setChanged();
		this.notifyObservers();
	}

    /**
     * Permet d'�changer la place de deux �tudiants
     * @param etuA
     * @param etuB
     */
    public void echangerPlaceEtudiants(Etudiant etuA, Etudiant etuB){
        try{
            CritereRechercheEtudiant creA = new CritereRechercheEtudiant(etuA.getNom(),etuA.getPrenom(),etuA.getGroupe());
            CritereRechercheEtudiant creB = new CritereRechercheEtudiant(etuB.getNom(),etuB.getPrenom(),etuB.getGroupe());

            InformationsPlacementEtudiant ifeA = this.trouverPlaceEtudiant(creA);
            InformationsPlacementEtudiant ifeB = this.trouverPlaceEtudiant(creB);

            Place placeA = ifeA.getPlace();
            Place placeB = ifeB.getPlace();

            if(ifeA.getSalle() == ifeB.getSalle()){
                //L'�change s'effectue dans la m�me salle
                System.out.println(placeA);
                System.out.println(etuB.getNom());
                System.out.println(ifeA.getSalle().getNom());
                placement.get(ifeA.getSalle()).put(placeA,etuB);
                placement.get(ifeB.getSalle()).put(placeB,etuA);
            }else{
                //L'�change s'effectue dans des salles diff�rente
                placement.get(ifeA.getSalle()).remove(ifeA.getPlace());
                placement.get(ifeB.getSalle()).remove(ifeB.getPlace());

                placement.get(ifeB.getSalle()).put(ifeB.getPlace(),etuA);
                placement.get(ifeA.getSalle()).put(ifeA.getPlace(),etuB);
            }
            verificationNonDoublons();
        }catch(NullPointerException e){
            JOptionPane jop = new JOptionPane();
            jop.showMessageDialog(null,"L'�change n'a pu �tre effectu�. \n Veuillez v�rifier que les places s�lectionn� sont attribuer � des �tudiants.","Erreur",JOptionPane.ERROR_MESSAGE);
        }

    }

    public void verificationNonDoublons(){
        System.out.println("AFFICHAGE DES ETUDIANTS DEBUG");
        for(Salle salle : placement.keySet()){
            System.out.println("___Salle___"+salle.getNom()+"___");
            for(Place place : placement.get(salle).keySet()){
                System.out.println(placement.get(salle).get(place).getNom()+"-"+placement.get(salle).get(place).getPrenom());
            }
        }
    }


    /**
     * Reinitisalise l'examen lorsque celui-ci est g�n�r�
     */
    public void reinitialiseExamen(){
        this.nom = "";
        this.date = "";
        this.matiere = "";
        this.groupeSepare = true;
        this.placement = new HashMap<modele.BDD.Salle, HashMap<modele.BDD.Place, modele.BDD.Etudiant>>();
        this.etudiants = new HashMap<modele.BDD.Etudiant, String>();
        this.salles = new ArrayList<Salle>();
    }


    public boolean isFini() {
        return fini;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }











}

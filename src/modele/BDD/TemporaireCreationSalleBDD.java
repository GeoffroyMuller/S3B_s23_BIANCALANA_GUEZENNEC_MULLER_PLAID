package modele.BDD;

import java.sql.Connection;
import java.sql.SQLException;

public class TemporaireCreationSalleBDD {
    public static void main(String[] args) {
        try {
            Connection bd = DBConnection.getConnection();

            Salle salle = new Salle("Salle_Test");
            salle.save();

            int idSalle = salle.getIdSalle();

            for(int i = 0; i < 5;i++){
                Place place = new Place();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}

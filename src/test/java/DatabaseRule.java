import org.junit.rules.ExternalResource;
import org.sql2o.*;

public class DatabaseRule extends ExternalResource {

    @Override
    protected void before() {
        DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker_test", "belyse", "belyse");
    }

    @Override
    protected void after() {
        try(Connection con = DB.sql2o.open()) {
            String deleteSightingQuery = "DELETE FROM sightings *;";
            String deleteAnimalQuery = "DELETE FROM animals *;";
            con.createQuery(deleteSightingQuery).executeUpdate();
            con.createQuery(deleteAnimalQuery).executeUpdate();
        }
    }
}
import org.sql2o.Connection;

import java.util.List;

public class Sighting {
    private String location;
    private String ranger;
    private int id;

    public Sighting(String location, String ranger) {
        this.location = location;
        this.ranger = ranger;
    }

    public String getLocation() {
        return location;
    }

    public String getRanger() {
        return ranger;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Sighting)) {
            return false;
        } else {
            Sighting newSighting = (Sighting) o;
            return this.getLocation().equals(newSighting.getLocation()) &&
                    this.getRanger().equals(newSighting.getRanger());
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO sightings (location, ranger) VALUES (:location, :ranger)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("location", this.location)
                    .addParameter("ranger", this.ranger)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Sighting> all() {
        String sql = "SELECT * FROM sightings";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Sighting.class);
        }
    }

    public static Sighting find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM sightings where id=:id";
            Sighting sighting = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Sighting.class);
            return sighting;
        }
    }
}
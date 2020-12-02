import java.util.List;
import org.sql2o.*;

public class RegularAnimal extends Animal implements DatabaseManagement {

    public static final String DATABASE_TYPE = "animal";

    public RegularAnimal(String name) {
        if(DatabaseManagement.nameValidation(name)) {
            this.name = name;
        }
        this.type = DATABASE_TYPE;
    }

    public void save() {
        if (Animal.nameExists(this.name, this.id)) {
            throw new IllegalArgumentException("Error: Name already exists.");
        } else {
            try(Connection con = DB.sql2o.open()) {
                String sql = "INSERT INTO animals (name, type) VALUES (:name, :type);";
                this.id = (int) con.createQuery(sql, true)
                        .addParameter("name", this.name)
                        .addParameter("type", DATABASE_TYPE)
                        .executeUpdate()
                        .getKey();
            }
        }
    }

    public void update() {
        if (Animal.nameExists(this.name, this.id)) {
            throw new IllegalArgumentException("Error: Name already exists.");
        } else {
            try(Connection con = DB.sql2o.open()) {
                String sql = "UPDATE animals SET name = :name WHERE id = :id;";
                con.createQuery(sql)
                        .addParameter("id", this.id)
                        .addParameter("name", this.name)
                        .executeUpdate();
            }
        }
    }

    public static RegularAnimal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE id = :id AND type = :type;";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .addParameter("type", DATABASE_TYPE)
                    .executeAndFetchFirst(RegularAnimal.class);
        }
    }

    public static List<RegularAnimal> all() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE type = :type;";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("type", DATABASE_TYPE)
                    .executeAndFetch(RegularAnimal.class);
        }
    }

    public static List<RegularAnimal> search(String search) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE name ~* :search AND type = :type;";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("search", ".*" + search + ".*")
                    .addParameter("type", DATABASE_TYPE)
                    .executeAndFetch(RegularAnimal.class);
        }
    }

    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof RegularAnimal)) {
            return false;
        } else {
            RegularAnimal otherAnimal = (RegularAnimal) otherObject;
            return this.getName().equals(otherAnimal.getName()) &&
                    this.getId() == otherAnimal.getId();
        }
    }

}
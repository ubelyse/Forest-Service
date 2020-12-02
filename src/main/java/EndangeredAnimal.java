import org.sql2o.*;
import java.util.List;

public class EndangeredAnimal extends Animal implements DatabaseManagement {

    private double age;
    private String health;
    public static final String DATABASE_TYPE = "endangered_animal";

    public EndangeredAnimal(String name, double age, String health) {
        if(DatabaseManagement.nameValidation(name)) {
            this.name = name;
        }
        if(DatabaseManagement.ageValidation(age)) {
            this.age = age;
        }
        this.health = health;
        this.type = DATABASE_TYPE;
    }

    public double getAge() {
        return this.age;
    }

    public void setAge(double age) {
        if(DatabaseManagement.ageValidation(age)) {
            this.age = age;
        }
    }

    public String getHealth() {
        return this.health;
    }

    public void setHealth(String health) {
        this.health = health;
    }

    @Override
    public void save() {
        if (Animal.nameExists(this.name, this.id)) {
            throw new IllegalArgumentException("Error: Name already exists.");
        } else {
            try(Connection con = DB.sql2o.open()) {
                String sql = "INSERT INTO animals (name, type, age, health) VALUES (:name, :type, :age, :health);";
                this.id = (int) con.createQuery(sql, true)
                        .addParameter("name", this.name)
                        .addParameter("age", this.age)
                        .addParameter("health", this.health)
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
                String sql = "UPDATE animals SET name = :name, age = :age, health = :health WHERE id = :id;";
                con.createQuery(sql)
                        .addParameter("id", this.id)
                        .addParameter("name", this.name)
                        .addParameter("age", this.age)
                        .addParameter("health", this.health)
                        .executeUpdate();
            }
        }
    }

    public static List<EndangeredAnimal> all() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE type = :type;";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("type", DATABASE_TYPE)
                    .executeAndFetch(EndangeredAnimal.class);
        }
    }

    public static EndangeredAnimal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE id = :id AND type = :type;";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("id", id)
                    .addParameter("type", DATABASE_TYPE)
                    .executeAndFetchFirst(EndangeredAnimal.class);
        }
    }

    public static List<EndangeredAnimal> search(String search) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals WHERE name ~* :search AND type = :type;";
            return con.createQuery(sql)
                    .throwOnMappingFailure(false)
                    .addParameter("search", ".*" + search + ".*")
                    .addParameter("type", DATABASE_TYPE)
                    .executeAndFetch(EndangeredAnimal.class);
        }
    }


    @Override
    public boolean equals(Object otherObject) {
        if (!(otherObject instanceof EndangeredAnimal)) {
            return false;
        } else {
            EndangeredAnimal otherAnimal = (EndangeredAnimal) otherObject;
            return this.getName().equals(otherAnimal.getName()) &&
                    this.getId() == otherAnimal.getId();
        }
    }

}

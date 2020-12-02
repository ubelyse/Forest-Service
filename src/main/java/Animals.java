import org.sql2o.Connection;

import java.util.List;

public class Animals {

    private String name;
    private String age;
    private String endangered;
    private String healthy;
    private int id;

    public Animals(String name, String age, String endangered,String  healthy) {
        this.name = name;
        this.age = age;
        this.endangered = endangered;
        this.healthy = healthy;
    }

    public String getName() {
        return name;
    }

    public String getAge() {
        return age;
    }

    public String getEndangered() {
        return endangered;
    }

    public String getHealthy() {
        return healthy;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof Animals)) {
            return false;
        } else {
            Animals newAnimal = (Animals) o;
            return this.getName().equals(newAnimal.getName()) &&
                    this.getAge().equals(newAnimal.getAge())&&
                    this.getEndangered().equals(newAnimal.getEndangered())&&
                    this.getHealthy().equals(newAnimal.getHealthy());
        }
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name, age,endangered,healthy) VALUES (:name, :age,:endangered,:healthy)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("age", this.age)
                    .addParameter("endangered", this.endangered)
                    .addParameter("healthy", this.healthy)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Animals> all() {
        String sql = "SELECT * FROM animals";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Animals.class);
        }
    }

    public static Animals find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            Animals Animal = con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animals.class);
            return Animal;
        }
    }
}

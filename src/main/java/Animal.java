import org.sql2o.Connection;

import java.util.List;
import java.util.Objects;

public class Animal {
    private String name;
    private String age;
    private String endangered;
    private String healthy;
    private int id;

    public Animal(String name, String age, String endangered, String healthy) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return name.equals(animal.name) &&
                age.equals(animal.age) &&
                endangered.equals(animal.endangered) &&
                healthy.equals(animal.healthy);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, endangered, healthy);
    }

    public void save() {
        try(Connection con = DB.sql2o.open()) {
            String sql = "INSERT INTO animals (name,age,endangered,healthy) VALUES (:name,:age,:endangered,:healthy)";
            this.id = (int) con.createQuery(sql, true)
                    .addParameter("name", this.name)
                    .addParameter("age", this.age)
                    .addParameter("endangered", this.endangered)
                    .addParameter("healthy", this.healthy)
                    .executeUpdate()
                    .getKey();
        }
    }

    public static List<Animal> all() {
        String sql = "SELECT * FROM animals";
        try(Connection con = DB.sql2o.open()) {
            return con.createQuery(sql).executeAndFetch(Animal.class);
        }
    }

    public int getId() {
        return id;
    }

    public static Animal find(int id) {
        try(Connection con = DB.sql2o.open()) {
            String sql = "SELECT * FROM animals where id=:id";
            Animal animal= con.createQuery(sql)
                    .addParameter("id", id)
                    .executeAndFetchFirst(Animal.class);
            return animal;
        }
    }

}

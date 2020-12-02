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
}

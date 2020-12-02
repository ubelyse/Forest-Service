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
}

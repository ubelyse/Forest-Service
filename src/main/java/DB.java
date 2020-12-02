import org.sql2o.*;

public class DB {
    public static Sql2o sql2o = new Sql2o("jdbc:postgresql://localhost:5432/wildlife_tracker", "belyse", "belyse");
    //public static Sql2o sql2o = new Sql2o("jdbc:postgresql://ec2-174-129-253-86.compute-1.amazonaws.com:5432/d5tkf3dsmrb98s", "tgrzzhuriljlfk", "85d7f9f13c4f92cdcb17bbea55a2d4f2439a4ee39cc3fdb00bff8cee5a4d1254");
}
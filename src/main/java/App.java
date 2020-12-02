import java.sql.Timestamp;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
    private static Map<String, Object> model;

    public static void main(String[] args) {
        staticFileLocation("/public");
        String layout = "templates/layout.vtl";
        model = new HashMap<String, Object>();

        before((request, response) -> {
            model.clear();
        });

        // Home
        get("/", (request, response) -> {
            model.put("rangers", Ranger.all());
            model.put("locations", Location.all());
            List<Animal> allAnimals = new ArrayList<Animal>();
            allAnimals.addAll(EndangeredAnimal.all());
            allAnimals.addAll(RegularAnimal.all());
            model.put("animals", allAnimals);
            model.put("sightings", Sighting.all());
            model.put("template", "templates/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        // Animal
        get("/animals", (request, response) -> {
            model.put("regularanimals", RegularAnimal.all());
            model.put("endangeredanimals", EndangeredAnimal.all());
            model.put("template", "templates/animals/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animals/new", (request, response) -> {
            model.put("template", "templates/animals/new.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animals/edit/:id", (request, response) -> {
            Animal animal = tryFindAnimal(request.params(":id"));
            if(animal == null) {
                response.redirect("/");
            } else {
                model.put("animal", animal);
            }
            model.put("template", "templates/animals/edit.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animals/delete/:id", (request, response) -> {
            Animal animal = tryFindAnimal(request.params(":id"));
            if(animal == null) {
                response.redirect("/");
            } else {
                model.put("animal", animal);
            }
            model.put("template", "templates/animals/delete.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animals/search", (request, response) -> {
            if(request.queryParams("s") != null) {
                String search = request.queryParams("s");
                model.put("endangeredanimals", EndangeredAnimal.search(search));
                model.put("regularanimals", RegularAnimal.search(search));
                model.put("search", search);
            }
            model.put("template", "templates/animals/search.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/animals/:id", (request, response) -> {
            Animal animal = tryFindAnimal(request.params(":id"));
            if(animal == null) {
                response.redirect("/");
            } else {
                model.put("animal", animal);
            }
            model.put("template", "templates/animals/view.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/animals", (request, response) -> {
            String type = request.queryParams("type");
            String name = request.queryParams("name");
            if(type.equals(EndangeredAnimal.DATABASE_TYPE)) {
                String health = request.queryParams("health");
                double age = Double.parseDouble(request.queryParams("age"));
                try {
                    EndangeredAnimal animal = new EndangeredAnimal(name, age, health);
                    animal.save();
                } catch (IllegalArgumentException e) {
                    response.redirect("/animals");
                }
            } else {
                RegularAnimal animal = new RegularAnimal(name);
                animal.save();
            }
            response.redirect("/animals");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/animals/edit", (request, response) -> {
            int id = tryParseInt(request.queryParams("id"));
            String type = request.queryParams("type");
            String name = request.queryParams("name");
            if(type.equals(EndangeredAnimal.DATABASE_TYPE)) {
                String health = request.queryParams("health");
                double age = Double.parseDouble(request.queryParams("age"));
                try {
                    EndangeredAnimal animal = EndangeredAnimal.find(id);
                    animal.setName(name);
                    animal.setHealth(health);
                    animal.setAge(age);
                    animal.update();
                } catch (IllegalArgumentException e) {
                    response.redirect("/animals");
                }
            } else {
                try {
                    RegularAnimal animal = RegularAnimal.find(id);
                    animal.setName(name);
                    animal.update();
                } catch (IllegalArgumentException e) {
                    response.redirect("/animals");
                }
            }
            response.redirect("/animals");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/animals/delete", (request, response) -> {
            Animal animal = tryFindAnimal(request.queryParams("animalId"));
            if(animal != null) {
                animal.delete();
            }
            response.redirect("/animals");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        // Location
        get("/locations", (request, response) -> {
            model.put("locations", Location.all());
            model.put("template", "templates/locations/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/locations/new", (request, response) -> {
            model.put("template", "templates/locations/new.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/locations/edit/:id", (request, response) -> {
            Location location = tryFindLocation(request.params(":id"));
            if(location == null) {
                response.redirect("/");
            } else {
                model.put("location", location);
            }
            model.put("template", "templates/locations/edit.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/locations/delete/:id", (request, response) -> {
            Location location = tryFindLocation(request.params(":id"));
            if(location == null) {
                response.redirect("/");
            } else {
                model.put("location", location);
            }
            model.put("template", "templates/locations/delete.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/locations/search", (request, response) -> {
            if(request.queryParams("s") != null) {
                String search = request.queryParams("s");
                model.put("locations", Location.search(search));
                model.put("search", search);
            }
            model.put("template", "templates/locations/search.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/locations/:id", (request, response) -> {
            Location location = tryFindLocation(request.params(":id"));
            if(location == null) {
                response.redirect("/");
            } else {
                model.put("location", location);
            }
            model.put("template", "templates/locations/view.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/locations", (request, response) -> {
            String name = request.queryParams("name");
            double xCoord = Double.parseDouble(request.queryParams("xcoord"));
            double yCoord = Double.parseDouble(request.queryParams("ycoord"));
            try {
                Location location = new Location(name, xCoord, yCoord);
                location.save();
            } catch (IllegalArgumentException e) {
                response.redirect("/locations");
            }
            response.redirect("/locations");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/locations/edit", (request, response) -> {
            int id = tryParseInt(request.queryParams("id"));
            String name = request.queryParams("name");
            double xCoord = Double.parseDouble(request.queryParams("xcoord"));
            double yCoord = Double.parseDouble(request.queryParams("xcoord"));
            try {
                Location location = Location.find(id);
                location.setName(name);
                location.setXCoord(xCoord);
                location.setYCoord(yCoord);
                location.update();
            } catch (IllegalArgumentException e) {
                response.redirect("/locations");
            }
            response.redirect("/locations");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/locations/delete", (request, response) -> {
            Location location = tryFindLocation(request.queryParams("locationId"));
            if(location != null) {
                location.delete();
            }
            response.redirect("/locations");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        // Ranger
        get("/rangers", (request, response) -> {
            model.put("rangers", Ranger.all());
            model.put("template", "templates/rangers/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/rangers/new", (request, response) -> {
            model.put("template", "templates/rangers/new.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/rangers/edit/:id", (request, response) -> {
            Ranger ranger = tryFindRanger(request.params(":id"));
            if(ranger == null) {
                response.redirect("/");
            } else {
                model.put("ranger", ranger);
            }
            model.put("template", "templates/rangers/edit.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/rangers/delete/:id", (request, response) -> {
            Ranger ranger = tryFindRanger(request.params(":id"));
            if(ranger == null) {
                response.redirect("/");
            } else {
                model.put("ranger", ranger);
            }
            model.put("template", "templates/rangers/delete.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/rangers/search", (request, response) -> {
            if(request.queryParams("s") != null) {
                String search = request.queryParams("s");
                model.put("rangers", Ranger.search(search));
                model.put("search", search);
            }
            model.put("template", "templates/rangers/search.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/rangers/:id", (request, response) -> {
            Ranger ranger = tryFindRanger(request.params(":id"));
            if(ranger == null) {
                response.redirect("/");
            } else {
                model.put("ranger", ranger);
            }
            model.put("template", "templates/rangers/view.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/rangers", (request, response) -> {
            String username = request.queryParams("username");
            String firstname = request.queryParams("firstname");
            String lastname = request.queryParams("lastname");
            int badge = Integer.parseInt(request.queryParams("badge"));
            long phone = Long.parseLong(request.queryParams("phone"));
            try {
                Ranger ranger = new Ranger(username, firstname, lastname, badge, phone);
                ranger.save();
            } catch (IllegalArgumentException e) {
                response.redirect("/rangers");
            }
            response.redirect("/rangers");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/rangers/edit", (request, response) -> {
            int id = tryParseInt(request.queryParams("id"));
            String username = request.queryParams("username");
            String firstname = request.queryParams("firstname");
            String lastname = request.queryParams("lastname");
            int badge = Integer.parseInt(request.queryParams("badge"));
            long phone = Long.parseLong(request.queryParams("phone"));
            try {
                Ranger ranger = Ranger.find(id);
                ranger.setUserName(username);
                ranger.setFirstName(firstname);
                ranger.setLastName(lastname);
                ranger.setPhone(phone);
                ranger.setBadge(badge);
                ranger.update();
            } catch (IllegalArgumentException e) {
                response.redirect("/rangers");
            }
            response.redirect("/rangers");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/rangers/delete", (request, response) -> {
            Ranger ranger = tryFindRanger(request.queryParams("rangerId"));
            if(ranger != null) {
                ranger.delete();
            }
            response.redirect("/rangers");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        // Sighting
        get("/sightings", (request, response) -> {
            model.put("Ranger", Ranger.class);
            model.put("Location", Location.class);
            model.put("Animal", Animal.class);
            model.put("sightings", Sighting.all());
            model.put("template", "templates/sightings/index.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/sightings/new", (request, response) -> {
            model.put("rangers", Ranger.all());
            model.put("locations", Location.all());
            List<Animal> allAnimals = new ArrayList<Animal>();
            allAnimals.addAll(EndangeredAnimal.all());
            allAnimals.addAll(RegularAnimal.all());
            model.put("animals", allAnimals);
            model.put("template", "templates/sightings/new.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/sightings/edit/:id", (request, response) -> {
            Sighting sighting = tryFindSighting(request.params(":id"));
            if(sighting == null) {
                response.redirect("/");
            } else {
                model.put("sighting", sighting);
            }
            model.put("rangers", Ranger.all());
            model.put("locations", Location.all());
            List<Animal> allAnimals = new ArrayList<Animal>();
            allAnimals.addAll(EndangeredAnimal.all());
            allAnimals.addAll(RegularAnimal.all());
            model.put("animals", allAnimals);
            model.put("template", "templates/sightings/edit.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/sightings/delete/:id", (request, response) -> {
            Sighting sighting = tryFindSighting(request.params(":id"));
            if(sighting == null) {
                response.redirect("/");
            } else {
                model.put("sighting", sighting);
            }
            model.put("template", "templates/sightings/delete.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        get("/sightings/:id", (request, response) -> {
            Sighting sighting = tryFindSighting(request.params(":id"));
            if(sighting == null) {
                response.redirect("/");
            } else {
                model.put("sighting", sighting);
            }
            model.put("ranger", Ranger.find(sighting.getRangerId()));
            model.put("location", Location.find(sighting.getRangerId()));
            if(Animal.getAnimalType(sighting.getAnimalId()).equals(EndangeredAnimal.DATABASE_TYPE)) {
                model.put("animal", EndangeredAnimal.find(sighting.getAnimalId()));
            } else {
                RegularAnimal.find(sighting.getAnimalId());
            }
            model.put("template", "templates/sightings/view.vtl");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/sightings", (request, response) -> {
            int rangerId = Integer.parseInt(request.queryParams("rangerId"));
            int locationId = Integer.parseInt(request.queryParams("locationId"));
            int animalId = Integer.parseInt(request.queryParams("animalId"));
            String timeOfSighting = request.queryParams("timeOfSighting");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            cal.setTime(dateFormat.parse(timeOfSighting));
            try {
                Sighting sighting = new Sighting(animalId, locationId, rangerId, new Timestamp(cal.getTimeInMillis()));
                sighting.save();
            } catch (IllegalArgumentException e) {
                response.redirect("/sightings");
            }
            response.redirect("/sightings");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

        post("/sightings/edit", (request, response) -> {
            int id = tryParseInt(request.queryParams("id"));
            int rangerId = Integer.parseInt(request.queryParams("rangerId"));
            int locationId = Integer.parseInt(request.queryParams("locationId"));
            int animalId = Integer.parseInt(request.queryParams("animalId"));
            String timeOfSighting = request.queryParams("timeOfSighting");
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            System.out.println(timeOfSighting);
            cal.setTime(dateFormat.parse(timeOfSighting));
            try {
                Sighting sighting = Sighting.find(id);
                sighting.setAnimalId(animalId);
                sighting.setLocationId(locationId);
                sighting.setRangerId(rangerId);
                sighting.setTimeOfSighting(new Timestamp(cal.getTimeInMillis()));
                sighting.update();
            } catch (IllegalArgumentException e) {
                response.redirect("/sightings");
            }
            response.redirect("/sightings");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());


        post("/sightings/delete", (request, response) -> {
            Sighting sighting = tryFindSighting(request.queryParams("sightingId"));
            if(sighting != null) {
                sighting.delete();
            }
            response.redirect("/sightings");
            return new ModelAndView(model, layout);
        }, new VelocityTemplateEngine());

    }

    private static Animal tryFindAnimal(String id) {
        Integer animalId = tryParseInt(id);
        if(animalId != null && Animal.idExists(animalId)) {
            if(Animal.getAnimalType(animalId).equals(EndangeredAnimal.DATABASE_TYPE)) {
                return EndangeredAnimal.find(animalId);
            } else if (Animal.getAnimalType(animalId).equals(RegularAnimal.DATABASE_TYPE)) {
                return RegularAnimal.find(animalId);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static Location tryFindLocation(String id) {
        Integer locationId = tryParseInt(id);
        if(locationId != null && Location.find(locationId) != null) {
            return Location.find(locationId);
        } else {
            return null;
        }
    }

    private static Ranger tryFindRanger(String id) {
        Integer rangerId = tryParseInt(id);
        if(rangerId != null && Ranger.find(rangerId) != null) {
            return Ranger.find(rangerId);
        } else {
            return null;
        }
    }

    private static Sighting tryFindSighting(String id) {
        Integer sightingId = tryParseInt(id);
        if(sightingId != null && Sighting.find(sightingId) != null) {
            return Sighting.find(sightingId);
        } else {
            return null;
        }
    }

    private static Integer tryParseInt(String toParse) {
        Integer number = null;
        try {
            number = Integer.parseInt(toParse);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing integer: " + e.getMessage());
        }
        return number;
    }

}

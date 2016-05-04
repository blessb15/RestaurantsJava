import java.util.*;
import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      Map model = new HashMap();

      model.put("cuisines", Cuisine.all());
      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/cuisines/:id", (request, response) -> {
      Map model = new HashMap();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));

      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/cuisines/:id/newRestaurant", (request, response) -> {
      Map model = new HashMap();
      Cuisine cuisine = Cuisine.find(Integer.parseInt(request.params(":id")));
      String restName = request.queryParams("restName");
      String restContact = request.queryParams("restContact");
      Restaurant newRestaurant = new Restaurant(restName,restContact);
      newRestaurant.save();
      cuisine.addRestaurant(newRestaurant);
      model.put("cuisine", cuisine);
      model.put("template", "templates/cuisine.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }

}

import org.sql2o.*;
import java.util.List;

public class Cuisine {
  private String type;
  private int id;


  public Cuisine(String type) {
    this.type = type;
  }

  public String getType(){
    return type;
  }

  public int getId() {
    return id;
  }

  public void save(){
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO cuisines (type) VALUES (:type)";
      this.id = (int) con.createQuery(sql, true).addParameter("type", this.type).executeUpdate().getKey();
    }
  }

  public static List<Cuisine> all() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM cuisines";
      return con.createQuery(sql).executeAndFetch(Cuisine.class);
    }
  }

  @Override
  public boolean equals(Object obj){
    if(!(obj instanceof Cuisine)){
      return false;
    } else {
      Cuisine newCuisine = (Cuisine) obj;
      return newCuisine.getType().equals(this.getType());
    }
  }

  public static Cuisine find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM cuisines WHERE id = :id";
      Cuisine foundCuisine = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Cuisine.class);
      return foundCuisine;
    }
  }

  public void addRestaurant(Restaurant rest){
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE restaurants SET cuisine_id=:cuisine_id WHERE id=:restaurant_id";
      con.createQuery(sql).addParameter("cuisine_id", this.id).addParameter("restaurant_id", rest.getId()).executeUpdate();
    }
  }

  public Restaurant getRestaurant(int restId){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM restaurants WHERE cuisine_id=:cuisine_id AND id=:id";
      return con.createQuery(sql).addParameter("cuisine_id", this.id).addParameter("id", restId).executeAndFetchFirst(Restaurant.class);
    }
  }

  public List<Restaurant> getAllRestaurants() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM restaurants WHERE cuisine_id=:cuisine_id";
      return con.createQuery(sql).addParameter("cuisine_id", this.id).executeAndFetch(Restaurant.class);
    }
  }


}

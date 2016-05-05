import org.sql2o.*;
import java.util.List;

public class Restaurant{
  private String name;
  private String contact;
  private int cuisine_id;
  private int id;

  public Restaurant(String name, String contact){
    this.name = name;
    this.contact = contact;
  }

  public String getName(){
    return name;
  }

  public String getContact(){
    return contact;
  }

  public int getId() {
    return id;
  }

  public int getCuisineId(){
    return cuisine_id;
  }

  public static List<Restaurant> all(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "SELECT (name, contact) FROM restaurants";
      return con.createQuery(sql).executeAndFetch(Restaurant.class);
    }
  }

  public void save(){
    try (Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO restaurants (name, contact) VALUES (:name, :contact)";
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("contact", this.contact).executeUpdate().getKey();
    }
  }

  public static Restaurant find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT name, contact, id, cuisine_id FROM restaurants WHERE id=:id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Restaurant.class);
    }
  }

  @Override
  public boolean equals(Object obj){
    if(!(obj instanceof Restaurant)){
      return false;
    } else {
      Restaurant newRest = (Restaurant) obj;
      return newRest.getName().equals(this.getName()) && newRest.getContact().equals(this.getContact()) && newRest.getId() == this.getId();
    }
  }

  public static void delete(Restaurant rest) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants WHERE id=:id";
      con.createQuery(sql).addParameter("id", rest.getId()).executeUpdate();
    }
  }

  public void addReview(Review rev){
    try(Connection con = DB.sql2o.open()){
      String sql = "UPDATE reviews SET restaurant_id=:restaurant_id WHERE id=:review_id";
      con.createQuery(sql).addParameter("restaurant_id", this.id).addParameter("review_id", rev.getId()).executeUpdate();
    }
  }

  public Review getReview(int revId){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM reviews WHERE restaurant_id=:restaurant_id AND id=:id";
      return con.createQuery(sql).addParameter("restaurant_id", this.id).addParameter("id", revId).executeAndFetchFirst(Review.class);
    }
  }

  public List<Review> allReviews(){
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM reviews WHERE restaurant_id=:restaurant_id";
      return con.createQuery(sql).addParameter("restaurant_id", this.id).executeAndFetch(Review.class);
    }
  }


}

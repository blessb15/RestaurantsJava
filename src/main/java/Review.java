import org.sql2o.*;
import java.util.List;

public class Review {
  private String text;
  private String reviewer;
  private int restaurant_id;
  private int id;

  public Review(String text, String reviewer) {
    this.text = text;
    this.reviewer = reviewer;
  }

  public String getReviewer(){
    return reviewer;
  }

  public String getText(){
    return text;
  }

  public int getRestaurantId(){
    return restaurant_id;
  }

  public int getId(){
    return id;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()){
      String sql = "INSERT INTO reviews (restaurant_id, text, reviewer) VALUES (:restaurant_id, :text, :reviewer)";
      this.id = (int) con.createQuery(sql, true).addParameter("text", this.text).addParameter("reviewer", this.reviewer).addParameter("restaurant_id", this.restaurant_id).executeUpdate()
      .getKey();
    }
  }

  public static List<Review> all() {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT restaurant_id, reviewer, text FROM reviews";
      return con.createQuery(sql).executeAndFetch(Review.class);
    }
  }

  public static void delete(Review rev) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM reviews WHERE id=:id";
      con.createQuery(sql).addParameter("id", rev.getId()).executeUpdate();
    }
  }

  public boolean equals(Review rev){
    if(!(rev instanceof Review)){
      return false;
    } else {
      Review newRev = (Review) rev;
      return newRev.getReviewer().equals(this.getReviewer()) && newRev.getText().equals(this.getText()) && newRev.getRestaurantId() == this.getRestaurantId();
    }
  }

  public static Review find(int id) {
    try(Connection con = DB.sql2o.open()){
      String sql = "SELECT * FROM reviews WHERE id = :id";
      return con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Review.class);
    }
  }


}

import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class CuisineTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/restaurant_data_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM cuisines *;";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Cuisine_InstantiateCuisinesCorrectly() {
    Cuisine testCuisine = new Cuisine("Mexican");
    assertTrue(testCuisine instanceof Cuisine);
  }

  @Test
  public void Cuisine_InstantiateCuisinesWithName_Mexican() {
    Cuisine testCuisine = new Cuisine("Mexican");
    assertEquals(testCuisine.getType(), "Mexican");
  }

  @Test
  public void Cuisine_SaveToCuisinesTableInDatabase() {
    Cuisine testCuisine = new Cuisine("Mexican");
    testCuisine.save();
    assertEquals(1, Cuisine.all().size());
  }

  @Test
  public void Cuisine_CuisineObjectsEqual(){
    Cuisine cuisine1 = new Cuisine("Mexican");
    Cuisine cuisine2 = new Cuisine("Mexican");
    assertTrue(cuisine1.equals(cuisine2));
  }

  @Test
  public void Cuisine_returnCuisineInsideofDataBase(){
    Cuisine newCuisine = new Cuisine("Italian");
    newCuisine.save();
    assertTrue(newCuisine.equals(Cuisine.find(newCuisine.getId())));
  }

  @Test
  public void Cuisine_AddRestaurantObjectToCuisine(){
    Cuisine newCuisine = new Cuisine("Italian");
    Restaurant testRant = new Restaurant("Billys", "562-334-8970");
    newCuisine.save();
    testRant.save();
    newCuisine.addRestaurant(testRant);
    Cuisine foundCuisine = Cuisine.find(newCuisine.getId());
    assertTrue(testRant.equals(foundCuisine.getRestaurant(testRant.getId())));
  }

  @Test
  public void Cuisine_returnAllCuisineRestaurants(){
    Cuisine newCuisine = new Cuisine("Italian");
    Restaurant testRant = new Restaurant("Billys", "562-334-8970");
    newCuisine.save();
    testRant.save();
    newCuisine.addRestaurant(testRant);
    System.out.println(newCuisine.getAllRestaurants().get(0).getName());
    assertTrue(newCuisine.getAllRestaurants().contains(testRant));
  }


}

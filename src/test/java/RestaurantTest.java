import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;
import java.util.List;

public class RestaurantTest{

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/restaurant_data_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "DELETE FROM restaurants *";
      con.createQuery(sql).executeUpdate();
    }
  }

  @Test
  public void Restaurant_restaurantInstantiates() {
    Restaurant billys = new Restaurant("billys","503-333-3332");
    assertTrue(billys instanceof Restaurant);
  }

  @Test
  public void Restaurant_restaurantInstantiateswithName() {
    Restaurant willys = new Restaurant("willys","360-244-3432");
    assertEquals("willys", willys.getName());
  }

  @Test
  public void Restaurant_checkrestaurantSavestoDataBase() {
    Restaurant gillys = new Restaurant("gillys","999-234-1234");
    gillys.save();
    assertEquals(1, Restaurant.all().size());
  }

  @Test
  public void Restaurant_restaurantObjectsEqual() {
    Restaurant rest1 = new Restaurant("jillys","562-245-4970");
    Restaurant rest2 = new Restaurant("jillys","562-245-4970");
    assertTrue(rest1.equals(rest2));
  }

  @Test
  public void Restaurant_findRestaurantObjectinDatabase() {
    Restaurant jims = new Restaurant("jims", "320-123-4414");
    jims.save();
    assertTrue(jims.equals(Restaurant.find(jims.getId())));
  }
}

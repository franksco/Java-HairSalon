import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class ClientTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/hair_salon_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteClientQuery = "DELETE FROM client *;";
      String deleteStylistQuery = "DELETE FROM stylist *;";
      con.createQuery(deleteClientQuery).executeUpdate();
      con.createQuery(deleteStylistQuery).executeUpdate();
    }
  }

  @Test
  public void Client_instantiatesCorrectly_true() {
    Client myClient = new Client("Bob", 1);
    assertEquals(true, myClient instanceof Client);
  }


  @Test
  public void all_emptyAtFirst() {
    assertEquals(0, Client.all().size());
  }

  @Test
  public void equals_returnsTrueIfNamesAreTheSame() {
    Client firstClient = new Client("Bob", 1);
    Client secondClient = new Client("Bob", 1);
    assertTrue(firstClient.equals(secondClient));
  }

  @Test
  public void save_returnsTrueIfNamesAreTheSame() {
    Client myClient = new Client("Bob", 1);
    myClient.save();
    assertTrue(Client.all().get(0).equals(myClient));
  }

  @Test
  public void save_assignsIdToObject() {
    Client myClient = new Client("Bob", 1);
    myClient.save();
    Client savedClient = Client.all().get(0);
    assertEquals(myClient.getId(), savedClient.getId());
  }

  @Test
  public void find_findsClientInDatabase_true() {
    Client myClient = new Client ("Bob", 1);
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertTrue(myClient.equals(savedClient));
  }

  @Test
  public void save_savesStylistIdIntoDB_true() {
    Stylist myStylist = new Stylist("Jimmy", "men", "male");
    myStylist.save();
    Client myClient = new Client("Bob", myStylist.getId());
    myClient.save();
    Client savedClient = Client.find(myClient.getId());
    assertEquals(savedClient.getStylistId(), myStylist.getId());
  }
}

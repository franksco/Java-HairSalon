
import org.fluentlenium.adapter.FluentTest;
import org.junit.ClassRule;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import static org.fluentlenium.core.filter.FilterConstructor.*;
import static org.assertj.core.api.Assertions.assertThat;
import org.sql2o.*;
import org.junit.*;

public class AppTest extends FluentTest {
  public WebDriver webDriver = new HtmlUnitDriver();

  @Override
  public WebDriver getDefaultDriver() {
    return webDriver;
  }

  @ClassRule
  public static ServerRule server = new ServerRule();

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
  public void rootTest() {
    goTo("http://localhost:4567");
    assertThat(pageSource()).contains("Scissors N'Razors");
  }

  @Test
  public void addStylistAndCheckForIt() {
    goTo("http://localhost:4567");
    click("a", withText("Add a new Stylist here:"));
    fill("#name").with("Sally");
    fill("#specialty").with("women");
    fill("#gender").with("female");
    submit(".btn");
    assertThat(pageSource()).contains("Sally");
  }

  @Test
  public void addClientToStylist() {
    goTo("http://localhost:4567");
    click("a", withText("Add a new Stylist here:"));
    fill("#name").with("Sally");
    fill("#specialty").with("women");
    fill("#gender").with("female");
    submit(".btn");
    click("a", withText("Sally"));
    fill("#name").with("Cindy");
    submit(".btn");
    assertThat(pageSource()).contains("Sally");
  }

}

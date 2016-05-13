import java.util.List;
import org.sql2o.*;

public class Stylist {
  private int id;
  private String name;
  private String specialty;
  private String gender;

  public Stylist(String name, String specialty, String gender) {
    this.name = name;
    this.specialty = specialty;
    this.gender = gender;
  }

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getSpecialty() {
    return specialty;
  }

  public String getGender() {
    return gender;
  }

  public static List<Stylist> all() {
    String sql = "SELECT id, name, specialty FROM stylist";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Stylist.class);
    }
  }

  @Override
  public boolean equals(Object otherStylist) {
    if (!(otherStylist instanceof Stylist)) {
      return false;
    } else {
      Stylist newStylist = (Stylist) otherStylist;
      return this.getName().equals(newStylist.getName());
    }
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO stylist(name, specialty, gender) VALUES (:name, :specialty, :gender)";
      this.id = (int) con.createQuery(sql, true)
        .addParameter("name", this.name)
        .addParameter("specialty", this.specialty)
        .addParameter("gender", this.gender)
        .executeUpdate()
        .getKey();
    }
  }

  public static Stylist find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM stylist where id = :id";
      Stylist stylist = con.createQuery(sql)
        .addParameter("id", id)
        .executeAndFetchFirst(Stylist.class);
      return stylist;
    }
  }

  public List<Client> getClients() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM client  where stylistid = :stylistid";
      return con.createQuery(sql)
        .addParameter("stylistid", this.id)
        .executeAndFetch(Client.class);
    }
  }
}

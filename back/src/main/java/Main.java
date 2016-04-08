import net.codestory.http.WebServer;

public class Main {
  public static void main(String[] args) {
    new WebServer().configure(routes -> routes
      .get("/verbe", "achete")
      .get("/adjectif", "brillant")
      .get("/nom", "le joueur")
    ).start();
  }
}

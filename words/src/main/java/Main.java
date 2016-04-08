import com.docker.lab.db.AllAdjectives;
import com.docker.lab.db.AllNouns;
import com.docker.lab.db.AllVerbs;
import com.docker.lab.db.Db;
import net.codestory.http.WebServer;
import net.codestory.http.filters.log.*;

import java.util.List;
import java.util.Random;

public class Main {
  public static void main(String[] args) {
    Random random = new Random();
    AllVerbs allVerbs = new AllVerbs();
    AllNouns allNouns = new AllNouns();
    AllAdjectives allAdjectives = new AllAdjectives();

    new Db().init();

    new WebServer().configure(routes -> routes
    .filter(LogRequestFilter.class)
      .get("/verb", () -> {
        List<String> verbs = allVerbs.all();
        return verbs.get(random.nextInt(verbs.size()));
      })
      .get("/adjective", () -> {
        List<String> adjectives = allAdjectives.all();
        return adjectives.get(random.nextInt(adjectives.size()));
      })
      .get("/noun", () -> {
        List<String> nouns = allNouns.all();
        return nouns.get(random.nextInt(nouns.size()));
      })
    ).start();
  }
}

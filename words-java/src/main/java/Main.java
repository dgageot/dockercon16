import com.mongodb.DB;
import com.mongodb.MongoClient;
import net.codestory.http.WebServer;
import net.codestory.http.filters.log.*;
import org.jongo.Jongo;

import java.util.List;
import java.util.Random;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) {
        // see https://github.com/bguerout/jongo/issues/289
        DB db = new MongoClient("mongo:27017").getDB("lab-docker");
        Jongo jongo = new Jongo(db);

        Words verbs = new Words(jongo, "verbs");
        Words nouns = new Words(jongo, "nouns");
        Words adjectives = new Words(jongo, "adjectives");

        verbs.addIfEmpty("boiras", "explose", "fait fumer", "endors", "marche vers");
        nouns.addIfEmpty("le cadavre", "l'éléphant", "le langage scala", "le laptop");
        adjectives.addIfEmpty("exquis", "rose", "un peu pourri", "rouge", "flottant", "du programmeur");

        new WebServer().configure(routes -> routes
                .filter(LogRequestFilter.class)
                .get("/verb", () -> random(verbs.all()))
                .get("/adjective", () -> random(adjectives.all()))
                .get("/noun", () -> random(nouns.all()))
        ).start();
    }

    private static String random(List<String> values) {
        return values.get(random.nextInt(values.size()));
    }
}

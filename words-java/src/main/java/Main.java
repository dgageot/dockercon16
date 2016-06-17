import com.mongodb.DB;
import com.mongodb.MongoClient;
import net.codestory.http.WebServer;
import net.codestory.http.filters.log.*;
import org.jongo.Jongo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Random;

public class Main {
    private static final Random random = new Random();

    public static void main(String[] args) throws UnknownHostException {
        String hostname = InetAddress.getLocalHost().getHostName();

        WordResponse verb = new WordResponse(randomWord("verbs"), hostname);
        WordResponse adjective = new WordResponse(randomWord("adjectives"), hostname);
        WordResponse noun = new WordResponse(randomWord("nouns"), hostname);

        new WebServer().configure(routes -> routes
                .filter(LogRequestFilter.class)
                .get("/verb", verb)
                .get("/adjective", adjective)
                .get("/noun", noun)
        ).start();
    }

    private static String randomWord(String set) {
        // see https://github.com/bguerout/jongo/issues/289
        DB db = new MongoClient("mongo:27017").getDB("lab-docker");

        Jongo jongo = new Jongo(db);
        Words words = new Words(jongo, set);

        switch (set) {
            case "nouns":
                words.addIfEmpty("dead body", "elephant", "go language", "laptop", "container", "micro-service");
                break;
            case "verbs":
                words.addIfEmpty("will drink", "smashes", "smokes", "eats", "walks towards", "loves", "helps");
                break;
            case "adjectives":
                words.addIfEmpty("the exquisite", "a pink", "the rotten", "a red", "the floating", "a broken", "a shiny", "the pretty");
                break;
        }

        Random random = new Random();
        List<String> all = words.all();
        return all.get(random.nextInt(all.size()));
    }

    public static class WordResponse {
        String word;
        String hostname;

        WordResponse(String word, String hostname) {
            this.word = word;
            this.hostname = hostname;
        }
    }
}

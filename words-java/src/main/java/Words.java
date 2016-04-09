import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.util.List;

import static net.codestory.Fluent.*;

public class Words {
    private final MongoCollection collection;

    public Words(Jongo jongo, String name) {
        collection = jongo.getCollection(name);
        collection.ensureIndex("{name:1}");
    }

    public void addIfEmpty(String... names) {
        if (collection.count() == 0) {
            collection.insert(of(names).map(Word::new).toArray(Word[]::new));
        }
    }

    public List<String> all() {
        return of(collection.find().as(Word.class).iterator()).map(w -> w.name).toList();
    }

    static class Word {
        ObjectId _id;
        String name;

        Word() {
        }

        Word(String name) {
            this.name = name;
        }
    }
}

package com.docker.lab.db;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.bson.types.ObjectId;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.util.List;

import static net.codestory.Fluent.*;

public abstract class AllWords {
    private final MongoCollection collection;

    public AllWords(String collectionName) {
        // see https://github.com/bguerout/jongo/issues/289
        DB db = new MongoClient("mongo:27017").getDB("lab-docker");
        Jongo jongo = new Jongo(db);
        collection = jongo.getCollection(collectionName);
        collection.ensureIndex("{name:1}");
    }

    public List<String> all() {
        return of(collection.find().as(Word.class).iterator()).map(w -> w.name).toList();
    }

    public void add(String name) {
        Word word = new Word(name);
        collection.insert(word);
    }

    public void addAll(String... names) {
        collection.insert(of(names).map(Word::new).toArray(Word[]::new));
    }

    public void reset() {
        collection.drop();
    }

    public static class Word {
        public ObjectId _id;
        public String name;

        public Word(){}

        public Word(String name){
            this.name = name;
        }
    }
}

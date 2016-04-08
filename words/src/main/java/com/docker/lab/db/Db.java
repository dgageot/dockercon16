package com.docker.lab.db;

public class Db {

    public void init() {
        AllAdjectives allAdjectives = new AllAdjectives();
        allAdjectives.reset();
        allAdjectives.addAll("exquis", "rose", "un peu pourri", "rouge", "flottant", "le programmeur");
        AllNouns allNouns = new AllNouns();
        allNouns.reset();
        allNouns.addAll("le cadavre", "l'éléphant", "le langage scala", "le laptop");
        AllVerbs allVerbs = new AllVerbs();
        allVerbs.reset();
        allVerbs.addAll("boiras", "explose", "fait fumer", "endors", "marche vers");
    }

    public static void main(String[] args) {
        new Db().init();
    }

}

package com.docker.lab.db;

public class Db {

    public void init() {
        AllAdjectives allAdjectives = new AllAdjectives();
        allAdjectives.reset();
        allAdjectives.addAll("exquis", "grand", "petit", "rouge", "flottant");
        AllNouns allNouns = new AllNouns();
        allNouns.reset();
        allNouns.addAll("le cadavre", "l'éléphant", "la rose", "le tuba");
        AllVerbs allVerbs = new AllVerbs();
        allVerbs.reset();
        allVerbs.addAll("boiras", "s'eteins", "rougit", "s'endors", "marche");
    }

    public static void main(String[] args) {
        new Db().init();
    }

}

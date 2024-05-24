package org.example.crazyjoesworld;

public class Singleton {
    private static Singleton instance = null;
    private int lautstärke;
    private int sensibilitaet;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public int getLautstärke() {
        return lautstärke;
    }

    public void setLautstärke(int lautstärke) {
        this.lautstärke = lautstärke;
    }

    public int getSensibilitaet() {
        return sensibilitaet;
    }

    public void setSensibilitaet(int sensibilitaet) {
        this.sensibilitaet = sensibilitaet;
    }
}

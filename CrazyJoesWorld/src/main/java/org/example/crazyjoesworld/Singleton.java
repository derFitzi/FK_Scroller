package org.example.crazyjoesworld;

public class Singleton {
    private static Singleton instance;
    private double lautstaerke=0.5;
    private double sensi=1;

    private Singleton() {
    }

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    public double getLautstaerke() {
        return lautstaerke;
    }

    public void setLautstaerke(double lautstaerke) {
        this.lautstaerke = lautstaerke;
    }

    public double getSensi() {
        return sensi;
    }

    public void setSensi(double sensi) {
        this.sensi = sensi;
    }
}
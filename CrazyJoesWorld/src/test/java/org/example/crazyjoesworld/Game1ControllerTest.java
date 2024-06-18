package org.example.crazyjoesworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Game1ControllerTest {
    private Game1Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Game1Controller();
    }

    @Test
    void checkaktuelleVelocity() {
        assertEquals(0, controller.getVelocity());
    }
    @Test
    void checkSpielerVorhanden() {
        assertEquals(1, controller.getaktuellesLevle());
    }

    @Test
    void checkAktuellesLevel() {
        assertEquals(1, controller.getaktuellesLevle());
    }

    @Test
    void checkStartCoins() {
        assertEquals(null, controller.getCoins());

    }

    @Test
    void checkStartGeschwindigkeit() {
        assertEquals(12, controller.getPlayerSpeed());
    }

    @Test
    void checkStartSensibilitaet() {

        assertEquals(1, controller.getSensi());
    }
}
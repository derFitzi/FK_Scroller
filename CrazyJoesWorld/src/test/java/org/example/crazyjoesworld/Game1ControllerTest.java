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
    void testLevelauswahl() {
        int level = 1; // oder ein anderer Wert
        controller.levelauswahl(level);
        // Überprüft, ob das ausgewählte Level korrekt gesetzt wurde
        assertEquals(level, controller.getAktuellesLevel(), "AktuellesLevel should be set to the given level after calling levelauswahl");
    }

    @Test
    void testInitialize() {
        controller.initialize();
        assertNotNull(controller.getPlayer(), "Player should be initialized after calling initialize");
    }


    @Test
    void testZumHauptmenue() {
        // Überprüft, ob die Musik gestoppt wurde und ob der KeyListener entfernt wurde
        controller.zumHauptmenue();
        assertFalse(controller.getMusic().isAutoPlay(), "Music should be stopped after calling zumHauptmenue");
        assertNull(controller.getGame1_pane().getOnKeyPressed(), "OnKeyPressed should be null after calling zumHauptmenue");
        assertNull(controller.getGame1_pane().getOnKeyReleased(), "OnKeyReleased should be null after calling zumHauptmenue");
    }

    @Test
    void testLautsterkenregelung() {
        double lautstaerke = 0.5; // oder ein anderer Wert
        controller.getLautstaerkeSlider().setValue(lautstaerke * 200);
        controller.lautsterkenregelung();
        // Überprüft, ob die Lautstärke der Musik korrekt eingestellt wurde
        assertEquals(lautstaerke, controller.getMusic().getVolume(), "Music volume should be set to the given value after calling lautsterkenregelung");
    }

    @Test
    void testSensibilitaetregelung() {
        double sensi = 0.5; // oder ein anderer Wert
        controller.getSensibilitaetSlider().setValue(sensi * 100);
        controller.sensibilitaetregelung();
        // Überprüft, ob die Sensibilität korrekt eingestellt wurde
        assertEquals(sensi, controller.getSensi(), "Sensi should be set to the given value after calling sensibilitaetregelung");
    }
}
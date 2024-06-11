package org.example.crazyjoesworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MainControllerTest {
    private MainController controller;

    @BeforeEach
    void setUp() {
        controller = new MainController();
        // Hier können Sie zusätzliche Setup-Logik hinzufügen, wenn nötig
    }

    @Test
    void testPlay() {
        controller.play();
        // Überprüfen Sie, ob nach dem Aufruf von play die erwarteten Änderungen vorgenommen wurden
        assertFalse(controller.getPlay().isVisible(), "Play button should be invisible after calling play");
        assertTrue(controller.getGameMusic().isPlaying(), "Game music should be playing after calling play");
    }

    @Test
    void testSettings() {
        controller.settings();
        // Überprüfen Sie, ob nach dem Aufruf von settings die erwarteten Änderungen vorgenommen wurden
        assertFalse(controller.getPlay().isVisible(), "Play button should be invisible after calling settings");
        assertTrue(controller.getLautstaerke().isVisible(), "Lautstaerke slider should be visible after calling settings");
    }

    @Test
    void testLautsterkenregelung() {
        double lautstaerke = 0.5; // oder ein anderer Wert
        controller.getLautstaerke().setValue(lautstaerke * 200);
        controller.lautsterkenregelung();
        // Überprüfen Sie, ob die Lautstärke der Musik korrekt eingestellt wurde
        assertEquals(lautstaerke, controller.getMainMenueMusic().getVolume(), "Main menu music volume should be set to the given value after calling lautsterkenregelung");
    }

    @Test
    void testSensibilitaetregelung() {
        double sensi = 0.5; // oder ein anderer Wert
        controller.getSensibilitaet().setValue(sensi * 100);
        controller.sensibilitaetregelung();
        // Überprüfen Sie, ob die Sensibilität korrekt eingestellt wurde
        assertEquals(sensi, Singleton.getInstance().getSensi(), "Sensi should be set to the given value after calling sensibilitaetregelung");
    }

    @Test
    void testGame1() {
        controller.game1();
        // Überprüfen Sie, ob nach dem Aufruf von game1 die erwarteten Änderungen vorgenommen wurden
        assertFalse(controller.getGameMusic().isPlaying(), "Game music should be stopped after calling game1");
    }
}
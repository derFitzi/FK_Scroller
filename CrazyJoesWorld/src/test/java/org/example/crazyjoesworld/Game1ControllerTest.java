package org.example.crazyjoesworld;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Game1ControllerTest {
    private Game1Controller controller;

    @BeforeEach
    void setUp() {
        controller = new Game1Controller();
        controller.initialize();
    }

    @Test
    void testPlayerNotNullAfterInitialization() {
        assertNotNull(controller.getPlayer(), "Player should be initialized after calling initialize");
    }

    @Test
    void testInitialPlayerPosition() {
        assertEquals(750, controller.getPlayer().getTranslateX(), "Initial player X position should be 750");
        assertEquals(100, controller.getPlayer().getTranslateY(), "Initial player Y position should be 100");
    }

    @Test
    void testInitialGamePaneNotNull() {
        assertNotNull(controller.getGame1_pane(), "Game pane should be initialized after calling initialize");
    }

    @Test
    void testInitialMusicNotNull() {
        assertNotNull(controller.getMusic(), "Music should be initialized after calling initialize");
    }

    @Test
    void testInitialLevel() {
        assertEquals(1, controller.getAktuellesLevel(), "Initial level should be 1");
    }
}
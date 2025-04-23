package org.example.launcher;

import org.example.launcher.gui.GuiLauncher;
import org.example.launcher.headless.HeadlessLauncher;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * LauncherFactoryTest checks that the factory returns the correct
 * Launcher implementation based on the `-run-silent` flag.
 */
class LauncherFactoryTest {

    @Test
    void testMakeLoud() {
        final String[] loadArgs = {};
        final Launcher loudLauncher = LauncherFactory.make(loadArgs);
        assertEquals(GuiLauncher.class, loudLauncher.getClass());
    }

    @Test
    void testMakeSilent() {
        final String[] silentArgs = {"-run-silent"};
        final Launcher silentLauncher = LauncherFactory.make(silentArgs);
        assertEquals(HeadlessLauncher.class, silentLauncher.getClass());
    }
}
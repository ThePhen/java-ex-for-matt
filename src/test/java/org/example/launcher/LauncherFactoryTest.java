package org.example.launcher;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LauncherFactoryTest {

    @Test
    void testMakeLoud() {
        final String[] loadArgs = {};
        final Launcher loudLauncher = LauncherFactory.make(loadArgs);
        assertEquals(GuiAppLauncher.class, loudLauncher.getClass());
    }

    @Test
    void testMakeSilent() {
        final String[] silentArgs = {"-run-silent"};
        final Launcher silentLauncher = LauncherFactory.make(silentArgs);
        assertEquals(HeadlessAppLauncher.class, silentLauncher.getClass());
    }
}
package org.example;

import org.example.launcher.GuiAppLauncher;
import org.example.launcher.HeadlessAppLauncher;
import org.example.launcher.Launcher;

public class App {

    public static void main(String[] args) {
        final JobContext ctx = CmdArgsJobContext.make(args);
        Launcher w = ctx.isRunningSilent() ? new HeadlessAppLauncher(ctx) : new GuiAppLauncher(ctx);
        w.start();
    }
}
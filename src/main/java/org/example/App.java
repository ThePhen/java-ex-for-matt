package org.example;

import org.example.jobcontext.CmdArgsJobContext;
import org.example.jobcontext.JobContext;
import org.example.launcher.GuiAppLauncher;
import org.example.launcher.HeadlessAppLauncher;
import org.example.launcher.Launcher;

public class App {

    public static void main(String[] args) {
        final JobContext ctx = new CmdArgsJobContext(args);
        Launcher w = ctx.isRunningSilent() ? new HeadlessAppLauncher(ctx) : new GuiAppLauncher(args);
        w.start();
    }
}
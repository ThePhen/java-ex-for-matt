package org.example.launcher;

import org.example.jobcontext.CmdArgsJobContext;
import org.example.jobcontext.JobContext;

public class LauncherFactory {
    public static Launcher make(String[] args) {
        final JobContext ctx = new CmdArgsJobContext(args);
        return ctx.isRunningSilent() ? new HeadlessAppLauncher(ctx) : new GuiAppLauncher(args);
    }
}

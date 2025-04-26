package org.example.launcher;

import org.example.jobcontext.BaseJobContext;
import org.example.jobcontext.CmdArgsJobContext;
import org.example.jobcontext.EnvVarJobContext;
import org.example.jobcontext.JobContext;

public class LauncherFactory {
    public static Launcher make(String[] args) {
        final JobContext baseCtx = new BaseJobContext();
        final JobContext envCtx = new EnvVarJobContext(baseCtx);
        final JobContext ctx = new CmdArgsJobContext(args, envCtx);
        return ctx.isRunningSilent() ? new HeadlessAppLauncher(ctx) : new GuiAppLauncher(ctx);
    }
}

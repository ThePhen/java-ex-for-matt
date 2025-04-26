package org.example.launcher;

import org.example.jobcontext.BaseJobContext;
import org.example.jobcontext.CmdArgsJobContext;
import org.example.jobcontext.EnvVarJobContext;
import org.example.jobcontext.JobContext;
import org.example.launcher.gui.GuiLauncher;
import org.example.launcher.headless.HeadlessLauncher;

public enum LauncherFactory {
    ;

    public static Launcher make(String[] args) {
        JobContext baseCtx = new BaseJobContext();
        JobContext envCtx = new EnvVarJobContext(baseCtx);
        JobContext ctx = new CmdArgsJobContext(args, envCtx);
        //FIXME should this really be using a `--headless` flag, instead?
        return ctx.isRunningSilent() ? new HeadlessLauncher(ctx) : new GuiLauncher(ctx);
    }
}

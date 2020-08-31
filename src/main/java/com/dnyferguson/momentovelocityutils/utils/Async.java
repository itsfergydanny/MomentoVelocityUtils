package com.dnyferguson.momentovelocityutils.utils;

import com.dnyferguson.momentovelocityutils.MomentoVelocityUtils;

public class Async {
    public static void schedule(MomentoVelocityUtils plugin, Runnable runnable) {
        plugin.getServer().getScheduler().buildTask(plugin, runnable).schedule();
    }
}

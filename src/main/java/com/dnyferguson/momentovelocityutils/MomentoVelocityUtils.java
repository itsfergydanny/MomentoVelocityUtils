package com.dnyferguson.momentovelocityutils;

import com.dnyferguson.momentovelocityutils.commands.IsMyServerDeadCommand;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;

@Plugin(
        id = "momentovelocityutils",
        name = "MomentoVelocityUtils",
        version = "1.0-SNAPSHOT"
)
public class MomentoVelocityUtils {
    private final ProxyServer proxy;
    private final Logger logger;

    @Inject
    public MomentoVelocityUtils(ProxyServer proxy, Logger logger) {
        this.proxy = proxy;
        this.logger = logger;

        logger.info("Hello there, it's a test plugin I made!");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        // register commands
        CommandManager cm = proxy.getCommandManager();
        cm.register(new IsMyServerDeadCommand(this), "ismyserverdead");
        logger.info("Hi mate im registering commands");
    }

    public ProxyServer getProxy() {
        return proxy;
    }

    public Logger getLogger() {
        return logger;
    }
}

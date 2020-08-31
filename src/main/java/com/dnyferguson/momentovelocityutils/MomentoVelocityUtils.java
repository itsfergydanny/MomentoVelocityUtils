package com.dnyferguson.momentovelocityutils;

import com.dnyferguson.momentovelocityutils.commands.IsMyServerDeadCommand;
import com.dnyferguson.momentovelocityutils.config.Configuration;
import com.dnyferguson.momentovelocityutils.listeners.PlayerKickedListener;
import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Plugin(
        id = "momentovelocityutils",
        name = "MomentoVelocityUtils",
        version = "1.0-SNAPSHOT"
)
public class MomentoVelocityUtils {
    private final ProxyServer server;
    private final Logger logger;
    private final Path folder;
    private Configuration config;

    @Inject
    public MomentoVelocityUtils(ProxyServer server, Logger logger, @DataDirectory Path folder) {
        this.server = server;
        this.logger = logger;
        this.folder = folder;

        logger.info("Hello there, it's a test plugin I made!");
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        loadConfig();
        // debug
        logger.info("Config loaded. Test value = " + config.isTest());
        logger.info("Mysql config = " + config.getMysql().toString());
        logger.info("kick reasons = " + config.getKicking().listReasons());
        // end of debug

        // register events
        EventManager em = server.getEventManager();
        em.register(this, new PlayerKickedListener(this));


        // register commands
        CommandManager cm = server.getCommandManager();
        cm.register(new IsMyServerDeadCommand(this), "ismyserverdead");
        logger.info("Hi mate im registering commands");
    }

    @Subscribe
    public void proxyReload(ProxyReloadEvent event) {
        // could possibly do stuff on proxy reload
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        // could possibly do stuff on proxy shutdown
    }

    private void loadConfig() {
        File folder = getFolder().toFile();
        File file = new File(folder, "config.yml");
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        if (!file.exists()) {
            try (InputStream input = getClass().getResourceAsStream("/" + file.getName())) {
                if (input != null) {
                    Files.copy(input, file.toPath());
                } else {
                    file.createNewFile();
                }
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }

        Yaml yaml = new Yaml();
        Path path = Paths.get(folder.getAbsolutePath() + "/config.yml");
        try(InputStream in = Files.newInputStream(path)) {
            config = yaml.loadAs(in, Configuration.class);
            System.out.println(config.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ProxyServer getServer() {
        return server;
    }

    public Logger getLogger() {
        return logger;
    }

    public Path getFolder() {
        return folder;
    }

    public Configuration getConfig() {
        return config;
    }
}

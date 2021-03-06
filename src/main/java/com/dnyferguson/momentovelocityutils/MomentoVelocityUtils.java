package com.dnyferguson.momentovelocityutils;

import com.dnyferguson.momentovelocityutils.commands.IsMyServerDeadCommand;
import com.dnyferguson.momentovelocityutils.config.Configuration;
import com.dnyferguson.momentovelocityutils.listeners.PlayerConnectToServerListener;
import com.dnyferguson.momentovelocityutils.listeners.PlayerKickedListener;
import com.dnyferguson.momentovelocityutils.mysql.MySQL;
import com.dnyferguson.momentovelocityutils.tasks.RejoinTask;
import com.dnyferguson.momentovelocityutils.utils.Chat;
import com.google.inject.Inject;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.EventManager;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyReloadEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.slf4j.Logger;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

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
    private MySQL sql;
    private final Map<UUID, String> previousServer = new ConcurrentHashMap<>();

    @Inject
    public MomentoVelocityUtils(ProxyServer server, Logger logger, @DataDirectory Path folder) {
        this.server = server;
        this.logger = logger;
        this.folder = folder;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        loadConfig();

        sql = new MySQL(this);

        // Register Events
        EventManager em = server.getEventManager();
        em.register(this, new PlayerKickedListener(this));
        em.register(this, new PlayerConnectToServerListener(this));


        // Register Commands
        CommandManager cm = server.getCommandManager();
        cm.register(new IsMyServerDeadCommand(this), "ismyserverdead");
        logger.info("Hi mate im registering commands");

        // Slash Server
        if (config.getSlashserver().isEnabled()) {
            for (RegisteredServer registeredServer : server.getAllServers()) {
                String serverName = registeredServer.getServerInfo().getName();
                cm.register(new Command() {
                    @Override
                    public void execute(CommandSource source, @NonNull String[] args) {
                        if (source instanceof Player) {
                            Player player = (Player) source;
                            player.sendMessage(Chat.green("Sending you to " + serverName + "..."));
                            player.createConnectionRequest(registeredServer).fireAndForget();
                        }
                    }
                }, serverName);
            }
        }

        // Register Repeating Rejoin Check Task
        if (config.getRejoin().isEnabled()) {
            server.getScheduler().buildTask(this, new RejoinTask(this))
                    .delay(config.getRejoin().getInterval(), TimeUnit.SECONDS)
                    .repeat(config.getRejoin().getInterval(), TimeUnit.SECONDS)
                    .schedule();
        }
    }

    @Subscribe
    public void proxyReload(ProxyReloadEvent event) {
        // could possibly do stuff on proxy reload
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent event) {
        if (sql != null) {
            sql.close();
        }
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

    public Map<UUID, String> getPreviousServer() {
        return previousServer;
    }
}

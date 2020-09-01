package com.dnyferguson.momentovelocityutils.tasks;

import com.dnyferguson.momentovelocityutils.MomentoVelocityUtils;
import com.dnyferguson.momentovelocityutils.utils.Chat;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.RegisteredServer;

import java.util.*;

public class RejoinTask implements Runnable {
    private MomentoVelocityUtils plugin;
    private Map<UUID, Integer> attempts = new HashMap<>();
    private int maxAttempts;

    public RejoinTask(MomentoVelocityUtils plugin) {
        this.plugin = plugin;
        maxAttempts = plugin.getConfig().getRejoin().getAttempts();
    }

    @Override
    public void run() {
        Set<UUID> toRemove = new HashSet<>();
        for (UUID uuid : plugin.getPreviousServer().keySet()) {
            if (maxAttempts > 0) {
                if (attempts.getOrDefault(uuid, 0) > maxAttempts) {
                    toRemove.add(uuid);
                    continue;
                }

                attempts.put(uuid, attempts.getOrDefault(uuid, 0) + 1);
            }

            ProxyServer server = plugin.getServer();

            if (!server.getPlayer(uuid).isPresent()) {
                toRemove.add(uuid);
            }

            Player player = server.getPlayer(uuid).get();
            String servName = plugin.getPreviousServer().get(uuid);

            if (!server.getServer(servName).isPresent()) {
                toRemove.add(uuid);
            }

            RegisteredServer serv = server.getServer(servName).get();

            player.sendMessage(Chat.green("Attempting to automatically send you back to " + servName + "..."));
            plugin.getLogger().info("attempting to automatically send " + player.getUsername() + " back to " + servName + ". attempt #" + attempts.get(player.getUniqueId()));

            server.getPlayer(uuid).get().createConnectionRequest(serv).fireAndForget();
        }

        for (UUID uuid : toRemove) {
            plugin.getPreviousServer().remove(uuid);
        }
    }
}

package com.dnyferguson.momentovelocityutils.listeners;

import com.dnyferguson.momentovelocityutils.MomentoVelocityUtils;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;
import com.velocitypowered.api.event.player.ServerConnectedEvent;
import com.velocitypowered.api.proxy.Player;

public class PlayerConnectToServerListener {
    private MomentoVelocityUtils plugin;

    public PlayerConnectToServerListener(MomentoVelocityUtils plugin) {
        this.plugin = plugin;
    }

    @Subscribe
    public void kicked(ServerConnectedEvent event) {
        Player player = event.getPlayer();

        if (!plugin.getPreviousServer().containsKey(player.getUniqueId())) {
            return;
        }

        String previousServer = plugin.getPreviousServer().get(player.getUniqueId());
        if (event.getServer().getServerInfo().getName().equalsIgnoreCase(previousServer)) {
            plugin.getLogger().info("Player " + player.getUsername() + " has successfully been resent to " + previousServer + ". Removing from queue.");
            plugin.getPreviousServer().remove(player.getUniqueId());
        }
    }
}

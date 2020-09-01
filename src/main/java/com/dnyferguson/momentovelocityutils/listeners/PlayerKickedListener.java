package com.dnyferguson.momentovelocityutils.listeners;

import com.dnyferguson.momentovelocityutils.MomentoVelocityUtils;
import com.dnyferguson.momentovelocityutils.config.RejoinConfig;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;

public class PlayerKickedListener {
    private final MomentoVelocityUtils plugin;
    private final boolean rejoin;

    public PlayerKickedListener(MomentoVelocityUtils plugin) {
        this.plugin = plugin;
        rejoin = plugin.getConfig().getRejoin().isEnabled();
    }

    @Subscribe
    public void kicked(KickedFromServerEvent event) {
        if (!event.getOriginalReason().isPresent()) {
            return;
        }

        String reason = event.getOriginalReason().get().toString().toLowerCase();
        for (String r : plugin.getConfig().getKicking().getReasons()) {
            if (reason.contains(r)) {
                event.getPlayer().disconnect(event.getOriginalReason().get());
                plugin.getLogger().info("player " + event.getPlayer().getUsername() + " has been fully kicked off of the proxy because their kick reason contained \"" + r + "\".");
                return;
            }
        }

        if (rejoin) {
            RejoinConfig config = plugin.getConfig().getRejoin();
            String server = event.getServer().getServerInfo().getName();

            for (String excluded : config.getServersExcluded()) {
                if (excluded.equalsIgnoreCase(server)) {
                    return;
                }
            }

            plugin.getLogger().info("queueing " + event.getPlayer().getUsername() + " to be resent to " + server + " shortly!");
            plugin.getPreviousServer().put(event.getPlayer().getUniqueId(), server);
        }
    }
}

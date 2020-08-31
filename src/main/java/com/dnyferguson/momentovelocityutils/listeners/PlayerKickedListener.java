package com.dnyferguson.momentovelocityutils.listeners;

import com.dnyferguson.momentovelocityutils.MomentoVelocityUtils;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.KickedFromServerEvent;

public class PlayerKickedListener {
    private final MomentoVelocityUtils plugin;

    public PlayerKickedListener(MomentoVelocityUtils plugin) {
        this.plugin = plugin;
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

        // todo handle queueing to send back to server they were on before reboot
    }
}

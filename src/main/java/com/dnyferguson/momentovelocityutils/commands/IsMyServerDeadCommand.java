package com.dnyferguson.momentovelocityutils.commands;

import com.dnyferguson.momentovelocityutils.MomentoVelocityUtils;
import com.dnyferguson.momentovelocityutils.utils.Chat;
import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IsMyServerDeadCommand implements Command {
    private final MomentoVelocityUtils proxy;

    public IsMyServerDeadCommand(MomentoVelocityUtils proxy) {
        this.proxy = proxy;
    }

    @Override
    public void execute(CommandSource source, @NonNull String[] args) {
        if (args.length == 0) {
            int count = 0;
            Set<String> previousIps = new HashSet<>();
            for (Player player : proxy.getProxy().getAllPlayers()) {
                String ip = player.getRemoteAddress().getAddress().getHostAddress();
                if (previousIps.contains(ip)) {
                    continue;
                }
                previousIps.add(ip);
                count++;
            }
            source.sendMessage(Chat.green("There are " + count + " unique players (ips) logged into this server!"));
            return;
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("debug")) {
            if (source instanceof Player) {
                Player player = (Player) source;
                player.sendMessage(Chat.combine(Chat.green("Your ip is "), Chat.gray(player.getRemoteAddress().getAddress().getHostAddress()), Chat.green(".")));
            }
        }
    }

    @Override
    public List<String> suggest(CommandSource source, @NonNull String[] currentArgs) {
        // null just means dont handle tab completion
        return null;
    }

    @Override
    public boolean hasPermission(CommandSource source, @NonNull String[] args) {
        return source.hasPermission("mvu.ismyserverdead");
    }
}

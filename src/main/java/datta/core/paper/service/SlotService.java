package datta.core.paper.service;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.Core;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.server.ServerListPingEvent;

import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Utils.send;

public class SlotService extends BaseCommand implements Listener {

    @Getter
    @Setter
    public static int defaultSlots = 150;

    @CommandPermission("core.admin")
    @CommandAlias("setslots")
    public void cmd(CommandSender sender, int slot) {
        setDefaultSlots(slot);

        send(sender, "&a&lEvento &8> &fSlots cambiados a " + slot + ".");
    }

    @EventHandler(
            priority = EventPriority.HIGH
    )
    public void onServerListPing(ServerListPingEvent e) {
        if (Core.getInstance().isEnabled()) {
                    e.setMaxPlayers(defaultSlots);
        }

    }

    @EventHandler(
            priority = EventPriority.HIGHEST
    )
    public void tryJoin(PlayerPreLoginEvent event) {
        int online = Bukkit.getOnlinePlayers().size();
        if (online >= getDefaultSlots()) {
            event.disallow(PlayerPreLoginEvent.Result.KICK_FULL, format("&cEl servidor está lleno."));
        }
    }
}
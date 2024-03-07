package datta.core.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import datta.core.paper.utilities.BukkitIteratorPlayers;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.WorldBorder;
import org.bukkit.entity.Player;

import static datta.core.paper.utilities.Utils.send;


@CommandPermission("sg.border")
@CommandAlias("border")
public class BorderCommand extends BaseCommand {

    @Subcommand("setsize")
    public static void setSize(Player player, int size, int seconds) {
        World world = player.getWorld();
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(size, seconds);

        BukkitIteratorPlayers.sendTitle("&a&lBorde", "&8> &f¡Disminuye a &e" + size + "x" + size + "&f &8<");
        BukkitIteratorPlayers.sendSound(Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 0);
        send(player, "&a&lBorder &8> &fEl borde estara en " + size + "x" + size + " en " + seconds + " segundos");
    }

    @Subcommand("setsize")
    public static void setSize(Player player, int size) {
        World world = player.getWorld();
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(size);

        send(player, "&a&lBorder &8> &fEl borde ahora esta en " + size + "x" + size);
    }


    @Subcommand("setsize")
    public static void setSize(int size, int seconds) {
        World world = Bukkit.getWorlds().get(0);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(size, seconds);

        BukkitIteratorPlayers.sendTitle("&a&lBorde", "&8> &f¡Disminuye a &e" + size + "x" + size + "&f &8<");
        BukkitIteratorPlayers.sendSound(Sound.ENTITY_WITHER_BREAK_BLOCK, 1, 0);
    }

    @Subcommand("setsize")
    public static void setSize(int size) {
        World world = Bukkit.getWorlds().get(0);
        WorldBorder worldBorder = world.getWorldBorder();
        worldBorder.setSize(size);
    }

    public long secondsToLong(int seconds) {
        return (long) seconds * 1000;
    }
}

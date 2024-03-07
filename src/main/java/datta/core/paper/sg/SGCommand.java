package datta.core.paper.sg;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import datta.core.paper.Core;
import datta.core.paper.utilities.etc.BukkitDelayedTask;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

import static datta.core.paper.utilities.BukkitIteratorPlayers.sendSound;
import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Utils.send;


@CommandPermission("sg.cmd")
@CommandAlias("sg|soarinngames|soarinngstudio|studio|sgc")
public class SGCommand extends BaseCommand {

    @Subcommand("screen")
    public static void screen() {
        sendSound(Sound.AMBIENT_UNDERWATER_LOOP_ADDITIONS_ULTRA_RARE, 1, 1);
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.sendTitle("日", "饿", 80, 50, 100);

        }

        BukkitDelayedTask.runTask(Core.getInstance(), () -> {
            sendSound(Sound.ENTITY_LIGHTNING_BOLT_THUNDER, 1, 0);
        }, 20L * 3);

        BukkitDelayedTask.runTask(Core.getInstance(), () -> {
            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                onlinePlayer.sendTitle(format("&e日"), "饿", 0, 0, 100);
            }
        }, 20L * 4);
    }

    @Subcommand("removeplayers")
    public static void removePlayers(CommandSender sender, int playerNumber) {
        List<Player> list = new ArrayList<>(Bukkit.getOnlinePlayers());
        List<String> bypassNames = List.of("datta", "Soarinng");
        List<Player> removeList = new ArrayList<>();

        for (Player player : list) {
            if (!player.isOp() && !bypassNames.contains(player.getName())) {
                removeList.add(player);
            }
        }

        if (removeList.size() >= playerNumber) {
            for (int i = 0; i < playerNumber; i++) {
                Player playerToRemove = removeList.get(i);
                playerToRemove.kickPlayer(format("&cUn administrador te elimino. Vuelve a entrar."));
                send(sender, "&c(❌) Se elimino a "+playerToRemove.getName()+".");
            }
        } else {
            send(sender, "No hay suficientes jugadores para eliminar.");
        }
    }
}
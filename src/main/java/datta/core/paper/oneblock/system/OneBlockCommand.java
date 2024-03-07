package datta.core.paper.oneblock.system;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import datta.core.paper.oneblock.OneBlock;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static datta.core.paper.oneblock.system.OneBlockManager.fillTeams;
import static datta.core.paper.oneblock.system.OneBlockManager.teleportTeams;
import static datta.core.paper.utilities.Utils.send;


@CommandPermission("sg.oneblock")
@CommandAlias("soarinngames|soarinngstudio|studio|games|gamemenu|soarinnggames|sg|sgmenu")
public class OneBlockCommand extends BaseCommand {

    @Subcommand("fill")
    public void fillTeam(CommandSender sender) {
        fillTeams();
        send(sender, "&aRellenando equipos.");
    }

    @Subcommand("teleport")
    public void teleport(CommandSender sender) {
        teleportTeams();
        send(sender, "&aRellenando equipos.");
    }


    @CommandCompletion("@players Amarillo|Azul|Verde|Rojo")
    @Subcommand("changeteam")
    public void teleport(CommandSender sender, OnlinePlayer onlinePlayer, String team) {

        OneBlock teamFromName = OneBlockManager.getTeamFromName(team);
        OneBlock playerTeam = OneBlockManager.getPlayerTeam(onlinePlayer.getPlayer());

        if (playerTeam != null) {
            playerTeam.getMembers().remove(onlinePlayer.getPlayer());
        }

        if (teamFromName == null) {
            send(sender, "&cNo existe el equipo " + team + ".");
            return;
        }

        teamFromName.getMembers().add(onlinePlayer.getPlayer());
        send(sender, "&aSe movio a " + onlinePlayer.getPlayer().getName() + " al equipo " + teamFromName.getColor() + teamFromName.getName() + "&f.");

    }

    @Subcommand("setchest")
    public void setchest(Player player) {
        OneBlockManager.spawnChest(player);
    }

    @Subcommand("teleportonfall")
    public void teleprotonfall(Player player, boolean value) {
        OneBlockEvents.b = value;
        send(player, "&aYa.");
    }


    @Subcommand("setblock")
    public void setblock(Player player, Material material) {
        OneBlockManager.spawnBlock(player, material);
    }
}

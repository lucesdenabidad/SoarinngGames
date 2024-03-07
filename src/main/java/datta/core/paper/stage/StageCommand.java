package datta.core.paper.stage;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.command.CommandSender;

import static datta.core.paper.utilities.Utils.send;


@CommandPermission("sg.stage")
@CommandAlias("stage")
public class StageCommand extends BaseCommand {

    @Subcommand("bypass")
    void bypass(CommandSender sender, boolean value) {
        StageManager.setOpBypass(value);
        send(sender, "&e&lEvento &8> &fBooleano &6'op-bypass'&f se cambio a &e" + value + "&f.");
    }

        @Subcommand("move")
    void move(CommandSender sender, boolean value) {
        StageManager.setMoveBoolean(value);
        send(sender, "&e&lEvento &8> &fBooleano &6'move'&f se cambio a &e" + value + "&f.");
    }

    @Subcommand("pvp")
    void pvpV(CommandSender sender, boolean value) {
        StageManager.setPvpBoolean(value);
        send(sender, "&e&lEvento &8> &fBooleano &6'pvp'&f se cambio a &e" + value + "&f.");
    }

    @Subcommand("break")
    void breakV(CommandSender sender, boolean value) {
        StageManager.setBreakBoolean(value);
        send(sender, "&e&lEvento &8> &fBooleano &6'break'&f se cambio a &e" + value + "&f.");
    }

    @Subcommand("build")
    void buildV(CommandSender sender, boolean value) {
        StageManager.setBuildBoolean(value);
        send(sender, "&e&lEvento &8> &fBooleano &6'build'&f se cambio a &e" + value + "&f.");
    }
}
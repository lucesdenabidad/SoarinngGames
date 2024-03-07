package datta.core.paper.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import datta.core.paper.utilities.services.TimerService;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.command.CommandSender;

import static datta.core.paper.utilities.Utils.send;

@CommandPermission("sg.timer")
@CommandAlias("timer")
public class TimerCommand extends BaseCommand {

    @Subcommand("start")
    @Syntax("<title> <barColor> <seconds>")
    @CommandCompletion("WHITE|RED|BLUE|PURPLE|PINK|YELLOW <seconds> <title>")
    public void start(CommandSender sender, BarColor barColor, int seconds, String title) {
        TimerService.timer(title, barColor, BarStyle.SOLID, seconds, () -> {

        });
        send(sender, "&a&lTimer &8> &fInicio el temporizador.");
    }

    @Subcommand("stop")
    @Syntax("")
    public void stop(CommandSender sender) {
        TimerService.removeBar();
        send(sender, "&a&lTimer &8> &fEl temporizador se eliminó");
    }
}

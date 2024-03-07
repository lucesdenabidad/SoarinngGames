package datta.core.paper.service.gamemenu;

import co.aikar.commands.BaseCommand;
import datta.core.paper.Core;
import org.bukkit.entity.Player;

public abstract class GameMenu extends BaseCommand {
    public abstract Runnable run(Player player);
    public abstract void runMenu(Player player);


    public static void registerMenu(GameMenu gameMenu){
        Core.getCommandManager().registerCommand(gameMenu);
    }
}

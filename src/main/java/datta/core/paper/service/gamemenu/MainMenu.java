package datta.core.paper.service.gamemenu;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.oneblock.system.OneBlockUI;
import datta.core.paper.utilities.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.builders.MenuBuilder.slot;

public class MainMenu extends GameMenu {

    @Override
    public Runnable run(Player player) {
        return () -> {

            menuBuilder.setItem(player, slot(2, 2), new ItemBuilder(
                    Material.OAK_SIGN,
                    "&bMenú de objetos",
                    "",
                    "&fAbre el menú de objetos.",
                    "",
                    "&eClic para abrir.").build(), () -> {

                new ItemsMenu().runMenu(player);
            });

            menuBuilder.setItem(player, slot(2, 5), new ItemBuilder(
                    Material.GRASS_BLOCK,
                    "&bOneblock",
                    "",
                    "&fAbre el menú de oneblock.",
                    "",
                    "&eClic para abrir.").build(), () -> {

                new OneBlockUI().runMenu(player);
            });


        };
    }


    @CommandPermission("sg.gamemenu")
    @CommandAlias("games|gamemenu|game")
    @Override
    public void runMenu(Player player) {
        menuBuilder.createMenu(player, "SGC > Menú principal", 9 * 5, false);
        menuBuilder.setContents(player, run(player));
    }
}
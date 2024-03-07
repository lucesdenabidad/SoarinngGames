package datta.core.paper.oneblock.system;

import datta.core.paper.oneblock.OneBlock;
import datta.core.paper.service.gamemenu.GameMenu;
import datta.core.paper.service.gamemenu.MainMenu;
import datta.core.paper.utilities.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.builders.MenuBuilder.slot;

public class OneBlockUI extends GameMenu {

    @Override
    public Runnable run(Player player) {
        return () -> {

            menuBuilder.setItem(player, slot(4, 2), new ItemBuilder(
                    Material.SHIELD,
                    "&aRellenar equipos",
                    "",
                    "&fRellena los equipos del oneblock",
                    "&fcon los jugadores conectados.",
                    "",
                    "&eClic para rellenar.").build(), OneBlockManager::fillTeams);

            menuBuilder.setItem(player, slot(5, 2), new ItemBuilder(
                    Material.LIME_DYE,
                    "&aIniciar OneBlock",
                    "",
                    "&fInicia el minijuego 'OneBlock'",
                    "",
                    "&eClic para iniciar.").build(), OneBlockManager::start);

            menuBuilder.setItem(player, slot(6, 2), new ItemBuilder(
                    Material.ENDER_PEARL,
                    "&bTeletransportar equipos",
                    "",
                    "&fTeletransporta a todos",
                    "&fa todos los jugadores",
                    "&fal bloque de su equipo.",
                    "",
                    "&eClic para iniciar.").build(), OneBlockManager::teleportTeams);

            menuBuilder.setItem(player, slot(3, 2), new ItemBuilder(
                    Material.REDSTONE,
                    "&cEliminar equipos",
                    "",
                    "&fElimina a los jugadores",
                    "&fde los equipos.",
                    "",
                    "&eClic para eliminar.").build(), () -> {
                for (OneBlock game : OneBlockManager.games) {
                    game.getMembers().clear();
                }
            });

            menuBuilder.setItem(player, slot(5, 5), new ItemBuilder(
                    Material.BARRIER,
                    "&cVolver",
                    "",
                    "&fVuelve atras.",
                    "",
                    "&eClic para eliminar.").build(), () -> {
                new MainMenu().runMenu(player);
            });

        };
    }


    @Override
    public void runMenu(Player player) {
        menuBuilder.createMenu(player, "Menú principal > Oneblock", 9 * 5, false);
        menuBuilder.setContents(player, run(player));
    }
}
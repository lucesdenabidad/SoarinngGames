package datta.core.paper.service.gamemenu;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import datta.core.paper.items.list.KickStick;
import datta.core.paper.items.list.KillStick;
import datta.core.paper.utilities.builders.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import static datta.core.paper.Core.menuBuilder;
import static datta.core.paper.utilities.builders.MenuBuilder.slot;

public class ItemsMenu extends GameMenu {

    @Override
    public Runnable run(Player player) {
        return () -> {

            menuBuilder.setItem(player, slot(2, 2), new ItemBuilder(
                    Material.SPECTRAL_ARROW,
                    "&eFlecha asesina",
                    "",
                    "&fObten la flecha asesina.",
                    "",
                    "&eClic para obtener.").build(), () -> {
                ItemStack itemStack = new KillStick().itemStack();
                player.getInventory().addItem(itemStack);
            });

            menuBuilder.setItem(player, slot(3, 2), new ItemBuilder(
                    Material.STICK,
                    "&cPalo expulsador",
                    "",
                    "&fObten el palo expulsador.",
                    "",
                    "&eClic para obtener.").build(), () -> {
                ItemStack itemStack = new KickStick().itemStack();
                player.getInventory().addItem(itemStack);
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


    @CommandPermission("sg.gamemenu")
    @CommandAlias("itemmenu")
    @Override
    public void runMenu(Player player) {
        menuBuilder.createMenu(player, "Menú principal > Objetos", 9 * 5, false);
        menuBuilder.setContents(player, run(player));
    }
}
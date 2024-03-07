package datta.core.paper.oneblock.system;

import datta.core.paper.Core;
import datta.core.paper.commands.BorderCommand;
import datta.core.paper.oneblock.OneBlock;
import datta.core.paper.oneblock.OneBlockElement;
import datta.core.paper.sg.SGCommand;
import datta.core.paper.stage.StageManager;
import datta.core.paper.utilities.etc.BukkitDelayedTask;
import datta.core.paper.utilities.services.TimerService;
import datta.core.paper.utilities.services.WorldEditService;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Color.stringToLocation;
import static datta.core.paper.utilities.Utils.*;

public class OneBlockManager {

    public static Map<Player, Integer> killMap = new HashMap<>();
    public static Map<Player, Integer> livesMap = new HashMap<>();

    public OneBlockManager(Core plugin) {

        Core.register(new OneBlockCommand());
        Core.register(new OneBlockEvents());

        new BukkitRunnable() {
            @Override
            public void run() {

                for (Player t : Bukkit.getOnlinePlayers()) {
                    giveNightVisionToPlayers(t);

                    int tLives = livesMap.getOrDefault(t, 1);
                    t.sendActionBar(format(t, "&e&l> &fTe quedan &a" + tLives + " vidas&f."));
                }

                for (OneBlock oneBlock : games) {
                    Location blockLocation = oneBlock.getBlockLocation();
                    Block block = blockLocation.getBlock();

                    if (block.getType() == Material.AIR) {
                        respawn(block);
                    }
                }
            }
        }.runTaskTimer(plugin, 0, 5L);
    }


    public static List<OneBlock> games = List.of(
            new OneBlock("Rojo", ChatColor.RED, stringToLocation("125 -61 125")),
            new OneBlock("Amarillo", ChatColor.YELLOW, stringToLocation("-125 -61 -125")),
            new OneBlock("Azul", ChatColor.BLUE, stringToLocation("125 -61 -125")),
            new OneBlock("Verde", ChatColor.GREEN, stringToLocation("-125 -61 125"))
    );

    public static void spawnBlock(Player player, Material material) {
        for (OneBlock oneBlock : games) {
            Location blockLocation = oneBlock.getBlockLocation();
            Block block = blockLocation.getBlock();
            block.setType(material);
        }

        send(player, "&aYa!");
    }

    public void giveNightVisionToPlayers(Player player) {
        Collection<PotionEffect> activePotionEffects = player.getActivePotionEffects();
        boolean hasNightVision = false;
        for (PotionEffect effect : activePotionEffects) {
            if (effect.getType().equals(PotionEffectType.NIGHT_VISION)) {
                hasNightVision = true;
                break;
            }
        }
        if (!hasNightVision) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 20 * 999 * 34, 0, false, false, false));
        }
    }

    public static OneBlock getTeamFromName(String name) {
        for (OneBlock game : games) {
            if (game.getName().equalsIgnoreCase(name)) {
                return game;
            }
        }
        return null;
    }

    public static OneBlock getPlayerTeam(Player player) {
        for (OneBlock block : games) {
            if (block.getMembers().contains(player)) {
                return block;
            }
        }

        return null;
    }

    public static void fillTeams() {
        List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        Collections.shuffle(players);

        int maxPerTeam = Bukkit.getOnlinePlayers().size() / games.size();
        int playersPerTeam = Math.min(maxPerTeam, players.size() / games.size());

        for (OneBlock block : games) {
            block.getMembers().clear();
        }

        int currentTeamIndex = 0;
        for (Player player : players) {
            OneBlock currentTeam = games.get(currentTeamIndex);
            if (currentTeam.getMembers().size() >= playersPerTeam) {
                currentTeamIndex++;
                if (currentTeamIndex >= games.size()) {
                    break;
                }
                currentTeam = games.get(currentTeamIndex);
            }
            currentTeam.getMembers().add(player);
        }
    }

    public static void teleportTeams() {
        for (OneBlock game : games) {
            Location add = game.getBlockLocation().clone().toCenterLocation().add(0, 2, 0);

            for (Player member : game.getMembers()) {
                member.teleport(add);
            }
        }
    }


    public static String parsePlayer(OfflinePlayer offlinePlayer, String param) {
        Player player = offlinePlayer.getPlayer();
        World world = player.getWorld();
        OneBlock playerTeam = getPlayerTeam(player);

        if (param.equalsIgnoreCase("team")) {
            return playerTeam != null ? playerTeam.getColor() + playerTeam.getName() : "&cNingúno";
        }

        if (param.equalsIgnoreCase("team_tab")) {
            return playerTeam != null ? playerTeam.getColor() + "• " : "";
        }

        if (param.equalsIgnoreCase("player_kills")) {
            return killMap.getOrDefault(player, 0).toString();
        }

        if (param.equalsIgnoreCase("border")) {
            return (int) world.getWorldBorder().getSize() + "";
        }

        if (param.equalsIgnoreCase("alive")) {
            return "" + Bukkit.getOnlinePlayers().stream().filter(t -> t.getGameMode() == GameMode.SURVIVAL).count();
        }

        return "null";
    }


    public static List<OneBlockElement> materials() {
        List<OneBlockElement> elements = new ArrayList<>();
        List<Material> materials = List.of(
                Material.DIRT, Material.GRASS_BLOCK,
                Material.COBBLESTONE, Material.STONE,
                Material.OAK_LOG, Material.OAK_WOOD, Material.STRIPPED_OAK_LOG,
                Material.GRAVEL, Material.CLAY, Material.COARSE_DIRT,
                Material.ANDESITE, Material.GRANITE,
                Material.COAL_ORE, Material.IRON_ORE, Material.GOLD_ORE
        );


        for (Material material : materials) {
            elements.add(new OneBlockElement(material));
        }

        return elements;
    }


    public static void spawnChest(Player player) {
        Block targetBlock = player.getTargetBlock(null, 5);
        Location blockLocation = targetBlock.getLocation();

        if (targetBlock.getType() == Material.CHEST) {
            Chest chest = (Chest) targetBlock.getState();
            Inventory blockInventory = chest.getBlockInventory();

            for (OneBlock game : games) {
                Location blockLocation1 = game.getBlockLocation();
                Block block = blockLocation1.getBlock();
                block.setType(Material.CHEST);
                Chest state = (Chest) block.getState();
                state.getBlockInventory().setContents(blockInventory.getContents());
            }

            send(player, "&aCofre puesto!");
        } else {
            player.sendMessage("El bloque que estás mirando no es un cofre.");
        }
    }

    public static List<OneBlockElement> elements() {
        List<OneBlockElement> list = new ArrayList<>();
        list.add(new OneBlockElement(new ItemStack(Material.STONE_SWORD)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_AXE)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_PICKAXE)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_SHOVEL)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_HELMET)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_CHESTPLATE)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_LEGGINGS)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_BOOTS)));
        list.add(new OneBlockElement(new ItemStack(Material.IRON_INGOT, 15)));
        list.add(new OneBlockElement(new ItemStack(Material.COBBLESTONE, 64)));
        list.add(new OneBlockElement(new ItemStack(Material.LAVA_BUCKET, 1)));
        list.add(new OneBlockElement(new ItemStack(Material.OAK_LOG, 64)));
        list.add(new OneBlockElement(new ItemStack(Material.WATER_BUCKET, 1)));

        list.add(new OneBlockElement(EntityType.VINDICATOR));
        list.add(new OneBlockElement(EntityType.CAVE_SPIDER));
        list.add(new OneBlockElement(EntityType.PHANTOM));
        list.add(new OneBlockElement(EntityType.ZOMBIE));
        list.add(new OneBlockElement(EntityType.PIG));
        list.add(new OneBlockElement(EntityType.COW));
        list.add(new OneBlockElement(EntityType.VILLAGER));
        list.add(new OneBlockElement(EntityType.BLAZE));
        list.add(new OneBlockElement(EntityType.ENDERMAN));
        list.add(new OneBlockElement(EntityType.SKELETON));
        list.add(new OneBlockElement(EntityType.CREEPER));
        list.add(new OneBlockElement(EntityType.WITCH));
        list.add(new OneBlockElement(EntityType.ZOMBIE));
        list.add(new OneBlockElement(EntityType.SPIDER));
        list.add(new OneBlockElement(EntityType.GHAST));
        list.add(new OneBlockElement(EntityType.MAGMA_CUBE));
        list.add(new OneBlockElement(EntityType.WITHER_SKELETON));


        for (int i = 0; i < 50; i++) {
            list.addAll(materials());
        }

        return list;
    }

    public static OneBlockElement randomElement(List<OneBlockElement> list) {
        return list.get(new Random().nextInt(list.size()));
    }

    public static void respawn(Block block) {
        OneBlockElement oneBlockElement = randomElement(elements());
        oneBlockElement.spawn(block.getLocation().toCenterLocation());
    }

    public static void start() {
        SGCommand.screen();
        BukkitDelayedTask.runTask(() -> {
            StageManager.setMoveBoolean(false);
            StageManager.setPvpBoolean(false);
            StageManager.setBuildBoolean(false);
            StageManager.setBreakBoolean(false);

            for (Player t : Bukkit.getOnlinePlayers()) {
                PlayerInventory inventory = t.getInventory();
                inventory.addItem(new ItemStack(Material.STONE_PICKAXE));
                inventory.addItem(new ItemStack(Material.STONE_AXE));
                inventory.addItem(new ItemStack(Material.COOKED_BEEF, 16));
            }

            teleportTeams();

            BukkitDelayedTask.runTask(() -> {
                countdown(10, () -> {
                    StageManager.setBuildBoolean(true);
                    StageManager.setBreakBoolean(true);
                    StageManager.setMoveBoolean(true);
                });

                TimerService.timer("&#ff1100Las barreras caen y el PvP comienza en {time}.", BarColor.RED, BarStyle.SOLID, 1800, () -> {
                    removeWalls();

                    BorderCommand.setSize(20,600);
                    TimerService.timer("&#b54dffLa tormenta cierra en {time}.", BarColor.PURPLE, BarStyle.SOLID, 600, () -> {});
                });
            }, 40L);
        }, 20L * 5);
    }

    public static void removeWalls() {
        Location[] FIRST = sLocation("0 39 -249 0 -60 249");
        Location f1 = FIRST[0];
        Location f2 = FIRST[1];

        Location[] SECONDS = sLocation("-249 39 0 249 -60 0");
        Location s1 = SECONDS[0];
        Location s2 = SECONDS[1];

        WorldEditService.fill(s1, s2, Material.AIR);
        WorldEditService.fill(f1, f2, Material.AIR);
    }

    public void placeWalls() {
        Location[] FIRST = sLocation("0 39 -249 0 -60 249");
        Location f1 = FIRST[0];
        Location f2 = FIRST[1];

        Location[] SECONDS = sLocation("-249 39 0 249 -60 0");
        Location s1 = SECONDS[0];
        Location s2 = SECONDS[1];

        WorldEditService.fill(s1, s2, Material.BARRIER);
        WorldEditService.fill(f1, f2, Material.BARRIER);
    }
}
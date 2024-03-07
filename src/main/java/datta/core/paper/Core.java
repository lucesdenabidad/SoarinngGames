package datta.core.paper;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import datta.core.paper.animations.ExpulseAnimation;
import datta.core.paper.animations.KillAnimation;
import datta.core.paper.animations.am.AnimationManager;
import datta.core.paper.commands.BorderCommand;
import datta.core.paper.commands.GlobalCMD;
import datta.core.paper.commands.TimerCommand;
import datta.core.paper.events.MessagesEvent;
import datta.core.paper.events.PlayerKillEvent;
import datta.core.paper.items.list.KickStick;
import datta.core.paper.items.list.KillStick;
import datta.core.paper.items.list.Menu;
import datta.core.paper.oneblock.system.OneBlockManager;
import datta.core.paper.oneblock.system.OneBlockParse;
import datta.core.paper.service.ResetService;
import datta.core.paper.service.SlotService;
import datta.core.paper.service.gamemenu.GameMenu;
import datta.core.paper.service.gamemenu.ItemsMenu;
import datta.core.paper.service.gamemenu.MainMenu;
import datta.core.paper.service.lobby.LeaveLobbyEvent;
import datta.core.paper.sg.SGCommand;
import datta.core.paper.stage.StageManager;
import datta.core.paper.task.BroadcastSenderTask;
import datta.core.paper.utilities.builders.MenuBuilder;
import datta.core.paper.utilities.etc.configuration.Configuration;
import datta.core.paper.utilities.etc.configuration.ConfigurationManager;
import datta.core.paper.utilities.score.ScoreHolder;
import datta.core.paper.utilities.services.TimerService;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;


public class Core extends JavaPlugin {

    private static @Getter Core instance;
    private static @Getter PaperCommandManager commandManager;

    public static MenuBuilder menuBuilder;

    public ConfigurationManager configurationManager;
    @Getter
    public Configuration config;

    public static Location spawn;

    @Override
    public void onEnable() {
        instance = this;
        menuBuilder = new MenuBuilder(this);
        menuBuilder = new MenuBuilder(this);
        commandManager = new PaperCommandManager(this);
        configurationManager = new ConfigurationManager(this);
        config = configurationManager.getConfig("configuration.yml");
        spawn = Bukkit.getWorlds().get(0).getSpawnLocation().toCenterLocation();

        // HOLY
        BroadcastSenderTask.start();

        //SCORE
        ScoreHolder scoreHolder = new ScoreHolder(this,
                "尼",
                "",
                "&f Equipo: &a%oneblock_team% ",
                "&f Bajas: &e%oneblock_player_kills%",
                "",
                "&f Borde: &6%oneblock_border%",
                "&f Vivos: &a%oneblock_alive%   ",
                "",
                "&e  www.holy.gg");
        scoreHolder.start(0, 20L);

        //LISTENERS
        register(new MessagesEvent());
        register(new PlayerKillEvent());
        register(new LeaveLobbyEvent());

        // SERVICES
        AnimationManager animationManager = new AnimationManager(commandManager);
        animationManager.register(new KillAnimation());
        animationManager.register(new ExpulseAnimation());

        registerBoth(new SlotService());

        ResetService resetService = new ResetService();
        resetService.hook();

        //COMMANDS
        register(new SGCommand());
        commandManager.registerCommand(new GlobalCMD());

        // ITEMS
        registerBoth(new KickStick());
        registerBoth(new KillStick());
        registerBoth(new Menu());


        OneBlockManager oneBlockManager = new OneBlockManager(this);
        StageManager stageManager = new StageManager(this);

        // MENUS
        GameMenu.registerMenu(new MainMenu());
        GameMenu.registerMenu(new ItemsMenu());
        new OneBlockParse().register();

        register(new BorderCommand());
        register(new TimerCommand());
    }

    @Override
    public void onDisable() {
        TimerService.removeBar();
        new OneBlockParse().unregister();

    }

    public static void registerBoth(Object object) {
        commandManager.registerCommand((BaseCommand) object);
        Bukkit.getPluginManager().registerEvents((Listener) object, Core.getInstance());

    }

    public static void register(Object object) {
        if (object instanceof BaseCommand) {
            commandManager.registerCommand((BaseCommand) object);
        }

        if (object instanceof Listener) {
            Bukkit.getPluginManager().registerEvents((Listener) object, Core.getInstance());
        }
    }
}
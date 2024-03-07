package datta.core.paper.utilities.services;

import datta.core.paper.Core;
import datta.core.paper.utilities.etc.BukkitDelayedTask;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import static datta.core.paper.utilities.Color.format;
import static datta.core.paper.utilities.Color.formatTime;

public class TimerService {
    private static BossBar bossBar;
    private static BukkitTask task;

    public static void timer(String title, BarColor barColor, BarStyle barStyle, int seconds, Runnable runnable) {
        JavaPlugin plugin = Core.getInstance();

        bossBar = Bukkit.createBossBar(format(title), barColor, barStyle);
        bossBar.setTitle(format(title));
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(onlinePlayer);
        }

        task = new BukkitRunnable() {
            int totalTimeSeconds = seconds;

            @Override
            public void run() {
                String finalTitle = title.replace("{time}", formatTime(totalTimeSeconds));
                double progress = (double) totalTimeSeconds / (double) seconds;

                bossBar.setProgress(Math.max(0, Math.min(progress, 1)));
                bossBar.setTitle(format(finalTitle));

                if (totalTimeSeconds == 0) {
                    BukkitDelayedTask.runTask(plugin, runnable,20L);


                    bossBar.setVisible(false);
                    bossBar.removeAll();
                    cancel();
                }

                totalTimeSeconds--;
            }
        }.runTaskTimer(Core.getInstance(), 0, 20L);
    }

    public static void removeBar() {
        if (bossBar != null) {
            bossBar.setVisible(false);
            bossBar.removeAll();
            bossBar = null;
            task.cancel();
        }
    }
}
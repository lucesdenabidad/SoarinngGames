package datta.core.paper.oneblock.system;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class OneBlockParse extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "oneblock";
    }

    @Override
    public @NotNull String getAuthor() {
        return "datta";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.1.1";
    }

    @Override
    public @NotNull String onRequest(OfflinePlayer offlinePlayer, String param) {
        return Objects.requireNonNull(OneBlockManager.parsePlayer(offlinePlayer, param));
    }
}

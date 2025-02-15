package pl.ycode.plugins.timecoins.user;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.ycode.plugins.timecoins.api.ApiUser;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User implements ApiUser {
    private final UUID uniqueId;
    private int coins;
    private int spentCoins;
    private final long firstLogin;
    private long lastUpdate;

    public User(Player player) {
        this.uniqueId = player.getUniqueId();
        this.coins = 0;
        this.spentCoins = 0;
        this.firstLogin = System.currentTimeMillis();
        this.lastUpdate = System.currentTimeMillis();
    }
    public User(ResultSet rs) throws SQLException {
        this.uniqueId = UUID.fromString(rs.getString("unique_id"));
        this.coins = rs.getInt("coins");
        this.spentCoins = rs.getInt("spent_coins");
        this.firstLogin = rs.getLong("first_login");
        this.lastUpdate = rs.getLong("last_update");
    }

    @Override
    public boolean has(int amount) {
        return this.coins >= amount;
    }
    @Override
    public void give(int amount) {
        this.coins += amount;
    }
    @Override
    public void take(int amount) {
        this.coins -= amount;
        this.spentCoins += amount;
    }

    @Override
    public UUID getUniqueId() {
        return this.uniqueId;
    }
    @Override
    public int getCoins() {
        return this.coins;
    }
    @Override
    public int getSpentCoins() {
        return spentCoins;
    }
    @Override
    public long getFirstLogin() {
        return firstLogin;
    }
    @Override
    public long getLastUpdate() {
        return lastUpdate;
    }

    public String getName() {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.uniqueId);
        return offlinePlayer.getName();
    }

    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

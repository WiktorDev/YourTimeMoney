package pl.ycode.plugins.timecoins.user;

import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {
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

    public void setCoins(int coins) {
        this.coins = coins;
    }
    public void setLastUpdate(long lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }
    public int getCoins() {
        return this.coins;
    }

    public long getLastUpdate() {
        return lastUpdate;
    }
    public long getFirstLogin() {
        return firstLogin;
    }

    public void addCoin() {
        this.coins++;
    }
    public int getSpentCoins() {
        return spentCoins;
    }

    public void setSpentCoins(int spentCoins) {
        this.spentCoins = spentCoins;
    }
}

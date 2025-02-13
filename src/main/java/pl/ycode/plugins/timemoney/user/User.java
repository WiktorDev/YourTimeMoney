package pl.ycode.plugins.timemoney.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class User {
    private UUID uniqueId;
    private int coins;

    public User(UUID uniqueId, int coins) {
        this.uniqueId = uniqueId;
        this.coins = coins;
    }
    public User(ResultSet rs) throws SQLException {
        this.uniqueId = UUID.fromString(rs.getString("unique_id"));
        this.coins = rs.getInt("coins");
    }
}

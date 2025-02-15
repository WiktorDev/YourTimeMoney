package pl.ycode.plugins.timecoins.api;

import java.util.UUID;

public interface ApiUser {
    boolean has(int amount);
    void give(int amount);
    void take(int amount);

    UUID getUniqueId();
    int getCoins();
    int getSpentCoins();
    long getFirstLogin();
    long getLastUpdate();
}

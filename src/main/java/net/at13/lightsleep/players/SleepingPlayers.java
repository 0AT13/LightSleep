package net.at13.lightsleep.players;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SleepingPlayers {

    // Storing player state for check percentage count from active players
    private final Map<UUID, Long> playerSleepingState = new HashMap<>();

    public void setSleepingState(UUID playerUUID, Long status) {
        playerSleepingState.put(playerUUID, status);
    }

    public Long isSleeping(UUID playerUUID) { return playerSleepingState.get(playerUUID); }

    public void removeSleepingState(UUID playerUUID) {
        playerSleepingState.remove(playerUUID);
    }

    public Long time(UUID playerUUID)
    {
        return playerSleepingState.get(playerUUID);
    }

    public int Count() {
        return playerSleepingState.size();
    }

    public void clearSleepingStates() {
        playerSleepingState.clear();
    }
}

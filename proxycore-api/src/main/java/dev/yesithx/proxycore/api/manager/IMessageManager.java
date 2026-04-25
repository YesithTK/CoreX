package dev.yesithx.proxycore.api.manager;

import java.util.UUID;

/**
 * Manages private messages between players across servers.
 *
 * @author YesithTK
 */
public interface IMessageManager {

    /**
     * Registers that {@code sender} last messaged {@code target}.
     * This is bidirectional — both players can /reply to each other.
     *
     * @param sender UUID of the player who sent the message
     * @param target UUID of the player who received it
     */
    void setLastMessaged(UUID sender, UUID target);

    /**
     * Removes a player from the reply map when they disconnect.
     *
     * @param uuid UUID of the disconnected player
     */
    void removePlayer(UUID uuid);

    /**
     * Returns whether the given player has someone to reply to.
     *
     * @param uuid UUID of the player
     * @return true if a reply target exists and is online
     */
    boolean hasReplyTarget(UUID uuid);
}

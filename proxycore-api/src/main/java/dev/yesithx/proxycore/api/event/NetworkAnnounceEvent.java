package dev.yesithx.proxycore.api.event;

/**
 * Fired just before an automatic announcement is broadcast to the network.
 * Addons can use this to log announcements or modify behavior.
 *
 * @author YesithTK
 */
public class NetworkAnnounceEvent extends ProxyCoreEvent {

    private final String message;
    private final int index;

    /**
     * @param message the announcement message about to be broadcast
     * @param index   the index of this message in the announcements list
     */
    public NetworkAnnounceEvent(String message, int index) {
        super();
        this.message = message;
        this.index = index;
    }

    /**
     * Returns the announcement message (with & color codes, unparsed).
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns the index of this announcement in the configured list.
     */
    public int getIndex() {
        return index;
    }
}

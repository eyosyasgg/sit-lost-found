package core.model;

import java.time.LocalDateTime;

/**
 * A report made by an owner who has lost something on campus.
 * Specializes {@link Item} with last-seen time and an urgency flag.
 */
public class LostItem extends Item {

    private static final String DEFAULT_LAST_SEEN_TIME = "Not specified";

    private String lastSeenTime;
    private boolean isUrgent;

    /**
     * Primary constructor for a newly reported lost item.
     * Reporter details are taken from the User object (composition) by Item.
     */
    public LostItem(User reporter, String title, String description,
                    String categoryTag, String location, String imageUrl,
                    String lastSeenTime, boolean isUrgent) {
        super(reporter, title, description, categoryTag, location, imageUrl);
        this.lastSeenTime = normalizeLastSeenTime(lastSeenTime);
        this.isUrgent = isUrgent;
    }

    /**
     * Convenience constructor: last seen time defaults to "Not specified"
     * and the report is not urgent.
     */
    public LostItem(User reporter, String title, String description,
                    String categoryTag, String location, String imageUrl) {
        this(reporter, title, description, categoryTag, location, imageUrl,
                DEFAULT_LAST_SEEN_TIME, false);
    }

    /**
     * Database reconstruction constructor: keeps the original id,
     * reporter snapshot, resolved flag and creation time.
     */
    public LostItem(String id, String reporterId, String reporterName,
                    String reporterContactInfo, String title, String description,
                    String categoryTag, String location, String imageUrl,
                    boolean resolved, LocalDateTime createdAt,
                    String lastSeenTime, boolean isUrgent) {
        super(id, reporterId, reporterName, reporterContactInfo, title,
                description, categoryTag, location, imageUrl, resolved, createdAt);
        this.lastSeenTime = normalizeLastSeenTime(lastSeenTime);
        this.isUrgent = isUrgent;
    }

    private static String normalizeLastSeenTime(String value) {
        return (value == null || value.trim().isEmpty())
                ? DEFAULT_LAST_SEEN_TIME
                : value.trim();
    }

    // ---------- Getters and setters ----------

    public String getLastSeenTime() {
        return lastSeenTime;
    }

    public void setLastSeenTime(String lastSeenTime) {
        if (lastSeenTime == null || lastSeenTime.trim().isEmpty()) {
            throw new IllegalArgumentException("Last seen time must not be blank.");
        }
        this.lastSeenTime = lastSeenTime.trim();
    }

    public boolean isUrgent() {
        return isUrgent;
    }

    public void setUrgent(boolean urgent) {
        this.isUrgent = urgent;
    }

    // ---------- Abstract method implementations ----------

    @Override
    public String getReportType() {
        return "LOST";
    }

    @Override
    public String getStatusSummary() {
        String prefix = isUrgent ? "[URGENT] " : "";
        return prefix + "Last seen at " + getLocation()
                + " around " + lastSeenTime
                + " | Contact: " + getReporterContactInfo();
    }

    @Override
    public String toString() {
        return "LostItem{title='" + getTitle() + "', location='" + getLocation()
                + "', lastSeenTime='" + lastSeenTime + "', urgent=" + isUrgent + "}";
    }
}

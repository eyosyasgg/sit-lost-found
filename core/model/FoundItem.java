package core.model;

import java.time.LocalDateTime;

/**
 * A report made by a finder who picked up a stray item on campus.
 * Specializes {@link Item} with a drop-off location and an optional
 * ownership verification question.
 */
public class FoundItem extends Item {

    private static final String DEFAULT_DROPOFF_LOCATION = "With finder";

    private String dropoffLocation;
    private String verificationQuestion;

    /**
     * Primary constructor for a newly registered found item.
     * Reporter details are taken from the User object (composition) by Item.
     */
    public FoundItem(User reporter, String title, String description,
                     String categoryTag, String location, String imageUrl,
                     String dropoffLocation, String verificationQuestion) {
        super(reporter, title, description, categoryTag, location, imageUrl);
        this.dropoffLocation = normalizeDropoff(dropoffLocation);
        this.verificationQuestion = normalizeQuestion(verificationQuestion);
    }

    /**
     * Convenience constructor: the finder holds the item and
     * no verification question is set.
     */
    public FoundItem(User reporter, String title, String description,
                     String categoryTag, String location, String imageUrl) {
        this(reporter, title, description, categoryTag, location, imageUrl,
                DEFAULT_DROPOFF_LOCATION, null);
    }

    /**
     * Database reconstruction constructor: keeps the original id,
     * reporter snapshot, resolved flag and creation time.
     */
    public FoundItem(String id, String reporterId, String reporterName,
                     String reporterContactInfo, String title, String description,
                     String categoryTag, String location, String imageUrl,
                     boolean resolved, LocalDateTime createdAt,
                     String dropoffLocation, String verificationQuestion) {
        super(id, reporterId, reporterName, reporterContactInfo, title,
                description, categoryTag, location, imageUrl, resolved, createdAt);
        this.dropoffLocation = normalizeDropoff(dropoffLocation);
        this.verificationQuestion = normalizeQuestion(verificationQuestion);
    }

    private static String normalizeDropoff(String value) {
        return (value == null || value.trim().isEmpty())
                ? DEFAULT_DROPOFF_LOCATION
                : value.trim();
    }

    private static String normalizeQuestion(String value) {
        return (value == null || value.trim().isEmpty()) ? null : value.trim();
    }

    // ---------- Getters and setters ----------

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        if (dropoffLocation == null || dropoffLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Drop-off location must not be blank.");
        }
        this.dropoffLocation = dropoffLocation.trim();
    }

    public String getVerificationQuestion() {
        return verificationQuestion;
    }

    /** The question is optional: null or blank clears it. */
    public void setVerificationQuestion(String verificationQuestion) {
        this.verificationQuestion = normalizeQuestion(verificationQuestion);
    }

    /** @return true when an ownership verification question has been set. */
    public boolean requiresVerification() {
        return verificationQuestion != null;
    }

    // ---------- Abstract method implementations ----------

    @Override
    public String getReportType() {
        return "FOUND";
    }

    @Override
    public String getStatusSummary() {
        return "Found at " + getLocation()
                + " | Currently held: " + dropoffLocation
                + (requiresVerification() ? " | Verification required" : "");
    }

    @Override
    public String toString() {
        return "FoundItem{title='" + getTitle() + "', foundAt='" + getLocation()
                + "', heldAt='" + dropoffLocation
                + "', requiresVerification=" + requiresVerification() + "}";
    }
}

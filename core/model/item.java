package core.model;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Item {
    private final String id;
    private final String reporterId;
    private final String reporterName;
    private final String reporterContactInfo;
    private String title;
    private String description;
    private String categoryTag;
    private String location;
    private String imageUrl;
    private boolean resolved;
    private final LocalDateTime createdAt;

    // New item reported by a user
    protected Item(User reporter, String title, String description,
                   String categoryTag, String location, String imageUrl) {
        if (reporter == null) {
            throw new IllegalArgumentException("Reporter is required");
        }
        this.id = UUID.randomUUID().toString();
        this.reporterId = reporter.getId();
        this.reporterName = reporter.getFullName();
        this.reporterContactInfo = reporter.getFormattedContactInfo();
        setTitle(title);
        setDescription(description);
        setCategoryTag(categoryTag);
        setLocation(location);
        this.imageUrl = imageUrl;
        this.resolved = false;
        this.createdAt = LocalDateTime.now();
    }

    // Item loaded from the database
    protected Item(String id, String reporterId, String reporterName,
                   String reporterContactInfo, String title, String description,
                   String categoryTag, String location, String imageUrl,
                   boolean resolved, LocalDateTime createdAt) {
        this.id = requireText(id, "id");
        this.reporterId = requireText(reporterId, "reporterId");
        this.reporterName = reporterName;
        this.reporterContactInfo = reporterContactInfo;
        setTitle(title);
        setDescription(description);
        setCategoryTag(categoryTag);
        setLocation(location);
        this.imageUrl = imageUrl;
        this.resolved = resolved;
        this.createdAt = (createdAt != null) ? createdAt : LocalDateTime.now();
    }

    public abstract String getReportType();
    public abstract String getStatusSummary();

    public void markAsResolved(User actor) {
        if (actor == null) {
            throw new SecurityException("No user given");
        }
        boolean isReporter = actor.getId() != null && actor.getId().equals(reporterId);
        boolean isApprover = actor instanceof Staff staff && staff.canApproveRetrieval();
        if (!isReporter && !isApprover) {
            throw new SecurityException("Only the reporter or approved staff can resolve this item");
        }
        this.resolved = true;
    }

    public boolean matchesQuery(String keyword, String selectedTag) {
        boolean tagOk = selectedTag == null || selectedTag.isBlank()
                || selectedTag.equalsIgnoreCase("All")
                || selectedTag.equalsIgnoreCase(categoryTag);
        if (!tagOk) return false;
        if (keyword == null || keyword.isBlank()) return true;
        String k = keyword.trim().toLowerCase();
        return contains(title, k) || contains(description, k) || contains(location, k);
    }

    private static boolean contains(String field, String k) {
        return field != null && field.toLowerCase().contains(k);
    }

    private static String requireText(String value, String name) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(name + " must not be blank");
        }
        return value.trim();
    }

    public String getId() {
         return id; 
    }
    public String getReporterId() {
         return reporterId; 
        }
    public String getReporterName() {
         return reporterName; 
        }
    public String getReporterContactInfo() {
         return reporterContactInfo; 
        }
    public String getTitle() {
         return title; 
        }
    public String getDescription() {
         return description; 
        }
    public String getCategoryTag() {
         return categoryTag; 
        }
    public String getLocation() {
         return location; 
        }
    public String getImageUrl() {
         return imageUrl; 
        }
    public boolean isResolved() {
         return resolved; 
        }
    public LocalDateTime getCreatedAt() {
         return createdAt; 
        }

    public void setTitle(String title) {
         this.title = requireText(title, "title"); 
        }
    public void setDescription(String description) {
         this.description = requireText(description, "description"); 
        }
    public void setCategoryTag(String categoryTag) {
         this.categoryTag = requireText(categoryTag, "categoryTag"); 
        }
    public void setLocation(String location) {
         this.location = requireText(location, "location"); 
        }
    public void setImageUrl(String imageUrl) {
         this.imageUrl = imageUrl; 
        }

    @Override
    public String toString() {
        return "[" + getReportType() + "] " + title + " @ " + location
                + (resolved ? " (resolved)" : "");
    }
}

package core.model;

import java.util.Objects;
import java.util.UUID;

public class User {
    private final String id;
    private String fullName;
    private String email;
    private String passwordHashString;
    private String phoneNumber;
    private String telegramTag;
    
    public User(String fullName, String email,
                String passwordHashString, String phoneNumber, String telegramTag) {
        
        this.id = UUID.randomUUID().toString()
        setFullName(fullName);
        setEmail(email);
        setPasswordHashString(passwordHashString);
        setPhoneNumber(phoneNumber);
        setTelegramTag(telegramTag);
    }

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return email;
    }

    public String getPasswordHashString() {
        return passwordHashString;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTelegramTag() {
        return telegramTag;
    }

    
    public void setFullName(String fullName) {
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name is required.");
        }
        this.fullName = fullName.trim();
    }

    public void setEmail(String email) {
        if (email == null || email.isBlank() || !email.contains("@")) {
            throw new IllegalArgumentException("A valid email is required.");
        }
        this.email = email.trim().toLowerCase();
    }

    public void setPasswordHashString(String passwordHashString) {
        if (passwordHashString == null || passwordHashString.isBlank()) {
            throw new IllegalArgumentException("Password hash is required.");
        }
        this.passwordHashString = passwordHashString;
    }

    public void setPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("Phone number is required.");
        }
        this.phoneNumber = phoneNumber.trim();
    }

    public void setTelegramTag(String telegramTag) {
        if (telegramTag == null || telegramTag.isBlank()) {
            throw new IllegalArgumentException("Telegram tag is required.");
        }
        String formatted = telegramTag.trim();
        this.telegramTag = formatted.startsWith("@") ? formatted : "@" + formatted;
    }

    // a method to generate a formatted contact info
    public String getFormattedContactInfo() {
        return "Phone: " + phoneNumber + " | Telegram: " + telegramTag;
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User user)) return false;
        return Objects.equals(id, user.id);
    }

    @Override public int hashCode() {
        return Objects.hash(id);
    }

    @Override public String toString() {
        return fullName + " (" + email + ") - " + getFormattedContactInfo();
    }
}
package core.model;

/**
 * A university student who reports lost belongings or returns found items.
 * Extends User with academic and residential details.
 */
public class Student extends User {

    private static final String DEFAULT_DORM_BLOCK = "Off-Campus";
    private static final int DEFAULT_DORM_NUMBER = 0;

    private final String studentId;
    private String dormBlock;
    private int dormNumber;

    // Primary constructor: new student registration
    public Student(String fullName, String email, String passwordHashString,
                   String phoneNumber, String telegramTag,
                   String studentId, String dormBlock, int dormNumber) {
        super(fullName, email, passwordHashString, phoneNumber, telegramTag);

        this.studentId = validateStudentId(studentId);

        setDormBlock(dormBlock);
        setDormNumber(dormNumber);
    }

    // Convenience constructor: commuter students (no dormitory)
    public Student(String fullName, String email, String passwordHashString,
                   String phoneNumber, String telegramTag, String studentId) {

        this(fullName, email, passwordHashString, phoneNumber, telegramTag,
                studentId, DEFAULT_DORM_BLOCK, DEFAULT_DORM_NUMBER);
    }

    // Reconstruction constructor: existing database id (records loaded from storage)
    public Student(String id, String fullName, String email, String passwordHashString,
                   String phoneNumber, String telegramTag,
                   String studentId, String dormBlock, int dormNumber) {
        super(id, fullName, email, passwordHashString, phoneNumber, telegramTag);
        
        this.studentId = validateStudentId(studentId);

        setDormBlock(dormBlock);
        setDormNumber(dormNumber);
    }

    private static String validateStudentId(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            throw new IllegalArgumentException("Student ID is required.");
        }
        return studentId.trim();
    }

    // Getters
    public String getStudentId() {
        return studentId;
    }

    public String getDormBlock() {
        return dormBlock;
    }

    public int getDormNumber() {
        return dormNumber;
    }

    // Validated setters (studentId is final, so it has no setter)
    public void setDormBlock(String dormBlock) {
        if (dormBlock == null || dormBlock.isBlank()) {
            throw new IllegalArgumentException("Dorm block is required.");
        }
        this.dormBlock = dormBlock.trim();
    }

    public void setDormNumber(int dormNumber) {
        if (dormNumber < 0) {
            throw new IllegalArgumentException("Dorm number cannot be negative.");
        }
        this.dormNumber = dormNumber;
    }

    @Override
    public String getDisplayBadge() {
        return "Student • " + studentId;
    }

    @Override
    public String toString() {
        return super.toString() + " | Student ID: " + studentId
                + " | Dorm: " + dormBlock + " " + dormNumber;
    }
}
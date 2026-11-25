package core.model;

public class Staff extends User {

    // Staff-specific attributes
    private String staffRole;
    private boolean canApproveRetrieval;


    // Standard constructor
    // Retrieval approval is false by default.
    public Staff(String fullName, String email,
                 String passwordHashString, String phoneNumber,
                 String telegramTag, String staffRole) {

        this(fullName, email, passwordHashString,
             phoneNumber, telegramTag, staffRole, false);
    }


    // Full constructor
    // Allows retrieval approval to be explicitly specified.
    public Staff(String fullName, String email,
                 String passwordHashString, String phoneNumber,
                 String telegramTag, String staffRole,
                 boolean canApproveRetrieval) {

        super(fullName, email, passwordHashString,
              phoneNumber, telegramTag);

        setStaffRole(staffRole);
        this.canApproveRetrieval = canApproveRetrieval;
    }


    // Reconstruction constructor
    // Used when retrieving an existing staff member from a database.
    public Staff(String id, String fullName, String email,
                 String passwordHashString, String phoneNumber,
                 String telegramTag, String staffRole,
                 boolean canApproveRetrieval) {

        super(id, fullName, email, passwordHashString,
              phoneNumber, telegramTag);

        setStaffRole(staffRole);
        this.canApproveRetrieval = canApproveRetrieval;
    }


    // Returns the staff role.
    public String getStaffRole() {
        return staffRole;
    }


    // Changes the staff role after validating it.
    public void setStaffRole(String staffRole) {

        if (staffRole == null || staffRole.isBlank()) {
            throw new IllegalArgumentException(
                "Staff role cannot be null or blank."
            );
        }

        this.staffRole = staffRole.trim();
    }


    // Returns whether the staff member can approve retrievals.
    public boolean canApproveRetrieval() {
        return canApproveRetrieval;
    }


    // Changes the retrieval approval permission.
    public void setCanApproveRetrieval(boolean canApproveRetrieval) {
        this.canApproveRetrieval = canApproveRetrieval;
    }


    // Returns a badge that distinguishes verified security staff.
    @Override
    public String getDisplayBadge() {

        if (staffRole.equalsIgnoreCase("Campus Security Officer")
                && canApproveRetrieval) {

            return "Verified Security Staff";
        }

        return "General Staff";
    }


    // Returns the staff information as a String.
    @Override
    public String toString() {

        return super.toString()
                + " | Staff Role: " + staffRole
                + " | Can Approve Retrieval: " + canApproveRetrieval;
    }
}
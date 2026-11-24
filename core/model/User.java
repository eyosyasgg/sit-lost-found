package core.model;

public class User {
    private String Id;
    private String fullName;
    private String email;
    private String passwordHashString;
    private int phoneNumber;
    private String telegramTag;
    public User(){
        this.Id = null;
        this.fullName = "";
        this.email = "";
        this.passwordHashString = "";
        this.phoneNumber = 0;
        this.telegramTag = "";
    }
}
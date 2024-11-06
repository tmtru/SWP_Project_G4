package model;

public class Account {
    private int ID_Account;
    private String username;
    private String password;
    private String email;
    private String role;

    private boolean isActive;

    // Constructor
    public Account(int idAccount, String email, String username, String password, String role, boolean isActive) {
        this.ID_Account = idAccount;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    public Account() {
       
    }

    // Getters and Setters
    public int getID_Account() {
        return ID_Account;
    }

    public void setID_Account(int idAccount) {
        this.ID_Account = idAccount;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    // toString method for easy printing
    @Override
    public String toString() {
        return "Account{" +
                "idAccount=" + ID_Account +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }
    // for chat
    private int unreadCount;

    public int getUnreadCount() {
        return unreadCount;
    }

    public void setUnreadCount(int unreadCount) {
        this.unreadCount = unreadCount;
    }
}
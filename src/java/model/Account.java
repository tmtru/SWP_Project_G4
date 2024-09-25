package model;

public class Account {
    private int idAccount;
    private String email;
    private String username;
    private String password;
    private String role;
    private boolean isActive;

    // Constructor
    public Account(int idAccount, String email, String username, String password, String role, boolean isActive) {
        this.idAccount = idAccount;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
        this.isActive = isActive;
    }

    // Getters and Setters
    public int getIdAccount() {
        return idAccount;
    }

    public void setIdAccount(int idAccount) {
        this.idAccount = idAccount;
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
                "idAccount=" + idAccount +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}
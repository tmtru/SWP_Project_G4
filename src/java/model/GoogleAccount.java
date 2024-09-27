package model;

public class GoogleAccount {
    private int ID_Account;
    private String email;
    private String username;

    public GoogleAccount() {
    }

    public GoogleAccount(int ID_Account, String email, String username) {
        this.ID_Account = ID_Account;
        this.email = email;
        this.username = username;
    }

    public int getID_Account() {
        return ID_Account;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setID_Account(int ID_Account) {
        this.ID_Account = ID_Account;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "GoogleAccount{" + "email=" + email + ", username=" + username + '}';
    }
    
}

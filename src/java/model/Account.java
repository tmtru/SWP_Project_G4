/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Admin
 */
public class Account {
    String Email;
    String Username;
    String Passowrd;
    String Role;

    public Account() {
    }

    public Account(String Email, String Username, String Passowrd, String Role) {
        this.Email = Email;
        this.Username = Username;
        this.Passowrd = Passowrd;
        this.Role = Role;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

    public String getPassowrd() {
        return Passowrd;
    }

    public void setPassowrd(String Passowrd) {
        this.Passowrd = Passowrd;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

    @Override
    public String toString() {
        return "Account{" + "Email=" + Email + ", Username=" + Username + ", Passowrd=" + Passowrd + ", Role=" + Role + '}';
    }
    
    
    
}

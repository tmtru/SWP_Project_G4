/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Admin
 */
public class Transaction {
    
    int ID_Transaction;
    String MaGiaoDich;
    Date Transaction;
    float Amount;
    String PaymentMethod;
    int ID_HoaDon;
    String MoTa;
    
    public Transaction() {
    }
    
    public Transaction(int ID_Transaction, String MaGiaoDich, Date Transaction, float Amount, String PaymentMethod, int ID_HoaDon) {
        this.ID_Transaction = ID_Transaction;
        this.MaGiaoDich = MaGiaoDich;
        this.Transaction = Transaction;
        this.Amount = Amount;
        this.PaymentMethod = PaymentMethod;
        this.ID_HoaDon = ID_HoaDon;
    }

    public Transaction(int ID_Transaction, String MaGiaoDich, Date Transaction, float Amount, String PaymentMethod, int ID_HoaDon, String MoTa) {
        this.ID_Transaction = ID_Transaction;
        this.MaGiaoDich = MaGiaoDich;
        this.Transaction = Transaction;
        this.Amount = Amount;
        this.PaymentMethod = PaymentMethod;
        this.ID_HoaDon = ID_HoaDon;
        this.MoTa = MoTa;
    }
    

    public int getID_Transaction() {
        return ID_Transaction;
    }

    public String getMoTa() {
        return MoTa;
    }

    public void setMoTa(String MoTa) {
        this.MoTa = MoTa;
    }

    public void setID_Transaction(int ID_Transaction) {
        this.ID_Transaction = ID_Transaction;
    }

    public String getMaGiaoDich() {
        return MaGiaoDich;
    }

    public void setMaGiaoDich(String MaGiaoDich) {
        this.MaGiaoDich = MaGiaoDich;
    }

    public Date getTransaction() {
        return Transaction;
    }

    public void setTransaction(Date Transaction) {
        this.Transaction = Transaction;
    }

    public float getAmount() {
        return Amount;
    }

    public void setAmount(float Amount) {
        this.Amount = Amount;
    }

    public String getPaymentMethod() {
        return PaymentMethod;
    }

    public void setPaymentMethod(String PaymentMethod) {
        this.PaymentMethod = PaymentMethod;
    }

    public int getID_HoaDon() {
        return ID_HoaDon;
    }

    public void setID_HoaDon(int ID_HoaDon) {
        this.ID_HoaDon = ID_HoaDon;
    }   
    
}

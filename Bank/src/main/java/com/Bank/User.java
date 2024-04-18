package com.Bank;

import jakarta.persistence.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(name = "email")
    private String email;
    @Column(name = "balance")
    private double balance;

    public User(){}
    public User(String username, String password, String email, double balance) {
        this.username = username;
        this.password = password;
        this.email = email;
    }
    public double getBalance() {
        return balance;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public Integer getId() {
        return id;
    }
    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setEmail(String email) {this.email = email;}
    public void setUsername(String username) {this.username = username;}
    public void setPassword(String password) {this.password = password;}
    public void setId(Integer id) {this.id = id;}
}

package com.example.blog.auth;

// Semplice classe per mappare il JSON della richiesta di login
public class AuthenticationRequest {

    private String email;
    private String password;

    // Getters e Setters
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
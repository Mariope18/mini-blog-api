package com.example.blog.auth;

import jakarta.validation.constraints.NotBlank;

// Semplice classe per mappare il JSON della richiesta di registrazione
public class RegisterRequest {
    @NotBlank(message = "Lo username è obbligatorio")
    private String username;
    @NotBlank(message = "La password è obbligatoria")
    private String password;
    @NotBlank(message = "Il nome è obbligatorio")
    private String firstName;
    @NotBlank(message = "Il cognome è obbligatorio")
    private String lastName;
    @NotBlank(message = "L'email è obbligatoria")
    private String email;


    // Getters e Setters per tutti i campi...
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}


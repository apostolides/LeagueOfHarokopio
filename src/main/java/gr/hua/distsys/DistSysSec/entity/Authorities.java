package com.example.springbootmvc.entity;


import javax.persistence.*;

@Entity
@Table(name = "Authorities")
public class Authorities {

    @Id
    @Column(name = "username")
    private String Username;

    @Column(name = "role")
    private String Role;

    //FK from user
    @OneToOne(mappedBy = "authorities",cascade= {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private User user;

    public Authorities(String username, String role) {
        Username = username;
        Role = role;
    }

    public Authorities() {
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    @Override
    public String toString() {
        return "Authorities{" +
                "Username='" + Username + '\'' +
                ", Role='" + Role + '\'' +
                '}';
    }
}

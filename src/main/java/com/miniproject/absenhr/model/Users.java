package com.miniproject.absenhr.model;


import com.miniproject.absenhr.model.enums.Role;
import jakarta.persistence.*;



@Entity
@Table(name="users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "username" , length = 16, nullable = false , unique = true)
    private String username;

    @Column(name = "password", length = 64, nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column (name = "role",  nullable = false)
    private Role role;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

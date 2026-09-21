package com.careconnect.entity;



import jakarta.persistence.*;

@Entity
@Table(name = "auth_tokens")
public class AuthToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(
        nullable = false,
        unique = true,
        length = 100
    )
    private String token;

    @OneToOne
    @JoinColumn(
        name = "user_id",
        nullable = false,
        unique = true
    )
    private User user;

    public Long getId() {
        return id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
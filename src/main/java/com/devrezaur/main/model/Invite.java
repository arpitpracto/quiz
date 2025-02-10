package com.devrezaur.main.model;

import javax.persistence.*;

@Entity
@Table(name = "invites", schema = "user_db")  // ✅ Ensure same schema
public class Invite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inviteId;

    @ManyToOne
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


    @Column(nullable = false)
    private String status = "PENDING";

    public Invite() {}

    public Invite(Test test, User user) {  // ✅ Changed from Integer userId to User user
        this.test = test;
        this.user = user;
        this.status = "PENDING";
    }

    // Getters and Setters
    public Integer getInviteId() {
        return inviteId;
    }

    public void setInviteId(Integer inviteId) {
        this.inviteId = inviteId;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {  // ✅ Changed from Integer getUserId() to User getUser()
        return user;
    }

    public void setUser(User user) {  // ✅ Changed setter to accept User object
        this.user = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

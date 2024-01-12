package com.example.healthAppStarter.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Validated
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = User.class)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany(fetch = FetchType.LAZY)
    @JsonIdentityReference(alwaysAsId = true)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private Set<Role> roles = new HashSet<>();

    @Column(name = "full_name")
    @Size(max = 20)
    private String fullName;
    @Enumerated(EnumType.STRING)
    @Column(name = "speciality")
    private Speciality speciality;

    @Column(name = "username")
    @Size(max = 20)
    private String username;

    @Column(name = "email", unique = true, nullable = false)
    @Size(max = 50)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_available")
    private boolean isAvailable;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Schedule> schedules = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Review> review = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIdentityReference(alwaysAsId = true)
    private Set<Booking> booking = new HashSet<>();

    public User() {
    }

    public User(String fullName, Speciality speciality, String username, String email, String password) {
        this.fullName = fullName;
        this.speciality = speciality;
        this.username = username;
        this.email = email;
        this.password = password;
    }
    public void updateUserInfo(User updatedUser) {
        this.setFullName(updatedUser.getFullName());
        this.setSpeciality(updatedUser.getSpeciality());
        this.setUsername(updatedUser.getUsername());
        this.setEmail(updatedUser.getEmail());
        this.setPassword(updatedUser.getPassword());
        this.setAvailable(updatedUser.isAvailable());
    }


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public boolean isAvailable() {
        return isAvailable;
    }
    public void setAvailable(boolean available) {
        isAvailable = available;
    }
    public Speciality getSpeciality() {
        return speciality;
    }
    public void setSpeciality(Speciality speciality) {
        this.speciality = speciality;
    }
    public Set<Schedule> getSchedules() {
        return schedules;
    }
    public void setSchedules(Set<Schedule> schedules) {
        this.schedules = schedules;
    }
    public Set<Review> getReview() {
        return review;
    }
    public void setReview(Set<Review> review) {
        this.review = review;
    }
    public Set<Booking> getBooking() {
        return booking;
    }
    public void setBooking(Set<Booking> booking) {
        this.booking = booking;
    }
}


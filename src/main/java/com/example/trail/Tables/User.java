package com.example.trail.Tables;



import jakarta.persistence.*;


import java.time.LocalDateTime;

@Entity
@Table(name = "app_users") // Avoid 'user' as it's a reserved keyword in some DBs

public class User {

    @Id
    @Column(name = "sub_id")
    private String subId; // Stores the 'sub' (subject) claim from the IdP

    @Column(unique = true, nullable = false)
    private String email;

    private String name;

    private String givenName;

    private String familyName;

    private String pictureUrl;

    @Column(nullable = false)
    private boolean emailVerified;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User(String subId, String email, String name, String givenName, String familyName, String pictureUrl,
			boolean emailVerified, LocalDateTime createdAt, LocalDateTime updatedAt) {
		super();
		this.subId = subId;
		this.email = email;
		this.name = name;
		this.givenName = givenName;
		this.familyName = familyName;
		this.pictureUrl = pictureUrl;
		this.emailVerified = emailVerified;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
    
}
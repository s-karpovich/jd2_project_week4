package by.tut.mdcatalog.week4app.repository.model;

public class User {
    private Long id;
    private String username;
    private String password;
    private String role;
    private Boolean isDeleted;

    public User(Long id, String name, String password, String role, Boolean isDeleted) {
        this.id = id;
        this.username = name;
        this.password = password;
        this.role = role;
        this.isDeleted = isDeleted;
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

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    public String getRole() { return role; }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }
}


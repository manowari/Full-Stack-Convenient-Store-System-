package backend.user;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;



@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Integer id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, length = 100, nullable = false)
    private String email;


    @Column(nullable = false, unique = true)
    private String userName;

    @Column(nullable = false)
    private String password;


    @Column(unique = true)
    private final String pf;

    public User(String pf, String fullName, int workClass, String username, String email, String userRole, String password) {
        this.pf = pf;
        // Initialize other fields
        this.fullName = fullName;
        this.workClass = workClass;
        this.userName = username;
        this.email = email;
        this.userRole = userRole;
        this.password = password;
    }

    // Getter method for pf field
    public String getPf() {
        return pf;
    }


    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }



    @CreationTimestamp
    @Column(updatable = false, name = "created_at")
    private Date createdAt;


    public int getWorkclass() {
        return workClass;
    }

    public int getWorkClass() {
        return workClass;
    }

    public void setWorkclass(int workClass) {
        this.workClass = workClass;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    @Column(nullable = false, columnDefinition = "INT DEFAULT 1 CHECK (workclass BETWEEN 1 AND 5)")
    private int workClass;

    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'officer'")
    private String userRole;





    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // Getters and setters



    public boolean isValidUsername(String username) {
        // Define regex pattern for allowed characters (alphanumeric and hyphen) and length (3-20 characters)
        String regex = "^[a-zA-Z0-9-]{3,20}$";
        return username.matches(regex);
    }

}


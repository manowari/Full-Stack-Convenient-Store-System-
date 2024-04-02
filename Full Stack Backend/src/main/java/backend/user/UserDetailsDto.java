package backend.user;

public class UserDetailsDto {
    private String fullName;
    private int workClass;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getWorkClass() {
        return workClass;
    }

    public void setWorkClass(int workClass) {
        this.workClass = workClass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    private String userName;
    private String email;
    private String userRole;

    // Constructors, getters, and setters
}


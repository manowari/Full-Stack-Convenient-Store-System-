package backend.user;

public class UserDetailsDto {
    private String fullName;
    private int workClass;

    public void setPf(String pf) {
        this.pf = pf;
    }

    private String pf;

    private String userName;
    private String email;
    private String userRole;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String password;






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

    public void setUserName(String username) {
        this.userName = username;
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

    public String getPf() {
        return pf;
    }


    // Constructors, getters, and setters
}


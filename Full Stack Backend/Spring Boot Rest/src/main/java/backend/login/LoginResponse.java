package backend.login;

public class LoginResponse {
    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    private long expiresIn;

    public String getToken() {
        return token;
    }

    // Getters and setters...
}

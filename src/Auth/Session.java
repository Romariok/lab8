package Auth;

public class Session {
    private String user;
    private boolean authorized;
    public Session(){
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isAuthorized() {
        return authorized;
    }

    public void setAuthoriazed(boolean authorized) {
        this.authorized = authorized;
    }
}

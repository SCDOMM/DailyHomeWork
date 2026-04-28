package model;

public class LoginData {
    private String account;
    private String password;

    public LoginData(String account,String password){
        this.account=account;
        this.password=password;
    }
    public String getAccount() {
        return account;
    }
    public String getPassword() {
        return password;
    }
}

package model;

public class RegisterData {
    private String userName;
    private String account;
    private String password;
    public RegisterData(String userName,String account,String password){
        this.account=account;
        this.password=password;
        this.userName=userName;
    }
    public String getAccount() {
        return account;
    }
    public String getPassword() {
        return password;
    }
    public String getUserName(){return userName;}
}

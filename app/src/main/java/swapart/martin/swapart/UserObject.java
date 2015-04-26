package swapart.martin.swapart;

/**
 * Created by mtkx on 26-04-2015.
 */
public class UserObject {

    private String username;
    private String password;
    private String fullname;
    private String city;
    private String phone;


    public UserObject(String Username, String Password, String Fullname, String City, String Phone) {

        this.username = Username;
        this.password = Password;
        this.fullname = Fullname;
        this.city = City;
        this.phone = Phone;
    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getFullname() { return fullname; }
    public String getCity() { return city; }
    public String getPhone() { return phone; }
}

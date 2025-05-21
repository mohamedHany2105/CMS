 // Contact.java
 // Replace 'yourpackage' with your actual package name

public class Contact {
    private int sno; // Assuming 'sno' is an integer ID
    private String name;
    private String phone; // Kept as String to handle various formats, but was int in your getphone()
    private String address;
    private String email;

    // Default constructor
    public Contact() {
    }

    // Constructor with all fields
    public Contact(int sno, String name, String phone, String address, String email) {
        this.sno = sno;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.email = email;
    }

    // Getters
    public int getSno() {
        return sno;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public String getEmail() {
        return email;
    }

    // Setters
    public void setSno(int sno) {
        this.sno = sno;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Contact{" +
               "sno=" + sno +
               ", name='" + name + '\'' +
               ", phone='" + phone + '\'' +
               ", address='" + address + '\'' +
               ", email='" + email + '\'' +
               '}';
    }
}
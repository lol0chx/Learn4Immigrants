package classes;

public class Provider {
    // private member


    private  String name;
    private  String contactInfo ;



    //  contractor

    public Provider(String name, String contactInfo) {
        this.name = name;
        this.contactInfo = contactInfo;
    }

    // getter and setter
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }
}

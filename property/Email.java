package property;
//import user.RegisteredRenter; when connected package add this
//import user.Landlord; when connected package add this

public class Email {
    private RegisteredRenter renter;
    private Property interestedIn;
    private String message;

    public Email(RegisteredRenter renter, Property interestedIn) {
        this.renter = renter;
        this.interestedIn = interestedIn;
    }

    public Email(RegisteredRenter renter, Property interestedIn, String message) {
        this.renter = renter;
        this.interestedIn = interestedIn;
        this.message = message;
    }

    public boolean sendMessage(){
        boolean sucesfull = true;
        /*try{
            sendTo:
                getPropertyOwner().getEmail();
                getPropertyOwner().getName();
        } catch{
            return false;
        }
     
        */
        if(sucesfull){
            return true;
        } 

        return false;
    }

    private Landlord getPropertyOwner(){
        return interestedIn.getPostedBy();
    }

    public RegisteredRenter getRenter() {
        return this.renter;
    }

    public void setRenter(RegisteredRenter renter) {
        this.renter = renter;
    }

    public Property getInterestedIn() {
        return this.interestedIn;
    }

    public void setInterestedIn(Property interestedIn) {
        this.interestedIn = interestedIn;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}

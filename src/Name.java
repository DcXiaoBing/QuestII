public class Name {

    private final String firstName, lastName;

    Name(String _firstName, String _lastName){
        firstName = _firstName;
        lastName = _lastName;
    }
    Name(String name){
        this(name, "");
    }

    public String getFirstName(){
        return firstName;
    }
    public String getFullName(){
        if(lastName.equals(""))
            return firstName;
        else 
            return firstName + " " + lastName;
    }
}
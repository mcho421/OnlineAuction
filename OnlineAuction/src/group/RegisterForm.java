package group;

import java.util.Hashtable;

public class RegisterForm {
private String username = "";
private String userpwd = "";
private String useremail = "";
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getUserpwd() {
	return userpwd;
}
public void setUserpwd(String userpwd) {
	this.userpwd = userpwd;
}
public String getUseremail() {
	return useremail;
}
public void setUseremail(String useremail) {
	this.useremail = useremail;
}
private Hashtable<String, String> errors= new Hashtable<String, String>();
public boolean validte() {
boolean okAll = true;
if (username.length()>10 || username.length()<6) {
	errors.put("name", "The length of username must be between 6 and 10.");
	okAll = false;
}
if(!username.matches("[a-zA-Z0-9_-]+")) {
	errors.put("name", "The content of username must be numbers, alphabets or mixing above.");
	okAll = false;
}
if (userpwd.length() > 10 ||userpwd.length() < 6) {
    errors.put("password","The length of password must be between 6 and 10");
    okAll = false;
}
if (!userpwd.matches("[a-zA-Z0-9_-]+")) {
	errors.put("password","The content of password must be numbers, alphabets or mixing above.");
	okAll = false;
	}

if(!useremail.matches("[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)+")){
    errors.put("email", "Invalide email address, all the alphabets must be lower case.");
    okAll = false;
}
return okAll;
}
public void setErrorMsg(String err,String errMsg) {
if (err != null && errMsg !=null) {
errors.put(err, errMsg);
}
}
public String getErrorMsg(String err) {
Object message = (String)errors.get(err);
return (String) ((message == null) ? "" : message);
}

}

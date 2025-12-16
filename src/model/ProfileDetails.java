package model;
//ProfileDetails.java
public class ProfileDetails {
 private String bio;
 private String hobby;

 public ProfileDetails(String bio, String hobby) {
     this.bio = bio;
     this.hobby = hobby;
 }

 @Override
 public String toString() {
     return "\nBio: " + bio + " | Hobby: " + hobby;
 }
}
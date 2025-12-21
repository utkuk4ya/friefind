package model;

public class Meeting {
    private String time;       
    private User user1;        
    private User user2;       
    private Location place;    
    private Activity activity; 

    
    public Meeting(User user1, User user2, Location place, Activity activity, String time) {
        this.user1 = user1;
        this.user2 = user2;
        this.place = place;
        this.activity = activity;
        this.time = time;
    }

    public void setTime(String time) {
        this.time = time;
        System.out.println("Meeting time updated to: " + time);
    }

    @Override
    public String toString() {
        return "\n=== MEETING DETAILS ===\n" +
               "Participants: " + user1.getName() + " & " + user2.getName() + "\n" +
               "Time        : " + time + "\n" +
               "Location    : " + place.getCity() + "\n" +
               "Activity    : " + activity + "\n" +
               "=======================";
    }

	public Activity getActivity() {
		return activity;
	}

}
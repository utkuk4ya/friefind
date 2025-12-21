package model;

public class Location {
	private String city;
	private double latitude;
	private double longitude;

	public Location(String city, double latitude, double longitude) {
		this.city = city;
		this.latitude = latitude;
		this.longitude = longitude;
	}

	public double calculateDistance(Location other) {

		double latDiff = this.latitude - other.latitude;
		double lonDiff = this.longitude - other.longitude;
		return Math.sqrt((latDiff * latDiff) + (lonDiff * lonDiff)) * 111;
	}

	@Override
	public String toString() {
		return city + " [" + latitude + ", " + longitude + "]";
	}

	public String getCity() {
		return city;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
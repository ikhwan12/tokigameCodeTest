package com.sbfs.api.model;

public class Flight {
	private String id;
	private String departure;
	private String arrival;
	private String arrivalTime;
	private String departureTime;
	
	public Flight() {
		
	}
	
	public Flight(String uuid, String flight, String arrival, String departure) {
		this.id = uuid;
		this.arrival = this.generateArrival(flight);
		this.departure = this.generateDeparture(flight);
		this.arrivalTime = arrival;
		this.departureTime = departure;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = departure;
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = arrival;
	}
	public String getDepartureTime() {
		return departureTime;
	}
	public void setDepartureTime(String departureTime) {
		this.departureTime = departureTime;
	}
	public String getArrivalTime() {
		return arrivalTime;
	}
	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	private String generateArrival(String flight) {
		String[] split = flight.split(" -> ");
		return split[0];
	}
	private String generateDeparture(String flight) {
		String[] split = flight.split(" -> ");
		return split[1];
	}
}

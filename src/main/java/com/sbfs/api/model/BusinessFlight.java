package com.sbfs.api.model;

import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class BusinessFlight {
	private String uuid;
	private String flight;
	private String departure;
	private String arrival;

	public BusinessFlight() {
		
	}
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getFlight() {
		return flight;
	}
	public void setFlight(String flight) {
		this.flight = flight;
	}
	public String getDeparture() {
		return departure;
	}
	public void setDeparture(String departure) {
		this.departure = this.toUnixTimestamp(departure);
	}
	public String getArrival() {
		return arrival;
	}
	public void setArrival(String arrival) {
		this.arrival = this.toUnixTimestamp(arrival);
	}
	private String toUnixTimestamp(String timestamp) {
		timestamp = timestamp.replace('T', ' ');
		timestamp = timestamp.replace(timestamp.substring(timestamp.length()-5, timestamp.length()), "");
		SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd HH:mm:ss");
		try {
			Date dt = sdf.parse(timestamp);
			Long epoch = dt.getTime();
			return epoch.toString();
		} catch (ParseException e) {
			return e.getMessage();
		}
	}
}

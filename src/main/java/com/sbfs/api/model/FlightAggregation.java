package com.sbfs.api.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.web.client.RestTemplate;

public class FlightAggregation {

	static final String URL_CHEAP = "https://obscure-caverns-79008.herokuapp.com/cheap";
	static final String URL_BUSINESS = "https://obscure-caverns-79008.herokuapp.com/business";
//	static final String URL_CHEAP = "https://testnlearn.000webhostapp.com/cheap.json";
//	static final String URL_BUSINESS = "https://testnlearn.000webhostapp.com/business.json";
	static final Integer PER_PAGE = 10;

	private ArrayList<Flight> flightTable;
	
	public FlightAggregation() {
		
	}
	
	public FlightAggregation(ArrayList<Flight> flights) {
		this.flightTable = flights; 
	}

	public ArrayList<Flight> getFlightTable() {
		return flightTable;
	}

	public void setFlightTable(ArrayList<Flight> flightTable) {
		this.flightTable = flightTable;
	}
	
	public ArrayList<Flight> getFilteredFlight(String filter){
		RestTemplate restTemplate = new RestTemplate();
		if(filter.equals("cheap")) {
			Flight[] cflights = restTemplate.getForObject(URL_CHEAP, Flight[].class);
			this.flightTable = new ArrayList<Flight>(Arrays.asList(cflights));
		}else if(filter.equals("business")) {
			BusinessFlight[] bflights = restTemplate.getForObject(URL_BUSINESS, BusinessFlight[].class);
			this.flightTable = new ArrayList<Flight>();
			for (BusinessFlight f : bflights) {
				Flight cf = new Flight(f.getUuid(), f.getFlight(), f.getArrival(), f.getDeparture());
				flightTable.add(cf);
	    	}
		}else {
			Flight[] cflights = restTemplate.getForObject(URL_CHEAP, Flight[].class);
			BusinessFlight[] bflights = restTemplate.getForObject(URL_BUSINESS, BusinessFlight[].class);
			flightTable = new ArrayList<Flight>(Arrays.asList(cflights));
			for (BusinessFlight f : bflights) {
				Flight cf = new Flight(f.getUuid(), f.getFlight(), f.getArrival(), f.getDeparture());
				flightTable.add(cf);
	    	}
		}
		return this.flightTable;
	}
	
	public ArrayList<Flight> getSortedFlight(String sortColumn, String sortOrder){
		if(!sortColumn.isEmpty()) {
			Collections.sort(this.flightTable, new Comparator<Flight>() {

				@Override
				public int compare(Flight f1, Flight f2) {
					if(sortColumn.equals("arrival")) {
						return f1.getArrival().compareToIgnoreCase(f2.getArrival());
					}else if(sortColumn.equals("departure")) {
						return f1.getDeparture().compareToIgnoreCase(f2.getDeparture());
					}else if(sortColumn.equals("arrivalTime")) {
						return f1.getArrivalTime().compareToIgnoreCase(f2.getArrivalTime());
					}else if(sortColumn.equals("departureTime")) {
						return f1.getDepartureTime().compareToIgnoreCase(f2.getDepartureTime());
					}else {
						return f1.getId().compareToIgnoreCase(f2.getId());
					}
				}
			});
		}
		
		if(sortOrder.equals("desc")) {
			Collections.reverse(this.flightTable);
		}
		
		return this.flightTable;
	}
	
	public ArrayList<Flight> getFlightByDepartureAndArrival(String departure, String arrival){
		ArrayList<Flight> temp = new ArrayList<Flight>();
		if(departure == "" || arrival == ""){
			return this.flightTable;
		}
		else {
			for (Flight f : this.flightTable) {
				if (f.getArrival().toLowerCase().contains(arrival.toLowerCase()) &&
						f.getDeparture().toLowerCase().contains(departure.toLowerCase())) {
					temp.add(f);
				}
			}
			this.flightTable = temp;
			return this.flightTable;
		}
	}
	
	public ArrayList<Flight> getFlightByDepTimeAndArrTime(Date departureTime, Date arrivalTime) {
		ArrayList<Flight> temp = new ArrayList<Flight>();
		if(arrivalTime == null) {
			Long depUnixIn = departureTime.getTime();
    		for (Flight f : this.flightTable) {
    			Long depUnix = Long.parseLong(f.getDepartureTime());
				if(depUnix <= depUnixIn ) {
				   temp.add(f);
				}
			}	
		}else if(departureTime == null) {
			Long arrUnixIn = arrivalTime.getTime();
    		for (Flight f : this.flightTable) {
    			Long arrUnix = Long.parseLong(f.getArrivalTime());
				if(arrUnix <= arrUnixIn ) {
				   temp.add(f);
				}
			}
		}else {
			Long depUnixIn = departureTime.getTime();
			Long arrUnixIn = arrivalTime.getTime();
    		for (Flight f : this.flightTable) {
    			Long depUnix = Long.parseLong(f.getDepartureTime());
    			Long arrUnix = Long.parseLong(f.getArrivalTime());
				if(depUnix <= depUnixIn && arrUnix <= arrUnixIn ) {
				   temp.add(f);
				}
			}
		}
		this.flightTable = temp;
		return this.flightTable;
	}
	
	public ArrayList<Flight> getPaginatedFlightSearchResult(int pagination){
		int max_pag = this.flightTable.size() / PER_PAGE;
		
		if(max_pag < 1) {
			pagination = 1;
		}else if(pagination > max_pag) {
			pagination = max_pag;
		}

		if(pagination == 0){
			return this.flightTable;
		}else{
			int start = (pagination-1)*PER_PAGE;
			int end = pagination*PER_PAGE;

			if(start < 0){
				start = 0;
			}
			if(end >= this.flightTable.size()) {
				end = this.flightTable.size();
			}

			List<Flight> res = new ArrayList<Flight>();
			if(this.flightTable.size() > 0) {
				res = this.flightTable.subList(start, end);
			}
			this.flightTable = new ArrayList<Flight>(res);
			return this.flightTable;
		}
	}
	
}

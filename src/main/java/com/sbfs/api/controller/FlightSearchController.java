package com.sbfs.api.controller;

import com.sbfs.api.model.Flight;
import com.sbfs.api.model.FlightAggregation;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sbfs.api.beans.Result;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class FlightSearchController {
	
	@GetMapping("/flight/search")

    @ResponseBody

    public String flightSearch(@RequestParam(name="arrival",required=false, defaultValue="") String arrival,
    						   @RequestParam(name="departure",required=false, defaultValue="") String departure,
    						   @RequestParam(name="arrivalTime", required=false, defaultValue="" ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date arrivalTime,
    						   @RequestParam(name="departureTime", required=false, defaultValue="" ) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") Date departureTime,
    						   @RequestParam(name="sortColumn", required=false, defaultValue="id") String sortColumn,
    						   @RequestParam(name="sortOrder", required=false, defaultValue="asc") String sortOrder,
    						   @RequestParam(name="filter", required=false, defaultValue="all") String filter,
    						   @RequestParam(name="pagination", required=false, defaultValue="0") @Min(1) int pagination) {
	    	
	    	String resp;
	    	ObjectMapper mapper = new ObjectMapper();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	
	    	ArrayList<Flight> flights = new ArrayList<Flight>();
	    	FlightAggregation flightAgg = new FlightAggregation(flights);
	    	
	    	//Filtering
    		flights = flightAgg.getFilteredFlight(filter);
	    	
    		//Sorting
    		flights = flightAgg.getSortedFlight(sortColumn, sortOrder);
    		
    		//Query by departure and arrival
    		flights = flightAgg.getFlightByDepartureAndArrival(departure, arrival);
    		//Query by departure time and arrival time
    		if(arrivalTime != null || departureTime != null) {
        		flights = flightAgg.getFlightByDepTimeAndArrTime(departureTime, arrivalTime);
    		}
    		
    		//Pagination
    		flights = flightAgg.getPaginatedFlightSearchResult(pagination);
    		
    		//Change Unix timestamp to "yyyy-MM-dd HH:mm:ss" format for json response
    		int idx = 0;
    		for (Flight f : flights) {
				Date dArrival = new Date(Long.parseLong(f.getArrivalTime()));
				flights.get(idx).setArrivalTime(sdf.format(dArrival));
				Date dDeparture = new Date(Long.parseLong(f.getDepartureTime()));
				flights.get(idx).setDepartureTime(sdf.format(dDeparture));
				idx++;
			}
    		
 			try {
 				String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(flights.toArray());
 				resp = jsonInString;
 			} catch (JsonGenerationException e) {
 				resp = e.getMessage();
 			} catch (JsonMappingException e) {
 				resp = e.getMessage();
 			} catch (IOException e) {
 				resp = e.getMessage();
 			}
	    	
			Result result = new Result(resp);
	        return result.getResponse();
	 }
}

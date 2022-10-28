package travelplannerpcm.interfaces;


import java.lang.Iterable;

import travelplannerpcm.datatypes.FlightOffer;


		
public interface AirlineAdministration {
			
	void setAvailableFlights(Iterable<FlightOffer> flights); 

}
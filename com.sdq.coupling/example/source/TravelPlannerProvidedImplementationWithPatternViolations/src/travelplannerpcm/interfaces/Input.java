package travelplannerpcm.interfaces;

import java.lang.Iterable;

import travelplannerpcm.datatypes.FlightOffer;

public interface Input {
			
	void getInput(); 
	void getSingleSelection(Iterable<FlightOffer> flightOffers); 

}
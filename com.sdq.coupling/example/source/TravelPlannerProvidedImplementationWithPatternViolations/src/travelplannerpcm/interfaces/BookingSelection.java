package travelplannerpcm.interfaces;

import travelplannerpcm.datatypes.FlightOffer;
import travelplannerpcm.datatypes.RequestData;
import travelplannerpcm.datatypes.UserCredentials;

public interface BookingSelection {
			
	void bookSelected(FlightOffer flightOffer); 
	void getFlightOffers(RequestData requestData); 

}
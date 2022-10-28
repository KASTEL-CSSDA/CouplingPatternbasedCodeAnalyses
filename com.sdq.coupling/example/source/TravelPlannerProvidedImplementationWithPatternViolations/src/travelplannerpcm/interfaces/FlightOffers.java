package travelplannerpcm.interfaces;
import java.lang.Iterable;

import travelplannerpcm.datatypes.FlightOffer;
import travelplannerpcm.datatypes.RequestData;
import travelplannerpcm.datatypes.UserCredentials;

public interface FlightOffers {
			
	Iterable<FlightOffer> getFlightOffers(RequestData requestData); 

}
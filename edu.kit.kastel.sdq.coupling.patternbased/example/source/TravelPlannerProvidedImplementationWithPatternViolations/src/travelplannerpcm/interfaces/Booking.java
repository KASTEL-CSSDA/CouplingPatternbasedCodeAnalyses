package travelplannerpcm.interfaces;

import travelplannerpcm.datatypes.CreditCardDetails;

public interface Booking {
			
	boolean bookFlightOffer(int offerId, CreditCardDetails ccd_decl); 

}
package travelplannerpcm.interfaces;

import travelplannerpcm.datatypes.AirlineSpec;
import travelplannerpcm.datatypes.CreditCardDetails;

public interface Confirmation {
			
	void confirmRelease(CreditCardDetails ccd, AirlineSpec spec); 

}
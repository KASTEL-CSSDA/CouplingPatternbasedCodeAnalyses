package travelplannerpcm.interfaces;

import travelplannerpcm.datatypes.AirlineSpec;
import travelplannerpcm.datatypes.CreditCardDetails;

public interface DeclassificationConfirmation {
			
	void getDeclassifiedCCD(CreditCardDetails ccd, AirlineSpec airline); 

}
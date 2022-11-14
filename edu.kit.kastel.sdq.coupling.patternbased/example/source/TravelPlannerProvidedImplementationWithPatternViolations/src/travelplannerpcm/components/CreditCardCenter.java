package travelplannerpcm.components;


import travelplannerpcm.datatypes.AirlineSpec;
import travelplannerpcm.datatypes.CreditCardDetails;
import travelplannerpcm.interfaces.CreditCardConfiguration;
import travelplannerpcm.interfaces.Confirmation;

import travelplannerpcm.interfaces.Declassification;
import travelplannerpcm.interfaces.DeclassificationConfirmation;
import travelplannerpcm.interfaces.DeclassificationReceiver;

public class CreditCardCenter implements Declassification, CreditCardConfiguration, DeclassificationConfirmation{
	
	private Confirmation confirmation;
	private CreditCardDetails ccd;
	private DeclassificationReceiver receiver;
	
	public CreditCardCenter(Confirmation confirmation) {
		// TODO: implement and verify auto-generated constructor.
	    this.confirmation = confirmation;
	}
	
	@Override
	public void releaseCCD(AirlineSpec spec){
		confirmation.confirmRelease(ccd, spec);
	}
	
	@Override
	public void getDeclassifiedCCD(CreditCardDetails ccd, AirlineSpec airline){
		//TODO: Normally Declassify, but here, forget to find leak error
		if(this.ccd == ccd) {
			try {
				receiver.declassifiedCCD(declassify(ccd, airline));
			} catch (Exception e) {
				receiver.declassifiedCCD(ccd);
			}
		}
	}
	
	public void setConfirmation(Confirmation confirmation) {
		this.confirmation = confirmation;
	}
	
	public void setDeclassificationReceiver(DeclassificationReceiver receiver) {
		this.receiver = receiver;
	}

	@Override
	public void setCreditCard(CreditCardDetails ccd) {
		this.ccd = ccd;
	}

	@Override
	public CreditCardDetails getCreditCard() {
		return this.ccd;
	}
	
	@edu.kit.joana.ui.annotations.Declassification (tags = "TODO:MODIFY", from = "User", to = "UserAirline") 
	private CreditCardDetails declassify(CreditCardDetails ccd, AirlineSpec airline) {
		//Do some fancy declassification here.
		int airlineId = airline.getAirlineId();
		CreditCardDetails ccd_decl = new CreditCardDetails(ccd.getProvider(), ccd.getCreditCardNumber()); 
		return ccd_decl;
	}
}
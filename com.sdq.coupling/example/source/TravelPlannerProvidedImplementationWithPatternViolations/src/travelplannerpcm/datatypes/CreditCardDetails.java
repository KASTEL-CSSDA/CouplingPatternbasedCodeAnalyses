package travelplannerpcm.datatypes;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class CreditCardDetails {
	
	private byte[] creditCardNumber;
	private byte[] provider;
	private byte[] lastDigits;
	
	public CreditCardDetails(String provider, int creditCardNumber) {
		this.provider = provider.getBytes(StandardCharsets.UTF_8);
		this.creditCardNumber = ByteBuffer.allocate(4).putInt(creditCardNumber).array();
		this.lastDigits = ByteBuffer.allocate(4).putInt(creditCardNumber / 1000).array();
	}
	
	public CreditCardDetails(byte[] provider, byte[] creditCardNumber, byte[] lastDigits) {
	  this.provider = provider;
	  this.creditCardNumber = creditCardNumber;
	  this.lastDigits = lastDigits;
	}

	public int getCreditCardNumber() {
	  int value = 0;
      for (byte b : creditCardNumber) {
        value = (value << 8) + (b & 0xFF);
      }
      return value;
	}
	
	public byte[] getCreditCardNumberAsBytes() {
	  return this.creditCardNumber;
	}
	

	public void setCreditCardNumber(int creditCardNumber) {
		this.creditCardNumber = ByteBuffer.allocate(4).putInt(creditCardNumber).array();
		this.lastDigits = ByteBuffer.allocate(4).putInt(creditCardNumber / 1000).array();
	}

	public int getLastDigits() {
	    int value = 0;
	    for (byte b : lastDigits) {
	      value = (value << 8) + (b & 0xFF);
	    }
	    return value;
	}
	
	public byte[] getLastDigitsAsBytes() {
	  return this.lastDigits;
	}

	public String getProvider() {
		return new String(provider, StandardCharsets.UTF_8);
	}
	
	public byte[] getProviderAsBytes() {
	  return this.provider;
	}

	public void setProvider(String provider) {
		this.provider = provider.getBytes(StandardCharsets.UTF_8);;
	}
}
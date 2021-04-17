package loanPaymentBridge;
import java.math.BigDecimal;

/**
 * output row class represents one row of the output table 
 * includes a numerical identifier of the form "xx-xxx-xxxx" 
 * and a debit and credit dollar amount
 * @author ethan horton
 *
 */
public class OutputRow {
	private String identifier;
	private BigDecimal debit, credit; 
	
	/**
	 * fill instance variables from class parameters
	 * @param identifier numerical identifier for entry
	 * @param debit debit dollar amount
	 * @param credit credit dollar amount
	 */
	public OutputRow(String identifier, BigDecimal debit, BigDecimal credit) {
		this.identifier = identifier; 
		this.debit = debit; 
		this.credit = credit; 
	}
	
	public String getId() {
		return identifier; 
	}
	
	public BigDecimal getDebit() {
		return debit; 
	}
	
	public BigDecimal getCredit() {
		return credit; 
	}
	
	@Override 
	public String toString() {
		return "Identifier: " + identifier + ", Debit: " + debit + ", Credit: " + credit; 
	}

}

package loanPaymentBridge;
import java.math.BigDecimal;

/**
 * Representation of a single row of the report input including only those values that we need
 * specifically, loan group, principal, and interest
 * @author ethan horton
 *
 */
public class ReportRow {
	
	//instance variables for individual components of the report row
	private String loanGroup;
	private BigDecimal principal, interest; 
	
	/**
	 * constructor fills instance variables from parameters
	 * @param loanGroup
	 * @param principal
	 * @param interest
	 */
	public ReportRow(String loanGroup, BigDecimal principal, BigDecimal interest) {
		this.loanGroup = loanGroup; 
		this.principal = principal; 
		this.interest = interest; 
	}
	
	//publicly available accessor methods for specified fields
	public String getLoanGroup() {
		return loanGroup; 
	}
	
	public BigDecimal getPrincipal() {
		return principal; 
	}
	
	public BigDecimal getInterest() {
		return interest; 
	}
	
	@Override
	public String toString() {
		return "Loan Group: " + loanGroup + ", Principal: " + principal + ", Interest: " + interest; 
	}

}

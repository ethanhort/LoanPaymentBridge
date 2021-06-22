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
	private BigDecimal principal, interest, escrow, servicing; 
	
	/**
	 * constructor fills instance variables from parameters
	 * @param loanGroup
	 * @param principal
	 * @param interest
	 */
	public ReportRow(String loanGroup, BigDecimal principal, BigDecimal interest, BigDecimal escrow, BigDecimal servicing) {
		this.loanGroup = loanGroup; 
		this.principal = principal; 
		this.interest = interest; 
		this.escrow = escrow; 
		this.servicing = servicing; 
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
	
	public BigDecimal getEscrow() {
		return escrow; 
	}
	
	public BigDecimal getServicing() {
		return servicing; 
	}
	
	@Override
	public String toString() {
		return "Loan Group: " + loanGroup + ", Principal: " + principal + ", Interest: " + interest + ", Escrow: " + escrow + ", Servicing: " + servicing; 
	}

}

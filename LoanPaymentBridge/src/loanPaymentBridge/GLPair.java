package loanPaymentBridge;

import java.math.BigDecimal;

/**
 * Utility class that associates a GL code with the monetary value associated with that code 
 * @author Ethan Horton
 *
 */
public class GLPair {
	
	private BigDecimal value; 
	private String GLNum; 
	
	public GLPair(String GLNum, BigDecimal value) {
		this.value = value;
		this.GLNum = GLNum; 
	}
	
	public BigDecimal getValue() {
		return value; 
	}
	
	public String getGLNum() {
		return GLNum; 
	}

}

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
	
	public void subtract(BigDecimal arg) {
		value = value.subtract(arg); 
	}
	
	public BigDecimal getValue() {
		return value; 
	}
	
	public String getGLNum() {
		return GLNum; 
	}
	
	@Override
	public String toString() {
		return GLNum + ", " + value;
	}

}

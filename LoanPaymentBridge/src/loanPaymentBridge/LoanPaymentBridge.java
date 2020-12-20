/**
 * 
 */
package loanPaymentBridge;

/**
 * @author Ethan Horton
 * @version 0.0.1
 * @email ehorton14892@gmail.com, emha2017@mymail.pomona.edu
 * 
 * Loan Payment bridge allows for automated data processing of loan distributions to ensure compatibility with MIP program for use
 * at Communities Unlimited of Fayetteville, Arkansas. In the event that this system breaks while in use at CU, please contact me 
 * at the email address(es) above.  
 */
public class LoanPaymentBridge {

	//indices for input fields. used for array of text fields/array of values
	private static final int ID_TEXT = 0; 
	private static final int SESS_DATE_TEXT = 1; 
	private static final int SESS_DESCRIPTION_TEXT = 2;
	private static final int DOC_NUM_TEXT = 3;
	private static final int DOC_DATE_TEXT = 4;
	private static final int DOC_DESCRIPTION_TEXT = 5;
	private static final int EFFECTIVE_DATE_TEXT = 6;
	/**
	 * @param args
	 * 		I hope I don't need to tell you what this is. 
	 * This program is intended to be interacted with through the UI, so there should not be any runtime arguments
	 */
	public static void main(String[] args) {
		
		//initialize the user interface when program starts
		UIHandler userInterface = new UIHandler(); 

	}

}

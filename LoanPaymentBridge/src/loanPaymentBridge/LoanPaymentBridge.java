/**
 * 
 */
package loanPaymentBridge;

import java.util.concurrent.TimeUnit;

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
	
	//indices for excel report used to populate data from excel file
	private static final int LOAN_GROUP_INDEX = 2; 
	private static final int PRINCIPAL_INDEX = 7; 
	private static final int INTEREST_INDEX = 8; 
	
	//values to be taken from UI
	private static String reportFilePath; //filepath of report spreadsheet 
	private static String[] userInputs; //array containing user-inputted data except gls
	private static Integer numGLCodes; //number of gl codes user has inputted
	private static GLPair[] GLs; //array containing gl codes and their values
	
	/**
	 * takes data that user inputs through the UI and populates it into the necessary instance variables
	 * @param ui user interface object
	 */
	public static void populateDataFromUI(UIHandler ui) {
		reportFilePath = ui.getFilePath();
		userInputs = ui.getUserInputs();
		numGLCodes = ui.getNumGLCodes();
		GLs = ui.getGLCodes();
	}
	
	/**
	 * @param args
	 * 		I hope I don't need to tell you what this is. 
	 * This program is intended to be interacted with through the UI, so there should not be any runtime arguments
	 */
	public static void main(String[] args) {
		
		//initialize the user interface when program starts
		UIHandler userInterface = new UIHandler(); 
		
		//wait for user to finish inputting data into UI. could probably be done with listeners but... this works 
		while (!userInterface.isFinished()) {
			try {
				TimeUnit.SECONDS.sleep(1);  				
			}
			catch (Exception e) {
				
			}
		}
		
		//get data from user interface
		populateDataFromUI(userInterface);
		
		//testing purposes only
		System.out.println(reportFilePath);
		System.out.println("Num GL Codes: " + numGLCodes);
		for(int i = 0; i < userInputs.length; i++) {
			System.out.println(userInputs[i]);
		}
		System.out.println("");
		for(int i = 0; i < GLs.length; i++) {
			System.out.println(GLs[i]);
		}

	}

}

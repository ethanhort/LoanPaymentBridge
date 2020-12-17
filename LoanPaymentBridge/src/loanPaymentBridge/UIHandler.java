package loanPaymentBridge;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel; 

/**
 * Utility class to build and operate UI elements abstracted from main computation
 * @author Ethan Horton
 *
 */
public class UIHandler {

	/**
	 * Basic constructor initializes initial frame in fullscreen mode as requested by client. 
	 */
	public UIHandler() {

		//Create Window that Program will be displayed in
		JFrame frame = new JFrame("Loan Payment Bridge"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//create top level panel to contain all other UI components
		JPanel panel = new JPanel(); 
		frame.add(panel); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		
		
		
		
		frame.setVisible(true);
	}
	
	public JPanel fileBrowser() {
		JPanel fileBrowserPanel = new JPanel();
		fileBrowserPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select Report File"));
		return null; 
	}



}

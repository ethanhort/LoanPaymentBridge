package loanPaymentBridge;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JFormattedTextField;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;

/**
 * Utility class to build and operate UI elements abstracted from main computation
 * @author Ethan Horton
 *
 */
public class UIHandler {
	
	//indices for input fields. used for array of text fields/array of values
	private static final int ID_TEXT = 0; 
	private static final int SESS_DATE_TEXT = 1; 
	private static final int SESS_DESCRIPTION_TEXT = 2;
	private static final int DOC_NUM_TEXT = 3;
	private static final int DOC_DATE_TEXT = 4;
	private static final int DOC_DESCRIPTION_TEXT = 5;
	private static final int EFFECTIVE_DATE_TEXT = 6;
	private static final int NUM_USER_INPUTS = 7; 
	
	private JFrame frame; 
	private String reportFilePath; 
	private JSpinner spinner; 
	private JTextField[] textFields = new JTextField[NUM_USER_INPUTS]; 
	private String[] userInputs = new String[NUM_USER_INPUTS]; 
	private Integer numGLCodes; 
	private GLPair[] GLs; 
	private boolean isFinished = false; 

	/**
	 * Basic constructor initializes initial frame in fullscreen mode as requested by client. 
	 */
	public UIHandler() {

		//Create Window that Program will be displayed in
		frame = new JFrame("Loan Payment Bridge"); 
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);

		//create top level panel to contain all other UI components
		JPanel panel = new JPanel(); 
		frame.add(panel); 
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		//create UI elements and add them to top-level panel
		panel.add(fileBrowser()); 
		panel.add(createTextFields()); 
		panel.add(submitPanel()); 

		frame.setVisible(true);
	}
	
	/**
	 * UI frame that pops up after first UI form is submitted. Allows for submission of GL codes and associated values
	 * this is a mess. I really should've planned it out better
	 */
	public void GLFrame(Integer numCodes) {
		GLs = new GLPair[numCodes]; 
		GLPanel[] panels = new GLPanel[numCodes];
		JPanel topPanel = new JPanel(); 
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
		JFrame GLFrame = new JFrame("GL Codes");
		GLFrame.add(topPanel);
		GLFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GLFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		//create a panel for each GL code that contains two text boxes for code and value
		for(int i = 0; i < numCodes; i++) {
			GLPanel panel = new GLPanel(); 
			panels[i] = panel; 
			topPanel.add(panel.getPanel());  
		}
		
		//create submit button that pulls values from text fields when user is finished
		JLabel finishedLabel = new JLabel(""); 
		finishedLabel.setForeground(Color.red);
		JButton finishedButton = new JButton("Submit"); 
		JPanel finishedPanel = new JPanel();
		finishedPanel.add(finishedButton);
		finishedPanel.add(finishedLabel); 
		topPanel.add(finishedPanel);
		finishedButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				boolean intFinished = true; //tracks whether all ui fields have been appropriately filled
				
				for (int i = 0; i < numCodes; i++) {
					
					//retrieve user-inputted values from text boxes
					String code = panels[i].getCodeBox().getText().trim();
					String value = panels[i].getValueBox().getText().trim();
					
					//ensure no fields were left empty
					if (code.length() == 0 || value.length() == 0) {
						finishedLabel.setText("None of these values may be empty");
						intFinished = false; 
					}
					
					//ensure no fields contain commas (would break csv output if so)
					else if (code.contains(",") || value.contains(",")) {
						finishedLabel.setText("Please remove all commas from these fields");
						intFinished = false; 
					}
					else {	
						
						//ensure GL values are numbers
						if (isNumeric(value)) {
							GLs[i] = new GLPair(code, BigDecimal.valueOf(Double.parseDouble(value))); 
						}
						else {
							int index = i + 1; 
							finishedLabel.setText("Value #" + index + " contains non-numeric character. \n Please ensure values contain only numbers.");
							intFinished = false; 
						}
					}
				}
				
				//if all fields were filled in correctly, close window and continue program
				if(intFinished) {
					isFinished = true; 
					GLFrame.dispose();
				}
			}
		});
		
		
		GLFrame.setVisible(true);
	}
	
	/**
	 * method checks if string is a double
	 * @param num string to be parsed
	 * @return true if string contains only numeric characters (and no decimals) false otherwise
	 */
	public boolean isNumeric(String num) {
		if (num == null) {
			return false; 
		}
		try {
			Double.parseDouble(num);
		}
		catch (NumberFormatException e){
			return false; 
		}
		return true; 
	}

	/**
	 * Create UI file browser to allow user to select report file for input
	 * @return JPanel containing file browser
	 */
	public JPanel fileBrowser() {

		//panel containing file browser button
		JPanel fileBrowserPanel = new JPanel();
		fileBrowserPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Select Report From NLS"));

		//initialize file chooser, button, and label to choose report from NLS and display filepath
		JFileChooser reportChooser = new JFileChooser(); 
		JButton reportButton = new JButton("Browse"); 
		JLabel reportLabel = new JLabel(""); 
		
		//set file chooser so user can only choose excel spreadsheets
		FileNameExtensionFilter filter = new FileNameExtensionFilter(".xlsx", "xlsx");
		reportChooser.setFileFilter(filter); 

		//action listener for file browser button
		reportButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				//chosen = APPROVE_OPTION only if a file is chosen in the browser
				int chosen = reportChooser.showOpenDialog(null);

				//get path of chosen file and display in UI
				if (chosen == JFileChooser.APPROVE_OPTION) {
					reportFilePath = reportChooser.getSelectedFile().getAbsolutePath();
					reportLabel.setText(reportFilePath); 
				}
			}
		});

		//add components to appropriate containers 
		fileBrowserPanel.add(reportLabel); 
		fileBrowserPanel.add(reportButton); 
		return fileBrowserPanel; 
	}

	/**
	 * create all text fields for inputting data, their individual containing panels, and the subpanel that contains each of those 
	 * individual panels 
	 * 
	 * Yes, this is mostly just copy/pasted, and yes, I could have written it more cleanly, but it's just UI stuff and I already 
	 * had it all written out from a previous project, so it was just easier.
	 * @return subpanel containing all text input panels 
	 */
	public JPanel createTextFields() {
		
		//Panel that contains all text submission components
		JPanel textPanel = new JPanel(); 
		textPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "INPUT SHOULD NOT CONTAIN COMMAS"));
		textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.PAGE_AXIS));

		//create panels for inputting text
		JPanel IDPanel = new JPanel(); 
		IDPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Session ID"));
		textPanel.add(IDPanel);
		JTextField IDText = new JTextField("");
		IDText.setColumns(10);
		textFields[ID_TEXT] = IDText; 
		IDPanel.add(IDText);

		JPanel sessDatePanel = new JPanel(); 
		sessDatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Session Date"));
		textPanel.add(sessDatePanel);
		JTextField sessDateText = new JTextField("");
		sessDateText.setColumns(10);
		textFields[SESS_DATE_TEXT] = sessDateText;
		sessDatePanel.add(sessDateText);

		JPanel sessDescriptionPanel = new JPanel(); 
		sessDescriptionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Session Description"));
		textPanel.add(sessDescriptionPanel);
		JTextField sessDescriptionText = new JTextField("");
		sessDescriptionText.setColumns(10);
		textFields[SESS_DESCRIPTION_TEXT] = sessDescriptionText;
		sessDescriptionPanel.add(sessDescriptionText);

		JPanel docNumPanel = new JPanel(); 
		docNumPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Document Number"));
		textPanel.add(docNumPanel);
		JTextField docNumText = new JTextField("");
		docNumText.setColumns(10);
		textFields[DOC_NUM_TEXT] = docNumText; 
		docNumPanel.add(docNumText);

		JPanel docDatePanel = new JPanel();
		docDatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Document Date"));
		textPanel.add(docDatePanel);
		JTextField docDateText = new JTextField("");
		docDateText.setColumns(10);
		textFields[DOC_DATE_TEXT] = docDateText; 
		docDatePanel.add(docDateText);

		JPanel docDescriptionPanel = new JPanel(); 
		docDescriptionPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Document Description"));
		textPanel.add(docDescriptionPanel);
		JTextField docDescriptionText = new JTextField("");
		docDescriptionText.setColumns(10);
		textFields[DOC_DESCRIPTION_TEXT] = docDescriptionText;
		docDescriptionPanel.add(docDescriptionText);

		JPanel effectiveDatePanel = new JPanel(); 
		effectiveDatePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Effective Date"));
		textPanel.add(effectiveDatePanel);
		JTextField effectiveDateText = new JTextField("");
		effectiveDateText.setColumns(10);
		textFields[EFFECTIVE_DATE_TEXT] = effectiveDateText;  
		effectiveDatePanel.add(effectiveDateText);
		
		//Jspinner for selecting number of GL codes that need to be inputted. Used for creating next window where gl codes are
		//inserted along with their associated values
		JPanel GLPanel = new JPanel(); 
		GLPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Number of GL Codes"));
		textPanel.add(GLPanel);
		spinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1)); 
		
		//change the text field so that it cannot be edited. this ensures that non-numeric values cannot be input
		JFormattedTextField tf = ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField();
		tf.setEditable(false);
		GLPanel.add(spinner);
		
		return textPanel; 
	}
	
	/**
	 * create panel containing submit button that pulls data from text fields when user is finished entering them
	 * @return panel containing submit button
	 */
	public JPanel submitPanel() {
		JPanel submitPanel = new JPanel(); 
		
		//create submit button and submit label to display potential error messages
		JLabel submitLabel = new JLabel(""); 
		submitLabel.setForeground(Color.red);
		JButton submitButton = new JButton("Submit"); 
		
		submitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				//ensure user has entered a valid filepath
				if(reportFilePath != null) {
					
					//tracks whether user has entered values in all fields
					boolean finished = true; 
					
					//get inputs from text field
					parseUserInputs(); 
					numGLCodes = (Integer)spinner.getValue();
					
					//ensure that all text fields contain a value and that none contain commas
					for(int i = 0; i < userInputs.length; i++) {
						if(userInputs[i].length() == 0) {
							submitLabel.setText("None of these fields may be empty");
							finished = false; 
						}
						else if(userInputs[i].contains(",")) {
							submitLabel.setText("Please remove all commas from these fields");
							finished = false; 
						}
					}
					
					if(finished) {
						
						//close window if user successfully submits form
						frame.dispose();
						
						//open UI form for inputting gl codes
						GLFrame(numGLCodes); 
					}
				}
				else {
					submitLabel.setText("Please choose an NLS report file");
				}
			}
		});
		
		//add components to panel
		submitPanel.add(submitLabel); 
		submitPanel.add(submitButton); 
		return submitPanel; 
	}
	
	/**
	 * iterate through array of text fields and pull text from each into other array that can be accessed by 
	 * non-UI system components
	 */
	public void parseUserInputs() {
		for (int i = 0; i < textFields.length; i++) {
			userInputs[i] = textFields[i].getText().trim(); 
		}
	}
	
	/**
	 * getter for user inputs 
	 * @return array of user inputs
	 */
	public String[] getUserInputs() {
		return userInputs; 
	}
	
	/**
	 * getter for number of gl codes that need to be entered
	 * @return number of gl codes from UI spinner
	 */
	public Integer getNumGLCodes() {
		return numGLCodes; 
	}
	
	/**
	 * getter for file path of NLS report
	 * @return string representation of filepath 
	 */
	public String getFilePath() {
		return reportFilePath; 
	}
	
	/**
	 * getter for GL code/value pairs
	 * 
	 * @return array containing GL codes and values
	 */
	public GLPair[] getGLCodes() {
		return GLs; 
	}
	
	/**
	 * indicates whether the user has finished inputting information into all UI elements
	 * @return true if all fields were successfully completed, false otherwise
	 */
	public boolean isFinished() {
		return isFinished;
	}
}

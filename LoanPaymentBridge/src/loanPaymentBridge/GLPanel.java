package loanPaymentBridge;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * Class represents one GL panel composed of two text boxes. one for GL code and other for GL value 
 * @author Ethan Horton
 *
 */
public class GLPanel {
	
	private JPanel panel;  
	private JTextField code, value; 

	public GLPanel() {
		panel = new JPanel(); 
		JPanel codePanel = new JPanel(); 
		JPanel valuePanel = new JPanel(); 
		panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "GL Code"));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		codePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Code"));
		valuePanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Value"));
		code = new JTextField(""); 
		code.setColumns(10);
		value = new JTextField(""); 
		value.setColumns(10);
		
		codePanel.add(code);
		valuePanel.add(value); 
		panel.add(codePanel);
		panel.add(valuePanel); 
		
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	public JTextField getCodeBox() {
		return code; 
	}
	
	public JTextField getValueBox() {
		return value; 
	}
}

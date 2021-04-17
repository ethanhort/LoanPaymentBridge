package loanPaymentBridge;
import java.util.ArrayList;
import java.math.BigDecimal;

public class OutputTable {
	private ArrayList<OutputRow> table; 
	
	/**
	 * 
	 * @param report
	 * @param GLs
	 */
	public OutputTable(Report report, GLPair[] GLs) {
		table = new ArrayList<OutputRow>(); 
		
		int glIndex = 0; 
		ReportRow currRow; 
		BigDecimal rowTotal; 
		for (int i = 0; i < report.size(); i++) { 
			currRow = report.getRow(i); 
			
			//add row to output table with principal value
			addRow(createId(currRow.getLoanGroup(), true), new BigDecimal(0), currRow.getPrincipal()); 
			//add row to output table with interest value
			addRow(createId(currRow.getLoanGroup(), false), new BigDecimal(0), currRow.getInterest()); 
			
			//TODO: add output row for debit values from gl codes
			rowTotal = currRow.getPrincipal().add(currRow.getInterest()); 
			if (GLs[glIndex].getValue().compareTo(rowTotal) > 0) {
				addRow(createId(currRow.getLoanGroup(), GLs[glIndex].getGLNum()), rowTotal, new BigDecimal(0)); 
				GLs[glIndex].subtract(rowTotal); 
			} else if (GLs[glIndex].getValue().compareTo(rowTotal) == 0) {
				addRow(createId(currRow.getLoanGroup(), GLs[glIndex].getGLNum()), rowTotal, new BigDecimal(0)); 
				GLs[glIndex].subtract(rowTotal);
				if (glIndex < GLs.length - 1) {
					glIndex++; 
				}
			} else {
				//make sure to catch error where there is more money in reports than in GLs
				//make sure to catch error where there is more money in gls than in reports
			}
		}
	}
	
	public String createId(String loanGroup, boolean isPrincipal) {
		String id = loanGroup.substring(0, 1) + "0-" + loanGroup + "-"; 
		if (isPrincipal) {
			id = id + "1410"; 
		} else {
			id = id + "4020"; 
		}
		return id; 
	}
	
	public String createId(String loanGroup, String glCode) {
		return loanGroup.substring(0, 1) + "0-" + loanGroup + "-" + glCode; 
	}
	
	/**
	 * add an output row to the table
	 * @param id numerical identifier for output entry
	 * @param debit debit value in dollars
	 * @param credit credit value in dollars
	 */
	public void addRow(String id, BigDecimal debit, BigDecimal credit) {
		table.add(new OutputRow(id, debit, credit)); 
	}
	
	public OutputRow getRow(int index) {
		return table.get(index); 
	}
	
	@Override
	/**
	 * convert output table to a string so that it can be printed
	 * in an understandable fashion
	 */
	public String toString() {
		String string = ""; 
		for (int i = 0; i < table.size(); i++) {
			string = string + table.get(i) + "\n"; 
		}
		
		return string; 
	}
}

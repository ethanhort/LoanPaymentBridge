package loanPaymentBridge;
import java.util.ArrayList;
import java.math.BigDecimal;

public class OutputTable {
	private ArrayList<OutputRow> table; 
	
	private static final int IS_PRINCIPLE = 0; 
	private static final int IS_INTEREST = 1; 
	private static final int IS_ESCROW = 2; 
	private static final int IS_SERVICING = 3; 
	
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
			if (currRow.getPrincipal().compareTo(new BigDecimal(0)) != 0) {
				addRow(createId(currRow.getLoanGroup(), IS_PRINCIPLE), new BigDecimal(0), currRow.getPrincipal()); 
			}
			//add row to output table with interest value
			if (currRow.getInterest().compareTo(new BigDecimal(0)) != 0) {
				addRow(createId(currRow.getLoanGroup(), IS_INTEREST), new BigDecimal(0), currRow.getInterest()); 
			}
			//add row to output table with escrow value
			if (currRow.getEscrow().compareTo(new BigDecimal(0)) != 0) {
				addRow(createId(currRow.getLoanGroup(), IS_ESCROW), new BigDecimal(0), currRow.getEscrow()); 
			}
			//add row to output table with servicing value
			if (currRow.getServicing().compareTo(new BigDecimal(0)) != 0) {
				addRow(createId(currRow.getLoanGroup(), IS_SERVICING), new BigDecimal(0), currRow.getServicing()); 
			}
			
			//add principal, interest, escrow, and servicing amt from one row of report to debit the grants
			rowTotal = currRow.getPrincipal().add(currRow.getInterest()).add(currRow.getEscrow()).add(currRow.getServicing()); 
			
			//case where current grant value is greater than or equal to P+I of current row of report
			if (GLs[glIndex].getValue().compareTo(rowTotal) >= 0) {
				addRow(createId(currRow.getLoanGroup(), GLs[glIndex].getGLNum()), rowTotal, new BigDecimal(0)); 
				GLs[glIndex].subtract(rowTotal); 
			} else {
				//case where grant value is less than P+I of current row
				
				
				//loop in case current row takes multiple grants to pay
				while (rowTotal.compareTo(new BigDecimal(0)) != 0) {
					
					//case where grant value is greater than or equal to row value
					if (GLs[glIndex].getValue().compareTo(rowTotal) >= 0) {
						GLs[glIndex].subtract(rowTotal);
						addRow(createId(currRow.getLoanGroup(), GLs[glIndex].getGLNum()), rowTotal, new BigDecimal(0)); 
						rowTotal = new BigDecimal(0); 
					} else {
						//case where grant value is less than row value
						addRow(createId(currRow.getLoanGroup(), GLs[glIndex].getGLNum()), GLs[glIndex].getValue(), new BigDecimal(0));
						rowTotal = rowTotal.subtract(GLs[glIndex].getValue()); 
						GLs[glIndex].subtract(GLs[glIndex].getValue());
						
						//update glIndex if we have another available grant
						if (glIndex < GLs.length - 1) {
							glIndex++; 
						} else {
							//we know that we have more money in report based on conditionals, so if there 
							//is no available grant, crash program and notify user that something has gone wrong
							UIHandler.handleError("Not enough money in cash accounts to cover ledger total");
						}
					}
				}
			}
			//if the value of the current grant is 0 and there is another available grant, update glIndex
			if (GLs[glIndex].getValue().compareTo(new BigDecimal(0)) > 0) {
				//do nothing because there is still money in the current grant
			} else if (GLs[glIndex].getValue().compareTo(new BigDecimal(0)) < 0){
				//case where current grant has negative money??? this would be very bad if it ever happened
				UIHandler.handleError("This should NEVER happen. Ignore the next message and email me: ehorton14892@gmail.com");
			} else {
				//case where current grant is at 0
				if (i == report.size() - 1) {
					//do nothing because we are done with the report
				} else {
					if (glIndex < GLs.length - 1) {
						glIndex++; 
					} else {
						//case where we are out of grant money but still have money in report
						UIHandler.handleError("Not enough money in cash accounts to cover ledger total"); 	
					}
				}
			}
		}
		
		for(int i = 0; i < GLs.length; i++) {
			if (GLs[i].getValue().compareTo(new BigDecimal(0)) != 0) {
				UIHandler.handleError("Too much money in cash accounts to cover ledger total"); 	
			}
		}
	}
	
	public String createId(String loanGroup, int type) {
		String id = loanGroup.substring(0, 1) + "0-" + loanGroup + "-"; 
		if (type == IS_PRINCIPLE) {
			id = id + "1410"; 
		} else if (type == IS_INTEREST) {
			id = id + "4020"; 
		} else if (type == IS_ESCROW) {
			id = id + "2098"; 
		} else {
			id = id + "2202"; 
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
	
	public int size() {
		return table.size(); 
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

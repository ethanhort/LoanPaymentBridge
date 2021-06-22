package loanPaymentBridge;
import java.io.FileNotFoundException;
import java.util.ArrayList; 
import java.io.IOException;
import java.io.FileInputStream;
import java.math.BigDecimal; 
import org.apache.poi.ss.usermodel.Sheet;  
import org.apache.poi.ss.usermodel.Workbook;  
import org.apache.poi.xssf.usermodel.XSSFWorkbook;  

/**
 * Representation of the loan payment report that this program uses to generate the appropriate file for MIP
 * This class extracts the necessary information from the report file so that it can be more easily worked with
 * We only care about the loan group, principal, and interest
 * @author ethan horton
 *
 */
public class Report {
	
	//constant for first row of useful data in file
	private static final int START_INDEX = 9; 
	
	//instance variables that may need to be accessed later
	private ArrayList<ReportRow> report; 

	public Report(String filePath, int principalIndex, int interestIndex, int groupIndex, int escrowIndex, int servicingIndex) {
		
		int index = START_INDEX; 
		report = new ArrayList<ReportRow>(); 
		
		try {
			Workbook wb = null; //apache poi workbook initialized to null
			
			//reading data from a file in the form of bytes
			FileInputStream fis = new FileInputStream(filePath); 
			
			//constructs an XSSFWorkbook object by buffering the whole stream into the memory  
			wb = new XSSFWorkbook(fis);  
			Sheet sheet = wb.getSheetAt(0);
			
			while(sheet.getRow(index) != null && sheet.getRow(index).getCell(groupIndex) != null) {
				index++; 
			} 
			
			//initialize the array of rows to the appropriate size 
			populateReport(index, principalIndex, interestIndex, groupIndex, escrowIndex, servicingIndex, sheet); 
		}
		catch(FileNotFoundException e)  {  
			System.out.println("Cannot find file containing ledger report at specified location");;  
		}  
		catch(IOException er)  {  
			System.out.println("Something bad happened. Please try again.");;  
		}  
	}
	
	/**
	 * take data from excel report and populate array of report rows with appropriate values
	 * @param lastIndex index of final data row that we care about
	 * @param principalIndex index of column containing principal values
	 * @param interestIndex index of column containing interest values
	 * @param groupIndex index of column containing loan group info
	 * @param sheet representation of excel spreadsheet
	 */
	public void populateReport(int lastIndex, int principalIndex, int interestIndex, int groupIndex, int escrowIndex, int servicingIndex, Sheet sheet) {
		
		BigDecimal principal = new BigDecimal(0);  
		BigDecimal interest = new BigDecimal(0);  
		BigDecimal escrow = new BigDecimal(0); 
		BigDecimal servicing = new BigDecimal(0); 
		String groupNum = sheet.getRow(START_INDEX).getCell(groupIndex).getStringCellValue().substring(0, 3); 
		for (int i = START_INDEX; i < lastIndex; i++) {
			if (groupNum.equals(sheet.getRow(i).getCell(groupIndex).getStringCellValue().substring(0, 3))) {
				principal = principal.add(BigDecimal.valueOf(sheet.getRow(i).getCell(principalIndex).getNumericCellValue()).multiply(new BigDecimal(-1))); 
				interest = interest.add(BigDecimal.valueOf(sheet.getRow(i).getCell(interestIndex).getNumericCellValue()).multiply(new BigDecimal(-1)));
				escrow = escrow.add(BigDecimal.valueOf(sheet.getRow(i).getCell(escrowIndex).getNumericCellValue()).multiply(new BigDecimal(-1))); 
				servicing = servicing.add(BigDecimal.valueOf(sheet.getRow(i).getCell(servicingIndex).getNumericCellValue()).multiply(new BigDecimal(-1))); 
			} else {
				addRow(groupNum, principal, interest, escrow, servicing);
				groupNum = sheet.getRow(i).getCell(groupIndex).getStringCellValue().substring(0, 3);
				principal = BigDecimal.valueOf(sheet.getRow(i).getCell(principalIndex).getNumericCellValue()).multiply(new BigDecimal(-1)); 
				interest = BigDecimal.valueOf(sheet.getRow(i).getCell(interestIndex).getNumericCellValue()).multiply(new BigDecimal(-1));
				escrow = BigDecimal.valueOf(sheet.getRow(i).getCell(escrowIndex).getNumericCellValue()).multiply(new BigDecimal(-1)); 
				servicing = BigDecimal.valueOf(sheet.getRow(i).getCell(servicingIndex).getNumericCellValue()).multiply(new BigDecimal(-1)); 
			}
		}
		addRow(groupNum, principal, interest, escrow, servicing);
	}
	
	/**
	 * add another report row to the end of the table
	 * @param loanGroup
	 * @param principal
	 * @param interest
	 */
	public void addRow(String loanGroup, BigDecimal principal, BigDecimal interest, BigDecimal escrow, BigDecimal servicing) {
		report.add(new ReportRow(loanGroup, principal, interest, escrow, servicing));
	}
	
	/**
	 * return specified row of report
	 * @param i index of row to return
	 * @return
	 */
	public ReportRow getRow(int i) {
		return report.get(i); 
	}
	
	/**
	 * returns the length of the array storing report rows
	 * @return
	 */
	public int size() {
		return report.size(); 
	}
	
	/**
	 * pretty clear what this does i think
	 */
	@Override
	public String toString() {
		String string = ""; 
		for (int i = 0; i < report.size(); i++) {
			string = string + report.get(i).toString() + "\n"; 
		}
		return string; 
	}
}

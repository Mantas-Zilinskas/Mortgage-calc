package application;

public class Row {

	public Integer cMonth;
	public Double cLoanRemainder;
	public Double cInterestPayment;
	public Double cLoanPayment;
	public Double cTotalPayment;
	public Double payed;
	
	public Row (Integer month, Double loanRemainder, 
			Double interestPayment, Double loanPayment, Double Payed) {
		this.cMonth = month;
		this.cLoanRemainder = loanRemainder;
		this.cInterestPayment = interestPayment;
		this.cLoanPayment = loanPayment;
		this.payed = Payed;
		
		this.cTotalPayment = Math.rint((loanPayment + interestPayment)*100)/100;
	}
	
	public Integer getCMonth() {
		return cMonth;
	}
	
	public Double getCLoanRemainder() {
		return cLoanRemainder;
	}
	
	public Double getCInterestPayment() {
		return cInterestPayment;
	}
	
	public Double getCLoanPayment() {
		return cLoanPayment;
	}
	
	public Double getCTotalPayment() {
		return cTotalPayment;
	}
	
	public Double getPayed() {
		return payed;
	}
	
	public void setCMonth(Integer cMonth) {
		this.cMonth = cMonth;
	}
}

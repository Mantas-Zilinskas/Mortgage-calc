package application;


import java.util.ArrayList;

public class Repayment {
	
	private final static Repayment repayment =  new Repayment(); 
	
	private Double interest;
	private Double monthlyPayment;
	public int length;
	
	private ArrayList<Double> loanRemainder = new ArrayList<Double>();
	private ArrayList<Double> loanPayment = new ArrayList<Double>();
	private ArrayList<Double> interestPayment = new ArrayList<Double>();
	private ArrayList<Double> payed = new ArrayList<Double>();
	
	private Repayment(){}
	
	public static Repayment getInstance() {
		return repayment;
	}
	
	public Double getMonthlyPayment() {
		return monthlyPayment;
	}
	
	public ArrayList<Double> getLoanRemainder(){
		return loanRemainder;
	}
	
	public ArrayList<Double> getLoanPayment(){
		return loanPayment;
	}
	
	public ArrayList<Double> getInterestPayment(){
		return interestPayment;
	}
	
	public ArrayList<Double> getPayed(){
		return payed;
	}
	
	public void calcAnnuity(Double loan, Double interest, int length, int breakStart, int breakLength, Double breakInterest) {
		this.length = length;
		this.interest = interest/1200;
		
		
		monthlyPayment = loan *(this.interest * Math.pow((1 + this.interest), length-breakLength))/
									(Math.pow((1 + this.interest), length-breakLength)-1);

		
		loanRemainder.add(loan);
		for(int i = 0; i < breakStart; ++i) {
			interestPayment.add(loanRemainder.get(i)*this.interest);
			loanPayment.add(monthlyPayment - interestPayment.get(i));
			loanRemainder.add(loanRemainder.get(i) - loanPayment.get(i));
			payed.add(interestPayment.get(i) + loanPayment.get(i) + ((i > 0)?payed.get(i-1):0));
			
		}
		for(int i = breakStart; i < breakStart + breakLength; ++i) {
			interestPayment.add(loanRemainder.get(i)*breakInterest/1200);
			loanPayment.add((double) 0);
			loanRemainder.add(loanRemainder.get(i));
			payed.add(interestPayment.get(i) + loanPayment.get(i) + ((i > 0)?payed.get(i-1):0));
		}
		for(int i = breakStart + breakLength; i < loan; ++i) {
			interestPayment.add(loanRemainder.get(i)*this.interest);
			loanPayment.add(monthlyPayment - interestPayment.get(i));
			loanRemainder.add(loanRemainder.get(i) - loanPayment.get(i));
			payed.add(interestPayment.get(i) + loanPayment.get(i) + ((i > 0)?payed.get(i-1):0));
		}
		
		loanRemainder.remove(length);
	}
	
	public void calcLinear(Double loan, Double interest, int length, int breakStart, int breakLength, Double breakInterest) {
		this.length = length;
		this.interest = (interest/100)/12;
		
		loanRemainder.add(loan);
		for(int i = 0; i < breakStart; ++i) {
			interestPayment.add(loanRemainder.get(i)*this.interest);
			loanPayment.add(loan/(length - breakLength));
			loanRemainder.add(loanRemainder.get(i)-loanPayment.get(i));
			payed.add(interestPayment.get(i) + loanPayment.get(i) + ((i > 0)?payed.get(i-1):0));
		}
		for(int i = breakStart; i < breakStart + breakLength; ++i) {
			interestPayment.add(loanRemainder.get(i)*breakInterest/1200);
			loanRemainder.add(loanRemainder.get(i));
			loanPayment.add((double) 0);
			payed.add(interestPayment.get(i) + loanPayment.get(i) + ((i > 0)?payed.get(i-1):0));
		}
		for(int i = breakStart + breakLength; i < length; ++i) {
			interestPayment.add(loanRemainder.get(i)*this.interest);
			loanPayment.add(loan/(length - breakLength));
			loanRemainder.add(loanRemainder.get(i)-loanPayment.get(i));
			payed.add(interestPayment.get(i) + loanPayment.get(i) + ((i > 0)?payed.get(i-1):0));
		}
		
		loanRemainder.remove(length);
	}
	
}



package application;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Controller implements Initializable{
	
	
	@FXML
	private TextField amount, breakInterest, breakLength, breakStart, termMonths;
	@FXML
	private TextField interest, termYears;
	@FXML
	private ChoiceBox<String> choiceBox;
	
	public Repayment repayment = Repayment.getInstance();
	
	private String[] repaymentType = {"Annuity","Linear"};
	private String repaymentChoice = "Annuity";
	private int loanTerm = 0;
	private int flag = 0;
	private Double loan;
	private Double loanRate;
	private int loanBreak;
	private int loanBreakLength;
	private Double loanBreakRate;
	
	
	ArrayList<Double> loanRemainder = new ArrayList<Double>();
	ArrayList<Double> loanPayment = new ArrayList<Double>();
	ArrayList<Double> interestPayment = new ArrayList<Double>();
	
	
	
	public void submit(ActionEvent event) throws IOException {
		try {
			loanTerm += 12*Integer.parseInt(termYears.getText());
		} catch(NumberFormatException e) {
				termYears.setPromptText("Invalid input");
				termYears.setStyle("-fx-prompt-text-fill: red");
				termYears.setText(null);
				flag = 1;
		}
		
		try {
			loanTerm += Integer.parseInt(termMonths.getText());
		} catch(NumberFormatException e) {
				termMonths.setPromptText("Invalid input");
				termMonths.setStyle("-fx-prompt-text-fill: red");
				termMonths.setText(null);
				flag = 1;
		}
		
		
		
		try {
			loan = Double.parseDouble(amount.getText());
		} catch(NumberFormatException e) {
				amount.setPromptText("Invalid input");
				amount.setStyle("-fx-prompt-text-fill: red");
				amount.setText(null);
				flag = 1;
		}
		
		try {
			 loanRate = Double.parseDouble(interest.getText());
		} catch(NumberFormatException e) {
				interest.setPromptText("Invalid input");
				interest.setStyle("-fx-prompt-text-fill: red");
				interest.setText(null);
				flag = 1;
		}
		
		try {
			 loanBreak = Integer.parseInt(breakStart.getText());
		} catch(NumberFormatException e) {
				breakStart.setPromptText("Invalid input");
				breakStart.setStyle("-fx-prompt-text-fill: red");
				breakStart.setText(null);
				flag = 1;
		}
		
		try {
			 loanBreakLength = Integer.parseInt(breakLength.getText());
		} catch(NumberFormatException e) {
			breakLength.setPromptText("Invalid input");
			breakLength.setStyle("-fx-prompt-text-fill: red");
			breakLength.setText(null);
			flag = 1;
		}
		
		try {
			 loanBreakRate = Double.parseDouble(breakInterest.getText());
		} catch(NumberFormatException e) {
				breakInterest.setPromptText("Invalid input");
				breakInterest.setStyle("-fx-prompt-text-fill: red");
				breakInterest.setText(null);
				flag = 1;
		}
		
		if(Integer.parseInt(termYears.getText()) < 0 ||
		   Integer.parseInt(termYears.getText()) < 0 ||
		   loanTerm <= 0							 ||
		   loan <= 0								 ||
		   loanRate <= 0							 ||
		   loanBreak <	0							 ||
		   loanBreakLength < 0						 ||
		   loanBreakRate < 0						 ){
	//		flag = 1;
			loanTerm = 0;
		}
		
		
		if(repaymentChoice.equals("Linear") && flag == 0) {
			repayment.calcLinear(loan, loanRate, loanTerm, loanBreak, loanBreakLength, loanBreakRate);
			
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("Second.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();	
			Scene scene = new Scene(root,475,400);
			stage.setScene(scene);
			stage.show();
		}
		
		if(repaymentChoice.equals("Annuity") && flag == 0) {
			repayment.calcAnnuity(loan, loanRate, loanTerm, loanBreak, loanBreakLength, loanBreakRate);
			
			
			Parent root = (Parent)FXMLLoader.load(getClass().getResource("Second.fxml"));
			Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();	
			Scene scene = new Scene(root,475,400);
			stage.setScene(scene);
			stage.show();
		}
		flag = 0;
		loanTerm = 0;
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		choiceBox.getItems().addAll(repaymentType);
		choiceBox.setValue("Annuity");
		choiceBox.setOnAction(this::getPaymentType);
	}
	
	public void getPaymentType(ActionEvent event) {
		repaymentChoice = choiceBox.getValue();
	}
	
}

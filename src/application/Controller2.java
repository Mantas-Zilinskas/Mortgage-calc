package application;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory; 
import java.io.*;
import org.apache.poi.hssf.usermodel.HSSFSheet;  
import org.apache.poi.hssf.usermodel.HSSFWorkbook;  
import org.apache.poi.hssf.usermodel.HSSFRow;  

public class Controller2 implements Initializable{

	@FXML
	private Button button;
	@FXML
	private LineChart<String, Double> lineChart;
	@FXML
	private ChoiceBox<String> choiceBox;
	@FXML
	private RadioButton radio1, radio2;
	@FXML
	private TableView<Row> table;
	@FXML
	private TableColumn<Row, Integer> cMonth;
	@FXML
	private TableColumn<Row, Double> cInterestPayment, cTotalPayment, cLoanRemainder, cLoanPayment, paid;
	
	private Repayment repayment = Repayment.getInstance();
	private ArrayList<Double> loanRemainder = repayment.getLoanRemainder();
	private ArrayList<Double> loanPayment = repayment.getLoanPayment();
	private ArrayList<Double> interestPayment = repayment.getInterestPayment();
	private ArrayList<Double> payeda = repayment.getPayed();
	
	public void getChart(ActionEvent e) {
		if(radio1.isSelected()) {
			table.setVisible(false);
			lineChart.setVisible(true);
		}else if(radio2.isSelected()) {
			lineChart.setVisible(false);
			table.setVisible(true);
		}
	}
	
	public void export(ActionEvent e){
		
		try   
		{  
		String filename = "report.xlsx";  
		HSSFWorkbook workbook = new HSSFWorkbook();  
		HSSFSheet sheet = workbook.createSheet("report"); 
		sheet.setColumnWidth(0, 8 * 256);
		sheet.setColumnWidth(1, 16 * 256);
		sheet.setColumnWidth(2, 16 * 256);
		sheet.setColumnWidth(3, 16 * 256);
		sheet.setColumnWidth(4, 16 * 256);
		
		HSSFRow rowhead = sheet.createRow((short)0);   
		rowhead.createCell(0).setCellValue("Month");  
		rowhead.createCell(1).setCellValue("Loan remainder");  
		rowhead.createCell(2).setCellValue("Interest Payment");  
		rowhead.createCell(3).setCellValue("Loan payment");  
		rowhead.createCell(4).setCellValue("Total payment");  

		for(int i = 0 ; i < repayment.length; ++i) {
			HSSFRow row = sheet.createRow((short)(i+1));
			row.createCell(0).setCellValue(Integer.toString(i+1));  
			row.createCell(1).setCellValue(Double.toString(
					Math.rint(loanRemainder.get(i) * 100)/100));  
			row.createCell(2).setCellValue(Double.toString(
					Math.rint(interestPayment.get(i) * 100)/100));  
			row.createCell(3).setCellValue(Double.toString(
					Math.rint(loanPayment.get(i) * 100)/ 100));  
			row.createCell(4).setCellValue(Double.toString(
					Math.rint((interestPayment.get(i) + loanPayment.get(i))*100)/100));
		}
		
		FileOutputStream fileOut = new FileOutputStream(filename);  
		workbook.write(fileOut);   
		fileOut.close();  
		workbook.close();     
		}   
		catch (Exception ex)   
		{  
		ex.printStackTrace();  
		}  
	} 
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		cMonth.setCellValueFactory(new PropertyValueFactory<Row,Integer>("cMonth"));
		cInterestPayment.setCellValueFactory(new PropertyValueFactory<Row,Double>("cInterestPayment"));
		cTotalPayment.setCellValueFactory(new PropertyValueFactory<Row,Double>("cTotalPayment"));
		cLoanRemainder.setCellValueFactory(new PropertyValueFactory<Row,Double>("cLoanRemainder"));
		cLoanPayment.setCellValueFactory(new PropertyValueFactory<Row,Double>("cLoanPayment"));
		paid.setCellValueFactory(new PropertyValueFactory<Row,Double>("payed"));
		ObservableList<Row> rows = table.getItems();
		
		String[] length = new String[(repayment.length / 12) + 
		                             ((repayment.length % 12 == 0)?0:1)];
		
		for(int i = repayment.length, j = 0; i > 0; ++j) {
			if(i >= 12) {
				i -= 12;
				length[j] = Integer.toString(j*12+1) + " - " + 
							Integer.toString((j+1)*12);
			}else {
				length[j] = Integer.toString(j*12+1) + " - " + 
							Integer.toString(j*12+i);
				i = 0;
			}
		}
		
		choiceBox.getItems().addAll(length);
		choiceBox.setValue(length[0]);
		choiceBox.setOnAction(this::graphPeriod);
		
		String temp = choiceBox.getValue();
		String[] period = temp.split(" ", 3);
		
		XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
		series.setName("Payments");
		

		for(int i = Integer.parseInt(period[0]); 
				i <= Integer.parseInt(period[2]); 
				++i) {
			series.getData().add(new XYChart.Data<String, Double>(Integer.toString(i), 
							loanPayment.get(i-1)+interestPayment.get(i-1)));
					
			Row row = new Row(
					i, 
					Math.rint(loanRemainder.get(i-1) * 100)/100, 
					Math.rint(interestPayment.get(i-1) * 100)/100, 
					Math.rint(loanPayment.get(i-1) * 100)/ 100,
					Math.rint(payeda.get(i-1) * 100)/ 100);
			rows.add(row);
		}
		
		table.setItems(rows);
		lineChart.getData().add(series);
	}
	

	private void graphPeriod(Event event1) {
		ObservableList<Row> rows = table.getItems();
		
		String temp = choiceBox.getValue();
		String[] period = temp.split(" ", 3);
		
		XYChart.Series<String, Double> series = new XYChart.Series<String, Double>();
		series.setName("Payments");
		
		rows.clear();
		for(int i = Integer.parseInt(period[0]); 
				i <= Integer.parseInt(period[2]); 
				++i) {
			series.getData().add(new XYChart.Data<String, Double>(Integer.toString(i), 
							loanPayment.get(i-1)+interestPayment.get(i-1)));
			
			Row row = new Row(
					i, 
					Math.rint(loanRemainder.get(i-1) * 100)/100, 
					Math.rint(interestPayment.get(i-1) * 100)/100, 
					Math.rint(loanPayment.get(i-1) * 100)/ 100,
					Math.rint(payeda.get(i-1) * 100)/ 100);
			rows.add(row);
		}
		
		lineChart.getData().clear();
		lineChart.getData().add(series);

		table.setItems(rows);
		
	}

}

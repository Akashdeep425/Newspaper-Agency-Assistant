package billGenerator;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class billGenControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private ListView<String> lstPapers;

    @FXML
    private ListView<String> lstPrice;

    @FXML
    private TextField txtTotal;

    @FXML
    void doBill(ActionEvent event) {
    	
    	try {
			pst=obj.prepareStatement("select * from customers where mobile=?");
			pst.setString(1, comboMob.getEditor().getText());
			ResultSet table=pst.executeQuery();
			java.sql.Date pre;
			LocalDate preDate=LocalDate.now();
			if(table.next())
			{
				pre=table.getDate("doj");
				preDate=pre.toLocalDate();
			}
			LocalDate current=LocalDate.now();
			//Period diff=Period.between(preDate, current);
			//System.out.println(diff.getDays());
			no=(int) ChronoUnit.DAYS.between(preDate, current);
			double sum=0;
			for (String string : priceList) {
				double amount=Double.parseDouble(string);
				sum=sum+amount*no;
			}
			txtTotal.setText(String.valueOf(sum));
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doFetch(ActionEvent event) {
    	paperlist=new ArrayList<String>();
    	priceList=new ArrayList<String>();
    	lstPapers.getItems().clear();
    	lstPrice.getItems().clear();
    	try {
			pst=obj.prepareStatement("select * from customers where mobile=?");
			pst.setString(1, comboMob.getEditor().getText());
			ResultSet table=pst.executeQuery();
			String papers="";
			String prices="";
			if(table.next())
			{
				papers=table.getString("papers");
			}
			String[] arr=papers.split(",");
			for (String string : arr) {
				paperlist.add(string);
			}
			lstPapers.getItems().addAll(paperlist);
			for (int i=0;i<paperlist.size();i++) {
				pst=obj.prepareStatement("select * from papers where paper=?");
				String string=paperlist.get(i);
				pst.setString(1, string);
				ResultSet t=pst.executeQuery();
				if(t.next())
				{
					prices=table.getString("price");
				}
			}
			String ary[]=prices.split(",");
			for (String string : ary) {
				priceList.add(string);
			}
			lstPrice.getItems().addAll(priceList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doSave(ActionEvent event) {

    	try {
			pst=obj.prepareStatement("insert into billing(mobile,days,dob,amount) values(?,?,?,?)");
			pst.setString(1, comboMob.getEditor().getText());
			pst.setInt(2, no);
			LocalDate current=LocalDate.now();
			java.sql.Date sqlDate=java.sql.Date.valueOf(current);
			pst.setDate(3,sqlDate);
			pst.setFloat(4, Float.parseFloat(txtTotal.getText()));
			pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText("Record Saved");
	    	alert.show();
	    	pst=obj.prepareStatement("update customers set doj=? where mobile=?");
	    	pst.setDate(1, sqlDate);
	    	pst.setString(2, comboMob.getEditor().getText());
	    	pst.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    	
    }

    int no=0;
   
    ArrayList<String> paperlist;
    ArrayList<String> priceList;
    Connection obj;
    PreparedStatement pst;
    @FXML
    void initialize() {
       obj=dataConnect.Connect.getConnection();
       ArrayList<String> moblist=new ArrayList<String>();
       try {
		pst=obj.prepareStatement("select * from customers");
		ResultSet table=pst.executeQuery();
		while (table.next()) {
			moblist.add(table.getString("mobile"));
			
		}
		comboMob.getItems().addAll(moblist);
		comboMob.getSelectionModel().select(0);
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    }
}

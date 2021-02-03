package billCollector;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class billCollectControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private ListView<LocalDate> lstDate;

    @FXML
    private ListView<String> lstAmount;
    
    @FXML
    private TextField txtTotal;

    ArrayList<String> amountlist;
    ArrayList<LocalDate> datelist;
    float sum;
    @FXML
    void doFetch(ActionEvent event) {
    	txtTotal.setText(null);
    	sum=0;
    	amountlist=new ArrayList<String>();
    	datelist=new ArrayList<LocalDate>();
    	lstAmount.getItems().clear();
    	lstDate.getItems().clear();
    	try {
			pst=obj.prepareStatement("select * from billing where mobile=? and status=?");
			pst.setString(1, comboMob.getEditor().getText());
			pst.setInt(2, 0);
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				java.sql.Date date=table.getDate("dob");
				datelist.add(date.toLocalDate());
				float price=table.getFloat("amount");
				sum=sum+price;
				amountlist.add(String.valueOf(price));
				
			}
			lstDate.getItems().addAll(datelist);
			lstAmount.getItems().addAll(amountlist);
			txtTotal.setText(String.valueOf(sum));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

    @FXML
    void doPay(ActionEvent event) {

    	try {
			pst=obj.prepareStatement("update billing set status=? where mobile=?");
			pst.setInt(1, 1);
			pst.setString(2, comboMob.getEditor().getText());
			pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText("Bill Paid");
	    	alert.show();
	    	txtTotal.setText(null);
	    	lstAmount.getItems().clear();
	    	lstDate.getItems().clear();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    }

    Connection obj;
    PreparedStatement pst;
    @FXML
    void initialize() {
    	obj=dataConnect.Connect.getConnection();
    	ArrayList<String> mobList=new ArrayList<String>();
    	try {
			pst=obj.prepareStatement("select * from customers");
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
				mobList.add(table.getString("mobile"));
			}
			comboMob.getItems().addAll(mobList);
			comboMob.getSelectionModel().select(0);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}

package billTable;

import java.io.File;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import dataConnect.Connect;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import billTable.UserBean;

public class billControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private RadioButton radAllPaid;

    @FXML
    private ToggleGroup allRec;

    @FXML
    private RadioButton radAllUnpaid;

    @FXML
    private RadioButton radCPaid;

    @FXML
    private ToggleGroup custRec;

    @FXML
    private RadioButton radCUnpaid;

    @FXML
    private TextField txtMob;

    @FXML
    private TableView<UserBean> table;

    @FXML
    void fetchAll(ActionEvent event) {
   
    	TableColumn<UserBean, String> mobile=new TableColumn<UserBean, String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));//bean wali field ka name
    	mobile.setMinWidth(150);
    	
    	TableColumn<UserBean, String> days=new TableColumn<UserBean, String>("No Of Days");
    	days.setCellValueFactory(new PropertyValueFactory<>("days"));//bean wali field ka name
    	days.setMinWidth(100);
    	
    	TableColumn<UserBean, String> amount=new TableColumn<UserBean, String>("Amount");
    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean wali field ka name
    	amount.setMinWidth(150);
    	
    	TableColumn<UserBean, String> dob=new TableColumn<UserBean, String>("Date Of Billing");
    	dob.setCellValueFactory(new PropertyValueFactory<>("dob"));//bean wali field ka name
    	dob.setMinWidth(150);
    	
    	TableColumn<UserBean, String> status=new TableColumn<UserBean, String>("Status");
    	status.setCellValueFactory(new PropertyValueFactory<>("status"));//bean wali field ka name
    	status.setMinWidth(105);
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(mobile,days,amount,dob,status);

    	table.setItems(getAllRecords());
    }

    ObservableList<UserBean> getAllRecords()
    {
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	int status=0;
    	try
    	{
    	pst=obj.prepareStatement("select * from billing where status=?");
    	if(radAllPaid.isSelected())
    	{
    		status=1;
    	}
    	else {
			status=0;
		}
    	pst.setInt(1, status);
    	ResultSet rs=pst.executeQuery();
    	while(rs.next())
    		{
    			
    			String mobile=rs.getString("mobile");
    			String days=rs.getString("days");
    			String dob=rs.getString("dob");
    			String amount=rs.getString("amount");
    			UserBean ref=new UserBean(mobile,days,amount,dob,String.valueOf(status));
    			
    			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    @FXML
    void fetchCust(ActionEvent event) {
    	TableColumn<UserBean, String> mobile=new TableColumn<UserBean, String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));//bean wali field ka name
    	mobile.setMinWidth(150);
    	
    	TableColumn<UserBean, String> days=new TableColumn<UserBean, String>("No Of Days");
    	days.setCellValueFactory(new PropertyValueFactory<>("days"));//bean wali field ka name
    	days.setMinWidth(100);
    	
    	TableColumn<UserBean, String> amount=new TableColumn<UserBean, String>("Amount");
    	amount.setCellValueFactory(new PropertyValueFactory<>("amount"));//bean wali field ka name
    	amount.setMinWidth(150);
    	
    	TableColumn<UserBean, String> dob=new TableColumn<UserBean, String>("Date Of Billing");
    	dob.setCellValueFactory(new PropertyValueFactory<>("dob"));//bean wali field ka name
    	dob.setMinWidth(150);
    	
    	TableColumn<UserBean, String> status=new TableColumn<UserBean, String>("Status");
    	status.setCellValueFactory(new PropertyValueFactory<>("status"));//bean wali field ka name
    	status.setMinWidth(105);
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(mobile,days,amount,dob,status);

    	table.setItems(getCustomer());
    }
    
    ObservableList<UserBean> getCustomer()
    {
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	int status=0;
    	try
    	{
    	pst=obj.prepareStatement("select * from billing where status=? and mobile=?");
    	if(radCPaid.isSelected())
    	{
    		status=1;
    	}
    	else {
			status=0;
		}
    	pst.setInt(1, status);
    	pst.setString(2, txtMob.getText());
    	ResultSet rs=pst.executeQuery();
    	while(rs.next())
    		{
    			
    			String mobile=rs.getString("mobile");
    			String days=rs.getString("days");
    			String dob=rs.getString("dob");
    			String amount=rs.getString("amount");
    			UserBean ref=new UserBean(mobile,days,amount,dob,String.valueOf(status));
    			
    			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    
    @FXML
    void doexport(ActionEvent event) {

    	FileChooser file=new FileChooser();
    	file.setTitle("Select File");
    	f=file.showOpenDialog(null);
    }
   
   
    File f;
Connection obj;
PreparedStatement pst;
    @FXML
    void initialize() {
    	obj=dataConnect.Connect.getConnection();
    }
}

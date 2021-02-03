package customers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;


public class custController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboMob;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtAdd;

    @FXML
    private ListView<String> lstPapers;

    @FXML
    private ListView<String> lstPrice;

    @FXML
    private ComboBox<String> comboArea;

    @FXML
    private TextField txtHawker;

    @FXML
    void doprice(MouseEvent event) {
    	lstPrice.getSelectionModel().clearSelection();
    	ObservableList<Integer> indices= lstPapers.getSelectionModel().getSelectedIndices();
    	for(int i=0;i<indices.size();i++)
		{
			int index=indices.get(i);
			lstPrice.getSelectionModel().select(index);
		}
    }
    @FXML
    void doDelete(ActionEvent event) {
    	lstPapers.getSelectionModel().clearSelection();
    	lstPrice.getSelectionModel().clearSelection();
    	try {
			pst=obj.prepareStatement("delete from customers where mobile=?");
			pst.setString(1, comboMob.getEditor().getText());
			int count=	pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText(count+" Record Deleted");
	    	alert.show();
	    	txtAdd.setText("");
	    	txtHawker.setText("");
	    	txtName.setText("");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    	
    }

    @FXML
    void doFetch(ActionEvent event) {
    	lstPapers.getSelectionModel().clearSelection();
    	lstPrice.getSelectionModel().clearSelection();
    	try {
			pst=obj.prepareStatement("select * from customers where mobile=?");
			pst.setString(1,comboMob.getEditor().getText());
			ResultSet table=pst.executeQuery();
			if(table.next())
			{
				String name=table.getString("name");
				String address=table.getString("address");
				String hawker=table.getString("hawker");
				allpaper=table.getString("papers");
				allprice=table.getString("price");
				String ary[]=allpaper.split(",");
				for (String string : ary) {
					lstPapers.getSelectionModel().select(string);
				}
				String arr[]=allprice.split(",");
				for (String string : arr) {
					lstPrice.getSelectionModel().select(string);
				}
				txtName.setText(name);
				txtAdd.setText(address);
				txtHawker.setText(hawker);
			}
			else
			{
				Alert alert=new Alert(AlertType.ERROR);
		    	alert.setTitle("Invalid Name");
		    	alert.setContentText("No Record Found");
		    	alert.show();
			}
		} 
    	catch (SQLException e) {
    		Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    }

    @FXML
    void hawkerName(ActionEvent event) {
    	try {
    		String s="";
    		String area=comboArea.getEditor().getText();
			pst=obj.prepareStatement("select name from hawkers where area like ?");
			pst.setString(1, "%"+area+"%");
			ResultSet table=pst.executeQuery();
			while (table.next()) {
				s=table.getString("name");
			}
			txtHawker.setText(s);
		} catch (SQLException e) {
			// TODO: handle exception
		}
    }

    @FXML
    void doSave(ActionEvent event) {
    	
    	try {
			pst=obj.prepareStatement("insert into customers values(?,?,?,?,?,?,?)");
			
			pst.setString(1,txtName.getText());
			pst.setString(2, comboMob.getEditor().getText());
			pst.setString(3, txtAdd.getText());
			allpaper="";
			ObservableList<String> papers=lstPapers.getSelectionModel().getSelectedItems();
			for (String string : papers) {
				allpaper=allpaper+string+",";
			}
			pst.setString(4, allpaper);
			allprice="";
			ObservableList<Integer> indices= lstPapers.getSelectionModel().getSelectedIndices();
			for(int i=0;i<indices.size();i++)
			{
				int index=indices.get(i);
				String p=price.get(index);
				allprice=allprice+p+",";
			}
			pst.setString(5,allprice);
			pst.setString(6, txtHawker.getText());
			LocalDate doj=LocalDate.now();
			java.sql.Date date=java.sql.Date.valueOf(doj);
			pst.setDate(7, date);
			pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText("Record Saved");
	    	alert.show();
	    	txtAdd.setText("");
	    	txtHawker.setText("");
	    	txtName.setText("");
	    	lstPapers.getSelectionModel().clearSelection();
	    	lstPrice.getSelectionModel().clearSelection();
	    	
		} catch (SQLException e) {
			Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    }

    @FXML
    void doUpdate(ActionEvent event) {

    	try {
			pst=obj.prepareStatement("update customers set name=?,address=?,papers=?,price=?,hawker=? where mobile=?");
			
			pst.setString(1,txtName.getText());
			pst.setString(6, comboMob.getEditor().getText());
			pst.setString(2, txtAdd.getText());
			
			ObservableList<String> papers=lstPapers.getSelectionModel().getSelectedItems();
			if(papers!=null)
			{
				allpaper="";
				allprice="";
				for (String string : papers) {
					allpaper=allpaper+string+",";
				}
				
				
				ObservableList<Integer> indices= lstPapers.getSelectionModel().getSelectedIndices();
				for(int i=0;i<indices.size();i++)
				{
					int index=indices.get(i);
					String p=price.get(index);
					allprice=allprice+p+",";
					lstPrice.getSelectionModel().select(p);
				}
			}
			pst.setString(3, allpaper);
			pst.setString(4,allprice);
			pst.setString(5, txtHawker.getText());
			
			int count=pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText(count+" Record updated");
	    	alert.show();
	    	txtAdd.setText("");
	    	txtHawker.setText("");
	    	txtName.setText("");
	    	lstPapers.getSelectionModel().clearSelection();
	    	lstPrice.getSelectionModel().clearSelection();
	    	
		} catch (SQLException e) {
			Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    }
    String allprice="";
    String allpaper="";
    ArrayList<String> papers;
    ArrayList<String> price;
    Connection obj;
    PreparedStatement pst;
    @FXML
    void initialize() {
    	obj=dataConnect.Connect.getConnection();
    	ArrayList<String> lst = new ArrayList<String>(Arrays.asList("New Shakti Nagar","Ajit Road","Model Town","Vishal Nagar","Green Avenue","Bharat Nagar","Veer Colony"));
    	comboArea.getItems().addAll(lst);
    	comboArea.getSelectionModel().select(0);
    	 ArrayList<String> moblst = new ArrayList<String>();
         try {
 			pst=obj.prepareStatement("select * from customers");
 			
 			ResultSet table=pst.executeQuery();
 			while(table.next())
 			{
 			String x=table.getString("mobile");
 			moblst.add(x);
 			}

 		} 
     	catch (Throwable e) {
     	System.out.println("Not Cool");	
     	}
 		comboMob.getItems().addAll(moblst);
 		papers=new ArrayList<String>();
 		price=new ArrayList<String>();
 		try {
			pst=obj.prepareStatement("select * from papers");
			ResultSet table=pst.executeQuery();
			while (table.next()) {
				String paper=table.getString("paper");
				String pr=table.getString("price");
				papers.add(paper);
				price.add(pr);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 		lstPapers.getItems().addAll(papers);
 		lstPrice.getItems().addAll(price);
 		lstPapers.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
 		lstPrice.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }
}

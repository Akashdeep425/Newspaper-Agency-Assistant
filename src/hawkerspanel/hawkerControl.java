package hawkerspanel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class hawkerControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private TextField txtMob;

    @FXML
    private TextField txtAdd;

    @FXML
    private TextField txtSal;

    @FXML
    private ListView<String> lstArea;

    @FXML
    private ImageView imgaadhaar;

    @FXML
    void doDel(ActionEvent event) {
    	try {
			pst=obj.prepareStatement("delete from hawkers where name=?");
			pst.setString(1,comboName.getEditor().getText() );
			
		int count=	pst.executeUpdate();
		Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Successful");
    	alert.setContentText(count+" Record Deleted");
    	alert.show();
    	txtAdd.setText(null);
    	txtMob.setText(null);
    	txtSal.setText(null);
    	
		} 
    	catch (SQLException e) {
    		Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    }

    @FXML
    void doFetch(ActionEvent event) {
    	try {
			pst=obj.prepareStatement("select * from hawkers where name=?");
			pst.setString(1,comboName.getEditor().getText());
			ResultSet table=pst.executeQuery();
			if(table.next())
			{
				String mob=table.getString("mobile");
				String add=table.getString("address");
				upall=table.getString("area");
				int sal=table.getInt("salary");
				String pic=table.getString("aadhaar");
				Image img=new Image(new FileInputStream(pic));
				imgaadhaar.setImage(img);
				txtAdd.setText(add);
				txtMob.setText(mob);
				txtSal.setText(String.valueOf(sal));
				f=new File(pic);
				String[] ary=upall.split(",");
				for (String string : ary) {
					lstArea.getSelectionModel().select(string);
				}
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
    	catch (FileNotFoundException e) {
    		e.printStackTrace();
    		// TODO: handle exception
		}
    }

    @FXML
    void doSave(ActionEvent event) {
    	try {
			pst=obj.prepareStatement("insert into hawkers values(?,?,?,?,?,?,?)");
			
			pst.setString(1,comboName.getEditor().getText());
			pst.setString(2, txtMob.getText());
			pst.setString(3, txtAdd.getText());
			String all="";
			ObservableList<String> areas=lstArea.getSelectionModel().getSelectedItems();
			for (String string : areas) {
				all=all+string+",";
			}
			pst.setString(4, all);
			pst.setString(5, f.getAbsolutePath());
			pst.setInt(6, Integer.parseInt(txtSal.getText()));
			LocalDate doj=LocalDate.now();
			java.sql.Date date=java.sql.Date.valueOf(doj);
			pst.setDate(7, date);
			pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText("Record Saved");
	    	alert.show();
	    	txtAdd.setText(null);
	    	txtMob.setText(null);
	    	txtSal.setText(null);
	    	
		} catch (SQLException e) {
			Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    }

    @FXML
    void doPic(ActionEvent event) {
    	FileChooser file=new FileChooser();
    	file.setTitle("Select Image");
		f=file.showOpenDialog(null);
		
		Image img=null;
		try {
			img=new Image(new FileInputStream(f));
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		imgaadhaar.setImage(img);
    }
    @FXML
    void doUpdate(ActionEvent event) {
    	try {
			pst=obj.prepareStatement("update hawkers set mobile=?,address=?,area=?,aadhaar=?,salary=? where name=?");
			pst.setString(1, txtMob.getText());
			pst.setString(2, txtAdd.getText());
			ObservableList<String> areas=lstArea.getSelectionModel().getSelectedItems();
			for (String string : areas) {
				upall=upall+string+",";
			}
			pst.setString(3, upall);
			pst.setString(4, f.getAbsolutePath());
			pst.setInt(5, Integer.parseInt(txtSal.getText()));
			pst.setString(6,comboName.getEditor().getText());
			int count=pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText(count+" Record Updated");
	    	alert.show();
	    	txtAdd.setText(null);
	    	txtMob.setText(null);
	    	txtSal.setText(null);
	    	
		} catch (SQLException e) {
			Alert alert=new Alert(AlertType.ERROR);
	    	alert.setTitle("Unsuccessful");
	    	alert.setContentText(e.getMessage());
	    	alert.show();
		}
    }
    String upall="";
    File f;
    Connection obj;
    PreparedStatement pst;
    @FXML
    void initialize() {
    	 obj=dataConnect.Connect.getConnection();
         ArrayList<String> lst = new ArrayList<String>(Arrays.asList("New Shakti Nagar","Ajit Road","Model Town","Vishal Nagar","Green Avenue","Bharat Nagar","Veer Colony"));
         lstArea.getItems().addAll(lst);
         lstArea.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
         ArrayList<String> namelst = new ArrayList<String>();
         try {
 			pst=obj.prepareStatement("select * from hawkers");
 			
 			ResultSet table=pst.executeQuery();
 			while(table.next())
 			{
 			String x=table.getString("name");
 			namelst.add(x);
 			}

 		} 
     	catch (Throwable e) {
     	System.out.println("Not Cool");	
     	}
 		comboName.getItems().addAll(namelst);
    }
}

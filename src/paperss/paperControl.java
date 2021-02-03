package paperss;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;

public class paperControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboName;

    @FXML
    private TextField txtprice;

    @FXML
    void doDel(ActionEvent event) {
    	try {
			pst=obj.prepareStatement("delete from papers where paper=?");
			pst.setString(1,comboName.getEditor().getText() );
			
		int count=	pst.executeUpdate();
		Alert alert=new Alert(AlertType.INFORMATION);
    	alert.setTitle("Successful");
    	alert.setContentText(count+" Record Deleted");
    	alert.show();
    	txtprice.setText("");
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
			pst=obj.prepareStatement("select * from papers where paper=?");
			pst.setString(1,comboName.getEditor().getText() );
			ResultSet table=pst.executeQuery();
			if(table.next())
			{
				float price=table.getFloat("price");
				txtprice.setText(String.valueOf(price));
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
    void doSave(ActionEvent event) {
    	try {
			pst=obj.prepareStatement("insert into papers values(?,?)");
			
			pst.setString(1,comboName.getEditor().getText());
			pst.setFloat(2, Float.parseFloat(txtprice.getText()));
			
			pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText("Record Saved");
	    	alert.show();
	    	txtprice.setText("");
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
			pst=obj.prepareStatement("update papers set price=? where paper=?");
			
			pst.setString(2,comboName.getEditor().getText());
			pst.setFloat(1, Float.parseFloat(txtprice.getText()));
			
			int count=pst.executeUpdate();
			Alert alert=new Alert(AlertType.INFORMATION);
	    	alert.setTitle("Successful");
	    	alert.setContentText(count+" Record Updated");
	    	alert.show();
	    	txtprice.setText("");
		} catch (SQLException e) {
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
        ArrayList<String> lst = new ArrayList<String>();
        try {
			pst=obj.prepareStatement("select * from papers");
			
			ResultSet table=pst.executeQuery();
			while(table.next())
			{
			String x=table.getString("paper");
			lst.add(x);
			}

		} 
    	catch (Throwable e) {
    	System.out.println("Not Cool");	
    	}
		comboName.getItems().addAll(lst);
		
    }
}

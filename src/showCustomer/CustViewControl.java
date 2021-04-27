package showCustomer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import showCustomer.UserBean;


public class CustViewControl {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> comboHawk;

    @FXML
    private ComboBox<String> comboPaper;

    @FXML
    private TableView<UserBean> table;
 
    @FXML
    void doHawker(ActionEvent event) {
//    	File file=new File("F:\\downloads\\file_example_WAV_1MG.wav");
//    	try {
//			AudioInputStream audio=AudioSystem.getAudioInputStream(file);
//			Clip clip=AudioSystem.getClip();
//			clip.open(audio);
//			clip.start();
//			
//		} catch (UnsupportedAudioFileException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	

    	TableColumn<UserBean, String> name=new TableColumn<UserBean, String>("Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));//bean wali field ka name
    	name.setMinWidth(110);
    	
    	TableColumn<UserBean, String> mobile=new TableColumn<UserBean, String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));//bean wali field ka name
    	mobile.setMinWidth(105);
    	
    	TableColumn<UserBean, String> address=new TableColumn<UserBean, String>("Address");
    	address.setCellValueFactory(new PropertyValueFactory<>("address"));//bean wali field ka name
    	address.setMinWidth(150);
    	
    	TableColumn<UserBean, String> paper=new TableColumn<UserBean, String>("Papers");
    	paper.setCellValueFactory(new PropertyValueFactory<>("papers"));//bean wali field ka name
    	paper.setMinWidth(150);
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(name,mobile,address,paper);
    	ObservableList<UserBean> ary=gethawkers();
    	table.setItems(gethawkers());
    	
    }

    ObservableList<UserBean> gethawkers()
    {
    	
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	try
    	{
    	pst=obj.prepareStatement("select * from customers where hawker=?");
    	pst.setString(1, comboHawk.getEditor().getText());
    	ResultSet rs=pst.executeQuery();
    	while(rs.next())
    		{
    			String name=rs.getString("name");
    			String mobile=rs.getString("mobile");
    			String address=rs.getString("address");
    			String papers=rs.getString("papers");
    			String hawker=rs.getString("hawker");
    			UserBean ref=new UserBean(name,mobile,address,hawker,papers);
    			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    
    ObservableList<UserBean> getpapers()
    {
    	ObservableList<UserBean> ary=FXCollections.observableArrayList();
    	try
    	{
    	pst=obj.prepareStatement("select * from customers where papers like ?");
    	String pString=comboPaper.getEditor().getText();
    	pst.setString(1, "%"+pString+"%");
    	ResultSet rs=pst.executeQuery();
    	while(rs.next())
    		{
    			String name=rs.getString("name");
    			String mobile=rs.getString("mobile");
    			String address=rs.getString("address");
    			String papers=rs.getString("papers");
    			String hawker=rs.getString("hawker");
    			UserBean ref=new UserBean(name,mobile,address,hawker,papers);
    			ary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return ary;
    }
    
    ObservableList<UserBean> getAllRecords()
    {
    	exary=FXCollections.observableArrayList();
    	try
    	{
    	pst=obj.prepareStatement("select * from customers");
    	ResultSet rs=pst.executeQuery();
    	while(rs.next())
    		{
    			String name=rs.getString("name");
    			String mobile=rs.getString("mobile");
    			String address=rs.getString("address");
    			String papers=rs.getString("papers");
    			String hawker=rs.getString("hawker");
    			UserBean ref=new UserBean(name,mobile,address,hawker,papers);
    			exary.add(ref);
    		}
    	}
    	catch(Exception exp)
    	{
    		exp.printStackTrace();
    	}
    	return exary;
    }
    
    @FXML
    void doPaper(ActionEvent event) {

    	TableColumn<UserBean, String> name=new TableColumn<UserBean, String>("Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));//bean wali field ka name
    	name.setMinWidth(110);
    	
    	TableColumn<UserBean, String> mobile=new TableColumn<UserBean, String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));//bean wali field ka name
    	mobile.setMinWidth(105);
    	
    	TableColumn<UserBean, String> hawker=new TableColumn<UserBean, String>("Hawker");
    	hawker.setCellValueFactory(new PropertyValueFactory<>("hawker"));//bean wali field ka name
    	hawker.setMinWidth(100);
    	
    	TableColumn<UserBean, String> address=new TableColumn<UserBean, String>("Address");
    	address.setCellValueFactory(new PropertyValueFactory<>("address"));//bean wali field ka name
    	address.setMinWidth(150);
    	
    	TableColumn<UserBean, String> paper=new TableColumn<UserBean, String>("Papers");
    	paper.setCellValueFactory(new PropertyValueFactory<>("papers"));//bean wali field ka name
    	paper.setMinWidth(150);
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(name,mobile,address,paper,hawker);
    	ObservableList<UserBean> ary=getpapers();
    	table.setItems(getpapers());
    }

    @FXML
    void doShowAll(ActionEvent event) {

    	TableColumn<UserBean, String> name=new TableColumn<UserBean, String>("Name");
    	name.setCellValueFactory(new PropertyValueFactory<>("name"));//bean wali field ka name
    	name.setMinWidth(110);
    	
    	TableColumn<UserBean, String> mobile=new TableColumn<UserBean, String>("Mobile");
    	mobile.setCellValueFactory(new PropertyValueFactory<>("mobile"));//bean wali field ka name
    	mobile.setMinWidth(105);
    	
    	TableColumn<UserBean, String> hawker=new TableColumn<UserBean, String>("Hawker");
    	hawker.setCellValueFactory(new PropertyValueFactory<>("hawker"));//bean wali field ka name
    	hawker.setMinWidth(100);
    	
    	TableColumn<UserBean, String> address=new TableColumn<UserBean, String>("Address");
    	address.setCellValueFactory(new PropertyValueFactory<>("address"));//bean wali field ka name
    	address.setMinWidth(150);
    	
    	TableColumn<UserBean, String> paper=new TableColumn<UserBean, String>("Papers");
    	paper.setCellValueFactory(new PropertyValueFactory<>("papers"));//bean wali field ka name
    	paper.setMinWidth(150);
    	
    	table.getColumns().clear();
    	table.setItems(null);
    	table.getColumns().addAll(name,mobile,address,paper,hawker);
//    	ObservableList<UserBean> ary=getAllRecords();
    	table.setItems(getAllRecords());
    }
    @FXML
    void doExport(ActionEvent event) {
    	getAllRecords();
        
    	Writer writer = null;
            try {

                File file = new File("Users.csv");
                writer = new BufferedWriter(new FileWriter(file));
                String text="NAME,MOBILE,ADDRESS,HAWKER,PAPERS\n";
                writer.write(text);
                for (UserBean p : exary)
                {
    				text = p.getName()+ "," + p.getMobile()+ "," + p.getAddress()+ "," + p.getHawker()+","+p.getPapers()+"\n";
                    writer.write(text);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            finally {
               
                try {
					writer.flush();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                 try {
					writer.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }

    }


    ObservableList<UserBean> exary;
    Connection obj;
    PreparedStatement pst;
    @FXML
    void initialize() {
        obj=dataConnect.Connect.getConnection();
        ArrayList<String> hawklst=new ArrayList<String>();
        ArrayList<String> paperlst=new ArrayList<String>();
        try {
			pst=obj.prepareStatement("select distinct hawker from customers");
			ResultSet table=pst.executeQuery();
			while (table.next()) {
				String hawker=table.getString("hawker");
				hawklst.add(hawker);
			}
			comboHawk.getItems().addAll(hawklst);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			pst=obj.prepareStatement("select * from papers");
			ResultSet table=pst.executeQuery();
			while(table.next()) {
				String paper=table.getString("paper");
				paperlst.add(paper);
			}
			comboPaper.getItems().addAll(paperlst);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        comboHawk.getSelectionModel().select(0);
        comboPaper.getSelectionModel().select(0);
        

    }
}

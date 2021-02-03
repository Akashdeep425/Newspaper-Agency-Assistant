package showCustomer;

public class UserBean {

	String name,mobile,address,hawker,papers;

	UserBean(){}
	public UserBean(String name, String mobile, String address, String hawker, String papers) {
		super();
		this.name = name;
		this.mobile = mobile;
		this.address = address;
		this.hawker = hawker;
		this.papers = papers;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getHawker() {
		return hawker;
	}
	
	public void setHawker(String hawker) {
		this.hawker = hawker;
	}
	
	public String getPapers() {
		return papers;
	}

	public void setPapers(String papers) {
		this.papers = papers;
	}
	
}

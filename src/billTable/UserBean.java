package billTable;

public class UserBean {

	String mobile,days,amount,dob,status;

	public UserBean(String mobile, String days, String amount, String dob,String status) {
		super();
		this.mobile = mobile;
		this.days = days;
		this.amount = amount;
		this.dob = dob;
		this.status=status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDays() {
		return days;
	}

	public void setDays(String days) {
		this.days = days;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDob() {
		return dob;
	}

	public void setDob(String dob) {
		this.dob = dob;
	}
}

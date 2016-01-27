package dapp.com.awok.awokdriversapp.Modals;

public class Order {
	private String order_no, name,phone,id,status,show, phone2;


	public Order() {
	}

	public Order(String order_no, String name, String phone, String phone2, String id, String status, String show) {
		this.order_no = order_no;
		this.name = name;
		this.phone = phone;
		this.phone2 = phone2;
        this.id=id;
		this.status=status;
		this.show=show;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getShow() {
		return show;
	}

	public void setShow(String show) {
		this.show = show;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}


	public String getOrder_no() {
		return order_no;
	}

	public void setOrder_no(String order_no) {
		this.order_no = order_no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}

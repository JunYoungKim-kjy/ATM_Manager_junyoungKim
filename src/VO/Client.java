package VO;

public class Client {
	private int clientNo;
	private String id;
	private String pw;
	private String name;
	private Account[] accounts;
	
	public int getClientNo() {
		return clientNo;
	}
	public String getId() {
		return id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Account[] getAccounts() {
		return accounts;
	}
	public Client(int clientNo, String id, String pw, String name) {
		this.clientNo = clientNo;
		this.id = id;
		this.pw = pw;
		this.name = name;
	}
	@Override
	public String toString() {
		return clientNo + "\t" + id + "\t" + pw + "\t" + name;
	}
	public String saveAsData() {
		String data = "";
		data = "%d/%s/%s/%s\n".formatted(clientNo,id,pw,name);
		return data;
				
	}
	
}

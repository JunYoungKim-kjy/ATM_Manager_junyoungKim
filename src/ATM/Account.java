package ATM;

public class Account {
	private int clientNo;
	private String clientId;
	private String Account;
	private int money;
	
	public int getClientNo() {
		return clientNo;
	}
	public String getClientId() {
		return clientId;
	}
	public String getAccount() {
		return Account;
	}
	public int getMoney() {
		return money;
	}
	public void setMoney(int money) {
		this.money = money;
	}
	Account(String clientId, String account, int money) {
		this.clientId = clientId;
		Account = account;
		this.money = money;
	}
	@Override
	public String toString() {
		return clientId + "\t" + Account + "\t" + money;
	}
	public String saveAsData() {
		String data = "";
		data = "%s/%s/%d\n".formatted(clientId,Account,money);
		return data;
	}
	
}

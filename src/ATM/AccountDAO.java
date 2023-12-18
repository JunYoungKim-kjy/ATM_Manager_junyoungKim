package ATM;

public class AccountDAO {
	Util util;
	Account[] accList;
	int cnt;
	AccountDAO() {
		util = new Util();
	}
	public String getSaveData() {
		String data = "";
		for(Account acc :accList) {
			data += acc.saveAsData();
		}
		return data;
	}
	public void deleteOneClientAllAccount(Client client) {
		if(accList ==null)return;
		for(int i=0; i < cnt; i+=1) {
			if(accList[i].getClientId().equals(client.getId())) {
				deleteOneAccount(accList[i]);
				i-=1;
			}
		}
	}
	private boolean hasAccountByClient(Client cl) {
		for(Account acc : accList) {
			if(acc.getClientId().equals(cl.getId()))return true;
		}
		return false;
	}
	public void deleteAccount(Client cl) {
		if(!hasAccountByClient(cl)) {
			System.out.println("이 계정에 존재하는 계좌가 없습니다.");
			return;
		}
		printMyAccountList(cl);
		Account acc = getAccountByAccount();
		if(acc == null) {
			System.out.println("계좌번호가 존재하지 않습니다.");
			return;
		}
		if(!acc.getClientId().equals(cl.getId())) {
			System.out.println("본인 계좌번호가 아닙니다.");
			return;
		}
		System.out.println(acc + "삭제완료");
		deleteOneAccount(acc);
		
		
		
	}
	public void printAllAccount() {
		for(Account acc : accList) {
			System.out.println(acc);
		}
	}
	private void printAllAccountOutMyAccout(Account acc) {
		for(Account account : accList) {
			if(account!=acc) {
				System.out.println(account);
			}
		}
	}
	private Account getAccountByAccount() {
		String account = "";
		while(true) {
			account = util.getStringValue("계좌번호 (-포함해주세요) [12자리]");
			if(account.length() < 14) {
				System.out.println("12자리를 입력해주세요.");
				continue;
			}
			break;
		}
		for(Account a :accList) {
			if(account.equals(a.getAccount())) {
				return a;
			}
		}
		return null;
	}
	private Account[] getAccListByClient(String id) {
		int cnt = 0;
		for(Account acc : accList) {
			if(acc.getClientId().equals(id)) {
				cnt +=1;
			}
		}
		if(cnt == 0)return null;
		Account[] tempList = new Account[cnt];
		int idx = 0;
		for(Account acc : accList) {
			if(acc.getClientId().equals(id)) {
				tempList[idx++] = acc;
			}
		}
		return tempList;
	}
	public void insertMoneyMyAccount(Client cl) {
		Account[] tempList = getAccListByClient(cl.getId());
		for(Account acc : tempList) {
			System.out.println(acc);
		}
		int idx = util.getValue("입금할 계좌를 선택해주세요.", 0, tempList.length-1);
		int money = util.getValue("입금할 금액 입력", 1, 1000000);
		tempList[idx].setMoney(tempList[idx].getMoney() + money);
		System.out.println(tempList[idx]);
		System.out.println("입금완료");
	}
	private void deleteOneAccount(Account acc) {
		if(cnt == 1) {
			accList = null;
			cnt -=1;
			return;
		}
		Account[] temp = accList;
		accList = new Account[cnt - 1];
		int idx = 0;
		for(int i = 0; i < cnt ; i+=1) {
			if(temp[i]!=acc) {
				accList[idx++] = temp[i];
			}
		}
		cnt-=1;
	}
	public void setData(String data) {
		String temp[] = data.split("\n");
		for(int i=0; i < temp.length; i+=1) {
			String[]info = temp[i].split("/");
			addOneAccount(info[0],info[1], Integer.parseInt(info[2]));
		}
	}
	public void setOneClientAccount(Client client) {
		for(Account acc : accList) {
			if(acc.getClientId().equals(client.getId())){
				addOneAccount(client.getId(), acc.getAccount(), acc.getMoney());
			}
		}
	}
	private void addOneAccount(String id,String account,int money) {
		if(cnt==0) {
			accList = new Account[1];
			accList[cnt] = new Account(id, account, money);
			cnt+=1;
			return;
		}
		Account[] temp = accList;
		accList = new Account[cnt + 1];
		for(int i=0; i<cnt;i+=1) {
			accList[i] = temp[i];
		}
		accList[cnt]=new Account(id, account, money);
		cnt+=1;
	}
	public void insertAccountInClient(Client cl) {
		System.out.println("[ 계좌번호 추가 ]");
		String acc1 = util.getAccount4Num();
		System.out.println(acc1+"-****-****");
		String acc2 = util.getAccount4Num();
		System.out.println(acc1+"-"+acc2+"-****");
		String acc3 = util.getAccount4Num();
		System.out.printf("%s-%s-%s\n",acc1,acc2,acc3);
		String account = acc1 + "-" + acc2 + "-" + acc3;
		if(!hasAccountNum(account)) {
			System.out.println("존재하는 계좌번호입니다.");
			return;
		}
		addOneAccount(cl.getId(), account, 500);
		System.out.println("[계좌번호 생성 완료]");
	}
	private boolean hasAccountNum(String account) {
		for(Account acc : accList) {
			if(account.equals(acc.getAccount())) {
				return false;
			}
		}
		return true;
	}
	public void getMoneyMyAccount(Client cl) {
		Account[] tempList = getAccListByClient(cl.getId());
		for(Account acc : tempList) {
			System.out.println(acc);
		}
		int idx = util.getValue("출금할 계좌를 선택해주세요.", 0, tempList.length-1);
		if(tempList[idx].getMoney() <= 0) {
			System.out.println("출금할 잔액이 없습니다.");
			return;
		}
		int money = util.getValue("출금할 금액 입력", 1, tempList[idx].getMoney());
		tempList[idx].setMoney(tempList[idx].getMoney() - money);
		System.out.println(tempList[idx]);
		System.out.println("출금완료");
	}
	public void sendMoneyOtherAccount(Client cl) {
		Account[] tempList = getAccListByClient(cl.getId());
		if(tempList==null) {
			System.out.println("이체 가능한 계좌가 없습니다");
			return;
		}
		for(Account acc : tempList) {
			System.out.println(acc);
		}
		int idx = util.getValue("이체할 계좌를 선택해주세요.", 0, (tempList.length-1));
		if(tempList[idx].getMoney() <= 0) {
			System.out.println("이체할 금액이 없습니다.");
			return;
		}
		Account myAcc = tempList[idx];
		printAllAccountOutMyAccout(myAcc);
		System.out.println("이체할 계좌번호를 입력해주세요");
		Account yourAcc = getAccountByAccount();
		if(yourAcc==null) {
			System.out.println("존재하지 않는 계좌번호");
			return;
		}
		if(myAcc==yourAcc) {
			System.out.println("같은 계좌에 이체할 수 없습니다.");
			return;
		}
		int money = util.getValue("이체하실 금액을 입력하세요", 1, myAcc.getMoney());
		myAcc.setMoney(myAcc.getMoney() - money);
		yourAcc.setMoney(yourAcc.getMoney() + money);
		System.out.println("이체 완료");
		System.out.println(myAcc);
		System.out.println(yourAcc);
		
	}
	public void printMyAccountList(Client cl) {
		if(!hasAccountByClient(cl)) {
			System.out.println("보유하신 계좌가 없습니다.");
			return;
		}
		for(Account acc : accList) {
			if(cl.getId().equals(acc.getClientId())) {
				System.out.println(acc);
			}
		}
	}
}

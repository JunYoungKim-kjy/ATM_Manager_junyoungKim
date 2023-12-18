package DAO;

import Utils.Util;
import VO.Client;

public class ClientDAO {
	private Client[] clientList;
	private int maxNo;
	private int cnt;
	
	public ClientDAO(){
		maxNo = 1001;
	}
	public void maxNo() {
		int max = maxNo;
		for(Client c : clientList) {
			if(max < c.getClientNo()) {
				max = c.getClientNo();
			}
		}
		maxNo = max;
	}
	public void setData(String data) {
		String temp[] = data.split("\n");
		for(int i=0; i < temp.length; i+=1) {
			String[]info = temp[i].split("/");
			insertOneClinet(info[1], info[3], info[2]);
		}
	}
	public String getSaveData() {
		String data = "";
		for(Client client :clientList) {
			data += client.saveAsData();
		}
		return data;
	}
	public void printClientList(AccountDAO accDAO){
		if(clientList == null) {
			System.out.println("회원데이터가 없습니다.");
			return;
		}
		System.out.println("=============================");
		System.out.println("No \tid \tpw \tname");
		System.out.println("-----------------------------");
		for(Client c :clientList) {
			System.out.println(c);
		}
		System.out.println("=============================");
	}
	public void editClient() {
		String id = Util.getStringValue("[회원수정]아이디 입력");
		Client client = getClientById(id);
		if(client == null) {
			System.out.println("회원이 존재하지 않습니다.");
			return;
		}
		System.out.println("[1]비밀번호 수정[2]이름 수정[0]종료");
		int sel = Util.getValue("[회원수정] 메뉴:", 0, 2);
		if(sel == 0) {
			System.out.println("[회원수정] 종료");
		}else if(sel ==1) {
			String pw = Util.getStringValue("[비밀번호변경] 비밀번호 입력");
			if(client.getPw().equals(pw)) {
				System.out.println("기존 비밀번호와 동일합니다.");
				return;
			}
			client.setPw(pw);
		}else if(sel ==2) {
			String name = Util.getStringValue("[이름변경] 이름 입력");
			if(client.getName().equals(name)) {
				System.out.println("기존 이름과 동일합니다.");
				return;
			}
			client.setName(name);
		}
		System.out.println(client + "\n수정 완료");
	}
	private Client getClientById(String id) {
		for(Client c : clientList) {
			if(id.equals(c.getId())) {
				return c;
			}
		}
		return null;
	}
	public void deleteClient(AccountDAO accDAO) {
		if(clientList == null){
			System.out.println("회원데이터가 없습니다.");
			return;
		}
		String id = Util.getStringValue("[회원삭제] 아이디 입력 ");
		Client client = getClientById(id);
		if(client == null) {
			System.out.println("존재하지 않는 회원입니다.");
			return;
		}
		accDAO.deleteOneClientAllAccount(client);
		deleteOneClient(client);
		
	}
	private void deleteOneClient(Client client) {
		if(cnt ==1) {
			clientList = null;
			cnt -=1;
			return;
		}
		Client[] temp = clientList;
		clientList = new Client[cnt - 1];
		int idx = 0;
		for(int i=0; i < cnt ; i +=1) {
			if(temp[i] != client) {
				clientList[idx++] = temp[i];
			}
		}
		cnt -=1;
	}
	private void setOneClientAccount(AccountDAO accDAO) {
		for(Client c : clientList) {
			accDAO.setOneClientAccount(c);
		}
	}
	public void insertClient() {
		System.out.println("[ 회원가입 ]");
		String id = Util.getStringValue("[회원가입] 아이디 입력:");
		Client client = getClientById(id);
		if(client != null) {
			System.out.println("존재하는 아이디 입니다.");
			return;
		}
		String name = Util.getStringValue("[회원가입] 이름 입력 :");
		String pw = Util.getStringValue("[회원가입] 비밀번호 입력 :");
		
		insertOneClinet(id, name, pw);
		System.out.println("[회원가입 완료]");
		
	}
	private void insertOneClinet(String id, String name, String pw) {
		if (cnt == 0) {
			clientList = new Client[cnt + 1];
			clientList[cnt] = new Client(++maxNo, id, pw, name);
			cnt += 1;
			return;
		}
		
		Client[] temp = clientList;
		clientList = new Client[cnt + 1];
		for (int i = 0; i < cnt; i += 1) {
			clientList[i] = temp[i];
		}
		clientList[cnt] = new Client(++maxNo, id, pw, name);
		cnt+=1;
	}
	public Client loginMng() {
		System.out.println("[ 로그인 ]");
		String id = Util.getStringValue("[로그인] 아이디 입력 :");
		String pw = Util.getStringValue("[로그인] 비밀번호 입력:");
		Client client = getClientById(id);
		
		if(client==null ||!pw.equals(client.getPw())) {
			System.out.println("아이디/비밀번호를 확인해주세요.");
			return null;
		}
		System.out.println("[로그인 완료]");
		return client;
	}
	public boolean exitClient(Client cl ,AccountDAO accDAO) {
		System.out.println("=====회원 탈퇴=======");
		String pw = Util.getStringValue("[회원탈퇴] 비밀번호 입력:");
		if(!pw.equals(cl.getPw())) {
			System.out.println("비밀번호가 틀렸습니다.");
			return false;
		}
		accDAO.deleteOneClientAllAccount(cl);
		deleteOneClient(cl);
		System.out.println(cl.getName() + "회원탈퇴 완료");
		return true;
	}
}

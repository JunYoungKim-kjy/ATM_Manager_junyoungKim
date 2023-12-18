package ATM;

public class BankController {
	Util util;
	AccountDAO accDAO;
	ClientDAO clientDAO;
	Client log;
	
	BankController(){
		util = new Util();
		accDAO = new AccountDAO();
		clientDAO = new ClientDAO();
		util.loadFromFile(accDAO, clientDAO);
	}
	//[1]관리자 [2]사용 [0] 종료
	//관리자
	//[1]회원목록 [2]회원수정[3]회원삭제[4]데이터 저장[5]데이터 로드[0]로그아웃
	
	//회원수정 : 회원아이디 검색 비밀번호,이름 수정가능
	//회원삭제 : 회원아이디
	//데이터 저장 : account.txt  , client.txt
	
	
	//사용자
	//[1]회원가입 [2]로그인 [0]뒤로가기
	
	//로그인
	//[1]계좌추가 [2]계좌삭제 [3]입금 [4]출금 [5]이체 [6]탈퇴 [7]마이페이지 [0]로그아웃
	//계좌추가 숫자4개-숫자4개-숫자4개 일치할 때 추가 가능
	//계좌삭제 본인계좌만 삭제 가능
	
	//입금 : account계좌가 있을 때 만 입금 가능 : 100원 이상 입금/이체/출금 가능 : 잔고만큼만 가능
	//탈퇴 : 패스워드 다시 입력 > 탈퇴가능
	//마이페이지 : 내 계좌 목록(잔고) 확인
	
	private void mainMenu() {
		System.out.println("[1]관리자 [2]사용자 [0]종료");
	}
	private void adminMenu() {
		while (true) {
			System.out.println("[1]회원목록 [2]회원수정 [3]회원삭제 [4]데이터 저장 [5]데이터 로드 [6]전체계좌목록 [0]로그아웃");
			int sel = util.getValue("관리자메뉴", 0, 6);
			if (sel == 0) {
				System.out.println("[뒤로가기]");
				return;
			} else if (sel == 1) {
				clientDAO.printClientList(accDAO);
			} else if (sel == 2) {
				clientDAO.editClient();
			} else if (sel == 3) {
				clientDAO.deleteClient(accDAO);
			} else if (sel == 4) {
				util.saveFromDataToFile(accDAO, clientDAO);
			} else if (sel == 5) {
				util.loadFromFile(accDAO, clientDAO);
			} else if (sel == 6) {
				accDAO.printAllAccount();
			}
		}
	}
	private void loginMenu() {
		while (true) {
			System.out.println("[1]계좌추가 [2]계좌삭제 [3]입금 [4]출금 [5]이체 [6]탈퇴 [7]마이페이지 [0]로그아웃");
			int sel = util.getValue("메뉴입력: ", 0, 7);
			if (sel == 0) {
				System.out.println("[뒤로가기]");
				return;
			} else if (sel == 1) {
				accDAO.insertAccountInClient(log);
			} else if (sel == 2) {
				accDAO.deleteAccount(log);
			} else if (sel == 3) {
				accDAO.insertMoneyMyAccount(log);
			} else if (sel == 4) {
				accDAO.getMoneyMyAccount(log);
			} else if (sel == 5) {
				accDAO.sendMoneyOtherAccount(log);
			} else if (sel == 6) {
				if(clientDAO.exitClient(log,accDAO)) {
					log =null;
					return;
				}
			} else if (sel == 7) {
				accDAO.printMyAccountList(log);
			}
		}
	}
	private void userMenu() {
		while (true) {
			System.out.println("[1]회원가입 [2]로그인 [0]뒤로가기");
			int sel = util.getValue("[로그인] 메뉴 : ", 0, 2);
			if (sel == 0) {
				System.out.println("[로그인 메뉴] 종료");
				return;
			} else if (sel == 1) {
				clientDAO.insertClient();
			} else if (sel == 2) {
				log = clientDAO.loginMng();
				if (log == null) {
					continue;
				}
				loginMenu();
			}
		}
	}
	public void run() {
		while(true) {
			mainMenu();
			int sel = util.getValue("메인메뉴", 0, 2);
			if(sel == 0) {
				System.out.println("종료");
				return;
			}else if(sel == 1) {
				adminMenu();
			}else if(sel == 2) {
				userMenu();
			}
			
		}
		
		
	}
	
}

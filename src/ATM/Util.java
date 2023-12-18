package ATM;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Util {
	private String CUR_PATH;
	public Scanner sc;
	private File file;
	Util () {
		CUR_PATH = System.getProperty("user.dir") + "\\src\\ATM\\";
		sc= new Scanner(System.in);
		fileInit("account.txt");
		fileInit("client.txt");
	}
	private String userData() {
		String userdata = "1001/test01/pw1/김철수\n";
		userdata += "1002/test02/pw2/이영희\n";
		userdata += "1003/test03/pw3/신민수\n";
		userdata += "1004/test04/pw4/최상민";
		return userdata;
	}
	private void fileInit(String fileName) {
		file = new File(CUR_PATH + fileName);
		if(!file.exists()) {
			try {
				file.createNewFile();
				System.out.println(fileName + "파일 생성 성공");
			} catch (IOException e) {
				System.out.println(fileName + "파일 생성 실패");
				e.printStackTrace();
			}
		}
	}
	private String accountData() {
		String accountdata = "test01/1111-1111-1111/8000\n";
		accountdata += "test02/2222-2222-2222/5000\n";
		accountdata += "test01/3333-3333-3333/11000\n";
		accountdata += "test03/4444-4444-4444/9000\n";
		accountdata += "test01/5555-5555-5555/5400\n";
		accountdata += "test02/6666-6666-6666/1000\n";
		accountdata += "test03/7777-7777-7777/1000\n";
		accountdata += "test04/8888-8888-8888/1000";
		return accountdata;
	}
	public void loadFromFile(AccountDAO accDAO,ClientDAO clientDAO) {
		String accData = getDataFromFile("account.txt");
		String clientData = getDataFromFile("client.txt");
		if(clientData==null) clientData = userData();
		if(accData == null) accData = accountData();
		accDAO.setData(accData);
		clientDAO.setData(clientData);
		clientDAO.maxNo();
//		clientDAO.setOneClientAccount(accDAO);
	}
	public String getAccount4Num() {
		String acc = "";
		while(true) {
			acc = getStringValue("4자리 입력 ****");
			if(acc.length() !=4) {
				System.out.println("4자리만 입력 가능합니다.");
				continue;
			}
			return acc;
		}
	}
	private String getDataFromFile(String fileName) {
		String data = "";
		try(FileReader fr = new FileReader(CUR_PATH + fileName);
			BufferedReader br = new BufferedReader(fr);){
			while(true) {
				String str = br.readLine();
				if(str == null) {
					break;
				}
				data += str+"\n";
			}
			System.out.println(fileName+"파일 로드 성공");
		} catch (IOException e) {
			System.out.println(fileName+"파일 로드 실패.");
			e.printStackTrace();
		}
		if(data.isEmpty() || data.isBlank()|| data.equals("")) {
			return null;
		}
		data = data.substring(0,data.length()-1);
		return data;
		
	}
	public void saveFromDataToFile(AccountDAO accDAO,ClientDAO clientDAO) {
		String accData = accDAO.getSaveData();
		String clientData = clientDAO.getSaveData();
		saveToFile("account.txt", accData);
		saveToFile("client.txt", clientData);
		
	}
	private void saveToFile(String fileName, String data) {
		try(FileWriter fw = new FileWriter(CUR_PATH + fileName);){
			fw.write(data);
			System.out.println(fileName+"파일 저장 성공");
		} catch (IOException e) {
			System.out.println(fileName+"파일 저장 실패");
		}
	}
	public int getValue(String msg,int start,int end) {
		while (true) {
			System.out.printf("[%s][%d-%d] : ", msg, start, end);
			try {
				int input = sc.nextInt();
				sc.nextLine();
				if (input < start || input > end) {
					System.out.println("입력 범위 오류");
					continue;
				}
				return input;
			} catch (Exception e) {
				sc.nextLine();
				System.out.println("숫자만 입력해주세요.");
			}
		}
	}
	public String getStringValue(String msg) {
		System.out.println("[" + msg + "]");
		return  sc.next();
	}
}

package bookWorm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Level {
	Scanner sc = new Scanner(System.in);
	
	private final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/pj_java";
	private final String USER_NAME = "root";
	private final String PASSWORD = "mirim2";
	
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	String sql;
	int age, often, aLevel, oLevel, level;
	
	// 책 레벨 선택하는 함수
	public void selectLevel() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			
			selectAge();
			
			System.out.println("빈도수를 입력하시겠습니까 ? (1: Yes, 2: No)");
			int o = Integer.parseInt(sc.nextLine());
			switch (o) {
				// 빈도수 입력하는 함수 불러옴.
				case 1: selectOften();
				break;
				case 2: System.out.println("알겠습니다.");
				break;
				default: System.out.println("잘못 입력하셨습니다.");
				break;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs.");
		}
		
	}

	// 나이 입력하는 함수
	public void selectAge() {
		System.out.println("나이를 입력하세요 : ");
		age = Integer.parseInt(sc.nextLine());
		
		// 나잇대에 따라 레벨이 달라지도록 함.
		if (age > 0) {
			if (age < 8) {
				aLevel = 1;
				System.out.println("당신의 레벨은 1입니다.");
			} else if (age < 14) {
				aLevel = 2;
				System.out.println("당신의 레벨은 2입니다.");
			} else if (age < 20) {
				aLevel = 3;
				System.out.println("당신의 레벨은 3입니다.");
			} else if (age < 40) {
				aLevel = 5;
				System.out.println("당신의 레벨은 5입니다.");
			} else {
				aLevel = 4;
				System.out.println("당신의 레벨은 4입니다.");
			}
		} else {
			System.out.println("잘못 입력하셨습니다.");
		}
		
		StringBuilder sb = new StringBuilder();
		sql = sb.append("SELECT * FROM booklist WHERE")
				.append(" level = ")
				.append(aLevel)
				.append(";").toString();
		
		//Debug
//		System.out.println(sql);
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// rs에 더이상 값이 없으면 '업데이트 예정입니다.' 문구를 출력함.
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("업데이트 예정입니다.\n");
			}

			// rsa에 rs.next()를 넣어 위의 if문과 겹치지 않도록 함. 값 하나가 잘려서 나오지 않는 문제 해결!
			while (rsa) {
				int bookNo1 = rs.getInt("bookNo");
				String title1 = rs.getString("title");
				String author1 = rs.getString("author");
				String country1 = rs.getString("country");
				int pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t작가 : " + author1 + ", 국가 : " + country1 + ", 페이지 수 : " + pageNo1);

				// rsa에 rs.next()값을 넣어줘서 계속 갱신이 되도록 함.
				rsa = rs.next();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs");
		}
		
	}
	
	public void selectOften() {
		System.out.println("책 읽는 빈도수를 입력하세요 (2주 단위) : ");
		often = Integer.parseInt(sc.nextLine());
		
		// 2주 단위에 맞춰서 책 읽는 빈도수에 따라 레벨이 달라지도록 함.
		if (often > 0 && often < 15) {
			if (often < 2) {
				oLevel = 1;
				System.out.println("당신의 레벨은 1입니다.");
			} else if (often < 5) {
				oLevel = 2;
				System.out.println("당신의 레벨은 2입니다.");
			} else if (often < 8) {
				oLevel = 3;
				System.out.println("당신의 레벨은 3입니다.");
			} else if (often < 11) {
				oLevel = 4;
				System.out.println("당신의 레벨은 4입니다.");
			} else if (often < 15) {
				oLevel = 5;
				System.out.println("당신의 레벨은 5입니다.");
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
		} else {
			System.out.println("잘못 입력하셨습니다.");
		}
		
		// 나이와 빈도수 레벨을 합친 후 평균 종합 레벨을 구함
		level = Math.round((aLevel + oLevel) / 2);
		
		System.out.println("\n종합레벨 : " + level + "\n");
		
		StringBuilder sb = new StringBuilder();
		sql = sb.append("SELECT * FROM booklist WHERE")
				.append(" level = ")
				.append(level)
				.append(";").toString();
		
		//Debug
//		System.out.println(sql);
		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();

			// rs에 더이상 값이 없으면 '업데이트 예정입니다.' 문구를 출력함.
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("업데이트 예정입니다.\n");
			}
			
			int bookNo1 = 0, pageNo1 = 0;
			String title1 = null, author1 = null, country1 = null;

			// rsa에 rs.next()를 넣어 위의 if문과 겹치지 않도록 함. 값 하나가 잘려서 나오지 않는 문제 해결!
			while (rsa) {
				bookNo1 = rs.getInt("bookNo");
				title1 = rs.getString("title");
				author1 = rs.getString("author");
				country1 = rs.getString("country");
				pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t 작가 : " + author1 + ", 나라 : " + country1 + ", 페이지 수 : " + pageNo1);

				// rsa에 rs.next()값을 넣어줘서 계속 갱신이 되도록 함.
				rsa = rs.next();
			}
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs.");
		}
		
	}
	
	public void setOften(int often) {
		this.often = often;
	}
	public int getOften() {
		return often;
	}

}

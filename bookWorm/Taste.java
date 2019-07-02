package bookWorm;

import java.util.*;
import java.sql.*;

public class Taste {
	Scanner sc = new Scanner(System.in);
	
	private final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/pj_java";
	private final String USER_NAME = "root";
	private final String PASSWORD = "mirim2";
	
	int age, country, genre, a, c;
	String author, rGenre, rCountry, sql;

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	// 취향을 설정하는 함수.
	public void selecTaste() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			
			// 장르 설정하는 함수를 불러옴.
			selectGenre();
			
			System.out.println("\n국가를 입력하시겠습니까? (1: Yes, 2: No)");
			c = Integer.parseInt(sc.nextLine());
			switch (c) {
			// 국가 설정하는 함수를 불러옴.
				case 1: selectCountry();
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

	// 장르를 설정하는 함수
	public void selectGenre() {
		try {
			System.out.println("장르 : 문학(1), 장편소설(2), 심리학(3), 판타지(4), 공학(5), 현대문학(6)");
			genre = Integer.parseInt(sc.nextLine());
			
			if (genre >0 && genre <7) {
				switch (genre) {
					case 1:	rGenre = "문학";
					break;
					case 2: rGenre = "장편소설";
					break;
					case 3: rGenre = "심리학";
					break;
					case 4: rGenre = "판타지";
					break;
					case 5: rGenre = "공학";
					break;
					case 6: rGenre = "현대문학";
					break;
					default: rGenre = "문학";
					break;
				}
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}
			
			StringBuilder sb = new StringBuilder();
			sql = sb.append("SELECT * FROM booklist WHERE")
					.append(" genre = '")
					.append(rGenre)
					.append("';").toString();
			
			//Debug
//			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int bookNo1 = 0, pageNo1 = 0;
			String title1 = null, author1 = null, country1 = null;
			
			// rs에 더이상 값이 없으면 '업데이트 예정입니다.' 문구를 출력함. 감사합니당 ~!!!
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("업데이트 예정입니다.\n");
			}
			
			System.out.println("\n[책 리스트]\n");
			
			// rsa에 rs.next()를 넣어 위의 if문과 겹치지 않도록 함. 값 하나가 잘려서 나오지 않는 문제 해결! 선생님은 천재세요..
			while (rsa) {
				bookNo1 = rs.getInt("bookNo");
				title1 = rs.getString("title");
				author1 = rs.getString("author");
				country1 = rs.getString("country");
				pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t 작가 : " + author1 + ", 나라 : " + country1 + ", 페이지 수 : " + pageNo1 + "\n");

				// rsa에 rs.next()값을 넣어줘서 계속 갱신이 되도록 함.
				rsa = rs.next();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs");
		}
	}
	
	// 국가를 선택하는 함수
	public void selectCountry() {
		try {

			System.out.println("\n국가 : 한국(1), 프랑스(2), 미국(3), 호주(4), 일본(5)");
			country = Integer.parseInt(sc.nextLine());
			
			if (country >0 && country <6) {
				switch (country) {
					case 1:	rCountry = "한국";
					break;
					case 2: rCountry = "프랑스";
					break;
					case 3: rCountry = "미국";
					break;
					case 4: rCountry = "호주";
					break;
					case 5: rCountry = "일본";
					break;
					default: rCountry = "한국";
					break;
				}
			} else {
				System.out.println("잘못 입력하셨습니다.");
			}

			// 위의 함수에서 선택한 장르와 이 함수에서 선택한 국가가 겹치는 책만 출력함.
			StringBuilder sb = new StringBuilder();
			sql = sb.append("SELECT * FROM booklist WHERE")
					.append(" country = '")
					.append(rCountry)
					.append("' and genre = '")
					.append(rGenre)
					.append("';").toString();
			
			//Debug
//			System.out.println(sql);
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			int bookNo1 = 0, pageNo1 = 0;
			String title1 = null, author1 = null, genre1 = null;
			
			// rs에 더이상 값이 없으면 '업데이트 예정입니다.' 문구를 출력함.
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("업데이트 예정입니다.\n");
			}
			
			// rsa에 rs.next()를 넣어 위의 if문과 겹치지 않도록 함. 값 하나가 잘려서 나오지 않는 문제 해결!
			while (rsa) {
				bookNo1 = rs.getInt("bookNo");
				title1 = rs.getString("title");
				author1 = rs.getString("author");
				genre1 = rs.getString("genre");
				pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t 작가 : " + author1 + ", 장르 : " + genre1 + ", 페이지 수 : " + pageNo1 + "\n");
				
				// rsa에 rs.next()값을 넣어줘서 계속 갱신이 되도록 함.
				rsa = rs.next();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs.");
		}
		
	}
}

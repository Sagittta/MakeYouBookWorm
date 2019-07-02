package bookWorm;

import java.sql.*;
import java.util.*;

public class Star {
	Scanner sc = new Scanner(System.in);

	private final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/pj_java";
	private final String USER_NAME = "root";
	private final String PASSWORD = "mirim2";
	
	int star;
	String text;
	
	//별점 주는 함수. 데이터베이스에 별점 주는 개수를 집어넣고, 평균을 구할 수 있도록 함.
	public void giveStar() {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String str = "";
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			
			System.out.print("서비스에 만족도에 따라 별의 개수를 입력해주세요.(1~5) : ");
			star = Integer.parseInt(sc.nextLine());
			if (star < 6 && star > 0) {
				for (int i = 0; i < star; i++)
					System.out.print("★");
				System.out.println("");
			} else {
				System.out.println("1 ~ 5로 다시 입력해 주세요.");
				star = Integer.parseInt(sc.nextLine());
				if (star < 6 && star > 0) {
					for (int i = 0; i < star; i++)
						System.out.print("★");
					System.out.println("");
				}
				else	System.out.println("프로그램이 종료됩니다. 다시 실행해주세요.");
			}
				
			if (star < 3) {
				System.out.println("감사합니다.");
				// 보완할 점 입력하는 함수 불러옴.
				writeMore();
			} else	System.out.println("감사합니다. 더 열심히 하겠습니다 !!");
			
			
			String sql;
			sql = "INSERT INTO star VALUES(?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			// int형인 star를 String형 star1로 바꿔줌
			String star1 = "" + star;
			pstmt.setString(1, star1);
			pstmt.setString(2, text);
			pstmt.executeUpdate();
			
			sql = "SELECT avg(count) FROM star";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.println("\n★ 별점 평균 ★");
			// 별점 평균을 내줌.
			if (rs.next()) {
				String avg = rs.getString("avg(count)");
				System.out.println(avg.substring(0, 3) + "\n");
			}
			
			pstmt.close();
			conn.close();
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.print("Error Occurs");
		}
		
	}
	
	//보완할 점을 입력할 수 있도록 하는 함수.
	public void writeMore() {
		System.out.println("더 나은 프로그램을 위해 보완할 점을 입력해주세요. (입력 : 1, 종료 : 0)");
		int a = Integer.parseInt(sc.nextLine());
		switch (a) {
		case 0 : 
			System.out.println("감사합니다.");
			break;
		case 1 : 
			text = sc.nextLine();
			System.out.println("감사합니다. 열심히 보완하겠습니다.");
			break;
		default :
			System.out.println("프로그램 종료합니다. 감사합니다.");
			break;
		}
	}

}

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
	
	// ������ �����ϴ� �Լ�.
	public void selecTaste() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			
			// �帣 �����ϴ� �Լ��� �ҷ���.
			selectGenre();
			
			System.out.println("\n������ �Է��Ͻðڽ��ϱ�? (1: Yes, 2: No)");
			c = Integer.parseInt(sc.nextLine());
			switch (c) {
			// ���� �����ϴ� �Լ��� �ҷ���.
				case 1: selectCountry();
				break;
				case 2: System.out.println("�˰ڽ��ϴ�.");
				break;
				default: System.out.println("�߸� �Է��ϼ̽��ϴ�.");
				break;
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs.");
		}
	}

	// �帣�� �����ϴ� �Լ�
	public void selectGenre() {
		try {
			System.out.println("�帣 : ����(1), ����Ҽ�(2), �ɸ���(3), ��Ÿ��(4), ����(5), ���빮��(6)");
			genre = Integer.parseInt(sc.nextLine());
			
			if (genre >0 && genre <7) {
				switch (genre) {
					case 1:	rGenre = "����";
					break;
					case 2: rGenre = "����Ҽ�";
					break;
					case 3: rGenre = "�ɸ���";
					break;
					case 4: rGenre = "��Ÿ��";
					break;
					case 5: rGenre = "����";
					break;
					case 6: rGenre = "���빮��";
					break;
					default: rGenre = "����";
					break;
				}
			} else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
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
			
			// rs�� ���̻� ���� ������ '������Ʈ �����Դϴ�.' ������ �����. �����մϴ� ~!!!
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("������Ʈ �����Դϴ�.\n");
			}
			
			System.out.println("\n[å ����Ʈ]\n");
			
			// rsa�� rs.next()�� �־� ���� if���� ��ġ�� �ʵ��� ��. �� �ϳ��� �߷��� ������ �ʴ� ���� �ذ�! �������� õ�缼��..
			while (rsa) {
				bookNo1 = rs.getInt("bookNo");
				title1 = rs.getString("title");
				author1 = rs.getString("author");
				country1 = rs.getString("country");
				pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t �۰� : " + author1 + ", ���� : " + country1 + ", ������ �� : " + pageNo1 + "\n");

				// rsa�� rs.next()���� �־��༭ ��� ������ �ǵ��� ��.
				rsa = rs.next();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs");
		}
	}
	
	// ������ �����ϴ� �Լ�
	public void selectCountry() {
		try {

			System.out.println("\n���� : �ѱ�(1), ������(2), �̱�(3), ȣ��(4), �Ϻ�(5)");
			country = Integer.parseInt(sc.nextLine());
			
			if (country >0 && country <6) {
				switch (country) {
					case 1:	rCountry = "�ѱ�";
					break;
					case 2: rCountry = "������";
					break;
					case 3: rCountry = "�̱�";
					break;
					case 4: rCountry = "ȣ��";
					break;
					case 5: rCountry = "�Ϻ�";
					break;
					default: rCountry = "�ѱ�";
					break;
				}
			} else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}

			// ���� �Լ����� ������ �帣�� �� �Լ����� ������ ������ ��ġ�� å�� �����.
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
			
			// rs�� ���̻� ���� ������ '������Ʈ �����Դϴ�.' ������ �����.
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("������Ʈ �����Դϴ�.\n");
			}
			
			// rsa�� rs.next()�� �־� ���� if���� ��ġ�� �ʵ��� ��. �� �ϳ��� �߷��� ������ �ʴ� ���� �ذ�!
			while (rsa) {
				bookNo1 = rs.getInt("bookNo");
				title1 = rs.getString("title");
				author1 = rs.getString("author");
				genre1 = rs.getString("genre");
				pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t �۰� : " + author1 + ", �帣 : " + genre1 + ", ������ �� : " + pageNo1 + "\n");
				
				// rsa�� rs.next()���� �־��༭ ��� ������ �ǵ��� ��.
				rsa = rs.next();
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs.");
		}
		
	}
}

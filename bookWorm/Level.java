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
	
	// å ���� �����ϴ� �Լ�
	public void selectLevel() {
		try {
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			
			selectAge();
			
			System.out.println("�󵵼��� �Է��Ͻðڽ��ϱ� ? (1: Yes, 2: No)");
			int o = Integer.parseInt(sc.nextLine());
			switch (o) {
				// �󵵼� �Է��ϴ� �Լ� �ҷ���.
				case 1: selectOften();
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

	// ���� �Է��ϴ� �Լ�
	public void selectAge() {
		System.out.println("���̸� �Է��ϼ��� : ");
		age = Integer.parseInt(sc.nextLine());
		
		// ���մ뿡 ���� ������ �޶������� ��.
		if (age > 0) {
			if (age < 8) {
				aLevel = 1;
				System.out.println("����� ������ 1�Դϴ�.");
			} else if (age < 14) {
				aLevel = 2;
				System.out.println("����� ������ 2�Դϴ�.");
			} else if (age < 20) {
				aLevel = 3;
				System.out.println("����� ������ 3�Դϴ�.");
			} else if (age < 40) {
				aLevel = 5;
				System.out.println("����� ������ 5�Դϴ�.");
			} else {
				aLevel = 4;
				System.out.println("����� ������ 4�Դϴ�.");
			}
		} else {
			System.out.println("�߸� �Է��ϼ̽��ϴ�.");
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

			// rs�� ���̻� ���� ������ '������Ʈ �����Դϴ�.' ������ �����.
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("������Ʈ �����Դϴ�.\n");
			}

			// rsa�� rs.next()�� �־� ���� if���� ��ġ�� �ʵ��� ��. �� �ϳ��� �߷��� ������ �ʴ� ���� �ذ�!
			while (rsa) {
				int bookNo1 = rs.getInt("bookNo");
				String title1 = rs.getString("title");
				String author1 = rs.getString("author");
				String country1 = rs.getString("country");
				int pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t�۰� : " + author1 + ", ���� : " + country1 + ", ������ �� : " + pageNo1);

				// rsa�� rs.next()���� �־��༭ ��� ������ �ǵ��� ��.
				rsa = rs.next();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs");
		}
		
	}
	
	public void selectOften() {
		System.out.println("å �д� �󵵼��� �Է��ϼ��� (2�� ����) : ");
		often = Integer.parseInt(sc.nextLine());
		
		// 2�� ������ ���缭 å �д� �󵵼��� ���� ������ �޶������� ��.
		if (often > 0 && often < 15) {
			if (often < 2) {
				oLevel = 1;
				System.out.println("����� ������ 1�Դϴ�.");
			} else if (often < 5) {
				oLevel = 2;
				System.out.println("����� ������ 2�Դϴ�.");
			} else if (often < 8) {
				oLevel = 3;
				System.out.println("����� ������ 3�Դϴ�.");
			} else if (often < 11) {
				oLevel = 4;
				System.out.println("����� ������ 4�Դϴ�.");
			} else if (often < 15) {
				oLevel = 5;
				System.out.println("����� ������ 5�Դϴ�.");
			} else {
				System.out.println("�߸� �Է��ϼ̽��ϴ�.");
			}
		} else {
			System.out.println("�߸� �Է��ϼ̽��ϴ�.");
		}
		
		// ���̿� �󵵼� ������ ��ģ �� ��� ���� ������ ����
		level = Math.round((aLevel + oLevel) / 2);
		
		System.out.println("\n���շ��� : " + level + "\n");
		
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

			// rs�� ���̻� ���� ������ '������Ʈ �����Դϴ�.' ������ �����.
			boolean rsa = rs.next();
			if (rsa == false) {
				System.out.println("������Ʈ �����Դϴ�.\n");
			}
			
			int bookNo1 = 0, pageNo1 = 0;
			String title1 = null, author1 = null, country1 = null;

			// rsa�� rs.next()�� �־� ���� if���� ��ġ�� �ʵ��� ��. �� �ϳ��� �߷��� ������ �ʴ� ���� �ذ�!
			while (rsa) {
				bookNo1 = rs.getInt("bookNo");
				title1 = rs.getString("title");
				author1 = rs.getString("author");
				country1 = rs.getString("country");
				pageNo1 = rs.getInt("pageNo");
				
				System.out.println(bookNo1 + ". " + title1 + ",\n\t �۰� : " + author1 + ", ���� : " + country1 + ", ������ �� : " + pageNo1);

				// rsa�� rs.next()���� �־��༭ ��� ������ �ǵ��� ��.
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

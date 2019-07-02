package bookWorm;

import java.util.*;
import java.sql.*;

public class Alarm {
	Scanner sc = new Scanner(System.in);
	
	private final String JDBC_DRIVER = "org.gjt.mm.mysql.Driver";
	private final String DB_URL = "jdbc:mysql://localhost:3306/pj_java";
	private final String USER_NAME = "root";
	private final String PASSWORD = "mirim2";
	
	String textAlarm;
	int hour, minute, time;
	

	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	
	//알람 시간 설정하는 함수. 올바르지 않은 시간 입력 시 한 번 더 입력할 수 있도록 함.
	public void setAlarm() {
		
		try {
			
			Class.forName(JDBC_DRIVER);
			conn = DriverManager.getConnection(DB_URL, USER_NAME, PASSWORD);
			
			System.out.print("Hour : ");
			time = Integer.parseInt(sc.nextLine());
			if (time > 0 && time < 25) {
				setHour(time);
			} else {
				System.out.println("1 ~ 24로 다시 입력해주세요.");
				time = Integer.parseInt(sc.nextLine());
				if (time > 0 && time < 25) {
					setHour(time);
				}
				else	System.out.println("프로그램이 종료됩니다.");
			}
			
			System.out.print("Minute : ");
			time = Integer.parseInt(sc.nextLine());
			if (time > -1 && time < 60)	{
				setMinute(time);
			} else {
				System.out.println("0 ~ 59로 다시 입력해주세요.");
				time = Integer.parseInt(sc.nextLine());
				if (time > 0 && time < 25) {
					setMinute(time);
				} else	System.out.println("프로그램이 종료됩니다.");
			}
			
			// 입력한 시간과 분을 데이터베이스에 입력.
			String sql;
			sql = "INSERT INTO alarm VALUES(?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			String hour1 = "" + getHour();
			String minute2 = "" + getMinute();
			pstmt.setString(1, hour1);
			pstmt.setString(2, minute2);
			pstmt.executeUpdate();
			
			// 알람 기록 보여주는 함수를 불러옴.
			showAlarm();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs");
		}
	}
	
	//현재까지 기록된 알람 기록을 보여줌.
	public void showAlarm() {
		try {
			//알람 데이터 베이스에 들어있는 시간과 분을 불러옴.
			String sql = "SELECT hour, minute FROM alarm";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			System.out.print("\n알람 기록을 보여드릴까요? (1 : 네, 2 : 아니요) :");
			int a = sc.nextInt();
			if (a == 1) {
				// rs 에 값이 있을 경우에 불러옴.
				if (rs != null) {
					while (rs.next()) {
						String showHour = rs.getString("hour");
						String showMinute = rs.getString("minute");
						String showText = showHour + "시 " + showMinute + "분에 알람이 있습니다.";
						System.out.println(showText);
					}
				}
				
				// 닫아줌.
				pstmt.close();
				conn.close();
				
			} else 	System.out.println("\n" + toString());
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error Occurs.");
		}
	}
	
	//알람이 울릴 시간을 문자열로 출력함.
	public String toString() {
		return ("알람이 " + getHour() + "시 " + getMinute() + "분에 울립니다.");
	}

	// 시간과 분을 설정할 때 사용하는 setter와 getter 함수
	public void setHour(int hour) {
		this.hour = hour;
	}
	public int getHour() {
		return hour;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}
	public int getMinute() {
		return minute;
	}
	
}

package bookWorm;

import java.util.*;

public class Alarm2 {
	Scanner sc = new Scanner(System.in);
	
	String textAlarm;
	int hour, minute, time;
	
	public void setAlarm() {
		System.out.print("Hour : ");
		time = sc.nextInt();
		if (time > 0 && time < 25)	setHour(time);
		else {
			System.out.println("1 ~ 24로 다시 입력해주세요.");
			time = sc.nextInt();
			if (time > 0 && time < 25)	setHour(time);
			else	System.out.println("프로그램이 종료됩니다.");
		}
		System.out.print("Minute : ");
		time = sc.nextInt();
		if (time > -1 && time < 60)	setMinute(time);
		else {
			System.out.println("0 ~ 59로 다시 입력해주세요.");
			time = sc.nextInt();
			if (time > 0 && time < 25)	setMinute(time);
			else	System.out.println("프로그램이 종료됩니다.");
		}
	}
	
	public String toString() {
		return ("알람이 " + getHour() + "시 " + getMinute() + "분에 울립니다.");
	}

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

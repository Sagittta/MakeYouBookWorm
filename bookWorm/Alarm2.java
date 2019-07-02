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
			System.out.println("1 ~ 24�� �ٽ� �Է����ּ���.");
			time = sc.nextInt();
			if (time > 0 && time < 25)	setHour(time);
			else	System.out.println("���α׷��� ����˴ϴ�.");
		}
		System.out.print("Minute : ");
		time = sc.nextInt();
		if (time > -1 && time < 60)	setMinute(time);
		else {
			System.out.println("0 ~ 59�� �ٽ� �Է����ּ���.");
			time = sc.nextInt();
			if (time > 0 && time < 25)	setMinute(time);
			else	System.out.println("���α׷��� ����˴ϴ�.");
		}
	}
	
	public String toString() {
		return ("�˶��� " + getHour() + "�� " + getMinute() + "�п� �︳�ϴ�.");
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

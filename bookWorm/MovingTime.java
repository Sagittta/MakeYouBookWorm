package bookWorm;

import java.util.*;

public class MovingTime {
	Scanner sc = new Scanner(System.in);
	Random r = new Random();
	
	int time;
	String text[] = {"å�� �Բ� �� ������ ������.", "å�� ì�⼼��.", "å�� �Բ���� ������ �� �� �־��.", "å�� �ʿ��ؿ�.", "����ִ� å�����鼭 ������ �̿��ؿ�.", "�ð��� ��½ ���� å �б�", 
			"ååå ååå ���� ��� �Ҹ� å�� ��������� �Ҹ�", "�ϺϺϺϺϺ� ���� ���� �Ҹ� å ���������� �غ��ϴ� �Ҹ�", "��å��å��åå ���ϴ� �Ҹ� å�� �аڴٴ� �Ҹ�", "å���� ���� ����־��? ��������"};
	
	//�̵��ð��� �����ϴ� �Լ�.
	public void setTime() {
		System.out.println("���� �̵� �ð��� �Է��ϼ���.(����:��) : ");
		time = Integer.parseInt(sc.nextLine());
		
		int randomText = r.nextInt(text.length);

		// �������� text[]�� �ִ� ���� ��µǵ��� ��.
		if (time > 40) 	System.out.println(text[randomText]);
		else if (time < 0)	System.out.println("�߸��� �Է��Դϴ�.");
		else System.out.println("���� å�̳� ������ ì��� ���� ��õ�ؿ�.");
	}
	
}

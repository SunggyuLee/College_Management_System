package Manage;

import java.util.List;
import java.util.Scanner;

import Database.DAOGrade;
import Database.Grade;

public class ManageGrade {
	public static boolean endGrading = false;
	String classIdNum;
	String userId;
	Float grade;
	Scanner scan = new Scanner(System.in);
	DAOGrade daog = DAOGrade.sharedInstance();

	public void run() {
		boolean run = true;
		while(run) {
			System.out.println();System.out.println();
			System.out.println("������ ������ �����ϼ���.");
			int chooseWork = this.inputInt("1.���� ���  2.���� ����  3.�л��� ����ǥ ��ȸ  7.���� ");

			switch (chooseWork) {
			case 1: // ���� ���
				if(endGrading)
					System.out.println("����ó���� �̹� �������ϴ�.");
				else {
					System.out.println("���� ����� �����մϴ�.");
					this.giveGrade();
				}
				break;

			case 2: // ���� ����
				if(endGrading)
					System.out.println("����ó���� �̹� �������ϴ�.");
				else {
					System.out.println("���� ������ �����մϴ�.");
					this.fixGrade();
				}
				break;
				
			case 3: // �л��� ����ǥ ��ȸ
				System.out.println("�л��� ����ǥ ��ȸ�� �����մϴ�.");
				this.checkReport();
				break;
				
			case 7: // ����
				System.out.println("�����մϴ�.");
				run = false;
				break;
			default:
			}
		}
	}

	private void checkReport() {
	}

	private void fixGrade() {
	}

	private void giveGrade() {
	}

	private int inputInt(String string) {
		System.out.print(string);
		return Integer.parseInt(scan.nextLine());
	}

	private String inputString(String string) {
		System.out.print(string);
		return scan.nextLine();
	}
}
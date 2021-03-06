package Manage;

import java.util.List;
import java.util.Scanner;

import Database.AllSubject;
import Database.DAOAllSubject;
import Database.User;

public class ManageAllSubject {
	String subjectName;
	String profName;
	Integer credit;
	Scanner scan = new Scanner(System.in);
	DAOAllSubject daoa = DAOAllSubject.sharedInstance();
	AllSubject allSubject = new AllSubject();

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.교과목 등록  2.교과목 수정  3.교과목 삭제  4.종료 ");

			switch (chooseWork) {
			case 1: // 교과목 등록
				System.out.println("> 교과목 등록을 시작합니다.");
				this.enrollAllSubject();
				break;

			case 2: // 교과목 수정
				System.out.println("> 교과목 수정을 시작합니다.");
				this.modifyAllSubject();
				break;

			case 3: // 교과목 삭제
				System.out.println("> 교과목 삭제를 시작합니다.");
				this.deleteAllSubject();
				break;

			case 4: // 종료
				System.out.println("> 종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void deleteAllSubject() {
		List<AllSubject> as_list = daoa.getAllSubjectList();

		if (as_list.size() == 0) {
			System.out.println("> 등록된 교과목이 없습니다.");
			this.run();
			return;
		}

		System.out.println("-------------------------- 교과목 목록 --------------------------");
		int count = 1;
		for (AllSubject u : as_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getSubjectName(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String subjectName = this.inputString("> 삭제할 과목명 (ex.algorithm) : ");
		allSubject.setSubjectName(subjectName);

		boolean r = daoa.deleteAllSubject(allSubject);

		if (r)
			System.out.println("> 교과목 삭제가 완료되었습니다.");
		else
			System.out.println("> 교과목 삭제를 실패하였습니다.");

	}

	private void modifyAllSubject() {
		List<AllSubject> as_list = daoa.getAllSubjectList();

		if (as_list.size() == 0) {
			System.out.println("> 등록된 교과목이 없습니다.");
			this.run();
			return;
		}

		System.out.println("-------------------------- 교과목 목록 --------------------------");
		int count = 1;
		for (AllSubject u : as_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getSubjectName(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String subjectName = this.inputString("> 수정할 과목명 (ex.algorithm) : ");
		String profName = this.inputString("> 담당교수 (ex.kimseongje) : ");
		Integer credit = this.inputInt("> 학점 (ex.3) : ");

		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);

		boolean r = daoa.modifyAllSubject(allSubject);

		if (r)
			System.out.println("> 교과목 수정이 완료되었습니다.");
		else
			System.out.println("> 교과목 수정을 실패하였습니다.");

	}

	private void enrollAllSubject() {
		List<AllSubject> as_list = daoa.getAllSubjectList();

		System.out.println("-------------------------- 교과목 목록 --------------------------");
		int count = 1;
		for (AllSubject u : as_list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getSubjectName(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String subjectName = this.inputString("> 과목명 (ex.algorithm) : ");
		String profName = this.inputString("> 담당교수 (ex.kimseongje) : ");
		Integer credit = this.inputInt("> 학점 (ex.3) : ");

		allSubject.setSubjectName(subjectName);
		allSubject.setProfName(profName);
		allSubject.setCredit(credit);

		boolean r = daoa.InsertAllSubject(allSubject);

		if (r)
			System.out.println("> 교과목 등록이 완료되었습니다.");
		else
			System.out.println("> 교과목 등록을 실패하였습니다.");

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
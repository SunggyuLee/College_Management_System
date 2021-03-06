package Manage;

import java.util.List;
import java.util.Scanner;

import Database.AllSubject;
import Database.DAOAllSubject;
import Database.DAOSubject;
import Database.Subject;

public class ManageSubject {
	String classIdNum;
	String subjectName;
	String classNum;
	String classTime;
	String classRoom;
	String syllabus;
	Integer availNum;
	Scanner scan = new Scanner(System.in);
	DAOSubject daos = DAOSubject.sharedInstance();
	DAOAllSubject daoas = DAOAllSubject.sharedInstance();
	Subject subject = new Subject();

	public void run() {
		boolean run = true;
		while (run) {
			System.out.println();
			System.out.println();
			System.out.println("실행할 업무를 선택하세요.");
			int chooseWork = this.inputInt("1.개설 과목 등록  2.개설 과목 수정  3.개설 과목 삭제  4.종료 ");

			switch (chooseWork) {
			case 1: // 개설 과목 등록
				System.out.println("> 개설 과목을 등록합니다.");
				this.enrollSubject();
				break;

			case 2: // 개설 과목 수정
				System.out.println("> 개설 과목을 수정합니다.");
				this.modifySubject();
				break;

			case 3: // 개설 과목 삭제
				System.out.println("> 개설 과목을 삭제합니다.");
				this.deleteSubject();
				break;

			case 4: // 종료
				System.out.println("> 종료합니다.");
				run = false;
				break;
			default:
			}
		}
	}

	private void deleteSubject() {
		List<Subject> list = daos.getSubjectList();

		if (list.size() == 0) {
			System.out.println("> 등록된 개설 과목이 없습니다.");
			this.run();
			return;
		}

		System.out.println("-------------------------- 개설 과목 목록 --------------------------");
		int count = 1;
		for (Subject u : list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getClassIdNum(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String classIdNum = this.inputString("> 삭제할 학수 번호 (ex.algorithm-1) : ");
		subject.setClassIdNum(classIdNum);

		boolean r = daos.deleteSubject(subject);

		if (r)
			System.out.println("> 교과목 삭제가 완료되었습니다.");
		else
			System.out.println("> 교과목 삭제를 실패하였습니다.");
	}

	private void modifySubject() {
		List<Subject> list = daos.getSubjectList();

		if (list.size() == 0) {
			System.out.println("> 등록된 개설 과목이 없습니다.");
			this.run();
			return;
		}

		System.out.println("-------------------------- 개설 과목 목록 --------------------------");
		int count = 1;
		for (Subject u : list) {
			if (count % 6 == 0) {
				System.out.println();
			}
			System.out.print(String.format("%s%3s", "[" + count + "] " + u.getClassIdNum(), " "));
			count++;
		}
		System.out.println();
		System.out.println();

		String classIdNum = this.inputString("> 학수 번호 (ex.algorithm-1) : ");
		String classTime = this.inputString("> 강의시간 (ex.13-15) : ");
		String classRoom = this.inputString("> 강의실 (ex.101) : ");
		String syllabus = this.inputString("> 강의계획서 : ");
		Integer availNum = this.inputInt("> 최대 수강 인원 (ex.30) : ");

		if (!classTime.contains("-")) {
			System.out.println("> 강의시간 입력 형식을 맞춰주세요.");
			this.modifySubject();
			return;
		}

		subject.setClassIdNum(classIdNum);
		subject.setClassTime(classTime);
		subject.setClassRoom(classRoom);
		subject.setSyllabus(syllabus);
		subject.setAvailNum(availNum);

		List<Subject> s_list = daos.checkDuplicationForclass(subject);

		boolean r1 = true;
		for (Subject s : s_list) {
			String time = s.getClassTime();
			int start_time = Integer.parseInt(time.split("-")[0]);
			int end_time = Integer.parseInt(time.split("-")[1]);

			int cur_start = Integer.parseInt(classTime.split("-")[0]);
			int cur_end = Integer.parseInt(classTime.split("-")[1]);

			if (start_time != cur_start) {
				if (start_time < cur_start) {
					if (end_time > cur_start) {
						r1 = false;
					}
				} else {
					if (start_time < cur_end) {
						r1 = false;
					}
				}
			} else {
				r1 = false;
			}
		}

		if (r1) {
			boolean r2 = daos.modifySubject(subject);

			if (r2)
				System.out.println("> 교과목 수정이 완료되었습니다.");
			else
				System.out.println("> 교과목 수정을 실패하였습니다.");
		} else {
			System.out.println("> 해당 강의시간과 강의실에 이미 다른 수업이 있습니다.");
		}

	}

	private void enrollSubject() {
		List<AllSubject> as_list = daoas.getAllSubjectList();

		System.out.println("-------------------------- 과목 목록 --------------------------");
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
		String classNum = this.inputString("> 분반 (ex.1) : ");
		String classTime = this.inputString("> 강의시간 (ex.13-15) : ");
		String classRoom = this.inputString("> 강의실 (ex.101) : ");
		String syllabus = this.inputString("> 강의계획서 : ");
		Integer availNum = this.inputInt("> 최대 수강 인원 (ex.30) : ");

		if (!classTime.contains("-")) {
			System.out.println("> 강의시간 입력 형식을 맞춰주세요.");
			this.enrollSubject();
			return;
		}

		subject.setClassIdNum(subjectName + "-" + classNum);
		subject.setSubjectName(subjectName);
		subject.setClassNum(classNum);
		subject.setClassTime(classTime);
		subject.setClassRoom(classRoom);
		subject.setSyllabus(syllabus);
		subject.setAvailNum(availNum);

		List<Subject> s_list = daos.checkDuplicationForclass(subject);

		boolean r1 = true;
		for (Subject s : s_list) {
			String time = s.getClassTime();
			int start_time = Integer.parseInt(time.split("-")[0]);
			int end_time = Integer.parseInt(time.split("-")[1]);

			int cur_start = Integer.parseInt(classTime.split("-")[0]);
			int cur_end = Integer.parseInt(classTime.split("-")[1]);

			if (start_time != cur_start) {
				if (start_time < cur_start) {
					if (end_time > cur_start) {
						r1 = false;
					}
				} else {
					if (start_time < cur_end) {
						r1 = false;
					}
				}
			} else {
				r1 = false;
			}
		}

		if (r1) {
			boolean r2 = daos.InsertSubject(subject);

			if (r2)
				System.out.println("> 교과목 등록이 완료되었습니다.");
			else
				System.out.println("> 교과목 등록을 실패하였습니다.");
		} else {
			System.out.println("> 해당 강의시간과 강의실에 이미 다른 수업이 있습니다.");
		}

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

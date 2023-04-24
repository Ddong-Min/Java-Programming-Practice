import java.util.Scanner;	
import java.util.Random;

class Room{
	boolean States;
	int Capacity;
}
//CONSTRUCTOR 써야행
public class Hotel {
	public void init(Room[] r){
		Random random = new Random();
		for(int i=0; i<10; i++) {
			r[i].States = false;
			r[i].Capacity = random.nextInt(4)+1;
			System.out.print(r[i].Capacity);
			System.out.print(" ");
		}
		System.out.println();
	}
	public void checkin(int n, int j, Room[] r) {
		/*
		if(n<1 || n> r.length) {   //n 의 값이 잘못들어온 경우애는 오류메세지를 어떻게 출력해야하나
			
		}
		*/
		
		if(r[n-1].States) {
			System.out.println("Check in error : already occupied");
		}
		else if(r[n-1].Capacity < j){
			System.out.println("Check in error : capacity exceed");
		}
		else {
			System.out.println("Check in Success");
		}
	}
	
	public void checkout(int n, Room[] r) {
		/*
		if(n<1 || n> r.length) {   //n 의 값이 잘못들어온 경우애는 오류메세지를 어떻게 출력해야하나
			
		}
		*/
		
		if(r[n-1].States) {
			r[n-1].States = false;
			System.out.println("Check out Success"); }
		else System.out.println("Check out error : already checked out");
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		int select, roomNum, visitor;
		Room[] room;
		room = new Room[10];
		for(int i=0; i<10; i++) {
			room[i] = new Room();
		}
		Hotel hotel = new Hotel();
		System.out.println("Each Room's Capacity");
		hotel.init(room);
		
		while(true) {
		System.out.println("Enter number (1 : check in), (2 : check out), (3 : Finish)");
		select = sc.nextInt();
		System.out.println("Enter room number");
		roomNum = sc.nextInt();
		
		if(select == 1) {
			System.out.println("How many people?");
			visitor = sc.nextInt();
			hotel.checkin(roomNum, visitor, room);
		}
		else if (select == 2){
			hotel.checkout(roomNum, room);
			//borrow(int n, Book[] b) 이리해도 오케이  .java로 해도 오케이
		}
		else break;	
	}
	}

}

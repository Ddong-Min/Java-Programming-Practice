import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class DongminPark_2022311608_pa1 {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int seed = Integer.parseInt(args[0]);
		Deck deck = new Deck();
		deck.shuffle(seed);
		
		//your code
		int numPlayer = Integer.parseInt(args[1]);
		
		//player 세팅
		House house = new House(deck);
		Player player = new Player(deck);
		Computer[] computer = new Computer[numPlayer-1];
		for(int i=0; i<numPlayer-1; i++) {
			computer[i] = new Computer(deck);
		}
		
		//player정보 출력
		house.first_house_print();
		System.out.print("Player1: ");
		player.print();
		for(int i=0; i<numPlayer-1; i++) {
			System.out.print("Player"+(i+2)+": ");
			computer[i].print();
		}
		System.out.println();
		
		//게임 시작
		if(house.cardSum() != 21) {
		System.out.println("--- Player1 turn ---");
		player.gameStart(deck);
		for(int i=0; i< numPlayer-1; i++) {
			System.out.println("--- Player" + (i+2) + " turn ---");
			computer[i].gameStart(deck);
		}
		System.out.println();
		System.out.println("--- House turn ---");
		house.gameStart(deck);
		}
		
		/*승패 집계*/
		System.out.println("--- Game Results ---");
		System.out.print("House: ");
		house.print();
		
		player.gameResult(house);
		System.out.print("Player1: ");
		player.print();
		for(int i=0; i<numPlayer-1;i++) {
			computer[i].gameResult(house);
			System.out.print("Player"+(i+2)+": ");
			computer[i].print();
		}
	}
}
/*
 Card class : 카드에 대한 정보를 담고 있는 class입니다.
 
 (변수)
 value : 카드가 계산될때 가지는 값
 suit : c,h,d,s중 어떤 카드인지 결정하기 위한 변수입니다.
 
 (메소드)
 getValue() : value값을 return해주는 함수입니다.
 getSuit() : suit값을 return해주는 함수입니다.
 changeValue() : 만약 A카드의 값이 변할때 카드의 value값을 바꿔주는 함수입니다.
 */
class Card{
	private int value;
	private int suit;
	
	public Card() {}
	public Card(int theValue, int theSuit) {
		value = theValue;
		suit = theSuit;
	}
	
	public int getValue() {
		return value;
	}

	public int getSuit() {
		return suit;
	}
	
	public void changeValue(int value) {
		this.value = value;
	}
}

/*
 Deck class : 케임을 진행하기 위한 전체카드를 모아논 덱에 대한 정보를 갖고  있는 class 입니다.
 
 (변수)
 deck : 전체 덱에 대한 정보를 갖고 있는 Card형 array
 cardsUsed : 현재 덱에서 가장 위에있는 카드(즉, 다음에 뽑힐 카드)의 위치정보를 담고 있는 변수입니다.
 
 (constructor)
 Deck() : Deck형 변수를 만들어 constructor가 불리면 A,2,3....10,J,Q,K 순서대로 deck array에 값을 입력해줬습니다.
 
 (메소드)
 shuffle : deck에 있는 카드들을 랜덤으로 섞어주는 함수입니다.
 dealCard() : deck에서 새로운 카드를 뽑아주는 함수입니다.
*/
class Deck{
	private Card[] deck;
	private int cardsUsed;
	
	Deck(){
		deck = new Card[52];
		for(int i=0; i<4; i++) {
			deck[i] = new Card(11, i%4);
		}
		for(int i=4; i<36; i++) {
			deck[i] = new Card(i/4+1, i%4); //A부터 9까지는 suit가 0이면 c, 1이면 h, 2면 d, 3이면 s
		}
		for(int i=36; i<52; i++) {
			deck[i] = new Card(10, i-36); //10부터는 value가 값기때문에 suit%4가 0이면 c, 1이면 h, 2면 d, 3이면 s
		}
	}
	
	public void shuffle(int seed) {
		Random random = new Random(seed);
		for(int i = deck.length-1; i>0; i--) {
			int rand = (int)(random.nextInt(i+1));
			Card temp = deck[i];
			deck[i]= deck[rand];
			deck[rand]=temp;
		}
		cardsUsed=0;
	}
	public Card dealCard() {
		if(cardsUsed == deck.length)
			throw new IllegalStateException("No cards are left in the deck.");
		cardsUsed++;
		return deck[cardsUsed - 1];
	}
}

/*
Hand class : 게임에 참여하는 사람들(House, Player들)의 손안에 어떤 카드들이 뭐가있는지 class 입니다.

(변수)
userDeck : 게임에 참여하는 사람(이제부터 user라고 부르겠음)의 손안에 있는 카드들의 정보를 담는 arrayList
choice : user가 Hit을 선택했는지 Stand를 선택했는지 아니면 Bust상태인지                            ** 이거 변수명 고치기!
         그 정보를 담고있는 String변수 class이외에서도 접근이 가능하게 하기 위해 public으로 설정했습니다.

(constructor)
Hand() : 게임을 시작하면 각 user가 각각 2장의 카드를 받기 때문에 userDeck[0]과 userDeck[1]에 user가 받은 카드정보를 저장했습니다.

(메소드)
cardSum() : user가 보유하고있는 카드들의 합계를 알려주는 함수입니다. A값은 만약 전체 카드 합이 21이 안넘으면 11 21을 넘으면 1로 바뀌게 저장했습니다.
print() : card의 value값에 따라 A,1,2,...,10,J,Q,K 값을 출력 suit값에 따라 c,h,d,s 값을 출력합니다.
cardDrawuing() : user가 가지고 카드의 함에 따라 카드를 더 뽑거나 그만 뽑게 하는 함수입니다. 
                 (computer를 기준으로 함수를 작성했고 player와 house에서 override했습니다.
				 1. cardSum()이 14보다 작으면 카드를 하나 더 뽑고 Hit을 출력합니다. 만약 카드를 뽑은 후 
				    cardSum()이 21보다 작거나 같다면 Hit을 return하고 21보다 크다면 Bust를 return 합니다.
				 2. cardSum()이 17보다 크다면 Stand를 출력하고 Stand를 return합니다.
				 3. cardSum()이 14보다 크거나 같고 17보다 작거나 같다면 1/2확률로 Hit을 출력하고 1/2확률로 Stand를 출력하고 Stand를 return합니다.
				 4. 만약 3번에서 Hit을 출력했다면 card를 뽑고난 후 cardSum()이 21보다 작거나 같으면 Hit을 return하고 21보다 크면 Bust를 return합니다.
gameStart() : 헤당 user의 차례가 되면 게임이 시작되는 함수입니다. 실질적인 게임에 해당되는 함수입니다.
              Hand 클래스에서는 computer와 house에 맞춰서 함수가 짜여있고 player class에서는 player에 맞게 override 했습니다.
*/
class Hand{
	ArrayList<Card> userDeck;
	String choice;
	
	Hand(Deck deck){
		choice = "Hit";
		userDeck = new ArrayList<Card>();
		userDeck.add(deck.dealCard()); 
		userDeck.add(deck.dealCard()); // 두개의 card 뽑기
	}
	
	public int cardSum() {
		int sum =0;
		for(int i=0; i<userDeck.size(); i++) {
			sum += userDeck.get(i).getValue();
		}
		
		if(sum > 21) {
			for(int i=0; i<userDeck.size(); i++) {
				// 만약 gardSum이 21이 넘을때 A가 있다면 A의 값을 11에서 1로 변경
				if(userDeck.get(i).getValue()==11) {
					userDeck.get(i).changeValue(1);
					sum -= 10;
				}
			}
		}
		return sum;
	}
	
	public String cardDrawing(Deck deck) {
		if(cardSum()<14) {
			userDeck.add(deck.dealCard());
			System.out.println("Hit");
			if(cardSum()<=21) return "Hit";  //새로 카드를 뽑았는데 cardSum()이 21이하이면 Hit return
			else return "Bust";  //21초과이면 Bust return
		}
		
		else if(cardSum() > 17) {  //cardSum() 17초과이면 Stand
			System.out.println("Stand");
			return "Stand";
		}
		
		else {
			Random random = new Random();
			int is_hit = (int)(random.nextInt(2));
			if(is_hit==1) {
				userDeck.add(deck.dealCard());
				System.out.println("Hit");
				if(cardSum()<=21) return "Hit";
				else return "Bust";
				}
			else {
				System.out.println("Stand");
				return "Stand";
			}
		}
	}
	
	public void print() {
		for(int i=0; i<userDeck.size();i++) {
			//A출력
			if(userDeck.get(i).getValue() == 11 || userDeck.get(i).getValue() == 1) {
				System.out.print("A");
			}
			
			//2~10까지 출력
			else if(userDeck.get(i).getValue() <= 10 && userDeck.get(i).getSuit() <= 3) {
				System.out.print(userDeck.get(i).getValue());
			}
			
			//J,Q,K 출력
			else {
				if(userDeck.get(i).getSuit()/4 == 1) System.out.print("J");  //suit가 4부터 7가지 J
				else if(userDeck.get(i).getSuit()/4 == 2) System.out.print("Q");  //suit가 8부터 11까지 Q
				else System.out.print("K");  //suit가 12부터 15까지 K
			}
			
			if(userDeck.get(i).getSuit()%4 == 0) System.out.print("c");  //suit%4가 0이면 c
			else if(userDeck.get(i).getSuit()%4 == 1) System.out.print("h");  //suit%4가 1이면 h
			else if(userDeck.get(i).getSuit()%4 == 2) System.out.print("d");  //suit%4가 2이면 d
			else System.out.print("s");  //suit%4가 3이면 s
			if(i+1 != userDeck.size()) System.out.print(", ");
			else System.out.print(" ");
		}
		
		if(cardSum()<=21)System.out.println("("+ cardSum() + ")");
		else System.out.println("("+ cardSum() + ")"+" - Bust!");
	}

	public void gameStart(Deck deck) {
		while(true) {
			print();
			choice = cardDrawing(deck);
			if(choice.equals("Bust") || choice.equals("Stand")){
				print();
				break;
			}
		}
		System.out.println();
	}
}

/*
Computer class : Player2부터 나머지 플레이어들의 정보를 담고 있는 class입니다.

(constructor)
Computer() : 부모 constructor에 deck변수를 인자로 줍니다.

(메소드)
gameResult() : game의 결과를 출력해주는 함수입니다.
*/
class Computer extends Hand{
	Computer(Deck deck){
		super(deck);
	}
	
	
	public void gameResult(House house) {
		if(house.choice.equals("Bust")) {
			if(!choice.equals("Bust")) System.out.print("[Win]  ");
			else System.out.print("[Lose] ");
		}
		else {
			if(choice.equals("Bust")) System.out.print("[Lose] ");
			else if(cardSum() > house.cardSum()) System.out.print("[Win]  ");
			else if(cardSum() == house.cardSum()) System.out.print("[Draw] ");
			else System.out.print("[Lose] ");
		}
	}
}
/*
Player class : Player1의 정보를 담고 있는 class입니다.

(constructor)
Computer() : 부모 constructor에 deck변수를 인자로 줍니다.

(메소드)
cardDrawuing() : user가 가지고 카드의 함에 따라 카드를 더 뽑는 함수입니다.(override했습니다.)
				 1. 카드를 뽑고 난후 cardSum()이 21보다 작거나 같으면 Hit을 return합니다.
				 2. 카드를 뽑고 난후 cardSum()이 21보다 크다면 Bust return합니다.
gameStart() : player의 차례가 되면 게임을 시작하는 함수이니다.(override 했습니다.)
gameResult() : 게임결과를 출력해주는 함수입니다.
*/
class Player extends Hand{
	
	Player(Deck deck){
		super(deck);
	}
	
	public String cardDrawing(Deck deck) {
		userDeck.add(deck.dealCard());
		if(cardSum()<=21) { //card를 새로 뽑았는데 CardSum이 21이하이면 Hit return
			return "Hit";
		}
		else{
			return "Bust";  //card를 새로 뽑았는데 CardSum이 21초과면 Bust return
		}
	}
	
	public void gameStart(Deck deck) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			print();
			choice = sc.next();
			if(choice.equals("Hit")) {
				choice = cardDrawing(deck);
				if(choice.equals("Bust")){
					print();
					break;
				}
			}
			else {
				print();
				break;
			}
		}
		System.out.println();
		sc.close();
	}
	
	public void gameResult(House house) {
		if(house.choice.equals("Bust")) {
			if(!choice.equals("Bust")) System.out.print("[Win]  ");
			else System.out.print("[Lose] ");
		}
		else {
			if(choice.equals("Bust")) System.out.print("[Lose] ");
			else if(cardSum() > house.cardSum()) System.out.print("[Win]  ");
			else if(cardSum() == house.cardSum()) System.out.print("[Draw] ");
			else System.out.print("[Lose] ");
		}
	}

}

/*
House class : House의 정보를 담고 있는 class입니다.

(constructor)
House() : 부모 constructor에 deck변수를 인자로 줍니다.

(메소드)
cardDrawuing() : user가 가지고 카드의 함에 따라 카드를 더 뽑거나 그만 뽑게 하는 함수입니다. (override했습니다.)
				 1. cardSum()이 16보다 작으면 같으면 카드를 하나 더 뽑고 Hit을 출력합니다. 만약 카드를 뽑은 후 
				    cardSum()이 21보다 작거나 같다면 Hit을 return하고 21보다 크다면 Bust를 return 합니다.
				 2. cardSum()이 17보다 크거나 같다면 Stand를 출력하고 Stand를 return 합니다.
first_house_print() : 게임을 처음 시작할 때 하우스가 가지고 있는 카드의 정보를 출력하는 함수입니다. house의 첫번째 카드는 Hidden으로 표시합니다.
*/
class House extends Hand{
	
	House(Deck deck){
		super(deck);
	}
	
	public String cardDrawing(Deck deck) {
		//cardSum이 16이하면 한장 더 뽑기
		if(cardSum() <= 16) {	
			userDeck.add(deck.dealCard());
			System.out.println("Hit");  
			if(cardSum()<=21) return "Hit";  //card를 뽑은 후 cardSum이 21이하이면 Hit return
			else  return "Bust";  //card를 뽑은 후 cardSum이 21초과이면 Bust return
		}
		
		//cardSum이 17이상이면 멈추기
		else{  
			System.out.println("Stand");
			return "Stand";
		}
	}
	
	//game시작될때 출력 
	public void first_house_print() {
			System.out.print("House: HIDDEN, "); //house의 첫카드는 Hidden으로 표시
			if(userDeck.get(1).getValue() == 11 || userDeck.get(1).getValue() == 1) {
				System.out.print("A");
			}
			else if(userDeck.get(1).getValue() <= 10 && userDeck.get(1).getSuit() <= 3) {
				System.out.print(userDeck.get(1).getValue());
			}
			else {
				if(userDeck.get(1).getSuit()/4 == 1) System.out.print("J");
				else if(userDeck.get(1).getSuit()/4 == 2) System.out.print("Q");
				else System.out.print("K");
			}
			
			if(userDeck.get(1).getSuit()%4 == 0) System.out.println("c");
			else if(userDeck.get(1).getSuit()%4 == 1) System.out.println("h");
			else if(userDeck.get(1).getSuit()%4 == 2) System.out.println("d");
			else System.out.println("s");

		}
}

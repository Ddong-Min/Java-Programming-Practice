
public class Animal {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String name = "Arcs";
		float weight = (float)8.5565;
		String nameSlave = "SKKU";
		Cat cat = new Cat(name, weight, nameSlave);
		cat.getName();
		cat.setName("Arcs2");
		cat.getName();
		cat.getWeight();
		cat.setWeight((float)8.556);
		cat.getWeight();
		cat.getNameSlave();
		cat.setNameSlave("SNU");
		cat.getNameSlave();
		cat.bark();
		cat.getFood();
	}

}

abstract class Mammal extends Animal{
	public abstract void bark();
	public abstract void getFood(); 
}

abstract class Reptile extends Animal{
	public abstract void getFood();
}

class Cat extends Mammal{
	private String name;
	private float weight;
	private String nameSlave;
	
	Cat(String name, float weight, String nameSlave){
		this.name = name;
		this.weight = weight;
		this.nameSlave = nameSlave;
	}
	
	public void bark() { 
		System.out.println("Meow");
	}

	public void getFood() {
		System.out.println("Fish");
	}

	public void getName() {
		System.out.print("Name: ");
		System.out.println(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void getWeight() {
		System.out.print("Weight: ");
		System.out.println(weight);
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public void getNameSlave() {
		System.out.print("NameSlave: ");
		System.out.println(nameSlave);
	}
	
	public void setNameSlave(String nameSlave) {
		this.nameSlave = nameSlave;
	}
}

class Dog extends Mammal{
	private String name;
	private float weight;
	private String nameMaster;
	
	Dog(String name, float weight, String nameMaster){
		this.name = name;
		this.weight = weight;
		this.nameMaster = nameMaster;
	}

	public void bark() { 
		System.out.println("Bowbow");
	}

	public void getFood() {
		System.out.println("Apple");
	}

	public void getName() {
		System.out.print("Name: ");
		System.out.println(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void getWeight() {
		System.out.print("Weight: ");
		System.out.println(weight);
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
	
	public void getNameMaster() {
		System.out.print("NameMaster: ");
		System.out.println(nameMaster);
	}	
	
	public void setNameMaster(String nameMaster) {
		this.nameMaster = nameMaster;
	}
}

class Crocodile extends Reptile{
	private String name;
	private float weight;
	
	Crocodile(String name, float weight){
		this.name = name;
		this.weight = weight;
	}
	
	public void getFood() {
		System.out.println("Meat");
	}
	
	public void getName() {
		System.out.print("Name: ");
		System.out.println(name);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void getWeight() {
		System.out.print("Weight: ");
		System.out.println(weight);
	}
	
	public void setWeight(float weight) {
		this.weight = weight;
	}
}
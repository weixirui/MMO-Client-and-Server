package serverTest;

public class NPC {
	
	String name;
	
	int hp;

	int level;
	
	int exp;
	
	int money;
	
	int islive;
	
	String talk = "Hello";
	
	public NPC(String name,int hp,int level,int exp,int money,int islive,String talk) {
		this.name = name;
		this.hp = hp;
		this.level = level;
		this.exp = exp;
		this.money = money;
		this.islive = islive;
		this.talk = talk;
	}
	

	public String getNPCName() {
		return this.name;
	}


	public String getNPCTalk() {
		return this.talk;
	}
}

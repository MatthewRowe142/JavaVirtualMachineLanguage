package pack;

public class machine {
	
	private int ady;
	private number[] mem;
	
	public machine() {
		ady = 101;
		mem = new number[999];
		
	}
	
	public void set(int val){
		number temp = new number(ady,val);
		mem[ady] = temp;
		ady = ady+1;
	}
	public number getNum(int ady) {
		if(mem[ady]==null) {
			return null;
		}else {
			return mem[ady];
		}
	}
}
package pack;

public class number {
	private int address;
	private Integer value;
	public number(int address, int value) {
		this.address = address;
		this.value = value;
	}
	public number(int value) {
		this.value = value;
	}
	public number() {
		
	}
	public Integer getValue() {
		if(value>(int)Math.pow(10,6)) {
			return (-1*(value-(int)Math.pow(10,6)));
		}else {
			return value;
		}
	}
	public void setValue(int value) {
		this.value = value;
	}	
}

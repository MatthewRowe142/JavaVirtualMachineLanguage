package pack;

import java.util.Scanner;

public class main {
	public static int opcode;
	public static machine mach = new machine();
	public static boolean show;
	public static boolean demo;
	public static void main(String[] args) throws Exception {

		Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to the Machine!"
				+ "\nThis virtual machine has 899 addresses and 10 operations"
				+ "\n\n00000nnn Print value at address nnn"
				+ "\n01nnnnnn Set value nnnnnn to an open address"
				+ "\n02nnnmmm Add values at addresses nnn and mmm"
				+ "\n03nnnmmm Subtract value at address mmm from nnn"
				+ "\n04nnnmmm Multiply values at addresses nnn and mmm"
				+ "\n05nnnmmm Divide value at address nnn by mmm"
				+ "\n06nnnmmm Modulo value at addresses nnn by mmm"
				+ "\n07nnnmmm Raise value at address nnn to power mmm"
				+ "\n08nnnmmm Subtract values at addresses mmm by nnn always resulting in a positive number"
				+ "\n09nnnnnn Set negative value nnnnnn to open address"
				+ "\n\nWould like to see the addresses current values while using the machine? Enter 1 for yes, any other key for no.");

		String userRes = in.nextLine();
		if(Integer.parseInt(userRes)==1) {
			show = true;
		}

		System.out.println("Would you like to see a demo of the machine? Enter 1 for yes, any other key for no.");
		userRes = in.nextLine();
		if(Integer.parseInt(userRes)==1) {
			demo = true;
		}

		mach = new machine();
		if(demo) {
			System.out.println("Rather than giving a long block of machine code, each operation has been individually displayed. (In the actually machine you can chain together multiple operations together)");
			try{
				System.out.println("Problem 1: (|37%3-18|*12/51)^3 \n");

				System.out.println("01000037");
				op("01000037");
				System.out.println("01000003");
				op("01000003");
				System.out.println("06101102");
				op("06101102");
				System.out.println("01000018");
				op("01000018");
				System.out.println("08103104");
				op("08103104");
				System.out.println("01000012");
				op("01000012");
				System.out.println("04105106");
				op("04105106");
				System.out.println("01000051");
				op("01000051");
				System.out.println("05060070");
				op("05107108");
				System.out.println("01000003");
				op("01000003");
				System.out.println("07109110");
				op("07109110");
				System.out.println("Answer: ");
				op("00000111");
				System.out.println("");

				//			With proper parenthesis: (80*9)+((2^8)/3)

				System.out.println("Problem 2: 80*9+7-3/24^8-9 \n");

				System.out.println("01000080");
				op("01000080");
				System.out.println("01000009");
				op("01000009");
				System.out.println("04112113");
				op("04112113");
				System.out.println("01000002");
				op("01000002");
				System.out.println("01000008");
				op("01000008");
				System.out.println("07115116");
				op("07115116");
				System.out.println("01000003");
				op("01000003");
				System.out.println("05117118");
				op("05117118");
				System.out.println("02114119");
				op("02114119");
				System.out.println("Answer: ");
				op("00000120");

				System.out.println();

				System.out.println("Problem 3: 1*2+3*2+4*3+4*1 \n");

				System.out.println("01000001");
				op("01000001");
				System.out.println("01000002");
				op("01000002");
				System.out.println("04121122");
				op("04121122");
				System.out.println("01000003");
				op("01000003");
				System.out.println("01000002");
				op("01000002");
				System.out.println("04124125");
				op("04124125");
				System.out.println("01000004");
				op("01000004");
				System.out.println("01000003");
				op("01000003");
				System.out.println("04127128");
				op("04127128");
				System.out.println("01000001");
				op("01000001");
				System.out.println("01000004");
				op("01000004");
				System.out.println("04130131");
				op("04130131");
				System.out.println("02123126");
				op("02123126");
				System.out.println("02129132");
				op("02129132");
				System.out.println("02133134");
				op("02133134");
				System.out.println("Answer: ");
				op("00000135");
			}catch(Exception e) {
				System.out.println(e);
			}
		}
		System.out.println("Now it's your turn!");
		while(true){
			try {
				String opcode = in.nextLine();
				for(int i = 0; i<opcode.length()-1;i+=8) {
					op(opcode.substring(i,i+8));
				}
			}catch(Exception e) {
				System.out.println(e);
			}
		}
	}

	public static void op(String opcode) throws NumberFormatException, Exception {
		int oper = Integer.parseInt(opcode.substring(0, 2));
		int ady1 = Integer.parseInt(opcode.substring(2, 5));
		int ady2 = Integer.parseInt(opcode.substring(5, 8));

		switch (oper) {
		case 0:
			System.out.println(get(ady2));
			break;
		case 1:
			set(Integer.parseInt(opcode.substring(2, 8)));
			break;
		case 2:
			set(add(ady1,ady2));
			break;
		case 3:
			set(sub(ady1,ady2));
			break;
		case 4:
			set(mpy(ady1,ady2));
			break;
		case 5:
			set(div(ady1,ady2));
			break;
		case 6:
			set(mod(ady1,ady2));
			break;
		case 7:
			set(exp(ady1,ady2));
			break;
		case 8:
			set(abs(ady1,ady2));
			break;
		case 9:
			set((-1)*Integer.parseInt(opcode.substring(2, 8)));
			break;

		}
		if(show) {
			int i = 101;

			while(mach.getNum(i)!=null) {
				System.out.println(i+" : "+mach.getNum(i).getValue());
				i++;
			}
		}	
	}

	public static void set(int val) throws Exception {
		if(val<(Math.pow(10, 6))&&val>(-1*(Math.pow(10, 6)))) {
			if(val>0) {
				mach.set(val);
			}
			if(val<0) {
				mach.set((int)Math.pow(10,6)+Math.abs(val));
			}
		}else {
			throw new Exception("Out of bounds value");
		}
	}
	public static int get(int ady) throws Exception {
		if(mach.getNum(ady)!=null) {
			return mach.getNum(ady).getValue();
		}else {
			throw new Exception("No value at address");
		}
	}

	public static int add(int ady1, int ady2) throws Exception {
		if(!(mach.getNum(ady1)==null||mach.getNum(ady2)==null)) {
			return mach.getNum(ady1).getValue()+mach.getNum(ady2).getValue();
		}else {
			throw new Exception("No value at address");
		}
	}
	public static int sub(int ady1, int ady2) throws Exception {
		if(!(mach.getNum(ady1)==null||mach.getNum(ady2)==null)) {
			return mach.getNum(ady1).getValue()-mach.getNum(ady2).getValue();
		}else {
			throw new Exception("No value at address");
		}
	}
	public static int mpy(int ady1, int ady2) throws Exception {
		if(!(mach.getNum(ady1)==null||mach.getNum(ady2)==null)) {
			return mach.getNum(ady1).getValue()*mach.getNum(ady2).getValue();
		}else {
			throw new Exception("No value at address");
		}
	}
	public static int div(int ady1, int ady2) throws Exception {
		if(!(mach.getNum(ady1)==null||mach.getNum(ady2)==null)) {
			return mach.getNum(ady1).getValue()/mach.getNum(ady2).getValue();
		}else {
			throw new Exception("No value at address");
		}
	}
	public static int mod(int ady1, int ady2) throws Exception {
		if(!(mach.getNum(ady1)==null||mach.getNum(ady2)==null)) {
			return mach.getNum(ady1).getValue()%mach.getNum(ady2).getValue();
		}else {
			throw new Exception("No value at address");
		}
	}
	public static int exp(int ady1, int ady2) throws Exception {
		if(!(mach.getNum(ady1)==null||mach.getNum(ady2)==null)) {
			return (int) Math.pow(mach.getNum(ady1).getValue(),mach.getNum(ady2).getValue());
		}else {
			throw new Exception("No value at address");
		}
	}
	public static int abs(int ady1, int ady2) throws Exception {
		if(!(mach.getNum(ady1)==null||mach.getNum(ady2)==null)) {
			return Math.abs(mach.getNum(ady1).getValue()-mach.getNum(ady2).getValue());
		}else {
			throw new Exception("No value at address");
		}
	}
}

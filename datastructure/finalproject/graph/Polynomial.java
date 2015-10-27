package datastructure.finalproject.graph;

/** Integer Polynomials only. */
public class Polynomial {
	private List<Integer> coefficient;
	public Polynomial() {
		coefficient = new LList<Integer>();	
	}
	
	public void append(int x){
		coefficient.append(x);
	}
	
	public void clear(){
		coefficient.clear();
	}
	
	public int length(){
		return coefficient.length();
	}
	
	public String toString(){
		String s = "";
		int length = length();
		coefficient.moveToStart();
		if (length == 0) return "0";
		
		int j = coefficient.getValue();
		if (j!=0) s += j;
		coefficient.next();
		if (length == 1) {
			if (s!="") return s;
			else return "0";
		}
		
		for (int i = 1; i < length; i++) {
			j = coefficient.getValue();
			if (j!=0) {
				if (j<0) s += " - ";
				else if (s!="") s+=" + ";
				if (Math.abs(j)!=1) s+= Math.abs(j);
				if (i!=1) s += ("x^" + i);
				else s += "x";
			}
			coefficient.next();
		}
		if (s!="") return s;
		else return "0";
	}
	
//	public String toString(){
//		String s = "";
//		int length = length();
//		coefficient.moveToStart();
//		
//		for (int i = 0; i < length; i++) {
//			int j = coefficient.getValue();
//				s+= j;
//				s += ("x^" + i + " + ");
//			coefficient.next();
//		}
//		return s;
//	}

	public int calculate(int x) {
		int ans = 0;
		// Save the current position of the list
		int oldPos = coefficient.currPos();
		int length = coefficient.length();
		
		if (length == 0) return ans;

		coefficient.moveToStart();
		for (int i = 0; i < length; i++) {
			ans += Math.pow(x, i)*coefficient.getValue();
			coefficient.next();
		}
		coefficient.moveToPos(oldPos);						// Reset the fence to its original position
		return ans;
	}
	
//	static Polynomial add(Polynomial p1, Polynomial p2){
//		int length = Math.max(p1.length(), p2.length());
//		while (p1.length()<length) p1.append(0);
//		while (p2.length()<length) p2.append(0);
//		p1.coefficient.moveToStart();
//		p2.coefficient.moveToStart();
//		Polynomial p = new Polynomial();
//		while (p1.coefficient.currPos() != length) {
//			p.append(p1.coefficient.getValue()+p2.coefficient.getValue());
//			p1.coefficient.next();
//			p2.coefficient.next();
//		}
//		return p;
//	}
	
	static Polynomial sub(Polynomial p1, Polynomial p2){
		int length = Math.max(p1.length(), p2.length());
		while (p1.length()<length) p1.append(0);	
		while (p2.length()<length) p2.append(0);
		p1.coefficient.moveToStart();
		p2.coefficient.moveToStart();
		Polynomial p = new Polynomial();
		while (p1.coefficient.currPos() < length) {
			p.append(p1.coefficient.getValue()-p2.coefficient.getValue());
			p1.coefficient.next();
			p2.coefficient.next();
		}
		return p;
	}
	
	int minNo(){
		int i = 0;
		while (i <= coefficient.length()) {
			if (calculate(i)!=0) return i;
			i++;
		}
		return -1;
	}
	
//	public static void main(String[] args) {
//		Polynomial p1 = new Polynomial();
//		Polynomial p2 = new Polynomial();
//		p1.append(1);
//		p1.append(2);
//		p1.append(3);
//		p2.append(4);
//		p2.append(6);
//		p2.append(9);
//		p2.append(12);
//		System.out.println(p1);
//		System.out.println(p2);
//		System.out.println(add(p1, p2));
//		System.out.println(sub(p1, p2));
//	}
}

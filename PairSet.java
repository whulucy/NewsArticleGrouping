package P2;



public class PairSet {
	private int numberA;
	private int numberB;
	
	public PairSet(int numberA, int numberB) {
		this.numberA = numberA;
		this.numberB = numberB ;
	}
	public int getNumberA() {
		return numberA;
	}
	public void setNumberA(int numberA) {
		this.numberA = numberA;
	}
	public int getNumberB() {
		return numberB;
	}
	public void setNumberB(int numberB) {
		this.numberB = numberB;
	}
	
	@Override
	public int hashCode() {
		return this.numberA + this.numberB * 31;

	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		PairSet other = (PairSet) obj;
		
		if (this.numberA != other.numberA)
			return false;
		if (this.numberB != other.numberB)
			return false;
	
        return true;
    }

}

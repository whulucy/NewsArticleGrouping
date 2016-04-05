package P2;

public class HashFunc {
	private int numFirst;
	private int numSecond;
	private int prime;
	private int numValue;
	public int calculateHashFunc(int x){	
		int result = (numFirst % numValue + numSecond * x) % prime;	
		return result;
	}
	
	public int getNumFirst() {
		return numFirst;
	}
	public void setNumFirst(int numFirst) {
		this.numFirst = numFirst;
	}
	public int getNumSecond() {
		return numSecond;
	}
	public void setNumSecond(int numSecond) {
		this.numSecond = numSecond;
	}
	public int getNumValue() {
		return numValue;
	}
	public void setNumValue(int numValue) {
		this.numValue = numValue;
	}
	public int getPrime() {
		return prime;
	}
	public void setPrime(int prime) {
		this.prime = prime;
	}
	public String toString() {
		return "h[a,b](x) = (" + numFirst + " % " + numValue + " + " + numSecond + "*x" 
				+ ") mod " + prime;
	}
}
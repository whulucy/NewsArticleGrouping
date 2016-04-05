package P2;


public class HashFuncProducer {
	
	private static final int RANGE = 3500000;
	private int numFirst;
	private int numSecond;
	private int numValue;
	private int prime;
	public HashFuncProducer(int numValue) {
		this.numValue = numValue;
		this.numFirst = (int) (Math.random()*RANGE )+1;
		this.numSecond = (int) (Math.random()*RANGE )+1;
		if(numFirst == numSecond) {
			this.numSecond = (int) (Math.random()*RANGE )+1;
		}
		this.prime = getNextPrime(numValue);
	}

	public HashFunc getHashFunction() {
		HashFunc hashFunc = new HashFunc();
		hashFunc.setNumFirst(numFirst);
		hashFunc.setNumSecond(numSecond);
		hashFunc.setNumValue(numValue);
		hashFunc.setPrime(prime);
		return hashFunc;
	}
	
	public void generateNewHashFunc() {
		this.numFirst = (int) (Math.random()*RANGE )+1;
		this.numSecond = (int) (Math.random()*RANGE )+1;
		if(numFirst == numSecond) {
			this.numSecond = (int) (Math.random()*RANGE )+1;
		}
	}
	
	private int getNextPrime(int value) {
		int iterator = value* 180;
		if(iterator < 3500000) iterator = 3500000;
		while(!isPrime(iterator)) {
			iterator++;
		}
		return iterator;
	}
	
	private boolean isPrime(int value) {
		for(int i = 2; i<Math.sqrt((double)value); i++) {
			if(GCD(value, i) != 1) return false;
		}
		return true;
	}
	
	private int GCD(int num1, int num2) {
		
		if (num2 == 0)
			return num1;
		else {
			return GCD(num2, num1%num2);
		}
	}
	public int getNumFirst() {
		return numFirst;
	}
	public int getNumSecond() {
		return numSecond;
	}
	public int getNumValue() {
		return numValue;
	}
	public int getPrime() {
		return prime;
	}
	@Override
	public int hashCode() {
		final int primeFactor = 31;
		int result = 1;
		result = primeFactor * result + numFirst;
		result = primeFactor * result + numSecond;
		result = primeFactor * result + numValue;
		result = primeFactor * result + this.prime;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HashFuncProducer other = (HashFuncProducer) obj;
		if (numFirst != other.numFirst)
			return false;
		if (numSecond != other.numSecond)
			return false;
		if (numValue != other.numValue)
			return false;
		if (prime != other.prime)
			return false;
		return true;
	}

}

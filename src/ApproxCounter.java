import java.math.BigInteger;
import java.util.Random;

public class ApproxCounter {
	
	private Random rand;
	private int[] exponents;
	private BigInteger length;
	
	public ApproxCounter(int n) {
		rand = new Random();
		exponents = new int[n];
		length = BigInteger.valueOf(n);
	}
	
	public void increment() {
		outer: for(int i = 0; i < exponents.length; i++) {
			for(int j = exponents[i]; j > 0; j -= 32) {
				int bits = (j < 32) ? j : 32;
				int mask = (1 << bits) - 1;
				if ((rand.nextInt() & mask) != 0)
					continue outer;
			}
			exponents[i]++;
		}
	}
	
	public BigInteger getValue() {
		BigInteger sum = BigInteger.ZERO;
		for(int i = 0; i < exponents.length; i++) {
			sum = sum.add(BigInteger.TWO.pow(exponents[i]));
		}
		return sum.subtract(length).divide(length);
	}
	
}
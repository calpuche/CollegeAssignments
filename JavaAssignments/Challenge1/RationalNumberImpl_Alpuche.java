package rationalnumbers;

public class RationalNumberImpl_Alpuche implements RationalNumber {
	private int num;
	private int den;

	public RationalNumberImpl_Alpuche(int theNum, int theDen) {

		/*
		 * Old finding the greatest common divsor if ((theDen % theNum) == 0) {
		 * num = theNum / theNum; den = theDen / theNum; } else if((theNum %
		 * theDen) == 0){
		 * 
		 * } else{ num = theNum; den = theDen; } System.out.println("Num: " +
		 * num + "  Den: " + den);
		 * 
		 */

		if (theDen == 0) {
			System.exit(0);
		}
		if (theDen < 0) {
			theNum = theNum * -1;
			theDen = theDen * -1;
		}
		int divisor = gcd(theNum, theDen);
		num = theNum / divisor;
		den = theDen / divisor;

	}

	public int getNumerator() {
		return this.num;
	}

	public int getDenominator() {
		return this.den;
	}

	public double getValue() {
		// System.out.println(this.num);
		// System.out.println(this.den);
		double newNum = this.num;
		double newDen = this.den;
		return (newNum / newDen);
	}

	public String toString() {
		String rv = this.num + "/" + this.den;
		return rv;
	}

	public static int gcd(int numerator, int denominator) {
		if (denominator == 0) {
			return numerator;
		}
		int remainder = numerator % denominator;
		return gcd(denominator, remainder);
	}

}
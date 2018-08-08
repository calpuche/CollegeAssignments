package rationalnumbers;

public class RationalNumberUtils_Alpuche {
	// rv is true <==> r1 = r2 as rational numbers
	public static boolean equal(RationalNumber r1, RationalNumber r2) {
		if (r1.getValue() == r2.getValue()) {
			return true;
		} else {
			return false;
		}

	}

	// rv = r1+ r2, where + is addition
	public static RationalNumber add(RationalNumber r1, RationalNumber r2) {
		int num = (r1.getNumerator() * r2.getDenominator()) + (r1.getDenominator() * r2.getNumerator());
		int den = (r1.getDenominator() * r2.getDenominator());

		RationalNumber rv = new RationalNumberImpl_Alpuche(num, den);

		return rv;

	}

	// rv = r1-r2
	public static RationalNumber subtract(RationalNumber r1, RationalNumber r2) {
		int num = ((r1.getNumerator() * r2.getDenominator()) - (r1.getDenominator() * r2.getNumerator()));
		int den = (r1.getDenominator() * r2.getDenominator());
		RationalNumber rv = new RationalNumberImpl_Alpuche(num, den);
		return rv;

	}

	// rv = r1*r2
	public static RationalNumber multiply(RationalNumber r1, RationalNumber r2) {
		int num = (r1.getNumerator() * r2.getNumerator());
		int den = (r1.getDenominator() * r2.getDenominator());
		RationalNumber rv = new RationalNumberImpl_Alpuche(num, den);
		return rv;

	}

	// rv = r1/r2
	public static RationalNumber divide(RationalNumber r1, RationalNumber r2) {
		int num = (r1.getNumerator() * r2.getDenominator());
		int den = (r1.getDenominator() * r2.getNumerator());
		RationalNumber rv = new RationalNumberImpl_Alpuche(num, den);
		return rv;

	}

	// rv = -r1, where - is regular numerical negation
	public static RationalNumber negate(RationalNumber r1) {
		int num = (r1.getNumerator() * -1);
		int den = (r1.getDenominator());
		RationalNumber rv = new RationalNumberImpl_Alpuche(num, den);
		return rv;
	}

	/*
	 * ---Testing-- public static void main(String[] args) { RationalNumber r1 =
	 * new RationalNumberImpl_Alpuche(-9, -1); RationalNumber r2 = new
	 * RationalNumberImpl_Alpuche(6, 4); System.out.println(r1);
	 * System.out.println(r2); RationalNumber negate = negate(r1);
	 * RationalNumber addition = add(r1, r2); RationalNumber subtraction =
	 * subtract(r1, r2); RationalNumber mulitplication = multiply(r1, r2);
	 * RationalNumber divison = divide(r1, r2); // System.out.println(
	 * "Value of r2: " +r2.getValue()); boolean equality = equal(r1, r2);
	 * System.out.println("Negation: " + negate); System.out.println(
	 * "addition: " + addition); System.out.println("subtraction: " +
	 * subtraction); System.out.println("mulitplication: " + mulitplication);
	 * System.out.println("Divison: " + divison); System.out.println("Equals: "
	 * + equality); System.out.println(r1); System.out.println(r2);
	 * 
	 * }
	 */
}

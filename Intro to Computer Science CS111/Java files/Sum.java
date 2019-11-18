public class Sum {
	public static void main(String[] args) {
		System.out.println("Enter first number (integer):");
			int firstnumber = IO.readInt();
		System.out.println("Enter second number (integer):");
			int secondnumber = IO.readInt();
		int sum = firstnumber + secondnumber;
		IO.outputIntAnswer(sum);
	}
}

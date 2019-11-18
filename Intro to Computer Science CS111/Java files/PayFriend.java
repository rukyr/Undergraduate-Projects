public class PayFriend {
	public static void main(String[] args) {
		System.out.println("What is the payment amount? Payment amount: ");
		double amount = IO.readDouble();
		if (amount <= 100) {
			IO.outputDoubleAnswer(5);
		} else if (amount > 100 &&  amount < 200) {
			IO.outputDoubleAnswer(6);
		} else if (amount >= 200 && amount < 1000) {
			double sum1 = amount * 0.03;
			IO.outputDoubleAnswer(sum1);
		} else if (amount >= 1000 && amount < 1500) {
			IO.outputDoubleAnswer(15);
		} else if (amount >= 1500 && amount < 10000) {
			double sum2 = amount * 0.01;
			IO.outputDoubleAnswer(sum2);
		} else if (amount == 10000) {
			IO.outputDoubleAnswer(100);
		} else if (amount > 10000) {
			double sum3 = amount - 10000;
			if (sum3 <= 5000) {
				double sum4 = (0.02 * sum3) + 100;
				IO.outputDoubleAnswer(sum4);
			} else if (sum3 > 5000) {
				double sum5 = sum3 - 5000;
				double sum6 = (sum5 * 0.03) + 200;
				IO.outputDoubleAnswer(sum6);
			}	
		}
	}
	 
}
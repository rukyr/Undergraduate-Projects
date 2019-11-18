public class Party {

	public static void main(String[] args) {
		System.out.println("How many people will be attending the party?");
		int attendance = IO.readInt();
		System.out.println("How many slices of pizza should each person be able to eat?");
		int sliceseat = IO.readInt();
		double totalslices = attendance * sliceseat;
		System.out.println("How many cans of soda should each person be able to drink?");
		int sodadrinks = IO.readInt();
		double totalcans = attendance * sodadrinks;
		System.out.println("What is the cost of a pizza pie? (In $)");
		double pizzaprice = IO.readDouble();
		System.out.println("How many slices are in a pizza pie?");
		int slicesperpie = IO.readInt();
		double piesneeded = totalslices / slicesperpie;
		double pizzatotalcost = Math.ceil(piesneeded) * pizzaprice;
		System.out.println("What is the cost of a case of soda? (In $)");
		double sodaprice = IO.readDouble();
		System.out.println("How many cans are in a case of soda?");
		int canspercase = IO.readInt();
		double casesneeded = totalcans / canspercase;
		double sodatotalcost = Math.ceil(casesneeded) * sodaprice;
		double totalcost = sodatotalcost + pizzatotalcost;
		IO.outputDoubleAnswer(totalcost);
	}	
}

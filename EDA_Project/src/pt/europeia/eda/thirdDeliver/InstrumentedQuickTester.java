package pt.europeia.eda.thirdDeliver;

import static java.lang.System.out;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.thirdDeliver.InstrumentedQuick;

public class InstrumentedQuickTester {

	public static void execute(final int limit) {
		final In in = new In("data/partially_sorted_" + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);

		InstrumentedQuick.sort(numbersToSortSorted);

	}

	public static void performExperimentsFor(final int limit) {

		execute(limit);

		out.println("Sorted file " + "data/partially_sorted_" + limit + ".txt \t Accesses= "
				+ InstrumentedQuick.getNumberOfArrayAccesses() + "\t Reads= "
				+ InstrumentedQuick.getNumberOfArrayReads() + "\t Writes= "
				+ InstrumentedQuick.getNumberOfArrayWrites() + "\t Compares= "
				+ InstrumentedQuick.getNumberOfComparisons() + "\t Swaps= "
				+ InstrumentedQuick.getNumberOfSwaps());
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (int exponent = 0, limit = 2; exponent != 24; exponent++, limit *= 2)
			performExperimentsFor(limit);

	}

	public static Double[] readAllDoubles(In in) {
		String[] fields = in.readAllStrings();
		Double[] vals = new Double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}
}

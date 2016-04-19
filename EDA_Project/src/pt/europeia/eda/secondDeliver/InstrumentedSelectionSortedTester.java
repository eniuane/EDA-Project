package pt.europeia.eda.secondDeliver;

import static java.lang.System.out;

import edu.princeton.cs.algs4.In;

public class InstrumentedSelectionSortedTester {

	public static void execute(final int limit) {
		final In in = new In("data/sorted_" + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);

		InstrumentedSelection.sort(numbersToSortSorted);

	}

	public static void performExperimentsFor(final int limit) {

		execute(limit);

		out.println("Sorted file " + "data/sorted_" + limit + ".txt \t Accesses= "
				+ InstrumentedSelection.getNumberOfArrayAccesses() + "\t Reads= "
				+ InstrumentedSelection.getNumberOfArrayReads() + "\t Writes= "
				+ InstrumentedSelection.getNumberOfArrayWrites() + "\t Compares= "
				+ InstrumentedSelection.getNumberOfComparisons() + "\t Swaps= "
				+ InstrumentedSelection.getNumberOfSwaps());
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

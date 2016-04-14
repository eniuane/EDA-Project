
package pt.europeia.eda.secondDeliver;

import static java.lang.System.out;

import edu.princeton.cs.algs4.In;

public class InstrumentedInsertionShuffleTester {

	public static void execute(final int limit) {
		final In in = new In("data/shuffled_" + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);

		InstrumentedInsertion.sort(numbersToSortSorted);

	}

	public static void performExperimentsFor(final int limit, final boolean isWarmup) {

		execute(limit);

		out.println("Sorted file " + "data/shuffled_" + limit + ".txt \t Accesses= "
				+ InstrumentedInsertion.getNumberOfArrayAccesses() + "\t Reads= "
				+ InstrumentedInsertion.getNumberOfArrayReads() + "\t Writes= "
				+ InstrumentedInsertion.getNumberOfArrayWrites() + "\t Compares= "
				+ InstrumentedInsertion.getNumberOfComparisons() + "\t Swaps= "
				+ InstrumentedInsertion.getNumberOfSwaps());
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (int exponent = 0, limit = 2; exponent != 31; exponent++, limit *= 2)
			performExperimentsFor(limit, false);

	}

	public static Double[] readAllDoubles(In in) {
		String[] fields = in.readAllStrings();
		Double[] vals = new Double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}
}

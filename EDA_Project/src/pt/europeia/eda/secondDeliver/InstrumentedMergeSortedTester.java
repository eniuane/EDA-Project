package pt.europeia.eda.secondDeliver;

import static java.lang.System.out;

import edu.princeton.cs.algs4.In;

public class InstrumentedMergeSortedTester {

	public static void execute(final int limit) {
		final In in = new In("data/sorted_" + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);

		InstrumentedMerge.sort(numbersToSortSorted);

	}

	public static void performExperimentsFor(final int limit) {

		execute(limit);

		out.println("Sorted file " + "data/sorted_" + limit + ".txt \t Accesses= "
				+ InstrumentedMerge.getNumberOfArrayAccesses() + "\t Reads= "
				+ InstrumentedMerge.getNumberOfArrayReads() + "\t Writes= " + InstrumentedMerge.getNumberOfArrayWrites()
				+ "\t Compares= " + InstrumentedMerge.getNumberOfComparisons());
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (int exponent = 0, limit = 2; exponent != 31; exponent++, limit *= 2)
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

package pt.europeia.eda.secondDeliver;

import static java.lang.System.out;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;

public class InstrumentedInsertionTester {

	public static final String[] directorysToSort = { "data/partially_sorted_", "data/sorted_", "data/shuffled_" };

	public static final double maxTimeForAnExperiment = 30.0; /* seconds */

	public static void execute(final int limit, final String filesToTest) {
		final In in = new In(filesToTest + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);

		InstrumentedInsertion.sort(numbersToSortSorted);

	}

	public static void performExperimentsFor(final int limit, final String filesToTest) {

		execute(limit, filesToTest);

		out.println("Sorted " + limit + "\t Accesses= " + InstrumentedInsertion.getNumberOfArrayAccesses()
				+ "\t Reads= " + InstrumentedInsertion.getNumberOfArrayReads() + "\t Writes= "
				+ InstrumentedInsertion.getNumberOfArrayWrites() + "\t Compares= "
				+ InstrumentedInsertion.getNumberOfComparisons() + "\t Swaps= "
				+ InstrumentedInsertion.getNumberOfSwaps());
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (String filesToTest : directorysToSort) {
			out.println(filesToTest);
			for (int exponent = 0, limit = 2; exponent != 24; exponent++, limit *= 2) {
				final Stopwatch stopwatch = new Stopwatch();
				performExperimentsFor(limit, filesToTest);
				if (stopwatch.elapsedTime() > maxTimeForAnExperiment)
					break;
			}
		}

	}

	public static Double[] readAllDoubles(In in) {
		String[] fields = in.readAllStrings();
		Double[] vals = new Double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}
}

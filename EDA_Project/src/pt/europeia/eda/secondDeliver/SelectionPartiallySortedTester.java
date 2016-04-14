package pt.europeia.eda.secondDeliver;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;

public class SelectionPartiallySortedTester {

	public static final double timeBudgetPerExperiment = 2.0 /* seconds */;

	public static final double minimumTimePerContiguousRepetitions = 1e-5 /* seconds */;

	public static double medianOf(final ArrayList<Double> values) {
		final int size = values.size();

		values.sort(null);

		if (size % 2 == 0)
			return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
		else
			return values.get(size / 2);
	}

	public static int contiguousRepetitionsFor(final int limit) {
		final In in = new In("data/partially_sorted_" + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);

		final Stopwatch stopwatch = new Stopwatch();
		int contiguousRepetitions = 0;
		do {
			final Double[] numbersToSort = numbersToSortSorted.clone();
			Selection.sort(numbersToSort);
			contiguousRepetitions++;

		} while (stopwatch.elapsedTime() < minimumTimePerContiguousRepetitions);

		return contiguousRepetitions;
	}

	public static double executionTimeFor(final int limit, final int contiguousRepetitions) {
		final In in = new In("data/partially_sorted_" + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);
		final ArrayList<Double[]> listOfNumbersToSort = new ArrayList<Double[]>();
		for (int i = 0; i != contiguousRepetitions; i++)
			listOfNumbersToSort.add(numbersToSortSorted);

		final Stopwatch stopwatch = new Stopwatch();
		for (int i = 0; i != contiguousRepetitions; i++) {
			final Double[] numbersToSort = listOfNumbersToSort.get(i).clone();
			for (int j = 0; j != limit; j++) {
				Selection.sort(numbersToSort);
			}
			listOfNumbersToSort.set(i, null);
		}
		return stopwatch.elapsedTime() / contiguousRepetitions;
	}

	public static void performExperimentsFor(final int limit, final boolean isWarmup) {
		final ArrayList<Double> executionTimes = new ArrayList<Double>();
		final Stopwatch stopwatch = new Stopwatch();
		final int contiguousRepetitions = contiguousRepetitionsFor(limit);
		long repetitions = 0;
		do {
			executionTimes.add(executionTimeFor(limit, contiguousRepetitions));
			repetitions++;
		} while (stopwatch.elapsedTime() < timeBudgetPerExperiment);

		final double median = medianOf(executionTimes);

		if (!isWarmup)
			out.println("Sorted file " + "data/partially_sorted_" + limit + ".txt" + " \t median= " + median
					+ "\t Reps= " + repetitions);
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (int exponent = 0, limit = 2; exponent != 8; exponent++, limit *= 2)
			performExperimentsFor(limit, true);

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
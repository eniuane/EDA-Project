package pt.europeia.eda.secondDeliver;

import static java.lang.System.out;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;

public class InsertionTester2 {

	public static final double timeBudgetPerExperiment = 30.0 /* seconds */;

	public static final double maxTime = 4.0 /* seconds */;

	public static final String[] filesToSort = { "data/partially_sorted_", "data/shuffled_", "data/sorted_" };

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
			final In in = new In(filesToSort[2] + limit + ".txt");
			final Double[] numbersToSortSorted = readAllDoubles(in);

		final Stopwatch stopwatch = new Stopwatch();
		int contiguousRepetitions = 0;
		do {
			final Double[] numbersToSort = numbersToSortSorted.clone();
			Insertion.sort(numbersToSort);
			contiguousRepetitions++;

		} while (stopwatch.elapsedTime() < minimumTimePerContiguousRepetitions);

		return contiguousRepetitions;
	}

	public static double executionTimeForSorted(final int limit, final int contiguousRepetitions) {
		final In in = new In(filesToSort[2] + limit + ".txt");
		final Double[] numbersToSortSorted = readAllDoubles(in);
		final ArrayList<Double[]> listOfNumbersToSort = new ArrayList<Double[]>();
		for (int i = 0; i != contiguousRepetitions; i++)
			listOfNumbersToSort.add(numbersToSortSorted);

		final Stopwatch stopwatch = new Stopwatch();
		for (int i = 0; i != contiguousRepetitions; i++) {
			final Double[] numbersToSort = listOfNumbersToSort.get(i).clone();
			for (int j = 0; j != limit; j++) {
				Insertion.sort(numbersToSort);
			}
			listOfNumbersToSort.set(i, null);
		}
		return stopwatch.elapsedTime() / contiguousRepetitions;
	}

	public static void performExperimentsForSorted(final int limit, final boolean isWarmup) {
		final ArrayList<Double> executionTimes = new ArrayList<Double>();
		final Stopwatch stopwatch = new Stopwatch();
		final int contiguousRepetitions = contiguousRepetitionsFor(limit);
		long repetitions = 0;
		do {
			executionTimes.add(executionTimeForSorted(limit, contiguousRepetitions));
			repetitions++;
		} while (stopwatch.elapsedTime() < timeBudgetPerExperiment);

		final double median = medianOf(executionTimes);

		if (!isWarmup)
			out.println("Sorted file " + filesToSort[2] + limit + ".txt" + " \t median= " + median + "\t Reps= " + repetitions);
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (int exponent = 0, limit = 2; exponent != 4; exponent++, limit *= 2)
			performExperimentsForSorted(limit, true);

		for (int exponent = 0, limit = 2; exponent != 31; exponent++, limit *= 2)
			performExperimentsForSorted(limit, false);

	}

	public static Double[] readAllDoubles(In in) {
		String[] fields = in.readAllStrings();
		Double[] vals = new Double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}
}

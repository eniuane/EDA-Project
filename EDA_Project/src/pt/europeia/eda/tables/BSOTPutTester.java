package pt.europeia.eda.tables;

import static java.lang.System.out;

import java.util.ArrayList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import pt.europeia.eda.Stopwatch;

public class BSOTPutTester {

	public static final double timeBudgetPerExperiment = 2.0; /* seconds */

	public static final double maxTimeForAnExperiment = 30.0;

	public static final double minimumTimePerContiguousRepetitions = 1e-5 /* seconds */;

	public static final String[] directorysToSort = { "data/partially_sorted_", "data/sorted_", "data/shuffled_" };

	public static double medianOf(final ArrayList<Double> values) {
		final int size = values.size();

		values.sort(null);

		if (size % 2 == 0)
			return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
		else
			return values.get(size / 2);
	}

	public static double averageOf(final ArrayList<Double> values) {
		double sum = 0.0;

		if (values.size() == 1)
			return values.get(0);

		for (int i = 0; i != values.size() - 1; i++)
			sum += values.get(i);

		return sum / values.size();
	}

	public static int contiguousRepetitionsFor(final int limit, final String fileToSort) {
		final In in = new In(fileToSort + limit + ".txt");
		final Double trashValue = 0.0;
		final Double[] keys = readAllDoubles(in);

		final Stopwatch stopwatch = new Stopwatch();
		int contiguousRepetitions = 0;
		do {
			final BinarySearchOrderedTable<Double,Double> table = new BinarySearchOrderedTable<Double,Double>();
			for (int i = 0; i != limit; i++)
				table.put(keys[i],trashValue);

			contiguousRepetitions++;

		} while (stopwatch.elapsedTime() < minimumTimePerContiguousRepetitions);

		return contiguousRepetitions;
	}

	public static double executionTimeFor(final int limit, final int contiguousRepetitions, final String fileToSort) {
		final In in = new In(fileToSort + limit + ".txt");
		final Double trashValue = 0.0;
		final Double[] keys = readAllDoubles(in);
		final ArrayList<BinarySearchOrderedTable<Double, Double>> tables = new ArrayList<BinarySearchOrderedTable<Double, Double>>();
		for (int i = 0; i != contiguousRepetitions; i++)
			tables.add(new BinarySearchOrderedTable<Double, Double>());
		
		final Stopwatch stopwatch = new Stopwatch();
		for (int i = 0; i != contiguousRepetitions; i++) {
			final BinarySearchOrderedTable<Double, Double> table = tables.get(i);
			for (int j = 0; j != limit; j++)
				table.put(keys[j], trashValue);
			tables.set(i, null);
		}
		return (stopwatch.elapsedTime() / contiguousRepetitions) / limit;
	}

	public static void performExperimentsFor(final int limit, final boolean isWarmup, final String fileToSort) {
		final ArrayList<Double> executionTimes = new ArrayList<Double>();
		final Stopwatch stopwatch = new Stopwatch();
		final int contiguousRepetitions = contiguousRepetitionsFor(limit, fileToSort);
		long repetitions = 0;
		do {
			executionTimes.add(executionTimeFor(limit, contiguousRepetitions, fileToSort));
			repetitions++;
		} while (stopwatch.elapsedTime() < timeBudgetPerExperiment);

		final double median = medianOf(executionTimes);
		final double average = averageOf(executionTimes);

		if (!isWarmup) {
			out.println("Put " + limit + " values \t median= " + median + "\t Average= "
					+ average + "\t Minimum= " + executionTimes.get(0) + "\t Maximum= "
					+ executionTimes.get(executionTimes.size() - 1) + "\t Reps= " + repetitions + "\t ContiguousReps= "
					+ contiguousRepetitions);
		}
	}

	public static void main(final String[] arguments) throws InterruptedException {

		for (int exponent = 0, limit = 2; exponent != 8; exponent++, limit *= 2)
			performExperimentsFor(limit, true, directorysToSort[0]);

		for (String filesToSort : directorysToSort) {
			out.println(filesToSort);
			for (int exponent = 0, limit = 2; exponent != 24; exponent++, limit *= 2) {
				final Stopwatch stopwatch = new Stopwatch();
				performExperimentsFor(limit, false, filesToSort);
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

package pt.europeia.eda.secondDeliver;

import static java.lang.System.out;
import static pt.europeia.eda.ObjectSizeFetcher.sizeOf;

import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;
import pt.europeia.eda.firstDeliver.Stack;

public class InsertionTester2 {
	// A time budget is established per experiment. Each experiment is repeated
	// as many times as necessary to expend this budget. That is, each
	// experiment is repeated until the total time spent repeating it exceeds
	// the budget.
	public static final double timeBudgetPerExperiment = 2.0 /* seconds */;

	public static final double maxTime = 4.0 /* seconds */;
	
	public static final String[] filesToSort = {"data/partially_sorted_","data/shuffled_","data/sorted_"};

	// Small execution times are very "noisy", since the System.nanoTime()
	// method does not have sufficient precision to measure them. In some
	// systems, smaller execution times may even be measured as 0.0! Hence, in
	// many cases it is preferable to perform a run of contiguous repetitions of
	// an experiment, instead of a single experiment. The total
	// execution time of that run of contiguous repetitions is measured. Then,
	// the execution time of a single experiment is estimated as the average
	// execution time, that is, the total execution time of the contiguous
	// repetitions divided by the number of contiguous repetitions of the
	// experiment performed. Instead of using always the same number of
	// contiguous repetitions, however, it is preferable to establish the
	// minimum
	// duration of a run to value which is clearly long enough for
	// System.nanoTime() to measure with acceptable accuracy.
	public static final double minimumTimePerContiguousRepetitions = 1e-5 /* seconds */;

	// A simple, inefficient way to calculate the median of the values in an
	// ArrayList:
	public static double medianOf(final ArrayList<Double> values) {
		final int size = values.size();

		values.sort(null);

		if (size % 2 == 0)
			return (values.get(size / 2 - 1) + values.get(size / 2)) / 2.0;
		else
			return values.get(size / 2);
	}

	// Estimate the number of contiguous repetitions to perform for a given
	// limit of the numbers to sum in the experiment:
	public static int contiguousRepetitionsFor(final int limit, final String[] files) {
		final ArrayList<Double[]> filesToSort = new ArrayList<Double[]>();
		for (int i=0 ; i != 3 ; i++)
		{
			final In inPartiallySorted = new In(files[i]+limit+".txt");
			final Double[] partiallySortedNumbersDouble = readAllDoubles(inPartiallySorted);
			filesToSort.add(partiallySortedNumbersDouble);
		}
		
		final Stopwatch stopwatch = new Stopwatch();
		int contiguousRepetitions = 0;
		do {
			final Double[] FileToSort = partiallySortedNumbersDouble.clone();
			for (int i = 0; i != limit; i++) {
				stackOfInts.push(i);
			}
			contiguousRepetitions++;

		} while (stopwatch.elapsedTime() < minimumTimePerContiguousRepetitions);

		// The loop stops when the minimum time per contiguous repetitions is
		// reached. For longer experiments, this will mostly turn out to be one,
		// which is what we would expect, since contiguous repetitions are
		// useful only for small execution times.

		return contiguousRepetitions;
	}

	// Performs a run of contiguous repetitions of an experiment to obtain the
	// execution time of the method to calculate the sum of the integers from 1
	// to a given limit. The number of contiguous experiments is also passed as
	// argument.
	public static double executionTimeFor(final int limit, final int contiguousRepetitions) {
		final ArrayList<Double[]> filesToSort = new ArrayList<Double[]>();
		for (int i = 0; i != contiguousRepetitions; i++)
			filesToSort.add(new Double[limit]);

		final Stopwatch stopwatch = new Stopwatch();
		for (int i = 0; i != contiguousRepetitions; i++) {
			final Stack<Integer> stackOfInts = stacks.get(i);
			for (int j = 0; j != limit; j++) {
				stackOfInts.push(j);
			}
			stacks.set(i, null);
		}
		return stopwatch.elapsedTime() / contiguousRepetitions;
	}

	// Performs experiments to obtain a sequence of estimates of the execution
	// time of the method to calculate the sum of the integers from 1
	// to a given limit. The number of experiments to performed is not fixed.
	// Rather, a time budget is used and the experiments are repeated until the
	// budged is spent. The sequence of the execution times obtained is then
	// used to calculate the median execution time, which is a reasonably robust
	// statistic. The results are shown, except if this is a warm up run.
	public static void performExperimentsFor(final int limit, final boolean isWarmup) {
		final ArrayList<Double> executionTimes = new ArrayList<Double>();
		final Stopwatch stopwatch = new Stopwatch();
		final int contiguousRepetitions = contiguousRepetitionsFor(limit,filesToSort);
		long repetitions = 0;
		do {
			executionTimes.add(executionTimeFor(limit, contiguousRepetitions));
			repetitions++;
		} while (stopwatch.elapsedTime() < timeBudgetPerExperiment);

		final double median = medianOf(executionTimes);

		if (!isWarmup) {
			final Stack<Integer> stackOfInts = new Stack<Integer>();
			for (int i = 0; i != limit; i++)
				stackOfInts.push(i);

			out.println("Made " + limit + " pushes \t Memory = " + allSizeOf(stackOfInts) + "bytes \t median= " + median
					+ "\t Reps= " + repetitions);
		}
	}



	public static void main(final String[] arguments) throws InterruptedException {
		// The experiments are run for limits of the sums which increase
		// geometrically, through the powers of 2:

		// Warm up (this attempts to force the JIT compiler to do its work
		// before the experiments actually begin):

		// out.println(sizeOf(new Stack<Integer>()));

		for (int exponent = 0, limit = 1; exponent != 8; exponent++, limit *= 2)
			performExperimentsFor(limit, true);

		// The actual experiments are performed here, with limits going from 1
		// to 2^30:
		for (int exponent = 0, limit = 1; exponent != 31; exponent++, limit *= 2)
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

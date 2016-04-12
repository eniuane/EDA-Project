package pt.europeia.eda.secondDeliver;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;

import static pt.europeia.eda.Tools.out;

import java.util.Arrays;

public class MergeTester {

	public static void main(final String[] arguments) {

		int maxReps = 1000;
		int middle = 0;
		double estimatedTime = 0;
		double averageTime = 0;
		double median = 0;
		double[] estimatedTimeArray = new double[maxReps];

		for (int i = 0; i < maxReps; i++) {
			final In inPartiallySorted = new In(arguments[0]);

			final Double[] partiallySortedNumbersDouble = readAllDoubles(inPartiallySorted);

			final Stopwatch stopwatch = new Stopwatch();
			Merge.sort(partiallySortedNumbersDouble);
			estimatedTime = stopwatch.elapsedTime();

			averageTime += estimatedTime;
			estimatedTimeArray[i] = estimatedTime;
		}

		Arrays.sort(estimatedTimeArray);

		middle = estimatedTimeArray.length / 2;

		if (estimatedTimeArray.length % 2 == 0)
			median = (estimatedTimeArray[middle] + estimatedTimeArray[middle - 1]) / 2;
		else
			median = estimatedTimeArray[middle];

		out.println("Sorted the '" + arguments[0] + "' Median = " + median + " Average = " + averageTime / maxReps);
	}

	public static Double[] readAllDoubles(In in) {
		String[] fields = in.readAllStrings();
		Double[] vals = new Double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}
}

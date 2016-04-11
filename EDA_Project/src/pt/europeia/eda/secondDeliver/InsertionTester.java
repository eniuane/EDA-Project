package pt.europeia.eda.secondDeliver;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;

import static pt.europeia.eda.Tools.out;

import java.util.Arrays;

public class InsertionTester {

	public static void main(final String[] arguments) {

		double estimatedTime = 0;
		int maxReps = 1000;
		double[] timers = new double[maxReps];
		double averageOfTimers = 0;
		
		// final In inShuffled = new In(arguments[1]);
		// final In inSorted = new In(arguments[2]);

		// final String[] partiallySortedNumbers =
		// inPartiallySorted.readAllStrings();
		// final Double[] partiallySortedNumbersDouble = new
		// Double[partiallySortedNumbers.length];

		// final double[] shuffledNumbers = inShuffled.readAllInts();
		// final double[] sortedNumbers = inSorted.readAllInts();

		// for (int i = 0; i != partiallySortedNumbers.length; i++)
		// partiallySortedNumbersDouble[i] =
		// Double.parseDouble(partiallySortedNumbers[i]);

		for (int i = 0; i < maxReps; i++) {
			final In inPartiallySorted = new In(arguments[0]);
			final Double[] partiallySortedNumbersDouble = readAllDoubles(inPartiallySorted);
			
			final Stopwatch stopwatch = new Stopwatch();
			Insertion.sort(partiallySortedNumbersDouble);
			estimatedTime = stopwatch.elapsedTime();
			
			averageOfTimers += estimatedTime;
			timers[i] = estimatedTime;
		}
		
		Arrays.sort(timers);
		
		int middle = timers.length / 2;
		double median = 0;

		if (timers.length % 2 == 0)
			median = (timers[middle] + timers[middle - 1]) / 2;
		else
			median = timers[middle];
		
		out.println("Sorted the '" + arguments[0] + "' Median = " + median + " Average = " + averageOfTimers / maxReps);
	}

	public static Double[] readAllDoubles(In in) {
		String[] fields = in.readAllStrings();
		Double[] vals = new Double[fields.length];
		for (int i = 0; i < fields.length; i++)
			vals[i] = Double.parseDouble(fields[i]);
		return vals;
	}

}

package pt.europeia.eda.stacks;

import static pt.europeia.eda.Tools.out;

import java.util.ArrayList;
import java.util.Arrays;

import pt.europeia.eda.Stopwatch;

public class StackPopTesterOlder {
	public static void main(String[] args) {

		int maxReps = 100;
		double estimatedTime = 0;

		for (int limit = 1, exponent = 0; exponent != 31; exponent++, limit *= 2) {

			double[] timers = new double[maxReps];

			double sumOfTimers = 0;

			for (int i = 0; i < maxReps; i++) {
				final Stack<Integer> stackOfInts = new Stack<Integer>();

				for (int j = 0; j < limit; j++)
					stackOfInts.push(i);

				final Stopwatch stopwatch = new Stopwatch();

				for (int j = 0; j < limit; j++)
					stackOfInts.pop();

				estimatedTime = stopwatch.elapsedTime();
				timers[i] = estimatedTime;
				sumOfTimers = sumOfTimers + timers[i];

			}


			double median = medianOf(timers);

			double averageOfTimers = sumOfTimers / maxReps;

			out.println("Pop " + limit + " times, Mediana = " + median + " Max = " + timers[timers.length - 1]
					+ " Min = " + timers[0] +" Sum = " + sumOfTimers + " Average = " + averageOfTimers);

		}
		
		
	}
	
	public static double medianOf(final double[] values) {
		final int middle = values.length / 2;

		Arrays.sort(values);

		if (values.length % 2 == 0)
			return (values[middle] + values[middle - 1]) / 2;
		else
			return values[middle];
	}
}

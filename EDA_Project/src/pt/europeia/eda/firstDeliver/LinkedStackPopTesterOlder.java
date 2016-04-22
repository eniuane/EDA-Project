package pt.europeia.eda.firstDeliver;

import static pt.europeia.eda.Tools.out;

import java.util.Arrays;

import pt.europeia.eda.Stopwatch;

public class LinkedStackPopTesterOlder {

	public static void main(String[] args) {

		int maxReps = 100;
		double estimatedTime = 0;

		for (int limit = 1, exponent = 0; exponent != 31; exponent++, limit *= 2) {

			double[] timers = new double[maxReps];

			double sumOfTimers = 0;

			for (int i = 0; i < maxReps; i++) {
				final LinkedStack<Integer> stackOfInts = new LinkedStack<Integer>();

				for (int j = 0; j < limit; j++)
					stackOfInts.push(i);

				final Stopwatch stopwatch = new Stopwatch();

				for (int j = 0; j < limit; j++)
					stackOfInts.pop();

				estimatedTime = stopwatch.elapsedTime();
				timers[i] = estimatedTime;
				sumOfTimers = sumOfTimers + timers[i];

			}

			Arrays.sort(timers);
			int middle = timers.length / 2;
			double median = 0;

			if (timers.length % 2 == 0)
				median = (timers[middle] + timers[middle - 1]) / 2;
			else
				median = timers[middle];

			double averageOfTimers = 0;

			averageOfTimers = sumOfTimers / maxReps;

			out.println("Pop " + limit + " times, Mediana = " + median + " Max = " + timers[timers.length - 1]
					+ " Min = " + timers[0] + " Sum = " + sumOfTimers + " Average = " + averageOfTimers);

		}
	}

}

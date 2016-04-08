package pt.europeia.eda.firstDeliver;

import static pt.europeia.eda.Tools.out;

import java.util.Arrays;

import pt.europeia.eda.Stopwatch;

public class LinkedStackTester {
	public static void main(String[] args) {

		final LinkedStack<Integer> linkedStackOfInts = new LinkedStack<Integer>();
		int maxReps = 100;
		double[] timers = new double[maxReps];
		long memory = 0;
		long[] memories = new long[maxReps];
		double estimatedTime = 0;

		for (int limit = 1, exponent = 0; exponent != 20; exponent++, limit *= 2) {

			for (int i = 0; i < maxReps; i++) {

				Runtime runtime = Runtime.getRuntime();
				final Stopwatch stopwatch = new Stopwatch();
				for (int j = 0; j < limit; j++)
					linkedStackOfInts.push(i);

				estimatedTime = stopwatch.elapsedTime();
				memory = runtime.totalMemory() - runtime.freeMemory();
				memories[i] = memory;
				timers[i] = estimatedTime;
			}

			Arrays.sort(timers);

			int middle = timers.length / 2;
			double median = 0;

			if (timers.length % 2 == 0)
				median = (timers[middle] + timers[middle - 1]) / 2;
			else
				median = timers[middle];

			double averageOfTimers = 0;
			double averageOfMemories = 0;
			double sumOfTimers = 0;
			double sumOfMemories = 0;
			

			for (int k = 0; k < maxReps; k++){
				sumOfTimers = sumOfTimers + timers[k];
				sumOfMemories = sumOfMemories +  memories[k];
			}
			
			averageOfTimers = sumOfTimers / maxReps;
			averageOfMemories = sumOfMemories / maxReps;
					
			out.println("Push " + limit + " times, Mediana = " + median + " Max = " + timers[timers.length - 1]
					+ " Min = " + timers[0] + " Sum = " + sumOfTimers + " Average = " + averageOfTimers
					+ " Average used memory " + averageOfMemories + " bytes");

		}
	}
}

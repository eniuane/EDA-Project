package pt.europeia.eda.secondDeliver;

import edu.princeton.cs.algs4.In;
import pt.europeia.eda.Stopwatch;

import static pt.europeia.eda.Tools.out;

import java.util.Arrays;

public class InsertionTester {

	public static void main(final String[] arguments) {

		double estimatedTime = 0;
		int maxReps = 1;
		double[] timers = new double[maxReps];
		double averageOfTimers = 0;
		
			for(int j = 0; j < arguments.length; j++){
				for (int i = 0; i < maxReps; i++) {
					final In inFile = new In(arguments[j]);
					final Double[] fileNumbersDouble = readAllDoubles(inFile);
					
					final Stopwatch stopwatch = new Stopwatch();
					Insertion.sort(fileNumbersDouble);
					estimatedTime = stopwatch.elapsedTime();
					
					averageOfTimers += estimatedTime;
					//out.println(averageOfTimers);
					timers[i] = estimatedTime;
				}
		
		
				Arrays.sort(timers);
				
				int middle = timers.length / 2;
				double median = 0;
		
				if (timers.length % 2 == 0)
					median = (timers[middle] + timers[middle - 1]) / 2;
				else
					median = timers[middle];
				
				out.println("Sorted the '" + arguments[j] + "' Median = " + median + " Average = " + averageOfTimers / maxReps);
				
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

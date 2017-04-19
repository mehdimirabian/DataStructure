//-----------------------------------------------------------------------------
// Name: Mohammad Mirabian
// CruzID: mmirabia
// StudentID# 1377020
// Date: 5/22/2015
// File Name: Simulation.java
// File Purpose: simulate Queue.java
//-----------------------------------------------------------------------------

import java.io.*;
import java.util.Scanner;

public class Simulation {

	// -----------------------------------------------------------------------------
	//
	// The following function may be of use in assembling the initial backup
	// and/or
	// storage queues. You may use it as is, alter it as you see fit, or delete
	// it
	// altogether.
	//
	// -----------------------------------------------------------------------------

	public static Job getJob(Scanner in) {
		String[] s = in.nextLine().split(" ");
		int a = Integer.parseInt(s[0]);
		int d = Integer.parseInt(s[1]);
		return new Job(a, d);
	}

	// -----------------------------------------------------------------------------
	//
	// The following stub for function main contains a possible algorithm for
	// this
	// project. Follow it if you like. Note that there are no instructions below
	// which mention writing to either of the output files. You must intersperse
	// those commands as necessary.
	//
	// -----------------------------------------------------------------------------

	public static void main(String[] args) throws IOException {
		Scanner input = null;
		PrintWriter report = null;
		PrintWriter trace = null;
		Queue storage = new Queue();
		int m, n, t;

		// 1. check command line arguments
		//
		if (args.length < 1) {
			System.err.println("Usage: Simulation fileIn fileOut");
			System.exit(1);
		}

		// 2. open files for reading and writing
		input = new Scanner(new File(args[0]));
		report = new PrintWriter(new FileWriter(args[0] + ".rpt"));
		trace = new PrintWriter(new FileWriter(args[0] + ".trc"));

		// 3. read in m jobs from input file
		String ss = input.nextLine();
		m = Integer.parseInt(ss);

		while (input.hasNextInt()) {
			storage.enqueue((Job) getJob(input));
		}
		trace.println("Trace file: " + args[0] + ".trc");
		trace.println(m + " Jobs:");
		trace.println(storage + "\n");

		// 4. run simulation with n processors for n=1 to n=m-1 {
		// 5. declare and initialize an array of n processor Queues and any
		// necessary storage Queues
		//
		// 6. while unprocessed jobs remain {
		//
		// 7. determine the time of the next arrival or finish event and
		// update time
		//
		// 8. complete all processes finishing now
		//
		// 9. if there are any jobs arriving now, assign them to a processor
		// Queue of minimum length and with lowest index in the queue array.
		//
		// 10. } end loop
		//
		// 11. compute the total wait, maximum wait, and average wait for
		// all Jobs, then reset finish times
		//
		// 12. } end loop
		//
		// 13. close input and output files

		for (n = 1; n < m; n++) {
			t = 0;

			Queue[] simulation = new Queue[n + 1];
			for (int i = 0; i < n + 1; i++) {
				simulation[i] = new Queue();
			}

			trace.println("*****************************");
			trace.println(n + " processor:");
			trace.println("*****************************");

			simulation[0] = storage;
			while (((Job) simulation[0].peek()).getFinish() == -1
					|| simulation[0].length() != m) {

				if (t == 0) {

					printTrace(trace, simulation, n, t);
					t = ((Job) simulation[0].peek()).getArrival();
					simulation[1].enqueue(simulation[0].dequeue());
					Job temp = (Job) simulation[1].peek();
					temp.computeFinishTime(t);
				} else if (((Job) simulation[0].peek()).getFinish() != -1) {
					int small = getIndex(simulation);
					t = ((Job) simulation[small].peek()).getFinish();
					simulation[0].enqueue(simulation[small].dequeue());
					printTrace(trace, simulation, n, t);

				} else {

					printTrace(trace, simulation, n, t);
					int small = getIndex(simulation);

					if (simulation[small].length() == 0) {
						t = ((Job) simulation[0].peek()).getArrival();
						simulation[small].enqueue(simulation[0].dequeue());
						Job temp = (Job) simulation[small].peek();
						temp.computeFinishTime(t);

						printTrace(trace, simulation, n, t);

						small = getIndex(simulation);
						t = ((Job) simulation[small].peek()).getFinish();
						simulation[0].enqueue(simulation[small].dequeue());
						printTrace(trace, simulation, n, t);

						small = getIndex(simulation);
						t = ((Job) simulation[0].peek()).getArrival();
						simulation[small].enqueue(simulation[0].dequeue());
						temp = (Job) simulation[small].peek();
						temp.computeFinishTime(t);
						printTrace(trace, simulation, n, t);

						t = ((Job) simulation[small + 1].peek()).getFinish();
						simulation[0].enqueue(simulation[small + 1].dequeue());
						printTrace(trace, simulation, n, t);

						small = getIndex(simulation);
						t = ((Job) simulation[small - 1].peek()).getFinish();
						simulation[0].enqueue(simulation[small - 1].dequeue());
						printTrace(trace, simulation, n, t);

					}

					else if (((Job) simulation[0].peek()).getArrival() <= ((Job) simulation[small]
							.peek()).getFinish()) {
						t = ((Job) simulation[0].peek()).getArrival();
						simulation[small].enqueue(simulation[0].dequeue());

						printTrace(trace, simulation, n, t);

						t = ((Job) simulation[small].peek()).getFinish();
						simulation[0].enqueue(simulation[small].dequeue());
						Job temp = (Job) simulation[small].peek();
						temp.computeFinishTime(t);

						printTrace(trace, simulation, n, t);
					} else {
						Job temp = (Job) simulation[small].peek();
						temp.computeFinishTime(t);

						printTrace(trace, simulation, n, t);

						t = ((Job) simulation[small].peek()).getFinish();
						simulation[0].enqueue(simulation[small].dequeue());

						printTrace(trace, simulation, n, t);

					}

				}

			}

			if (n == 1) {
				report.println("Report file: " + args[0] + ".rpt");
				report.println(m + " Jobs:");
				report.println(simulation[0] + "\n");
				report.println("************************************************");
			}
			float averageWait = 0;
			int totalWait = 0;
			int maxWait = 0;
			int max = 0;
			Queue holdQueue = new Queue();
			while (simulation[0].length() != 0) {
				max = ((Job) simulation[0].peek()).getWaitTime();
				if (maxWait < max) {
					maxWait = max;
				}
				totalWait += ((Job) simulation[0].peek()).getWaitTime();
				holdQueue.enqueue((Job) simulation[0].dequeue());
			}
			averageWait = (float) totalWait / m;
			report.println(n + " processor: totalWait=" + totalWait
					+ " maxWait=" + maxWait + " averageWait=" + averageWait);

			while (holdQueue.length() != 0) {
				((Job) holdQueue.peek()).resetFinishTime();
				storage.enqueue((Job) holdQueue.dequeue());
			}
			trace.println();

		}

		input.close();
		report.close();
		trace.close();

	}

	public static void printTrace(PrintWriter trace, Queue[] s, int n, int time) {
		trace.println("time = " + time);
		for (int i = 0; i < n + 1; i++) {
			trace.println(i + ": " + s[i]);
		}
	}

	public static int getIndex(Queue[] s) {
		int index = 0;
		if (((Job) s[index].peek()).getFinish() == -1) {
			index = 1;
		}
		for (int i = 1; i < s.length; i++) {
			if (s[i].length() < s[index].length()) {
				if (s[i].length() == s[index].length()) {
					index = index;
				} else {
					index = i;
				}
			} else if (s[i].length() < s[index].length()
					&& ((Job) s[index].peek()).getFinish() != -1) {// <
																	// ((Job)s[index].peek()).getFinish()){
				index = index;
			}
		}
		return index;
	}

}

/********************Starter Code
 * 
 * This class contains some examples of required inputs and outputs
 * 
 * @author alice toniolo
 *
 * we assume parameters are valid, no need to check
 * 
 */


public class P1main {

	public static final int NOT_IMPLEMENTED=-200;
	public static final int NOT_FOUND=-1;
	public static final int NOT_TERMINATED=-100;

	public static void main(String[] args) {

		if(args.length<4) {
			System.out.println("usage: java P1main <AStar|BestF|Alt|AStarOpt|BestFOpt> <D> <r,c> <coverage> [<verbose>]");
			System.exit(1);
		}

		//process input examples 
		boolean verbose=false;
		if(args[args.length-1].equals("verbose")) {
			verbose=true;
		}
		String algo=args[0];
		int d=Integer.parseInt(args[1]);
		String start=args[2];
		int coverage=Math.round(d*d*Integer.parseInt(args[3])/100);
		int time_limit=30; //run at most for 30s
		//run your search algorithm 
		int path_cost=runSearch(algo, d, start, coverage, verbose, time_limit);


		/*
		 * The system must print the following information from your search methods
		 * All code below is for demonstration purposes only
		 */

		//Example: java P1main BestF 4 0,0 35



		if(verbose) {
			//assume 
			path_cost=13;
			if(path_cost>=0) {//path found

				// 1) Print the Frontier at each step before polling the node, using a brief representation of the state only, formatting is up to you

				String frontier_string="";

				//starting point (0,0) 
				//insert node in the frontier, then print the frontier:
				frontier_string="[(0,0):12]";

				System.out.println(frontier_string);

				//extract (0,0) 
				//insert successors in the frontier (3,0), (0,3), (2,2), then print the frontier,  and repeat for all steps until a path is found or not 


				frontier_string="[(3,0):9, (0,3):9, (2,2):9]\n"
						+ "[(3,3):6, (1,2):6, (2,2):9, (0,3):9]\n"
						+ "[(0,3):3, (1,1):3, (1,2):6, (2,2):9, (0,3):9]\n"
						+ "[(2,1):0, (1,1):3, (1,2):6, (2,2):9, (0,3):9]";

				System.out.println(frontier_string);
			}

			// 2) three lines representing the number of nodes explored, and the path and length of the path if any


			int n_explored=5;
			System.out.println(n_explored);


			String path="";
			int path_length=0;
			if(path_cost==NOT_FOUND || path_cost==NOT_TERMINATED) {
				//do nothing
			}else {
				path="(0,0)(3,0)(3,3)(0,3)(2,1)";
				path_length=5;
				System.out.println(path);
				System.out.println(path_length);
			}


		}

		//3) the path cost 

		System.out.println(path_cost);

	}

	private static int runSearch(String algo, int d, String start, int coverage, boolean verbose, int time_limit) {

		switch(algo) {

		case "BestF": //run BestF
			return NOT_IMPLEMENTED;
		case "AStar": //run AStar
			return NOT_IMPLEMENTED;
		case "BestFOpt": //run BestF with additional heuristic
			return NOT_IMPLEMENTED;
		case "AStarOpt": //run AStar with additional heuristic
			return NOT_IMPLEMENTED;

		}
		return NOT_IMPLEMENTED;

	}




}

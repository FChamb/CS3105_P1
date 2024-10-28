import java.util.*;

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
		int coverage=d*d*Integer.parseInt(args[3])/100;
		int time_limit=30; //run at most for 30s
		//run your search algorithm 
		int path_cost=runSearch(algo, d, start, coverage, verbose, time_limit);
		System.out.println(path_cost);

	}

	private static int runSearch(String algo, int d, String start, int coverage, boolean verbose, int time_limit) {

		switch(algo) {

		case "BestF": //run BestF
            BestFirstSearch bestF = new BestFirstSearch(d, new int[]{Integer.parseInt(start.split(",")[0]), Integer.parseInt(start.split(",")[1])}, coverage, verbose, time_limit);
			return bestF.search();
		case "AStar": //run AStar
            AStarSearch aStar = new AStarSearch(d, new int[]{Integer.parseInt(start.split(",")[0]), Integer.parseInt(start.split(",")[1])}, coverage, verbose, time_limit);
			return aStar.search();
		case "BestFOpt": //run BestF with additional heuristic
            BestFirstSearchOpt bestFOpt = new BestFirstSearchOpt(d, new int[]{Integer.parseInt(start.split(",")[0]), Integer.parseInt(start.split(",")[1])}, coverage, verbose, time_limit);
			return bestFOpt.search();
		case "AStarOpt": //run AStar with additional heuristic
            AStarSearchOpt aStarOpt = new AStarSearchOpt(d, new int[]{Integer.parseInt(start.split(",")[0]), Integer.parseInt(start.split(",")[1])}, coverage, verbose, time_limit);
            return aStarOpt.search();

		}
		return NOT_IMPLEMENTED;

	}
}

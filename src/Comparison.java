import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalTime;

public class Comparison {
    public static void main(String[] args) {
        Comparison comparison = new Comparison();
    }

    public Comparison() {
        File file = new File("../comparison_data/data.csv");
        try {
            FileWriter writer = new FileWriter(file);
            writer.write("dimension,coverage,timeBF,costBF,nodesBF,timeAS,costAS,nodesAS\n");
            runComparison(writer);
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }

    public void runComparison(FileWriter writer) {
        for (int dimensions = 3; dimensions < 25; dimensions++) {
            int prev_cov = -1;
            for (int coverage = 1; coverage <= 100; coverage++) {
                int cov = dimensions*dimensions*coverage/100;
                if (cov == 0 || prev_cov == cov) {
                    continue;
                }
                System.out.println(dimensions + ", " + cov);
                BestFirstSearch bestF = new BestFirstSearch(dimensions, new int[]{0, 0}, cov, false, 30);
                AStarSearch aStar = new AStarSearch(dimensions, new int[]{0, 0}, cov, false, 30);
                LocalTime timeStartBF = LocalTime.now();
                int costBF = bestF.search();
                LocalTime timeBF = LocalTime.now();
                LocalTime timeStartAS = LocalTime.now();
                int costAS = aStar.search();
                LocalTime timeAS = LocalTime.now();
                try {
                    writer.write(dimensions + "," + coverage + "," + Duration.between(timeStartBF, timeBF).toMillis() + "," + costBF + "," + bestF.getNodes() + "," + Duration.between(timeStartAS, timeAS).toMillis() + "," + costAS + "," + aStar.getNodes() + "\n");
                    writer.flush();
                } catch (IOException e) {
                    System.out.println("IO Exception: " + e.getMessage());
                }
                prev_cov = cov;
            }
        }
        try {
            writer.close();
        } catch (IOException e) {
            System.out.println("IO Exception: " + e.getMessage());
        }
    }
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Optional;

public class Day1 {
    public static void main(String[] args){
        File inputFile = new File(args[0]);
        boolean debug = Boolean.parseBoolean(args[1]);
        Optional<Integer> part1Solution = part1(inputFile, debug);
        if (!part1Solution.isPresent()){
            System.out.println("an error in part 1 occurred");
        }
        Optional<Integer> part2Solution = part2(inputFile, debug);
        if (!part2Solution.isPresent()){
            System.out.println("an error in part 2 occurred");
        }
        if(part1Solution.isPresent() && part2Solution.isPresent()){
            System.out.println("Part 1: " + part1Solution.get());
            System.out.println("Part 2: "+ part2Solution.get());
        }
    }

    private static Optional<Integer> part1 (File inputFile, boolean debug){
        int sum = 0;
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            while (br.ready()){
                count++;
                String moduleString = br.readLine();
                int moduleWeight = Integer.parseInt(moduleString);
                int fuelNeeded = moduleWeight/3 - 2;
                sum += fuelNeeded;
                if(debug){
                    System.out.println(count);
                    System.out.println(moduleString);
                    System.out.println(sum);
                }
            }
            return Optional.of(sum);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static Optional<Integer> part2(File inputFile, boolean debug){
        int sum = 0;
        int count = 0;
        try{
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            while (br.ready()){
                count++;
                String moduleString = br.readLine();
                int moduleWeight = Integer.parseInt(moduleString);
                int fuelNeeded = calculateNeededFuel(moduleWeight);
                sum += fuelNeeded;
                if(debug){
                    System.out.println(count);
                    System.out.println(moduleString);
                    System.out.println(sum);
                }
            }
            return Optional.of(sum);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    private static int calculateNeededFuel(int weight) {
        if (weight <= 0) {
            return 0;
        } else {
            int neededFuel = weight / 3 - 2;
            if(neededFuel <=0 ){
                return 0;
            }
            return neededFuel + calculateNeededFuel(neededFuel);
        }
    }
}

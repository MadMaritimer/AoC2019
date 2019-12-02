import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Optional;

public class Day2 {
    public static void main(String[] args){
        File inputFile = new File(args[0]);
        boolean debug = Boolean.parseBoolean(args[1]);
        int[] programIntArray =new int[1];
        try {
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String intCodeString = br.readLine();
            String[] stringArray = intCodeString.split(",");
            programIntArray = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("error reading input, exiting...");
            System.exit(0);
        }
        if (programIntArray.length<10){
            System.out.println("error reading input, exiting...");
            System.exit(0);
        }
        Optional<Integer> part1Solution = part1(programIntArray, debug);
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

    public static Optional<Integer> part1(int[] programIntArray,  boolean debug){
        try{
            int cursor = 0;
            int opcode = programIntArray[cursor];
            int loc1;
            int loc2;
            int operand1;
            int operand2;
            int locStore;
            while (opcode != 99){
                loc1 = programIntArray[cursor+1];
                loc2 = programIntArray[cursor+2];
                locStore = programIntArray[cursor+3];
                operand1 = programIntArray[loc1];
                operand2 = programIntArray[loc2];
                programIntArray[locStore] = opcode == 1 ? operand1 + operand2 : operand1 * operand2;
                cursor += 4;
                opcode = programIntArray[cursor];
            }
            return Optional.of(programIntArray[0]);
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public static Optional<Integer> part2(File inputFile, boolean debug){
        try{
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            String intCodeString = br.readLine();
            String[] stringArray = intCodeString.split(",");
            for (int i = 0; i<100; i++){
                for (int j = 0; j< 100; j++){
                    int[] programIntArray = Arrays.stream(stringArray).mapToInt(Integer::parseInt).toArray();
                    programIntArray[1] = i;
                    programIntArray[2] = j;
                    Optional<Integer> outputOpt = part1(programIntArray, debug);
                    if(!outputOpt.isPresent()){
                        continue;
                    }
                    if (outputOpt.get() == 19690720){
                        int sol2 = 100 * i + j;
                        return Optional.of(sol2);
                    }
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            return Optional.empty();
        }
        return Optional.empty();
    }


}

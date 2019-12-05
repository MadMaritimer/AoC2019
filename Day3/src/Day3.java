import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.*;

public class Day3 {
    public static void main(String[] args){
        File inputFile = new File(args[0]);
        boolean debug = Boolean.parseBoolean(args[1]);
        int part1Sol = part1(inputFile);
        System.out.println("Part 1: "+part1Sol);
        int part2Sol = part2(inputFile);
        System.out.println("Part 2: "+part2Sol);

    }

    public static int part1(File input){
        int minDistance = Integer.MAX_VALUE;
        try(BufferedReader br = new BufferedReader(new FileReader(input))){
            String wire1 = br.readLine();
            Map<Integer, Set<Integer>> wire1Map = mapWire(wire1);
            String wire2 = br.readLine();
            Map<Integer, Set<Integer>> wire2Map = mapWire(wire2);

            Map<Integer, Set<Integer>> intersects = new HashMap<>();
            for (Integer xCoOrd : wire1Map.keySet()) {
                if (wire2Map.containsKey(xCoOrd)){
                    for (Integer yCoOrd : wire1Map.get(xCoOrd)) {
                        if (wire2Map.get(xCoOrd).contains(yCoOrd)){
                            saveStep(intersects, xCoOrd, yCoOrd);
                        }
                    }
                }
            }

            for (Integer x : intersects.keySet()) {
                for(Integer y : intersects.get(x)){
                    int distance = Math.abs(x) + Math.abs(y);
                    minDistance = Math.min(distance, minDistance);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return minDistance;
    }

    public static int part2(File input){
        int minSteps = Integer.MAX_VALUE;
        try(BufferedReader br = new BufferedReader(new FileReader(input))){
            String wire1 = br.readLine();
            List<CoordTuple> wire1Map = mapWirePart2(wire1);
            String wire2 = br.readLine();
            List<CoordTuple> wire2Map = mapWirePart2(wire2);

            List<CoordTuple> intersects = new ArrayList<>();
            for (CoordTuple point : wire1Map) {
                if (wire2Map.contains(point)){
                    point.stepCount += wire2Map.get(wire2Map.indexOf(point)).stepCount;
                    intersects.add(point);
                }
            }

           for(CoordTuple intersect : intersects){
               minSteps = Math.min(minSteps, intersect.stepCount);
           }
        }catch (Exception e){
            e.printStackTrace();
        }
        return minSteps;
    }

    private static Map<Integer, Set<Integer>> mapWire(String wire){
        Map<Integer, Set<Integer>> wireCoords = new HashMap<>();
        String[] wire1Steps = wire.split(",");
        int x1 = 0, y1 = 0, stepCount = 0;
        for (String step : wire1Steps) {
            char direction = step.charAt(0);
            int magnitude = Integer.parseInt(step.substring(1));
            while (magnitude > 0) {
                ++stepCount;
                switch (direction) {
                    case 'R':
                        x1++;
                        saveStep(wireCoords, x1, y1);
                        break;
                    case 'L':
                        x1--;
                        saveStep(wireCoords, x1, y1);
                        break;
                    case 'U':
                        y1++;
                        saveStep(wireCoords, x1, y1);
                        break;
                    case 'D':
                        y1--;
                        saveStep(wireCoords, x1, y1);
                        break;
                    default:
                        System.out.println("invalid direction");
                        System.exit(1);
                }
                magnitude--;
            }
        }
        return wireCoords;
    }

    private static List<CoordTuple> mapWirePart2(String wire){
        List<CoordTuple> wireCoords = new ArrayList<>();
        String[] wire1Steps = wire.split(",");
        int x1 = 0, y1 = 0, stepCount = 0;
        for (String step : wire1Steps) {
            char direction = step.charAt(0);
            int magnitude = Integer.parseInt(step.substring(1));
            while (magnitude > 0) {
                ++stepCount;
                switch (direction) {
                    case 'R':
                        x1++;
                        saveStepPart2(wireCoords, x1, y1, stepCount);
                        break;
                    case 'L':
                        x1--;
                        saveStepPart2(wireCoords, x1, y1, stepCount);
                        break;
                    case 'U':
                        y1++;
                        saveStepPart2(wireCoords, x1, y1, stepCount);
                        break;
                    case 'D':
                        y1--;
                        saveStepPart2(wireCoords, x1, y1, stepCount);
                        break;
                    default:
                        System.out.println("invalid direction");
                        System.exit(1);
                }
                magnitude--;
            }
        }
        return wireCoords;
    }

    private static void saveStep(Map<Integer, Set<Integer>> coords, int x, int y){
        if (coords.containsKey(x)){
            coords.get(x).add(y);
        } else{
            coords.put(x, new HashSet<>(Collections.singletonList(y)));
        }
    }

    private static void saveStepPart2(List<CoordTuple> coords, int x, int y, int steps){
        coords.add(new CoordTuple(x,y,steps));
    }

    static class CoordTuple{
        int x;
        int y;
        int stepCount;

        public CoordTuple(int x, int y, int stepCount){
            this.x = x;
            this.y = y;
            this.stepCount = stepCount;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }

        public int getStepCount() {
            return stepCount;
        }

        public void setStepCount(int stepCount) {
            this.stepCount = stepCount;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CoordTuple that = (CoordTuple) o;
            return x == that.x &&
                y == that.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }

    }
}

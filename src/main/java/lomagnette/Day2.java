package lomagnette;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

/**
 * @author lma
 * @date 04/12/2023
 */
public class Day2 {

    public static final int RED = 12;
    public static final int GREEN = 13;
    public static final int BLUE = 14;

    public static void main(String[] args) throws IOException {
        var data = Arrays.stream(Files.readString(Path.of("/Users/lma/sandbox/lab-java/AdventOfCode/src/main/resources/day-2.txt"))
                .split("\n"))
                .filter(s -> !s.isBlank())
                .toList();
        var parsedGames = data.stream().map(Game::parseGame).toList();
        var validGames = parsedGames.stream().filter(Day2::isGameValid).toList();
        List<Integer> list = validGames.stream().map(game -> game.id).toList();
        System.out.println("PART 1:" + list.stream().mapToInt(Integer::intValue).sum());
        System.out.println("PART 2:" + parsedGames.stream().map(Game::optimizedDrawValue).reduce(Integer::sum).orElse(0));
    }

    private static boolean isGameValid(Game game) {
        return game.draws.stream().filter(Day2::isDrawValid).toList().size() == game.draws.size();
    }

    private static boolean isDrawValid(Draw draw) {
        return draw.red <= RED && draw.green <= GREEN && draw.blue <= BLUE;
    }


}

class Game{
    public int id;
    public List<Draw> draws = new ArrayList<>();

    public Draw optimizedDraw;
    public  static Game parseGame(String value){
        var game = new Game();
        var split = value.split(":");
        var id = Integer.parseInt(split[0].replace("Game ", ""));
        game.id = id;
        var draws = split[1].split(";");
        for (String draw : draws) {
            game.draws.add(Draw.parse(draw));
        }

        game.initOptimizedDraw();

        return game;
    }

    @Override
    public String toString() {
        return "Game " + id + ": "
                + String.join("; ",draws.stream().map(Draw::toString).toList());
    }

    public void initOptimizedDraw() {
        var optimizedDraw = new Draw();
        optimizedDraw.red = getMaxValue(draw -> draw.red);
        optimizedDraw.blue = getMaxValue(draw -> draw.blue);
        optimizedDraw.green = getMaxValue(draw -> draw.green);
        this.optimizedDraw = optimizedDraw;
    }

    public int optimizedDrawValue(){
        return optimizedDraw.red * optimizedDraw.green * optimizedDraw.blue;
    }

    private Integer getMaxValue(Function<Draw, Integer> colorFunction) {
        return this.draws.stream().map(colorFunction).max(Integer::compareTo)
                .orElse(0);
    }

}

class Draw{
    public int red, green, blue = 0;

    public static Draw parse(String value){
        var draw = new Draw();
        var split = List.of(value.split(","));
        split.forEach(draw::setColor);

        return draw;
    }

    private void setColor(String value ){
        var split = value.split(" ");
        var color = split[2];
        var number = Integer.parseInt(split[1]);
        switch (color){
            case "red" -> red = number;
            case "green" -> green = number;
            case "blue"-> blue = number;
        }
    }

    @Override
    public String toString() {
        var toString ="";
        toString += blue > 0 ? blue +" blue, " : "";
        toString += green > 0 ? green +" green, " : "";
        toString += red > 0 ? red +" red " : "";
        return toString;
    }
}

package kolok2.zad1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Partial exam II 2016/2017
 */
public class FootballTableTest {
    public static void main(String[] args) throws IOException {
        FootballTable table = new FootballTable();
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        reader.lines()
                .map(line -> line.split(";"))
                .forEach(parts -> table.addGame(parts[0], parts[1],
                        Integer.parseInt(parts[2]),
                        Integer.parseInt(parts[3])));
        reader.close();
        System.out.println("=== TABLE ===");
        System.out.printf("%-19s%5s%5s%5s%5s%5s\n", "Team", "P", "W", "D", "L", "PTS");
        table.printTable();
    }
}

class Team{
    String name;
    int wins;
    int loses;
    int draws;
    int dadeniGolovi;
    int primeniGolovi;

    public Team(String name) {
        this.name = name;
        this.wins = 0;
        this.loses = 0;
        this.draws = 0;
        this.dadeniGolovi = 0;
        this.primeniGolovi = 0;
    }

    public Team() {

    }

    public String getName() {
        return name;
    }

    public int getWins() {
        return wins;
    }

    public int getLoses() {
        return loses;
    }

    public int getDraws() {
        return draws;
    }

    public int getDadeniGolovi() {
        return dadeniGolovi;
    }

    public int getPrimeniGolovi() {
        return primeniGolovi;
    }

    public int points(){
        return (wins * 3) + draws;
    }

    public int goalDifference(){
        return dadeniGolovi - primeniGolovi;
    }

    public int gamesPlayed(){
        return wins + draws + loses;
    }

    @Override
    public String toString() {
        return String.format("%-15s%5d%5d%5d%5d%5d", name, gamesPlayed()
                ,wins,draws,loses,points());
    }
}

class FootballTable{
    TreeMap<String, Team> mapByTeamName;

    public FootballTable() {
        mapByTeamName = new TreeMap<>();
    }

    public Team check(String name){
        if (mapByTeamName.containsKey(name)){
            return mapByTeamName.get(name);
        }else {
            Team t = new Team(name);
            mapByTeamName.put(name, t);
            return t;
        }
    }


    public void addGame(String homeTeam, String awayTeam, int homeGoals, int awayGoals) {
        Team home = check(homeTeam);
        Team away = check(awayTeam);
        if (homeGoals > awayGoals){
            home.wins += 1;
            away.loses += 1;
        } else if (awayGoals > homeGoals) {
            home.loses += 1;
            away.wins += 1;
        }else {
            home.draws += 1;
            away.draws += 1;
        }
        away.dadeniGolovi += awayGoals;
        away.primeniGolovi += homeGoals;
        home.dadeniGolovi += homeGoals;
        home.primeniGolovi += awayGoals;
        mapByTeamName.putIfAbsent(homeTeam , home);
        mapByTeamName.putIfAbsent(awayTeam , away);
        mapByTeamName.computeIfPresent(homeTeam, (k, v) -> v = home);
        mapByTeamName.computeIfPresent(awayTeam, (k, v) -> v = away);
    }

    public void printTable() {
        Comparator comparator = Comparator.comparing(Team::points)
                .thenComparing(Team::goalDifference).reversed()
                .thenComparing(Team::getName);
         List <Team>teams = (List<Team>) mapByTeamName.values().stream().sorted(comparator).collect(Collectors.toList());
        IntStream.range(0, mapByTeamName.size()).forEach(i -> {
            System.out.printf("%2d. %s\n",i+1, teams.get(i).toString());
        });
    }
}


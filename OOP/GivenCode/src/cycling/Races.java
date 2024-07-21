
package cycling;

import java.util.List;
import java.util.Map;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * Represents a cycling race.
 * This class contains information about the teams participating in the race, the name, stages, race ID, and description.
 * It also provides methods to retrieve and update this information. And other methods for dealing with the different classifications.
 */

public class Races {
    private Team[] TeamsArray;
    private String Racename;
    private Stages[] StagesArray;
    private int StageId;
    private String Description;
    private int RaceId;

    /**
     * Constructs a Races object with the given race ID, name, and description.
     *
     * @param raceId      The unique identifier for the race.
     * @param raceName    The name of the race.
     * @param description A description of the race.
     */

    public Races(int id, String name, String Description) {
        this.RaceId = id;
        this.Racename = name;
        this.Description = Description;
        
    }

    // getters and setters for all attributes

    public int getRaceId() {
        return RaceId;
    }

    public void RaceId(int GivenRaceId) {
        this.RaceId = GivenRaceId;
    }

    public Team[] getTeams() {
        return TeamsArray;
    }

    public void setTeams(Team[] TeamsArray) {
        this.TeamsArray = TeamsArray;
    }

    public String getRacename() {
        return Racename;
    }

    public void setRacename(String Racename) {
        this.Racename = Racename;
    }

    public Stages[] getStages() {
        return StagesArray;
    }

    public void setStages(Stages[] stages) {
        this.StagesArray = stages; 
    }


    public int getId() {
        return StageId;
    }

    public void setId(int StageId) {
        this.StageId = StageId;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    // returns the number of stages in the race.
    public int getTotalStages() {
        if (StagesArray != null) {
            return StagesArray.length;
        } else {
            return 0;
        }
    }

    // Checks if that team is in the array, if not then it is appened to the List copy of the array, which is then reverted back to array.
    public void addTeam(Team team) {
        if (TeamsArray == null) {
            TeamsArray = new Team[] { team };
        } else {
            List<Team> teamList = new ArrayList<>(Arrays.asList(TeamsArray));
            teamList.add(team);
            TeamsArray = teamList.toArray(new Team[0]);
        }
    }


    public void removeTeam(Team team) {
        // Checking if TeamsArray is not null to avoid NullPointerException
        if (TeamsArray != null) {
            // Initializing index to -1 to indicate that the team was not found initially
            int index = -1;
            
            // Looping through the TeamsArray to find the index of the team to be removed
            for (int i = 0; i < TeamsArray.length; i++) {
                // Checking if the current element in TeamsArray is equal to the team to be removed
                if (TeamsArray[i].equals(team)) {
                    // Setting index to the current value of i if team is found
                    index = i;
                    // Exiting the loop since the team is found
                    break;
                }
            }
            
            // If the team was found
            if (index != -1) {
                // Creating a new array to hold the teams after removing the specified team
                Team[] newTeams = new Team[TeamsArray.length - 1];
                
                // Copying the teams before the removed team into the new array
                for (int i = 0, j = 0; i < TeamsArray.length; i++) {
                    // Skipping the element at the index where the team is to be removed
                    if (i != index) {
                        newTeams[j++] = TeamsArray[i];
                    }
                }
                
                // Assigning the new array to TeamsArray, effectively removing the specified team
                TeamsArray = newTeams;
            }
        }
    }
    
    // Checks if that stage is in the array, if not then it is appened to the List copy of the array, which is then reverted back to array.

    public void addStage(Stages stage) {
        if (this.StagesArray == null) {
            this.StagesArray = new Stages[] { stage };
        }
        else {
            List<Stages> stageList = new ArrayList<>(Arrays.asList(this.StagesArray));
            stageList.add(stage);
            this.StagesArray = stageList.toArray(new Stages[0]);
        }
    }

    
    public void removeStage(Stages stage) {
        // Check if StagesArray is not null to avoid NullPointerException
        if (this.StagesArray != null) {
            // Initialize index to -1 to indicate that the stage was not found initially
            int index = -1;
            
            // Loop through the StagesArray to find the index of the stage to be removed
            for (int i = 0; i < this.StagesArray.length; i++) {
                // Check if the current element in StagesArray is equal to the stage to be removed
                if (this.StagesArray[i].equals(stage)) {
                    // Set index to the current value of i if stage is found
                    index = i;
                    // Exit the loop since the stage is found
                    break;
                }
            }
            
            // If the stage was found
            if (index != -1) {
                // Create a new array to hold the stages after removing the specified stage
                Stages[] newStages = new Stages[this.StagesArray.length - 1];
                
                // Copy the stages before the removed stage into the new array
                for (int i = 0, j = 0; i < this.StagesArray.length; i++) {
                    // Skip the element at the index where the stage is to be removed
                    if (i != index) {
                        // Assign the current element from StagesArray to newStages at index j
                        newStages[j++] = this.StagesArray[i];
                        // Increment j to move to the next index in newStages
                        j++;
                    }
                }
                
                // Assign the new array to StagesArray, effectively removing the specified stage
                this.StagesArray = newStages;
            }
        }
    }

    // If team id matches the one in the Teams array, the Team name is returned.
    public String getTeamName(int teamid) {
        if (TeamsArray != null) {
            for (Team team : TeamsArray) {
                if (team.getId() == teamid) {
                    return team.getTeamName();
                }
            }
        }
        return null;
    }

    public List<List<Object>> AdjustResultsList(List<List<Object>> originalList) {
        originalList.remove(0);
        return originalList;

    }

    public List<Map<Integer,Integer>> GetAllStagesPoints() {
        // Iterating thorugh all the stages, putting all the results into one big List.
        // Create a new list to store all sublists
        List<Map<Integer,Integer>> allStageMaps = new ArrayList<>();
        
        // Iterate through the array of Stages objects
        for (Stages stage : StagesArray) {
            // Getting the order ids and the points, and adding them to a HashMap
            int CurrentStageId = stage.getId();
            int[] ResultsForStage = stage.getRidersPointsInStageMethod(CurrentStageId);
            int[] RidersRankForStage = stage.getRidersRankInStageMethod(CurrentStageId);
            
            Map<Integer, Integer> StageMap = new HashMap<Integer,Integer>();

            int CounterForIndexing = 0;
            for (int RiderId : RidersRankForStage) {
                StageMap.put(RiderId, ResultsForStage[CounterForIndexing]);
                CounterForIndexing++;
            }

            allStageMaps.add(StageMap);
            

        }

        return allStageMaps;
    }

    public List<Map<Integer,Integer>> GetAllStagesMountainPoints() {
        // Iterating thorugh all the stages, putting all the results into one big List.
        // Create a new list to store all sublists
        List<Map<Integer,Integer>> allStageMaps = new ArrayList<>();
        
        
        // Iterate through the array of Stages objects
        for (Stages stage : StagesArray) {
            // Getting the order ids and the points, and adding them to a HashMap
            int[] MountainResultsForStage = stage.getRidersMountainPointsInStage();
            int CurrentStageId = stage.getId();
            int[] RidersRankForStage = stage.getRidersRankInStageMethod(CurrentStageId);
            
            Map<Integer, Integer> StageMap = new HashMap<Integer,Integer>();

            int CounterForIndexing = 0;
            for (int RiderId : RidersRankForStage) {
                StageMap.put(RiderId, MountainResultsForStage[CounterForIndexing]);
                CounterForIndexing++;
            }

            allStageMaps.add(StageMap);
            
        }

        return allStageMaps;
    }

    public List<List<Object>> GetAllStagesData() {
        // Iterating thorugh all the stages, putting all the results into one big List.
        // Create a new list to store all sublists
        List<List<Object>> allStageSublists = new ArrayList<>();

        // Iterate through the array of Stages objects
        for (Stages stage : StagesArray) {
            // Access the attribute containing the list of lists
            List<List<Object>> StageData = stage.getResultsList();
            // This is to check that if any stages dont have any results, then returns an empty list
            if (StageData.size() == 0) {
                return null;
            }

            List<List<Object>> ResultsCopy = new ArrayList<>();
            if (StageData.size() != 0) {

                for (List<Object> sublist : StageData) {

                    if (sublist.size() != 3) {
                        ResultsCopy.add(sublist);
                    }
                }
            }
            StageData = ResultsCopy;

            // Iterating through the sublists of the Stage
            for (List<Object> StageSublists : StageData) {
                // Adding sublists to main data List
                allStageSublists.add(StageSublists);
            }
        }

        return allStageSublists;
    }

    public LocalTime[] getGeneralClassificationTimesInRacemethod() {
        // Creating a List of all the sublists from each stage
        List<List<Object>> RankingList = GetAllStagesData();

        if (RankingList == null) {
            return null;
        }

        Map<Integer, LocalTime> racerTotalTime = new HashMap<>();
        
        // Calculate total time for each racer
        for (List<Object> data : RankingList) {
            int racerID = (int) data.get(1);
            LocalTime Time = (LocalTime) data.get(4);
            racerTotalTime.put(racerID, racerTotalTime.getOrDefault(racerID, LocalTime.of(0, 0,0)).plusNanos(Time.toNanoOfDay()));
        }
        
        List<LocalTime> ListOfElapsedTimesSummed = new ArrayList<>();

        // Add all values from the dictionary to the ArrayList
        for (Map.Entry<Integer, LocalTime> entry : racerTotalTime.entrySet()) {
            ListOfElapsedTimesSummed.add(entry.getValue());
        }

        Collections.sort(ListOfElapsedTimesSummed);

        // Converting the list to int array.
        LocalTime[] SortedTimesArray = new LocalTime[ListOfElapsedTimesSummed.size()];
        
        for (int i = 0; i < ListOfElapsedTimesSummed.size(); i++) {
            SortedTimesArray[i] = ListOfElapsedTimesSummed.get(i);
        }

        if (SortedTimesArray.length == 0) {
            return null;
        } else {
            return SortedTimesArray;
        }
    } 

    public int[] getRidersGeneralClassificationRankMethod() {
        // Creating a List of all the sublists from each stage
        List<List<Object>> RankingList = GetAllStagesData();

        Map<Integer, LocalTime> racerTotalTime = new HashMap<>();
        
        // Calculate total time for each racer
        for (List<Object> data : RankingList) {
            int racerID = (int) data.get(1);
            LocalTime time = (LocalTime) data.get(4);
            racerTotalTime.put(racerID, racerTotalTime.getOrDefault(racerID, LocalTime.of(0, 0)).plusSeconds(time.toSecondOfDay()));
        }
        
        // Sort racers based on total time
        List<Integer> sortedRacerIDs = new ArrayList<>(racerTotalTime.keySet());
        sortedRacerIDs.sort(Comparator.comparing(racerTotalTime::get));

        // Converting the list to int array.
        int[] SortedRiderIdArray = new int[sortedRacerIDs.size()];
        
        for (int i = 0; i < sortedRacerIDs.size(); i++) {
            SortedRiderIdArray[i] = sortedRacerIDs.get(i);
        }

        if (SortedRiderIdArray.length == 0) {
            return null;
        } else {
            return SortedRiderIdArray;
        }
        
    }
    
    public int[] getRidersPointsInRace() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllPointsToRiders = GetAllStagesPoints();

        if (AllPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //List For storing the ranked points for riders.
        List<Integer> ListOfPointsRanked = new ArrayList<>();

        for (int RiderId: RankOfRiders) {
            int SumOfPoints = 0;
            // Iterating through the maps inthe list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfPoints += value; 
                    }
                }
            }
            ListOfPointsRanked.add(SumOfPoints);
        }

        // Converting the list to int array.
        int[] PointsArray = new int[ListOfPointsRanked.size()];
        
        for (int i = 0; i < ListOfPointsRanked.size(); i++) {
            PointsArray[i] = ListOfPointsRanked.get(i);
        }         
        
        return PointsArray; 

    }

    public int[] getRidersPointClassificationRank() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllPointsToRiders = GetAllStagesPoints();

        if (AllPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //Map for storing rider Ids to points
        Map<Integer,Integer> MapOfAllSumOfPoints = new HashMap<Integer, Integer>();

        for (int RiderId: RankOfRiders) {
            int SumOfPoints = 0;
            // Iterating through the maps inthe list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfPoints += value; 
                    }
                }
            }
            MapOfAllSumOfPoints.put(RiderId, SumOfPoints);
        }

        // Sort the entries based on the values
        List<Map.Entry<Integer, Integer>> MapSortedPoints = new ArrayList<>(MapOfAllSumOfPoints.entrySet());
        MapSortedPoints.sort(Map.Entry.comparingByValue());

        // Create a list of keys in the new order
        List<Integer> ListOfIdsSortedOfPoints = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : MapSortedPoints) {
            ListOfIdsSortedOfPoints.add(entry.getKey());
        }
        //reversing the list, so that the rider with most points is now first
        Collections.reverse(ListOfIdsSortedOfPoints);
        // Converting the list to int array.
        int[] ArrayOfIdsSortedOfPoints = new int[ListOfIdsSortedOfPoints.size()];
        
        for (int i = 0; i < ListOfIdsSortedOfPoints.size(); i++) {
            ArrayOfIdsSortedOfPoints[i] = ListOfIdsSortedOfPoints.get(i);
        }         
        
        return ArrayOfIdsSortedOfPoints;

    }

    public int[] getRidersMountainPointsInRace() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllMountainPointsToRiders = GetAllStagesMountainPoints();

        if (AllMountainPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //List For storing the ranked points for riders.
        List<Integer> ListOfMountainPointsRanked = new ArrayList<>();

        for (int RiderId: RankOfRiders) {
            int SumOfPoints = 0;
            // Iterating through the maps inthe list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllMountainPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfPoints += value; 
                    }
                }
            }
            ListOfMountainPointsRanked.add(SumOfPoints);
        }

        // Converting the list to int array.
        int[] PointsArray = new int[ListOfMountainPointsRanked.size()];
        
        for (int i = 0; i < ListOfMountainPointsRanked.size(); i++) {
            PointsArray[i] = ListOfMountainPointsRanked.get(i);
        }         
        
        return PointsArray; 

    }
    
    public int[] getRidersMountainPointClassificationRank() {
        // Creating a List of all the Maps of points, from each stage
        List<Map<Integer,Integer>> AllPointsToRiders = GetAllStagesMountainPoints();

        if (AllPointsToRiders.size() == 0){
            return null;
        }

        // Getting the rank of the riders, sorted by total adjusted elapsed time
        int[] RankOfRiders = getRidersGeneralClassificationRankMethod();

        //Map for storing rider Ids to points
        Map<Integer,Integer> MapOfAllSumOfMountainPoints = new HashMap<Integer, Integer>();

        for (int RiderId: RankOfRiders) {
            int SumOfMountainPoints = 0;
            // Iterating through the maps in the list, to gather all the points for that rider
            for (Map<Integer,Integer> SublistMap : AllPointsToRiders) {
                for (Map.Entry<Integer, Integer> entry : SublistMap.entrySet()) {
                    Integer key = entry.getKey();
                    Integer value = entry.getValue(); 
                    if (key == RiderId) {
                        SumOfMountainPoints += value; 
                    }
                }
            }
            MapOfAllSumOfMountainPoints.put(RiderId, SumOfMountainPoints);
        }

        // Sort the entries based on the values
        List<Map.Entry<Integer, Integer>> MapSortedPoints = new ArrayList<>(MapOfAllSumOfMountainPoints.entrySet());
        MapSortedPoints.sort(Map.Entry.comparingByValue());

        // Create a list of keys in the new order
        List<Integer> ListOfIdsSortedOfMountainPoints = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : MapSortedPoints) {
            ListOfIdsSortedOfMountainPoints.add(entry.getKey());
        }
        //reversing the list, so that the rider with most points is now first

        Collections.reverse(ListOfIdsSortedOfMountainPoints);

        // Converting the list to int array.
        int[] ArrayOfIdsSortedOfMountainPoints = new int[ListOfIdsSortedOfMountainPoints.size()];
        
        for (int i = 0; i < ListOfIdsSortedOfMountainPoints.size(); i++) {
            ArrayOfIdsSortedOfMountainPoints[i] = ListOfIdsSortedOfMountainPoints.get(i);
        }         
        
        return ArrayOfIdsSortedOfMountainPoints;

    }
    
    
}
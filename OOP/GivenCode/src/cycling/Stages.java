



package cycling;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents stages of a cycling race.
 * This class contains information about the stage type, length, checkpoints, start time, name, state,
 * checkpoint type, results list, points for riders, mountain points for riders, categorized climbs, and intermediate sprints.
 * It also provides methods to retrieve and update this information. And other methods for dealing with the different classifications.
 */

public class Stages {
    private StageType stageType; //
    private double length;
    private List<Integer> checkpointIds = new ArrayList<>();
    private Checkpoint[] checkpoints;
    private int StageId;
    private LocalDateTime startTime;
    private String name;
    private StageState state;
    private CheckpointType checkpointtype;
    private List<List<Object>> ResultsList = new ArrayList<>();
    private Map<Integer, Integer> RidersPoints = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> RidersPointsMountain = new HashMap<Integer, Integer>();
    private List<CategorizedClimb> categorizedClimbs = new ArrayList<>();
    private List<IntermediateSprint> intermediateSprints = new ArrayList<>();

    /**
     * Constructs a Stages object with the given stage ID, name, description, length, start time, and stage type.
     *
     * @param stageId     The unique identifier for the stage.
     * @param name        The name of the stage.
     * @param description A description of the stage.
     * @param length      The total length of the stage.
     * @param startTime   The start time of the stage.
     * @param stageType   The type of the stage.
     */


    public Stages(int StageId, String name, String description, double length, LocalDateTime startTime, StageType stageType) {
        this.stageType = stageType;
        this.length = length;
        this.startTime = startTime;
        this.name = name;
        this.StageId = StageId;
    }


    // getters and setters for all attributes
    public CheckpointType getCurrentCheckpointType() {
        return checkpointtype;
    }

    public void setCurrentCheckpointType(CheckpointType checkpointtype) {
        this.checkpointtype = checkpointtype;
    }


    public StageType getStageType() {
        return stageType;
    }

    public void setStageType(StageType stageType) {
        this.stageType = stageType;
    }

    public double getlength() {
        return length;
    }

    public void setlength(double length) {
        this.length = length;
    }

    public Checkpoint[] getCheckpoints() {
        return checkpoints;
    }

    public void setCheckpoints(Checkpoint[] checkpoints) {
        this.checkpoints = checkpoints;
    }

    public int getId() {
        return StageId;
    }

    public void setId(int Id) {
        this.StageId = Id;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    public String getStageName() {
        return name;
    }
    public void setStageName(String stageName) {
        this.name = stageName;

    }
    public List<List<Object>> getResultsList() {
        return ResultsList;
    }
    public void setResultsList(List<List<Object>> NewResultsList) {
        this.ResultsList = NewResultsList;
    } 
    public Map<Integer, Integer> getRidersPointsMountain() {
        return RidersPointsMountain;
    }
    public void setRidersPointsMountain(Map<Integer, Integer> NewRidersPointsMountain) {
        this.RidersPointsMountain = NewRidersPointsMountain;
    } 
    public Map<Integer, Integer> getRidersPoints() {
        return RidersPoints;
    }
    public void setRidersPoints(Map<Integer, Integer> GivenListOfPoints) {
        this.RidersPoints = GivenListOfPoints;

    }
    public StageState getState() {
        return state;
    }

    public void setState(StageState newstate) {
        this.state = newstate;
    }


    
    public List<List<Object>> AdjustResultsList(List<List<Object>> originalList) {
        List<List<Object>> ResultsCopy1 = new ArrayList<>();
        if (originalList.size() != 0) {

            for (List<Object> sublist : originalList) {

                if (sublist.size() != 3) {
                    ResultsCopy1.add(sublist);
                }
            }
        }

        return ResultsCopy1;

    }

    public List<List<Object>> deepCopyList(List<List<Object>> originalList) {
        // Create a new ArrayList to store the copied 
        // elements 
        List<List<Object>> copiedList = new ArrayList<>(); 
  
        // Iterate over each element in the original list 
        for (List<Object> item : originalList) { 
            // Creating a new instance of each element
            List<Object> newItem =  new ArrayList<>(item); 
            copiedList.add(newItem); 
        } 
  
        // Return the deep copied list 
        return copiedList; 
    }

    public Map<Integer, LocalTime> DealWithDict(Map<Integer, LocalTime> dictionary) { 

        // Convert the dictionary into a list of map entries
        List<Map.Entry<Integer, LocalTime>> entryList = new ArrayList<>(dictionary.entrySet());

        // Sort the list based on the item (LocalTime)
        Collections.sort(entryList, Comparator.comparing(Map.Entry::getValue));

        // Create a LinkedHashMap to preserve the order after sorting
        Map<Integer, LocalTime> sortedDictionary = new LinkedHashMap<>();
        for (Map.Entry<Integer, LocalTime> entry : entryList) {
            sortedDictionary.put(entry.getKey(), entry.getValue());
        }

        // Reverse the sorted dictionary
        Map<Integer, LocalTime> reversedDictionary = new LinkedHashMap<>();
        List<Integer> keys = new ArrayList<>(sortedDictionary.keySet());
        Collections.reverse(keys);
        for (Integer key : keys) {
            reversedDictionary.put(key, sortedDictionary.get(key));
        }

        boolean CompleteRecursion = false;

        for (int i = 0; i < reversedDictionary.size() - 1; i++) {
            try {
                // This gets the values from the array list
                LocalTime firstVal = new ArrayList<>(reversedDictionary.values()).get(i);
                LocalTime secondVal = new ArrayList<>(reversedDictionary.values()).get(i + 1);
                int firstKey = new ArrayList<>(reversedDictionary.keySet()).get(i);
                int secondKey = new ArrayList<>(reversedDictionary.keySet()).get(i + 1);
                
                // Converts LocalTime to the nanoseconds
                long firstValSec = firstVal.toNanoOfDay();
                long secondValSec = secondVal.toNanoOfDay();

                // comparing times, by removing second from one, so we can see if within one second
                if (((firstValSec - secondValSec) < 1000000000) && ((firstValSec - secondValSec) > -1000000000) && ((firstValSec - secondValSec) != 0)){
                    reversedDictionary.put(firstKey, reversedDictionary.get(secondKey));
                    CompleteRecursion = true;
                } else if (((secondValSec - firstValSec) < 1000000000 ) && ((secondValSec -firstValSec ) > -1000000000) && ((secondValSec - firstValSec) != 0)){
                    reversedDictionary.put(secondKey, reversedDictionary.get(firstKey));
                    CompleteRecursion = true;
                }

            } catch (Exception e) {
                // Ignore and continue to the next iteration
            }
        }

        // Reverse the dictionary
        Map<Integer, LocalTime> ReReversedDictionary = new LinkedHashMap<>();
        List<Integer> keys2 = new ArrayList<>(reversedDictionary.keySet());
        Collections.reverse(keys2);
        for (Integer key1 : keys2) {
            ReReversedDictionary.put(key1, reversedDictionary.get(key1));
        }

        if (CompleteRecursion){
            ReReversedDictionary = DealWithDict(ReReversedDictionary);
        }

        return ReReversedDictionary;

        
    }

    public List<List<Object>> GetAdjustedFinishTime(List<List<Object>> Ranking) {
        // Make a copy of the ranking to ensure mutability.
        List<List<Object>> RankingCopy = new ArrayList<>();
    
        // Iterate through the original ranking to create a copy.
        for (List<Object> innerList : Ranking) {
            List<Object> copiedInnerList = new ArrayList<>(innerList);
            RankingCopy.add(copiedInnerList);
        }
    
        // Create a dictionary to store finish times indexed by position.
        Map<Integer, LocalTime> RankingDict = new HashMap<>();
    
        // Populate the dictionary with finish times from the original ranking.
        for (int i = 0; i < RankingCopy.size(); i++) {
            List<Object> sublist = Ranking.get(i);
            LocalTime[] obj = (LocalTime[]) sublist.get(2); // Assuming finish times are stored in the third element of each sublist.
            LocalTime FinishTime = obj[obj.length - 1]; // Taking the last finish time.
            
            RankingDict.put(i, FinishTime);
        }
    
        // Process the dictionary to adjust finish times.
        RankingDict = DealWithDict(RankingDict);
    
        // Counter to keep track of the current index in the ranking.
        int CounterForCheckingIfIndexIsCorrect = 0;
    
        // Iterate through the copied ranking to adjust finish times.
        for (List<Object> innerList : RankingCopy) { 
            // Extract finish times from the sublist.
            LocalTime[] TimesAsArray = (LocalTime[]) innerList.get(2);
    
            // Create a copy of the finish times list.
            List<LocalTime> CopyOfTimes = new ArrayList<>();
            for (int i = 0; i < TimesAsArray.length; i++) {
                CopyOfTimes.add(TimesAsArray[i]);
            }
    
            // Adjust the finish time using the dictionary.
            for (int key : RankingDict.keySet()) {
                if (key == CounterForCheckingIfIndexIsCorrect) {
                    LocalTime AdjustedTimeFromDict = RankingDict.get(key);
                    CopyOfTimes.add(AdjustedTimeFromDict);
                }
            }
    
            // Convert the adjusted finish times list to an array.
            LocalTime[] CopyOfTimesArray = new LocalTime[CopyOfTimes.size()];
            for (int i = 0; i < CopyOfTimes.size(); i++) {
                CopyOfTimesArray[i] = CopyOfTimes.get(i);
            }
    
            // Set the adjusted finish times array back to the sublist.
            innerList.set(2, CopyOfTimesArray);
            // Update the sublist in the copied ranking.
            RankingCopy.set(CounterForCheckingIfIndexIsCorrect, innerList);
    
            // Increment the counter.
            CounterForCheckingIfIndexIsCorrect++;
        }
    
        // Return the modified ranking with adjusted finish times.
        return RankingCopy;
    }

    private List<List<Object>> AddElapsedTime(List<List<Object>> OriginalRanking){

        // Make a copy of the ranking to ensure mutability.
        List<List<Object>> RankingCopy1 = new ArrayList<>();
    
        // Iterate through the original ranking to create a copy.

        for (List<Object> innerList : OriginalRanking) {
            List<Object> copiedInnerList = new ArrayList<>(innerList);
            RankingCopy1.add(copiedInnerList);
        }


        int CounterForIndexInRanking = 0;
        for (List<Object> RankingSublist : RankingCopy1) {
            // Get the Array of times for the checkpoints
            LocalTime[] ArrayOfLocalTimes = (LocalTime[]) RankingSublist.get(2);
            // Getting start and finish times
            LocalTime StartTime = (LocalTime) ArrayOfLocalTimes[0];
            int ArrayOfLocalTimeslength = ArrayOfLocalTimes.length;
            LocalTime FinishTime = (LocalTime) ArrayOfLocalTimes[ArrayOfLocalTimeslength-1];
            
            // Find the difference in seconds between the two LocalDateTime objects
            long StartTimeInSeconds = StartTime.toNanoOfDay();
            long FinishTimeInSeconds = FinishTime.toNanoOfDay();

            long secondsDifference = FinishTimeInSeconds - StartTimeInSeconds;

            // Convert the difference in seconds to a LocalTime object
            LocalTime timeDifference = LocalTime.ofNanoOfDay(secondsDifference);

            RankingSublist.add(timeDifference);
            RankingCopy1.set(CounterForIndexInRanking,RankingSublist);
            CounterForIndexInRanking ++;
        }

        return RankingCopy1;
    }

    public List<List<Object>> GetAdjustedElapsedList(List<List<Object>> RankingChangedName) {
        // Create a deep copy of the original ranking.
        List<List<Object>> RankingDeepCopy = deepCopyList(RankingChangedName);
    
        // Container to hold lists of sublists grouped by stage ID.
        List<List<List<Object>>> ContainerForStageIdLists = new ArrayList<>();
    
        // Create a list to hold unique stage IDs.
        List<Integer> ListOfStageIDs = new ArrayList<>();
    
        // Populate ListOfStageIDs with unique stage IDs from RankingDeepCopy.
        for (List<Object> Sublist : RankingDeepCopy) {
            int StageId = (int) Sublist.get(0); // Assuming stage ID is stored in the first element of each sublist.
            if (!ListOfStageIDs.contains(StageId)) {
                ListOfStageIDs.add(StageId);
            }
        }
    
        // Iterate through each stage ID.
        for (int StageId : ListOfStageIDs) {
            // Create a list to hold sublists with the current stage ID.
            List<List<Object>> StageIdLists = new ArrayList<>();
            // Add sublists with the current stage ID to StageIdLists.
            for (List<Object> Sublist : RankingDeepCopy) {
                int CurrentStageId = (int) Sublist.get(0);
                if (CurrentStageId == StageId) {
                    StageIdLists.add(Sublist);
                }
            }
            // Add StageIdLists to ContainerForStageIdLists.
            ContainerForStageIdLists.add(StageIdLists);
        }
    
        // Container to hold lists of adjusted sublists grouped by stage ID.
        List<List<List<Object>>> ContainerForAdjustedStageIdLists = new ArrayList<>();
    
        // Adjust finish times for each stage ID group.
        for (List<List<Object>> RankingCopySortedStageId : ContainerForStageIdLists) {
            RankingCopySortedStageId = GetAdjustedFinishTime(RankingCopySortedStageId);
            ContainerForAdjustedStageIdLists.add(RankingCopySortedStageId);
        }
    
        // Flatten the lists of adjusted sublists into a single list.
        List<List<Object>> RankingAdjustedTimes = new ArrayList<>();
        for (List<List<Object>> SubListOfSameRank : ContainerForAdjustedStageIdLists) {
            for (List<Object> ListOfAdjustedDetails : SubListOfSameRank) {
                RankingAdjustedTimes.add(ListOfAdjustedDetails);
            }
        }

        // Calculate and add elapsed time to the ranking with adjusted finish times.
        List<List<Object>> RankingCopy3 = AddElapsedTime(RankingAdjustedTimes);

        // Initialize a counter for referencing the index of each sublist.
        int CounterForReferencingIndexOfSublist = 0;

        // Iterate through each sublist in RankingCopy3.
        for (List<Object> InnerList : RankingCopy3) { 
            // Extract the array of LocalTime objects representing finish times from the sublist.
            LocalTime[] TimesAsArray = (LocalTime[]) InnerList.get(2);

            // Create a copy of finish times to manipulate.
            List<LocalTime> CopyOfTimes = new ArrayList<>();
            for (int i = 0; i < TimesAsArray.length; i++) {
                CopyOfTimes.add(TimesAsArray[i]);
            }

            // Remove the last element (finish time) from the copy of finish times.
            CopyOfTimes.remove(CopyOfTimes.size() - 1);

            // Convert the manipulated finish times list to an array of LocalTime objects.
            LocalTime[] CopyOfTimesArray = new LocalTime[CopyOfTimes.size()];
            for (int i = 0; i < CopyOfTimes.size(); i++) {
                CopyOfTimesArray[i] = CopyOfTimes.get(i);
            }

            // Update the sublist with the adjusted finish times array.
            InnerList.set(2, CopyOfTimesArray);
            
            // Update the sublist in RankingCopy3.
            RankingCopy3.set(CounterForReferencingIndexOfSublist, InnerList);
            
            // Increment the counter for referencing the index of each sublist.
            CounterForReferencingIndexOfSublist++;
        }

        // Return the modified ranking with adjusted elapsed times.
        return RankingCopy3;
    }
    
    public void AddRidersToList(int stageId, int riderId, LocalTime[] checkpointTimes) throws DuplicatedResultException, InvalidCheckpointTimesException{      
        // checking to see if invalid checkpoint times
        if (checkpointTimes.length != (checkpoints.length + 2)) {
            throw new InvalidCheckpointTimesException("Incorrect amount of checkpoint times");
        }

        ResultsList = AdjustResultsList(ResultsList);

        //Creating a new list
        List<Object> NewEntry = List.of(stageId, riderId, checkpointTimes);

        List<List<Object>> ResultsListAdjustedTimesRemoved = new ArrayList<>();

        for (List<Object> SubList : ResultsList) {
            // Checking to see if the rider already has a result in the result List
            if ((int) SubList.get(1) == riderId) {
                throw new DuplicatedResultException("Rider id of " + riderId + " already found");
            }
            
            SubList.remove(4);
            SubList.remove(3);
            
            ResultsListAdjustedTimesRemoved.add(SubList);
        }

        // Adding the List to a List, so in same format as the main List of results
        ResultsListAdjustedTimesRemoved.add(NewEntry);
        // Calling function to add elapsed time to all results
        ResultsListAdjustedTimesRemoved = AddElapsedTime(ResultsListAdjustedTimesRemoved);
        // Adjusts the elapsed time, if they are within a seconds.
        ResultsListAdjustedTimesRemoved = GetAdjustedElapsedList(ResultsListAdjustedTimesRemoved);

        if (stageType == StageType.TT) {
            for (List<Object> SubList : ResultsListAdjustedTimesRemoved) {
                int CurrentRiderId = (int) SubList.get(1);
                if (CurrentRiderId == riderId) {
                    LocalTime ElapsedTime = (LocalTime) SubList.get(3);
                    SubList.set(4, ElapsedTime);
                }
            } 
        }

        for (List<Object> Sublists : ResultsListAdjustedTimesRemoved ) {
            if (Sublists.size() == 5) {
                ResultsList.add(Sublists);
            }
        }


    }

    public LocalTime[] getRiderResultsInStageMethod(int stageId, int riderId) {

        ResultsList = AdjustResultsList(ResultsList);

        // List to store the rider's results in the specified stage.
        List<LocalTime> RiderResultsList = new ArrayList<>();
    
        // Flag to indicate if the rider ID is found in the results list.
        boolean NotInList = false;

        // List<Object> FirstItem =  (List<Object>) (ResultsList.get(0));
        // if (FirstItem.size() == 3) {
        //     ResultsList = AdjustResultsList(ResultsList);
        // }


        // Iterate through the results list to find the rider's results in the specified stage.
        for (List<Object> innerList : ResultsList) { 
            // Check if the current innerList corresponds to the specified stage ID and rider ID.
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                // Extract the checkpoint times from the innerList.
                LocalTime[] RiderCheckPointTimes = (LocalTime[]) (innerList.get(2));
    
                // Iterate through the checkpoint times to add them to RiderResultsList,
                // excluding the start and finish times.
                int CounterForStartFinish = 0;
                for (LocalTime Time: RiderCheckPointTimes) {
                    if ((CounterForStartFinish != 0) && (CounterForStartFinish != (RiderCheckPointTimes.length - 1))) {
                        RiderResultsList.add(Time);
                    }
                    CounterForStartFinish++;
                }
    
                // Extract the elapsed time from the innerList and add it to RiderResultsList.
                LocalTime ElapsedTime = (LocalTime) innerList.get(3);
                RiderResultsList.add(ElapsedTime);
    
                // Set the flag to true indicating that the rider ID is found in the results list.
                NotInList = true;
            }
        } 

        // Converting the list to LocalTime array.
        LocalTime[] RiderResultsArray = new LocalTime[RiderResultsList.size()];
        for (int i = 0; i < RiderResultsList.size(); i++) {
            RiderResultsArray[i] = RiderResultsList.get(i);
        }
        if (NotInList) {
            return RiderResultsArray;
        } else {
            // Returning null as no rider id found.
            return null;
        }

    }

    public LocalTime getRiderAdjustedElapsedTimeInStageMethod(int stageId, int riderId) {

        ResultsList = AdjustResultsList(ResultsList);

        // Iterates through the list to see if the stageId and RankId match, If so, returns the Elapsed time.
        for (List<Object> innerList : ResultsList) {
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                LocalTime AdjustedElapsedTime = (LocalTime) innerList.get(4);
                return AdjustedElapsedTime;
            }
        }
        // As no rider id matches any in the list of riders 
        return null;
    }

    public boolean DeleteRider(int stageId, int riderId) {
        
        List<List<Object>> ResultsCopy1 = new ArrayList<>();
        if (ResultsList.size() != 0) {

            for (List<Object> sublist : ResultsList) {

                if (sublist.size() != 3) {
                    ResultsCopy1.add(sublist);
                }
            }
        }

        ResultsList = ResultsCopy1;
        
        
        // Mkaing a copy of Ranking to avoid java.util.ConcurrentModificationException error when iterating through a list where you delete one of the items
        List<List<Object>> ResultsCopy2 = new ArrayList<>();
        for (List<Object> Sublist : ResultsList) {
            ResultsCopy2.add(Sublist);
        }

        // Iterating through the Copy of Results to see if the stageid and riderid match.
        // If so deletes that sublist.
        boolean riderIdFound = false;
        int CounterForDeletionIndex = 0;
        for (List<Object> innerList : ResultsCopy2) { 
            if ((innerList.get(0).equals(stageId)) && (innerList.get(1).equals(riderId))) {
                ResultsList.remove(CounterForDeletionIndex);
                riderIdFound = true;
            }
            CounterForDeletionIndex++;
        }
        return riderIdFound;
    }

    public int[] getRidersRankInStageMethod(int stageId) {
    
        ResultsList = AdjustResultsList(ResultsList);

        // Sorting the ResultsList by the elapsed time.

        Collections.sort(ResultsList, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime maxTime1 = (LocalTime) sublist1.get(3);
                LocalTime maxTime2 = (LocalTime) sublist2.get(3);
                return maxTime1.compareTo(maxTime2);
            }
        });

        // Iterate through the ResultsList list, ading the rider id to the new list

        List<Integer> resultList = new ArrayList<>();
        for (List<Object> sublist : ResultsList) {
            
            int CurrentRiderId = (int) sublist.get(1);
            resultList.add(CurrentRiderId);
            
        }

        // Converting the list to int array.
        int[] RiderIdArray = new int[resultList.size()];
        
        for (int i = 0; i < resultList.size(); i++) {
            RiderIdArray[i] = resultList.get(i);
        }

        if (RiderIdArray.length == 0) {
            return null;
        }else {
            return RiderIdArray;
        }

    }
    
    public static LocalTime findMaxLocalTime(LocalTime[] times) {
        //Finds the maximum LocalTime in an array of LocalTime objects        
        LocalTime maxTime = null;
        for (LocalTime time : times) {
            if (maxTime == (null) || time.isAfter(maxTime)) {
                maxTime = time;
            }
        }
        return maxTime;
    }
    
    public List<List<Object>> SortListbasedOfFinalTime(List<List<Object>> Ranking) {
        // Sort the sublists based on the maximum LocalTime in each sublist's array
        Collections.sort(Ranking, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime maxTime1 = findMaxLocalTime((LocalTime[]) sublist1.get(2));
                LocalTime maxTime2 = findMaxLocalTime((LocalTime[]) sublist2.get(2));
                return maxTime1.compareTo(maxTime2);
            }
        });

        return Ranking;
    }

    public LocalTime[] getRankedAdjustedElapsedTimesInStageMethod(int stageId) {

        ResultsList = AdjustResultsList(ResultsList);

        List<List<Object>> ResultsSortedByFinishTime = new ArrayList<>();

        // Sort the sublists based off of the Final Time. 
        ResultsSortedByFinishTime = SortListbasedOfFinalTime(ResultsList);
        
        // Iterating through the sorted by finish time list, and gets stage id from the sublist

        List<LocalTime> resultList = new ArrayList<>();
        for (List<Object> sublist : ResultsSortedByFinishTime) {
            int CurrentStageId = (int) sublist.get(0);
            // If stage id is equal to the one passed in, that Adjusted elapsed time from that sublist, is added to the result list
            if (CurrentStageId == stageId) {
                LocalTime AdjustedElapsedTime = (LocalTime) sublist.get(4);
                resultList.add(AdjustedElapsedTime);
            }
            
        }

        // The reason that stage id is still checked even though all results in the stage object's result list should be from the same stage, Is if there are 
        //any errors when appending results. E.g. accidentily putting in a result from another stage. This code will handle that error and not display that elapsed time from the other stage.

        // Converting the list to LocalTime array.
        LocalTime[] RiderAdjustedElapsedTimes = new LocalTime[resultList.size()];
        
        for (int i = 0; i < resultList.size(); i++) {
            RiderAdjustedElapsedTimes[i] = resultList.get(i);
        }

        if (RiderAdjustedElapsedTimes.length == 0) {
            return null;
        } else {
            return RiderAdjustedElapsedTimes;
        }

        
    }
 
    public List<Integer> getPointsForStageType() {


        List<Integer> ListOfPoints = new ArrayList<>();
        // Creates an array of the points, in order, given to 1st -> 15th place.
        if (stageType == StageType.MEDIUM_MOUNTAIN) {
            int[] ArrayOfPoints = {30,25,22,19,17,15,13,11,9,7,6,5,4,3,2};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        } else if (stageType == StageType.HIGH_MOUNTAIN) {
            int[] ArrayOfPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        } else if (stageType == StageType.FLAT) {
            int[] ArrayOfPoints = {50,30,20,18,16,14,12,10,8,7,6,5,4,3,2};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        } else if (stageType == StageType.TT) {
            int[] ArrayOfPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPoints.add(point);
            }
        }

        int CounterForCheckpoints = 1;
        for (Checkpoint CurrentCheckpoint : checkpoints) {
            int CounterForAssigningPoints = 0;
            // Get the checkpoint type from the checkpoint class.
            CheckpointType CurrentCheckpointType = CurrentCheckpoint.getCheckpointType();
            List<Integer> ListOfPointsCopy = new ArrayList<>();
            
            if (CurrentCheckpointType == CheckpointType.SPRINT) {
                int[] ArrayOfPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
                // Adding the points to the List
                for (int point : ArrayOfPoints ){
                    ListOfPointsCopy.add(point);
                }

                //Sorting the List of Results based upon Checkpoint times
                List<List<Object>> SortedResultsList = SortListAtIndex(ResultsList, CounterForCheckpoints);
                    
                // Adding the points to the dict, if the rider id already there it updates the points, by adding to the last result.
                for (List<Object> Sublist : SortedResultsList) {
                    if (CounterForAssigningPoints < ListOfPointsCopy.size()){
                        int RiderId = (int) Sublist.get(1);
                        try {
                            int NewPoints = (RidersPoints.get(RiderId) + ListOfPointsCopy.get(CounterForAssigningPoints));
                            RidersPoints.put(RiderId,NewPoints);
                        }
                        catch (Exception e) {
                            RidersPoints.put(RiderId,ListOfPointsCopy.get(CounterForAssigningPoints));
                        }
                    }
                    CounterForAssigningPoints ++;
                    
                }
            }            

            CounterForCheckpoints ++;
            

        }
        return ListOfPoints;
    }

    public void AssignPointsToRiders() {
        
        List<Integer> ListOfPoints = getPointsForStageType();
        // Getting the list of riderIds for the stage
        List<Integer> SortedListOfRiderIds = new ArrayList<>();
        // Getting the Sorted Result List
        List<List<Object>> SortedResultList = SortListbasedOfFinalTime(ResultsList);

        for (List<Object> Sublist : SortedResultList) {
            int RiderId = (int) Sublist.get(1);
            SortedListOfRiderIds.add(RiderId);
        }     


        // Adds the riders ID and their given points to the dictionary
        int CounterToReferenceRiderIds = 0;
        int NumberOfRiders = SortedListOfRiderIds.size();
        for (int Points : ListOfPoints) {
            // Assigns points to the riders in the list, and not out of list range.
            if (CounterToReferenceRiderIds < NumberOfRiders) {
                try {
                    int NewPoints = (RidersPoints.get(SortedListOfRiderIds.get(CounterToReferenceRiderIds)) + Points);
                    RidersPoints.put(SortedListOfRiderIds.get(CounterToReferenceRiderIds),NewPoints);
                }
                catch (Exception e) {
                    RidersPoints.put(SortedListOfRiderIds.get(CounterToReferenceRiderIds),Points);
                } 
                
                CounterToReferenceRiderIds++;
            }
            
        }
        
    }
    
    public int[] getRidersPointsInStageMethod(int stageId) {
        
        RidersPoints.clear();

        // Checking that if there are no results for that stage, then just returns null
        if (ResultsList.size() == 0) {
            return null;
        }  

        List<List<Object>> ResultsCopy = new ArrayList<>();
        for (List<Object> Sublist : ResultsList) {
            ResultsCopy.add(Sublist);
        }
        for (List<Object> sublist : ResultsCopy) {

            if (sublist.size() == 3) {
                ResultsList = AdjustResultsList(ResultsList);
            }
        }

        // Assigning the points to the riders.
        AssignPointsToRiders();

        
        int[] ArrayOfSortedRiderIds = getRidersRankInStageMethod(stageId);

        // Create a new LinkedHashMap to maintain the order
        Map<Integer, Integer> reorderedRiderPoints = new LinkedHashMap<>();

        
        // Iterate through the sorted rider IDs array
        for (int riderId : ArrayOfSortedRiderIds) {
            // Check if the rider ID exists in the original dictionary
            if (RidersPoints.containsKey(riderId)) {
                // If found, add the rider ID and points to the reordered dictionary
                reorderedRiderPoints.put(riderId, RidersPoints.get(riderId));
                // Remove the rider ID from the original dictionary to avoid duplicates
                RidersPoints.remove(riderId);
            }
        }

        // Add any remaining entries from the original dictionary (if any)
        reorderedRiderPoints.putAll(RidersPoints);

        // Update the original dictionary with the reordered entries
        Map<Integer, Integer> NewRidersPoints = reorderedRiderPoints;


        
        List<Integer> RankedListOfPoints = new ArrayList<>();
        // Iterating through the Sorted By Elapsed Time Dictioanry of riders and points.
        // Creating a list of the points
        for (Map.Entry<Integer, Integer> entry : NewRidersPoints.entrySet()) {
            Integer value = entry.getValue(); 
            RankedListOfPoints.add(value);
        }


        // Converting the list to int array.
        int[] PointsArray = new int[RankedListOfPoints.size()];
        
        for (int i = 0; i < RankedListOfPoints.size(); i++) {
            PointsArray[i] = RankedListOfPoints.get(i);
        }
              
        return PointsArray;
         

    }

    public List<Integer> GetPointsForCheckpointTypeMountain(CheckpointType CurrentCheckpointType) {

        List<Integer> ListOfPointsMountain = new ArrayList<>();
        // Creates an array of the points, in order, given to 1st -> 15th place.
        if (CurrentCheckpointType == CheckpointType.C4) {
            int[] ArrayOfPoints = {1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        } else if (CurrentCheckpointType == CheckpointType.C3) {
            int[] ArrayOfPoints = {2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        } else if (CurrentCheckpointType == CheckpointType.C2) {
            int[] ArrayOfPoints = {5,3,2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        } else if (CurrentCheckpointType == CheckpointType.C1) {
            int[] ArrayOfPoints = {10,8,6,4,2,1};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        } else if (CurrentCheckpointType == CheckpointType.HC) {
            int[] ArrayOfPoints = {20,15,12,10,8,6,4,2};
            // Adding the points to the List
            for (int point : ArrayOfPoints ){
                ListOfPointsMountain.add(point);
            }
        }
        // } else if (CurrentCheckpointType == CheckpointType.SPRINT) {
        //     int[] ArrayOfPoints = {20,17,15,13,11,10,9,8,7,6,5,4,3,2,1};
        //     // Adding the points to the List
        //     for (int point : ArrayOfPoints ){
        //         ListOfPointsMountain.add(point);
        //     }
        // }
        return ListOfPointsMountain;
    } 

    public List<List<Object>> SortListAtIndex(List<List<Object>> Ranking, int Index) {
        // Sort the sublists based on the maximum LocalTime in each sublist's array
        Collections.sort(Ranking, new Comparator<List<Object>>() {
            @Override
            public int compare(List<Object> sublist1, List<Object> sublist2) {
                LocalTime[] Checkpoints1 = (LocalTime[]) sublist1.get(2);
                LocalTime[] Checkpoints2 = (LocalTime[]) sublist2.get(2);
                LocalTime CurrentCheckpointTime1 = (LocalTime) Checkpoints1[Index];
                LocalTime CurrentCheckpointTime2 = (LocalTime) Checkpoints2[Index];
                return CurrentCheckpointTime1.compareTo(CurrentCheckpointTime2);
            }
        });

        return Ranking;
    }
    
    public void AssignMountainPointsToRiders() {
        
        int CounterForCheckpoints = 1;
        for (Checkpoint CurrentCheckpoint : checkpoints) {
            
            int CounterForAssigningPoints = 0;
            // Get the checkpoint type from the checkpoint class.
            CheckpointType CurrentCheckpointType = CurrentCheckpoint.getCheckpointType();

            //Getting the list of points for that checkpoint type
            List<Integer> ListOfMountainPoints = GetPointsForCheckpointTypeMountain(CurrentCheckpointType);
            
            //Sorting the List of Results based upon Checkpoint times
            List<List<Object>> SortedResultsList = SortListAtIndex(ResultsList, CounterForCheckpoints);
            
            // Adding the points to the dict, if the rider id already there it updates the points, by adding to the last result.

            SortedResultsList = AdjustResultsList(SortedResultsList);

            for (List<Object> Sublist : SortedResultsList) {
                if (CounterForAssigningPoints < ListOfMountainPoints.size()){
                    int RiderId = (int) Sublist.get(1);
                    try {
                        int NewPoints = (RidersPointsMountain.get(RiderId) + ListOfMountainPoints.get(CounterForAssigningPoints));
                        RidersPointsMountain.put(RiderId,NewPoints);
                    }
                    catch (Exception e) {
                        RidersPointsMountain.put(RiderId,ListOfMountainPoints.get(CounterForAssigningPoints));
                    }
                }
                
                CounterForAssigningPoints ++;
                
            } 

            CounterForCheckpoints ++;

        }

    }
    
    public int[] getRidersMountainPointsInStage() {
        
        RidersPointsMountain.clear();

        // Checking that if there are no results for that stage, then just returns null
        if (ResultsList.size() == 0) {
            return null;
        }  
        // Assigning the mountain points to the riders.
        AssignMountainPointsToRiders();

        //Sorted list of points by finish time
        List<Integer> RankedListOfPoints = new ArrayList<>();

        List<List<Object>> ResultsSortedByFinishTime = SortListbasedOfFinalTime(ResultsList);
        
        
        List<List<Object>> ResultsCopy = new ArrayList<>();
        if (ResultsSortedByFinishTime.size() != 0) {

            for (List<Object> sublist : ResultsSortedByFinishTime) {

                if (sublist.size() != 3) {
                    ResultsCopy.add(sublist);
                }
            }
        }

        ResultsSortedByFinishTime = ResultsCopy;

        // List of Rider Id's in mountain point dictionary
        List<Integer> ListOfRiderIdsInMap = new ArrayList<>(RidersPointsMountain.keySet());

        // Iterating through the sorted sublits, and if that rider id is in the Map, then adding that point to the list
        for (List<Object> sublist : ResultsSortedByFinishTime) {
            
            int riderid = (int) sublist.get(1);
            if (ListOfRiderIdsInMap.contains(riderid)) {
                // Adding that riders points from the Dictionary to the list
                RankedListOfPoints.add(RidersPointsMountain.get(riderid));
            }
            else {
                RankedListOfPoints.add(0);
            }

        }


        
        
        // Converting the list to int array.
        int[] PointsArray = new int[RankedListOfPoints.size()];
        
        for (int i = 0; i < RankedListOfPoints.size(); i++) {
            PointsArray[i] = RankedListOfPoints.get(i);
        }
              
        return PointsArray;
    }

    // Adding a Categorized Climb to the Categorized Climb List
    public void addCategorizedClimb(CategorizedClimb climb) {
        categorizedClimbs.add(climb);

        List<Checkpoint> CheckpointList = new ArrayList<>();

        int CheckpointId = 0;
        if (checkpointIds.size() != 0) {
            CheckpointId = ((checkpointIds.get(checkpointIds.size() - 1)) + 1);
            checkpointIds.add(CheckpointId);
            for (Checkpoint checkpoint : checkpoints) {
                CheckpointList.add(checkpoint);
            }
        }
        else{
            checkpointIds.add(0);
        }

        
        CheckpointType CurrentCheckpointType = climb.getType();
        Checkpoint NewCheckpoint = new Checkpoint (CheckpointId, CurrentCheckpointType);
        CheckpointList.add(NewCheckpoint);

        // Converting the list to LocalTime array.
        Checkpoint[] CheckpointArray = new Checkpoint[CheckpointList.size()];
        for (int i = 0; i < CheckpointList.size(); i++) {
            CheckpointArray[i] = CheckpointList.get(i);
        }

        setCheckpoints(CheckpointArray);

    }
    // Adding a Intermediate Sprint to the Intermediate Sprint List
    public void addIntermediateSprint(IntermediateSprint sprint) {
        intermediateSprints.add(sprint);
        
        List<Checkpoint> CheckpointList = new ArrayList<>();

        int CheckpointId = 0;
        if (checkpointIds.size() != 0) {
            CheckpointId = ((checkpointIds.get(checkpointIds.size() - 1)) + 1);
            checkpointIds.add(CheckpointId);
            for (Checkpoint checkpoint : checkpoints) {
                CheckpointList.add(checkpoint);
            }
        }
        else{
            checkpointIds.add(0);
        }


        CheckpointType CurrentCheckpointType = CheckpointType.SPRINT;
        Checkpoint NewCheckpoint = new Checkpoint(CheckpointId, CurrentCheckpointType);
        CheckpointList.add(NewCheckpoint);

        // Converting the list to LocalTime array.
        Checkpoint[] CheckpointArray = new Checkpoint[CheckpointList.size()];
        for (int i = 0; i < CheckpointList.size(); i++) {
            CheckpointArray[i] = CheckpointList.get(i);
        }

        setCheckpoints(CheckpointArray);
    }
     

}


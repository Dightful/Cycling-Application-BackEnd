import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Arrays;

import cycling.CyclingPortalImpl;
import cycling.IllegalNameException;
import cycling.InvalidNameException;
import cycling.MiniCyclingPortal;
import cycling.IDNotRecognisedException;
import cycling.InvalidStageStateException;
import cycling.InvalidStageTypeException;
import cycling.Races;
import cycling.Stages;
import cycling.CyclingPortal;
import cycling.DuplicatedResultException;
import cycling.Team;
import cycling.Rider;
import cycling.CategorizedClimb;
import cycling.IntermediateSprint;
import cycling.InvalidCheckpointTimesException;
import cycling.InvalidLengthException;
import cycling.InvalidLocationException;
import cycling.CheckpointType;
import cycling.StageState;
import cycling.StageType;


public class TestOfImplementation {

    public static void main(String[] args) throws IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidStageStateException, InvalidCheckpointTimesException, InvalidLengthException, DuplicatedResultException,InvalidLocationException, InvalidStageStateException,InvalidStageTypeException{ 
        CyclingPortal cyclingPortal = new CyclingPortalImpl();
        cyclingPortal = addRace(cyclingPortal);
        cyclingPortal = testaddStageToRace(cyclingPortal);

    }

    public static CyclingPortal addRace(CyclingPortal cyclingPortal) throws IllegalNameException, InvalidNameException, IDNotRecognisedException{ 
        
        int raceId = cyclingPortal.createRace("Test Race 1", "TEST");
        System.out.println(raceId);

        // int raceId2 = cyclingPortal.createRace("Test Race 2", "TEST2");
        // System.out.println(raceId2);

        // String actualDetails = cyclingPortal.viewRaceDetails(0);
        // System.out.println("\n" + actualDetails);
        return cyclingPortal;

    }

    // DONEE
    public static CyclingPortal testaddStageToRace(CyclingPortal portal)throws InvalidCheckpointTimesException, DuplicatedResultException, IllegalNameException, InvalidNameException, IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,InvalidLengthException, InvalidStageTypeException {

        LocalDateTime stageStartTime = LocalDateTime.of(2024, 5, 6, 7, 5, 0);
        int stageId = portal.addStageToRace(0, "Hill", "Steep", 100, stageStartTime, StageType.HIGH_MOUNTAIN);

        LocalDateTime stageStartTime2 = LocalDateTime.of(2024, 6, 6, 7, 5, 0);
        int stageId2 = portal.addStageToRace(0, "Flat", "veryFlat", 200, stageStartTime2, StageType.FLAT);

        CheckpointType decidedCheckpointType = CheckpointType.C4;
        CheckpointType decidedCheckpointType2 = CheckpointType.C3;
        portal.addCategorizedClimbToStage(stageId, 50d, decidedCheckpointType, 80d, 40d);
        //portal.addCategorizedClimbToStage(stageId, 30d, decidedCheckpointType2, 30d, 40d);

        portal.addIntermediateSprintToStage(stageId, 50d);
        
        CheckpointType decidedCheckpointType3 = CheckpointType.C1;
        portal.addCategorizedClimbToStage(1, 70d, decidedCheckpointType3, 90d, 40d);
        
        portal.concludeStagePreparation(stageId);
        portal.concludeStagePreparation(stageId2);

        // testRegisterRiderResultsInStage - DONE
        LocalTime[] ltObject7 = {LocalTime.of(1, 0, 0), LocalTime.of(1, 40, 0), LocalTime.of(3, 0, 0),LocalTime.of(3, 41, 5,2400)};
        portal.registerRiderResultsInStage(0, 18, ltObject7);
        LocalTime[] ltObjec8 = {LocalTime.of(1, 0, 0), LocalTime.of(1, 42, 0), LocalTime.of(2, 10, 0),LocalTime.of(3, 41, 5,1500)};
        portal.registerRiderResultsInStage(0, 19, ltObjec8);
        LocalTime[] ltObjec9 = {LocalTime.of(1, 0, 0), LocalTime.of(1, 44, 0), LocalTime.of(2, 15, 0),LocalTime.of(3, 52, 2,0)};
        portal.registerRiderResultsInStage(0, 20, ltObjec9);
        LocalTime[] ltObjec10 = {LocalTime.of(1, 0, 0), LocalTime.of(1, 46, 0), LocalTime.of(2, 20, 0),LocalTime.of(3, 41, 5, 1000)};
        portal.registerRiderResultsInStage(0, 21, ltObjec10);

        LocalTime[] ltObject1 = {LocalTime.of(6, 0, 0), LocalTime.of(8, 40, 0),LocalTime.of(13, 50, 4, 8000)};
        portal.registerRiderResultsInStage(1, 18, ltObject1);
        LocalTime[] ltObjec2 = {LocalTime.of(6,0, 0), LocalTime.of(8, 42, 0), LocalTime.of(13, 50, 5, 5000)};
        portal.registerRiderResultsInStage(1, 19, ltObjec2);
        LocalTime[] ltObjec3 = {LocalTime.of(6, 0, 0), LocalTime.of(8, 44, 0),LocalTime.of(13, 52, 8)};
        portal.registerRiderResultsInStage(1, 20, ltObjec3);
        LocalTime[] ltObjec4 = {LocalTime.of(6, 0, 0), LocalTime.of(8, 46, 0),LocalTime.of(13, 53, 5)};
        portal.registerRiderResultsInStage(1, 21, ltObjec4);

        //getRiderResultsInStage - DONE
        // LocalTime[] results = portal.getRiderResultsInStage(0,18);
        // System.out.println(Arrays.toString(results));
        // LocalTime[] results2 = portal.getRiderResultsInStage(0,20);
        // System.out.println(Arrays.toString(results2));
        
        //getRiderAdjustedElapsedTimeInStage - DONE
        LocalTime result = portal.getRiderAdjustedElapsedTimeInStage(0,18);
        System.out.println(result);
        LocalTime result2 = portal.getRiderAdjustedElapsedTimeInStage(0,20);
        System.out.println(result2);

        // deleteRiderResultsInStage - DONE
        // portal.deleteRiderResultsInStage(0,20);
        // LocalTime[] results2 = portal.getRiderResultsInStage(0,20);
        // System.out.println(Arrays.toString(results2));

        // getRankedAdjustedElapsedTimesInStage - DONE
        // LocalTime[] result3 = portal.getRankedAdjustedElapsedTimesInStage(stageId2);
        // System.out.println(Arrays.toString(result3));  
        
        // getRidersRankInStage - DONE
        // int[] result4 = portal.getRidersRankInStage(0);
        // System.out.println(Arrays.toString(result4));       
        // int[] result45 = portal.getRidersRankInStage(1);
        // System.out.println(Arrays.toString(result45));  
        // System.out.println("");

        // // getRidersPointsInStage - DONE
        // int[] result44 = portal.getRidersPointsInStage(0);
        // System.out.println(Arrays.toString(result44));  
        // int[] result4454 = portal.getRidersPointsInStage(1);
        // System.out.println(Arrays.toString(result4454));   

        // getRidersMountainPointsInStage - DONE
        // int[] result4445 = portal.getRidersMountainPointsInStage(0);
        // System.out.println(Arrays.toString(result4445)); 
        // int[] result444 = portal.getRidersMountainPointsInStage(1);
        // System.out.println(Arrays.toString(result444)); 

        // getGeneralClassificationTimesInRace - DONE
        // LocalTime[] result3 = portal.getGeneralClassificationTimesInRace(0);
        // System.out.println(Arrays.toString(result3));

        //getRidersGeneralClassificationRank - DONE
        // int[] result1 = portal.getRidersGeneralClassificationRank(0);
        // System.out.println(Arrays.toString(result1));

        //getRidersPointsInRace - DONE
        // int[] result2 = portal.getRidersPointsInRace(0);
        // System.out.println(Arrays.toString(result2));

        //getRidersMountainPointsInRace - DONE
        int[] result22 = portal.getRidersMountainPointsInRace(0);
        System.out.println(Arrays.toString(result22));
        System.out.println("");

        //getRidersPointClassificationRank - DONE
        // int[] result = portal.getRidersPointClassificationRank(0);
        // System.out.println(Arrays.toString(result));

        //getRidersMountainPointClassificationRank - DONE
        // int[] result = portal.getRidersMountainPointClassificationRank(0);
        // System.out.println(Arrays.toString(result));

        return portal;
    }

}

package cycling;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
* Implements the Cycling Portal interaface, which performs methods on the Cycling system
 */
public class CyclingPortalImpl implements CyclingPortal {

	// Creates an empty Array of Team objects
	private Team[] TeamsArray = new Team[0];
	//Creates an empty Array of Stages objects
	private Stages[] StagesArray = new Stages[0];
	//Creates an empty Array of Rider objects
	private Rider[] RidersArray = new Rider[0];
	//Create a races array
	private Races[] RacesArray = new Races[0];

	//Store nextStageId
	private List<Integer> nextStageId = new ArrayList<>();
	// List to store races
	private List<Races> racesList = new ArrayList<>();
	// Stores the Next race id for the races
	private List<Integer> nextRaceId = new ArrayList<>();


	//Team counter for team id
	private int generateTeamId() {
		if (TeamsArray.length == 0) {
			return 0;
		} else {
			return TeamsArray.length;
		}
	
	}

	private int generateRiderId() {
		if (RidersArray.length == 0) {
			return 0;
		} else {
			return RidersArray.length;
		}
	
	}


    // Returns an array of race IDs
	@Override
	public int[] getRaceIds() {
		int[] raceIds = new int[RacesArray.length];
		for (int i = 0; i < RacesArray.length; i++) {
			raceIds[i] = RacesArray[i].getId();
		}
		return raceIds;
	}

	@Override
	// I need name, teams, id, description, totalstages
	public int createRace(String name, String description) throws IllegalNameException, InvalidNameException {
		// Validate name and description
		if (name == null || name.isEmpty() || description == null || description.isEmpty()) {
			throw new InvalidNameException("Name and description must not be null or empty");
		}

		// Check if a race with the same name already exists
		for (Races race : racesList) {
			if (race.getRacename().equals(name)) {
				throw new IllegalNameException("A race with this name already exists");
			}
		}
		int raceid = 0;
		//Generate unique ID for race
		if (nextRaceId.isEmpty()) {
			nextRaceId.add(0);
		} else {
			raceid = nextRaceId.get(nextRaceId.size() - 1) + 1;
			nextRaceId.add(raceid);
		}


		// Create a new race and add it to the list
		Races newRace = new Races(raceid, name, description);
		racesList.add(newRace);
		
		// Converting RaceArray to List
		List<Races> TempRacesList = new ArrayList<>();
		
		for (Races race : RacesArray) {
			TempRacesList.add(race);
		}
		//Adding new stage to list
		TempRacesList.add(newRace);

		//Converting list to array
		Races[] TempRacesArray = new Races[TempRacesList.size()];
        for (int i = 0; i < TempRacesList.size(); i++) {
            TempRacesArray[i] = TempRacesList.get(i);
        }

		RacesArray = TempRacesArray;
				

		// Return ID of new race
		return newRace.getRaceId();
	}

	@Override
	public String viewRaceDetails(int raceId) throws IDNotRecognisedException {
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				// Format details
				String details = "Name: " + race.getRacename() +
				                 "\nDescription: " + race.getDescription() +
								 "\nID" + race.getRaceId();
								 
				return details;
			}
		}
		
		//If race withgiven ID is not found, throw exception
		throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
	}

	@Override
	public void removeRaceById(int raceId) throws IDNotRecognisedException {
		for (int i = 0; i < racesList.size(); i++) {
			if (racesList.get(i).getId() == raceId) {
				racesList.remove(i);
				return;
			}
		}

		//If the race with given Id is not found
		throw new IDNotRecognisedException("No race with ID" + raceId + "was found");

	}

	// Retrieves the number of stages in a race by ID
	@Override
	public int getNumberOfStages(int raceId) throws IDNotRecognisedException {
		for (Races race : racesList) {
			if (race.getId() == raceId) {
				return race.getTotalStages();
			}
		}
		

		//If race is not found with given Id, throw exception
		throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
	}

	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Adds a stage to a race
	@Override
	public int addStageToRace(int raceId, String name, String description, double length, LocalDateTime startTime, StageType type)
			throws IDNotRecognisedException, IllegalNameException, InvalidNameException, InvalidLengthException {

		// Validate stageName
		if (name == null || name.isEmpty() || name.length() > 50 || name.trim().isEmpty()) {
			throw new InvalidNameException("Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace");
		}

		// Validate length
		if (length < 5) {
			throw new InvalidLengthException("Length must not be less than 5 km");
		}

		for (Races race : racesList) {
			if (race.getId() == raceId) {
				// Check if stageName is already in use
				if (race.getStages() != null)
					for (Stages stage : race.getStages()) {
						if (stage.getStageName().equals(name)) {
							throw new IllegalNameException("Name is already in use");
						}
					}

				int StageId = 0;
				if (nextStageId.isEmpty()) {
					nextStageId.add(0);
				} else {
					StageId = nextStageId.get(nextStageId.size() -1) + 1;
					nextStageId.add(StageId);
				}
				
				
				// Create a new stage and add it to the race
				Stages newStage = new Stages(StageId, name, description, length, startTime, type);
				race.addStage(newStage);
				//Add to stages array
				//Converting stages to list
				List<Stages> TempStagesList = new ArrayList<>(Arrays.asList(StagesArray));
				//Adding new stage to list
				TempStagesList.add(newStage);
				//Converting list to array
				Stages[] TempStagesArray = new Stages[TempStagesList.size()];
				for (int i = 0; i< TempStagesList.size(); i++){
					TempStagesArray[i] = TempStagesList.get(i);
				}
				StagesArray = TempStagesArray;
				// Setting the new stages state to preperation
				newStage.setState(StageState.PREPARATION);

				return newStage.getId();
			
			}

		}

		// If the race with the given ID is not found, throw an exception
		throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
	}
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public int[] getRaceStages(int raceId) throws IDNotRecognisedException {
		// Iterate through the list of races
		for (Races race : RacesArray) {
			// Check if the current race has the specified ID
			if (race.getId() == raceId) {
				// Retrieve the stages associated with the race
				Stages[] stages = race.getStages();
				// Create an array to store the stage IDs
				int[] stageIds = new int[stages.length];
				// Iterate through the stages and populate the stage IDs array
				for (int i = 0; i < stages.length; i++) {
					stageIds[i] = stages[i].getId();
				}
				// Return the array of stage IDs
				return stageIds;
			}
		}
		// If no race with the provided ID is found, throw an exception
		throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
	}


	public double getStageLength(int stageId) throws IDNotRecognisedException {
		// Iterate through the list of races
		for (Races race : RacesArray) {
			// Iterate through the stages of each race
			for (Stages stage : race.getStages()) {
				// Check if the current stage has the specified ID
				if (stage.getId() == stageId) {

					System.out.println(stage.getState());

					// Return the length of the stage
					return stage.getlength();
				}
			}
		}

		// If no stage with the provided ID is found, throw an exception
		throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
	}

	@Override
	public void removeStageById(int stageId) throws IDNotRecognisedException {
		// Flag to indicate if the stage with the given ID is found
		boolean stageFound = false;
		
		// Iterate through the list of races
		for (Races race : RacesArray) {
			// Retrieve the array of stages for the current race
			Stages[] stages = race.getStages();
			
			// Iterate through the array of stages
			for (int i = 0; i < stages.length; i++) {
				// Check if the current stage has the specified ID
				if (stages[i].getId() == stageId) {
					// Create a new array to hold stages with one less element
					Stages[] newStages = new Stages[stages.length - 1];
					
					// Copy elements from the original array to the new array, excluding the stage to be removed
					for (int j = 0, k = 0; j < stages.length; j++) {
						if (j != i) {
							newStages[k++] = stages[j];
						}
					}
					
					// Set the stages array of the current race to the new array without the removed stage
					race.setStages(newStages);
					
					// Set the flag to indicate that the stage was found and removed
					stageFound = true;
					
					// Exit the loop once the stage is found and removed
					break;
				}
			}
			
			// If the stage was found and removed, exit the outer loop
			if (stageFound) {
				break;
			}
		}
		
		// If no stage with the provided ID is found, throw an exception
		if (!stageFound) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
	}


	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	// Checkpoint type
	// Preparation stage state
	@Override
	public int addCategorizedClimbToStage(int stageId, Double location, CheckpointType type, Double averageGradient,
			Double length) throws IDNotRecognisedException, InvalidLocationException, InvalidStageStateException,
			InvalidStageTypeException {
		for (Races race : RacesArray){
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) { 
					// Check if stage type is suitable for adding a categorized climb
					if (type != CheckpointType.C1 && type != CheckpointType.C2 && type != CheckpointType.C3 && type != CheckpointType.C4 && type != CheckpointType.HC){
						throw new InvalidStageTypeException("The stage type must be C1, C2, C3, C4, HC to add a categorized climb");
					}
					//Check if the stage is in a state that allows for a categorized climb to be added
					if (stage.getState() != StageState.PREPARATION) {
						throw new InvalidStageStateException("The stage must be in the PREPARATION state to add a categorized climb");
					}
					//Check if the location is valid
					if (location < 0 || location > stage.getlength()) {
						throw new InvalidLocationException("The location must be between 0 and the stage length");
					}
					// If all checks are successful, add the categorized climb
					CategorizedClimb climb = new CategorizedClimb(location, type, averageGradient, length);
					stage.addCategorizedClimb(climb);
					return climb.getId();
				}
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
		
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public int addIntermediateSprintToStage(int stageId, double location) throws IDNotRecognisedException,
			InvalidLocationException, InvalidStageStateException, InvalidStageTypeException {
		for (Races race : RacesArray) {
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) { 
					// Check if stage type is suitable for adding an intermediate sprint
					if (stage.getStageType() == StageType.TT) {
						throw new InvalidStageTypeException("The stage type must be a SPRINT to add an intermediate sprint");
					}
					//Check if the stage is in a state that allows for an intermediate sprint to be added
					if (stage.getState() != StageState.PREPARATION) {
						throw new InvalidStageStateException("The stage must be in the PREPARATION state to add an intermediate sprint");
					}
					//Check if the location is valid
					if (location < 0 || location > stage.getlength()) {
						throw new InvalidLocationException("The location must be between 0 and the stage length");
					}
					// If all checks are successful, add the intermediate sprint
					IntermediateSprint sprint = new IntermediateSprint(location);
					stage.addIntermediateSprint(sprint);
					return sprint.getId();
				}
			}
		}
		throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
	}
//------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public void removeCheckpoint(int checkpointId) throws IDNotRecognisedException, InvalidStageStateException {
		for (Races race : RacesArray) {
			for (Stages stage : race.getStages()) {
				Checkpoint[] checkpoints = stage.getCheckpoints();
				for (int i = 0; i < checkpoints.length; i++) {
					if (checkpoints[i].getCheckpointId() == checkpointId) {
						//Check if the stage is in a state that allows for a checkpoint to be removed
						if (stage.getState() != StageState.PREPARATION) {
							throw new InvalidStageStateException("The stage must be in the PREPARATION state to remove a checkpoint");
						}
						// Create new array, without checkpoint to be removed
						Checkpoint[] newCheckpoints = new Checkpoint[checkpoints.length - 1];
						for (int j = 0, k = 0; j < checkpoints.length; j++) {
							if (j != i) continue;
								newCheckpoints[k++] = checkpoints[j];
						}
						stage.setCheckpoints(newCheckpoints);
						return;
						
					}
				}
			}
		}

		
		throw new IDNotRecognisedException("No checkpoint with ID" + checkpointId + "was found");
	}

	


	@Override
	public void concludeStagePreparation(int stageId) throws IDNotRecognisedException, InvalidStageStateException {
		boolean stageFound = false;
		for (Races race : RacesArray) {
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) {
					//Check if the stage is in the PREPARATION state
					if (stage.getState() != StageState.PREPARATION) {
						throw new InvalidStageStateException("The stage must be in the PREPARATION state to conclude stage preparation");
					}
					//Change the state of the stage to ONGOING
					stage.setState(StageState.ONGOING);
					stageFound = true;
					break;
				}
			}
			if (stageFound) {
				break;
			}
		}
		if (!stageFound) {
			throw new IDNotRecognisedException("No stage with ID" + stageId + "was found");
		}

	}

	@Override
	public int[] getStageCheckpoints(int stageId) throws IDNotRecognisedException {
		// Iterate through the RacesArray to find the stage with the given ID
		for (Races race : RacesArray) {
			for (Stages stage : race.getStages()) {
				// Check if the current stage has the specified ID
				if (stage.getId() == stageId) {
					// Retrieve the array of checkpoints associated with the stage
					Checkpoint[] checkpoints = stage.getCheckpoints();
					
					if (checkpoints == null) {
						return new int[0];
					}

					// Create an array to store the IDs of checkpoints
					int[] checkpointIds = new int[checkpoints.length];
					
					// Populate the array with the IDs of checkpoints
					for (int i = 0; i < checkpoints.length; i++) {
						checkpointIds[i] = checkpoints[i].getCheckpointId();
					}
					
					// Return the array of checkpoint IDs
					return checkpointIds;
				}
			}
		}
		
		// If no stage with the provided ID is found, throw an exception
		throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
	}

	
	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override
	public int createTeam(String name, String description) throws IllegalNameException, InvalidNameException {
		// Check if name is valid
		if (name == null || name.isEmpty() || name.length() > 50 || name.trim().isEmpty()) {
			throw new InvalidNameException("Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace");
		}

		// Check if name is legal
		for (Team team : TeamsArray) {
			if (team.getTeamName().equals(name)) {
				throw new IllegalNameException("A team with this name already exists");
			}
		}
		//generate ID for new team
		int NewTeamId = generateTeamId();
		// Create a new team and add it to the list
		Team newTeam = new Team(name, NewTeamId, description);
		//Add the new team to the array
		TeamsArray = Arrays.copyOf(TeamsArray, TeamsArray.length + 1);
		TeamsArray[TeamsArray.length-1] = newTeam;
		return newTeam.getId();
	}
	//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override
	public void removeTeam(int teamId) throws IDNotRecognisedException {
		//Initialize the index of the team to remove as -1 (an invalid index)
		int indexToRemove = -1;

		//iterate through the teams array to find the team with the given ID
		for (int i = 0; i < TeamsArray.length; i++) {
			// If the ID of the current team matches the given team ID
			if (TeamsArray[i].getId() == teamId) {
				// Set the index of the team to remove as the current index
				indexToRemove = i;
				break;
			}
		}
		//If no team with the given ID was found, throw an exception
		if (indexToRemove == -1) {
			throw new IDNotRecognisedException("No team with ID" + teamId + "was found");
		}

		//Create new array without the team to remove
		Team[] newTeamsArray = new Team[TeamsArray.length - 1];

		//Iterate over the original teams array
		for (int i = 0, j = 0; i < TeamsArray.length; i++) {
			//if current index is the index to be removed, skip the iteration
			if (i == indexToRemove) continue;
			//Add current team to new rray and increment the index for the new array
			newTeamsArray[j++] = TeamsArray[i];
		}
		//Replace the original array with the new array
		TeamsArray = newTeamsArray;

	}
	
	@Override
	public int[] getTeams() {
		// Create an array to store the IDs of teams
		int[] teamIds = new int[TeamsArray.length];
		
		// Iterate through the TeamsArray to retrieve team IDs
		for (int i = 0; i < TeamsArray.length; i++) {
			// Populate the array with the IDs of teams
			teamIds[i] = TeamsArray[i].getId();
		}
		
		// Return the array of team IDs
		return teamIds;
	}

	@Override
	public int[] getTeamRiders(int teamId) throws IDNotRecognisedException {
		// Iterate through the TeamsArray to find the team with the specified ID
		for (Team team : TeamsArray) {
			// Check if the current team's ID matches the specified team ID
			if (team.getId() == teamId) {
				// Retrieve the riders belonging to the team
				Rider[] riders = team.getRiders();
				// Create an array to store the IDs of riders
				int[] riderIds = new int[riders.length];
				// Populate the array with the IDs of riders
				for (int i = 0; i < riders.length; i++) {
					riderIds[i] = riders[i].getRiderId();
				}
				// Return the array of rider IDs
				return riderIds;
			}
		}
		// If no team with the specified ID is found, throw an exception
		throw new IDNotRecognisedException("No team with ID " + teamId + " was found");
	}

	@Override
	public int createRider(int teamID, String name, int yearOfBirth)
			throws IDNotRecognisedException, IllegalArgumentException {
		// Check if team exists
		Team team = null;
		for (int i = 0; i < TeamsArray.length; i++) {
			if (TeamsArray[i].getId() == teamID) {
				team = TeamsArray[i];
				break;
			}
		}
		if (team == null) {
			throw new IDNotRecognisedException("No team with ID " + teamID + " was found");
		}
		// Check if the name is valid
		if (name == null || name.isEmpty() || name.length() > 50 || name.trim().isEmpty()) {
			throw new IllegalArgumentException("Name must not be null or empty, must not exceed character limit of 50 and characters must contain not only whitespace");
		}
		// Check if the year of birth is valid, i think you have to be at least 18 to be a professional cyclist
		// Of course you cant have someone who is really old
		if (yearOfBirth <= 1900 || yearOfBirth >= 2005) {
			throw new IllegalArgumentException("Year of birth must be between 1900 and 2005");
		}
		// Create a new Rider and add it to the team
		//generate ID for new rider
		int NewRiderId = generateRiderId();
		Rider newRider = new Rider(name, teamID, NewRiderId, yearOfBirth);
		// Add the new team to the array
		RidersArray = Arrays.copyOf(RidersArray, RidersArray.length + 1);
		RidersArray[RidersArray.length-1] = newRider;

		team.addRider(newRider); 

		return newRider.getRiderId();
	}
	

	@Override
	public void removeRider(int riderId) throws IDNotRecognisedException {
		// Initialize a flag to track whether the rider is found
		boolean riderFound = false;
		// Iterate through each team in the TeamsArray
		for (Team team : TeamsArray) {
			// Retrieve the array of riders belonging to the current team
			Rider[] riders = team.getRiders();
			// Iterate through the riders array of the current team
			for (int i = 0; i < riders.length; i++) {
				// Check if the current rider's ID matches the specified rider ID
				if (riders[i].getRiderId() == riderId) {
					// Create a new array to store the remaining riders after removal
					Rider[] newRiders = new Rider[riders.length - 1];
					// Populate the new array, excluding the rider to be removed
					for (int j = 0, k = 0; j < riders.length; j++) {
						if (j == i) continue; // Skip the rider to be removed
						newRiders[k++] = riders[j];
					}
					// Set the team's riders array to the new array without the removed rider
					team.setRiders(newRiders);
					// Set the riderFound flag to true
					riderFound = true;
					// Exit the loop once the rider is removed
					break;
				}
			}
			// If the rider is found and removed, exit the outer loop
			if (riderFound) {
				break;
			}
		}
		// If the rider is not found in any team, throw an exception
		if (!riderFound) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found");
		}
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override//DONE
	public void registerRiderResultsInStage(int stageId, int riderId, LocalTime... checkpoints) throws IDNotRecognisedException, DuplicatedResultException, InvalidCheckpointTimesException, InvalidStageStateException {
		// Iterating though the race and stage lists, to find the correct object
		boolean RiderAdded = false;
		
		for (Races race : RacesArray) {
			boolean StageFound = false;
			for (Stages stage : race.getStages()) {
				if (stage.getId() == stageId) {
					//Setting variable to true, as stage id found
					StageFound = true;
					// getting the checkpoints from that stage
					Checkpoint[] AmountOfCheckpoints = stage.getCheckpoints();

					//Check if the stage is in a state that allows for an intermediate sprint to be added
					if (stage.getState() == StageState.PREPARATION) {
						throw new InvalidStageStateException("The stage must be in the ONGOING state to add a new rider results");
					} else if (checkpoints.length  == ((AmountOfCheckpoints.length) + 2)) {
						
						//Check if the rider's results already exist for the stage
						List<List<Object>> TempResultsList = stage.getResultsList();
						for (List<Object> result : TempResultsList) {
							int RiderId = (int) result.get(1);
							if (RiderId == riderId) {
								throw new DuplicatedResultException("the rider's results for the stage already exist");
							}
						}


						int counterToSeeIfFirst = 0;
						LocalTime PreviousCheckpoint = null;
						// Checking that the checkpoint times follow in chronological order, by comparing the times in nano seconds.
						for (LocalTime checkpoint : checkpoints) {
							if (counterToSeeIfFirst == 0) {
								PreviousCheckpoint = checkpoint;
							} else {
								long PreviousTime = PreviousCheckpoint.toNanoOfDay();
								long CurrentTime = checkpoint.toNanoOfDay();

								if (PreviousTime >= CurrentTime) {
									throw new InvalidCheckpointTimesException("The checkpoints must follow in chronological order");
								} else {
									PreviousCheckpoint = checkpoint;
								}
							}
							
							counterToSeeIfFirst ++;
						}
						
						
						// Register the rider's results for the stage
						if (!RiderAdded) {
							stage.AddRidersToList(stageId, riderId, checkpoints);
							RiderAdded = true;
						} 
						

					} else {
						System.err.println("Number of checkpoints invalid");
					}
					

				}
			}
			if (StageFound == false) {
				throw new IDNotRecognisedException("No Stage with ID " + stageId + " was found");
			}
		}
	}

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override// DONE
	public LocalTime[] getRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Declare variable to store the stage
		Stages foundStage = null;

		for (Stages stage : StagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;			
			}
		}
		// As if foundStage is still null, that means no stage with the given stageId was found
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get results of the rider
		LocalTime[] results = foundStage.getRiderResultsInStageMethod(stageId,riderId);
		if (results == null) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}
		return results;
	}

	@Override// DONE
	public LocalTime getRiderAdjustedElapsedTimeInStage(int stageId, int riderId) throws IDNotRecognisedException {
		//Find stage
		Stages foundStage = null;
		for (Stages stage : StagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the rider
		LocalTime RiderAdjustedElapsedTime = foundStage.getRiderAdjustedElapsedTimeInStageMethod(stageId,riderId);
		
		if (RiderAdjustedElapsedTime == null) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}
		
		return RiderAdjustedElapsedTime;
	}

	@Override// DONE
	public void deleteRiderResultsInStage(int stageId, int riderId) throws IDNotRecognisedException {
		// Find stage
		Stages foundStage = null;
		for (Stages stage : StagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		// Throwing excpetion as no stage with Id of parameter stageId found
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		
		}
		// Delete the results of the rider
		boolean deleted = foundStage.DeleteRider( stageId, riderId);
		if (!deleted) {
			throw new IDNotRecognisedException("No rider with ID " + riderId + " was found in stage with ID " + stageId);
		}

	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override// DONE
	public int[] getRidersRankInStage(int stageId) throws IDNotRecognisedException {
		// Find the stage with the given stage ID
		Stages foundStage = null;
		for (Stages stage : StagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		// If the stage is not found, throw an exception
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Retrieve the ranked rider IDs for the stage
		int[] rankedRiderIds = foundStage.getRidersRankInStageMethod(stageId);
		// If no riders are ranked for the stage, return an empty array
		if (rankedRiderIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			// Otherwise, return the array of ranked rider IDs
			return rankedRiderIds;
		}
	}
//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
	@Override//DONE
	public LocalTime[] getRankedAdjustedElapsedTimesInStage(int stageId) throws IDNotRecognisedException {

		// Finds the stage
		Stages foundStage = null;
		for (Stages stage : StagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the results of the stage
		LocalTime[] getRankedAdjustedElapsedTimes = foundStage.getRankedAdjustedElapsedTimesInStageMethod(stageId);
		// If the result is null, returns an empty LocalTime array
		if (getRankedAdjustedElapsedTimes == null) {
			LocalTime[] EmptyArray = {};
			return EmptyArray;
		} else {
			return getRankedAdjustedElapsedTimes;
		}

	}


	@Override// DONE
	public int[] getRidersPointsInStage(int stageId) throws IDNotRecognisedException {
		// Find the stage with the given stage ID
		Stages foundStage = null;
		for (Stages stage : StagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}
		// If the stage is not found, throw an exception
		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		}
		// Get the points earned by riders in the stage
		int[] riderPoints = foundStage.getRidersPointsInStageMethod(stageId);
		// If no points are available for the stage, return an empty array
		if (riderPoints == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			// Otherwise, return the array of rider points
			return riderPoints;
		}
	}

	@Override// DONE
	public int[] getRidersMountainPointsInStage(int stageId) throws IDNotRecognisedException {
		// Finds the stage
		Stages foundStage = null;
		for (Stages stage : StagesArray) {
			if (stage.getId() == stageId) {
				foundStage = stage;
				break;
			}
		}

		if (foundStage == null) {
			throw new IDNotRecognisedException("No stage with ID " + stageId + " was found");
		
		}
		//Get results of the stage
		int[] riderPoints = foundStage.getRidersMountainPointsInStage();
		if (riderPoints == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		}else {
			return riderPoints;
		}
	}

//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++

	@Override
	public void eraseCyclingPortal() {
		// Clears all data from cycling portal
		//As we are using arrays, I will just create new empty arrays and assign them to the existing arrays
		StagesArray = new Stages[0];
		RidersArray = new Rider[0];
		TeamsArray = new Team[0];
		RacesArray = new Races[0];

	}

	@Override
	public void saveCyclingPortal(String filename) throws IOException {
		//saves the current state of the cycling portal to a file
		//Object output stream is used to write objects to a file
		try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
			out.writeObject(StagesArray);
			out.writeObject(RidersArray);
			out.writeObject(TeamsArray);
			out.writeObject(RacesArray);
		} catch (IOException e) {
			System.err.println("An error occured while saving the cycling portal" + e.getMessage());
		}
		
	}

	@Override
	public void loadCyclingPortal(String filename) throws IOException, ClassNotFoundException {
		//Loads the state of the cycling portal from a file
		try {
			FileInputStream fileStream = new FileInputStream(filename);
			ObjectInputStream objectSteam = new ObjectInputStream(fileStream);
			
			// Read the data into arrays
			Stages[] stages = (Stages[]) objectSteam.readObject();
			Rider[] riders = (Rider[]) objectSteam.readObject();
			Team[] teams = (Team[]) objectSteam.readObject();
			Races[] races = (Races[]) objectSteam.readObject();

			this.StagesArray = stages;
			this.RidersArray = riders;
			this.TeamsArray = teams;
			this.RacesArray = races;

			objectSteam.close();
			fileStream.close();
		} catch (IOException e) {
			System.err.println("An error occured while loading the cycling portal: " + e.getMessage());
		} catch (ClassNotFoundException e) {
			System.err.println("A class not found error occured while loading the cycling portal: " + e.getMessage());
		}

	}



	// The next few methods are not in minicycling portal
	//An error occurs for every single one of these before I have edited them

	@Override
	public void removeRaceByName(String name) throws NameNotRecognisedException {
		// Removes a race from the races list based on a name
		Races raceToRemove = null;
		for (Races race : RacesArray) {
			if (race.getRacename().equals(name)) {
				raceToRemove = race;
				break;
			}
		}
		if (raceToRemove == null) {
			throw new NameNotRecognisedException("No race with name" + name + "was found");
		}
		List<Races> racesList = new ArrayList<>(Arrays.asList(RacesArray));
		racesList.remove(raceToRemove);
		RacesArray = racesList.toArray(new Races[0]);

	}


//----------------------------------------------------------------------------------------------------------------------------------------------------------

	@Override// DONE
	public LocalTime[] getGeneralClassificationTimesInRace(int raceId) throws IDNotRecognisedException {
		// Returns an array of general classification times of riders in the given race
		// Find the race
		Races foundRace = null;
		for (Races race : RacesArray) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
		}
		// get the summed times of all stages for the race
		LocalTime[] RankedSumOfAdjustedElapasedTimes = foundRace.getGeneralClassificationTimesInRacemethod();
		// Checking if it is null then, return empty array.
		if (RankedSumOfAdjustedElapasedTimes == null) {
			LocalTime[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RankedSumOfAdjustedElapasedTimes;
		}
	}


	@Override// DONE
	public int[] getRidersPointsInRace(int raceId) throws IDNotRecognisedException {
		// Find the race with the given race ID
		Races race = null;
		for (Races r : RacesArray) {
			if (r.getId() == raceId) {
				race = r;
				break;
			}
		}
		// If the race is not found, throw an exception
		if (race == null) {
			throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
		}
		// Get the points earned by riders in the race
		int[] ridersPointsRanked = race.getRidersPointsInRace();
		// If no points are available for the race, return an empty array
		if (ridersPointsRanked == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			// Otherwise, return the array of rider points
			return ridersPointsRanked;
		} 
	}

	@Override//DONE
	public int[] getRidersMountainPointsInRace(int raceId) throws IDNotRecognisedException {
		// Find the race with the given race ID
		Races race = null;
		for (Races r : RacesArray) {
			if (r.getId() == raceId) {
				race = r;
				break;
			}
		}
		// If the race is not found, throw an exception
		if (race == null) {
			throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
		}
		// Get the mountain points earned by riders in the race
		int[] ridersPointsRanked = race.getRidersMountainPointsInRace();
		// If no mountain points are available for the race, return an empty array
		if (ridersPointsRanked == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			// Otherwise, return the array of rider mountain points
			return ridersPointsRanked;
		}
	}

//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++=


	@Override// DONE
	public int[] getRidersGeneralClassificationRank(int raceId) throws IDNotRecognisedException {
		// find the race
		Races foundRace = null;
		for (Races race : RacesArray) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID" + raceId + "was found");
		}
		// get the ranked Id's based off of their sum of adjusted elapsed times in all stages
		int[] RankedRiderIds = foundRace.getRidersGeneralClassificationRankMethod();
		// Checking if it is null then, return empty array.
		if (RankedRiderIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			return RankedRiderIds;
		}

	}


	@Override// DONE
	public int[] getRidersPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// Find the race with the given race ID
		Races foundRace = null;
		for (Races race : RacesArray) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		// If the race is not found, throw an exception
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
		}
		// Get the classification rank of riders based on points earned in the race
		int[] rankedListOfIds = foundRace.getRidersPointClassificationRank();
		// If no classification ranks are available for the race, return an empty array
		if (rankedListOfIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			// Otherwise, return the array of rider classification ranks
			return rankedListOfIds;
		} 
	}

	@Override//DONE
	public int[] getRidersMountainPointClassificationRank(int raceId) throws IDNotRecognisedException {
		// Find the race with the given race ID
		Races foundRace = null;
		for (Races race : RacesArray) {
			if (race.getId() == raceId) {
				foundRace = race;
				break;
			}
		}
		// If the race is not found, throw an exception
		if (foundRace == null) {
			throw new IDNotRecognisedException("No race with ID " + raceId + " was found");
		}
		// Get the classification rank of riders based on mountain points earned in the race
		int[] rankedListOfIds = foundRace.getRidersMountainPointClassificationRank();
		// If no classification ranks are available for the race, return an empty array
		if (rankedListOfIds == null) {
			int[] EmptyArray = {};
			return EmptyArray;
		} else {
			// Otherwise, return the array of rider classification ranks
			return rankedListOfIds;
		}
	}

}

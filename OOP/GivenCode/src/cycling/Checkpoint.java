package cycling;

/**
 * Represents a checkpoint in a cycling race stage.
 * Checkpoints are specific locations along the race route where various events, such as categorized climbs
 * or intermediate sprints, may occur.
 * Each checkpoint is identified by a unique ID and has a checkpoint type.
 */

public class Checkpoint {
    private int CheckpointId; 
    private CheckpointType checkpointType;

    /**
     * Constructs a new Checkpoint object with the specified ID and checkpoint type.
     * 
     * @param checkpointId The unique identifier for the checkpoint.
     * @param checkpointType The type of checkpoint (e.g., categorized climb, intermediate sprint).
     */

    //Constructor
    public Checkpoint(int CheckpointId, CheckpointType checkpointType) {
        this.CheckpointId = CheckpointId;
        this.checkpointType = checkpointType;
    }

    //Get Checkpoint Id
    public int getCheckpointId() {
        return CheckpointId;
    }

    //Create an instance of checkpointType
    public CheckpointType getCheckpointType() {
        return checkpointType;
    }
}
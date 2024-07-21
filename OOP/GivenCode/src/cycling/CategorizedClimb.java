package cycling;

/**
 * Represents a categorized climb checkpoint in a cycling race stage.
 * Categorized climbs are specific locations along the race route where cyclists
 * encounter significant inclines, categorized based on their difficulty level.
 */

public class CategorizedClimb {
    private static int idCount = 0;
    private int id;
    private Double location;
    private CheckpointType type;
    private Double averageGradient;
    private Double length;

    /**
     * Constructs a new CategorizedClimb object with the specified location, type, average gradient, and length.
     * 
     * @param location The location along the race route where the categorized climb is situated.
     * @param type The type of categorized climb checkpoint.
     * @param averageGradient The average gradient of the climb, representing its steepness.
     * @param length The length of the climb in kilometers.
     */

    public CategorizedClimb(Double location, CheckpointType type, Double averageGradient, Double length) {
        this.id = idCount++;
        this.location = location;
        this.type = type;
        this.averageGradient = averageGradient;
        this.length = length;
    }

    // Getter and setters of all the attributes

    public int getId() {
        return id;
    }

    public Double getLocation() {
        return location;
    }

    public CheckpointType getType() {
        return type;
    }

    public Double getAverageGradient() {
        return averageGradient;
    }

    public Double getLength() {
        return length;
    }
}

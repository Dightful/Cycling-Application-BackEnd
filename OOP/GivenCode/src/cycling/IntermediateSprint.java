package cycling;
/**
 * Represents an intermediate sprint checkpoint in a cycling race stage.
 * Intermediate sprints are specific locations along the race route where cyclists
 * compete for points in sprinting contests.
 */
public class IntermediateSprint {
    private static int idCounter = 0;
    private int id;
    private Double location;

    /**
     * Constructs a new IntermediateSprint object with the specified location.
     * 
     * @param location The location along the race route where the intermediate sprint is situated.
     */

    public IntermediateSprint(Double location) {
        this.id = idCounter++;
        this.location = location;
    }

    // Getter and setter

    public int getId() {
        return id;
    }

    public Double getLocation() {
        return location;
    }
}

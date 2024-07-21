package cycling;

import java.io.Serializable;

/**
 * Represents a cycling team.
 * This class contains information about the team name, nationality, riders, team ID, and description.
 * It also provides methods to retrieve and update this information.
 */

public class Team implements Serializable {
    private String name;
    private String Nationality;
    private Rider[] Riders;
    private int TeamId;
    private String Description; 

    /**
     * Constructs a Team object with the given name, team ID, and description.
     *
     * @param name        The name of the team.
     * @param teamId      The unique identifier for the team.
     * @param description A description of the team.
     */

    public Team(String name, int TeamId, String Description) {
        this.name = name;
        this.TeamId = TeamId;
        this.Description = Description;
    }
    // Getters and setters for all attributes
    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }
    
    public String getTeamName() {
        return name;
    }

    public void setTeamName(String name) {
        this.name = name;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String Nationality) {
        this.Nationality = Nationality;
    }

    public Rider[] getRiders() {
        return Riders;
    }

    public void setRiders(Rider[] Riders) {
        this.Riders = Riders;
    }

    public int getId() {
        return TeamId;
    }

    public void setId(int TeamId) {
        this.TeamId = TeamId;
    }

    public void addRider(Rider newRider) {
        // If the list of riders is currently empty
        if (Riders == null) {
            // Create a new array with a size of 1 and add the new rider
            Riders = new Rider[1];
            Riders[0] = newRider;
        } else {
            // If the list of riders is not empty
            // Create a new array with a size one greater than the current list
            Rider[] newRiders = new Rider[Riders.length + 1];
            // Copy the existing riders to the new array
            System.arraycopy(Riders, 0, newRiders, 0, Riders.length);
            // Add the new rider to the end of the new array
            newRiders[Riders.length] = newRider;
            // Update the reference to the list of riders to point to the new array
            Riders = newRiders;
        }
    }


}

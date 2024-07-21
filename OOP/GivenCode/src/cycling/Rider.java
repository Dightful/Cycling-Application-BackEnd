package cycling;

import java.io.Serializable;
/**
 * Represents a rider in cycling.
 * This class contains information about the rider's name, nationality, age, rider ID, team ID,
 * associated team, and year of birth.
 * It also provides methods to retrieve and update this information.
 */

public class Rider implements Serializable {
    private String name;
    private String Nationality;
    private int Age;
    private int RiderId;
    private int TeamId;
    private Team team;
    private int YearOfBirth;

    /**
     * Constructs a Rider object with the given name, team ID, rider ID, and year of birth.
     *
     * @param name        The name of the rider.
     * @param teamId      The ID of the team the rider belongs to.
     * @param riderId     The unique identifier for the rider.
     * @param yearOfBirth The year of birth of the rider.
     */
    public Rider(String name, int TeamId, int RiderId, int YearOfBirth) {
        this.name = name;
        this.RiderId = RiderId;
        this.TeamId = TeamId;
        this.YearOfBirth = YearOfBirth;

    }
    //getting and setting for all attributes

    public String getname() {
        return name;
    }

    public void setname(String Name) {
        Name = name;
    }

    public String getNationality() {
        return Nationality;
    }

    public void setNationality(String nationality) {
        Nationality = nationality;
    }

    public int getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public int getRiderId() {
        return RiderId;
    }

    public void setRiderId(int RiderId) {
        this.RiderId = RiderId;
    }

    public int getTeamId() {
        return TeamId;
    }

    public void setTeamId(int TeamId) {
        this.TeamId = TeamId;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
    
    public int getYearOfBirth() {
        return YearOfBirth;
    }

    public void setYearOfBirth(int YearOfBirth) {
        this.YearOfBirth = YearOfBirth;
    }

    // Returning the name in a more readable format
    public String toString() {
        return "Rider: " + name; 
    }

}

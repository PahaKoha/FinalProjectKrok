package pkoh.krokFinalProject.entities;

import java.util.Objects;

/**
 * Сущность кандидата
 */
public class Candidate {
    private int id;
    private final String name;
    private final String surname;
    private final int age;
    private final int politicalExperience;
    private final String electionProgram;

    public Candidate(int id, String name, String surname, int age, int politicalExperience, String electionProgram) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.politicalExperience = politicalExperience;
        this.electionProgram = electionProgram;
    }

    public Candidate(String name, String surname, int age, int politicalExperience, String electionProgram) {
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.politicalExperience = politicalExperience;
        this.electionProgram = electionProgram;
    }

    public int getId() {return id;}
    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    public int getPoliticalExperience() {
        return politicalExperience;
    }

    public String getElectionProgram() {
        return electionProgram;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Candidate candidate = (Candidate) o;
        return id == candidate.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age, politicalExperience, electionProgram);
    }

    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                ", politicalExperience=" + politicalExperience +
                ", electionProgram='" + electionProgram + '\'' +
                '}';
    }
}

package mpr.proj.pedigree;

public class Horse {

    private long id;
    private String name;
    private Sex sex;
    private DateOfBirth dob;
    private Color color;
    private Horse sire;
    private Horse dam;
    private Breeder breeder;

    public Horse(long id, String name, Sex sex, DateOfBirth dob, Color color, Horse sire, Horse dam, Breeder breder) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.dob = dob;
        this.color = color;
        this.sire = sire;
        this.dam = dam;
        this.breeder = breeder;
    }

    public void setName(String name) {
    	this.name = name;
    }

    public String getName() {
    	return name;
    }

    // oraz kolejne "settery i gettery"

}

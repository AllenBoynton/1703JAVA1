// Allen Boynton

// JAV1 - 1703

// Person.java

package edu.fullsail.aboynton.boyntonallen_ce06.Object;


public class Person {

    private String name;
    private String birthday;
    private Integer pictureIds;

    public Person(String name, String birthday, Integer pictureIds) {
        this.name = name;
        this.birthday = birthday;
        this.pictureIds = pictureIds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getPictureIds() {
        return pictureIds;
    }

    public void setPictureIds(Integer pictureIds) {
        this.pictureIds = pictureIds;
    }
}

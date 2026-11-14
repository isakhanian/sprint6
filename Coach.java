import java.util.Objects;

public class Coach {
    private String surname;
    private String name;
    private String middleName;

    public Coach(String surname, String name, String middleName) {
        this.surname = surname;
        this.name = name;
        this.middleName = middleName;
    }

    public String getSurname() {
        return surname;
    }

    public String getName() {
        return name;
    }

    public String getMiddleName() {
        return middleName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Coach other = (Coach) o;

        return Objects.equals(surname, other.surname)
            && Objects.equals(name, other.name)
            && Objects.equals(middleName, other.middleName);
}
    @Override
    public int hashCode() {
        return Objects.hash(surname, name, middleName);
}

}
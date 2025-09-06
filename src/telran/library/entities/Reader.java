package telran.library.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

public class Reader implements Serializable {
    private int readerId;
    private String name;
    private String phone;
    private LocalDate birthDay;

    public Reader() {
    }

    public Reader(int readerId, String name, String phone, LocalDate birthDay) {
        if (readerId > 0)
            this.readerId = readerId;
        this.name = name;
        this.phone = phone;
        this.birthDay = birthDay;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getReaderId() {
        return readerId;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reader reader = (Reader) o;
        return readerId == reader.readerId;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(readerId);
    }

    @Override
    public String toString() {
        return "Reader{" +
                "readerId=" + getReaderId() +
                ", name='" + getName() + '\'' +
                ", phone='" + getPhone() + '\'' +
                ", birthDay=" + getBirthDay() +
                '}';
    }
}

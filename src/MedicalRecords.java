/**
 * Represents the object to be sorted.
 */
public class MedicalRecords implements Comparable<MedicalRecords> {
    String lastName;
    String firstName;
    String patientID;
    String gender;
    int contactInfo;
    String medications;
    String reasonForVisit;
    String physician;

    /**
     * Getter Methods
     * */
    // Input Methods

    /**
     * Setter Methods
     * */
    // Input Methods

    /**
     * compareTo Method
     * */
    @Override
    public int compareTo(MedicalRecords o) {
        return this.lastName.compareTo(o.lastName);
    }
}

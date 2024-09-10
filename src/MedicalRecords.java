/**
 * Represents a medical record with various attributes related to a patient.
 * Implements {@code Comparable<MedicalRecords>} to enable sorting based on the last name.
 * <p>
 * The {@code MedicalRecords} class encapsulates information such as the patient's last name, first name,
 * patient ID, gender, contact information, medications, reason for visit, and physician.
 * </p>
 *
 * @author Hyowon Bernabe
 */
public class MedicalRecords implements Comparable<MedicalRecords> {
    private String lastName;
    private String firstName;
    private String patientID;
    private String gender;
    private long contactInfo;
    private String medications;
    private String reasonForVisit;
    private String physician;

    /**
     * Constructs a new {@code MedicalRecords} object with the specified details.
     *
     * @param lastName The last name of the patient.
     * @param firstName The first name of the patient.
     * @param patientID The ID of the patient.
     * @param gender The gender of the patient.
     * @param contactInfo The contact information of the patient.
     * @param medications The medications prescribed to the patient.
     * @param reasonForVisit The reason for the patient's visit.
     * @param physician The physician assigned to the patient.
     */
    public MedicalRecords(String lastName, String firstName, String patientID, String gender, long contactInfo,
                          String medications, String reasonForVisit, String physician) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.patientID = patientID;
        this.gender = gender;
        this.contactInfo = contactInfo;
        this.medications = medications;
        this.reasonForVisit = reasonForVisit;
        this.physician = physician;
    }

    /**
     * Returns the last name of the patient.
     *
     * @return The last name of the patient.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the first name of the patient.
     *
     * @return The first name of the patient.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the ID of the patient.
     *
     * @return The patient ID.
     */
    public String getPatientID() {
        return patientID;
    }

    /**
     * Returns the gender of the patient.
     *
     * @return The gender of the patient.
     */
    public String getGender() {
        return gender;
    }

    /**
     * Returns the contact information of the patient.
     *
     * @return The contact information of the patient.
     */
    public long getContactInfo() {
        return contactInfo;
    }

    /**
     * Returns the medications prescribed to the patient.
     *
     * @return The medications prescribed to the patient.
     */
    public String getMedications() {
        return medications;
    }

    /**
     * Returns the reason for the patient's visit.
     *
     * @return The reason for the visit.
     */
    public String getReasonForVisit() {
        return reasonForVisit;
    }

    /**
     * Returns the physician assigned to the patient.
     *
     * @return The physician assigned to the patient.
     */
    public String getPhysician() {
        return physician;
    }

    /**
     * Sets the last name of the patient.
     *
     * @param lastName The new last name of the patient.
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the first name of the patient.
     *
     * @param firstName The new first name of the patient.
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the ID of the patient.
     *
     * @param patientID The new patient ID.
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * Sets the gender of the patient.
     *
     * @param gender The new gender of the patient.
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Sets the contact information of the patient.
     *
     * @param contactInfo The new contact information of the patient.
     */
    public void setContactInfo(long contactInfo) {
        this.contactInfo = contactInfo;
    }

    /**
     * Sets the medications prescribed to the patient.
     *
     * @param medications The new medications prescribed to the patient.
     */
    public void setMedications(String medications) {
        this.medications = medications;
    }

    /**
     * Sets the reason for the patient's visit.
     *
     * @param reasonForVisit The new reason for the visit.
     */
    public void setReasonForVisit(String reasonForVisit) {
        this.reasonForVisit = reasonForVisit;
    }

    /**
     * Sets the physician assigned to the patient.
     *
     * @param physician The new physician assigned to the patient.
     */
    public void setPhysician(String physician) {
        this.physician = physician;
    }

    /**
     * Returns a string representation of the {@code MedicalRecords} object.
     * <p>
     * The string representation includes all attributes of the patient, formatted for readability.
     * </p>
     *
     * @return A string representation of the {@code MedicalRecords} object.
     */
    @Override
    public String toString() {
        return "{" +
                "lastName='" + lastName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", patientID='" + patientID + '\'' +
                ", gender='" + gender + '\'' +
                ", contactInfo='+639" + contactInfo + '\'' +
                ", medications='" + medications + '\'' +
                ", reasonForVisit='" + reasonForVisit + '\'' +
                ", physician='" + physician + '\'' +
                '}';
    }

    /**
     * Compares this {@code MedicalRecords} object with another {@code MedicalRecords} object based on the last name.
     * <p>
     * The comparison is used for sorting purposes.
     * </p>
     *
     * @param o The {@code MedicalRecords} object to compare with.
     * @return A negative integer, zero, or a positive integer as this object's last name is less than,
     *         equal to, or greater than the specified object's last name.
     */
    @Override
    public int compareTo(MedicalRecords o) {
        return this.lastName.compareTo(o.lastName);
    }
}

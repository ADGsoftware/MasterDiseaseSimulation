package masterdiseasesimulation;

public class TransmissionTestInfoStorage {
    private int percentage;
    private int duration;

    public TransmissionTestInfoStorage(int percentage, int duration) {
        this.percentage = percentage;
        this.duration = duration;
    }

    public int getPercentage() {
        return percentage;
    }

    public int getDuration() {
        return duration;
    }
}
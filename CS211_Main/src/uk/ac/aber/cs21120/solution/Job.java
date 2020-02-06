package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.hospital.*;

/**
 * This class contains the implementation of IJob interface called Job
 * and it's further development
 *
 * @author vek1
 */

public class Job implements IJob, Comparable<IJob>{
    private int id;
    private int priority;
    private int duration;
    private int submitted;

    public Job(int id, int priority, int duration){
        this.id = id;
        this.priority = priority;
        this.duration = duration;
        this.submitted = -1;
    }

    /** Method to get the ID number of Job
     *
     * @return id of the job
     */

    @Override
    public int getID() {
        return id;
    }

    /** Method to get the priority of Job
     *
     * @return priority of the job
     */

    @Override
    public int getPriority() {
        return priority;
    }

    /** Method to update the duration of the job
     */

    @Override
    public void tick() { // for loop??? or later on???
            duration--;
    }

    /** Method to check if the job is completed
     */

    @Override
    public boolean isDone() {
        return duration <= 0;
    }

    /** Method to check the number of ticks since the job was added to the Simulator
     *
     * @param now current tick
     * @return difference between now and time submitted
     */

    @Override
    public int getTimeSinceSubmit(int now) throws RuntimeException {
        if(submitted == -1){ // throw error if submitted remained default
            throw new RuntimeException("Time submitted was not initialised. Please try again");
        }
        return now - submitted;
    }

    /** Method to check the number of ticks since the job was added to the Simulator
     *
     * @param time tick at which the job was added
     */

    @Override
    public void setSubmitTime(int time) {
        submitted = time;

    }

    /** Method to compare the priority levels of Jobs for further use in Simulator
     *
     * @return 1 if priority of the first job is higher
     * @return -1 if priority of the second job is higher
     * @return 0 if priorities are equal
     */

    @Override
    public int compareTo(IJob j) {
        if(this.getPriority() > j.getPriority()){
            return 1;
        } else if (this.getPriority() < j.getPriority()){
            return -1;
        } else {
            return 0;
        }
    }
}

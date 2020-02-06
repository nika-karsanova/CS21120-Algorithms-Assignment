package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.hospital.*;

import java.util.*;

/**
 * This class contains the implementation of ISimulator interface called Simulator
 * and it's further development
 *
 * @author vek1
 */

public class Simulator implements ISimulator {
    private int freeAmbulances;
    private PriorityQueue<IJob> waitingJobs;
    private Set<IJob> runningJobs;
    private Map<Integer, Integer> totalTime;
    private Map<Integer, Integer> totalNumberOfJobs;
    private int currentTime;


    public Simulator(int freeAmbulances) {
        this.freeAmbulances = freeAmbulances;
        waitingJobs = new PriorityQueue<>();
        runningJobs = new HashSet<>();
        totalTime = new HashMap<>();
        totalNumberOfJobs = new HashMap<>();
        currentTime = 0;

        // creating maps to track the average
        totalTime.put(0, 0);
        totalTime.put(1, 0);
        totalTime.put(2, 0);
        totalTime.put(3, 0);

        totalNumberOfJobs.put(0, 0);
        totalNumberOfJobs.put(1, 0);
        totalNumberOfJobs.put(2, 0);
        totalNumberOfJobs.put(3, 0);


    }

    /**
     * Method to enqueue the jobs in the priority queue
     *
     * @param j the Job we are adding
     */

    @Override
    public void add(IJob j) {
        j.setSubmitTime(currentTime);
        waitingJobs.add(j);
    }

    /**
     * Method to update the simulator by one tick and change the state of Jobs being handled
     */

    @Override
    public void tick() {
        List<IJob> toRemove = new ArrayList<>(); // creating an array list. we will store the jobs that will be removed here
        Iterator<IJob> itr = runningJobs.iterator(); // creating iterator for runningJobs to improve efficiency but also to allow modifications within the code

        while (itr.hasNext()) { // while there are running jobs
            IJob runningJob = itr.next(); // get the next running job
            runningJob.tick(); // and update it
            if(runningJob.isDone()) { // if the job completed
                toRemove.add(runningJob); // add in the ArrayList to remove later
                freeAmbulances++; // free the ambulance that was occupied with that job

                // update the average completion time
                int jobCount = totalNumberOfJobs.get(runningJob.getPriority());
                int oldTime = totalTime.get(runningJob.getPriority());
                int timeToAdd = runningJob.getTimeSinceSubmit(currentTime);

                totalTime.replace(runningJob.getPriority(), oldTime, oldTime + timeToAdd);
                totalNumberOfJobs.replace(runningJob.getPriority(), jobCount, 1 + jobCount);
            }
        }

        runningJobs.removeAll(toRemove); // remove the jobs that completed in this cycle

        while (freeAmbulances > 0 && !waitingJobs.isEmpty()) { // while we have ambulances and waiting jobs
            runningJobs.add(waitingJobs.remove()); // add waiting jobs to running job at the same time as removing it from waiting
            freeAmbulances--; // ambulance than got occupied
        }

        currentTime++; // move to the next tick
    }

    /**
     * Method to simply return current tick count
     *
     * @return currentTime
     */

    @Override
    public int getTime() {
        return currentTime;
    }

    /**
     * Method to check that all the jobs were completed
     *
     * @return true if all the jobs were completed
     */

    @Override
    public boolean allDone() {
        return waitingJobs.isEmpty() && runningJobs.isEmpty(); // if no jobs
    }

    /**
     * Method to return the IDs of all the running Jobs
     *
     * @return set of IDs for running jobs
     */

    @Override
    public Set<Integer> getRunningJobs() {
        Set<Integer> runningJobIDs = new HashSet<>(); // creating a running job set once method is called
        for (IJob runningJob : runningJobs) { // iterate through the running jobs
            runningJobIDs.add(runningJob.getID()); // add all of the running jobs ids in the just created set
        }
        return runningJobIDs; // return set populated with ids
    }

    /**
     * Method to return the average Job completion time
     *
     * @return average Job completion time
     */

    @Override
    public double getAverageJobCompletionTime(int priority) {
        // rounds up to 4 digits after decimal point
        return Math.round(((double) totalTime.get(priority) / (double) totalNumberOfJobs.get(priority)) * 10000.0) / 10000.0; // convert the value into doubles and then divided complete time by number of jobs per priority
    }


}


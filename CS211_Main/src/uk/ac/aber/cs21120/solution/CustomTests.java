package uk.ac.aber.cs21120.solution;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CustomTests {

    /** Ensure that tick in job runs correctly
     */

    @Test
    public void testJobTick(){
        Job j = new Job (0,0,5); // create job

        while(!j.isDone()){ // run until job is done
            j.tick();
        }

        Assertions.assertTrue(j.isDone()); // assert that duration is 0
    }

    /** Ensure that simulator properly works just for one job
     */

    @Test
    public void testWorkForOneJob(){
        Simulator s = new Simulator(1);
        Job j = new Job(1, 2, 3); // our job

        s.add(j); // submission time = 0

        Assertions.assertEquals(0, j.getTimeSinceSubmit(s.getTime())); // ensure that job knows that it's been just added to the queue

        s.tick(); // tick once to move job to running

        Assertions.assertTrue(s.getRunningJobs().contains(j.getID())); // ensure that our job is in the running jobs

        while(!s.allDone()){ //run simulator until job is done
            s.tick();
        }

        Assertions.assertEquals(4, s.getTime()); // ensure that its currently 4th tick
        Assertions.assertTrue(j.isDone() && !s.getRunningJobs().contains(j.getID())); // ensure that the job was done and that our job is not in the running jobs anymore

    }

    /** Ensure that simulator properly works with 2 jobs and 1 ambulance
     */

    @Test
    public void testForFewerAmbulances(){
        Simulator s = new Simulator(1);
        Job j1 = new Job(1, 0, 3);
        Job j2 = new Job (2, 2, 5);

        s.add(j1);
        s.add(j2);

        s.tick(); // run tick

        Assertions.assertEquals(1, s.getRunningJobs().size());
        Assertions.assertTrue(s.getRunningJobs().contains(j1.getID())); // check that only the job with higher priority was moved to the running jobs

        while(!j1.isDone()){ // run until first job is done
            s.tick();
        }

        Assertions.assertEquals(1, s.getRunningJobs().size());
        Assertions.assertTrue(s.getRunningJobs().contains(j2.getID())); // check that the second job was moved into running
    }

    /** Ensure that jobs are being chosen in right priority order
     */

    @Test
    public void testPriorityOrder(){
        Simulator s = new Simulator(1);
        for(int i = 0; i <= 4; i++){
            Job j = new Job(i, i, 3);
            s.add(j); // adding all jobs of different priorities to waitingJobs queue
        }

        s.tick(); // tick to move the prioritised job to the runningJobs set

        for(Integer j : s.getRunningJobs()){
            int i = j; // store the id value of job in runningJobs
            Assertions.assertEquals(0, i); // ensure that it equals to 0 (since our id = priority)
        }

    }
}

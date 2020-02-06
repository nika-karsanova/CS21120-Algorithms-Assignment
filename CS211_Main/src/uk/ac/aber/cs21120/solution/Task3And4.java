package uk.ac.aber.cs21120.solution;
import uk.ac.aber.cs21120.hospital.JobDisplay;
import uk.ac.aber.cs21120.hospital.RandomPriorityGenerator;

import java.util.*;

public class Task3And4 {

    private Scanner scan;
    private static final int NUMBER_OF_TICKS = 10000;
    private static final int NUMBER_OF_AMBULANCES = 20;

    public Task3And4(){
        scan = new Scanner(System.in);
    }

    private void runMenu(){
        int response;
        do {
            printMenu();
            System.out.println("WHAT WOULD YOU LIKE TO DO: ");
            scan = new Scanner(System.in);
            response = scan.nextInt();
            switch (response) {
                case 1:
                    task3();
                    break;
                case 2:
                    task4();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Something went wrong. Please, try again.\n");
            }
        } while (response != 3);
    }

    private void printMenu() {
        System.out.println("");
        System.out.println("1 – RUN TASK 3");
        System.out.println("2 - RUN TASK 4");
        System.out.println("3 - QUIT\n");

    }

    private static void task3(){
        tasksCombined(4);

    }

    private static void task4(){
        for(int a = 4; a <= NUMBER_OF_AMBULANCES; a++){
            System.out.print("\nResults for " + a + " number of ambulances:\n");
            tasksCombined(a);
        }
    }

    /**
     *
     * @param a number of ambulances
     */
    private static void tasksCombined(int a){
        Random random = new Random();
        RandomPriorityGenerator gen = new RandomPriorityGenerator();
        Simulator simulator = new Simulator(a);
        //JobDisplay display = new JobDisplay();

        int id = 1;

        for(int tick = 0; tick <= NUMBER_OF_TICKS; tick++){
            int chance = random.nextInt(3);
            if(chance == 1){
                int duration = random.nextInt(11) + 10;
                int priority = gen.next();
                Job job = new Job(id, priority, duration);
                simulator.add(job);
                id++;
            }
            simulator.tick();
            //display.add(simulator);
        }

        //display.show();

        while(!simulator.allDone()){
            simulator.tick();
        }

        for(int p = 0; p <= 3; p++){
            System.out.print("The average job completion for priority " + p + " is ");
            System.out.println(simulator.getAverageJobCompletionTime(p));
        }
    }


    public static void main(String[] args){
        System.out.println("––––––––––––––––––––––––––––––––");
        System.out.println("WELCOME TO CS21120 ASSIGNMENT");
        System.out.println("––––––––––––––––––––––––––––––––");


        Task3And4 run = new Task3And4();
        run.runMenu();

        System.out.println("–––––––––––––––––––––––––––––––––––––––––––––");
        System.out.println("THANKS FOR CHECKING OUT CS21120 ASSIGNMENT");
        System.out.println("––––––––––––––––––––––––––––––––-------------\n");

    }
}

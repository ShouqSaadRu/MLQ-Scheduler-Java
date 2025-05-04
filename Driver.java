
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.io.*;
public class Driver {

    //------------------------------------attributes------------------------------------
    static int size;   
    static int processID = 0 ; // repersents the process ID.
    static PCB[] Q1 = new PCB[60], Q2 = new PCB[40] ;
    static Scanner in = new Scanner(System.in) ;
    static int user = 0 ;
    static int Q1_index = 0, Q2_index = 0 ; // to know the first empty space

    //------------------------------------main------------------------------------
    public static void main(String[] args) {
        
        do {

            System.out.println("1. Enter process information.\n2. Report detailed information about each process and different scheduling criteria.\n3. Exit the program.");
            user = in.nextInt(); 

            switch (user) {
                
                // CASE 1
                case 1:
                    if ( (Q1.length==Q1_index) && (Q2.length==Q2_index))
                        {
                            System.out.println("Queues are full");
                            break;
                        }
                    System.out.println("Enter the number of processes: ");
                    if (!callInfo(in.nextInt())) 
                            System.out.println("something is wrong!");
                    break;
                
                
                // CASE 2
                case 2:
                scheduleProcesses();
                    break;
                    
                //CASE 3
                case 3:
                    System.out.println("Bye.");
                    System.exit(0);
            
                default:
                    System.out.println("Wrong Input!");
                    System.out.println("Try again");
                    break;
            }

        } while (true);

    }

    // ------------------------------------ A method for the user to enter process info ------------------------------------
    public static boolean enterInfo() {
   
        System.out.println("Enter process priority 1 or 2: "); 
        int priority = in.nextInt() ;
        while(priority != 2 && priority !=1){
            System.out.println("invalid, the priority should be 1 or 2");
            priority = in.nextInt() ;
        }
        if ( (priority==1 && Q1.length==Q1_index) || (priority==2 && Q2.length==Q2_index) )
            return false ;

        System.out.println("Enter process Arrival Time: ");
        double arrivalTime = in.nextDouble() ;

        System.out.println("Enter process CPU burst: ");
        double cpuBurst = in.nextDouble() ;

        PCB process = new PCB(++processID, priority, arrivalTime, cpuBurst);
        // -------------------
        if (priority==1) Q1[Q1_index++] = process ; 
        else 
        Q2[Q2_index++] = process ;

        return true ;
    }
    // ------------------------------------ A method calls enterInfo as many as needed------------------------------------
    public static boolean callInfo(int numOfTimes) {
        size = numOfTimes ; // to know how many processes we have
        for(int i = 0; i<numOfTimes; i++) {
            System.out.println("----------------------------------------------------------");
            System.out.println("Information of process " + (i+1) + ":");
             if (!enterInfo()) return false; 
        }
        System.out.println("----------------------------------------------------------");
        return true;
    }


 // ------------------------------------Method for scheduling the processes, ends with writing on a file ------------------------------------
    public static void scheduleProcesses() {

        
        
        // if (Q1_index == 0 && Q2_index == 0) {
        //     System.out.println("There are no processes!");
        //     return;
        // }
    
        // // Sort processes in Q1 based on arrival time 
        // if (Q1_index > 0) sortByArrivalTime(Q1, Q1_index);
        // if (Q2_index > 0) sortByArrivalTime(Q2, Q2_index);
    
        // double currentTime = 0;
        // if (Q1_index!=0 && Q2_index!=0) currentTime= (Q1[0].getArrivalTime()>Q2[0].getArrivalTime())? Q2[0].getArrivalTime() : Q1[0].getArrivalTime();
        // else if (Q1_index!=0) currentTime=Q1[0].getArrivalTime();
        // else if (Q2_index!=0)currentTime=Q2[0].getArrivalTime();

    

            if (Q1_index == 0 && Q2_index == 0) {
                System.out.println("There are no processes!");
                return;
            }
        
            // Sort processes in Q1 based on arrival time 
            if (Q1_index > 0) sortByArrivalTime(Q1, Q1_index);
            if (Q2_index > 0) sortByArrivalTime(Q2, Q2_index);
        
            double currentTime = 0;
            if (Q1_index != 0 && Q2_index != 0) currentTime = (Q1[0].getArrivalTime() > Q2[0].getArrivalTime()) ? Q2[0].getArrivalTime() : Q1[0].getArrivalTime();
            else if (Q1_index != 0) currentTime = Q1[0].getArrivalTime();
            else if (Q2_index != 0) currentTime = Q2[0].getArrivalTime();
        

        List<PCB> finishedProcessesList = new ArrayList<>();
        List<PCB> notFinishedRoundRobinYet = new ArrayList<>();
       
        PCB currentProcess = null;
    
        // while (Q1_index > 0 || Q2_index > 0 || notFinishedRoundRobinYet.size()>0) {

            while (Q1_index > 0 || Q2_index > 0 || !notFinishedRoundRobinYet.isEmpty()) {

            // Check if there are any processes in Q1 to execute or in the notFinishedRoundRobinYet list
            if (Q1_index > 0 && Q1[0].getArrivalTime() <= currentTime) {
                // There are processes in Q1 and the first process in Q1 is ready to run
                currentProcess = Q1[0];
                Q1_index--; 
                System.arraycopy(Q1, 1, Q1, 0, Q1_index);

            // } else if (!notFinishedRoundRobinYet.isEmpty() && (Q1_index == 0 || notFinishedRoundRobinYet.get(0).getArrivalTime() < Q1[0].getArrivalTime())) {
            } else if (!notFinishedRoundRobinYet.isEmpty() && (Q1_index == 0 || notFinishedRoundRobinYet.get(0).getArrivalTime() < Q1[0].getArrivalTime())) { //------------
                // There are processes in notFinishedRoundRobinYet and either Q1 is empty or the first process in notFinishedRoundRobinYet has an earlier arrival time than the first process in Q1
                currentProcess = notFinishedRoundRobinYet.remove(0);
            }
            
            if (currentProcess != null && currentProcess.getPriority()==1) {
                
                if (currentProcess.getremainingCPUburst() <= 3) { // Process finishes execution within time quantum
                    if (finishedProcessesList.size()==0 && currentProcess.getStartTime()==-1) {
                        currentTime=currentProcess.getArrivalTime();
                        currentProcess.setStartTime(currentTime);
                    }
                    else if (currentProcess.getStartTime()==-1) 
                    currentProcess.setStartTime(currentTime);

                    currentTime += currentProcess.getremainingCPUburst();
                    currentProcess.setTerminationTime(currentTime);
                    currentProcess.setTurnAroundTime(currentProcess.getTerminationTime() - currentProcess.getArrivalTime());
                    currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getCPUburst());
                    currentProcess.setResponseTime(currentProcess.getStartTime() - currentProcess.getArrivalTime());

                    if (finishedProcessesList.isEmpty() || !finishedProcessesList.get(finishedProcessesList.size() - 1).getProcessID().equals(currentProcess.getProcessID()))
                    finishedProcessesList.add(currentProcess); 

            

                } else { // Process needs more CPU burst time

                    if (finishedProcessesList.size()==0 && currentProcess.getStartTime()==-1) {
                        currentTime=currentProcess.getArrivalTime();
                        currentProcess.setStartTime(currentTime);
                    }
                    else if (currentProcess.getStartTime()==-1) currentProcess.setStartTime(currentTime);

                    currentTime += 3;
                    currentProcess.setremainingCPUburst(currentProcess.getremainingCPUburst() - 3);
                    notFinishedRoundRobinYet.add(currentProcess); // Move the current process to the end of the list
                   
                   
                    if(finishedProcessesList.isEmpty())
                    finishedProcessesList.add(currentProcess);
                    else 
                     if (!finishedProcessesList.isEmpty() && !finishedProcessesList.get(finishedProcessesList.size() - 1).getProcessID().equals(currentProcess.getProcessID()))//-----
                        finishedProcessesList.add(currentProcess); 
                    

                    }
                }
            
    
                // Check if Q2 should be processed. This should only happen if:
                // 1. There are processes in Q2.
                // 2. There are no processes in Q1 that are ready to run.
                // 3. There are no processes in notFinishedRoundRobinYet that are ready to run.

                // if (Q2_index > 0 && (Q1_index == 0 || Q1[0].getArrivalTime() > currentTime) && (notFinishedRoundRobinYet.isEmpty())) {
                    if (Q2_index > 0 && (Q1_index == 0 || Q1[0].getArrivalTime() > currentTime) && notFinishedRoundRobinYet.isEmpty()) {

                // Find the process with the shortest CPU burst time in Q2 //notFinishedRoundRobinYet.size()==0 || notFinishedRoundRobinYet.get(0).getArrivalTime()>currentTime
                PCB shortestBurstProcess = Q2[0];
                int shortestBurstIndex = 0;
                for (int i = 1; i < Q2_index; i++) {
                    if (Q2[i].getremainingCPUburst() < shortestBurstProcess.getremainingCPUburst() && Q2[i].getArrivalTime() <= currentTime) {
                        shortestBurstProcess = Q2[i];
                        shortestBurstIndex = i;
                    }
                }
                currentProcess = shortestBurstProcess;
                if (finishedProcessesList.size()==0) {
                    currentTime=currentProcess.getArrivalTime();
                    currentProcess.setStartTime(currentTime);
                }
                else currentProcess.setStartTime(currentTime);
                currentTime += currentProcess.getremainingCPUburst();
                currentProcess.setTerminationTime(currentTime);
                currentProcess.setTurnAroundTime(currentProcess.getTerminationTime() - currentProcess.getArrivalTime());
                currentProcess.setWaitingTime(currentProcess.getTurnAroundTime() - currentProcess.getremainingCPUburst());
                currentProcess.setResponseTime(currentProcess.getStartTime() - currentProcess.getArrivalTime());
                finishedProcessesList.add(currentProcess);
                System.arraycopy(Q2, shortestBurstIndex + 1, Q2, shortestBurstIndex, Q2_index - shortestBurstIndex - 1);
                Q2_index--;
            }
        }
    
        // Write finished processes information to the text file after Q1 and Q2 are done
        writeReportToFile(finishedProcessesList);
    }
  

  // ------------------------------------ A method that writes the processes info to a file------------------------------------
    public static void writeReportToFile(List<PCB>  finishedProcesses) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Report.txt"))) {
            // Initialize variables for calculating average turnaround time, waiting time, and response time
            double totalTurnaroundTime = 0;
            double totalWaitingTime = 0;
            double totalResponseTime = 0;
            String schedulingOrder = "the scheduling order of the processes [";
        for (int i = 0; i < finishedProcesses.size(); i++) {
                schedulingOrder += finishedProcesses.get(i).getProcessID()+"";
                if (i != finishedProcesses.size() - 1 ) {
                    schedulingOrder += " | ";
                }
            }
        
        schedulingOrder += "]\n";

        writer.write(schedulingOrder);
                
                // Iterate over the array to write each process's info
                for (int i = 0; i < finishedProcesses.size(); i++) {
                        System.out.println(finishedProcesses.get(i).printProcessInfo());

                        // Calculate  total turnaround time, waiting time, and response time
                        totalTurnaroundTime += finishedProcesses.get(i).getTurnAroundTime();
                        totalWaitingTime += finishedProcesses.get(i).getWaitingTime();
                        totalResponseTime +=finishedProcesses.get(i).getResponseTime();
                    
                        writer.write(finishedProcesses.get(i).printProcessInfo() + "\n");
                }

                // Print average turnaround time, waiting time, and response time
                double averageTurnaroundTime = totalTurnaroundTime / size;
                double averageWaitingTime = totalWaitingTime / size;
                double averageResponseTime = totalResponseTime / size;
                System.out.println(schedulingOrder);
                System.out.println("Average Turnaround Time: " + averageTurnaroundTime);
                System.out.println("Average Waiting Time: " + averageWaitingTime);
                System.out.println("Average Response Time: " + averageResponseTime);
                System.out.println("----------------------------------------------------------");

                // Write average times to the text file
                writer.write("Average Turnaround Time: " + averageTurnaroundTime + "\n");
                writer.write("Average Waiting Time: " + averageWaitingTime + "\n");
                writer.write("Average Response Time: " + averageResponseTime + "\n");

            
                System.out.println("Processes information successfully written to Report.txt");
            } catch (IOException e) {
                System.err.println("An error occurred while writing to Report.txt: " + e.getMessage());
            }
    }



    // Method to sort by arrival time
    public static void sortByArrivalTime(PCB[] queue, int size) {
        boolean swapped;
        for (int i = 0; i < size - 1; i++) {
            swapped = false;
            for (int j = 0; j < size - i - 1; j++) {
                if (queue[j] != null && queue[j + 1] != null) {
                    if (queue[j].getArrivalTime() > queue[j + 1].getArrivalTime()) {
                        PCB temp = queue[j];
                        queue[j] = queue[j + 1];
                        queue[j + 1] = temp;
                        swapped = true;
                }
            }
            }
            if (!swapped) {
                break; // No swaps in this iteration, meaning the array is sorted
            }
        }
    }

}

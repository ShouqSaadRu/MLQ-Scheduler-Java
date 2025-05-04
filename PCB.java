public class PCB {
    
    //------------------------------------attributes------------------------------------
    private String processID ;
    private int priority; //1, 2. 1 is for Q1
    private double arrivalTime, CPUburst, startTime, terminationTime, turnAroundTime, waitingTime, responseTime,remainingCPUburst;

    // ------------------------------------constructor------------------------------------
    public PCB(int processID, int priority, double arrivalTime, double CPUburst) {
        this.processID = "P"+ processID; //it's written in the project description that an id starts with P
        this.priority = priority;
        this.arrivalTime = arrivalTime;
        this.CPUburst = CPUburst;
        remainingCPUburst = CPUburst;

        // to be calculated
        this.startTime = -1 ;
        this.terminationTime = 0;
        this.turnAroundTime = 0;
        this.waitingTime = 0;
        this.responseTime = 0;
    }

    // ------------------------------------getters------------------------------------
    public String getProcessID() {
        return processID;
    }

    public int getPriority() {
        return priority;
    }

    public double getArrivalTime() {
        return arrivalTime;
    }

    public double getCPUburst() {
        return CPUburst;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getTerminationTime() {
        return terminationTime;
    }

    public double getTurnAroundTime() {
        return turnAroundTime;
    }

    public double getWaitingTime() {
        return waitingTime;
    }

    public double getResponseTime() {
        return responseTime;
    }
    public double getremainingCPUburst(){
        return remainingCPUburst;
    }
    // ------------------------------------setters for "To be calculated" ------------------------------------
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    public void setTerminationTime(double terminationTime) {
        this.terminationTime = terminationTime;
    }

    public void setTurnAroundTime(double turnAroundTime) {
        this.turnAroundTime = turnAroundTime;
    }

    public void setWaitingTime(double waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setResponseTime(double responseTime) {
        this.responseTime = responseTime;
    }
    public void setCpuBurst(double CPUburst){
        this.CPUburst = CPUburst;
    }
    public void setremainingCPUburst(double remainingCPUburst){
        this.remainingCPUburst = remainingCPUburst;
    }
    // ------------------------------------print------------------------------------
    public String printProcessInfo() { 
        return "Process ID: " + processID + ",\nPriority: " + priority + 
                "\nArrival Time: " + arrivalTime + "\nCPU Burst: " + CPUburst + 
                "\nStart Time: " + startTime + "\nTermination Time: " + terminationTime + 
                "\nTurnaround Time: " + turnAroundTime + "\nWaiting Time: " + waitingTime + 
                "\nResponse Time: " + responseTime + "\n----------------------------------------------------------";
    }
}

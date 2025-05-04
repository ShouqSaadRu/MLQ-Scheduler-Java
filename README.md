# Multilevel Queue CPU Scheduler (Java)

This Java-based simulator implements a **Multilevel Queue (MLQ) CPU Scheduling** algorithm for Operating Systems coursework.

## 🧠 Features
- **Q1 (High Priority):** Round Robin with 3ms quantum
- **Q2 (Low Priority):** Shortest Job First (non-preemptive)
- **Preemptive scheduling** between Q1 and Q2
- Detailed scheduling report printed to `Report.txt`

## 🛠️ How It Works
1. User enters number of processes
2. For each process, inputs: priority, arrival time, and CPU burst
3. Scheduler:
   - Assigns to correct queue
   - Simulates execution
   - Calculates start time, end time, turnaround, waiting, and response time
   - Prints a summary report


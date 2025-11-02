package metrics;

public class PerfomanceTracker {

    private int dfsVisits  = 0;
    private int dfsEdges  = 0;
    private int stackPushes  = 0;
    private int stackPops  = 0;
    private int relaxations = 0;
    private int operations = 0;
    private long executionTime = 0;

    private long startTime = 0;
    private long endTime = 0;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
        setExecutionTime();
    }

    public void setExecutionTime() {
        this.executionTime = (endTime - startTime);
    }

    public void incOperations() {
        this.operations++;
    }

    public void incDFSVisits() {
        this.dfsVisits++;
        incOperations();
    }
    public void incDFSEdges() {
        this.dfsEdges++;
        incOperations();
    }
    public void incStackPushes() {
        this.stackPushes++;
        incOperations();
    }
    public void incStackPops() {
        this.stackPops++;
        incOperations();
    }
    public void incRelaxations() {
        this.relaxations++;
        incOperations();
    }

    public long getExecutionTime() {return executionTime;}
    public int getDFSVisits() {return dfsVisits;}
    public int getDFSEdges() {return dfsEdges;}
    public int getStackPushes() {return stackPushes;}
    public int getStackPops() {return stackPops;}
    public int getRelaxations() {return relaxations;}
    public int getOperations() {return operations;}



    public void reset(){
        executionTime = 0;
        startTime = 0;
        endTime = 0;
        operations = 0;
        dfsVisits = 0;
        dfsEdges = 0;
        stackPushes = 0;
        stackPops = 0;
    }




}

package GameUtils;
import java.util.ArrayList;
import java.util.LinkedList;

public class State {

    private Graph graph;

    private int selectedNodeId;

    private State parentState;

    private int costUntilNow = 0;

    private int estimatedCostToGoal = 0;

    //keep track of the best alternative path available from any ancestor of the current node
    private int fLimit = 0;

    public State(Graph graph, int selectedNodeId, State parentState){
        this.graph= graph.copy();
        this.selectedNodeId= selectedNodeId;
        if(parentState != null){
            this.parentState= parentState;
        }else{
            this.parentState = null;
        }
    }

    public ArrayList<State> reverseSuccessor(){
        ArrayList<State> children= new ArrayList<State>();

        for (int i = 0; i < this.graph.size(); i++) {

            int nodeId = this.graph.getNode(i).getId();

            if(nodeId != selectedNodeId){

                ArrayList<State> posiiblStates = new ArrayList<State>();

                State newState = new State(this.graph.copy(), nodeId, this);

                posiiblStates.add(newState);

                // now we proccess current node
                // we going reverse so we should proccess green and red nodes first
                if(newState.getGraph().getNode(nodeId).getColor() != Color.Black){

                    int greenNeighborsCount = 0;
                    int redNeighborsCount = 0;
                    int blackNeighborcount = 0;

                    // adjacent nodes
                    LinkedList<Integer> nodeNeighbors = newState.getGraph().getNode(nodeId).getNeighborsIds();
                    // find the count of nodeColors
                    for (Integer neighborId : nodeNeighbors) {
                        switch (newState.getGraph().getNode(neighborId).getColor()) {
                            case Green -> greenNeighborsCount++;
                            case Red -> redNeighborsCount++;
                            case Black -> blackNeighborcount++;
                        }
                    }

                    Color currentNodeColor = newState.getGraph().getNode(nodeId).getColor();
                    
                    if(greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborcount){

                        if (currentNodeColor == Color.Red){ // mosalas
                            newState.getGraph().getNode(nodeId).changeColorTo(Color.Green);
                        }
                        // this state map to two state
                        // change node to black and red
                        else if (currentNodeColor == Color.Green){ // dayere zabdar

                            newState.getGraph().getNode(nodeId).changeColorTo(Color.Red);

                            State blackState = new State(this.graph.copy(), nodeId, this);

                            blackState.getGraph().getNode(nodeId).changeColorTo(Color.Black);

                            posiiblStates.add(blackState);
                        }
                    }
                    else if(redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborcount){

                        if (currentNodeColor == Color.Green){ // moraba
                            newState.getGraph().getNode(nodeId).changeColorTo(Color.Red);
                        }

                        else if (currentNodeColor == Color.Red){ // setare
                            newState.getGraph().getNode(nodeId).changeColorTo(Color.Green);

                            State blackState = new State(this.graph.copy(), nodeId, this);

                            blackState.getGraph().getNode(nodeId).changeColorTo(Color.Black);

                            posiiblStates.add(blackState);
                        }
                    }
                }
                else {
                    newState.getGraph().getNode(nodeId).changeColorTo(Color.Black);;
                }

                for (State substate : posiiblStates) {
                    // adjacent nodes
                    LinkedList<Integer> nodeNeighbors = substate.getGraph().getNode(nodeId).getNeighborsIds();
                    for (Integer neighborId : nodeNeighbors) {
                        substate.getGraph().getNode(neighborId).reverseNodeColor();
                    }
                }

                children.addAll(posiiblStates);
            }
        }
        return children;
    }

    public ArrayList<State> successor(){
        ArrayList<State> children= new ArrayList<State>();

        for (int i = 0; i < this.graph.size(); i++) {

            int nodeId = this.graph.getNode(i).getId();

            if(nodeId != selectedNodeId){

                State newState = new State(this.graph.copy(), nodeId, this);
                // adjacent nodes
                LinkedList<Integer> nodeNeighbors = newState.getGraph().getNode(nodeId).getNeighborsIds();

                // red --> green || green --> red
                // iterate over adjacent node to reverse their color
                for (int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighborId = nodeNeighbors.get(j);
                    newState.getGraph().getNode(neighborId).reverseNodeColor();
                }

                // now we proccess current node
                if(newState.getGraph().getNode(nodeId).getColor() == Color.Black){

                    int greenNeighborsCount = 0;
                    int redNeighborsCount = 0;
                    int blackNeighborcount = 0;

                    // find the count of nodeColors
                    for (int j = 0; j < nodeNeighbors.size(); j++) {
                        int neighborId = nodeNeighbors.get(j);
                        switch (newState.getGraph().getNode(neighborId).getColor()) {
                            case Green -> greenNeighborsCount++;
                            case Red -> redNeighborsCount++;
                            case Black -> blackNeighborcount++;
                        }
                    }
                    // number of adajcent Green nodes are more so color of current node become Green
                    if(greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborcount){
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Green);
                    }
                    // number of adajcent Red nodes are more so color of current node become Red
                    else if(redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborcount){
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Red);
                    }
                    // number of adajcent Black nodes are more so color of current node become Black
                    // it remains black
                }
                else {
                    // if current node is not black we just reverse its color without any changes to adjacent nodes
                    newState.getGraph().getNode(nodeId).reverseNodeColor();
                }
                children.add(newState);
            }
        }
        return children;
    }

    public String hash(){
        String result= "";
        for (int i = 0; i < graph.size(); i++) {
            switch (graph.getNode(i).getColor()) {
                case Green -> result += "g";
                case Red -> result += "r";
                case Black -> result += "b";
            }
        }
        return result;
    }

    public String outputGenerator(){
        String result= "";
        for (int i = 0; i < graph.size(); i++) {
            String color = switch (graph.getNode(i).getColor()) {
                case Red -> "R";
                case Green -> "G";
                case Black -> "B";
            };
            result += graph.getNode(i).getId()+color+" ";
            for (int j = 0; j < graph.getNode(i).getNeighborsIds().size(); j++) {
                int neighborId=graph.getNode(i).getNeighborsId(j);
                String neighborColor = switch (graph.getNode(neighborId).getColor()) {
                    case Red -> "R";
                    case Green -> "G";
                    case Black -> "B";
                };
                result += neighborId+neighborColor+" ";
            }
            result += ",";
        }
        return result;
    }

    public Graph getGraph(){
        return graph;
    }

    public State getParentState(){
        return parentState;
    }

    public  int getSelectedNodeId(){
        return selectedNodeId;
    }

    
    public void setParentState(State parentState) {
        this.parentState = parentState;
    }

    
    public Color getNodeColor() {
        return this.getGraph().getNode(selectedNodeId).getColor();
    }

    public int getCostUntilNow() {
        return costUntilNow;
    }

    public void setCostUntilNow(int costUntilNow) {
        this.costUntilNow = costUntilNow;
    }

    public int getEstimatedCostToGoal() {
        return estimatedCostToGoal;
    }

    public void setEstimatedCostToGoal(int estimatedCostToGoal) {
        this.estimatedCostToGoal = estimatedCostToGoal;
    }
    
    public int getFLimit() {
        return fLimit;
    }

    public void setFLimit(int f) {
        this.fLimit = f;
    }
}
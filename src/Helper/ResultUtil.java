package Helper;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import Algorithm.UnInformed.BFS;
import GameUtils.Color;
import GameUtils.Graph;
import GameUtils.Node;
import GameUtils.State;

public class ResultUtil {

    public static int heuristicResult(State state) {
        Stack<State> states = new Stack<State>();
        while (true){
            states.push(state);
            if(state.getParentState() == null){
                break;
            }
            else {
                state = state.getParentState();
            }
        }
        states.pop();
        return states.size();
    }

    public static boolean isGoal(State state){
        for (int i = 0; i < state.getGraph().size(); i++) {
            if(state.getGraph().getNode(i).getColor() == Color.Red
                    || state.getGraph().getNode(i).getColor() == Color.Black){
                return false;
            }
        }
        return true;
    }

    public static void result(State state, AlgorithmType type){
        if(state == null){
            try {
                FileWriter myWriter = new FileWriter(type.filePath());
                myWriter.close();
            }  catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            } finally {
                return;
            }
        }
        Stack<State>  states = new Stack<State>();
        while (true){
            states.push(state);
            if(state.getParentState() == null){
                break;
            }
            else {
                state = state.getParentState();
            }
        }
        try {
            FileWriter myWriter = new FileWriter(type.filePath());
            System.out.println("initial state : ");
            while (!states.empty()){
                State tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId()+" ,");
                myWriter.write(tempState.outputGenerator()+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void reverseResult(State state, AlgorithmType type){
        if(state == null){
            try {
                FileWriter myWriter = new FileWriter(type.filePath());
                myWriter.close();
            }  catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            } finally {
                return;
            }
        }
        Stack<State>  states = new Stack<State>();
        while (true){
            states.push(state);
            if(state.getParentState() == null){
                break;
            }
            else {
                state = state.getParentState();
            }
        }
        try {
            FileWriter myWriter = new FileWriter(type.filePath());
            System.out.println("final state : ");
            while (!states.empty()){
                State tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId()+" ,");
                myWriter.write(tempState.outputGenerator()+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    static public Graph relaxGraph(Graph graph){
        Graph newGame = graph.copy();

        for (Node node : newGame.getNodes()) {
            if (node.getColor() == Color.Black) {
                node.setColor(Color.Green);
            }
        }
        return newGame;
    }

    static public int heuristic(State state){
        // first we relax graph
        Graph relaxedGraph = relaxGraph(state.getGraph());

        State temp = new State(relaxedGraph, state.getSelectedNodeId(), null);

        BFS bfs = new BFS();
        // we find path to goal with relaxed map and bfs algorithm
        return bfs.heuristicSearch(temp);
    }
}
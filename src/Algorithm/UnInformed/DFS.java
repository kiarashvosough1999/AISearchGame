package Algorithm.UnInformed;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import Algorithm.BasicAlgorithm;
import GameUtils.SimpleSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class DFS extends BasicAlgorithm implements SimpleSearch {

    public DFS() {
        super();
    }

    public void search(State initialState){

        Stack<State> frontier = new Stack<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(initialState)){
            ResultUtil.result(initialState, AlgorithmType.DFS);
            return;
        }

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);

        while (!frontier.isEmpty()){

            State temp = frontier.pop();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(),true);
            
            numberOfExpandedNodes++;

            ArrayList<State> children = temp.successor();

            for (State state : children) {
                if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    if (ResultUtil.isGoal(state)) {
                        ResultUtil.result(state, AlgorithmType.DFS);
                        return;
                    }
                    frontier.push(state);
                    inFrontier.put(state.hash(), true);
                }
            }
        }
    }
}
package Algorithm.UnInformed;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;
import Algorithm.BasicAlgorithm;
import GameUtils.LimitedSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class DLS extends BasicAlgorithm implements LimitedSearch {

    public DLS() {
        super();
    }

    public boolean search(State initialState, int limit){

        Stack<State> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Integer> inFrontierDepth = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(initialState)){
            ResultUtil.result(initialState, AlgorithmType.DLS);
            return true;
        }

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);
        inFrontierDepth.put(initialState.hash(), 0);

        while (!frontier.isEmpty()){
            
            State temp = frontier.pop();
            inFrontier.remove(temp.hash());

            int depth = inFrontierDepth.get(temp.hash());

            inFrontierDepth.remove(temp.hash());
            explored.put(temp.hash(),true);

            if(depth >= limit){
                continue;
            }

            ArrayList<State> children = temp.successor();
            numberOfExpandedNodes++;
            for (State state : children) {
                if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    if (ResultUtil.isGoal(state)) {
                        ResultUtil.result(state, AlgorithmType.DLS);
                        return true;
                    }
                    frontier.push(state);
                    inFrontierDepth.put(state.hash(), depth + 1);
                    inFrontier.put(state.hash(), true);
                }
            }
        }
        ResultUtil.result(null, AlgorithmType.DLS);
        return false;
    }
}

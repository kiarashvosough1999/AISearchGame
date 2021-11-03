package Algorithm.UnInformed;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import Algorithm.BasicAlgorithm;
import GameUtils.Color;
import GameUtils.SimpleSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class UCS extends BasicAlgorithm implements SimpleSearch {

    public UCS() {
        super();
    }

    public void search(State initialState) {

        Queue<State> frontier = new LinkedList<State>();

        // visited nodes
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();

        // costs to reach to every node
        Hashtable<String, Integer> cost = new Hashtable<>();

        frontier.add(initialState);

        cost.put(initialState.hash(), 0);

        State temp = initialState;

        if(ResultUtil.isGoal(initialState)){
            ResultUtil.result(initialState, AlgorithmType.UCS);
            return;
        }
        
        while (!frontier.isEmpty()) {

            // check until we find min cost path to one node
            while (true) {
                temp = frontier.poll();
                if (ResultUtil.isMinCost(cost, temp)) {
                    break;
                } else {
                    frontier.add(temp);
                }
            }

            // as we are going to visit `temp` so we put it inside `inFrontier`
            inFrontier.put(temp.hash(),true);

            if (ResultUtil.isGoal(temp)) {
                ResultUtil.result(temp, AlgorithmType.UCS);
                return;
            }
            numberOfExpandedNodes++;
            ArrayList<State> children = temp.successor();

            for (State state : children) {
                // check for every node if it was not visited
                if (!(inFrontier.containsKey(state.hash()))) {
                    if (state.getNodeColor() == Color.Green) {
                        int newCost = 3 + cost.get(state.getParentState().hash()).intValue();
                        cost.put(state.hash(), newCost);
                    }
                    else if (state.getNodeColor() == Color.Black) {
                        int newCost = 2 + cost.get(state.getParentState().hash()).intValue();
                        cost.put(state.hash(), newCost);
                    }
                    else if (state.getNodeColor() == Color.Red) {
                        int newCost = 1 + cost.get(state.getParentState().hash()).intValue();
                        cost.put(state.hash(), newCost);
                    }
                    frontier.add(state);
                }
            }
            cost.remove(temp.hash());
        }
        return;
    }
}
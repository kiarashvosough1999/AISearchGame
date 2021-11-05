package Algorithm.Informed;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import Algorithm.BasicAlgorithm;
import GameUtils.SimpleSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class GBFS extends BasicAlgorithm implements SimpleSearch {

    public GBFS() {
        super();
    }

    public void search(State initialState) {

        Queue<State> frontier = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Integer> cost = new Hashtable<>();

        frontier.add(initialState);

        cost.put(initialState.hash(), 0);

        State temp = initialState;

        if (ResultUtil.isGoal(initialState)) {
            ResultUtil.result(initialState,AlgorithmType.GBFS);
            return;
        }
        while (!frontier.isEmpty()) {

            while (true) {
                temp = frontier.poll();
                if (ResultUtil.isMinCost(cost, temp)) {
                    break;
                } else {
                    frontier.add(temp);
                }
            }

            inFrontier.put(temp.hash(), true);

            if (ResultUtil.isGoal(temp)) {
                ResultUtil.result(temp, AlgorithmType.GBFS);
                return;
            }
            numberOfExpandedNodes++;

            ArrayList<State> children = temp.successor();
            
            for (State state : children) {
                if (!(inFrontier.containsKey(state.hash()))) {

                    cost.put(state.hash(), ResultUtil.heuristic(state));

                    frontier.add(state);
                }
            }
            cost.remove(temp.hash());

        }
        return;

    }
}

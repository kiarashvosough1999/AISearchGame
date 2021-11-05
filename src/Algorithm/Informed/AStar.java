package Algorithm.Informed;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import Algorithm.BasicAlgorithm;
import GameUtils.SimpleSearch;
import GameUtils.State;
import GameUtils.StateComparator;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class AStar extends BasicAlgorithm implements SimpleSearch {

    public AStar() {
        super();
    }
    
    public void search(State intialNode){

        PriorityQueue<State> frontier = new PriorityQueue<>(new StateComparator());
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(intialNode)){
            ResultUtil.result(intialNode, AlgorithmType.AStar);
            return;
        }

        intialNode.setCostUntilNow(0);

        intialNode.setEstimatedCostToGoal(ResultUtil.heuristic(intialNode));

        frontier.add(intialNode);
        inFrontier.put(intialNode.hash(),true);

        while (!frontier.isEmpty()){

            State temp = frontier.poll();

            inFrontier.remove(temp.hash());
            explored.put(temp.hash(),true);

            numberOfExpandedNodes++;

            ArrayList<State> children = temp.successor();

            for (State state : children) {
                if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    if (ResultUtil.isGoal(state)) {
                        ResultUtil.result(state, AlgorithmType.AStar);
                        return;
                    }
                    state.setCostUntilNow(state.getCostUntilNow() + 1);

                    state.setEstimatedCostToGoal(ResultUtil.heuristic(state));

                    frontier.add(state);
                    inFrontier.put(state.hash(), true);
                }
            }
        }
    }
}

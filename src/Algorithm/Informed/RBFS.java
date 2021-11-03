package Algorithm.Informed;

import java.util.ArrayList;
import java.util.PriorityQueue;
import Algorithm.BasicAlgorithm;
import GameUtils.SimpleSearch;
import GameUtils.State;
import GameUtils.StateComparator;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class RBFS extends BasicAlgorithm implements SimpleSearch {

    public RBFS() {
        super();
    }

    public void search(State intialNode){

        intialNode.setCostUntilNow(0);
        intialNode.setEstimatedCostToGoal(ResultUtil.heuristic(intialNode));
        intialNode.setFLimit(intialNode.getCostUntilNow() + intialNode.getEstimatedCostToGoal());

        rbfs(intialNode, Integer.MAX_VALUE/2);
    }

    public int rbfs(State node, int fLimit) {

        if(ResultUtil.isGoal(node)){
            ResultUtil.result(node, AlgorithmType.RBFS);
            System.exit(0);
        }
        
        PriorityQueue<State> successors = new PriorityQueue<>(new StateComparator());

        ArrayList<State> children = node.successor();

        numberOfExpandedNodes++;

        if(children.isEmpty())
            return Integer.MAX_VALUE;

        for(State child : children){
            child.setCostUntilNow(child.getParentState().getCostUntilNow() + 1);

            child.setEstimatedCostToGoal(ResultUtil.heuristic(child));

            child.setFLimit(Math.max(child.getCostUntilNow() + child.getEstimatedCostToGoal(), node.getFLimit()));

            successors.add(child);
        }

        while(true){
            State best = successors.poll();

            if(ResultUtil.isGoal(best)){
                ResultUtil.result(best, AlgorithmType.RBFS);
                ResultUtil.nodeExpansionResultgenerator(this);
                System.exit(0);
            }

            if(best.getFLimit() > fLimit)
                return best.getFLimit();

            int alternative = successors.size() > 0 ?  successors.peek().getFLimit() : fLimit;

            best.setFLimit(rbfs(best, Math.min(fLimit, alternative)));

            successors.add(best);
        }
    }
}

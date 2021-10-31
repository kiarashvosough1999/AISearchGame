package Algorithm;

import java.util.ArrayList;
import java.util.PriorityQueue;
import GameUtils.Color;
import GameUtils.Graph;
import GameUtils.Node;
import GameUtils.State;
import GameUtils.StateComparator;
import Helper.ResultUtil;

public class RBFS {

    int expandedNodes = 0;

    public void search(State intialNode){
        intialNode.setCostUntilNow(0);

        intialNode.setEstimatedCostToGoal(heuristic(intialNode));

        intialNode.setF(intialNode.getCostUntilNow() + intialNode.getEstimatedCostToGoal());

        rbfs(intialNode, Integer.MAX_VALUE/2);
    }

    public int rbfs(State node, int fLimit) {

        if(ResultUtil.isGoal(node)){
            ResultUtil.result(node, AlgorithmType.RBFS);
            System.exit(0);
        }
        PriorityQueue<State> successors = new PriorityQueue<>(new StateComparator());

        ArrayList<State> children = node.successor();

        expandedNodes++;

        if(children.size()==0)
            return Integer.MAX_VALUE/2;

        for(State child : children){
            child.setCostUntilNow(child.getParentState().getCostUntilNow() + 1);

            child.setEstimatedCostToGoal(heuristic(child));

            child.setF(Math.max(child.getCostUntilNow() + child.getEstimatedCostToGoal(), node.getF()));

            successors.add(child);
        }

        while(true){
            State best = successors.poll();

            if(ResultUtil.isGoal(best)){
                ResultUtil.result(best, AlgorithmType.RBFS);

                System.exit(0);
            }
            if(best.getF() > fLimit)
                return best.getF();

            int alternative = successors.size() > 0 ?  successors.peek().getF() : fLimit;

            best.setF(rbfs(best, Math.min(fLimit, alternative)));

            successors.add(best);
        }
    }

    public Graph relaxGraph(Graph graph){
        Graph newGame = graph.copy();

        for (Node node : newGame.getNodes()) {
            if (node.getColor() == Color.Black) {
                node.setColor(Color.Green);
            }
        }
        return newGame;
    }

    public int heuristic(State state){
        // first we relax graph
        Graph relaxedGraph = relaxGraph(state.getGraph());

        State temp = new State(relaxedGraph, state.getSelectedNodeId(), null);

        BFS bfs = new BFS();
        // we find path to goal with relaxed map and bfs algorithm
        return bfs.heuristicSearch(temp);
    }
}

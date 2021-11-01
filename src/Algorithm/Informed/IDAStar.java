package Algorithm.Informed;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;
import Algorithm.BasicAlgorithm;
import Algorithm.UnInformed.BFS;
import GameUtils.Graph;
import GameUtils.SimpleSearch;
import GameUtils.State;
import GameUtils.StateComparator;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class IDAStar extends BasicAlgorithm implements SimpleSearch {

    private int cutoff;

    private BFS bfs;

    public IDAStar() {
        super();
        this.cutoff = 0;
        this.bfs = new BFS();
    }

    public void search(State intialNode){

        PriorityQueue<State> frontier = new PriorityQueue<>(new StateComparator());
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(intialNode)){
            ResultUtil.result(intialNode, AlgorithmType.IDAStar);
            return;
        }
        
        Graph initialMap = intialNode.getGraph().copy();
        Graph relaxedMap = ResultUtil.relaxGraph(initialMap);

        intialNode.setCostUntilNow(0);

        State tempp = new State(relaxedMap, intialNode.getSelectedNodeId(), null);

        intialNode.setEstimatedCostToGoal(bfs.heuristicSearch(tempp));

        cutoff = intialNode.getCostUntilNow() + intialNode.getEstimatedCostToGoal();

        frontier.add(intialNode);
        inFrontier.put(intialNode.hash(),true);

        while (!frontier.isEmpty()){

            State temp = frontier.poll();

            frontier.clear();
            inFrontier.clear();
            explored.clear();

            cutoff = temp.getCostUntilNow() + temp.getEstimatedCostToGoal();

            Stack<State> additionalFrontier = new Stack<>();

            additionalFrontier.push(intialNode);

            while (!additionalFrontier.isEmpty()){

                State node = additionalFrontier.pop();
                inFrontier.remove(node.hash());
                explored.put(temp.hash(),true);

                if(node.getCostUntilNow() + node.getEstimatedCostToGoal() > cutoff){
                    frontier.add(node);
                    continue;
                }
                ArrayList<State> children = node.successor();

                numberOfExpandedNodes++;

                for (State state : children) {
                    if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                        if (ResultUtil.isGoal(state)) {
                            ResultUtil.result(state, AlgorithmType.IDAStar);
                            return;
                        }
                        state.setCostUntilNow(state.getParentState().getCostUntilNow() + 1);

                        tempp = new State(relaxedMap.copy(), state.getSelectedNodeId(), null);

                        state.setEstimatedCostToGoal(bfs.heuristicSearch(tempp));

                        additionalFrontier.push(state);

                        inFrontier.put(state.hash(), true);
                    }
                }
            }
        }
    }
}

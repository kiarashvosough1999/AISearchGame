package Algorithm;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.util.Stack;
import GameUtils.Color;
import GameUtils.Graph;
import GameUtils.Node;
import GameUtils.State;
import GameUtils.StateComparator;
import Helper.ResultUtil;

public class IDAStar {

    private int expandedNodes = 0;

    private int cutoff;

    public void search(State intialNode){

        PriorityQueue<State> frontier = new PriorityQueue<>(new StateComparator());
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(intialNode)){
            ResultUtil.result(intialNode, AlgorithmType.IDAStar);
            return;
        }

        BFS bfs = new BFS();
        
        Graph initialMap = intialNode.getGraph().copy();
        Graph relaxedMap = relaxGraph(initialMap);

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
                expandedNodes++;

                for(int i = 0;i<children.size();i++){
                    if(!(inFrontier.containsKey(children.get(i).hash())) && !(explored.containsKey(children.get(i).hash()))) {
                        if (ResultUtil.isGoal(children.get(i))) {
                            ResultUtil.result(children.get(i), AlgorithmType.IDAStar);
                            return;
                        }
                        children.get(i).setCostUntilNow(children.get(i).getParentState().getCostUntilNow() + 1);

                        tempp = new State(relaxedMap.copy(), children.get(i).getSelectedNodeId(), null);

                        children.get(i).setEstimatedCostToGoal(bfs.heuristicSearch(tempp));

                        additionalFrontier.push(children.get(i));

                        inFrontier.put(children.get(i).hash(), true);
                    }
                }
            }
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
}

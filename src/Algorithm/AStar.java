package Algorithm;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.PriorityQueue;
import GameUtils.Color;
import GameUtils.Graph;
import GameUtils.Node;
import GameUtils.State;
import GameUtils.StateComparator;
import Helper.ResultUtil;

public class AStar {

    int expandedNodes = 0;

    public void search(State intialNode){
        PriorityQueue<State> frontier = new PriorityQueue<>(new StateComparator());
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(intialNode)){
            ResultUtil.result(intialNode, AlgorithmType.AStar);
            return;
        }

        // set initial node cost and estimated steps to goal
        intialNode.setCostUntilNow(0);
        intialNode.setEstimatedCostToGoal(heuristic(intialNode));

        frontier.add(intialNode);
        inFrontier.put(intialNode.hash(),true);

        while (!frontier.isEmpty()){

            State temp = frontier.poll();

            // remove node from fringe and store it in explored
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(),true);

            expandedNodes++;

            ArrayList<State> children = temp.successor();

            for(int i = 0;i<children.size();i++){
                if(!(inFrontier.containsKey(children.get(i).hash())) && !(explored.containsKey(children.get(i).hash()))) {

                    if(ResultUtil.isGoal(children.get(i))){
                        ResultUtil.result(children.get(i), AlgorithmType.AStar);
                        return;
                    }
                    
                    children.get(i).setCostUntilNow(children.get(i).getParentState().getCostUntilNow() + 1);
                    // estimate cost to goal from generated state
                    children.get(i).setEstimatedCostToGoal(heuristic(children.get(i)));;
                    
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
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

    public int heuristic(State state){
        // first we relax graph
        Graph relaxedGraph = relaxGraph(state.getGraph());

        State temp = new State(relaxedGraph, state.getSelectedNodeId(), null);

        BFS bfs = new BFS();
        // we find path to goal with relaxed map and bfs algorithm
        return bfs.heuristicSearch(temp);
    }
}

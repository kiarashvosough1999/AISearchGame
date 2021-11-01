package Algorithm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import GameUtils.State;
import Helper.ResultUtil;

public class BDS {

    private int expandedNodes = 0;

    public void search(State initialState, State finalState){

        Queue<State> frontier = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        LinkedList<State> exploredList = new LinkedList<>();

        Queue<State> reverseFrontier = new LinkedList<State>();
        Hashtable<String, Boolean> inReverseFrontier = new Hashtable<>();
        Hashtable<String, Boolean> reverseExplored = new Hashtable<>();
        LinkedList<State> reverseExploredList = new LinkedList<>();

        if(initialState.hash().equals(finalState.hash())){
            ResultUtil.result(initialState, AlgorithmType.BDS);
            return;
        }

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);
        reverseFrontier.add(finalState);
        inReverseFrontier.put(finalState.hash(), true);

        while (!frontier.isEmpty() && !reverseFrontier.isEmpty()){
            State temp = frontier.poll();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(),true);
            exploredList.add(temp);
            ArrayList<State> children = temp.successor();
            expandedNodes++;
            for(int i = 0;i<children.size();i++){
                if(!(inFrontier.containsKey(children.get(i).hash())) && !(explored.containsKey(children.get(i).hash()))) {
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }

            Set<String> fridgeSharedNodes = new HashSet<String>();
            fridgeSharedNodes.addAll(inFrontier.keySet());
            fridgeSharedNodes.retainAll(inReverseFrontier.keySet());
            if(!fridgeSharedNodes.isEmpty()){
                System.out.println(fridgeSharedNodes);
                String intersectionNode = (String) fridgeSharedNodes.toArray()[0];
                State nodeInFront = searchInList(intersectionNode, (LinkedList<State>) frontier);
                State nodeInReverse = searchInList(intersectionNode, (LinkedList<State>) reverseFrontier);
                ResultUtil.result(nodeInFront, AlgorithmType.BDS);
                ResultUtil.reverseResult(nodeInReverse, AlgorithmType.BDSReverse);
                return;
            }

            State reverseTemp = reverseFrontier.poll();
            inReverseFrontier.remove(reverseTemp.hash());
            reverseExplored.put(reverseTemp.hash(), true);
            reverseExploredList.add(reverseTemp);
            ArrayList<State> reverseChildren = reverseTemp.reverseSuccessor();
            expandedNodes++;
            for(int i = 0;i<reverseChildren.size();i++){
                if(!(inReverseFrontier.containsKey(reverseChildren.get(i).hash())) && !(reverseExplored.containsKey(reverseChildren.get(i).hash()))) {
                    reverseFrontier.add(reverseChildren.get(i));
                    inReverseFrontier.put(reverseChildren.get(i).hash(), true);
                }
            }

            fridgeSharedNodes = new HashSet<String>();
            fridgeSharedNodes.addAll(inFrontier.keySet());
            fridgeSharedNodes.retainAll(inReverseFrontier.keySet());
            if(!fridgeSharedNodes.isEmpty()){
                System.out.println(fridgeSharedNodes);
                String intersectionNode = (String) fridgeSharedNodes.toArray()[0];
                State nodeInFront = searchInList(intersectionNode, (LinkedList<State>) frontier);
                State nodeInReverse = searchInList(intersectionNode, (LinkedList<State>) reverseFrontier);
                ResultUtil.result(nodeInFront, AlgorithmType.BDS);
                ResultUtil.reverseResult(nodeInReverse, AlgorithmType.BDSReverse);
                return;
            }
        }

        Set<String> exploredSharedNodes = explored.keySet();
        exploredSharedNodes.retainAll(reverseExplored.keySet());
        if(!exploredSharedNodes.isEmpty()){
            String intersectionNode = (String) exploredSharedNodes.toArray()[exploredSharedNodes.size()-1];
            State nodeInFront = searchInList(intersectionNode, exploredList);
            State nodeInReverse = searchInList(intersectionNode, reverseExploredList);
            ResultUtil.result(nodeInFront, AlgorithmType.BDS);
            ResultUtil.reverseResult(nodeInReverse, AlgorithmType.BDSReverse);
            return;
        }
    }

    
    private State searchInList(String node, LinkedList<State> list){
        for(State item : list)
            if(item.hash().equals(node))
                return item;
        return null;
    }
}

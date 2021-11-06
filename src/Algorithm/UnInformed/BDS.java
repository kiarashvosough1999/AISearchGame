//  Copyright 2020 KiarashVosough and other contributors
//
//  Permission is hereby granted, free of charge, to any person obtaining
//  a copy of this software and associated documentation files (the
//  Software"), to deal in the Software without restriction, including
//  without limitation the rights to use, copy, modify, merge, publish,
//  distribute, sublicense, and/or sell copies of the Software, and to
//  permit persons to whom the Software is furnished to do so, subject to
//  the following conditions:
//
//  The above copyright notice and this permission notice shall be
//  included in all copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
//  EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
//  NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
//  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
//  OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
//  WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package Algorithm.UnInformed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import Algorithm.BasicAlgorithm;
import GameUtils.BidirectionalSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class BDS extends BasicAlgorithm implements BidirectionalSearch {

    public BDS() {
        super();
    }

    public void search(State initialState, State finalState){

        // forward
        Queue<State> frontier = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();
        LinkedList<State> exploredList = new LinkedList<>();

        // backward
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

            // generate nodes forward
            ArrayList<State> children = temp.successor();

            numberOfExpandedNodes++;

            // check if we have visited expanded nodes forward
            // if not visited add them to visted list
            for (State state : children) {
                if (!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    frontier.add(state);
                    inFrontier.put(state.hash(), true);
                }
            }

            // Finding intersection if reached
            Set<String> fringeSharedNodes = new HashSet<String>();
            // add all node from forward visted list
            fringeSharedNodes.addAll(inFrontier.keySet());
            // remove all inserted ellemnt execpt those which inReverseFrontier contain them
            fringeSharedNodes.retainAll(inReverseFrontier.keySet());

            if(!fringeSharedNodes.isEmpty()){
                String intersectionNode = (String) fringeSharedNodes.toArray()[0];
                State nodeFromFront = findIntersectedNode(intersectionNode, (LinkedList<State>) frontier);
                State nodeFromReverse = findIntersectedNode(intersectionNode, (LinkedList<State>) reverseFrontier);
                ResultUtil.result(nodeFromFront, AlgorithmType.BDS);
                ResultUtil.reverseResult(nodeFromReverse, AlgorithmType.BDSReverse);
                return;
            }

            State reverseTemp = reverseFrontier.poll();
            inReverseFrontier.remove(reverseTemp.hash());
            reverseExplored.put(reverseTemp.hash(), true);
            reverseExploredList.add(reverseTemp);

            // generate nodes backward
            ArrayList<State> reverseChildren = reverseTemp.reverseSuccessor();

            numberOfExpandedNodes++;

            // check if we have visited expanded nodes backward
            // if not visited add them to visted list
            for (State state : reverseChildren) {
                if (!(inReverseFrontier.containsKey(state.hash())) && !(reverseExplored.containsKey(state.hash()))) {
                    reverseFrontier.add(state);
                    inReverseFrontier.put(state.hash(), true);
                }
            }

            // we should check again for intesection
            // because it might be genereated from backward
            fringeSharedNodes = new HashSet<String>();

            fringeSharedNodes.addAll(inFrontier.keySet());
            fringeSharedNodes.retainAll(inReverseFrontier.keySet());

            if(!fringeSharedNodes.isEmpty()){
                String intersectionNode = (String) fringeSharedNodes.toArray()[0];
                State nodeFromFront = findIntersectedNode(intersectionNode, (LinkedList<State>) frontier);
                State nodeFromReverse = findIntersectedNode(intersectionNode, (LinkedList<State>) reverseFrontier);
                ResultUtil.result(nodeFromFront, AlgorithmType.BDS);
                ResultUtil.reverseResult(nodeFromReverse, AlgorithmType.BDSReverse);
                return;
            }
        }

        Set<String> exploredSharedNodes = explored.keySet();
        exploredSharedNodes.retainAll(reverseExplored.keySet());
        if(!exploredSharedNodes.isEmpty()){
            String intersectionNode = (String) exploredSharedNodes.toArray()[exploredSharedNodes.size()-1];
            State nodeFromFront = findIntersectedNode(intersectionNode, exploredList);
            State nodeFromReverse = findIntersectedNode(intersectionNode, reverseExploredList);
            ResultUtil.result(nodeFromFront, AlgorithmType.BDS);
            ResultUtil.reverseResult(nodeFromReverse, AlgorithmType.BDSReverse);
            return;
        }
    }
    
    private State findIntersectedNode(String node, LinkedList<State> list){
        for(State item : list)
            if(item.hash().equals(node))
                return item;
        return null;
    }
}

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

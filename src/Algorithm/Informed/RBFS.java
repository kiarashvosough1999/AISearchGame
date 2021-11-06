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

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
import java.util.LinkedList;
import java.util.Queue;
import Algorithm.BasicAlgorithm;
import GameUtils.SimpleSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class GBFS extends BasicAlgorithm implements SimpleSearch {

    public GBFS() {
        super();
    }

    public void search(State initialState) {

        Queue<State> frontier = new LinkedList<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Integer> cost = new Hashtable<>();

        frontier.add(initialState);

        cost.put(initialState.hash(), 0);

        State temp = initialState;

        if (ResultUtil.isGoal(initialState)) {
            ResultUtil.result(initialState,AlgorithmType.GBFS);
            return;
        }
        while (!frontier.isEmpty()) {

            while (true) {
                temp = frontier.poll();
                if (ResultUtil.isMinCost(cost, temp)) {
                    break;
                } else {
                    frontier.add(temp);
                }
            }

            inFrontier.put(temp.hash(), true);

            if (ResultUtil.isGoal(temp)) {
                ResultUtil.result(temp, AlgorithmType.GBFS);
                return;
            }
            numberOfExpandedNodes++;

            ArrayList<State> children = temp.successor();
            
            for (State state : children) {
                if (!(inFrontier.containsKey(state.hash()))) {

                    cost.put(state.hash(), ResultUtil.heuristic(state));

                    frontier.add(state);
                }
            }
            cost.remove(temp.hash());

        }
        return;

    }
}

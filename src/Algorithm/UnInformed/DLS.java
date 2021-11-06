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
import java.util.Hashtable;
import java.util.Stack;
import Algorithm.BasicAlgorithm;
import GameUtils.LimitedSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class DLS extends BasicAlgorithm implements LimitedSearch {

    public DLS() {
        super();
    }

    public boolean search(State initialState, int limit){

        Stack<State> frontier = new Stack<>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Integer> inFrontierDepth = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(initialState)){
            ResultUtil.result(initialState, AlgorithmType.DLS);
            return true;
        }

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);
        inFrontierDepth.put(initialState.hash(), 0);

        while (!frontier.isEmpty()){
            
            State temp = frontier.pop();
            inFrontier.remove(temp.hash());

            int depth = inFrontierDepth.get(temp.hash());

            inFrontierDepth.remove(temp.hash());
            explored.put(temp.hash(),true);

            if(depth >= limit){
                continue;
            }

            ArrayList<State> children = temp.successor();
            numberOfExpandedNodes++;
            for (State state : children) {
                if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    if (ResultUtil.isGoal(state)) {
                        ResultUtil.result(state, AlgorithmType.DLS);
                        return true;
                    }
                    frontier.push(state);
                    inFrontierDepth.put(state.hash(), depth + 1);
                    inFrontier.put(state.hash(), true);
                }
            }
        }
        ResultUtil.result(null, AlgorithmType.DLS);
        return false;
    }
}

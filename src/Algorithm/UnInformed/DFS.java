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
import GameUtils.SimpleSearch;
import GameUtils.State;
import Helper.AlgorithmType;
import Helper.ResultUtil;

public class DFS extends BasicAlgorithm implements SimpleSearch {

    public DFS() {
        super();
    }

    public void search(State initialState){

        Stack<State> frontier = new Stack<State>();
        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        if(ResultUtil.isGoal(initialState)){
            ResultUtil.result(initialState, AlgorithmType.DFS);
            return;
        }

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);

        while (!frontier.isEmpty()){

            State temp = frontier.pop();
            inFrontier.remove(temp.hash());
            explored.put(temp.hash(),true);
            
            numberOfExpandedNodes++;

            ArrayList<State> children = temp.successor();

            for (State state : children) {
                if(!(inFrontier.containsKey(state.hash())) && !(explored.containsKey(state.hash()))) {
                    if (ResultUtil.isGoal(state)) {
                        ResultUtil.result(state, AlgorithmType.DFS);
                        return;
                    }
                    frontier.push(state);
                    inFrontier.put(state.hash(), true);
                }
            }
        }
    }
}
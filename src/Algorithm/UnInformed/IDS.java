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

import Algorithm.BasicAlgorithm;
import GameUtils.LimitedSearch;
import GameUtils.SimpleSearch;
import GameUtils.State;

public class IDS extends BasicAlgorithm implements SimpleSearch {

    private LimitedSearch dls;
    private int limit;

    public IDS(int limit) {
        super();
        this.limit = limit;
        dls = new DLS();
    }

    public void search(State initialNode){
        for(int i = 0 ; i <= limit ; i++){
            if(dls.search(initialNode, i)){
                numberOfExpandedNodes += dls.getNumberOfExpandedNodes();
                return;
            }
            numberOfExpandedNodes += dls.getNumberOfExpandedNodes();
        }
    }
}

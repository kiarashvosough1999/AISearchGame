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

package Helper;

public enum AlgorithmType {
    BFS, DFS, DLS, UCS, BDS, AStar, IDAStar, RBFS, BDSReverse, GBFS;

    public String filePath() {
        switch (this) {
            case BFS: return "BfsResult.txt";
            case DFS: return "DfsResult.txt";
            case DLS: return "DlsResult.txt";
            case UCS: return "UcsResult.txt";
            case BDS: return "BdsResult.txt";
            case AStar: return "AstarResult.txt";
            case IDAStar: return "IdastarResult.txt";
            case RBFS: return "RbfsResult.txt";
            case BDSReverse: return "BdsreverseResult.txt";
            case GBFS: return "GbfsResult.txt";
            default: return "";
        }
    }
}

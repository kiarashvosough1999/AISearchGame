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

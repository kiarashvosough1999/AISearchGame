package Algorithm;

public enum AlgorithmType {
    BFS, DFS, DLS, UCS, AStar;

    public String filePath() {
        switch (this) {
            case BFS: return "BfsResult.txt";
            case DFS: return "DfsResult.txt";
            case DLS: return "DlsResult.txt";
            case UCS: return "UcsResult.txt";
            case AStar: return "AstarResult.txt";
            default: return "";
        }
    }
}

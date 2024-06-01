public class MazeGenerator {
    int[][] maze;
    final static int background = 0;
    final static int wall = 1;
    final static int path = 2;
    final static int empty = 3;
    final static int visited = 4;

    public MazeGenerator(){

    }

    public int[][] getMaze(int rows, int columns) {
        maze = null;
        generateMaze(rows, columns);
        return maze;
    }

    protected void generateMaze(int rows, int columns){
        if(maze == null)
            maze = new int[rows][columns];


        int wallNum = 0;
        int emptyNum = 0;
        int[] wallRow = new int[(rows * columns)/ 2];
        int[] wallCol = new int[(rows * columns)/2];
        int i;
        int j;
        for( i = 0; i < rows; i++)
            for( j = 0; j < columns; j++)
                maze[i][j] = wall;


        for (i = 1; i < rows - 1; i += 2)
            for (j = 1; j < columns - 1; j += 2){
                emptyNum++;
                maze[i][j] = -emptyNum;
                if(i < rows - 2){
                    wallRow[wallNum] = i + 1;
                    wallCol[wallNum] = j;
                    wallNum++;
                }
                if(j < columns - 2){
                    wallRow[wallNum] = i;
                    wallCol[wallNum] = j + 1;
                    wallNum++;
                }
            }
            int rand;
            for(i = wallNum - 1; i > 0; i--){
                 rand = (int) (Math.random() * i);
                tearDown(wallRow[rand], wallCol[rand]);
                wallRow[rand] = wallRow[i];
                wallCol[rand] = wallCol[i];
            }
            for (i = 1; i < rows - 1; i++)
                for (j = 1; j < columns - 1; j++)
                    if(maze[i][j] < 0){
                        maze[i][j] = empty;
                    }



    }

    protected void tearDown(int row, int col) {
        if (row % 2 == 1 && maze[row][col - 1] != maze[row][col + 1]) {
            fill(row, col - 1, maze[row][col - 1], maze[row][col + 1]);
            maze[row][col] = maze[row][col + 1];
        } else if (row % 2 == 0 && maze[row - 1][col] != maze[row + 1][col]) {
            fill(row - 1, col, maze[row - 1][col], maze[row + 1][col]);
            maze[row][col] = maze[row + 1][col];
        }
    }

    private void fill(int row, int col, int replace, int replaceWith) {

        if (row < 0 || row >= maze.length || col < 0 || col >= maze[0].length) {
            return;
        }

        if (maze[row][col] == replace) {
            maze[row][col] = replaceWith;
            fill(row + 1, col, replace, replaceWith);
            fill(row - 1, col, replace, replaceWith);
            fill(row, col + 1, replace, replaceWith);
            fill(row, col - 1, replace, replaceWith);
        }
    }


}


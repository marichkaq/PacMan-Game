import javax.swing.table.AbstractTableModel;


public class BoardTableModel extends AbstractTableModel {

    protected Game game;

    public BoardTableModel(Game game) {

        this.game = game;
    }


    @Override
    public int getRowCount() {
        return game.getY();
    }

    @Override
    public int getColumnCount() {
        return game.getX();
    }

    @Override
    public Object getValueAt(int numRows, int numColumns) {
        return game.gameBoard[numRows][numColumns];
    }

   /* public void setValueAt(Object value, int rowIndex, int colIndex){
        if(isCellEditable(rowIndex, colIndex)) {
            board[rowIndex][colIndex] = value;
            fireTableCellUpdated(rowIndex, colIndex);
        }
    } */

}




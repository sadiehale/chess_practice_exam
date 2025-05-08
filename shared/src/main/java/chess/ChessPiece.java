package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {
    @Override
    public String toString() {
        return "ChessPiece{" +
                "teamColor=" + teamColor +
                ", pieceType=" + pieceType +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return teamColor == that.teamColor && pieceType == that.pieceType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamColor, pieceType);
    }

    private final ChessGame.TeamColor teamColor;
    private final ChessPiece.PieceType pieceType;
    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.teamColor = pieceColor;
        this.pieceType = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return teamColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return pieceType;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> movePossibilities = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();

        if (getPieceType() == PieceType.PAWN){
            //pawn - moves forward one, or possibly two if first move, promotes to any other type besides king, diagonal only when capturing
            int [][] pawnMoves;
            int [][] pawnMoves_capturing;
            if (board.getPiece(myPosition).teamColor == ChessGame.TeamColor.WHITE){
                pawnMoves_capturing = new int[][] {{+1, +1}, {+1, -1}};
                if (myPosition.getRow() == 2){
                    pawnMoves = new int[][] {{+1, 0}, {+2, 0}};
                }else{
                    pawnMoves = new int[][] {{+1, 0}};
                }
            }else{
                pawnMoves_capturing = new int[][] {{-1, -1}, {-1, +1}};
                if (myPosition.getRow() == 6){
                    pawnMoves = new int[][] {{-1, 0}, {-2, 0}};
                }else{
                    pawnMoves = new int[][] {{-1,0}};
                }
            }

            for (int[] possibility : pawnMoves){
                int newRow = row;
                newRow += possibility[0];
                int newCol = col;
                newCol += possibility[1];
                if inBounds(newRow, newCol)){
                    ChessPosition newSpot = new ChessPosition(newRow, newCol);
                    if (board.getPiece(newSpot) == null){

                    }
                }
            }


        }else if(getPieceType() == PieceType.ROOK){
            //rook - moves in straight lines
            int [][] rookMoves = {
                    {0, +1}, {0, -1}, {-1,0}, {+1, 0}
            };
            for (int[] possibility : rookMoves){
                int newRow = row;
                int newCol = col;

                while(true){
                    newRow += possibility[0];
                    newCol += possibility[1];

                    if (!inBounds(newRow, newCol)) {
                        break;
                    }else{
                        ChessPosition newSpot = new ChessPosition(newRow, newCol);
                        if (canMove(newSpot, board)){
                            movePossibilities.add(new ChessMove(myPosition, newSpot, null));
                            if (board.getPiece(newSpot) != null){
                                break;
                            }
                        }else{
                            break;
                        }
                    }
                }
            }
        }else if(getPieceType() == PieceType.KNIGHT){
            //knight - moves in L shape
            int [][] knightMoves = {
                    {+1,+2}, {+1,-2}, {-1,+2}, {-1, -2}, {+2, +1}, {+2, -1}, {-2,+1}, {-2, -1}
            };
            for (int[] possibility : knightMoves){
                int newRow = row + possibility[0];
                int newCol = col + possibility[1];
                if (inBounds(newRow, newCol)){
                    ChessPosition newSpot = new ChessPosition(newRow, newCol);
                    if( canMove(newSpot, board)){
                        movePossibilities.add(new ChessMove(myPosition, newSpot, null));
                    }
                }
            }
        }else if(getPieceType() == PieceType.BISHOP){
            //bishop - moves in diagonal lines
            int [][] bishopMoves = {
                    {+1, +1}, {+1, -1}, {-1,-1}, {-1, +1}
            };
            for (int[] possibility : bishopMoves){
                int newRow = row;
                int newCol = col;

                while(true){
                    newRow += possibility[0];
                    newCol += possibility[1];

                    if (!inBounds(newRow, newCol)) {
                        break;
                    }else{
                        ChessPosition newSpot = new ChessPosition(newRow, newCol);
                        if (canMove(newSpot, board)){
                            movePossibilities.add(new ChessMove(myPosition, newSpot, null));
                            if (board.getPiece(newSpot) != null){
                                break;
                            }
                        }else{
                            break;
                        }
                    }
                }
            }
        }else if(getPieceType() == PieceType.QUEEN){
            //queen - moves in straight and diagonal lines
            int [][] bishopMoves = {
                    {+1, +1}, {+1, -1}, {-1,-1}, {-1, +1},{0, -1}, {0, +1}, {+1, 0}, {-1, 0}
            };
            for (int[] possibility : bishopMoves){
                int newRow = row;
                int newCol = col;

                while(true){
                    newRow += possibility[0];
                    newCol += possibility[1];

                    if (!inBounds(newRow, newCol)) {
                        break;
                    }else{
                        ChessPosition newSpot = new ChessPosition(newRow, newCol);
                        if (canMove(newSpot, board)){
                            movePossibilities.add(new ChessMove(myPosition, newSpot, null));
                            if (board.getPiece(newSpot) != null){
                                break;
                            }
                        }else{
                            break;
                        }
                    }
                }
            }
        }else if(getPieceType() == PieceType.KING){
            int [][] kingMoves = {
                    {0,+1}, {0, -1}, {+1,0}, {-1,0}, {+1,+1}, {+1,-1}, {-1,-1}, {-1,+1}
            };
            for (int[] possibility : kingMoves){
                int newRow = row + possibility[0];
                int newCol = col + possibility[1];
                if (inBounds(newRow, newCol)){
                    ChessPosition newSpot = new ChessPosition(newRow, newCol);
                    if( canMove(newSpot, board)){
                        movePossibilities.add(new ChessMove(myPosition, newSpot, null));
                    }
                }
            }
        }
        return movePossibilities;
    }
    public boolean inBounds(int row, int col){
            return row >= 1 && row <= 8 && col >= 1 && col <= 8;
        }
    public boolean canMove(ChessPosition move, ChessBoard board){
        if (!inBounds(move.getRow(), move.getColumn())){
            return false;
        }
        ChessPiece spotCheck = board.getPiece(move);
        return spotCheck == null || spotCheck.teamColor != this.teamColor;
    }
}

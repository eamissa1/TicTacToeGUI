import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame
{
    private static final int ROW = 3;
    private static final int COL = 3;
    private JButton[][] buttons = new JButton[ROW][COL];
    private String currentPlayer = "X";
    private static String[][] board = new String[ROW][COL];

    public TicTacToeFrame()
    {
        super("Tic Tac Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(ROW, COL));
        ActionListener buttonListener = new ButtonListener();

        // Initialize the GUI board and the logical board
        for (int i = 0; i < ROW; i++)
        {
            for (int j = 0; j < COL; j++)
            {
                board[i][j] = " ";
                JButton button = new JButton();
                buttons[i][j] = button;
                button.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 50));
                button.setActionCommand(i + "," + j);  // Set the button's action command to its row,col
                button.addActionListener(buttonListener);
                add(button);
            }
        }
        setVisible(true);
    }

    private class ButtonListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();
            int row = Integer.parseInt(command.split(",")[0]);
            int col = Integer.parseInt(command.split(",")[1]);
            JButton button = buttons[row][col];

            if (!button.getText().equals(""))
            {
                JOptionPane.showMessageDialog(null, "Invalid move! The cell is already occupied.");
                return;
            }

            if (button.getText().equals("") && !isGameOver())
            {
                board[row][col] = currentPlayer;
                button.setText(currentPlayer);
                if (isWin(currentPlayer))
                {
                    JOptionPane.showMessageDialog(null, "Player " + currentPlayer + " wins!");
                    int playAgain = JOptionPane.showConfirmDialog(null, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION)
                    {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                } else if (isTie()) {
                    JOptionPane.showMessageDialog(null, "The game is a tie!");
                    int playAgain = JOptionPane.showConfirmDialog(null, "Play again?", "Game Over", JOptionPane.YES_NO_OPTION);
                    if (playAgain == JOptionPane.YES_OPTION)
                    {
                        resetGame();
                    } else {
                        System.exit(0);
                    }
                } else {
                    currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                }
            }
        }
    }

    private boolean isGameOver()
    {
        return isWin("X") || isWin("O") || isTie();
    }

    private void resetGame()
    {
        for (int i = 0; i < ROW; i++)
        {
            for (int j = 0; j < COL; j++)
            {
                board[i][j] = " ";
                buttons[i][j].setText("");
            }
        }
    }
    private static boolean isWin(String player)
    {
        if (isColWin(player) || isRowWin(player) || isDiagnalWin(player))
        {
            return true;
        }

        return false;
    }

    private static boolean isColWin(String player)
    {
        // checks for a col win for specified player
        for (int col = 0; col < COL; col++)
        {
            if (board[0][col].equals(player) &&
                    board[1][col].equals(player) &&
                    board[2][col].equals(player)) {
                return true;
            }
        }
        return false; // no col win
    }

    private static boolean isRowWin(String player)
    {
        // checks for a row win for the specified player
        for (int row = 0; row < ROW; row++)
        {
            if (board[row][0].equals(player) &&
                    board[row][1].equals(player) &&
                    board[row][2].equals(player))
            {
                return true;
            }
        }
        return false; // no row win
    }

    private static boolean isDiagnalWin(String player)
    {
        // checks for a diagonal win for the specified player

        if (board[0][0].equals(player) &&
                board[1][1].equals(player) &&
                board[2][2].equals(player)) {
            return true;
        }
        if (board[0][2].equals(player) &&
                board[1][1].equals(player) &&
                board[2][0].equals(player))
        {
            return true;
        }
        return false;
    }

    // checks for a tie before board is filled.
    // check for the win first to be efficient
    private static boolean isTie()
    {
        boolean xFlag = false;
        boolean oFlag = false;
        // Check all 8 win vectors for an X and O so
        // no win is possible
        // Check for row ties
        for (int row = 0; row < ROW; row++)
        {
            if (board[row][0].equals("X") ||
                    board[row][1].equals("X") ||
                    board[row][2].equals("X"))
            {
                xFlag = true; // there is an X in this row
            }
            if (board[row][0].equals("O") ||
                    board[row][1].equals("O") ||
                    board[row][2].equals("O"))
            {
                oFlag = true; // there is an O in this row
            }

            if (!(xFlag && oFlag))
            {
                return false; // No tie can still have a row win
            }

            xFlag = oFlag = false;

        }
        // Now scan the columns
        for (int col = 0; col < COL; col++)
        {
            if (board[0][col].equals("X") ||
                    board[1][col].equals("X") ||
                    board[2][col].equals("X"))
            {
                xFlag = true; // there is an X in this col
            }
            if (board[0][col].equals("O") ||
                    board[1][col].equals("O") ||
                    board[2][col].equals("O"))
            {
                oFlag = true; // there is an O in this col
            }

            if (!(xFlag && oFlag))
            {
                return false; // No tie can still have a col win
            }
        }
        // Now check for the diagonals
        xFlag = oFlag = false;

        if (board[0][0].equals("X") ||
                board[1][1].equals("X") ||
                board[2][2].equals("X"))
        {
            xFlag = true;
        }
        if (board[0][0].equals("O") ||
                board[1][1].equals("O") ||
                board[2][2].equals("O"))
        {
            oFlag = true;
        }
        if (!(xFlag && oFlag))
        {
            return false; // No tie can still have a diag win
        }
        xFlag = oFlag = false;

        if (board[0][2].equals("X") ||
                board[1][1].equals("X") ||
                board[2][0].equals("X"))
        {
            xFlag = true;
        }
        if (board[0][2].equals("O") ||
                board[1][1].equals("O") ||
                board[2][0].equals("O"))
        {
            oFlag = true;
        }
        if (!(xFlag && oFlag))
        {
            return false; // No tie can still have a diag win
        }
        // Checked every vector so I know I have a tie
        return true;
    }
}
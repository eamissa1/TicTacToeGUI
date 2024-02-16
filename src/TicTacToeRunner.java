public class TicTacToeRunner
{
    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            public void run()
            {
                new TicTacToeFrame();
            }
        });
    }
}

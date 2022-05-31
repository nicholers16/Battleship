package Board;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Overseer {
    private Layout _lay;
    private Player _ply;
    private Computer _comp;
    private boolean plyWin = false;
    private boolean compWin = false;
    public JButton[][] playbutton = new JButton[10][10];
    private String _askName;
    private int compHitCnt = 0;
    private int plyHitCnt = 0;

    //initializes display in game-specific order
    public Overseer() {
        introduction1();
        _askName = JOptionPane.showInputDialog("What is your name? Type 'Rico' for cheat.");
        _ply = new Player();
        _comp = new Computer();
        _lay = new Layout(_askName);
        makeBattleshipBoard();
        update();
    }

    //checks whether the computer has hit all ships, or vice verse. displays lose/victory accordingly.
    public void update() {
        while (!plyWin && !compWin) {
            playChooseTarget();
            _lay.setArr(_comp.compArray);
            _lay.showBoard("computer");
            if (plyHitCnt >= 9)
                victory();
            JPanel p = new JPanel();
            JLabel b = new JLabel("You will now view the opponent's attack on your board.");
            p.add(b);
            JOptionPane.showMessageDialog(null, p, "AHHH!", JOptionPane.INFORMATION_MESSAGE);
            compMakeMove();
            _lay.setArr(_ply.playArray);
            _lay.showBoard("player");

            if (compHitCnt >= 9)
                lose();
        }
        if (plyWin)
            victory();
        else
            lose();
    }

    //randomly selects a spot on the battleship board and throws shot. the visual array updates accordingly
    public void compMakeMove() {
        Random rnd = new Random();
        int row = rnd.nextInt(_comp.compArray.length);
        int col = rnd.nextInt(_comp.compArray.length);
        if (_ply.playArray[row][col] == 1) {
            _ply.playArray[row][col] = 2;
            compHitCnt++;
        }
        if (_ply.playArray[row][col] == 0) {
            _ply.playArray[row][col] = 3;
        }
        if (compHitCnt >= 9) {
            compWin = true;
            _ply.isDead = true;
        }
    }

    //displays board where player ca select a single target
    public void playChooseTarget() {
        JPanel panel = new JPanel(new GridLayout(10, 10));
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout());
        panel.setSize(650, 650);
        for (int r = 0; r < playbutton.length; r++) {
            for (int c = 0; c < playbutton[r].length; c++) {
                playbutton[r][c] = new JButton();
                playbutton[r][c].setOpaque(true);
                //displays hit boats
                if (_comp.compArray[r][c] == 2) {
                    playbutton[r][c].setBorderPainted(false);
                    playbutton[r][c].setBackground(Color.GREEN);
                }
                //displays no hits
                if (_comp.compArray[r][c] == 3) {
                    playbutton[r][c].setBorderPainted(false);
                    playbutton[r][c].setBackground(Color.RED);
                }
                playbutton[r][c].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        for (int i = 0; i < playbutton.length; i++) {
                            for (int j = 0; j < playbutton[0].length; j++) {
                                if (e.getSource() == playbutton[i][j]) {
                                    if (_comp.compArray[i][j] == 1) {
                                        _comp.compArray[i][j] = 2;
                                        plyHitCnt++;
                                    }
                                    if (_comp.compArray[i][j] == 0) {
                                        _comp.compArray[i][j] = 3;
                                    }
                                    playbutton[i][j].setBorderPainted(false);
                                    if (_comp.compArray[i][j] == 2)
                                        playbutton[i][j].setBackground(Color.GREEN);
                                    else if (_comp.compArray[i][j] == 3)
                                        playbutton[i][j].setBackground(Color.RED);
                                }
                            }
                        }
                    }
                });
                panel.add(playbutton[r][c]);
            }
        }
        if (plyHitCnt >= 9) {
            plyWin = true;
        }
        panel.setVisible(true);
        frame.setResizable(true);
        JOptionPane.showMessageDialog(null, panel, "Choose ONE Target", JOptionPane.INFORMATION_MESSAGE);
    }

    //displays victory message
    public void victory() {
        JPanel panel = new JPanel();
        JFrame frame = new JFrame();
        JLabel c = new JLabel("You beat the computer! Maybe you ARE smarter than a fifth grader!");
        panel.add(c);
        c.setForeground(new Color(44, 8, 8));
        panel.setBackground(new Color(57, 197, 216));
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(null, panel, "Victory!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(3);
    }

    //displays lose message
    public void lose() {
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        JLabel c = new JLabel("You lost to the computer! Dumb.");
        panel.add(c);
        c.setForeground(new Color(8, 24, 44));
        panel.setBackground(new Color(216, 57, 57));
        frame.add(panel);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JOptionPane.showMessageDialog(null, panel, "You lost!", JOptionPane.INFORMATION_MESSAGE);
        System.exit(3);
    }

    //creates empty battleship board
    public void makeBattleshipBoard() {
        int row = _ply.row;
        int col = _ply.col;
        JButton[][] button = new JButton[10][10];
        JPanel panel = new JPanel(new GridLayout(row, col));
        JFrame frame = new JFrame();
        frame.setLayout(new GridLayout());
        panel.setSize(650, 650);
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                button[i][j] = new JButton();
                JButton b = button[i][j];
                b.setOpaque(true);
                int r = i;
                int c = j;
                b.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if (e.getSource() == button[r][c]) {
                            //if the light is on
                            if (_ply.playArray[r][c] == 0) {
                                b.setOpaque(true);
                                b.setBorderPainted(false);
                                b.setBackground(Color.BLUE);
                                _ply.playArray[r][c] = 1;
                            } else {
                                _ply.playArray[r][c] = 0;
                                b.setOpaque(false);
                                b.setBorderPainted(true);
                            }
                        }
                    }
                });
                panel.add(button[i][j]);
            }
        }
        panel.setVisible(true);

        JOptionPane.showMessageDialog(null, panel, "Add your 3 ships. A small 2-hit, a medium 3-hit, a large 4-hit) ", JOptionPane.INFORMATION_MESSAGE);

    }

    //displays intro message
    public static void introduction1() {
        JPanel ipanel = new JPanel();
        JLabel a = new JLabel("Welcome BattleShip Player.");
        JLabel b = new JLabel("A button board will appear for you to select ONE target location per play.");
        JLabel e = new JLabel("If your target hasn't hit a ship, the selection will be RED.");
        JLabel f = new JLabel("If your target has hit a ship, the selection will be GREEN.");
        JLabel c = new JLabel("You will then see your attack results.");
        JLabel d = new JLabel("After that, you will see the computer's attack results on your board.");
        ipanel.setLayout(new BoxLayout(ipanel, BoxLayout.Y_AXIS));
        ipanel.add(a);
        ipanel.add(b);
        ipanel.add(e);
        ipanel.add(f);
        ipanel.add(c);
        ipanel.add(d);
        ipanel.setBackground(new Color(238, 235, 211));
        JOptionPane.showMessageDialog(null, ipanel, "Battleship", JOptionPane.INFORMATION_MESSAGE);
    }
}

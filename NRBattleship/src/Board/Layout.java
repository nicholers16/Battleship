package Board;

import javax.swing.*;
import java.awt.*;

public class Layout {

    private JFrame _frame;
    private JPanel _pan;
    private JLabel[][] _labels;
    private String name;
    private int [][] _arr;
    public void setArr(int[][] i) { _arr = i; }

    //initializes battleship board using name
    public Layout (String _name){
        name = _name;
    }

    //displays frame with battleship board
    public void showBoard (String name){
        _frame = new JFrame();
        _pan = new JPanel();
        _pan.setLayout(new GridLayout(10, 10));
        _labels = new JLabel[10][10];
        for (int r = 0; r < _labels.length; r++){
            for (int c = 0; c < _labels[0].length; c++){
                _labels[r][c] = new JLabel(findIcon(r, c));
                _pan.add(_labels[r][c]);
            }
        }
        _pan.setSize(700,800);
        _frame.setSize(700,800);
        _frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        _frame.add(_pan);
        _frame.setVisible(false);
        JOptionPane.showMessageDialog(null, _pan, "Board display of " + name, JOptionPane.INFORMATION_MESSAGE);

    }

    //returns icon based on battleship hit value. if the element == 2, the ship has been hit.
    //if the element is == 3, the ship has not been hit. if the element is == 1, the ship is there but hasnt been hit.
    private ImageIcon findIcon(int r, int c) {
        if (name.equalsIgnoreCase("Rico")){
            if (_arr[r][c] == 2){
                return Graphics.HIT.getImage();
            }
            else if (_arr[r][c] == 3) {
                return Graphics.NOHIT.getImage();
            }
            else if (_arr[r][c] == 1){
                return Graphics.SHIP.getImage();
            }
            else {
                return Graphics.PATH.getImage();
            }
        }
        else {
            if (_arr[r][c] == 2){
                return Graphics.HIT.getImage();
            }
            else if (_arr[r][c] == 3) {
                return Graphics.NOHIT.getImage();
            }
            else {
                return Graphics.PATH.getImage();
            }
        }
    }

}



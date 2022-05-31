package Board;

import javax.swing.ImageIcon;
public enum Graphics {
    //displays variables that store the pngs of the game
    HIT("hit.png"),
    PATH("path.png"),
    NOHIT("nohit.png"),
    SHIP("ship.png");

    private ImageIcon _img;
    //returns variable for the image
    public ImageIcon getImage() {
        return _img;
    }
    Graphics(String fName) {
        _img = new ImageIcon(getClass().getResource(fName));
    }
}

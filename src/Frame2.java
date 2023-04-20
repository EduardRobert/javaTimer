import javax.swing.*;
import java.awt.*;

public class Frame2 extends JFrame {
    public static void main(String[] args) {
        Frame2 f = new Frame2();

        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        f.setLocationRelativeTo(null);
        f.setSize(new Dimension(400, 400));
        f.setLayout(new FlowLayout());

        f.setVisible(true);
    }
}

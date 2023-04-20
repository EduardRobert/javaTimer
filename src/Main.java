import javax.swing.*;
import java.text.ParseException;

public class Main {
    public static void main(String[] args) throws ParseException {
        int choice = JOptionPane.showOptionDialog(null, "Chose option!", "Option Dialog", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, new String[]{"Settings", "Close"}, null);


            if(choice == 0){
                //Settings
            TimerSettings frame = new TimerSettings();
            frame.setVisible(true);
                frame.setTitle("Timer Settings");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 300);
                frame.setLocationRelativeTo(null);
            }else{
                //Close
                System.exit(0);
            }
    }
}
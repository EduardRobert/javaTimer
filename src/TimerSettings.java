import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimerSettings extends JFrame {
   public JPanel mainPanel;
   public JPanel radioButtonPanel;
   private JPanel colorPanel;
   private JPanel speedPanel;
   private JPanel comandPanel;
   public JRadioButton onTimeRadioButton;
   public JRadioButton countdownRadioButton;
   private JTextField textField2;
   private JButton choseColorButton;
   private JLabel speedLabel;
   private JButton startButton;
   private JButton stopButton;
   private JFormattedTextField textField1;
   private JLabel colorLabel;
   private JComboBox speed;
   static Integer change;
   static SwingWorker sw;
   int counter;

   public TimerSettings() {
      this.setContentPane(mainPanel);
      ButtonGroup radioGroup = new ButtonGroup();
      radioGroup.add(onTimeRadioButton);
      radioGroup.add(countdownRadioButton);

      textField1.setFormatterFactory(new JFormattedTextField.AbstractFormatterFactory() {
         @Override
         public JFormattedTextField.AbstractFormatter getFormatter(JFormattedTextField tf) {
            try {
               return new MaskFormatter("##:##:##");
            } catch (ParseException e) {
               throw new RuntimeException(e);
            }
         }
      });
      speed.addItem(1);
      speed.addItem(2);
      speed.addItem(3);
      speed.addItem(4);
      speed.addItem(5);


      choseColorButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            Color c = JColorChooser.showDialog(null, "Choose background color", Color.red);
            colorLabel.setForeground(c);
            String colorCode = String.format("#%06X", (0xFFFFFF & c.getRGB()));
            colorLabel.setText(colorCode);
         }
      });

      onTimeRadioButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            textField2.setEnabled(false);
            textField1.setEnabled(true);
         }
      });

      countdownRadioButton.addActionListener(new ActionListener() {
         @Override
         public void actionPerformed(ActionEvent e) {
            textField1.setEnabled(false);
            textField2.setEnabled(true);
         }
      });

      startButton.addActionListener(e -> {
         choseColorButton.setEnabled(false);
         startButton.setEnabled(false);
         textField1.setEnabled(false);
         textField2.setEnabled(false);
         onTimeRadioButton.setEnabled(false);
         countdownRadioButton.setEnabled(false);
         speed.setEnabled(false);

         if (onTimeRadioButton.isSelected()) {
            Frame2 fr2 = new Frame2();
            sw = new SwingWorker() {
               @Override
               protected Object doInBackground() throws Exception {

                  fr2.getContentPane().setBackground(colorLabel.getForeground());
                  fr2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                  fr2.setLocationRelativeTo(null);
                  fr2.setSize(new Dimension(400, 400));
                  fr2.setLayout(new FlowLayout());

                  String speed1 = String.valueOf(speed.getSelectedItem());
                  change = Integer.parseInt(String.valueOf(speed1));

                  Timer colorChange = new Timer(1000 * change, new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                        change++;

                        Color[] colors = new Color[]{Color.WHITE, colorLabel.getForeground()};
                        fr2.getContentPane().setBackground(colors[counter++]);
                        if (counter >= colors.length) {
                           counter = 0;
                        }
                     }
                  });
                  colorChange.start();

                  String hh = textField1.getText();
                  String result;
                  DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

                  while (true) {
                     result = dateFormat.format(new Date());
                     if (result.equals(hh)) {
                        fr2.setVisible(true);
                     }
                  }
               }
            };
            sw.execute();


            stopButton.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                  radioGroup.clearSelection();
                  textField1.setText("");
                  sw.cancel(true);
                  fr2.dispose();
                  textField2.setText("");
                  choseColorButton.setEnabled(true);
                  startButton.setEnabled(true);
                  textField1.setEnabled(true);
                  textField2.setEnabled(true);
                  onTimeRadioButton.setEnabled(true);
                  countdownRadioButton.setEnabled(true);
                  speed.setEnabled(true);
               }
            });


         } else {
            try {
               int seconds = Integer.parseInt(textField2.getText());

               if (seconds <= 0) {
                  textField2.setText("Positive number(seconds)");

               } else {
                  Frame2 fr2 = new Frame2();

                  Timer timer = new Timer(seconds * 1000, new ActionListener() {
                     public void actionPerformed(ActionEvent e) {


                        fr2.getContentPane().setBackground(colorLabel.getForeground());
                        fr2.setVisible(true);
                        fr2.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                        fr2.setLocationRelativeTo(null);
                        fr2.setSize(new Dimension(400, 400));
                        fr2.setLayout(new FlowLayout());

                        String speed1 = String.valueOf(speed.getSelectedItem());
                        change = Integer.parseInt(String.valueOf(speed1));

                        Timer colorChange = new Timer(1000 * change, new ActionListener() {
                           @Override
                           public void actionPerformed(ActionEvent e) {
                              change++;

                              Color[] colors = new Color[]{Color.WHITE, colorLabel.getForeground()};
                              fr2.getContentPane().setBackground(colors[counter++]);
                              if (counter >= colors.length) {
                                 counter = 0;
                              }
                           }
                        });
                        colorChange.start();
                     }
                  });
                  timer.setRepeats(false);
                  timer.start();

                  stopButton.addActionListener(new ActionListener() {
                     @Override
                     public void actionPerformed(ActionEvent e) {
                        radioGroup.clearSelection();
                        timer.stop();
                        fr2.dispose();
                        textField2.setText("");
                        choseColorButton.setEnabled(true);
                        startButton.setEnabled(true);
                        textField1.setEnabled(true);
                        textField2.setEnabled(true);
                        onTimeRadioButton.setEnabled(true);
                        countdownRadioButton.setEnabled(true);
                        speed.setEnabled(true);
                     }
                  });
               }
            } catch (NumberFormatException exc) {
               textField2.setText("Numbers only!");
               choseColorButton.setEnabled(true);
               startButton.setEnabled(true);
               textField1.setEnabled(true);
               textField2.setEnabled(true);
               onTimeRadioButton.setEnabled(true);
               countdownRadioButton.setEnabled(true);
               speed.setEnabled(true);
            }
         }
      });
   }
}
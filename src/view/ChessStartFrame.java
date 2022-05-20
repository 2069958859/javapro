package view;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JButton;
import controller.GameController;

public class ChessStartFrame extends JFrame {
    private final int WIDTH;
    private final int HEIGHT;

    public ChessStartFrame(int WIDTH, int HEIGHT)  {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        setSize(WIDTH,HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        StartButton();
       JLabel im=new JLabel();
        ImageIcon icon=new ImageIcon("./images/img.png");
        im.setIcon(icon);
        im.setHorizontalAlignment(SwingConstants.CENTER);
        im.setSize(WIDTH,HEIGHT);
        im.setOpaque(true);
        add(im);
    }
    public void StartButton(){
        JButton start=new JButton("Start Your Game!");

        start.setLocation(HEIGHT, HEIGHT / 10 + 120);
        start.setSize(200, 60);
        start.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(start);
        start.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                closeThis();
            }
        });
    }
    private void closeThis() {
        this.dispose();
        ChessGameFrame mainFrame = new ChessGameFrame(1000, 750);
        mainFrame.setVisible(true);
    }

//        ImageIcon icon2=new ImageIcon("./images/img2.jpg");
//        im.setIcon(icon2);
//        icon2.setImage(icon2.getImage().getScaledInstance(1000,750 , Image.SCALE_DEFAULT));
//        im.setHorizontalAlignment(SwingConstants.CENTER);
//        im.setSize(WIDTH,HEIGHT);
//        im.setOpaque(true);
//        add(im);
    }


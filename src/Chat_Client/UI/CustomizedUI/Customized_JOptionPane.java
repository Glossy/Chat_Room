package Chat_Client.UI.CustomizedUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

/**
 * Created by Wu on 2017/5/2.
 */
public class Customized_JOptionPane extends JDialog {
    public Customized_JOptionPane(String title, String content){
        setUndecorated(true);
        //拖动操作
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                cursor_x = e.getX();
                cursor_y = e.getY();
                isDragged = true;
            }
            @Override
            public void mouseReleased(MouseEvent e){
                isDragged = false;
            }
        });
        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (isDragged){
                    int x = getLocation().x;
                    int y = getLocation().y;
                    //窗口新的坐标位置  = 移动前坐标位置+（鼠标指针当前坐标-鼠标按下时指针的位置）
                    setLocation(e.getX() + x - cursor_x,e.getY() + y - cursor_y);
                }
            }
        });
        setBounds(100, 100, 450, 200);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBackground(Color.DARK_GRAY);
        contentPane.setBorder(new EmptyBorder(5,5,5,5));
        setResizable(false);
        setContentPane(contentPane);
        contentPane.setLayout(null);

        jtitle = new JLabel(title);
        jtitle.setForeground(Color.WHITE);
        jtitle.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 30));
        jtitle.setBounds(10,0,200,40);
        contentPane.add(jtitle);

        textArea = new JTextArea();
        textArea.setBackground(Color.DARK_GRAY);
        textArea.setForeground(Color.WHITE);
        textArea.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 25));
        textArea.setBounds(20, 50, 410 , 70);
        textArea.setWrapStyleWord(true);
        textArea.setText(content);
        contentPane.add(textArea);

        confirmButton = new UniversalButton("Confirm");
        confirmButton.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 20));
        confirmButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        confirmButton.setBounds(180,this.getHeight() - 50,90,30);
        contentPane.add(confirmButton);

        closeButton = new CloseButton();
        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        int width = this.getWidth();
        closeButton.setBounds(width - 40,0,40,30);
        contentPane.add(closeButton);
        setVisible(true);
    }

    private boolean isDragged = false;
    private int cursor_x, cursor_y;
    private JPanel contentPane;
    private JTextArea textArea;
    private JLabel jtitle;
    private CloseButton closeButton;
    private UniversalButton confirmButton;
}


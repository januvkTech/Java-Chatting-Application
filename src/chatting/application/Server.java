package chatting.application;

import javax.swing.*;//JFrame, JPanel, JLabel, ImageIcon
import javax.swing.border.*;//for padding of the chat-panel
import java.awt.*;//Color, Font, BorderLayout, Image
import java.awt.event.*;//ActionListener
import java.net.*;//ServerSocket and Socket
import java.io.*;//DataInput/OutputStream
//import java.text.*;

public class Server implements ActionListener {

    static JFrame f = new JFrame();//declaration of JFrame object
    JTextField textBox;
    JPanel chatBox;
    static Box vertical = Box.createVerticalBox();//boc layout for arranging the chats vertically
    static DataOutputStream dOut;

    Server() {
        f.setLayout(null);//setting layout to null to manually set the components

        //creating and seting the top most header of the frame
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(7, 94, 84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);
        
        //ading the back button to the panel p1
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5, 20, 25, 25);
        p1.add(back);
        back.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/1.png"));
        Image i5 = i4.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(45, 10, 50, 50);
        p1.add(profile);

        JLabel name = new JLabel("Server");
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF", Font.BOLD, 18));
        name.setBounds(115, 15, 100, 18);
        p1.add(name);

        chatBox = new JPanel();
        chatBox.setBounds(5, 75, 440, 570);
//        chatBox.setBackground(new Color(22, 22, 22));
        f.add(chatBox);

        textBox = new JTextField();
        textBox.setBounds(5, 655, 350, 30);
        textBox.setForeground(Color.BLACK);
        textBox.setFont(new Font("SAN_SERIF", Font.PLAIN, 14));
        f.add(textBox);

        JButton send = new JButton("Send");
        send.setFont(new Font("SAN_SERIF", Font.BOLD, 14));
        send.setBackground(new Color(7, 94, 84));
        send.setForeground(Color.WHITE);
        send.setBounds(365, 655, 80, 30);
        send.addActionListener(this);
        f.add(send);

        f.setSize(450, 700);//size of the frame
        f.setLocation(900, 50);//set the open location of the frame
        f.getContentPane().setBackground(Color.WHITE);//for seting the background color
        f.setUndecorated(true);//for removing the default header 
        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent a) {
        try {
            String input = textBox.getText();
//        JLabel output = new JLabel(input);
//        JPanel p2 = new JPanel();
            JPanel p2 = formatTextLabel(input);
//        p2.setBackground(Color.red);
//        p2.add(output);
            chatBox.setLayout(new BorderLayout());
            JPanel right = new JPanel(new BorderLayout());
//        right.setBackground(Color.green);
            right.add(p2, BorderLayout.LINE_END);
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));
            chatBox.add(vertical, BorderLayout.PAGE_START);
            dOut.writeUTF(input);
            textBox.setText("");
//        System.out.println(input);
            f.repaint();
            f.invalidate();
            f.validate();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static JPanel formatTextLabel(String input) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        JLabel output = new JLabel(input);
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(new Color(37, 211, 102));
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(10, 15, 10, 50));
        panel.add(output);
        return panel;
    }

    public static void main(String[] args) {
        new Server();
        try {
            ServerSocket skt = new ServerSocket(6666);
            while (true) {
                Socket s = skt.accept();
                DataInputStream dIn = new DataInputStream(s.getInputStream());
                dOut = new DataOutputStream(s.getOutputStream());
                while (true) {
                    String msg = dIn.readUTF();
                    JPanel panel = formatTextLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    f.validate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

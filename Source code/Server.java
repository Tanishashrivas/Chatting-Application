//phle server run hoga fir client run hoga. and pehle client se msg bheja :)

package chattingapplication;
// import java.awt.Color;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Calendar;
// import java.util.spi.CalendarNameProvider;
import java.util.*;
import java.text.*;
import java.net.*; //server or socekt class(for client) comes from net package

public class Server implements ActionListener{
    static JFrame f= new JFrame();
    JTextField text;
    JPanel a1;
    static Box vertical = Box.createVerticalBox();//taaki messages ek ke neeche ek jaye!
    static DataOutputStream dout;

    Server(){

        f.setLayout(null);

        JPanel p= new JPanel();
        p.setBackground(new Color(5, 24, 109));
        p.setBounds(0,0,450,50); //coordinates of panel
        p.setLayout(null);
        f.add(p); //adds panel over the frame

        ImageIcon i= new ImageIcon(ClassLoader.getSystemResource("icons/3.png")); 
        Image i2= i.getImage().getScaledInstance(18, 18, Image.SCALE_DEFAULT);
        ImageIcon i3= new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(5,15,18,18); //coordinates of img, for this to work,we set layout of panel to null
        p.add(back); //adds image over the panel

        back.addMouseListener(new MouseAdapter() {//back button press hone pr mouse ka action chahiye 
            public void mouseClicked(MouseEvent ae){
                System.exit(0);
            }
        });
        //For DP
        ImageIcon i4= new ImageIcon(ClassLoader.getSystemResource("icons/1.png")); 
        Image i5= i4.getImage().getScaledInstance(36, 36, Image.SCALE_DEFAULT);
        ImageIcon i6= new ImageIcon(i5);
        JLabel Profile = new JLabel(i6);
        Profile.setBounds(30,7,36,36); //coordinates of img, for this to work,we set layout of panel to null
        p.add(Profile); 

        ImageIcon i7= new ImageIcon(ClassLoader.getSystemResource("icons/video.png")); 
        Image i8= i7.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i9= new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(310,15,20,20); //coordinates of img, for this to work,we set layout of panel to null
        p.add(video); 

        ImageIcon i10= new ImageIcon(ClassLoader.getSystemResource("icons/phone.png")); 
        Image i11= i10.getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT);
        ImageIcon i12= new ImageIcon(i11);
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,15,20,20); //coordinates of img, for this to work,we set layout of panel to null
        p.add(phone);

        ImageIcon i13= new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png")); 
        Image i14= i13.getImage().getScaledInstance(8, 16, Image.SCALE_DEFAULT);
        ImageIcon i15= new ImageIcon(i14);
        JLabel icon3 = new JLabel(i15);
        icon3.setBounds(405,17,20,16); //coordinates of img, for this to work,we set layout of panel to null
        p.add(icon3); 

        //to write something on frame,we use JLabel
        JLabel name= new JLabel("Muskan");
        name.setBounds(80,12,90,16);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF",Font.BOLD,16));
        p.add(name);

        JLabel status= new JLabel("Online");
        status.setBounds(80,33,90,10);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN SERIF",Font.BOLD,10));
        p.add(status);

        //Different Panel below the header
        a1= new JPanel();
        a1.setBounds(5,55,440,590);
        f.add(a1);
        
        text= new JTextField();
        text.setBounds(5,655,350,35);
        text.setFont(new Font("Inkfree",Font.BOLD,16));
        f.add(text);

        JButton send= new JButton("Send");
        send.setBounds(357,655,90,35);
        send.setBackground(new Color(5, 24, 109));
        send.setFont(new Font("Inkfree",Font.PLAIN,13));
        send.addActionListener(this);//to add an action!
        send.setForeground(Color.WHITE);
        f.add(send);

        f.setSize(450,700);
        f.setLocation(200,25);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);

        f.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae){
        try{
        String out= text.getText();//With this you can take out value wriiten in the textfield
        // System.out.println(out);
        JPanel p2= formatLabel(out);

        a1.setLayout(new BorderLayout());

        JPanel right= new JPanel(new BorderLayout());//frame ka right side
        right.add(p2, BorderLayout.LINE_END);//this 2 lines will align the message to the right
        vertical.add(right);//vertical alignment
        vertical.add(Box.createVerticalStrut(13));//space between 2 messages

        a1.add(vertical, BorderLayout.PAGE_START);

        //msg send krne ke liye
        dout.writeUTF(out);

        text.setText("");

        f.repaint();//Frame is fixed, if we don't repaint it remains unloaded and hence nothing is displayed
        f.invalidate();
        f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static JPanel formatLabel(String out){//jpanel is return type
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));//y axis ke along ek ke neeche ek
        
        JLabel output = new JLabel("<html><p style=\"width:95px \">" + out + "</p></html>");//pehle se quotation marks the isliye style wale "" ko escape kiya
        output.setFont(new Font("Tahoma",Font.PLAIN,15));
        output.setBackground(Color.cyan);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(6,8,6,30));//to set padding, LTBR

        panel.add(output);
        //for message time
        Calendar cal= Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time= new JLabel();
        time.setText(sdf.format(cal.getTime()));//kisi bhi cheez ko dynamically set krne ke liye we use setText
        panel.add(time);

        return panel;
    }
    
    public static void main(String[] args){
        new Server();

        try {
            //creating server
            ServerSocket skt = new ServerSocket(5001);//port number is 5001
            //Server ko infinitely run krna hai or saare message accept krna hai:
            while(true){
                Socket s = skt.accept();//socket ke andar store krva rhe hain
                DataInputStream din = new DataInputStream(s.getInputStream()); // This is used to receive messages
                dout = new DataOutputStream(s.getOutputStream());//for sending msgs

                while(true){
                    // infinite send and receive can be achieved through readutf and writeutf protocol
                    String msg= din.readUTF();
                    JPanel panel = formatLabel(msg);//msg ko panel pr display krne ke liye

                    JPanel left= new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    // vertical.add(Box.createVerticalStrut(13));
                    f.validate();
                    
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
    }
}

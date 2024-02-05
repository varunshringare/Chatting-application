
package chatting.application;
import static chatting.application.Server.dout;
import static chatting.application.Server.f;
import static chatting.application.Server.formatLabel;
import static chatting.application.Server.vertical;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client  implements ActionListener{
    JTextField text1;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static JFrame f = new JFrame();
    static DataOutputStream dout;
    Client(){
        f.setLayout(null); 
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(0,117,159));
        p1.setBounds(0,0,500,80);
        p1.setLayout(null);
        f.add(p1);
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        ImageIcon i3=new ImageIcon(i2);
        JLabel back = new JLabel(i3);
        back.setBounds(10, 25,30, 30);
        p1.add(back);
        back.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent ae)
        {
            System.exit(0);
        }
        });
       
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/max.png"));
        Image i5 = i4.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
        ImageIcon i6=new ImageIcon(i5);
        JLabel profile = new JLabel(i6);
        profile.setBounds(50, 10,60, 60);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon i9=new ImageIcon(i8);
        JLabel video = new JLabel(i9);
        video.setBounds(350, 15,35, 35);
        p1.add(video);
        
        ImageIcon ia = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image ib = ia.getImage().getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        ImageIcon ic=new ImageIcon(ib);
        JLabel phone = new JLabel(ic);
        phone.setBounds(400, 15,35, 35);
        p1.add(phone);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i11 = i10.getImage().getScaledInstance(15, 35, Image.SCALE_SMOOTH);
        ImageIcon i12=new ImageIcon(i11);
        JLabel dots = new JLabel(i12);
        dots.setBounds(460, 15,15, 35);
        p1.add(dots);
        
        JLabel name = new JLabel("Max");
        name.setBounds(115,19,100,14);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
        p1.add(name);
        
        JLabel status = new JLabel("Active");
        status.setBounds(115,36,100,14);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN_SERIF",Font.PLAIN,14));
        p1.add(status);
        
a1 = new JPanel();
        a1.setBounds(5, 88, 490, 597);
        f.add(a1);
 
        text1 = new JTextField();
        text1.setBounds(5,688,355,50);
        text1.setFont(new Font("SANS_SERIF",Font.PLAIN,20));
        f.add(text1);
        
        JButton send = new JButton("send");
        send.setBounds(365, 688, 128, 48);
        send.setBackground(new Color(0,117,159));
         send.setFont(new Font("SANS_SERIF",Font.PLAIN,20));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        f.add(send);
        
        f.setSize(500,740);
       f.getContentPane().setBackground(Color.white);
       f.setUndecorated(true);
        f.setLocation(800,20);
        f.setVisible(true);
    }
        public void actionPerformed(ActionEvent ae)
        {
            try
            {
            String out=text1.getText();
            JPanel p2 = formatLabel(out);
      a1.setLayout(new BorderLayout());
      JPanel right = new JPanel(new BorderLayout());
      right.add(p2,BorderLayout.LINE_END);
      vertical.add(right);
      vertical.add(Box.createVerticalStrut(15));
      
      a1.add(vertical,BorderLayout.PAGE_START);
      
      dout.writeUTF(out);
      
      text1.setText("");
      f.repaint();
      f.invalidate();
      f.validate();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
      
        }
        public static JPanel formatLabel(String out)
        {
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
            JLabel output =  new JLabel("<html><p style=\"width:150px\"> "+ out+"</p></html>");
            output.setFont(new Font("Tahoma",Font.PLAIN,18));
            output.setBackground(Color.orange);
            output.setOpaque(true);
            output.setBorder(new EmptyBorder(15,15,15,50));
            panel.add(output);
            
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:MM");
            
            JLabel time = new JLabel();
            time.setText(sdf.format(cal.getTime()));
            panel.add(time);
            return panel;
        }
    
    public static void main(String args[])
    {
        new Client();
        
        try{
            Socket s = new Socket("127.0.0.1", 6001);
            DataInputStream din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
            while(true){
                a1.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel,BorderLayout.LINE_START);
                    vertical.add(left);
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical,BorderLayout.PAGE_START);
                    f.validate();
                }
    }catch(Exception e)
    {
        e.printStackTrace();
    }
}
}


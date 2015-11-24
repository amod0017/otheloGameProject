package edu.lamar.othelo.client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by jason on 11/14/15.
 */
public class LoginUI implements ActionListener {

    public GameClient client;

    JTextField userText = new JTextField(20);
    JPasswordField passwordText = new JPasswordField(20);
    JTextField hostText = new JTextField(15);
    JTextField portText = new JTextField(5);

    LoginUI() {
        JFrame frame = new JFrame("Login");
        frame.setSize(300, 250);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        frame.add(panel);
        placeComponents(panel);

        frame.setVisible(true);
    }

    public static void main(String[] args) {
        LoginUI login = new LoginUI();
    }

    public void actionPerformed(ActionEvent ae) {
        String action = ae.getActionCommand();
        if (action.equals("register")) {
            String username = userText.getText();
            String pd = new String(passwordText.getPassword());
            String password = String.valueOf(pd.hashCode());
            String host = hostText.getText();
            int port = Integer.parseInt(portText.getText());
            client = GameClient.getInstance(host, port);
			client.register(username,pd);
        }
        if (action.equals("login")) {
            String username = userText.getText();
            String pd = new String(passwordText.getPassword());
            String password = String.valueOf(pd.hashCode());
            String host = hostText.getText();
            int port = Integer.parseInt(portText.getText());
            client = GameClient.getInstance(host, port);
			client.login(username,pd);
        }
    }

    public void placeComponents(JPanel panel) {

        panel.setLayout(null);

        JLabel userLabel = new JLabel("User");
        userLabel.setBounds(10, 10, 80, 25);
        panel.add(userLabel);


        userText.setBounds(100, 10, 160, 25);
        panel.add(userText);

        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setBounds(10, 40, 80, 25);
        panel.add(passwordLabel);


        passwordText.setBounds(100, 40, 160, 25);
        panel.add(passwordText);

        JLabel hostLabel = new JLabel("Host");
        hostLabel.setBounds(10, 70, 80, 25);
        panel.add(hostLabel);


        hostText.setBounds(100, 70, 80, 25);
        panel.add(hostText);

        JLabel portLabel = new JLabel("Port");
        portLabel.setBounds(10, 100, 80, 25);
        panel.add(portLabel);


        portText.setBounds(100, 100, 80, 25);
        panel.add(portText);

        JButton loginButton = new JButton("login");
        loginButton.setBounds(10, 130, 80, 25);
        loginButton.addActionListener(this);
        panel.add(loginButton);


        JButton registerButton = new JButton("register");
        registerButton.setBounds(180, 130, 100, 25);
        registerButton.addActionListener(this);
        panel.add(registerButton);

    }

}

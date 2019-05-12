package es.deusto.client.controller;

import es.deusto.client.view.LoginJDialog;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginController {
    private LoginJDialog login;
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private JTextField usernameField;

    public LoginController() {
        initComponents();
        initListeners();
    }

    private void initComponents() {
        login = new LoginJDialog();

        contentPane = login.getContentPane();
        buttonOK = login.getButtonOK();
        buttonCancel = login.getButtonCancel();
        passwordField = login.getPasswordField();
        usernameField = login.getUsernameField();
    }

    public void showLoginWindow() {
        //Show landing page
        login.setVisible(true);
    }

    private void initListeners() {
        buttonOK.addActionListener(e -> onOK());
        buttonCancel.addActionListener(e -> onCancel());
    }

    private void onCancel() {
        // add your code here
        login.dispose();
    }

    private void onOK() {
        // add your code here
        login.dispose();

    }
}

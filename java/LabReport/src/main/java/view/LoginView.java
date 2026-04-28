package view;

import model.LoginDTO;
import model.LoginData;
import repository.ReversoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.HintTextField;
import utils.ViewUtils;

import javax.swing.*;
import java.awt.*;

public class LoginView {
    private static JPanel loginPanel = new JPanel();
    private static JTextArea loginText = new JTextArea(1, 15);

    public static void onCreate(JFrame frame) {
        loginPanel.removeAll();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));

        loginText.setEditable(false);
        loginText.setText("请输入账号密码");
        loginText.setVisible(true);
        loginPanel.add(loginText);
        loginPanel.add(Box.createVerticalStrut(10));

        initEvent();

        frame.getContentPane().add(loginPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void initEvent() {
        HintTextField accountText = new HintTextField("请输入账号");
        HintTextField passwordText = new HintTextField("请输入密码");
        JButton confirm = new JButton("登录");
        loginPanel.add(accountText);
        loginPanel.add(Box.createVerticalStrut(5));
        loginPanel.add(passwordText);
        loginPanel.add(confirm);

        confirm.addActionListener(event -> {
            if (accountText.getText().isEmpty() || passwordText.getText().isEmpty()) {
                loginText.setText("账号/密码为空！");
                return;
            }
            if (accountText.getText().length() < 6 || passwordText.getText().length() < 6) {
                loginText.setText("账号/密码长度过短！");
                return;
            }

            Call<LoginDTO> call = ReversoService.loginService(new LoginData(accountText.getText(), passwordText.getText()));
            call.enqueue(new Callback<>() {
                @Override
                public void onResponse(Call<LoginDTO> call, Response<LoginDTO> response) {
                    SwingUtilities.invokeLater(() -> {
                        LoginDTO body =response.body();
                        if (!response.isSuccessful() || body == null) {
                            loginText.setText("请求超时！");
                            ViewUtils.generateDialog("请求超时！");
                            return;
                        }
                        if (!body.getStatus().equals("200")) {
                            loginText.setText("请求失败！");
                            ViewUtils.generateDialog("请求失败！");
                        } else {
                            loginText.setText("请求成功！");
                        }
                    });
                }

                @Override
                public void onFailure(Call call, Throwable throwable) {
                    throwable.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        ViewUtils.generateDialog("请求超时！" + throwable.getMessage());
                    });
                }
            });
        });
    }

}

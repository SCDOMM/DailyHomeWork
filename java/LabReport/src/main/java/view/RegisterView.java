package view;

import model.LoginDTO;
import model.RegisterDTO;
import model.RegisterData;
import repository.ReversoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import utils.HintTextField;
import utils.ViewUtils;

import javax.swing.*;

public class RegisterView {
    private static JPanel registerPanel = new JPanel();
    private static JTextArea registerText = new JTextArea(1, 15);

    public static void onCreate(JFrame frame) {
        registerPanel.removeAll();
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

        registerText.setEditable(false);
        registerText.setText("请进行注册");
        registerText.setVisible(true);
        registerPanel.add(registerText);
        registerPanel.add(Box.createVerticalStrut(10));

        initEvent();

        frame.getContentPane().add(registerPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void initEvent() {
        HintTextField nameText = new HintTextField("请输入用户名");
        HintTextField accountText = new HintTextField("请输入账号");
        HintTextField passwordText = new HintTextField("请输入密码");
        JButton confirm = new JButton("注册");
        registerPanel.add(nameText);
        registerPanel.add(Box.createVerticalStrut(10));
        registerPanel.add(accountText);
        registerPanel.add(Box.createVerticalStrut(10));
        registerPanel.add(passwordText);
        registerPanel.add(confirm);

        confirm.addActionListener(event -> {
            if (nameText.getText().isEmpty() || accountText.getText().isEmpty() || passwordText.getText().isEmpty()) {
                registerText.setText("用户名/账号/密码为空！");
                return;
            }
            if (nameText.getText().length() < 2 || accountText.getText().length() < 6 || passwordText.getText().length() < 6) {
                registerText.setText("用户名/账号/密码长度过短！");
                return;
            }

            Call<RegisterDTO> call = ReversoService.registerService(new RegisterData(nameText.getText(), accountText.getText(), passwordText.getText()));
            call.enqueue(new Callback<>() {

                @Override
                public void onResponse(Call<RegisterDTO> call, Response<RegisterDTO> response) {
                    SwingUtilities.invokeLater(() -> {
                        RegisterDTO body = response.body();
                        if (!response.isSuccessful() || body == null) {
                            registerText.setText("请求超时！");
                            ViewUtils.generateDialog("请求失败！");
                            return;
                        }
                        if (!body.getStatus().equals("200")) {
                            registerText.setText("请求失败！");
                            ViewUtils.generateDialog("请求超时！");
                        } else {
                            registerText.setText("请求成功！");
                        }
                    });
                }

                @Override
                public void onFailure(Call call, Throwable throwable) {
                    throwable.printStackTrace();
                    SwingUtilities.invokeLater(() -> {
                        registerText.setText("请求失败！");
                        ViewUtils.generateDialog("请求超时！" + throwable.getMessage());
                    });
                }
            });

        });
    }
}


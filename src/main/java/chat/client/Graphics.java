package chat.client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.TimerTask;

import static chat.Core.*;

public class Graphics extends JFrame{
    JTextPane textPane;
    JTextField input;
    JPanel panel;
    JButton send;
    JTextPane online;
    java.util.Timer timer = new java.util.Timer();
    public Graphics(){
        super("Chat" + " - " + version.type + ' ' + version.version + " | " + version.prefix);
        this.setSize(size.x, size.y);
        timer.schedule(new OnlineChecker(), 1L, 1L);
        textPane = new JTextPane();
        online = new JTextPane();
        input = new JTextField();
        panel = new JPanel();
        send = new JButton(">");
        send.addActionListener(e -> {
            out(input.getText());
            input.setText("");
        });
        textPane.setText(textPane.getText() + "\n");
        textPane.setEditable(false);
        online.setEditable(false);
        textPane.setBounds(10, 10, getWidth() - 200, getHeight() - 100);
        textPane.setBackground(new Color(43, 43, 43));
        textPane.setForeground(new Color(255, 255, 255));
        input.setBounds(10, getHeight() - 80, getWidth() - 80, 25);
        input.setBackground(new Color(49, 51, 53));
        input.setForeground(new Color(255, 255, 255));
        send.setBounds(getWidth() - 60, getHeight() - 80, 40, 25);
        send.setBackground(new Color(116, 122, 128));
        send.setForeground(new Color(255, 255, 255));
        online.setBounds(getWidth() - 180, 10, 160, getHeight() - 100);
        online.setBackground(new Color(43, 43, 43));
        online.setForeground(new Color(255,255,255));
        panel.setLayout(null);
        panel.add(textPane);
        panel.add(online);
        panel.add(input);
        panel.add(send);
        panel.setBackground(new Color(60, 63, 65));
        setContentPane(panel);
        setResizable(false);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosing(WindowEvent e)
            {
                Client.exit();
                e.getWindow().dispose();
            }
        });
        setVisible(true);
    }

    /**
     * Обработка ввода нового сообщения
     * @param s сообщение
     */
    //TODO подсчет полученых строк и фикс перегрузки
    public void in(String s) {
        String string = textPane.getText();
        ArrayList<String> strings = new ArrayList<>();
        ArrayList<String> strings2 = new ArrayList<>();
        Collections.addAll(strings, string.split("\n"));
        Collections.addAll(strings, s.split("\n"));
        for (String se : strings) {
            if (se.length() < 60) {
                strings2.add(se);
                continue;
            }
            strings2.add(se.substring(0, 59));
            strings2.add(se.substring(60));
        }
        strings = strings2;
        while (strings.size() > 26) {
            strings.remove(0);
        }
        StringBuilder out = new StringBuilder();
        for (String str : strings) {
            out.append(str).append("\n");
        }
        textPane.setText(out.toString());
    }

    /**
     * Отправка сообщения на сервер
     * @param s текст сообщения
     */
    public static void out(String s) {
        Client.send(s);
    }

    /**
     * Запрос никнейма
     * @return Введенный в поле ник, при отмене/закрытии null
     */
    public String loginDialog() {
        return JOptionPane.showInputDialog(this.panel,
                "Введите никнейм", "Авторизация", JOptionPane.QUESTION_MESSAGE);
    }

    /**
     * Вывод информации об людях онлайн
     */
    public void online() {
        StringBuilder text = new StringBuilder("В сети:\n");
        for (String s : ClientSomthing.online) {
            text.append(s).append("\n");
        }
        if (online.getText().equals(text.toString())) {
            return;
        }
        online.setText(text.toString());
    }

    /**
     * Отсылка запросов на получение списка людей онлайн
     */
    public static class OnlineChecker extends TimerTask {
        @Override
        public void run() {
            if (Client.clientSomthing != null) {
                out("||online");
            }
        }
    }
}

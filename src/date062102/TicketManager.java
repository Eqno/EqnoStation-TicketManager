package date062102;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.WindowConstants;
import com.sun.security.auth.NTDomainPrincipal;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Insets;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;

//录入、修改、查询、删除。
class Manager extends JFrame {
    public Manager(boolean resizable) throws IOException {
        setVisible(true);
        setSize(810, 500);
        setResizable(resizable);
        setLocationRelativeTo(null);
        setTitle("长途汽车站票务信息管理系统");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        CardLayout card = new CardLayout();
        mainPanel.setLayout(card);

        AddInfo addInfoPanel = new AddInfo();
        ModInfo modInfoPanel = new ModInfo();
        QueInfo queInfoPanel = new QueInfo();
        DelInfo delInfoPanel = new DelInfo();
        Book bookPanel = new Book();
        mainPanel.add(addInfoPanel, "录入");
        mainPanel.add(modInfoPanel, "修改");
        mainPanel.add(queInfoPanel, "查询");
        mainPanel.add(delInfoPanel, "删除");
        mainPanel.add(bookPanel, "预定");
        JMenuBar menuBar = new JMenuBar();
        SelectButton addInfo = new SelectButton("录入");
        addInfo.setBorder(BorderFactory.createLoweredBevelBorder());
        SelectButton modInfo = new SelectButton("修改");
        SelectButton queInfo = new SelectButton("查询");
        SelectButton delInfo = new SelectButton("删除");
        SelectButton book = new SelectButton("预定");

        ///
        ActionListener switchListener = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                card.show(mainPanel, e.getActionCommand().trim());
                addInfo.setBorder(BorderFactory.createRaisedBevelBorder());
                modInfo.setBorder(BorderFactory.createRaisedBevelBorder());
                queInfo.setBorder(BorderFactory.createRaisedBevelBorder());
                delInfo.setBorder(BorderFactory.createRaisedBevelBorder());
                book.setBorder(BorderFactory.createRaisedBevelBorder());
                ((JButton) e.getSource()).setBorder(BorderFactory.createLoweredBevelBorder());
                try {
                    addInfoPanel.readDataAgain();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    modInfoPanel.readDataAgain();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    queInfoPanel.readDataAgain();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
                try {
                    delInfoPanel.readDataAgain();
                } catch (IOException e2) {
                    // TODO Auto-generated catch block
                    e2.printStackTrace();
                }
                try {
                    bookPanel.readDataAgain();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }
        };
        ///

        addInfo.addActionListener(switchListener);
        modInfo.addActionListener(switchListener);
        queInfo.addActionListener(switchListener);
        delInfo.addActionListener(switchListener);
        book.addActionListener(switchListener);
        menuBar.add(addInfo);
        menuBar.add(modInfo);
        menuBar.add(queInfo);
        menuBar.add(delInfo);
        menuBar.add(book);
        setJMenuBar(menuBar);

        add(mainPanel);
    }
    class SelectButton extends JButton {
        public SelectButton(String s) {
            super("            "+s+"            ");
            setContentAreaFilled(false);
            setBorder(BorderFactory.createRaisedBevelBorder());
            setFont(new Font("Microsoft YaHei", Font.BOLD, 16));
        }
    }

//////////////////////////////////////////////////////////////////

    ///
    final int verticalGap = 10;
    final int horizontalGap = 70;
    final int labelTextGap = 10;
    final int borderTop = 50;
    final int borderBottom = 50;
    final int borderLeft = 100;
    final int borderRight = 100;
    final int padding = 5;
    ///

    Font textFont = new Font("Microsoft YaHei", Font.BOLD, 14);

    class Label extends JLabel {
        public Label(String s) {
            super(s);
            setFont(textFont);
        }
    }
    class Button extends JButton {
        public Button(String s) {
            super(s);
            setContentAreaFilled(false);
            setFont(textFont);
        }
    }
    class TextField extends JTextField {
        public TextField() {
            setFont(textFont);
            setMargin(new Insets(padding, padding, padding, padding));
            addFocusListener(selectListener);
            addActionListener(transFocusListener);
        }
    }
    class TextArea extends JTextArea {
        public TextArea() {
            setFont(textFont);
            setBorder(null);
            setBackground(null);
            setBorder(BorderFactory.createEmptyBorder(padding, padding, padding, padding));
        }
    }
    FocusListener selectListener = new FocusListener() {

        @Override
        public void focusLost(FocusEvent e) {

        }

        @Override
        public void focusGained(FocusEvent e) {
            ((TextField) e.getSource()).selectAll();
        }
    };
    ActionListener transFocusListener = new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {
            TextField textField = (TextField) e.getSource();
            textField.transferFocus();
            textField.transferFocus();
        }
    };

    class MainPanel extends JPanel {
        ArrayList<String[]> data;
        ArrayList<String> thisBus;
        FrameBox frameBox;
        Box numberBox, timeBox, stationBox, addedBox, buttonBox;
        Component addDelStrut, priceAddStrut;
        Button query;
        TextField busNumber;
        TextField startTimeLeft, startTimeRight;
        TextField arriveTimeLeft, arriveTimeRight;
        TextField passStation, price;
        Button addStation, delStation;
        TextArea info;
        TextField total, sold;
        Button submit, reset;
        MainPanel thisObject;
        ActionListener busNumberListener, addStationListener;
        ActionListener submitListener, queListener, resetListener;
        FocusListener didQueryBusNumber;
        JScrollPane scrollPane;
        public void readDataAgain() throws IOException {
            readData(data, thisBus);
        }
        public MainPanel() throws IOException {
            data = new ArrayList<String[]>();
            thisBus = new ArrayList<String>();
            setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
            add(Box.createHorizontalStrut(borderLeft));
            frameBox = new FrameBox();
            add(frameBox);
            add(Box.createHorizontalStrut(borderRight));
            thisObject = this;
        }
        class FrameBox extends JPanel {
            public FrameBox() {
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

                ///
                add(Box.createVerticalStrut(borderTop));
                ///

                numberBox = Box.createHorizontalBox();
                numberBox.add(new Label("车       次："));
                busNumber = new TextField();
                busNumber.removeActionListener(transFocusListener);
                busNumber.removeFocusListener(selectListener);;
                numberBox.add(busNumber);
                add(numberBox);

                ///
                add(Box.createVerticalStrut(verticalGap));
                ///

                timeBox = Box.createHorizontalBox();
                timeBox.add(new Label("出发时间："));
                startTimeLeft = new TextField();
                timeBox.add(startTimeLeft);
                timeBox.add(new Label(" ："));
                startTimeRight = new TextField();
                timeBox.add(startTimeRight);
                timeBox.add(Box.createHorizontalStrut(labelTextGap));
                timeBox.add(new Label("到达时间："));
                arriveTimeLeft = new TextField();
                timeBox.add(arriveTimeLeft);
                timeBox.add(new Label(" ："));
                arriveTimeRight = new TextField();
                timeBox.add(arriveTimeRight);
                add(timeBox);

                ///
                add(Box.createVerticalStrut(verticalGap));
                ///

                stationBox = Box.createHorizontalBox();

                stationBox.add(new Label("经  停  站："));
                passStation = new TextField();
                passStation.setColumns(10);
                stationBox.add(passStation);
                stationBox.add(Box.createHorizontalStrut(labelTextGap));
                final int verticalGap = 10;
                final int horizontalGap = 70;
                final int labelTextGap = 10;
                final int borderTop = 50;
                final int borderBottom = 50;
                final int borderLeft = 100;
                final int borderRight = 100;
                final int padding = 5;
                ///
                stationBox.add(new Label("票       价："));
                price = new TextField();
                price.removeActionListener(transFocusListener);
                addStationListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addStationToThisBus(thisObject);
                        passStation.requestFocus();
                    }
                };
                price.addActionListener(addStationListener);
                stationBox.add(price);
                priceAddStrut = Box.createHorizontalStrut(labelTextGap);
                stationBox.add(priceAddStrut);

                addStation = new Button("添加");
                addStation.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        addStationToThisBus(thisObject);
                    }
                });
                stationBox.add(addStation);
                addDelStrut = Box.createHorizontalStrut(labelTextGap);
                stationBox.add(addDelStrut);
                delStation = new Button("删除");
                delStation.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        removeStationFromThisBus(thisObject);
                    }
                });
                stationBox.add(delStation);

                add(stationBox);

                ///
                add(Box.createVerticalStrut(verticalGap));
                ///

                addedBox = Box.createHorizontalBox();
                addedBox.add(new Label("已  添  加："));
                info = new TextArea();
                info.setLineWrap(true);
                info.setEditable(false);
                scrollPane = new JScrollPane(
                        info,
                        ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
                        ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER
                );
                scrollPane.setPreferredSize(new Dimension(810, 100));
                addedBox.add(scrollPane);
                add(addedBox);

                ///
                add(Box.createVerticalStrut(verticalGap));
                ///

                Box ticketNumBox = Box.createHorizontalBox();
                ticketNumBox.add(new Label("车票总数："));
                total = new TextField();
                ticketNumBox.add(total);
                ticketNumBox.add(Box.createHorizontalStrut(labelTextGap));
                ticketNumBox.add(new Label("已  售  出："));
                sold = new TextField();
                sold.removeActionListener(transFocusListener);
                sold.addActionListener(new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            submitInfo();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                ticketNumBox.add(sold);
                add(ticketNumBox);

                ///
                add(Box.createVerticalStrut(30));
                ///

                queListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            readDataAgain();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }

                        for (int i=0; i<30; i++) {
                            System.out.print("-");
                        }
                        System.out.println();
                        for (String[] j: data) {

                            for (String k: j) {
                                System.out.print(k+" | ");
                            }
                            System.out.println();
                        }
                        System.out.println();
                        for (int i=0; i<30; i++) {
                            System.out.print("-");
                        }

                        queryBus(thisObject);
                    }
                };
                busNumberListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        queryBus(thisObject);
                    }
                };
                submitListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            submitInfo();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                };
                didQueryBusNumber = new FocusListener() {

                    @Override
                    public void focusLost(FocusEvent e) {

                    }

                    @Override
                    public void focusGained(FocusEvent e) {
                        if (busNumber.getText().equals("")) {
                            busNumber.requestFocus();
                            JOptionPane.showMessageDialog(null, "请先查询车次！", "修改失败", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
                resetListener = new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        resetText(thisObject);
                        busNumber.setText("");
                    }
                };
            }
        }
        void submitInfo() throws IOException {}
    }

    class AddInfo extends MainPanel {
        public AddInfo() throws IOException {
            super();
            buttonBox = Box.createHorizontalBox();
            submitAndReset(thisObject, "提交");
            readDataAgain();
            busNumber.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    startTimeLeft.requestFocus();
                }
            });
            busNumber.addFocusListener(new FocusListener() {

                @Override
                public void focusLost(FocusEvent e) {
                    if (judgeBusNumber(thisObject)) {
                        JOptionPane.showMessageDialog(null, "该车次已存在！", "录入失败", JOptionPane.ERROR_MESSAGE);
                        busNumber.setText("");
                        busNumber.requestFocus();
                    }
                }

                @Override
                public void focusGained(FocusEvent e) {
                    ((TextField) e.getSource()).selectAll();
                }
            });
        }
        void submitInfo() throws IOException {
            addOrSet(thisObject, 0, true);
        }
    }
    class ModInfo extends MainPanel {
        public ModInfo() throws IOException {
            super();
            numberBox.add(Box.createHorizontalStrut(labelTextGap));
            query = new Button("查询");
            query.addActionListener(queListener);
            numberBox.add(query);

            busNumber.addActionListener(busNumberListener);
            buttonBox = Box.createHorizontalBox();
            submitAndReset(thisObject, "修改");
            busNumber.addFocusListener(selectListener);
            startTimeLeft.addFocusListener(didQueryBusNumber);
            startTimeRight.addFocusListener(didQueryBusNumber);
            arriveTimeLeft.addFocusListener(didQueryBusNumber);
            arriveTimeRight.addFocusListener(didQueryBusNumber);
            passStation.addFocusListener(didQueryBusNumber);
            price.addFocusListener(didQueryBusNumber);
            total.addFocusListener(didQueryBusNumber);
            sold.addFocusListener(didQueryBusNumber);
        }
        void submitInfo() throws IOException {
            addOrSet(thisObject, 1, true);
        }
    }
    class QueInfo extends MainPanel {
        public QueInfo() throws IOException {
            super();
            numberBox.add(Box.createHorizontalStrut(labelTextGap));
            query = new Button("查询");
            query.addActionListener(queListener);
            numberBox.add(query);

            buttonBox = Box.createHorizontalBox();
            onlyReset(thisObject);
            setTextDisabled(thisObject);
            busNumber.addActionListener(busNumberListener);
            busNumber.addFocusListener(selectListener);
        }
    }
    class DelInfo extends MainPanel {
        public DelInfo() throws IOException {
            super();
            numberBox.add(Box.createHorizontalStrut(labelTextGap));
            query = new Button("查询");
            query.addActionListener(queListener);
            numberBox.add(query);

            busNumber.addActionListener(busNumberListener);
            buttonBox = Box.createHorizontalBox();
            submitAndReset(thisObject, "删除");
            submit.removeActionListener(submitListener);
            busNumber.addFocusListener(selectListener);
            setTextDisabled(thisObject);
            submit.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String busNumberText = busNumber.getText();
                    if (busNumberText.equals("") || startTimeLeft.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "请先查询车次！", "删除失败", JOptionPane.ERROR_MESSAGE);
                    }
                    else {
                        int n = JOptionPane.showConfirmDialog(null, "是否确定删除车次"+busNumberText+"的相关信息?", "删除确认",JOptionPane.YES_NO_OPTION);
                        if (n == 0) {
                            boolean flag = false;
                            for (int i=0; i<data.size(); i++) {
                                if (data.get(i)[0].equals(busNumberText)) {
                                    flag = true;
                                    data.remove(i);
                                    try {
                                        writeData(data);
                                    } catch (IOException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    }
                                    busNumber.requestFocus();
                                    JOptionPane.showMessageDialog(null, "信息删除成功！");
                                    resetText(thisObject);
                                    busNumber.setText("");
                                    try {
                                        readDataAgain();
                                    } catch (IOException e1) {
                                        // TODO Auto-generated catch block
                                        e1.printStackTrace();
                                    }
                                    break;
                                }
                            }
                            if (!flag) {
                                JOptionPane.showMessageDialog(null, "未找到相关信息！", "查询失败", JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        else {
                            busNumber.requestFocus();
                            return ;
                        }
                    }
                }
            });
        }
    }
    class Book extends MainPanel {
        public Book() throws IOException {
            super();
            numberBox.add(Box.createHorizontalStrut(labelTextGap));
            numberBox.add(new Label("从："));
            TextField start = new TextField();
            start.addFocusListener(didQueryBusNumber);
            numberBox.add(start);
            numberBox.add(Box.createHorizontalStrut(labelTextGap));
            numberBox.add(new Label("到："));
            TextField end = new TextField();
            end.addFocusListener(didQueryBusNumber);
            numberBox.add(end);
            numberBox.add(Box.createHorizontalStrut(labelTextGap));
            query = new Button("查询");
            busNumber.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    if (startTimeLeft.getText().equals("")) {
                        queryBus(thisObject);
                    }
                    else {
                        busNumber.transferFocus();
                        busNumber.transferFocus();
                    }
                }
            });
            query.addActionListener(queListener);
            numberBox.add(query);

            buttonBox = Box.createHorizontalBox();
            submitAndReset(thisObject, "下单");
            submit.removeActionListener(submitListener);
            busNumber.addFocusListener(selectListener);
            setTextDisabled(thisObject);

            ActionListener orderListener = new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String busNumberText = busNumber.getText();
                    String startPlace = start.getText();
                    String endPlace = end.getText();
                    if (busNumberText.equals("") || startTimeLeft.getText().equals("")) {
                        JOptionPane.showMessageDialog(null, "请先查询车次！", "下单失败", JOptionPane.ERROR_MESSAGE);
                    }
                    else if (startPlace.equals("")||endPlace.equals("")) {
                        if (queryBus(thisObject)) {
                            JOptionPane.showMessageDialog(null, "请输入起点和终点！", "查询失败", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    else {
                        queryBus(thisObject);
                        for (int i=0; i<data.size(); i++) {
                            if (data.get(i)[0].equals(busNumberText)) {
                                thisBus.clear();
                                for (String j: data.get(i)) {
                                    thisBus.add(j);
                                }
                                int index = thisBus.size()-1;
                                for (; index>=0; index--) {
                                    if (thisBus.get(index).equals("STOP")) {
                                        index --;
                                        break;
                                    }
                                }
                                int cot = 0;
                                String startMoney = "0", endMoney = "0";
                                for (; index>=0; index--) {
                                    if (cot == 2) {
                                        break;
                                    }
                                    String tmp = thisBus.get(index);
                                    if (tmp.equals(startPlace)) {
                                        startMoney = thisBus.get(index+1);
                                        cot ++;
                                    }
                                    if (tmp.equals(endPlace)) {
                                        endMoney = thisBus.get(index+1);
                                        cot ++;
                                    }
                                }
                                if (cot < 2) {
                                    JOptionPane.showMessageDialog(null, "起点或终点错误！", "查询失败", JOptionPane.ERROR_MESSAGE);
                                    return ;
                                }
                                BigDecimal delta = new BigDecimal(endMoney).subtract(new BigDecimal(startMoney));
                                if (delta.compareTo(BigDecimal.ZERO)>0) {
                                    int n = JOptionPane.showConfirmDialog(null, "共需"+delta.toString()+"元，是否下单?", "下单确认",JOptionPane.YES_NO_OPTION);
                                    if (n == 0) {
                                        Integer totalTicket = Integer.parseInt(total.getText());
                                        Integer soldTicket = Integer.parseInt(sold.getText());
                                        if (soldTicket < totalTicket) {
                                            JOptionPane.showMessageDialog(null, "购买成功，一路平安！");
                                            soldTicket ++;
                                            sold.setText(soldTicket.toString());
                                            try {
                                                addOrSet(thisObject, 1, false);
                                            } catch (IOException e1) {
                                                e1.printStackTrace();
                                            }
                                        }
                                        else {
                                            JOptionPane.showMessageDialog(null, "票已售罄！", "下单失败", JOptionPane.ERROR_MESSAGE);
                                        }
                                    }
                                    else {
                                        return ;
                                    }
                                }
                                else {
                                    JOptionPane.showMessageDialog(null, "起点或终点错误！", "查询失败", JOptionPane.ERROR_MESSAGE);
                                    return ;
                                }
                                break;
                            }
                        }
                    }
                }
            };
            submit.addActionListener(orderListener);
            end.addActionListener(orderListener);
            reset.removeActionListener(resetListener);
            reset.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    resetText(thisObject);
                    start.setText("");
                    end.setText("");
                    busNumber.setText("");
                }
            });
        }
    }

    public void readData(ArrayList<String[]> data, ArrayList<String> thisBus) throws IOException {
        data.clear();
        thisBus.clear();
        File f = new File("ticketInfo.txt");
        BufferedReader bufr;
        try {
            bufr = new BufferedReader(new FileReader(f));
        } catch (Exception e) {
            f.createNewFile();
            bufr = new BufferedReader(new FileReader(f));
        }
        String line;
        while ((line=bufr.readLine())!=null) {
            data.add(line.split("\\|"));
        }
        bufr.close();
    }
    public void writeData(ArrayList<String[]> data) throws IOException {
        File f = new File("ticketInfo.txt");
        BufferedWriter bufw;
        try {
            bufw = new BufferedWriter(new FileWriter(f, false));
        } catch (Exception e) {
            f.createNewFile();
            bufw = new BufferedWriter(new FileWriter(f, false));
        }
        for (String[] i: data) {
            bufw.write(i[0]);
            for (int j=1; j<i.length; j++) {
                bufw.write("|"+i[j]);
            }
            bufw.write('\n');
        }
        bufw.close();
    }
    public void submitAndReset(MainPanel object, String s) {
        ///
        object.submit = new Button(s);
        object.submit.addActionListener(object.submitListener);
        object.buttonBox.add(object.submit);
        object.buttonBox.add(Box.createHorizontalStrut(horizontalGap));
        onlyReset(object);
        ///
    }
    public void onlyReset(MainPanel object) {
        ///
        object.reset = new Button("重置");
        object.reset.addActionListener(object.resetListener);
        object.buttonBox.add(object.reset);
        object.frameBox.add(object.buttonBox);
        ///

        ///
        object.frameBox.add(Box.createVerticalStrut(borderBottom));
        ///
    }
    public void setTextDisabled(MainPanel object) {
        object.startTimeLeft.setEditable(false);
        object.startTimeRight.setEditable(false);
        object.arriveTimeLeft.setEditable(false);
        object.arriveTimeRight.setEditable(false);;
        object.passStation.setEditable(false);
        object.price.setEditable(false);
        object.total.setEditable(false);
        object.sold.setEditable(false);
        object.scrollPane.setBorder(BorderFactory.createLineBorder(new Color(184, 207, 229)));
        object.stationBox.remove(object.addStation);
        object.stationBox.remove(object.delStation);
        object.stationBox.remove(object.addDelStrut);
        object.	stationBox.remove(object.priceAddStrut);
        object.	price.removeActionListener(object.addStationListener);
        object.price.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                object.total.requestFocus();
            }
        });
    }
    public void addOrSet(MainPanel object, int cmd, boolean showTip) throws IOException {
        String busNumberText = object.busNumber.getText();
        String startTimeLeftText = object.startTimeLeft.getText();
        String startTimeRightText = object.startTimeRight.getText();
        String arriveTimeLeftText = object.arriveTimeLeft.getText();
        String arriveTimeRightText = object.arriveTimeRight.getText();
        String totalText = object.total.getText();
        String soldText = object.sold.getText();
        //JOptionPane.showMessageDialog(null, "FLAG！");
        if (busNumberText.equals("")
                ||startTimeLeftText.equals("")||startTimeRightText.equals("")
                ||arriveTimeLeftText.equals("")||arriveTimeRightText.equals("")
                ||totalText.equals("")||soldText.equals("")) {
            JOptionPane.showMessageDialog(null, "车次信息不能为空！", "修改失败", JOptionPane.ERROR_MESSAGE);
        }
        else {
            if (cmd == 0) {
                if (judgeBusNumber(object)) {
                    object.busNumber.setText("");
                    object.busNumber.requestFocus();
                    return ;
                }
                else {
                    object.thisBus.add(0, busNumberText);
                    object.thisBus.add(1, startTimeLeftText);
                    object.thisBus.add(2, startTimeRightText);
                    object.thisBus.add(3, arriveTimeLeftText);
                    object.thisBus.add(4, arriveTimeRightText);
                    object.thisBus.add(object.thisBus.size(), totalText);
                    object.thisBus.add(object.thisBus.size(), soldText);
                }
            }
            else {
                object.thisBus.set(0, busNumberText);
                object.thisBus.set(1, startTimeLeftText);
                object.thisBus.set(2, startTimeRightText);
                object.thisBus.set(3, arriveTimeLeftText);
                object.thisBus.set(4, arriveTimeRightText);
                object.thisBus.set(object.thisBus.size()-2, totalText);
                object.thisBus.set(object.thisBus.size()-1, soldText);
            }
            String[] tmp = new String[object.thisBus.size()];
            for (int i=0; i<object.thisBus.size(); i++) {
                tmp[i] = object.thisBus.get(i);
            }
            if (cmd == 0) {
                object.data.add(tmp);
            }
            else {
                boolean flag = false;
                for (int i=0; i<object.data.size(); i++) {
                    if (object.data.get(i)[0].equals(tmp[0])) {
                        object.data.set(i, tmp);
                        break;
                    }
                }
            }
            writeData(object.data);
            if (showTip) {
                if (cmd == 1) {
                    object.busNumber.requestFocus();
                }
                JOptionPane.showMessageDialog(null, "信息录入成功！");
                resetText(object.thisObject);
                object.busNumber.setText("");
                object.readDataAgain();
            }
            else {
                queryBus(object);
                object.readDataAgain();
            }
        }
    }
    public boolean judgeBusNumber(MainPanel object) {
        String busnum = object.busNumber.getText();
        for (String[] i: object.data) {
            if (i[0].equals(busnum)) {
                return true;
            }
        }
        return false;
    }
    public void resetText(MainPanel object) {
        object.startTimeLeft.setText("");
        object.startTimeRight.setText("");
        object.arriveTimeLeft.setText("");
        object.arriveTimeRight.setText("");
        object.passStation.setText("");
        object.price.setText("");
        object.info.setText("");
        object.total.setText("");
        object.sold.setText("");
    }
    public boolean queryBus(MainPanel object) {
        String busnum = object.busNumber.getText();
        boolean flag = false;
        for (String[] i: object.data) {
            if (i[0].equals(busnum)) {
                flag = true;
                object.thisBus.add(i[0]);
                object.startTimeLeft.setText(i[1]);
                object.thisBus.add(i[1]);
                object.startTimeRight.setText(i[2]);
                object.thisBus.add(i[2]);
                object.arriveTimeLeft.setText(i[3]);
                object.thisBus.add(i[3]);
                object.arriveTimeRight.setText(i[4]);
                object.thisBus.add(i[4]);
                String passed = "";
                ArrayList<String> ps = new ArrayList<String>();
                ArrayList<String> pp = new ArrayList<String>();
                int index = 5;
                for (; index<i.length; index++) {
                    object.thisBus.add(i[index]);
                    if (i[index].equals("STOP")) {
                        break;
                    }
                    if (index%2==1) {
                        ps.add(i[index]);
                        passed = passed+"经停站："+i[index]+" ,  ";
                    }
                    else {
                        pp.add(i[index]);
                        passed = passed+"始发站至此站票价："+i[index]+"元 ;\n";
                    }
                }
                object.passStation.setText("总共"+(index-5)/2+"站");
                object.price.setText("最多"+pp.get(pp.size()-1)+"元");
                object.info.setText(passed);
                index ++;
                object.total.setText(i[index]);
                object.thisBus.add(i[index]);
                index ++;
                object.sold.setText(i[index]);
                object.thisBus.add(i[index]);
            }
        }
        if (!flag) {
            resetText(object);
            JOptionPane.showMessageDialog(null, "未找到相关信息！", "查询失败", JOptionPane.ERROR_MESSAGE);
            object.thisBus.clear();
            return false;
        }
        return true;
    }
    public void addStationToThisBus(MainPanel object) {
        String thisStation = object.passStation.getText();
        String thisPrice = object.price.getText();
        if (thisStation.equals("")||thisPrice.equals("")) {
            JOptionPane.showMessageDialog(null, "经停站信息不能为空！", "添加失败", JOptionPane.ERROR_MESSAGE);
            object.passStation.requestFocus();
        }
        else {
            if (object.thisBus.size()==0) {
                object.thisBus.add("STOP");
            }
            System.out.println(object.thisBus);
            for (int index = object.thisBus.size()-1; index>=0; index--) {
                if (object.thisBus.get(index).equals("STOP")) {
                    object.thisBus.add(index, thisStation);
                    object.thisBus.add(index+1, thisPrice);
                    object.info.setText(object.info.getText()+"经停站："+thisStation+" ,  "+"始发站至此站票价："+thisPrice+"元 ;\n");
                }
            }
        }
    }
    private void removeStationFromThisBus(MainPanel object) {
        String infoText = object.info.getText();
        if (infoText.equals("")) {
            JOptionPane.showMessageDialog(null, "无经停站！", "删除失败", JOptionPane.ERROR_MESSAGE);
            object.passStation.requestFocus();
        }
        else {
            int index = object.thisBus.size()-1;
            for (; index>=0; index--) {
                if (object.thisBus.get(index).equals("STOP")) {
                    break;
                }
            }
            object.thisBus.remove(index-1);
            object.thisBus.remove(index-2);
            index = infoText.length()-1;
            if (infoText.charAt(index)=='\n') {
                index --;
            }
            for (; index>=0; index--) {
                if (infoText.charAt(index)=='\n') {
                    break;
                }
            }
            System.out.println(index);
            infoText = infoText.substring(0, index+1);
            object.info.setText(infoText);
        }
    }
}

public class TicketManager {
    public static void main(String[] args) {
        ///
        final boolean resizable = false;
        ///

        ///
        final String[] usr = {"admin"};
        final String[] pwd = {"123456"};
        ///

        ///
        final int verticalGap = 20;
        final int horizontalGap = 70;
        final int labelTextGap = 10;
        final int borderTop = 30;
        final int borderBottom = 50;
        final int borderLeft = 150;
        final int borderRight = 150;
        ///

        JFrame login = new JFrame();
        login.setVisible(true);
        login.setResizable(resizable);
        login.setSize(500, 300);
        login.setLocationRelativeTo(null);
        login.setTitle("长途汽车站票务信息管理系统");
        login.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container content = login.getContentPane();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));

        ///
        JTextField username = new JTextField();
        JPasswordField password = new JPasswordField();
        ActionListener JudgeLogIn = new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String inputUsr = username.getText();
                String inputPwd = String.valueOf(password.getPassword());
                boolean flag = false;
                for (int i=0; i<usr.length; i++) {
                    System.out.println(inputUsr+" "+inputPwd);
                    if (inputUsr.equals(usr[i]) && inputPwd.equals(pwd[i])) {
                        login.setVisible(false);
                        flag = true;
                        JOptionPane.showMessageDialog(null, inputUsr+", 欢迎回来！");

                        Manager manager;
                        try {
                            manager = new Manager(resizable);
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
                if (!flag) {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "登录失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        };
        password.addActionListener(JudgeLogIn);
        username.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                password.setText("");
                username.transferFocus();
            }
        });
        ///

        ///
        content.add(Box.createVerticalStrut(borderTop));
        ///

        JLabel loginTip = new JLabel("管理员登录");
        loginTip.setFont(new Font("Microsoft YaHei", Font.BOLD, 30));
        loginTip.setAlignmentX(Box.CENTER_ALIGNMENT);
        content.add(loginTip);

        ///
        content.add(Box.createVerticalStrut(verticalGap));
        ///

        /*
         * JPanel usernamePanel = new JPanel();
         * usernamePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         * usernamePanel.add(new JLabel("用户名："));
         * JTextField username = new JTextField();
         * usernamePanel.add(username);
         * content.add(usernamePanel);
         */

        Box usernameBox = Box.createHorizontalBox();
        usernameBox.add(Box.createHorizontalStrut(borderLeft));
        usernameBox.add(new JLabel("用户名："));
        usernameBox.add(username);
        usernameBox.add(Box.createHorizontalStrut(borderRight));
        content.add(usernameBox);

        ///
        content.add(Box.createVerticalStrut(10));
        ///

        /*
         * JPanel passwordPanel = new JPanel();
         * passwordPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
         * passwordPanel.add(new JLabel("密码："));
         * JPasswordField password = new JPasswordField();
         * passwordPanel.add(password);
         * content.add(passwordPanel);
         */

        Box passwordBox = Box.createHorizontalBox();
        passwordBox.add(Box.createHorizontalStrut(borderLeft));
        passwordBox.add(new JLabel("密    码："));
        passwordBox.add(password);
        passwordBox.add(Box.createHorizontalStrut(borderRight));
        content.add(passwordBox);

        ///
        content.add(Box.createVerticalStrut(verticalGap));
        ///e

        Box buttonBox = Box.createHorizontalBox();
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(JudgeLogIn);
        JButton closeButton = new JButton("关闭");
        closeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonBox.add(loginButton);
        buttonBox.add(Box.createHorizontalStrut(horizontalGap));
        buttonBox.add(closeButton);
        content.add(buttonBox);

        ///
        content.add(Box.createVerticalStrut(borderBottom));
        ///
    }
}

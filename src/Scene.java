import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.sound.sampled.*;
import javax.swing.*;


public class Scene extends JFrame implements Runnable {
    JPanel startbg,storybg, p1, p2, p3; //p1 : ��ư�� �׸� �г�  p2 : ���ڸ� �׸� �г� p3 : �Ϸ�� �г�
    JLabel score;
    Font font;
    JButton startBtn, open;
    ImageIcon startimg, story, background;
    Thread th = new Thread(this);    // ��ü �ð� ������
    Ingredients b1,b2, b3, b4, b5, dough, s2, s1;
    Submit submit;
    
    Clip clip; //BGM�� ��ü
    File file; //bgm ���

    //���� ������ ��� ����Ʈ�� ������ �迭
    int[] ingredient = new int[8];
    int money = 0;//���� ���� ����
    boolean current = false;
    JLabel label;
    JProgressBar timer;    // ��ü�ð� �����
    Character ch;    // ������ �մ�
    Font ft = new Font("TT���Դ�", Font.PLAIN, 12);
    Font ft2 = new Font("TT���Դ�", Font.PLAIN, 24);

    public Scene() {
        //BGM �ҷ�����
        file = new File("./bgm.wav");
        if(!file.exists()) {
            System.out.println("������ ã�� �� �����ϴ�.");
        }

        //���� ȭ��
        startimg = new ImageIcon("start_bg.jpg");
        startbg = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(startimg.getImage(), 0, 0, null);
                setOpaque(false);
                super.paintComponents(g);
            }
        };
        startbg.setLayout(null);
        startbg.setBounds(0, 0, 740, 680);
		
        startBtn = new JButton("START");
        startBtn.setBounds(320, 500, 100, 50);
        startBtn.setFont(ft);
        startBtn.setBackground(Color.WHITE);
        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                startbg.setVisible(false);
                storybg.setVisible(true);
            }
        });
        startbg.add(startBtn);

        //���丮 ȭ��
        story = new ImageIcon("story.jpg");
        storybg = new JPanel() {
            public void paintComponent(Graphics g) {
                g.drawImage(story.getImage(), 0, 0, null);
                setOpaque(false);
                super.paintComponents(g);
            }
        };
        storybg.setLayout(null);
        storybg.setBounds(0, 0, 740, 680);
        label = new JLabel("������ �����Ϸ��� OPEN��ư�� ��������.");
        open = new JButton("OPEN.....");
        label.setBounds(250, 500, 300, 50);
        open.setBounds(320, 550, 100, 50);
       
        //open ��ư ������ ���� ���� ȭ������
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                storybg.setVisible(false);
                p1.setVisible(true);
                p2.setVisible(true);


                ch = new Character();    // ĳ���� ������ ����
                ch.setBounds(20, 0, 725, 680);// ĳ���� ��ġ
                p2.add(ch);

                th.start();//��ü�ð� ������ ����

                try(AudioInputStream stream = AudioSystem.getAudioInputStream(file)){
                    clip = AudioSystem.getClip();
                    clip.open(stream);
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                } catch(IOException | LineUnavailableException | UnsupportedAudioFileException err) {
                    err.printStackTrace();
                }

            }
        });

        label.setFont(ft);
        open.setFont(ft);
        open.setBackground(Color.white);
        storybg.add(label);
        storybg.add(open);
        storybg.setVisible(false);
        //���� ȭ��
        background = new ImageIcon("bg.png");

        p1 = new JPanel() {

            public void paintComponent(Graphics g) {

                setOpaque(false);
                super.paintComponents(g);
            }
        };
        
        p2 = new JPanel() {
            public void paintComponent(Graphics g) {

                //��ü ȭ���� ����� ����
                g.clearRect(0, 0, 740, 680);

                //��ü ȭ���� ������ ������ ��ü ȭ���� �ٽ� �׷����մϴ�.
                ImageIcon background = new ImageIcon("bg.png");
                g.drawImage(background.getImage(), 0, 0, null);

                ImageIcon[] imgs = new ImageIcon[8];
                imgs[0] = new ImageIcon("dough.png");
                imgs[1] = new ImageIcon("pepperoni.png");
                imgs[2] = new ImageIcon("olive.png");
                imgs[3] = new ImageIcon("mushroom.png");
                imgs[4] = new ImageIcon("pepper.png");
                imgs[5] = new ImageIcon("bacon.png");
                imgs[6] = new ImageIcon("kecup.png");
                imgs[7] = new ImageIcon("mustard.png");

                //���õ� ���鸸 �׸��� ����
                for (int i = 0; i < 8; i++) {
                    if (ingredient[i] == 1) {
                        g.drawImage(imgs[i].getImage(), 0, 0, null);
                    }
                }
                setOpaque(false);
                super.paintComponents(g);
            }
        };

        // ��ư ����
        p1.setLayout(null);
        p2.setLayout(null);
        p1.setBounds(0, 0, 740, 680);
        p2.setBounds(0, 0, 740, 680);

        dough= new Ingredients("����",0, 650, 120, 28,ingredient,0,p1,p2);
        b1= new Ingredients("���۷δ�",52, 499, 98, 27,ingredient,1,p1,p2);
        b2= new Ingredients("�ø���",180, 505, 98, 27,ingredient,2,p1,p2);
        b3= new Ingredients("�����",308, 505, 100, 28,ingredient,3,p1,p2);
        b4= new Ingredients("�Ǹ�",435, 505, 95, 28,ingredient,4,p1,p2);
        b5= new Ingredients("������",563, 505, 104, 27,ingredient,5,p1,p2);
        s1= new Ingredients("����",613, 560, 89, 39,ingredient,6,p1,p2);
        s1= new Ingredients("�ӽ�Ÿ��",633, 620, 86, 37,ingredient,7,p1,p2);
        
        submit = new Submit(p1);
        submit.jb.addActionListener(new ActionListener() {

            //���� �ϼ��� ingredient �迭�� ���� ȭ���� ����
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Arrays.equals(ingredient, ch.order)) {    // �ֹ���� ���������
                    System.out.println("���");
                    p2.repaint();
                    ch.stop = true;// ���� ĳ���� ������ ����
                    ch.setVisible(false);//���� ĳ���� �Ⱥ��̰�
                    ch = new Character();    // ���ο� ĳ���� ������ ����
                    ch.setBounds(20, 0, 725, 680);// ĳ���� ��ġ
                    p2.add(ch);
                    money += 1000;
                    score.setText(Integer.toString(money));
                    System.out.println("money : " + money);
                } else {    // �ֹ��� �ٸ� ���ڸ� ���� ���
                    System.out.println("����");
                    p2.repaint();
                    ch.stop = true;// ���� ĳ���� ������ ����
                    ch.setVisible(false);//���� ĳ���� �Ⱥ��̰�
                    ch = new Character();    // ���ο� ĳ���� ������ ����
                    ch.setBounds(20, 0, 725, 680);// ĳ���� ��ġ
                    p2.add(ch);
                    money -= 1000;
                    score.setText(Integer.toString(money));
                    System.out.println("money : " + money);
                }
                for (int i = 0; i < 8; i++) {
                    System.out.print(ingredient[i] + ", ");
                    ingredient[i] = 0;
                }
                System.out.println("");
            }
        });


        //������
        score = new JLabel();
        score.setBounds(580, -20, 1000, 100);
        score.setText(Integer.toString(money));

        font = new Font("TTTogether", 4, 45);
        score.setFont(font);
        p2.add(score);

        add(startbg);
        add(storybg);

        timer = new JProgressBar(0, 60);
        timer.setBackground(Color.white);
        timer.setForeground(new Color(76,159,225));
        timer.setBounds(0, 0, 565, 60);
        timer.setVisible(false);
        p2.add(timer);

        //���â �г�
        p3 = new JPanel();
        p3.setLayout(null);
        p3.setBounds(0, 0, 740, 680);

        add(p1);
        p1.setVisible(false);
        add(p2);
        p2.setFont(ft2);
        p2.setVisible(false);
        add(p3);
        p3.setVisible(false);
    }


    public void run() {    // ��ü �ð� ������
        timer.setValue(60);
        timer.setVisible(true);
        for (int i = 60; i >= 0; i--) {
            //���� ĳ���� �������� while���� �ѹ��� ���Ҵٸ�, �� �մ��� ���ڸ� ���� ���ϰ� �Ѿ�ٸ� �г�Ƽ
            //�� �� Ȯ���Ѵ�.
            if(ch.repeated) {
                money -= 1000;
                score.setText(Integer.toString(money));
                System.out.println("money : " + money);
            }

            timer.setValue(i);
            try {
                Thread.sleep(1000); // 1��
            } catch (Exception ex) { }

            if (i == 0) {   //gameover
            	 clip.stop();//���� ����
                System.out.println("���� ��");
                ch.stop = true;//ĳ���� ������ ���߱�
                p1.setVisible(false);
                p2.setVisible(false);
                p3.setVisible(true);
                if (money > 5000) {
                    JLabel l1 = new JLabel("Success");
                    l1.setBounds(300, 250, 1000, 100);
                    l1.setFont(new Font("TT���Դ�", Font.PLAIN, 24));
                    JLabel l2 = new JLabel("����: " + money);
                    l2.setBounds(300, 290, 1000, 100);
                    l2.setFont(new Font("TT���Դ�", Font.PLAIN, 24));
                    p3.add(l1);
                    p3.add(l2);
                } else {
                    JLabel l1 = new JLabel("Game over");
                    l1.setBounds(290, 250, 1000, 100);
                    l1.setFont(new Font("TT���Դ�", Font.PLAIN, 24));
                    JLabel l2 = new JLabel("����: " + money);
                    l2.setBounds(290, 290, 1000, 100);
                    l2.setFont(new Font("TT���Դ�", Font.PLAIN, 24));

                    p3.add(l1);
                    p3.add(l2);
                }
            }
        }
    }
}



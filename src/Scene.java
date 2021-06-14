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
    JPanel startbg,storybg, p1, p2, p3; //p1 : 버튼을 그릴 패널  p2 : 피자를 그릴 패널 p3 : 완료용 패널
    JLabel score;
    Font font;
    JButton startBtn, open;
    ImageIcon startimg, story, background;
    Thread th = new Thread(this);    // 전체 시간 스레드
    Ingredients b1,b2, b3, b4, b5, dough, s2, s1;
    Submit submit;
    
    Clip clip; //BGM용 객체
    File file; //bgm 경로

    //만든 피자의 재료 리스트를 저장할 배열
    int[] ingredient = new int[8];
    int money = 0;//성공 기준 점수
    boolean current = false;
    JLabel label;
    JProgressBar timer;    // 전체시간 진행바
    Character ch;    // 눈송이 손님
    Font ft = new Font("TT투게더", Font.PLAIN, 12);
    Font ft2 = new Font("TT투게더", Font.PLAIN, 24);

    public Scene() {
        //BGM 불러오기
        file = new File("./bgm.wav");
        if(!file.exists()) {
            System.out.println("파일을 찾을 수 없습니다.");
        }

        //시작 화면
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

        //스토리 화면
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
        label = new JLabel("게임을 시작하려면 OPEN버튼을 누르세요.");
        open = new JButton("OPEN.....");
        label.setBounds(250, 500, 300, 50);
        open.setBounds(320, 550, 100, 50);
       
        //open 버튼 누르면 게임 시작 화면으로
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                storybg.setVisible(false);
                p1.setVisible(true);
                p2.setVisible(true);


                ch = new Character();    // 캐릭터 스레드 시작
                ch.setBounds(20, 0, 725, 680);// 캐릭터 위치
                p2.add(ch);

                th.start();//전체시간 스레드 시작

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
        //게임 화면
        background = new ImageIcon("bg.png");

        p1 = new JPanel() {

            public void paintComponent(Graphics g) {

                setOpaque(false);
                super.paintComponents(g);
            }
        };
        
        p2 = new JPanel() {
            public void paintComponent(Graphics g) {

                //전체 화면을 지우는 과정
                g.clearRect(0, 0, 740, 680);

                //전체 화면을 지웠기 때문에 전체 화면을 다시 그려야합니다.
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

                //선택된 재료들만 그리는 과정
                for (int i = 0; i < 8; i++) {
                    if (ingredient[i] == 1) {
                        g.drawImage(imgs[i].getImage(), 0, 0, null);
                    }
                }
                setOpaque(false);
                super.paintComponents(g);
            }
        };

        // 버튼 설정
        p1.setLayout(null);
        p2.setLayout(null);
        p1.setBounds(0, 0, 740, 680);
        p2.setBounds(0, 0, 740, 680);

        dough= new Ingredients("도우",0, 650, 120, 28,ingredient,0,p1,p2);
        b1= new Ingredients("페퍼로니",52, 499, 98, 27,ingredient,1,p1,p2);
        b2= new Ingredients("올리브",180, 505, 98, 27,ingredient,2,p1,p2);
        b3= new Ingredients("양송이",308, 505, 100, 28,ingredient,3,p1,p2);
        b4= new Ingredients("피망",435, 505, 95, 28,ingredient,4,p1,p2);
        b5= new Ingredients("베이컨",563, 505, 104, 27,ingredient,5,p1,p2);
        s1= new Ingredients("케찹",613, 560, 89, 39,ingredient,6,p1,p2);
        s1= new Ingredients("머스타드",633, 620, 86, 37,ingredient,7,p1,p2);
        
        submit = new Submit(p1);
        submit.jb.addActionListener(new ActionListener() {

            //피자 완성시 ingredient 배열과 피자 화면을 리셋
            @Override
            public void actionPerformed(ActionEvent e) {

                if (Arrays.equals(ingredient, ch.order)) {    // 주문대로 만들었으면
                    System.out.println("통과");
                    p2.repaint();
                    ch.stop = true;// 기존 캐릭터 스레드 멈춤
                    ch.setVisible(false);//기존 캐릭터 안보이게
                    ch = new Character();    // 새로운 캐릭터 스레드 시작
                    ch.setBounds(20, 0, 725, 680);// 캐릭터 위치
                    p2.add(ch);
                    money += 1000;
                    score.setText(Integer.toString(money));
                    System.out.println("money : " + money);
                } else {    // 주문과 다른 피자를 줬을 경우
                    System.out.println("실패");
                    p2.repaint();
                    ch.stop = true;// 기존 캐릭터 스레드 멈춤
                    ch.setVisible(false);//기존 캐릭터 안보이게
                    ch = new Character();    // 새로운 캐릭터 스레드 시작
                    ch.setBounds(20, 0, 725, 680);// 캐릭터 위치
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


        //점수판
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

        //결과창 패널
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


    public void run() {    // 전체 시간 스레드
        timer.setValue(60);
        timer.setVisible(true);
        for (int i = 60; i >= 0; i--) {
            //만약 캐릭터 스레드의 while문이 한바퀴 돌았다면, 즉 손님이 피자를 받지 못하고 넘어갔다면 패널티
            //매 초 확인한다.
            if(ch.repeated) {
                money -= 1000;
                score.setText(Integer.toString(money));
                System.out.println("money : " + money);
            }

            timer.setValue(i);
            try {
                Thread.sleep(1000); // 1초
            } catch (Exception ex) { }

            if (i == 0) {   //gameover
            	 clip.stop();//음악 중지
                System.out.println("게임 끝");
                ch.stop = true;//캐릭터 스레드 멈추기
                p1.setVisible(false);
                p2.setVisible(false);
                p3.setVisible(true);
                if (money > 5000) {
                    JLabel l1 = new JLabel("Success");
                    l1.setBounds(300, 250, 1000, 100);
                    l1.setFont(new Font("TT투게더", Font.PLAIN, 24));
                    JLabel l2 = new JLabel("점수: " + money);
                    l2.setBounds(300, 290, 1000, 100);
                    l2.setFont(new Font("TT투게더", Font.PLAIN, 24));
                    p3.add(l1);
                    p3.add(l2);
                } else {
                    JLabel l1 = new JLabel("Game over");
                    l1.setBounds(290, 250, 1000, 100);
                    l1.setFont(new Font("TT투게더", Font.PLAIN, 24));
                    JLabel l2 = new JLabel("점수: " + money);
                    l2.setBounds(290, 290, 1000, 100);
                    l2.setFont(new Font("TT투게더", Font.PLAIN, 24));

                    p3.add(l1);
                    p3.add(l2);
                }
            }
        }
    }
}



import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Character extends JLabel implements Runnable {
	JLabel label = new JLabel();
	Image guest[] = new Image[9];
	JLabel lorder = new JLabel();
	boolean ordercheck, repeated;
	int[] order;
	boolean stop=false;

	public Character() {
		for(int i=0 ;i <guest.length;i++) {	// 캐릭터 이미지 배열
			guest[i] = new ImageIcon(i+".png").getImage();
		}

		lorder.setBounds(135, 65, 490, 200);	// 주문 label 위치 지정
		lorder.setFont(new Font("TTTogether",Font.PLAIN,13));	// 글꼴
		add(lorder);

		repeated = false;

		Thread t = new Thread(this);	// 스레드 시작
		t.start();
	}


	public void run() {
		while(!stop) {
			int random = (int)(Math.random() * 3) * 3; // 0,3,6
			setIcon(new ImageIcon(guest[random]));	// 랜덤하게 캐릭터 이미지 지정
			order(); // 랜덤으로 주문 생성
			for(int i=5;i>0;i--) {	// 인내심 5초
				if(i == 4) { repeated = false; }	// Scene 클래스에서 repeated이 true임을 1초만 확인할 수 있도록 함.
				if(i == 3) {	// 무표정
					random++;
					setIcon(new ImageIcon(guest[random]));
				}
				if(i == 1) {	// 화난표정
					random++;
					setIcon(new ImageIcon(guest[random]));
				}
				try{
					Thread.sleep(1000); //1초
				}catch(InterruptedException e) {};
			}
			repeated = true; //반복된다는 뜻은 피자를 제때 주지 못했다는 뜻. 반복되었음을 체크해둠.
		}
	}

	public void order() {	// 랜덤하게 주문
		String ing = "";	// 재료
		String sau = "";	// 소스
		order = new int[8];	// 재료를 선택하는 배열
		order[0] = 1; // 기본 재료 세팅

		Random rand = new Random();
		for(int i = 1 ; i < 6 ; i++) {	// 페퍼로니, 올리브, 양송이, 피망, 베이컨
			order[rand.nextInt(5)+1] = 1;	// 주문하는 것 1, 주문 하지 않는 것 0
		}

		order[6] = (int)(Math.random() * 2);
		order[7] = (int)(Math.random() * 2);

		for(int i=0;i<order.length;i++) {
			if(!(order[i] == 0)) {
				switch(i) {
					case 1:
						ing += "페퍼로니 "; break;
					case 2:
						ing += "올리브 "; break;
					case 3:
						ing += "양송이 "; break;
					case 4:
						ing += "피망 "; break;
					case 5:
						ing += "베이컨 "; break;
					case 6:
						sau += "케찹 "; break;
					case 7:
						sau += "머스타드 "; break;
				}
			}
		}

		ing += "<br>들어간 피자";
		sau += " 뿌려주세요";
		if ((order[6] == 1) || (order[7]==1))
			lorder.setText("<html>"+ing+"에 <br>"+sau+"<html>");
		else
			lorder.setText("<html>"+ing+"주세요<html>");
	}
}

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
		for(int i=0 ;i <guest.length;i++) {	// ĳ���� �̹��� �迭
			guest[i] = new ImageIcon(i+".png").getImage();
		}

		lorder.setBounds(135, 65, 490, 200);	// �ֹ� label ��ġ ����
		lorder.setFont(new Font("TTTogether",Font.PLAIN,13));	// �۲�
		add(lorder);

		repeated = false;

		Thread t = new Thread(this);	// ������ ����
		t.start();
	}


	public void run() {
		while(!stop) {
			int random = (int)(Math.random() * 3) * 3; // 0,3,6
			setIcon(new ImageIcon(guest[random]));	// �����ϰ� ĳ���� �̹��� ����
			order(); // �������� �ֹ� ����
			for(int i=5;i>0;i--) {	// �γ��� 5��
				if(i == 4) { repeated = false; }	// Scene Ŭ�������� repeated�� true���� 1�ʸ� Ȯ���� �� �ֵ��� ��.
				if(i == 3) {	// ��ǥ��
					random++;
					setIcon(new ImageIcon(guest[random]));
				}
				if(i == 1) {	// ȭ��ǥ��
					random++;
					setIcon(new ImageIcon(guest[random]));
				}
				try{
					Thread.sleep(1000); //1��
				}catch(InterruptedException e) {};
			}
			repeated = true; //�ݺ��ȴٴ� ���� ���ڸ� ���� ���� ���ߴٴ� ��. �ݺ��Ǿ����� üũ�ص�.
		}
	}

	public void order() {	// �����ϰ� �ֹ�
		String ing = "";	// ���
		String sau = "";	// �ҽ�
		order = new int[8];	// ��Ḧ �����ϴ� �迭
		order[0] = 1; // �⺻ ��� ����

		Random rand = new Random();
		for(int i = 1 ; i < 6 ; i++) {	// ���۷δ�, �ø���, �����, �Ǹ�, ������
			order[rand.nextInt(5)+1] = 1;	// �ֹ��ϴ� �� 1, �ֹ� ���� �ʴ� �� 0
		}

		order[6] = (int)(Math.random() * 2);
		order[7] = (int)(Math.random() * 2);

		for(int i=0;i<order.length;i++) {
			if(!(order[i] == 0)) {
				switch(i) {
					case 1:
						ing += "���۷δ� "; break;
					case 2:
						ing += "�ø��� "; break;
					case 3:
						ing += "����� "; break;
					case 4:
						ing += "�Ǹ� "; break;
					case 5:
						ing += "������ "; break;
					case 6:
						sau += "���� "; break;
					case 7:
						sau += "�ӽ�Ÿ�� "; break;
				}
			}
		}

		ing += "<br>�� ����";
		sau += " �ѷ��ּ���";
		if ((order[6] == 1) || (order[7]==1))
			lorder.setText("<html>"+ing+"�� <br>"+sau+"<html>");
		else
			lorder.setText("<html>"+ing+"�ּ���<html>");
	}
}

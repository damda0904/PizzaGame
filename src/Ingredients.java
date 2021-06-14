import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Ingredients implements ActionListener {
	Font ft = new Font("TT≈ı∞‘¥ı", Font.PLAIN, 12);
	JButton jb;
	int x,y,w,h;
	String name;
	int[] ingredient;
	int num;
	JPanel jp,jp2;
	
	public Ingredients(String s, int a, int b, int c, int d, int[] e, int n, JPanel _jp,JPanel _jp2){
		name=s;
		x=a;
		y=b;
		w=c;
		h=d;
		ingredient = e;
		num = n;
		jp = _jp;
		jp2 = _jp2;
		
		jb = new JButton(name);
		jb.setBounds(x, y, w, h);
	    jb.setBackground(Color.LIGHT_GRAY);
	    jb.setBorder(BorderFactory.createLineBorder(Color.black));
	    jb.setFont(ft);
	    jb.addActionListener(this);
	    jp.add(jb);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		 if (e.getSource() == jb) {
	            if (ingredient[num] == 0) {
	                ingredient[num] = 1;
	            } else {
	                ingredient[num] = 0;
	            }
	            jp2.repaint();
		 }


	}
}

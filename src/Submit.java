import java.awt.Color;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Submit {
	Font ft = new Font("TT���Դ�", Font.PLAIN, 12);
	JButton jb;
	JPanel jp,jp2;

	
	public Submit(JPanel _jp){
		
		jp = _jp;
		
		jb = new JButton("�ϼ�");
        jb.setBounds(310, 370, 100, 30);
        jb.setBackground(Color.white);
        jb.setBorder(BorderFactory.createLineBorder(Color.black));
        jb.setFont(ft);
        jp.add(jb);
	}

}

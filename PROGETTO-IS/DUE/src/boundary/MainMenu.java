package boundary;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;

import database.SettoreDAO;
import entity.Settore;
import exception.DAOException;
import exception.DBConnectionException;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.JComboBox;
import com.toedter.calendar.JCalendar;
import com.toedter.calendar.JDateChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu {

	private JFrame frmDue;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainMenu window = new MainMenu();
					window.frmDue.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainMenu() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmDue = new JFrame();
		frmDue.setResizable(false);
		frmDue.getContentPane().setBackground(new Color(44, 62, 80));
		frmDue.getContentPane().setLayout(null);
		
		/*JLabel lblNewLabel = new JLabel("");
		ImageIcon icon = new ImageIcon("C:\\Users\\domenico\\OneDrive\\Desktop\\JavaDUE\\Progetto-DUE\\PROGETTO-IS\\DUE\\resources\\camp2.jpg");
		lblNewLabel.setBounds(-31, -57, 807, 347);
		lblNewLabel.setIcon(new ImageIcon(icon.getImage().getScaledInstance(lblNewLabel.getBounds().width, lblNewLabel.getBounds().height,  java.awt.Image.SCALE_SMOOTH)));
		frmDue.getContentPane().add(lblNewLabel);*/
		
		JButton btnNewButton = new JButton("VERIFICA LA DISPONIBILITÀ");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//NomeFinestra2 f = new NomeFinestra2();
				//f.show();
			}
		});
		btnNewButton.setBackground(new Color(39, 174, 96));
		btnNewButton.setForeground(new Color(255, 255, 255));
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 16));
		btnNewButton.setBounds(195, 388, 381, 44);
		frmDue.getContentPane().add(btnNewButton);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setFont(new Font("Arial", Font.PLAIN, 16));
		comboBox.setBounds(195, 300, 381, 44);
		try {
			for(Settore s : SettoreDAO.getSettori()) {
				comboBox.addItem(s.getTipoSettore() + " - " + s.getCosto() + " €");
			}
		} catch (DAOException e) {
			e.printStackTrace();
		} catch (DBConnectionException e) {
			e.printStackTrace();
		}
		frmDue.getContentPane().add(comboBox);
		
		JDateChooser dateChooser = new JDateChooser();
		dateChooser.setBounds(195, 246, 153, 19);
		frmDue.getContentPane().add(dateChooser);
		
		JDateChooser dateChooser_1 = new JDateChooser();
		dateChooser_1.setBounds(423, 246, 153, 19);
		frmDue.getContentPane().add(dateChooser_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(39, 174, 96));
		panel.setBounds(0, 0, 775, 74);
		frmDue.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("LOGO");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 12));
		lblNewLabel.setBounds(23, 10, 68, 39);
		panel.add(lblNewLabel);
		
		JMenu mnNewMenu = new JMenu("New menu");
		mnNewMenu.setBounds(610, 23, 115, 26);
		panel.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem);
		
		JPopupMenu popupMenu = new JPopupMenu();
		addPopup(mntmNewMenuItem, popupMenu);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("New menu item");
		mnNewMenu.add(mntmNewMenuItem_3);
		frmDue.setTitle("DUE");
		frmDue.setBounds(100, 100, 789, 528);
		frmDue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}

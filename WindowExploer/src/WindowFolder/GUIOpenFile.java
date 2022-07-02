package WindowFolder;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.SystemColor;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.spec.ECFieldF2m;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.SwingConstants;

public class GUIOpenFile extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JTextArea textArea = new JTextArea();
	
	private JTextField currentpath;
	public String data = "";
	

	
	public GUIOpenFile(String file) {
		setTitle("Open");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 734, 448);
		contentPane = new JPanel();
		contentPane.setBackground(Color.DARK_GRAY);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel title = new JLabel("OPEN FILE");
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setForeground(Color.WHITE);
		title.setFont(new Font("Tahoma", Font.BOLD, 17));
		title.setBounds(199, 10, 313, 34);
		contentPane.add(title);
		
		
		textArea.setForeground(Color.BLACK);
		textArea.setBounds(10, 92, 700, 276);
		contentPane.add(textArea);
		
		JLabel lblNewLabel_3 = new JLabel("Content:");
		lblNewLabel_3.setForeground(Color.WHITE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNewLabel_3.setBounds(253, 54, 66, 21);
		contentPane.add(lblNewLabel_3);
		
		currentpath = new JTextField();
		currentpath.setForeground(Color.BLACK);
		currentpath.setBounds(352, 54, 122, 19);
		contentPane.add(currentpath);
		currentpath.setColumns(10);
		
		JButton savefile = new JButton("Save");
		savefile.setForeground(Color.BLACK);
		savefile.setBounds(253, 378, 85, 21);
		contentPane.add(savefile);
		
		JButton btnNewButton = new JButton("Save as");
		btnNewButton.setForeground(Color.BLACK);
		btnNewButton.addActionListener(this);
		btnNewButton.setBounds(352, 378, 85, 21);
		contentPane.add(btnNewButton);
		savefile.addActionListener(this);
		
		Open(file);
		data = textArea.getText();
		setLocationRelativeTo(null);
		setVisible(true);
	}
	

	public void Open(String name) {
		try {
			FileInputStream file = new FileInputStream(name);
			BufferedInputStream b = new BufferedInputStream(file);    
			int ch = 0;
			String conten = "";
			while((ch = b.read()) != -1) {
				conten += (char)ch;
			}
			textArea.append(conten);
			currentpath.setText(name);
		} catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(rootPane, "Not exist file");
		}
	}
	
	public void Save(String file_save) {
		
			if (textArea.getText().equals("")) {
				JOptionPane.showMessageDialog(rootPane, "Have not data!");
			}else {
				try {
					FileWriter fileWriter = new FileWriter(file_save);
					
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					PrintWriter printWriter = new PrintWriter(bufferedWriter);
					
					printWriter.println(textArea.getText());
					printWriter.close();
					JOptionPane.showMessageDialog(rootPane, "Save file success!");
				} catch (Exception e) {
					// TODO: handle exception
					JOptionPane.showMessageDialog(rootPane, "Save file fail!");
					
				}
			}
			
		
		
	}
	
	public void SaveAs(String namefile) throws IOException {
		
		
		
		if (textArea.getText().equals("")) {
			JOptionPane.showMessageDialog(rootPane, "Not have data !");
		}else {
			File file = new File(namefile);
			if (!file.exists()) {
				file.createNewFile();
				try {
					FileWriter fileWriter = new FileWriter(file);
					
					BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
					PrintWriter printWriter = new PrintWriter(bufferedWriter);
					
					printWriter.println(textArea.getText());
					printWriter.close();
					JOptionPane.showMessageDialog(rootPane, "Save as success!");
				} catch (Exception e){
					// TODO: handle exception
					JOptionPane.showMessageDialog(rootPane, "Save as fail!");
				}
			}else {
				JOptionPane.showMessageDialog(rootPane, "File had exists!");
			}
		}
	}

	
	@Override
	public void actionPerformed(ActionEvent e){
		// TODO Auto-generated method stub
		
		if (e.getActionCommand().equals("Save")){
			
				Save(currentpath.getText());
		}
		if (e.getActionCommand().equals("Save as")){
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setCurrentDirectory(new File("\\"));
			int retur = jFileChooser.showSaveDialog(this);
			if (retur == JFileChooser.APPROVE_OPTION) {
				File file = jFileChooser.getSelectedFile();
				try {
					SaveAs(file.toString());
				} catch (IOException e1) {
					
					e1.printStackTrace();
				}
			}
			
		}
	
		
	}
}


package WindowFolder;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import java.awt.GridLayout;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JScrollPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.SystemColor;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;

public class WindowExplorer extends JFrame {

	/**
	 * 
	 */
	
	private String classify = "";
	private int row;
	private String name;

	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JTextField txtpath;

	private JTable table;
	public DefaultTableModel model = new DefaultTableModel(0, 0);
	
	public DefaultTreeModel treemodel; 
	public DefaultMutableTreeNode root; 
	public File currentFile;
	

	public String currentpath = "";
	public String Filename = "";
	public String destinationpath = "";
	public String CurrentFolder = "D:\\";
	

	public String COLNAME[] = {"Name" ,"Type"};
	
	
	public WindowExplorer() {
		setTitle("Window Explorer");
		setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		setBounds(100, 100, 854, 442);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		this.setSize(814, 700);
		setLocationRelativeTo(null);
		contentPane.setLayout(null);
		
		

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(5, 34, 180, 593);
		contentPane.add(panel_1);
		panel_1.setLayout(null);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 180, 593);
		panel_1.add(scrollPane_1);

		JTree tree = new JTree();
		tree.setForeground(Color.BLACK);
		tree.setBackground(Color.WHITE);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		scrollPane_1.setViewportView(tree);

		root = new DefaultMutableTreeNode("This PC");
		File diskofD = new File("D:\\");
		
		DefaultMutableTreeNode DiskofD = new DefaultMutableTreeNode("D:\\");

		createChildren(diskofD, DiskofD);
		
		root.add(DiskofD);

		treemodel = new DefaultTreeModel(root);
		tree.setModel(treemodel);
		
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer)tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 627, 793, 31);
		panel_2.setBackground(Color.DARK_GRAY);
		panel_2.setForeground(Color.WHITE);
		contentPane.add(panel_2);

		JLabel lbname = new JLabel("Name");
		lbname.setForeground(Color.WHITE);
		
		JButton btnRename = new JButton("Rename");
		btnRename.setForeground(Color.BLACK);
		btnRename.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				String newname = JOptionPane.showInputDialog("Input new name");

				if (newname.equals("")) {
					JOptionPane.showConfirmDialog(null, "You need type your new name!");
				} else {

					String nameofFile = txtpath.getText() + "\\" + lbname.getText();

					String newpathname = txtpath.getText() + "\\" + newname;
					File file = new File(nameofFile);
					boolean renameto = file.renameTo(new File(newpathname));
					if (renameto) {
						JOptionPane.showConfirmDialog(null, "Rename success");
					} else
						JOptionPane.showConfirmDialog(null, "Rename false");
				}
			}
		});
		
		
		JButton btnCopy = new JButton("Copy");
		btnCopy.setForeground(Color.BLACK);
		btnCopy.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				classify = "Copy";

				currentpath = txtpath.getText() + "\\" + lbname.getText();

				Filename = lbname.getText();

				JOptionPane.showConfirmDialog(null, "Your file you want to copy is :" + lbname.getText());
				
				
			}
		});
		
		JButton btnPaste = new JButton("Paste");
		btnPaste.setForeground(Color.BLACK);
		btnPaste.addMouseListener(new MouseAdapter() { 
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				String newpath = txtpath.getText() + "\\" + Filename;
				File file_old = new File(currentpath);
				File file_new = new File(newpath);
				try {
					if (classify.equals("Copy")){
						
						
						copyFile(file_old, file_new);
						
					}else if (classify.equals("Cut")) {
						copyFile(file_old, file_new);
						deleteFile(currentpath);
						JOptionPane.showMessageDialog(null, "Cut success!");
					}
					
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		});
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setForeground(Color.BLACK);
		btnDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent m) {
				
				String filedelete = txtpath.getText() + "\\" +  lbname.getText();
			
				try {
					deleteFile(filedelete);
					JOptionPane.showConfirmDialog(null, "Do you want delete file?");
					
				} catch (Exception e) {
					
					e.printStackTrace();
				}
			}
		});
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		panel_2.add(lbname);
		panel_2.add(btnPaste);
		
		JButton btnCut = new JButton("Cut");
		btnCut.setForeground(Color.BLACK);
		panel_2.add(btnCut); 
		btnCut.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				classify = "Cut";
				
				currentpath = txtpath.getText() + "\\" + lbname.getText();
				
				Filename = lbname.getText();
				
				JOptionPane.showConfirmDialog(null, "Your file you want to cut is :" + lbname.getText());
				
				
			}
		});
		panel_2.add(btnRename);
		panel_2.add(btnCopy);
		panel_2.add(btnDelete);
		
		
		
		JButton createfile = new JButton("New file");
		createfile.setForeground(Color.BLACK);
		panel_2.add(createfile);
		createfile.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				String file_name = JOptionPane.showInputDialog("Enter new file name!");
				
				String path = txtpath.getText() + "\\" +  file_name;
				
				
				File filenew = new File(path + file_name);
				
				try {
					if (filenew.createNewFile()) {
						JOptionPane.showMessageDialog(null, "Create success!");
					}else {
						JOptionPane.showMessageDialog(null, "Create file fail!");
					}
					
				} catch (IOException e1) {
					
					e1.printStackTrace(); 
				}
				
			
			} 
		} );
		
		JButton openfile = new JButton("Open");
		openfile.setForeground(Color.BLACK);
		panel_2.add(openfile);
		
		
		openfile.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				 
				if (row == -1) {
					JOptionPane.showMessageDialog(null, "Please choose file");	
				}else {
					String path = txtpath.getText() + "\\" + lbname.getText();
					
					GUIOpenFile gui  = new GUIOpenFile(path);
					
				}
			}
		});
		
		JButton searchbtn = new JButton("Search");
		panel_2.add(searchbtn);
		searchbtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (row == -1) {
					JOptionPane.showMessageDialog(rootPane, "Please choose a folder!");
					
				}else {
					File file = new File(txtpath.getText());
					if (file.isDirectory()) {
						String[] list = file.list(new FilenameFilter() {
							
							@Override
							public boolean accept(File dir, String name) {
								// TODO Auto-generated method stub
								return name.endsWith(".docx"); 
							}
						});
						
						if (list.length == 0) {
							JOptionPane.showMessageDialog(rootPane, "No file");
						}
						
						if (model.getRowCount() > 0) {
							for (int i = model.getRowCount() - 1; i > -1; i--) {
								model.removeRow(i);
							}
						}
						
						for (String files : list) {
							StringBuffer temp = new StringBuffer().append(files);
							String namefile = temp.toString();
							
							model.addRow(new Object[] {namefile, ".txt"});
						}
					}else {
						JOptionPane.showMessageDialog(rootPane,"It's not folder");
					}
					
				}
				
				
			}
		} );	
		

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 793, 29);
		panel.setBackground(Color.DARK_GRAY);
		contentPane.add(panel);
		txtpath = new JTextField();
		txtpath.setForeground(Color.BLACK);
		panel.add(txtpath);
		txtpath.setColumns(40);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(185, 34, 612, 593);
		contentPane.add(scrollPane);

		table = new JTable();
		table.setForeground(Color.BLACK);

		
		table.setShowGrid(false);
		table.setAutoCreateRowSorter(true);
	
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				JTable table = (JTable)e.getSource();
				row = table.getSelectedRow();
				String name = table.getModel().getValueAt(row, 0).toString();
				
				lbname.setText(name);

				if (e.getClickCount() == 2) {
					CurrentFolder = txtpath.getText() + "\\" + name;

					File file = new File(CurrentFolder);
					if (file.isDirectory()) {
						model = CreateData(model, CurrentFolder);
						txtpath.setText(CurrentFolder);

					}

				}
			}
		});
		scrollPane.setViewportView(table);
		model.setColumnIdentifiers(COLNAME);
	
		tree.addTreeSelectionListener(new TreeSelectionListener() {

			@Override
			public void valueChanged(TreeSelectionEvent e) {
				// TODO Auto-generated method stub
				DefaultMutableTreeNode nodeselected = (DefaultMutableTreeNode)e.getPath().getLastPathComponent();
			
				String name = nodeselected.getUserObject().toString();
				
				lbname.setText(name);
				TreePath treepath = e.getPath();
				
				txtpath.setText(getTreepath(treepath));
			
				model = CreateData(model, txtpath.getText());
				table.setModel(model);
				
			}
		});
		
		setVisible(true); 

	}
	
	
	public DefaultTableModel CreateData(DefaultTableModel tb, String folder) {
		if (tb.getRowCount() > 0) {
			for (int i = tb.getRowCount() - 1; i > -1; i--) {
				tb.removeRow(i);
			}
		}
		File file = new File(folder);
		File[] list = file.listFiles();
		for (File files : list) {
			String name = files.getName();
			String type = "";
			
			if (files.isFile()) {
				type = "." + name.substring(name.lastIndexOf(".") + 1);
				
			}
			if (files.isDirectory()) {
				type = "Folder";
			}
			tb.addRow(new Object[] {name, type});
		}
		return tb;
	}
	

	public String FileNodeName(File file) {
		String name = file.getName();
		if (name.equals("")) {
			return file.getAbsolutePath();
		} else {
			return name;
		}
	}
	
	
	public void createChildren(File fileRoot, DefaultMutableTreeNode node) {
		File[] files = fileRoot.listFiles();
		if (files == null) {
			return;
		}else {
			for (File file : files) {
				if (file.isDirectory()) {
					DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(FileNodeName(file));
					node.add(childNode);
					if (file.isDirectory())
						createChildren(file, childNode);
				}

			}
		}
			
		
	}

	public String getTreepath(TreePath path) {
		String value = path.toString();
		
		value = value.replace("[This PC, ", "");
		value = value.replace(", ", "\\");
		value = value.replace("]", "");

		return value;

	}
	
	public static void copyFile(File file_old, File file_new) throws IOException {
		
		
		if (file_old.isDirectory()) {
			if (!file_old.exists()) {
				JOptionPane.showMessageDialog(null, file_old + "not exists!");
			}else {
				String files[] = file_old.list();
				for (String file : files) {
					File srcFile = new File(file_old, file);
					File tarFile = new File(file_new, file);
					copyFile(srcFile, tarFile);
				}
				
				JOptionPane.showMessageDialog(null, file_old + "Copy folder success!");
			}
		}else {
			InputStream in = new FileInputStream(file_old);
            OutputStream out = new FileOutputStream(file_new);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            JOptionPane.showMessageDialog(null, file_old + "copy file success!");
            in.close();
            out.close();
		}
	}
	
	public static void deleteFile(String souPath) throws IOException {
		File deletefile = new File(souPath);
		deletefile.delete();
	}
	
	public static void main(String[] args) {
		new WindowExplorer();
	}
	
	
}



// Aditya Prakash		axp171931
package Library_Mgmt;
import Library_Mgmt.Link;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.table.TableColumn;
// All libraries
public class Library_UI
{
	//Checkin Tab
	private JTextField checkinISBN;
	private JTextField checkinCard;
	private JTextField checkinName;
	private JTable checkinTable;
	
	//Checkout Tab
	private JTextField checkoutCard;
	private JTextField checkoutISBN;
	
	//Borrower Tab
	private JTextField borrowerFName;
	private JTextField borrowerLName;
	private JTextField borrowerSSN;
	private JTextField borrowerEmail;
	private JTextField borrowerPhone;
		
	//Find Tab
	private JTextField findText;
	private JTable findList;
		
	//Fines Tab
	private JTextField finesCard;
	private JTable finesList;

	private JFrame Window_frame;	 
	
	// Main Function
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{	
					// Run the Library UI
					Library_UI frame_UI = new Library_UI();
					frame_UI.Window_frame.setVisible(true);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
	}

	// Create the UI
	public Library_UI()
	{
		initialize();
	}

	// Initialize
	private void initialize() 
	{
		Window_frame = new JFrame();
		Window_frame.setBounds(-10, 0, 2000, 1200);
		Window_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane allTabs = new JTabbedPane(JTabbedPane.LEFT);
		Window_frame.getContentPane().add(allTabs, BorderLayout.CENTER);
		
		// Find Panel	
		
		JPanel findPanel = new JPanel();
		allTabs.addTab("Find", null, findPanel, null);
		findPanel.setLayout(null);
		findText = new JTextField();
		findText.setHorizontalAlignment(SwingConstants.LEFT);
		findText.setFont(new Font("Ariel", Font.PLAIN, 26));
		findText.setForeground(Color.BLACK);
		findText.setBounds(1100, 40, 500, 40);
		findPanel.add(findText);
		
		JLabel fLabelHead = new JLabel("Please enter any ISBN, Book Title or Author name and press find to view search results.");
		fLabelHead.setHorizontalAlignment(SwingConstants.LEFT);
		fLabelHead.setForeground(Color.BLACK);
		fLabelHead.setFont(new Font("Ariel", Font.PLAIN, 26));
		fLabelHead.setBounds(40, 920, 1000, 40);
		findPanel.add(fLabelHead);
		
		JButton findButton = new JButton("Find");
		findButton.setFont(new Font("Ariel", Font.PLAIN, 26));
		findButton.setForeground(Color.BLACK);
		findButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae1) 
			{
				try 
				{
					Link link1 = new Link();
					String query= null;		
					String bookISBN,bookTitle,bookAuthor;
					DefaultTableModel tableModel1 = (DefaultTableModel) findList.getModel();
					tableModel1.setRowCount(0);
					String find = findText.getText();
					
					boolean bookIsPresent;
					
					if(!find.isEmpty())
					{
						query="SELECT b1.isbn13, b1.title, a1.author, b1.availability FROM book b1, BOOK_AUTHORS bauth, authors a1 WHERE b1.isbn13=bauth.isbn13 AND bauth.author_id=a1.author_id AND (b1.isbn13 LIKE '%"+ find +"%' OR a1.author LIKE '%"+ find +"%' OR b1.title LIKE '%"+ find +"%')";
					
						if(!query.isEmpty())
						{  						
							ResultSet rSet1 = link1.Data_Link(query);  // database implementation    
							if(!rSet1.next())
							{
								JOptionPane.showMessageDialog(null,"Your search query returned no results.");
							}
							else
							{
								do
								{
									bookISBN = rSet1.getString("isbn13");
									bookTitle = rSet1.getString("title");
									bookAuthor = rSet1.getString("author");
									bookIsPresent = rSet1.getBoolean("availability");
			                   
									Object searchRow[] ={bookISBN, bookTitle, bookAuthor, bookIsPresent};
									tableModel1.addRow(searchRow);  
			                    }
								while(rSet1.next());
							}
						}
					}
					else 
					{
						JOptionPane.showMessageDialog(null,"Please enter a keyword to find.");
					}
				}
				catch(SQLException e2)
				{
					e2.printStackTrace();
				}
			}
		});
		findButton.setBounds(1650, 40, 100, 40);
		findPanel.add(findButton);
		findList = new JTable();
		findList.setBounds(40, 120, 1710, 800);
		
		
		JScrollPane findListScroll = new JScrollPane();
		findListScroll.setBounds(40, 110, 1710, 800);
		findPanel.add(findListScroll);
		
		findList.setModel(new DefaultTableModel(new Object[][] {},	new String[] 
				{
						"ISBN", "Title", "Author", "Present?"
				}
			)
		);
		findList.setFont(new Font("Ariel", Font.PLAIN, 14));
		findList.getTableHeader().setFont(new Font("Ariel", Font.BOLD, 18));
		findList.setRowHeight(100);
		TableColumn column = null;
	    for (int i = 0; i < 3; i++) 
	    {
	        column = findList.getColumnModel().getColumn(i);
	        if (i == 1) 
	        {
	            column.setPreferredWidth(500); //set bigger
	        }
	        if (i == 2) 
	        {
	            column.setPreferredWidth(300); //set medium
	        }
	    } 
		findListScroll.setViewportView(findList);
		
		//Check Out Panel
		JPanel checkoutPanel = new JPanel();
		allTabs.addTab("Checkout", null, checkoutPanel, null);
		checkoutPanel.setLayout(null);
		
		JLabel fLabelHead1 = new JLabel("Please enter the ISBN of the book and the Card Number to checkout.");
		fLabelHead1.setHorizontalAlignment(SwingConstants.LEFT);
		fLabelHead1.setForeground(Color.BLACK);
		fLabelHead1.setFont(new Font("Ariel", Font.PLAIN, 26));
		fLabelHead1.setBounds(400, 500, 1000, 40);
		checkoutPanel.add(fLabelHead1);
		
		
		JLabel cardLabel = new JLabel("Card Number:");
		cardLabel.setHorizontalAlignment(SwingConstants.LEFT);
		cardLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		cardLabel.setForeground(Color.BLACK);
		cardLabel.setBounds(400, 150, 500, 40);
		checkoutPanel.add(cardLabel);
		
		checkoutCard = new JTextField();
		checkoutCard.setHorizontalAlignment(SwingConstants.LEFT);
		checkoutCard.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkoutCard.setForeground(Color.BLACK);
		checkoutCard.setBounds(750, 150, 300, 40);
		checkoutPanel.add(checkoutCard);
		checkoutCard.setColumns(10);
		
		
		JLabel ISBNLabel = new JLabel("ISBN:");
		ISBNLabel.setHorizontalAlignment(SwingConstants.LEFT);
		ISBNLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		ISBNLabel.setForeground(Color.BLACK);
		ISBNLabel.setBounds(400, 250, 500, 40);
		checkoutPanel.add(ISBNLabel);
		
		checkoutISBN = new JTextField();
		checkoutISBN.setHorizontalAlignment(SwingConstants.LEFT);
		checkoutISBN.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkoutISBN.setForeground(Color.BLACK);
		checkoutISBN.setBounds(750, 250, 300, 40);
		checkoutPanel.add(checkoutISBN);
		checkoutISBN.setColumns(10);
		
		JButton checkoutButton = new JButton("Checkout");
		checkoutButton.setForeground(Color.BLACK);
		checkoutButton.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkoutButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				Link link1 = new Link(); 
				String ISBNNumber = checkoutISBN.getText();
				String cardNumber = checkoutCard.getText();
				
				if(!cardNumber.isEmpty() || !ISBNNumber.isEmpty())
				{
					boolean availability;
					String query_1,query_2,query_3,query_4,query_5;
					
					query_1 = "SELECT * FROM book WHERE isbn13 = '" + ISBNNumber + "'";
					query_2 = "SELECT * FROM borrowers WHERE card_id = '"+ cardNumber + "'";
					query_3 = "SELECT COUNT(loan_id) FROM book_loans WHERE card_id = '"+ cardNumber +"'";
					
					ResultSet rSet1 = link1.Data_Link(query_1); 
					ResultSet rSet2 = link1.Data_Link(query_2);
					ResultSet rSet3 = link1.Data_Link(query_3);
					
					try 
					{
						if(rSet1.next() && rSet2.next())
						{
							if(rSet3.next())
							{
								String num = rSet3.getString(1);
								int num1 = Integer.parseInt(num);
								if(num1<3)
								{
									availability = rSet1.getBoolean("Availability");
									if(availability)
									{
										 query_4 = "INSERT INTO book_loans (isbn13,card_id,date_out,due_date)" +
										          "VALUES ('"+ ISBNNumber +"', '"+ cardNumber +"', curdate(), DATE_ADD(date_out,INTERVAL 14 DAY))";
											 
										 query_5 = "UPDATE book SET Availability = 0 WHERE isbn13='"+ ISBNNumber +"'";
										 link1.Affected_Rows_Link(query_4);
										 link1.Affected_Rows_Link(query_5);
										 JOptionPane.showMessageDialog(null,"Checkout added."); 
									}
									else
									{
										JOptionPane.showMessageDialog(null,"Book unavailable.");
									}
								}
								else 
								{
									JOptionPane.showMessageDialog(null,"Member already checked out 3 books.");
								}
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null, "Invalid member.");
						}	
				 	} 
					catch (Exception e3) 
					{
						e3.printStackTrace();
					}	
				}
				else if (cardNumber.isEmpty() && !ISBNNumber.isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Enter ISBN.");		
				}
				else if (!ISBNNumber.isEmpty() && cardNumber.isEmpty())
				{
					JOptionPane.showMessageDialog(null,"Enter Card Number.");
				}
				else
				{
					JOptionPane.showMessageDialog(null,"Please enter the fields.");
				}
			}
		});
		checkoutButton.setBounds(800, 350, 200, 40);
		checkoutPanel.add(checkoutButton);
		
		// Check In Panel
		JPanel checkinPanel = new JPanel();
		allTabs.addTab("Checkin", null, checkinPanel, null);
		checkinPanel.setLayout(null);
		checkinPanel.setForeground(Color.LIGHT_GRAY);
		
		JLabel fLabelHead2 = new JLabel("Please enter the ISBN, Card Number and Name to check Book Loans. Select from the list to Check In the book.");
		fLabelHead2.setHorizontalAlignment(SwingConstants.LEFT);
		fLabelHead2.setForeground(Color.BLACK);
		fLabelHead2.setFont(new Font("Ariel", Font.PLAIN, 26));
		fLabelHead2.setBounds(40, 900, 1400, 40);
		checkinPanel.add(fLabelHead2);
		
		JLabel CheckinISBNLabel = new JLabel("ISBN: ");
		CheckinISBNLabel.setHorizontalAlignment(SwingConstants.LEFT);
		CheckinISBNLabel.setForeground(Color.BLACK);
		CheckinISBNLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		CheckinISBNLabel.setBounds(1300, 50, 100, 40);
		checkinPanel.add(CheckinISBNLabel);
		
		checkinISBN = new JTextField();
		checkinISBN.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkinISBN.setHorizontalAlignment(SwingConstants.LEFT);
		checkinISBN.setForeground(Color.BLACK);
		checkinISBN.setBounds(1500, 50, 300, 40);
		checkinPanel.add(checkinISBN);
		checkinISBN.setColumns(10);
		
		JLabel CheckinCardLabel = new JLabel("Card Number:");
		CheckinCardLabel.setHorizontalAlignment(SwingConstants.LEFT);
		CheckinCardLabel.setForeground(Color.BLACK);
		CheckinCardLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		CheckinCardLabel.setBounds(1300, 150, 200, 40);
		checkinPanel.add(CheckinCardLabel);
		
		checkinCard = new JTextField();
		checkinCard.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkinCard.setHorizontalAlignment(SwingConstants.LEFT);
		checkinCard.setForeground(Color.BLACK);
		checkinCard.setBounds(1500, 150, 300, 40);
		checkinPanel.add(checkinCard);
		checkinCard.setColumns(10);
		
		JLabel CheckinNameLabel = new JLabel("Name:");
		CheckinNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		CheckinNameLabel.setForeground(Color.BLACK);
		CheckinNameLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		CheckinNameLabel.setBounds(1300, 250, 100, 40);
		checkinPanel.add(CheckinNameLabel);
		
		checkinName = new JTextField();
		checkinName.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkinName.setHorizontalAlignment(SwingConstants.LEFT);
		checkinName.setForeground(Color.BLACK);
		checkinName.setBounds(1500, 250, 300, 40);
		checkinPanel.add(checkinName);
		checkinName.setColumns(10);
		
		JButton checkinButton = new JButton("Check Loans");
		checkinButton.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkinButton.setForeground(Color.BLACK);
		checkinButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae5) 
			{
				DefaultTableModel checkinTableModel = (DefaultTableModel) checkinTable.getModel();
				checkinTableModel.setRowCount(0);
				String name = checkinName.getText();
				String ISBNNumber = checkinISBN.getText();
				String cardNumber = checkinCard.getText();
				
				try
				{					
					String checkinQuery;
					if(!ISBNNumber.isEmpty()||!name.isEmpty()||!cardNumber.isEmpty())
					{	
						if(!ISBNNumber.isEmpty()&&!cardNumber.isEmpty()&&!name.isEmpty()) 
						{
							checkinQuery = "SELECT * FROM book_loans WHERE isbn13='"+ ISBNNumber +"'";
						}
						
						else if(!ISBNNumber.isEmpty()&&!cardNumber.isEmpty()&&!name.isEmpty())
						{
							checkinQuery = "SELECT * FROM book_loans WHERE isbn13='"+ ISBNNumber +"'";
						}
						else if(!ISBNNumber.isEmpty() && !cardNumber.isEmpty() && name.isEmpty())
						{
							checkinQuery = "SELECT * FROM book_loans WHERE isbn13='"+ ISBNNumber +"'";
						}
						else if(!ISBNNumber.isEmpty() && cardNumber.isEmpty() && !name.isEmpty())
						{
							checkinQuery = "SELECT * FROM book_loans WHERE isbn13='"+ ISBNNumber +"'";
						}
						else if(ISBNNumber.isEmpty() && !cardNumber.isEmpty() && !name.isEmpty())
						{
							checkinQuery = "SELECT * FROM book_loans WHERE card_id='"+ cardNumber +"'";
						}
						else if(ISBNNumber.isEmpty() && !cardNumber.isEmpty() && name.isEmpty())
						{
							checkinQuery = "SELECT * FROM book_loans WHERE card_id='"+ cardNumber +"'";
						}
						else if(!ISBNNumber.isEmpty() && cardNumber.isEmpty() && name.isEmpty())
						{
							checkinQuery = "SELECT * FROM book_loans WHERE isbn13='"+ ISBNNumber +"'";
						}
						else
						{
							Link checkinLink = new Link();
							String query_1;
							query_1 = "SELECT card_id FROM borrowers WHERE bname LIKE '%"+name+"%'";
							ResultSet rSetCheckin = checkinLink.Data_Link(query_1);
							String numCheckin = rSetCheckin.getString(1);
							int numCheckin1 = Integer.parseInt(numCheckin);
							checkinQuery = "SELECT * FROM book_loans WHERE card_id = '" + numCheckin1 + "'";
						}
						Link checkinLink = new Link();
						ResultSet rSetCheckin1 = checkinLink.Data_Link(checkinQuery);
						
						if(!rSetCheckin1.next())
						{	
							JOptionPane.showMessageDialog(null,"No book loans.");	
						}
						else
						{
							do
							{	
								String checkinCardNum = rSetCheckin1.getString("card_id");
								String checkinLoanID = rSetCheckin1.getString("loan_id");
								String checkinDueDate= rSetCheckin1.getString("due_date");
								String checkinDateIn= rSetCheckin1.getString("date_in");
								String checkinDateOut = rSetCheckin1.getString("date_out");
								String checkinISBNstr = rSetCheckin1.getString("isbn13");
								Object obj1[] = {checkinLoanID,checkinISBNstr,checkinCardNum,checkinDateIn,checkinDateOut,checkinDueDate}; 
								checkinTableModel.addRow(obj1);
							}
							while(rSetCheckin1.next());
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Enter the values.");
					}
					
				}
				catch(Exception e4)
				{
					e4.printStackTrace();
				}
			}
		});
		checkinButton.setBounds(1550, 350, 200, 40);
		checkinPanel.add(checkinButton);
		
		JScrollPane checkinScroll = new JScrollPane();
		checkinScroll.setBounds(40, 50, 1250, 700);
		checkinPanel.add(checkinScroll);
		
		checkinTable = new JTable();
		checkinTable.setModel(new DefaultTableModel(new Object[][] {},new String[] 
				{
						"Loan ID", "ISBN", "Card Number", "Date in","Date out","Due Date"
				}
			)
		);
		TableColumn column1 = null;
		checkinTable.setRowHeight(100);
		checkinTable.getTableHeader().setFont(new Font("Ariel", Font.BOLD, 18));
		checkinTable.setFont(new Font("Ariel", Font.PLAIN, 14));
	    for (int i = 0; i < 5; i++) 
	    {
	        column1 = checkinTable.getColumnModel().getColumn(i);
	        if (i == 0) 
	        {
	            column1.setPreferredWidth(30); // set smaller
	        }
	        if (i == 1) 
	        {
	            column1.setPreferredWidth(200); //set bigger
	        }
	        if (i == 3) 
	        {
	            column1.setPreferredWidth(150); //set medium
	        }
	        if (i == 4) 
	        {
	            column1.setPreferredWidth(150); //set medium
	        }
	        if (i == 5) 
	        {
	            column1.setPreferredWidth(150); //set medium
	        }
	    }
		checkinScroll.setViewportView(checkinTable);
		JButton checkinButton1 = new JButton("Check In");
		checkinButton1.setFont(new Font("Ariel", Font.PLAIN, 26));
		checkinButton1.setForeground(Color.BLACK);
		checkinButton1.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae2) 
			{	
				Link link1 = new Link();
				int rowPosition=checkinTable.getSelectedRow();
				if(rowPosition == -1)
				{	
					JOptionPane.showMessageDialog(null,"No row selected.");	
				}
				else
				{
					String checkinLoanID = (String) checkinTable.getValueAt(rowPosition, 0);
					String checkinISBN = (String) checkinTable.getValueAt(rowPosition, 1);
					
			        try
			        {
			        	String query1= "UPDATE book_loans SET date_in = curdate() WHERE loan_id = '"+checkinLoanID+"'";
			            String query2= "UPDATE book SET availability =1 WHERE isbn13='"+checkinISBN+"'";
			            String query4="SELECT datediff(date_in,due_date) AS diff FROM book_loans WHERE isbn13 = '"+checkinISBN+"';";
			            link1.Affected_Rows_Link(query1);
			            link1.Affected_Rows_Link(query2);
			            ResultSet rSetCheckin1 = link1.Data_Link(query4);
				           
			            if(rSetCheckin1.next())
			            {
			            	String num = rSetCheckin1.getString("diff");
						    int checkinNum1 = Integer.parseInt(num);
						    if(checkinNum1 > 0)
						    {
						    	float y = (float) (checkinNum1 * 0.25);
						        String query3= "INSERT INTO fines (loan_id,fine_amt,paid) VALUES ('"+checkinLoanID+"','"+y+"',0)";
						        link1.Affected_Rows_Link(query3);      
						    }
			            }
			            JOptionPane.showMessageDialog(null,"Data changed.");
			        }
			        catch (Exception e4) 
			        {
			            e4.printStackTrace();
			        }        
				}
			}
		});
		checkinButton1.setBounds(500, 800, 150, 40);
		checkinPanel.add(checkinButton1);
		
		//Add Borrowers Panel
		JPanel borrowerPanel = new JPanel();
		borrowerPanel.setForeground(new Color(0, 0, 255));
		allTabs.addTab("New Borrower", null, borrowerPanel, null);
		borrowerPanel.setLayout(null);

		JLabel fLabelHead3 = new JLabel("Please enter all the details to create a new Borrower.");
		fLabelHead3.setHorizontalAlignment(SwingConstants.LEFT);
		fLabelHead3.setForeground(Color.BLACK);
		fLabelHead3.setFont(new Font("Ariel", Font.PLAIN, 26));
		fLabelHead3.setBounds(400, 40, 1000, 40);
		borrowerPanel.add(fLabelHead3);

		JLabel bFNameLabel = new JLabel("First Name:");
		bFNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bFNameLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		bFNameLabel.setForeground(Color.BLACK);
		bFNameLabel.setBounds(400, 150, 200, 40);
		borrowerPanel.add(bFNameLabel);
		
		borrowerFName = new JTextField();
		borrowerFName.setHorizontalAlignment(SwingConstants.LEFT);
		borrowerFName.setFont(new Font("Ariel", Font.PLAIN, 26));
		borrowerFName.setForeground(Color.BLACK);
		borrowerFName.setBounds(750, 150, 200, 40);
		borrowerPanel.add(borrowerFName);
		borrowerFName.setColumns(10);
		
		JLabel bLNameLabel = new JLabel("Last Name:");
		bLNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bLNameLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		bLNameLabel.setForeground(Color.BLACK);
		bLNameLabel.setBounds(400, 250, 200, 40);
		borrowerPanel.add(bLNameLabel);
		
		borrowerLName = new JTextField();
		borrowerLName.setHorizontalAlignment(SwingConstants.LEFT);
		borrowerLName.setFont(new Font("Ariel", Font.PLAIN, 26));
		borrowerLName.setForeground(Color.BLACK);
		borrowerLName.setBounds(750, 250, 200, 40);
		borrowerPanel.add(borrowerLName);
		borrowerLName.setColumns(10);
		
		JLabel bSSNLabel = new JLabel("SSN:");
		bSSNLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bSSNLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		bSSNLabel.setForeground(Color.BLACK);
		bSSNLabel.setBounds(400, 350, 200, 40);
		borrowerPanel.add(bSSNLabel);
		
		borrowerSSN = new JTextField();
		borrowerSSN.setHorizontalAlignment(SwingConstants.LEFT);
		borrowerSSN.setFont(new Font("Ariel", Font.PLAIN, 26));
		borrowerSSN.setForeground(Color.BLACK);
		borrowerSSN.setBounds(750, 350, 200, 40);
		borrowerPanel.add(borrowerSSN);
		borrowerSSN.setColumns(10);
		
		JLabel bAddressLabel = new JLabel("Address:");
		bAddressLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bAddressLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		bAddressLabel.setForeground(Color.BLACK);
		bAddressLabel.setBounds(400, 650, 200, 40);
		borrowerPanel.add(bAddressLabel);
		
		JTextArea borrowerAddress = new JTextArea();
		borrowerAddress.setFont(new Font("Ariel", Font.PLAIN, 26));
		borrowerAddress.setForeground(Color.BLACK);
		borrowerAddress.setBounds(750, 650, 200, 80);
		borrowerPanel.add(borrowerAddress);
		
		JLabel bEmailLabel = new JLabel("Email:");
		bEmailLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bEmailLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		bEmailLabel.setForeground(Color.BLACK);
		bEmailLabel.setBounds(400, 450, 200, 40);
		borrowerPanel.add(bEmailLabel);
		
		borrowerEmail = new JTextField();
		borrowerEmail.setHorizontalAlignment(SwingConstants.LEFT);
		borrowerEmail.setFont(new Font("Ariel", Font.PLAIN, 26));
		borrowerEmail.setForeground(Color.BLACK);
		borrowerEmail.setBounds(750, 450, 200, 40);
		borrowerPanel.add(borrowerEmail);
		borrowerEmail.setColumns(10);
		
		JLabel bPhoneLabel = new JLabel("Phone: ");
		bPhoneLabel.setHorizontalAlignment(SwingConstants.LEFT);
		bPhoneLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		bPhoneLabel.setForeground(Color.BLACK);
		bPhoneLabel.setBounds(400, 550, 200, 40);
		borrowerPanel.add(bPhoneLabel);
		
		borrowerPhone = new JTextField();
		borrowerPhone.setHorizontalAlignment(SwingConstants.LEFT);
		borrowerPhone.setFont(new Font("Ariel", Font.PLAIN, 26));
		borrowerPhone.setForeground(Color.BLACK);
		borrowerPhone.setBounds(750, 550, 200, 40);
		borrowerPanel.add(borrowerPhone);
		borrowerPhone.setColumns(10);
		
		JButton btnAddBorrower = new JButton("Add Borrower");
		btnAddBorrower.setFont(new Font("Ariel", Font.PLAIN, 26));
		btnAddBorrower.setForeground(Color.BLACK);
		btnAddBorrower.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae3)
			{
				String strPhone = borrowerPhone.getText();
				String strAddress = borrowerAddress.getText();
				String strEMail = borrowerEmail.getText();
				String strLName = borrowerLName.getText();
				String strSSN = borrowerSSN.getText();
				String strFName = borrowerFName.getText();
				String fullName =strFName+" "+strLName;
				try
				{
					if(!strFName.isEmpty() && !strLName.isEmpty() && !strSSN.isEmpty() && !strEMail.isEmpty() && !strPhone.isEmpty() && !strAddress.isEmpty())
					{
						if(strPhone.matches("[0-9]{10}"))
						{
							String query1 = "SELECT COUNT(*) FROM borrowers WHERE email ='" + strEMail + "';";
							Link link1  = new Link();
							ResultSet rSetBorrower = link1.Data_Link(query1);
							if(rSetBorrower.next())
							{
								String num = rSetBorrower.getString(1);
								int num1 = Integer.parseInt(num);
							
								if(num1==0)
								{							
									String query2 = "INSERT INTO borrowers(bname,email,address,phone) VALUES ('"+fullName+"','"+strEMail+"','"+strAddress+"','"+strPhone+"');";
									int bNumRows = link1.Affected_Rows_Link(query2);
								
									if(bNumRows!=0)
									{
										JOptionPane.showMessageDialog(null,"New Borrower data included.");
									}	
								}
							
								else
								{
									JOptionPane.showMessageDialog(null,"Email already in use.");
								}
							}
						}
						else
						{
							JOptionPane.showMessageDialog(null,"Invalid Phone number.");	
						}
					}
					else
					{
						JOptionPane.showMessageDialog(null,"Please fill in all fields.");
					}
				}
				catch(SQLException e5)
				{	
					e5.printStackTrace();
				}
			}
		});
		btnAddBorrower.setBounds(750, 800, 200, 40);
		borrowerPanel.add(btnAddBorrower);
		
		//Fines Panel
		JPanel finesPanel = new JPanel();
		finesPanel.setForeground(new Color(128, 0, 0));
		allTabs.addTab("Fines", null, finesPanel, null);
		finesPanel.setLayout(null);
		
		JLabel finesCardLabel = new JLabel("Card Number:");
		finesCardLabel.setHorizontalAlignment(SwingConstants.LEFT);
		finesCardLabel.setFont(new Font("Ariel", Font.PLAIN, 26));
		finesCardLabel.setForeground(Color.BLACK);
		finesCardLabel.setBounds(1200, 50, 200, 40);
		finesPanel.add(finesCardLabel);
		
		finesCard = new JTextField();
		finesCard.setForeground(Color.BLACK);
		finesCard.setHorizontalAlignment(SwingConstants.LEFT);
		finesCard.setFont(new Font("Ariel", Font.PLAIN, 26));
		finesCard.setBounds(1500, 50, 200, 40);
		finesPanel.add(finesCard);
		finesCard.setColumns(10);
		
		JCheckBox prevPaidCheck = new JCheckBox("Include History?");
		prevPaidCheck.setHorizontalAlignment(SwingConstants.LEFT);
		prevPaidCheck.setForeground(Color.BLACK);
		prevPaidCheck.setFont(new Font("Ariel", Font.PLAIN, 26));
		prevPaidCheck.setBounds(1350, 100, 350, 40);
		finesPanel.add(prevPaidCheck);
		
		JButton payButton = new JButton("Pay");
		payButton.setForeground(Color.BLACK);
		payButton.setFont(new Font("Dialog", Font.PLAIN, 26));
		payButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{	
				Link link = new Link();
				String query=null;
				 
				int rowPosition = finesList.getSelectedRow();
				if(rowPosition < 0)
				{
					JOptionPane.showMessageDialog(null, "No selection made.");
				}
				else
				{
					String loanID = (String) finesList.getValueAt(rowPosition,0);
					String paid = (String) finesList.getValueAt(rowPosition,3);
					String isBookReturned = (String) finesList.getValueAt(rowPosition,4);
					
			        try
			        {
			        	if(isBookReturned.equals("NO"))
			        	{
			        		JOptionPane.showMessageDialog(null, "Book not returned yet.");
			        	}
			           
			        	else
			        	{
			        		if(paid !=null && paid.toString().equals("1"))
			        		{
			        			JOptionPane.showMessageDialog(null, "No Dues.");
			        		}
			        		else
			        		{
			        			query="UPDATE fines SET paid=1 WHERE loan_id='"+loanID+"'";
			        			link.Affected_Rows_Link(query);
			        			JOptionPane.showMessageDialog(null, "Fine paid");
			        		}
			        	}    
			        }
			        catch (Exception e6) 
			        {
			            e6.printStackTrace();
			        }
				}    
			}
		});
		payButton.setBounds(40, 900, 150, 40);
		finesPanel.add(payButton);
		
		JButton refreshButton = new JButton("Refresh");
		refreshButton.setFont(new Font("Dialog", Font.PLAIN, 26));
		refreshButton.setForeground(Color.BLACK);
		refreshButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae4) 
			{
				Link link = new Link();
				boolean previousBool = prevPaidCheck.isSelected();
				String ISBNNumber,loanID,fineAmountStr,paid,isBookReturned,dateInStr;
				String finesCardStr= finesCard.getText();
				DefaultTableModel finesTableModel = (DefaultTableModel) finesList.getModel();
				finesTableModel.setRowCount(0);
				try
				{
		        	String query,query1;	
		        	query ="SELECT * FROM borrowers WHERE card_id= '"+finesCardStr+"'";
		        	query1 = "SELECT f.Loan_id, bl.isbn13, f.fine_amt,f.paid,bl.date_in, datediff(curdate(), bl.due_date) AS curdue,datediff( bl.date_in,bl.due_date) AS indue FROM book_loans bl JOIN fines f ON  bl.Loan_id = f.Loan_id WHERE  bl.card_id='"+finesCardStr+"' HAVING curdue>0 OR indue>0 UNION SELECT f.Loan_id, bl.isbn13, f.fine_amt,f.paid,bl.date_in, datediff(curdate(), bl.due_date) AS curdue,datediff( bl.date_in,bl.due_date) AS indue FROM book_loans bl JOIN fines f ON  bl.Loan_id = f.Loan_id WHERE  bl.card_id='"+finesCardStr+"';";
		        	ResultSet rs = link.Data_Link(query1);
		            ResultSet rs1= link.Data_Link(query);
		            if(!rs1.next())
		            {
		            	JOptionPane.showMessageDialog(null," Invalid Card.");
		            }
		            else
		            {
		            	if(!rs.next())
		            	{
		                	JOptionPane.showMessageDialog(null,"No loans for the user.");
		            	}
		            	else
		            	{
		            		do
		            		{
		            			fineAmountStr = rs.getString("fine_amt");
		            			paid = rs.getString("paid");
		            			dateInStr = rs.getString("date_in");
		            			loanID = rs.getString("loan_id");	
		            			ISBNNumber = rs.getString("isbn13");
		            			int checkPayment = Integer.parseInt(paid);
		            			if(dateInStr == null)
		            			{
		            				isBookReturned = "NO";
		            			}
		            			else
		            			{
		            				isBookReturned = "YES";
		            			}
		            			if(previousBool)
		            			{
		            				Object obj[]={loanID,ISBNNumber,fineAmountStr,paid,isBookReturned};
	        						finesTableModel.addRow(obj);
		            			}
		            			else
		            			{
		            				if(checkPayment==0)
		            				{
		            					Object obj[]={loanID,ISBNNumber,fineAmountStr,paid,isBookReturned};
		        						finesTableModel.addRow(obj);
		            				}
		            			}
		            		}
		            		while(rs.next());
		            	}	
		            }
		        }
		        catch (Exception e8) 
				{
		            e8.printStackTrace();
		        }
			}
		});
		refreshButton.setBounds(990, 900, 150, 40);
		finesPanel.add(refreshButton);
		JButton getDataButton = new JButton("Get Fines");	
		getDataButton.setFont(new Font("Dialog", Font.PLAIN, 26));
		getDataButton.setForeground(Color.BLACK);
		getDataButton.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent ae4) 
			{
				Link link = new Link();
				boolean previousBool = prevPaidCheck.isSelected();
				String ISBNNumber,loanID,fineAmountStr,paid,isBookReturned,dateInStr;
				String finesCardStr= finesCard.getText();
				DefaultTableModel finesTableModel = (DefaultTableModel) finesList.getModel();
				finesTableModel.setRowCount(0);
				try
				{
		        	String query,query1;	
		        	query ="SELECT * FROM borrowers WHERE card_id= '"+finesCardStr+"'";
		        	query1 = "SELECT f.Loan_id, bl.isbn13, f.fine_amt,f.paid,bl.date_in, datediff(curdate(), bl.due_date) AS curdue,datediff( bl.date_in,bl.due_date) AS indue FROM book_loans bl JOIN fines f ON  bl.Loan_id = f.Loan_id WHERE  bl.card_id='"+finesCardStr+"' HAVING curdue>0 OR indue>0 UNION SELECT f.Loan_id, bl.isbn13, f.fine_amt,f.paid,bl.date_in, datediff(curdate(), bl.due_date) AS curdue,datediff( bl.date_in,bl.due_date) AS indue FROM book_loans bl JOIN fines f ON  bl.Loan_id = f.Loan_id WHERE  bl.card_id='"+finesCardStr+"';";
		        	ResultSet rs = link.Data_Link(query1);
		            ResultSet rs1= link.Data_Link(query);
		            if(!rs1.next())
		            {
		            	JOptionPane.showMessageDialog(null," Invalid Card.");
		            }
		            else
		            {
		            	if(!rs.next())
		            	{
		                	JOptionPane.showMessageDialog(null,"No loans for the user.");
		            	}
		            	else
		            	{
		            		do
		            		{
		            			fineAmountStr = rs.getString("fine_amt");
		            			paid = rs.getString("paid");
		            			dateInStr = rs.getString("date_in");
		            			loanID = rs.getString("loan_id");	
		            			ISBNNumber = rs.getString("isbn13");
		            			int checkPayment = Integer.parseInt(paid);
		            			if(dateInStr == null)
		            			{
		            				isBookReturned = "NO";
		            			}
		            			else
		            			{
		            				isBookReturned = "YES";
		            			}
		            			if(previousBool)
		            			{
		            				Object obj[]={loanID,ISBNNumber,fineAmountStr,paid,isBookReturned};
	        						finesTableModel.addRow(obj);
		            			}
		            			else
		            			{
		            				if(checkPayment==0)
		            				{
		            					Object obj[]={loanID,ISBNNumber,fineAmountStr,paid,isBookReturned};
		        						finesTableModel.addRow(obj);
		            				}
		            			}
		            		}
		            		while(rs.next());
		            	}	
		            }
		        }
		        catch (Exception e8) 
				{
		            e8.printStackTrace();
		        }
			}	
		});
		getDataButton.setBounds(1350, 150, 200, 40);
		finesPanel.add(getDataButton);
		
		JScrollPane finesScrollPane = new JScrollPane();
		finesScrollPane.setBounds(40, 50, 1100, 800);
		finesPanel.add(finesScrollPane);
		
		finesList = new JTable();
		finesList.setBounds(37, 136, 1084, 430);
		finesList = new JTable();
		finesList.setModel(new DefaultTableModel(new Object[][]{},new String[] 
				{
					"Loan ID", "ISBN", "Fine amount", "Paid?", "Book Returned?"
				}
			)
		);
		finesList.setFont(new Font("Ariel", Font.PLAIN, 14));
		finesList.getTableHeader().setFont(new Font("Ariel", Font.BOLD, 18));
		finesList.setRowHeight(100);
		TableColumn column2 = null;
	    for (int i = 0; i < 5; i++) 
	    {
	        column2 = finesList.getColumnModel().getColumn(i);
	        if (i == 1) 
	        {
	            column2.setPreferredWidth(500); //sport column is bigger
	        }
	    }
		finesScrollPane.setViewportView(finesList);
	}
}
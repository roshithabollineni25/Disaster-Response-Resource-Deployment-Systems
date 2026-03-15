import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class DisasterResponseApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel contentPanel;

    private DisasterService service = new DisasterService();

    public DisasterResponseApp(){

        setTitle("Disaster Response & Resource Deployment System");
        setSize(1100,650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        add(createHeader(),BorderLayout.NORTH);
        add(createSidebar(),BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);

        contentPanel.add(createReportPanel(),"REPORT");
        contentPanel.add(createTeamsPanel(),"TEAMS");
        contentPanel.add(createHistoryPanel(),"HISTORY");
        contentPanel.add(createAdminPanel(),"ADMIN");

        add(contentPanel,BorderLayout.CENTER);
    }


  
    private JPanel createHeader(){

        JPanel header = new JPanel(){
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;

                int w = getWidth();
                int h = getHeight();

                GradientPaint gp = new GradientPaint(
                        0,0,new Color(40,60,140),
                        w,h,new Color(80,140,220));

                g2.setPaint(gp);
                g2.fillRect(0,0,w,h);
            }
        };

        header.setLayout(new BorderLayout());
        header.setPreferredSize(new Dimension(0,70));

        JLabel title = new JLabel("Disaster Response & Resource Deployment System");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI",Font.BOLD,20));

        JLabel subtitle = new JLabel("Project: DisasterResponseSystems | Rural & Semi-Urban Safety");
        subtitle.setForeground(new Color(230,230,230));
        subtitle.setFont(new Font("Segoe UI",Font.PLAIN,12));

        JPanel text = new JPanel();
        text.setOpaque(false);
        text.setLayout(new GridLayout(2,1));
        text.setBorder(new EmptyBorder(10,20,10,10));

        text.add(title);
        text.add(subtitle);

        header.add(text,BorderLayout.WEST);

        return header;
    }
  

  
    private JPanel createSidebar(){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.setPreferredSize(new Dimension(220,0));
        panel.setBackground(new Color(25,35,80));
        panel.setBorder(new EmptyBorder(20,10,20,10));

        JButton report = createMenuButton("Report Incident");
        JButton teams = createMenuButton("Rescue Teams");
        JButton history = createMenuButton("History & Patterns");
        JButton admin = createMenuButton("Admin Panel");

        report.addActionListener(e->cardLayout.show(contentPanel,"REPORT"));
        teams.addActionListener(e->cardLayout.show(contentPanel,"TEAMS"));
        history.addActionListener(e->cardLayout.show(contentPanel,"HISTORY"));
        admin.addActionListener(e->cardLayout.show(contentPanel,"ADMIN"));

        panel.add(report);
        panel.add(Box.createVerticalStrut(10));

        panel.add(teams);
        panel.add(Box.createVerticalStrut(10));

        panel.add(history);
        panel.add(Box.createVerticalStrut(10));

        panel.add(admin);

        return panel;
    }
  


    private JPanel createReportPanel(){

        JPanel panel = new JPanel(new GridLayout(7,2,10,10));
        panel.setBorder(new EmptyBorder(40,40,40,40));

        JComboBox<String> location = new JComboBox<>(new String[]{
                "Chinna Kondur","Kondur","Nalgonda","Kodad",
                "Hill Area","Low Lying Area"
        });

        JComboBox<String> type = new JComboBox<>(new String[]{
                "Flood","Heavy Rain","Landslide","Fire"
        });

        JComboBox<String> severity = new JComboBox<>(new String[]{
                "Low","Medium","High","Critical"
        });

        JTextField reporter = new JTextField();

        JTextArea desc = new JTextArea(3,20);
        desc.setLineWrap(true);
        desc.setWrapStyleWord(true);

        JButton submit = new JButton("Submit Incident & Generate Alert");

        submit.addActionListener(e->{

            String alert = service.generateAlert(
                    location.getSelectedItem().toString(),
                    type.getSelectedItem().toString(),
                    severity.getSelectedItem().toString(),
                    reporter.getText(),
                    desc.getText()
            );
            JOptionPane.showMessageDialog(
                    this,
                    alert,
                    "Alert Generated",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });

        panel.add(new JLabel("Location"));
        panel.add(location);

        panel.add(new JLabel("Disaster Type"));
        panel.add(type);

        panel.add(new JLabel("Severity"));
        panel.add(severity);

        panel.add(new JLabel("Reported By"));
        panel.add(reporter);

        panel.add(new JLabel("Additional Details"));
        panel.add(new JScrollPane(desc));

        panel.add(new JLabel());
        panel.add(submit);

        return panel;
    }
 
    private JButton createMenuButton(String text){

        JButton btn = new JButton(text);

        btn.setMaximumSize(new Dimension(200,40));
        btn.setFocusPainted(false);

        btn.setBackground(new Color(60,90,180));
        btn.setForeground(Color.WHITE);

        btn.setFont(new Font("Segoe UI",Font.BOLD,14));

        btn.setBorder(BorderFactory.createEmptyBorder(8,10,8,10));

        btn.addMouseListener(new java.awt.event.MouseAdapter(){

            public void mouseEntered(java.awt.event.MouseEvent evt){
                btn.setBackground(new Color(90,120,210));
            }

            public void mouseExited(java.awt.event.MouseEvent evt){
                btn.setBackground(new Color(60,90,180));
            }

        });

        return btn;
    }
  


  
    private JPanel createTeamsPanel(){

        JPanel panel = new JPanel(new BorderLayout());

        String[] columns = {"Team Name","Specialty","Base Location","Contact"};

        DefaultTableModel model = new DefaultTableModel(columns,0);

        JTable table = new JTable(model);

        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI",Font.PLAIN,13));
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,13));

        List<RescueTeam> list = service.getAllTeams();

        for(RescueTeam t : list){

            model.addRow(new Object[]{
                    t.getTeamName(),
                    t.getSpecialty(),
                    t.getBaseLocationName(),
                    t.getContactNumber()
            });
        }

        panel.add(new JScrollPane(table),BorderLayout.CENTER);

        return panel;
    }
 

   
    private JPanel createHistoryPanel(){

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20,20,20,20));
        panel.setBackground(new Color(245,248,255));

        String[] columns = {"Location","Disaster Type","Frequency (5 yrs)","Season Pattern","Risk Level"};

        DefaultTableModel model = new DefaultTableModel(columns,0);

        JTable table = new JTable(model);

        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI",Font.PLAIN,13));
        table.getTableHeader().setFont(new Font("Segoe UI",Font.BOLD,13));

        List<HistoricalPattern> list = service.getAllPatterns();

        for(HistoricalPattern hp : list){

            model.addRow(new Object[]{
                    hp.getLocationName(),
                    hp.getDisasterType(),
                    hp.getFrequency(),
                    hp.getSeasonPattern(),
                    hp.getRiskLevel()
            });
        }

        JScrollPane tableScroll = new JScrollPane(table);
        panel.add(tableScroll,BorderLayout.CENTER);


        JTextArea prediction = new JTextArea();
        prediction.setEditable(false);
        prediction.setLineWrap(true);
        prediction.setWrapStyleWord(true);
        prediction.setFont(new Font("Segoe UI",Font.PLAIN,14));
        prediction.setBackground(new Color(245,248,255));

        prediction.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,200,230)),
                BorderFactory.createEmptyBorder(10,10,10,10)
        ));

        JButton btn = new JButton("Generate Prediction");
        btn.setBackground(new Color(240,140,60));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);

        btn.addActionListener(e->{

            int max = 0;
            String location="";
            String disaster="";
            String season="";
            String risk="";

            for(HistoricalPattern hp : list){

                if(hp.getFrequency()>max){

                    max = hp.getFrequency();
                    location = hp.getLocationName();
                    disaster = hp.getDisasterType();
                    season = hp.getSeasonPattern();
                    risk = hp.getRiskLevel();
                }
            }

            prediction.setText(
                    "Based on last 5 years data there is HIGH chance of " + disaster +
                    "\nin " + location + " during " + season +
                    "\n\nRisk Level: " + risk +
                    "\nRecommended Action: Keep rescue teams ready and monitor alerts."
            );
        });

        JPanel bottom = new JPanel(new BorderLayout(10,10));
        bottom.setBorder(new EmptyBorder(10,0,0,0));

        bottom.add(prediction,BorderLayout.CENTER);
        bottom.add(btn,BorderLayout.EAST);

        panel.add(bottom,BorderLayout.SOUTH);

        return panel;
    }
  

 
    private JPanel createAdminPanel(){

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20,20,20,20));
        panel.setBackground(new Color(245,248,255));

        JLabel heading = new JLabel("Admin Control Panel");
        heading.setFont(new Font("Segoe UI",Font.BOLD,20));
        heading.setForeground(new Color(40,60,120));

        panel.add(heading,BorderLayout.NORTH);

        JPanel grid = new JPanel(new GridLayout(2,2,20,20));
        grid.setBackground(new Color(245,248,255));

        grid.add(createAdminCard(
                "📍 Manage Locations",
                "Add or update village locations and GPS coordinates"
        ));

        grid.add(createAdminCard(
                "👥 User & Team Management",
                "Assign rescue teams and manage system users"
        ));

        grid.add(createAdminCard(
                "⚠ Alert Thresholds",
                "Configure disaster alert conditions and severity levels"
        ));

        grid.add(createAdminCard(
                "📜 System Logs",
                "View previous incidents and administrative actions"
        ));

        panel.add(grid,BorderLayout.CENTER);

        return panel;
    }



    private JPanel createAdminCard(String title,String desc){

        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(Color.WHITE);

        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180,200,230)),
                BorderFactory.createEmptyBorder(15,15,15,15)
        ));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI",Font.BOLD,15));
        titleLabel.setForeground(new Color(40,60,120));

        JLabel descLabel = new JLabel("<html><body style='width:200px'>" + desc + "</body></html>");
        descLabel.setFont(new Font("Segoe UI",Font.PLAIN,12));

        JButton open = new JButton("Open");
        open.setBackground(new Color(70,120,210));
        open.setForeground(Color.WHITE);
        open.setFocusPainted(false);
        open.setFont(new Font("Segoe UI",Font.BOLD,12));

        open.addActionListener(e -> {

            if(title.contains("Manage Locations"))
                openManageLocations();

            else if(title.contains("User & Team"))
                openTeamManagement();

            else if(title.contains("Alert"))
                openAlertSettings();

            else if(title.contains("System Logs"))
                openSystemLogs();

        });

        card.add(titleLabel,BorderLayout.NORTH);
        card.add(descLabel,BorderLayout.CENTER);
        card.add(open,BorderLayout.SOUTH);

        return card;
    }

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {

            DisasterResponseApp app = new DisasterResponseApp();
            app.setVisible(true);

        });
    }

private void openManageLocations(){

    JFrame frame = new JFrame("Manage Locations");
    frame.setSize(400,250);
    frame.setLocationRelativeTo(this);

    JPanel panel = new JPanel(new GridLayout(4,2,10,10));
    panel.setBorder(new EmptyBorder(20,20,20,20));

    JTextField name = new JTextField();
    JTextField lat = new JTextField();
    JTextField lon = new JTextField();

    JButton add = new JButton("Add Location");

    add.addActionListener(e->{
        try{
            Connection conn = DBUtil.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO VillageLocations(name,latitude,longitude) VALUES(?,?,?)");

            ps.setString(1,name.getText());
            ps.setDouble(2,Double.parseDouble(lat.getText()));
            ps.setDouble(3,Double.parseDouble(lon.getText()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame,"Location Added");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    });

    panel.add(new JLabel("Location Name"));
    panel.add(name);

    panel.add(new JLabel("Latitude"));
    panel.add(lat);

    panel.add(new JLabel("Longitude"));
    panel.add(lon);

    panel.add(new JLabel());
    panel.add(add);

    frame.add(panel);
    frame.setVisible(true);
}
private void openTeamManagement(){

    JFrame frame = new JFrame("Rescue Team Management");
    frame.setSize(400,250);
    frame.setLocationRelativeTo(this);

    JPanel panel = new JPanel(new GridLayout(4,2,10,10));
    panel.setBorder(new EmptyBorder(20,20,20,20));

    JTextField name = new JTextField();
    JTextField specialty = new JTextField();
    JTextField contact = new JTextField();

    JButton add = new JButton("Add Team");

    add.addActionListener(e->{
        try{
            Connection conn = DBUtil.getConnection();

            PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO EmergencyTeams(team_name,specialty,contact_number) VALUES(?,?,?)");

            ps.setString(1,name.getText());
            ps.setString(2,specialty.getText());
            ps.setString(3,contact.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(frame,"Team Added");

        }catch(Exception ex){
            ex.printStackTrace();
        }
    });

    panel.add(new JLabel("Team Name"));
    panel.add(name);

    panel.add(new JLabel("Specialty"));
    panel.add(specialty);

    panel.add(new JLabel("Contact"));
    panel.add(contact);

    panel.add(new JLabel());
    panel.add(add);

    frame.add(panel);
    frame.setVisible(true);
}
private void openAlertSettings(){

    JOptionPane.showMessageDialog(this,
            "Alert configuration module will be implemented.");
}
private void openSystemLogs(){

    JFrame frame = new JFrame("System Logs");
    frame.setSize(600,400);
    frame.setLocationRelativeTo(this);

    DefaultTableModel model = new DefaultTableModel(
            new String[]{"Location","Type","Severity","Reporter"},0);

    JTable table = new JTable(model);

    try{
        Connection conn = DBUtil.getConnection();
        Statement st = conn.createStatement();

        ResultSet rs = st.executeQuery("SELECT * FROM DisasterReports");

        while(rs.next()){

            model.addRow(new Object[]{
                    rs.getInt("location_id"),
                    rs.getString("type"),
                    rs.getString("severity"),
                    rs.getString("reported_by")
            });
        }

    }catch(Exception e){
        e.printStackTrace();
    }

    frame.add(new JScrollPane(table));
    frame.setVisible(true);
}
}
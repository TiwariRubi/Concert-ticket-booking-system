

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class ConcertTicketBookingSystem extends JFrame {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/CONCERT_TICKET_BOOKING";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "Rubi@sql123";

    private Connection connection;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Color scheme
    private final Color PRIMARY_COLOR = new Color(138, 43, 226);
    private final Color SECONDARY_COLOR = new Color(75, 0, 130);
    private final Color ACCENT_COLOR = new Color(255, 215, 0);
    private final Color BG_COLOR = new Color(20, 20, 30);
    private final Color CARD_BG = new Color(40, 40, 60);

    public ConcertTicketBookingSystem() {
        setTitle("Concert Ticket Booking System");
        setSize(1400, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Connect to database
        if (!connectDatabase()) {
            return;
        }

        // Initialize UI
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create panels
        mainPanel.add(createWelcomePanel(), "WELCOME");
        mainPanel.add(createMainMenuPanel(), "MENU");
        mainPanel.add(createEventsPanel(), "EVENTS");
        mainPanel.add(createBookingPanel(), "BOOKING");
        mainPanel.add(createArtistsPanel(), "ARTISTS");
        mainPanel.add(createUsersPanel(), "USERS");
        mainPanel.add(createCrewPanel(), "CREW");
        mainPanel.add(createTicketTypesPanel(), "TICKET_TYPES");
        mainPanel.add(createCollaborationsPanel(), "COLLABORATIONS");
        mainPanel.add(createReportsPanel(), "REPORTS");

        add(mainPanel);
        setVisible(true);
    }

    private boolean connectDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            System.out.println("Database connected successfully!");
            return true;
        } catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this,
                    "MySQL JDBC Driver not found!\nPlease add mysql-connector-java to your classpath.",
                    "Driver Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                    "Database connection failed!\nError: " + e.getMessage() +
                            "\n\nPlease check:\n1. MySQL is running\n2. Database exists\n3. Credentials are correct",
                    "Connection Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
        }
    }

    // ==================== WELCOME PANEL ====================
    private JPanel createWelcomePanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BG_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        JLabel iconLabel = new JLabel("♫");
        iconLabel.setFont(new Font("Arial", Font.PLAIN, 120));
        iconLabel.setForeground(ACCENT_COLOR);
        centerPanel.add(iconLabel, gbc);

        JLabel titleLabel = new JLabel("CONCERT TICKET BOOKING");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        titleLabel.setForeground(PRIMARY_COLOR);
        centerPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Your Gateway to Amazing Live Experiences");
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        subtitleLabel.setForeground(ACCENT_COLOR);
        centerPanel.add(subtitleLabel, gbc);

        gbc.insets = new Insets(40, 0, 10, 0);
        JButton enterButton = createStyledButton("ENTER SYSTEM", ACCENT_COLOR, Color.BLACK);
        enterButton.setFont(new Font("Arial", Font.BOLD, 24));
        enterButton.setPreferredSize(new Dimension(300, 60));
        enterButton.addActionListener(e -> cardLayout.show(mainPanel, "MENU"));
        centerPanel.add(enterButton, gbc);

        panel.add(centerPanel, BorderLayout.CENTER);

        JLabel footerLabel = new JLabel("© 2025 Concert Management System | All Rights Reserved");
        footerLabel.setForeground(Color.GRAY);
        footerLabel.setHorizontalAlignment(SwingConstants.CENTER);
        footerLabel.setBorder(new EmptyBorder(10, 0, 10, 0));
        panel.add(footerLabel, BorderLayout.SOUTH);

        return panel;
    }

    // ==================== MAIN MENU PANEL ====================
    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(BG_COLOR);

        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        JLabel headerLabel = new JLabel("CONCERT MANAGEMENT DASHBOARD");
        headerLabel.setFont(new Font("Arial", Font.BOLD, 32));
        headerLabel.setForeground(ACCENT_COLOR);
        headerPanel.add(headerLabel);
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel menuGrid = new JPanel(new GridLayout(3, 3, 20, 20));
        menuGrid.setBackground(BG_COLOR);
        menuGrid.setBorder(new EmptyBorder(30, 30, 30, 30));

        menuGrid.add(createMenuCard("Events", "Manage concerts", "EVENTS"));
        menuGrid.add(createMenuCard("Book Tickets", "Purchase tickets", "BOOKING"));
        menuGrid.add(createMenuCard("Artists", "Artist management", "ARTISTS"));
        menuGrid.add(createMenuCard("Users", "User management", "USERS"));
        menuGrid.add(createMenuCard("Crew", "Crew management", "CREW"));
        menuGrid.add(createMenuCard("Ticket Types", "Manage ticket types", "TICKET_TYPES"));
        menuGrid.add(createMenuCard("Collaborations", "Artist collaborations", "COLLABORATIONS"));
        menuGrid.add(createMenuCard("Reports", "Analytics & reports", "REPORTS"));
        menuGrid.add(createMenuCard("Exit", "Close application", "EXIT"));

        panel.add(menuGrid, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createMenuCard(String title, String description, String action) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(20, 20, 20, 20)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(ACCENT_COLOR);

        JLabel descLabel = new JLabel(description, SwingConstants.CENTER);
        descLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        descLabel.setForeground(Color.LIGHT_GRAY);

        card.add(titleLabel, BorderLayout.CENTER);
        card.add(descLabel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (action.equals("EXIT")) {
                    int confirm = JOptionPane.showConfirmDialog(ConcertTicketBookingSystem.this,
                            "Are you sure you want to exit?", "Confirm Exit",
                            JOptionPane.YES_NO_OPTION);
                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            if (connection != null && !connection.isClosed()) {
                                connection.close();
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                        }
                        System.exit(0);
                    }
                } else {
                    cardLayout.show(mainPanel, action);
                }
            }
            public void mouseEntered(MouseEvent e) {
                card.setBackground(PRIMARY_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
            }
        });

        return card;
    }

    // ==================== EVENTS PANEL ====================
    private JPanel createEventsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Event Management", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Event ID", "Event Name", "Venue", "Date", "Total Seats"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = createStyledTable(model);

        loadEvents(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setBackground(BG_COLOR);

        JButton addBtn = createStyledButton("Add Event", PRIMARY_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> showAddEventDialog(model));

        JButton assignArtistBtn = createStyledButton("Assign Artist", ACCENT_COLOR, Color.BLACK);
        assignArtistBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int eventId = (int) model.getValueAt(row, 0);
                showAssignArtistDialog(eventId);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select an event first!");
            }
        });

        JButton assignCrewBtn = createStyledButton("Assign Crew", new Color(0, 150, 136), Color.WHITE);
        assignCrewBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int eventId = (int) model.getValueAt(row, 0);
                showAssignCrewDialog(eventId);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select an event first!");
            }
        });

        JButton viewBtn = createStyledButton("View Details", new Color(33, 150, 243), Color.WHITE);
        viewBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) {
                int eventId = (int) model.getValueAt(row, 0);
                showEventDetails(eventId);
            } else {
                JOptionPane.showMessageDialog(panel, "Please select an event first!");
            }
        });

        JButton refreshBtn = createStyledButton("Refresh", SECONDARY_COLOR, Color.WHITE);
        refreshBtn.addActionListener(e -> loadEvents(model));

        buttonPanel.add(addBtn);
        buttonPanel.add(assignArtistBtn);
        buttonPanel.add(assignCrewBtn);
        buttonPanel.add(viewBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadEvents(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM EVENTS ORDER BY EVENT_DATE DESC");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("EVENT_ID"),
                        rs.getString("EVENT_NAME"),
                        rs.getString("VENUE"),
                        rs.getDate("EVENT_DATE"),
                        rs.getInt("TOTAL_SEATS")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showError("Error loading events: " + e.getMessage());
        }
    }

    private void showAddEventDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New Event", true);
        dialog.setSize(500, 450);
        dialog.setLocationRelativeTo(this);
        dialog.getContentPane().setBackground(BG_COLOR);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField venueField = new JTextField(20);
        JTextField dateField = new JTextField(20);
        dateField.setToolTipText("Format: YYYY-MM-DD (e.g., 2025-12-31)");
        JTextField seatsField = new JTextField(20);

        addFormField(panel, gbc, "Event ID:", idField, 0);
        addFormField(panel, gbc, "Event Name:", nameField, 1);
        addFormField(panel, gbc, "Venue:", venueField, 2);
        addFormField(panel, gbc, "Date (YYYY-MM-DD):", dateField, 3);
        addFormField(panel, gbc, "Total Seats:", seatsField, 4);

        JButton saveBtn = createStyledButton("Save Event", ACCENT_COLOR, Color.BLACK);
        saveBtn.addActionListener(e -> {
            try {
                if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        venueField.getText().trim().isEmpty() || dateField.getText().trim().isEmpty() ||
                        seatsField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Please fill all fields!");
                    return;
                }

                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO EVENTS VALUES (?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                pstmt.setString(2, nameField.getText().trim());
                pstmt.setString(3, venueField.getText().trim());
                pstmt.setDate(4, java.sql.Date.valueOf(dateField.getText().trim()));
                pstmt.setInt(5, Integer.parseInt(seatsField.getText().trim()));
                pstmt.executeUpdate();
                pstmt.close();

                JOptionPane.showMessageDialog(dialog, "Event added successfully!");
                loadEvents(model);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError("Invalid number format! Please check Event ID and Total Seats.");
            } catch (IllegalArgumentException ex) {
                showError("Invalid date format! Please use YYYY-MM-DD format.");
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    showError("Event ID already exists! Please use a different ID.");
                } else {
                    showError("Database error: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showAssignArtistDialog(int eventId) {
        JDialog dialog = new JDialog(this, "Assign Artist to Event", true);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> artistCombo = new JComboBox<>();
        loadComboBoxData(artistCombo, "SELECT ARTIST_ID, ARTIST_NAME FROM ARTISTS ORDER BY ARTIST_NAME");

        if (artistCombo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(dialog, "No artists available! Please add artists first.");
            dialog.dispose();
            return;
        }

        addFormField(panel, gbc, "Select Artist:", artistCombo, 0);

        JButton assignBtn = createStyledButton("Assign", ACCENT_COLOR, Color.BLACK);
        assignBtn.addActionListener(e -> {
            try {
                if (artistCombo.getSelectedItem() != null) {
                    String selected = artistCombo.getSelectedItem().toString();
                    int artistId = Integer.parseInt(selected.split(" - ")[0]);

                    PreparedStatement pstmt = connection.prepareStatement(
                            "INSERT INTO EVENT_ARTIST (EVENT_ID, ARTIST_ID) VALUES (?, ?)");
                    pstmt.setInt(1, eventId);
                    pstmt.setInt(2, artistId);
                    pstmt.executeUpdate();
                    pstmt.close();

                    JOptionPane.showMessageDialog(dialog, "Artist assigned successfully!");
                    dialog.dispose();
                }
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    showError("This artist is already assigned to this event!");
                } else {
                    showError("Error assigning artist: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(assignBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showAssignCrewDialog(int eventId) {
        JDialog dialog = new JDialog(this, "Assign Crew to Event", true);
        dialog.setSize(450, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> crewCombo = new JComboBox<>();
        loadComboBoxData(crewCombo, "SELECT CREW_ID, CREW_NAME FROM CREW ORDER BY CREW_NAME");

        if (crewCombo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(dialog, "No crew members available! Please add crew first.");
            dialog.dispose();
            return;
        }

        addFormField(panel, gbc, "Select Crew:", crewCombo, 0);

        JButton assignBtn = createStyledButton("Assign", ACCENT_COLOR, Color.BLACK);
        assignBtn.addActionListener(e -> {
            try {
                if (crewCombo.getSelectedItem() != null) {
                    String selected = crewCombo.getSelectedItem().toString();
                    int crewId = Integer.parseInt(selected.split(" - ")[0]);

                    PreparedStatement pstmt = connection.prepareStatement(
                            "INSERT INTO EVENT_CREW (EVENT_ID, CREW_ID) VALUES (?, ?)");
                    pstmt.setInt(1, eventId);
                    pstmt.setInt(2, crewId);
                    pstmt.executeUpdate();
                    pstmt.close();

                    JOptionPane.showMessageDialog(dialog, "Crew assigned successfully!");
                    dialog.dispose();
                }
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    showError("This crew member is already assigned to this event!");
                } else {
                    showError("Error assigning crew: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(assignBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showEventDetails(int eventId) {
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT e.*, " +
                            "(SELECT GROUP_CONCAT(a.ARTIST_NAME SEPARATOR ', ') " +
                            " FROM EVENT_ARTIST ea JOIN ARTISTS a ON ea.ARTIST_ID = a.ARTIST_ID " +
                            " WHERE ea.EVENT_ID = e.EVENT_ID) as artists, " +
                            "(SELECT GROUP_CONCAT(c.CREW_NAME SEPARATOR ', ') " +
                            " FROM EVENT_CREW ec JOIN CREW c ON ec.CREW_ID = c.CREW_ID " +
                            " WHERE ec.EVENT_ID = e.EVENT_ID) as crew " +
                            "FROM EVENTS e WHERE e.EVENT_ID = ?");
            pstmt.setInt(1, eventId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String artists = rs.getString("artists");
                String crew = rs.getString("crew");

                String details = String.format(
                        "EVENT DETAILS\n" +
                                "═══════════════════════════════════\n\n" +
                                "Event ID: %d\n" +
                                "Event Name: %s\n" +
                                "Venue: %s\n" +
                                "Date: %s\n" +
                                "Total Seats: %d\n\n" +
                                "ASSIGNED ARTISTS:\n" +
                                "%s\n\n" +
                                "ASSIGNED CREW:\n" +
                                "%s",
                        rs.getInt("EVENT_ID"),
                        rs.getString("EVENT_NAME"),
                        rs.getString("VENUE"),
                        rs.getDate("EVENT_DATE"),
                        rs.getInt("TOTAL_SEATS"),
                        (artists != null && !artists.isEmpty()) ? artists : "No artists assigned yet",
                        (crew != null && !crew.isEmpty()) ? crew : "No crew assigned yet"
                );

                JTextArea textArea = new JTextArea(details);
                textArea.setEditable(false);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
                textArea.setBackground(CARD_BG);
                textArea.setForeground(Color.WHITE);
                textArea.setBorder(new EmptyBorder(10, 10, 10, 10));

                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new Dimension(500, 350));

                JOptionPane.showMessageDialog(this, scrollPane, "Event Details",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            showError("Error loading event details: " + e.getMessage());
        }
    }

    // ==================== BOOKING PANEL ====================
    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Ticket Booking", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(CARD_BG);
        formPanel.setBorder(new CompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(30, 30, 30, 30)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> eventCombo = new JComboBox<>();
        JComboBox<String> userCombo = new JComboBox<>();
        JComboBox<String> ticketTypeCombo = new JComboBox<>();
        JSpinner ticketSpinner = new JSpinner(new SpinnerNumberModel(1, 1, 10, 1));
        JComboBox<String> paymentCombo = new JComboBox<>(
                new String[]{"Credit Card", "Debit Card", "UPI", "Cash"});
        JTextField seatField = new JTextField(20);

        loadComboBoxData(eventCombo, "SELECT EVENT_ID, EVENT_NAME FROM EVENTS ORDER BY EVENT_DATE DESC");
        loadComboBoxData(userCombo, "SELECT USER_ID, USER_NAME FROM USERS ORDER BY USER_NAME");

        eventCombo.addActionListener(e -> {
            if (eventCombo.getSelectedItem() != null) {
                String selected = eventCombo.getSelectedItem().toString();
                String eventId = selected.split(" - ")[0];
                loadTicketTypes(ticketTypeCombo, eventId);
            }
        });

        // Load ticket types for first event
        if (eventCombo.getItemCount() > 0) {
            String firstEvent = eventCombo.getItemAt(0).toString();
            loadTicketTypes(ticketTypeCombo, firstEvent.split(" - ")[0]);
        }

        addFormField(formPanel, gbc, "Select Event:", eventCombo, 0);
        addFormField(formPanel, gbc, "Select User:", userCombo, 1);
        addFormField(formPanel, gbc, "Ticket Type:", ticketTypeCombo, 2);
        addFormField(formPanel, gbc, "Number of Tickets:", ticketSpinner, 3);
        addFormField(formPanel, gbc, "Seat Number:", seatField, 4);
        addFormField(formPanel, gbc, "Payment Method:", paymentCombo, 5);

        JButton bookBtn = createStyledButton("BOOK NOW", ACCENT_COLOR, Color.BLACK);
        bookBtn.setFont(new Font("Arial", Font.BOLD, 18));
        bookBtn.addActionListener(e -> processBooking(eventCombo, userCombo, ticketTypeCombo,
                ticketSpinner, paymentCombo, seatField));

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(bookBtn, gbc);

        panel.add(formPanel, BorderLayout.CENTER);

        return panel;
    }

    private void loadTicketTypes(JComboBox<String> combo, String eventId) {
        combo.removeAllItems();
        try {
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT TICKETTYPE_ID, TYPE_NAME, PRICE FROM TICKET_TYPE WHERE EVENT_ID = ? ORDER BY PRICE");
            pstmt.setInt(1, Integer.parseInt(eventId));
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                combo.addItem(rs.getInt(1) + " - " + rs.getString(2) + " (₹" +
                        String.format("%.2f", rs.getDouble(3)) + ")");
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            showError("Error loading ticket types: " + e.getMessage());
        }
    }

    private void processBooking(JComboBox<String> eventCombo, JComboBox<String> userCombo,
                                JComboBox<String> ticketTypeCombo, JSpinner ticketSpinner,
                                JComboBox<String> paymentCombo, JTextField seatField) {
        try {
            if (eventCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No events available! Please add events first.");
                return;
            }
            if (userCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No users available! Please add users first.");
                return;
            }
            if (ticketTypeCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "No ticket types available for this event!");
                return;
            }
            if (seatField.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter a seat number!");
                return;
            }

            int eventId = Integer.parseInt(eventCombo.getSelectedItem().toString().split(" - ")[0]);
            int userId = Integer.parseInt(userCombo.getSelectedItem().toString().split(" - ")[0]);
            int ticketTypeId = Integer.parseInt(ticketTypeCombo.getSelectedItem().toString().split(" - ")[0]);
            int numTickets = (int) ticketSpinner.getValue();
            String seatNo = seatField.getText().trim();

            // Get price
            PreparedStatement pstmt = connection.prepareStatement(
                    "SELECT PRICE FROM TICKET_TYPE WHERE TICKETTYPE_ID = ?");
            pstmt.setInt(1, ticketTypeId);
            ResultSet rs = pstmt.executeQuery();

            double price = 0;
            if (rs.next()) {
                price = rs.getDouble(1);
            }
            rs.close();
            pstmt.close();

            double totalAmount = price * numTickets;

            // Generate unique IDs
            int paymentId = generateUniqueId("PAYMENTS", "PAYMENT_ID");
            int bookingId = generateUniqueId("BOOKINGS", "BOOKING_ID");

            // Insert payment
            pstmt = connection.prepareStatement(
                    "INSERT INTO PAYMENTS (PAYMENT_ID, PAYMENT_DATE, AMOUNT, PAYMENT_METHOD, STATUS) " +
                            "VALUES (?, CURDATE(), ?, ?, 'COMPLETED')");
            pstmt.setInt(1, paymentId);
            pstmt.setDouble(2, totalAmount);
            pstmt.setString(3, paymentCombo.getSelectedItem().toString());
            pstmt.executeUpdate();
            pstmt.close();

            // Insert booking
            pstmt = connection.prepareStatement(
                    "INSERT INTO BOOKINGS (BOOKING_ID, USER_ID, EVENT_ID, TICKETTYPE_ID, " +
                            "PAYMENT_ID, BOOKING_DATE, NO_OF_TICKETS) VALUES (?, ?, ?, ?, ?, CURDATE(), ?)");
            pstmt.setInt(1, bookingId);
            pstmt.setInt(2, userId);
            pstmt.setInt(3, eventId);
            pstmt.setInt(4, ticketTypeId);
            pstmt.setInt(5, paymentId);
            pstmt.setInt(6, numTickets);
            pstmt.executeUpdate();
            pstmt.close();

            // Insert ticket
            pstmt = connection.prepareStatement(
                    "INSERT INTO TICKETS (BOOKING_ID, TICKETTYPE_ID, SEAT_NO) VALUES (?, ?, ?)");
            pstmt.setInt(1, bookingId);
            pstmt.setInt(2, ticketTypeId);
            pstmt.setString(3, seatNo);
            pstmt.executeUpdate();
            pstmt.close();

            String successMsg = String.format(
                    "BOOKING SUCCESSFUL!\n\n" +
                            "Booking ID: %d\n" +
                            "Payment ID: %d\n" +
                            "Seat Number: %s\n" +
                            "Number of Tickets: %d\n" +
                            "Total Amount: ₹%.2f\n\n" +
                            "Payment Status: COMPLETED",
                    bookingId, paymentId, seatNo, numTickets, totalAmount);

            JOptionPane.showMessageDialog(this, successMsg, "Booking Confirmed",
                    JOptionPane.INFORMATION_MESSAGE);

            // Clear form
            seatField.setText("");
            ticketSpinner.setValue(1);

        } catch (Exception e) {
            showError("Booking failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private int generateUniqueId(String tableName, String columnName) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet rs = stmt.executeQuery(
                "SELECT MAX(" + columnName + ") as maxId FROM " + tableName);
        int maxId = 0;
        if (rs.next()) {
            maxId = rs.getInt("maxId");
        }
        rs.close();
        stmt.close();
        return maxId + 1;
    }

    // ==================== ARTISTS PANEL ====================
    private JPanel createArtistsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Artist Management", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Artist ID", "Artist Name", "Genre"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = createStyledTable(model);

        loadArtists(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BG_COLOR);

        JButton addBtn = createStyledButton("Add Artist", PRIMARY_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> showAddArtistDialog(model));

        JButton refreshBtn = createStyledButton("Refresh", SECONDARY_COLOR, Color.WHITE);
        refreshBtn.addActionListener(e -> loadArtists(model));

        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadArtists(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM ARTISTS ORDER BY ARTIST_NAME");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("ARTIST_ID"),
                        rs.getString("ARTIST_NAME"),
                        rs.getString("GENRE")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showError("Error loading artists: " + e.getMessage());
        }
    }

    private void showAddArtistDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New Artist", true);
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField genreField = new JTextField(20);

        addFormField(panel, gbc, "Artist ID:", idField, 0);
        addFormField(panel, gbc, "Artist Name:", nameField, 1);
        addFormField(panel, gbc, "Genre:", genreField, 2);

        JButton saveBtn = createStyledButton("Save Artist", ACCENT_COLOR, Color.BLACK);
        saveBtn.addActionListener(e -> {
            try {
                if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "Artist ID and Name are required!");
                    return;
                }

                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO ARTISTS VALUES (?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                pstmt.setString(2, nameField.getText().trim());
                pstmt.setString(3, genreField.getText().trim().isEmpty() ? null : genreField.getText().trim());
                pstmt.executeUpdate();
                pstmt.close();

                JOptionPane.showMessageDialog(dialog, "Artist added successfully!");
                loadArtists(model);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError("Invalid Artist ID! Please enter a number.");
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    showError("Artist ID already exists!");
                } else {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== USERS PANEL ====================
    private JPanel createUsersPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("User Management", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"User ID", "Name", "Email", "Phone"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = createStyledTable(model);

        loadUsers(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BG_COLOR);

        JButton addBtn = createStyledButton("Add User", PRIMARY_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> showAddUserDialog(model));

        JButton refreshBtn = createStyledButton("Refresh", SECONDARY_COLOR, Color.WHITE);
        refreshBtn.addActionListener(e -> loadUsers(model));

        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadUsers(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM USERS ORDER BY USER_NAME");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("USER_ID"),
                        rs.getString("USER_NAME"),
                        rs.getString("EMAIL"),
                        rs.getString("PHONE")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showError("Error loading users: " + e.getMessage());
        }
    }

    private void showAddUserDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New User", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField emailField = new JTextField(20);
        JTextField phoneField = new JTextField(20);

        addFormField(panel, gbc, "User ID:", idField, 0);
        addFormField(panel, gbc, "Name:", nameField, 1);
        addFormField(panel, gbc, "Email:", emailField, 2);
        addFormField(panel, gbc, "Phone:", phoneField, 3);

        JButton saveBtn = createStyledButton("Save User", ACCENT_COLOR, Color.BLACK);
        saveBtn.addActionListener(e -> {
            try {
                if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        emailField.getText().trim().isEmpty() || phoneField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required!");
                    return;
                }

                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO USERS VALUES (?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                pstmt.setString(2, nameField.getText().trim());
                pstmt.setString(3, emailField.getText().trim());
                pstmt.setString(4, phoneField.getText().trim());
                pstmt.executeUpdate();
                pstmt.close();

                JOptionPane.showMessageDialog(dialog, "User added successfully!");
                loadUsers(model);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError("Invalid User ID! Please enter a number.");
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    if (ex.getMessage().contains("EMAIL")) {
                        showError("Email already exists!");
                    } else if (ex.getMessage().contains("PHONE")) {
                        showError("Phone number already exists!");
                    } else {
                        showError("User ID already exists!");
                    }
                } else {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== CREW PANEL ====================
    private JPanel createCrewPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Crew Management", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Crew ID", "Name", "Role", "Contact"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = createStyledTable(model);

        loadCrew(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BG_COLOR);

        JButton addBtn = createStyledButton("Add Crew", PRIMARY_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> showAddCrewDialog(model));

        JButton refreshBtn = createStyledButton("Refresh", SECONDARY_COLOR, Color.WHITE);
        refreshBtn.addActionListener(e -> loadCrew(model));

        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadCrew(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM CREW ORDER BY CREW_NAME");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("CREW_ID"),
                        rs.getString("CREW_NAME"),
                        rs.getString("ROLE"),
                        rs.getString("CONTACT")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showError("Error loading crew: " + e.getMessage());
        }
    }

    private void showAddCrewDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New Crew Member", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JTextField nameField = new JTextField(20);
        JTextField roleField = new JTextField(20);
        JTextField contactField = new JTextField(20);

        addFormField(panel, gbc, "Crew ID:", idField, 0);
        addFormField(panel, gbc, "Name:", nameField, 1);
        addFormField(panel, gbc, "Role:", roleField, 2);
        addFormField(panel, gbc, "Contact:", contactField, 3);

        JButton saveBtn = createStyledButton("Save Crew", ACCENT_COLOR, Color.BLACK);
        saveBtn.addActionListener(e -> {
            try {
                if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        roleField.getText().trim().isEmpty() || contactField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required!");
                    return;
                }

                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO CREW VALUES (?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                pstmt.setString(2, nameField.getText().trim());
                pstmt.setString(3, roleField.getText().trim());
                pstmt.setString(4, contactField.getText().trim());
                pstmt.executeUpdate();
                pstmt.close();

                JOptionPane.showMessageDialog(dialog, "Crew member added successfully!");
                loadCrew(model);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError("Invalid Crew ID! Please enter a number.");
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    if (ex.getMessage().contains("CONTACT")) {
                        showError("Contact number already exists!");
                    } else {
                        showError("Crew ID already exists!");
                    }
                } else {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== TICKET TYPES PANEL ====================
    private JPanel createTicketTypesPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Ticket Type Management", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Type ID", "Event ID", "Type Name", "Price", "Total Seats"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = createStyledTable(model);

        loadTicketTypesTable(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BG_COLOR);

        JButton addBtn = createStyledButton("Add Ticket Type", PRIMARY_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> showAddTicketTypeDialog(model));

        JButton refreshBtn = createStyledButton("Refresh", SECONDARY_COLOR, Color.WHITE);
        refreshBtn.addActionListener(e -> loadTicketTypesTable(model));

        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadTicketTypesTable(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM TICKET_TYPE ORDER BY EVENT_ID, PRICE");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("TICKETTYPE_ID"),
                        rs.getInt("EVENT_ID"),
                        rs.getString("TYPE_NAME"),
                        String.format("₹%.2f", rs.getDouble("PRICE")),
                        rs.getInt("TOTAL_SEATS")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showError("Error loading ticket types: " + e.getMessage());
        }
    }

    private void showAddTicketTypeDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New Ticket Type", true);
        dialog.setSize(550, 500);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JComboBox<String> eventCombo = new JComboBox<>();
        JTextField typeNameField = new JTextField(20);
        JTextField priceField = new JTextField(20);
        JTextField seatsField = new JTextField(20);

        loadComboBoxData(eventCombo, "SELECT EVENT_ID, EVENT_NAME FROM EVENTS ORDER BY EVENT_DATE DESC");

        if (eventCombo.getItemCount() == 0) {
            JOptionPane.showMessageDialog(dialog, "No events available! Please add events first.");
            dialog.dispose();
            return;
        }

        addFormField(panel, gbc, "Type ID:", idField, 0);
        addFormField(panel, gbc, "Event:", eventCombo, 1);
        addFormField(panel, gbc, "Type Name:", typeNameField, 2);
        addFormField(panel, gbc, "Price:", priceField, 3);
        addFormField(panel, gbc, "Total Seats:", seatsField, 4);

        JButton saveBtn = createStyledButton("Save Ticket Type", ACCENT_COLOR, Color.BLACK);
        saveBtn.addActionListener(e -> {
            try {
                if (idField.getText().trim().isEmpty() || typeNameField.getText().trim().isEmpty() ||
                        priceField.getText().trim().isEmpty() || seatsField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required!");
                    return;
                }

                int eventId = Integer.parseInt(eventCombo.getSelectedItem().toString().split(" - ")[0]);
                double price = Double.parseDouble(priceField.getText().trim());

                if (price <= 0) {
                    JOptionPane.showMessageDialog(dialog, "Price must be greater than 0!");
                    return;
                }

                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO TICKET_TYPE VALUES (?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                pstmt.setInt(2, eventId);
                pstmt.setString(3, typeNameField.getText().trim());
                pstmt.setDouble(4, price);
                pstmt.setInt(5, Integer.parseInt(seatsField.getText().trim()));
                pstmt.executeUpdate();
                pstmt.close();

                JOptionPane.showMessageDialog(dialog, "Ticket type added successfully!");
                loadTicketTypesTable(model);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError("Invalid number format! Please check your inputs.");
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    showError("Ticket Type ID already exists!");
                } else {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== COLLABORATIONS PANEL ====================
    private JPanel createCollaborationsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Artist Collaborations", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        String[] columns = {"Collab ID", "Artist 1", "Artist 2", "Collaboration Name", "Type", "Start Date"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        JTable table = createStyledTable(model);

        loadCollaborations(model);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 2));
        panel.add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(BG_COLOR);

        JButton addBtn = createStyledButton("Add Collaboration", PRIMARY_COLOR, Color.WHITE);
        addBtn.addActionListener(e -> showAddCollaborationDialog(model));

        JButton refreshBtn = createStyledButton("Refresh", SECONDARY_COLOR, Color.WHITE);
        refreshBtn.addActionListener(e -> loadCollaborations(model));

        buttonPanel.add(addBtn);
        buttonPanel.add(refreshBtn);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void loadCollaborations(DefaultTableModel model) {
        model.setRowCount(0);
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT ac.*, a1.ARTIST_NAME as artist1_name, a2.ARTIST_NAME as artist2_name " +
                            "FROM ARTIST_COLLABORATION ac " +
                            "JOIN ARTISTS a1 ON ac.ARTIST1_ID = a1.ARTIST_ID " +
                            "JOIN ARTISTS a2 ON ac.ARTIST2_ID = a2.ARTIST_ID " +
                            "ORDER BY ac.START_DATE DESC");
            while (rs.next()) {
                model.addRow(new Object[]{
                        rs.getInt("COLLAB_ID"),
                        rs.getString("artist1_name"),
                        rs.getString("artist2_name"),
                        rs.getString("COLLAB_NAME"),
                        rs.getString("COLLAB_TYPE"),
                        rs.getDate("START_DATE")
                });
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showError("Error loading collaborations: " + e.getMessage());
        }
    }

    private void showAddCollaborationDialog(DefaultTableModel model) {
        JDialog dialog = new JDialog(this, "Add New Collaboration", true);
        dialog.setSize(600, 550);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(CARD_BG);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField idField = new JTextField(20);
        JComboBox<String> artist1Combo = new JComboBox<>();
        JComboBox<String> artist2Combo = new JComboBox<>();
        JTextField nameField = new JTextField(20);
        JTextField typeField = new JTextField(20);
        JTextField dateField = new JTextField(20);
        dateField.setToolTipText("Format: YYYY-MM-DD");

        loadComboBoxData(artist1Combo, "SELECT ARTIST_ID, ARTIST_NAME FROM ARTISTS ORDER BY ARTIST_NAME");
        loadComboBoxData(artist2Combo, "SELECT ARTIST_ID, ARTIST_NAME FROM ARTISTS ORDER BY ARTIST_NAME");

        if (artist1Combo.getItemCount() < 2) {
            JOptionPane.showMessageDialog(dialog, "Need at least 2 artists for collaboration!");
            dialog.dispose();
            return;
        }

        addFormField(panel, gbc, "Collab ID:", idField, 0);
        addFormField(panel, gbc, "Artist 1:", artist1Combo, 1);
        addFormField(panel, gbc, "Artist 2:", artist2Combo, 2);
        addFormField(panel, gbc, "Collaboration Name:", nameField, 3);
        addFormField(panel, gbc, "Type:", typeField, 4);
        addFormField(panel, gbc, "Start Date (YYYY-MM-DD):", dateField, 5);

        JButton saveBtn = createStyledButton("Save Collaboration", ACCENT_COLOR, Color.BLACK);
        saveBtn.addActionListener(e -> {
            try {
                if (idField.getText().trim().isEmpty() || nameField.getText().trim().isEmpty() ||
                        typeField.getText().trim().isEmpty() || dateField.getText().trim().isEmpty()) {
                    JOptionPane.showMessageDialog(dialog, "All fields are required!");
                    return;
                }

                int artist1Id = Integer.parseInt(artist1Combo.getSelectedItem().toString().split(" - ")[0]);
                int artist2Id = Integer.parseInt(artist2Combo.getSelectedItem().toString().split(" - ")[0]);

                if (artist1Id == artist2Id) {
                    JOptionPane.showMessageDialog(dialog, "Please select two different artists!");
                    return;
                }

                PreparedStatement pstmt = connection.prepareStatement(
                        "INSERT INTO ARTIST_COLLABORATION VALUES (?, ?, ?, ?, ?, ?)");
                pstmt.setInt(1, Integer.parseInt(idField.getText().trim()));
                pstmt.setInt(2, artist1Id);
                pstmt.setInt(3, artist2Id);
                pstmt.setString(4, nameField.getText().trim());
                pstmt.setString(5, typeField.getText().trim());
                pstmt.setDate(6, java.sql.Date.valueOf(dateField.getText().trim()));
                pstmt.executeUpdate();
                pstmt.close();

                JOptionPane.showMessageDialog(dialog, "Collaboration added successfully!");
                loadCollaborations(model);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                showError("Invalid Collab ID! Please enter a number.");
            } catch (IllegalArgumentException ex) {
                showError("Invalid date format! Please use YYYY-MM-DD format.");
            } catch (SQLException ex) {
                if (ex.getMessage().contains("Duplicate entry")) {
                    showError("Collaboration ID already exists!");
                } else {
                    showError("Error: " + ex.getMessage());
                }
            }
        });

        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(saveBtn, gbc);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    // ==================== REPORTS PANEL ====================
    // ==================== REPORTS PANEL ====================
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Reports & Analytics", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel reportsGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        reportsGrid.setBackground(BG_COLOR);
        reportsGrid.setBorder(new EmptyBorder(20, 20, 20, 20));

        reportsGrid.add(createReportCard("Total Revenue", "₹", this::showRevenueReport));
        reportsGrid.add(createReportCard("Event Statistics", "#", this::showEventStats));
        reportsGrid.add(createReportCard("User Bookings", "U", this::showUserBookings));
        reportsGrid.add(createReportCard("Artist Performance", "*", this::showArtistPerformance));

        panel.add(reportsGrid, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportCard(String title, String icon, Runnable action) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(30, 30, 30, 30)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 60));
        iconLabel.setForeground(ACCENT_COLOR);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(ACCENT_COLOR);

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
            public void mouseEntered(MouseEvent e) {
                card.setBackground(PRIMARY_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
            }
        });

        return card;
    }

    // ==================== REVENUE REPORT USING STORED PROCEDURE ====================
    private void showRevenueReport() {
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            // Call stored procedure
            cstmt = connection.prepareCall("{CALL show_revenue_report_data()}");

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════\n");
            report.append("        TOTAL REVENUE REPORT\n");
            report.append("═══════════════════════════════════════\n\n");

            // Execute and get first result set (total revenue)
            boolean hasResults = cstmt.execute();
            if (hasResults) {
                rs = cstmt.getResultSet();
                double total = 0;
                if (rs.next()) {
                    total = rs.getDouble("total_revenue");
                }
                rs.close();

                report.append(String.format("Total Revenue: ₹%.2f\n\n", total));
                report.append("Revenue by Event:\n");
                report.append("───────────────────────────────────────\n");

                // Get second result set (event-wise revenue)
                if (cstmt.getMoreResults()) {
                    rs = cstmt.getResultSet();
                    while (rs.next()) {
                        report.append(String.format("%-30s ₹%,10.2f\n",
                                rs.getString("event_name"),
                                rs.getDouble("revenue")));
                    }
                }
            }

            showReportDialog(report.toString(), "Revenue Report");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // ==================== EVENT STATISTICS USING STORED PROCEDURE ====================
    private void showEventStats() {
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            // Call stored procedure
            cstmt = connection.prepareCall("{CALL show_event_stats_data()}");
            boolean hasResults = cstmt.execute();

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append("                    EVENT STATISTICS\n");
            report.append("═══════════════════════════════════════════════════════════════\n\n");
            report.append(String.format("%-30s %10s %10s %12s %10s\n",
                    "Event", "Total", "Bookings", "Tickets Sold", "Occupancy%"));
            report.append("───────────────────────────────────────────────────────────────\n");

            if (hasResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    String eventName = rs.getString("event_name");
                    eventName = eventName.substring(0, Math.min(30, eventName.length()));

                    report.append(String.format("%-30s %10d %10d %12d %10.2f%%\n",
                            eventName,
                            rs.getInt("total_seats"),
                            rs.getInt("total_bookings"),
                            rs.getInt("tickets_sold"),
                            rs.getDouble("occupancy_pct")));
                }
            }

            showReportDialog(report.toString(), "Event Statistics");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showUserBookings() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT u.USER_NAME, " +
                            "COUNT(DISTINCT b.BOOKING_ID) as total_bookings, " +
                            "COALESCE(SUM(b.NO_OF_TICKETS), 0) as total_tickets, " +
                            "COALESCE(SUM(p.AMOUNT), 0) as total_spent " +
                            "FROM USERS u " +
                            "LEFT JOIN BOOKINGS b ON u.USER_ID = b.USER_ID " +
                            "LEFT JOIN PAYMENTS p ON b.PAYMENT_ID = p.PAYMENT_ID AND p.STATUS = 'COMPLETED' " +
                            "GROUP BY u.USER_ID, u.USER_NAME " +
                            "ORDER BY total_bookings DESC");

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append("                  USER BOOKINGS REPORT\n");
            report.append("═══════════════════════════════════════════════════════════════\n\n");
            report.append(String.format("%-25s %10s %10s %12s\n",
                    "User", "Bookings", "Tickets", "Total Spent"));
            report.append("───────────────────────────────────────────────────────────────\n");

            while (rs.next()) {
                report.append(String.format("%-25s %10d %10d ₹%,10.2f\n",
                        rs.getString("USER_NAME").substring(0, Math.min(25, rs.getString("USER_NAME").length())),
                        rs.getInt("total_bookings"),
                        rs.getInt("total_tickets"),
                        rs.getDouble("total_spent")));
            }

            rs.close();
            stmt.close();

            showReportDialog(report.toString(), "User Bookings");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        }
    }

    // ==================== USER BOOKINGS USING STORED PROCEDURE ====================
   /* private void showUserBookings() {
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            // Call stored procedure
            cstmt = connection.prepareCall("{CALL show_user_bookings_data()}");
            boolean hasResults = cstmt.execute();

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append("                    USER BOOKINGS REPORT\n");
            report.append("═══════════════════════════════════════════════════════════════\n\n");
            report.append(String.format("%-20s %10s %10s %15s\n",
                    "Username", "Bookings", "Tickets", "Total Spent"));
            report.append("───────────────────────────────────────────────────────────────\n");

            if (hasResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    String username = rs.getString("username");
                    username = username.substring(0, Math.min(20, username.length()));

                    report.append(String.format("%-20s %10d %10d ₹%,14.2f\n",
                            username,
                            rs.getInt("total_bookings"),
                            rs.getInt("total_tickets"),
                            rs.getDouble("total_spent")));
                }
            }

            showReportDialog(report.toString(), "User Bookings");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    // ==================== ARTIST PERFORMANCE USING STORED PROCEDURE ====================
    /*private void showArtistPerformance() {
        CallableStatement cstmt = null;
        ResultSet rs = null;

        try {
            // Call stored procedure
            cstmt = connection.prepareCall("{CALL show_artist_performance_data()}");
            boolean hasResults = cstmt.execute();

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append("                ARTIST PERFORMANCE REPORT\n");
            report.append("═══════════════════════════════════════════════════════════════\n\n");
            report.append(String.format("%-25s %-15s %8s %10s %15s\n",
                    "Artist", "Genre", "Events", "Tickets", "Revenue"));
            report.append("───────────────────────────────────────────────────────────────\n");

            if (hasResults) {
                rs = cstmt.getResultSet();
                while (rs.next()) {
                    String artistName = rs.getString("artist_name");
                    artistName = artistName.substring(0, Math.min(25, artistName.length()));

                    String genre = rs.getString("genre");
                    genre = genre.substring(0, Math.min(15, genre.length()));

                    report.append(String.format("%-25s %-15s %8d %10d ₹%,13.2f\n",
                            artistName,
                            genre,
                            rs.getInt("total_events"),
                            rs.getInt("total_tickets_sold"),
                            rs.getDouble("total_revenue")));
                }
            }

            showReportDialog(report.toString(), "Artist Performance");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (cstmt != null) cstmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }*/

    // ==================== HELPER METHOD FOR SHOWING REPORT DIALOG ====================
    private void showReportDialog(String reportText, String title) {
        JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), title, true);
        dialog.setLayout(new BorderLayout(10, 10));
        dialog.setSize(700, 500);
        dialog.setLocationRelativeTo(this);

        JTextArea textArea = new JTextArea(reportText);
        textArea.setFont(new Font("Courier New", Font.PLAIN, 12));
        textArea.setEditable(false);
        textArea.setBackground(CARD_BG);
        textArea.setForeground(ACCENT_COLOR);
        textArea.setMargin(new Insets(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton closeButton = new JButton("Close");
        closeButton.setBackground(PRIMARY_COLOR);
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.setFont(new Font("Arial", Font.BOLD, 14));
        closeButton.addActionListener(e -> dialog.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(BG_COLOR);
        buttonPanel.add(closeButton);

        dialog.add(scrollPane, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.getContentPane().setBackground(BG_COLOR);

        dialog.setVisible(true);
    }



    /*
    private JPanel createReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(BG_COLOR);
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));

        JPanel headerPanel = createHeaderPanel("Reports & Analytics", "MENU");
        panel.add(headerPanel, BorderLayout.NORTH);

        JPanel reportsGrid = new JPanel(new GridLayout(2, 2, 20, 20));
        reportsGrid.setBackground(BG_COLOR);
        reportsGrid.setBorder(new EmptyBorder(20, 20, 20, 20));

        reportsGrid.add(createReportCard("Total Revenue", "₹", this::showRevenueReport));
        reportsGrid.add(createReportCard("Event Statistics", "#", this::showEventStats));
        reportsGrid.add(createReportCard("User Bookings", "U", this::showUserBookings));
        reportsGrid.add(createReportCard("Artist Performance", "*", this::showArtistPerformance));

        panel.add(reportsGrid, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createReportCard(String title, String icon, Runnable action) {
        JPanel card = new JPanel(new BorderLayout(10, 10));
        card.setBackground(CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
                new EmptyBorder(30, 30, 30, 30)
        ));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Arial", Font.BOLD, 60));
        iconLabel.setForeground(ACCENT_COLOR);

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(ACCENT_COLOR);

        card.add(iconLabel, BorderLayout.CENTER);
        card.add(titleLabel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                action.run();
            }
            public void mouseEntered(MouseEvent e) {
                card.setBackground(PRIMARY_COLOR);
            }
            public void mouseExited(MouseEvent e) {
                card.setBackground(CARD_BG);
            }
        });

        return card;
    }

    private void showRevenueReport() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT SUM(AMOUNT) as total FROM PAYMENTS WHERE STATUS = 'COMPLETED'");

            double total = 0;
            if (rs.next()) {
                total = rs.getDouble("total");
            }
            rs.close();

            rs = stmt.executeQuery(
                    "SELECT e.EVENT_NAME, SUM(p.AMOUNT) as revenue " +
                            "FROM BOOKINGS b " +
                            "JOIN PAYMENTS p ON b.PAYMENT_ID = p.PAYMENT_ID " +
                            "JOIN EVENTS e ON b.EVENT_ID = e.EVENT_ID " +
                            "WHERE p.STATUS = 'COMPLETED' " +
                            "GROUP BY e.EVENT_NAME " +
                            "ORDER BY revenue DESC");

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════\n");
            report.append("        TOTAL REVENUE REPORT\n");
            report.append("═══════════════════════════════════════\n\n");
            report.append(String.format("Total Revenue: ₹%.2f\n\n", total));
            report.append("Revenue by Event:\n");
            report.append("───────────────────────────────────────\n");

            while (rs.next()) {
                report.append(String.format("%-30s ₹%,10.2f\n",
                        rs.getString("EVENT_NAME"), rs.getDouble("revenue")));
            }

            rs.close();
            stmt.close();

            showReportDialog(report.toString(), "Revenue Report");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        }
    }

    private void showEventStats() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT e.EVENT_NAME, e.TOTAL_SEATS, " +
                            "COUNT(DISTINCT b.BOOKING_ID) as bookings, " +
                            "COALESCE(SUM(b.NO_OF_TICKETS), 0) as tickets_sold " +
                            "FROM EVENTS e " +
                            "LEFT JOIN BOOKINGS b ON e.EVENT_ID = b.EVENT_ID " +
                            "GROUP BY e.EVENT_ID, e.EVENT_NAME, e.TOTAL_SEATS " +
                            "ORDER BY tickets_sold DESC");

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append("                    EVENT STATISTICS\n");
            report.append("═══════════════════════════════════════════════════════════════\n\n");
            report.append(String.format("%-30s %10s %10s %12s\n",
                    "Event", "Total", "Bookings", "Tickets Sold"));
            report.append("───────────────────────────────────────────────────────────────\n");

            while (rs.next()) {
                report.append(String.format("%-30s %10d %10d %12d\n",
                        rs.getString("EVENT_NAME").substring(0, Math.min(30, rs.getString("EVENT_NAME").length())),
                        rs.getInt("TOTAL_SEATS"),
                        rs.getInt("bookings"),
                        rs.getInt("tickets_sold")));
            }

            rs.close();
            stmt.close();

            showReportDialog(report.toString(), "Event Statistics");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        }
    }*/

   /*


    private void showReportDialog(String reportText, String title) {
        JTextArea textArea = new JTextArea(reportText);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textArea.setEditable(false);
        textArea.setBackground(CARD_BG);
        textArea.setForeground(Color.WHITE);
        textArea.setBorder(new EmptyBorder(15, 15, 15, 15));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(700, 450));

        JOptionPane.showMessageDialog(this, scrollPane, title,
                JOptionPane.INFORMATION_MESSAGE);
    }*/

    private void showArtistPerformance() {
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(
                    "SELECT a.ARTIST_NAME, a.GENRE, " +
                            "COUNT(DISTINCT ea.EVENT_ID) as events, " +
                            "(SELECT COUNT(*) FROM ARTIST_COLLABORATION ac " +
                            " WHERE ac.ARTIST1_ID = a.ARTIST_ID OR ac.ARTIST2_ID = a.ARTIST_ID) as collaborations " +
                            "FROM ARTISTS a " +
                            "LEFT JOIN EVENT_ARTIST ea ON a.ARTIST_ID = ea.ARTIST_ID " +
                            "GROUP BY a.ARTIST_ID, a.ARTIST_NAME, a.GENRE " +
                            "ORDER BY events DESC");

            StringBuilder report = new StringBuilder();
            report.append("═══════════════════════════════════════════════════════════════\n");
            report.append("               ARTIST PERFORMANCE REPORT\n");
            report.append("═══════════════════════════════════════════════════════════════\n\n");
            report.append(String.format("%-25s %-15s %8s %15s\n",
                    "Artist", "Genre", "Events", "Collaborations"));
            report.append("───────────────────────────────────────────────────────────────\n");

            while (rs.next()) {
                String artistName = rs.getString("ARTIST_NAME");
                String genre = rs.getString("GENRE");
                report.append(String.format("%-25s %-15s %8d %15d\n",
                        artistName.substring(0, Math.min(25, artistName.length())),
                        genre != null ? genre.substring(0, Math.min(15, genre.length())) : "N/A",
                        rs.getInt("events"),
                        rs.getInt("collaborations")));
            }

            rs.close();
            stmt.close();

            showReportDialog(report.toString(), "Artist Performance");

        } catch (SQLException e) {
            showError("Error generating report: " + e.getMessage());
        }
    }
    // ==================== UTILITY METHODS ====================
    private JPanel createHeaderPanel(String title, String backAction) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(SECONDARY_COLOR);
        headerPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(ACCENT_COLOR);

        JButton backBtn = createStyledButton("<- Back", ACCENT_COLOR, Color.BLACK);
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, backAction));

        headerPanel.add(titleLabel, BorderLayout.CENTER);
        headerPanel.add(backBtn, BorderLayout.WEST);

        return headerPanel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 40));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    private JTable createStyledTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setBackground(CARD_BG);
        table.setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 13));
        table.setRowHeight(28);
        table.setSelectionBackground(PRIMARY_COLOR);
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(SECONDARY_COLOR);
        table.setShowGrid(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(SECONDARY_COLOR);
        header.setForeground(ACCENT_COLOR);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        return table;
    }

    private void addFormField(JPanel panel, GridBagConstraints gbc, String label,
                              JComponent component, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel jLabel = new JLabel(label);
        jLabel.setForeground(ACCENT_COLOR);
        jLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(jLabel, gbc);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.EAST;

        if (component instanceof JTextField) {
            ((JTextField) component).setFont(new Font("Arial", Font.PLAIN, 14));
            ((JTextField) component).setPreferredSize(new Dimension(250, 30));
        } else if (component instanceof JComboBox) {
            ((JComboBox<?>) component).setFont(new Font("Arial", Font.PLAIN, 14));
            ((JComboBox<?>) component).setPreferredSize(new Dimension(250, 30));
        } else if (component instanceof JSpinner) {
            ((JSpinner) component).setFont(new Font("Arial", Font.PLAIN, 14));
            ((JSpinner) component).setPreferredSize(new Dimension(250, 30));
        }

        panel.add(component, gbc);
    }

    private void loadComboBoxData(JComboBox<String> combo, String query) {
        combo.removeAllItems();
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                combo.addItem(rs.getInt(1) + " - " + rs.getString(2));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            showError("Error loading data: " + e.getMessage());
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    // ==================== MAIN METHOD ====================
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> new ConcertTicketBookingSystem());
    }
}

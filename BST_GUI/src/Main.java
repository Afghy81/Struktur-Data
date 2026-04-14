
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.List;

public class Main extends JFrame {
    // =========================
    // NODE AVL
    // =========================
    static class Node {
        int id;
        String nama;
        int height = 1;
        Node left, right;

        Node(int id, String nama) {
            this.id = id;
            this.nama = nama;
        }
    }

    static class ParsedRecord {
        final int id;
        final String nama;

        ParsedRecord(int id, String nama) {
            this.id = id;
            this.nama = nama;
        }
    }

    // =========================
    // MODEL
    // =========================
    private Node root = null;
    private int selectedId = -1;
    private static final String DATA_FILE = "data_avl.txt";

    // =========================
    // UI COMPONENTS
    // =========================
    private final JTextField idField = new JTextField(14);
    private final JTextField nameField = new JTextField(18);
    private final JTextArea bulkArea = new JTextArea(10, 24);

    private final JComboBox<String> searchModeCombo = new JComboBox<>(new String[] { "ID", "Nama" });
    private final JTextField searchField = new JTextField(18);

    private final JComboBox<String> deleteModeCombo = new JComboBox<>(new String[] { "ID", "Nama" });
    private final JTextField deleteField = new JTextField(18);

    private final JTextArea outputArea = new JTextArea();
    private final JLabel statusLabel = new JLabel("Siap.");

    private final TreePanel treePanel = new TreePanel();
    private JScrollPane treeScrollPane;

    // =========================
    // CONSTRUCTOR
    // =========================
    public Main() {
        super("AVL + BST GUI - Visual Tree, Search, Delete, Traversal");

        // Style text fields
        styleTextField(idField);
        styleTextField(nameField);
        styleTextField(searchField);
        styleTextField(deleteField);

        buildUI();
        loadFromFile();
        refreshTreeView();
        setStatus("Data dimuat. Node saat ini: " + countNodes(root));
    }

    // =========================
    // UI BUILD
    // =========================
    private void buildUI() {
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setSize(1420, 860);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        setJMenuBar(createMenuBar());

        add(createLeftControlPanel(), BorderLayout.WEST);
        treeScrollPane = createTreeScrollPane();
        add(treeScrollPane, BorderLayout.CENTER);
        add(createBottomOutputPanel(), BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (confirm("Simpan data sebelum keluar?")) {
                    saveToFile();
                }
                dispose();
                System.exit(0);
            }
        });
    }

    private JMenuBar createMenuBar() {
        JMenuBar bar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem saveItem = new JMenuItem("Simpan");
        JMenuItem loadItem = new JMenuItem("Muat Ulang");
        JMenuItem resetItem = new JMenuItem("Reset Data");
        JMenuItem exitItem = new JMenuItem("Keluar");

        saveItem.addActionListener(e -> {
            saveToFile();
            log("Data berhasil disimpan.");
        });

        loadItem.addActionListener(e -> {
            if (confirm("Muat ulang dari file? Data di memori akan diganti.")) {
                loadFromFile();
                selectedId = -1;
                refreshTreeView();
                log("Data berhasil dimuat ulang dari file.");
                setStatus("Muat ulang selesai. Node: " + countNodes(root));
            }
        });

        resetItem.addActionListener(e -> resetData());

        exitItem.addActionListener(e -> {
            if (confirm("Simpan data sebelum keluar?")) {
                saveToFile();
            }
            dispose();
            System.exit(0);
        });

        fileMenu.add(saveItem);
        fileMenu.add(loadItem);
        fileMenu.addSeparator();
        fileMenu.add(resetItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        bar.add(fileMenu);
        return bar;
    }

    private JScrollPane createTreeScrollPane() {
        // Panel wrapper untuk tree dengan zoom controls
        JPanel treeWrapper = new JPanel(new BorderLayout(5, 5));

        // Zoom control panel di atas
        JPanel zoomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        zoomPanel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        zoomPanel.setBackground(new Color(240, 240, 240));

        JLabel zoomLabel = new JLabel("Zoom: 100%");
        zoomLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        zoomLabel.setPreferredSize(new Dimension(70, 25));
        treePanel.setZoomLabel(zoomLabel);

        JButton zoomInBtn = new JButton("+ Zoom In");
        JButton zoomOutBtn = new JButton("- Zoom Out");
        JButton zoomResetBtn = new JButton("Reset Zoom");

        zoomInBtn.addActionListener(e -> treePanel.zoomIn()); // ✅ Simple!
        zoomOutBtn.addActionListener(e -> treePanel.zoomOut()); // ✅ Simple!
        zoomResetBtn.addActionListener(e -> treePanel.resetZoom()); // ✅ Simple!

        // Styling buttons
        for (JButton btn : new JButton[] { zoomInBtn, zoomOutBtn, zoomResetBtn }) {
            btn.setFont(new Font("Segoe UI", Font.PLAIN, 10));
            btn.setFocusPainted(false);
            btn.setForeground(Color.WHITE);
            btn.setBackground(new Color(33, 150, 243));
            btn.setBorderPainted(false);
            btn.setOpaque(true);
            btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btn.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent e) {
                    btn.setBackground(new Color(25, 120, 210));
                }

                public void mouseExited(MouseEvent e) {
                    btn.setBackground(new Color(33, 150, 243));
                }
            });
        }

        zoomPanel.add(zoomLabel);
        zoomPanel.add(zoomInBtn);
        zoomPanel.add(zoomOutBtn);
        zoomPanel.add(zoomResetBtn);

        treePanel.setBorder(new TitledBorder(new EtchedBorder(), "Visual Tree AVL"));
        JScrollPane sp = new JScrollPane(treePanel);
        sp.setPreferredSize(new Dimension(820, 650));

        treeWrapper.add(zoomPanel, BorderLayout.NORTH);
        treeWrapper.add(sp, BorderLayout.CENTER);

        return new JScrollPane(treeWrapper);
    }

    private JPanel createBottomOutputPanel() {
        outputArea.setEditable(false);
        outputArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 13));
        JScrollPane outScroll = new JScrollPane(outputArea);
        outScroll.setBorder(new TitledBorder(new EtchedBorder(), "Output / Hasil"));

        JPanel panel = new JPanel(new BorderLayout(6, 6));
        panel.add(outScroll, BorderLayout.CENTER);
        panel.add(statusLabel, BorderLayout.SOUTH);
        panel.setPreferredSize(new Dimension(1000, 180));
        return panel;
    }

    private JScrollPane createLeftControlPanel() {
        JPanel outer = new JPanel();
        outer.setLayout(new BoxLayout(outer, BoxLayout.Y_AXIS));
        outer.setBorder(new EmptyBorder(10, 10, 10, 10));

        outer.add(createInputSection());
        outer.add(Box.createVerticalStrut(10));
        outer.add(createBulkSection());
        outer.add(Box.createVerticalStrut(10));
        outer.add(createSearchSection());
        outer.add(Box.createVerticalStrut(10));
        outer.add(createDeleteSection());
        outer.add(Box.createVerticalStrut(10));
        outer.add(createTraversalSection());
        outer.add(Box.createVerticalStrut(10));
        outer.add(createQuickActionsSection());

        JScrollPane scroll = new JScrollPane(outer);
        scroll.setPreferredSize(new Dimension(390, 700));
        scroll.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        return scroll;
    }

    private JPanel createInputSection() {
        JPanel p = sectionPanel("Tambah Data");
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = gbc();

        addLabelField(p, c, 0, "ID", idField);
        addLabelField(p, c, 1, "Nama", nameField);

        JButton addButton = new JButton("Tambah / Update");
        JButton clearButton = new JButton("Bersihkan Field");

        addButton.addActionListener(e -> addSingleFromFields());
        clearButton.addActionListener(e -> {
            idField.setText("");
            nameField.setText("");
            idField.requestFocus();
        });

        styleButton(addButton, new Color(33, 150, 243));
        styleButton(clearButton, new Color(158, 158, 158));

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(addButton, c);

        c.gridy = 3;
        p.add(clearButton, c);

        JLabel hint = new JLabel("<html><i>Input cepat: ID angka + Nama string.</i></html>");
        c.gridy = 4;
        p.add(hint, c);

        return p;
    }

    private JPanel createBulkSection() {
        JPanel p = sectionPanel("Paste Data dari Excel");
        p.setLayout(new BorderLayout(6, 6));

        bulkArea.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        bulkArea.setLineWrap(false);
        bulkArea.setRows(16);
        bulkArea.setMargin(new Insets(10, 8, 10, 8));

        String hintText = "Contoh format per baris:\n1001\tBudi Santoso\n1002\tSiti Aminah\natau 1001,Budi Santoso";
        bulkArea.setText(hintText);
        bulkArea.setForeground(Color.GRAY);
        bulkArea.setBackground(new Color(250, 250, 250));
        bulkArea.setCaretColor(new Color(33, 150, 243));

        bulkArea.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (bulkArea.getForeground().equals(Color.GRAY)) {
                    bulkArea.setText("");
                    bulkArea.setForeground(Color.BLACK);
                    bulkArea.setBackground(Color.WHITE);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (bulkArea.getText().trim().isEmpty()) {
                    bulkArea.setText(hintText);
                    bulkArea.setForeground(Color.GRAY);
                    bulkArea.setBackground(new Color(250, 250, 250));
                }
            }
        });

        JScrollPane bulkScroll = new JScrollPane(bulkArea);
        bulkScroll.setBorder(BorderFactory.createMatteBorder(1, 1, 2, 2, new Color(180, 180, 180)));
        bulkScroll.setPreferredSize(new Dimension(350, 380));
        bulkScroll.getVerticalScrollBar().setPreferredSize(new Dimension(10, 0));
        p.add(bulkScroll, BorderLayout.CENTER);

        JButton importButton = new JButton("Tambah dari Paste");
        importButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
        importButton.setBackground(new Color(76, 175, 80));
        importButton.setForeground(Color.WHITE);
        importButton.setFocusPainted(false);
        importButton.setBorderPainted(false);
        importButton.setOpaque(true);
        importButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        importButton.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                importButton.setBackground(new Color(56, 142, 60));
            }

            public void mouseExited(MouseEvent e) {
                importButton.setBackground(new Color(76, 175, 80));
            }
        });
        importButton.addActionListener(e -> addBulkFromTextArea());
        p.add(importButton, BorderLayout.SOUTH);

        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 450));
        return p;
    }

    private JPanel createSearchSection() {
        JPanel p = sectionPanel("Cari Data");
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = gbc();

        addLabelField(p, c, 0, "Mode", searchModeCombo);
        addLabelField(p, c, 1, "Kata kunci", searchField);

        JButton searchButton = new JButton("Cari");
        searchButton.addActionListener(e -> doSearch());
        styleButton(searchButton, new Color(33, 150, 243));

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(searchButton, c);

        return p;
    }

    private JPanel createDeleteSection() {
        JPanel p = sectionPanel("Hapus Data");
        p.setLayout(new GridBagLayout());
        GridBagConstraints c = gbc();

        addLabelField(p, c, 0, "Mode", deleteModeCombo);
        addLabelField(p, c, 1, "Kata kunci", deleteField);

        JButton deleteButton = new JButton("Hapus");
        deleteButton.addActionListener(e -> doDelete());
        styleButton(deleteButton, new Color(244, 67, 54));

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(deleteButton, c);

        return p;
    }

    private JPanel createTraversalSection() {
        JPanel p = sectionPanel("Traversal");
        p.setLayout(new GridLayout(3, 1, 6, 6));

        JButton inorderBtn = new JButton("Inorder");
        JButton preorderBtn = new JButton("Preorder");
        JButton postorderBtn = new JButton("Postorder");

        inorderBtn.addActionListener(e -> showTraversal("INORDER", getInorder(root)));
        preorderBtn.addActionListener(e -> showTraversal("PREORDER", getPreorder(root)));
        postorderBtn.addActionListener(e -> showTraversal("POSTORDER", getPostorder(root)));

        styleButton(inorderBtn, new Color(156, 39, 176));
        styleButton(preorderBtn, new Color(156, 39, 176));
        styleButton(postorderBtn, new Color(156, 39, 176));

        p.add(inorderBtn);
        p.add(preorderBtn);
        p.add(postorderBtn);
        return p;
    }

    private JPanel createQuickActionsSection() {
        JPanel p = sectionPanel("Aksi Cepat");
        p.setLayout(new GridLayout(2, 1, 6, 6));

        JButton resetButton = new JButton("Reset Data");
        JButton reloadButton = new JButton("Muat Ulang File");

        resetButton.addActionListener(e -> resetData());
        reloadButton.addActionListener(e -> {
            if (confirm("Muat ulang dari file? Data di memori akan diganti.")) {
                loadFromFile();
                selectedId = -1;
                refreshTreeView();
                log("Data berhasil dimuat ulang dari file.");
                setStatus("Muat ulang selesai. Node: " + countNodes(root));
            }
        });

        styleButton(resetButton, new Color(255, 152, 0));
        styleButton(reloadButton, new Color(0, 150, 136));

        p.add(resetButton);
        p.add(reloadButton);
        return p;
    }

    private JPanel sectionPanel(String title) {
        JPanel p = new JPanel();
        p.setBorder(new TitledBorder(new EtchedBorder(), title));
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        return p;
    }

    private GridBagConstraints gbc() {
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(4, 4, 4, 4);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;
        return c;
    }

    private void addLabelField(JPanel p, GridBagConstraints c, int row, String label, JComponent comp) {
        c.gridx = 0;
        c.gridy = row;
        c.gridwidth = 1;
        c.weightx = 0.3;
        p.add(new JLabel(label), c);

        c.gridx = 1;
        c.gridy = row;
        c.gridwidth = 1;
        c.weightx = 0.7;
        p.add(comp, c);
    }

    // =========================
    // ACTIONS
    // =========================
    private void addSingleFromFields() {
        String idText = idField.getText().trim();
        String nama = nameField.getText().trim();

        if (idText.isEmpty() || nama.isEmpty()) {
            showMessage("ID dan Nama harus diisi.");
            return;
        }

        try {
            int id = Integer.parseInt(idText);
            boolean existed = findById(root, id) != null;
            root = insert(root, id, nama);
            selectedId = id;
            refreshTreeView();
            saveToFile();
            log((existed ? "Data diperbarui" : "Data ditambahkan") + ": ID=" + id + " | Nama=" + nama);
            setStatus("Berhasil. Total node: " + countNodes(root));
            idField.setText("");
            nameField.setText("");
            idField.requestFocus();
        } catch (NumberFormatException ex) {
            showMessage("ID harus berupa angka.");
        }
    }

    private void addBulkFromTextArea() {
        String text = bulkArea.getText();
        if (text == null || text.trim().isEmpty() || bulkArea.getForeground().equals(Color.GRAY)) {
            showMessage("Kotak paste masih kosong.");
            return;
        }

        String[] lines = text.split("\\R");
        int inserted = 0;
        int updated = 0;
        int invalid = 0;

        for (String raw : lines) {
            ParsedRecord rec = parseRecord(raw);
            if (rec == null) {
                if (!raw.trim().isEmpty())
                    invalid++;
                continue;
            }

            boolean existed = findById(root, rec.id) != null;
            root = insert(root, rec.id, rec.nama);
            if (existed)
                updated++;
            else
                inserted++;
        }

        selectedId = -1;
        refreshTreeView();
        saveToFile();

        log("Paste selesai. Ditambah: " + inserted + ", diperbarui: " + updated + ", format salah: " + invalid);
        setStatus("Paste selesai. Node: " + countNodes(root));

        // Auto-clear input setelah berhasil submit
        bulkArea.setText("Contoh format per baris:\n1001\tBudi Santoso\n1002\tSiti Aminah\natau 1001,Budi Santoso");
        bulkArea.setForeground(Color.GRAY);
    }

    private void doSearch() {
        String mode = (String) searchModeCombo.getSelectedItem();
        String key = searchField.getText().trim();

        if (key.isEmpty()) {
            showMessage("Kata kunci pencarian belum diisi.");
            return;
        }

        if ("ID".equals(mode)) {
            try {
                int id = Integer.parseInt(key);
                Node found = findById(root, id);

                if (found == null) {
                    outputArea.setText("Data tidak ditemukan untuk ID: " + id);
                    selectedId = -1;
                    refreshTreeView();
                    return;
                }

                selectedId = found.id;
                refreshTreeView();

                int pos = inorderPositionOfId(found.id);
                outputArea.setText(
                        "DATA ANDA BERHASIL DITEMUKAN\n" +
                                "Urutan node inorder ke-" + pos + "\n\n" +
                                "ID   : " + found.id + "\n" +
                                "Nama : " + found.nama + "\n");
                setStatus("Pencarian selesai.");
            } catch (NumberFormatException ex) {
                showMessage("ID harus berupa angka.");
            }
        } else {
            List<Node> matches = findByName(root, key);

            if (matches.isEmpty()) {
                outputArea.setText("Data tidak ditemukan untuk Nama: " + key);
                selectedId = -1;
                refreshTreeView();
                return;
            }

            StringBuilder sb = new StringBuilder();
            sb.append("DATA ANDA BERHASIL DITEMUKAN\n");
            sb.append("Jumlah data: ").append(matches.size()).append("\n\n");

            Node first = matches.get(0);
            selectedId = first.id;
            refreshTreeView();

            for (Node n : matches) {
                int pos = inorderPositionOfId(n.id);
                sb.append("Urutan node inorder ke-").append(pos).append("\n");
                sb.append("ID   : ").append(n.id).append("\n");
                sb.append("Nama : ").append(n.nama).append("\n");
                sb.append("------------------------------\n");
            }

            outputArea.setText(sb.toString());
            setStatus("Pencarian selesai.");
        }
    }

    private void doDelete() {
        String mode = (String) deleteModeCombo.getSelectedItem();
        String key = deleteField.getText().trim();

        if (key.isEmpty()) {
            showMessage("Kata kunci hapus belum diisi.");
            return;
        }

        if ("ID".equals(mode)) {
            try {
                int id = Integer.parseInt(key);
                Node found = findById(root, id);
                if (found == null) {
                    showMessage("Data dengan ID tersebut tidak ditemukan.");
                    return;
                }

                root = delete(root, id);
                if (selectedId == id)
                    selectedId = -1;
                refreshTreeView();
                saveToFile();
                log("Data dihapus: ID=" + id + " | Nama=" + found.nama);
                setStatus("Hapus selesai. Node: " + countNodes(root));
            } catch (NumberFormatException ex) {
                showMessage("ID harus berupa angka.");
            }
        } else {
            List<Node> matches = findByName(root, key);
            if (matches.isEmpty()) {
                showMessage("Data dengan nama tersebut tidak ditemukan.");
                return;
            }

            if (matches.size() == 1) {
                Node target = matches.get(0);
                root = delete(root, target.id);
                if (selectedId == target.id)
                    selectedId = -1;
                refreshTreeView();
                saveToFile();
                log("Data dihapus: ID=" + target.id + " | Nama=" + target.nama);
                setStatus("Hapus selesai. Node: " + countNodes(root));
                return;
            }

            StringBuilder options = new StringBuilder();
            options.append("Ditemukan beberapa data dengan nama yang sama.\n");
            for (Node n : matches) {
                options.append("ID=").append(n.id)
                        .append(" | Nama=").append(n.nama)
                        .append(" | Posisi inorder=").append(inorderPositionOfId(n.id))
                        .append("\n");
            }
            String inputId = JOptionPane.showInputDialog(this, options + "\nMasukkan ID yang ingin dihapus:");
            if (inputId == null || inputId.trim().isEmpty())
                return;

            try {
                int id = Integer.parseInt(inputId.trim());
                Node target = findById(root, id);
                if (target == null || !target.nama.equalsIgnoreCase(key)) {
                    showMessage("ID tidak cocok dengan nama yang dicari.");
                    return;
                }

                root = delete(root, id);
                if (selectedId == id)
                    selectedId = -1;
                refreshTreeView();
                saveToFile();
                log("Data dihapus: ID=" + target.id + " | Nama=" + target.nama);
                setStatus("Hapus selesai. Node: " + countNodes(root));
            } catch (NumberFormatException ex) {
                showMessage("ID harus berupa angka.");
            }
        }
    }

    private void resetData() {
        if (!confirm("Yakin ingin menghapus semua data?"))
            return;

        root = null;
        selectedId = -1;
        bulkArea.setText("Contoh format per baris:\n1001\tBudi Santoso\n1002\tSiti Aminah\natau 1001,Budi Santoso");
        bulkArea.setForeground(Color.GRAY);
        outputArea.setText("");
        deleteField.setText("");
        searchField.setText("");
        idField.setText("");
        nameField.setText("");

        try {
            Files.deleteIfExists(Paths.get(DATA_FILE));
        } catch (IOException ignored) {
        }

        refreshTreeView();
        setStatus("Semua data di-reset.");
        log("Semua data berhasil dihapus.");
    }

    // =========================
    // AVL CORE
    // =========================
    private int height(Node n) {
        return (n == null) ? 0 : n.height;
    }

    private int balanceFactor(Node n) {
        return (n == null) ? 0 : height(n.left) - height(n.right);
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node t2 = x.right;

        x.right = y;
        y.left = t2;

        y.height = 1 + Math.max(height(y.left), height(y.right));
        x.height = 1 + Math.max(height(x.left), height(x.right));
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node t2 = y.left;

        y.left = x;
        x.right = t2;

        x.height = 1 + Math.max(height(x.left), height(x.right));
        y.height = 1 + Math.max(height(y.left), height(y.right));
        return y;
    }

    private Node insert(Node node, int id, String nama) {
        if (node == null)
            return new Node(id, nama);

        if (id < node.id) {
            node.left = insert(node.left, id, nama);
        } else if (id > node.id) {
            node.right = insert(node.right, id, nama);
        } else {
            node.nama = nama; // update nama jika ID sama
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = balanceFactor(node);

        // Left Left
        if (balance > 1 && id < node.left.id)
            return rightRotate(node);

        // Right Right
        if (balance < -1 && id > node.right.id)
            return leftRotate(node);

        // Left Right
        if (balance > 1 && id > node.left.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left
        if (balance < -1 && id < node.right.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current != null && current.left != null)
            current = current.left;
        return current;
    }

    private Node delete(Node node, int id) {
        if (node == null)
            return null;

        if (id < node.id) {
            node.left = delete(node.left, id);
        } else if (id > node.id) {
            node.right = delete(node.right, id);
        } else {
            // 0 atau 1 anak
            if (node.left == null || node.right == null) {
                Node temp = (node.left != null) ? node.left : node.right;
                if (temp == null) {
                    node = null;
                } else {
                    node = temp;
                }
            } else {
                // 2 anak: inorder successor
                Node succ = minValueNode(node.right);
                node.id = succ.id;
                node.nama = succ.nama;
                node.right = delete(node.right, succ.id);
            }
        }

        if (node == null)
            return null;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        int balance = balanceFactor(node);

        // Left Left
        if (balance > 1 && balanceFactor(node.left) >= 0)
            return rightRotate(node);

        // Left Right
        if (balance > 1 && balanceFactor(node.left) < 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Right
        if (balance < -1 && balanceFactor(node.right) <= 0)
            return leftRotate(node);

        // Right Left
        if (balance < -1 && balanceFactor(node.right) > 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // =========================
    // SEARCH
    // =========================
    private Node findById(Node node, int id) {
        while (node != null) {
            if (id == node.id)
                return node;
            node = (id < node.id) ? node.left : node.right;
        }
        return null;
    }

    private List<Node> findByName(Node node, String nama) {
        List<Node> result = new ArrayList<>();
        findByNameRec(node, nama, result);
        return result;
    }

    private void findByNameRec(Node node, String nama, List<Node> result) {
        if (node == null)
            return;
        if (node.nama.equalsIgnoreCase(nama))
            result.add(node);
        findByNameRec(node.left, nama, result);
        findByNameRec(node.right, nama, result);
    }

    // =========================
    // TRAVERSAL
    // =========================
    private List<Node> getInorder(Node node) {
        List<Node> result = new ArrayList<>();
        inorderRec(node, result);
        return result;
    }

    private void inorderRec(Node node, List<Node> result) {
        if (node == null)
            return;
        inorderRec(node.left, result);
        result.add(node);
        inorderRec(node.right, result);
    }

    private List<Node> getPreorder(Node node) {
        List<Node> result = new ArrayList<>();
        preorderRec(node, result);
        return result;
    }

    private void preorderRec(Node node, List<Node> result) {
        if (node == null)
            return;
        result.add(node);
        preorderRec(node.left, result);
        preorderRec(node.right, result);
    }

    private List<Node> getPostorder(Node node) {
        List<Node> result = new ArrayList<>();
        postorderRec(node, result);
        return result;
    }

    private void postorderRec(Node node, List<Node> result) {
        if (node == null)
            return;
        postorderRec(node.left, result);
        postorderRec(node.right, result);
        result.add(node);
    }

    private int inorderPositionOfId(int id) {
        List<Node> list = getInorder(root);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).id == id)
                return i + 1;
        }
        return -1;
    }

    private int countNodes(Node node) {
        if (node == null)
            return 0;
        return 1 + countNodes(node.left) + countNodes(node.right);
    }

    private void showTraversal(String title, List<Node> list) {
        StringBuilder sb = new StringBuilder();
        sb.append("TRAVERSAL ").append(title).append("\n");
        sb.append("Jumlah data: ").append(list.size()).append("\n\n");
        for (int i = 0; i < list.size(); i++) {
            Node n = list.get(i);
            sb.append(i + 1).append(". ")
                    .append("ID=").append(n.id)
                    .append(" | Nama=").append(n.nama)
                    .append(" | Posisi inorder=").append(inorderPositionOfId(n.id))
                    .append("\n");
        }
        outputArea.setText(sb.toString());
        setStatus(title + " selesai.");
    }

    // =========================
    // PERSISTENCE
    // =========================
    private void saveToFile() {
        try (PrintWriter pw = new PrintWriter(
                new OutputStreamWriter(new FileOutputStream(DATA_FILE), StandardCharsets.UTF_8))) {
            for (Node n : getInorder(root)) {
                pw.println(n.id + "|" + n.nama);
            }
        } catch (Exception e) {
            showMessage("Gagal menyimpan data: " + e.getMessage());
        }
    }

    private void loadFromFile() {
        root = null;
        Path path = Paths.get(DATA_FILE);
        if (!Files.exists(path))
            return;

        try {
            List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);
            for (String line : lines) {
                ParsedRecord rec = parseRecordForFile(line);
                if (rec != null) {
                    root = insert(root, rec.id, rec.nama);
                }
            }
        } catch (Exception e) {
            showMessage("Gagal memuat data: " + e.getMessage());
        }
    }

    private ParsedRecord parseRecordForFile(String raw) {
        if (raw == null)
            return null;
        String line = raw.trim();
        if (line.isEmpty())
            return null;

        int idx = line.indexOf('|');
        if (idx <= 0)
            return null;
        String idPart = line.substring(0, idx).trim();
        String namePart = line.substring(idx + 1).trim();

        try {
            int id = Integer.parseInt(idPart);
            if (namePart.isEmpty())
                return null;
            return new ParsedRecord(id, namePart);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    // =========================
    // PARSE FLEXIBLE INPUT
    // =========================
    private ParsedRecord parseRecord(String raw) {
        if (raw == null)
            return null;
        String line = raw.trim();
        if (line.isEmpty())
            return null;

        String[] parts = splitOnce(line, '\t');
        if (parts == null)
            parts = splitOnce(line, ',');
        if (parts == null)
            parts = splitOnce(line, ';');
        if (parts == null)
            parts = splitOnce(line, '|');
        if (parts == null) {
            int idx = line.indexOf(' ');
            if (idx > 0)
                parts = new String[] { line.substring(0, idx), line.substring(idx + 1).trim() };
        }

        if (parts == null || parts.length < 2)
            return null;

        try {
            int id = Integer.parseInt(parts[0].trim());
            String nama = parts[1].trim();
            if (nama.isEmpty())
                return null;
            return new ParsedRecord(id, nama);
        } catch (NumberFormatException ex) {
            return null;
        }
    }

    private String[] splitOnce(String line, char delimiter) {
        int idx = line.indexOf(delimiter);
        if (idx <= 0)
            return null;
        return new String[] { line.substring(0, idx), line.substring(idx + 1).trim() };
    }

    // =========================
    // TREE PANEL
    // =========================
    private class TreePanel extends JPanel {
        private final int NODE_W = 110;
        private final int NODE_H = 52;
        private final int H_STEP = 140;
        private final int V_STEP = 110;
        private final int TOP_MARGIN = 40;
        private final int LEFT_MARGIN = 40;

        private final Map<Node, Point> positions = new IdentityHashMap<>();
        private int xCounter = 0;
        private int maxDepth = 0;

        // Zoom properties
        private double zoomLevel = 1.0;
        private final double ZOOM_INCREMENT = 0.1;
        private final double MIN_ZOOM = 0.5;
        private final double MAX_ZOOM = 3.0;
        private JLabel zoomLabel;

        // Pan/Drag properties
        private int panX = 0;
        private int panY = 0;
        private int lastMouseX = 0;
        private int lastMouseY = 0;
        private boolean isDragging = false;

        TreePanel() {
            setBackground(Color.WHITE);
            setPreferredSize(new Dimension(1200, 700));
            setCursor(new Cursor(Cursor.HAND_CURSOR));

            // Mouse listeners untuk drag/pan canvas
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    isDragging = true;
                    lastMouseX = e.getX();
                    lastMouseY = e.getY();
                    setCursor(new Cursor(Cursor.MOVE_CURSOR));
                }

                @Override
                public void mouseReleased(MouseEvent e) {
                    isDragging = false;
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });

            addMouseMotionListener(new MouseAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (isDragging) {
                        int deltaX = e.getX() - lastMouseX;
                        int deltaY = e.getY() - lastMouseY;

                        panX += deltaX;
                        panY += deltaY;

                        lastMouseX = e.getX();
                        lastMouseY = e.getY();

                        repaint();
                    }
                }

                @Override
                public void mouseMoved(MouseEvent e) {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            });
        }

        void setZoomLabel(JLabel label) {
            this.zoomLabel = label;
        }

        void zoomIn() {
            if (zoomLabel != null && zoomLevel < MAX_ZOOM) {
                zoomLevel += ZOOM_INCREMENT;
                zoomLabel.setText("Zoom: " + getZoomPercentage() + "%"); // ← Auto-update!
                revalidate();
                repaint();
            }
        }

        void zoomOut() {
            if (zoomLabel != null && zoomLevel > MIN_ZOOM) {
                zoomLevel -= ZOOM_INCREMENT;
                zoomLabel.setText("Zoom: " + getZoomPercentage() + "%"); // ← Auto-update!
                revalidate();
                repaint();
            }
        }

        void resetZoom() {
            zoomLevel = 1.0;
            panX = 0;
            panY = 0;
            if (zoomLabel != null) {
                zoomLabel.setText("Zoom: " + getZoomPercentage() + "%");
            }
            revalidate();
            repaint();
        }

        int getZoomPercentage() {
            return (int) (zoomLevel * 100);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            try {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

                if (root == null) {
                    g2.setColor(new Color(120, 120, 120));
                    g2.setFont(getFont().deriveFont(Font.BOLD, 18f));
                    g2.drawString("Tree masih kosong.", 30, 40);
                    return;
                }

                positions.clear();
                xCounter = 0;
                maxDepth = 0;
                layout(root, 0);

                // Apply zoom scaling
                int neededW = (int) Math.max(1200, (xCounter * H_STEP + LEFT_MARGIN * 2) * zoomLevel);
                int neededH = (int) Math.max(700, ((maxDepth + 1) * V_STEP + TOP_MARGIN * 2) * zoomLevel);
                if (getPreferredSize().width != neededW || getPreferredSize().height != neededH) {
                    setPreferredSize(new Dimension(neededW, neededH));
                    revalidate();
                }

                // Scale graphics untuk zoom
                g2.scale(zoomLevel, zoomLevel);

                // Apply pan/drag offset
                g2.translate(panX / zoomLevel, panY / zoomLevel);

                drawEdges(g2, root);
                drawNodes(g2, root);
            } finally {
                g2.dispose();
            }
        }

        private void layout(Node node, int depth) {
            if (node == null)
                return;
            layout(node.left, depth + 1);

            int x = LEFT_MARGIN + xCounter * H_STEP;
            int y = TOP_MARGIN + depth * V_STEP;
            positions.put(node, new Point(x, y));
            xCounter++;
            maxDepth = Math.max(maxDepth, depth);

            layout(node.right, depth + 1);
        }

        private void drawEdges(Graphics2D g2, Node node) {
            if (node == null)
                return;
            Point p = positions.get(node);
            if (p == null)
                return;

            int x1 = p.x + NODE_W / 2;
            int y1 = p.y + NODE_H;

            if (node.left != null) {
                Point pl = positions.get(node.left);
                if (pl != null) {
                    int x2 = pl.x + NODE_W / 2;
                    int y2 = pl.y;
                    g2.setColor(new Color(100, 100, 100));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawLine(x1, y1, x2, y2);
                }
                drawEdges(g2, node.left);
            }
            if (node.right != null) {
                Point pr = positions.get(node.right);
                if (pr != null) {
                    int x2 = pr.x + NODE_W / 2;
                    int y2 = pr.y;
                    g2.setColor(new Color(100, 100, 100));
                    g2.setStroke(new BasicStroke(2f));
                    g2.drawLine(x1, y1, x2, y2);
                }
                drawEdges(g2, node.right);
            }
        }

        private void drawNodes(Graphics2D g2, Node node) {
            if (node == null)
                return;
            Point p = positions.get(node);
            if (p == null)
                return;

            int x = p.x;
            int y = p.y;
            boolean selected = node.id == selectedId;

            // Modern gradient background
            g2.setColor(selected ? new Color(180, 240, 190) : new Color(245, 248, 252));
            g2.fillRoundRect(x, y, NODE_W, NODE_H, 18, 18);

            // Modern border dengan shadow effect
            g2.setColor(selected ? new Color(40, 120, 60) : new Color(100, 150, 200));
            g2.setStroke(new BasicStroke(2.5f));
            g2.drawRoundRect(x, y, NODE_W, NODE_H, 18, 18);

            // Subtle shadow
            g2.setColor(new Color(0, 0, 0, 20));
            g2.fillRoundRect(x + 2, y + 2, NODE_W - 1, NODE_H - 1, 15, 15);

            g2.setColor(Color.DARK_GRAY);
            g2.setFont(getFont().deriveFont(Font.BOLD, 14f));
            String idText = "ID: " + node.id;
            FontMetrics fm = g2.getFontMetrics();
            int idWidth = fm.stringWidth(idText);
            g2.drawString(idText, x + (NODE_W - idWidth) / 2, y + 22);

            g2.setFont(getFont().deriveFont(Font.PLAIN, 11f));
            String nameText = shorten(node.nama, 14);
            fm = g2.getFontMetrics();
            int nameWidth = fm.stringWidth(nameText);
            g2.drawString(nameText, x + (NODE_W - nameWidth) / 2, y + 39);

            drawNodes(g2, node.left);
            drawNodes(g2, node.right);
        }

        private String shorten(String s, int max) {
            if (s == null)
                return "";
            if (s.length() <= max)
                return s;
            return s.substring(0, Math.max(0, max - 3)) + "...";
        }
    }

    // =========================
    // HELPERS
    // =========================
    private void styleButton(JButton btn, Color bgColor) {
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setBackground(bgColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setOpaque(true);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            Color originalBg = bgColor;

            public void mouseEntered(MouseEvent e) {
                btn.setBackground(new Color(Math.max(0, bgColor.getRed() - 30),
                        Math.max(0, bgColor.getGreen() - 30),
                        Math.max(0, bgColor.getBlue() - 30)));
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(originalBg);
            }
        });
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(33, 150, 243)));
        field.setBackground(new Color(245, 245, 245));
        field.setMargin(new Insets(5, 5, 5, 5));
    }

    private void refreshTreeView() {
        treePanel.revalidate();
        treePanel.repaint();
    }

    private void log(String message) {
        outputArea.setText(message);
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    private boolean confirm(String message) {
        return JOptionPane.showConfirmDialog(this, message, "Konfirmasi",
                JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void setStatus(String message) {
        statusLabel.setText(message);
    }

    // =========================
    // MAIN
    // =========================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main app = new Main();
            app.setVisible(true);
        });
    }
}

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.HashMap;

public class Penyimpanan extends JFrame {

    JTable tabel;
    DefaultTableModel model;

    JTextField tfBahan, tfKadaluarsa, tfHarga;
    JSpinner spJumlah;
    JComboBox<String> cbSatuan;

    JComboBox<String> cbRasa;
    JSpinner spJumlahProduksi;
    JLabel lblStatus;

    int idTerpilih = -1;

    public Penyimpanan() {

        // ==================== FRAME =========================
        setTitle("Penyimpanan Bahan Baku");
        setSize(1150, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 248, 250));

        // ==================== HEADER =========================
        JLabel header = new JLabel("Penyimpanan Bahan Baku", SwingConstants.CENTER);
        header.setOpaque(true);
        header.setBackground(new Color(0, 123, 255));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setPreferredSize(new Dimension(1000, 60));
        add(header, BorderLayout.NORTH);

        // ==================== TABEL =========================
        String[] kolom = { "ID", "Bahan Baku", "Jumlah", "Satuan", "Kadaluarsa", "Harga" };
        model = new DefaultTableModel(kolom, 0);
        tabel = new JTable(model);
        tabel.setRowHeight(28);
        tabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tabel.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tabel.getTableHeader().setBackground(new Color(230, 230, 230));

        JScrollPane scroll = new JScrollPane(tabel);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));

        tabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = tabel.getSelectedRow();
                if (row != -1) {
                    idTerpilih = Integer.parseInt(model.getValueAt(row, 0).toString());
                    tfBahan.setText(model.getValueAt(row, 1).toString());
                    spJumlah.setValue(Double.parseDouble(model.getValueAt(row, 2).toString()));
                    cbSatuan.setSelectedItem(model.getValueAt(row, 3).toString());
                    tfKadaluarsa.setText(model.getValueAt(row, 4).toString());
                    tfHarga.setText(model.getValueAt(row, 5).toString());
                }
            }
        });

        // ==================== FORM =========================
        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBorder(new EmptyBorder(15, 15, 15, 15));
        panelForm.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 7, 7, 7);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        tfBahan = new JTextField();
        tfKadaluarsa = new JTextField();
        tfHarga = new JTextField();

        spJumlah = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999.0, 0.1));
        cbSatuan = new JComboBox<>(new String[] {
                "pcs", "butir", "gram", "ons", "kg", "liter", "ml", "botol"
        });

        JLabel lblFormatTanggal = new JLabel("Format: YYYY-MM-DD");
        lblFormatTanggal.setFont(new Font("Segoe UI", Font.ITALIC, 11));
        lblFormatTanggal.setForeground(Color.GRAY);

        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);

        // LABEL & FIELD
        addField(panelForm, gbc, 0, "Bahan Baku :", labelFont, tfBahan);
        addField(panelForm, gbc, 1, "Jumlah :", labelFont, spJumlah);
        addField(panelForm, gbc, 2, "Satuan :", labelFont, cbSatuan);
        addField(panelForm, gbc, 3, "Kadaluarsa :", labelFont, tfKadaluarsa);

        gbc.gridx = 1;
        gbc.gridy = 4;
        panelForm.add(lblFormatTanggal, gbc);

        addField(panelForm, gbc, 5, "Harga :", labelFont, tfHarga);

        // ================= BUTTON STYLE ================
        JButton btnTambah = createButton("Tambah", new Color(0, 200, 100));
        JButton btnEdit = createButton("Edit", new Color(255, 180, 0));
        JButton btnHapus = createButton("Hapus", new Color(230, 0, 0));

        JPanel panelTombol = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelTombol.setBackground(Color.WHITE);
        panelTombol.add(btnTambah);
        panelTombol.add(btnEdit);
        panelTombol.add(btnHapus);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        panelForm.add(panelTombol, gbc);

        // ==================== PANEL PRODUKSI =========================
        JPanel panelProduksi = new JPanel(new GridBagLayout());
        panelProduksi.setBorder(BorderFactory.createTitledBorder("Produksi Barang"));
        panelProduksi.setBackground(new Color(250, 250, 255));

        GridBagConstraints gbcP = new GridBagConstraints();
        gbcP.insets = new Insets(5, 5, 5, 5);
        gbcP.fill = GridBagConstraints.HORIZONTAL;

        cbRasa = new JComboBox<>(new String[] { "Original", "Tape", "Pandan" });
        spJumlahProduksi = new JSpinner(new SpinnerNumberModel(0, 0, 5000, 1));

        JButton btnProduksi = createButton("Mulai Produksi", new Color(0, 123, 255));

        lblStatus = new JLabel("Status: -");
        lblStatus.setFont(new Font("Segoe UI", Font.BOLD, 13));

        addField(panelProduksi, gbcP, 0, "Rasa :", new Font("Segoe UI", Font.BOLD, 13), cbRasa);
        addField(panelProduksi, gbcP, 1, "Jumlah :", new Font("Segoe UI", Font.BOLD, 13), spJumlahProduksi);

        gbcP.gridx = 0;
        gbcP.gridy = 2;
        gbcP.gridwidth = 2;
        panelProduksi.add(btnProduksi, gbcP);

        gbcP.gridy = 3;
        panelProduksi.add(lblStatus, gbcP);

        gbc.gridx = 0;
        gbc.gridy = 7;
        panelForm.add(panelProduksi, gbc);

        // ==================== SPLIT =========================
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scroll, panelForm);
        split.setResizeWeight(0.58);
        add(split, BorderLayout.CENTER);

        // ==================== BUTTON EVENT =========================
        btnTambah.addActionListener(e -> tambahData());
        btnEdit.addActionListener(e -> editData());
        btnHapus.addActionListener(e -> hapusData());
        btnProduksi.addActionListener(e -> prosesProduksi());

        setVisible(true);
        loadTable();
    }

    // ========================== METHOD MEMBUAT BUTTON ==========================
    private JButton createButton(String text, Color baseColor) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setForeground(Color.WHITE);
        btn.setBackground(baseColor);
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(baseColor.darker());
            }

            public void mouseExited(MouseEvent e) {
                btn.setBackground(baseColor);
            }
        });

        return btn;
    }

    // ========================== METHOD ADD FIELD KE GRIDBAG
    // ==========================
    private void addField(JPanel panel, GridBagConstraints gbc, int y, String label, Font font, JComponent field) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(label) {
            {
                setFont(font);
            }
        }, gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    // ================== LOGIKA PRODUKSI ==========================
    public void prosesProduksi() {
        String rasa = cbRasa.getSelectedItem().toString();
        int jumlah = (int) spJumlahProduksi.getValue();

        if (jumlah <= 0) {
            lblStatus.setText("Status: jumlah harus > 0");
            return;
        }

        // ======= RESEP =======
        HashMap<String, Double> resepDasar = new HashMap<>();
        resepDasar.put("Tepung terigu", 0.5);
        resepDasar.put("Gula", 0.5);
        resepDasar.put("Mentega", 0.15);
        resepDasar.put("Susu bubuk", 0.015);
        resepDasar.put("Baking powder", 0.015);
        resepDasar.put("TBM", 0.015);
        resepDasar.put("Vanili", 20.0);
        resepDasar.put("Telur", 12.0);

        HashMap<String, Double> resep = new HashMap<>(resepDasar);

        if (rasa.equals("Tape")) {
            resep.put("Tape", 0.25);
        } else if (rasa.equals("Pandan")) {
            resep.put("Pasta pandan", 40.0);
        }

        try {
            Connection conn = Koneksi.getConnection();

            // CEK STOK
            for (String bahan : resep.keySet()) {
                double totalButuh = resep.get(bahan) * jumlah;

                PreparedStatement pst = conn.prepareStatement(
                        "SELECT jumlah FROM bahan_baku WHERE nama_bahan=?");
                pst.setString(1, bahan);
                ResultSet rs = pst.executeQuery();

                if (!rs.next()) {
                    lblStatus.setText("Status: '" + bahan + "' belum ada!");
                    return;
                }

                if (rs.getDouble("jumlah") < totalButuh) {
                    lblStatus.setText("Status: kurang " + bahan);
                    return;
                }
            }

            // KURANGI STOK
            for (String bahan : resep.keySet()) {
                double totalButuh = resep.get(bahan) * jumlah;

                PreparedStatement pst = conn.prepareStatement(
                        "UPDATE bahan_baku SET jumlah = jumlah - ? WHERE nama_bahan=?");
                pst.setDouble(1, totalButuh);
                pst.setString(2, bahan);
                pst.executeUpdate();
            }

            lblStatus.setText("Status: produksi berhasil (" + jumlah + " batch)");

            loadTable();

        } catch (Exception e) {
            lblStatus.setText("Error: " + e.getMessage());
        }
    }

    // ========================== LOAD TABEL ==========================
    public void loadTable() {
        model.setRowCount(0);
        try {
            Connection conn = Koneksi.getConnection();
            ResultSet rs = conn.createStatement().executeQuery("SELECT * FROM bahan_baku");

            while (rs.next()) {
                model.addRow(new Object[] {
                        rs.getInt("id"),
                        rs.getString("nama_bahan"),
                        rs.getDouble("jumlah"),
                        rs.getString("satuan"),
                        rs.getString("kadaluarsa"),
                        rs.getInt("harga")
                });
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    // ========================== CRUD ==========================
    public void tambahData() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "INSERT INTO bahan_baku (nama_bahan, jumlah, satuan, kadaluarsa, harga) VALUES (?,?,?,?,?)";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, tfBahan.getText());
            pst.setDouble(2, (Double) spJumlah.getValue());
            pst.setString(3, cbSatuan.getSelectedItem().toString());
            pst.setString(4, validasiTanggal(tfKadaluarsa.getText()));
            pst.setInt(5, Integer.parseInt(tfHarga.getText()));

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil ditambah!");
            loadTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void editData() {
        if (idTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();

            String sql = "UPDATE bahan_baku SET nama_bahan=?, jumlah=?, satuan=?, kadaluarsa=?, harga=? WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setString(1, tfBahan.getText());
            pst.setDouble(2, (Double) spJumlah.getValue());
            pst.setString(3, cbSatuan.getSelectedItem().toString());
            pst.setString(4, validasiTanggal(tfKadaluarsa.getText()));
            pst.setInt(5, Integer.parseInt(tfHarga.getText()));
            pst.setInt(6, idTerpilih);

            pst.executeUpdate();
            JOptionPane.showMessageDialog(this, "Data berhasil diubah!");
            loadTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public void hapusData() {
        if (idTerpilih == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data dulu!");
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();

            String sql = "DELETE FROM bahan_baku WHERE id=?";
            PreparedStatement pst = conn.prepareStatement(sql);

            pst.setInt(1, idTerpilih);
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            loadTable();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    public String validasiTanggal(String input) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setLenient(false);
        sdf.parse(input);
        return input;
    }

}

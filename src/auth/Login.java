package auth;

public class Login extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Login.class.getName());


    public Login() {
        initComponents();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("LOGIN ");

        jLabel2.setText("NIM ");

        jTextField1.setText("jTextField1");

        jLabel3.setText("Password");

        jTextField2.setText("jTextField2");

        jButton1.setText("SIMPAN");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2))
                            .addGap(32, 32, 32)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jTextField1)
                                .addComponent(jTextField2, javax.swing.GroupLayout.DEFAULT_SIZE, 298, Short.MAX_VALUE)))))
                .addContainerGap(23, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addComponent(jLabel1)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
            
        try {
            //1. Ambil data Input (Bisa berisi NIM atau Email)
            String usernameInput = jTextField1.getText();
            String passwordInput = jTextField2.getText(); // (Inget ganti ke JPasswordField nanti)

            //2. Validasi input kosong
            if(usernameInput.isEmpty() || passwordInput.isEmpty()) {
               javax.swing.JOptionPane.showMessageDialog(this, "Kolom tidak boleh kosong!");
                return;
            }    

            //3. Panggil koneksi
            java.sql.Connection conn = koneksi.Koneksi.getConnection();
            
            //4. Siapkan query - Mapping DB
            String sqlMhs = "SELECT * FROM mahasiswa WHERE nim = ? AND password = ?";
            java.sql.PreparedStatement pstMhs = conn.prepareStatement(sqlMhs);
            pstMhs.setString(1, usernameInput);
            pstMhs.setString(2, passwordInput);
            
            //5. Eksekusi query 
            java.sql.ResultSet rsMhs = pstMhs.executeQuery();

            //6.Validasi Pindah halaman
            if (rsMhs.next()) {
                
                //Operasi Mahasiswa
                String namaMhs = rsMhs.getString("nama_mahasiswa");
                javax.swing.JOptionPane.showMessageDialog(this, "Login Berhasil! Selamat datang, " + namaMhs);
                
                this.dispose(); // Tutup Login
                new mahasiswa.MainMahasiswa().setVisible(true); //Masuk ke frame MainMahasiswa
            } else {                
                //Operasi untuk petugas 
                               
                //Siapkan query | Mapping DB - variabel
                String sqlPetugas = "SELECT * FROM petugas WHERE email_petugas = ? AND password = ?";
                java.sql.PreparedStatement pstPetugas = conn.prepareStatement(sqlPetugas);
                pstPetugas.setString(1, usernameInput);
                pstPetugas.setString(2, passwordInput);
                    
                //Eksekusi query 
                java.sql.ResultSet rsPetugas = pstPetugas.executeQuery();

                if (rsPetugas.next()) {
                    
                    //Operasi login petugas 
                    String namaPetugas = rsPetugas.getString("nama_petugas");
                    javax.swing.JOptionPane.showMessageDialog(this, "Login Admin Berhasil! Halo, " + namaPetugas);

                    this.dispose(); // Tutup Login
                    new petugas.MainPetugas().setVisible(true); //Masuk ke halaman MainPetugas
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Gagal: NIM/Email atau Password salah!");
                }
            }
        } catch (Exception e) {
           javax.swing.JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        }                
    }//GEN-LAST:event_jButton1ActionPerformed


    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> new Login().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}

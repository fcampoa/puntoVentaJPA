/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.GUI;

import com.POJOS.Cliente;
import com.POJOS.Producto;
import com.POJOS.ProductosVenta;
import com.POJOS.Venta;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author felix
 */
public class DlgRegistroVentas extends javax.swing.JDialog {

    private UnitOfWork unitOfWork;

    private Venta venta;
    private Cliente cliente;
    private Producto producto;
    private ProductosVenta detalle;

    private List<ProductosVenta> detallesVenta;
    private List<Cliente> listaClientes;
    private List<Producto> listaProductos;

    private float total = 0;

    private DefaultTableModel columnasBuscador = new DefaultTableModel(new Object[]{"Id Producto", "Nombre", "Precio Actual", "Stock"}, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    private DefaultTableModel columnasProductos = new DefaultTableModel(new Object[]{"Id Producto", "Nombre", "Precio Actual", "Cantidad", "Monto Total"}, 0) {
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };

    /**
     * Creates new form DlgVentas
     */
    public DlgRegistroVentas(java.awt.Frame parent, boolean modal, UnitOfWork unitOfWork) {
        super(parent, "Registro de ventas", modal);
        initComponents();
        tablaBuscador.setModel(columnasBuscador);
        tablaProductos.setModel(columnasProductos);
        this.unitOfWork = unitOfWork;
        listaProductos = this.unitOfWork.productosRepository().lista();
        listaClientes = this.unitOfWork.clientesRepository().lista();
        cargarClientes();
        cargarProductos();
        this.detallesVenta = new ArrayList<>();
        nuevo();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void cargarClientes() {
        DefaultComboBoxModel modelo = (DefaultComboBoxModel) comboClientes.getModel();
        Cliente cliente = new Cliente();
        cliente.setNombre("seleccionar");
        modelo.addElement(cliente);
        listaClientes.forEach((Cliente c) -> {
            modelo.addElement(c);
        });
    }

    private void cargarProductos() {
        DefaultTableModel modelo = (DefaultTableModel) tablaBuscador.getModel();
        modelo.setRowCount(0);
        listaProductos.forEach((Producto p) -> {
            modelo.addRow(new Object[]{p.getIdProducto(), p.getNombre(), p.getPrecioActual(), p.getStock()});
        });
    }

    private void cargarDetalles() {
        DefaultTableModel modelo = (DefaultTableModel) tablaProductos.getModel();
        modelo.setRowCount(0);
        for (ProductosVenta detalle : detallesVenta) {
            modelo.addRow(new Object[]{detalle.getProducto().getIdProducto(),
                detalle.getProducto().getNombre(),
                detalle.getPrecio(),
                detalle.getCantidad(),
                detalle.getMontoTotal()});
        }
    }

    private void nuevo() {
        venta = new Venta();
        detallesVenta.clear();
        buscar("");
        vaciarTabla(tablaProductos);
        limpiarCampos();
        cliente = (Cliente) comboClientes.getSelectedItem();
    }

    private void limpiarCampos() {
        comboClientes.setSelectedIndex(0);
        txtBuscar.setText("");
        txtSubTotal.setText("0");
        txtDescuento.setText("0");
        txtTotal.setText("0");
    }

    private void vaciarTabla(JTable tabla) {
        DefaultTableModel dm = (DefaultTableModel) tabla.getModel();
        int rowCount = dm.getRowCount();
        for (int i = rowCount - 1; i >= 0; i--) {
            dm.removeRow(i);
        }
    }

    private void parsearVenta() {
        venta.setCliente(cliente);
        venta.setDescuento((txtDescuento.getText().equals("") ? 0 : Float.parseFloat(txtDescuento.getText())));
        venta.setFecha(new GregorianCalendar());
        venta.setMontoFinal(Float.parseFloat(txtTotal.getText()));
        venta.setListaProductos(detallesVenta);
    }

    private void buscar(String nombre) {
        if ("".equals(nombre)) {
            listaProductos = this.unitOfWork.productosRepository().lista();
        } else {
            listaProductos = unitOfWork.productosRepository().buscarNombre(nombre);
        }
        cargarProductos();
    }

    private void registrar() {
        try {
            if (this.checarCamposObligatorios()) {
                parsearVenta();
                unitOfWork.ventasRepository().agregar(venta);
                nuevo();
                JOptionPane.showMessageDialog(this, "Se ha registrado la venta", "Guardado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Deben seleccionar un cliente y al menos un producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al registrar la venta: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void agregarProducto(Producto p) {
        detallesVenta.forEach((ProductosVenta d) -> {
            if (d.getProducto().getIdProducto() == p.getIdProducto()) {
                detalle = d;
            }
        });
        if (detalle == null) {
            detalle = new ProductosVenta();
            detalle.setVenta(venta);
            detalle.setProducto(p);
            detalle.setCantidad(1);
            detalle.setPrecio(p.getPrecioActual());
            detalle.setMontoTotal(p.getPrecioActual());

            detallesVenta.add(detalle);
        } else {
            int index = detallesVenta.indexOf(detalle);
            detalle.setCantidad(detalle.getCantidad() + 1);
            detalle.setMontoTotal((detalle.getCantidad() * detalle.getPrecio()));
            detallesVenta.set(index, detalle);
        }
        cargarDetalles();
        detalle = null;
        calcularTotal();
    }

    private void eliminarDetalle(int index) {
        Integer id = Integer.parseInt(tablaProductos.getValueAt(index, 0).toString());
        detallesVenta.forEach((ProductosVenta d) -> {
            if (d.getProducto().getIdProducto() == id) {
                detalle = d;
            }
        });
        detallesVenta.remove(detalle);

        cargarDetalles();

        calcularTotal();
    }

    private void calcularTotal() {
        float montoTotal = 0;
        for (ProductosVenta d : detallesVenta) {
            montoTotal += d.getMontoTotal();
        }
        total = montoTotal;
        txtTotal.setText(String.valueOf(total));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelDetalles = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        comboClientes = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        txtSubTotal = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtDescuento = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtTotal = new javax.swing.JTextField();
        btnRegistrar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnNuevo = new javax.swing.JButton();
        scrollBuscador = new javax.swing.JScrollPane();
        tablaBuscador = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        txtBuscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        scrollProductos = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        panelDetalles.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setText("Detalles de Venta");

        jLabel4.setText("Cliente:");

        comboClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboClientesActionPerformed(evt);
            }
        });

        jLabel5.setText("Subtotal:");

        txtSubTotal.setEnabled(false);

        jLabel6.setText("Descuento:");

        txtDescuento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescuentoKeyTyped(evt);
            }
        });

        jLabel7.setText("Total:");

        txtTotal.setEnabled(false);

        btnRegistrar.setText("Registrar");
        btnRegistrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelDetallesLayout = new javax.swing.GroupLayout(panelDetalles);
        panelDetalles.setLayout(panelDetallesLayout);
        panelDetallesLayout.setHorizontalGroup(
            panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetallesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(panelDetallesLayout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(panelDetallesLayout.createSequentialGroup()
                                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtSubTotal)
                                    .addComponent(comboClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDescuento)
                                    .addComponent(txtTotal, javax.swing.GroupLayout.DEFAULT_SIZE, 262, Short.MAX_VALUE)))
                            .addGroup(panelDetallesLayout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(btnRegistrar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnCancelar)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnNuevo)))))
                .addContainerGap(30, Short.MAX_VALUE))
        );
        panelDetallesLayout.setVerticalGroup(
            panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelDetallesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(comboClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtSubTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(24, 24, 24)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtDescuento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panelDetallesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRegistrar)
                    .addComponent(btnCancelar)
                    .addComponent(btnNuevo))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tablaBuscador.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Precio Actual", "Stock"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaBuscador.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaBuscadorMouseClicked(evt);
            }
        });
        scrollBuscador.setViewportView(tablaBuscador);

        jLabel1.setText("Buscador de productos");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jLabel2.setText("Productos Seleccionados");

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id Producto", "Nombre", "Precio Actual", "Cantidad", "Total"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaProductos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaProductosMouseClicked(evt);
            }
        });
        scrollProductos.setViewportView(tablaProductos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelDetalles, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(scrollBuscador)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(scrollProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 549, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBuscar))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollBuscador, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(scrollProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 11, Short.MAX_VALUE))
                    .addComponent(panelDetalles, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        setVisible(false);
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnRegistrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarActionPerformed
        registrar();
    }//GEN-LAST:event_btnRegistrarActionPerformed

    private void comboClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboClientesActionPerformed
        cliente = (Cliente) comboClientes.getSelectedItem();
    }//GEN-LAST:event_comboClientesActionPerformed

    private void txtDescuentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescuentoKeyTyped
        if (Character.isLetter(evt.getKeyChar())) {
            evt.consume();
        }
    }//GEN-LAST:event_txtDescuentoKeyTyped

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        buscar(txtBuscar.getText());
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        nuevo();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void tablaBuscadorMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaBuscadorMouseClicked
        Integer id = Integer.parseInt(tablaBuscador.getValueAt(tablaBuscador.getSelectedRow(), 0).toString());
        Producto p = new Producto(id);
        producto = listaProductos.get(listaProductos.indexOf(p));
        agregarProducto(producto);
    }//GEN-LAST:event_tablaBuscadorMouseClicked

    private void tablaProductosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaProductosMouseClicked
        eliminarDetalle(tablaProductos.getSelectedRow());
    }//GEN-LAST:event_tablaProductosMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JButton btnRegistrar;
    private javax.swing.JComboBox<String> comboClientes;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel panelDetalles;
    private javax.swing.JScrollPane scrollBuscador;
    private javax.swing.JScrollPane scrollProductos;
    private javax.swing.JTable tablaBuscador;
    private javax.swing.JTable tablaProductos;
    private javax.swing.JTextField txtBuscar;
    private javax.swing.JTextField txtDescuento;
    private javax.swing.JTextField txtSubTotal;
    private javax.swing.JTextField txtTotal;
    // End of variables declaration//GEN-END:variables

    private boolean checarCamposObligatorios() {
        return !(cliente.getIdCliente() == null || listaProductos.isEmpty());
    }
}

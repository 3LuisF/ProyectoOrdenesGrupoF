

package com.mycompany.ordenes;
import com.google.protobuf.TextFormat.ParseException;
import com.mysql.cj.protocol.Resultset;
//import com.mysql.cj.jdbc.CallableStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Luis F Montoya
 */
public class ordenes {

 int radicado;
 String Tipo;
 int Codigo;
 String Especificacion;
 Date fechaCreacion;
 Date fechaLimite;
 String observaciones;

    public int getRadicado() {
        return radicado;
    }
    public void setRadicado(int radicado) {
        this.radicado = radicado;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

    public int getCodigo() {
        return Codigo;
    }

    public void setCodigo(int Codigo) {
        this.Codigo = Codigo;
    }

    public String getEspecificacion() {
        return Especificacion;
    }

    public void setEspecificacion(String Especificacion) {
        this.Especificacion = Especificacion;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

public java.sql.Date getFechaLimite() {
    // Suponiendo que setFechaLimite(fechaLimite) establece correctamente el atributo fechaLimite
    return new java.sql.Date(fechaLimite.getTime());
}

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
    public void CrearOrden(JTextField paramTipo, JTextField paramCodigo, JTextField paramEspecificacion,JTextField paramFechaLimite, JTextField paramObservaciones) throws java.text.ParseException{
        
        setTipo(paramTipo.getText());
        String codigoString = paramCodigo.getText();
                try{
                    int codigo = Integer.parseInt(codigoString);
                    setCodigo(codigo);
                } catch (NumberFormatException e) {

                System.err.println("Error: Ingresa un número válido para el código");
            }
        setEspecificacion(paramEspecificacion.getText());
        String fechaLimiteString = paramFechaLimite.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Ajusta el formato según el formato de tu fecha
        Date fechaLimite = null;
                try {
                 fechaLimite = dateFormat.parse(fechaLimiteString);
                 setFechaLimite(fechaLimite);
                } catch (java.text.ParseException e) {
                System.err.println("Error: Ingresa una fecha válida para la fecha de vencimiento");
                }
        setObservaciones(paramObservaciones.getText());
        
        conexion objetoConexion = new conexion();
        
        String consulta = "insert into Ordenes (tipo,codigo,especificacion, fecha_limite, observaciones) values (?,?,?,?,?)";
        try {
            CallableStatement cs = objetoConexion.estableConexion().prepareCall(consulta);
            cs.setString(1,getTipo());
            cs.setInt(2,getCodigo());
            cs.setString(3,getEspecificacion());
            cs.setDate(4, (java.sql.Date) getFechaLimite());
            cs.setString(5,getObservaciones());
            
            cs.execute();
            JOptionPane.showMessageDialog(null,"Orden creada" );
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"No se creo la orden"+e.toString() );
        } finally {
            //objetoConexion.cerrarConexion();
        }
        objetoConexion.estableConexion();
        
            
            
    }
    
    public void MostrarOrdenes(JTable paramOrdenes){
        conexion objetoConexion = new conexion();    
        DefaultTableModel modelo = new DefaultTableModel();
        
       //Para ordenar el encabezado de la tabla
        TableRowSorter<TableModel> OrdenarTabla = new TableRowSorter<TableModel>(modelo);
        
        paramOrdenes.setRowSorter(OrdenarTabla);
        
        String sql = "";
        
        modelo.addColumn("Radicado");
        modelo.addColumn("Tipo");
        modelo.addColumn("Codigo");
        modelo.addColumn("Especificacion");
        modelo.addColumn("Fecha Creacion");
        modelo.addColumn("Fecha limite");
        modelo.addColumn("Observaciones");
        
        paramOrdenes.setModel(modelo);
        
        sql = "select * from ordenes;";
        
        String[] datos = new String[7];
        Statement st;
        try {
            //realizar conexion
            st= objetoConexion.estableConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            while(rs.next()){
               datos[0] = rs.getString(1);
               datos[1] = rs.getString(2);
               datos[2] = rs.getString(3);
               datos[3] = rs.getString(4);
               datos[4] = rs.getString(5);
               datos[5] = rs.getString(6);
               datos[6] = rs.getString(7);
               
               modelo.addRow(datos);
                       
            }
            
            paramOrdenes.setModel(modelo);
            
            
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "No se pudo crear los registros"+e.toString());
        }
        
    }
    
    
    public void SeleccionarOrdenes(JTable paramTablaOrdenes,JTextField paramRadicado, JTextField paramTipo, JTextField paramCodigo, JTextField paramEspecificacion,JTextField paramFechaCreacion,JTextField paramFechaLimite, JTextField paramObservaciones){
    
        try {
            int fila = paramTablaOrdenes.getSelectedRow();
            
            if (fila >= 0){
                
                paramRadicado.setText((String) paramTablaOrdenes.getValueAt(fila, 0));
                paramTipo.setText((String) paramTablaOrdenes.getValueAt(fila, 1));
                paramCodigo.setText((String) paramTablaOrdenes.getValueAt(fila, 2));
                paramEspecificacion.setText((String) paramTablaOrdenes.getValueAt(fila, 3));
                paramFechaCreacion.setText((String) paramTablaOrdenes.getValueAt(fila, 4));
                paramFechaLimite.setText((String) paramTablaOrdenes.getValueAt(fila, 5));
                paramObservaciones.setText((String) paramTablaOrdenes.getValueAt(fila, 6));
            }
            
            else{
                    
                JOptionPane.showMessageDialog(null, "Fila no seleccionada");
            }
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null, "Error de seleccion"+e.toString());
        }
    }
    public void ModificarOrdenes( JTextField paramRadicado,JTextField paramTipo, JTextField paramCodigo, JTextField paramEspecificacion,JTextField paramFechaLimite, JTextField paramObservaciones){
        setRadicado(Integer.parseInt(paramRadicado.getText()));
        setTipo(paramTipo.getText());
        setCodigo(Integer.parseInt(paramCodigo.getText()));
        setEspecificacion(paramEspecificacion.getText());
        String fechaLimiteString = paramFechaLimite.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");  // Ajusta el formato según el formato de tu fecha
        Date fechaLimite = null;
                try {
                 fechaLimite = dateFormat.parse(fechaLimiteString);
                 setFechaLimite(fechaLimite);
                } catch (java.text.ParseException e) {
                System.err.println("Error: Ingresa una fecha válida para la fecha de vencimiento");
                }
        setObservaciones(paramObservaciones.getText());
        
        conexion objetoConexion = new conexion();
        
        String consulta = "update ordenes set ordenes.tipo =  ?, ordenes.codigo = ?, ordenes.especificacion = ?, ordenes.fecha_limite = ?, ordenes.observaciones = ? where ordenes.radicado =?;";
        try {
           CallableStatement cs = objetoConexion.estableConexion().prepareCall(consulta);
           cs.setString(1,getTipo());
           cs.setInt(2,getCodigo());
           cs.setString(3,getEspecificacion());
           cs.setDate(4, (java.sql.Date) getFechaLimite());
           cs.setString(5,getObservaciones());
           cs.setInt(6,getRadicado());
            
           cs.execute();
           
            JOptionPane.showMessageDialog(null,"Modificacion exitosa" );
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"No se modifico la orden"+e.toString() );

        }
    }
    public void Elminar(JTextField paramRadicado){
        setRadicado(Integer.parseInt(paramRadicado.getText()));
        conexion objetoConexion = new conexion();
        
        String consulta = "delete from ordenes where ordenes.radicado = ?;";
        try {
            CallableStatement cs = objetoConexion.estableConexion().prepareCall(consulta);
            cs.setInt(1,getRadicado());
            cs.execute();
            JOptionPane.showMessageDialog(null, "Se elimino la orden con radicado:"+radicado);
        } catch (Exception e) {
            
            JOptionPane.showMessageDialog(null,"No se pudo eliminar orden con radicado"+paramRadicado +e.toString());
        }
        
    }
    
    public void LimpiarCampo(JTextField paramRadicado,JTextField paramTipo, JTextField paramCodigo, JTextField paramEspecificacion,JTextField paramFechaCreacion, JTextField paramFechaLimite, JTextField paramObservaciones){
        paramRadicado.setText("");
        paramTipo.setText("");
        paramCodigo.setText("");
        paramEspecificacion.setText("");
        paramFechaCreacion.setText("");
        paramFechaLimite.setText("");
        paramObservaciones.setText("");
    }

}

package app.ito.poo;

import java.awt.HeadlessException;
import java.time.LocalDate;
import Clases.ito.poo.CuentaBancaria;
import Clases.ito.poo.CuentasdeBanco;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import excepcion.ito.poo.CuentaExistente;
import excepcion.ito.poo.BorrarCuenta;
import excepcion.ito.poo.Deposito;
import excepcion.ito.poo.NumeCuenta;
import excepcion.ito.poo.Retiro;
import excepcion.ito.poo.Saldo;

public class Aplicacion {

	static CuentasdeBanco c;
	static CuentaBancaria e;
	
	
	static void menu() throws NumeCuenta, Saldo, Retiro, Deposito, HeadlessException, CuentaExistente, BorrarCuenta {
		inicializa();
		final JPanel panel=new JPanel();
		boolean error=true;
		while(error) {
		try {
		boolean ciclo=true;
		int respuesta=0;
		while(ciclo) {
		String opciones="Elige la opci?n que desees:\n 1)Agregar una cuenta\n 2)Imprimir las cuentas existentes\n 3)Hacer un dep?sito a una cuenta\n "
				+ "4)Hacer un retiro a una cuenta\n 5)Dar de baja una cuenta\n 6)Hacer una consulta\n 7)Exit";
		respuesta=Integer.parseInt(JOptionPane.showInputDialog(opciones));
		switch(respuesta) {
		case 1:agregarCuenta();break;
		case 2:mostrarCuentas();break;
		case 3:hacerDeposito();break;
		case 4:hacerRetiro();break;
		case 5:borrarCuenta();break;
		case 6:consulta();break;
		case 7:ciclo=false;error=false;break;
		default:JOptionPane.showMessageDialog(null,"Ingrese una de las opciones indicadas por favor");
		  }
		}
		}catch(NumeCuenta e){
			JOptionPane.showMessageDialog(panel,e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
		}catch(Saldo e) {
			JOptionPane.showMessageDialog(panel,e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
		}catch(Deposito e) {
			JOptionPane.showMessageDialog(panel,e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
		}catch(Retiro e) {
			JOptionPane.showMessageDialog(panel,e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
		}catch(CuentaExistente e) {
			JOptionPane.showMessageDialog(panel,e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
		}catch(BorrarCuenta e) {
			JOptionPane.showMessageDialog(panel,e.getMessage(),"ERROR", JOptionPane.ERROR_MESSAGE);
		}
		}
	}
	
	static CuentaBancaria capturarCuenta() throws NumeCuenta,Saldo {
		CuentaBancaria n=new CuentaBancaria();
		long l;String fecha,nombre;float saldo;
		l=Long.parseLong(JOptionPane.showInputDialog("Proporciona un n?mero de cuenta:"));
		nombre=JOptionPane.showInputDialog("Proporciona el nombre del cliente:");
		saldo=Float.parseFloat(JOptionPane.showInputDialog("Proporciona el saldo de la cuenta:"));
		fecha=JOptionPane.showInputDialog("Proporciona la fecha de apertura(aaaa-mm-dd):");
		n.setNumeCuenta(l);
		n.setNomCliente(nombre);
		n.setSaldo(saldo);
		n.setFechaApertura(LocalDate.parse(fecha));
		return n;
	}
	
	static void inicializa() {
		c=new CuentasdeBanco();
	}
	
	static void agregarCuenta() throws NumeCuenta, Saldo, HeadlessException, CuentaExistente {
		CuentaBancaria nueva;
		nueva=capturarCuenta();
		if(c.addItem(nueva)) {
			JOptionPane.showMessageDialog(null,"Se ah agregado la cuenta con exito!!");
			if(c.isFull())
				c.crecerArreglo();
		}
		else
			JOptionPane.showMessageDialog(null,"Error:La cuenta ya existe!!");
	}
	
	static void mostrarCuentas() {
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
		String cuentas="";
		for(int i=0;i<c.getSize();i++)
			cuentas=cuentas+"\n"+(c.getItem(i));
		JOptionPane.showMessageDialog(null,cuentas);
		}
	}
	
	static void hacerDeposito() throws Saldo, Deposito {
		int pos=0;
		float cantidad=0;
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
			boolean bandera=true;
			while(bandera) {
		    String cuentas="";
		    for(int i=0;i<c.getSize();i++)
			    cuentas=cuentas+"\n"+(i+1)+")"+(c.getItem(i));
		    pos=Integer.parseInt(JOptionPane.showInputDialog("A que cuenta deseas hacer un deposito?\n"+cuentas));
		    if((c.getSize())>=pos&&pos>0) {
		    cantidad=Float.parseFloat(JOptionPane.showInputDialog("Que cantidad desea depositar?"));
		    c.Deposito(cantidad);
		    c.getItem(pos-1).setSaldoActualizado(c.getItem(pos-1).getSaldo()+cantidad);
		    c.getItem(pos-1).setFechaActualizacion(LocalDate.now());
		    JOptionPane.showMessageDialog(null,"Dinero depositado exitosamente!!");
		    bandera=false;
		    }
		    else
		    	JOptionPane.showMessageDialog(null,"Esa cuenta no existe!!");
			}
		}
	}
	
	static void hacerRetiro() throws Saldo, Retiro {
		int pos=0;
		float cantidad=0;
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
			boolean bandera=true;
			while(bandera) {
		    String cuentas="";
		    for(int i=0;i<c.getSize();i++)
			    cuentas=cuentas+"\n"+(i+1)+")"+(c.getItem(i));
		    pos=Integer.parseInt(JOptionPane.showInputDialog("A que cuenta deseas hacer un retiro?\n"+cuentas));
		    if((c.getSize())>=pos&&pos>0) {
		    cantidad=Float.parseFloat(JOptionPane.showInputDialog("Que cantidad desea retirar?"));
		    c.Retiro(cantidad);
		    if(!(c.getItem(pos-1).getSaldo()<cantidad)) {
		    c.getItem(pos-1).setSaldoActualizado(c.getItem(pos-1).getSaldo()-cantidad);
		    c.getItem(pos-1).setFechaActualizacion(LocalDate.now());
		    JOptionPane.showMessageDialog(null,"Dinero retirado exitosamente!!");
		    bandera=false;
		    }
		    else {
		    	JOptionPane.showMessageDialog(null,"La cantidad exede el saldo de la cuenta!!");
		    }
		    }
		    else
		    	JOptionPane.showMessageDialog(null,"Esa cuenta no existe!!");
			}
		}
	}
	
	static void borrarCuenta() throws BorrarCuenta {
		int pos=0;
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
			boolean bandera=true;
			while(bandera) {
		    String cuentas="";
		    for(int i=0;i<c.getSize();i++)
			    cuentas=cuentas+"\n"+(i+1)+")"+(c.getItem(i));
		    pos=Integer.parseInt(JOptionPane.showInputDialog("Cual cuenta deseas dar de baja?\n"+cuentas));
		    if((c.getSize())>=pos&&pos>0) {
		    	c.BorrarCuenta(c.getItem(pos-1));
		    	c.clear(c.getItem(pos-1));
		    	JOptionPane.showMessageDialog(null,"Cuenta dada de baja con ?xito!!");
		    	bandera=false;
		    }	
		    else
		    	JOptionPane.showMessageDialog(null,"Esa cuenta no existe!!");
		  }
		}
	}
	
	static void consulta() {
		int respuesta=0;
		boolean ciclo=true;
		while(ciclo) {
		String opciones="Elige la consulta que desees:\n 1)Monto total de las cuentas\n 2)Monto promedio de las cuentas\n"
				+ " 3)Cuentas con saldo superior de los $10,000\n "
				+ "4)Cuenta/s con saldo m?ximo\n 5)Cuenta/s con saldo m?nimo\n 6)Exit";
		respuesta=Integer.parseInt(JOptionPane.showInputDialog(opciones));
		switch(respuesta) {
		case 1:montoTotal();ciclo=false;break;
		case 2:montoPromedio();ciclo=false;break;
		case 3:mayor10mil();ciclo=false;break;
		case 4:saldoMax();ciclo=false;break;
		case 5:saldoMin();ciclo=false;break;
		case 6:ciclo=false;break;
		default:JOptionPane.showMessageDialog(null,"Ingrese una de las opciones indicadas por favor");
		  }
		}
	}
	
	static void montoTotal() {
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
		    float montoTotal=0;
		    for(int i=0;i<c.getSize();i++) 
			    montoTotal=montoTotal+c.getItem(i).getSaldo();
		    JOptionPane.showMessageDialog(null,"El monto tota es: $"+montoTotal);
		}
	}
	
	static void montoPromedio() {
		float montoProm=0;
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
		    float montoTotal=0;
		    for(int i=0;i<c.getSize();i++) 
		        montoTotal=montoTotal+c.getItem(i).getSaldo();
		    montoProm=montoTotal/c.getSize(); 
		    JOptionPane.showMessageDialog(null,"El monto promedio es: $"+montoProm);
		}
	}
	
	static void mayor10mil() {
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
			int vacio=0;
			CuentaBancaria copia[]=new CuentaBancaria[c.getSize()];
			for(int i=0;i<c.getSize();i++)
				if(c.getItem(i).getSaldo()>10000) 
					copia[i-vacio]=c.getItem(i);
				else
					vacio++;
			String cuentas="";
			for(int j=0;j<(c.getSize()-vacio);j++)
				cuentas=cuentas+"\n"+copia[j];
			JOptionPane.showMessageDialog(null,"Las cuentas que tienen un saldo exedente a $10,000 son:\n"+cuentas);
		}
	}
	
	static void saldoMax() {
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
			int vacio=0;
			float max=c.getItem(0).getSaldo();
			for(int i=0;i<c.getSize();i++)
				if(c.getItem(i).getSaldo()>max)
					max=c.getItem(i).getSaldo();
			CuentaBancaria copia[]=new CuentaBancaria[c.getSize()];
			for(int i=0;i<c.getSize();i++)
				if(c.getItem(i).getSaldo()==max) 
					copia[i-vacio]=c.getItem(i);
				else
					vacio++;
			String cuentas="";
			for(int j=0;j<(c.getSize()-vacio);j++)
				cuentas=cuentas+"\n"+copia[j];
			JOptionPane.showMessageDialog(null,"La/las cuenta/cuentas con mayor saldo es/son:\n"+cuentas);
		}
		
	}
	
	static void saldoMin() {
		if(c.isFree())
			JOptionPane.showMessageDialog(null,"Todav?a no hay ninguna cuenta");
		else {
			int vacio=0;
			float min=c.getItem(0).getSaldo();
			for(int i=0;i<c.getSize();i++)
				if(c.getItem(i).getSaldo()<min)
					min=c.getItem(i).getSaldo();
			CuentaBancaria copia[]=new CuentaBancaria[c.getSize()];
			for(int i=0;i<c.getSize();i++)
				if(c.getItem(i).getSaldo()==min) 
					copia[i-vacio]=c.getItem(i);
				else
					vacio++;
			String cuentas="";
			for(int j=0;j<(c.getSize()-vacio);j++)
				cuentas=cuentas+"\n"+copia[j];
			JOptionPane.showMessageDialog(null,"La/las cuenta/cuentas con menor saldo es/son:\n"+cuentas);
		}
	}

}
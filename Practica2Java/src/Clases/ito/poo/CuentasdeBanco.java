package Clases.ito.poo;

import excepcion.ito.poo.CuentaExistente;
import excepcion.ito.poo.BorrarCuenta;
import excepcion.ito.poo.Deposito;
import excepcion.ito.poo.Retiro;
import interfaz.ito.poo.Arreglo;
import Clases.ito.poo.CuentaBancaria;


public class CuentasdeBanco implements Arreglo<CuentaBancaria> {

	static CuentaBancaria e;

	private CuentaBancaria cuentas[]=null;
	private int ultimo=0;
	private final int INC=5;
	
	public CuentasdeBanco() {
		super();
		this.cuentas=new CuentaBancaria[INC];
		this.ultimo=-1;
	}
	
	public void Deposito(float deposito) throws Deposito{
		if (deposito<500F||deposito>25000F)
			throw new Deposito("No se puede depositar menos de 500$ o m�s de 25,000$");
	}
	public void Retiro(float retiro) throws Retiro{
		if (!((retiro%100)==0) || retiro<100 || retiro>6000)
			throw new Retiro("No se puede retirar m�s de 6000$ ni menos de 100$, y el retio debe ser divisible entre 100");
	}
	
	public void CuentaExistente(CuentaBancaria item) throws CuentaExistente{
		if(this.existeItem(item)) {
			throw new CuentaExistente("La cuenta ya existe");
		}
	}
	
	public void BorrarCuenta(CuentaBancaria item) throws BorrarCuenta{
		if(item.getSaldo()==0) {
			throw new BorrarCuenta("Una cuenta con saldo distinto a 0 no se puede eliminar");
		}
	}
	
	
	
	public void crecerArreglo() {
		CuentaBancaria copia[]=new CuentaBancaria[this.cuentas.length+INC];
		for(int i=0;i<cuentas.length;i++)
			copia[i]=this.cuentas[i];
		cuentas=copia;
	}
	
	@Override
	public boolean addItem(CuentaBancaria item) {
		boolean add=false;
		if(this.isFull()) 
			crecerArreglo();
		int j=0;
		for(;j<=this.ultimo;j++)
			if(item.compareTo(this.cuentas[j])<0) {
				break;
				}
				
				for(int i=this.ultimo;i>=j;i--)
					cuentas[i+1]=cuentas[i];
				this.cuentas[j]=item;
				this.ultimo++;
				add=true;
			
		return add;
	}

	@Override
	public boolean existeItem(CuentaBancaria item) {
		boolean existe=false;
		for(int i=0;i<=this.ultimo;i++)
			if(item.compareTo(this.cuentas[i])==0) {
				existe=true;
				break;
			}
					
		return existe;
	}
	
	

	@Override
	public CuentaBancaria getItem(int pos) {
		CuentaBancaria cb=null;
		if(pos<=this.ultimo&&!this.isFree())
			cb=this.cuentas[pos];
		return cb;
	}

	@Override
	public int getSize() {
		return this.ultimo+1;
	}

	@Override
	public boolean clear(CuentaBancaria item) {
		boolean borrar=false;
		if(this.existeItem(item)) {
			int i=0;
			for(;i<=this.ultimo;i++)
				if(item.compareTo(this.cuentas[i])==0)
					break;
			for(;i<=this.ultimo;i++)
				cuentas[i]=cuentas[i+1];
			this.ultimo--;
			borrar=true;
		}
		return borrar;
	}

	@Override
	public boolean isFree() {
		return this.ultimo==-1;
	}

	@Override
	public boolean isFull() {
		
		return this.ultimo+1==this.cuentas.length;
	}
	
	

}
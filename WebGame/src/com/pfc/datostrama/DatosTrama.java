package com.pfc.datostrama;

public class DatosTrama {
	
	private Aceleraciones aceleraciones;
	private DatosXBee datosXBee;

	public DatosTrama() {
	}

	public Aceleraciones getAceleraciones() {
		return aceleraciones;
	}

	public void setAceleraciones(Aceleraciones aceleraciones) {
		this.aceleraciones = aceleraciones;
	}

	public DatosXBee getDatosXBee() {
		return datosXBee;
	}

	public void setDatosXBee(DatosXBee datosXBee) {
		this.datosXBee = datosXBee;
	}

	public static DatosTrama getData(String trama) {
		
		DatosTrama datosTrama = new DatosTrama();
		Aceleraciones aceleraciones = new Aceleraciones();
		DatosXBee datosXBee = new DatosXBee();

		// Delimitadores correctos
		if (trama != null && trama.length() > 0 && trama.startsWith("IT")
				&& trama.endsWith("FT")) {

			String datos = trama.substring(2, trama.length() - 2);
			String[] datosSeparados = datos.split(",");
			// HEXADECIMAL
			datosXBee.setDireccion(Integer.valueOf(
					datosSeparados[1].substring(2), 16));
			datosXBee.setIdRed(Integer.valueOf(datosSeparados[1].substring(2),
					16));
			datosXBee.setCanal(Integer.valueOf(datosSeparados[2].substring(2),
					16));

			aceleraciones.setX(Double.valueOf(datosSeparados[4].substring(1)));
			aceleraciones.setY(Double.valueOf(datosSeparados[5].substring(1)));
			aceleraciones.setZ(Double.valueOf(datosSeparados[6].substring(1)));

			datosTrama.setAceleraciones(aceleraciones);
			datosTrama.setDatosXBee(datosXBee);
		}
		return datosTrama;
	}
}

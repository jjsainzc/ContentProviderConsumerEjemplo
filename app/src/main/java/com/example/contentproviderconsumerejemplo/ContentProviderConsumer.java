package com.example.contentproviderconsumerejemplo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.example.contentproviderconsumerejemplo.utilidades.SQLite;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ContentProviderConsumer extends Activity {

	private static final String URI = "content://com.contentprovider/personas";
	private TextView resultado;
	private StringBuilder sb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_provider_consumer);

		resultado = (TextView) findViewById(R.id.resultado);
		sb = new StringBuilder();
	}

	public void accionLeer(View v) {
		// Columnas de la tabla a recuperar
				String[] columnas = new String[] { "persona_id", "nombre", "cedula",
						"fechaNac", "estadoCivil", "discapacitado", "estatura" };

				sb.setLength(0);
				Uri clientesUri = Uri.parse(URI);

				ContentResolver cr = getContentResolver();

				Cursor cur = cr.query(clientesUri, null, // Columnas a devolver
						null, // Condición del query persona_id = 1
						null, // Argumentos variables del query
						null); // Orden de los resultados

				if (cur == null) {
					Map<String, Object> dato = new HashMap<String, Object>();

					dato.put("nombre", "Persona 3");
					dato.put("cedula", "3333333");
					dato.put("fechaNac", "2000-06-30");
					dato.put("estadoCivil", "casado");
					dato.put("discapacitado", 0);
					dato.put("estatura", 5.2f);

					cr.insert(clientesUri, SQLite.convertMapToValues(dato));

				} else {
					List<Map<String, ?>> datos = SQLite.cursorToLista(cur);
					if (datos != null) {
						for (Iterator<Map<String, ?>> it = datos.iterator(); it
								.hasNext();) {
							Map<String, ?> registro = (Map<String, ?>) it.next();
							sb.append(registro.toString()).append("\n");
						}
					} else
						Toast.makeText(this, "Proveedor no tiene datos",
								Toast.LENGTH_LONG).show();
				}

				resultado.setText(sb.toString());
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.content_provider_consumer, menu);

		

		return true;
	}

}

package com.example.createpdf;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import harmony.java.awt.Color;

public class MainActivity extends AppCompatActivity {

    String nombreDirectorio = "MisPDF´s";
    String nombreDocumento = "MiPdf.pdf";

    EditText et1, etNombreArchivo;
    Button btnGenerar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et1 = (EditText)findViewById(R.id.editTextTextPersonName);
        btnGenerar = (Button)findViewById(R.id.button);
        etNombreArchivo = (EditText)findViewById(R.id.etNombreArchivo);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                        PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }

        btnGenerar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            crearPDF();
                Toast.makeText(MainActivity.this, "Se creo un PDF", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void crearPDF() {
        Document document = new  Document();
        try {
            File file = crearFichero(this.etNombreArchivo.getText().toString() + ".pdf");
            FileOutputStream ficheroPDF = new FileOutputStream(file.getAbsoluteFile());
            PdfWriter pdfWriter = PdfWriter.getInstance(document, ficheroPDF);
            document.open();
            document.add(new Paragraph("asdfadsf /n/n"));
            document.add(new Paragraph(et1.getText().toString()+"/n/n"));

            //Crear la Tabla
            PdfPTable pdfPTable = new PdfPTable(5);
            pdfPTable.addCell("Damas");
            pdfPTable.addCell("Caballeros");
            pdfPTable.addCell("Jovenes");
            pdfPTable.addCell("Niños");
            pdfPTable.addCell("Visitas");
            pdfPTable.addCell("15");

            //Celda
            PdfPCell pdfPCell = new PdfPCell();
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(Color.cyan);

            for (int i=0; i<15; i++){
                pdfPTable.addCell("Celta " + i);
            }
            document.add(pdfPTable);

        }catch (DocumentException e) {
        }catch (IOException e){
        }finally {
            document.close();
        }
    }

    public File crearFichero(String nombreFichero){
        File ruta = getRuta();
        File fichero = null;
        if (ruta!=null){
            fichero = new File(ruta, nombreFichero);
        }
        return fichero;
    }

    public File getRuta(){
        File ruta = null;

        if(Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
            ruta = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), this.nombreDirectorio);
            if (ruta !=null){
                if (!ruta.mkdirs()){
                    if (!ruta.exists()){
                        return null;
                    }
                }

            }
        }
        return ruta;
    }

}

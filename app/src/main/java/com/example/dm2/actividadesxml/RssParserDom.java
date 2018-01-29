package com.example.dm2.actividadesxml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * Created by dm2 on 13/12/2017.
 */

public class RssParserDom {
    private URL rssURL;
    public RssParserDom (String url){
        try{
            this.rssURL =new URL (url);
        }catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<String> parse() {
        //Instanciamos la fabrica para DOM
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        List<String> prediccion = new ArrayList<String>();
        try {
            //Creamos un nuevo parser DOM
            DocumentBuilder builder = factory.newDocumentBuilder();
            //Realizamos la lectura completa del XML
            Document dom = builder.parse(this.getInputStream());
            //Nos posicionamos en el nodo principal del árbol (<rss>)
            Element root = dom.getDocumentElement();
            //Localizamos todos los elemntos <item>
            NodeList items = root.getElementsByTagName("prediccion");

            //Obtenemos la noticia actual
            Node item = items.item(0);
            //Obtenemos la lista de datos de la noticia actual
            NodeList datosTiempo = item.getChildNodes();
            //Procesamos cada dato de la noticia
            for (int j=0; j<datosTiempo.getLength(); j++){
                Node dato = datosTiempo.item(j);
                String etiqueta = dato.getNodeName();
                Date fecha=new Date();
                String fechaFormat=new SimpleDateFormat("yyyy-MM-dd").format(fecha);
                if (etiqueta.equals("dia") && dato.getAttributes().equals(fechaFormat)){ //POENEMOS LOS ATRIBVUTPOS CVOMPARANDO LA FECHA ACTUAL
                    String texto = obtenerTexto(dato);
                    prediccion.add(texto);

                }
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (Exception ex){
            throw new RuntimeException(ex);
        }
        return prediccion;
    }
    public String obtenerTexto (Node dato) {
        StringBuilder texto = new StringBuilder();
        NodeList fragmentos = dato.getChildNodes();
        for (int k=0; k<fragmentos.getLength(); k++) {
            texto.append(fragmentos.item(k).getNodeValue());
        }
        return texto.toString();
    }
    private InputStream getInputStream() {
        try {
            return rssURL.openConnection().getInputStream();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

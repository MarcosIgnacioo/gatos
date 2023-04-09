package org.example;

import com.google.gson.Gson;
import okhttp3.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class GatoService {
    public static void verGatos() throws IOException {
        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("text/plain");
        Request request = new Request.Builder()
                .url("https://api.thecatapi.com/v1/images/search")
                .get()
                .build();
        Response response = client.newCall(request).execute();
        String elJson = response.body().string();

        // se ocupan cortar 2 llaves para que pueda ser entendido como un objeto gato para json

        elJson = elJson.substring(1,elJson.length()); //corta la primera posicion
        elJson = elJson.substring(0,elJson.length()-1); // corta la ultima posicion
        // lo que hace es reescribir todo el string pero desde la posicion que se le indica en el primer parametro, y como en la posicion 0 hay
        // la primera llave, esta es la que se quiere cortar, por lo que se omite esta poniendo 1 y se escribe hasta el final
        // lo mismo pasa para el otro, ya que ahora el que se quiere cortar es el ultimo, se escribe ahora desde el inicio hasta la penultima cosa del string
        // que es el caracter previo a la ultima llave es como lo de recortar videos  en el telefono


        Gson gson = new Gson();
        Gatos gatitos = gson.fromJson(elJson, Gatos.class);
        // convierte el json a la clase gato para que se pueda ser utilizada por ella, en la cual se van a almacenar la url, los tama;os etc, ahi estan definidas
        // las variables al inicio de la misma

        Image image = null;
        try{
            URL url = new URL(gatitos.getUrl());
            image = ImageIO.read(url);
            ImageIcon gatoFoto = new ImageIcon(image);
            if (gatoFoto.getIconWidth() > 800){
                Image foto = gatoFoto.getImage();
                Image modificada = foto.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                gatoFoto = new ImageIcon(modificada);
            }
            String menu = "Opciones: \n" +
                          "1.- Ver otra imagen\n"+
                          "2.- Favorito\n"+
                          "3.- Volver \n";
            String botones [] = {"Ver otra imagen", "Favorito","Ver favoritos" ,"Volver"};
            String idGato = gatitos.getId();
            String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, gatoFoto,botones,botones[0]);
            int seleccion = -1;
            for (int i = 0; i<botones.length; i++){
                if (opcion == botones[i]){
                    seleccion = i;
                }
            }
            switch (seleccion){
                case 0:
                    verGatos();
                    break;
                case 1:
                    favoritoGato(gatitos);
                    break;
                case 2:
                    verFavoritos(gatitos);
                    break;
                default:
                    break;
            }
        }catch (Exception e){
            System.out.println(e);
        }
    }

    private static void favoritoGato(Gatos gato) {
        try{
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, "{\r\n    \"image_id\": \""+gato.getId()+"\"\r\n}");
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites?")
                    .method("POST", body)
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", gato.getApikey())
                    .build();
            Response response = client.newCall(request).execute();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    private static void verFavoritos(Gatos gato){
        try{
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            Request request = new Request.Builder()
                    .url("https://api.thecatapi.com/v1/favourites")
                    .get()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("x-api-key", "live_d9U3hV4HL1urhHrtzswTrm89gwp0QTc2gpXaRXWNfG5ABhklfkvdvqsKXMWXuLWc")
                    .build();
            Response response = client.newCall(request).execute();

            String json = response.body().string();
            Gson gson = new Gson();
            GatoFav[] gatosFavoritos = gson.fromJson(json, GatoFav[].class);
            System.out.println(gatosFavoritos[3]);
            System.out.println(gatosFavoritos.length);
            for (int i = 0; i<gatosFavoritos.length; i++){
                System.out.println(i);
                String menu = "Opciones: \n" +
                        "1.- Ver siguiente favorito\n"+
                        "2.- Volver\n";
                String botones [] = {"Siguiente", "Volver"};
                String idGato = String.valueOf(gatosFavoritos[i].getId());
                String opcion = (String) JOptionPane.showInputDialog(null, menu, idGato, JOptionPane.INFORMATION_MESSAGE, descargarImagen(gatosFavoritos[i]),botones,botones[0]);
                System.out.println(gatosFavoritos[i].getImage().getUrl());
                if (opcion == botones[1]){
                    break;
                }
            }
        }catch(Exception e){
            System.out.println(e);
        }
    }

    public static ImageIcon descargarImagen(GatoFav gatitos){
        Image image = null;
        try{
            URL url = new URL(gatitos.image.getUrl());
            image = ImageIO.read(url);
            ImageIcon gatoFoto = new ImageIcon(image);
            if (gatoFoto.getIconWidth() > 800){
                Image foto = gatoFoto.getImage();
                Image modificada = foto.getScaledInstance(800,600, Image.SCALE_SMOOTH);
                gatoFoto = new ImageIcon(modificada);
            }

            return gatoFoto;

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package org.example;

public class GatoFav {
    int id;
    String imageId;
    String apiKey = "live_d9U3hV4HL1urhHrtzswTrm89gwp0QTc2gpXaRXWNfG5ABhklfkvdvqsKXMWXuLWc";
    ImageX image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public ImageX getImage() {
        return image;
    }

    public void setImage(ImageX image) {
        this.image = image;
    }
}

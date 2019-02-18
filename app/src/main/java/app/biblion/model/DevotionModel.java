package app.biblion.model;

public class DevotionModel {

    /**
     * title : Yoga
     * description : <p><strong>True love is not a strong, fiery, impetuous passion. It is, on the contrary, an element calm and deep. It looks beyond mere externals, and is attracted by qualities alone. It is wise and discriminating, and its devotion is real and abiding</strong></p>\r\n\r\n<p><em>True love is not a strong, fiery, impetuous passion. It is, on the contrary, an element calm and deep. It looks beyond mere externals, and is attracted by qualities alone. It is wise and discriminating, and its devotion is real and abiding</em></p>\r\n\r\n<p><strong>True love is not a strong, fiery, impetuous passion. It is, on the contrary, an element calm and deep. It looks beyond mere externals, and is attracted by qualities alone. It is wise and discriminating, and its devotion is real and abiding</strong><br />\r\n&nbsp;</p>
     * image : http://frozenkitchen.in/biblion_demo/public/images/Penguins.jpg
     */

    private String title;
    private String description;
    private String image;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

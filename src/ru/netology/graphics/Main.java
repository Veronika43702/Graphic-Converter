package ru.netology.graphics;

import ru.netology.graphics.image.TextGraphicsConverter;
import ru.netology.graphics.image.TextGraphicsConverterMyVersion;
import ru.netology.graphics.server.GServer;

public class Main {
    public static void main(String[] args) throws Exception {
        TextGraphicsConverter converter = new TextGraphicsConverterMyVersion();
        GServer server = new GServer(converter); // Создаём объект сервера
        server.start(); // Запускаем

        // Или то же, но с выводом на экран:
//        String url = "https://w7.pngwing.com/pngs/30/283/png-transparent-grumpy-cat-youtube-kitten-lolcat-cat-face-animals-cat-like-mammal-thumbnail.png";
//        String imgTxt = converter.convert(url);
//        System.out.println(imgTxt);
    }
}

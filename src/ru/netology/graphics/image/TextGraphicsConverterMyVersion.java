package ru.netology.graphics.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.net.URL;

public class TextGraphicsConverterMyVersion implements TextGraphicsConverter {
    private char[][] matrix;
    private int maxWidth;
    private int maxHeight;
    private double maxRatio;
    private TextColorSchema schema;

    @Override
    public String convert(String url) throws IOException, BadImageSizeException {
        // скачивание картинки
        BufferedImage img = ImageIO.read(new URL(url));

        // проверка соотношения сторон
        double ratio = (double) img.getWidth() / img.getHeight();
        if (maxRatio != 0.0 & ratio > maxRatio) {
            throw new BadImageSizeException(ratio, maxRatio);
        }

        // задание ширины и высоты
        int newWidth;
        int newHeight;
        double proportion;
        if ((img.getWidth() > maxWidth & maxWidth != 0) || img.getHeight() > maxHeight & maxHeight != 0) {
            if (maxHeight != 0 &
                    maxWidth != 0 &
                    (double) img.getWidth() / maxWidth > (double) img.getHeight() / maxHeight || maxHeight == 0) {
                proportion = (double) img.getWidth() / maxWidth;
            } else {
                proportion = (double) img.getHeight() / maxHeight;
            }
        } else {
            proportion = 1;
        }
        newWidth = (int) (img.getWidth() / proportion);
        newHeight = (int) (img.getHeight() / proportion);

        // изменение размеров
        Image scaledImage = img.getScaledInstance(newWidth, newHeight, BufferedImage.SCALE_SMOOTH);
        // создание черно-белой пустой картинки с указанными размерами
        BufferedImage bwImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_BYTE_GRAY);
        // создание инструмента для рисования в картинке
        Graphics2D graphics = bwImg.createGraphics();
        // копирование (рисование) картинки (img с указанными размерами)
        graphics.drawImage(scaledImage, 0, 0, null);

        // запись каждого пикселя в массив с дублированием пискселя на строчке
        // для более широкой картинки
        matrix = new char[newHeight][newWidth * 2];
        WritableRaster bwRaster = bwImg.getRaster();
        for (int i = 0; i < newHeight; i++) {
            int pixel = 0;
            for (int j = 0; j < newWidth * 2; j += 2) {
                int color = bwRaster.getPixel(pixel, i, new int[3])[0];
                if (schema == null) {
                    schema = new TextColorDefault();
                }
                char c = schema.convert(color);
                matrix[i][j] = c;
                matrix[i][j + 1] = c;
                pixel++;
            }
        }

        // создание текста из символов в массиве
        StringBuilder pixureOfSymbols = new StringBuilder();
        for (int i = 0; i < newHeight; i++) {
            for (int j = 0; j < newWidth * 2; j++) {
                pixureOfSymbols.append(matrix[i][j]);
            }
            pixureOfSymbols.append("\n");
        }
        return pixureOfSymbols.toString();
    }

    @Override
    public void setMaxWidth(int width) {
        this.maxWidth = width;
    }


    @Override
    public void setMaxHeight(int height) {
        this.maxHeight = height;
    }

    @Override
    public void setMaxRatio(double maxRatio) {
        this.maxRatio = maxRatio;
    }

    @Override
    public void setTextColorSchema(TextColorSchema schema) {
        this.schema = schema;
    }
}

package ru.netology.graphics.image;

import java.util.ArrayList;
import java.util.List;

public class TextColorDefault implements TextColorSchema {
    ArrayList<Character> symbols = new ArrayList<>(List.of('#', '$', '@', '%', '*', '+', '-', '\''));

    @Override
    public char convert(int color) {
        return symbols.get(color / (256 / symbols.size()));
    }
}

package com.example.pokemonacademy.Entity;

public class Lecture {
    public final static int WORDS_PER_SLIDE = 50;

    private int _world;
    private String _title;
    private String _content;
    private String[] _slides;
    private int _totalSlides = -1;

    public Lecture(int world, String title, String content)  {
        this._world = world;
        this._title = title;
        this._content = content;

        this.splitContentBySlides(WORDS_PER_SLIDE);
    }

    private boolean splitContentBySlides(int wordsPerSlide)   {

        if(_content == null || _content.isEmpty()) return false;

        // count string by words
        String[] splitted_content = _content.split(" ");
        int wordcount = splitted_content.length;
        String stringBuilder = "";

        // round up
        _totalSlides = (int) Math.ceil((double) wordcount / (double) wordsPerSlide);

        for(int i=0; i<_totalSlides; i++)   {
            for(int j=i*wordsPerSlide; j<wordcount; j++)    {
                stringBuilder += splitted_content[j];
            }
            _slides[i] = stringBuilder;
            stringBuilder = "";
        }

        return true;
    }

    public String getSlideByNumber(int slideNumber) {
        if(slideNumber > _totalSlides || slideNumber <= 0) return "";

        return _slides[slideNumber - 1];
    }
}

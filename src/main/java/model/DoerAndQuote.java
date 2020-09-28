package model;

public class DoerAndQuote {
    private String surName;
    private String name;
    private String text;
    private Long id = 0L;


//    public QuoteDoer(String surName, String name, String text) {
//        this.surName = surName;
//        this.name = name;
//        this.text = text;
//    }

    public String getSurName() {
        return surName;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


}


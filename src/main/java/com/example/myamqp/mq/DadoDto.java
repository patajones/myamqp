package com.example.myamqp.mq;

import java.io.Serializable;

public class DadoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String original;
    private String maisculo;

    public String getOriginal() {
        return original;
    }

    public String getMaisculo() {
        return maisculo;
    }

    public void setMaisculo(String maisculo) {
        this.maisculo = maisculo;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    public String toString() {
        return "[original: \""+original+"\", maiusculo: \""+maisculo+"\"]";
    }

}
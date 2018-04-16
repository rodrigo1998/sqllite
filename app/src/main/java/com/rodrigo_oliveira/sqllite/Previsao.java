package com.rodrigo_oliveira.sqllite;

public class Previsao {
    private int id;
    private String descricao, diaDaSemana, nomeCidade, icone;
    private double min, max;
    private int humidade;


    public Previsao (String descricao, String diaDaSemana, double min,
                     double max, int humidade, String nomeCidade, String icone){
        this.descricao = descricao;
        this.diaDaSemana = diaDaSemana;
        this.min = min;
        this.max = max;
        this.humidade = humidade;
        this.nomeCidade = nomeCidade;
        this.icone = icone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Previsao(){

    }

    public String getIcone() {
        return icone;
    }

    public void setIcone(String icone) {
        this.icone = icone;
    }

    public String getDiaDaSemana() {
        return diaDaSemana;
    }

    public void setDiaDaSemana(String diaDaSemana) {
        this.diaDaSemana = diaDaSemana;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public int getHumidade() {
        return humidade;
    }

    public void setHumidade(int humidade) {
        this.humidade = humidade;
    }

    public String getNomeCidade() {
        return nomeCidade;
    }

    public void setNomeCidade(String nomeCidade) {
        this.nomeCidade = nomeCidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Previsao previsao = (Previsao) o;

        if (Double.compare(previsao.min, min) != 0) return false;
        if (Double.compare(previsao.max, max) != 0) return false;
        if (humidade != previsao.humidade) return false;
        if (descricao != null ? !descricao.equals(previsao.descricao) : previsao.descricao != null)
            return false;
        if (diaDaSemana != null ? !diaDaSemana.equals(previsao.diaDaSemana) : previsao.diaDaSemana != null)
            return false;
        if (nomeCidade != null ? !nomeCidade.equals(previsao.nomeCidade) : previsao.nomeCidade != null)
            return false;
        return icone != null ? icone.equals(previsao.icone) : previsao.icone == null;
    }
}

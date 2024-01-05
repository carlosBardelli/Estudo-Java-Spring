package br.com.alura.screemmatch.principal;

import br.com.alura.screemmatch.model.DadosSerie;
import br.com.alura.screemmatch.model.DadosTemporada;
import br.com.alura.screemmatch.service.ConsumoApi;
import br.com.alura.screemmatch.service.ConverteDados;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private static final String ENDERECO = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=6585022c";
    private String endPoint;
    private  ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();


    public void exibeMenu(){
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();
        endPoint = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        String json = consumoApi.obterDados(endPoint);
        DadosSerie dadosSerie = converteDados.obterDados(json,DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

		for (int numeroTemporada = 1; numeroTemporada <= dadosSerie.totalTemporadas(); numeroTemporada++){
            endPoint = ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + numeroTemporada + API_KEY;
			json = consumoApi.obterDados(endPoint);
			DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
			temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        temporadas.forEach(temporada -> temporada.episodios()
                .forEach(episodeo -> System.out.println(episodeo.titulo()))
        );
    }
}

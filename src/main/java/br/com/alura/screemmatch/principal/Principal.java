package br.com.alura.screemmatch.principal;

import br.com.alura.screemmatch.model.DadosEpisodio;
import br.com.alura.screemmatch.model.DadosSerie;
import br.com.alura.screemmatch.model.DadosTemporada;
import br.com.alura.screemmatch.model.Episodio;
import br.com.alura.screemmatch.service.ConsumoApi;
import br.com.alura.screemmatch.service.ConverteDados;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {

    private Scanner leitura = new Scanner(System.in);
    private static final String ENDERECO = "https://www.omdbapi.com/?t=";
    private static final String API_KEY = "&apikey=6585022c";
    private String endPoint;
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados converteDados = new ConverteDados();


    public void exibeMenu() {
        System.out.println("Digite o nome da série para busca");
        String nomeSerie = leitura.nextLine();
        endPoint = ENDERECO + nomeSerie.replace(" ", "+") + API_KEY;
        String json = consumoApi.obterDados(endPoint);
        DadosSerie dadosSerie = converteDados.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList<>();

        for (int numeroTemporada = 1; numeroTemporada <= dadosSerie.totalTemporadas(); numeroTemporada++) {
            endPoint = ENDERECO + nomeSerie.replace(" ", "+") + "&season=" + numeroTemporada + API_KEY;
            json = consumoApi.obterDados(endPoint);
            DadosTemporada dadosTemporada = converteDados.obterDados(json, DadosTemporada.class);
            temporadas.add(dadosTemporada);
        }
//        temporadas.forEach(System.out::println);
//
//        temporadas.forEach(temporada -> temporada.episodios()
//                .forEach(episodio -> System.out.println(episodio.titulo()))
//        );

//        List<DadosEpisodio> dadosEpisodios = temporadas.stream()
//                .flatMap(t -> t.episodios().stream())
//                .collect(Collectors.toList());


//        dadosEpisodios.stream()
//                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
//                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
//                .limit(5)
//                .forEach(System.out::println);

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(d -> new Episodio(t.numero(), d))
                ).collect(Collectors.toList());

        episodios.forEach(System.out::println);
//        System.out.println("escola o ano para filtrar");
//        Integer ano = leitura.nextInt();
//        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
//
//        DateTimeFormatter formatador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//        episodios.stream()
//                .filter(e -> e.getDataLacamento() != null && e.getDataLacamento().isAfter(dataBusca))
//                .forEach(e -> System.out.println(
//                        "Temporada: " + e.getTemporada() +
//                        " Episodio:" + e.getTitulo() +
//                        " Data: " + e.getDataLacamento().format(formatador)
//                ));

        System.out.println("digite o nome do episodio");

        String trechoTitulo = leitura.nextLine().toUpperCase();

//        Optional<Episodio> episodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo))
//                .findFirst();
//        if(episodioBuscado.isPresent()){
//            System.out.println("Episodio encontrado!!");
//            System.out.println("Temporada: " + episodioBuscado.get());
//        }else {
//            System.out.println("Episodio não encontrado");
//        }

//        List<Episodio> listaEpisodioBuscado = episodios.stream()
//                .filter(e -> e.getTitulo().toUpperCase().contains(trechoTitulo))
//                .collect(Collectors.toList());
//
//        listaEpisodioBuscado.forEach(System.out::println);

    }

}

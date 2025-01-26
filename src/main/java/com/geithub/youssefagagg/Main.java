package com.geithub.youssefagagg;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Main {
  public static void main(String[] args) throws Exception {
    String response = ApiFetcher.fetchDataFromApi(2, 1);
    System.out.println(response);
    int perPageIndex = response.indexOf("per_page\":");
    int firstComa = response.indexOf(",", perPageIndex);
    int perPage= Integer.parseInt(response.substring(perPageIndex+10, firstComa));
    int totalIndex = response.indexOf("total\":");
    int firstComa2 = response.indexOf(",", totalIndex);
    int total= Integer.parseInt(response.substring(totalIndex+7, firstComa2));
    int indexTotal_pages = response.indexOf("total_pages\":");
    int firstComa3 = response.indexOf(",", indexTotal_pages);
    int totalPages= Integer.parseInt(response.substring(indexTotal_pages+13, firstComa3));
    System.out.println(totalPages);
    System.out.println(total);
    System.out.println(perPage);
    List<Pair> temp= new ArrayList<>();
    if (totalPages==0){
      System.out.println("0");
      return;
    }
    System.out.println("********");
    temp.add(getTotalOfBodyTemp(response));
    List<Integer>l =new ArrayList<>();

    for (int i = 2; i <= totalPages; i++) {
      String response2 = ApiFetcher.fetchDataFromApi(1, i);
      Pair d2=getTotalOfBodyTemp(response2);
      temp.add(d2);
      System.out.println(d2);
    }
    double x = temp.stream().mapToDouble(value -> value.sum).sum() / temp.stream().mapToInt(value -> value.count).sum();

    System.out.println(String.format("%.1f", x));

  }

  private static Pair getTotalOfBodyTemp(String response) {
    int index = response.indexOf("bodyTemperature\":");
    double sum = 0;
    int size =17;
    int count = 0;
    while (index >= 0) {
      int firstComa4 = response.indexOf("},", index);
      double temp = Double.parseDouble(response.substring(index + size, firstComa4));
        sum += temp;
      index = response.indexOf("bodyTemperature\":", index + 1);
      count++;
    }
    return new Pair(sum, count);
  }
  static class Pair{
    double sum;
    int count;

    public Pair(double sum, int count) {
      this.sum = sum;
      this.count = count;
    }
  }


  static class ApiFetcher {

    private static final String USER_AGENT = "Mozilla/5.0";


    public static String fetchDataFromApi(int userId, int page) throws Exception {
      String stringUrl ="https://jsonmock.hackerrank.com/api/medical_records?"+"userId="+userId+"&page="+page;
      URL url = new URL(stringUrl);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.setRequestProperty("User-Agent", USER_AGENT);

      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
          response.append(inputLine);
        }
        in.close();
        return response.toString();
      } else {
        throw new RuntimeException("Failed : HTTP error code : " + responseCode);
      }
    }
  }

}
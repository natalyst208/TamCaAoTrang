package module;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

import dictionary.DictionaryManagement;
import utility.ProjectConfig;


public class LevenshteinAlthogrim {

    static DictionaryManagement myDictionary = DictionaryManagement.getDictionaryManagement();

    /*
    https://www.geeksforgeeks.org/edit-distance-dp-5/
    https://stackoverflow.com/questions/33722217/improving-search-result-using-levenshtein-distance-in-java
    javacode
     */
    public static int calcLevenshteinAlthogrim(String A, String B) {
        int n = A.length();
        int m = B.length();
        A = " " + A;    B = " " + B;
        int[][] f = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                f[i][j] = f[i - 1][j] + 1;
                f[i][j] = Math.min(f[i][j], f[i][j - 1] + 1);
                int temp = A.charAt(i) == B.charAt(j) ? 0 : 1;
                f[i][j] = Math.min(f[i][j], f[i - 1][j - 1] + temp);
            }
        }
        return f[n][m];
    }

    public static String[] getMostSimilar(String word){
        ResultSet resultSet = myDictionary.SearchDic(word.substring(0, (word.length() + 1)/2));
        String[] result = new String[ProjectConfig.numberSimilarWord];
        ArrayList<Pair> arrayList = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                String tempWord = resultSet.getString("word");
                int tempsimilarity = calcLevenshteinAlthogrim(tempWord, word);
                arrayList.add(new Pair(tempWord, tempsimilarity));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(arrayList);
        for (int i = 0; i < Math.min(ProjectConfig.numberSimilarWord, arrayList.size()); i++) {
            result[i] = arrayList.get(i).word;
        }
        return result;
    }
}

class Pair implements Comparable<Pair> {
    String word;
    int similarity;

    public Pair(String word, int similarity) {
        this.word = word;
        this.similarity = similarity;
    }

    @Override
    public int compareTo(Pair other) {
        return this.similarity- other.similarity;
    }
}

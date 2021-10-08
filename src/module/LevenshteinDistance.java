package module;

import dictionary.DictionaryManagement;
import utility.ProjectConfig;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class LevenshteinDistance {

    static DictionaryManagement myDictionary = DictionaryManagement.getDictionaryManagement();

    public static int calcLevenshteinDistance(String A, String B) {
        int n = A.length();
        int m = B.length();
        A = " " + A;    B = " " + B;
        int[][] f = new int[n + 1][m + 1];
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= m; j++) {
                f[i][j] = f[i - 1][j] + 2;
                f[i][j] = Math.min(f[i][j], f[i][j - 1] + 2);
                int temp = A.charAt(i) == B.charAt(j) ? 0 : 1;
                f[i][j] = Math.min(f[i][j], f[i - 1][j - 1] + temp);
            }
        }
        return f[n][m];
    }

    public static String[] getTopScore(String word){
        ResultSet resultSet = myDictionary.SearchDic(word.substring(0, (word.length() + 1)/2));
        String[] result = new String[ProjectConfig.numberDidYouMeanWord];
        ArrayList<Pair> arrayList = new ArrayList<>();
        while (true) {
            try {
                if (!resultSet.next()) break;
                String tempWord = resultSet.getString("word");
                int tempScore = calcLevenshteinDistance(tempWord, word);
                arrayList.add(new Pair(tempWord, tempScore));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        Collections.sort(arrayList);
        for (int i = 0; i < Math.min(ProjectConfig.numberDidYouMeanWord, arrayList.size()); i++) {
            result[i] = arrayList.get(i).word;
        }
        return result;
    }
}

class Pair implements Comparable<Pair> {
    String word;
    int score;

    public Pair(String word, int score) {
        this.word = word;
        this.score = score;
    }

    @Override
    public int compareTo(Pair other) {
        return this.score- other.score;
    }
}

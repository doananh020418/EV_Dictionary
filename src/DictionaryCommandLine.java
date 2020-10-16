import javazoom.jl.decoder.JavaLayerException;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

public class DictionaryCommandLine extends DictionaryManagement {
    DictionaryManagement DM  =new DictionaryManagement();
    Scanner sc = new Scanner(System.in);
    Audio audio = new Audio();

    public void showAllWords() {
        System.out.println("No\t|English\t\t|Vietnamese");
        for(int i=0;i<DM.dict.numberOfWord;i++) {
            System.out.println(i+"\t|"+DM.dict.words[i].getWord_target() + "\t\t\t|" + DM.dict.words[i].getWord_explain());
        }
    }

    public void dictionaryBasic() {
        DM.insertFromCommandline();
        this.showAllWords();
    }

    public void insertFromFile() throws Exception {
        File file = new File("D:\\Dictionary\\src\\dictionaries.txt");
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        int count = 0;
        while ((st = br.readLine()) != null) {
            String a = st.split("\t")[0].trim();
            String b = st.split("\t")[1].trim();
            DM.dict.words[count]= new Word();
            DM.dict.words[count].setWord_target(a);
            DM.dict.words[count].setWord_explain(b);
            //System.out.println(st.split("\t")[1]);
            count++;
        }
        DM.dict.numberOfWord=count;
        //System.out.println(DM.dict.numberOfWord);
    }

    public void insertFromDataset() throws Exception {
        File file = new File("D:\\Dictionary\\src\\dataset.txt");
        //File file = new File("D:\\test\\dataTest.txt");
        FileInputStream fi = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fi, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);        String st;
        int count = 0;
        while ((st = br.readLine()) != null) {
            if(st.contains(",")) {
                String a = st.split(",")[0].trim();
                String b = String.copyValueOf(st.toCharArray(), a.length(), st.length()-a.length());
                if(b.contains("|")) {
                    b = b.replace("|","\n");
                    b = b.replace("=","Example: ");
                    b = b.replace("+","-->");
                }
                String c = " ";
                if(a.contains("[")){
                    String d = a;
                    a = d.split(" ")[0].trim();
                    c = d.split(" ")[1].trim();
                }
                DM.dict.words[count] = new Word();
                DM.dict.words[count].setWord_target(a);
                DM.dict.words[count].setWord_explain(b);
                DM.dict.words[count].setWord_pronun(c);
                //System.out.println(st.split("\t")[1]);
                count++;
            }
        }
        DM.dict.numberOfWord=count;
        //System.out.println(DM.dict.numberOfWord);
    }

    // map == dictionary
    TreeMap<String, String> map = new TreeMap<String, String>();

    public void insertFromDataset2() throws Exception {
        File file = new File("D:\\Dictionary\\src\\dataset.txt");
        //File file = new File("D:\\test\\dataTest.txt");
        FileInputStream fi = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fi, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);        String st;
        while ((st = br.readLine()) != null) {
            if(st.contains(",")) {
                String a = st.split(",")[0].trim();
                String b = String.copyValueOf(st.toCharArray(),a.length(), st.length() - a.length());
                if(b.contains("|")) {
                    b = b.replace("|","\n");
                    b = b.replace("=","Example: ");
                    b = b.replace("+","-->");
                }
                String c = " ";
                if(a.contains("[")){
                    String d = a;
                    a = d.split(" ")[0].trim();
                    c = d.split(" ")[1].trim();
                }
                map.put( a, c + "\n" + b);
            }
        }
    }

    public TreeMap<String , String> addWord(String word, String explain) throws IOException {
        map.put(word,explain);
        this.exportWordToFile(word,explain);
        DM.dict.numberOfWord++;
        return map;
    }

    public void updateDict(String word, String explain) {
        map.put(word,explain);
    }

    public void exportWordToFile(String a, String b) throws IOException {
        FileWriter fstream = new FileWriter("D:\\Dictionary\\src\\dataset.txt", true);
        BufferedWriter out = new BufferedWriter(fstream);
        out.write(a + "," + b + "\n" );
        out.flush();   // Flush the buffer and write all changes to the disk
        out.close();    // Close the file
    }

    public String dictionaryLookupVjp(String word) {
        return (map.get(word));
    }


    public void deleteWord(String word) {
        map.remove(word);
    }

    public void insertFromDatasetUB() throws Exception {
        //File file = new File("dataset.txt");
        File file = new File("D:\\Dictionary\\src\\dataTest.txt");
        FileInputStream fi = new FileInputStream(file);
        InputStreamReader isr = new InputStreamReader(fi, StandardCharsets.UTF_8);
        BufferedReader br = new BufferedReader(isr);        String st;
        int count=0;
        while ((st = br.readLine()) != null){
            if(st.contains("=")){
                String a=st.split("=")[0].trim();
                String b=st.split("=")[1];
                DM.dict.words[count]= new Word();
                DM.dict.words[count].setWord_target(a);
                DM.dict.words[count].setWord_explain(b);
                //System.out.println(st.split("\t")[1]);
                count++;
            }
        }
        DM.dict.numberOfWord=count;
        //System.out.println(DM.dict.numberOfWord);
    }

    public void dictionaryLookup() throws IOException, JavaLayerException {
        System.out.println("---------------------------------------------Lookup:--------------------------------------------");
        //String choice="y";
        //while (choice.equals("y")){
        System.out.println("Type your word: ");
        String word=sc.nextLine();
        int count=0;
        //System.out.println(DM.dict.numberOfWord);
        for(int i=0; i<DM.dict.numberOfWord; i++){
            if(DM.dict.words[i].getWord_target().equals(word.trim())) {
                System.out.println("Explain: "+DM.dict.words[i].getWord_explain() + "\n" + "Pronunciation: " + DM.dict.words[i].getWord_pronun());
                count++;
                audio.play(audio.getAudio(word.strip()));
            }
            else if((!DM.dict.words[i].getWord_target().equals(word.trim()))&i==DM.dict.numberOfWord-1&count==0){
                System.out.println("Sorry!We don't find your word:" + "\"" + word + "\"");
            }
        }
        //System.out.println("Do you want to find another word?(y/n) ");
        //Scanner sc=new Scanner(System.in);
        //choice=sc.nextLine();
        //if(choice.equals("y")){
        //this.dictionarySearcher();
        //}

        //}
    }
    public static boolean includes(String[] arr , String str ) {
        try {
            for (String s : arr) {
                while (s != null) {
                    if (s.equals(str)) {
                        return true;
                    } else {
                        break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }

        return false;
    }

    public void dictionarySearcher() {
        System.out.println("---------------------------------------------Searching:--------------------------------------------");
        System.out.println("Search:");
        String word=sc.nextLine();
        int count = 0;
        String[] str = new String[99999];
        for(int i = 0; i < DM.dict.numberOfWord; i++) {
            if(DM.dict.words[i].getWord_target().contains(word)) {
                if(!includes(str, DM.dict.words[i].getWord_target())) {
                    str[count] = DM.dict.words[i].getWord_target();
                    System.out.println(DM.dict.words[i].getWord_target());
                    count++ ;
                }
            }

        }
    }


    public void exportToFile() throws IOException {
        FileWriter fstream = new FileWriter("D:\\Dictionary\\src\\dataset.txt", true);
        BufferedWriter out = new BufferedWriter(fstream);
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            out.write(entry.getKey() + "," + entry.getValue() + "\n");
            out.flush();   // Flush the buffer and write all changes to the disk
        }

        out.close();    // Close the file
    }
    /*
    public InputStream onlineSearching(String text) throws IOException {
        URL url = new URL("https://translate.google.com/#view=home&op=translate&sl=en&tl=vi&text=" + text.replace(" ", "%20"));
        URLConnection urlConn = url.openConnection();
        urlConn.addRequestProperty("User-Agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        InputStream audioSrc = urlConn.getInputStream();
        return audioSrc;


    }

     */
    /*
    import com.google.cloud.translate.*;

    public void search(String text) {
        Translate translate = TranslateOptions.getDefaultInstance().getService();

        Translation translation = translate.translate("¡Hola Mundo!");
        System.out.printf("Translated Text:\n\t%s\n", translation.getTranslatedText());
    }

     */


    public void dictionaryAdvanced() throws Exception {
        //this.insertFromFile();
        //this.insertFromDataset2();
        this.insertFromDataset();
        //this.insertFromDatasetUB();
        //this.showAllWords();
        //this.dictionarySearcher();
        this.dictionaryLookup();
        //this.DM.updateDict();
        //this.DM.deleteWord();
        //this.dictionaryLookup();
        //this.showAllWords();
        //this.dictionarySearcher();
        //this.DM.dictionaryExportToFile();
        //System.out.printf(String.valueOf(this.map.size()));
        //this.map=this.addWord(sc.nextLine(),sc.nextLine());
        //this.updateDict(sc.nextLine(),sc.nextLine());
        //System.out.printf(String.valueOf(this.map.size()));
        //this.deleteWord(sc.nextLine());
        //this.exportToFile();
        //String a=this.dictionaryLookupVjp(sc.nextLine());
        //System.out.print(a);
    }
}

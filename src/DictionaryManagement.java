import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.stream.IntStream;

public class DictionaryManagement {
    Dictionary dict=new Dictionary();
    public void insertFromCommandline(){
        Scanner sc=new Scanner(System.in);
        System.out.println("Please type the number of words! \n");
        dict.numberOfWord=sc.nextInt();
        sc.nextLine();
        IntStream.range(0, dict.numberOfWord).forEach(i -> {
            dict.words[i] = new Word();
            System.out.println("Word " + i + ":");
            dict.words[i].setWord_target(sc.nextLine());
            System.out.println("Explain " + i + ":");
            dict.words[i].setWord_explain(sc.nextLine());
        });
        System.out.println(dict.words[0].getWord_explain());
    }
    public void updateDict(){
        System.out.println("---------------------------------------------Update:--------------------------------------------");
        //System.out.println("numberOfWord: "+dict.numberOfWord);
        Scanner sc=new Scanner(System.in);
        String choice="y";
        while (choice.equals("y")){
            System.out.println("Type the word need updated: ");
            String word;
            String mean;
            word=sc.nextLine();
            System.out.println("Type the word's meaning need updated: ");
            mean=sc.nextLine();
            //System.out.println(dict.words[0].getWord_target());

            for(int i=0;i<dict.numberOfWord;i++)
            {
                if(dict.words[i].getWord_target().strip().equals(word.strip())){
                    dict.words[i].setWord_explain(mean);
                    System.out.println("Updated!");
                    break;
                }
                else if(!dict.words[i].getWord_target().strip().equals(word.strip())&i==dict.numberOfWord-1){
                    dict.words[dict.numberOfWord]=new Word();
                    dict.words[dict.numberOfWord].setWord_target(word);
                    dict.words[dict.numberOfWord].setWord_explain(mean);
                    dict.numberOfWord+=1;
                }
            }
            System.out.println("Do you want to update another word?(y/n) ");
            choice=sc.nextLine();

        }
    }

    public void deleteWord() {
        System.out.println("---------------------------------------------Delete:--------------------------------------------");
        String choice="y";
        while (choice.equals("y")) {
            System.out.println("Type the word need deleting! ");
            String word;
            int k=-1;
            Scanner sc=new Scanner(System.in);
            word=sc.nextLine();
            for(int i=0;i<dict.numberOfWord;i++) {
                if(word.strip().equals(dict.words[i].getWord_target())) {
                    k=i;
                    break;
                }
                else if((!dict.words[i].getWord_target().equals(word.strip()))&i==dict.numberOfWord-1) {
                    System.out.println("Sorry!We don't find your word:"+"\"" + word + "\"");
                }
            }

            if(k!=-1){
                for(int i=k;i<dict.numberOfWord;i++){
                    dict.words[i]= new Word();
                    dict.words[i]=dict.words[i+1];
                }
                dict.words[dict.numberOfWord-1]=null;
                dict.numberOfWord--;
                System.out.println("\""+word+"\""+"Deleted!");
            }
            System.out.println("Do you want to delete another word?(y/n) ");
            choice=sc.nextLine();
        }
    }

    public void addWord() {
        Scanner sc=new Scanner(System.in);
        dict.words[dict.numberOfWord] = new Word();
        dict.words[dict.numberOfWord].setWord_target(sc.nextLine());
        dict.words[dict.numberOfWord].setWord_explain(sc.nextLine());
        dict.numberOfWord+=1;
    }

    public void dictionaryExportToFile() {
        IntStream.range(0, dict.numberOfWord).forEach(i -> {
            try {
                FileWriter writer = new FileWriter("D:\\Dictionary\\data.txt", true);
                writer.write(dict.words[i].getWord_target());
                writer.write("\t");
                writer.write(dict.words[i].getWord_explain());
                writer.write("\n");
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}

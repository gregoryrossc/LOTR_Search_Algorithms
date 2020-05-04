
/**
 * * I, Gregory Carroll, 000101968 certify that this material is my original work.
 * No other person's work has been used without due acknowledgement.
 *
 * @author Gregory Carroll
 */
import java.awt.print.Book;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import lab3.PosWord;
import java.lang.*;
import static java.lang.System.currentTimeMillis;
import java.text.DecimalFormat;
import mylab3.charInfo;

public class MyLab3 {

    /**
     * The starting point of the application
     *
     * @param args the command line arguments - not used
     */
    public static void main(String[] args) {
        // File is stored in a resources folder in the project
        final String novelName = "TheLordOfTheRIngs.txt";
        final String dictionaryFile = "US.txt";
        ArrayList<BookWord> bookWords = new ArrayList<BookWord>();
        SimpleHashSet<BookWord> sw = new SimpleHashSet<BookWord>(); //string word
        SimpleHashSet<String> hashDictionary = new SimpleHashSet<String>(); //hash dictionary
        ArrayList<String> dictionaryWords = new ArrayList<String>(); //
        ArrayList<BookWord> sixtyfourwords = new ArrayList<BookWord>(); //words that have the count of 64
        ArrayList<Integer> charCount = new ArrayList<Integer>(); //count of charactor
        int totalWordCount = 0; //count of total words in novel

        // Read in the dictionary
        try {
            Scanner fin = new Scanner(new File(dictionaryFile), "UTF-8");
            while (fin.hasNext()) {
                String w = fin.next();
                // TODO : Add code to store dictionary words into an appropriate data structure
                dictionaryWords.add(w.toLowerCase()); //making sure lists are exact with lower case
                hashDictionary.insert(w.toLowerCase()); //inserting and hashing the dictionary word into buckets
            }

            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        // Read in the text file
        try {
            Scanner fin = new Scanner(new File(novelName));
            fin.useDelimiter("\\s|\"|\\(|\\)|\\.|\\,|\\?|\\!|\\_|\\-|\\:|\\;|\\n");  // Filter - DO NOT CHANGE
            while (fin.hasNext()) {
                String fileWord = fin.next().toLowerCase();
                if (fileWord.length() > 0) {
                   
                    // TODO : Need to create an instance of a BookWord object here and add to ArrayList
                    BookWord bw = findBookWord(sw, fileWord);

                    if (bw != null) {
                        bw.incrementCount();
                    } else {
                        BookWord bookWordObject = new BookWord(fileWord);
                        bookWords.add(bookWordObject);
                        sw.insert(bookWordObject);

                    }
                    totalWordCount += 1; //increase count words in novel

                }
            }

            Collections.sort(dictionaryWords);

            //10 Most Frequent Words in File
            bookWords.sort((bw1, bw2) -> {
                if (bw1.getCount() == bw2.getCount()) {
                    return 0;
                }
                return bw1.getCount() > bw2.getCount() ? -1 : 1;
            });

            ArrayList<BookWord> top10 = new ArrayList<BookWord>();

            for (int i = 0; i < bookWords.size(); i++) {
                top10.add(bookWords.get(i));
            }

            //words that occur 64 times in the file
            for (int i = 0; i < bookWords.size(); i++) {

                if (bookWords.get(i).getCount() == 64) {
                    sixtyfourwords.add(bookWords.get(i));
                }
            }

            sortWords(sixtyfourwords); //call sort on sixtyfourwords array list

            System.out.println("\ntotal words in novel: " + totalWordCount); //total number of words in the file
            System.out.println("\ntotal unique words: " + bookWords.size()); //total num of unique words
            System.out.println("");
            for (int i = 0; i < 10; i++) {
                System.out.println("top 10 frequent words and count: " + top10.get(i));
            }
            System.out.println("");

            for (BookWord bws : sixtyfourwords) {
                System.out.println("words that occur 64 times: " + bws);
            }

            System.out.println("");
            System.out.println("not in dictionary:(Contains Method) " + notInDict(dictionaryWords, bookWords));
            System.out.println("not in dictionary:(Binary Search) " + binarySearch(dictionaryWords, bookWords));
            System.out.println("not in dictionary:(Hash Search) " + hashSearch(hashDictionary, bookWords));
            System.out.println("\n PART B: ");
            prox();

            fin.close();
        } catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }

        // TODO: Analyze and report code here based on lab requirements
        /*
         * The linear search takes the longest, Binary search is faster than a linear search 
         * but is slower than a hash search & a hash search is the fastest.
         *  
         * The normal linear search requires the program to search through every element in the array list which makes it the slowest of the three.
         * A binary search is able to find the index of the word we are searching for in the dictionary and check in the range of half the data each time it does 
         * a search, thus lessening the time required because we done have to search through the entire elements in the list. 
         * The hashing search is able to find a signature of data which is much faster than the other two methods.
         *
         */
    }

    public static BookWord findBookWord(SimpleHashSet<BookWord> list, String theWord) {

        BookWord temp = new BookWord(theWord);
        int index = temp.hashCode();
        if (index < 0) {
            index = -index;
        }
        index = index % list.numberOfBuckets;

        if (!list.buckets[index].isEmpty()) {

            for (int i = 0; i < list.buckets[index].size(); i++) {
                BookWord temp2 = list.buckets[index].get(i);
                if (temp2.equals(theWord)) {
                    return temp2;
                }
            }
        }

        return null;

    }

    //sorts the BookWord text / words alphabetically
    public static void sortWords(ArrayList<BookWord> sixtyfourwords) {
        sixtyfourwords.sort(Comparator.comparing(BookWord -> BookWord.getText().toLowerCase()));
    }

    //finds amount of words not found in the dictionary using contains
    public static int notInDict(ArrayList<String> words, ArrayList<BookWord> bookWords) {

        long startTime1 = currentTimeMillis();
        int countDict = 0;
        //  System.out.println(words.size());
        for (int i = 0; i < bookWords.size(); i++) {
            //    System.out.println(bookWords.get(i).getText());
            if (!words.contains(bookWords.get(i).getText())) {
                countDict++;
            }

        }
        long endTime1 = currentTimeMillis();
        System.out.println("Time Required normal Search: " + (endTime1 - startTime1) + "ms");
        return countDict; //count of words not in dic
    }

    //finds amount of words not found in the dictionary using binary search
    public static int binarySearch(ArrayList<String> words, ArrayList<BookWord> bookWords) {

        long startTime2 = currentTimeMillis();
        //must be sorted becfore we can properly binary search
        String bword = "";
        int count = 0;
        for (int i = 0; i < bookWords.size(); i++) {
            //get the text from the bookword.java
            bword = bookWords.get(i).getText();
            //get the index of the word if it does not exist return a negative number
            int index = Collections.binarySearch(words, bword);
            //compare the index to 0
            if (index < 0) {
                count++; //count of words not in dic
            }
        }
        long endTime2 = currentTimeMillis();
        System.out.println("Time Required for Binary Search: " + (endTime2 - startTime2) + "ms");
        return count;
    }

    public static int hashSearch(SimpleHashSet<String> hashDictionary, ArrayList<BookWord> bookWords) {

        long startTime3 = currentTimeMillis();
        int count = 0;
        //  System.out.println(words.size());
        for (int i = 0; i < bookWords.size(); i++) {
            //    System.out.println(bookWords.get(i).getText());
            if (!hashDictionary.contains(bookWords.get(i).getText())) {
                count++;
            }
        }
        long endTime3 = currentTimeMillis();
        System.out.println("Time Required for Hash Search: " + (endTime3 - startTime3) + "ms");
        return count; //count of words not in dic
    }

    /*
    proximity search method first scans data, populates the array lists and filters the word ring
    then finds and calculates the closeness factor of a charactors to the mention of the word ring
    then sorts the list in descending order from highest closeness factor to lowest.
    */
    public static void prox() {

        try {

            Scanner scan = new Scanner(new File("TheLordOfTheRIngs.txt")); 
            String currentW; //current word
            ArrayList<PosWord> ring = new ArrayList<PosWord>(); //ring position
            ArrayList<PosWord> namesList = new ArrayList<PosWord>(); //char name position
            int actualPos = 1; //actual position in array (start at 1 instead of 0)

            Dictionary<String, Integer> dic = new Hashtable<>(); //char and freq count close to ring

            int countNames[] = new int[20]; //names of chars

            Map<PosWord, PosWord> hmap = new HashMap<>(); //positions of ring / char

            String[] names = {"frodo", "sam", "bilbo", "gandalf", "boromir", "aragorn", "legolas", "gollum", "pippin", "merry",
                "gimli", "sauron", "saruman", "faramir", "denethor", "treebeard", "elrond", "galadriel"};


            for (String x : names) {
                dic.put(x, 0); //add to dic amount of times char close to the ring
            }
            
            
            while (scan.hasNext()) {
                actualPos++; //position in array
                currentW = scan.next().toLowerCase();
                boolean outcome = currentW.contains("ring"); //check if scan contains ring
                String keyWord = "ring"; //declare keyword

                if (!outcome) { //if no ring is found 
                    for (int i = 0; (i < names.length) && !outcome; i++) {
                        outcome = currentW.contains(names[i]); //check which name
                        if (outcome) {
                            countNames[i]++; //add to count name
                            keyWord = names[i]; //get char name
                        }
                    }
                } else {
                    int firstIndex = currentW.indexOf("ring"); //filter ring (removes letters before and after unless ',','!' or other valid possiblities)
                    int beforeRIndex = firstIndex - 1; //before ring r index
                    int afterGIndex = firstIndex + 4; //after ring

                    if (beforeRIndex >= 0) {
                        char c = currentW.charAt(beforeRIndex);
                        outcome = !Character.isJavaIdentifierPart(c); //before Ring is not another letter

                    }

                    if (outcome && (afterGIndex < currentW.length())) {
                        char c = currentW.charAt(afterGIndex);
                        Character c1 = new Character(c);
                        outcome = !Character.isJavaIdentifierPart(c) && !c1.equals('-'); //after ring letter is not another letter or - (ex. ring-wraiths)

                    }
                }

                if (outcome) {
                    PosWord posword = new PosWord(actualPos, currentW, keyWord); //position of the Ring
                    if (currentW.contains("ring")) {
                        ring.add(posword); //put in ring array if it contains
                    } else {
                        namesList.add(posword); //add position object to nameList
                    }
                }

            }
            long startTimeB = currentTimeMillis(); //start timer for performance
            //(after filtering for proper word ring ie. checking begining and end of string)
            int j = 0; //ring search index

            for (int i = 0; (i < namesList.size()) && (j < ring.size()); i++) {

                PosWord charObj = namesList.get(i); //char object position

                int lowBoundary = charObj.getWordPos() - 42; // low bounds of current ring 
                int highBoundary = charObj.getWordPos() + 42; //highest bounds of current ring

                while ((j < ring.size()) && (ring.get(j).getWordPos() < lowBoundary)) {
                    j++; //increase ring search index
                }

                if ((j >= ring.size())) {
                    continue;
                }

                PosWord ringObj = ring.get(j);

                if ((ringObj.getWordPos() >= lowBoundary) && (ringObj.getWordPos() <= highBoundary)) {

                    hmap.put(ringObj, charObj);
                    dic.put(charObj.getKeyWord(), dic.get(charObj.getKeyWord()) + 1);
                }
            }
            long endTimeB = currentTimeMillis(); //end timer for performance            
            System.out.println("Performance Grade: " + (endTimeB - startTimeB) + "ms");
 
            ArrayList<charInfo> xz = new ArrayList<charInfo>();

            for (int i = 0; i < names.length; i++) {
                String curName = names[i];
                double closeness = ((double) (dic.get(curName).intValue())) / countNames[i];
                charInfo freshChar = new charInfo(curName, countNames[i], dic.get(curName), closeness);
                xz.add(freshChar);
            }
            Collections.sort(xz); //sort the list 

            for (charInfo x : xz) {
                System.out.println(x);
            }

        } catch (FileNotFoundException e) {
            System.out.println("Exception caught: " + e.getMessage());
        }
    }
}

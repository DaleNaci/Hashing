// Dale Nacianceno, 6607

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class Main_6607 {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Please provide the file name as a command line argument!");
            System.exit(1);
        }

        String file_name = args[0];
        FileParser parser = new FileParser();

        try (BufferedReader br = new BufferedReader(new FileReader(file_name))) {
            String line;

            while ((line = br.readLine()) != null) {
                parser.parseLine(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }
}


class FileParser {
    private Lexicon lex;

    public FileParser() {
        this.lex = new Lexicon();
    }

    public void parseLine(String line) {
        int command = Integer.parseInt(line.substring(0, 2));
        String info = null;
        
        if (line.length() > 2) {
            info = line.substring(3);
        }

        if (command == 10) {
            lex.insert(info);
        } else if (command == 11) {
            lex.delete(info);
        } else if (command == 12) {
            lex.search(info);
        } else if (command == 13) {
            lex.printLex();
        } else if (command == 14) {
            lex.create(Integer.parseInt(info));
        } else if (command == 15) {
            // Do nothing, 15 means it is a comment
        }
    }
}


class Lexicon {
    private int slots;
    private int[] table;
    private char[] array;
    

    public Lexicon() {
        this.slots = 0;
        this.table = new int[0];
        this.array = new char[0];
    }
    

    public void create(int slots) {
        this.slots = slots;
        this.table = new int[slots];
        this.array = new char[15 * slots];
        for (int i = 0; i < this.array.length; i++) {
            this.array[i] = ' ';
        }
    }
    

    public void insert(String word) {
        int hash_val = this.hash(word, 0);
        int table_slot = this.get_slot(hash_val);
        int array_idx = this.get_array_idx();
        
        this.table[table_slot] = array_idx;
        this.add_to_array(word, array_idx);
    }
    

    public void delete(String word) {
        int idx = new String(this.array).indexOf(word);
        
        for (int i = 0; i < word.length(); i++) {
            this.array[idx] = '*';
            idx++;
        }
        
        int slot = this.hash(word, 0);
        this.table[slot] = 0;
        
        System.out.printf("%s\tdeleted from slot %d%n", word, slot);
    }
    

    public void search(String word) {
        boolean word_found = new String(this.array).contains(word);
        
        if (word_found) {
            int slot = new String(this.array).indexOf(word);
            System.out.printf("%s\tfound at slot %d%n", word, slot);
        } else {
            System.out.printf("%s\tnot found%n", word);
        }
    }
    

    public boolean isEmpty() {
        for (int val : this.table) {
            if (val != 0) {
                return false;
            }
        }
        return true;
    }
    
    public boolean isFull() {
        for (int val : this.table) {
            if (val == 0) {
                return false;
            }
        }
        return true;
    }
    

    public void printLex() {
        System.out.println("T:\n");
        this.print_table();
        
        System.out.print("\nA: ");
        this.print_array();
        
        System.out.println();
    }
    

    private void print_table() {
        for (int i = 0; i < this.table.length; i++) {
            if (this.table[i] == 0) {
                System.out.printf("%d:%n", i);
            } else {
                System.out.printf("%d: %d%n", i, this.table[i]);
            }
        }
    }
    
    private void print_array() {
        for (char val : this.array) {
            System.out.print(val);
        }
    }
    

    private int get_array_idx() {
        int idx = 0;
        
        while (this.array[idx] != ' ') {
            idx++;
        }
        
        return idx;
    }
    

    private void add_to_array(String word, int start_idx) {
        int idx = start_idx;
        
        for (char c : word.toCharArray()) {
            this.array[idx] = c;
            idx++;
        }
        
        this.array[idx] = '\\';
    }


    private int get_slot(int start_idx) {
        int idx = start_idx;
    
        while (table[idx] != 0) {
            idx++;
    
            if (idx >= table.length) {
                double_table_size();
            }
        }
    
        return idx;
    }
    

    private void double_table_size() {
        int[] newTable = new int[slots * 2];
        char[] newArray = new char[15 * slots * 2];
    
        for (int i = 0; i < table.length; i++) {
            newTable[i] = table[i];
        }
    
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
    
        slots *= 2;
        table = newTable;
        array = newArray;
    }
    
    
    private int hash(String word, int i) {
        int ascii_total = 0;
        for (int j = 0; j < word.length(); j++) {
            ascii_total += (int) word.charAt(j);
        }
    
        return (((ascii_total - 4) % slots) + i * i) % slots;
    }
}
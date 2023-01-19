import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;

public class WorkWithFile {
    static int nFile = 0;
    final static int maxLineInFile = 2000;

    public static void createFiles(String path){
        try {
            File file = new File(path);
            file.delete();
            file.createNewFile();
        } catch (IOException e) {
            System.out.println("Couldn't create the file");
            e.printStackTrace();
        }
    }

    public static void removeTempFiles(int nFile, ArrayList<Integer> nTempList){
        int n = nTempList.get(nFile);
        for(int i=0;i<n;i++){
            File file = new File("temp" + nFile + "-" + i + ".txt");
            file.delete();
        }

    }

    public static void writeFile(String path, ArrayList<?> arrayList) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));

            for (Object element : arrayList)
                writer.write(element.toString() + "\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Couldn't write the out.txt");
            e.printStackTrace();
        }
    }

    public static void writeOutFile(String path, ArrayList<?> arrayList) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            for (Object element : arrayList)
            writer.write(element.toString() + "\n");

            writer.close();
        } catch (IOException e) {
            System.out.println("Couldn't write the out.txt");
            e.printStackTrace();
        }
    }

    public static <T> ArrayList<?> readFile(String path, Comparator<T> comp, int stringFlag) {
        if (stringFlag == 1) return readStringFile(path, (Comparator<String>) comp);
        else return readIntegerFile(path, (Comparator<Integer>) comp);
    }

    public Object readTempFile(String path, int stringFlag) {
        if (stringFlag == 1) return readStringTempFile(path);
        else return readIntegerTempFile(path);
    }

    public static ArrayList<String> readIntegerFile(String path, Comparator<Integer> comparator) {
        nFileChange();
        ArrayList<Integer> arrayList = new ArrayList<>();
        ArrayList<String> listTempFile = new ArrayList<>();
        int newElement, flag = 1, predElement = Integer.MIN_VALUE;
        int lineInFile = 0, nTemp = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null) {
                if (lineInFile != maxLineInFile) {
                    try {
                        newElement = Integer.parseInt(line);
                        if (flag == 1) {
                            arrayList.add(newElement);
                            predElement = newElement;
                            flag = 0;
                            lineInFile++;
                        }
                        else if (comparator.compare(newElement, predElement) >= 0) {
                            arrayList.add(newElement);
                            predElement = newElement;
                            lineInFile++;
                        }

                    } catch (Exception e) {
                        System.out.println("Wrong format [" + line + "]! This is not format INTEGER");
                    }
                } else {
                    writeFile("temp" + nFile + "-" + nTemp + ".txt", arrayList);
                    listTempFile.add("temp" + nFile + "-" + nTemp + ".txt");
                    nTemp++;

                    arrayList.clear();
                    newElement = Integer.parseInt(line);
                    arrayList.add(newElement);
                    predElement = newElement;

                    lineInFile = 1;
                }
            }
            writeFile("temp" + nFile + "-" + nTemp + ".txt", arrayList);
            listTempFile.add("temp" + nFile + "-" + nTemp + ".txt");
            nFile++;
            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't read a txt-file");
            e.printStackTrace();
        }
        return listTempFile;
    }

    public static ArrayList<String> readStringFile(String path, Comparator<String> comparator) {
        nFileChange();
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayList<String> listTempFile = new ArrayList<>();

        String newElement, predElement = "";
        int flag = 1;
        int lineInFile = 0, nTemp = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null) {
                if (lineInFile != maxLineInFile) {
                    newElement = line;
                    if (!newElement.contains(" ")) {
                        if (flag == 1) {
                            arrayList.add(newElement);
                            predElement = newElement;
                            flag = 0;
                            lineInFile++;
                        } else if (comparator.compare(newElement, predElement) >= 0) {
                            arrayList.add(newElement);
                            predElement = newElement;
                            lineInFile++;
                        }
                    }
                }
                else {
                    writeFile("temp" + nFile + "-" + nTemp + ".txt", arrayList);
                    listTempFile.add("temp" + nFile + "-" + nTemp + ".txt");
                    nTemp++;

                    arrayList.clear();
                    newElement = line;
                    arrayList.add(newElement);
                    predElement = newElement;

                    lineInFile = 1;
                }
            }
            writeFile("temp" + nFile + "-" + nTemp + ".txt", arrayList);
            listTempFile.add("temp" + nFile + "-" + nTemp + ".txt");
            nFile++;
            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't read a txt-file");
            e.printStackTrace();
        }
        return listTempFile;
    }

    public static ArrayList<Integer> readIntegerTempFile(String path) {
        ArrayList<Integer> arrayList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null) arrayList.add(Integer.parseInt(line));

            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't read a txt-file");
            e.printStackTrace();
        }
        return arrayList;
    }

    public static ArrayList<String> readStringTempFile(String path) {
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;

            while ((line = reader.readLine()) != null) arrayList.add(line);

            reader.close();
        } catch (IOException e) {
            System.out.println("Couldn't read a txt-file");
            e.printStackTrace();
        }
        return arrayList;
    }

    public static void nFileChange(){
        if(nFile>1) nFile=0;
    }

    public static void display(ArrayList<?> arrayList) {
        for (Object element : arrayList) System.out.print(element + " ");
        System.out.println();
    }
}

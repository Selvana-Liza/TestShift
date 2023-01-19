import java.util.ArrayList;
import java.util.Comparator;

public class ArraysSort<T> extends WorkWithFile {
    ArrayList<T> arrayA = new ArrayList<>();
    ArrayList<T> arrayB = new ArrayList<>();
    ArrayList<T> arrayC = new ArrayList<>();

    public ArraysSort(String[] args, Comparator<T> comp, int i, int stringFlag) {
        ArrayList<Integer> nTempList = new ArrayList<>();
        createFiles(args[i]);

        try {
            int k = i + 1;
            while (k < args.length) {
                ArrayList<T> out = (ArrayList<T>) readTempFile(args[i], stringFlag);

                if (!out.isEmpty()) {
                    nTempList.add(readFile(args[i], comp, stringFlag).size());
                    nTempList.add(readFile(args[k], comp, stringFlag).size());
                    createFiles(args[i]);
                } else {
                    nTempList.add(readFile(args[k], comp, stringFlag).size());
                    nTempList.add(readFile(args[++k], comp, stringFlag).size());
                }

                int nTemp1 = 0;
                int nTemp2 = 0;

                arrayA = (ArrayList<T>) readTempFile("temp0-" + nTemp1 + ".txt", stringFlag);
                nTemp1++;

                arrayB = (ArrayList<T>) readTempFile("temp1-" + nTemp2 + ".txt", stringFlag);
                nTemp2++;

                if (arrayB.isEmpty() && !arrayA.isEmpty()) {
                    nTemp1 = 0;
                    while (nTemp1 < nTempList.get(0)) {
                        writeOutFile(args[i], (ArrayList<T>) readTempFile("temp0-" + nTemp1 + ".txt", stringFlag));
                        nTemp1++;
                    }
                }

                if (arrayA.isEmpty() && !arrayB.isEmpty()) {
                    nTemp2 = 0;
                    while (nTemp2 < nTempList.get(1)) {
                        writeOutFile(args[i], (ArrayList<T>) readTempFile("temp1-" + nTemp2 + ".txt", stringFlag));
                        nTemp2++;
                    }
                }

                if (!arrayA.isEmpty() && !arrayB.isEmpty()) {
                    while (nTemp1 <= nTempList.get(0) && nTemp2 <= nTempList.get(1)) {
                        if (comp.compare(arrayA.get(arrayA.size() - 1), arrayB.get(0)) <= 0) {
                            writeOutFile(args[i], arrayA);
                            arrayA.clear();

                            if (nTemp1 < nTempList.get(0) && nTemp2 < nTempList.get(1)) {
                                ArrayList<T> a = (ArrayList<T>) readTempFile("temp0-" + nTemp1 + ".txt", stringFlag);
                                ArrayList<T> b = (ArrayList<T>) readTempFile("temp1-" + nTemp2 + ".txt", stringFlag);

                                if (comp.compare(a.get(0), b.get(0)) > 0) {
                                    arrayA.addAll(b);
                                    nTemp2++;
                                } else {
                                    arrayA.addAll(a);
                                    nTemp1++;
                                }
                            } else if (nTemp1 < nTempList.get(0)) {
                                arrayA = (ArrayList<T>) readTempFile("temp0-" + nTemp1 + ".txt", stringFlag);
                                nTemp1++;
                            } else if (nTemp2 < nTempList.get(1)) {
                                arrayA = (ArrayList<T>) readTempFile("temp1-" + nTemp2 + ".txt", stringFlag);
                                nTemp2++;
                            } else break;

                        } else {
                            ArrayList<T> arr = new ArrayList<>();
                            for (T element : arrayA) {
                                if (comp.compare(element, arrayB.get(0)) <= 0) {
                                    arr.add(element);
                                }
                            }
                            writeOutFile(args[i], arr);
                            for (T element : arr) arrayA.remove(element);

                            mergeArrays(arrayA, arrayB, arrayC, comp);

                            arrayA.clear();
                            arrayA.addAll(arrayC);
                            arrayB.clear();
                            arrayC.clear();

                            if (nTemp1 < nTempList.get(0) && nTemp2 < nTempList.get(1)) {
                                ArrayList<T> a = (ArrayList<T>) readTempFile("temp0-" + nTemp1 + ".txt", stringFlag);
                                ArrayList<T> b = (ArrayList<T>) readTempFile("temp1-" + nTemp2 + ".txt", stringFlag);

                                if (comp.compare(a.get(0), b.get(0)) > 0) {
                                    arrayB.addAll(b);
                                    nTemp2++;
                                } else {
                                    arrayB.addAll(a);
                                    nTemp1++;
                                }
                            } else if (nTemp1 < nTempList.get(0)) {
                                arrayB = (ArrayList<T>) readTempFile("temp0-" + nTemp1 + ".txt", stringFlag);
                                nTemp1++;
                            } else if (nTemp2 < nTempList.get(1)) {
                                arrayB = (ArrayList<T>) readTempFile("temp1-" + nTemp2 + ".txt", stringFlag);
                                nTemp2++;
                            } else {
                                if (nTemp1 < nTempList.get(0)) nTemp2++;
                                else nTemp1++;
                            }
                        }
                    }
                    writeOutFile(args[i], arrayA);
                    arrayA.clear();

                    if (!arrayB.isEmpty()) writeOutFile(args[i], arrayB);
                    arrayB.clear();

                    while (nTemp1 < nTempList.get(0)) {
                        writeOutFile(args[i], (ArrayList<T>) readTempFile("temp0-" + nTemp1 + ".txt", stringFlag));
                        nTemp1++;
                    }

                    while (nTemp2 < nTempList.get(1)) {
                        writeOutFile(args[i], (ArrayList<T>) readTempFile("temp1-" + nTemp2 + ".txt", stringFlag));
                        nTemp2++;
                    }
                }
                k++;
                removeTempFiles(0, nTempList);
                removeTempFiles(1, nTempList);
                nTempList.clear();
            }
        } catch (Exception e) {
            System.out.println("Error! No all arguments");
            e.printStackTrace();
        }
    }

    public void mergeArrays(ArrayList<T> arrayA,
                            ArrayList<T> arrayB,
                            ArrayList<T> arrayC,
                            Comparator<T> comparator) {
        int aIndex = 0, bIndex = 0;

        while (aIndex < arrayA.size() && bIndex < arrayB.size())
            if (comparator.compare(arrayA.get(aIndex), arrayB.get(bIndex)) < 0)
                arrayC.add(arrayA.get(aIndex++));
            else
                arrayC.add(arrayB.get(bIndex++));

        while (aIndex < arrayA.size())
            arrayC.add(arrayA.get(aIndex++));

        while (bIndex < arrayB.size())
            arrayC.add(arrayB.get(bIndex++));
    }
}

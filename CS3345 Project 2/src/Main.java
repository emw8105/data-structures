import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Random rand = new Random();
        int[] quickRandArr1 = new int[10];
        for(int i = 0; i < 10; i++) {
            quickRandArr1[i] = rand.nextInt(1000);
        }
        int[] mergeRandArr1 = new int[10];
        for(int i = 0; i < 10; i++) {
            mergeRandArr1[i] = rand.nextInt(1000);
        }
        int[] quickRandArr2 = new int[100];
        for(int i = 0; i < 100; i++) {
            quickRandArr2[i] = rand.nextInt(1000);
        }
        int[] mergeRandArr2 = new int[100];
        for(int i = 0; i < 100; i++) {
            mergeRandArr2[i] = rand.nextInt(1000);
        }
        int[] quickRandArr3 = new int[250];
        for(int i = 0; i < 250; i++) {
            quickRandArr3[i] = rand.nextInt(1000);
        }
        int[] mergeRandArr3 = new int[250];
        for(int i = 0; i < 250; i++) {
            mergeRandArr3[i] = rand.nextInt(1000);
        }
        int[] mergeAlmostArr = new int[] {
                4, 6, 7, 15, 19, 20, 21, 22, 34, 35, 37,
                38, 43, 44, 46, 56, 59, 65, 68, 81, 91,
                97, 98, 106, 115, 351, 123, 124, 133, 139,
                142, 158, 160, 161, 421, 180, 181, 185,
                204, 207, 213, 215, 490, 225, 237, 240,
                247, 251, 255, 257, 454, 273, 277, 280,
                281, 286, 298, 299, 306, 309, 314, 317,
                319, 330, 332, 334, 340, 343, 116, 362,
                367, 376, 381, 396, 397, 398, 404, 407,
                412, 175, 423, 424, 432, 437, 446, 449,
                271, 455, 458, 459, 462, 463, 465, 469,
                471, 476, 478, 480, 483, 219
        };
        int[] quickAlmostArr = new int[] {
                4, 6, 7, 15, 19, 20, 21, 22, 34, 35, 37,
                38, 43, 44, 46, 56, 59, 65, 68, 81, 91,
                97, 98, 106, 115, 351, 123, 124, 133, 139,
                142, 158, 160, 161, 421, 180, 181, 185,
                204, 207, 213, 215, 490, 225, 237, 240,
                247, 251, 255, 257, 454, 273, 277, 280,
                281, 286, 298, 299, 306, 309, 314, 317,
                319, 330, 332, 334, 340, 343, 116, 362,
                367, 376, 381, 396, 397, 398, 404, 407,
                412, 175, 423, 424, 432, 437, 446, 449,
                271, 455, 458, 459, 462, 463, 465, 469,
                471, 476, 478, 480, 483, 219
        };

        if (testMerge(mergeRandArr1, false) < testQuick(quickRandArr1, false)) {
            System.out.println("Therefore, Merge Sort can sort a small number of values more efficiently\n");
        } else {
            System.out.println("Therefore, Quick Sort can sort a small number of values more efficiently\n");
        }
        if (testMerge(mergeRandArr2, false) < testQuick(quickRandArr2, false)) {
            System.out.println("Therefore, Merge Sort can sort a medium number of values more efficiently\n");
        } else {
            System.out.println("Therefore, Quick Sort can sort a medium number of values more efficiently\n");
        }
        if (testMerge(mergeRandArr3, false) < testQuick(quickRandArr3, false)) {
            System.out.println("Therefore, Merge Sort can sort a large number of values more efficiently\n");
        } else {
            System.out.println("Therefore, Quick Sort can sort a large number of values more efficiently\n");
        }
        if (testMerge(mergeAlmostArr, true) < testQuick(quickAlmostArr, true)) {
            System.out.println("Therefore, Merge Sort can sort an array of nearly sorted values more efficiently\n");
        } else {
            System.out.println("Therefore, Quick Sort can sort an array of nearly sorted values more efficiently\n");
        }
        System.out.println("As can be seen by the results of the speed comparisons, Quick Sort is generally faster for all array cases");
    }

    static int partition(int arr[], int ind1, int ind2)
    {
        // calculates the pivot, organizes elements based on it, then returns the pivot
        int mid = ind1 + (ind2 - ind1) / 2; // calculate midpoint
        int pivotInd;
        // median of 3 partitioning calculation
        // check if first <= mid <= last or last <= mid <= first, then mid is median
        if ((arr[ind1] <= arr[mid] && arr[mid] <= arr[ind2]) || (arr[ind2] <= arr[mid] && arr[mid] <= arr[ind1])) {
            pivotInd = mid;
        }
        // check if mid <= first <= last or last <= first <= mid, then first is median
        else if ((arr[mid] <= arr[ind1] && arr[ind1] <= arr[ind2]) || (arr[ind2] <= arr[ind1] && arr[ind1] <= arr[mid])) {
            pivotInd = ind1;
        }
        else { // else if first <= last <= mid or mid <= last <= first, then last is median
            pivotInd = ind2;
        }
        int pivotVal = arr[pivotInd]; // get the value from the determined median index
        int smallerValInd = ind1 - 1;
        // swap elements based on the value of the pivot
        for (int i = ind1; i < ind2; i++) {
            if (arr[i] <= pivotVal) {
                smallerValInd++;
                int temp = arr[smallerValInd];
                arr[smallerValInd] = arr[i];
                arr[i] = temp;
            }
        }
        // swap the pivot back into it's sorted position in the array
        int temp = arr[smallerValInd+1];
        arr[smallerValInd+1] = arr[pivotInd];
        arr[pivotInd] = temp;
        smallerValInd++;
        // i stops at values < the pivot, increment i by 1 to return the pivot
        return smallerValInd;
    }

    static void quickSort(int arr[], int ind1, int ind2) {
        if (ind2 - ind1 > 15) {  // determine if partition is within the cutoff for insertion or not
            // if more than 15 elements, then find the pivot and recursively call quickSort on the partitions
            int pivotInd = partition(arr, ind1, ind2);
            quickSort(arr, ind1, pivotInd - 1);
            quickSort(arr, pivotInd + 1, ind2);
        } else {  // use insertion sort on the partition if <= 15
            // do sort from the partition indexes (i = 1 --> i = ind1 +1, loop to ind2)
            for (int i = ind1 + 1; i <= ind2; i++) {
                int currentVal = arr[i];
                int j = i - 1;
                while (j >= ind1 && arr[j] > currentVal) {
                    arr[j+1] = arr[j];
                    j--;
                }
                arr[j+1] = currentVal;
            }
        }
    }

    static void mergeSort(int[] arr, int ind1, int ind2) {
        if (ind1 < ind2) {
            int mid = (ind1 + ind2) / 2; // calc the middle value index to find partition
            mergeSort(arr, ind1, mid); // recursively sort first half
            mergeSort(arr, mid + 1, ind2); // recursively sort second half
            // once recursion makes it down to base case one 1 value, start merging into sorted arrays
            merge(arr, ind1, ind2, mid);
        }

    }

    static void merge(int[] arr, int ind1, int ind2, int mid) {
        int leftLen = mid - ind1 + 1; // calculate the size of the left subarray
        int rightLen = ind2 - mid; // calculate the size of the right subarray
        int[] leftHalf = new int[leftLen]; // allocate corresponding subarrays
        int[] rightHalf = new int[rightLen];

        for(int i = 0; i < leftLen; ++i) {
            leftHalf[i] = arr[ind1+i];
        }
        for(int j = 0; j < rightLen; ++j) {
            rightHalf[j] = arr[mid+1+j];
        }

        // traverse value by value to merge the arrays together in sorted order
        int leftPtr = 0, rightPtr = 0, arrPtr = ind1;
        while (leftPtr < leftLen && rightPtr < rightLen) {
            // check if value in leftHalf array should be added next (i.e. next smallest number)
            if (leftHalf[leftPtr] <= rightHalf[rightPtr]) {
                arr[arrPtr] = leftHalf[leftPtr];
                leftPtr++;
            } else { // else, the next value in rightHalf array should be added next
                arr[arrPtr] = rightHalf[rightPtr];
                rightPtr++;
            }
            arrPtr++; // move to next element in the merged array and reloop
        }
        // flush the leftover values from uneven array merges, just pickup where indexes left off
        while (leftPtr < leftLen) {
            arr[arrPtr] = leftHalf[leftPtr];
            leftPtr++;
            arrPtr++;
        }
        while (rightPtr < rightLen) {
            arr[arrPtr] = rightHalf[rightPtr];
            rightPtr++;
            arrPtr++;
        }
    }

    static void printArray(int[] arr, int len) {
        for (int i = 0; i < len; i++) {
            System.out.print(arr[i] + ", ");
        }
        System.out.println();
    }
    static long testMerge(int[] arr, boolean nearSorted) {
        int len = arr.length;
        long preMerge = System.nanoTime();
        mergeSort(arr, 0, len-1);
        long postMerge = System.nanoTime() - preMerge;
        printArray(arr, len-1);
        if(nearSorted) {
            System.out.println("Merge Sort " + len + " nearly sorted elements time elapsed: " + postMerge);
        } else {
            System.out.println("Merge Sort " + len + " elements time elapsed: " + postMerge);
        }
        return postMerge;
    }
    static long testQuick(int[] arr, boolean nearSorted) {
        int len = arr.length;
        long preQuick = System.nanoTime();
        quickSort(arr, 0, len-1);
        long postQuick = System.nanoTime() - preQuick;
        printArray(arr, len-1);
        if(nearSorted) {
            System.out.println("Quick Sort " + len + " nearly sorted elements time elapsed: " + postQuick);
        } else {
            System.out.println("Quick Sort " + len + " elements time elapsed: " + postQuick);
        }
        return postQuick;
    }
}
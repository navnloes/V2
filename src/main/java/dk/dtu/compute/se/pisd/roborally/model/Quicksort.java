package dk.dtu.compute.se.pisd.roborally.model;

public class Quicksort {

    /**
     * @author https://www.programcreek.com/2012/11/quicksort-array-in-java/
     *
     * Altered by S205354
     *
     * @param arrStart er start placeringen på arrayet.
     *
     * @param arrEnd er den sidste plads i array (x.length - 1)
     */

    public void doQuickSort(int[] arr, int arrStart, int arrEnd) {

        // pick the pivot
        int middle = arrStart + (arrEnd - arrStart) / 2;
        int pivot = arr[middle];

        // make left < pivot and right > pivot
        int i = arrStart, j = arrEnd;
        while (i <= j) {

            //Hvis arr[i] er mindre end omdrejningspunktet (pivot), hæver den i indtil at dette ikke er sandt.
            while (arr[i] < pivot) {
                i++;
            }

            //Hvis arr[j] er større end omdrejningspunktet, så nedsætter den j indtil at dette ikke er sandt.
            while (arr[j] > pivot) {
                j--;
            }

            //Her bytter den rundt på der hvor i er større end pivot, og der hvor j er mindre end pivot.
            if (i <= j) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }

        // recursively sort two sub parts

        //Hvis j er større end arrStart
        if (arrStart < j)
            doQuickSort(arr, arrStart, j);

        //Hvis arrEnd er større end i
        if (arrEnd > i)
            doQuickSort(arr, i, arrEnd);
    }

}

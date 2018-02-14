package s4.b173325; // Please modify to s4.Bnnnnnn, where nnnnnn is your student ID.
import java.lang.*;
import s4.specification.*;

/*
interface FrequencerInterface {     // This interface provides the design for frequency counter.
    void setTarget(byte[]  target); // set the data to search.
    void setSpace(byte[]  space);  // set the data to be searched target from.
    int frequency(); //It return -1, when TARGET is not set or TARGET's length is zero
                    //Otherwise, it return 0, when SPACE is not set or Space's length is zero
                    //Otherwise, get the frequency of TAGET in SPACE
    int subByteFrequency(int start, int end);
    // get the frequency of subByte of taget, i.e target[start], taget[start+1], ... , target[end-1].
    // For the incorrect value of START or END, the behavior is undefined.
*/


public class Frequencer implements FrequencerInterface
{
    // Code to Test, *warning: This code  contains intentional problem*
    byte [] myTarget;
    byte [] mySpace;
    boolean targetReady = false;
    boolean spaceReady = false;
    
    int [] suffixArray;
    // The variable, "suffixArray" is the sorted array of all suffixes of mySpace.
    // Each suffix is expressed by a interger, which is the starting position in mySpace. // The following is the code to print the variable
    private void printSuffixArray()
    {
        if(spaceReady)
        {
            for(int i = 0; i < mySpace.length; i++)
            {
                int s = suffixArray[i];
                for(int j = s; j < mySpace.length; j++)
                {
                    System.out.write(mySpace[j]);
                }
                System.out.write('\n');
            }
        }
    }
    
    private int suffixCompare(int i, int j)
    {
        // comparing two suffixes by dictionary order.
        // It should be called from setSpace or some method to create suffix. 
        // It should not be used for searching indexes.
        // i and j denoetes suffix_i, and suffix_j
        // if suffix_i > suffix_j, it returns 1
        // if suffix_i < suffix_j, it returns -1 
        // if suffix_i = suffix_j, it returns 0; 
        // Example of dictionary order
        int si = suffixArray[i];
        int sj = suffixArray[j];
        int s = 0;
        
        if(si > s) s = si;
        if(sj > s) s = sj;
        
        int n = mySpace.length - s;
        
        for(int k = 0; k < n; k++)
        {
            if(mySpace[si+k] > mySpace[sj+k]) return 1;
            if(mySpace[si+k] < mySpace[sj+k]) return -1;
        }
        
        if(si < sj) return 1;
        if(si > sj) return -1;
        return 0;
        
    }
            
    public void setSpace(byte []space)
    {
        mySpace = space;
        if(mySpace.length > 0) 
        {
            spaceReady = true;
            suffixArray = new int[space.length];
            
            for(int i = 0; i < space.length; i++)
            {
                suffixArray[i] = i;
            }
    		
            //printSuffixArray();

    		quickSort(0, mySpace.length - 1);
            //mergeSort(suffixArray);
    		
            /*
            for(int i = 0; i < space.length; i++)
            {
                for(int j = 0; j < space.length; j++)
                {
                    if(suffixCompare(i, j) == -1)
                    {
                        int a = suffixArray[i];
                        suffixArray[i] = suffixArray[j];
                        suffixArray[j] = a;
                    }
                }
            }
            */

            //printSuffixArray();
            //System.out.println();
        }
    }

    public void merge(int[] a1, int[] a2, int[] a)
    {
        int i = 0, j = 0;

        while(i < a1.length || j < a2.length)
        { 
            if(j >= a2.length)
            {
                a[i+j] = a1[i];
                i++;
            }
            else if(i >= a1.length)
            {
                a[i+j] = a2[j];
                j++;
            }
            else
            {
                if(suffixCompare(a1[i], a2[j]) == -1)
                {
                    a[i+j] = a1[i];
                    i++;
                }
                else
                {
                    a[i+j] = a2[j];
                    j++;
                }
            }
        }
    }


    public void mergeSort(int[] a)
    {
        if(a.length > 1)
        {
            int m = a.length / 2;
            int n = a.length - m;
            int[] a1 = new int[m];
            int[] a2 = new int[n];
            for(int i = 0; i < m; i++) a1[i] = a[i];
            for(int i = 0; i < n; i++) a2[i] = a[m + i];
            mergeSort(a1);
            mergeSort(a2);
            merge(a1, a2, a);
        }
    }

    public void quickSort(int left, int right)
    {
        int l = left;
        int r = right;
        int pivot = left;//(left + right) / 2;

        do
        {
            while(suffixCompare(l, pivot) == -1)
            {
                l++;
            }
            while(suffixCompare(pivot, r) == -1)
            {
                r--;
            }
            if(l < r)
            {
                //System.out.println(l + " and " + 
                //                  r + " change");

                int tmp = suffixArray[l];
                suffixArray[l] = suffixArray[r];
                suffixArray[r] = tmp;
            }
            if(l <= r)
            {
                l++;
                r--;
            }
        }while(l <= r);

        if(left < r)
        {
            quickSort(left, r);
        }

        if(l < right)
        {
            quickSort(l, right);
        }
    }
    
    private int targetCompare(int i, int start, int end)
    {
        // comparing suffix_i and target_j_end by dictonary order with limitation of length;
        // if the beginning of suffix_i matches target_i_end, and suffix is longer than target it returns 0;
        // if suffix_i > target_i_end it return 1;
        // if suffix_i < target_i_end it return -1
        // It is not implemented yet.
        // It should be used to search the apropriate index of some suffix.
        
        int si = suffixArray[i];
        byte[] target_start_end = subBytes(myTarget, start, end);
        int target_length = target_start_end.length;

        int result;
        int n;

        if((mySpace.length - suffixArray[i]) >= target_length)
        {
            n = target_length;
            result = 0;
        }
        else
        {
            n = mySpace.length - suffixArray[i];
            result = -1;
        }
        
        for(int k = 0; k < n; k++)
        {
            if(mySpace[si+k] > target_start_end[k]) return 1;
            if(mySpace[si+k] < target_start_end[k]) return -1;
        }

        return result;
    }
    
    
    byte [] subBytes(byte [] x, int start, int end)
    {
        // corresponding to substring of String for  byte[] ,
        // It is not implement in class library because internal structure of byte[] requires copy.
        byte [] result = new byte[end - start];
        for(int i = 0; i < end - start; i++) 
		{ 
			result[i] = x[start + i]; 
		}
		
        return result;
    }
    
    private int subByteStartIndex(int start, int end)
    {
        // It returns the index of the first suffix which is equal or greater than subBytes;
        // not implemented yet;
        // For "Ho", it will return 5 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 6 for "Hi Ho Hi Ho".
        int i;
        
        for(i = 0; i < suffixArray.length; i++)
        {
            if(targetCompare(i, start, end) == 0) return i;
        }
        
        return -1;
    }
    
    private int subByteEndIndex(int start, int end)
    {
        // It returns the next index of the first suffix which is greater than subBytes;
        // not implemented yet
        // For "Ho", it will return 7 for "Hi Ho Hi Ho".
        // For "Ho ", it will return 7 for "Hi Ho Hi Ho".
        int i;
        
        for(i = 0; i < suffixArray.length; i++)
        {
            if(targetCompare(i, start, end) == 1) return i;
        }
        
        return -1;
    }

    public int binarySearch(int start , int end)
    {
        int left = 0;
        int right = suffixArray.length - 1;

        do
        {
            int center = (left + right) / 2;

            if(targetCompare(center, start, end) == 0)
            {
                return center;
            }
            else if(targetCompare(center, start, end) == -1)
            {
                left = center + 1;
            }
            else
            {
                right = center - 1;
            }
        }while(left <= right);

        return -1;
    }
    
    public int subByteFrequency(int start, int end)
    {
        /* This method could be defined as follows though it is slow.
         int spaceLength = mySpace.length;
         int count = 0;
         for(int offset = 0; offset< spaceLength - (end - start); offset++) {
         boolean abort = false;
         for(int i = 0; i< (end - start); i++) {
         if(myTarget[start+i] != mySpace[offset+i]) { abort = true; break; } }
         if(abort == false) { count++; } }
        */
        
        int first = 0;
        int last = 0;

        int center = binarySearch(start, end);

        if(center != -1)
        {
            for(int i = center; i >= 0; i--)
            {
                if(targetCompare(i, start, end) == -1)
                {
                    first = i + 1;
                    break;
                }
            }
            for(int i = center; i < suffixArray.length; i++)
            {
                if(targetCompare(i, start, end) == 1)
                {
                    last = i;
                    break;
                }
            }
        }
        
        //int first = subByteStartIndex(start, end);
        //int last = subByteEndIndex(start, end);

        //System.out.println(first + " " + last);

        //inspection code
        //for(int k=start;k<end;k++) { System.out.write(myTarget[k]); }
        //System.out.printf(": first=%d last1=%d\n", first, last1);
        
        return last - first;
    }
    
    public void setTarget(byte [] target)
    {
        myTarget = target;
        if(myTarget.length>0) targetReady = true;
    }
    
    
    public int frequency()
    {        
        if(targetReady == false) return -1;
        if(spaceReady == false) return 0;
        return subByteFrequency(0, myTarget.length);
    }

    public static void main(String[] args)
    {
        Frequencer frequencerObject;
        try {
            frequencerObject = new Frequencer();
            frequencerObject.setSpace("Hi Ho Hi Ho".getBytes());
            frequencerObject.setTarget("Ho".getBytes());
            int result = frequencerObject.frequency();
            System.out.print("Freq = "+ result+" ");
            
            if(4 == result) { System.out.println("OK"); }
            else {System.out.println("WRONG"); }
        }
        catch(Exception e) {
            System.out.println("STOP");
        }
    }
}	    
	    

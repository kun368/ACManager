package jskills;

import java.util.*;

/**
 * Helper class to sort ranks in non-decreasing order.
 */
public class RankSorter {

    /**
     * Returns a list of all the elements in items, sorted in non-descending
     * order, according to itemRanks. Uses a stable sort, and also sorts
     * itemRanks (in place)
     * 
     * @param items
     *            The items to sort according to the order specified by ranks.
     * @param itemRanks
     *            The ranks for each item where 1 is first place.
     * @return the items sorted according to their ranks
     */
    public static <T> List<T> sort(Collection<T> items, int[] itemRanks) {
        Guard.argumentNotNull(items, "items");
        Guard.argumentNotNull(itemRanks, "itemRanks");

        int lastObserverdRank = 0;
        boolean needToSort = false;

        for (int currentRank : itemRanks) {
            // We're expecting ranks to go up (e.g. 1, 2, 2, 3, ...)
            // If it goes down, then we've got to sort it.
            if (currentRank < lastObserverdRank) {
                needToSort = true;
                break;
            }

            lastObserverdRank = currentRank;
        }

        // Don't bother doing more work, it's already in a good order
        if (!needToSort) return new ArrayList<T>(items);

        // Get the existing items as an indexable list.
        List<T> itemsInList = new ArrayList<T>(items);

        // item -> rank
        final Map<T, Integer> itemToRank = new HashMap<T, Integer>();
        for (int i = 0; i < itemsInList.size(); i++)
            itemToRank.put(itemsInList.get(i), itemRanks[i]);
        
        Collections.sort(itemsInList, new Comparator<T>() {
            public int compare(T o1, T o2) {
                return itemToRank.get(o1).compareTo(itemToRank.get(o2));
            }
        });
        
        Arrays.sort(itemRanks);
        return itemsInList;
    }
}
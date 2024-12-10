//Written by Christina Tu & Austin Lee

public class ArrayList<T extends Comparable<T>> implements List<T> {
  T[] list;
  int size;
  boolean isSorted;

  // constructor
  public ArrayList() {
    list = (T[]) new Comparable[2];
    isSorted = true;
  }

  // helper methods
  private void resize() {
    T[] copyCat = (T[]) new Comparable[size * 2 + 1];
    for(int i = 0; i < size; i++) {
      copyCat[i] = list[i];
    }
    list = copyCat;
  }
  private boolean checkSorted() {
    if(size > 1) {
      for(int i = 0; i < size - 1; i++) {
        if(list[i].compareTo(list[i+1]) > 0) {
          return false;
        }
      }
    }
    return true;
  }

  // methods
  public boolean add(T element) {
    if(element != null) {
      if(size == list.length) {
        resize();
      }
      list[size++] = element;
      if(size > 1 && element.compareTo(list[size - 2]) < 0) {
        isSorted = false;
      }
      return true;
    }
    return false;
  }
  public boolean add(int index, T element) {
    if(element != null && index >= 0 && index < size) {
      if(size == list.length) {
        resize();
      }
      int i;
      for(i = size - 1; i >= index; i--) {
        list[i+1] = list[i];
      }
      list[i+1] = element;
      // there will always be an element after the inserted element, so we don't need to check if there is
      if(element.compareTo(list[index + 1]) > 0) {
        isSorted = false;
      }
      // if it was put at the beginning, there is nothing else to check it with
      else if(index > 0 && element.compareTo(list[index - 1]) < 0) {
        isSorted = false;
      }
      size++;
      return true;
    }
    return false;
  }
  public void clear() {
    T[] temp = (T[]) new Comparable[list.length];
    list = temp;
    size = 0;
    isSorted = true;
  }
  public T get(int index) {
    if(index < size && index >= 0) {
      return list[index];
    }
    return null;
  }
  public int indexOf(T element) {
    if(element == null) return -1;
    // binary search if sorted, linear search if not
    if(isSorted) {
      int low = 0;
      int high = size - 1;
      while(low <= high) {
        int mid = (low + high) / 2;
        if(list[mid].compareTo(element) == 0) {
          // if the list is sorted, all the elements that are the same are right next to each other
          // we want to find the first instance of the element, so iterate backwards until we reach
          // something that is not the desired element OR the beginning of the list
          while(mid - 1 >= 0 && list[mid - 1].compareTo(element) == 0) {
            mid--;
          }
          return mid;
        } else if(list[mid].compareTo(element) < 0) {
          low = mid + 1;
        } else {
          high = mid - 1;
        }
      }
    } else {
      for(int i = 0; i < size; i++) {
        if(list[i].compareTo(element) == 0) {
          return i;
        }
      }
    }
    return -1;
  }
  public boolean isEmpty(){
    if (size == 0) return true;
    return false;
  }
  public int size() {
    return size;
  }
  public void sort() {
    if(isSorted) {
      return;
    }
    int i, j;
    T temp;
    // insertion sort
    for(i = 1; i < size; i++) {
      temp = list[i];
      for(j = i-1; j >= 0 && list[j].compareTo(temp) > 0; j--) {
        list[j+1] = list[j];
      }
      list[j+1] = temp;
    }
    isSorted = true;
  }
  public T remove(int index) {
    if(index >= 0 && index < size) {
      T temp = list[index];
      int count = index;
      // removes, then shifts everything back and decreases size by 1
      while (count < size - 1) {
        list[count] = list[count + 1];
        list[count + 1] = null;
        count++;
      }
      size--;
      isSorted = checkSorted();
      return temp;
    }
    return null;
  }
  public void equalTo(T element) {
    if(!isSorted) {
      if (element != null) {
        // counts number of elements
        int numElem = 0;
        for (int i = 0; i < size; i++) {
          if (element.equals(list[i])) {
            numElem++;
          }
        }
        // replaces the appropriate amount of items and sets the rest of the elements to null
        int count = 0;
        while (count < numElem) {
          list[count] = element;
          count++;
        }
        while (count < size) {
          list[count] = null;
          count++;
        }
        size = numElem;
      }
    } else {
      if (element != null) {
        int numElem = 0;
        int i = 0;
        // increases efficiency by noticing that elements will be in a block
        while (element.compareTo(list[i]) <= 0) {
          if (element.equals(list[i])) {
            numElem++;
          }
          i++;
        }

        int count = 0;
        while (count < numElem) {
          list[count] = element;
          count++;
        }
        while (count < size) {
          list[count] = null;
          count++;
        }
        size = numElem;
      }
    }
    isSorted = true;
  }
  public void reverse() {
    T temp;
    // switch the first and last element up to the middle of the array
    for(int i = 0; i < size / 2; i++) {
      temp = list[i];
      list[i] = list[size - 1 - i];
      list[size - 1 - i] = temp;
    }
    isSorted = checkSorted();
  }

  public T getMin() {
    if (list == null) {
      return null;
    }

    T min = list[0];

    for (int i = 0; i < size; i ++) {
      if (min.compareTo(list[i]) > 0) {
        min = list[i];
      }
    }
    return  min;
  }


  public T getMax() {
    if (list == null) {
      return null;
    }

    T max = list[0];

    for (int i = 0; i < size; i ++) {
      if (max.compareTo(list[i]) < 0) {
        max = list[i];
      }
    }
    return  max;
  }


  public void intersect(List<T> otherList) {
    try {
      ArrayList<T> other = (ArrayList<T>) otherList;
      if (!isSorted) {
        sort();
      }

      if (!other.isSorted) {
        other.sort();
      }

      T[] temp = (T[]) new Comparable[Math.min(size, other.size())];
      //cycling through this list
      int i = 0;
      //cycling through other list
      int j = 0;
      //size
      int count = 0;
      // adds from both in order until the end of one list
      while(i < size && j < other.size()) {
        T elementFromThis = list[i];
        T elementFromOther = other.get(j);

        if (elementFromThis.compareTo(elementFromOther) == 0) {
          temp[count++] = elementFromThis;
          i++;
          j++;
        } else if (elementFromThis.compareTo(elementFromOther) < 0) {
          i++;
        } else {
          j++;
        }
      }
      list = temp;
      size = count;
    } catch(ClassCastException e) {
      throw new ClassCastException("Incompatible List Type");
    }
  }

  public boolean isSorted() {
    return isSorted;
  }
}
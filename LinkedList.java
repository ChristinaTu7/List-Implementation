//Written by Christina Tu & Austin Lee
public class LinkedList<T extends Comparable<T>> implements List<T> {
  private Node<T> head;
  private boolean isSorted;
  private int size;

  // constructor
  public LinkedList() {
    head = new Node<>(null, null);
    size = 0;
    isSorted = true;
  }

  //helper methods

  public boolean contains(T element) {
    Node<T> current = head.getNext();

    while (current != null) {
      if (current.getData().equals(element)) {
        return true;
      }
      current = current.getNext();
    }

    return false;
  }


  // methods
  public boolean add(T element) {
    if(element != null) {
      Node<T> newNode = new Node<>(element, null);
      if(head.getNext() == null) {
        head.setNext(newNode);
      } else {
        Node<T> ptr = head.getNext();
        Node<T> trailer = head;
        while(ptr != null) {
          trailer = ptr;
          ptr = ptr.getNext();
        }
        trailer.setNext(newNode);
        if(newNode.getData().compareTo(trailer.getData()) < 0) {
          isSorted = false;
        }
      }
      size += 1;
      return true;
    }
    return false;
  }
  public boolean add(int index, T element){
    if(element != null && index >= 0 && index < size){
      Node<T> ptr = head.getNext();
      Node<T> trailer = head;
      int counter = 0;
      while(ptr != null && counter < index){
        trailer = ptr;
        ptr = ptr.getNext();
        counter++;
      }
      Node<T> newNode = new Node<>(element, ptr);
      trailer.setNext(newNode);
      size++;
      // update isSorted
      if(trailer.getData() != null) { // only check if the trailer and element are in order if trailer is not head
        if (element.compareTo(trailer.getData()) < 0) {
          isSorted = false;
          return true;
        }
      }
      if(element.compareTo(ptr.getData()) > 0) {
        isSorted = false;
        return true;
      }
      return true;
    }
    return false;
  }
  public void clear() {
    size = 0;
    head.setNext(null);
    isSorted = true;
  }
  public T get(int index) {
    if(index >= 0 && index < size){
      Node<T> ptr = head.getNext();
      Node<T> trailer = head;
      int counter = -1;
      while(ptr != null && counter < index){
        trailer = ptr;
        ptr = ptr.getNext();
        counter++;
      }
      return trailer.getData();
    }
    return null;
  }
  public int indexOf(T element) {
    if(element != null) {
      Node<T> ptr = head.getNext(); // starting at index 0
      int index = 0;
      // isSorted increases the efficiency if sorted by not checking anything bigger than element, but
      // cannot use binary search on a linked list
      if(isSorted) {
        while(ptr != null && ptr.getData().compareTo(element) <= 0) {
          if(ptr.getData().equals(element)) {
            return index;
          }
          index++;
          ptr = ptr.getNext();
        }
      } else {
        while(ptr != null) {
          if(ptr.getData().equals(element)) {
            return index;
          }
          index++;
          ptr = ptr.getNext();
        }
      }
    }
    return -1;
  }
  public boolean isEmpty() {
    if(head.getNext() == null){
      return true;
    }
    return false;
  }
  public int size() {
    return size;
  }
  public void sort() {
    if(isSorted || size <= 1) {
      return;
    } else {
      Node<T> ptr = head.getNext().getNext();
      Node<T> trailer = head.getNext();
      while(ptr != null) {
        if(ptr.getData().compareTo(trailer.getData()) >= 0) {
          // if ptr is in the right spot relative to the last sorted item (trailer) do nothing
          trailer = ptr;
          ptr = ptr.getNext();
        } else {
          // otherwise, iterate from the front of the list to find the right spot, and arrange nodes accordingly
          Node<T> p = head.getNext();
          Node<T> t = head;
          while(ptr.getData().compareTo(p.getData()) > 0) {
            t = p;
            p = p.getNext();
          }
          trailer.setNext(ptr.getNext());
          t.setNext(ptr);
          ptr.setNext(p);
          ptr = trailer.getNext();
        }
      }
      isSorted = true;
    }
  }
  public T remove(int index) {
    if(index < this.size && index >= 0){
      Node<T> ptr = head.getNext();
      Node<T> trailer = head;

      int count = 0;

      while(count < index){
        ptr = ptr.getNext();
        trailer = trailer.getNext();
        count++;
      }

      trailer.setNext(ptr.getNext());
      size--;

      isSorted = true;
      Node ptrOne = head.getNext();
      while(ptrOne != null && ptrOne.getNext() != null) {
        if(ptrOne.getData().compareTo(ptrOne.getNext().getData()) > 0) {
          isSorted = false;
        }
        ptrOne = ptrOne.getNext();
      }
      return ptr.getData();
    }
    return null;
  }
  public void equalTo(T element) {
    if(!isSorted){
      boolean doneAlready;
      Node<T> ptr = head.getNext();
      Node<T> trailer = head;

      int count = 0;
      int sized = size;

      while(count < sized && ptr != null){
        doneAlready = false;
        if(!ptr.getData().equals(element)){
          trailer.setNext(ptr.getNext());
          ptr = ptr.getNext();
          doneAlready = true;
          size--;
        }
        if (ptr != null && !doneAlready) {
          ptr = ptr.getNext();
          trailer = trailer.getNext();
        }
        count++;
      }
    } else {
      boolean doneAlready;
      Node<T> ptr = head.getNext();
      Node<T> trailer = head;

      int count = 0;
      int sized = size;

      while(count < sized && ptr != null && ptr.getData().compareTo(element) <= 0){
        doneAlready = false;
        if(!ptr.getData().equals(element)){
          trailer.setNext(ptr.getNext());
          ptr = ptr.getNext();
          doneAlready = true;
          size--;
        }
        if (ptr != null && !doneAlready) {
          ptr = ptr.getNext();
          trailer = trailer.getNext();
        }
        count++;
      }
      trailer.setNext(null);
    }
    isSorted = true;
  }
  public void reverse() {
    int looper = 0;
    Node<T> ptr = head.getNext();
    Node<T> trailer = head;
    Node<T> trailerTwo = head;

    while(looper < size) {
      while (ptr.getNext() != null) {
        ptr = ptr.getNext();
        trailerTwo = trailerTwo.getNext();
      }

      trailerTwo.setNext(null);
      ptr.setNext(trailer.getNext());
      trailer.setNext(ptr);

      trailer = trailer.getNext();
      ptr = ptr.getNext();
      trailerTwo = trailer;

      looper++;
    }
    isSorted = true;
    Node ptrOne = head.getNext();
    while(ptrOne != null && ptrOne.getNext() != null) {
      if(ptrOne.getData().compareTo(ptrOne.getNext().getData()) > 0) {
        isSorted = false;
      }
      ptrOne = ptrOne.getNext();
    }
  }


  public T getMin() {
    if (head.getNext() == null) {
      return null;
    }
    T min = head.getNext().getData();
    Node<T> ptr = head.getNext();

    while (ptr != null) {
      T currentData = ptr.getData();
      if (currentData.compareTo(min) < 0) {
        min = currentData;
      }
      ptr = ptr.getNext();
    }

    return min;
  }


  public T getMax() {
    if (head.getNext() == null) {
      return null;
    }

    T max = head.getNext().getData();
    Node<T> ptr = head.getNext();

    while (ptr != null) {
      T currentData = ptr.getData();
      if (currentData.compareTo(max) > 0) {
        max = currentData;
      }
      ptr = ptr.getNext();
    }

    return max;
  }

  public void intersect(List<T> otherList) {
    if (otherList == null) {
      return;
    }

    // Check if the lists are sorted, and if not, sort them
    if (!isSorted()) {
      sort();
    }

    LinkedList<T> other = (LinkedList<T>) otherList;

    if (!other.isSorted()) {
      other.sort();
    }
    //intersection
    Node<T> trailer = new Node<>(null);
    Node<T> current = trailer;

    Node<T> ptr = head.getNext();
    Node<T> otherPtr = other.head.getNext();

    while (ptr != null && otherPtr != null) {
      if (ptr.getData().compareTo(otherPtr.getData()) > 0) {
        current.setNext(ptr);
        ptr = ptr.getNext();
      } else {
        current.setNext(otherPtr);
        otherPtr = otherPtr.getNext();
      }
      current = current.getNext();
    }

    // Append remaining elements, if any
    if (ptr != null) {
      current.setNext(ptr);
    } else if (otherPtr != null) {
      current.setNext(otherPtr);
    }

    // Set the head to the first node after the dummy node
    head = trailer;
  }


  public String toString() {
    String s = "";
    Node<T> ptr = head.getNext();
    while(ptr != null) {
      s += ptr.getData() + "\n";
      ptr = ptr.getNext();
    }
    return s;
  }
  public boolean isSorted() {
    return isSorted;
  }
}
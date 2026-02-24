class NodeDoubly {
    int data;
    NodeDoubly next;
    NodeDoubly prev;

    NodeDoubly(int value) {
        data = value;
        prev = next = null; 
    }
}

public class Doubly_LL {
    public static void main(String[] args) {
        NodeDoubly head = null;

        NodeDoubly new_node = new NodeDoubly(5);
        head = new_node;

        new_node = new NodeDoubly(7);
        new_node.next = head;
        head.prev = new_node;
        head = new_node;

        new_node = new NodeDoubly(9);
        new_node.next = head;
        head.prev = new_node;
        head = new_node;

        new_node = new NodeDoubly(11);
        new_node.next = head;
        head.prev = new_node;
        head = new_node;

        //FORWARD TRAVERSAL
        NodeDoubly temp = head;
        while (temp.next != null) {
            System.out.print(temp.data + " ");
            temp = temp.next;
        }
        System.out.print(temp.data + " ");
        temp = temp.prev;

        //BACKWARD TRAVERSAL
        while (temp != null) {
            System.out.print(temp.data + " ");
            temp = temp.prev;
        }
    }
}
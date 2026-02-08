public class App {
    public static void main(String[] args) {
        int[] arr = {10,19,23,21,0};
        int n = 4;
        int element = 66;

        System.out.println("Array sebelum penambahan elemen:");
        for (int i = 0; i < n; i++){
            System.out.print(arr[i] + " ");
        }
        for (int i = n-1; i >=0; i--){
            arr[i+1] = arr[i];
        }

        arr[0] = element;

        System.out.println("\nArray setelah penambahan elemen:");
        for (int i = 0; i <= n; i++){
            System.out.print(arr[i] + " ");
        }
    }
}

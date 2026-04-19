
import java.util.Scanner;

class Node {
    String data;
    Node next, prev;

    public Node(String data) {
        this.data = data;
    }

}

class CircularDoublyLinkedList{
    Node head = null;
    int size = 0;
// Tambah Berita
    void tambahBerita(String teks){
        Node newNode = new Node(teks);
        if (head == null) {
            head = newNode;
            head.next = head;
            head.prev = head;
        } else {
            Node tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }
        size++;
        System.out.println("Data Berita: "+ newNode.data + " Berhasil Ditambahkan!");
    }

    public volatile boolean isRunning = false;

    public void stopRunning(){
        isRunning = false;
        System.out.println("---Tampilan Berita Berhenti---");
    }
// Forward Tampilan Berita
    void forwardBerita() throws InterruptedException{
        if (head == null) {
            return;
        }
        isRunning = true;
        Node currNode  = head;
        while(isRunning){
            System.out.println("<<< " + currNode.data + " >>>");

            try{
                Thread.sleep(3000);
            } catch (InterruptedException e){
                System.out.println("Sistem Intrupsi");
                break;
            }

            currNode = currNode.next;
        }
    }
// Backward Tampilan Berita
    void  backwardBerita() throws InterruptedException{
        if (head == null) {
            return;
        }
        isRunning = true;
        Node currNode = head.prev;

        while (isRunning) {
            System.out.println("<<< " + currNode.data + " >>>");

            try{
                Thread.sleep(3000);
            } catch(InterruptedException e){
                System.out.println("Sistem Intrupsi");
                break;
            }
            
            currNode = currNode.prev;
        }
    }
// Hapus Berita
    void hapusBerita(int pos){
        if (head == null || pos < 1 || pos > size) {
            System.out.println("Gagal Menghapus Berita: Posisi Input Tidak Valid");
            return;
        }
        Node curr = head;
        for(int i = 1; i < pos; i++){
            curr = curr.next;
        }

        if (size == 1) {
            head = null;
        } else {
            curr.prev.next = curr.next;
            curr.next.prev = curr.prev;
            if (curr == head) {
                head = curr.next;
            }
        }
        size--;

        System.out.println("Data Berita: " + curr.data + " Berhasil Dihapus!");
    }
// Tampilkan Berita
    void tampilanPilihanBerita(int pos){
        if (head == null) {
            System.out.println("Berita Kosong!");
            return;
        }
        if (pos > size || pos < 1) {
            System.out.println("Nomor Berita Tidak Ditemukan!");
            return;
        }
        Node curr = head;
        for(int i = 1; i < pos; i++){
            curr = curr.next;
        }
        System.out.println("Data: "+ curr.data);

    }
}

public class circularDobList {
    public static void main(String[] args) throws InterruptedException {
            Scanner sc = new Scanner(System.in);
            int pilihanMenu;
            CircularDoublyLinkedList list = new  CircularDoublyLinkedList();
            System.out.println("==== Sistem Running Text Berita ====");
            do { System.out.println("=============================================");
                System.out.println("Pilih Menu: ");
                System.out.println("1. Tambahkan Data Berita ");
                System.out.println("2. Hapus Data Berita ");
                System.out.println("3. Tampilkan Running Text Berita Forward (3s) ");
                System.out.println("4. Tampilkan Runing Text Berita Backward (3s) ");
                System.out.println("5. Tampilkan Data Berita");
                System.out.println("6. Keluar");
                System.out.println("=============================================");
                System.out.println("Pilihan: ");

                pilihanMenu = sc.nextInt();
                sc.nextLine();
                switch (pilihanMenu) {
                    case 1:
                        System.out.println("=== Tambah Berita ===");
                        System.out.println("Masukkan Text Berita: ");
                        String dataBerita = sc.nextLine();

                        list.tambahBerita(dataBerita);
                        break;
                    case 2: 
                        System.out.println("=== Hapus Berita ===");
                        System.out.println("Masukkan Nomor Berita: ");
                        list.hapusBerita(sc.nextInt());
                        break;
                    case 3:
                        System.out.println("=== Tampil Forward Berita ===");
                        System.out.println("(Tekan tombol ENTER kapan saja untuk berhenti...)");
                        System.out.println("----------------------------------------------");

                        Thread threadBerita = new Thread(() -> {
                            try {
                                list.forwardBerita();
                            } catch (InterruptedException e) {
                            }
                        });
                        threadBerita.start();
                        sc.nextLine(); 

                        sc.nextLine(); 

                        list.isRunning = false; 
                        threadBerita.interrupt();

                        System.out.println("\n=============================================");
                        System.out.println("Berita berhasil dihentikan. Kembali ke menu utama.");
                        break;
                    case 4:
                        System.out.println("=== Tampil Backward Berita ===");
                        System.out.println("(Tekan tombol ENTER kapan saja untuk berhenti...)");
                        System.out.println("----------------------------------------------");

                        Thread threadBeritaBack = new Thread(() -> {
                            try {
                                list.backwardBerita();
                            } catch (InterruptedException e) {
                            }
                        });

                        threadBeritaBack.start();

                        sc.nextLine(); 
                        sc.nextLine(); 

                        list.isRunning = false;
                        threadBeritaBack.interrupt();

                        System.out.println("\n=============================================");
                        System.out.println("Berita berhasil dihentikan. Kembali ke menu utama.");
                        break;
                    case 5:
                        System.out.println("=== Cari Berita ===");
                        System.out.println("Masukkan Nomor Berita: ");
                        int pos = sc.nextInt();
                        list.tampilanPilihanBerita(pos);
                        break;
                    case 6:
                        sc.close();
                        System.out.println("Program Telah Ditutup");
                        System.exit(0);
                        break;
                }
            } while (true);
    }
}